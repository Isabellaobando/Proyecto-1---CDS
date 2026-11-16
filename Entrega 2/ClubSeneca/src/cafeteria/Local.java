package cafeteria;

import java.util.ArrayList;
import java.util.List;

public class Local implements java.io.Serializable {
	private String nombre;
    private String horaApertura;
    private String horaSalida;
    private List<Producto> productos;
    private List<Venta> ventas;

    public Local(String nombre, String horaApertura, String horaSalida)
    {
        this.nombre = nombre;
        this.horaApertura = horaApertura;
        this.horaSalida = horaSalida;
        this.productos = new ArrayList<Producto>();
        this.ventas = new ArrayList<Venta>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getHoraApertura()
    {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura)
    {
        this.horaApertura = horaApertura;
    }

    public String getHoraSalida()
    {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida)
    {
        this.horaSalida = horaSalida;
    }

    public List<Producto> getProductos()
    {
        return productos;
    }

    public List<Venta> getVentas()
    {
        return ventas;
    }

    public void agregarProducto(Producto producto)
    {
        if (!productos.contains(producto)) 
        {
            productos.add(producto);
        }
    }

    public void eliminarProducto(Producto producto)
    {
        productos.remove(producto);
    }

    public List<Producto> consultarProductos()
    {
        return productos;
    }

    public void reabastecerProducto(Producto producto, int cantidad)
    {
        producto.setStock(producto.getStock() + cantidad);
    }

    public void registrarVenta(Venta venta)
    {
        for (Producto producto : venta.getProductos()) 
        {
            producto.actualizarInventario(1);
        }
        venta.calcularTotal();
        venta.calcularPuntosFidelidad();
        if (venta.getBonoAplicado() != null)
        {
            venta.getBonoAplicado().setRedimido();
        }
        ventas.add(venta);
    }
}
