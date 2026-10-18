package estadistica;

public class EstadisticasBaloncesto extends Estadisticas {
	
	private int puntos;
    private int rebotes;

    public EstadisticasBaloncesto() {
        super();
    }

    public EstadisticasBaloncesto(int puntos, int rebotes) {
        super();
        this.puntos = puntos;
        this.rebotes = rebotes;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getRebotes() {
        return rebotes;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

}
