import axios from "axios"
import authHeader from "./auth-header"

const API_URL = "http://localhost:8080/files/"


const getFile = (path, name) => {
    return axios.get(API_URL + "download", 
    {
        headers: authHeader(),
        params: {path, name}
    })
}

export default {
    getFile
}