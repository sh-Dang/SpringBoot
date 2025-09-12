/*
CORS(Cross-Origin Resource Sharing) 정책 위반 


*/
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { insertBoard, updateBoard } from "../api/api";

export default function BoardForm(){

    const {boardId} = useParams();
    //넘겨받은 boardId가 있다면 수정, 없으면 새로운 글쓰기
    const isEdit=!!boardId; //true이면 수정, false이면 새 글 작성
    const navigate = useNavigate();

    //이 컨포넌트에서 관리되어질 상태값들..
    const [title, setTitle]=useState('');  //  const title=ref('')와 같다
    const [writer, setWriter]=useState('');  
    const [content, setContent]=useState(''); 


    //함수정의 
    const saveBoard= async() =>{
        console.log("글쓰기 요청");

        //글쓰기
        const data={title,writer,content};
        if(isEdit==false){
            await insertBoard(data); //jquery ajax 처럼 JSON.stringify() 할 필요없음
        }else{
            //글수정
            await updateBoard(boardId, data);
        }

        //쓰기/수정 성공되면 목록으로 가자 
        navigate('/');
    }

    return (
        <div>
            <h2>{ isEdit ? '글 수정':'글 새로 작성'}</h2>                
            <form>
                <div>
                    <input type="text" value={title} onChange={ (e) => setTitle(e.target.value)}/>
                </div>                    
                <div>
                    <input type="text" value={writer} onChange={(e)=>setWriter(e.target.value)}/>
                </div>                    
                <div>
                    <textarea placeholder="내용입력" value={content} onChange={(e)=>setContent(e.target.value)}></textarea>
                </div>   
                <button type="button" onClick={saveBoard}>
                    {isEdit? '수정하기' : '등록하기'}
                </button>   
                <button type="button" onClick={()=>navigate('/')}>목록보기</button>                              
            </form>
        </div>        
    )
}