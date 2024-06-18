import axios from "axios"
import authHeader from "./auth-header"

const API_URL = "http://localhost:8082/teacher/"

const getHandlingList = (teacherUid) => {

    return axios.get(API_URL + "main/" + teacherUid, {
        headers: authHeader()
    })
}

const getHandlingById = (id) => {
    
    return axios.get(API_URL + "handling/" + id,{
        headers: authHeader()
    })
}

const updateHandling = (id, updateHandling) => {
    
    return axios.put(API_URL + "handling/" + id, updateHandling, {
        headers: authHeader()
    })
}


export default {
    getHandlingList,
    getHandlingById,
    updateHandling
}