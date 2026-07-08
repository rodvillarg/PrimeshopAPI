import { BASE_URL, manejarRespuesta } from './httpCliente.js';

class AuthApi {

    static async registrar(nombre, correo, contrasena, telefono) {
        const response = await fetch(`${BASE_URL}/auth/registro`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nombre, correo, contrasena, telefono })
        });

        const datos = await manejarRespuesta(response);
        this.guardarSesion(datos);
        return datos;
    }

    static async login(correo, contrasena) {
        const response = await fetch(`${BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ correo, contrasena })
        });

        const datos = await manejarRespuesta(response);
        this.guardarSesion(datos);
        return datos;
    }

    static guardarSesion(datos) {
        localStorage.setItem('token', datos.token);
        localStorage.setItem('nombre', datos.nombre);
        localStorage.setItem('correo', datos.correo);
        localStorage.setItem('rol', datos.rol);
    }

    static cerrarSesion() {
        localStorage.removeItem('token');
        localStorage.removeItem('nombre');
        localStorage.removeItem('correo');
        localStorage.removeItem('rol');
    }

    static obtenerToken() {
        return localStorage.getItem('token');
    }

    static haySesionActiva() {
        return AuthApi.obtenerToken() !== null;
    }
}

export default AuthApi;