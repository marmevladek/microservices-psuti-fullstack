import React, { useState, useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Routes, Route, Link, useLocation } from "react-router-dom";

// import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";


import Login from "./components/login/Login";
// import Profile from "./components/Profile";

import { logout } from "./actions/auth";
import { clearMessage } from "./actions/message";


import EventBus from "./common/EventBus";

import AppRoutes from "./Routes"

const App = () => {


  return (
    <>
      <AppRoutes />
    </>
  );
};

export default App;
