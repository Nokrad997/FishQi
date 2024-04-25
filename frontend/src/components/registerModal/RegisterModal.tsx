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
		return null;
	}

	const submitHandler = async (e: React.FormEvent) => {
		e.preventDefault();

		try {
			if (password !== matchingPassword) {
				setError("Passwords do not match.");
				return;
			}

			const registerSuccessfull = await registerCustomer({ username, email, password, matchingPassword, is_admin });

			if (registerSuccessfull) {
				errorHandler();
				onClose();
			}
		} catch (error: any) {
			errorHandler();
			setError(error.message);
		}
	};

	const errorHandler = () => {
		setEmail("");
		setPassword("");
		setUsername("");
		setMatchingPassword("");
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
					{error && <div className="error-message">{error}</div>}
					<button>Register</button>
				</form>
			</div>
		</div>
	);
};
