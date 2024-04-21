import RegistrationData from "../interfaces/RegistrationData";
import LoginData from "../interfaces/LoginData";
import { signUp, signIn } from "./axios";

type RegistrationPrimise = {
    id: number;
    username: string;
    email: string;
    is_admin: boolean;
}

type LoginPromise = {
    access: string;
    refresh: string;
}

export async function registration(customerData: RegistrationData): Promise<RegistrationPrimise> {
    const response = await signUp.post("/", customerData);

    return response.data;
}

export async function login(customerData: LoginData): Promise<LoginPromise> {
    const response = await signIn.post("/", customerData);

    return response.data;
}