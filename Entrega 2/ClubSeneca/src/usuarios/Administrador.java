package usuarios;

import java.util.Date;
import java.util.List;

public class Administrador extends EmpleadoClub{
	private List<EmpleadoClub> empleados;
	
	public Administrador(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, int edad, String noEmpleado, List<EmpleadoClub> empleados) {
		super(id, nombre, email, login, password, telefono, fechaNacimiento, edad, noEmpleado);
		this.empleados = empleados;
	}

	public List<EmpleadoClub> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<EmpleadoClub> empleados) {
		this.empleados = empleados;
	}
	
	
}
