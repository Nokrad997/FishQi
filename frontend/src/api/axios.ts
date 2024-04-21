import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api/",
    headers: {
        "Content-Type": "application/json",
    },
});

const signUp = axios.create({
    baseURL: "http://localhost:8080/api/register",
    headers: {
        "Content-Type": "application/json",
    },
});

const signIn = axios.create({
    baseURL: "http://localhost:8080/api/login",
    headers: {
        "Content-Type": "application/json",
    },
});

export { api, signUp, signIn };