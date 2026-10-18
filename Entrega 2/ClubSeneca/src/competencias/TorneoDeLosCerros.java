package competencias;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import equipos.Equipo;

public class TorneoDeLosCerros extends Competencia{

	private static final int PUNTOS_VICTORIA = 3;
	private static final int PUNTOS_EMPATE = 1;

	private int jornadasTotales;
	private int jornadaActual;
	private HashMap<Equipo, Integer> mapaTorneo;
	private List<Partido> partidos;

	public TorneoDeLosCerros(String nombre, Date fechaInicio, Date fechaFin, int jornadasTotales, 
			int jornadaActual) {
		super(nombre, fechaInicio, fechaFin);
		this.jornadasTotales = jornadasTotales;
		this.jornadaActual = jornadaActual;
		this.mapaTorneo = new HashMap<>();
		this.partidos = new ArrayList<>();
	}

	public void agregarEquipo(Equipo equipo) {
		if (!mapaTorneo.containsKey(equipo)) {
			mapaTorneo.put(equipo, 0);
		}
	}

	public void agregarPartido(Partido partido) {
		partidos.add(partido);
	}

	public void actualizarTablaPosiciones() {
		for (Equipo equipo : mapaTorneo.keySet()) {
			mapaTorneo.put(equipo, 0);
		}
		for (Partido partido : partidos) {
			actualizarPuntosPorResultado(partido);
		}
	}

	private void actualizarPuntosPorResultado(Partido partido) {
		if (!partido.getEstado().equals("Finalizado")) {
			return;
		}

		Equipo local = partido.getEquipoLocal();
		Equipo visitante = partido.getEquipoVisitante();
		agregarEquipo(local);
		agregarEquipo(visitante);

		if (partido.getMarcadorLocal() > partido.getMarcadorVisitante()) {
			mapaTorneo.put(local, mapaTorneo.get(local) + PUNTOS_VICTORIA);
		}
		else if (partido.getMarcadorVisitante() > partido.getMarcadorLocal()) {
			mapaTorneo.put(visitante, mapaTorneo.get(visitante) + PUNTOS_VICTORIA);
		}
		else {
			mapaTorneo.put(local, mapaTorneo.get(local) + PUNTOS_EMPATE);
			mapaTorneo.put(visitante, mapaTorneo.get(visitante) + PUNTOS_EMPATE);
		}
	}

	public int calcularPuntosEquipo(Equipo equipo) {
		return mapaTorneo.getOrDefault(equipo, 0);
	}

	/*
	 * Devuelve los equipos ordenados de mas a menos puntos
	 */

	public List<Equipo> consultarTablaPosiciones() {
		List<Equipo> tabla = new ArrayList<>(mapaTorneo.keySet());
		tabla.sort((a, b) -> calcularPuntosEquipo(b) - calcularPuntosEquipo(a));
		return tabla;
	}

	public int determinarPosicion(Equipo equipo) {
		return consultarTablaPosiciones().indexOf(equipo) + 1;
	}

	public int getJornadasTotales() {
		return jornadasTotales;
	}

	public int getJornadaActual() {
		return jornadaActual;
	}

	public List<Partido> getPartidos() {
		return partidos;
	}
}
