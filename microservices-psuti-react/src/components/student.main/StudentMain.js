import React, { useState, useEffect } from "react";
import userService from "../../services/user.service";
import eventBus from "../../common/EventBus";
import "./Style.css";
// import indexjs from "./index";
import icon from "../../assets/Icon.svg";
import icon2 from "../../assets/Icon2.svg";
import sending from "../../assets/sending.svg";
import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";
import { logout } from "../../actions/auth";

const StudentMain = () => {
    const { user: currentUser } = useSelector((state) => state.auth);

    


    const [currentFile, setCurrentFile] = useState(undefined);
    const [message, setMessage] = useState("");
    const [isSuccess, setIsSuccess] = useState(false);
    const [isError, setIsError] = useState(false);

    const selectFile = (event) => {
        setCurrentFile(event.target.files[0]);

        setMessage("");

    };

    const upload = () => {
        userService.sendHandling(currentFile, currentUser.uid)
        .then((response) => {
            setMessage(response.data.message);
            setIsSuccess(true);
        })
        .catch((err) => {
            if (err.response && err.response.data && err.response.data.message) {
                setMessage(err.response.data.message);
                       
            } else {
                setMessage("Не удается загрузить файл!")
            }
            
            setIsError(true);
            setCurrentFile(undefined)
        })

    };

    useEffect(() => {
        const fileInput = document.getElementById("file-upload");
        const uploadText = document.getElementById("upload-text");
        const uploadIcon = document.querySelector(".upload-icon");
        const submitButton = document.getElementById("submit-button");
        const sendingMessage = document.querySelector(".sending-message");
        const successMessage = document.getElementById("success");
        const errorButton = document.getElementById("error-button");
        const errorText = document.querySelector(".error-text");

        const themeButtonLight = document.querySelector(".theme-button-light");
        const themeButtonDark = document.querySelector(".theme-button-dark");

        function toggleTheme(theme) {
            document.body.classList.toggle("theme-light", theme === "light");
            document.body.classList.toggle("theme-dark", theme === "dark");
        }

        themeButtonLight.addEventListener("click", () => {
            toggleTheme("light");
        });

        themeButtonDark.addEventListener("click", () => {
            toggleTheme("dark");
        });

        const fileChangeHandler = (event) => {
            const file = event.target.files[0];
            if (file) {
                uploadText.textContent = file.name;
                uploadIcon.style.display = "none";
                submitButton.style.display = "block";
            }
        };

        const submitHandler = () => {
            submitButton.style.display = "none";
            uploadText.style.display = "none";
            sendingMessage.style.display = "block";

            setTimeout(() => {

                sendingMessage.style.display = "none";
                if (isSuccess) {
                    successMessage.style.display = "block";
                }
                
                

                // const isError = Math.random() > 0.5;

                if (isError) {
                    errorText.textContent = "";
                    errorButton.style.display = "block";
                }
            }, 2000);
        };

        const errorButtonClickHandler = () => {
            if (isError) errorButton.style.display = "none";
            if (isError) errorText.textContent = "";
            successMessage.style.display = "none";
            uploadText.style.display = "block";
            uploadText.textContent = "Загрузить работу";
            uploadIcon.style.display = "block";
            fileInput.value = "";
            submitButton.style.display = "none";
        };

        fileInput.addEventListener("change", fileChangeHandler);
        submitButton.addEventListener("click", submitHandler);
        if (isError) errorButton.addEventListener("click", errorButtonClickHandler);

        return () => {
            fileInput.removeEventListener("change", fileChangeHandler);
            submitButton.removeEventListener("click", submitHandler);
            if (isError) errorButton.removeEventListener("click", errorButtonClickHandler);
        };
    }, []);

    if (!currentUser) {
        return <Navigate to="/auth/login" />;
    }

    if (!currentUser.role.includes("ROLE_STUDENT")) {
        return <Navigate to="/teacher/main" />;
    }

    return (
        <>
            <header className="page-header">
                <nav className="main-nav">
                    <div className="theme-content">
                        <ul className="theme-switcher">
                            <p className="theme">Тема</p>
                            <li>
                                <button className="theme-button-light active" type="button"></button>
                            </li>
                            <li>
                                <button className="theme-button-dark" type="button"></button>
                            </li>
                        </ul>
                    </div>
                    <ul className="site-navigation">
                        <li className="site-navigation-item">
                            <a href="#">Главная страница</a>
                        </li>
                        <li className="site-navigation-item">
                            <a href="#">История</a>
                        </li>
                        <li className="site-navigation-item">
                            <a href="#">Выход</a>
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
                        {isSuccess && (
                            <div id="success" style={{ display: "none" }}>
                            <img src={icon} alt="Успешная Загрузка" />
                            <p id="success-message">
                                {message}
                            </p>
                            </div>
                        )}
                        {isError && (
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
                        )}
                        
                    </form>
                </section>
            </main>
            {/* <script src="index.js"></script> */}
        </>
    );
}

export default StudentMain;
