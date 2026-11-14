package competencias;

import java.util.Date;
import usuarios.Participante;

public class InscripcionTorneo
{
	private Participante participante;
	private Date fechaInscripcion;
	private boolean preferencial;
	private double montoPagado;

	public InscripcionTorneo(Participante participante, Date fechaInscripcion, boolean preferencial, double montoPagado)
	{
		this.participante = participante;
		this.fechaInscripcion = fechaInscripcion;
		this.preferencial = preferencial;
		this.montoPagado = montoPagado;
	}

	public Participante getParticipante()
	{
		return participante;
	}

	public Date getFechaInscripcion()
	{
		return fechaInscripcion;
	}

	public boolean esPreferencial()
	{
		return preferencial;
	}

	public void setPreferencial(boolean preferencial)
	{
		this.preferencial = preferencial;
	}

	public double getMontoPagado()
	{
		return montoPagado;
	}
}