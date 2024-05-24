import React, { useState, useEffect } from 'react';
import UserData from "../../interfaces/UserData";
import './UserCard.scss';

interface Props {
  userData: UserData;
}

const UserCard: React.FC<Props> = ({ userData }) => {
  const [username, setUsername] = useState(userData.username);
  const [email, setEmail] = useState(userData.email);
  const [role, setRole] = useState(userData.is_admin ? "Admin" : "User");
  const [isEdited, setIsEdited] = useState(false);

  useEffect(() => {
    if (
      username !== userData.username ||
      email !== userData.email ||
      (userData.is_admin ? "Admin" : "User") !== role
    ) {
      setIsEdited(true);
    } else {
      setIsEdited(false);
    }
  }, [username, email, role, userData]);

  const handleSave = () => {
    
    setIsEdited(false);
  };

  const handleDelete = () => {
    // Logic to delete the user
  };

  return (
    <div className="user-card">
      <input
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <select
        value={role}
        onChange={(e) => setRole(e.target.value)}
      >
        <option value="User">User</option>
        <option value="Admin">Admin</option>
      </select>
      <button
        className="saveButton"
        onClick={handleSave}
        disabled={!isEdited}
      >
        Save
      </button>
      <button
        className="deleteButton"
        onClick={handleDelete}
      >
        Delete
      </button>
    </div>
  );
};

export default UserCard;
