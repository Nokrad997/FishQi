// import { useState } from 'react'
import { registration, login, isAdmin } from '../api/auth';
import RegistrationData from '../interfaces/RegistrationData'
import LoginData from '../interfaces/LoginData'

const useAuth = () => {
    const registerCustomer = async (userData: RegistrationData) => {
        try {
            await registration(userData);

            return true;
        } catch (error: any) {
            console.log("registration failed: ", error)
            throw new Error(error.message || "registration failed");
        }
    };

    const loginCustomer = async (userData: LoginData) => {
        try {
            const response = await login(userData);
            localStorage.setItem("access", response.accessToken);
            localStorage.setItem("refresh", response.refreshToken);

            return true;
        } catch (error: any) {
            console.error("Login failed:", error);
            throw new Error(error.message || "Login failed");
        }
    }

    const checkIfAdmin =  async () => {
        try {
            const response = await isAdmin();

            return response;
        } catch(error: any) {
            return false;
        }
    }

    return { registerCustomer, loginCustomer, checkIfAdmin };
}

export default useAuth;