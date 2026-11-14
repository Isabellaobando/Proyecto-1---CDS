package usuarios;

import java.util.Date;

public class Administrador extends EmpleadoClub
{
	public Administrador(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, int edad, String noEmpleado)
	{
		super(id, nombre, email, login, password, telefono, fechaNacimiento, edad, noEmpleado);
	}
}
