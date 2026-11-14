package usuarios;

import java.util.List;
import competencias.Torneo;
import deportes.Deporte;

public interface Participante
{
	public String getNombre();

	public double getRanking(Deporte deporte);

	public boolean puedeInscribirse(Torneo torneo);

	// Las personas que forman el participante: uno (jugador o empleado), dos (pareja) o varios (equipo)
	public List<Usuario> getIntegrantes();
}
