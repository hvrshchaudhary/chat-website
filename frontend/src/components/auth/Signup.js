import React, { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthForm from './AuthForm';
import { AuthContext } from '../../context/AuthContext';
import { getToken, isTokenExpired } from '../../helpers/auth';

const Signup = () => {
  const { authToken } = useContext(AuthContext);
  const navigate = useNavigate();

    useEffect(() => {
      const token = getToken();
      if (authToken || (token && !isTokenExpired(token))) {
        navigate('/dashboard');
      }
    }, [authToken, navigate]);

  const handleSignupSubmit = (data) => {

    console.log('Signup successful:', data);

    navigate('/login');
  };

  return (
    <AuthForm type="signup" onSubmit={handleSignupSubmit} />
  );
};

export default Signup;