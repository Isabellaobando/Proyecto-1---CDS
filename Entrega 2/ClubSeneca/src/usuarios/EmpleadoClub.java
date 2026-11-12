package usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import competencias.Torneo;
import deportes.Deporte;

public class EmpleadoClub extends Usuario implements Participante
{
	private String noEmpleado;
	private List<Date> turnos;
	
	public EmpleadoClub(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, int edad, String noEmpleado) {
		super(id, nombre, email, login, password, telefono, fechaNacimiento, edad);
		this.noEmpleado = noEmpleado;
		this.turnos = new ArrayList<Date>();
	}

	public String getNoEmpleado() {
		return noEmpleado;
	}

	public void setNoEmpleado(String noEmpleado) {
		this.noEmpleado = noEmpleado;
	}

	@Override
	public double getRanking(Deporte deporte) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean puedeInscribirse(Torneo torneo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Usuario> getIntegrante() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
