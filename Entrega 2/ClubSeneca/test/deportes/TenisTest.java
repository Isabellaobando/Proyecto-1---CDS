package deportes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import instalaciones.Instalacion;

class TenisTest {

    private Tenis tenis;

    @BeforeEach
    void setUp() {
        tenis = new Tenis("Tenis");
        Tenis.instalaciones = new ArrayList<>();
    }

    @Test
    void crearTenisCorrectamente() {
        assertNotNull(tenis);
    }

    @Test
    void consultarMultiplicadorElo() {
        assertEquals(0.03, Tenis.multiplicadorElo, 0.001);
    }

    @Test
    void agregarInstalacionCorrectamente() {
        Instalacion instalacion = new Instalacion(
                "Cancha 1", "Tenis", 4, true,
                "08:00", "20:00", false);

        Tenis.agregarInstalacion(instalacion);

        assertEquals(1, Tenis.instalaciones.size());
        assertTrue(Tenis.instalaciones.contains(instalacion));
    }

    @Test
    void agregarVariasInstalaciones() {
        Instalacion primera = new Instalacion(
                "Cancha 1", "Tenis", 4, true,
                "08:00", "20:00", false);

        Instalacion segunda = new Instalacion(
                "Cancha 2", "Tenis", 4, true,
                "08:00", "20:00", false);

        Tenis.agregarInstalacion(primera);
        Tenis.agregarInstalacion(segunda);

        assertEquals(2, Tenis.instalaciones.size());
    }

    @Test
    void calcularEloCuandoGana() {
        double resultado = Tenis.calcularNuevoElo(
                1000, new String("SI"));

        assertEquals(1030, resultado, 0.001);
    }

    @Test
    void calcularEloCuandoPierde() {
        double resultado = Tenis.calcularNuevoElo(
                1000, "NO");

        assertEquals(970, resultado, 0.001);
    }

    @Test
    void calcularEloConResultadoEnMinusculas() {
        double resultado = Tenis.calcularNuevoElo(
                1000, "si");

        assertEquals(1030, resultado, 0.001);
    }

    @Test
    void calcularEloCuandoGanaConDiferentesValores() {
        double resultado = Tenis.calcularNuevoElo(
                2000, "SI");

        assertEquals(2060, resultado, 0.001);
    }

    @Test
    void calcularEloCuandoPierdeConDiferentesValores() {
        double resultado = Tenis.calcularNuevoElo(
                2000, "NO");

        assertEquals(1940, resultado, 0.001);
    }
}