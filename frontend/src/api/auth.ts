import RegistrationData from "../interfaces/RegistrationData";
import LoginData from "../interfaces/LoginData";
import { signUp, signIn, api } from "./axios";

type RegistrationPrimise = {
    id: number;
    username: string;
    email: string;
    is_admin: boolean;
}

type LoginPromise = {
    accessToken: string;
    refreshToken: string;
}

export async function registration(customerData: RegistrationData): Promise<RegistrationPrimise> {
    try {
        const response = await signUp.post("", customerData);

        return response.data;
    } catch (error: any) {
        if (error.response) {
            console.error("Server responded with an error:", error.response.status, error.data);

            if (error.response.data.errorMessages) {

                throw new Error(`Error during registration: ${error.response.data.errorMessages}`);
            } else {

                throw new Error(`Error during registration: ${error.response.data}`);
            }
        } else if (error.request) {
            console.error("No response received:", error.request);

            throw new Error("No response from server during registration");
        } else {
            console.error("Error setting up the request:", error.message);

            throw new Error("Error setting up registration request");
        }
    }
}

export async function login(customerData: LoginData): Promise<LoginPromise> {
    try {
        console.log("customerData", customerData);
        const response = await signIn.post("", customerData);

        return response.data;
    } catch (error: any) {
        if (error.response) {
            console.error("Server responded with an error:", error.response.status, error.response.data);

            if (error.response.data.errorMessages) {

                throw new Error(`Error during login: ${error.response.data.errorMessages}`);
            } else {

                throw new Error(`Error during login: ${error.response.data}`);
            }
        } else if (error.request) {
            console.error("No response received:", error.request);

            throw new Error("No response from server during login");
        } else {
            console.error("Error setting up the request:", error.message);

            throw new Error("Error setting up login request");
        }
    }
}

export async function isAdmin(): Promise<boolean> {
    try {
        const response = await api.post("auth/checkifadmin");
        
        return response.data;
    } catch(error: any) {
       return false;
    }
};