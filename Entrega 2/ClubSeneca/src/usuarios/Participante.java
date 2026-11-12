package usuarios;

import java.util.ArrayList;
import java.util.List;
import competencias.Torneo;
import deportes.Deporte;

public interface Participante 
{
	public String getNombre();
	public double getRanking(Deporte deporte);
	public boolean puedeInscribirse(Torneo torneo);
	public List<Usuario> getIntegrante();
}
