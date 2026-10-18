package competencias;

import java.util.Date;

public class CompetenciaASCUN extends Competencia{
	
	private int numGrupos;
    private int equiposPorGrupo;

    public CompetenciaASCUN() {
        super();
    }

    public CompetenciaASCUN(
            String nombre,
            Date fechaInicio,
            Date fechaFin,
            int numGrupos,
            int equiposPorGrupo) {

        super(nombre, fechaInicio, fechaFin);
        this.numGrupos = numGrupos;
        this.equiposPorGrupo = equiposPorGrupo;
    }

    public int getNumGrupos() {
        return numGrupos;
    }

    public void setNumGrupos(int numGrupos) {
        this.numGrupos = numGrupos;
    }

    public int getEquiposPorGrupo() {
        return equiposPorGrupo;
    }

    public void setEquiposPorGrupo(int equiposPorGrupo) {
        this.equiposPorGrupo = equiposPorGrupo;
    }

}
