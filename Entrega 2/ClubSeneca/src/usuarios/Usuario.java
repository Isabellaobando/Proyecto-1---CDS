package usuarios;

import java.util.Date;

public abstract class Usuario {
	
	// Atributos
	
	// id = numero representativo del usuario
	
	protected int id;
	
	protected String nombre;
	
	protected String email;
	
	// login = nombre de usuario para iniciar sesion en la aplicacion
	protected String login;
	
	protected String password;
	
	protected String telefono;
	
	protected Date fechaNacimiento;
	
	protected int edad;
	
	// Constructor
	
	public Usuario(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, int edad) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.login = login;
		this.password = password;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.edad = edad;
	}
	
	
	// Metodos
	
	
	
	/*
	 * Este metodo cambia la password para un usuario
	 */
	public void cambiarPassword(String newPassword) {
		this.password = newPassword;
		
	}
	

	/*
	 * Este metodo verifica que las credenciales son correcto para un usuario, devolviendo true si el login y el password coinciden, y false si no
	 */
	
	public boolean validarCredenciales(String login, String password) {
		if( login == this.login && password == this.password) {
			return true;
		}
		
		else {
			return false;
		}
		
	}
	
	public int getEdad() {
		return this.edad;
	}
	
	

}
