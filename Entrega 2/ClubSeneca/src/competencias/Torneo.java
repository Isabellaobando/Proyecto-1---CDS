package competencias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import deportes.Deporte;
import deportes.DeporteGrupal;
import equipos.Equipo;
import usuarios.Pareja;
import usuarios.Participante;

public abstract class Torneo implements java.io.Serializable {

	public static final String POR_INICIAR = "Por iniciar";
	public static final String EN_CURSO = "En curso";
	public static final String FINALIZADO = "Finalizado";
	protected static final double PORCENTAJE_PREFERENCIAL = 0.2;

	protected String nombre;
	protected Deporte deporte;
	protected String categoria;
	protected int cupos;
	protected boolean esDobles;
	protected Date fechaInicio;
	protected String estado;
	protected List<InscripcionTorneo> inscripciones;
	protected List<EncuentroTorneo> encuentros;
	protected List<Participante> posiciones;

	// Si es true, los cupos preferenciales que no se llenaron quedan abiertos para todos

	private boolean cuposPreferencialesLiberados;

	public Torneo(String nombre, Deporte deporte, String categoria, int cupos, boolean esDobles, Date fechaInicio)
	{
		this.nombre = nombre;
		this.deporte = deporte;
		this.categoria = categoria;
		this.cupos = cupos;
		this.esDobles = esDobles && !(deporte instanceof DeporteGrupal);
		this.fechaInicio = fechaInicio;
		this.estado = POR_INICIAR;
		this.inscripciones = new ArrayList<>();
		this.encuentros = new ArrayList<>();
		this.posiciones = new ArrayList<>();
		this.cuposPreferencialesLiberados = false;
	}

	public boolean inscribir(Participante participante)
	{
		if (participante == null || !estado.equals(POR_INICIAR))
		{
			return false;
		}
		if (buscarInscripcion(participante) != null)
		{
			return false;
		}
		if (!validarParticipante(participante) || !hayCupos())
		{
			return false;
		}

		boolean preferencial = esPreferencial(participante);
		if (!preferencial && !cuposPreferencialesLiberados)
		{
			int regularesOcupados = 0;
			for (InscripcionTorneo inscripcion : inscripciones)
			{
				if (!inscripcion.esPreferencial())
				{
					regularesOcupados++;
				}
			}
			if (regularesOcupados >= cupos - getCuposPreferenciales())
			{
				return false;
			}
		}
		inscripciones.add(new InscripcionTorneo(participante, new Date(), preferencial,
				calcularCostoInscripcion(participante)));
		return true;
	}

	public boolean desinscribir(Participante participante)
	{
		if (!estado.equals(POR_INICIAR))
		{
			return false;
		}
		InscripcionTorneo inscripcion = buscarInscripcion(participante);
		if (inscripcion == null)
		{
			return false;
		}
		inscripciones.remove(inscripcion);
		return true;
	}

	public int getCuposPreferenciales()
	{
		return (int) Math.ceil(cupos * PORCENTAJE_PREFERENCIAL);
	}

	public int getCuposDisponibles()
	{
		return cupos - inscripciones.size();
	}

	public boolean hayCupos()
	{
		return getCuposDisponibles() > 0;
	}

	public void iniciar()
	{
		if (estado.equals(POR_INICIAR))
		{
			estado = EN_CURSO;
		}
	}

	public void registrarEncuentro(EncuentroTorneo encuentro)
	{
		if (estado.equals(EN_CURSO) && encuentro != null)
		{
			encuentros.add(encuentro);
		}
	}

	public void finalizar(List<Participante> posiciones)
	{
		if (!estado.equals(EN_CURSO) || posiciones == null)
		{
			return;
		}
		this.posiciones = new ArrayList<>(posiciones);
		estado = FINALIZADO;
		entregarPremios();
	}

	public String consultarHistorial()
	{
		String historial = "Torneo: " + nombre + " (" + deporte.obtenerNombre() + " - " + categoria + ")\n";
		historial += "Estado: " + estado + "\n";
		historial += "Participantes:\n";
		for (InscripcionTorneo inscripcion : inscripciones)
		{
			String marca = inscripcion.esPreferencial() ? " (preferencial)" : "";
			historial += "  " + inscripcion.getParticipante().getNombre() + marca + "\n";
		}
		historial += "Encuentros:\n";
		for (EncuentroTorneo encuentro : encuentros)
		{
			historial += "  " + encuentro.consultarDetalle() + "\n";
		}
		historial += "Posiciones:\n";
		for (int i = 0; i < posiciones.size(); i++)
		{
			historial += "  " + (i + 1) + ". " + posiciones.get(i).getNombre() + "\n";
		}
		return historial;
	}

	public String getEstado()
	{
		return estado;
	}

	public boolean estaEnCurso()
	{
		return estado.equals(EN_CURSO);
	}

	public boolean estaFinalizado()
	{
		return estado.equals(FINALIZADO);
	}

	public Deporte getDeporte()
	{
		return deporte;
	}

	public String getNombre()
	{
		return nombre;
	}

	public String getCategoria()
	{
		return categoria;
	}

	public Date getFechaInicio()
	{
		return fechaInicio;
	}

	public boolean isEsDobles()
	{
		return esDobles;
	}

	public List<Participante> getInscritos()
	{
		List<Participante> inscritos = new ArrayList<>();
		for (InscripcionTorneo inscripcion : inscripciones)
		{
			inscritos.add(inscripcion.getParticipante());
		}
		return inscritos;
	}

	protected boolean validarParticipante(Participante participante)
	{
		if (deporte instanceof DeporteGrupal)
		{
			if (!(participante instanceof Equipo))
			{
				return false;
			}
		}
		else if (esDobles)
		{
			if (!(participante instanceof Pareja))
			{
				return false;
			}
		}
		else if (participante instanceof Equipo || participante instanceof Pareja)
		{
			return false;
		}
		return participante.puedeInscribirse(this);
	}

	protected boolean esPreferencial(Participante participante)
	{
		double ranking = participante.getRanking(deporte);
		if (ranking <= 0)
		{
			return false;
		}
		int mejores = 0;
		for (InscripcionTorneo inscripcion : inscripciones)
		{
			if (inscripcion.getParticipante().getRanking(deporte) > ranking)
			{
				mejores++;
			}
		}
		return mejores < getCuposPreferenciales();
	}

	public void liberarCuposPreferenciales()
	{
		cuposPreferencialesLiberados = true;
	}

	private InscripcionTorneo buscarInscripcion(Participante participante)
	{
		for (InscripcionTorneo inscripcion : inscripciones)
		{
			if (inscripcion.getParticipante() == participante)
			{
				return inscripcion;
			}
		}
		return null;
	}

	// Lo que cambia segun el tipo de torneo

	public abstract double calcularCostoInscripcion(Participante participante);

	public abstract void entregarPremios();
}
