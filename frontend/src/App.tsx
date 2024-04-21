// src/App.tsx

import React from 'react';
import { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/home/Home';
import Navbar from './components/navbar/Navbar';
import { RegisterModal } from './components/registerModal/RegisterModal';
import { LoginModal } from './components/loginModal/LoginModal';

const App: React.FC = () => {

  const [isRegisterModalVisible, setIsRegisterModalVisible] = useState(false);
  const [isLoginModalVisible, setIsLoginModalVisible] = useState(false);
  const toggleRegistrationModal = () => {
    setIsRegisterModalVisible(!isRegisterModalVisible);
  }
  const toggleLoginModal = () => {
    setIsLoginModalVisible(!isLoginModalVisible);
  }

  return (
    <Router>      
      <Navbar onSignUpClick={toggleRegistrationModal} onSignInClick={toggleLoginModal}/>
      <Routes>
        <Route path="/" element={<Home />} />
      </Routes>
      <RegisterModal isOpen={isRegisterModalVisible} onClose={toggleRegistrationModal}/>
      <LoginModal isOpen={isLoginModalVisible} onClose={toggleLoginModal} />
    </Router>
  );
};

export default App;
