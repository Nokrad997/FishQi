// src/components/Navbar.tsx

import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Navbar: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const navigate = useNavigate();

  const handleSearchInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  const handleSearchSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await fetch('/api/search', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ q: searchTerm }),
      });

      if (response.ok) {
        const data = await response.json();
        console.log('Search results:', data);
        // Replace the navigator function call with history.push
        navigate('/search', { state: { results: data } });
      } else {
        console.error('Failed to fetch search results:', response.statusText);
      }
    } catch (error) {
      console.error('An error occurred while fetching search results:', error);
    }
    setSearchTerm('');
  };

  return (
    <nav>
      <ul>
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/popular">Popular</Link>
        </li>
        <li>
          <form onSubmit={handleSearchSubmit}>
            <input type="text" value={searchTerm} onChange={handleSearchInputChange} placeholder="Search..." />
            <button type="submit">Search</button>
          </form>
        </li>
        <li>
          <Link to="/login">Login</Link>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
