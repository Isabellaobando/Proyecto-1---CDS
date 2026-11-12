package cafeteria;

import java.util.Date;
import java.util.HashMap;

import usuarios.EmpleadoClub;
import usuarios.Usuario;

public class Venta {
    private static final double impuestoCafeteria = 0.08;
    private static final double impuestoTienda = 0.19;
    private static final double porcentajePuntos = 0.02;
	private static double DescuentoEmpleado = 0.15;
	private static double DescuentoUsuario = 0.08; 

    private Date fecha;
    private double subTotal;
    private double impuesto;
    private double descuento;
    private double propina;
    private double total;
    private double puntosGenerados;
    private String lugar;
    private Usuario comprador;
    private EmpleadoClub codigoEmpleado;
    private HashMap<Producto, Integer> productos;

    public Venta(String lugar, Usuario comprador) 
    {
        this.fecha = new Date();
        this.lugar = lugar;
        this.comprador = comprador;
        this.productos = new HashMap<>();
    }

    public Date getFecha() {
        return fecha;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getPropina() {
        return propina;
    }

    public double getTotal() {
        return total;
    }

    public double getPuntosGenerados() {
        return puntosGenerados;
    }

    public String getLugar() {
        return lugar;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public HashMap<Producto, Integer> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        if (!productos.containsKey(producto)) {
            productos.put(producto, cantidad);
        } else {
            productos.put(producto, cantidad + productos.get(producto));
        }
    }

    public void aplicarCodigoDescuento(EmpleadoClub empleado) {
        this.codigoEmpleado = empleado;
    }

    public double calcularSubTotal() 
    {
        double suma = 0;
        for (Producto producto : productos.keySet()) 
        {
            int cantidad = productos.get(producto);
            suma += producto.getPrecio() * cantidad;
        }
        this.subTotal = suma;
        return subTotal;
    }

    public double calcularImpuesto() 
    {
        if (lugar.equalsIgnoreCase("cafeteria")) 
        {
            this.impuesto = this.subTotal * impuestoCafeteria;
        } 
        else 
        {
            this.impuesto = this.subTotal * impuestoTienda;
        }
        return impuesto;
    }

    public double calcularDescuento() 
    {
        if (comprador instanceof EmpleadoClub) 
        {
            this.descuento = this.subTotal * DescuentoEmpleado;
        } 
        else if (codigoEmpleado != null) {
            this.descuento = this.subTotal * DescuentoUsuario;
        } 
        else 
        {
            this.descuento = 0;
        }
        return descuento;
    }

    public double calcularPropina(double porcentaje) {
        this.propina = calcularSubTotal() * porcentaje;
        return propina;
    }

    public double calcularTotal() 
    {
        this.total = subTotal - descuento + impuesto + propina;
        return total;
    }

    public double calcularPuntosFidelidad() 
    {
        double valorCompra = subTotal - descuento + impuesto;
        this.puntosGenerados = valorCompra * porcentajePuntos;
        return puntosGenerados;
    }
}