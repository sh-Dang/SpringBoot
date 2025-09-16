import axios from 'axios';

//상품관령 공통 URL
const URL = "http://localhost:7777/products";

//상품목록
export const getProducts = ()=>axios.get(URL);
//상품등록
export const registerProduct = (data)=>axios.post(URL, data);
//상품한건 가져오기
export const getProduct = (productId)=>axios.get(`${URL}/${productId}`);
//상품수정
export const updateProduct = (data)=>axios.put(`${URL}/${productId}`, data);
//상품삭제
export const deleteProduct = (productId)=>axios.delete(`${URL}/${productId}`);