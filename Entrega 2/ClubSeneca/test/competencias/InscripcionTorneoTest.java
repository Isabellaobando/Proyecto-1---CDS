package competencias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deportes.Deporte;
import usuarios.Participante;
import usuarios.Usuario;

class InscripcionTorneoTest {

    private Participante participante;
    private Date fecha;
    private InscripcionTorneo inscripcion;

    @BeforeEach
    void setUp() {
        participante = new Participante() {

            @Override
            public String getNombre() {
                return "Juan";
            }

            @Override
            public double getRanking(Deporte deporte) {
                return 100;
            }

            @Override
            public boolean puedeInscribirse(Torneo torneo) {
                return true;
            }

            @Override
            public List<Usuario> getIntegrantes() {
                return Collections.emptyList();
            }
        };

        fecha = new Date();

        inscripcion = new InscripcionTorneo(
                participante, fecha, false, 50000
        );
    }

    @Test
    void consultarParticipante() {
        assertSame(participante, inscripcion.getParticipante());
    }

    @Test
    void consultarFechaInscripcion() {
        assertEquals(fecha, inscripcion.getFechaInscripcion());
    }

    @Test
    void consultarEstadoPreferencialInicial() {
        assertFalse(inscripcion.esPreferencial());
    }

    @Test
    void modificarEstadoPreferencial() {
        inscripcion.setPreferencial(true);

        assertTrue(inscripcion.esPreferencial());
    }

    @Test
    void modificarEstadoPreferencialANoPreferencial() {
        inscripcion.setPreferencial(true);
        inscripcion.setPreferencial(false);

        assertFalse(inscripcion.esPreferencial());
    }

    @Test
    void consultarMontoPagado() {
        assertEquals(50000, inscripcion.getMontoPagado(), 0.001);
    }

    @Test
    void conservarDatosDelConstructor() {
        assertSame(participante, inscripcion.getParticipante());
        assertEquals(fecha, inscripcion.getFechaInscripcion());
        assertFalse(inscripcion.esPreferencial());
        assertEquals(50000, inscripcion.getMontoPagado(), 0.001);
    }

    @Test
    void permitirMontoPagadoCero() {
        InscripcionTorneo nueva = new InscripcionTorneo(
                participante, fecha, false, 0
        );

        assertEquals(0, nueva.getMontoPagado(), 0.001);
    }
}