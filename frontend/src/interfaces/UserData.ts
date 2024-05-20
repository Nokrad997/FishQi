export default interface UserData {
    userId: number;
    username: string;
    email: string;
    password?: string;
    is_admin: boolean;
}