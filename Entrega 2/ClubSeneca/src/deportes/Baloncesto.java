package deportes;

public class Baloncesto extends DeporteGrupal{
	
	// Constructor 
	
	public Baloncesto(String nombre, int minJugadores, int maxJugadores) {
		super(nombre, minJugadores,maxJugadores);
	}

	
	// Metodos
	
	/*
	 * Calcula los puntos que se obtienen en la liga dependiendo de si se gano un partido o no. El resultado puede ser
	 * o GANADO, PERDIDO
	 */
	
	public static int calcularPuntosLiga(String resultadoPartido) {
		if(resultadoPartido == "GANADO") {
			return 3;
		}
		else {
			return 0;
		}
	}
	
	/*
	 * Determina quien gano el partido. Si devuelve String "FAVOR", gana el equipo a favor, si devuelve "CONTRA" gana el
	 * equipo en contra
	 */
	
	public static String determinarGanador(int puntosFavor, int puntosContra) {
		if(puntosFavor > puntosContra) {
			return "FAVOR";
		}
		
		else {
			return "CONTRA";
		}
	}
}
