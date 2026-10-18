package deportes;

public class Futbol extends DeporteGrupal{
	
	public Futbol(String nombre, int minJugadores, int maxJugadores) {
		super(nombre,  minJugadores,  maxJugadores);
		
	}
	
	// Metodos
	
	/*
	 * Calcula los puntos que se obtienen en la liga dependiendo de si se gano un partido o no. El resultado puede ser
	 * o GANADO, PERDIDO, o EMPATADO
	 */
			
	public static int calcularPuntosLiga(String resultadoPartido) {
		if(resultadoPartido == "GANADO") {
			return 3;
		}
		else if(resultadoPartido == "EMPATADO") {
			return 1;
		}
				
		else {
			return 0;
		}
	}
			
	/*
	 * Determina quien gano el partido. Si devuelve String "FAVOR", gana el equipo a favor, si devuelve "CONTRA" gana el
	 * equipo en contra, si devuelve "EMPATE", ambos equipos quedaron empatados
	 */
			
	public static String determinarGanador(int golesFavor, int golesContra) {
		if(golesFavor > golesContra) {
			return "FAVOR";
		}
				
		else if (golesFavor == golesContra){
			return "EMPATE";
		}
		else {
			return "CONTRA";
		}
				
	}
	
	public static boolean seSuspendeJugador(int numeroAmarillas, int numeroRojas) {
		if(numeroAmarillas >= 2 || numeroRojas >= 1) {
			return true;
		}
		else {
			return false;
		}
	}
			
	
			

}
