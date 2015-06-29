package ecar.pojo;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class UnidadeOrcamentariaPeriodoExercicioUoPerExePK implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8693483087367830168L;

	private Long codUo;
	
	private Long codPerExe;

	/**
	 * @return the codPerExe
	 */
	public Long getCodPerExe() {
		return codPerExe;
	}

	/**
	 * @param codPerExe the codPerExe to set
	 */
	public void setCodPerExe(Long codPerExe) {
		this.codPerExe = codPerExe;
	}

	/**
	 * @return the codUo
	 */
	public Long getCodUo() {
		return codUo;
	}

	/**
	 * @param codUo the codUo to set
	 */
	public void setCodUo(Long codUo) {
		this.codUo = codUo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codPerExe == null) ? 0 : codPerExe.hashCode());
		result = PRIME * result + ((codUo == null) ? 0 : codUo.hashCode());
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
		final UnidadeOrcamentariaPeriodoExercicioUoPerExePK other = (UnidadeOrcamentariaPeriodoExercicioUoPerExePK) obj;
		if (codPerExe == null) {
			if (other.codPerExe != null)
				return false;
		} else if (!codPerExe.equals(other.codPerExe))
			return false;
		if (codUo == null) {
			if (other.codUo != null)
				return false;
		} else if (!codUo.equals(other.codUo))
			return false;
		return true;
	}

}
