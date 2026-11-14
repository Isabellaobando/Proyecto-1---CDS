package deportes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import equipos.Equipo;

public abstract class DeporteGrupal extends Deporte {
	public static final Set<String> categorias = new HashSet<String>(Set.of("SUB-13", "SUB-15", "SUB-17", "MAYORES"));
	protected int minJugadores;
	protected int maxJugadores;
	protected List<Equipo> catalogoEquipos;

	public DeporteGrupal(String nombre, int minJugadores, int maxJugadores) 
	{
		super(nombre);
		this.tipoDeporte = "GRUPAL";
		this.minJugadores = minJugadores;
		this.maxJugadores = maxJugadores;
		this.catalogoEquipos = new ArrayList<>();
	}

	public int getMaxJugadores() 
	{
		return this.maxJugadores;
	}

	public int getMinJugadores() 
	{
		return this.minJugadores;
	}

	public void crearEquipo(Equipo equipo) 
	{
		for (Equipo existente : this.catalogoEquipos) 
		{
			if (existente.getCategoria().equals(equipo.getCategoria()) && existente.getNombre().equals(equipo.getNombre())) 
			{
				return;
			}
		}
		this.catalogoEquipos.add(equipo);
	}

	public List<Equipo> consultarEquiposPorCategoria(String categoria)
	{
		List<Equipo> lista = new ArrayList<>();
		for (Equipo equipo : this.catalogoEquipos) 
		{
			if (equipo.getCategoria().equals(categoria)) 
			{
				lista.add(equipo);
			}
		}
		return lista;
	}
}
