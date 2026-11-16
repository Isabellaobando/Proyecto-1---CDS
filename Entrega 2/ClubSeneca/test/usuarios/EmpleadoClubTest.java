package usuarios;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

class EmpleadoClubTest {

    private EmpleadoClub empleado;

    @BeforeEach
    void setUp() {
        empleado = new EmpleadoClub(
            1,
            "Carlos Pérez",
            "carlos@gmail.com",
            "carlos01",
            "clave123",
            "3001234567",
            new Date(90, 0, 1),
            35,
            "EMP001"
        );
    }

    @Test
    void consultaNumeroEmpleado() {
        assertEquals("EMP001", empleado.getNoEmpleado());
    }

    @Test
    void actualizaNumeroEmpleado() {
        empleado.setNoEmpleado("EMP002");

        assertEquals("EMP002", empleado.getNoEmpleado());
    }

    @Test
    void agregaTurnoCorrectamente() {
        Date fecha = crearFecha(2026, 11, 16, 8, 0);

        empleado.agregarTurno(fecha);

        assertTrue(empleado.estaDeTurno(fecha));
    }

    @Test
    void reconoceTurnoAunqueCambieLaHora() {
        empleado.agregarTurno(
            crearFecha(2026, 11, 16, 8, 0)
        );

        Date otraHora = crearFecha(2026, 11, 16, 15, 30);

        assertTrue(empleado.estaDeTurno(otraHora));
    }

    @Test
    void rechazaFechaSinTurno() {
        empleado.agregarTurno(
            crearFecha(2026, 11, 16, 8, 0)
        );

        Date otroDia = crearFecha(2026, 11, 17, 8, 0);

        assertFalse(empleado.estaDeTurno(otroDia));
    }

    @Test
    void rankingDeEmpleadoEsCero() {
        assertEquals(0.0, empleado.getRanking(null), 0.001);
    }

    @Test
    void empleadoApareceComoSuPropioIntegrante() {
        List<Usuario> integrantes = empleado.getIntegrantes();

        assertEquals(1, integrantes.size());
        assertSame(empleado, integrantes.get(0));
    }

    private Date crearFecha(int anio, int mes, int dia, int hora, int minuto) {
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio, mes - 1, dia, hora, minuto, 0);
        calendario.set(Calendar.MILLISECOND, 0);

        return calendario.getTime();
    }
}