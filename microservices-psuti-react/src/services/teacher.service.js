import axios from "axios"
import authHeader from "./auth-header"

const API_URL = "http://localhost:8082/teacher/"


const getHandlingById = (id) => {
    
    return axios.get(API_URL + "handling/" + id,{
        headers: authHeader()
    })
}


export default {
    getHandlingById
}