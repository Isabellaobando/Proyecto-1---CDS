package consola;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import logica.ClubSeneca;
import usuarios.Administrador;
import java.util.Date;

class ConsolaClubSenecaTest {
    @Test void menuPrincipalPermiteSalirSinExcepcion() {
        ClubSeneca club = new ClubSeneca();
        club.agregarEmpleado(new Administrador(1, "Admin", "admin@seneca.co", "admin", "clave",
                "3000000000", new Date(), 30, "ADM-1"));
        ConsolaClubSeneca consola = new ConsolaClubSeneca(new Scanner("admin\nclave\n13\nSALIR\n"), club);
        PrintStream anterior = System.out;
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(salida, true, StandardCharsets.UTF_8));
            assertDoesNotThrow(consola::ejecutar);
        } finally { System.setOut(anterior); }
        assertTrue(salida.toString(StandardCharsets.UTF_8).contains("CLUB DEPORTIVO"));
    }

    @Test void permiteInyectarEstadoDelClub() {
        ClubSeneca club = new ClubSeneca();
        club.agregarEmpleado(new Administrador(1, "Admin", "admin@seneca.co", "admin", "clave",
                "3000000000", new Date(), 30, "ADM-1"));
        ConsolaClubSeneca consola = new ConsolaClubSeneca(new Scanner("admin\nclave\n13\nSALIR\n"), club);
        assertSame(club, consola.getClub());
    }
}
