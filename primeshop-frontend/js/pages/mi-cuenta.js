import '../auth-guard.js';
import AuthApi from '../api/authApi.js';
import CuentaApi from '../api/cuentaApi.js';

document.addEventListener('DOMContentLoaded', async () => {

    const elementoNombre = document.getElementById('perfil-nombre');
    const elementoCorreo = document.getElementById('perfil-correo');
    const elementoRol = document.getElementById('perfil-rol');
    const botonCerrarSesion = document.getElementById('cerrar-sesion');

    try {
        const perfil = await CuentaApi.obtenerPerfil();
        elementoNombre.textContent = perfil.nombre;
        elementoCorreo.textContent = perfil.correo;
        elementoRol.textContent = 'Rol: ' + (perfil.rol === 'CLIENTE' ? 'Cliente' : 'Administrador');
    } catch (error) {
        AuthApi.cerrarSesion();
        window.location.href = 'login.html';
        return;
    }

    botonCerrarSesion.addEventListener('click', (evento) => {
        evento.preventDefault();
        AuthApi.cerrarSesion();
        window.location.href = 'login.html';
    });
});