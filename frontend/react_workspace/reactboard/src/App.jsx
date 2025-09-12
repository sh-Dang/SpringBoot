import BoardList from "./pages/BoardList";
import BoardForm from "./pages/BoardForm";
import BoardDetail from "./pages/BoardDetail";
import {Route, Routes, BrowserRouter} from "react-router-dom";

function App() {

    return (
        <>
            <div>
                게시판
            </div>

            {/* 화면전환 모듈 */}
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<BoardList/>}/>
                    <Route path="/new" element={<BoardForm/>}/>
                    <Route path="/view" element={<BoardDetail/>}/>
                </Routes>
            </BrowserRouter>
        </>
    )
}

export default App
