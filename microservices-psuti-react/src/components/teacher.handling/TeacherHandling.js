import React, { useState, useEffect, useCallback } from "react";
import { Helmet } from "react-helmet";
import { useSelector, useDispatch } from "react-redux";
import { Navigate, useParams } from "react-router-dom";
import userService from "../../services/student.service";
import { logout } from "../../actions/auth";
import "./Style.css";
import teacherService from "../../services/teacher.service";

const TeacherHandling = () => {
    const {user: currentUser} = useSelector((state) => state.auth)

    if (!currentUser) {
        <Navigate to="/auth/login" />
    }

    if (!currentUser.role.includes("ROLE_TEACHER")) {
        <Navigate to="/student/main" />
    }

    const [handling, setHandling] = useState("")
    const [message, setMessage] = useState("")
    const {id} = useParams()

    useEffect(() => {
        teacherService.getHandlingById(id)
        .then(response => {
            setHandling(response.data)
        })
        .catch((err) => {
            setMessage(err.response?.data?.message || "Не удается загрузить файл!");
        });
    }, [])

    return(
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
            <a href="#">Выход</a>
          </li>
        </ul>
      </nav>
    </header>
    <main className="main-content">
      <div className="container">
        <h2 className="container-title">
          Группа:
          <p className="container-paragraph">ИСТ-01</p>
        </h2>

        <h2 className="container-title">
          Студент:
          <p className="container-paragraph">Иванов Иван</p>
        </h2>
        <h2 className="container-title">
          Тема работы:
          <p className="container-paragraph">
            Разработка клиентской части веб - приложения
          </p>
        </h2>
        <div className="file-upload">
          <img src="img/word.svg" alt="Word Icon" />
          <span>Глава 1</span>
        </div>
        <form id="feedback-form">
          <div aria-required="true" className="radio">
            <h2>Установите статус <span className="req">*</span></h2>
            <input
              type="radio"
              id="accept"
              name="fav_language"
              value="Accept"
            />
            <label for="accept">Принято</label>
            <input
              type="radio"
              id="decline"
              name="fav_language"
              value="Decline"
            />
            <label for="decline">Отклонено</label>
            <p id="error-message" className="error-message" style={{display: "none"}}>
              Вы не поставили статус. Это обязательный пункт.
            </p>
          </div>
          <div className="comment-div">
            <h2>Ваш комментарий <span className="req">*</span></h2>
            <textarea name="comment" id="comment" required></textarea>
          </div>
          <div className="submit-container">
            <button className="submit" type="submit">Отправить</button>
            <p id="error-message" className="error-message" style={{display: "none"}}>
              Оставьте комментарий. Это обязательный пункт.
            </p>
          </div>
        </form>
      </div>
    </main>
        </>
    )
}

export default TeacherHandling;