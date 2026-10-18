package estadistica;

public class EstadisticasVoleibol extends Estadisticas {
	
	private int bloqueos;
    private int saques;

    public EstadisticasVoleibol() {
        super();
    }

    public EstadisticasVoleibol(int bloqueos, int saques) {
        super();
        this.bloqueos = bloqueos;
        this.saques = saques;
    }

    public int getBloqueos() {
        return bloqueos;
    }

    public void setBloqueos(int bloqueos) {
        this.bloqueos = bloqueos;
    }

    public int getSaques() {
        return saques;
    }

    public void setSaques(int saques) {
        this.saques = saques;
    }

}
