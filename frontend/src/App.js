import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import AuthenticatedRoute from './components/layout/AuthenticatedRoute';
import './App.css';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoutes from './components/layout/ProtectedRoutes';

const App = () => {
  return (
    <AuthProvider>
        <BrowserRouter>
          <ProtectedRoutes />
        </BrowserRouter>
    </AuthProvider>
  );
};

export default App;