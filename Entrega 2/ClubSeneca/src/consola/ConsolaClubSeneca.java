

package consola;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.List;

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import persistencia.PersistenciaClubSeneca;

import logica.ClubSeneca;

import usuarios.EmpleadoClub;
import usuarios.Jugador;
import usuarios.Usuario;
import usuarios.Administrador;
import usuarios.Entrenador;
import usuarios.Fisioterapeuta;
import java.util.ArrayList;

import equipos.Equipo;

import deportes.DeporteGrupal;

import deportes.Futbol;

import deportes.Voleibol;

import deportes.Baloncesto;

import deportes.DeporteIndividual;

import deportes.InscripcionDeporteIndividual;

import instalaciones.Instalacion;
import entrenamientos.SesionEntrenamiento;

import cafeteria.Local;
import cafeteria.Producto;
import cafeteria.Venta;
import competencias.Torneo;
import competencias.TorneoCompetitivo;
import competencias.TorneoRecreativo;
import competencias.EncuentroTorneo;
import usuarios.Participante;
import usuarios.Pareja;
import deportes.Deporte;
import deportes.Padel;
import deportes.Tenis;
import deportes.TenisMesa;

public class ConsolaClubSeneca {

    private Scanner scanner;

    private ClubSeneca club;

    public ConsolaClubSeneca() {
        this(new Scanner(System.in), new ClubSeneca());
    }

    public ConsolaClubSeneca(Scanner scanner, ClubSeneca club) {
        this.scanner = scanner;
        this.club = club;
    }

    public ClubSeneca getClub() { return club; }

    public void setClub(ClubSeneca club) {
        if (club == null) throw new IllegalArgumentException("El club no puede ser null");
        this.club = club;
    }

    private static final Path ARCHIVO_DATOS = Path.of("datos", "club-seneca.dat");

    private void guardarInformacion() {
        try {
            PersistenciaClubSeneca.guardar(club, ARCHIVO_DATOS);
            System.out.println("Información guardada correctamente en " + ARCHIVO_DATOS.toAbsolutePath());
        } catch (IOException | RuntimeException e) {
            System.out.println("No fue posible guardar la información: " + e.getMessage());
        }
    }

    private void cargarInformacion() {
        try {
            club = PersistenciaClubSeneca.cargar(ARCHIVO_DATOS);
            System.out.println("Información cargada correctamente.");
        } catch (IOException | ClassNotFoundException | RuntimeException e) {
            System.out.println("No fue posible cargar la información: " + e.getMessage());
        }
    }

    private void generarReportes() {
        System.out.println("\n===== REPORTES DEL CLUB DEPORTIVO SÉNECA =====");
        System.out.println("REPORTE GENERAL");
        System.out.println("Empleados: " + club.getEmpleados().size());
        System.out.println("Jugadores: " + club.getJugadores().size());
        System.out.println("Equipos: " + club.getEquipos().size());
        System.out.println("Deportes individuales: " + club.getDeportesIndividuales().size());
        System.out.println("Locales: " + club.getLocales().size());
        System.out.println("Instalaciones: " + club.getInstalaciones().size());
        System.out.println("Sesiones de entrenamiento: " + club.getSesionesEntrenamiento().size());
        System.out.println("Torneos: " + club.getTorneos().size());
        System.out.println("\nREPORTE DE INVENTARIO");
        for (Local local : club.getLocales()) {
            System.out.println("Local: " + local.getNombre());
            for (Producto producto : local.getProductos()) {
                System.out.println(" - " + producto.getNombre() + " | stock: " + producto.getStock()
                        + " | precio: $" + producto.getPrecio());
            }
        }
        System.out.println("\nREPORTE DE VENTAS");
        double totalVentas = 0;
        int numeroVentas = 0;
        for (Local local : club.getLocales()) {
            double totalLocal = 0;
            for (Venta venta : local.getVentas()) {
                totalLocal += venta.getTotal();
                numeroVentas++;
            }
            totalVentas += totalLocal;
            System.out.println(local.getNombre() + ": $" + String.format(java.util.Locale.US, "%.2f", totalLocal));
        }
        System.out.println("Ventas registradas: " + numeroVentas);
        System.out.println("Total vendido: $" + String.format(java.util.Locale.US, "%.2f", totalVentas));
        System.out.println("\nREPORTE DE TORNEOS");
        for (Torneo torneo : club.getTorneos()) {
            System.out.println(" - " + torneo.getNombre() + " | estado: " + torneo.getEstado()
                    + " | inscritos: " + torneo.getInscritos().size()
                    + " | cupos disponibles: " + torneo.getCuposDisponibles());
        }
    }

    public static void main(String[] args) {

        ConsolaClubSeneca consola = new ConsolaClubSeneca();

        consola.ejecutar();

    }

    // =========================================

    // MENU PRINCIPAL

    // =========================================

    /**
     * Punto de entrada del sistema: exige autenticación antes de mostrar
     * cualquier menú funcional. Al cerrar sesión vuelve a pedir credenciales.
     */
    public void ejecutar() {
        if (Files.exists(ARCHIVO_DATOS)) {
            try {
                club = PersistenciaClubSeneca.cargar(ARCHIVO_DATOS);
                System.out.println("Información del club cargada automáticamente.");
            } catch (IOException | ClassNotFoundException | RuntimeException e) {
                System.out.println("No se pudo cargar la información guardada: " + e.getMessage());
            }
        }
        boolean existeAdministrador = false;
        for (EmpleadoClub empleado : club.getEmpleados()) {
            if (empleado instanceof Administrador) {
                existeAdministrador = true;
                break;
            }
        }
        if (!existeAdministrador) {
            crearAdministradorInicial();
        }

        boolean salir = false;
        while (!salir) {
            Usuario usuario = iniciarSesion();
            if (usuario == null) {
                salir = true;
                continue;
            }
            System.out.println("\nBienvenido/a, " + usuario.getNombre() + ".");
            boolean cerrarSesion = false;
            while (!cerrarSesion && !salir) {
                if (usuario instanceof Administrador) {
                    cerrarSesion = menuAdministrador();
                } else if (usuario instanceof Entrenador) {
                    cerrarSesion = menuEntrenador();
                } else if (usuario instanceof Fisioterapeuta) {
                    cerrarSesion = menuFisioterapeuta();
                } else if (usuario instanceof Jugador) {
                    cerrarSesion = menuJugador();
                } else {
                    cerrarSesion = menuEmpleado();
                }
            }
            if (salir) break;
            System.out.println("Sesión cerrada correctamente.");
        }
        scanner.close();
    }

    private void crearAdministradorInicial() {
        System.out.println("\n===== CONFIGURACIÓN INICIAL =====");
        System.out.println("No existen usuarios registrados. Cree la cuenta del administrador inicial.");
        try {
            int id = leerEntero("ID: ");
            String nombre = leerTexto("Nombre: ");
            String email = leerTexto("Correo electrónico: ");
            String login = leerTexto("Usuario para iniciar sesión: ");
            String password = leerTexto("Contraseña: ");
            String telefono = leerTexto("Teléfono: ");
            Date nacimiento = leerFecha("Fecha de nacimiento (yyyy-MM-dd): ");
            int edad = leerEntero("Edad: ");
            String codigo = leerTexto("Número de empleado: ");
            club.agregarEmpleado(new Administrador(id, nombre, email, login, password,
                    telefono, nacimiento, edad, codigo));
            guardarInformacion();
            System.out.println("Administrador inicial creado. Ahora inicie sesión.");
        } catch (ParseException e) {
            System.out.println("Fecha inválida. Reinicie el programa para intentar de nuevo.");
        }
    }

    private Usuario iniciarSesion() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("       CLUB DEPORTIVO SÉNECA - INICIO DE SESIÓN");
            System.out.println("========================================");
            String login = leerTexto("Usuario (escriba SALIR para terminar): ");
            if (login.equalsIgnoreCase("SALIR")) return null;
            String password = leerTexto("Contraseña: ");
            Usuario usuario = buscarUsuario(login, password);
            if (usuario != null) return usuario;
            System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.");
            String continuar = leerTexto("¿Desea intentar otra vez? (S/N): ");
            if (continuar.equalsIgnoreCase("N")) return null;
        }
    }

    private Usuario buscarUsuario(String login, String password) {
        for (EmpleadoClub empleado : club.getEmpleados()) {
            if (empleado.validarCredenciales(login, password)) return empleado;
        }
        for (Jugador jugador : club.getJugadores()) {
            if (jugador.validarCredenciales(login, password)) return jugador;
        }
        return null;
    }

    private boolean menuAdministrador() {
        System.out.println("\n===== MENÚ ADMINISTRADOR =====");
        System.out.println("1. Gestionar empleados");
        System.out.println("2. Gestionar jugadores");
        System.out.println("3. Gestionar equipos");
        System.out.println("4. Gestionar deportes individuales");
        System.out.println("5. Gestionar cafetería");
        System.out.println("6. Gestionar torneos");
        System.out.println("7. Gestionar instalaciones");
        System.out.println("8. Gestionar entrenamientos");
        System.out.println("9. Consultar información");
        System.out.println("10. Generar reportes");
        System.out.println("11. Guardar información");
        System.out.println("12. Cargar información");
        System.out.println("13. Cerrar sesión");
        int opcion = leerEntero("Seleccione una opción: ");
        switch (opcion) {
            case 1: menuEmpleados(); break;
            case 2: menuJugadores(); break;
            case 3: menuEquipos(); break;
            case 4: menuDeportesIndividuales(); break;
            case 5: menuCafeteria(); break;
            case 6: menuTorneos(); break;
            case 7: menuInstalaciones(); break;
            case 8: menuEntrenamientos(); break;
            case 9: consultarInformacion(); break;
            case 10: generarReportes(); break;
            case 11: guardarInformacion(); break;
            case 12: cargarInformacion(); break;
            case 13: return true;
            default: System.out.println("Opción no válida.");
        }
        return false;
    }

    private boolean menuEntrenador() {
        System.out.println("\n===== MENÚ ENTRENADOR =====");
        System.out.println("1. Gestionar entrenamientos");
        System.out.println("2. Consultar información del club");
        System.out.println("3. Cafetería");
        System.out.println("4. Cerrar sesión");
        switch (leerEntero("Seleccione una opción: ")) {
            case 1: menuEntrenamientos(); break;
            case 2: consultarInformacion(); break;
            case 3: menuCafeteria(); break;
            case 4: return true;
            default: System.out.println("Opción no válida.");
        }
        return false;
    }

    private boolean menuFisioterapeuta() {
        System.out.println("\n===== MENÚ FISIOTERAPEUTA =====");
        System.out.println("1. Consultar jugadores");
        System.out.println("2. Consultar información del club");
        System.out.println("3. Cafetería");
        System.out.println("4. Cerrar sesión");
        switch (leerEntero("Seleccione una opción: ")) {
            case 1: listarJugadores(); break;
            case 2: consultarInformacion(); break;
            case 3: menuCafeteria(); break;
            case 4: return true;
            default: System.out.println("Opción no válida.");
        }
        return false;
    }

    private boolean menuJugador() {
        System.out.println("\n===== MENÚ JUGADOR =====");
        System.out.println("1. Consultar información del club");
        System.out.println("2. Consultar sesiones de entrenamiento");
        System.out.println("3. Cafetería");
        System.out.println("4. Cerrar sesión");
        switch (leerEntero("Seleccione una opción: ")) {
            case 1: consultarInformacion(); break;
            case 2: listarSesionesEntrenamiento(); break;
            case 3: menuCafeteria(); break;
            case 4: return true;
            default: System.out.println("Opción no válida.");
        }
        return false;
    }

    private boolean menuEmpleado() {
        System.out.println("\n===== MENÚ EMPLEADO =====");
        System.out.println("1. Cafetería");
        System.out.println("2. Consultar información del club");
        System.out.println("3. Cerrar sesión");
        switch (leerEntero("Seleccione una opción: ")) {
            case 1: menuCafeteria(); break;
            case 2: consultarInformacion(); break;
            case 3: return true;
            default: System.out.println("Opción no válida.");
        }
        return false;
    }

    // =========================================

    // GESTION DE EMPLEADOS

    // =========================================

    private void menuEmpleados() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("\n===== GESTIÓN DE EMPLEADOS =====");

            System.out.println("1. Registrar empleado");

            System.out.println("2. Consultar empleados");

            System.out.println("3. Retirar empleado");

            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {

                case 1:

                    registrarEmpleado();

                    break;

                case 2:

                    listarEmpleados();

                    break;

                case 3:

                    retirarEmpleado();

                    break;

                case 0:

                    break;

                default:

                    System.out.println("Opción no válida.");

            }

        }

    }

    private void registrarEmpleado() {

        try {

            System.out.println("\n===== REGISTRAR EMPLEADO =====");

            int id = leerEntero("ID: ");

            String nombre = leerTexto("Nombre: ");

            String email = leerTexto("Correo electrónico: ");

            String login = leerTexto("Login: ");

            String password = leerTexto("Contraseña: ");

            String telefono = leerTexto("Teléfono: ");

            Date fechaNacimiento =

                    leerFecha("Fecha de nacimiento (yyyy-MM-dd): ");

            int edad = leerEntero("Edad: ");

            String noEmpleado = leerTexto("Número de empleado: ");

            for (EmpleadoClub empleado : club.getEmpleados()) {

                if (empleado.getNoEmpleado().equals(noEmpleado)) {

                    System.out.println(

                            "No se pudo registrar: ya existe ese número de empleado.");

                    return;

                }

            }

            System.out.println("Tipo de empleado:");
            System.out.println("1. Empleado general");
            System.out.println("2. Administrador");
            System.out.println("3. Entrenador");
            System.out.println("4. Fisioterapeuta");
            int tipoEmpleado = leerEntero("Seleccione el tipo: ");
            EmpleadoClub empleado;
            switch (tipoEmpleado) {
                case 1:
                    empleado = new EmpleadoClub(id, nombre, email, login, password,
                            telefono, fechaNacimiento, edad, noEmpleado);
                    break;
                case 2:
                    empleado = new Administrador(id, nombre, email, login, password,
                            telefono, fechaNacimiento, edad, noEmpleado);
                    break;
                case 3:
                    empleado = new Entrenador(id, nombre, email, login, password,
                            telefono, fechaNacimiento, edad, noEmpleado, null);
                    break;
                case 4:
                    empleado = new Fisioterapeuta(id, nombre, email, login, password,
                            telefono, fechaNacimiento, edad, noEmpleado, new ArrayList<>());
                    break;
                default:
                    System.out.println("Tipo de empleado inválido.");
                    return;
            }
            club.agregarEmpleado(empleado);
            System.out.println("Empleado registrado correctamente.");

        } catch (ParseException e) {

            System.out.println("La fecha debe tener el formato yyyy-MM-dd.");

        }

    }

    private void listarEmpleados() {

        List<EmpleadoClub> empleados = club.getEmpleados();

        if (empleados.isEmpty()) {

            System.out.println("No hay empleados registrados.");

            return;

        }

        System.out.println("\n===== EMPLEADOS REGISTRADOS =====");

        for (EmpleadoClub empleado : empleados) {

            System.out.println("---------------------------");

            System.out.println("Número de empleado: "

                    + empleado.getNoEmpleado());

        }

    }

    private void retirarEmpleado() {

        listarEmpleados();

        List<EmpleadoClub> empleados = club.getEmpleados();

        if (empleados.isEmpty()) {

            return;

        }

        int posicion = leerEntero(

                "Número del empleado que desea retirar: ");

        if (posicion >= 1 && posicion <= empleados.size()) {

            club.sacarEmpleado(empleados.get(posicion - 1));

            System.out.println("Empleado retirado correctamente.");

        } else {

            System.out.println("Número inválido.");

        }

    }

    // =========================================

    // GESTION DE JUGADORES

    // =========================================

    private void menuJugadores() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("\n===== GESTIÓN DE JUGADORES =====");

            System.out.println("1. Registrar jugador");

            System.out.println("2. Consultar jugadores");

            System.out.println("3. Buscar jugador por código");

            System.out.println("4. Retirar jugador");

            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {

                case 1:

                    registrarJugador();

                    break;

                case 2:

                    listarJugadores();

                    break;

                case 3:

                    consultarJugador();

                    break;

                case 4:

                    retirarJugador();

                    break;

                case 0:

                    break;

                default:

                    System.out.println("Opción no válida.");

            }

        }

    }

    private void registrarJugador() {

        try {

            System.out.println("\n===== REGISTRAR JUGADOR =====");

            int id = leerEntero("ID: ");

            String nombre = leerTexto("Nombre: ");

            String email = leerTexto("Correo electrónico: ");

            String login = leerTexto("Login: ");

            String password = leerTexto("Contraseña: ");

            String telefono = leerTexto("Teléfono: ");

            Date fechaNacimiento =

                    leerFecha("Fecha de nacimiento (yyyy-MM-dd): ");

            String codigoSocio = leerTexto("Código de socio: ");

            Date fechaAfiliacion =

                    leerFecha("Fecha de afiliación (yyyy-MM-dd): ");

            int puntosLealtad = leerEntero("Puntos de lealtad: ");

            int edad = leerEntero("Edad: ");

            System.out.println("\nTipos de socio:");

            System.out.println("1. Socio");

            System.out.println("2. Estudiante");

            System.out.println("3. Externo");

            int tipoOpcion = leerEntero("Seleccione el tipo: ");

            String tipo;

            switch (tipoOpcion) {

                case 1:

                    tipo = Jugador.TIPO_SOCIO;

                    break;

                case 2:

                    tipo = Jugador.TIPO_ESTUDIANTE;

                    break;

                case 3:

                    tipo = Jugador.TIPO_EXTERNO;

                    break;

                default:

                    System.out.println("Tipo de socio inválido.");

                    return;

            }

            if (club.consultarJugador(codigoSocio) != null) {

                System.out.println(

                        "No se pudo registrar: ya existe ese código de socio.");

                return;

            }

            Jugador jugador = new Jugador(

                    id,

                    nombre,

                    email,

                    login,

                    password,

                    telefono,

                    fechaNacimiento,

                    codigoSocio,

                    fechaAfiliacion,

                    puntosLealtad,

                    edad,

                    tipo

            );

            club.crearJugador(jugador);

            System.out.println("Jugador registrado correctamente.");

        } catch (ParseException e) {

            System.out.println("La fecha debe tener el formato yyyy-MM-dd.");

        }

    }

    private void listarJugadores() {

        List<Jugador> jugadores = club.getJugadores();

        if (jugadores.isEmpty()) {

            System.out.println("No hay jugadores registrados.");

            return;

        }

        System.out.println("\n===== JUGADORES REGISTRADOS =====");

        for (Jugador jugador : jugadores) {

            System.out.println("---------------------------");

            mostrarDatosJugador(jugador);

        }

    }

    private void consultarJugador() {

        String codigo = leerTexto("Ingrese el código del socio: ");

        Jugador jugador = club.consultarJugador(codigo);

        if (jugador != null) {

            mostrarDatosJugador(jugador);

        } else {

            System.out.println("No existe un jugador con ese código.");

        }

    }

    private void retirarJugador() {

        listarJugadores();

        List<Jugador> jugadores = club.getJugadores();

        if (jugadores.isEmpty()) {

            return;

        }

        int posicion = leerEntero(

                "Número del jugador que desea retirar: ");

        if (posicion >= 1 && posicion <= jugadores.size()) {

            club.sacarJugador(jugadores.get(posicion - 1));

            System.out.println("Jugador retirado correctamente.");

        } else {

            System.out.println("Número inválido.");

        }

    }

    private void mostrarDatosJugador(Jugador jugador) {

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Código de socio: "

                + jugador.getCodigoSocio());

        System.out.println("Tipo de socio: "

                + jugador.getTipo());

        System.out.println("Fecha de afiliación: "

                + formato.format(jugador.getFechaAfiliacion()));

        System.out.println("Puntos de lealtad: "

                + jugador.getPuntosLealtad());

    }

    // =========================================

    // GESTION DE EQUIPOS

    // =========================================


private void menuEquipos() {

    int opcion = -1;

    while (opcion != 0) {

        System.out.println("\n===== GESTIÓN DE EQUIPOS =====");
        System.out.println("1. Registrar equipo");
        System.out.println("2. Listar equipos");
        System.out.println("3. Buscar equipo por nombre");
        System.out.println("4. Consultar jugadores del equipo");
        System.out.println("5. Inscribir jugador en un equipo");
        System.out.println("6. Retirar jugador de un equipo");
        System.out.println("7. Retirar equipo");
        System.out.println("0. Volver al menú principal");

        opcion = leerEntero("Seleccione una opción: ");

        switch (opcion) {

            case 1:
                registrarEquipo();
                break;

            case 2:
                listarEquipos();
                break;

            case 3:
                consultarEquipo();
                break;

            case 4:
                consultarJugadoresEquipo();
                break;

            case 5:
                inscribirJugadorEquipo();
                break;

            case 6:
                retirarJugadorEquipo();
                break;

            case 7:
                retirarEquipo();
                break;

            case 0:
                break;

            default:
                System.out.println("Opción no válida.");
        }
    }
}


    private void registrarEquipo() {

        System.out.println("\n===== REGISTRAR EQUIPO =====");

        String nombre = leerTexto("Nombre del equipo: ");

        if (club.consultarEquipo(nombre) != null) {

            System.out.println("Ya existe un equipo con ese nombre.");

            return;

        }

        System.out.println("\nDeportes disponibles:");

        System.out.println("1. Fútbol");

        System.out.println("2. Voleibol");

        System.out.println("3. Baloncesto");

        int opcionDeporte = leerEntero("Seleccione el deporte: ");

        String nombreDeporte;

        int minJugadores;

        int maxJugadores;

        switch (opcionDeporte) {

            case 1:

                nombreDeporte = "Fútbol";

                minJugadores = 7;

                maxJugadores = 11;

                break;

            case 2:

                nombreDeporte = "Voleibol";

                minJugadores = 6;

                maxJugadores = 12;

                break;

            case 3:

                nombreDeporte = "Baloncesto";

                minJugadores = 5;

                maxJugadores = 12;

                break;

            default:

                System.out.println("Deporte no válido.");

                return;

        }

        System.out.println("\nCategorías disponibles:");

        System.out.println("1. SUB-13");

        System.out.println("2. SUB-15");

        System.out.println("3. SUB-17");

        System.out.println("4. MAYORES");

        int opcionCategoria = leerEntero("Seleccione la categoría: ");

        String categoria;

        switch (opcionCategoria) {

            case 1:

                categoria = "SUB-13";

                break;

            case 2:

                categoria = "SUB-15";

                break;

            case 3:

                categoria = "SUB-17";

                break;

            case 4:

                categoria = "MAYORES";

                break;

            default:

                System.out.println("Categoría no válida.");

                return;

        }

        DeporteGrupal deporte;

        switch (opcionDeporte) {

            case 1:

                deporte = new Futbol(

                        nombreDeporte, minJugadores, maxJugadores);

                break;

            case 2:

                deporte = new Voleibol(

                        nombreDeporte, minJugadores, maxJugadores);

                break;

            default:

                deporte = new Baloncesto(

                        nombreDeporte, minJugadores, maxJugadores);

                break;

        }

        Equipo equipo = new Equipo(

                categoria, nombre, false, deporte);

        club.crearEquipo(equipo);

        deporte.crearEquipo(equipo);

        System.out.println("Equipo registrado correctamente.");

    }

    private void listarEquipos() {

        List<Equipo> equipos = club.getEquipos();

        if (equipos.isEmpty()) {

            System.out.println("No hay equipos registrados.");

            return;

        }

        System.out.println("\n===== EQUIPOS REGISTRADOS =====");

        for (Equipo equipo : equipos) {

            System.out.println("---------------------------");

            System.out.println("Nombre: " + equipo.getNombre());

            System.out.println("Categoría: " + equipo.getCategoria());

            System.out.println("Activo: " + equipo.isActivo());

        }

    }

    private void consultarEquipo() {

        String nombre = leerTexto("Nombre del equipo: ");

        Equipo equipo = club.consultarEquipo(nombre);

        if (equipo == null) {

            System.out.println("No existe ese equipo.");

            return;

        }

        System.out.println("\n===== INFORMACIÓN DEL EQUIPO =====");

        System.out.println("Nombre: " + equipo.getNombre());

        System.out.println("Categoría: " + equipo.getCategoria());

        System.out.println("Activo: " + equipo.isActivo());

        System.out.println("Desempeño: " + equipo.getDesempeno());

        if (equipo.getEntrenador() != null) {

            System.out.println("Entrenador asignado.");

        } else {

            System.out.println("Sin entrenador asignado.");

        }

    }

    private void consultarJugadoresEquipo() {

        String nombre = leerTexto("Nombre del equipo: ");

        Equipo equipo = club.consultarEquipo(nombre);

        if (equipo == null) {

            System.out.println("No existe ese equipo.");

            return;

        }

        List<Usuario> integrantes = equipo.getIntegrantes();

        if (integrantes.isEmpty()) {

            System.out.println("El equipo todavía no tiene jugadores.");

            return;

        }

        System.out.println("\n===== JUGADORES DEL EQUIPO =====");

        for (Usuario usuario : integrantes) {

            System.out.println(usuario.getNombre());

        }

    }
    

private void inscribirJugadorEquipo() {

    System.out.println("\n===== INSCRIBIR JUGADOR EN EQUIPO =====");

    List<Equipo> equipos = club.getEquipos();

    if (equipos.isEmpty()) {
        System.out.println("No hay equipos registrados.");
        return;
    }

    for (int i = 0; i < equipos.size(); i++) {
        Equipo equipo = equipos.get(i);

        System.out.println((i + 1) + ". " + equipo.getNombre()
                + " | Deporte: " + equipo.getDeporte().obtenerNombre()
                + " | Categoría: " + equipo.getCategoria());
    }

    int posicionEquipo = leerEntero("Seleccione el equipo: ");

    if (posicionEquipo < 1 || posicionEquipo > equipos.size()) {
        System.out.println("Número de equipo inválido.");
        return;
    }

    Equipo equipo = equipos.get(posicionEquipo - 1);

    List<Jugador> jugadores = club.getJugadores();

    if (jugadores.isEmpty()) {
        System.out.println("No hay jugadores registrados.");
        return;
    }

    System.out.println("\n===== JUGADORES DISPONIBLES =====");

    for (int i = 0; i < jugadores.size(); i++) {
        Jugador jugador = jugadores.get(i);

        System.out.println((i + 1) + ". " + jugador.getNombre()
                + " | Código: " + jugador.getCodigoSocio()
                + " | Edad: " + jugador.getEdad());
    }

    int posicionJugador = leerEntero("Seleccione el jugador: ");

    if (posicionJugador < 1 || posicionJugador > jugadores.size()) {
        System.out.println("Número de jugador inválido.");
        return;
    }

    Jugador jugador = jugadores.get(posicionJugador - 1);

    String posicion = leerTexto("Posición del jugador: ");

    int numeroCamisa = leerEntero("Número de camiseta: ");

    int opcionTitular = leerEntero("¿Es titular? (1. Sí / 0. No): ");

    if (opcionTitular != 0 && opcionTitular != 1) {
        System.out.println("Opción inválida.");
        return;
    }

    boolean titular = opcionTitular == 1;

    boolean inscrito = jugador.inscribirseAEquipo(
            posicion,
            numeroCamisa,
            titular,
            new Date(),
            equipo
    );

    if (inscrito) {
        System.out.println("Jugador inscrito correctamente.");
        System.out.println("Equipo: " + equipo.getNombre());
        System.out.println("Jugador: " + jugador.getNombre());
        System.out.println("Estado del equipo: "
                + (equipo.isActivo() ? "Activo" : "Inactivo"));
    } else {
        System.out.println("No se pudo inscribir al jugador.");
        System.out.println("Verifique la edad, los cupos disponibles "
                + "y que el jugador no esté inscrito previamente.");
    }
}

private void retirarJugadorEquipo() {

    System.out.println("\n===== RETIRAR JUGADOR DEL EQUIPO =====");

    List<Equipo> equipos = club.getEquipos();

    if (equipos.isEmpty()) {
        System.out.println("No hay equipos registrados.");
        return;
    }

    for (int i = 0; i < equipos.size(); i++) {
        System.out.println((i + 1) + ". " + equipos.get(i).getNombre());
    }

    int posicionEquipo = leerEntero("Seleccione el equipo: ");

    if (posicionEquipo < 1 || posicionEquipo > equipos.size()) {
        System.out.println("Número de equipo inválido.");
        return;
    }

    Equipo equipo = equipos.get(posicionEquipo - 1);

    List<Usuario> integrantes = equipo.getIntegrantes();

    if (integrantes.isEmpty()) {
        System.out.println("El equipo no tiene jugadores inscritos.");
        return;
    }

    System.out.println("\n===== JUGADORES DEL EQUIPO =====");

    for (int i = 0; i < integrantes.size(); i++) {
        Usuario usuario = integrantes.get(i);
        System.out.println((i + 1) + ". " + usuario.getNombre());
    }

    int posicionJugador = leerEntero("Seleccione el jugador que desea retirar: ");

    if (posicionJugador < 1 || posicionJugador > integrantes.size()) {
        System.out.println("Número de jugador inválido.");
        return;
    }

    Jugador jugador = (Jugador) integrantes.get(posicionJugador - 1);

    boolean retirado = jugador.retirarDeEquipo(equipo);

    if (retirado) {
        System.out.println("Jugador retirado correctamente.");
        System.out.println("Equipo: " + equipo.getNombre());
        System.out.println("Estado actual del equipo: "
                + (equipo.isActivo() ? "Activo" : "Inactivo"));
    } else {
        System.out.println("No se pudo retirar al jugador.");
    }
}


    private void retirarEquipo() {

        listarEquipos();

        List<Equipo> equipos = club.getEquipos();

        if (equipos.isEmpty()) {

            return;

        }

        int posicion = leerEntero(

                "Número del equipo que desea retirar: ");

        if (posicion >= 1 && posicion <= equipos.size()) {

            club.sacarEquipo(equipos.get(posicion - 1));

            System.out.println("Equipo retirado correctamente.");

        } else {

            System.out.println("Número inválido.");

        }

    }
    
    private void menuDeportesIndividuales()
    {
        int opcion = -1;

        while (opcion != 0)
        {
            System.out.println("\n===== DEPORTES INDIVIDUALES =====");
            System.out.println("1. Registrar deporte individual");
            System.out.println("2. Listar deportes individuales");
            System.out.println("3. Inscribir jugador en un deporte");
            System.out.println("4. Consultar jugadores por deporte");
            System.out.println("5. Consultar jugadores por nivel");
            System.out.println("6. Consultar ranking de un jugador");
            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion)
            {
                case 1:
                    registrarDeporteIndividual();
                    break;

                case 2:
                    listarDeportesIndividuales();
                    break;

                case 3:
                    inscribirJugadorDeporteIndividual();
                    break;

                case 4:
                    consultarJugadoresDeporte();
                    break;

                case 5:
                    consultarJugadoresNivel();
                    break;

                case 6:
                    consultarRankingIndividual();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
    
    private void registrarDeporteIndividual()
    {
        System.out.println("\n===== REGISTRAR DEPORTE INDIVIDUAL =====");

        System.out.println("1. Pádel");
        System.out.println("2. Tenis");
        System.out.println("3. Tenis de mesa");

        int opcion = leerEntero("Seleccione el deporte: ");

        DeporteIndividual deporte;

        switch (opcion)
        {
            case 1:
                deporte = new Padel("Pádel");
                break;

            case 2:
                deporte = new Tenis("Tenis");
                break;

            case 3:
                deporte = new TenisMesa("Tenis de mesa");
                break;

            default:
                System.out.println("Opción no válida.");
                return;
        }

        boolean agregado = club.agregarDeporteIndividual(deporte);

        if (agregado)
        {
            System.out.println("Deporte registrado correctamente.");
        }
        else
        {
            System.out.println("Este deporte ya está registrado.");
        }
    }

    private void listarDeportesIndividuales()
    {
        List<DeporteIndividual> deportes = club.getDeportesIndividuales();

        if (deportes.isEmpty())
        {
            System.out.println("No hay deportes individuales registrados.");
            return;
        }

        System.out.println("\n===== DEPORTES INDIVIDUALES =====");

        for (int i = 0; i < deportes.size(); i++)
        {
            System.out.println((i + 1) + ". "
                    + deportes.get(i).obtenerNombre());
        }
    }

    private void inscribirJugadorDeporteIndividual()
    {
        List<DeporteIndividual> deportes = club.getDeportesIndividuales();

        if (deportes.isEmpty())
        {
            System.out.println("No hay deportes individuales registrados.");
            return;
        }

        listarDeportesIndividuales();

        int posicionDeporte = leerEntero("Seleccione el deporte: ");

        if (posicionDeporte < 1 || posicionDeporte > deportes.size())
        {
            System.out.println("Número de deporte inválido.");
            return;
        }

        DeporteIndividual deporte = deportes.get(posicionDeporte - 1);

        List<Jugador> jugadores = club.getJugadores();

        if (jugadores.isEmpty())
        {
            System.out.println("No hay jugadores registrados.");
            return;
        }

        listarJugadores();

        int posicionJugador = leerEntero("Seleccione el jugador: ");

        if (posicionJugador < 1 || posicionJugador > jugadores.size())
        {
            System.out.println("Número de jugador inválido.");
            return;
        }

        Jugador jugador = jugadores.get(posicionJugador - 1);

        System.out.println("\nNiveles disponibles:");
        System.out.println("1. PRINCIPIANTE");
        System.out.println("2. INTERMEDIO");
        System.out.println("3. AVANZADO");

        int nivelOpcion = leerEntero("Seleccione el nivel: ");

        String nivel;

        switch (nivelOpcion)
        {
            case 1:
                nivel = "PRINCIPIANTE";
                break;

            case 2:
                nivel = "INTERMEDIO";
                break;

            case 3:
                nivel = "AVANZADO";
                break;

            default:
                System.out.println("Nivel inválido.");
                return;
        }

        if (jugador.inscribirseADeporte(deporte, nivel))
        {
            System.out.println("Jugador inscrito correctamente.");
        }
        else
        {
            System.out.println("No se pudo realizar la inscripción.");
        }
    }

    private void consultarJugadoresDeporte()
    {
        List<DeporteIndividual> deportes = club.getDeportesIndividuales();

        if (deportes.isEmpty())
        {
            System.out.println("No hay deportes registrados.");
            return;
        }

        listarDeportesIndividuales();

        int posicion = leerEntero("Seleccione el deporte: ");

        if (posicion < 1 || posicion > deportes.size())
        {
            System.out.println("Número inválido.");
            return;
        }

        DeporteIndividual deporte = deportes.get(posicion - 1);

        System.out.println("\nJugadores inscritos en "
                + deporte.obtenerNombre() + ":");

        List<InscripcionDeporteIndividual> inscritos =
                new java.util.ArrayList<>();

        for (Jugador jugador : club.getJugadores())
        {
            for (DeporteIndividual deporteJugador : jugador.consultarDeportes())
            {
                if (deporteJugador == deporte)
                {
                    for (InscripcionDeporteIndividual inscripcion : deporte.consultarJugadoresPorNivel("PRINCIPIANTE")) {
                        if (inscripcion.consultarJugador() == jugador) System.out.println("- " + jugador.getNombre() + " | Nivel: PRINCIPIANTE");
                    }
                    for (InscripcionDeporteIndividual inscripcion : deporte.consultarJugadoresPorNivel("INTERMEDIO")) {
                        if (inscripcion.consultarJugador() == jugador) System.out.println("- " + jugador.getNombre() + " | Nivel: INTERMEDIO");
                    }
                    for (InscripcionDeporteIndividual inscripcion : deporte.consultarJugadoresPorNivel("AVANZADO")) {
                        if (inscripcion.consultarJugador() == jugador) System.out.println("- " + jugador.getNombre() + " | Nivel: AVANZADO");
                    }
                }
            }
        }
    }

    private void consultarJugadoresNivel()
    {
        List<DeporteIndividual> deportes = club.getDeportesIndividuales();

        if (deportes.isEmpty())
        {
            System.out.println("No hay deportes registrados.");
            return;
        }

        listarDeportesIndividuales();

        int posicion = leerEntero("Seleccione el deporte: ");

        if (posicion < 1 || posicion > deportes.size())
        {
            System.out.println("Número inválido.");
            return;
        }

        DeporteIndividual deporte = deportes.get(posicion - 1);

        System.out.println("1. PRINCIPIANTE");
        System.out.println("2. INTERMEDIO");
        System.out.println("3. AVANZADO");

        int nivelOpcion = leerEntero("Seleccione el nivel: ");

        String nivel;

        switch (nivelOpcion)
        {
            case 1: nivel = "PRINCIPIANTE"; break;
            case 2: nivel = "INTERMEDIO"; break;
            case 3: nivel = "AVANZADO"; break;
            default:
                System.out.println("Nivel inválido.");
                return;
        }

        List<InscripcionDeporteIndividual> inscritos =
                deporte.consultarJugadoresPorNivel(nivel);

        if (inscritos.isEmpty())
        {
            System.out.println("No hay jugadores registrados en ese nivel.");
            return;
        }

        for (InscripcionDeporteIndividual inscripcion : inscritos)
        {
            System.out.println("- " + inscripcion.consultarJugador().getNombre());
        }
    }

    private void consultarRankingIndividual()
    {
        String codigo = leerTexto("Código del jugador: ");

        Jugador jugador = club.consultarJugador(codigo);

        if (jugador == null)
        {
            System.out.println("No existe ese jugador.");
            return;
        }

        List<DeporteIndividual> deportes = jugador.consultarDeportes();

        if (deportes.isEmpty())
        {
            System.out.println("El jugador no está inscrito en deportes individuales.");
            return;
        }

        System.out.println("\nRanking de " + jugador.getNombre() + ":");

        for (DeporteIndividual deporte : deportes)
        {
            System.out.println(deporte.obtenerNombre()
                    + ": " + jugador.getRanking(deporte));
        }
    }

    // =========================================


    // =========================================
    // GESTIÓN DE INSTALACIONES
    // =========================================
    private void menuInstalaciones() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n===== GESTIÓN DE INSTALACIONES =====");
            System.out.println("1. Registrar instalación");
            System.out.println("2. Listar instalaciones");
            System.out.println("3. Cambiar disponibilidad");
            System.out.println("4. Eliminar instalación");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1: registrarInstalacion(); break;
                case 2: listarInstalaciones(); break;
                case 3: cambiarDisponibilidadInstalacion(); break;
                case 4:
                    String nombre = leerTexto("Nombre de la instalación: ");
                    System.out.println(club.eliminarInstalacion(nombre) ? "Instalación eliminada." : "No se pudo eliminar. Verifique que exista y no tenga sesiones asociadas.");
                    break;
                case 0: break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private void registrarInstalacion() {
        String nombre = leerTexto("Nombre: ");
        String deporte = leerTexto("Deporte asociado (Fútbol, Tenis, Pádel, etc.): ");
        int capacidad = leerEntero("Capacidad: ");
        if (capacidad <= 0) { System.out.println("La capacidad debe ser mayor que cero."); return; }
        String apertura = leerTexto("Hora de apertura (HH:mm): ");
        String cierre = leerTexto("Hora de cierre (HH:mm): ");
        int disponible = leerEntero("¿Está disponible? (1=Sí, 0=No): ");
        int restriccion = leerEntero("¿Restringe bebidas calientes? (1=Sí, 0=No): ");
        Instalacion instalacion = new Instalacion(nombre, deporte, capacidad, disponible == 1, apertura, cierre, restriccion == 1);
        System.out.println(club.agregarInstalacion(instalacion) ? "Instalación registrada correctamente." : "Ya existe una instalación con ese nombre.");
    }

    private void listarInstalaciones() {
        List<Instalacion> instalaciones = club.getInstalaciones();
        if (instalaciones.isEmpty()) { System.out.println("No hay instalaciones registradas."); return; }
        for (int i = 0; i < instalaciones.size(); i++) {
            Instalacion x = instalaciones.get(i);
            System.out.println((i + 1) + ". " + x.getNombre() + " | Deporte: " + x.getTipoDeporte()
                    + " | Capacidad: " + x.getCapacidad() + " | Horario: " + x.getHoraApertura() + "-" + x.getHoraCierre()
                    + " | Disponible: " + (x.isDisponible() ? "Sí" : "No"));
        }
    }

    private void cambiarDisponibilidadInstalacion() {
        listarInstalaciones();
        List<Instalacion> instalaciones = club.getInstalaciones();
        if (instalaciones.isEmpty()) return;
        int n = leerEntero("Seleccione la instalación: ");
        if (n < 1 || n > instalaciones.size()) { System.out.println("Selección inválida."); return; }
        Instalacion i = instalaciones.get(n - 1);
        i.setDisponible(!i.isDisponible());
        System.out.println("Nueva disponibilidad: " + (i.isDisponible() ? "Disponible" : "No disponible"));
    }

    // =========================================
    // GESTIÓN DE ENTRENAMIENTOS
    // =========================================
    private void menuEntrenamientos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n===== GESTIÓN DE ENTRENAMIENTOS =====");
            System.out.println("1. Crear sesión de entrenamiento");
            System.out.println("2. Listar sesiones");
            System.out.println("3. Convocar jugador a una sesión");
            System.out.println("4. Registrar asistencia");
            System.out.println("5. Consultar asistencia de una sesión");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1: crearSesionEntrenamiento(); break;
                case 2: listarSesionesEntrenamiento(); break;
                case 3: convocarJugadorSesion(); break;
                case 4: registrarAsistenciaSesion(); break;
                case 5: consultarAsistenciaSesion(); break;
                case 0: break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private void crearSesionEntrenamiento() {
        if (club.getInstalaciones().isEmpty()) { System.out.println("Primero debe registrar una instalación."); return; }
        listarInstalaciones();
        int n = leerEntero("Seleccione la instalación: ");
        if (n < 1 || n > club.getInstalaciones().size()) { System.out.println("Selección inválida."); return; }
        String fecha = leerTexto("Fecha (yyyy-MM-dd): ");
        String hora = leerTexto("Hora de inicio (HH:mm): ");
        int duracion = leerEntero("Duración en minutos: ");
        if (duracion <= 0) { System.out.println("La duración debe ser mayor que cero."); return; }
        SesionEntrenamiento sesion = new SesionEntrenamiento(fecha, hora, duracion, club.getInstalaciones().get(n - 1));
        System.out.println(club.agregarSesionEntrenamiento(sesion) ? "Sesión creada correctamente." : "No se pudo crear la sesión.");
    }

    private void listarSesionesEntrenamiento() {
        List<SesionEntrenamiento> sesiones = club.getSesionesEntrenamiento();
        if (sesiones.isEmpty()) { System.out.println("No hay sesiones registradas."); return; }
        for (int i = 0; i < sesiones.size(); i++) {
            SesionEntrenamiento s = sesiones.get(i);
            System.out.println((i + 1) + ". " + s.getFecha() + " " + s.getHoraInicio() + " | "
                    + s.getDuracionMin() + " min | Instalación: " + s.getInstalacion().getNombre()
                    + " | Convocados: " + s.consultarConvocados().size());
        }
    }

    private SesionEntrenamiento seleccionarSesion() {
        listarSesionesEntrenamiento();
        List<SesionEntrenamiento> sesiones = club.getSesionesEntrenamiento();
        if (sesiones.isEmpty()) return null;
        int n = leerEntero("Seleccione la sesión: ");
        if (n < 1 || n > sesiones.size()) { System.out.println("Selección inválida."); return null; }
        return sesiones.get(n - 1);
    }

    private void convocarJugadorSesion() {
        SesionEntrenamiento sesion = seleccionarSesion();
        if (sesion == null) return;
        String codigo = leerTexto("Código del jugador: ");
        Jugador jugador = club.consultarJugador(codigo);
        if (jugador == null) { System.out.println("No existe un jugador con ese código."); return; }
        if (sesion.consultarConvocados().contains(jugador)) { System.out.println("El jugador ya está convocado."); return; }
        sesion.agregarJugadorConvocado(jugador);
        System.out.println("Jugador convocado correctamente.");
    }

    private void registrarAsistenciaSesion() {
        SesionEntrenamiento sesion = seleccionarSesion();
        if (sesion == null) return;
        List<Jugador> convocados = sesion.consultarConvocados();
        if (convocados.isEmpty()) { System.out.println("La sesión no tiene jugadores convocados."); return; }
        for (int i = 0; i < convocados.size(); i++) System.out.println((i + 1) + ". " + convocados.get(i).getNombre() + " (" + convocados.get(i).getCodigoSocio() + ")");
        int n = leerEntero("Seleccione el jugador: ");
        if (n < 1 || n > convocados.size()) { System.out.println("Selección inválida."); return; }
        System.out.println("1. Presente  2. Ausente  3. Justificado");
        int estado = leerEntero("Estado: ");
        String texto = estado == 1 ? "Presente" : estado == 2 ? "Ausente" : estado == 3 ? "Justificado" : null;
        if (texto == null) { System.out.println("Estado inválido."); return; }
        sesion.registrarAsistencia(convocados.get(n - 1), texto);
        System.out.println("Asistencia registrada.");
    }

    private void consultarAsistenciaSesion() {
        SesionEntrenamiento sesion = seleccionarSesion();
        if (sesion == null) return;
        List<Jugador> convocados = sesion.consultarConvocados();
        if (convocados.isEmpty()) { System.out.println("No hay jugadores convocados."); return; }
        for (Jugador j : convocados) {
            String estado = sesion.consultarAsistencia(j);
            System.out.println(j.getNombre() + " | " + (estado == null ? "Sin registrar" : estado));
        }
        System.out.printf("Porcentaje de asistencia: %.2f%%%n", sesion.calcularPorcentajeAsistencia());
    }

    // =========================================
    // GESTIÓN DE CAFETERÍA
    // =========================================

    private void menuCafeteria() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n===== GESTIÓN DE CAFETERÍA =====");
            System.out.println("1. Registrar local");
            System.out.println("2. Listar locales");
            System.out.println("3. Registrar producto en un local");
            System.out.println("4. Listar productos de un local");
            System.out.println("5. Reabastecer producto");
            System.out.println("6. Eliminar producto");
            System.out.println("7. Registrar venta");
            System.out.println("8. Consultar ventas de un local");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1: registrarLocal(); break;
                case 2: listarLocales(); break;
                case 3: registrarProducto(); break;
                case 4: listarProductos(); break;
                case 5: reabastecerProducto(); break;
                case 6: eliminarProducto(); break;
                case 7: registrarVenta(); break;
                case 8: consultarVentas(); break;
                case 0: break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private void registrarLocal() {
        System.out.println("\n===== REGISTRAR LOCAL =====");
        String nombre = leerTexto("Nombre del local: ");
        if (club.consultarLocal(nombre) != null) {
            System.out.println("Ya existe un local con ese nombre.");
            return;
        }
        String horaApertura = leerTexto("Hora de apertura: ");
        String horaSalida = leerTexto("Hora de cierre: ");
        club.agregarLocal(new Local(nombre, horaApertura, horaSalida));
        System.out.println("Local registrado correctamente.");
    }

    private void listarLocales() {
        List<Local> locales = club.getLocales();
        if (locales.isEmpty()) {
            System.out.println("No hay locales registrados.");
            return;
        }
        System.out.println("\n===== LOCALES REGISTRADOS =====");
        for (int i = 0; i < locales.size(); i++) {
            Local local = locales.get(i);
            System.out.println("---------------------------");
            System.out.println("Número: " + (i + 1));
            System.out.println("Nombre: " + local.getNombre());
            System.out.println("Hora de apertura: " + local.getHoraApertura());
            System.out.println("Hora de cierre: " + local.getHoraSalida());
        }
    }

    private Local seleccionarLocal() {
        List<Local> locales = club.getLocales();
        if (locales.isEmpty()) {
            System.out.println("No hay locales registrados.");
            return null;
        }
        listarLocales();
        int posicion = leerEntero("Seleccione el número del local: ");
        if (posicion < 1 || posicion > locales.size()) {
            System.out.println("Número de local inválido.");
            return null;
        }
        return locales.get(posicion - 1);
    }

    private void registrarProducto() {
        System.out.println("\n===== REGISTRAR PRODUCTO =====");
        Local local = seleccionarLocal();
        if (local == null) return;
        String nombre = leerTexto("Nombre del producto: ");
        for (Producto producto : local.getProductos()) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Ya existe ese producto en el local.");
                return;
            }
        }
        double precio = leerDecimal("Precio del producto: ");
        if (precio <= 0) {
            System.out.println("El precio debe ser mayor que cero.");
            return;
        }
        String categoria = leerTexto("Categoría del producto: ");
        int stock = leerEntero("Cantidad inicial en inventario: ");
        if (stock < 0) {
            System.out.println("El stock no puede ser negativo.");
            return;
        }
        local.agregarProducto(new Producto(nombre, precio, categoria, stock));
        System.out.println("Producto registrado correctamente.");
    }

    private void listarProductos() {
        System.out.println("\n===== PRODUCTOS DEL LOCAL =====");
        Local local = seleccionarLocal();
        if (local == null) return;
        if (local.getProductos().isEmpty()) {
            System.out.println("Este local no tiene productos registrados.");
            return;
        }
        listarProductosDeLocal(local);
    }

    private void listarProductosDeLocal(Local local) {
        List<Producto> productos = local.getProductos();
        System.out.println("\nProductos disponibles:");
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            System.out.println((i + 1) + ". " + producto.getNombre()
                    + " | Categoría: " + producto.getCategoria()
                    + " | Precio: $" + producto.getPrecio()
                    + " | Stock: " + producto.getStock());
        }
    }

    private void reabastecerProducto() {
        System.out.println("\n===== REABASTECER PRODUCTO =====");
        Local local = seleccionarLocal();
        if (local == null) return;
        List<Producto> productos = local.getProductos();
        if (productos.isEmpty()) {
            System.out.println("Este local no tiene productos registrados.");
            return;
        }
        listarProductosDeLocal(local);
        int posicion = leerEntero("Seleccione el producto: ");
        if (posicion < 1 || posicion > productos.size()) {
            System.out.println("Número de producto inválido.");
            return;
        }
        int cantidad = leerEntero("Cantidad que desea agregar: ");
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }
        Producto producto = productos.get(posicion - 1);
        local.reabastecerProducto(producto, cantidad);
        System.out.println("Producto reabastecido correctamente.");
        System.out.println("Nuevo stock: " + producto.getStock());
    }

    private void eliminarProducto() {
        System.out.println("\n===== ELIMINAR PRODUCTO =====");
        Local local = seleccionarLocal();
        if (local == null) return;
        List<Producto> productos = local.getProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos para eliminar.");
            return;
        }
        listarProductosDeLocal(local);
        int posicion = leerEntero("Seleccione el producto que desea eliminar: ");
        if (posicion < 1 || posicion > productos.size()) {
            System.out.println("Número de producto inválido.");
            return;
        }
        local.eliminarProducto(productos.get(posicion - 1));
        System.out.println("Producto eliminado correctamente.");
    }

    private void registrarVenta() {
        System.out.println("\n===== REGISTRAR VENTA =====");
        Local local = seleccionarLocal();
        if (local == null) return;
        if (local.getProductos().isEmpty()) {
            System.out.println("El local no tiene productos registrados.");
            return;
        }

        System.out.println("\nTipo de comprador:");
        System.out.println("1. Jugador");
        System.out.println("2. Empleado");
        int tipoComprador = leerEntero("Seleccione una opción: ");
        Usuario comprador;

        if (tipoComprador == 1) {
            List<Jugador> jugadores = club.getJugadores();
            if (jugadores.isEmpty()) {
                System.out.println("No hay jugadores registrados.");
                return;
            }
            for (int i = 0; i < jugadores.size(); i++) {
                System.out.println((i + 1) + ". " + jugadores.get(i).getNombre()
                        + " - " + jugadores.get(i).getCodigoSocio());
            }
            int posicion = leerEntero("Seleccione el jugador: ");
            if (posicion < 1 || posicion > jugadores.size()) {
                System.out.println("Número de jugador inválido.");
                return;
            }
            comprador = jugadores.get(posicion - 1);
        } else if (tipoComprador == 2) {
            List<EmpleadoClub> empleados = club.getEmpleados();
            if (empleados.isEmpty()) {
                System.out.println("No hay empleados registrados.");
                return;
            }
            for (int i = 0; i < empleados.size(); i++) {
                System.out.println((i + 1) + ". " + empleados.get(i).getNoEmpleado());
            }
            int posicion = leerEntero("Seleccione el empleado: ");
            if (posicion < 1 || posicion > empleados.size()) {
                System.out.println("Número de empleado inválido.");
                return;
            }
            comprador = empleados.get(posicion - 1);
        } else {
            System.out.println("Tipo de comprador inválido.");
            return;
        }

        Venta venta = new Venta("cafeteria", comprador);
        int continuar = 1;
        while (continuar == 1) {
            listarProductosDeLocal(local);
            int posicion = leerEntero("Seleccione el producto: ");
            List<Producto> productos = local.getProductos();
            if (posicion < 1 || posicion > productos.size()) {
                System.out.println("Número de producto inválido.");
                return;
            }
            Producto producto = productos.get(posicion - 1);
            int cantidad = leerEntero("Cantidad: ");
            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser mayor que cero.");
                continue;
            }
            int cantidadEnVenta = 0;
            for (Producto productoVenta : venta.getProductos()) {
                if (productoVenta == producto) cantidadEnVenta++;
            }
            if (cantidad + cantidadEnVenta > producto.getStock()) {
                System.out.println("No hay suficientes unidades disponibles.");
                continue;
            }
            venta.agregarProducto(producto, cantidad);
            System.out.println("Producto agregado a la venta.");
            continuar = leerEntero("¿Desea agregar otro producto? (1. Sí / 0. No): ");
            if (continuar != 0 && continuar != 1) {
                System.out.println("Opción no válida. Se continuará con la venta.");
                continuar = 1;
            }
        }

        if (venta.getProductos().isEmpty()) {
            System.out.println("No se registró la venta porque está vacía.");
            return;
        }
        double porcentajePropina = leerDecimal(
                "Porcentaje de propina (ingrese 0 si no aplica): ");
        if (porcentajePropina < 0) {
            System.out.println("La propina no puede ser negativa.");
            return;
        }
        venta.calcularPropina(porcentajePropina / 100.0);
        local.registrarVenta(venta);

        System.out.println("\n===== VENTA REGISTRADA =====");
        System.out.println("Comprador: " + comprador.getNombre());
        System.out.println("Local: " + local.getNombre());
        System.out.println("Subtotal: $" + venta.getSubTotal());
        System.out.println("Impuesto: $" + venta.getImpuesto());
        System.out.println("Descuento: $" + venta.getDescuento());
        System.out.println("Propina: $" + venta.getPropina());
        System.out.println("TOTAL: $" + venta.getTotal());
        System.out.println("Puntos generados: " + venta.getPuntosGenerados());
    }

    private void consultarVentas() {
        System.out.println("\n===== CONSULTAR VENTAS =====");
        Local local = seleccionarLocal();
        if (local == null) return;
        List<Venta> ventas = local.getVentas();
        if (ventas.isEmpty()) {
            System.out.println("Este local todavía no tiene ventas.");
            return;
        }
        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            System.out.println("---------------------------");
            System.out.println("Venta número: " + (i + 1));
            System.out.println("Fecha: " + venta.getFecha());
            System.out.println("Comprador: " + venta.getComprador().getNombre());
            System.out.println("Subtotal: $" + venta.getSubTotal());
            System.out.println("Impuesto: $" + venta.getImpuesto());
            System.out.println("Descuento: $" + venta.getDescuento());
            System.out.println("Propina: $" + venta.getPropina());
            System.out.println("Total: $" + venta.getTotal());
            System.out.println("Puntos generados: " + venta.getPuntosGenerados());
        }
    }

    // =========================================
    // GESTIÓN DE TORNEOS
    // =========================================

    private void menuTorneos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n===== GESTIÓN DE TORNEOS =====");
            System.out.println("1. Crear torneo");
            System.out.println("2. Listar torneos");
            System.out.println("3. Buscar torneo por nombre");
            System.out.println("4. Consultar torneos por estado");
            System.out.println("5. Iniciar torneo");
            System.out.println("6. Inscribir participante");
            System.out.println("7. Desinscribir participante");
            System.out.println("8. Registrar encuentro y resultado");
            System.out.println("9. Finalizar torneo");
            System.out.println("10. Consultar historial");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1: crearTorneoConsola(); break;
                case 2: listarTorneos(); break;
                case 3: buscarTorneoConsola(); break;
                case 4: consultarTorneosEstado(); break;
                case 5: iniciarTorneoConsola(); break;
                case 6: inscribirParticipanteConsola(); break;
                case 7: desinscribirParticipanteConsola(); break;
                case 8: registrarEncuentroConsola(); break;
                case 9: finalizarTorneoConsola(); break;
                case 10: consultarHistorialTorneo(); break;
                case 0: break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private void crearTorneoConsola() {
        try {
            System.out.println("\n===== CREAR TORNEO =====");
            String nombre = leerTexto("Nombre del torneo: ");
            if (club.consultarTorneo(nombre) != null) {
                System.out.println("Ya existe un torneo con ese nombre.");
                return;
            }
            System.out.println("1. Fútbol\n2. Voleibol\n3. Baloncesto\n4. Pádel\n5. Tenis\n6. Tenis de mesa");
            int op = leerEntero("Seleccione el deporte: ");
            Deporte deporte;
            boolean grupal;
            switch (op) {
                case 1: deporte = new Futbol("Fútbol", 7, 11); grupal = true; break;
                case 2: deporte = new Voleibol("Voleibol", 6, 12); grupal = true; break;
                case 3: deporte = new Baloncesto("Baloncesto", 5, 12); grupal = true; break;
                case 4: deporte = new Padel("Pádel"); grupal = false; break;
                case 5: deporte = new Tenis("Tenis"); grupal = false; break;
                case 6: deporte = new TenisMesa("Tenis de mesa"); grupal = false; break;
                default: System.out.println("Deporte no válido."); return;
            }
            String categoria = leerTexto("Categoría: ");
            int cupos = leerEntero("Número de cupos: ");
            if (cupos <= 0) { System.out.println("Los cupos deben ser mayores que cero."); return; }
            boolean dobles = false;
            if (!grupal) {
                int d = leerEntero("¿Es modalidad dobles? (1. Sí / 0. No): ");
                if (d != 0 && d != 1) { System.out.println("Opción inválida."); return; }
                dobles = d == 1;
            }
            Date fecha = leerFecha("Fecha de inicio (yyyy-MM-dd): ");
            System.out.println("1. Competitivo\n2. Recreativo");
            int tipo = leerEntero("Tipo de torneo: ");
            Torneo torneo;
            if (tipo == 1) {
                double tarifa = leerDecimal("Tarifa de inscripción: ");
                if (tarifa < 0) { System.out.println("La tarifa no puede ser negativa."); return; }
                torneo = new TorneoCompetitivo(nombre, deporte, categoria, cupos, dobles, fecha, tarifa);
            } else if (tipo == 2) {
                double porcentaje = leerDecimal("Porcentaje de bono (ej. 10): ");
                if (porcentaje < 0 || porcentaje > 100) { System.out.println("Porcentaje inválido."); return; }
                torneo = new TorneoRecreativo(nombre, deporte, categoria, cupos, dobles, fecha, porcentaje / 100.0);
            } else { System.out.println("Tipo de torneo inválido."); return; }
            club.crearTorneo(torneo);
            System.out.println("Torneo creado correctamente.");
        } catch (ParseException e) {
            System.out.println("La fecha debe tener el formato yyyy-MM-dd.");
        }
    }

    private void listarTorneos() {
        List<Torneo> torneos = club.getTorneos();
        if (torneos.isEmpty()) { System.out.println("No hay torneos registrados."); return; }
        System.out.println("\n===== TORNEOS REGISTRADOS =====");
        for (int i = 0; i < torneos.size(); i++) {
            Torneo t = torneos.get(i);
            System.out.println("---------------------------");
            System.out.println("Número: " + (i + 1) + " | Nombre: " + t.getNombre());
            System.out.println("Deporte: " + t.getDeporte().obtenerNombre());
            System.out.println("Categoría: " + t.getCategoria() + " | Estado: " + t.getEstado());
            System.out.println("Fecha: " + t.getFechaInicio() + " | Cupos disponibles: " + t.getCuposDisponibles());
        }
    }

    private Torneo seleccionarTorneo() {
        List<Torneo> torneos = club.getTorneos();
        if (torneos.isEmpty()) { System.out.println("No hay torneos registrados."); return null; }
        listarTorneos();
        int p = leerEntero("Seleccione el número del torneo: ");
        if (p < 1 || p > torneos.size()) { System.out.println("Número inválido."); return null; }
        return torneos.get(p - 1);
    }

    private void buscarTorneoConsola() {
        Torneo t = club.consultarTorneo(leerTexto("Nombre del torneo: "));
        if (t == null) { System.out.println("No existe ese torneo."); return; }
        System.out.println(t.consultarHistorial());
        System.out.println("Cupos disponibles: " + t.getCuposDisponibles());
        if (t instanceof TorneoCompetitivo) {
            System.out.println("Total recaudado: $" + ((TorneoCompetitivo) t).getTotalRecaudado());
        }
    }

    private void consultarTorneosEstado() {
        System.out.println("1. Por iniciar\n2. En curso\n3. Finalizado");
        int op = leerEntero("Seleccione el estado: ");
        String estado;
        if (op == 1) estado = Torneo.POR_INICIAR;
        else if (op == 2) estado = Torneo.EN_CURSO;
        else if (op == 3) estado = Torneo.FINALIZADO;
        else { System.out.println("Estado inválido."); return; }
        club.consultarTorneosPorEstado(estado);
    }

    private void iniciarTorneoConsola() {
        Torneo t = seleccionarTorneo();
        if (t == null) return;
        club.iniciarTorneo(t.getNombre());
        System.out.println("Estado actual: " + t.getEstado());
    }

    private List<Participante> participantesDisponibles(Torneo t) {
        List<Participante> lista = new java.util.ArrayList<>();
        if (t.getDeporte() instanceof DeporteGrupal) {
            lista.addAll(club.getEquipos());
        } else if (t.isEsDobles()) {
            List<Jugador> jugadores = club.getJugadores();
            for (int i = 0; i < jugadores.size(); i++) {
                for (int j = i + 1; j < jugadores.size(); j++) {
                    lista.add(new Pareja(jugadores.get(i), jugadores.get(j)));
                }
            }
        } else {
            lista.addAll(club.getJugadores());
            lista.addAll(club.getEmpleados());
        }
        return lista;
    }

    private void mostrarParticipantes(List<Participante> lista) {
        for (int i = 0; i < lista.size(); i++) System.out.println((i + 1) + ". " + lista.get(i).getNombre());
    }

    private Participante elegirParticipante(Torneo t) {
        List<Participante> disponibles = participantesDisponibles(t);
        if (disponibles.isEmpty()) { System.out.println("No hay participantes compatibles registrados."); return null; }
        System.out.println("Participantes disponibles:");
        mostrarParticipantes(disponibles);
        int p = leerEntero("Seleccione el participante: ");
        if (p < 1 || p > disponibles.size()) { System.out.println("Número inválido."); return null; }
        return disponibles.get(p - 1);
    }

    private void inscribirParticipanteConsola() {
        Torneo t = seleccionarTorneo();
        if (t == null) return;
        Participante p = elegirParticipante(t);
        if (p == null) return;
        club.inscribirEnTorneo(t.getNombre(), p);
        if (t.getInscritos().contains(p)) System.out.println("Participante inscrito correctamente.");
        else System.out.println("No se pudo inscribir. Verifique el estado, los cupos y los requisitos.");
    }

    private void desinscribirParticipanteConsola() {
        Torneo t = seleccionarTorneo();
        if (t == null) return;
        List<Participante> inscritos = t.getInscritos();
        if (inscritos.isEmpty()) { System.out.println("No hay participantes inscritos."); return; }
        mostrarParticipantes(inscritos);
        int p = leerEntero("Seleccione el participante que desea retirar: ");
        if (p < 1 || p > inscritos.size()) { System.out.println("Número inválido."); return; }
        Participante elegido = inscritos.get(p - 1);
        club.desinscribirDeTorneo(t.getNombre(), elegido);
        if (!t.getInscritos().contains(elegido)) System.out.println("Participante retirado correctamente.");
        else System.out.println("No se pudo retirar al participante.");
    }

    private void registrarEncuentroConsola() {
        Torneo t = seleccionarTorneo();
        if (t == null) return;
        List<Participante> inscritos = t.getInscritos();
        if (!t.estaEnCurso()) { System.out.println("El torneo debe estar En curso."); return; }
        if (inscritos.size() < 2) { System.out.println("Se necesitan dos participantes inscritos."); return; }
        mostrarParticipantes(inscritos);
        int a = leerEntero("Participante A: ");
        int b = leerEntero("Participante B: ");
        if (a < 1 || a > inscritos.size() || b < 1 || b > inscritos.size() || a == b) { System.out.println("Selección inválida."); return; }
        try {
            Date fecha = leerFecha("Fecha del encuentro (yyyy-MM-dd): ");
            int ronda = leerEntero("Número de ronda: ");
            if (ronda <= 0) { System.out.println("Ronda inválida."); return; }
            EncuentroTorneo e = new EncuentroTorneo(inscritos.get(a - 1), inscritos.get(b - 1), fecha, ronda);
            String resultado = leerTexto("Resultado (ej. 2-1): ");
            System.out.println("1. " + inscritos.get(a - 1).getNombre());
            System.out.println("2. " + inscritos.get(b - 1).getNombre());
            int g = leerEntero("Seleccione el ganador: ");
            if (g != 1 && g != 2) { System.out.println("Ganador inválido."); return; }
            e.registrarResultado(resultado, inscritos.get(g == 1 ? a - 1 : b - 1));
            club.registrarEncuentro(t.getNombre(), e);
            System.out.println("Encuentro registrado correctamente.");
        } catch (ParseException ex) { System.out.println("Fecha inválida. Use yyyy-MM-dd."); }
    }

    private void finalizarTorneoConsola() {
        Torneo t = seleccionarTorneo();
        if (t == null) return;
        if (!t.estaEnCurso()) { System.out.println("El torneo debe estar En curso."); return; }
        List<Participante> inscritos = t.getInscritos();
        if (inscritos.isEmpty()) { System.out.println("No hay participantes inscritos."); return; }
        mostrarParticipantes(inscritos);
        int cantidad = leerEntero("¿Cuántas posiciones desea registrar?: ");
        if (cantidad < 1 || cantidad > inscritos.size()) { System.out.println("Cantidad inválida."); return; }
        List<Participante> posiciones = new java.util.ArrayList<>();
        for (int i = 1; i <= cantidad; i++) {
            int p = leerEntero("Número del participante que ocupa el puesto " + i + ": ");
            if (p < 1 || p > inscritos.size()) { System.out.println("Participante inválido."); return; }
            Participante elegido = inscritos.get(p - 1);
            if (posiciones.contains(elegido)) { System.out.println("No puede repetir participantes."); return; }
            posiciones.add(elegido);
        }
        club.finalizarTorneo(t.getNombre(), posiciones);
        System.out.println("Estado actual: " + t.getEstado());
    }

    private void consultarHistorialTorneo() {
        Torneo t = seleccionarTorneo();
        if (t != null) System.out.println(t.consultarHistorial());
    }

    // CONSULTA GENERAL

    // =========================================

    private void consultarInformacion() {

        System.out.println("\n===== INFORMACIÓN DEL CLUB =====");

        System.out.println("Empleados: "

                + club.getEmpleados().size());

        System.out.println("Jugadores: "

                + club.getJugadores().size());

        System.out.println("Equipos: "

                + club.getEquipos().size());

        System.out.println("Locales: "

                + club.getLocales().size());

        System.out.println("Torneos: "

                + club.getTorneos().size());

    }

    // =========================================

    // METODOS AUXILIARES

    // =========================================

    private int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            try {

                return Integer.parseInt(

                        scanner.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Debe ingresar un número válido.");

            }

        }

    }

    private String leerTexto(String mensaje) {

        System.out.print(mensaje);

        return scanner.nextLine().trim();

    }

    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    private Date leerFecha(String mensaje) throws ParseException {

        System.out.print(mensaje);

        String fechaTexto = scanner.nextLine().trim();

        SimpleDateFormat formato =

                new SimpleDateFormat("yyyy-MM-dd");

        formato.setLenient(false);

        return formato.parse(fechaTexto);

    }

}