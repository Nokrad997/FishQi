import { getUserById, getUserData, getUserDataEmail, updateUserData } from '../api/userData';
import UserData from '../interfaces/UserData';

const useUserDetails = () => {
  const checkUpdatedData = (user: UserData): UserData => {
    const updatedUser: UserData = {
      user_id: user.user_id,
      username:
        user.username != localStorage.getItem('userUsername')
          ? user.username
          : localStorage.getItem('userUsername') ?? 'coś sie zespuło',
      email: user.email ? user.email : localStorage.getItem('userEmail') ?? 'coś sie zespuło',
      password: user.password || '',
      is_admin: user.is_admin,
    };

    return updatedUser;
  };

  const getUserDetails = async (): Promise<void> => {
    try {
      const response = await getUserData();
      console.log(response);

      localStorage.setItem('user_id', response.user_id.toString());
      localStorage.setItem('userUsername', response.username);
      localStorage.setItem('userEmail', response.email);
      localStorage.setItem('userRole', response.is_admin == true ? 'admin' : 'user');
    } catch (error: any) {
      console.log('Failed in retreiving user data: ', error);

      throw new Error(error.message || 'Failed in retreiving user data');
    }
  };

  const getUserDataByEmail = async (email: string): Promise<void> => {
    try {
      const response = await getUserDataEmail(email);
      
      localStorage.setItem('user_id', response.user_id.toString());
      localStorage.setItem('userUsername', response.username);
      localStorage.setItem('userEmail', response.email);
      localStorage.setItem('userRole', response.is_admin == true ? 'admin' : 'user');

    } catch (error: any) {
      console.log('Failed in retreiving user data: ', error);

      throw new Error(error.message || 'Failed in retreiving user data');
    }
  }

  const updateUserDetails = async (user: UserData): Promise<void> => {
    try {
      console.log(user);
      user = checkUpdatedData(user);
      const response = await updateUserData(user);

      localStorage.setItem('user_id', response.user_id.toString());
      localStorage.setItem('userUsername', response.username);
      localStorage.setItem('userEmail', response.email);
      localStorage.setItem('userRole', response.is_admin == true ? 'admin' : 'user');
    } catch (error: any) {
      console.log('Failed in updating user data: ', error);

      throw new Error(error.message || 'Failed in updating user data');
    }
  };

  const getUserId = async (id: number) => {
    try {
      const response = await getUserById(id);

      return response;
    } catch (error: any) {
      console.log('Failed in retreiving user data: ', error);

      throw new Error(error.message || 'Failed in retreiving user data');
    }
  };
  return { getUserDetails, updateUserDetails , getUserId, getUserDataByEmail };
};

export default useUserDetails;
