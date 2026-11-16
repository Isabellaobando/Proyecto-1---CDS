package competencias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deportes.Baloncesto;
import equipos.Equipo;

class CompetenciaTest {

    private Competencia competencia;
    private Equipo equipo;

    @BeforeEach
    void setUp() {
        competencia = new Competencia(
                "Torneo Interclubes",
                new Date(126, 0, 1),
                new Date(126, 11, 31)
        );

        Baloncesto deporte = new Baloncesto("Baloncesto", 2, 5);

        equipo = new Equipo(
                "MAYORES",
                "Los Halcones",
                true,
                deporte
        );
    }

    @Test
    void crearCompetenciaConConstructorVacio() {
        Competencia nueva = new Competencia();

        assertNull(nueva.getNombre());
        assertNull(nueva.getFechaInicio());
        assertNull(nueva.getFechaFin());
        assertTrue(nueva.consultarEquiposInscritos().isEmpty());
    }

    @Test
    void crearCompetenciaConConstructorCompleto() {
        assertEquals("Torneo Interclubes", competencia.getNombre());
        assertNotNull(competencia.getFechaInicio());
        assertNotNull(competencia.getFechaFin());
    }

    @Test
    void modificarNombre() {
        competencia.setNombre("Torneo Nacional");

        assertEquals("Torneo Nacional", competencia.getNombre());
    }

    @Test
    void modificarFechaInicio() {
        Date nuevaFecha = new Date(127, 0, 1);

        competencia.setFechaInicio(nuevaFecha);

        assertEquals(nuevaFecha, competencia.getFechaInicio());
    }

    @Test
    void modificarFechaFin() {
        Date nuevaFecha = new Date(127, 11, 31);

        competencia.setFechaFin(nuevaFecha);

        assertEquals(nuevaFecha, competencia.getFechaFin());
    }

    @Test
    void inscribirEquipoCorrectamente() {
        assertTrue(competencia.inscribirEquipo(equipo));

        assertEquals(1, competencia.consultarEquiposInscritos().size());
        assertTrue(competencia.consultarEquiposInscritos().contains(equipo));
    }

    @Test
    void rechazarInscripcionDeEquipoNulo() {
        assertFalse(competencia.inscribirEquipo(null));

        assertTrue(competencia.consultarEquiposInscritos().isEmpty());
    }

    @Test
    void rechazarInscripcionDeEquipoDuplicado() {
        assertTrue(competencia.inscribirEquipo(equipo));

        assertFalse(competencia.inscribirEquipo(equipo));

        assertEquals(1, competencia.consultarEquiposInscritos().size());
    }

    @Test
    void consultarEquiposDevuelveUnaCopia() {
        competencia.inscribirEquipo(equipo);

        List<Equipo> equipos = competencia.consultarEquiposInscritos();

        equipos.clear();

        assertEquals(1, competencia.consultarEquiposInscritos().size());
    }
}