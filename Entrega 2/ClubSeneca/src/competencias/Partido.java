package competencias;

import java.util.Date;

import equipos.Equipo;

public class Partido {

	// Atributos

	private Date fecha;

	private String hora;

	// estado = "Programado" o "Finalizado". Solo los partidos finalizados dan puntos en la tabla

	private String estado;

	private Equipo equipoLocal;

	private Equipo equipoVisitante;

	private int marcadorLocal;

	private int marcadorVisitante;

	// Constructor

	public Partido(Date fecha, String hora, Equipo equipoLocal, Equipo equipoVisitante) {
		this.fecha = fecha;
		this.hora = hora;
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.estado = "Programado";
		this.marcadorLocal = 0;
		this.marcadorVisitante = 0;
	}

	// Metodos

	/*
	 * Guarda el resultado del partido y lo marca como finalizado
	 */

	public void registrarMarcador(int marcadorLocal, int marcadorVisitante) {
		this.marcadorLocal = marcadorLocal;
		this.marcadorVisitante = marcadorVisitante;
		this.estado = "Finalizado";
	}

	/*
	 * Devuelve el nombre del equipo ganador, o "Empate". Si el partido no se ha jugado devuelve "Pendiente"
	 */

	public String calcularResultado() {
		if (!estado.equals("Finalizado")) {
			return "Pendiente";
		}
		if (marcadorLocal > marcadorVisitante) {
			return equipoLocal.getNombre();
		}
		else if (marcadorVisitante > marcadorLocal) {
			return equipoVisitante.getNombre();
		}
		else {
			return "Empate";
		}
	}

	public Date getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

	public String getEstado() {
		return estado;
	}

	public Equipo getEquipoLocal() {
		return equipoLocal;
	}

	public Equipo getEquipoVisitante() {
		return equipoVisitante;
	}

	public int getMarcadorLocal() {
		return marcadorLocal;
	}

	public int getMarcadorVisitante() {
		return marcadorVisitante;
	}
}
