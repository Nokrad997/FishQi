import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api/",
    headers: {
        "Content-Type": "application/json",
    },
});

const register = axios.create({
    baseURL: "http://localhost:8080/api/customer/register",
    headers: {
        "Content-Type": "application/json",
    },
});

const login = axios.create({
    baseURL: "http://localhost:8080/login",
    headers: {
        "Content-Type": "application/json",
    },
});

export { api, register, login };