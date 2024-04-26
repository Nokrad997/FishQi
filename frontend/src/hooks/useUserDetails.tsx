import { getUserData } from '../api/userData';

const useUserDetails = () => {
  const getUserDetails = async (): Promise<void> => {
    try {
      const response = await getUserData();

      localStorage.setItem('userId', response.id.toString());
      localStorage.setItem('userUsername', response.username);
      localStorage.setItem('userEmail', response.email);
      localStorage.setItem('userRole', response.is_admin == true ? 'admin' : 'user');
      
    } catch (error: any) {
      console.log('Failed in retreiving user data: ', error);
      throw new Error(error.message || 'Failed in retreiving user data');
    }
  };
};

export default useUserDetails;

