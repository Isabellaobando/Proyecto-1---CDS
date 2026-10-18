

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
        this.estadisticas = new Estadisticas();
    }

    
    public ParticipacionEnPartido(boolean titular, int minutosJugados) {
        this();
        this.titular = titular;
        setMinutosJugados(minutosJugados);
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
        if (minutosJugados < 0 || minutosJugados > 120) {
            throw new IllegalArgumentException(
                "Los minutos jugados deben estar entre 0 y 120."
            );
        }
        this.minutosJugados = minutosJugados;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Estadisticas consultarEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Estadisticas estadisticas) {
        if (estadisticas == null) {
            throw new IllegalArgumentException(
                "Las estadísticas no pueden ser nulas."
            );
        }
        this.estadisticas = estadisticas;
    }

    public int getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    public int getTarjetasRojas() {
        return tarjetasRojas;
    }

    public int getAcumulacionTarjetas() {
        return acumulacionTarjetas;
    }

   
    public void registrarParticipacion(Jugador jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException(
                "El jugador no puede ser nulo."
            );
        }
        this.jugador = jugador;
    }

    
    public void registrarTarjetaAmarilla() {
        tarjetasAmarillas++;
        actualizarAcumulacionTarjetas();
    }

   
    public void registrarTarjetaRoja() {
        tarjetasRojas++;
    }

  
    public void actualizarAcumulacionTarjetas() {
        acumulacionTarjetas = tarjetasAmarillas;
    }

   
    public boolean verificarSuspension() {
        return tarjetasRojas > 0 || acumulacionTarjetas >= 2;
    }

}