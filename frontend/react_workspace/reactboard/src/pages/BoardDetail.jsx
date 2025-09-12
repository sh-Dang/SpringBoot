import { Link } from "react-router-dom";

export default function BoardDetail(){

    return(
        <div>
            <h2>{'오늘 점심은 피자'}</h2>
            <p>작성자 : {'홍길동'}</p>
            <p>작성일 : {'2025-09-10'}</p>
            <p>조회수 : {15}</p>
            
            <button type="button">삭제</button>
            <Link to={''}>수정</Link>
            <Link to={'/'}>목록</Link>
        </div>
    )
}