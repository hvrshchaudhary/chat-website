import React from 'react';
import { useNavigate } from 'react-router-dom';
import AuthForm from './AuthForm';
import { useContext, useEffect } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { getToken, isTokenExpired } from '../../helpers/auth';

const Login = () => {
  const navigate = useNavigate();
  const { authToken, setAuthToken } = useContext(AuthContext);

  useEffect(() => {
    // Upon mounting, check if the user already has a valid token
    const token = getToken();
    if (authToken || (token && !isTokenExpired(token))) {
      navigate('/dashboard'); // Redirects to dashboard if a valid token exists
    }
  }, [authToken, navigate]);

  const handleLoginSubmit = (data) => {
    console.log('Login successful:', data);
    // Redirect to another route/page on successful login
    navigate('/dashboard');
    // Storing the JWT
    setAuthToken(data.token);
  };

  return (
    <AuthForm type="login" onSubmit={handleLoginSubmit} />
  );
};

export default Login;
