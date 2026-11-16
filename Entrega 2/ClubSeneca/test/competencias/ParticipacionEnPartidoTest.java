package competencias;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import estadistica.Estadisticas;
import usuarios.Jugador;

class ParticipacionEnPartidoTest {

    private ParticipacionEnPartido participacion;

    @BeforeEach
    void setUp() {
        participacion = new ParticipacionEnPartido();
    }

    @Test
    void constructorPorDefectoInicializaEstadisticas() {
        assertNotNull(participacion.consultarEstadisticas());
    }

    @Test
    void constructorInicializaTitularYMinutos() {
        ParticipacionEnPartido p = new ParticipacionEnPartido(true, 90);

        assertTrue(p.isTitular());
        assertEquals(90, p.getMinutosJugados());
    }

    @Test
    void cambiarEstadoDeTitular() {
        participacion.setTitular(true);
        assertTrue(participacion.isTitular());

        participacion.setTitular(false);
        assertFalse(participacion.isTitular());
    }

    @Test
    void asignarMinutosValidos() {
        participacion.setMinutosJugados(45);
        assertEquals(45, participacion.getMinutosJugados());
    }

    @Test
    void aceptarMinimoDeMinutos() {
        participacion.setMinutosJugados(0);
        assertEquals(0, participacion.getMinutosJugados());
    }

    @Test
    void aceptarMaximoDeMinutos() {
        participacion.setMinutosJugados(120);
        assertEquals(120, participacion.getMinutosJugados());
    }

    @Test
    void rechazarMinutosNegativos() {
        assertThrows(IllegalArgumentException.class,
            () -> participacion.setMinutosJugados(-1));
    }

    @Test
    void rechazarMinutosMayoresA120() {
        assertThrows(IllegalArgumentException.class,
            () -> participacion.setMinutosJugados(121));
    }

    @Test
    void registrarJugador() {
        Jugador jugador = crearJugador();

        participacion.registrarParticipacion(jugador);

        assertSame(jugador, participacion.getJugador());
    }

    @Test
    void rechazarJugadorNulo() {
        assertThrows(IllegalArgumentException.class,
            () -> participacion.registrarParticipacion(null));
    }

    @Test
    void consultarEstadisticasIniciales() {
        assertNotNull(participacion.consultarEstadisticas());
    }

    @Test
    void asignarEstadisticasValidas() {
        Estadisticas estadisticas = new Estadisticas();

        participacion.setEstadisticas(estadisticas);

        assertSame(estadisticas, participacion.consultarEstadisticas());
    }

    @Test
    void rechazarEstadisticasNulas() {
        assertThrows(IllegalArgumentException.class,
            () -> participacion.setEstadisticas(null));
    }

    @Test
    void registrarTarjetaAmarilla() {
        participacion.registrarTarjetaAmarilla();

        assertEquals(1, participacion.getTarjetasAmarillas());
        assertEquals(1, participacion.getAcumulacionTarjetas());
    }

    @Test
    void registrarVariasTarjetasAmarillas() {
        participacion.registrarTarjetaAmarilla();
        participacion.registrarTarjetaAmarilla();

        assertEquals(2, participacion.getTarjetasAmarillas());
        assertEquals(2, participacion.getAcumulacionTarjetas());
    }

    @Test
    void registrarTarjetaRoja() {
        participacion.registrarTarjetaRoja();

        assertEquals(1, participacion.getTarjetasRojas());
    }

    @Test
    void verificarSuspensionPorTarjetaRoja() {
        participacion.registrarTarjetaRoja();

        assertTrue(participacion.verificarSuspension());
    }

    @Test
    void verificarSuspensionPorDosAmarillas() {
        participacion.registrarTarjetaAmarilla();
        participacion.registrarTarjetaAmarilla();

        assertTrue(participacion.verificarSuspension());
    }

    @Test
    void noHaySuspensionSinTarjetas() {
        assertFalse(participacion.verificarSuspension());
    }

    @Test
    void noHaySuspensionConUnaAmarilla() {
        participacion.registrarTarjetaAmarilla();

        assertFalse(participacion.verificarSuspension());
    }

    @Test
    void actualizarAcumulacionDeTarjetas() {
        participacion.registrarTarjetaAmarilla();
        participacion.actualizarAcumulacionTarjetas();

        assertEquals(participacion.getTarjetasAmarillas(),
                     participacion.getAcumulacionTarjetas());
    }

    private Jugador crearJugador() {
        return new Jugador(
            1,
            "Juan",
            "juan@email.com",
            "juan",
            "1234",
            "3001234567",
            new java.util.Date(),
            "JUG001",
            new java.util.Date(),
            0,
            20,
            "Activo"
        );
    }
}
