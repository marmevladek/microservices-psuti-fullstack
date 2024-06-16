import React, { useState, useEffect, useCallback } from "react";
import { Helmet } from "react-helmet";
import { useSelector, useDispatch } from "react-redux";
import { Link, Navigate } from "react-router-dom";
import userService from "../../services/student.service";
import { logout } from "../../actions/auth";
import "./Style.css";
import icon from "../../assets/Icon.svg";
import icon2 from "../../assets/Icon2.svg";
import sending from "../../assets/sending.svg";

const StudentMain = () => {
    const { user: currentUser } = useSelector((state) => state.auth);
    const dispatch = useDispatch();

    const [currentFile, setCurrentFile] = useState(undefined);
    const [message, setMessage] = useState("");
    const [theme, setTheme] = useState("light");

    const selectFile = (event) => {
        setCurrentFile(event.target.files[0]);
        setMessage("");
    };

    const upload = () => {
        userService.sendHandling(currentFile, currentUser.uid)
            .then((response) => {
                setMessage(response.data.message);
            })
            .catch((err) => {
                setMessage(err.response?.data?.message || "Не удается загрузить файл!");
            });
    };

    const toggleTheme = useCallback((theme) => {
        setTheme(theme);
        document.body.className = `theme-${theme}`;
    }, []);

    useEffect(() => {
        const fileInput = document.getElementById("file-upload");
        const uploadText = document.getElementById("upload-text");
        const uploadIcon = document.querySelector(".upload-icon");
        const submitButton = document.getElementById("submit-button");
        const sendingMessage = document.querySelector(".sending-message");
        const successMessage = document.getElementById("success");
        const errorButton = document.getElementById("error-button");
        const errorText = document.querySelector(".error-text");

        fileInput.addEventListener("change", (event) => {
            const file = event.target.files[0];
            if (file) {
                uploadText.textContent = file.name;
                uploadIcon.style.display = "none";
                submitButton.style.display = "block";
            }
        });

        submitButton.addEventListener("click", () => {
            submitButton.style.display = "none";
            uploadText.style.display = "none";
            sendingMessage.style.display = "block";

            setTimeout(() => {
                sendingMessage.style.display = "none";
                successMessage.style.display = "block";

                const isError = Math.random() > 0.5;

                if (isError) {
                    errorText.textContent = "";
                    errorButton.style.display = "block";
                }
            }, 2000);
        });

        errorButton.addEventListener("click", () => {
            errorButton.style.display = "none";
            errorText.textContent = "";
            successMessage.style.display = "none";
            uploadText.style.display = "block";
            uploadText.textContent = "Загрузить работу";
            uploadIcon.style.display = "block";
            fileInput.value = "";
            submitButton.style.display = "none";
        });
    }, []);

    if (!currentUser) {
        return <Navigate to="/auth/login" />;
    }

    if (!currentUser.role.includes("ROLE_STUDENT")) {
        return <Navigate to="/teacher/main" />;
    }

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
                                    onClick={() => toggleTheme("light")}
                                ></button>
                            </li>
                            <li>
                                <button
                                    className={`theme-button-dark ${theme === "dark" ? "active" : ""}`}
                                    type="button"
                                    onClick={() => toggleTheme("dark")}
                                ></button>
                            </li>
                        </ul>
                    </div>
                    <ul className="site-navigation">
                        <li className="site-navigation-item">
                            <a href="#">Главная страница</a>
                        </li>
                        <li className="site-navigation-item">
                            <Link to={`/student/history/19`}>
                            <a href="#">История</a>
                            </Link>
                            
                        </li>
                        <li className="site-navigation-item">
                            <button onClick={logout}>Выход</button>
                        </li>
                    </ul>
                </nav>
            </header>
            <main className="main-content">
                <section className="upload-content">
                    <form id="upload-form">
                        <div className="form-group">
                            <span id="upload-text">Загрузить работу</span>
                            <label htmlFor="file-upload" className="custom-file-upload">
                                <div className="upload-icon">+</div>
                            </label>
                            <input type="file" id="file-upload" name="file-upload" onChange={selectFile} />
                        </div>
                        <button
                            className="submit-button"
                            type="button"
                            id="submit-button"
                            style={{ display: "none" }}
                            disabled={!currentFile}
                            onClick={upload}
                        >
                            Отправить
                        </button>
                        <div className="sending-message" style={{ display: "none" }}>
                            <img src={sending} alt="Загрузка" />
                            <p id="sending">Подождите, мы уже отправляем ваше сообщение</p>
                        </div>
                        <div id="success" style={{ display: "none" }}>
                            <img src={icon} alt="Успешная Загрузка" />
                            <p id="success-message">
                                {message}
                            </p>
                        </div>
                        <div className="error-group" style={{ display: "none" }}>
                            <img src={icon2} alt="Ошибка" />
                            <p className="error-text">
                                {message}
                            </p>
                            <button
                                className="error-button"
                                type="button"
                                id="error-button"
                                style={{ display: "none" }}
                            >
                                Назад
                            </button>
                        </div>
                    </form>
                </section>
            </main>
        </>
    );
}

export default StudentMain;
