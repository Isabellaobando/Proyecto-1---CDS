package cafeteria;

public class Bebida extends Producto{
	private boolean esCaliente;
	
	public Bebida(String nombre, double precio, String categoria, int stock, boolean esCaliente) {
		super(nombre, precio, categoria, stock);
		this.esCaliente = esCaliente;
	}

	public boolean getEsCaliente() {
		return esCaliente;
	}

	public void setEsCaliente(boolean esCaliente) {
		this.esCaliente = esCaliente;
	}
	
	private boolean verificarRestriccionSeguridad(String instalacion) {
        return esCaliente; //&& instalacion.getRestriccionBebidaCaliente();}
    }
}
