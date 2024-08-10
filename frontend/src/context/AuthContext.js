import React, { createContext, useState, useEffect } from 'react';
import { getToken, isTokenExpired, removeToken, decodeJwt, saveToken } from '../helpers/auth';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [authToken, setAuthToken] = useState(null);
  const [username, setUsername] = useState('');
  const [loading, setLoading] = useState(true);

  const logout = () => {
    setAuthToken(null); // Remove the token from the state
    setUsername('');
    removeToken(); // Clear the token from local storage
  };

  const saveAuthInfo = (token) => {
    setAuthToken(token);
    const decoded = decodeJwt(token);
    setUsername(decoded.sub); // Save the username from token
    saveToken(token); // This can remain the same as it's just local storage handling
  };

  useEffect(() => {
    const token = getToken();
    if (token && !isTokenExpired(token)) {
        setAuthToken(token);
        const decoded = decodeJwt(token);
        setUsername(decoded.sub); // Assume 'sub' is your username field in JWT
    }
    setLoading(false);
  }, []);

  return (
    <AuthContext.Provider value={{ authToken, setAuthToken, username, saveAuthInfo, loading, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
