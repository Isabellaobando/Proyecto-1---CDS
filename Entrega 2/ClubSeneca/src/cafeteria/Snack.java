package cafeteria;

import java.util.List;

public class Snack extends Producto{
	private List<String> alergenos;
	
	public Snack(String nombre, double precio, String categoria, int stock, List<String> alergenos) {
		super(nombre, precio, categoria, stock);
		this.alergenos = alergenos;
	}

	public List<String> getAlergenos() {
		return alergenos;
	}

	public void agregarAlergenos(String alergeno) {
		alergenos.addLast(alergeno);
	}
	
	public void eliminarAlergenos(String alergeno) {
		alergenos.remove(alergeno);
	}
}
