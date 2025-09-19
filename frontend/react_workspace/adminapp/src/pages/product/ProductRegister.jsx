import { useState } from "react";
import { registerProduct } from "../../utils/ProductApi";
import { useNavigate } from "react-router-dom";

export default function ProductRegister(){
    const navigate=useNavigate();
    const [files, setFiles]=useState([]); 
    const [previews, setPreviews]=useState([]); 
    
    //한꺼번에 선언할 수 있다는 이점을 가진 JSON으로 간다.
    //폼양식의 파라미터 들
    const [formData, setFormData] = useState({
        subCategoryId:0,
        product_name:'',
        brand:'',
        price:0,
        discount:0,
        detail:''
    });

    /* UseState 가지고놀기(문법 예제) */
    // const [numbers, setNumbers] = useState([1,2]);
    // setNumbers((previousValue)=>[...previousValue, 3]);

    const handleFileChange=(e)=>{
        console.log("파일변경정보는===",e);
        // 사용자가 선택한 이미지들을 얻어와서 previews 배열에 반영시키자
        // e.target.files에는 선택한 이미지들이 유사배열 형태로 들어있다
        // readOnly 이므로, 수정이 불가능하다
        // 대안? 일반 배열을 선언하여 유사배열안의 요소들을 복사해놓기
        const selectedFiles=Array.from(e.target.files); //매개변수로 지정된 배열의 요소를 하나씩 접근하여, 새로운 배열 반환
        console.log("셀파================",selectedFiles);

        //setFiles(미리보았던 배열 + 지금 추가로 선택한 배열)
        setFiles((prev)=>[...previews, ...selectedFiles]);

        // 이미지가 아닌것을 제외
        //filter()는 선언적 메서드로서 화살표함수에서 호출된 로직에 맞는 새로운 배열을 반환
        const newPreviews =  selectedFiles
                                .filter((file)=>file.type.startsWith("image/")) // 이것은 곧 새로운 배열의 탄생을 의미
                                .map((file)=>({
                                    name : file.name, // alt에 써먹을 것
                                    url : URL.createObjectURL(file) //src에 대입할 용도
                                })); // 어떤 요소로 채울지 결정

        //합쳐진 배열 수 만큼 화면에 출력
        // setPreviews(()=>[기존의 미리보기 배열, 새롭게 합쳐진 배열]);
        setPreviews((prev)=>[...prev, ...newPreviews]);

    }
    
    //미리보기 이미지 삭제
    //name 매개변수는 삭제할 이미지 명
    const removePreview = (name)=>{
        // 넘어온 name은 남아있을 아이들, 삭제될 아이는 삭제될 아이이기 때문에 비교연산자 !=
        setPreviews((prev)=>prev.filter( (p)=>p.name !== name ));

        //미리보기와 업로드 할 이미지를 동기화
        setFiles((prev)=> prev.filter((f)=>f.name !== name));
    }

    /*서버에 파일 업로드*/
    const upload = async () => {
        // text와 파일을 포함한 파라미터를 전송할 때는 FormData 사용
        // append()를 FormData객체안에 key : value 값으로 넣어주기
        const sendData = new FormData();

        sendData.append("subCategoryDTO.subCategoryId", formData.subCategoryId);
        sendData.append("productName", formData.product_name);
        sendData.append("brand", formData.brand);
        sendData.append("price", formData.price);
        sendData.append("discount", formData.discount);
        sendData.append("detail", formData.detail);

        // 바이너리 파일추가..(배열의 수만큼 반복하면서 sendData 에 넣기)
        files.forEach((file)=>sendData.append("files", file));

        registerProduct(sendData)
        .then(res=>{ //서버로부터 응답정보가 올 경우 응답정보를 출력해보기
            console.log("서버로 부터 응답받은 정보는 ", res);
            navigate('/product/list');
        })
        .catch(err=>{
            console.log("에러발생", err);
        });
    }

    const handleInput = (e)=>{
        setFormData({...formData, [e.target.name]:e.target.value});
    }


    return(
        <>
            {/* <!-- general form elements --> */}
            <div className="card card-primary">
                <div className="card-header">
                    <h3 className="card-title">상품 등록 폼</h3>
                </div>
                {/* <!-- /.card-header --> */}
                {/* <!-- form start --> */}
                <form>
                    <div className="card-body">
                        <div className="form-group">

                            <div className="form-group">
                                <select name="topcategoryId" onChange={handleInput}>
                                    <option value="">상위카테고리 선택</option>
                                </select>
                            </div>
                            <div className="form-group">
                                <select name="subCategoryId" onChange={handleInput}>
                                    <option value="">하위카테고리 선택</option>
                                    <option value="1">운동화</option>
                                </select>
                            </div>

                            <div className="form-group">
                                <input type="text" className="form-control" name="product_name" placeholder="상품명" onChange={handleInput}/>
                            </div>
                            <div className="form-group">
                                <input type="text" className="form-control" name="brand" placeholder="브랜드" onChange={handleInput}/>
                            </div>
                            <div className="form-group">
                                <input type="number" className="form-control" name="price" placeholder="가격" onChange={handleInput}/>
                            </div>
                            <div className="form-group">
                                <input type="number" className="form-control" name="discount" placeholder="할인금액" onChange={handleInput}/>
                            </div>
                            <div className="form-group">
                                <textarea className="form-control" name="detail" placeholder="상세설명" onChange={handleInput}/>
                            </div>
{/* 
                            <div className="form-group">
                                <label htmlFor="exampleInputEmail1">상품설명</label>
                                <input type="text" className="form-control" id="exampleInputEmail1" placeholder="상품설명을 입력하세요"/>
                            </div> */}

                            <div className="form-group">
                                <label htmlFor="productFile">상품 이미지</label>
                                <input type="file" className="form-control" id="productFile" name="product_file" multiple onChange={handleFileChange}/>
                            </div>

                            {/* 이미지 미리보기 영역 */}
                            <div style={{display:'flex', gap:10, flexWrap:'wrap', marginTop:15}}>
                                {previews.map(preview=>(
                                    <div key={preview.name} style={{position:'relative', display:'inline-block'}}>
                                        <img src={preview.url} alt={preview.name} style={{width:120, height:120, objectFit:'cover', border:'1px solid #CCC', borderRadius:5}}/>
                                        <button style={{position:'absolute', top:0, right:0, background:'red', color:'white', border:'none', borderRadius:'50%', cursor:'pointer', width:20, height:20, lineHeight:18, padding:0}} onClick={()=>{removePreview(preview.name)}}>X</button>
                                    </div>
                                ))}
                            </div>
                            
                            {/* 업로드 버튼 */}
                            <div className="form-group">
                                <button className="form-control" onClick={upload}>업로드</button>
                            </div>


                        </div>
                    </div>
                    {/* <!-- /.card-body --> */}
                </form>
            </div>
            {/* <!-- /.card --> */}
        </>
    )
}