package equipos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deportes.Baloncesto;
import usuarios.Jugador;
import equipos.Equipo;
import equipos.InscripcionEquipo;

class InscripcionEquipoTest {

    private Equipo equipo;
    private Jugador jugador;
    private InscripcionEquipo inscripcion;

    @BeforeEach
    void setUp() {
        Baloncesto deporte = new Baloncesto("Baloncesto", 2, 5);

        equipo = new Equipo("MAYORES", "Los Halcones", true, deporte);

        jugador = new Jugador(
                1, "Juan", "juan@gmail.com", "juan",
                "123", "3001111111", new Date(),
                "J001", new Date(), 0, 20, "Socio");

        inscripcion = new InscripcionEquipo(
                "Base", 10, true, false,
                new Date(), equipo, jugador);
    }

    @Test
    void consultarJugador() {
        assertEquals(jugador, inscripcion.getJugador());
    }

    @Test
    void consultarEquipo() {
        assertEquals(equipo, inscripcion.getEquipo());
    }

    @Test
    void consultarPosicion() {
        assertEquals("Base", inscripcion.getPosicion());
    }

    @Test
    void consultarNumeroCamisa() {
        assertEquals(10, inscripcion.getNumCamisa());
    }

    @Test
    void consultarTitularidad() {
        assertTrue(inscripcion.isTitular());
    }

    @Test
    void consultarEstadoDeSuspension() {
        assertFalse(inscripcion.getSuspendido());
    }

    @Test
    void consultarFechaInscripcion() {
        assertNotNull(inscripcion.getFechaInscripcion());
    }

    @Test
    void inscribirJugadorCorrectamente() {
        assertTrue(inscripcion.inscribirJugador());
        assertEquals(1, equipo.consultarJugadores().size());
    }

    @Test
    void rechazarInscripcionDuplicada() {
        assertTrue(inscripcion.inscribirJugador());
        assertFalse(inscripcion.inscribirJugador());
    }

    @Test
    void cancelarInscripcionCorrectamente() {
        inscripcion.inscribirJugador();

        assertTrue(inscripcion.cancelarInscripcion());
        assertTrue(equipo.consultarJugadores().isEmpty());
    }

    @Test
    void rechazarCancelacionDeInscripcionNoRegistrada() {
        assertFalse(inscripcion.cancelarInscripcion());
    }

    @Test
    void permitirCategoriaSub13() {
        Jugador menor = crearJugador(2, "Pedro", "J002", 13);
        Equipo sub13 = crearEquipo("SUB-13");

        assertTrue(InscripcionEquipo.sePuedeInscribir(menor, sub13));
    }

    @Test
    void rechazarCategoriaSub13PorEdad() {
        Jugador menor = crearJugador(3, "Carlos", "J003", 14);
        Equipo sub13 = crearEquipo("SUB-13");

        assertFalse(InscripcionEquipo.sePuedeInscribir(menor, sub13));
    }

    @Test
    void permitirCategoriaSub15() {
        Jugador jugadorSub15 = crearJugador(4, "Luis", "J004", 15);
        Equipo sub15 = crearEquipo("SUB-15");

        assertTrue(InscripcionEquipo.sePuedeInscribir(jugadorSub15, sub15));
    }

    @Test
    void permitirCategoriaSub17() {
        Jugador jugadorSub17 = crearJugador(5, "Ana", "J005", 17);
        Equipo sub17 = crearEquipo("SUB-17");

        assertTrue(InscripcionEquipo.sePuedeInscribir(jugadorSub17, sub17));
    }

    @Test
    void permitirCategoriaMayores() {
        Jugador adulto = crearJugador(6, "Maria", "J006", 18);
        Equipo mayores = crearEquipo("MAYORES");

        assertTrue(InscripcionEquipo.sePuedeInscribir(adulto, mayores));
    }

    @Test
    void rechazarCategoriaMayoresPorEdad() {
        Jugador menor = crearJugador(7, "Pablo", "J007", 17);
        Equipo mayores = crearEquipo("MAYORES");

        assertFalse(InscripcionEquipo.sePuedeInscribir(menor, mayores));
    }

    private Jugador crearJugador(
            int id, String nombre, String codigo, int edad) {

        return new Jugador(
                id, nombre, nombre.toLowerCase() + "@gmail.com",
                nombre.toLowerCase(), "123", "3001111111",
                new Date(), codigo, new Date(), 0, edad, "Socio");
    }

    private Equipo crearEquipo(String categoria) {
        Baloncesto deporte = new Baloncesto("Baloncesto", 2, 5);
        return new Equipo(categoria, "Equipo de prueba", true, deporte);
    }
}