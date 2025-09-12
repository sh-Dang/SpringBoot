import {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
// 수출.
// +) default가 있는 함수만 EXPORT하겠다
// ES6 부터는 클래스, '함수'를 통해서도 컴포넌트를 정의할 수 있음
export default function BoardList(){
    /*
    useState는 react의 데이터 상태(state)
    훅(Hook)으로 BoardList 컴포넌트 안에서 상태관리를 담당
    boards : 현재의 상태값
    setBoards : 상태를 변경할 수 있는 함수 (setter를 통해서만 제어가능)
    */
                    //setter 
    const [boards, setBoards] = useState([0,0,0,0,0,0,0,0,0,0]);
    const navigate = useNavigate();
    return (
        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Writer</th>
                    <th>Registered</th>
                    <th>Hit</th>
                </tr>
            </thead>
            <tbody>
                {boards.map(b=>(
                <tr>
                    <td><Link to={'/view'}>오늘부터 react 수업시작</Link></td>
                    <td>길동이</td>
                    <td>2025-09-15</td>
                    <td>3</td>                
                </tr>
                ))}
                <tr>
                    <td colSpan="4">
                        <button type='button' onClick={()=> navigate('/new')}>글쓰기</button>
                    </td>
                </tr>

            </tbody>
        </table>
    )
}