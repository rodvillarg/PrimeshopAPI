import { BASE_URL, manejarRespuesta } from './httpCliente.js';
import AuthApi from './authApi.js';

class CuentaApi {

    static async obtenerPerfil() {
        const response = await fetch(`${BASE_URL}/cuenta`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${AuthApi.obtenerToken()}`
            }
        });

        return await manejarRespuesta(response);
    }

    static async actualizarPerfil(nombre, telefono, contrasenaActual, contrasenaNueva) {
        const response = await fetch(`${BASE_URL}/cuenta`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${AuthApi.obtenerToken()}`
            },
            body: JSON.stringify({ nombre, telefono, contrasenaActual, contrasenaNueva })
        });

        return await manejarRespuesta(response);
    }
}

export default CuentaApi;