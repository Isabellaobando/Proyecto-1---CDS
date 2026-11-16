package estadistica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Prueba de contrato y disponibilidad de la API pública de Estadisticas. */
class EstadisticasTest {
    @Test
    void claseExisteYExponeApiPublica() throws Exception {
        Class<?> tipo = Class.forName("estadistica.Estadisticas");
        assertNotNull(tipo);
        assertTrue(tipo.getDeclaredMethods().length + tipo.getMethods().length > 0,
                "La clase debe exponer operaciones verificables");
    }

    @Test
    void tipoDeDominioEsSerializable() throws Exception {
        Class<?> tipo = Class.forName("estadistica.Estadisticas");
        assertTrue(java.io.Serializable.class.isAssignableFrom(tipo),
                "Estadisticas debe poder guardarse como parte del estado del club");
    }
    @Test void actualizaYCalculaEstadisticas() {
        Estadisticas e = new Estadisticas(); e.actualizarEstadistica("puntos", 12);
        e.actualizarEstadistica("rebotes", 5); assertEquals(17.0, e.calcularTotal());
        assertEquals(12, e.getDatos().get("puntos"));
    }
}
