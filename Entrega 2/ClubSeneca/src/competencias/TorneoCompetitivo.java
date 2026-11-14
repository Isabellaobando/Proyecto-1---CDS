package competencias;

import java.util.Date;

import deportes.Deporte;
import usuarios.EmpleadoClub;
import usuarios.Participante;
import usuarios.Usuario;

public class TorneoCompetitivo extends Torneo
{
	private double tarifaInscripcion;
	private double totalRecaudado;

	public TorneoCompetitivo(String nombre, Deporte deporte, String categoria, int cupos, boolean esDobles,
			Date fechaInicio, double tarifaInscripcion)
	{
		super(nombre, deporte, categoria, cupos, esDobles, fechaInicio);
		this.tarifaInscripcion = tarifaInscripcion;
		this.totalRecaudado = 0;
	}

	@Override
	public double calcularCostoInscripcion(Participante participante)
	{
		if (esEmpleado(participante))
		{
			return 0;
		}
		return tarifaInscripcion;
	}

	@Override
	public boolean inscribir(Participante participante)
	{
		if (!super.inscribir(participante))
		{
			return false;
		}
		registrarPago(calcularCostoInscripcion(participante));
		return true;
	}

	@Override
	public boolean desinscribir(Participante participante)
	{
		double monto = calcularCostoInscripcion(participante);
		if (!super.desinscribir(participante))
		{
			return false;
		}
		registrarPago(-monto);
		return true;
	}

	public void registrarPago(double monto)
	{
		totalRecaudado += monto;
	}

	@Override
	public void entregarPremios()
	{
		for (Participante participante : posiciones)
		{
			if (!esEmpleado(participante))
			{
				System.out.println("Premio de $" + getTotalRecaudado() + " para " + participante.getNombre());
				return;
			}
		}
		System.out.println("Todos los participantes son empleados");
	}
	
	private boolean esEmpleado(Participante participante)
	{
		for (Usuario integrante : participante.getIntegrantes())
		{
			if (!(integrante instanceof EmpleadoClub))
			{
				return false;
			}
		}
		return true;
	}

	public double getTarifaInscripcion()
	{
		return tarifaInscripcion;
	}

	public double getTotalRecaudado()
	{
		return totalRecaudado;
	}
}