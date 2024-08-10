import React, { useContext } from 'react';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import { AuthContext } from '../../context/AuthContext';
import './AuthForm.css';


const AuthForm = ({ type, onSubmit }) => {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const { saveAuthInfo } = useContext(AuthContext);

  const handleFormSubmit = async (data) => {
    try {
        const endpoint = process.env.REACT_APP_API_BASE_URL + (type === 'login' ? '/users/login' : '/users/register');
        const response = await axios.post(endpoint, data);

      if (type === 'login') {
        // Store JWT token (e.g., in local storage)
        saveAuthInfo(response.data.token);
      }

      // Call onSubmit callback (e.g., for redirection)
      onSubmit(response.data);
    } catch (error) {
      console.error(error);
            if (!error.response) {
              alert("Network error. Please check your internet connection.");
            } else if (error.response.status === 400) {
              alert("Bad request");
            } else if (error.response.status === 401) {
              alert("Invalid username or password.");
            } else {
              alert("An unexpected error occurred. Please try again later.");
            }
    }
  };

  return (
    <form onSubmit={handleSubmit(handleFormSubmit)} className="auth-form">
      <div className="form-group">
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          {...register('username', { required: true })}
          className={errors.username ? 'input-error' : ''} // Conditional class for errors
        />
        {errors.username && <span className="error-message">Username is required</span>}
      </div>
      <div className="form-group">
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          {...register('password', { required: true })}
          className={errors.password ? 'input-error' : ''} // Conditional class for errors
        />
        {errors.password && <span className="error-message">Password is required</span>}
      </div>
      <div className="form-group">
        <button type="submit" className="submit-button">
          {type === 'login' ? 'Log In' : 'Sign Up'}
        </button>
      </div>
    </form>
  );
};

export default AuthForm;