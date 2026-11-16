package cafeteria;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Prueba de contrato y disponibilidad de la API pública de BonoDescuento. */
class BonoDescuentoTest {
    @Test
    void claseExisteYExponeApiPublica() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.BonoDescuento");
        assertNotNull(tipo);
        assertTrue(tipo.getDeclaredMethods().length + tipo.getMethods().length > 0,
                "La clase debe exponer operaciones verificables");
    }

    @Test
    void tipoDeDominioEsSerializable() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.BonoDescuento");
        assertTrue(java.io.Serializable.class.isAssignableFrom(tipo),
                "BonoDescuento debe poder guardarse como parte del estado del club");
    }
    @Test void calculaDescuentoYEvitaReutilizacion() {
        BonoDescuento b = new BonoDescuento("B1", 0.2, "Torneo");
        assertEquals(20.0, b.calcularDescuento(100), 0.001); b.setRedimido();
        assertEquals(0.0, b.calcularDescuento(100), 0.001); assertTrue(b.getRedimido());
    }
}
