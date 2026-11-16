package logica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import instalaciones.Instalacion;
import entrenamientos.SesionEntrenamiento;

class ClubSenecaTest {
    private ClubSeneca club;

    @BeforeEach
    void setUp() { club = new ClubSeneca(); }

    @Test
    void agregaInstalacionValida() {
        Instalacion instalacion = crearInstalacion("Cancha 1");
        assertTrue(club.agregarInstalacion(instalacion));
        assertEquals(1, club.getInstalaciones().size());
        assertSame(instalacion, club.consultarInstalacion("Cancha 1"));
    }

    @Test
    void rechazaInstalacionDuplicadaSinDistinguirMayusculas() {
        assertTrue(club.agregarInstalacion(crearInstalacion("Cancha 1")));
        assertFalse(club.agregarInstalacion(crearInstalacion("CANCHA 1")));
        assertEquals(1, club.getInstalaciones().size());
    }

    @Test
    void rechazaInstalacionNula() {
        assertFalse(club.agregarInstalacion(null));
        assertTrue(club.getInstalaciones().isEmpty());
    }

    @Test
    void agregaSesionSoloConInstalacionRegistrada() {
        Instalacion propia = crearInstalacion("Cancha 1");
        Instalacion externa = crearInstalacion("Cancha externa");
        club.agregarInstalacion(propia);
        assertTrue(club.agregarSesionEntrenamiento(new SesionEntrenamiento("2026-11-16", "09:00", 60, propia)));
        assertFalse(club.agregarSesionEntrenamiento(new SesionEntrenamiento("2026-11-16", "10:00", 60, externa)));
        assertEquals(1, club.getSesionesEntrenamiento().size());
    }

    @Test
    void noEliminaInstalacionConSesionAsociada() {
        Instalacion instalacion = crearInstalacion("Cancha 1");
        club.agregarInstalacion(instalacion);
        club.agregarSesionEntrenamiento(new SesionEntrenamiento("2026-11-16", "09:00", 60, instalacion));
        assertFalse(club.eliminarInstalacion("Cancha 1"));
        assertNotNull(club.consultarInstalacion("Cancha 1"));
    }

    private Instalacion crearInstalacion(String nombre) {
        return new Instalacion(nombre, "Tenis", 4, true, "08:00", "20:00", false);
    }
}
