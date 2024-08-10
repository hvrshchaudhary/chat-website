import React, { useContext } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import Login from '../auth/Login';
import Signup from '../auth/Signup';
import AuthenticatedRoute from './AuthenticatedRoute';
import Dashboard from '../dashboard/Dashboard';

const ProtectedRoutes = () => {
  const { loading } = useContext(AuthContext);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/dashboard" element={
        <AuthenticatedRoute>
          <Dashboard />
        </AuthenticatedRoute>
      } />
      <Route path="/signup" element={<Signup />} />
    </Routes>
  );
};

export default ProtectedRoutes;
