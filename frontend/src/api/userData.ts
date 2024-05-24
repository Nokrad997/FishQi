import UserData from '../interfaces/UserData';
import { api } from './axios';

export async function getUserData(): Promise<UserData> {
  try {
    const response = await api.get('customer/');

    return response.data;
  } catch (error: any) {
    console.log('Failed in retreiving user data: ', error);

    throw new Error(error.message || 'Failed in retreiving user data');

    //trzeba zrobić bardziej dogłębną obsługę błędów
  }
}

export async function getUserDataEmail(email: string): Promise<UserData> {
  try {
    const response = await api.get(`customer/email/${email}`);

    return response.data;
  } catch (error: any) {
    console.log('Failed in retreiving user data: ', error);

    throw new Error(error.message || 'Failed in retreiving user data');
  }
}

export async function updateUserData(user: UserData): Promise<UserData> {
  try {
    console.log(user);
    const response = await api.put(`customer/${user.userId}`, user);

    return response.data;
  } catch (error: any) {
    console.log('Failed in updating user data: ', error);

    throw new Error(error.message || 'Failed in updating user data');
  }
}

export async function getUserById(id: number) {
  try {
    const response = await api.get(`customer/${id}`);

    return response.data;
  } catch (error: any) {
    console.log('Failed in retreiving user data: ', error);

    throw new Error(error.message || 'Failed in retreiving user data');
  }
}

export async function getAll() {
  try {
    const response = await api.get('customer/');
    console.log(response);
    return response.data;
  } catch(error: any) {
    console.log('Failed in retreiving users data: ', error);

    throw new Error(error.message || 'Failed in retreiving users data');
  }
}
