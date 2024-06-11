import axios from "axios";

const API_URL = "http://localhost:8082/auth/"

const login = async (cn, userPassword) => {
    const response = await axios
        .post(API_URL + "login", {
            cn,
            userPassword
        });

    localStorage.setItem("user", JSON.stringify(response.data))

    return response.data;
}

const logout = () => {
    localStorage.removeItem("user");
};

export default {
    login,
    logout
}