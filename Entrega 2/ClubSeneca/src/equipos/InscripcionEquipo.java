package equipos;

import java.util.Date;

import usuarios.Jugador;

public class InscripcionEquipo {
	
	// Atributos
	
	private String posicion;
	
	private int numCamisa;
	
	// Indica si el jugador es titular en el equipo o no
	
	private boolean titular;
	
	// Indica si el jugador fue suspendido o no
	
	private boolean suspendido;
	
	private Date fechaInscripcion;
	
	private Equipo equipo;
	
	private Jugador jugador;
	
	// Constructor
	
	public InscripcionEquipo(String posicion, int numCamisa, boolean titular, boolean suspendido, Date fechaInscripcion,
			Equipo equipo, Jugador jugador) {
		this.posicion = posicion;
		this.numCamisa = numCamisa;
		this.titular = titular;
		this.suspendido = suspendido;
		this.fechaInscripcion = fechaInscripcion;
		this.equipo = equipo;
		this.jugador = jugador;
		
	}
	
	// Metodos
	
	public Jugador getJugador() {
		return this.jugador;
	}
	
	public boolean getSuspendido() {
		return this.suspendido;
	}
	
	public boolean inscribirJugador(Equipo equipo) {
		return equipo.agregarJugador(this);
		
	}
	
	public boolean cancelarInscripcion() {
		return equipo.retirarJugador(this);
		
	}
	
	public static boolean sePuedeInscribir(Jugador jugador, Equipo equipo) {
		if (equipo.getCategoria() == "SUB-13" && jugador.getEdad() <= 13) {
			return true;
		}
		else if (equipo.getCategoria() == "SUB-15" && 13 < jugador.getEdad() && jugador.getEdad() <= 15) {
			return true;
			
		}
		else if (equipo.getCategoria() == "SUB-17" && 15 < jugador.getEdad() && jugador.getEdad() <= 17) {
			return true;
		}
		else if (equipo.getCategoria() == "MAYORES" && 18 <= jugador.getEdad()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	

}
