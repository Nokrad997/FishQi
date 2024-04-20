import RegistrationData from "../interfaces/RegistrationData";
import { register } from "./axios";

type RegistrationPrimise = {
    id: number;
    username: string;
    email: string;
    is_admin: boolean;
}

export async function registration(customerData: RegistrationData): Promise<RegistrationPrimise> {
    const response = await register.post("/", customerData);

    return response.data;
}