package competencias;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deportes.Deporte;
import usuarios.Participante;
import usuarios.Usuario;

class EncuentroTorneoTest {

    private Participante participanteA;
    private Participante participanteB;
    private Participante participanteC;
    private Date fecha;
    private EncuentroTorneo encuentro;

    // Participante sencillo para las pruebas
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
                return false;
            }

            @Override
            public List<Usuario> getIntegrantes() {
                return Collections.emptyList();
            }
        };
    }

    @BeforeEach
    void setUp() throws Exception {
        participanteA = crearParticipante("Juan");
        participanteB = crearParticipante("Pedro");
        participanteC = crearParticipante("Carlos");

        fecha = new SimpleDateFormat("yyyy-MM-dd").parse("2026-01-15");

        encuentro = new EncuentroTorneo(
            participanteA,
            participanteB,
            fecha,
            1
        );
    }

    @Test
    void consultarGanadorInicialmente() {
        assertNull(encuentro.getGanador());
    }

    @Test
    void consultarPerdedorSinResultado() {
        assertNull(encuentro.getPerdedor());
    }

    @Test
    void registrarResultadoConParticipanteAComoGanador() {
        encuentro.registrarResultado("2-0", participanteA);

        assertSame(participanteA, encuentro.getGanador());
        assertSame(participanteB, encuentro.getPerdedor());
    }

    @Test
    void registrarResultadoConParticipanteBComoGanador() {
        encuentro.registrarResultado("0-2", participanteB);

        assertSame(participanteB, encuentro.getGanador());
        assertSame(participanteA, encuentro.getPerdedor());
    }

    @Test
    void rechazarGanadorQueNoParticipa() {
        encuentro.registrarResultado("2-0", participanteC);

        assertNull(encuentro.getGanador());
        assertNull(encuentro.getPerdedor());
    }

    @Test
    void rechazarGanadorNulo() {
        encuentro.registrarResultado("2-0", null);

        assertNull(encuentro.getGanador());
    }

    @Test
    void consultarDetalleEncuentroPendiente() {
        String detalle = encuentro.consultarDetalle();

        assertTrue(detalle.contains("Ronda 1"));
        assertTrue(detalle.contains("2026-01-15"));
        assertTrue(detalle.contains("Juan vs Pedro"));
        assertTrue(detalle.contains("Pendiente"));
    }

    @Test
    void consultarDetalleEncuentroFinalizado() {
        encuentro.registrarResultado("2-0", participanteA);

        String detalle = encuentro.consultarDetalle();

        assertTrue(detalle.contains("2-0"));
        assertTrue(detalle.contains("gana Juan"));
    }
}
