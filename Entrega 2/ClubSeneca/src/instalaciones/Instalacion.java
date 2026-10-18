package instalaciones;

public class Instalacion {
	
	private String nombre;
    private String tipoDeporte;
    private int capacidad;
    private boolean disponible;
    private String horaApertura;
    private String horaCierre;
    private boolean restriccionBebidaCaliente;

    public Instalacion() {
    }

    public Instalacion(
            String nombre,
            String tipoDeporte,
            int capacidad,
            boolean disponible,
            String horaApertura,
            String horaCierre,
            boolean restriccionBebidaCaliente) {

        this.nombre = nombre;
        this.tipoDeporte = tipoDeporte;
        this.capacidad = capacidad;
        this.disponible = disponible;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.restriccionBebidaCaliente = restriccionBebidaCaliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDeporte() {
        return tipoDeporte;
    }

    public void setTipoDeporte(String tipoDeporte) {
        this.tipoDeporte = tipoDeporte;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public boolean isRestriccionBebidaCaliente() {
        return restriccionBebidaCaliente;
    }

    public void setRestriccionBebidaCaliente(
            boolean restriccionBebidaCaliente) {

        this.restriccionBebidaCaliente = restriccionBebidaCaliente;
    }

}
