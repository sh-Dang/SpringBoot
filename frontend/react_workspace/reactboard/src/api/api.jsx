/* 비동기방식의 요청을 시도할 예정
    Vue, react => jquery ajax() 사용안함
    [axios]
    - 비동기 방식의 요청 객체
    - web 뿐만 아니라, node.js와 같은 독립실행형 자바스크림트에서도 사용 가능
    - JSON 자동 변환 지원 따라서 서버가 보내준 데이터를 바로 접근 가능(data.boardList)
    - React, Vue 프로젝트에서 axios가 사실상 표준임

    [jquery ajax]
    - jquery에서만 지원하는 웹용 비동기 요청 객체
    - 콜백 기반이라, 코드가 복잡해질 수 있음
    - node.js에서는 사용불가
    - 서버가 보내준 정보를 json으로 직접 파싱
    - 기존 레거시 프로젝트에서 많이 사용해 왔으므로, 유지보수 시 알아야 함
*/
import axios from 'axios';

//공통 URL을 선언
const URL = 'http://localhost:7777/api/boards';

//목록보기 요청
export const getBoards = () => axios.get(URL); //목록 요청
// export const getBoard = boardId => axios.get(URL + "/" + boardId);
export const getBoard = boardId => axios.get(`${URL}/${boardId}`); //한 건 요청
export const insertBoard = data => axios.post(URL, data);
export const updateBoard = (boardId, data) => axios.put(`${URL}/${boardId}`, data); //한 건 수정 요청
export const deleteBoard = boardId => axios.delete(`${URL}/${boardId}`); // 글 삭제 요청