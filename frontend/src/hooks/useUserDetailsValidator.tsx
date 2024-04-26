const useUserDetailsValidator = () => {
  const validateEmail = (email: string) => {
    try {
      const emailRegex = new RegExp(
        '^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@' + '[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$',
      );

      return emailRegex.test(email);
    } catch (error: any) {
      console.log('Email validation failed: ', error);
      throw new Error(error.message || 'Email validation failed');
    }
  };

  const validatePassword = (password: string) => {
    try {
      const passwordRegex = RegExp('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$');

      return passwordRegex.test(password);
    } catch (error: any) {
      console.log('Password validation failed: ', error);
      throw new Error(error.message || 'Password validation failed');
    }
  };

  const validateMatchingPassword = (password: string, matchingPassword: string) => {
    try {
      return password === matchingPassword;
    } catch (error: any) {
      console.log('Password do not match: ', error);
      throw new Error(error.message || 'Password do not match');
    }
  };

  return { validateEmail, validatePassword, validateMatchingPassword }
};

export default useUserDetailsValidator;
