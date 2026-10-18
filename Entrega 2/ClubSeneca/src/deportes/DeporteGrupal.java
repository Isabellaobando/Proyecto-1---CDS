package deportes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import equipos.Equipo;

public abstract class DeporteGrupal extends Deporte {
	
	// Atributos
	
	// categorias = un set estatico de las posibles categorias para un Deporte Grupal
		
	public static final Set<String> categorias = new HashSet<String>(Set.of("SUB-13", "SUB-15", "SUB-17", "MAYORES"));
	
	protected int minJugadores;
	
	// maxJugadores = el numero maximo de jugadores que un EQUIPO de dicho deporte puede tener 
	
	protected int maxJugadores;
	
	// catalogoEquipos = un mapa con llave categoria, cuyo valor es otro mapa con llave el nombre del equipo, y valor el equipo
	
	protected Map<String, Map<String, Equipo>> catalogoEquipos;
		
	// Constructor
		
	public DeporteGrupal(String nombre, int minJugadores, int maxJugadores) {
		super(nombre);
		this.tipoDeporte = "GRUPAL";
		this.minJugadores = minJugadores;
		this.maxJugadores = maxJugadores;
		this.catalogoEquipos = new HashMap<>();
	}
	
	
	
	// Metodos
	
	public int getMaxJugadores() {
		return this.maxJugadores;
	}
	
	public int getMinJugadores() {
		return this.minJugadores;
	}
	
	// Añade un equipo creado a el catalogo de los deportes
	
	public void crearEquipo(Equipo equipo) {
		String categoria = equipo.getCategoria();
		if(!this.catalogoEquipos.containsKey(categoria)) {
			Map<String, Equipo> mapaEquipo = new HashMap<>();
			mapaEquipo.put(equipo.getNombre(), equipo);
			catalogoEquipos.put(categoria, mapaEquipo);
		}
		else {
			Map<String, Equipo> mapaEquipo = catalogoEquipos.get(categoria);
			mapaEquipo.put(equipo.getNombre(), equipo);
		}
	}
	
	
	// Consulta los equipos segun la categoria
	
	public List<Equipo> consultarEquiposPorCategoria(String categoria){
		Map<String, Equipo> mapaEquipo = this.catalogoEquipos.get(categoria);
		List<Equipo> lista = new ArrayList<>(mapaEquipo.values());
		return lista;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
}
