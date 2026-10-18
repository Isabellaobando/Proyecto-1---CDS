package entrenamientos;

import java.util.Date;

public class AsistenciaEntrenamiento {

    private SesionEntrenamiento sesion;
    private String idDeportista;
    private boolean asistio;
    private Date fechaRegistro;
    private String estado;

    public AsistenciaEntrenamiento() {
    }

    public AsistenciaEntrenamiento(SesionEntrenamiento sesion,
            String idDeportista, boolean asistio, Date fechaRegistro) {
        this.sesion = sesion;
        this.idDeportista = idDeportista;
        this.asistio = asistio;
        this.fechaRegistro = fechaRegistro;

        if (asistio) {
            estado = "Presente";
        } else {
            estado = "Ausente";
        }
    }

    public SesionEntrenamiento getSesion() {
        return sesion;
    }

    public void setSesion(SesionEntrenamiento sesion) {
        this.sesion = sesion;
    }

    public String getIdDeportista() {
        return idDeportista;
    }

    public void setIdDeportista(String idDeportista) {
        this.idDeportista = idDeportista;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;

        if (asistio) {
            estado = "Presente";
        } else {
            estado = "Ausente";
        }
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void registrarEstado(String estado) {
        if (validarEstado(estado)) {
            this.estado = estado;

            if (estado.equals("Presente")) {
                asistio = true;
            } else {
                asistio = false;
            }
        }
    }

    public String obtenerEstado() {
        return estado;
    }

    public boolean validarEstado(String estado) {
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
}
