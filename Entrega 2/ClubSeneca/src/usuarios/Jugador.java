package usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cafeteria.BonoDescuento;
import competencias.Torneo;
import deportes.Deporte;
import deportes.DeporteIndividual;
import deportes.InscripcionDeporteIndividual;
import equipos.Equipo;
import equipos.InscripcionEquipo;

public class Jugador extends Usuario implements Participante, java.io.Serializable {

    private String codigoSocio;
    private Date fechaAfiliacion;
    private int puntosLealtad;
    private List<InscripcionDeporteIndividual> deportesIndividuales;
    private List<InscripcionEquipo> equipos;

    public static final String TIPO_SOCIO = "Socio";
    public static final String TIPO_ESTUDIANTE = "Estudiante";
    public static final String TIPO_EXTERNO = "Externo";

    private String tipo;
    private List<BonoDescuento> bonos;

    public Jugador(int id, String nombre, String email, String login,
            String password, String telefono, Date fechaNacimiento,
            String codigoSocio, Date fechaAfiliacion, int puntosLealtad,
            int edad, String tipo) {

        super(id, nombre, email, login, password, telefono,
                fechaNacimiento, edad);

        this.codigoSocio = codigoSocio;
        this.fechaAfiliacion = fechaAfiliacion;
        this.puntosLealtad = puntosLealtad;
        this.tipo = tipo;

        this.deportesIndividuales = new ArrayList<>();
        this.equipos = new ArrayList<>();
        this.bonos = new ArrayList<>();
    }

    public String getTipo() {
        return tipo;
    }

    public String getCodigoSocio() {
        return codigoSocio;
    }

    public Date getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public int getPuntosLealtad() {
        return puntosLealtad;
    }

    public boolean inscribirseADeporte(DeporteIndividual deporte, String nivel) {

        if (deporte == null || nivel == null) {
            return false;
        }

        String nivelNormalizado = nivel.trim().toUpperCase();

        if (!DeporteIndividual.niveles.contains(nivelNormalizado)) {
            return false;
        }

        for (InscripcionDeporteIndividual inscripcion : deportesIndividuales) {
            if (inscripcion.consultarDeporte() == deporte) {
                return false;
            }
        }

        InscripcionDeporteIndividual inscripcion =
                new InscripcionDeporteIndividual(
                        deporte, nivelNormalizado, 0, this);

        boolean estado = inscripcion.inscribirJugador();

        if (!estado) {
            return false;
        }

        deportesIndividuales.add(inscripcion);
        return true;
    }

    public boolean inscribirseAEquipo(String posicion, int numCamisa,
            boolean titular, Date fechaInscripcion, Equipo equipo) {

        if (equipo == null || fechaInscripcion == null) {
            return false;
        }

        InscripcionEquipo inscripcion = new InscripcionEquipo(
                posicion, numCamisa, titular, false,
                fechaInscripcion, equipo, this);

        if (!InscripcionEquipo.sePuedeInscribir(this, equipo)) {
            return false;
        }

        boolean estado = inscripcion.inscribirJugador();

        if (estado) {
            equipos.add(inscripcion);
            return true;
        }

        return false;
    }

    public boolean retirarDeEquipo(Equipo equipo) {

        if (equipo == null) {
            return false;
        }

        for (int i = 0; i < equipos.size(); i++) {

            InscripcionEquipo inscripcion = equipos.get(i);

            if (inscripcion.getEquipo() == equipo) {

                boolean retirado = inscripcion.cancelarInscripcion();

                if (retirado) {
                    equipos.remove(i);
                    return true;
                }

                return false;
            }
        }

        return false;
    }

    public List<DeporteIndividual> consultarDeportes() {

        List<DeporteIndividual> lista = new ArrayList<>();

        for (InscripcionDeporteIndividual inscripcion : deportesIndividuales) {
            lista.add(inscripcion.consultarDeporte());
        }

        return lista;
    }

    public List<Equipo> consultarEquipos() {

        List<Equipo> lista = new ArrayList<>();

        for (InscripcionEquipo inscripcion : equipos) {
            lista.add(inscripcion.getEquipo());
        }

        return lista;
    }

    private InscripcionDeporteIndividual buscarInscripcionDeporte(
            Deporte deporte) {

        for (InscripcionDeporteIndividual inscripcion : deportesIndividuales) {

            if (inscripcion.consultarDeporte() == deporte) {
                return inscripcion;
            }
        }

        return null;
    }

    @Override
    public double getRanking(Deporte deporte) {

        InscripcionDeporteIndividual inscripcion =
                buscarInscripcionDeporte(deporte);

        if (inscripcion == null) {
            return 0;
        }

        return inscripcion.consultarElo();
    }

    @Override
    public boolean puedeInscribirse(Torneo torneo) {

        if (torneo == null || !(torneo.getDeporte() instanceof DeporteIndividual)) {
            return false;
        }

        InscripcionDeporteIndividual inscripcion =
                buscarInscripcionDeporte(torneo.getDeporte());

        if (inscripcion == null) {
            return true;
        }

        return inscripcion.consultarNivel()
                .equalsIgnoreCase(torneo.getCategoria());
    }

    @Override
    public List<Usuario> getIntegrantes() {

        List<Usuario> integrantes = new ArrayList<>();
        integrantes.add(this);

        return integrantes;
    }

    public void agregarBono(BonoDescuento bono) {

        if (bono != null) {
            bonos.add(bono);
        }
    }

    public List<BonoDescuento> getBonosDisponibles() {

        List<BonoDescuento> disponibles = new ArrayList<>();

        for (BonoDescuento bono : bonos) {
            if (!bono.getRedimido()) {
                disponibles.add(bono);
            }
        }

        return disponibles;
    }

    public BonoDescuento usarBono(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (BonoDescuento bono : getBonosDisponibles()) {

            if (bono.getCodigo().equals(codigo)) {
                return bono;
            }
        }

        return null;
    }
}