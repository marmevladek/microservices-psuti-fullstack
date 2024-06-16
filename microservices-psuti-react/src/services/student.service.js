import axios from "axios"
import authHeader from "./auth-header"

const API_URL = "http://localhost:8082/student/"

const sendHandling = (file, uid) => {
    let formData = new FormData();

    formData.append("file", file);
    formData.append("uid", uid)

    return axios.post(API_URL + "main", formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            ...authHeader()
        },
    })
}

const getHandlingHistory = (uid) => {
    
    return axios.get(API_URL + "history/" + uid,{
        headers: authHeader()
    })
}


export default {
    sendHandling,
    getHandlingHistory,
}