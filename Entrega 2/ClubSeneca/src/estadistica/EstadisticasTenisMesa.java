package estadistica;

public class EstadisticasTenisMesa extends Estadisticas{
	
	private int setsGanados;
    private int puntosTotales;

    public EstadisticasTenisMesa() {
        super();
    }

    public EstadisticasTenisMesa(int setsGanados, int puntosTotales) {
        super();
        this.setsGanados = setsGanados;
        this.puntosTotales = puntosTotales;
    }

    public int getSetsGanados() {
        return setsGanados;
    }

    public void setSetsGanados(int setsGanados) {
        this.setsGanados = setsGanados;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

}
