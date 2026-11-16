package equipos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import competencias.Torneo;
import deportes.Baloncesto;
import usuarios.Jugador;
import usuarios.Participante;
import equipos.Equipo;
import equipos.InscripcionEquipo;

class EquipoTest {

    private Equipo equipo;
    private Baloncesto deporte;
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugador3;

    @BeforeEach
    void setUp() {
        deporte = new Baloncesto("Baloncesto", 2, 3);

        equipo = new Equipo(
                "MAYORES", "Los Halcones", true, deporte);

        jugador1 = crearJugador(1, "Juan", "J001", 20);
        jugador2 = crearJugador(2, "Pedro", "J002", 21);
        jugador3 = crearJugador(3, "Carlos", "J003", 22);
    }

    private Jugador crearJugador(
            int id, String nombre, String codigo, int edad) {

        return new Jugador(
                id, nombre, nombre.toLowerCase() + "@gmail.com",
                nombre.toLowerCase(), "123", "3001111111",
                new Date(), codigo, new Date(), 0, edad, "Socio");
    }

    private InscripcionEquipo crearInscripcion(Jugador jugador) {
        return new InscripcionEquipo(
                "Jugador", 10, true, false,
                new Date(), equipo, jugador);
    }

    @Test
    void crearEquipoCorrectamente() {
        assertEquals("MAYORES", equipo.getCategoria());
        assertEquals("Los Halcones", equipo.getNombre());
        assertEquals(deporte, equipo.getDeporte());
        assertFalse(equipo.isActivo());
    }

    @Test
    void cambiarEstadoActivo() {
        equipo.setActivo(true);

        assertTrue(equipo.isActivo());
    }

    @Test
    void cambiarEstadoInactivo() {
        equipo.setActivo(true);
        equipo.setActivo(false);

        assertFalse(equipo.isActivo());
    }

    @Test
    void consultarJugadoresInicialmenteVacios() {
        assertTrue(equipo.consultarJugadores().isEmpty());
    }

    @Test
    void agregarJugadorCorrectamente() {
        assertTrue(equipo.agregarJugador(crearInscripcion(jugador1)));
        assertEquals(1, equipo.consultarJugadores().size());
    }

    @Test
    void activarEquipoAlAlcanzarMinimoJugadores() {
        equipo.agregarJugador(crearInscripcion(jugador1));
        equipo.agregarJugador(crearInscripcion(jugador2));

        assertTrue(equipo.isActivo());
        assertTrue(equipo.puedeCompetir());
    }

    @Test
    void rechazarJugadorDuplicado() {
        equipo.agregarJugador(crearInscripcion(jugador1));

        assertFalse(equipo.agregarJugador(crearInscripcion(jugador1)));
        assertEquals(1, equipo.consultarJugadores().size());
    }

    @Test
    void rechazarJugadorCuandoNoHayCupos() {
        equipo.agregarJugador(crearInscripcion(jugador1));
        equipo.agregarJugador(crearInscripcion(jugador2));
        equipo.agregarJugador(crearInscripcion(jugador3));

        Jugador cuarto = crearJugador(4, "Luis", "J004", 23);

        assertFalse(equipo.agregarJugador(crearInscripcion(cuarto)));
        assertEquals(3, equipo.consultarJugadores().size());
    }

    @Test
    void retirarJugadorCorrectamente() {
        InscripcionEquipo inscripcion = crearInscripcion(jugador1);
        equipo.agregarJugador(inscripcion);

        assertTrue(equipo.retirarJugador(inscripcion));
        assertTrue(equipo.consultarJugadores().isEmpty());
    }

    @Test
    void desactivarEquipoAlQuedarDebajoDelMinimo() {
        InscripcionEquipo primera = crearInscripcion(jugador1);
        InscripcionEquipo segunda = crearInscripcion(jugador2);

        equipo.agregarJugador(primera);
        equipo.agregarJugador(segunda);

        assertTrue(equipo.isActivo());

        equipo.retirarJugador(primera);

        assertFalse(equipo.isActivo());
    }

    @Test
    void rechazarRetiroDeJugadorNoInscrito() {
        assertFalse(equipo.retirarJugador(crearInscripcion(jugador1)));
    }

    @Test
    void consultarJugadorPorCodigo() {
        equipo.agregarJugador(crearInscripcion(jugador1));

        assertNotNull(equipo.consultarJugador("J001"));
        assertNull(equipo.consultarJugador("J999"));
    }

    @Test
    void consultarJugadoresDevuelveCopia() {
        equipo.agregarJugador(crearInscripcion(jugador1));

        List<InscripcionEquipo> copia = equipo.consultarJugadores();
        copia.clear();

        assertEquals(1, equipo.consultarJugadores().size());
    }

    @Test
    void consultarCantidadJugadoresHabilitados() {
        equipo.agregarJugador(crearInscripcion(jugador1));
        equipo.agregarJugador(crearInscripcion(jugador2));

        assertEquals(2, equipo.obtenerCantidadJugadoresHabilitados());
    }

    @Test
    void consultarIntegrantes() {
        equipo.agregarJugador(crearInscripcion(jugador1));
        equipo.agregarJugador(crearInscripcion(jugador2));

        List<usuarios.Usuario> integrantes = equipo.getIntegrantes();

        assertEquals(2, integrantes.size());
        assertTrue(integrantes.contains(jugador1));
        assertTrue(integrantes.contains(jugador2));
    }

    @Test
    void consultarRanking() {
        equipo.setDesempeno(85.5);

        assertEquals(85.5, equipo.getRanking(deporte), 0.001);
    }

    @Test
    void modificarDesempeno() {
        equipo.setDesempeno(90);

        assertEquals(90, equipo.getDesempeno(), 0.001);
    }

    @Test
    void consultarProximosPartidosInicialmenteVacios() {
        assertTrue(equipo.getProximosPartido().isEmpty());
    }

    @Test
    void modificarListaDeProximosPartidos() {
        equipo.setProximosPartido(new ArrayList<>());

        assertNotNull(equipo.getProximosPartido());
        assertTrue(equipo.getProximosPartido().isEmpty());
    }

    @Test
    void rechazarInscripcionATorneoNulo() {
        assertFalse(equipo.puedeInscribirse(null));
    }

    @Test
    void permitirInscripcionATorneoCompatible() {
        equipo.agregarJugador(crearInscripcion(jugador1));
        equipo.agregarJugador(crearInscripcion(jugador2));

        Torneo torneo = new Torneo(
                "Copa Séneca", deporte, "MAYORES",
                10, false, new Date()) {

            @Override
            public double calcularCostoInscripcion(Participante participante) {
                return 0;
            }

            @Override
            public void entregarPremios() {
            }
        };

        assertTrue(equipo.puedeInscribirse(torneo));
    }

    @Test
    void rechazarInscripcionATorneoConCategoriaDiferente() {
        equipo.agregarJugador(crearInscripcion(jugador1));
        equipo.agregarJugador(crearInscripcion(jugador2));

        Torneo torneo = new Torneo(
                "Copa Séneca", deporte, "SUB-17",
                10, false, new Date()) {

            @Override
            public double calcularCostoInscripcion(Participante participante) {
                return 0;
            }

            @Override
            public void entregarPremios() {
            }
        };

        assertFalse(equipo.puedeInscribirse(torneo));
    }
}