package competencias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deportes.Voleibol;
import equipos.Equipo;

class TorneoDeLosCerrosTest {

    private TorneoDeLosCerros torneo;
    private Equipo equipoA;
    private Equipo equipoB;
    private Equipo equipoC;

    @BeforeEach
    void setUp() {
        torneo = new TorneoDeLosCerros(
            "Torneo de los Cerros",
            new Date(),
            new Date(),
            10,
            1
        );

        Voleibol deporte = new Voleibol("Voleibol", 6, 12);

        equipoA = new Equipo("Mayores", "Halcones", true, deporte);
        equipoB = new Equipo("Mayores", "Águilas", true, deporte);
        equipoC = new Equipo("Mayores", "Cóndores", true, deporte);
    }

    @Test
    void constructorInicializaJornadas() {
        assertEquals(10, torneo.getJornadasTotales());
        assertEquals(1, torneo.getJornadaActual());
    }

    @Test
    void constructorInicializaListaDePartidos() {
        assertNotNull(torneo.getPartidos());
        assertTrue(torneo.getPartidos().isEmpty());
    }

    @Test
    void agregarEquipoAlTorneo() {
        torneo.agregarEquipo(equipoA);

        assertEquals(0, torneo.calcularPuntosEquipo(equipoA));
    }

    @Test
    void agregarEquipoNoDuplicaRegistro() {
        torneo.agregarEquipo(equipoA);
        torneo.agregarEquipo(equipoA);

        assertEquals(0, torneo.calcularPuntosEquipo(equipoA));
        assertEquals(1, torneo.consultarTablaPosiciones().size());
    }

    @Test
    void agregarVariosEquipos() {
        torneo.agregarEquipo(equipoA);
        torneo.agregarEquipo(equipoB);
        torneo.agregarEquipo(equipoC);

        assertEquals(3, torneo.consultarTablaPosiciones().size());
    }

    @Test
    void calcularCeroPuntosParaEquipoNoRegistrado() {
        assertEquals(0, torneo.calcularPuntosEquipo(equipoA));
    }

    @Test
    void agregarPartido() {
        Partido partido = crearPartido(equipoA, equipoB, 2, 1);

        torneo.agregarPartido(partido);

        assertEquals(1, torneo.getPartidos().size());
        assertSame(partido, torneo.getPartidos().get(0));
    }

    @Test
    void registrarVictoriaLocal() {
        torneo.agregarPartido(crearPartido(equipoA, equipoB, 3, 1));

        torneo.actualizarTablaPosiciones();

        assertEquals(3, torneo.calcularPuntosEquipo(equipoA));
        assertEquals(0, torneo.calcularPuntosEquipo(equipoB));
    }

    @Test
    void registrarVictoriaVisitante() {
        torneo.agregarPartido(crearPartido(equipoA, equipoB, 1, 3));

        torneo.actualizarTablaPosiciones();

        assertEquals(0, torneo.calcularPuntosEquipo(equipoA));
        assertEquals(3, torneo.calcularPuntosEquipo(equipoB));
    }

    @Test
    void registrarEmpate() {
        torneo.agregarPartido(crearPartido(equipoA, equipoB, 2, 2));

        torneo.actualizarTablaPosiciones();

        assertEquals(1, torneo.calcularPuntosEquipo(equipoA));
        assertEquals(1, torneo.calcularPuntosEquipo(equipoB));
    }

    @Test
    void ignorarPartidoNoFinalizado() {
        Partido partido = new Partido(
            new Date(), "10:00", "Programado", equipoA, equipoB
        );

        partido.registrarMarcador(3, 0);
        partido.setEstado("Programado");

        torneo.agregarPartido(partido);
        torneo.actualizarTablaPosiciones();

        assertEquals(0, torneo.calcularPuntosEquipo(equipoA));
        assertEquals(0, torneo.calcularPuntosEquipo(equipoB));
    }

    @Test
    void actualizarPuntosDeVariosPartidos() {
        torneo.agregarPartido(crearPartido(equipoA, equipoB, 3, 1));
        torneo.agregarPartido(crearPartido(equipoA, equipoC, 2, 2));
        torneo.agregarPartido(crearPartido(equipoB, equipoC, 0, 1));

        torneo.actualizarTablaPosiciones();

        assertEquals(4, torneo.calcularPuntosEquipo(equipoA));
        assertEquals(0, torneo.calcularPuntosEquipo(equipoB));
        assertEquals(4, torneo.calcularPuntosEquipo(equipoC));
    }

    @Test
    void actualizarTablaRecalculaPuntosDesdeCero() {
        torneo.agregarPartido(crearPartido(equipoA, equipoB, 3, 0));

        torneo.actualizarTablaPosiciones();
        assertEquals(3, torneo.calcularPuntosEquipo(equipoA));

        torneo.actualizarTablaPosiciones();

        assertEquals(3, torneo.calcularPuntosEquipo(equipoA));
        assertEquals(0, torneo.calcularPuntosEquipo(equipoB));
    }

    @Test
    void consultarTablaOrdenadaPorPuntos() {
        torneo.agregarPartido(crearPartido(equipoA, equipoB, 3, 0));
        torneo.agregarPartido(crearPartido(equipoA, equipoC, 3, 0));
        torneo.agregarPartido(crearPartido(equipoB, equipoC, 2, 0));

        torneo.actualizarTablaPosiciones();

        List<Equipo> tabla = torneo.consultarTablaPosiciones();

        assertEquals(equipoA, tabla.get(0));
        assertEquals(equipoB, tabla.get(1));
        assertEquals(equipoC, tabla.get(2));
    }

    @Test
    void determinarPrimeraPosicion() {
        torneo.agregarPartido(crearPartido(equipoA, equipoB, 3, 0));
        torneo.actualizarTablaPosiciones();

        assertEquals(1, torneo.determinarPosicion(equipoA));
    }

    @Test
    void determinarSegundaPosicion() {
        torneo.agregarPartido(crearPartido(equipoA, equipoB, 3, 0));
        torneo.actualizarTablaPosiciones();

        assertEquals(2, torneo.determinarPosicion(equipoB));
    }

    @Test
    void equipoNoRegistradoTienePosicionCero() {
        assertEquals(0, torneo.determinarPosicion(equipoC));
    }

    @Test
    void consultarTablaVacia() {
        assertTrue(torneo.consultarTablaPosiciones().isEmpty());
    }

    private Partido crearPartido(
            Equipo local, Equipo visitante, int marcadorLocal,
            int marcadorVisitante) {

        Partido partido = new Partido(
            new Date(),
            "10:00",
            "Programado",
            local,
            visitante
        );

        partido.registrarMarcador(marcadorLocal, marcadorVisitante);

        return partido;
    }
}
