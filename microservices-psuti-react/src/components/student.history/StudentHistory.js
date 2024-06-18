import React, { useState, useEffect, useCallback } from "react";
import { Helmet } from "react-helmet";
import { useSelector, useDispatch } from "react-redux";
import { Link, Navigate, useParams } from "react-router-dom";
import userService from "../../services/student.service";
import { logout } from "../../actions/auth";
import "./Style.css";

const StudentHistory = () => {
    const {user: currentUser} = useSelector((state) => state.auth)

    if (!currentUser) {
        <Navigate to="/auth/login" />
    }

    if (!currentUser.role.includes("ROLE_STUDENT")) {
        <Navigate to="/teacher/main" />
    }

    const [history, setHistory] = useState("")
    const [message, setMessage] = useState("")
    // const [uid, setUid] = useState("")
    const {id} = useParams()

    const statusHandling = (status) => {
        console.log(status)
        if (status === null) {
            return "Не проверено";
        } else if (status === false) {
            return "Не принято"
        } else if (status === true) {
            return "Принято"
        }
    }

    // ?
    useEffect(() => {
        userService.getHandlingHistory(currentUser.uid)
        .then(response => {
            setHistory(response.data)
        })
        .catch((err) => {
            setMessage(err.response?.data?.message || "Не удается загрузить файл!");
        });
    }, [])
    // ?

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
            <Link to={`/student/main`}>
                <a href="#">Главная страница</a>
            </Link>
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
      <table>
        <thead>
          <tr>
            <th>Название</th>
            <th>Дата отправки</th>
            <th>Дата проверки</th>
            <th>Комментарий</th>
            <th>Статус</th>
          </tr>
        </thead>
        <tbody id="table-body">
        {history && history.map(handling =>
            handling.status === null ? (
                <tr className="status-not-checked">
                <td>{handling.file.name}</td>
                <td>{handling.departureDate}<br/>{handling.departureTime}</td>
                <td></td>
                <td>{handling.comment}</td>
                <td>Не проверено</td>
                </tr>
            ) : handling.status === true ? (
                <tr className="status-accepted">
                <td>{handling.file.name}</td>
                <td>{handling.departureDate}<br/>{handling.departureTime}</td>
                <td>-</td>
                <td>{handling.comment}</td>
                <td>Принято</td>
                </tr>
            ) : handling.status === false ? (
                <tr className="status-not-accepted">
                <td>{handling.file.name}</td>
                <td>{handling.departureDate}<br/>{handling.departureTime}</td>
                <td>-</td>
                <td>{handling.comment}</td>
                <td>Отклонено</td>
                </tr>
            ) : (
                <>
                </>
            )
          )}
        </tbody>
      </table>
    </main>
    </>
    )
}

export default StudentHistory