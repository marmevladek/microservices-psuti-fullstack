import { LOGIN_SUCCESS, LOGIN_FAIL, LOGOUT, SET_MESSAGE } from "./types";

import UserService from "../services/student.service"
import authService from "../services/auth.service";
import { type } from "@testing-library/user-event/dist/type";

export const login = (cn, userPassword) => (dispatch) => {
    return authService.login(cn, userPassword).then(
        (data) => {
            dispatch({
                type: LOGIN_SUCCESS,
                payload: {user: data}
            });

            return Promise.resolve();
        },
        (error) => {
            const message =
            (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();

            dispatch({
                type: LOGIN_FAIL
            });

            dispatch({
                type: SET_MESSAGE,
                payload: message
            });

            return Promise.reject();
        }
    )
}

export const logout = () => (dispatch) => {
    authService.logout();

    dispatch({
        type: LOGOUT
    })
}