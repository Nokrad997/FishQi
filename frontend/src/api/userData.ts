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
