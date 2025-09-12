import { BrowserRouter, Link, Routes, Route} from 'react-router-dom'
import Home from "./pages/Home"
import Company from "./pages/Company"
import Portfolio from "./pages/Portfolio"
import Support from "./pages/Support"

function App() {

    return (
        <BrowserRouter>
            <nav>
                <Link style={{margin:"30px"}} to="/">Home</Link>
                <Link style={{margin:"30px"}} to="/company">Company</Link>
                <Link style={{margin:"30px"}} to="/portfolio">Portfolio</Link>
                <Link style={{margin:"30px"}} to="/support">Support</Link>
            </nav>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/company" element={<Company/>}/>
                <Route path="/portfolio" element={<Portfolio/>}/>
                <Route path="/support" element={<Support/>}/>
            </Routes>
        </BrowserRouter>
    );
}

export default App
