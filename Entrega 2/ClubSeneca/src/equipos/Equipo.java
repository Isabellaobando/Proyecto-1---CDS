package equipos;

import java.util.ArrayList;
import java.util.List;

import competencias.Partido;
import competencias.Torneo;
import deportes.Deporte;
import deportes.DeporteGrupal;
import usuarios.Entrenador;
import usuarios.Participante;
import usuarios.Usuario;


public class Equipo implements Participante 
{
	private String categoria;
	private String nombre;
	private boolean activo;
	private DeporteGrupal deporte;
	private List<Partido> proximosPartido;
	private List<InscripcionEquipo> jugadores;
	private Entrenador entrenador;
	private double desempeno;

	public Equipo(String categoria, String nombre, boolean activo, DeporteGrupal deporte) 
	{
		this.categoria = categoria;
		this.nombre = nombre;
		this.activo = false;
		this.deporte = deporte;
		this.proximosPartido = new ArrayList<>();
		this.jugadores = new ArrayList<>();
	}

	public boolean isActivo() 
	{
		return activo;
	}

	public void setActivo(boolean activo) 
	{
		this.activo = activo;
	}

	public List<Partido> getProximosPartido() 
	{
		return proximosPartido;
	}

	public void setProximosPartido(List<Partido> proximosPartido) 
	{
		this.proximosPartido = proximosPartido;
	}

	public Entrenador getEntrenador() 
	{
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador) 
	{
		this.entrenador = entrenador;
	}

	public boolean agregarJugador(InscripcionEquipo inscripcionJugador) 
	{
		String codigoJugador = inscripcionJugador.getJugador().getCodigoSocio();
		if(! tieneCupoDisponible() || consultarJugador(codigoJugador) != null) 
		{
			return false;
		}
		else 
		{
			this.jugadores.add(inscripcionJugador);
			if (puedeCompetir()) 
			{
				this.activo = true;
			}
			return true;
		}
	}

	public boolean retirarJugador(InscripcionEquipo inscripcionJugador) 
	{
		String codigoSocio = inscripcionJugador.getJugador().getCodigoSocio();
		InscripcionEquipo inscripcion = consultarJugador(codigoSocio);
		if(inscripcion == null) 
		{
			return false;
		}
		else 
		{
			this.jugadores.remove(inscripcion);
			if (!puedeCompetir()) 
			{
				this.activo = false;
			}
			return true;
		}
	}

	public List<InscripcionEquipo> consultarJugadores()
	{
		List<InscripcionEquipo> listaJugadores = new ArrayList<>(this.jugadores);
		return listaJugadores;
	}

	public InscripcionEquipo consultarJugador(String codigoSocio) 
	{
		for (InscripcionEquipo inscripcion : this.jugadores) 
		{
			if (inscripcion.getJugador().getCodigoSocio().equals(codigoSocio)) 
			{
				return inscripcion;
			}
		}
		return null;
	}

	public boolean tieneCupoDisponible()
	{
		if(this.jugadores.size() >= this.deporte.getMaxJugadores()) 
		{
			return false;
		} 
		else 
		{
			return true;
		}
	}

	public boolean puedeCompetir() 
	{
		if (this.jugadores.size() >= this.deporte.getMinJugadores()) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}


	public int obtenerCantidadJugadoresHabilitados() 
	{
		List<InscripcionEquipo> listaJugadores = consultarJugadores();
		int contador = 0;
		for(InscripcionEquipo jugador: listaJugadores) 
		{
			if (!jugador.getSuspendido()) 
			{
				contador ++;
			}
		}
		return contador;
	}

	public String getCategoria() 
	{
		return this.categoria;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public double getDesempeno() 
	{
		return desempeno;
	}

	public void setDesempeno(double desempeno) 
	{
		this.desempeno = desempeno;
	}

	@Override
	public double getRanking(Deporte deporte) 
	{
		return this.desempeno;
	}

	@Override
	public boolean puedeInscribirse(Torneo torneo) 
	{
		if (torneo.getDeporte() != this.deporte) 
		{
			return false;
		}
		if (!this.categoria.equalsIgnoreCase(torneo.getCategoria())) 
		{
			return false;
		}
		return puedeCompetir();
	}

	@Override
	public List<Usuario> getIntegrantes() 
	{
		List<Usuario> integrantes = new ArrayList<>();
		for (InscripcionEquipo inscripcion : this.jugadores) 
		{
			integrantes.add(inscripcion.getJugador());
		}
		return integrantes;
	}
}
