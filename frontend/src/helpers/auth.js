export const getToken = () => {
  return localStorage.getItem('token');
};

export const saveToken = (token) => {
  localStorage.setItem('token', token);
};

export const removeToken = () => {
  localStorage.removeItem('token');
};

// Decodes the JWT payload and returns the username or userId
export const decodeJwt = (token) => {
  try {
    const base64Url = token.split('.')[1]; // Get the payload part of the token
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const payload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(payload);
  } catch (e) {
    console.error('Error decoding JWT', e);
    return null;
  }
};

export const isTokenExpired = (token) => {
  try {
    const { exp } = JSON.parse(atob(token.split('.')[1])); // Decode payload
    const now = Date.now() / 1000;
    return exp < now;
  } catch (e) {
    return false;
  }
};
