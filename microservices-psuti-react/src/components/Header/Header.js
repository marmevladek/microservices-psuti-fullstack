import { Link, Navigate } from "react-router-dom"
import { Helmet } from "react-helmet";
import { useState, useCallback, useEffect } from "react";
import { logout } from "../../actions/auth";
import "./StyleHeader.css"
import { useSelector, useDispatch} from "react-redux";
import EventBus from "../../common/EventBus"


const Header = () => {
    const [theme, setTheme] = useState("light");
    const { user: currentUser } = useSelector((state) => state.auth);
    
    const dispatch = useDispatch();

    const log0ut = useCallback(() => {
        dispatch(logout());
      }, [dispatch]);
    
      useEffect(() => {
        EventBus.on("logout", () => {
          logout();
        });
        
        return () => {
          EventBus.remove("logout");
        };
      }, [currentUser, log0ut]);

    return (
        <>
        <Helmet>
                <link rel="preconnect" href="https://fonts.googleapis.com" />
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin="true" />
                <link
                    href="https://fonts.googleapis.com/css2?family=Philosopher:ital,wght@0,400;0,700;1,400&family=Roboto:ital,wght@0,400;0,700;1,400&display=swap"
                    rel="stylesheet"
                />
            </Helmet>
        <header className="page-header">
                <nav className="main-nav">
                    <div className="theme-content">
                        <ul className="theme-switcher">
                            <p className="theme">Тема</p>
                            <li>
                                <button
                                    className={`theme-button-light ${theme === "light" ? "active" : ""}`}
                                    type="button"
                                    // onClick={() => toggleTheme("light")}
                                ></button>
                            </li>
                            <li>
                                <button
                                    className={`theme-button-dark ${theme === "dark" ? "active" : ""}`}
                                    type="button"
                                    // onClick={() => toggleTheme("dark")}
                                ></button>
                            </li>
                        </ul>
                    </div>
                    <ul className="site-navigation">
                        
                        {currentUser.role.includes("ROLE_TEACHER") ? (
                            <li className="site-navigation-item">
                            <Link to={`/teacher/main`}>
                                <a href="#">Главная страница</a>
                            </Link>
                            </li>
                        ) : (
                            <>
                            <li className="site-navigation-item">
                            <Link to={`/student/main`}>
                                <a href="#">Главная страница</a>
                            </Link>
                            </li>
                            <li className="site-navigation-item">
                                <Link to={`/student/history`}>
                                <a href="#">История</a>
                                </Link>
                            </li>
                            </>
                            
                        )}
                        
                        <li className="site-navigation-item">
                            <Link to={`/auth/login`}>
                            <button onClick={log0ut}>Выход</button>
                            </Link>
                            
                        </li>
                    </ul>
                </nav>
            </header>
        </>
        
    )
}

export default Header;