package persistencia;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import logica.ClubSeneca;
import cafeteria.Local;
import cafeteria.Producto;

class PersistenciaClubSenecaTest {
    @TempDir Path temporal;

    @Test void guardaYCargaEstadoCompletoDelClub() throws Exception {
        ClubSeneca original = new ClubSeneca();
        Local local = new Local("Sede principal", "07:00", "20:00");
        local.agregarProducto(new Producto("Agua", 3000, "Bebida", 12));
        original.agregarLocal(local);
        Path archivo = temporal.resolve("datos/club.dat");
        PersistenciaClubSeneca.guardar(original, archivo);
        assertTrue(Files.exists(archivo));
        ClubSeneca recuperado = PersistenciaClubSeneca.cargar(archivo);
        assertEquals(1, recuperado.getLocales().size());
        assertEquals("Sede principal", recuperado.getLocales().get(0).getNombre());
        assertEquals(12, recuperado.getLocales().get(0).getProductos().get(0).getStock());
    }

    @Test void rechazaArgumentosNulos() {
        assertThrows(NullPointerException.class, () -> PersistenciaClubSeneca.guardar(null, temporal.resolve("x")));
        assertThrows(NullPointerException.class, () -> PersistenciaClubSeneca.cargar(null));
    }
}
