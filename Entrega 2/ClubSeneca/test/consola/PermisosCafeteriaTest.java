package consola;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import logica.ClubSeneca;
import usuarios.Administrador;
import usuarios.EmpleadoClub;
import usuarios.Entrenador;
import usuarios.Fisioterapeuta;
import usuarios.Jugador;
import usuarios.Usuario;

class PermisosCafeteriaTest {

    private ConsolaClubSeneca crearConsola(Usuario usuario) throws Exception {
        ConsolaClubSeneca consola = new ConsolaClubSeneca(new Scanner("0\n"), new ClubSeneca());
        Field campo = ConsolaClubSeneca.class.getDeclaredField("usuarioActual");
        campo.setAccessible(true);
        campo.set(consola, usuario);
        return consola;
    }

    private boolean permiso(ConsolaClubSeneca consola, int opcion) throws Exception {
        Method metodo = ConsolaClubSeneca.class.getDeclaredMethod("tienePermisoCafeteria", int.class);
        metodo.setAccessible(true);
        return (boolean) metodo.invoke(consola, opcion);
    }

    private Administrador admin() {
        return new Administrador(1, "Admin", "admin@seneca.co", "admin", "clave",
                "3000000000", new Date(), 35, "ADM-1");
    }

    private Jugador jugador() {
        return new Jugador(2, "Jugador", "jugador@seneca.co", "jugador", "clave",
                "3000000001", new Date(), "SOC-2", new Date(), 0, 20, Jugador.TIPO_SOCIO);
    }

    @Test
    void administradorPuedeTodasLasOpcionesDeCafeteria() throws Exception {
        ConsolaClubSeneca consola = crearConsola(admin());
        for (int opcion = 1; opcion <= 8; opcion++) assertTrue(permiso(consola, opcion));
    }

    @Test
    void empleadoGeneralPuedeConsultarYRegistrarVentasPeroNoAdministrar() throws Exception {
        EmpleadoClub empleado = new EmpleadoClub(3, "Empleado", "empleado@seneca.co", "empleado", "clave",
                "3000000002", new Date(), 28, "EMP-3");
        ConsolaClubSeneca consola = crearConsola(empleado);
        assertTrue(permiso(consola, 2));
        assertTrue(permiso(consola, 4));
        assertTrue(permiso(consola, 7));
        assertFalse(permiso(consola, 1));
        assertFalse(permiso(consola, 3));
        assertFalse(permiso(consola, 5));
        assertFalse(permiso(consola, 6));
        assertFalse(permiso(consola, 8));
    }

    @Test
    void jugadorPuedeConsultarYRegistrarVentasPeroNoAdministrar() throws Exception {
        ConsolaClubSeneca consola = crearConsola(jugador());
        assertTrue(permiso(consola, 2));
        assertTrue(permiso(consola, 4));
        assertTrue(permiso(consola, 7));
        assertFalse(permiso(consola, 1));
        assertFalse(permiso(consola, 3));
        assertFalse(permiso(consola, 5));
        assertFalse(permiso(consola, 6));
        assertFalse(permiso(consola, 8));
    }

    @Test
    void entrenadorYFisioterapeutaSoloPuedenConsultarLocalesYProductos() throws Exception {
        Entrenador entrenador = new Entrenador(4, "Entrenador", "entrenador@seneca.co", "coach", "clave",
                "3000000003", new Date(), 30, "ENT-4", null);
        Fisioterapeuta fisio = new Fisioterapeuta(5, "Fisio", "fisio@seneca.co", "fisio", "clave",
                "3000000004", new Date(), 30, "FIS-5", new java.util.ArrayList<>());
        for (Usuario usuario : new Usuario[] { entrenador, fisio }) {
            ConsolaClubSeneca consola = crearConsola(usuario);
            assertTrue(permiso(consola, 2));
            assertTrue(permiso(consola, 4));
            assertFalse(permiso(consola, 1));
            assertFalse(permiso(consola, 3));
            assertFalse(permiso(consola, 5));
            assertFalse(permiso(consola, 6));
            assertFalse(permiso(consola, 7));
            assertFalse(permiso(consola, 8));
        }
    }
}
