package equipos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import competencias.Partido;
import deportes.DeporteGrupal;
import usuarios.Entrenador;


public class Equipo {
	
	// Atributos
	
	private String categoria;
	
	private String nombre;
	
	// activo = Determina si un equipo puede jugar o no 
	
	private boolean activo;
	
	private DeporteGrupal deporte;
	
	// proximosPartido = muestra cual es el siguiente partido para ese equipo, arrayList sola si no tiene proximos Partidos
	
	private List<Partido> proximosPartido;
	
	// jugadores = un mapa donde la llave es el codigo de socio del jugador del que se quiere saber su informacion. 
	private Map<String, InscripcionEquipo> jugadores;
	
	private Entrenador entrenador;
	
	// TODO: InscripcionCompetencia
	
	// Constructor 

	public Equipo(String categoria, String nombre, boolean activo, DeporteGrupal deporte) {
		this.categoria = categoria;
		this.nombre = nombre;
		this.activo = false;
		this.deporte = deporte;
		this.proximosPartido = new ArrayList<>();
		this.jugadores = new HashMap<>();
	}
	
	// Metodos
	
	/*
	 *  Este metodo agrega la inscripcion del jugador a la informacion del equipo. Si el equipo esta lleno, retorna
	 *  false. Si no esta lleno, agrega el jugador al mapa. Ademas, si se alcanzo el minimo de jugadores, se pone
	 *  activo igual a true
	 */
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<Partido> getProximosPartido() {
		return proximosPartido;
	}

	public void setProximosPartido(List<Partido> proximosPartido) {
		this.proximosPartido = proximosPartido;
	}

	public Entrenador getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}

	public boolean agregarJugador(InscripcionEquipo inscripcionJugador) {
		
		if(! tieneCupoDisponible()) {
			return false;
		}
		
		else {
			String codigoJugador = inscripcionJugador.getJugador().getCodigoSocio();
			this.jugadores.put(codigoJugador, inscripcionJugador);
			if (puedeCompetir()) {
				this.activo = true;
			}
			return true;
		}
		
		
	}
	
	/*
	 * Este metodo quita a un jugador del equipo. Si el equipo esta vacio o no existe el jugador, retorna false, de
	 * resto, devuelve true 
	 */
	
	public boolean retirarJugador(InscripcionEquipo inscripcionJugador) {
		String codigoSocio = inscripcionJugador.getJugador().getCodigoSocio();
		if(! this.jugadores.containsKey(codigoSocio) || this.jugadores.size() == 0) {
			return false;
		}
		
		else {
			this.jugadores.remove(codigoSocio);
			if (!puedeCompetir()) {
				this.activo = false;
				
			}
			return true;
		}
	}
	
	/*
	 * Devuelve una lista cuyos valores son la informacion de los registros de los jugadores.
	 */
	
	public List<InscripcionEquipo> consultarJugadores(){
		List<InscripcionEquipo> listaJugadores = new ArrayList<>(this.jugadores.values());
		return listaJugadores;
	}
	
	
	/*
	 * consultarJugador recibe el codigo socio de un jugador y devuelve la informacion de su inscripcion
	 */
	
	public InscripcionEquipo consultarJugador(String codigoSocio) {
		if (this.jugadores.containsKey(codigoSocio)) {
			return this.jugadores.get(codigoSocio);
		}
		
		else {
			return null;
		}
	}
	
	
	
	// Mira si el equipo se encuentra lleno o no
	
		public boolean tieneCupoDisponible(){
			if(this.jugadores.size() >= this.deporte.getMaxJugadores()) {
				return false;
			} else {
				return true;
			}
		}
	
	
	
	// Mira si el equipo cumple con el minimo de jugadores para competir o hacer parte de partidos
	
	public boolean puedeCompetir() {
		if (this.jugadores.size() >= this.deporte.getMinJugadores()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public int obtenerCantidadJugadoresHabilitados() {
		List<InscripcionEquipo> listaJugadores = consultarJugadores();
		int contador = 0;
		for(InscripcionEquipo jugador: listaJugadores) {
			if (!jugador.getSuspendido()) {
				contador ++;
			}
		}
		
		return contador;
	}
	
	public String getCategoria() {
		return this.categoria;
	}
	
	public String getNombre() {
		return this.nombre;
	}
}
