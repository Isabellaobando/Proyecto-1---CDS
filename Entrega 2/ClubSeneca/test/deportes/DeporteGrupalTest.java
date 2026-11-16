package deportes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import equipos.Equipo;

class DeporteGrupalTest {

    private DeporteGrupal deporte;
    private Equipo equipo1;
    private Equipo equipo2;
    private Equipo equipo3;

    @BeforeEach
    void setUp() {
        deporte = new Baloncesto("Baloncesto", 5, 12);

        equipo1 = new Equipo("SUB-13", "Halcones", true, deporte);
        equipo2 = new Equipo("SUB-15", "Tigres", true, deporte);
        equipo3 = new Equipo("SUB-13", "Leones", true, deporte);
    }

    @Test
    void consultarMinimoJugadores() {
        assertEquals(5, deporte.getMinJugadores());
    }

    @Test
    void consultarMaximoJugadores() {
        assertEquals(12, deporte.getMaxJugadores());
    }

    @Test
    void crearEquipoCorrectamente() {
        deporte.crearEquipo(equipo1);

        List<Equipo> equipos =
                deporte.consultarEquiposPorCategoria("SUB-13");

        assertEquals(1, equipos.size());
        assertEquals("Halcones", equipos.get(0).getNombre());
    }

    @Test
    void evitarCrearEquipoDuplicado() {
        deporte.crearEquipo(equipo1);
        deporte.crearEquipo(equipo1);

        List<Equipo> equipos =
                deporte.consultarEquiposPorCategoria("SUB-13");

        assertEquals(1, equipos.size());
    }

    @Test
    void permitirEquiposConMismoNombreEnCategoriasDiferentes() {
        Equipo equipo = new Equipo(
                "SUB-15", "Halcones", true, deporte);

        deporte.crearEquipo(equipo1);
        deporte.crearEquipo(equipo);

        assertEquals(1,
                deporte.consultarEquiposPorCategoria("SUB-13").size());

        assertEquals(1,
                deporte.consultarEquiposPorCategoria("SUB-15").size());
    }

    @Test
    void consultarEquiposPorCategoria() {
        deporte.crearEquipo(equipo1);
        deporte.crearEquipo(equipo2);
        deporte.crearEquipo(equipo3);

        List<Equipo> equipos =
                deporte.consultarEquiposPorCategoria("SUB-13");

        assertEquals(2, equipos.size());
    }

    @Test
    void consultarCategoriaSinEquipos() {
        deporte.crearEquipo(equipo1);

        List<Equipo> equipos =
                deporte.consultarEquiposPorCategoria("MAYORES");

        assertTrue(equipos.isEmpty());
    }

    @Test
    void consultarCategoriaNula() {
        deporte.crearEquipo(equipo1);

        assertTrue(
                deporte.consultarEquiposPorCategoria(null).isEmpty());
    }
}
