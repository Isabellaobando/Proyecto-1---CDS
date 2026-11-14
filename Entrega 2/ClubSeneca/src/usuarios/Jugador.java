package usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cafeteria.BonoDescuento;
import competencias.Torneo;
import deportes.Deporte;
import deportes.DeporteIndividual;
import deportes.InscripcionDeporteIndividual;
import equipos.Equipo;
import equipos.InscripcionEquipo;

public class Jugador extends Usuario implements Participante{
	private String codigoSocio;
	private Date fechaAfiliacion;
	private int puntosLealtad;
	private List<InscripcionDeporteIndividual> deportesIndividuales;
	private List<InscripcionEquipo> equipos;
	public static final String TIPO_SOCIO = "Socio";
	public static final String TIPO_ESTUDIANTE = "Estudiante";
	public static final String TIPO_EXTERNO = "Externo";
	private String tipo;
	private List<BonoDescuento> bonos;

	public Jugador(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, String codigoSocio, Date fechaAfiliacion, int puntosLealtad, int edad, String tipo) 
	{
		super(id, nombre, email, login, password, telefono, fechaNacimiento, edad);
		this.codigoSocio = codigoSocio;
		this.fechaAfiliacion = fechaAfiliacion;
		this.puntosLealtad = puntosLealtad;
		this.tipo = tipo;
		this.deportesIndividuales = new ArrayList<>();
		this.equipos = new ArrayList<>();
		this.bonos = new ArrayList<>();
	}

	public String getTipo() 
	{
		return tipo;
	}

	public String getCodigoSocio() 
	{
		return this.codigoSocio;
	}

	public boolean inscribirseADeporte(DeporteIndividual deporte, String nivel) 
	{
		InscripcionDeporteIndividual inscripcion = new InscripcionDeporteIndividual(deporte, nivel, 0, this);
		boolean estadoInscripcion = inscripcion.inscribirJugador();
		if(estadoInscripcion) {
			this.deportesIndividuales.add(inscripcion);
			return true;
		}
		else {
			return false;
		}

	}

	public boolean inscribirseAEquipo(String posicion, int numCamisa, boolean titular, Date fechaInscripcion,
			Equipo equipo)
	{
		InscripcionEquipo inscripcion = new InscripcionEquipo(posicion, numCamisa, titular, false, fechaInscripcion , equipo, this);
		if (!InscripcionEquipo.sePuedeInscribir(this, equipo))
		{
			return false;
		}
		else
		{
			boolean estado = inscripcion.inscribirJugador();
			if (estado)
			{
				this.equipos.add(inscripcion);
				return true;
			}

			else
			{
				return false;
			}
		}
	}

	public List<DeporteIndividual> consultarDeportes()
	{
		List<DeporteIndividual> deportes = new ArrayList<>();
		for (InscripcionDeporteIndividual inscripcion : this.deportesIndividuales)
		{
			deportes.add(inscripcion.consultarDeporte());
		}
		return deportes;
	}


	public List<Equipo> consultarEquipos()
	{
		List<Equipo> listaEquipos = new ArrayList<>();
		for (InscripcionEquipo inscripcion : this.equipos)
		{
			listaEquipos.add(inscripcion.getEquipo());
		}
		return listaEquipos;
	}

	private InscripcionDeporteIndividual buscarInscripcionDeporte(Deporte deporte)
	{
		for (InscripcionDeporteIndividual inscripcion : this.deportesIndividuales)
		{
			if (inscripcion.consultarDeporte() == deporte)
			{
				return inscripcion;
			}
		}
		return null;
	}

	public Date getFechaAfiliacion()
	{
		return fechaAfiliacion;
	}

	public int getPuntosLealtad()
	{
		return puntosLealtad;
	}


	@Override
	public double getRanking(Deporte deporte) 
	{
		InscripcionDeporteIndividual inscripcion = buscarInscripcionDeporte(deporte);
		if (inscripcion == null)
		{
			return 0;
		}
		else
		{
			return inscripcion.consultarElo();
		}
	}

	@Override
	public boolean puedeInscribirse(Torneo torneo) 
	{
		if (!(torneo.getDeporte() instanceof DeporteIndividual))
		{
			return false;
		}
		InscripcionDeporteIndividual inscripcion = buscarInscripcionDeporte(torneo.getDeporte());
		if (inscripcion == null)
		{
			return true;
		}
		return inscripcion.consultarNivel().equalsIgnoreCase(torneo.getCategoria());
	}

	@Override
	public List<Usuario> getIntegrantes() 
	{
		List<Usuario> integrantes = new ArrayList<>();
		integrantes.add(this);
		return integrantes;
	}

	public void agregarBono(BonoDescuento bono) 
	{
		if (bono != null)
		{
			this.bonos.add(bono);
		}
	}

	public List<BonoDescuento> getBonosDisponibles() 
	{
		List<BonoDescuento> disponibles = new ArrayList<>();
		for (BonoDescuento bono : this.bonos)
		{
			if (!bono.getRedimido())
			{
				disponibles.add(bono);
			}
		}
		return disponibles;
	}

	public BonoDescuento usarBono(String codigo) 
	{
		for (BonoDescuento bono : getBonosDisponibles())
		{
			if (bono.getCodigo().equals(codigo))
			{
				return bono;
			}
		}
		return null;
	}
}
