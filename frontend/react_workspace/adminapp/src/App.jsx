import './App.css'
import PreLoader from './components/Preloader'
import Navbar from './components/Navbar'
import Sidebar from './components/SideBar'
import Footer from './components/Footer'
import ProductList from './pages/product/ProductList'
import ProductRegister from './pages/product/ProductRegister'
import ProductDetail from './pages/product/ProductDetail'
import OrderList from './pages/order/OrderList'
import { BrowserRouter, Routes, Route } from 'react-router-dom'

function App() {

  return (
    <BrowserRouter>
        <div className='wrapper'>
            <PreLoader/>
            <Navbar/>
            <Sidebar/>
            {/* 페이지전환될 영역 */}
            <div className='content-wrapper'>
                <section className='content'></section>
            <Routes>
                {/* 상품관련 */}
                
                {/* 상품목록 */}
                <Route path="/product/list" element={<ProductList/>}/>

                {/* 상품상세 */}
                <Route path="/product/detail/:productId" element={<ProductDetail/>}/>

                {/* 상품등록 */}
                <Route path="/product/registerform" element={<ProductRegister/>}/>

                {/* 주문관련 */}
                <Route path="/order/list" element={<OrderList/>}/>
            </Routes>
            </div>
            <Footer/>
        </div>
    </BrowserRouter>
  )
}

export default App
