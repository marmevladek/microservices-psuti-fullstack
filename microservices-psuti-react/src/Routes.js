import React from "react";
import { Routes, Route } from "react-router-dom";

import Login from "./components/login/Login"
import StudentMain from "./components/student.main/StudentMain";
import StudentHistory from "./components/student.history/StudentHistory";
import TeacherHandling from "./components/teacher.handling/TeacherHandling";
// import StudentMain from "./components/Student.main";

const AppRoutes = () => {
    return (
        <Routes>

            <Route path="/auth/login" element={<Login />} />

            <Route path="/student/main" element={<StudentMain />} />

            <Route path="/student/history/:id" element={<StudentHistory />} />

            <Route path="/teacher/handling/:id" element={<TeacherHandling />} />

        </Routes>
    )
}

export default AppRoutes