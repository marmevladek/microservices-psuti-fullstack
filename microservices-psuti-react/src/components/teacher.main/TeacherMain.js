import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import teacherService from "../../services/teacher.service";
import "./Style.css";

import shape from "../../assets/shape.svg"
import Header from "../Header/Header";

const TeacherMain = () => {
    const { user: currentUser } = useSelector((state) => state.auth);
    const [handlings, setHandlings] = useState("");
    const [studentGroup, setStudentGroup] = useState("")
    const [studentName, setStudentName] = useState("")
    const [message, setMessage] = useState("");
    const [status, setStatus] = useState(null);


    useEffect(() => {
        teacherService.getHandlingList(currentUser.uid)
                .then(response => {
                    setHandlings(response.data)
                })
                .catch(err => {
                    setMessage(err.response?.data?.message);
                });
    }, []);

    const onChooseStatus = (e) => {
        const status = e.target.value;
        setStatus(status)

    }

    return(
        <>
        <Header />
    <main class="tm-main-content">
      <section>
        <form id="radio-form">
          <div class="radio">
            <p>Выбрать:</p>
            <input type="radio" id="new" name="fav_language" value="New"  onChange={onChooseStatus}/>
            <label for="new">Новые</label>
            <input
              type="radio"
              id="accept"
              name="fav_language"
              value="Accept"
              onChange={onChooseStatus}
            />
            <label for="accept">Принятые</label>
            <input
              type="radio"
              id="decline"
              name="fav_language"
              value="Decline"
              onChange={onChooseStatus}
            />
            <label for="decline">Отклоненные</label>
          </div>
        </form>
        <div class="search-box">
          <input class="search-input" type="text" placeholder="Поиск" />
          <button class="search-btn">
            <img src={shape} alt="поиск" />
          </button>
        </div>
      </section>
      <table>
        <thead>
          <tr>
            <th>Группа</th>
            <th>Студент</th>
            <th>Дата отправки</th>
            <th>Дата проверки</th>
            <th>Статус</th>
            <th></th>
          </tr>
        </thead>
        <tbody id="table-body">
            {handlings && handlings.map(handling =>
                status === "New" && handling.status === null ? (
                    <tr className="status-not-checked">
                    <td>{handling.studentGroup}</td>
                    <td>{handling.studentName}</td>
                    <td>{handling.departureDate}<br/>{handling.departureTime}</td>
                    <td>{handling.inspectionDate}<br/>{handling.inspectionTime}</td>
                    <td>Не проверено</td>
                    <td>
                        <Link to={`/teacher/handling/${handling.id}`}>
                            <button class="open-work-button">Открыть работу</button>
                        </Link>
                    </td>
                    </tr>        
                ) : status === "Accept" && handling.status === true ? (
                    <tr className="status-accepted">
                    <td>{handling.studentGroup}</td>
                    <td>{handling.studentName}</td>
                    <td>{handling.departureDate}<br/>{handling.departureTime}</td>
                    <td>{handling.inspectionDate}<br/>{handling.inspectionTime}</td>
                    <td>Принято</td>
                    <td>
                        <Link to={`/teacher/handling/${handling.id}`}>
                            <button class="open-work-button">Открыть работу</button>
                        </Link>
                    </td>
                    </tr>
                ) : status === "Decline" && handling.status === false ? (
                    <tr className="status-not-accepted">
                    <td>{handling.studentGroup}</td>
                    <td>{handling.studentName}</td>
                    <td>{handling.departureDate}<br/>{handling.departureTime}</td>
                    <td>{handling.inspectionDate}<br/>{handling.inspectionTime}</td>
                    <td>Не принято</td>
                    <td>
                        <Link to={`/teacher/handling/${handling.id}`}>
                            <button class="open-work-button">Открыть работу</button>
                        </Link>
                    </td>
                    </tr>
                ) : (
                    <>
                    </>
                )
            )}
        </tbody>
      </table>
      <section class="inside" style={{display: "none"}}>
        <div>привет</div>
      </section>
    </main>
        </>
    )

}

export default TeacherMain;
