package ecar.pojo;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class UnidadeOrcamentariaPeriodoExercicioUoPerExe implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -651555568532109883L;

	private ecar.pojo.UnidadeOrcamentariaPeriodoExercicioUoPerExePK compId;
	
	private String indAtivoUoPerExe;
	
	private ecar.pojo.UnidadeOrcamentariaUO unidadeOrcamentariaUO;
	
	private ecar.pojo.PeriodoExercicioPerExe periodoExercicioPerExe;

	/**
	 * @return the compId
	 */
	public ecar.pojo.UnidadeOrcamentariaPeriodoExercicioUoPerExePK getCompId() {
		return compId;
	}

	/**
	 * @param compId the compId to set
	 */
	public void setCompId(ecar.pojo.UnidadeOrcamentariaPeriodoExercicioUoPerExePK compId) {
		this.compId = compId;
	}

	/**
	 * @return the indAtivoPodPerExe
	 */
	public String getIndAtivoUoPerExe() {
		return indAtivoUoPerExe;
	}

	/**
         *
         * @param indAtivoUoPerExe
         */
	public void setIndAtivoUoPerExe(String indAtivoUoPerExe) {
		this.indAtivoUoPerExe = indAtivoUoPerExe;
	}

	/**
	 * @return the periodoExercicioPerExe
	 */
	public ecar.pojo.PeriodoExercicioPerExe getPeriodoExercicioPerExe() {
		return periodoExercicioPerExe;
	}

	/**
	 * @param periodoExercicioPerExe the periodoExercicioPerExe to set
	 */
	public void setPeriodoExercicioPerExe(
			ecar.pojo.PeriodoExercicioPerExe periodoExercicioPerExe) {
		this.periodoExercicioPerExe = periodoExercicioPerExe;
	}

	/**
	 * @return the poderPod
	 */
	public ecar.pojo.UnidadeOrcamentariaUO getUnidadeOrcamentariaUO() {
		return unidadeOrcamentariaUO;
	}

	/**
         *
         * @param unidadeOrcamentariaUO
         */
	public void setUnidadeOrcamentariaUO(ecar.pojo.UnidadeOrcamentariaUO unidadeOrcamentariaUO) {
		this.unidadeOrcamentariaUO = unidadeOrcamentariaUO;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((compId == null) ? 0 : compId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UnidadeOrcamentariaPeriodoExercicioUoPerExe other = (UnidadeOrcamentariaPeriodoExercicioUoPerExe) obj;
		if (compId == null) {
			if (other.compId != null)
				return false;
		} else if (!compId.equals(other.compId))
			return false;
		return true;
	}
	
	

}
