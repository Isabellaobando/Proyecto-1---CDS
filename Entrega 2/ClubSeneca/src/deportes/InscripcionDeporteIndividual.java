package deportes;

import usuarios.Jugador;

public class InscripcionDeporteIndividual implements java.io.Serializable {

    private DeporteIndividual deporte;
    private String nivel;
    private double rankingElo;
    private Jugador jugador;

    public InscripcionDeporteIndividual(
            DeporteIndividual deporte,
            String nivel,
            double rankingElo,
            Jugador jugador) {

        this.deporte = deporte;
        this.nivel = nivel;
        this.rankingElo = rankingElo;
        this.jugador = jugador;
    }

    public String consultarNivel() {
        return nivel;
    }

    public Jugador consultarJugador() {
        return jugador;
    }

    public DeporteIndividual consultarDeporte() {
        return deporte;
    }

    public double consultarElo() {
        return rankingElo;
    }

    public void setRankingElo(double rankingElo) {
        this.rankingElo = rankingElo;
    }

    public boolean inscribirJugador() {

        if (deporte == null || jugador == null || nivel == null) {
            return false;
        }

        return deporte.inscribirJugador(this);
    }
}