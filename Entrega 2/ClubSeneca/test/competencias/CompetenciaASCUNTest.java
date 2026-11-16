package competencias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompetenciaASCUNTest {

    private CompetenciaASCUN competencia;
    private Date fechaInicio;
    private Date fechaFin;

    @BeforeEach
    void setUp() {
        fechaInicio = new Date(126, 0, 10);
        fechaFin = new Date(126, 5, 10);

        competencia = new CompetenciaASCUN(
            "ASCUN 2026",
            fechaInicio,
            fechaFin,
            4,
            5
        );
    }

    @Test
    void constructorVacio() {
        CompetenciaASCUN nueva = new CompetenciaASCUN();

        assertNotNull(nueva);
        assertEquals(0, nueva.getNumGrupos());
        assertEquals(0, nueva.getEquiposPorGrupo());
        assertNull(nueva.getNombre());
    }

    @Test
    void constructorConParametros() {
        assertEquals("ASCUN 2026", competencia.getNombre());
        assertEquals(fechaInicio, competencia.getFechaInicio());
        assertEquals(fechaFin, competencia.getFechaFin());
        assertEquals(4, competencia.getNumGrupos());
        assertEquals(5, competencia.getEquiposPorGrupo());
    }

    @Test
    void consultarNumeroGrupos() {
        assertEquals(4, competencia.getNumGrupos());
    }

    @Test
    void modificarNumeroGrupos() {
        competencia.setNumGrupos(6);

        assertEquals(6, competencia.getNumGrupos());
    }

    @Test
    void consultarEquiposPorGrupo() {
        assertEquals(5, competencia.getEquiposPorGrupo());
    }

    @Test
    void modificarEquiposPorGrupo() {
        competencia.setEquiposPorGrupo(8);

        assertEquals(8, competencia.getEquiposPorGrupo());
    }

    @Test
    void modificarNombreHeredado() {
        competencia.setNombre("ASCUN Universitario");

        assertEquals("ASCUN Universitario", competencia.getNombre());
    }
}
