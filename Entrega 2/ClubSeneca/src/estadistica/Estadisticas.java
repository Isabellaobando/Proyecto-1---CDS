package estadistica;

import java.util.HashMap;
import usuarios.Jugador;

public class Estadisticas {

    private HashMap<String, Integer> datos;

    public Estadisticas() {
        datos = new HashMap<String, Integer>();
    }

    public Estadisticas(HashMap<String, Integer> datos) {
        this.datos = datos;
    }

    public HashMap<String, Integer> getDatos() {
        return datos;
    }

    public void setDatos(HashMap<String, Integer> datos) {
        this.datos = datos;
    }

    public double calcularTotal() {
        int total = 0;

        for (Integer valor : datos.values()) {
            if (valor != null) {
                total = total + valor;
            }
        }

        return total;
    }

    public void actualizarEstadistica(String nombre, int valor) {
        datos.put(nombre, valor);
    }

    public void actualizarEstadisticas(String nombre, int valor) {
        actualizarEstadistica(nombre, valor);
    }

    public String consultarPorJugador(Jugador jugador) {
        if (jugador == null) {
            return "Jugador no encontrado";
        }

        String codigoSocio = jugador.getCodigoSocio();

        if (codigoSocio == null) {
            return "El jugador no tiene codigo de socio";
        }

        return "Codigo de socio: " + codigoSocio + "\n" + datos.toString();
    }

    public int consultarPartidosJugados() {
        Integer partidos = datos.get("partidosJugados");

        if (partidos == null) {
            return 0;
        }

        return partidos;
    }
}

