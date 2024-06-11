import axios from "axios"
import authHeader from "./auth-header"

const API_URL = "http://localhost:8082/"

const login = async (cn, userPassword) => {
    const response = await axios
        .post(API_URL + "auth/login", {
            cn,
            userPassword}, {
                headers: authHeader()
            });

    localStorage.setItem("user", JSON.stringify(response.data))

    return response.data;
}

const logout = () => {
    localStorage.removeItem("user");
};

const sendHandling = (file, uid) => {
    let formData = new FormData();

    formData.append("file", file);
    formData.append("uid", uid)

    return axios.post(API_URL + "student/main", formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            ...authHeader()
        },
    })
}

// const sendHandling = (file, uid, onUploadProgress) => {

//     let formData = new FormData();

//     formData.append("file", file);
//     formData.append("uid", uid)


//     console.log(uid);
//     return axios.post(API_URL + "student/main", formData,{
//         headers: {
//             'Content-Type': 'multipart/form-data',
//             ...authHeader()
//         },
//         onUploadProgress
//     })
// }

const getHandlingHistory = (uid) => {
    
    return axios.get(API_URL + "student/history", {
        headers: authHeader()
    })
}

const getTeacherBoard = () => {
    return axios.get(API_URL + "teacher", {
        headers: authHeader()
    })
}

export default {
    login,
    sendHandling,
    getHandlingHistory,
    getTeacherBoard
}