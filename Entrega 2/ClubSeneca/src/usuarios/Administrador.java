package usuarios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cafeteria.Local;
import cafeteria.Producto;
import cafeteria.Venta;
import competencias.Partido;
import equipos.Equipo;

public class Administrador extends EmpleadoClub{
	private static final int STOCK_MINIMO = 5;
	
	private HashMap<String, EmpleadoClub> empleados;
    private HashMap<String, Jugador> jugadores;
    private HashMap<String, Equipo> equipos;
    private HashMap<String, Local> locales;
	
	public Administrador(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, int edad, String noEmpleado, HashMap<String, EmpleadoClub> empleados, 
			HashMap<String, Jugador> jugadores, HashMap<String, Equipo> equipos, HashMap<String, Local> locales) {
		super(id, nombre, email, login, password, telefono, fechaNacimiento, edad, noEmpleado);
		this.empleados = new HashMap<>();
		this.jugadores = new HashMap<>();
		this.equipos = new HashMap<>();
		this.locales = new HashMap<>();
	}

	public static int getStockMinimo() {
		return STOCK_MINIMO;
	}

	public HashMap<String, EmpleadoClub> getEmpleados() {
		return empleados;
	}

	public HashMap<String, Jugador> getJugadores() {
		return jugadores;
	}

	public HashMap<String, Equipo> getEquipos() {
		return equipos;
	}

	public HashMap<String, Local> getLocales() {
		return locales;
	}

	public void agregarEmpleado(EmpleadoClub empleado) {
		if (!empleados.containsKey(empleado.getNoEmpleado())) {
			empleados.put(empleado.getNoEmpleado(), empleado);
		}
	}

	public void sacarEmpleado(EmpleadoClub empleado) {
		empleados.remove(empleado.getNoEmpleado());
	}
	
	public void crearJugador(Jugador jugador) {
		if (jugadores.containsKey(jugador.getCodigoSocio())) {
			System.out.println("Ya existe un jugador con el codigo " + jugador.getCodigoSocio());
			return;
		}
		jugadores.put(jugador.getCodigoSocio(), jugador);
	}

	public void sacarJugador(Jugador jugador) {
		jugadores.remove(jugador.getCodigoSocio());
	}

	public Jugador consultarJugador(String codigoSocio) {
		return jugadores.get(codigoSocio);
	}
	
	public void crearEquipo(Equipo equipo) {
		if (equipos.containsKey(equipo.getNombre())) {
			System.out.println("Ya existe un equipo llamado " + equipo.getNombre());
			return;
		}
		equipos.put(equipo.getNombre(), equipo);
	}

	public void sacarEquipo(Equipo equipo) {
		equipos.remove(equipo.getNombre());
	}

	public Equipo consultarEquipo(String nombre) {
		return equipos.get(nombre);
	}

	public void agregarLocal(Local local) {
		if (!locales.containsKey(local.getNombre())) {
			locales.put(local.getNombre(), local);
		}
	}

	public Local consultarLocal(String nombre) {
		return locales.get(nombre);
	}

	public boolean aprobarSugerencia(Producto producto, String nombreLocal) {
		Local local = locales.get(nombreLocal);
		if (local == null) {
			System.out.println("No existe el local " + nombreLocal);
			return false;
		}
		if (producto.getPrecio() <= 0) {
			System.out.println("El precio de " + producto.getNombre() + " no es valido");
			return false;
		}
		if (local.getProductos().contains(producto)) {
			System.out.println(producto.getNombre() + " ya esta en " + nombreLocal);
			return false;
		}
		local.agregarProducto(producto);
		return true;
	}

	public void organizarInventario(String nombreLocal) {
		Local local = locales.get(nombreLocal);
		if (local == null) {
			System.out.println("No existe el local " + nombreLocal);
			return;
		}

		List<Producto> inventario = new ArrayList<>(local.getProductos());
		inventario.sort((a, b) -> a.getStock() - b.getStock());

		System.out.println("Inventario de " + nombreLocal + ":");
		for (Producto producto : inventario) {
			String aviso = producto.getStock() < STOCK_MINIMO ? "  <-- REABASTECER" : "";
			System.out.println(producto.getNombre() + ": " + producto.getStock() + aviso);
		}
	}

	public void generarReporteVentas(String periodo) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM");
		double totalPeriodo = 0;
		int cantidadVentas = 0;

		for (Local local : locales.values()) {
			double totalLocal = 0;
			for (Venta venta : local.getVentas()) {
				if (formato.format(venta.getFecha()).equals(periodo)) {
					totalLocal += venta.getTotal();
					cantidadVentas++;
				}
			}
			System.out.println(local.getNombre() + ": $" + totalLocal);
			totalPeriodo += totalLocal;
		}
		System.out.println("Ventas en " + periodo + ": " + cantidadVentas + " | Total: $" + totalPeriodo);
	}

	public void generarReporteDeportivo(String periodo, Competencia torneo) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM");
		int jugados = 0;

		for (Partido partido : torneo.getPartidos()) {
			if (formato.format(partido.getFecha()).equals(periodo) && partido.getEstado().equals("Finalizado")) {
				System.out.println(partido.getEquipoLocal().getNombre() + " " + partido.getMarcadorLocal()
						+ " - " + partido.getMarcadorVisitante() + " " + partido.getEquipoVisitante().getNombre());
				jugados++;
			}
		}
		System.out.println("Partidos jugados en " + periodo + ": " + jugados);

		torneo.actualizarTablaPosiciones();
		System.out.println("Tabla de posiciones:");
		for (Equipo equipo : torneo.consultarTablaPosiciones()) {
			System.out.println(torneo.determinarPosicion(equipo) + ". " + equipo.getNombre()
					+ " - " + torneo.calcularPuntosEquipo(equipo) + " pts");
		}
	}
}
