package usuarios;

import java.util.Date;
import java.util.List;

import equipos.Equipo;

public class Fisioterapeuta extends EmpleadoClub{
	private List<Equipo> equipos;
	
	public Fisioterapeuta(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, int edad, String noEmpleado, List<Equipo> equipos) {
		super(id, nombre, email, login, password, telefono, fechaNacimiento, edad, noEmpleado);
		this.equipos = equipos;
	}

	public List<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}
	
	public void registrarEquipo(Equipo equipo) {
		equipos.addFirst(equipo);
	}
}
