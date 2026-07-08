const BASE_URL = 'http://localhost:8080/api';

/**
 * Interpreta la respuesta del backend. Si todo salió bien, regresa
 * el cuerpo ya parseado. Si hubo error, lanza un Error.
 */
async function manejarRespuesta(response) {
    let cuerpo = null;

    try {
        cuerpo = await response.json();
    } catch (error) {
        cuerpo = null;
    }

    if (!response.ok) {
        const errorFinal = new Error(extraerMensajeError(cuerpo));
        errorFinal.camposInvalidos = (cuerpo && cuerpo.errores) ? cuerpo.errores : null;
        throw errorFinal;
    }

    return cuerpo;
}

function extraerMensajeError(cuerpo) {
    if (typeof cuerpo === 'string') {
        return cuerpo;
    }
    if (cuerpo && cuerpo.error) {
        return cuerpo.error;
    }
    if (cuerpo && cuerpo.message) {
        return cuerpo.message;
    }
    return 'Ocurrió un error inesperado. Intenta de nuevo.';
}

export { BASE_URL, manejarRespuesta };