package deportes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import instalaciones.Instalacion;

class PadelTest {

    private Padel padel;

    @BeforeEach
    void setUp() {
        padel = new Padel("Padel");
        Padel.instalaciones = new ArrayList<>();
    }

    @Test
    void crearPadelCorrectamente() {
        assertNotNull(padel);
    }

    @Test
    void consultarMultiplicadorElo() {
        assertEquals(0.025, Padel.multiplicadorElo, 0.001);
    }

    @Test
    void agregarInstalacionCorrectamente() {
        Instalacion instalacion = new Instalacion(
                "Cancha 1", "Padel", 4, true,
                "08:00", "20:00", false);

        Padel.agregarInstalacion(instalacion);

        assertEquals(1, Padel.instalaciones.size());
        assertTrue(Padel.instalaciones.contains(instalacion));
    }

    @Test
    void agregarVariasInstalaciones() {
        Instalacion primera = new Instalacion(
                "Cancha 1", "Padel", 4, true,
                "08:00", "20:00", false);

        Instalacion segunda = new Instalacion(
                "Cancha 2", "Padel", 4, true,
                "08:00", "20:00", false);

        Padel.agregarInstalacion(primera);
        Padel.agregarInstalacion(segunda);

        assertEquals(2, Padel.instalaciones.size());
    }

    @Test
    void calcularEloCuandoGana() {
        double resultado = Padel.calcularNuevoElo(
                1000, new String("SI"));

        assertEquals(1025, resultado, 0.001);
    }

    @Test
    void calcularEloCuandoPierde() {
        double resultado = Padel.calcularNuevoElo(
                1000, "NO");

        assertEquals(975, resultado, 0.001);
    }

    @Test
    void calcularEloConResultadoEnMinusculas() {
        double resultado = Padel.calcularNuevoElo(
                1000, "si");

        assertEquals(1025, resultado, 0.001);
    }

    @Test
    void calcularEloConDiferentesValores() {
        double resultado = Padel.calcularNuevoElo(
                2000, "SI");

        assertEquals(2050, resultado, 0.001);
    }

    @Test
    void calcularEloCuandoPierdeConDiferentesValores() {
        double resultado = Padel.calcularNuevoElo(
                2000, "NO");

        assertEquals(1950, resultado, 0.001);
    }
}
