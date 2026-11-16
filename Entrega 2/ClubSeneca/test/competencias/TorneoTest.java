package competencias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deportes.Deporte;
import deportes.Tenis;
import equipos.Equipo;
import usuarios.Participante;
import usuarios.Usuario;

class TorneoTest {

    private TorneoPrueba torneo;
    private Deporte deporte;

    @BeforeEach
    void setUp() {
        deporte = new Tenis("Tenis");
        torneo = new TorneoPrueba(
            "Copa Séneca", deporte, "Mayores", 10, false, new Date()
        );
    }

    @Test
    void constructorInicializaAtributos() {
        assertEquals("Copa Séneca", torneo.getNombre());
        assertSame(deporte, torneo.getDeporte());
        assertEquals("Mayores", torneo.getCategoria());
        assertEquals(new Date(torneo.getFechaInicio().getTime()),
                     torneo.getFechaInicio());
        assertEquals(Torneo.POR_INICIAR, torneo.getEstado());
        assertFalse(torneo.isEsDobles());
    }

    @Test
    void constructorInicializaListas() {
        assertNotNull(torneo.getInscritos());
        assertTrue(torneo.getInscritos().isEmpty());
    }

    @Test
    void calcularCuposPreferenciales() {
        assertEquals(2, torneo.getCuposPreferenciales());
    }

    @Test
    void calcularCuposPreferencialesConUnCupo() {
        TorneoPrueba otro = new TorneoPrueba(
            "Torneo pequeño", deporte, "Mayores", 1, false, new Date()
        );

        assertEquals(1, otro.getCuposPreferenciales());
    }

    @Test
    void consultarCuposDisponiblesIniciales() {
        assertEquals(10, torneo.getCuposDisponibles());
        assertTrue(torneo.hayCupos());
    }

    @Test
    void noHayCuposCuandoSeCompletaCapacidad() {
        TorneoPrueba lleno = new TorneoPrueba(
            "Torneo", deporte, "Mayores", 0, false, new Date()
        );

        assertEquals(0, lleno.getCuposDisponibles());
        assertFalse(lleno.hayCupos());
    }

    @Test
    void iniciarTorneo() {
        torneo.iniciar();

        assertEquals(Torneo.EN_CURSO, torneo.getEstado());
        assertTrue(torneo.estaEnCurso());
    }

    @Test
    void noCambiarEstadoAlIniciarUnTorneoEnCurso() {
        torneo.iniciar();
        torneo.iniciar();

        assertEquals(Torneo.EN_CURSO, torneo.getEstado());
    }

    @Test
    void finalizarTorneo() {
        torneo.iniciar();

        List<Participante> posiciones = new ArrayList<>();
        posiciones.add(crearParticipante("Juan"));

        torneo.finalizar(posiciones);

        assertTrue(torneo.estaFinalizado());
        assertEquals(Torneo.FINALIZADO, torneo.getEstado());
        assertEquals(1, torneo.getPosiciones().size());
        assertTrue(torneo.premiosEntregados);
    }

    @Test
    void noFinalizarTorneoQueNoHaIniciado() {
        torneo.finalizar(new ArrayList<>());

        assertEquals(Torneo.POR_INICIAR, torneo.getEstado());
    }

    @Test
    void noFinalizarConPosicionesNulas() {
        torneo.iniciar();
        torneo.finalizar(null);

        assertEquals(Torneo.EN_CURSO, torneo.getEstado());
    }

    @Test
    void liberarCuposPreferenciales() {
        TorneoPrueba torneoPequeno = new TorneoPrueba(
            "Torneo pequeño", deporte, "Mayores", 5, false, new Date()
        );

        assertTrue(torneoPequeno.inscribir(crearParticipante("Juan")));
        assertTrue(torneoPequeno.inscribir(crearParticipante("Pedro")));
        assertTrue(torneoPequeno.inscribir(crearParticipante("Carlos")));
        assertTrue(torneoPequeno.inscribir(crearParticipante("Luis")));

        // El quinto cupo está reservado para participantes preferenciales.
        assertFalse(torneoPequeno.inscribir(crearParticipante("Andrés")));

        torneoPequeno.liberarCuposPreferenciales();

        // Después de liberar los cupos, permite la inscripción.
        assertTrue(torneoPequeno.inscribir(crearParticipante("Andrés")));
    }

    @Test
    void consultarHistorial() {
        String historial = torneo.consultarHistorial();

        assertTrue(historial.contains("Copa Séneca"));
        assertTrue(historial.contains("Tenis"));
        assertTrue(historial.contains("Mayores"));
        assertTrue(historial.contains("Por iniciar"));
    }

    @Test
    void consultarEstadoInicial() {
        assertFalse(torneo.estaEnCurso());
        assertFalse(torneo.estaFinalizado());
    }

    @Test
    void constructorNoPermiteDoblesEnDeporteGrupal() {
        TorneoPrueba grupal = new TorneoPrueba(
            "Torneo grupal",
            new deportes.Voleibol("Voleibol", 6, 12),
            "Mayores", 10, true, new Date()
        );

        assertFalse(grupal.isEsDobles());
    }

    private Participante crearParticipante(String nombre) {
        return new Participante() {
            @Override
            public String getNombre() {
                return nombre;
            }

            @Override
            public double getRanking(Deporte deporte) {
                return 0;
            }

            @Override
            public boolean puedeInscribirse(Torneo torneo) {
                return true;
            }

            @Override
            public List<Usuario> getIntegrantes() {
                return new ArrayList<>();
            }
        };
    }

    private static class TorneoPrueba extends Torneo {

        boolean premiosEntregados = false;

        TorneoPrueba(String nombre, Deporte deporte, String categoria,
                     int cupos, boolean esDobles, Date fechaInicio) {
            super(nombre, deporte, categoria, cupos, esDobles, fechaInicio);
        }

        @Override
        public double calcularCostoInscripcion(Participante participante) {
            return 50000;
        }

        @Override
        public void entregarPremios() {
            premiosEntregados = true;
        }

        List<Participante> getPosiciones() {
            return posiciones;
        }

    }
}
