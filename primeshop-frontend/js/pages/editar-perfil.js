import '../auth-guard.js';
import AuthApi from '../api/authApi.js';
import CuentaApi from '../api/cuentaApi.js';

document.addEventListener('DOMContentLoaded', async () => {

    const form = document.getElementById('editar-perfil-form');
    const campoNombre = document.getElementById('nombre');
    const campoCorreo = document.getElementById('correo');
    const campoTelefono = document.getElementById('telefono');
    const campoContrasenaActual = document.getElementById('contrasenaActual');
    const campoContrasenaNueva = document.getElementById('contrasenaNueva');
    const mensajeGeneral = document.getElementById('mensaje-general');
    const mensajeExito = document.getElementById('mensaje-exito');

    try {
        const perfil = await CuentaApi.obtenerPerfil();
        campoNombre.value = perfil.nombre;
        campoCorreo.value = perfil.correo;
        campoTelefono.value = perfil.telefono || '';
    } catch (error) {
        AuthApi.cerrarSesion();
        window.location.href = 'login.html';
        return;
    }

    campoNombre.addEventListener('input', () => validarNombre());
    campoTelefono.addEventListener('input', () => validarTelefono());
    campoContrasenaActual.addEventListener('input', () => validarContrasenas());
    campoContrasenaNueva.addEventListener('input', () => validarContrasenas());

    form.addEventListener('submit', async (evento) => {
        evento.preventDefault();
        ocultarMensajes();

        const nombreValido = validarNombre();
        const telefonoValido = validarTelefono();
        const contrasenasValidas = validarContrasenas();

        if (!nombreValido || !telefonoValido || !contrasenasValidas) {
            return;
        }

        try {
            await CuentaApi.actualizarPerfil(
                campoNombre.value.trim(),
                campoTelefono.value.trim(),
                campoContrasenaActual.value,
                campoContrasenaNueva.value
            );
            campoContrasenaActual.value = '';
            campoContrasenaNueva.value = '';
            mostrarMensajeExito('Tus datos se actualizaron correctamente');
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

    function validarContrasenas() {
        const actual = campoContrasenaActual.value;
        const nueva = campoContrasenaNueva.value;

        quitarError('contrasenaActual');
        quitarError('contrasenaNueva');

        if (nueva === '' && actual === '') {
            return true;
        }

        if (nueva !== '' && nueva.length < 6) {
            marcarError('contrasenaNueva', 'La nueva contraseña debe tener al menos 6 caracteres');
            return false;
        }

        if (nueva !== '' && actual === '') {
            marcarError('contrasenaActual', 'Escribe tu contraseña actual para cambiarla');
            return false;
        }

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

    function mostrarMensajeExito(texto) {
        mensajeExito.textContent = texto;
        mensajeExito.hidden = false;
    }

    function ocultarMensajes() {
        mensajeGeneral.hidden = true;
        mensajeGeneral.textContent = '';
        mensajeExito.hidden = true;
        mensajeExito.textContent = '';
    }
});