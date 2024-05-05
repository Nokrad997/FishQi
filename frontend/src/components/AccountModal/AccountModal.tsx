/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useEffect, useState } from 'react';
import './AccountModal.scss';
import useUserDetailsValidator from '../../hooks/useUserDetailsValidator';
import useUserDetails from '../../hooks/useUserDetails';
import UserData from '../../interfaces/UserData';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

export const AccountModal: React.FC<Props> = ({ isOpen, onClose }) => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [matchingPassword, setMatchingPassword] = useState('');
  const [error, setError] = useState<string>('');
  const { validateEmail, validatePassword, validateMatchingPassword } = useUserDetailsValidator();
  const { getUserDetails, updateUserDetails } = useUserDetails();

  useEffect(() => {
    const fetchData = async () => {
      if (isOpen) {
        await getUserDetails();
        setUsername(localStorage.getItem('userUsername') ?? '');
        setEmail(localStorage.getItem('userEmail') ?? '');
      }
    };
  
    fetchData();
  }, [isOpen, getUserDetails]);

  if (!isOpen) {
    return null;
  }

  const submitHandler = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (email !== '') validateEmail(email);
      if (password !== '') {
        validatePassword(password);
        validateMatchingPassword(password, matchingPassword);
      }
      console.log(password, matchingPassword)
      let userId = localStorage.getItem('user_id');
      let parseduserId = userId != null ? parseInt(userId) : 0;
      const userDetails: UserData = {
        user_id: parseduserId,
        username: username,
        email: email,
        password: password,
        is_admin: localStorage.getItem('userRole') == 'admin' ? true : false,
      };
      await updateUserDetails(userDetails);
    } catch (error: any) {
      //   errorHandler();
      setError(error.message);
    }
  };

  //   const errorHandler = () => {
  //     setEmail('');
  //     setPassword('');
  //   };

  return (
    <div className="account-modal">
      <div className="account-card">
        <img src="src/assets/icons/reject.png" alt="reject" className="reject-icon" width={40} onClick={onClose} />

        <form className="accountForm" onSubmit={submitHandler}>
          <h1>Acount</h1>

          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            placeholder="Change username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <label htmlFor="email">Email</label>
          <input
            type="text"
            id="email"
            placeholder="Change Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <label htmlFor="password">Password</label>
          <input
            type="password"
            placeholder="Change Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          {password != '' && (
            <div className="hiddenPasswordField">
              <label htmlFor="password"> Confirm password </label>
              <input
                type="password"
                placeholder="Retype Password"
                value={matchingPassword}
                onChange={(e) => setMatchingPassword(e.target.value)}
              />
            </div>
          )}
          {error && <div className="error-message">{error}</div>}

          <button>Save</button>
        </form>
      </div>
    </div>
  );
};
