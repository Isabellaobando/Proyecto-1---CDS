package cafeteria;

import java.util.Date;

public class BonoDescuento implements java.io.Serializable {
	private String codigo;
	private double porcentaje;
	private Date fechaEmision;
	private boolean redimido;
	private String torneoOrigen;

	public BonoDescuento(String codigo, double porcentaje, String torneoOrigen)
	{
		this.codigo = codigo;
		this.porcentaje = porcentaje;
		this.fechaEmision = new Date();
		this.redimido = false;
		this.torneoOrigen = torneoOrigen;
	}

	public double calcularDescuento(double subtotal)
	{
		if (redimido)
		{
			return 0;
		}
		return subtotal * porcentaje;
	}

	public String getCodigo()
	{
		return codigo;
	}

	public double getPorcentaje()
	{
		return porcentaje;
	}

	public Date getFechaEmision()
	{
		return fechaEmision;
	}

	public String getTorneoOrigen()
	{
		return torneoOrigen;
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
