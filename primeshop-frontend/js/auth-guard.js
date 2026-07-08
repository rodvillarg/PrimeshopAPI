import AuthApi from './api/authApi.js';

document.addEventListener('DOMContentLoaded', () => {
    if (!AuthApi.haySesionActiva()) {
        window.location.href = 'login.html';
    }
});