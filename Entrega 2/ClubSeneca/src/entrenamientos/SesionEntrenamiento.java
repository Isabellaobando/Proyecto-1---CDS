
package entrenamientos;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import usuarios.Jugador;
import instalaciones.Instalacion;

public class SesionEntrenamiento {

    private Date fecha;
    private String hora;
    private int duracionMin;
    private List<Jugador> convocados;
    private HashMap<Jugador, String> asistencias;
    private Instalacion instalacion;

    public SesionEntrenamiento() {
        convocados = new ArrayList<Jugador>();
        asistencias = new HashMap<Jugador, String>();
    }

    public SesionEntrenamiento(Date fecha, String hora, int duracionMin) {
        this.fecha = fecha;
        this.hora = hora;
        this.duracionMin = duracionMin;
        convocados = new ArrayList<Jugador>();
        asistencias = new HashMap<Jugador, String>();
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

    public int getDuracionMin() {
        return duracionMin;
    }
    
    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }

    public void agregarJugadorConvocado(Jugador jugador) {
        if (jugador != null) {
            if (!convocados.contains(jugador)) {
                convocados.add(jugador);
            }
        }
    }

    public void registrarAsistencia(Jugador jugador, String estado) {
        if (jugador != null) {
            if (convocados.contains(jugador)) {
                if (validarEstado(estado)) {
                    asistencias.put(jugador, estado);
                }
            }
        }
    }

    public double calcularPorcentajeAsistencia() {
        if (convocados.size() == 0) {
            return 0;
        }

        int presentes = 0;

        for (Jugador jugador : convocados) {
            if ("Presente".equals(asistencias.get(jugador))) {
                presentes++;
            }
        }

        return presentes * 100.0 / convocados.size();
    }

    public List<Jugador> consultarConvocados() {
        return convocados;
    }

    private boolean validarEstado(String estado) {
        if (estado == null) {
            return false;
        }

        if (estado.equals("Presente")) {
            return true;
        }

        if (estado.equals("Ausente")) {
            return true;
        }

        if (estado.equals("Justificado")) {
            return true;
        }

        return false;
    }

    public boolean validarDisponibilidadInstalacion() {
        if (instalacion == null) {
            return false;
        }

        if (!instalacion.isDisponible()) {
            return false;
        }

        if (hora == null) {
            return false;
        }

        if (hora.compareTo(instalacion.getHoraApertura()) < 0) {
            return false;
        }

        if (hora.compareTo(instalacion.getHoraCierre()) >= 0) {
            return false;
        }

        return true;
    }
