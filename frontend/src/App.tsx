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
import { SetViewModal } from './components/SetViewModal/SetViewModal';
import ErrorBox from './components/ErrorBox/ErrorBox';
import { SearchModal } from './components/SearchModal/SearchModal';
import useFishQSet from './hooks/useFisQSet';

const App: React.FC = () => {
  useEffect(() => {
    const getSetsData = async () => {
      const response = await getSets();
      setSetsData(response);
      setIsLoading(false);
    };
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
    getSetsData();
  }, []);

  const { getSets } = useFishQSet();
  const [editData, setEditData] = useState<editData | null>(null);
  const [viewData, setViewData] = useState<ViewData | null>(null);
  const [setsData, setSetsData] = useState<any>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>('');

  const [modalVisibility, setModalVisibility] = useState<{
    registerModal: boolean;
    loginModal: boolean;
    accountModal: boolean;
    createSetModal: boolean;
    setViewModal: boolean;
    searchModal: boolean;
  }>({
    registerModal: false,
    loginModal: false,
    accountModal: false,
    createSetModal: false,
    setViewModal: false,
    searchModal: false,
  });

  useEffect(() => {
    if (error) {
      const errorBox = document.querySelector('.error-box') as HTMLElement;
      if (errorBox) {
        errorBox.style.display = 'block';
        setTimeout(() => {
          errorBox.style.display = 'none';
          setError('');
        }, 5000);
      }
    }
  }, [setError, error]);

  const toggleModal = (modalName: keyof typeof modalVisibility, data?: any): void => {
    if (modalName === 'createSetModal' && data) {
      console.log(data);
      setEditData(data);
    } else {
      setEditData(null);
    }

    if (modalName === 'setViewModal' && data) {
      if (data.fishQs.length != 0) setViewData(data);
      else setError('No flashCards found for this set');
    } else {
      setViewData(null);
    }

    setModalVisibility((prevState) => ({
      ...prevState,
      [modalName]: !prevState[modalName],
    }));
  };

  return isLoading ? (
    <div>
      {' '}
      <p> loading</p>
    </div>
  ) : (
    <Router>
      <Navbar
        onSignUpClick={() => toggleModal('registerModal')}
        onSignInClick={() => toggleModal('loginModal')}
        onAccountClick={() => toggleModal('accountModal')}
        onCreateSetClick={() => toggleModal('createSetModal')}
        onSearchClick={() => toggleModal('searchModal')}
      />
      <Routes>
        <Route
          path="/"
          element={
            <Home
              onEditClick={(data) => toggleModal('createSetModal', data)}
              onViewClick={(data) => toggleModal('setViewModal', data)}
              setsDatas={setsData}
            />
          }
        />
      </Routes>
      <RegisterModal isOpen={modalVisibility.registerModal} onClose={() => toggleModal('registerModal')} />
      <LoginModal isOpen={modalVisibility.loginModal} onClose={() => toggleModal('loginModal')} />
      <AccountModal isOpen={modalVisibility.accountModal} onClose={() => toggleModal('accountModal')} />
      <CreateSetModal
        isOpen={modalVisibility.createSetModal}
        onClose={() => toggleModal('createSetModal')}
        initialData={editData}
      />
      <SetViewModal
        isOpen={modalVisibility.setViewModal}
        onClose={() => toggleModal('setViewModal')}
        initialData={viewData}
      />
      <SearchModal
        isOpen={modalVisibility.searchModal}
        onClose={() => toggleModal('searchModal')}
        setsData={setsData}
      />
      <ErrorBox error={error} />
    </Router>
  );
};

export default App;
