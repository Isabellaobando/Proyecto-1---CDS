
package instalacion;

import static org.junit.jupiter.api.Assertions.*;
import instalaciones.Instalacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InstalacionTest {

    private Instalacion instalacion;

    @BeforeEach
    void setUp() {
        instalacion = new Instalacion(
                "Cancha 1",
                "Padel",
                4,
                true,
                "08:00",
                "20:00",
                false
        );
    }

    @Test
    void crearInstalacionConConstructorCompleto() {
        assertEquals("Cancha 1", instalacion.getNombre());
        assertEquals("Padel", instalacion.getTipoDeporte());
        assertEquals(4, instalacion.getCapacidad());
        assertTrue(instalacion.isDisponible());
        assertEquals("08:00", instalacion.getHoraApertura());
        assertEquals("20:00", instalacion.getHoraCierre());
        assertFalse(instalacion.isRestriccionBebidaCaliente());
    }

    @Test
    void crearInstalacionConConstructorVacio() {
        Instalacion nueva = new Instalacion();

        assertNull(nueva.getNombre());
        assertNull(nueva.getTipoDeporte());
        assertEquals(0, nueva.getCapacidad());
        assertFalse(nueva.isDisponible());
        assertNull(nueva.getHoraApertura());
        assertNull(nueva.getHoraCierre());
        assertFalse(nueva.isRestriccionBebidaCaliente());
    }

    @Test
    void cambiarNombre() {
        instalacion.setNombre("Cancha Principal");

        assertEquals("Cancha Principal", instalacion.getNombre());
    }

    @Test
    void cambiarTipoDeporte() {
        instalacion.setTipoDeporte("Tenis");

        assertEquals("Tenis", instalacion.getTipoDeporte());
    }

    @Test
    void cambiarCapacidad() {
        instalacion.setCapacidad(8);

        assertEquals(8, instalacion.getCapacidad());
    }

    @Test
    void cambiarDisponibilidad() {
        instalacion.setDisponible(false);

        assertFalse(instalacion.isDisponible());
    }

    @Test
    void cambiarHoraApertura() {
        instalacion.setHoraApertura("07:00");

        assertEquals("07:00", instalacion.getHoraApertura());
    }

    @Test
    void cambiarHoraCierre() {
        instalacion.setHoraCierre("22:00");

        assertEquals("22:00", instalacion.getHoraCierre());
    }

    @Test
    void cambiarRestriccionBebidaCaliente() {
        instalacion.setRestriccionBebidaCaliente(true);

        assertTrue(instalacion.isRestriccionBebidaCaliente());
    }

    @Test
    void modificarTodosLosAtributos() {
        instalacion.setNombre("Cancha Central");
        instalacion.setTipoDeporte("Tenis");
        instalacion.setCapacidad(6);
        instalacion.setDisponible(false);
        instalacion.setHoraApertura("06:00");
        instalacion.setHoraCierre("21:00");
        instalacion.setRestriccionBebidaCaliente(true);

        assertEquals("Cancha Central", instalacion.getNombre());
        assertEquals("Tenis", instalacion.getTipoDeporte());
        assertEquals(6, instalacion.getCapacidad());
        assertFalse(instalacion.isDisponible());
        assertEquals("06:00", instalacion.getHoraApertura());
        assertEquals("21:00", instalacion.getHoraCierre());
        assertTrue(instalacion.isRestriccionBebidaCaliente());
    }
}

