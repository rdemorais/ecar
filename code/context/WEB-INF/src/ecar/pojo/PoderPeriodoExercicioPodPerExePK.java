package ecar.pojo;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class PoderPeriodoExercicioPodPerExePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5500235352230132363L;

	private Long codPod;
	
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
	 * @return the codPod
	 */
	public Long getCodPod() {
		return codPod;
	}

	/**
	 * @param codPod the codPod to set
	 */
	public void setCodPod(Long codPod) {
		this.codPod = codPod;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codPerExe == null) ? 0 : codPerExe.hashCode());
		result = PRIME * result + ((codPod == null) ? 0 : codPod.hashCode());
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
		final PoderPeriodoExercicioPodPerExePK other = (PoderPeriodoExercicioPodPerExePK) obj;
		if (codPerExe == null) {
			if (other.codPerExe != null)
				return false;
		} else if (!codPerExe.equals(other.codPerExe))
			return false;
		if (codPod == null) {
			if (other.codPod != null)
				return false;
		} else if (!codPod.equals(other.codPod))
			return false;
		return true;
	}
}
