package estadistica;

public class EstadisticasTenis extends Estadisticas {
	
	private int setsGanados;
    private int gamesGanados;

    public EstadisticasTenis() {
        super();
    }

    public EstadisticasTenis(int setsGanados, int gamesGanados) {
        super();
        this.setsGanados = setsGanados;
        this.gamesGanados = gamesGanados;
    }

    public int getSetsGanados() {
        return setsGanados;
    }

    public void setSetsGanados(int setsGanados) {
        this.setsGanados = setsGanados;
    }

    public int getGamesGanados() {
        return gamesGanados;
    }

    public void setGamesGanados(int gamesGanados) {
        this.gamesGanados = gamesGanados;
    }

}
