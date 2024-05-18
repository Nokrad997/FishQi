/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useState } from 'react';
import './LoginModal.scss';
import useAuth from '../../hooks/useAuth';
import useUserDetails from '../../hooks/useUserDetails';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

export const LoginModal: React.FC<Props> = ({ isOpen, onClose }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string>('');
  const { loginCustomer } = useAuth();
  const { getUserDataByEmail } = useUserDetails();

  if (!isOpen) {
    return null;
  }

  const submitHandler = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const loginSuccess = await loginCustomer({ email, password });

      if (loginSuccess) {
        await getUserDataByEmail(email);
        errorHandler();
        onClose();
		location.reload();
      }
    } catch (error: any) {
      errorHandler();
      setError(error.message);
    }
  };

  const errorHandler = () => {
    setEmail('');
    setPassword('');
  };

  return (
    <div className="login-modal">
      <div className="login-card">
        <img src="src/assets/icons/reject.png" alt="reject" className="reject-icon" width={40} onClick={onClose} />

        <form className="loginForm" onSubmit={submitHandler}>
          <h1>Login</h1>

          <label htmlFor="email">Email</label>
          <input
            type="text"
            id="email"
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
          {error && <div className="error-message">{error}</div>}

          <button>Sing In</button>
        </form>
      </div>
    </div>
  );
};
