package deportes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VoleibolTest {

    @Test
    void crearVoleibolCorrectamente() {
        Voleibol voleibol = new Voleibol("Voleibol", 6, 12);

        assertEquals("Voleibol", voleibol.obtenerNombre());
        assertEquals(6, voleibol.getMinJugadores());
        assertEquals(12, voleibol.getMaxJugadores());
    }

    @Test
    void calcularPuntosCuandoGana() {
        assertEquals(3, Voleibol.calcularPuntosLiga("GANADO"));
    }

    @Test
    void calcularPuntosCuandoPierde() {
        assertEquals(0, Voleibol.calcularPuntosLiga("PERDIDO"));
    }

    @Test
    void calcularPuntosConResultadoInvalido() {
        assertEquals(0, Voleibol.calcularPuntosLiga("EMPATADO"));
    }

    @Test
    void determinarGanadorCuandoGanaEquipoFavor() {
        assertEquals("FAVOR", Voleibol.determinarGanador(3, 1));
    }

    @Test
    void determinarGanadorCuandoGanaEquipoContra() {
        assertEquals("CONTRA", Voleibol.determinarGanador(1, 3));
    }

    @Test
    void determinarGanadorCuandoHayEmpate() {
        assertEquals("CONTRA", Voleibol.determinarGanador(2, 2));
    }
}
