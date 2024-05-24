// AdminModal.tsx

import React, { useState } from 'react';
import UserData from '../../interfaces/UserData';
import UserCard from '../UserCard/UserCard';
import './AdminModal.scss';

interface Props {
  isOpen: boolean;
  onClose: () => void;
  userData: UserData[];
}

const AdminModal: React.FC<Props> = ({ isOpen, onClose, userData }) => {
  const currentUserId = Number.parseInt(localStorage.getItem("userId") || '');
  const [filteredUserData, setFilteredUserData] = useState(userData.filter(user => user.userId !== currentUserId));

  if (!isOpen) {
    return null;
  }


  const handleUserDelete = (deletedUserId: number) => {
    setFilteredUserData(filteredUserData.filter(user => user.userId !== deletedUserId));
  };

  return (
    <div className="admin-modal">
      <div className="admin-card">
        <img src="src/assets/icons/reject.png" alt="reject" className="reject-icon" width={40} onClick={onClose} />
        <div className="admin-content">
          <h1> Admin </h1>
          <div className="admin-users">
            {filteredUserData.map((user) => (
              <UserCard key={user.userId} userData={user} onDelete={handleUserDelete} />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminModal;
