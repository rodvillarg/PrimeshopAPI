import AuthApi from '../api/authApi.js';

document.addEventListener('DOMContentLoaded', () => {

    if (AuthApi.haySesionActiva()) {
        window.location.href = 'mi-cuenta.html';
        return;
    }

    const form = document.getElementById('registro-form');
    const campoNombre = document.getElementById('nombre');
    const campoCorreo = document.getElementById('correo');
    const campoContrasena = document.getElementById('password');
    const campoTelefono = document.getElementById('telefono');
    const mensajeGeneral = document.getElementById('mensaje-general');

    campoNombre.addEventListener('input', () => validarNombre());
    campoCorreo.addEventListener('input', () => validarCorreo());
    campoContrasena.addEventListener('input', () => validarContrasena());
    campoTelefono.addEventListener('input', () => validarTelefono());

    form.addEventListener('submit', async (evento) => {
        evento.preventDefault();
        ocultarMensajeGeneral();

        const nombreValido = validarNombre();
        const correoValido = validarCorreo();
        const contrasenaValida = validarContrasena();
        const telefonoValido = validarTelefono();

        if (!nombreValido || !correoValido || !contrasenaValida || !telefonoValido) {
            return;
        }

        try {
            await AuthApi.registrar(
                campoNombre.value.trim(),
                campoCorreo.value.trim(),
                campoContrasena.value,
                campoTelefono.value.trim()
            );
            window.location.href = 'mi-cuenta.html';
        } catch (error) {
            if (error.camposInvalidos) {
                mostrarErroresBackend(error.camposInvalidos);
            } else {
                mostrarMensajeGeneral(error.message);
            }
        }
    });

    function validarNombre() {
        const valor = campoNombre.value.trim();

        if (valor === '') {
            marcarError('nombre', 'El nombre es obligatorio');
            return false;
        }
        if (valor.length < 2 || valor.length > 100) {
            marcarError('nombre', 'El nombre debe tener entre 2 y 100 caracteres');
            return false;
        }
        quitarError('nombre');
        return true;
    }

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
        if (valor.length < 6 || valor.length > 50) {
            marcarError('password', 'La contraseña debe tener al menos 6 caracteres');
            return false;
        }
        quitarError('password');
        return true;
    }

    function validarTelefono() {
        const valor = campoTelefono.value.trim();
        const patronTelefono = /^[0-9]{10}$/;

        if (valor !== '' && !patronTelefono.test(valor)) {
            marcarError('telefono', 'El teléfono debe tener 10 dígitos');
            return false;
        }
        quitarError('telefono');
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