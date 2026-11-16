package competencias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deportes.Deporte;
import deportes.Tenis;
import usuarios.Participante;
import usuarios.Usuario;

class TorneoCompetitivoTest {

    private TorneoCompetitivo torneo;
    private Deporte deporte;

    @BeforeEach
    void setUp() {
        deporte = new Tenis("Tenis");

        torneo = new TorneoCompetitivo(
            "Copa Séneca",
            deporte,
            "Mayores",
            10,
            false,
            new Date(),
            50000
        );
    }

    @Test
    void constructorInicializaAtributos() {
        assertEquals("Copa Séneca", torneo.getNombre());
        assertSame(deporte, torneo.getDeporte());
        assertEquals("Mayores", torneo.getCategoria());
        assertEquals(50000, torneo.getTarifaInscripcion());
        assertEquals(0, torneo.getTotalRecaudado());
    }

    @Test
    void calcularCostoParaParticipanteNoEmpleado() {
        Participante participante = crearParticipanteNoEmpleado("Juan");

        assertEquals(
            50000,
            torneo.calcularCostoInscripcion(participante)
        );
    }

    @Test
    void calcularCostoParaEmpleado() {
        Participante empleado = crearParticipanteEmpleado("Carlos");

        assertEquals(0, torneo.calcularCostoInscripcion(empleado));
    }

    @Test
    void registrarPagoPositivo() {
        torneo.registrarPago(50000);

        assertEquals(50000, torneo.getTotalRecaudado());
    }

    @Test
    void registrarVariosPagos() {
        torneo.registrarPago(50000);
        torneo.registrarPago(30000);

        assertEquals(80000, torneo.getTotalRecaudado());
    }

    @Test
    void registrarPagoNegativo() {
        torneo.registrarPago(50000);
        torneo.registrarPago(-20000);

        assertEquals(30000, torneo.getTotalRecaudado());
    }

    @Test
    void registrarPagoCero() {
        torneo.registrarPago(0);

        assertEquals(0, torneo.getTotalRecaudado());
    }

    @Test
    void inscribirParticipanteNoEmpleado() {
        Participante participante = crearParticipanteNoEmpleado("Juan");

        assertTrue(torneo.inscribir(participante));
        assertEquals(50000, torneo.getTotalRecaudado());
        assertEquals(1, torneo.getInscritos().size());
    }

    @Test
    void inscribirParticipanteEmpleado() {
        Participante empleado = crearParticipanteEmpleado("Carlos");

        assertTrue(torneo.inscribir(empleado));
        assertEquals(0, torneo.getTotalRecaudado());
        assertEquals(1, torneo.getInscritos().size());
    }

    @Test
    void noCobrarDosVecesPorInscripcionDuplicada() {
        Participante participante = crearParticipanteNoEmpleado("Juan");

        assertTrue(torneo.inscribir(participante));
        assertFalse(torneo.inscribir(participante));

        assertEquals(50000, torneo.getTotalRecaudado());
        assertEquals(1, torneo.getInscritos().size());
    }

    @Test
    void desinscribirParticipanteDevuelveElPago() {
        Participante participante = crearParticipanteNoEmpleado("Juan");

        torneo.inscribir(participante);
        assertTrue(torneo.desinscribir(participante));

        assertEquals(0, torneo.getTotalRecaudado());
        assertTrue(torneo.getInscritos().isEmpty());
    }

    @Test
    void desinscribirEmpleadoNoModificaRecaudo() {
        Participante empleado = crearParticipanteEmpleado("Carlos");

        torneo.inscribir(empleado);
        assertTrue(torneo.desinscribir(empleado));

        assertEquals(0, torneo.getTotalRecaudado());
    }

    @Test
    void noDesinscribirParticipanteNoInscrito() {
        Participante participante = crearParticipanteNoEmpleado("Juan");

        assertFalse(torneo.desinscribir(participante));
        assertEquals(0, torneo.getTotalRecaudado());
    }

    @Test
    void noEntregarPremiosSiNoHayParticipantesEnPosiciones() {
        assertDoesNotThrow(() -> torneo.entregarPremios());
    }

    @Test
    void entregarPremiosAParticipanteNoEmpleado() {
        Participante participante = crearParticipanteNoEmpleado("Juan");

        List<Participante> posiciones = new ArrayList<>();
        posiciones.add(participante);

        torneo.iniciar();
        torneo.finalizar(posiciones);

        assertTrue(torneo.estaFinalizado());
    }

    @Test
    void entregarPremiosCuandoTodosSonEmpleados() {
        Participante empleado = crearParticipanteEmpleado("Carlos");

        List<Participante> posiciones = new ArrayList<>();
        posiciones.add(empleado);

        torneo.iniciar();

        assertDoesNotThrow(() -> torneo.finalizar(posiciones));
        assertTrue(torneo.estaFinalizado());
    }

    private Participante crearParticipanteNoEmpleado(String nombre) {
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
                return Collections.<Usuario>singletonList(null);
            }
        };
    }

    private Participante crearParticipanteEmpleado(String nombre) {
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
}
