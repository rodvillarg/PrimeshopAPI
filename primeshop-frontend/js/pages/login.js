import AuthApi from '../api/authApi.js';

document.addEventListener('DOMContentLoaded', () => {

    if (AuthApi.haySesionActiva()) {
        window.location.href = 'mi-cuenta.html';
        return;
    }

    const form = document.getElementById('login-form');
    const campoCorreo = document.getElementById('correo');
    const campoContrasena = document.getElementById('password');
    const mensajeGeneral = document.getElementById('mensaje-general');

    campoCorreo.addEventListener('input', () => validarCorreo());
    campoContrasena.addEventListener('input', () => validarContrasena());

    form.addEventListener('submit', async (evento) => {
        evento.preventDefault();
        ocultarMensajeGeneral();

        const correoValido = validarCorreo();
        const contrasenaValida = validarContrasena();

        if (!correoValido || !contrasenaValida) {
            return;
        }

        try {
            await AuthApi.login(campoCorreo.value.trim(), campoContrasena.value);
            window.location.href = 'mi-cuenta.html';
        } catch (error) {
            if (error.camposInvalidos) {
                mostrarErroresBackend(error.camposInvalidos);
            } else {
                mostrarMensajeGeneral(error.message);
            }
        }
    });

    function validarCorreo() {
        const valor = campoCorreo.value.trim();
        const patronCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (valor === '') {
            marcarError('correo', 'El correo es obligatorio');
            return false;
        }
        if (!patronCorreo.test(valor)) {
            marcarError('correo', 'El correo no tiene un formato válido');
            return false;
        }
        quitarError('correo');
        return true;
    }

    function validarContrasena() {
        const valor = campoContrasena.value;

        if (valor === '') {
            marcarError('password', 'La contraseña es obligatoria');
            return false;
        }
        quitarError('password');
        return true;
    }

    function marcarError(nombreCampo, mensaje) {
        const contenedor = document.getElementById('campo-' + nombreCampo);
        const textoError = document.getElementById('error-' + nombreCampo);
        contenedor.classList.add('campo--invalido');
        textoError.textContent = mensaje;
    }

    function quitarError(nombreCampo) {
        const contenedor = document.getElementById('campo-' + nombreCampo);
        const textoError = document.getElementById('error-' + nombreCampo);
        contenedor.classList.remove('campo--invalido');
        textoError.textContent = '';
    }

    function mostrarErroresBackend(camposInvalidos) {
        for (const nombreCampo in camposInvalidos) {
            if (Object.prototype.hasOwnProperty.call(camposInvalidos, nombreCampo)) {
                marcarError(nombreCampo, camposInvalidos[nombreCampo]);
            }
        }
    }

    function mostrarMensajeGeneral(texto) {
        mensajeGeneral.textContent = texto;
        mensajeGeneral.hidden = false;
    }

    function ocultarMensajeGeneral() {
        mensajeGeneral.hidden = true;
        mensajeGeneral.textContent = '';
    }
});