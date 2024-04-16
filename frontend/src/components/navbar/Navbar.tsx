import React from 'react';
import './Navbar.scss';

interface Props {
  onSignUpClick: () => void;
}

const Navbar: React.FC<Props> = ( {onSignUpClick} ) => {
  return (
    <nav className='navBar'>
      <div className='logRegLayout'>
        <button className='logRegButton'>Login</button>
        <button className='logRegButton' onClick={onSignUpClick}>Sign up</button>
      </div>
      
      <div className='logo'>
        <h1> FishQi</h1>
      </div>
      <div className='searchBar'>
        <input className='search' type='text' placeholder='Search for fish...'/>
      </div>
    </nav>
  )
};

export default Navbar;