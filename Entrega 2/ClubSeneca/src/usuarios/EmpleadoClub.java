package usuarios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import competencias.Torneo;
import deportes.Deporte;
import deportes.DeporteGrupal;

public class EmpleadoClub extends Usuario implements Participante
{
	private String noEmpleado;
	private List<Date> turnos;

	public EmpleadoClub(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, int edad, String noEmpleado) 
	{
		super(id, nombre, email, login, password, telefono, fechaNacimiento, edad);
		this.noEmpleado = noEmpleado;
		this.turnos = new ArrayList<Date>();
	}

	public String getNoEmpleado() 
	{
		return noEmpleado;
	}

	public void setNoEmpleado(String noEmpleado) 
	{
		this.noEmpleado = noEmpleado;
	}

	public void agregarTurno(Date fecha) 
	{
		turnos.add(fecha);
	}

	/*
	 * Revisa si el empleado trabaja ese dia (compara solo la fecha, no la hora)
	 */

	public boolean estaDeTurno(Date fecha) 
	{
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		String dia = formato.format(fecha);
		for (Date turno : turnos) {
			if (formato.format(turno).equals(dia)) 
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean puedeInscribirse(Torneo torneo) 
	{
		if (torneo.getDeporte() instanceof DeporteGrupal) 
		{
			return false;
		}
		return !estaDeTurno(torneo.getFechaInicio());
	}

	@Override
	public double getRanking(Deporte deporte) 
	{
		return 0;
	}

	@Override
	public List<Usuario> getIntegrantes() 
	{
		List<Usuario> integrantes = new ArrayList<>();
		integrantes.add(this);
		return integrantes;
	}
}
