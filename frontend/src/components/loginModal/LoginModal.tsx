/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useState } from 'react';
import './LoginModal.scss';
import useAuth from '../../hooks/useAuth';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

export const LoginModal: React.FC<Props> = ({ isOpen, onClose }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string>("");
  const { loginCustomer } = useAuth();

  if (!isOpen) {
    return null;
  }

  const submitHandler = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await loginCustomer({email, password});

    } catch (error: any) {
      setError(error.message);
    }
  };

  return (
    <div className="login-modal">
      <div className="login-card">

        <img src='src/assets/icons/reject.png' alt='reject' className='reject-icon' width={40} onClick={onClose} />

        <form className='loginForm' onSubmit={submitHandler}>
          <h1>Login</h1>

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

          <button>Sing In</button>
        </form>
      </div>
    </div>
  );
};
