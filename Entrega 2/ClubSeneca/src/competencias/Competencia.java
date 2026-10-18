package competencias;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import equipos.Equipo;

public class Competencia {
	
	    protected String nombre;
	    protected Date fechaInicio;
	    protected Date fechaFin;
	    protected List<Equipo> equiposInscritos = new ArrayList<Equipo>();

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

	    public boolean inscribirEquipo(Equipo equipo) {
	        if (equipo == null) {
	            return false;
	        }

	        if (equiposInscritos.contains(equipo)) {
	            return false;
	        }

	        equiposInscritos.add(equipo);
	        return true;
	    }
	    
	    public List<Equipo> consultarEquiposInscritos() {
	        return new ArrayList<Equipo>(equiposInscritos);
	    }

}
