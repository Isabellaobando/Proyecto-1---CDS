package usuarios;

import java.util.Date;

public class EmpleadoClub extends Usuario{
	private String noEmpleado;
	private static double codigoDescuento = 0.15;
	private static double codigoFamilia = 0.08; 
	
	public EmpleadoClub(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, int edad, String noEmpleado) {
		super(id, nombre, email, login, password, telefono, fechaNacimiento, edad);
		this.noEmpleado = noEmpleado;
	}

	public String getNoEmpleado() {
		return noEmpleado;
	}

	public void setNoEmpleado(String noEmpleado) {
		this.noEmpleado = noEmpleado;
	}

	public static double getCodigoDescuento() {
		return codigoDescuento;
	}

	public static double getCodigoFamilia() {
		return codigoFamilia;
	}
	
}
