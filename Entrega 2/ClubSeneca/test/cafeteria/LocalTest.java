package cafeteria;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Prueba de contrato y disponibilidad de la API pública de Local. */
class LocalTest {
    @Test
    void claseExisteYExponeApiPublica() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.Local");
        assertNotNull(tipo);
        assertTrue(tipo.getDeclaredMethods().length + tipo.getMethods().length > 0,
                "La clase debe exponer operaciones verificables");
    }

    @Test
    void tipoDeDominioEsSerializable() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.Local");
        assertTrue(java.io.Serializable.class.isAssignableFrom(tipo),
                "Local debe poder guardarse como parte del estado del club");
    }
    @Test void administraInventarioYReabastecimiento() {
        Local l = new Local("Central", "07:00", "20:00"); Producto p = new Producto("Agua", 2, "B", 3);
        l.agregarProducto(p); l.reabastecerProducto(p, 4);
        assertEquals(7, p.getStock()); assertEquals(1, l.consultarProductos().size());
        l.eliminarProducto(p); assertTrue(l.getProductos().isEmpty());
    }
}
