// import { useState } from 'react'
import { registration, login } from '../api/auth';
import RegistrationData from '../interfaces/RegistrationData'
import LoginData from '../interfaces/LoginData'

const useAuth = () => {
    const registerCustomer = async (userData: RegistrationData) => {
        try {
            const response = await registration(userData);
            console.log(response);
        } catch(error) {
            throw new Error('Registration failed');
        }
    };

    const loginCustomer = async (userData: LoginData) => {
        try {
            const response = await login(userData);
            console.log(response);
        } catch (error) {
            throw new Error("Login failed");
        }
    }

    return { registerCustomer, loginCustomer };
}

export default useAuth;