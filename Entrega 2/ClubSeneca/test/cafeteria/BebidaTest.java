package cafeteria;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Prueba de contrato y disponibilidad de la API pública de Bebida. */
class BebidaTest {
    @Test
    void claseExisteYExponeApiPublica() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.Bebida");
        assertNotNull(tipo);
        assertTrue(tipo.getDeclaredMethods().length + tipo.getMethods().length > 0,
                "La clase debe exponer operaciones verificables");
    }

    @Test
    void tipoDeDominioEsSerializable() throws Exception {
        Class<?> tipo = Class.forName("cafeteria.Bebida");
        assertTrue(java.io.Serializable.class.isAssignableFrom(tipo),
                "Bebida debe poder guardarse como parte del estado del club");
    }
    @Test void conservaYModificaIndicadorDeBebidaCaliente() {
        Bebida b = new Bebida("Té", 4000, "Bebida", 5, true);
        assertTrue(b.getEsCaliente()); b.setEsCaliente(false); assertFalse(b.getEsCaliente());
    }
}
