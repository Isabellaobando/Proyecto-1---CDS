package usuarios;

import java.util.Date;
import java.util.List;

import deportes.Deporte;
import entrenamientos.SesionEntrenamiento;

public class Entrenador extends EmpleadoClub{
	private Deporte deporte;
	private List<SesionEntrenamiento> entrenamientos; 
	
	public Entrenador(int id, String nombre, String email, String login, String password, String telefono,
			Date fechaNacimiento, int edad, String noEmpleado, Deporte deporte) {
		super(id, nombre, email, login, password, telefono, fechaNacimiento, edad, noEmpleado);
		this.deporte = deporte;
	}
	
	public Deporte getDeporte() {
		return deporte;
	}
	
	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}

	public List<SesionEntrenamiento> getEntrenamientos() {
		return entrenamientos;
	}

	public void setEntrenamientos(List<SesionEntrenamiento> entrenamientos) {
		this.entrenamientos = entrenamientos;
	}
	
	public void planearEntrenamiento(SesionEntrenamiento sesion) {
		if (!entrenamientos.contains(sesion)) {
			entrenamientos.add(sesion);
		}
		else
			System.out.println("Sesión ya planeada");
	}
}
