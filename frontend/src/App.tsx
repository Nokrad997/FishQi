// src/App.tsx

import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/home/Home';
import LoginForm from './components/LoginForm';
import Navbar from './components/navbar/Navbar';
import { RegisterModal } from './components/registerModal/RegisterModal';

const App: React.FC = () => {

  const [isRegisterModalVisible, setIsRegisterModalVisible] = React.useState(false);
  const toggleRegistrationModal = () => {
    console.log('toggleRegistrationModal');
    setIsRegisterModalVisible(!isRegisterModalVisible);
    console.log('isRegisterModalVisible', isRegisterModalVisible);
  }

  return (
    <Router>      
      <Navbar onSignUpClick={toggleRegistrationModal} />
      <Routes>
        <Route path="/" element={<Home />} />
      </Routes>
      <RegisterModal isOpen={isRegisterModalVisible} onClose={toggleRegistrationModal}/>
    </Router>
  );
};

export default App;
