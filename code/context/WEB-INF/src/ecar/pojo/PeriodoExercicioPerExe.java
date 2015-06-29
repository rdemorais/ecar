package ecar.pojo;

import java.io.Serializable;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class PeriodoExercicioPerExe implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1890985275504656231L;

	/** identifier field */
    private Long codPerExe;

    /** nullable persistent field */
    private String nomePerExe;

    /** persistent field */
    private Set exercicioExes;
    
    private Set poderPeriodoExercicioPodPerexes;

    private Set orgaoPeriodoExercicioOrgPerexes;
    
    private Set unidadeOrcamentariaPeriodoExercicioUoPerexes;

    /**
	 * @return the poderPeriodoExercicioPodPerexes
	 */
	public Set getPoderPeriodoExercicioPodPerexes() {
		return poderPeriodoExercicioPodPerexes;
	}

	/**
	 * @param poderPeriodoExercicioPodPerexes the poderPeriodoExercicioPodPerexes to set
	 */
	public void setPoderPeriodoExercicioPodPerexes(
			Set poderPeriodoExercicioPodPerexes) {
		this.poderPeriodoExercicioPodPerexes = poderPeriodoExercicioPodPerexes;
	}

        /** full constructor
         * @param codPerExe
         * @param exercicioExes
         * @param nomePerExe
         */
    public PeriodoExercicioPerExe(Long codPerExe, String nomePerExe, Set exercicioExes) {
    	this.codPerExe = codPerExe;
    	this.nomePerExe = nomePerExe;
    	this.exercicioExes = exercicioExes;
    }

    /** default constructor */
    public PeriodoExercicioPerExe() {
    }

    /**
     *
     * @return
     */
    public Long getCodPerExe() {
		return codPerExe;
	}

        /**
         *
         * @param codPerExe
         */
        public void setCodPerExe(Long codPerExe) {
		this.codPerExe = codPerExe;
	}

        /**
         *
         * @return
         */
        public Set getExercicioExes() {
		return exercicioExes;
	}

        /**
         *
         * @param exercicioExes
         */
        public void setExercicioExes(Set exercicioExes) {
		this.exercicioExes = exercicioExes;
	}

        /**
         *
         * @return
         */
        public String getNomePerExe() {
		return nomePerExe;
	}

        /**
         *
         * @param nomePerExe
         */
        public void setNomePerExe(String nomePerExe) {
		this.nomePerExe = nomePerExe;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codPerExe == null) ? 0 : codPerExe.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PeriodoExercicioPerExe other = (PeriodoExercicioPerExe) obj;
		if (codPerExe == null) {
			if (other.codPerExe != null)
				return false;
		} else if (!codPerExe.equals(other.codPerExe))
			return false;
		return true;
	}

	/**
	 * @return the orgaoPeriodoExercicioOrgPerexes
	 */
	public Set getOrgaoPeriodoExercicioOrgPerexes() {
		return orgaoPeriodoExercicioOrgPerexes;
	}

	/**
	 * @param orgaoPeriodoExercicioOrgPerexes the orgaoPeriodoExercicioOrgPerexes to set
	 */
	public void setOrgaoPeriodoExercicioOrgPerexes(
			Set orgaoPeriodoExercicioOrgPerexes) {
		this.orgaoPeriodoExercicioOrgPerexes = orgaoPeriodoExercicioOrgPerexes;
	}

	/**
	 * @return the unidadeOrcamentariaPeriodoExercicioUoPerexes
	 */
	public Set getUnidadeOrcamentariaPeriodoExercicioUoPerexes() {
		return unidadeOrcamentariaPeriodoExercicioUoPerexes;
	}

	/**
	 * @param unidadeOrcamentariaPeriodoExercicioUoPerexes the unidadeOrcamentariaPeriodoExercicioUoPerexes to set
	 */
	public void setUnidadeOrcamentariaPeriodoExercicioUoPerexes(
			Set unidadeOrcamentariaPeriodoExercicioUoPerexes) {
		this.unidadeOrcamentariaPeriodoExercicioUoPerexes = unidadeOrcamentariaPeriodoExercicioUoPerexes;
	}

}
