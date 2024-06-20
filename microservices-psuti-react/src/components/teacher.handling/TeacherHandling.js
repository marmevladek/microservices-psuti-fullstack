import React, { useState, useEffect, useRef } from "react";
import { Navigate, useNavigate, useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import teacherService from "../../services/teacher.service";
import fileService from "../../services/file.service";
import "./Style.css";
import Header from "../Header/Header";

const TeacherHandling = () => {
    let navigate = useNavigate();

    const { user: currentUser } = useSelector((state) => state.auth);
    const [handling, setHandling] = useState("");
    const [studentGroup, setStudentGroup] = useState("");
    const [studentName, setStudentName] = useState("");
    const [file, setFile] = useState({ path: "", name: "" });
    const [message, setMessage] = useState("");
    const { id } = useParams();

    const [status, setStatus] = useState(null);
    const [comment, setComment] = useState("");

    const dispatch = useDispatch();
    const form = useRef();
    const checkBtn = useRef();

    useEffect(() => {
        if (id) {
            teacherService.getHandlingById(id)
                .then(response => {
                    setHandling(response.data.handling);
                    setStudentGroup(response.data.studentGroup);
                    setStudentName(response.data.studentName);
                    setFile(response.data.handling.file);
                })
                .catch(err => {
                    setMessage(err.response?.data?.message || "Не удается загрузить файл!");
                });
        }
    }, [id]);

    const onChangeStatus = (e) => {
        const status = e.target.value;
        if (status === 'true') {
            setStatus(true);
        } else if (status === 'false') {
            setStatus(false);
        }
    };

    const onChangeComment = (e) => {
        const comment = e.target.value;
        setComment(comment);
    };

    const handleDownload = async () => {
        try {
            const response = await fileService.getFile(file.path, file.name);
            const blob = new Blob([response.data], { type: response.data.type });
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', file.name);
            document.body.appendChild(link);
            link.click();
            link.remove();
        } catch (err) {
            setMessage(err.response?.data?.message || "Не удается скачать файл!");
        }
    };

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

        form.current.validateAll();

        const updateHandling = { status, comment };

        if (checkBtn.current.context._errors.length === 0) {
            teacherService.updateHandling(id, updateHandling)
                .then(() => {
                    navigate("/teacher/main");
                    window.location.reload();
                })
                .catch(() => {
                    // Обработка ошибки
                });
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
            <Header />
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
                        <button className="doc-button" type="button" onClick={handleDownload}/>
                        <span>{file.name}</span>
                    </div>
                    <Form id="feedback-form" onSubmit={handleSubmit} ref={form}>
                        <div aria-required="true" className="radio">
                            <h2>Установите статус <span className="req">*</span></h2>
                            <Input
                                type="radio"
                                id="accept"
                                name="fav_language"
                                value="true"
                                onChange={onChangeStatus}
                                required
                            />
                            <label htmlFor="accept">Принято</label>
                            <Input
                                type="radio"
                                id="decline"
                                name="fav_language"
                                value="false"
                                onChange={onChangeStatus}
                                required
                            />
                            <label htmlFor="decline">Отклонено</label>
                            <p id="error-message" className="error-message" style={{ display: "none" }}>
                                Вы не поставили статус. Это обязательный пункт.
                            </p>
                        </div>
                        <div className="comment-div">
                            <h2>Ваш комментарий <span className="req">*</span></h2>
                            <textarea name="comment" id="comment" onChange={onChangeComment} required />
                        </div>
                        <div className="submit-container">
                            <button className="submit" type="submit">Отправить</button>
                            <p id="error-message" className="error-message" style={{ display: "none" }}>
                            Оставьте комментарий. Это обязательный пункт.
                            </p>
                        </div>
                        <CheckButton style={{ display: "none" }} ref={checkBtn} />
                    </Form>
                </div>
            </main>
        </>
    );
}

export default TeacherHandling;