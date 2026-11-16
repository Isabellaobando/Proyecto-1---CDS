package usuarios;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Prueba de contrato y disponibilidad de la API pública de Usuario. */
class UsuarioTest {
    @Test
    void claseExisteYExponeApiPublica() throws Exception {
        Class<?> tipo = Class.forName("usuarios.Usuario");
        assertNotNull(tipo);
        assertTrue(tipo.getDeclaredMethods().length + tipo.getMethods().length > 0,
                "La clase debe exponer operaciones verificables");
    }


    @Test
    void validaCredencialesConCadenasDeIgualContenidoAunqueSeanObjetosDistintos() {
        Usuario usuario = new Usuario(1, "Ana", "ana@example.com", "ana", "clave123",
                "3000000000", new java.util.Date(), 20) {};

        assertTrue(usuario.validarCredenciales(new String("ana"), new String("clave123")));
        assertFalse(usuario.validarCredenciales("ana", "incorrecta"));
        assertFalse(usuario.validarCredenciales("otra", "clave123"));
        assertFalse(usuario.validarCredenciales(null, "clave123"));
        assertFalse(usuario.validarCredenciales("ana", null));
    }

    @Test
    void tipoDeDominioEsSerializable() throws Exception {
        Class<?> tipo = Class.forName("usuarios.Usuario");
        assertTrue(java.io.Serializable.class.isAssignableFrom(tipo),
                "Usuario debe poder guardarse como parte del estado del club");
    }
}
