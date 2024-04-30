export default interface UserData {
    user_id: number;
    username: string;
    email: string;
    password?: string;
    is_admin: boolean;
}