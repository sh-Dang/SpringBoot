import { useNavigate } from "react-router-dom";
import { insertBoard, updateBoard } from "../api/api";

export default function BoardForm(){
    const isEdit = false;
    const navigate = useNavigate();

    //이 컴포넌트에서 관리되어질 상태값들
    const [title, setTitle]= useState(''); // /= ref('');
    const [writer, setWriter]= useState('');
    const [content, setContent]= useState('');

    //함수 정의
    const saveBoard = async() => {
        //글쓰기
        const data = {title, writer, content};
        await insertBoard(data); //jquery ajax처럼 JSON.stringfy() 할 필요없음

        //글수정
        // 글쓰기/글수정 성공시 목록으로 가자
navigate('/')
    };

    return (
        
        <div>
            <h2>{ isEdit ? '글 수정' : '글 새로 작성'}</h2>
            <form>
                <div>
                    <input type="text" value={title} placeholder="제목을 입력하쇼"/>
                </div>
                <div>
                    <input type="text" placeholder="작성자를 입력하세요"/>
                </div>
                <div>
                    <textarea placeholder="내용을 입력하세요"/>
                </div>
                <button type="button" onClick={()=>{saveBoard('/boards')}}>
                    { isEdit ? '수정하기' : '등록하기'}
                </button>
                <button type="button" onClick={()=>navigate('/')}>목록보기</button>
            </form>
        </div>
    )
}