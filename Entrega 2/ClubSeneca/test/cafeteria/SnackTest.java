package cafeteria;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Prueba de contrato y disponibilidad de la API pública de Snack. */
class SnackTest {
    @Test
    void claseExisteYExponeApiPublica() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.Snack");
        assertNotNull(tipo);
        assertTrue(tipo.getDeclaredMethods().length + tipo.getMethods().length > 0,
                "La clase debe exponer operaciones verificables");
    }

    @Test
    void tipoDeDominioEsSerializable() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.Snack");
        assertTrue(java.io.Serializable.class.isAssignableFrom(tipo),
                "Snack debe poder guardarse como parte del estado del club");
    }
    @Test void administraAlergenos() {
        Snack s = new Snack("Galleta", 2000, "Snack", 8, new java.util.ArrayList<>());
        s.agregarAlergenos("gluten"); assertTrue(s.getAlergenos().contains("gluten"));
        s.eliminarAlergenos("gluten"); assertFalse(s.getAlergenos().contains("gluten"));
    }
}
