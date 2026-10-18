
package competencias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import equipos.Equipo;
import estadistica.Estadisticas;

public class Partido {

    private Date fecha;
    private String hora;
    private String estado;

    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int marcadorLocal;
    private int marcadorVisitante;
    private List<Estadisticas> estadisticas;

    public Partido() {
        estadisticas = new ArrayList<Estadisticas>();
    }

    public Partido(Date fecha, String hora, String estado,
            Equipo equipoLocal, Equipo equipoVisitante) {
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        estadisticas = new ArrayList<Estadisticas>();
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean programarPartido() {
        if (fecha == null) {
            return false;
        }

        if (hora == null || hora.equals("")) {
            return false;
        }

        if (!validarEquipos()) {
            return false;
        }

        if (!validarJugadoresHabilitados()) {
            return false;
        }

        estado = "Programado";
        return true;
    }

    public void registrarMarcador(int marcadorLocal,
            int marcadorVisitante) {
        if (marcadorLocal >= 0 && marcadorVisitante >= 0) {
            this.marcadorLocal = marcadorLocal;
            this.marcadorVisitante = marcadorVisitante;
            estado = "Finalizado";
        }
    }

    public void registrarEstadistica(Estadisticas estadistica) {
        if (estadistica != null) {
            estadisticas.add(estadistica);
        }
    }

    public String calcularResultado() {
        if (estado == null || !estado.equals("Finalizado")) {
            return "Partido no finalizado";
        }

        if (marcadorLocal > marcadorVisitante) {
            return "Victoria local: " + marcadorLocal
                    + " - " + marcadorVisitante;
        }

        if (marcadorVisitante > marcadorLocal) {
            return "Victoria visitante: " + marcadorLocal
                    + " - " + marcadorVisitante;
        }

        return "Empate: " + marcadorLocal
                + " - " + marcadorVisitante;
    }

    public List<Estadisticas> consultarEstadisticas() {
        return estadisticas;
    }

    private boolean validarEquipos() {
        if (equipoLocal == null || equipoVisitante == null) {
            return false;
        }

        if (equipoLocal == equipoVisitante) {
            return false;
        }

        if (!equipoLocal.puedeCompetir()) {
            return false;
        }

        if (!equipoVisitante.puedeCompetir()) {
            return false;
        }

        return true;
    }

    private boolean validarJugadoresHabilitados() {
        if (equipoLocal == null || equipoVisitante == null) {
            return false;
        }

        if (equipoLocal.obtenerCantidadJugadoresHabilitados() == 0) {
            return false;
        }

        if (equipoVisitante.obtenerCantidadJugadoresHabilitados() == 0) {
            return false;
        }

        return true;
    }
}

