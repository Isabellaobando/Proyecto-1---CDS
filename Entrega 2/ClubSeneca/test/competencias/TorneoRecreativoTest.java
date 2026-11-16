package competencias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cafeteria.BonoDescuento;
import deportes.Deporte;
import deportes.Tenis;
import usuarios.Jugador;
import usuarios.Participante;
import usuarios.Usuario;

class TorneoRecreativoTest {

    private TorneoRecreativo torneo;
    private Deporte deporte;

    @BeforeEach
    void setUp() {
        deporte = new Tenis("Tenis");

        torneo = new TorneoRecreativo(
            "Copa Recreativa",
            deporte,
            "Mayores",
            10,
            false,
            new Date(),
            20.0
        );
    }

    @Test
    void constructorInicializaAtributos() {
        assertEquals("Copa Recreativa", torneo.getNombre());
        assertSame(deporte, torneo.getDeporte());
        assertEquals("Mayores", torneo.getCategoria());
        assertEquals(20.0, torneo.getPorcentajeBono());
        assertEquals(Torneo.POR_INICIAR, torneo.getEstado());
    }

    @Test
    void calcularCostoInscripcionEsCero() {
        Participante participante = crearParticipanteSinIntegrantes();

        assertEquals(0.0, torneo.calcularCostoInscripcion(participante));
    }

    @Test
    void inscribirParticipanteNoGeneraRecaudo() {
        Participante participante = crearParticipanteSinIntegrantes();

        assertTrue(torneo.inscribir(participante));
    }

    @Test
    void entregarPremiosSinPosicionesNoGeneraError() {
        assertDoesNotThrow(() -> torneo.entregarPremios());
    }

    @Test
    void entregarPremioAlJugadorGanador() {
        Jugador jugador = crearJugador();

        List<Participante> posiciones = new ArrayList<>();
        posiciones.add(crearParticipanteConJugador(jugador));

        torneo.iniciar();
        torneo.finalizar(posiciones);

        assertTrue(torneo.estaFinalizado());
        assertEquals(1, jugador.getBonosDisponibles().size());
    }

    @Test
    void entregarPremioGeneraBonoConPorcentajeCorrecto() {
        Jugador jugador = crearJugador();

        List<Participante> posiciones = new ArrayList<>();
        posiciones.add(crearParticipanteConJugador(jugador));

        torneo.iniciar();
        torneo.finalizar(posiciones);

        BonoDescuento bono = jugador.getBonosDisponibles().get(0);

        assertEquals(20.0, bono.getPorcentaje());
    }

    @Test
    void entregarPremioGeneraCodigoConNombreDelTorneo() {
        Jugador jugador = crearJugador();

        List<Participante> posiciones = new ArrayList<>();
        posiciones.add(crearParticipanteConJugador(jugador));

        torneo.iniciar();
        torneo.finalizar(posiciones);

        BonoDescuento bono = jugador.getBonosDisponibles().get(0);

        assertTrue(bono.getCodigo().startsWith("Copa Recreativa-"));
    }

    @Test
    void entregarPremioSoloAlGanador() {
        Jugador ganador = crearJugador();
        Jugador segundoLugar = crearJugador();

        List<Participante> posiciones = new ArrayList<>();
        posiciones.add(crearParticipanteConJugador(ganador));
        posiciones.add(crearParticipanteConJugador(segundoLugar));

        torneo.iniciar();
        torneo.finalizar(posiciones);

        assertEquals(1, ganador.getBonosDisponibles().size());
        assertEquals(0, segundoLugar.getBonosDisponibles().size());
    }

    private Participante crearParticipanteSinIntegrantes() {
        return new Participante() {
            @Override
            public String getNombre() {
                return "Participante";
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

    private Participante crearParticipanteConJugador(Jugador jugador) {
        return new Participante() {
            @Override
            public String getNombre() {
                return jugador.getNombre();
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
                List<Usuario> integrantes = new ArrayList<>();
                integrantes.add(jugador);
                return integrantes;
            }
        };
    }

    private Jugador crearJugador() {
        return new Jugador(
            1,
            "Juan",
            "juan@email.com",
            "juan",
            "1234",
            "3001234567",
            new Date(),
            "JUG001",
            new Date(),
            0,
            20,
            "Socio"
        );
    }
}