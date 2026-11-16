package usuarios;

import java.util.ArrayList;
import java.util.List;

import competencias.Torneo;
import deportes.Deporte;

public class Pareja implements Participante, java.io.Serializable {
	private Participante jugador1;
	private Participante jugador2;

	public Pareja(Participante jugador1, Participante jugador2)
	{
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
	}

	@Override
	public String getNombre()
	{
		return jugador1.getNombre() + " - " + jugador2.getNombre();
	}

	@Override
	public double getRanking(Deporte deporte)
	{
		return (jugador1.getRanking(deporte) + jugador2.getRanking(deporte)) / 2;
	}

	@Override
	public boolean puedeInscribirse(Torneo torneo)
	{
		return jugador1.puedeInscribirse(torneo) && jugador2.puedeInscribirse(torneo);
	}

	@Override
	public List<Usuario> getIntegrantes()
	{
		List<Usuario> integrantes = new ArrayList<>(jugador1.getIntegrantes());
		integrantes.addAll(jugador2.getIntegrantes());
		return integrantes;
	}

	public boolean contiene(Participante participante)
	{
		return jugador1 == participante || jugador2 == participante;
	}
}