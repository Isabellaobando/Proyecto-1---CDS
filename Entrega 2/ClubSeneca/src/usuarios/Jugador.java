package usuarios;

import java.util.Date;

public class Jugador extends Usuario {
	
	// Atributos
	
	// El codigo del socio
	
	private String codigoSocio;
	
	private Date fechaAfiliacion;
	
	private int puntosLealtad;
	
	// Constructor

	public Jugador(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, String codigoSocio, Date fechaAfiliacion, int puntosLealtad, int edad) {
		
		super(id, nombre, email, login, password, telefono, fechaNacimiento, edad);
		this.codigoSocio = codigoSocio;
		this.fechaAfiliacion = fechaAfiliacion;
		this.puntosLealtad = puntosLealtad;
		
	}
	
	
	// Metodos
	
	public String getCodigoSocio() {
		return this.codigoSocio;
	}
	
	
	
	

}
