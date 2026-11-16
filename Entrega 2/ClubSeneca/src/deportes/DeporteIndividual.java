package deportes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import instalaciones.Instalacion;
import usuarios.Entrenador;
import usuarios.Jugador;
import usuarios.Usuario;

public abstract class DeporteIndividual extends Deporte implements java.io.Serializable {

    public static final Set<String> niveles = new HashSet<>(
            Set.of("PRINCIPIANTE", "INTERMEDIO", "AVANZADO"));

    protected List<InscripcionDeporteIndividual> catalogoJugadores;
    protected List<String> partidosProgramados;

    public DeporteIndividual(String nombre) {
        super(nombre);

        this.tipoDeporte = "INDIVIDUAL";
        this.catalogoJugadores = new ArrayList<>();
        this.partidosProgramados = new ArrayList<>();
    }

    public boolean inscribirJugador(
            InscripcionDeporteIndividual registroJugador) {

        if (registroJugador == null
                || registroJugador.consultarJugador() == null
                || registroJugador.consultarNivel() == null) {
            return false;
        }

        String codigoJugador =
                registroJugador.consultarJugador().getCodigoSocio();

        for (InscripcionDeporteIndividual inscripcion : catalogoJugadores) {

            if (inscripcion.consultarJugador().getCodigoSocio()
                    .equals(codigoJugador)) {
                return false;
            }
        }

        catalogoJugadores.add(registroJugador);
        return true;
    }

    public List<InscripcionDeporteIndividual> consultarJugadoresPorNivel(
            String nivel) {

        List<InscripcionDeporteIndividual> lista = new ArrayList<>();

        if (nivel == null) {
            return lista;
        }

        for (InscripcionDeporteIndividual inscripcion : catalogoJugadores) {

            if (inscripcion.consultarNivel().equalsIgnoreCase(nivel)) {
                lista.add(inscripcion);
            }
        }

        return lista;
    }

    public void actualizarRanking(
            InscripcionDeporteIndividual jugador,
            double eloGanado) {

        if (jugador == null) {
            return;
        }

        double nuevoElo = jugador.consultarElo() + eloGanado;
        jugador.setRankingElo(nuevoElo);
    }

    public boolean programarPartido(
            Usuario jugador1,
            Usuario jugador2,
            Instalacion instalacion,
            String fecha,
            String hora) {

        if (!puedenJugar(jugador1, jugador2)) {
            return false;
        }

        if (instalacion == null
                || !instalacion.isDisponible()
                || instalacion.getCapacidad() < 2) {
            return false;
        }

        if (!instalacion.getTipoDeporte().equalsIgnoreCase(this.nombre)) {
            return false;
        }

        partidosProgramados.add(
                fecha + " " + hora + " - "
                + jugador1.getNombre() + " vs "
                + jugador2.getNombre()
                + " en " + instalacion.getNombre());

        return true;
    }

    public List<String> consultarPartidosProgramados() {
        return new ArrayList<>(partidosProgramados);
    }

    public boolean puedenJugar(Usuario jugador1, Usuario jugador2) {

        if (jugador1 == null || jugador2 == null || jugador1 == jugador2) {
            return false;
        }

        if (jugador1 instanceof Entrenador
                || jugador2 instanceof Entrenador) {
            return true;
        }

        int nivel1 = posicionNivel(consultarNivel(jugador1));
        int nivel2 = posicionNivel(consultarNivel(jugador2));

        if (nivel1 == -1 || nivel2 == -1) {
            return false;
        }

        return Math.abs(nivel1 - nivel2) <= 1;
    }

    private String consultarNivel(Usuario usuario) {

        if (!(usuario instanceof Jugador)) {
            return null;
        }

        String codigo = ((Jugador) usuario).getCodigoSocio();

        for (InscripcionDeporteIndividual inscripcion : catalogoJugadores) {

            if (inscripcion.consultarJugador().getCodigoSocio()
                    .equals(codigo)) {
                return inscripcion.consultarNivel();
            }
        }

        return null;
    }

    private int posicionNivel(String nivel) {

        if (nivel == null) {
            return -1;
        }

        if (nivel.equalsIgnoreCase("PRINCIPIANTE")) {
            return 0;
        }

        if (nivel.equalsIgnoreCase("INTERMEDIO")) {
            return 1;
        }

        if (nivel.equalsIgnoreCase("AVANZADO")) {
            return 2;
        }

        return -1;
    }
}
