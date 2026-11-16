
package deportes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import usuarios.Jugador;

class InscripcionDeporteIndividualTest {

    private DeporteIndividual deporte;
    private Jugador jugador;
    private InscripcionDeporteIndividual inscripcion;

    @BeforeEach
    void setUp() {
        deporte = new Padel("Padel");

        jugador = new Jugador(
                1, "Juan", "juan@gmail.com", "juan",
                "123", "3001111111", new Date(),
                "J001", new Date(), 0, 20, "Socio");

        inscripcion = new InscripcionDeporteIndividual(
                deporte, "PRINCIPIANTE", 100, jugador);
    }

    @Test
    void consultarNivel() {
        assertEquals("PRINCIPIANTE", inscripcion.consultarNivel());
    }

    @Test
    void consultarJugador() {
        assertEquals(jugador, inscripcion.consultarJugador());
    }

    @Test
    void consultarDeporte() {
        assertEquals(deporte, inscripcion.consultarDeporte());
    }

    @Test
    void consultarEloInicial() {
        assertEquals(100.0, inscripcion.consultarElo(), 0.001);
    }

    @Test
    void actualizarRankingElo() {
        inscripcion.setRankingElo(250.5);

        assertEquals(250.5, inscripcion.consultarElo(), 0.001);
    }

    @Test
    void inscribirJugadorCorrectamente() {
        assertTrue(inscripcion.inscribirJugador());
    }

    @Test
    void rechazarInscripcionConDeporteNulo() {
        InscripcionDeporteIndividual nueva =
                new InscripcionDeporteIndividual(
                        null, "PRINCIPIANTE", 100, jugador);

        assertFalse(nueva.inscribirJugador());
    }

    @Test
    void rechazarInscripcionConJugadorNulo() {
        InscripcionDeporteIndividual nueva =
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 100, null);

        assertFalse(nueva.inscribirJugador());
    }

    @Test
    void rechazarInscripcionConNivelNulo() {
        InscripcionDeporteIndividual nueva =
                new InscripcionDeporteIndividual(
                        deporte, null, 100, jugador);

        assertFalse(nueva.inscribirJugador());
    }

    @Test
    void rechazarInscripcionDuplicada() {
        assertTrue(inscripcion.inscribirJugador());
        assertFalse(inscripcion.inscribirJugador());
    }
}
