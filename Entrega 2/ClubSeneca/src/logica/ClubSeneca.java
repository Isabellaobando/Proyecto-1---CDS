package logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cafeteria.Local;
import cafeteria.Producto;
import cafeteria.Venta;
import competencias.Competencia;
import competencias.CompetenciaASCUN;
import competencias.EncuentroTorneo;
import competencias.Partido;
import competencias.Torneo;
import competencias.TorneoDeLosCerros;
import equipos.Equipo;
import usuarios.EmpleadoClub;
import usuarios.Jugador;
import usuarios.Participante;

public class ClubSeneca
{
	private static final int STOCK_MINIMO = 5;
	private List<EmpleadoClub> empleados;
	private List<Jugador> jugadores;
	private List<Equipo> equipos;
	private List<Local> locales;
	private List<Torneo> torneos;

	public ClubSeneca()
	{
		this.empleados = new ArrayList<>();
		this.jugadores = new ArrayList<>();
		this.equipos = new ArrayList<>();
		this.locales = new ArrayList<>();
		this.torneos = new ArrayList<>();
	}

	public List<EmpleadoClub> getEmpleados()
	{
		return empleados;
	}

	public List<Jugador> getJugadores()
	{
		return jugadores;
	}

	public List<Equipo> getEquipos()
	{
		return equipos;
	}

	public List<Local> getLocales()
	{
		return locales;
	}

	public List<Torneo> getTorneos()
	{
		return torneos;
	}

	public void agregarEmpleado(EmpleadoClub empleado)
	{
		for (EmpleadoClub e : empleados)
		{
			if (e.getNoEmpleado().equals(empleado.getNoEmpleado()))
			{
				System.out.println("Ya existe un empleado con el codigo" + empleado.getNoEmpleado());
				return;
			}
		}
		empleados.add(empleado);
	}

	public void sacarEmpleado(EmpleadoClub empleado)
	{
		empleados.remove(empleado);
	}

	public void crearJugador(Jugador jugador)
	{
		if (consultarJugador(jugador.getCodigoSocio()) != null)
		{
			System.out.println("Ya existe un jugador con el codigo " + jugador.getCodigoSocio());
			return;
		}
		jugadores.add(jugador);
	}

	public void sacarJugador(Jugador jugador)
	{
		jugadores.remove(jugador);
	}

	public Jugador consultarJugador(String codigoSocio)
	{
		for (Jugador jugador : jugadores)
		{
			if (jugador.getCodigoSocio().equals(codigoSocio))
			{
				return jugador;
			}
		}
		return null;
	}

	public void crearEquipo(Equipo equipo)
	{
		if (consultarEquipo(equipo.getNombre()) != null)
		{
			System.out.println("Ya existe un equipo llamado " + equipo.getNombre());
			return;
		}
		equipos.add(equipo);
	}

	public void sacarEquipo(Equipo equipo)
	{
		equipos.remove(equipo);
	}

	public Equipo consultarEquipo(String nombre)
	{
		for (Equipo equipo : equipos)
		{
			if (equipo.getNombre().equals(nombre))
			{
				return equipo;
			}
		}
		return null;
	}

	public void agregarLocal(Local local)
	{
		if (consultarLocal(local.getNombre()) == null)
		{
			locales.add(local);
		}
	}

	public Local consultarLocal(String nombre)
	{
		for (Local local : locales)
		{
			if (local.getNombre().equals(nombre))
			{
				return local;
			}
		}
		return null;
	}

	public boolean aprobarSugerencia(Producto producto, String nombreLocal)
	{
		Local local = consultarLocal(nombreLocal);
		if (local == null)
		{
			System.out.println("No existe el local " + nombreLocal);
			return false;
		}
		if (producto.getPrecio() <= 0)
		{
			System.out.println("El precio de " + producto.getNombre() + " no es valido");
			return false;
		}
		if (local.getProductos().contains(producto))
		{
			System.out.println(producto.getNombre() + " ya esta en " + nombreLocal);
			return false;
		}
		local.agregarProducto(producto);
		return true;
	}

	public void organizarInventario(String nombreLocal)
	{
		Local local = consultarLocal(nombreLocal);
		if (local == null)
		{
			System.out.println("No existe el local " + nombreLocal);
			return;
		}

		List<Producto> inventario = new ArrayList<>(local.getProductos());
		inventario.sort((a, b) -> a.getStock() - b.getStock());

		System.out.println("Inventario de " + nombreLocal + ":");
		for (Producto producto : inventario)
		{
			String aviso = producto.getStock() < STOCK_MINIMO ? "  <-- REABASTECER" : "";
			System.out.println(producto.getNombre() + ": " + producto.getStock() + aviso);
		}
	}

	public void generarReporteVentas(String periodo)
	{
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM");
		double totalPeriodo = 0;
		int cantidadVentas = 0;

		for (Local local : locales)
		{
			double totalLocal = 0;
			for (Venta venta : local.getVentas())
			{
				if (formato.format(venta.getFecha()).equals(periodo))
				{
					totalLocal += venta.getTotal();
					cantidadVentas++;
				}
			}
			System.out.println(local.getNombre() + ": $" + totalLocal);
			totalPeriodo += totalLocal;
		}
		System.out.println("Ventas en " + periodo + ": " + cantidadVentas + " | Total: $" + totalPeriodo);
	}

	public void generarReporteDeportivo(String periodo, Competencia competencia)
	{
		System.out.println("Reporte de " + competencia.getNombre() + " - " + periodo);
		System.out.println("Equipos inscritos: " + competencia.consultarEquiposInscritos().size());

		if (competencia instanceof TorneoDeLosCerros)
		{
			TorneoDeLosCerros torneo = (TorneoDeLosCerros) competencia;
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM");
			int jugados = 0;

			for (Partido partido : torneo.getPartidos())
			{
				if (partido.getFecha() != null && formato.format(partido.getFecha()).equals(periodo)
						&& "Finalizado".equals(partido.getEstado()))
				{
					System.out.println(partido.getEquipoLocal().getNombre() + " " + partido.getMarcadorLocal()
							+ " - " + partido.getMarcadorVisitante() + " " + partido.getEquipoVisitante().getNombre());
					jugados++;
				}
			}
			System.out.println("Partidos jugados en " + periodo + ": " + jugados);

			torneo.actualizarTablaPosiciones();
			System.out.println("Tabla de posiciones:");
			for (Equipo equipo : torneo.consultarTablaPosiciones())
			{
				System.out.println(torneo.determinarPosicion(equipo) + ". " + equipo.getNombre()
						+ " - " + torneo.calcularPuntosEquipo(equipo) + " pts");
			}
		}
		else if (competencia instanceof CompetenciaASCUN)
		{
			CompetenciaASCUN ascun = (CompetenciaASCUN) competencia;
			System.out.println("Formato: fase de grupos + eliminacion directa");
			System.out.println("Grupos: " + ascun.getNumGrupos() + " | Equipos por grupo: " + ascun.getEquiposPorGrupo());
			for (Equipo equipo : ascun.consultarEquiposInscritos())
			{
				System.out.println("  " + equipo.getNombre());
			}
		}
	}

	public void crearTorneo(Torneo torneo)
	{
		if (consultarTorneo(torneo.getNombre()) != null)
		{
			System.out.println("Ya existe un torneo llamado " + torneo.getNombre());
			return;
		}
		torneos.add(torneo);
	}

	public Torneo consultarTorneo(String nombre)
	{
		for (Torneo torneo : torneos)
		{
			if (torneo.getNombre().equals(nombre))
			{
				return torneo;
			}
		}
		return null;
	}

	public List<Torneo> consultarTorneosPorEstado(String estado)
	{
		List<Torneo> resultado = new ArrayList<>();
		for (Torneo torneo : torneos)
		{
			if (torneo.getEstado().equals(estado))
			{
				resultado.add(torneo);
			}
		}
		return resultado;
	}

	public void iniciarTorneo(String nombreTorneo)
	{
		Torneo torneo = consultarTorneo(nombreTorneo);
		if (torneo != null)
		{
			torneo.iniciar();
		}
	}

	public boolean inscribirEnTorneo(String nombreTorneo, Participante participante)
	{
		Torneo torneo = consultarTorneo(nombreTorneo);
		if (torneo == null)
		{
			return false;
		}
		return torneo.inscribir(participante);
	}

	public boolean desinscribirDeTorneo(String nombreTorneo, Participante participante)
	{
		Torneo torneo = consultarTorneo(nombreTorneo);
		if (torneo == null)
		{
			return false;
		}
		return torneo.desinscribir(participante);
	}

	public void registrarEncuentro(String nombreTorneo, EncuentroTorneo encuentro)
	{
		Torneo torneo = consultarTorneo(nombreTorneo);
		if (torneo != null)
		{
			torneo.registrarEncuentro(encuentro);
		}
	}

	public void finalizarTorneo(String nombreTorneo, List<Participante> posiciones)
	{
		Torneo torneo = consultarTorneo(nombreTorneo);
		if (torneo != null)
		{
			torneo.finalizar(posiciones);
		}
	}
}