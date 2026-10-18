package estadistica;

public class EstadisticasFutbol extends Estadisticas {
	
	private int goles;
    private int asistencias;

    public EstadisticasFutbol() {
        super();
    }

    public EstadisticasFutbol(int goles, int asistencias) {
        super();
        this.goles = goles;
        this.asistencias = asistencias;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

}
