import React, { useState, useEffect, useCallback } from "react";
import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";
import userService from "../../services/student.service";
import "./Style.css";
import Header from "../Header/Header";

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
    

    useEffect(() => {
      
        userService.getHandlingHistory(currentUser.uid)
        .then(response => {
            setHistory(response.data)
        })
        .catch((err) => {
            setMessage(err.response?.data?.message || "Не удается загрузить файл!");
        });
    }, [])

    return(
        <>
            <Header />
    <main className="sh-main-content">
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
                <td></td>
                <td>{handling.comment}</td>
                <td>Принято</td>
                </tr>
            ) : handling.status === false ? (
                <tr className="status-not-accepted">
                <td>{handling.file.name}</td>
                <td>{handling.departureDate}<br/>{handling.departureTime}</td>
                <td>{handling.inspectionDate}<br/>{handling.inspectionTime}</td>
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