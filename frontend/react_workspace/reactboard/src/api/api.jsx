/*비동기방식의 요청을 시도할 예정 
 Vue, react  jquery ajax() 를 잘 사용하지 않음 

 [ axios  ] 
    - 비동기 방식의 요청 객체 
    - web 뿐만 아니라,  node.js와 같은 독립실행형 자바스크립트에서도 사용이 가능
    - JSON 자동 변환 지원 따라서 서버가 보내준 데이터를 바로 접근가능(data.boardList)
    - React, vue 프로젝트에서 axios가 사실 상 표준임     

[ jquery ajax]
    - jquery 에서만 지원하는 웹용 비동기 요청 객체 
    - 콜백 기반이라, 코드가 복잡해질 수 있음 
    - node.js 에서는 사용불가
    - 서버가 보내준 정보를 json 으로 직접 파싱
    - 기존 레거시 프로젝트에서 많이 사용해왔으므로, 유지보수 시 알아야 함
 */
import axios from 'axios';

//공통 URL을 선언
const api = axios.create({
  baseURL: "http://localhost:7777/api"
});

//목록보기 요청 
export const getBoards = () => api.get("/boards"); //목록 요청 
export const getBoard = (boardId) => api.get(`/boards/${boardId}`); //한건 요청
export const insertBoard = (data) => api.post("/boards", data); //글등록
export const updateBoard = (boardId, data) => api.put(`/boards/${boardId}`, data); //글수정
export const deleteBoard = (boardId) => api.delete(`/boards/${boardId}`);//글 삭제
