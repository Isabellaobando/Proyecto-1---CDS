package equipos;

import java.util.Date;

import usuarios.Jugador;

public class InscripcionEquipo implements java.io.Serializable {
	private String posicion;

	private int numCamisa;
	private boolean titular;
	private boolean suspendido;
	private Date fechaInscripcion;
	private Equipo equipo;
	private Jugador jugador;
	
	public InscripcionEquipo(String posicion, int numCamisa, boolean titular, boolean suspendido, Date fechaInscripcion,
			Equipo equipo, Jugador jugador) 
	{
		this.posicion = posicion;
		this.numCamisa = numCamisa;
		this.titular = titular;
		this.suspendido = suspendido;
		this.fechaInscripcion = fechaInscripcion;
		this.equipo = equipo;
		this.jugador = jugador;
	}

	public Jugador getJugador() 
	{
		return this.jugador;
	}

	public Equipo getEquipo() 
	{
		return this.equipo;
	}

	public boolean getSuspendido() 
	{
		return this.suspendido;
	}

	public boolean inscribirJugador() 
	{
		return this.equipo.agregarJugador(this);

	}

	public boolean cancelarInscripcion() 
	{
		return this.equipo.retirarJugador(this);

	}

	public static boolean sePuedeInscribir(Jugador jugador, Equipo equipo) 
	{
		if (equipo.getCategoria().equals("SUB-13") && jugador.getEdad() <= 13) 
		{
			return true;
		}
		else if (equipo.getCategoria().equals("SUB-15") && 13 < jugador.getEdad() && jugador.getEdad() <= 15) 
		{
			return true;
		}
		else if (equipo.getCategoria().equals("SUB-17") && 15 < jugador.getEdad() && jugador.getEdad() <= 17) 
		{
			return true;
		}
		else if (equipo.getCategoria().equals("MAYORES") && 18 <= jugador.getEdad()) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	public String getPosicion() 
	{
		return posicion;
	}

	public int getNumCamisa() 
	{
		return numCamisa;
	}

	public boolean isTitular() 
	{
		return titular;
	}

	public Date getFechaInscripcion() 
	{
		return fechaInscripcion;
	}



}
