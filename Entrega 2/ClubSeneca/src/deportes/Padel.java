package deportes;

import java.util.ArrayList;
import java.util.List;


public class Padel extends DeporteIndividual {

	// Atributos
	
		/*
		 * instalaciones = lista de todas las instalaciones para este deporte
		 */
		
		public static List<Instalacion> instalaciones = new ArrayList<>();
		
		// multiplicadorElo = Un numero de cada deporte que ayuda a generar el calculo del elo del jugador
		public static final double multiplicadorElo = 0.025;
		
		// Constructor 
		
		public Padel(String nombre) {
			super(nombre);
		}
		
		// Metodos
		
		public static void agregarInstalacion(Instalacion instalacion) {
			Padel.instalaciones.add(instalacion);
		}
		
		
		/*
		 * Calcula el nuevo elo del participante dependiendo de si gano o perdio el partido
		 */
		
		public static double calcularNuevoElo(double eloActual, String ganoPartido) {
			if (ganoPartido == "SI") {
				double nuevoElo = eloActual*(1 + 1*Padel.multiplicadorElo);
				return nuevoElo;
			}
			else {
				double nuevoElo = eloActual*(1 - 1*Padel.multiplicadorElo);
				return nuevoElo;
			}
		}
	
}
