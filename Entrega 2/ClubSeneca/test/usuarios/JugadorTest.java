package usuarios;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deportes.Padel;
import deportes.DeporteIndividual;

class JugadorTest {

    private Jugador jugador;
    private DeporteIndividual padel;

    @BeforeEach
    void setUp() {
        jugador = new Jugador(
            1,
            "Laura Gómez",
            "laura@gmail.com",
            "laura01",
            "clave123",
            "3001234567",
            crearFecha(2005, 5, 15),
            "JUG001",
            crearFecha(2024, 1, 10),
            100,
            21,
            Jugador.TIPO_SOCIO
        );

        padel = new Padel("Padel");
    }

    @Test
    void consultaCodigoSocio() {
        assertEquals("JUG001", jugador.getCodigoSocio());
    }

    @Test
    void consultaTipoJugador() {
        assertEquals(Jugador.TIPO_SOCIO, jugador.getTipo());
    }

    @Test
    void consultaFechaAfiliacion() {
        assertEquals(
            crearFecha(2024, 1, 10),
            jugador.getFechaAfiliacion()
        );
    }

    @Test
    void consultaPuntosLealtad() {
        assertEquals(100, jugador.getPuntosLealtad());
    }

    @Test
    void inscribeJugadorEnDeporteIndividual() {
        assertTrue(jugador.inscribirseADeporte(padel, "PRINCIPIANTE"));

        assertEquals(1, jugador.consultarDeportes().size());
        assertSame(padel, jugador.consultarDeportes().get(0));
    }

    @Test
    void rechazaInscripcionConNivelInvalido() {
        assertFalse(jugador.inscribirseADeporte(padel, "EXPERTO"));

        assertTrue(jugador.consultarDeportes().isEmpty());
    }

    @Test
    void rechazaInscripcionConDeporteNulo() {
        assertFalse(jugador.inscribirseADeporte(null, "PRINCIPIANTE"));

        assertTrue(jugador.consultarDeportes().isEmpty());
    }

    @Test
    void rechazaInscripcionConNivelNulo() {
        assertFalse(jugador.inscribirseADeporte(padel, null));

        assertTrue(jugador.consultarDeportes().isEmpty());
    }

    @Test
    void rechazaInscripcionDuplicada() {
        assertTrue(jugador.inscribirseADeporte(padel, "PRINCIPIANTE"));

        assertFalse(jugador.inscribirseADeporte(padel, "AVANZADO"));

        assertEquals(1, jugador.consultarDeportes().size());
    }

    @Test
    void rankingInicialDeDeporteEsCero() {
        assertTrue(jugador.inscribirseADeporte(padel, "INTERMEDIO"));

        assertEquals(0.0, jugador.getRanking(padel), 0.001);
    }

    @Test
    void rankingDeDeporteNoInscritoEsCero() {
        assertEquals(0.0, jugador.getRanking(padel), 0.001);
    }

    @Test
    void jugadorApareceComoSuPropioIntegrante() {
        List<Usuario> integrantes = jugador.getIntegrantes();

        assertEquals(1, integrantes.size());
        assertSame(jugador, integrantes.get(0));
    }

    @Test
    void consultaEquiposInicialmenteVacios() {
        assertTrue(jugador.consultarEquipos().isEmpty());
    }

    private Date crearFecha(int anio, int mes, int dia) {
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio, mes - 1, dia, 12, 0, 0);
        calendario.set(Calendar.MILLISECOND, 0);

        return calendario.getTime();
    }
}