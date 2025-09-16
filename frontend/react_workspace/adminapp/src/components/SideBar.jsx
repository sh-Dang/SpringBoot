import { Link } from 'react-router-dom';
import { useState } from 'react';

export default function Sidebar() {
    const [productMenuOpen, setProductMenuOpen] = useState(false);
    const [memberMenuOpen, setMemberMenuOpen] = useState(false);
    const [orderMenuOpen, setOrderMenuOpen] = useState(false);

    return (
        <>
            {/* Main Sidebar Container */}
            <aside className="main-sidebar sidebar-dark-primary elevation-4">
                {/* <!-- Brand Logo --> */}
                <a href="index3.html" className="brand-link">
                    <img src="/dist/img/AdminLTELogo.png" alt="AdminLTE Logo" className="brand-image img-circle elevation-3" style={{ opacity: '.8' }} />
                    <span className="brand-text font-weight-light">AdminLTE 3</span>
                </a>

                {/* <!-- Sidebar --> */}
                <div className="sidebar">
                    {/* <!-- Sidebar user panel (optional) --> */}
                    <div className="user-panel mt-3 pb-3 mb-3 d-flex">
                        <div className="image">
                            <img src="/dist/img/user2-160x160.jpg" className="img-circle elevation-2" alt="User Image" />
                        </div>
                        <div className="info">
                            <a href="#" className="d-block">Alexander Pierce</a>
                        </div>
                    </div>

                    {/* <!-- SidebarSearch Form --> */}
                    <div className="form-inline">
                        <div className="input-group" data-widget="sidebar-search">
                            <input className="form-control form-control-sidebar" type="search" placeholder="Search" aria-label="Search" />
                            <div className="input-group-append">
                                <button className="btn btn-sidebar">
                                    <i className="fas fa-search fa-fw"></i>
                                </button>
                            </div>
                        </div>
                    </div>

                    {/* <!-- Sidebar Menu --> */}
                    <nav className="mt-2">
                        <ul className="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                            {/* 상품 관리 */}
                            <li className={`nav-item ${productMenuOpen ? 'menu-open' : ''}`}>
                                <a href="#" className="nav-link" onClick={(e) => { e.preventDefault(); setProductMenuOpen(!productMenuOpen); }}>
                                    <i className="nav-icon fas fa-copy"></i>
                                    <p>
                                        상품 관리
                                        <i className="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul className="nav nav-treeview">
                                    <li className="nav-item">
                                        <Link to="/product/list" className="nav-link">
                                            <i className="far fa-circle nav-icon"></i>
                                            <p>상품 목록</p>
                                        </Link>
                                    </li>
                                    <li className="nav-item">
                                        <Link to="/product/registerform" className="nav-link">
                                            <i className="far fa-circle nav-icon"></i>
                                            <p>상품 등록</p>
                                        </Link>
                                    </li>
                                </ul>
                            </li>

                            {/* 회원 관리 */}
                            <li className={`nav-item ${memberMenuOpen ? 'menu-open' : ''}`}>
                                <a href="#" className="nav-link" onClick={(e) => { e.preventDefault(); setMemberMenuOpen(!memberMenuOpen); }}>
                                    <i className="nav-icon fas fa-user"></i>
                                    <p>
                                        회원 관리
                                        <i className="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul className="nav nav-treeview">
                                    <li className="nav-item">
                                        <Link to="/member/list" className="nav-link">
                                            <i className="far fa-circle nav-icon"></i>
                                            <p>회원 목록</p>
                                        </Link>
                                    </li>
                                </ul>
                            </li>

                            {/* 주문 관리 */}
                            <li className={`nav-item ${orderMenuOpen ? 'menu-open' : ''}`}>
                                <a href="#" className="nav-link" onClick={(e) => { e.preventDefault(); setOrderMenuOpen(!orderMenuOpen); }}>
                                    <i className="nav-icon fas fa-shopping-cart"></i>
                                    <p>
                                        주문 관리
                                        <i className="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul className="nav nav-treeview">
                                    <li className="nav-item">
                                        <Link to="/order/list" className="nav-link">
                                            <i className="far fa-circle nav-icon"></i>
                                            <p>주문 목록</p>
                                        </Link>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </nav>
                    {/* <!-- /.sidebar-menu --> */}
                </div>
                {/* <!-- /.sidebar --> */}
            </aside>
        </>
    );
}