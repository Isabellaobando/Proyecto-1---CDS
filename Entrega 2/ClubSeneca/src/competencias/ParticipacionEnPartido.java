

package competencias;

import estadistica.Estadisticas;
import usuarios.Jugador;

public class ParticipacionEnPartido {

    private boolean titular;
    private int minutosJugados;
    private Jugador jugador;
    private Estadisticas estadisticas;
    private int tarjetasAmarillas;
    private int tarjetasRojas;
    private int acumulacionTarjetas;

    public ParticipacionEnPartido() {
        estadisticas = new Estadisticas();
    }

    public ParticipacionEnPartido(boolean titular, int minutosJugados) {
        this.titular = titular;
        this.minutosJugados = minutosJugados;
        estadisticas = new Estadisticas();
    }

    public boolean isTitular() {
        return titular;
    }

    public void setTitular(boolean titular) {
        this.titular = titular;
    }

    public int getMinutosJugados() {
        return minutosJugados;
    }

    public void setMinutosJugados(int minutosJugados) {
        this.minutosJugados = minutosJugados;
    }

    public void registrarParticipacion(Jugador jugador) {
        this.jugador = jugador;
    }

    public void registrarTarjetaAmarilla() {
        tarjetasAmarillas++;
        actualizarAcumulacionTarjetas();
    }

    public void registrarTarjetaRoja() {
        tarjetasRojas++;
    }

    public boolean verificarSuspension() {
        if (tarjetasRojas > 0) {
            return true;
        }

        if (acumulacionTarjetas >= 2) {
            return true;
        }

        return false;
    }

    public Estadisticas consultarEstadisticas() {
        return estadisticas;
    }

    public void actualizarAcumulacionTarjetas() {
        acumulacionTarjetas = tarjetasAmarillas;
    }
}
