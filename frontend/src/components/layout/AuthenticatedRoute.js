import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import { isTokenExpired } from '../../helpers/auth';
import { getToken, removeToken } from '../../helpers/auth';


const AuthenticatedRoute = ({ children }) => {
  const { authToken } = useContext(AuthContext);
  const { loading } = useContext(AuthContext);

  if (loading) {
      // Possibly return a loading indicator or null to delay rendering
      return null; // or <LoadingSpinner />;
    }
  const token = getToken();

  if (!authToken || isTokenExpired(token)) {
    removeToken();
    return <Navigate to="/login" />;
  }

  return children;
};

export default AuthenticatedRoute;
