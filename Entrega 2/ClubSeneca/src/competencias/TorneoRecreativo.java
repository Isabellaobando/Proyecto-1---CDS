package competencias;

import java.util.Date;

import cafeteria.BonoDescuento;
import deportes.Deporte;
import usuarios.Jugador;
import usuarios.Participante;
import usuarios.Usuario;

public class TorneoRecreativo extends Torneo implements java.io.Serializable {
	private double porcentajeBono;
	private int bonosGenerados;

	public TorneoRecreativo(String nombre, Deporte deporte, String categoria, int cupos, boolean esDobles,
			Date fechaInicio, double porcentajeBono)
	{
		super(nombre, deporte, categoria, cupos, esDobles, fechaInicio);
		this.porcentajeBono = porcentajeBono;
		this.bonosGenerados = 0;
	}

	@Override
	public double calcularCostoInscripcion(Participante participante)
	{
		return 0;
	}

	@Override
	public void entregarPremios()
	{
		if (posiciones.isEmpty())
		{
			return;
		}
		Participante ganador = posiciones.get(0);
		for (Usuario integrante : ganador.getIntegrantes())
		{
			if (integrante instanceof Jugador)
			{
				Jugador jugador = (Jugador) integrante;
				jugador.agregarBono(generarBono(jugador));
			}
		}
	}

	private BonoDescuento generarBono(Participante ganador)
	{
		bonosGenerados++;
		String codigo = nombre + "-" + bonosGenerados;
		return new BonoDescuento(codigo, porcentajeBono, nombre);
	}

	public double getPorcentajeBono()
	{
		return porcentajeBono;
	}
}