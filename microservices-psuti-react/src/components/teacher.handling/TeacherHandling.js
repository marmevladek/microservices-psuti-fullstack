import React, { useState, useEffect } from "react";
import { Navigate, useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import teacherService from "../../services/teacher.service";
import "./Style.css";
import { Helmet } from "react-helmet";

const TeacherHandling = () => {
    const { user: currentUser } = useSelector((state) => state.auth);
    const [handling, setHandling] = useState("");
    const [studentGroup, setStudentGroup] = useState("")
    const [studentName, setStudentName] = useState("")
    const [message, setMessage] = useState("");
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            teacherService.getHandlingById(id)
                .then(response => {
                    setHandling(response.data.handling);
                    setStudentGroup(response.data.studentGroup)
                    setStudentName(response.data.studentName)
                })
                .catch(err => {
                    setMessage(err.response?.data?.message || "Не удается загрузить файл!");
                });
        }
    }, [id]);

    const handleSubmit = (event) => {
        event.preventDefault();
        const commentField = document.getElementById("comment");
        const errorMessage = document.getElementById("error-message");
        const body = document.body;
        body.className += 'page';
        if (commentField.value.trim() === "") {
            errorMessage.style.display = "block";
        } else {
            errorMessage.style.display = "none";
            commentField.value = ""; // Обнуление поля комментария
            event.target.reset(); // Обнуление формы после отправки
        }
    };

    if (!currentUser) {
        return <Navigate to="/auth/login" />;
    }

    if (!currentUser.role.includes("ROLE_TEACHER")) {
        return <Navigate to="/student/main" />;
    }

    return (
        <>
            <Helmet>
            <meta charset="utf-8" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Philosopher:ital,wght@0,400;0,700;1,400&family=Roboto:ital,wght@0,400;0,700;1,400&display=swap"
      rel="stylesheet"
    />
    <link href="css/style.css" rel="stylesheet" />
    <title>PGUTI</title>
            </Helmet>
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
                            <a href="#">Выход</a>
                        </li>
                    </ul>
                </nav>
            </header>
            <main className="main-content">
                <div className="p-container">
                    <h2 className="p-container-title">
                        Группа:
                        <p className="p-container-paragraph">{studentGroup}</p>
                    </h2>
                    <h2 className="p-container-title">
                        Студент:
                        <p className="p-container-paragraph">{studentName}</p>
                    </h2>
                    <h2 className="p-container-title">
                        Тема работы:
                        <p className="p-container-paragraph">
                            Разработка клиентской части веб - приложения
                        </p>
                    </h2>
                    <div className="file-upload">
                        <button className="doc-button" type="button" />
                        <span>Глава 1</span>
                    </div>
                    <form id="feedback-form" onSubmit={handleSubmit}>
                        <div aria-required="true" className="radio">
                            <h2>Установите статус <span className="req">*</span></h2>
                            <input
                                type="radio"
                                id="accept"
                                name="fav_language"
                                value="Accept"
                                required
                            />
                            <label htmlFor="accept">Принято</label>
                            <input
                                type="radio"
                                id="decline"
                                name="fav_language"
                                value="Decline"
                                required
                            />
                            <label htmlFor="decline">Отклонено</label>
                            <p id="error-message" className="error-message" style={{ display: "none" }}>
                                Вы не поставили статус. Это обязательный пункт.
                            </p>
                        </div>
                        <div className="comment-div">
                            <h2>Ваш комментарий <span className="req">*</span></h2>
                            <textarea name="comment" id="comment" required></textarea>
                        </div>
                        <div className="submit-container">
                            <button className="submit" type="submit">Отправить</button>
                            <p id="error-message" className="error-message" style={{ display: "none" }}>
                                Оставьте комментарий. Это обязательный пункт.
                            </p>
                        </div>
                    </form>
                </div>
            </main>
        </>
    );
}

export default TeacherHandling;
