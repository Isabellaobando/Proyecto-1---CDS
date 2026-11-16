package competencias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import equipos.Equipo;
import estadistica.Estadisticas;
import deportes.Voleibol;

class PartidoTest {

    private Partido partido;

    @BeforeEach
    void setUp() {
        partido = new Partido();
    }

    @Test
    void constructorPorDefectoInicializaEstadisticas() {
        assertNotNull(partido.consultarEstadisticas());
        assertTrue(partido.consultarEstadisticas().isEmpty());
    }

    @Test
    void constructorCompletoInicializaAtributos() {
        Date fecha = new Date();
        Equipo local = crearEquipo("Local");
        Equipo visitante = crearEquipo("Visitante");

        Partido p = new Partido(
            fecha, "10:00", "Pendiente", local, visitante
        );

        assertEquals(fecha, p.getFecha());
        assertEquals("10:00", p.getHora());
        assertEquals("Pendiente", p.getEstado());
        assertSame(local, p.getEquipoLocal());
        assertSame(visitante, p.getEquipoVisitante());
        assertNotNull(p.consultarEstadisticas());
    }

    @Test
    void modificarFecha() {
        Date fecha = new Date();
        partido.setFecha(fecha);

        assertEquals(fecha, partido.getFecha());
    }

    @Test
    void modificarHora() {
        partido.setHora("14:30");

        assertEquals("14:30", partido.getHora());
    }

    @Test
    void modificarEstado() {
        partido.setEstado("Programado");

        assertEquals("Programado", partido.getEstado());
    }

    @Test
    void programarPartidoSinFecha() {
        partido.setHora("10:00");

        assertFalse(partido.programarPartido());
    }

    @Test
    void programarPartidoSinHora() {
        partido.setFecha(new Date());

        assertFalse(partido.programarPartido());
    }

    @Test
    void programarPartidoSinEquipos() {
        partido.setFecha(new Date());
        partido.setHora("10:00");

        assertFalse(partido.programarPartido());
    }

    @Test
    void rechazarMismoEquipoComoLocalYVisitante() {
        Equipo equipo = crearEquipo("Equipo");

        Partido p = new Partido(
            new Date(), "10:00", "Pendiente", equipo, equipo
        );

        assertFalse(p.programarPartido());
    }

    @Test
    void registrarMarcadorValido() {
        partido.registrarMarcador(3, 1);

        assertEquals(3, partido.getMarcadorLocal());
        assertEquals(1, partido.getMarcadorVisitante());
        assertEquals("Finalizado", partido.getEstado());
    }

    @Test
    void rechazarMarcadorNegativo() {
        partido.setEstado("Programado");
        partido.registrarMarcador(-1, 2);

        assertEquals(0, partido.getMarcadorLocal());
        assertEquals(0, partido.getMarcadorVisitante());
        assertEquals("Programado", partido.getEstado());
    }

    @Test
    void calcularResultadoDePartidoNoFinalizado() {
        assertEquals(
            "Partido no finalizado",
            partido.calcularResultado()
        );
    }

    @Test
    void calcularVictoriaLocal() {
        partido.registrarMarcador(3, 1);

        assertEquals(
            "Victoria local: 3 - 1",
            partido.calcularResultado()
        );
    }

    @Test
    void calcularVictoriaVisitante() {
        partido.registrarMarcador(1, 3);

        assertEquals(
            "Victoria visitante: 1 - 3",
            partido.calcularResultado()
        );
    }

    @Test
    void calcularEmpate() {
        partido.registrarMarcador(2, 2);

        assertEquals(
            "Empate: 2 - 2",
            partido.calcularResultado()
        );
    }

    @Test
    void registrarEstadisticaValida() {
        Estadisticas estadistica = new Estadisticas();

        partido.registrarEstadistica(estadistica);

        assertEquals(1, partido.consultarEstadisticas().size());
        assertSame(estadistica, partido.consultarEstadisticas().get(0));
    }

    @Test
    void ignorarEstadisticaNula() {
        partido.registrarEstadistica(null);

        assertTrue(partido.consultarEstadisticas().isEmpty());
    }

    @Test
    void consultarEquiposDelPartido() {
        Equipo local = crearEquipo("Local");
        Equipo visitante = crearEquipo("Visitante");

        Partido p = new Partido(
            new Date(), "10:00", "Pendiente", local, visitante
        );

        assertSame(local, p.getEquipoLocal());
        assertSame(visitante, p.getEquipoVisitante());
    }

    private Equipo crearEquipo(String nombre) {
        Voleibol deporte = new Voleibol("Voleibol", 6, 12);
        return new Equipo("Mayores", nombre, true, deporte);
    }
}
