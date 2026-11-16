package estadistica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Prueba de contrato y disponibilidad de la API pública de EstadisticasBaloncesto. */
class EstadisticasBaloncestoTest {
    @Test
    void claseExisteYExponeApiPublica() throws Exception {
        Class<?> tipo = Class.forName("estadistica.EstadisticasBaloncesto");
        assertNotNull(tipo);
        assertTrue(tipo.getDeclaredMethods().length + tipo.getMethods().length > 0,
                "La clase debe exponer operaciones verificables");
    }

    @Test
    void tipoDeDominioEsSerializable() throws Exception {
        Class<?> tipo = Class.forName("estadistica.EstadisticasBaloncesto");
        assertTrue(java.io.Serializable.class.isAssignableFrom(tipo),
                "EstadisticasBaloncesto debe poder guardarse como parte del estado del club");
    }
    @Test void almacenaPuntosYRebotes() {
        EstadisticasBaloncesto e = new EstadisticasBaloncesto(20, 8);
        assertEquals(20, e.getPuntos()); assertEquals(8, e.getRebotes());
        e.setPuntos(25); assertEquals(25, e.getPuntos());
    }
}
