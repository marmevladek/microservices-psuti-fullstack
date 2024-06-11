// /path/to/your/component/Login.js

import React, { useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Navigate, useNavigate } from 'react-router-dom';
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { login } from "../../actions/auth";

// Import the CSS file
import './Style.css';
import pgutiLogo from "../../assets/pgutiLogo.svg"

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        Поле не должно быть пустым
      </div>
    );
  }
};

const Login = (props) => {
  let navigate = useNavigate();

  const form = useRef();
  const checkBtn = useRef();

  const [cn, setCn] = useState("");
  const [userPassword, setUserPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const { isLoggedIn } = useSelector(state => state.auth);
  const { message } = useSelector(state => state.message);

  const dispatch = useDispatch();

  const onChangeUsername = (e) => {
    const cn = e.target.value;
    setCn(cn);
  };

  const onChangePassword = (e) => {
    const userPassword = e.target.value;
    setUserPassword(userPassword);
  };

  const handleLogin = (e) => {
    e.preventDefault();

    setLoading(true);

    form.current.validateAll();

    if (checkBtn.current.context._errors.length === 0) {
      dispatch(login(cn, userPassword))
        .then(() => {
          navigate("/student/main");
          window.location.reload();
        })
        .catch(() => {
          setLoading(false);
        });
    } else {
      setLoading(false);
    }
  };

  if (isLoggedIn) {
    return <Navigate to="/student/main" />;
  }

  return (
    <>
      <div className="container">
        <div className="left-panel">
          <img src={pgutiLogo} alt="logo site" />
        </div>
        <div className="right-panel">
          <div className="form">
            <h2>Войти</h2>
            <div className="form-group-top">
              <label htmlFor="login-type">Войти как:</label>
              <select id="login-type" name="login-type">
                <option value="username">Имя пользователя</option>
                <option value="email">Электронная почта</option>
                <option value="phone">Телефон</option>
              </select>
            </div>
          </div>
          <Form onSubmit={handleLogin} ref={form}>
            <div className="form-group-middle">
              <Input
                type="text"
                id="username"
                name="cn"
                placeholder="Имя пользователя"
                value={cn}
                onChange={onChangeUsername}
                validations={[required]}
              />
              <Input
                type="password"
                id="password"
                name="userPassword"
                placeholder="Пароль"
                value={userPassword}
                onChange={onChangePassword}
                validations={[required]}
              />
            </div>
            <div className="form-group-bottom">
              <button type="submit">Войти</button>
            </div>

            {message && (
              <div className="form-group">
                <div className="alert alert-danger" role="alert">
                  {message}
                </div>
              </div>
            )}
            <CheckButton style={{ display: "none" }} ref={checkBtn} />
          </Form>
        </div>
      </div>
    </>
  );
};

export default Login;
