/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useState } from 'react';
import './RegisterModal.scss';
import useAuth from '../../hooks/useAuth';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

export const RegisterModal: React.FC<Props> = ({ isOpen, onClose }) => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [matchingPassword, setMatchingPassword] = useState("");
  const [is_admin, setIsAdmin] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  const { registerCustomer } = useAuth();

  if (!isOpen) {
    console.log(' not open')
    return null;
  }

  const submitHandler = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsAdmin(false);

    try {
      await registerCustomer({username, email, password, matchingPassword, is_admin});
    
    } catch (error: any) {
      setError(error.message);
    }
  };

  return (
    <div className="register-modal">
      <div className="register-card">

        <img src='src/assets/icons/reject.png' alt='reject' className='reject-icon' width={40} onClick={onClose} />

        <form className='registrationForm' onSubmit={submitHandler}>
          <h1>Registration</h1>

          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            placeholder="Enter username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <label htmlFor="email">Email</label>
          <input
            type="text"
            id='email'
            placeholder="Enter Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <label htmlFor="password">Password</label>
          <input
            type="password"
            placeholder="Enter Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <label htmlFor="confirmPassword">Confirm Password</label>
          <input
            type="password"
            placeholder="Confirm Password"
            value={matchingPassword}
            onChange={(e) => setMatchingPassword(e.target.value)}
            required
          />

          <button>Register</button>
        </form>
      </div>
    </div>
  );
};
