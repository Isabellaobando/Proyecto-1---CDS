package deportes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FutbolTest {

    @Test
    void crearFutbolCorrectamente() {
        Futbol futbol = new Futbol("Futbol", 11, 25);

        assertEquals("Futbol", futbol.obtenerNombre());
        assertEquals(11, futbol.getMinJugadores());
        assertEquals(25, futbol.getMaxJugadores());
    }

    @Test
    void calcularPuntosCuandoGana() {
        assertEquals(3, Futbol.calcularPuntosLiga("GANADO"));
    }

    @Test
    void calcularPuntosCuandoEmpata() {
        assertEquals(1, Futbol.calcularPuntosLiga("EMPATADO"));
    }

    @Test
    void calcularPuntosCuandoPierde() {
        assertEquals(0, Futbol.calcularPuntosLiga("PERDIDO"));
    }

    @Test
    void determinarGanadorCuandoGanaEquipoFavor() {
        assertEquals("FAVOR", Futbol.determinarGanador(3, 1));
    }

    @Test
    void determinarGanadorCuandoGanaEquipoContra() {
        assertEquals("CONTRA", Futbol.determinarGanador(1, 3));
    }

    @Test
    void determinarGanadorCuandoHayEmpate() {
        assertEquals("EMPATE", Futbol.determinarGanador(2, 2));
    }

    @Test
    void suspenderJugadorPorDosAmarillas() {
        assertTrue(Futbol.seSuspendeJugador(2, 0));
    }

    @Test
    void suspenderJugadorPorTarjetaRoja() {
        assertTrue(Futbol.seSuspendeJugador(0, 1));
    }

    @Test
    void noSuspenderJugadorConUnaAmarilla() {
        assertFalse(Futbol.seSuspendeJugador(1, 0));
    }

    @Test
    void noSuspenderJugadorSinTarjetas() {
        assertFalse(Futbol.seSuspendeJugador(0, 0));
    }
}
