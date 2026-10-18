package usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import deportes.DeporteIndividual;
import deportes.InscripcionDeporteIndividual;
import equipos.Equipo;
import equipos.InscripcionEquipo;

public class Jugador extends Usuario {
	
	// Atributos
	
	// El codigo del socio
	
	private String codigoSocio;
	
	private Date fechaAfiliacion;
	
	private int puntosLealtad;
	
	// deportesIndividuales = un mapa cuya llave es el deporte, y valor la inscripcion del jugador al deporte
	
	private Map<DeporteIndividual, InscripcionDeporteIndividual> deportesIndividuales;
	
	// equipos = un mapa cuya llave es el equipo, y valor la inscripcion del jugador al deporte
	private Map<Equipo, InscripcionEquipo> equipos;
	
	// Constructor

	public Jugador(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, String codigoSocio, Date fechaAfiliacion, int puntosLealtad, int edad) {
		
		super(id, nombre, email, login, password, telefono, fechaNacimiento, edad);
		this.codigoSocio = codigoSocio;
		this.fechaAfiliacion = fechaAfiliacion;
		this.puntosLealtad = puntosLealtad;
		this.deportesIndividuales = new HashMap<>();
	}
	
	
	// Metodos
	
	public String getCodigoSocio() {
		return this.codigoSocio;
	}
	
	/*
	 * Este metodo Inscribe al jugador a un deporte individual, si reporta True, la inscripcion fue exitosa, si retorna
	 * false, el jugador ya estaba inscrito en ese deporte individual.
	 */
	
	public boolean inscribirseADeporte(DeporteIndividual deporte, String nivel) {
		InscripcionDeporteIndividual inscripcion = new InscripcionDeporteIndividual(deporte, nivel, 0, this);
		boolean estadoInscripcion = inscripcion.inscribirJugador();
		if(estadoInscripcion) {
			this.deportesIndividuales.put(deporte, inscripcion);
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/*
	 * inscribe al jugador del equipo y devuelve true si fue exitosa, o false si: no hay espacio en el equipo, no 
	 * cumple la categoria
	 */
	
	public boolean inscribirseAEquipo(String posicion, int numCamisa, boolean titular, Date fechaInscripcion,
			Equipo equipo) {
		
		InscripcionEquipo inscripcion = new InscripcionEquipo(posicion, numCamisa, titular, false, fechaInscripcion , equipo, this);
		if (!InscripcionEquipo.sePuedeInscribir(this, equipo)) {
			return false;
		}
		else {
			boolean estado = inscripcion.inscribirJugador();
			if (estado) {
				this.equipos.put(equipo, inscripcion);
				return true;
			}
			
			else {
				return false;
			}
		}
		 
		
	}
	
	public List<DeporteIndividual> consultarDeportes(){
		return new ArrayList<>(this.deportesIndividuales.keySet());
		
	}
	
	
	public List<Equipo> consultarEquipos(){
		return new ArrayList<>(this.equipos.keySet());
		
	}
	
	//TODO: consultarEstadisticas y relacion con participacion partido
	
	public double consultarRanking(DeporteIndividual deporte) {
		if (!this.deportesIndividuales.containsKey(deporte)) {
			return 0;
		}
		else {
			return this.deportesIndividuales.get(deporte).consultarElo();
		}
	}


	public Date getFechaAfiliacion() {
		return fechaAfiliacion;
	}


	public int getPuntosLealtad() {
		return puntosLealtad;
	}
	
	
	
	
	

}
