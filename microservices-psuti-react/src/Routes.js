import React from "react";
import { Routes, Route } from "react-router-dom";

import Login from "./components/login/Login"
import StudentMain from "./components/student.main/StudentMain";
// import StudentMain from "./components/Student.main";

const AppRoutes = () => {
    return (
        <Routes>

            <Route path="/auth/login" element={<Login />} />

            <Route path="/student/main" element={<StudentMain />} />

        </Routes>
    )
}

export default AppRoutes