package cafeteria;

import java.util.Date;
import java.util.HashMap;

import usuarios.EmpleadoClub;
import usuarios.Usuario;

public class Venta {
    private static final double IMPUESTO_CAFETERIA = 0.08;
    private static final double IMPUESTO_TIENDA = 0.19;
    private static final double PORCENTAJE_PUNTOS = 0.02;

    private Date fecha;
    private double subTotal;
    private double impuesto;
    private double descuento;
    private double propina;
    private double total;
    private int puntosGenerados;
    private String lugar;
    private Usuario comprador;
    private EmpleadoClub codigoEmpleado;
    private HashMap<Producto, Integer> productos;

    public Venta(String lugar, Usuario comprador) {
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

    public int getPuntosGenerados() {
        return puntosGenerados;
    }

    public String getLugar() {
        return lugar;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public EmpleadoClub getCodigoEmpleado() {
        return codigoEmpleado;
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

    public double calcularSubTotal() {
        double suma = 0;
        for (Producto producto : productos.keySet()) {
            int cantidad = productos.get(producto);
            suma += producto.getPrecio() * cantidad;
        }
        this.subTotal = suma;
        return subTotal;
    }

    public double calcularImpuesto() {
        if (lugar.equalsIgnoreCase("cafeteria")) {
            this.impuesto = calcularSubTotal() * IMPUESTO_CAFETERIA;
        } else {
            this.impuesto = calcularSubTotal() * IMPUESTO_TIENDA;
        }
        return impuesto;
    }

    public double calcularDescuento() {
        if (comprador instanceof EmpleadoClub) {
            this.descuento = calcularSubTotal() * EmpleadoClub.getCodigoDescuento();
        } else if (codigoEmpleado != null) {
            this.descuento = calcularSubTotal() * EmpleadoClub.getCodigoFamilia();
        } else {
            this.descuento = 0;
        }
        return descuento;
    }

    public double calcularPropina(double porcentaje) {
        this.propina = calcularSubTotal() * porcentaje;
        return propina;
    }

    public double calcularTotal() {
        calcularSubTotal();
        calcularImpuesto();
        calcularDescuento();
        this.total = subTotal - descuento + impuesto + propina;
        return total;
    }

    public int calcularPuntosFidelidad() {
        double valorCompra = subTotal - descuento + impuesto;
        this.puntosGenerados = (int) (valorCompra * PORCENTAJE_PUNTOS);
        return puntosGenerados;
    }
}