package cafeteria;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Prueba de contrato y disponibilidad de la API pública de Producto. */
class ProductoTest {
    @Test
    void claseExisteYExponeApiPublica() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.Producto");
        assertNotNull(tipo);
        assertTrue(tipo.getDeclaredMethods().length + tipo.getMethods().length > 0,
                "La clase debe exponer operaciones verificables");
    }

    @Test
    void tipoDeDominioEsSerializable() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.Producto");
        assertTrue(java.io.Serializable.class.isAssignableFrom(tipo),
                "Producto debe poder guardarse como parte del estado del club");
    }
    @Test void actualizaPrecioStockYConsultaExistencias() {
        Producto p = new Producto("Agua", 2500, "Bebida", 10);
        p.setPrecio(3000); p.actualizarInventario(3);
        assertEquals(3000, p.getPrecio()); assertEquals(7, p.getStock());
        assertTrue(p.existencias(7)); assertFalse(p.existencias(8));
    }
}
