package deportes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public abstract class DeporteIndividual extends Deporte{
	
	// Atributos
	
	// niveles = una set estatico de los posibles niveles para un Deporte Individual
	
	public static final Set<String> niveles = new HashSet<String>(Set.of("PRINCIPANTE", "INTERMEDIO", "AVANZADO"));
	
	// Hash Map que contiene la informacion de los jugadores, donde la llave es el nivel y el valor es un Hash Map con llave codigo socio del jugador  y Valor InscripcionDeporteIndividual 
	
	protected Map<String, Map<String, InscripcionDeporteIndividual>> catalogoJugadores;
	
	
	// Constructor
	
	public DeporteIndividual(String nombre) {
		super(nombre);
		this.tipoDeporte = "INDIVIDUAL";
		this.catalogoJugadores = new HashMap<>();
		
	}
	
	// Metodos
	
	/*
	 * inscribirJugador inscribe a un jugador dentro de la estructura de datos para guardar los jugadores registrados al deporte. 
	 * Si el jugador ya existia en el registro, retorna false
	 */
	
	public boolean inscribirJugador(InscripcionDeporteIndividual registroJugador) {
		String nivel = registroJugador.consultarNivel();
		String codigoJugador = registroJugador.consultarJugador().getCodigoSocio();
		if (! this.catalogoJugadores.containsKey(nivel)) {
			Map<String, InscripcionDeporteIndividual> mapaJugadores = new HashMap<>();
			mapaJugadores.put(codigoJugador, registroJugador);
			this.catalogoJugadores.put(nivel, mapaJugadores);
			return true;
			
		}
		
		else {
			Map<String, InscripcionDeporteIndividual> mapaJugadores = this.catalogoJugadores.get(nivel);
			if (mapaJugadores.containsKey(codigoJugador)) {
				return false;
			}
			
			mapaJugadores.put(codigoJugador, registroJugador);
			return true;
		}
	}
	
	
	// Devuelve una lista con todos los jugadores (inscripcion Jugador) que pertenecen a un nivel especifico
	
	public List<InscripcionDeporteIndividual> consultarJugadoresPorNivel(String nivel){
		Map<String, InscripcionDeporteIndividual> mapaNivel = this.catalogoJugadores.get(nivel);
		List<InscripcionDeporteIndividual> lista = new ArrayList<>(mapaNivel.values());
		return lista;
	}
	
	// Actualiza el ranking (elo) del jugador, sumando o restando un valor 
	
	public void actualizarRanking(InscripcionDeporteIndividual jugador, double eloGanado) {
		double viejoElo = jugador.consultarElo();
		double nuevoElo = viejoElo + eloGanado;
		jugador.setRankingElo(nuevoElo);
	}
	
	
	//TODO: programarPartidos
	
	
	
	
	
	

}
