package competencias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import equipos.Equipo;
import equipos.InscripcionEquipo;
import usuarios.Jugador;

public class InscripcionCompetencia {
	private Date fechaInscripcion;
	private Equipo equipo;
	
	public InscripcionCompetencia(Date fechaInscripcion, Equipo equipo) {
		this.fechaInscripcion = fechaInscripcion;
		this.equipo = equipo;
	}

	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	
	private boolean verificarInscripcionOficial(Jugador jugador) {
        return equipo.consultarJugador(jugador.getCodigoSocio()) != null;
    }

    public boolean validarNominacion(Jugador jugador) {
        if (jugador == null) {
            return false;
        }
        if (!equipo.isActivo()) {
            System.out.println("El equipo " + equipo.getNombre() + " no está activo");
            return false;
        }
        if (!verificarInscripcionOficial(jugador)) {
            System.out.println(jugador.getNombre() + " no está inscrito oficialmente en el equipo");
            return false;
        }
        if (equipo.consultarJugador(jugador.getCodigoSocio()).getSuspendido()) {
            System.out.println(jugador.getNombre() + " está suspendido");
            return false;
        }
        return true;
    }

    public List<Jugador> consultarNomina() {
        List<Jugador> nomina = new ArrayList<>();
        for (InscripcionEquipo inscripcion : equipo.consultarJugadores()) {
            nomina.add(inscripcion.getJugador());
        }
        return nomina;
    }
}
