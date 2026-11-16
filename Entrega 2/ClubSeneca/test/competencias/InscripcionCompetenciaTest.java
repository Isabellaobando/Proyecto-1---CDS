package competencias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deportes.Voleibol;
import equipos.Equipo;
import equipos.InscripcionEquipo;
import usuarios.Jugador;

class InscripcionCompetenciaTest {

    private InscripcionCompetencia inscripcion;
    private Equipo equipo;
    private Jugador jugador;
    private Date fecha;

    @BeforeEach
    void setUp() {
        fecha = new Date();

        Voleibol deporte = new Voleibol("Voleibol", 6, 12);

        equipo = new Equipo("MAYORES", "Los Halcones", true, deporte);

        jugador = new Jugador(
                1,
                "Juan",
                "juan@gmail.com",
                "juan",
                "1234",
                "3001234567",
                new Date(90, 0, 1),
                "JUG001",
                fecha,
                100,
                25,
                Jugador.TIPO_SOCIO
        );

        inscripcion = new InscripcionCompetencia(fecha, equipo);
    }

    @Test
    void consultarFechaInscripcion() {
        assertEquals(fecha, inscripcion.getFechaInscripcion());
    }

    @Test
    void modificarFechaInscripcion() {
        Date nuevaFecha = new Date();
        inscripcion.setFechaInscripcion(nuevaFecha);

        assertEquals(nuevaFecha, inscripcion.getFechaInscripcion());
    }

    @Test
    void consultarEquipo() {
        assertEquals(equipo, inscripcion.getEquipo());
    }

    @Test
    void modificarEquipo() {
        Equipo nuevoEquipo = new Equipo(
                "MAYORES",
                "Los Tigres",
                true,
                new Voleibol("Voleibol", 6, 12)
        );

        inscripcion.setEquipo(nuevoEquipo);

        assertEquals(nuevoEquipo, inscripcion.getEquipo());
    }

    @Test
    void rechazarNominacionDeJugadorNulo() {
        assertFalse(inscripcion.validarNominacion(null));
    }

    @Test
    void rechazarNominacionSiEquipoEstaInactivo() {
        equipo.setActivo(false);

        assertFalse(inscripcion.validarNominacion(jugador));
    }

    @Test
    void rechazarNominacionSiJugadorNoEstaInscrito() {
        equipo.setActivo(true);

        assertFalse(inscripcion.validarNominacion(jugador));
    }

    @Test
    void aceptarNominacionDeJugadorValido() {
        equipo.setActivo(true);

        InscripcionEquipo registro = new InscripcionEquipo(
                "Central",
                10,
                true,
                false,
                fecha,
                equipo,
                jugador
        );

        equipo.agregarJugador(registro);

        assertTrue(inscripcion.validarNominacion(jugador));
    }

    @Test
    void rechazarNominacionDeJugadorSuspendido() {
        equipo.setActivo(true);

        InscripcionEquipo registro = new InscripcionEquipo(
                "Central",
                10,
                true,
                true,
                fecha,
                equipo,
                jugador
        );

        equipo.agregarJugador(registro);

        assertFalse(inscripcion.validarNominacion(jugador));
    }

    @Test
    void consultarNominaDelEquipo() {
        InscripcionEquipo registro = new InscripcionEquipo(
                "Central",
                10,
                true,
                false,
                fecha,
                equipo,
                jugador
        );

        equipo.agregarJugador(registro);

        List<Jugador> nomina = inscripcion.consultarNomina();

        assertEquals(1, nomina.size());
        assertEquals(jugador, nomina.get(0));
    }

    @Test
    void consultarNominaVacia() {
        List<Jugador> nomina = inscripcion.consultarNomina();

        assertTrue(nomina.isEmpty());
    }
}