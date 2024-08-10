import React, { useState, useEffect, useContext } from 'react';
import './UserList.css';
import { AuthContext } from '../../context/AuthContext';

const UserList = ({ onUserSelect }) => {
    const [users, setUsers] = useState([]);
    const { authToken } = useContext(AuthContext);

    useEffect(() => {
        const fetchUsers = async () => {
            if (!authToken) return;
            try {
                const endpoint = process.env.REACT_APP_API_BASE_URL + '/users/all';
                const response = await fetch(endpoint, {
                    headers: {
                    'Authorization': `Bearer ${authToken}`, // Include the auth token in the request
                    'Content-Type': 'application/json'
                    },
                });
                if (response.ok) {
                    const userDTOs = await response.json();
                    setUsers(userDTOs);
                } else {
                    throw new Error(`Failed to fetch users: ${response.status}`);
                }
            } catch (error) {
                console.error('Error fetching users:', error);
                // Handle errors as appropriate for your application
            }
        };

        fetchUsers();
    }, []);

    return (
        <div className="user-list">
            {users.map((userDTO, index) => (
                <div
                    key={index}
                    onClick={() => onUserSelect(userDTO.username)}
                    className="user"
                >
                    {userDTO.username}
                </div>
            ))}
        </div>
    );
};

export default UserList;
