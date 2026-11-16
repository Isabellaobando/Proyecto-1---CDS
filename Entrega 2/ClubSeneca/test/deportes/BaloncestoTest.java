package deportes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BaloncestoTest {

    @Test
    void crearBaloncestoCorrectamente() {
        Baloncesto baloncesto = new Baloncesto("Baloncesto", 5, 12);

        assertEquals("Baloncesto", baloncesto.obtenerNombre());
        assertEquals(5, baloncesto.getMinJugadores());
        assertEquals(12, baloncesto.getMaxJugadores());
    }

    @Test
    void calcularPuntosCuandoGana() {
        assertEquals(3, Baloncesto.calcularPuntosLiga("GANADO"));
    }

    @Test
    void calcularPuntosCuandoPierde() {
        assertEquals(0, Baloncesto.calcularPuntosLiga("PERDIDO"));
    }

    @Test
    void determinarGanadorCuandoGanaEquipoFavor() {
        assertEquals("FAVOR", Baloncesto.determinarGanador(80, 70));
    }

    @Test
    void determinarGanadorCuandoPierdeEquipoFavor() {
        assertEquals("CONTRA", Baloncesto.determinarGanador(60, 75));
    }

    @Test
    void determinarGanadorCuandoHayEmpate() {
        assertEquals("CONTRA", Baloncesto.determinarGanador(70, 70));
    }
}