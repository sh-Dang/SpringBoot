import {useState, useEffect} from 'react'
import { useNavigate, Link } from 'react-router-dom';
import { getBoards } from '../api/api';

//나 아닌 다른 파일에서도 이 함수를 사용하도록 허용해주는 명령어
//ES6 에서는 클래스 뿐아니라 함수를 통해서도 컴포넌트를 정의할 수 있음
export default function BoardList(){
    /*
    useState는 react의 데이터 상태(state) 훅(hook) 으로 BoardList 컴포넌트안에서
    상태 관리를 담당함 
    boards : 현재의 상태값 
    setBoards: 상태를 변경할 수 있는 함수 (setter를 통해서만 제어가능)
    */
    const [boards, setBoards]=useState([]);
    const navigate=useNavigate();

    /*----------------------------------------------
    사이드 이펙트 Hook ( 부수효과를 관리하는 훅)
    사이드 이펙트가 호출되는 시점은 3가지 경우이다.
    1) 매 랜더링마다(이 컴포넌트가 화면에 그려질때 마다) - 거의 안씀
        useEffect()에 매개변수로 배열이 존재하지 않을 경우    
    2) 단 1회만 호출되는 경우 
        useEffect() 매개변수에 빈 배열이 존재할 경우 
    3) 특정 값이 바뀔때 호출되는 경우
        useState에 의한 특정 상태값이나 props 값이 변경될때 
    ------------------------------------------------*/
    useEffect(()=>{ load()} , []);

    //서버와의 통신을 비동기로 진행 
    //aysnc로 선언된 함수는 반드시 내부에 await 명시해야 함 
    //await 가 명시된 함수 호출은 , 해당함수 호출이 완료되어야 그 다음 코드가 수행됨..즉 일종의 
    //동기 방식의 수행됨..(순서지킴)
    const load=async ()=>{
        const res=await getBoards(); //서버로부터 가져온 json 배열 res 보관
        console.log(res.data);
        console.log("목록을 가져왔습니다.");
        setBoards(res.data);
    }
    

    return (
            <table>
                <thead>
                    <tr>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>등록일</th>
                        <th>조회수</th>
                    </tr>  
                </thead>
                <tbody>
                    
                    {boards.map(b=>(
                    <tr key={b.boardId}>
                        <td><Link to={`/view/${b.boardId}`}>{b.title}</Link></td>
                        <td>{b.writer}</td>
                        <td>{b.regdate}</td>
                        <td>{b.hit}</td>
                    </tr>         
                    ))}
                    <tr>
                        <td colSpan="4">
                            <button type="button" onClick={()=> navigate('/new')}>글쓰기</button>    
                        </td>    
                    </tr>       
                </tbody>
                <tfoot/>
            </table>

    );
}