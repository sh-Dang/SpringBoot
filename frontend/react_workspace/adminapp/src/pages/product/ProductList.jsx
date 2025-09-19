import { useEffect, useState } from 'react';
import { getProducts } from '../../utils/ProductApi';
import { Link } from 'react-router-dom'; // Make sure this is imported

export default function ProductList() {
    const [productList, setProductList] = useState([]);

    const getProductList = async () => {
        console.log("상품 목록 요청");
        try {
            const response = await getProducts();
            const data = response.data;
            console.log("서버에서 받아온 결과 는", data);

            // The product list is in the 'result' property
            if (data && Array.isArray(data.result)) {
                setProductList(data.result);
            }
            // Check for paginated response (content property)
            else if (data && Array.isArray(data.content)) {
                setProductList(data.content);
            }
            // Check if the response data is an array itself
            else if (Array.isArray(data)) {
                setProductList(data);
            }
            // Handle unexpected data structure
            else {
                console.error("Received data's product list is not in a recognized format:", data);
                setProductList([]); // Set to empty array to prevent crash
            }
        } catch (error) {
            console.error("Error fetching products:", error);
            setProductList([]); // Set to empty array on error
        }
    };

    useEffect(() => {
        getProductList();
    }, []);

    return (
        <>
            <div className="row">
                <div className="col-12">
                    <div className="card">
                        <div className="card-header">
                            <h3 className="card-title">상품목록</h3>
                            <div className="card-tools">
                                <div className="input-group input-group-sm" style={{ width: 150 }}>
                                    <input type="text" name="table_search" className="form-control float-right" placeholder="Search" />
                                    <div className="input-group-append">
                                        <button type="submit" className="btn btn-default">
                                            <i className="fas fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="card-body table-responsive p-0">
                            <table className="table table-hover text-nowrap">
                                <thead>
                                    <tr>
                                        <th>번호</th>
                                        <th>이미지</th>
                                        <th>카테고리</th>
                                        <th>상품명</th>
                                        <th>브랜드</th>
                                        <th>가격</th>
                                        <th>할인가</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {Array.isArray(productList) && productList.map(product => {
                                        const fileName = product.productFileList && product.productFileList.length > 0
                                            ? product.productFileList[0].fileName
                                            : null;
                                        
                                        const imageUrl = fileName
                                            ? `http://localhost:7777/productapp/resource/p${product.productId}/${fileName}`
                                            : 'https://via.placeholder.com/35'; // Placeholder

                                        return (
                                            <tr key={product.productId}>
                                                <td>{product.productId}</td>
                                                <td>
                                                    <img src={imageUrl} style={{ width: 35 }} alt={product.productName} />
                                                </td>
                                                <td>{product.subCategoryDTO?.subName}</td>
                                                <td>
                                                    <Link to={`/product/detail/${product.productId}`}>{product.productName}</Link>
                                                </td>
                                                <td>{product.brand}</td>
                                                <td>{product.price}</td>
                                                <td>{product.discount}</td>
                                            </tr>
                                        );
                                    })}
                                    <tr>
                                        <td colSpan={7}>
                                            <button type='button'>상품등록</button>
                                            <button type='button' onClick={getProductList}>상품목록</button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}
