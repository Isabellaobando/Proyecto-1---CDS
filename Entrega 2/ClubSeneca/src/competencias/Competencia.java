package competencias;

import java.util.Date;

public class Competencia {
	
	    protected String nombre;
	    protected Date fechaInicio;
	    protected Date fechaFin;

	    public Competencia() {
	    }

	    public Competencia(String nombre, Date fechaInicio, Date fechaFin) {
	        this.nombre = nombre;
	        this.fechaInicio = fechaInicio;
	        this.fechaFin = fechaFin;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public Date getFechaInicio() {
	        return fechaInicio;
	    }

	    public void setFechaInicio(Date fechaInicio) {
	        this.fechaInicio = fechaInicio;
	    }

	    public Date getFechaFin() {
	        return fechaFin;
	    }

	    public void setFechaFin(Date fechaFin) {
	        this.fechaFin = fechaFin;
	    }

	    public boolean inscribirEquipo(Object equipo) {
	        return equipo != null;
	    }

}
