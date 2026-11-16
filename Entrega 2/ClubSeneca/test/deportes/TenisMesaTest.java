package deportes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import instalaciones.Instalacion;

class TenisMesaTest {

    private TenisMesa tenisMesa;

    @BeforeEach
    void setUp() {
        tenisMesa = new TenisMesa("TenisMesa");
        TenisMesa.instalaciones = new ArrayList<>();
    }

    @Test
    void crearTenisMesaCorrectamente() {
        assertNotNull(tenisMesa);
    }

    @Test
    void consultarMultiplicadorElo() {
        assertEquals(0.03, TenisMesa.multiplicadorElo, 0.001);
    }

    @Test
    void agregarInstalacionCorrectamente() {
        Instalacion instalacion = new Instalacion(
                "Mesa 1", "TenisMesa", 4, true,
                "08:00", "20:00", false);

        TenisMesa.agregarInstalacion(instalacion);

        assertEquals(1, TenisMesa.instalaciones.size());
        assertTrue(TenisMesa.instalaciones.contains(instalacion));
    }

    @Test
    void agregarVariasInstalaciones() {
        Instalacion primera = new Instalacion(
                "Mesa 1", "TenisMesa", 4, true,
                "08:00", "20:00", false);

        Instalacion segunda = new Instalacion(
                "Mesa 2", "TenisMesa", 4, true,
                "08:00", "20:00", false);

        TenisMesa.agregarInstalacion(primera);
        TenisMesa.agregarInstalacion(segunda);

        assertEquals(2, TenisMesa.instalaciones.size());
    }

    @Test
    void calcularEloCuandoGana() {
        double resultado = TenisMesa.calcularNuevoElo(
                1000, new String("SI"));

        assertEquals(1030, resultado, 0.001);
    }

    @Test
    void calcularEloCuandoPierde() {
        double resultado = TenisMesa.calcularNuevoElo(
                1000, "NO");

        assertEquals(970, resultado, 0.001);
    }

    @Test
    void calcularEloConResultadoEnMinusculas() {
        double resultado = TenisMesa.calcularNuevoElo(
                1000, "si");

        assertEquals(1030, resultado, 0.001);
    }

    @Test
    void calcularEloCuandoGanaConDiferentesValores() {
        double resultado = TenisMesa.calcularNuevoElo(
                2000, "SI");

        assertEquals(2060, resultado, 0.001);
    }

    @Test
    void calcularEloCuandoPierdeConDiferentesValores() {
        double resultado = TenisMesa.calcularNuevoElo(
                2000, "NO");

        assertEquals(1940, resultado, 0.001);
    }
}