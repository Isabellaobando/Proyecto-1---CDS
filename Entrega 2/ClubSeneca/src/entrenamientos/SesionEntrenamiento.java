
package entrenamientos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import instalaciones.Instalacion;
import usuarios.Jugador;

public class SesionEntrenamiento {

    private String fecha;
    private String horaInicio;
    private int duracionMin;
    private Instalacion instalacion;
    private List<Jugador> convocados;
    private List<AsistenciaEntrenamiento> asistencias;

    public SesionEntrenamiento() 
    {
        convocados = new ArrayList<Jugador>();
        asistencias = new ArrayList<AsistenciaEntrenamiento>();
    }

    public SesionEntrenamiento(String fecha, String horaInicio, int duracionMin, Instalacion instalacion) 
    {
        this();
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        setDuracionMin(duracionMin);
        this.instalacion = instalacion;
    }

    public String getFecha() 
    {
        return fecha;
    }

    public void setFecha(String fecha) 
    {
        this.fecha = fecha;
    }

    public String getHoraInicio() 
    {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) 
    {
        this.horaInicio = horaInicio;
    }

    public int getDuracionMin() 
    {
        return duracionMin;
    }

    public void setDuracionMin(int duracionMin) 
    {
        if (duracionMin > 0) 
        {
            this.duracionMin = duracionMin;
        }
    }

    public Instalacion getInstalacion() 
    {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) 
    {
        this.instalacion = instalacion;
    }

    public void agregarJugadorConvocado(Jugador jugador) 
    {
        if (jugador != null && !convocados.contains(jugador)) 
        {
            convocados.add(jugador);
        }
    }

    public List<Jugador> consultarConvocados() 
    {
        return new ArrayList<Jugador>(convocados);
    }

    public void registrarAsistencia(Jugador jugador, String estado) 
    {
        if (jugador == null || estado == null) 
        {
            return;
        }

        if (convocados.contains(jugador)) 
        {
            if (estado.equals("Presente")
                    || estado.equals("Ausente")
                    || estado.equals("Justificado")) {
                AsistenciaEntrenamiento asistencia = buscarAsistencia(jugador);
                if (asistencia == null) {
                    asistencia = new AsistenciaEntrenamiento(this, jugador.getCodigoSocio(),
                            estado.equals("Presente"), new Date());
                    asistencias.add(asistencia);
                }
                asistencia.registrarEstado(estado);
            }
        }
    }

    public String consultarAsistencia(Jugador jugador) 
    {
        AsistenciaEntrenamiento asistencia = buscarAsistencia(jugador);
        if (asistencia == null) 
        {
            return null;
        }
        return asistencia.obtenerEstado();
    }

    private AsistenciaEntrenamiento buscarAsistencia(Jugador jugador) 
    {
        for (AsistenciaEntrenamiento asistencia : asistencias) 
        {
            if (asistencia.getIdDeportista().equals(jugador.getCodigoSocio())) 
            {
                return asistencia;
            }
        }
        return null;
    }

    public double calcularPorcentajeAsistencia() 
    {
        int presentes = 0;
        if (convocados.size() == 0) 
        {
            return 0;
        }
        for (Jugador jugador : convocados) 
        {
            if ("Presente".equals(consultarAsistencia(jugador))) 
            {
                presentes++;
            }
        }
        return presentes * 100.0 / convocados.size();
    }
}