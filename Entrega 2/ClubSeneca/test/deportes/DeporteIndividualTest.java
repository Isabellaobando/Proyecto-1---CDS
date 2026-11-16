package deportes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import instalaciones.Instalacion;
import usuarios.Jugador;

class DeporteIndividualTest {

    private DeporteIndividual deporte;
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugador3;

    @BeforeEach
    void setUp() {
        deporte = new Padel("Padel");

        jugador1 = new Jugador(
                1, "Juan", "juan@gmail.com", "juan",
                "123", "3001111111", new Date(),
                "J001", new Date(), 0, 20, "Socio");

        jugador2 = new Jugador(
                2, "Pedro", "pedro@gmail.com", "pedro",
                "123", "3002222222", new Date(),
                "J002", new Date(), 0, 21, "Socio");

        jugador3 = new Jugador(
                3, "Carlos", "carlos@gmail.com", "carlos",
                "123", "3003333333", new Date(),
                "J003", new Date(), 0, 22, "Socio");
    }

    @Test
    void inscribirJugadorCorrectamente() {
        InscripcionDeporteIndividual inscripcion =
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador1);

        assertTrue(deporte.inscribirJugador(inscripcion));
    }

    @Test
    void rechazarInscripcionNula() {
        assertFalse(deporte.inscribirJugador(null));
    }

    @Test
    void rechazarInscripcionSinJugador() {
        InscripcionDeporteIndividual inscripcion =
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, null);

        assertFalse(deporte.inscribirJugador(inscripcion));
    }

    @Test
    void rechazarInscripcionSinNivel() {
        InscripcionDeporteIndividual inscripcion =
                new InscripcionDeporteIndividual(
                        deporte, null, 0, jugador1);

        assertFalse(deporte.inscribirJugador(inscripcion));
    }

    @Test
    void rechazarJugadorDuplicado() {
        InscripcionDeporteIndividual primera =
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador1);

        InscripcionDeporteIndividual segunda =
                new InscripcionDeporteIndividual(
                        deporte, "INTERMEDIO", 0, jugador1);

        assertTrue(deporte.inscribirJugador(primera));
        assertFalse(deporte.inscribirJugador(segunda));
    }

    @Test
    void consultarJugadoresPorNivel() {
        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador1));

        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "AVANZADO", 0, jugador2));

        List<InscripcionDeporteIndividual> resultado =
                deporte.consultarJugadoresPorNivel("PRINCIPIANTE");

        assertEquals(1, resultado.size());
        assertEquals(jugador1, resultado.get(0).consultarJugador());
    }

    @Test
    void consultarNivelIgnorandoMayusculas() {
        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "INTERMEDIO", 0, jugador1));

        assertEquals(1,
                deporte.consultarJugadoresPorNivel("intermedio").size());
    }

    @Test
    void consultarJugadoresConNivelNulo() {
        assertTrue(deporte.consultarJugadoresPorNivel(null).isEmpty());
    }

    @Test
    void actualizarRankingElo() {
        InscripcionDeporteIndividual inscripcion =
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 100, jugador1);

        deporte.actualizarRanking(inscripcion, 50);

        assertEquals(150, inscripcion.consultarElo());
    }

    @Test
    void actualizarRankingConInscripcionNula() {
        assertDoesNotThrow(() -> deporte.actualizarRanking(null, 50));
    }

    @Test
    void consultarPartidosInicialmenteVacios() {
        assertTrue(deporte.consultarPartidosProgramados().isEmpty());
    }

    @Test
    void programarPartidoCorrectamente() {
        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador1));

        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador2));

        Instalacion instalacion = new Instalacion(
                "Cancha 1", "Padel", 4, true,
                "08:00", "20:00", false);

        boolean resultado = deporte.programarPartido(
                jugador1, jugador2, instalacion,
                "2026-11-20", "10:00");

        assertTrue(resultado);
        assertEquals(1, deporte.consultarPartidosProgramados().size());
    }

    @Test
    void rechazarPartidoConJugadoresNulos() {
        assertFalse(deporte.puedenJugar(null, jugador1));
        assertFalse(deporte.puedenJugar(jugador1, null));
    }

    @Test
    void rechazarPartidoConMismoJugador() {
        assertFalse(deporte.puedenJugar(jugador1, jugador1));
    }

    @Test
    void rechazarPartidoConJugadorNoInscrito() {
        assertFalse(deporte.puedenJugar(jugador1, jugador2));
    }

    @Test
    void permitirPartidoEntreNivelesConsecutivos() {
        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador1));

        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "INTERMEDIO", 0, jugador2));

        assertTrue(deporte.puedenJugar(jugador1, jugador2));
    }

    @Test
    void rechazarPartidoEntreNivelesDistantes() {
        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador1));

        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "AVANZADO", 0, jugador2));

        assertFalse(deporte.puedenJugar(jugador1, jugador2));
    }

    @Test
    void rechazarPartidoEnInstalacionNula() {
        assertFalse(deporte.programarPartido(
                jugador1, jugador2, null,
                "2026-11-20", "10:00"));
    }

    @Test
    void rechazarPartidoEnInstalacionNoDisponible() {
        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador1));

        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador2));

        Instalacion instalacion = new Instalacion(
                "Cancha 1", "Padel", 4, false,
                "08:00", "20:00", false);

        assertFalse(deporte.programarPartido(
                jugador1, jugador2, instalacion,
                "2026-11-20", "10:00"));
    }

    @Test
    void rechazarPartidoPorCapacidadInsuficiente() {
        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador1));

        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador2));

        Instalacion instalacion = new Instalacion(
                "Cancha 1", "Padel", 1, true,
                "08:00", "20:00", false);

        assertFalse(deporte.programarPartido(
                jugador1, jugador2, instalacion,
                "2026-11-20", "10:00"));
    }

    @Test
    void rechazarPartidoEnInstalacionDeOtroDeporte() {
        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador1));

        deporte.inscribirJugador(
                new InscripcionDeporteIndividual(
                        deporte, "PRINCIPIANTE", 0, jugador2));

        Instalacion instalacion = new Instalacion(
                "Cancha 1", "Tenis", 4, true,
                "08:00", "20:00", false);

        assertFalse(deporte.programarPartido(
                jugador1, jugador2, instalacion,
                "2026-11-20", "10:00"));
    }

    @Test
    void consultarPartidosDevuelveCopiaDeLaLista() {
        List<String> partidos = deporte.consultarPartidosProgramados();

        partidos.add("Partido de prueba");

        assertTrue(deporte.consultarPartidosProgramados().isEmpty());
    }
}
