package deportes;

import usuarios.Jugador;

public class InscripcionDeporteIndividual {
	
	private DeporteIndividual deporte;
	private String nivel;
	private double rankingElo; 
	private Jugador jugador;
	
	
	
	public InscripcionDeporteIndividual(DeporteIndividual deporte, String nivel, double rankingElo, Jugador jugador) {
		this.deporte = deporte;
		this.nivel = nivel;
		this.rankingElo = rankingElo;
		this.jugador = jugador;
	}

	public String consultarNivel() {
		return this.nivel;
	}
	
	public Jugador consultarJugador() {
		return this.jugador;
	}

	public double consultarElo() {
		return rankingElo;
	}

	public void setRankingElo(double rankingElo) {
		this.rankingElo = rankingElo;
	}
	
	public boolean inscribirJugador() {
		return this.deporte.inscribirJugador(this);
	}
	
}
