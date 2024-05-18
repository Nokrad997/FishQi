// src/App.tsx

import React, { useEffect } from 'react';
import { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/home/Home';
import Navbar from './components/navbar/Navbar';
import { RegisterModal } from './components/registerModal/RegisterModal';
import { LoginModal } from './components/loginModal/LoginModal';
import { AccountModal } from './components/AccountModal/AccountModal';
import { validateToken } from './api/axios';
import { CreateSetModal } from './components/CreateSetModal/CreateSetModal';

const App: React.FC = () => {
  useEffect(() => {
    const checkSession = async () => {
      if (localStorage.getItem('access') != null) {
        try {
          const response = await validateToken.post('', { refreshToken: localStorage.getItem('refresh') });
          console.log(response);
        } catch (error: any) {
          console.log(error);
          localStorage.clear();
          location.reload();
        }
      }
    };

    checkSession();
  }, []);

  interface editData{
    title: string;
    description: string;
    language: string;
    visibility: string;
    img: string;
    words: FishQData[];
  
  }

  const [editData, setEditData] = useState<editData | null>(null);
  const [modalVisibility, setModalVisibility] = useState<{
    registerModal: boolean;
    loginModal: boolean;
    accountModal: boolean;
    createSetModal: boolean;
  }>({
    registerModal: false,
    loginModal: false,
    accountModal: false,
    createSetModal: false,
  });

  const toggleModal = (modalName: keyof typeof modalVisibility, data?: any): void => {
    if (modalName === 'createSetModal' && data) {
      console.log(data);
      setEditData(data);
    } else {
      setEditData(null);
    }
    setModalVisibility((prevState) => ({
      ...prevState,
      [modalName]: !prevState[modalName],
    }));
  };

  return (
    <Router>
      <Navbar
        onSignUpClick={() => toggleModal('registerModal')}
        onSignInClick={() => toggleModal('loginModal')}
        onAccountClick={() => toggleModal('accountModal')}
        onCreateSetClick={() => toggleModal('createSetModal')}
      />
      <Routes>
        <Route path="/" element={<Home onEditClick={(data) => toggleModal('createSetModal', data)} />} />
      </Routes>
      <RegisterModal isOpen={modalVisibility.registerModal} onClose={() => toggleModal('registerModal')} />
      <LoginModal isOpen={modalVisibility.loginModal} onClose={() => toggleModal('loginModal')} />
      <AccountModal isOpen={modalVisibility.accountModal} onClose={() => toggleModal('accountModal')} />
      <CreateSetModal isOpen={modalVisibility.createSetModal} onClose={() => toggleModal('createSetModal')} initialData={editData}/>
    </Router>
  );
};

export default App;
