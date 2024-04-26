/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useState } from 'react';
import './AccountModal.scss';
import useUserDetailsValidator from '../../hooks/useUserDetailsValidator';

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

  if (!isOpen) {
    return null;
  }

  const submitHandler = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (email != '') validateEmail(email);
      if (password != '') {
        validatePassword(password);
        validateMatchingPassword(password, matchingPassword);
      }

      return;
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
            value={email}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <label htmlFor="email">Email</label>
          <input
            type="text"
            id="email"
            placeholder="Change Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <label htmlFor="password">Password</label>
          <input
            type="password"
            placeholder="Change Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <label htmlFor="password"> Confirm password </label>
          <input
            type="password"
            placeholder="Retype Password"
            value={matchingPassword}
            onChange={(e) => setMatchingPassword(e.target.value)}
            required
          />
          {error && <div className="error-message">{error}</div>}

          <button>Save</button>
        </form>
      </div>
    </div>
  );
};
