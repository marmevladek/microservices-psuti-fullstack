import { type } from "@testing-library/user-event/dist/type";
import { SET_MESSAGE, CLEAR_MESSAGE } from "./types";

export const setMessage = (message) => ({
    type: SET_MESSAGE,
    payload: message
});

export const clearMessage = () => ({
    type: CLEAR_MESSAGE
})