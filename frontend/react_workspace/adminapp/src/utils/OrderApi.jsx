import axios from 'axios';

//상품관령 공통 URL
const URL = "http://localhost:7777/orderapp/orders";

//상품목록
export const getOrders = ()=>axios.get(URL);
//상품등록
export const registerOrder = (data)=>axios.post(URL, data);
//상품한건 가져오기
export const getOrder = (orderId)=>axios.get(`${URL}/${orderId}`);
//상품수정
export const updateOrder = (orderId, data)=>axios.put(`${URL}/${orderId}`, data);
//상품삭제
export const deleteOrder = (orderId)=>axios.delete(`${URL}/${orderId}`);