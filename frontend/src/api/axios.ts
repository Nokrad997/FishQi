import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api/",
    headers: {
        "Content-Type": "application/json",
    },
});

const registration = axios.create({
    baseURL: "http://localhost:8080/registration/",
    headers: {
        "Content-Type": "application/json",
    },
});

const login = axios.create({
    baseURL: "http://localhost:8080/login/",
    headers: {
        "Content-Type": "application/json",
    },
});

export default { api, registration, login }