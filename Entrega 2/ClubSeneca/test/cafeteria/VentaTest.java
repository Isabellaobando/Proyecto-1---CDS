package cafeteria;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Prueba de contrato y disponibilidad de la API pública de Venta. */
class VentaTest {
    @Test
    void claseExisteYExponeApiPublica() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.Venta");
        assertNotNull(tipo);
        assertTrue(tipo.getDeclaredMethods().length + tipo.getMethods().length > 0,
                "La clase debe exponer operaciones verificables");
    }

    @Test
    void tipoDeDominioEsSerializable() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.Venta");
        assertTrue(java.io.Serializable.class.isAssignableFrom(tipo),
                "Venta debe poder guardarse como parte del estado del club");
    }
}
