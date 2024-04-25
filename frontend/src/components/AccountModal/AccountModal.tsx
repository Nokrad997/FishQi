/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useState } from 'react';
import './AccountModal.scss';

interface Props {
    isOpen: boolean;
    onClose: () => void;
}

export const AccountModal: React.FC<Props> = ({ isOpen, onClose }) => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<string>("");

    if (!isOpen) {
        return null;
    }

    const submitHandler = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            return;
        } catch (error: any) {
            errorHandler();
            setError(error.message);
        }
    };

    const errorHandler = () => {
        setEmail("");
        setPassword("");
    };

    return (
        <div className="account-modal">
            <div className="account-card">

                <img src='src/assets/icons/reject.png' alt='reject' className='reject-icon' width={40} onClick={onClose} />

                <form className='accountForm' onSubmit={submitHandler}>
                    <h1>Acount</h1>

                    <label htmlFor="username">Username</label>
                    <input
                        type="text"
                        id='username'
                        placeholder="Change username"
                        value={email}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                    <label htmlFor="email">Email</label>
                    <input
                        type="text"
                        id='email'
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
                    {error && <div className="error-message">{error}</div>}

                    <button>Sing In</button>
                </form>
            </div>
        </div>
    );
};
