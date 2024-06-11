import { LOGIN_SUCCESS, LOGIN_FAIL, LOGOUT, SET_MESSAGE } from "./types";

import UserService from "../services/user.service"
import { type } from "@testing-library/user-event/dist/type";

export const login = (cn, userPassword) => (dispatch) => {
    return UserService.login(cn, userPassword).then(
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
    UserService.logout();

    dispatch({
        type: LOGOUT
    })
}