package cafeteria;

import java.util.Date;

public class BonoDescuento 
{
	private String codigo;
	private double porcentaje;
	private Date fechaEmision;
	private boolean redimido;
	private String torneoOrigen;
	
	public BonoDescuento(String codigo, double porcentaje, Date fechaEmision, String torneoOrigen) 
	{
		super();
		this.codigo = codigo;
		this.porcentaje = porcentaje;
		this.fechaEmision = fechaEmision;
		this.redimido = false;
		this.torneoOrigen = torneoOrigen;
	}

	public String getCodigo() 
	{
		return codigo;
	}

	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}

	public double getPorcentaje() 
	{
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) 
	{
		this.porcentaje = porcentaje;
	}

	public Date getFechaEmision() 
	{
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) 
	{
		this.fechaEmision = fechaEmision;
	}

	public String getTorneoOrigen() 
	{
		return torneoOrigen;
	}

	public void setTorneoOrigen(String torneoOrigen) 
	{
		this.torneoOrigen = torneoOrigen;
	}

	public boolean getRedimido() 
	{
		return redimido;
	}
	
	public void setRedimido()
	{
		this.redimido = true;
	}
}
