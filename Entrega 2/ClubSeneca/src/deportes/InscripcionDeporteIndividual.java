package deportes;

import usuarios.Jugador;

public class InscripcionDeporteIndividual {
	
	private String nivel;
	private Jugador jugador;
	
	
	
	public String consultarNivel() {
		return this.nivel;
	}
	
	public Jugador consultarJugador() {
		return this.jugador;
	}
}
