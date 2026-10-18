package usuarios;

import java.util.Date;
import java.util.List;

import deportes.Deporte;

public class Entrenador extends EmpleadoClub{
	private Deporte deporte;
	private List<String> entrenamientos; // String provisional, sesionEntrenamiento
	
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

	public List<String> getEntrenamientos() {
		return entrenamientos;
	}

	public void setEntrenamientos(List<String> entrenamientos) {
		this.entrenamientos = entrenamientos;
	}
	
	public void planearEntrenamiento(String sesion) {
		if (!entrenamientos.contains(sesion)) {
			entrenamientos.add(sesion);
		}
		else
			System.out.println("Sesión ya planeada");
	}
	
	public void registrarAsistencia(String sesion, boolean asistencia) {
		// sesion.setAsistenciaEntrenamiento(asistencia);
	}
}
