package entrenamientos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SesionEntrenamientoTest {
    @Test
    void noAceptaDuracionCeroONegativa() {
        SesionEntrenamiento sesion = new SesionEntrenamiento();
        sesion.setDuracionMin(60);
        sesion.setDuracionMin(0);
        assertEquals(60, sesion.getDuracionMin());
        sesion.setDuracionMin(-15);
        assertEquals(60, sesion.getDuracionMin());
    }

    @Test
    void porcentajeAsistenciaEsCeroSinConvocados() {
        SesionEntrenamiento sesion = new SesionEntrenamiento();
        assertEquals(0.0, sesion.calcularPorcentajeAsistencia());
    }
}
