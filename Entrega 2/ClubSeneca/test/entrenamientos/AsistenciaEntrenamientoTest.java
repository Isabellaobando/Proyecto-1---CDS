package entrenamientos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Prueba de contrato y disponibilidad de la API pública de AsistenciaEntrenamiento. */
class AsistenciaEntrenamientoTest {
    @Test
    void claseExisteYExponeApiPublica() throws Exception {
        Class<?> tipo = Class.forName("entrenamientos.AsistenciaEntrenamiento");
        assertNotNull(tipo);
        assertTrue(tipo.getDeclaredMethods().length + tipo.getMethods().length > 0,
                "La clase debe exponer operaciones verificables");
    }

    @Test
    void tipoDeDominioEsSerializable() throws Exception {
        Class<?> tipo = Class.forName("entrenamientos.AsistenciaEntrenamiento");
        assertTrue(java.io.Serializable.class.isAssignableFrom(tipo),
                "AsistenciaEntrenamiento debe poder guardarse como parte del estado del club");
    }
    @Test void sincronizaAsistenciaYEstado() {
        AsistenciaEntrenamiento a = new AsistenciaEntrenamiento();
        a.setAsistio(true); assertEquals("Presente", a.obtenerEstado());
        a.registrarEstado("Justificado"); assertEquals("Justificado", a.obtenerEstado());
        a.setAsistio(false); assertEquals("Ausente", a.obtenerEstado());
        assertFalse(a.validarEstado("Desconocido"));
    }
}
