package deportes;

public abstract class Deporte {
	
	// Atributos
	
	protected String nombre;
	
	// Dice el tipo de deporte, se inicializa en None pero en los super de las otras clases se les da el tipo
	
	protected String tipoDeporte;
	
	// Constructor
	
	public Deporte(String nombre) {
		this.nombre = nombre;
	}
	
	
	// Metodos
	
	public String obtenerNombre() {
		return this.nombre;
	}
	
	public String obtenerTipoDeporte() {
		return this.tipoDeporte;
	}
	
	
	
	
	
}
