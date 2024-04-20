import React from 'react';
import './RegisterModal.scss';
import {api} from "../../api/axios";

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

export const RegisterModal: React.FC<Props> = ({ isOpen, onClose }) => {
  
  if(!isOpen) {
    console.log(' not open')
    return null;
  }

  return (
    <div className="register-modal">
      <div className="register-card">
        
        <img src='src/assets/icons/reject.png' alt='reject' className='reject-icon' width={40} onClick={onClose}/>

        <form className='registrationForm' onSubmit={() => {
          api.get()
        }}>
          <h1>Registration</h1>

          <label htmlFor="username">Username</label>
          <input type="text" placeholder="Enter Username" />
          <label htmlFor="email">Email</label>
          <input type="email" placeholder="Enter Email" />
          <label htmlFor="password">Password</label>
          <input type="password" placeholder="Enter Password" />
          <label htmlFor="confirmPassword">Confirm Password</label>
          <input type="password" placeholder="Confirm Password" />

          <button>Register</button>
        </form>
      </div>
    </div>
  );
};
