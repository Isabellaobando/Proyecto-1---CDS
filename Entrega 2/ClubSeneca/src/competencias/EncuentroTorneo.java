package competencias;

import java.text.SimpleDateFormat;
import java.util.Date;

import usuarios.Participante;

public class EncuentroTorneo implements java.io.Serializable {
	private Participante participanteA;
	private Participante participanteB;
	private Date fecha;
	private int ronda;
	private String resultado;
	private Participante ganador;

	public EncuentroTorneo(Participante participanteA, Participante participanteB, Date fecha, int ronda)
	{
		this.participanteA = participanteA;
		this.participanteB = participanteB;
		this.fecha = fecha;
		this.ronda = ronda;
		this.resultado = null;
		this.ganador = null;
	}

	public void registrarResultado(String resultado, Participante ganador)
	{
		if (ganador != participanteA && ganador != participanteB)
		{
			return;
		}
		this.resultado = resultado;
		this.ganador = ganador;
	}

	public Participante getGanador()
	{
		return ganador;
	}

	public Participante getPerdedor()
	{
		if (ganador == null)
		{
			return null;
		}
		return (ganador == participanteA) ? participanteB : participanteA;
	}

	public String consultarDetalle()
	{
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		String detalle = "Ronda " + ronda + " (" + formato.format(fecha) + "): "
				+ participanteA.getNombre() + " vs " + participanteB.getNombre();
		if (ganador == null)
		{
			return detalle + " - Pendiente";
		}
		return detalle + " - " + resultado + " (gana " + ganador.getNombre() + ")";
	}
}