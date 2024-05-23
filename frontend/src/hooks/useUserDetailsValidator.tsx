const useUserDetailsValidator = () => {
  const validateEmail = (email: string) => {
    try {
      const emailRegex = new RegExp(
        '^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@' + '[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$',
      );

        if (!emailRegex.test(email)) {
          throw new Error('Email is not valid');
        }
        
    } catch (error: any) {
      console.log('Email validation failed: ', error);
      throw new Error(error.message || 'Email validation failed');
    }
  };

  const validatePassword = (password: string) => {
    try {
      const passwordRegex = RegExp('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$');

      if(!passwordRegex.test(password)) {
        throw new Error('Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character');
      }

    } catch (error: any) {
      console.log('Password validation failed: ', error);
      throw new Error(error.message || 'Password validation failed');
    }
  };

  const validateMatchingPassword = (password: string, matchingPassword: string) => {
    try {
      if (password !== matchingPassword) {
        throw new Error('Passwords do not match');
      }
    } catch (error: any) {
      console.log('Password do not match: ', error);
      throw new Error(error.message || 'Password do not match');
    }
  };

  return { validateEmail, validatePassword, validateMatchingPassword }
};

export default useUserDetailsValidator;
