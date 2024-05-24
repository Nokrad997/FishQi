import React from 'react';
import './Navbar.scss';

interface Props {
	onSignUpClick: () => void;
	onSignInClick: () => void;
	onAccountClick: () => void;
	onCreateSetClick: () => void;
	onSearchClick: () => void;
	onAdminClick: () => void;
	isAdmin: boolean;
}

const Navbar: React.FC<Props> = ({ onSignUpClick, onSignInClick, onAccountClick, onCreateSetClick, onSearchClick, onAdminClick, isAdmin }) => {
	const logoutHandler = () => {
		localStorage.clear();
		location.reload();
	}

	return (
		<nav className='navBar'>
			{localStorage.getItem('access') ?
				<div className='logRegLayout'>
					<button className='logRegButton' onClick={logoutHandler}>Logout</button>
					<button className='logRegButton' onClick={onAccountClick}>Account</button>
					<button className='logRegButton' onClick={onCreateSetClick}>Create Set</button>
					{isAdmin ? <button className='logRegButton' onClick={onAdminClick}>Admin</button> : null}
				</div>
				:
				<div className='logRegLayout'>
					<button className='logRegButton' onClick={onSignInClick}>Login</button>
					<button className='logRegButton' onClick={onSignUpClick}>Sign up</button>
				</div>}

			<div className='logo'>
				<h1> FishQi</h1>
			</div>
			<div className='searchBar'>
				<button className='search' onClick={onSearchClick}> search for fishQ </button>
			</div>
		</nav>
	)
};

export default Navbar;