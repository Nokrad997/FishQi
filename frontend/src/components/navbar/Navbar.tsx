import React from 'react';
import './Navbar.scss';

interface Props {
	onSignUpClick: () => void;
	onSignInClick: () => void;
	onAccountClick: () => void;
	onCreateSetClick: () => void;
}

const Navbar: React.FC<Props> = ({ onSignUpClick, onSignInClick, onAccountClick, onCreateSetClick }) => {
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
				<input className='search' type='text' placeholder='Search for fish...' />
			</div>
		</nav>
	)
};

export default Navbar;