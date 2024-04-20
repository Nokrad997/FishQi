// import { useState } from 'react'
import { registration } from '../api/auth';
import RegistrationData from '../interfaces/RegistrationData'

const useAuth = () => {
    const registerCustomer = async (userData: RegistrationData) => {
        try {
            const response = await registration(userData);
            console.log(response);
        } catch(error) {
            throw new Error('Registration failed');
        }
    };

    return { registerCustomer };
}

export default useAuth;