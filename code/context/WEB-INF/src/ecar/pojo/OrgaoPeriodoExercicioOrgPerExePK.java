package ecar.pojo;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class OrgaoPeriodoExercicioOrgPerExePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7483994177233553246L;

	private Long codOrg;
	
	private Long codPerExe;

	
	
	/**
	 * @return the codOrg
	 */
	public Long getCodOrg() {
		return codOrg;
	}

	/**
	 * @param codOrg the codOrg to set
	 */
	public void setCodOrg(Long codOrg) {
		this.codOrg = codOrg;
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codOrg == null) ? 0 : codOrg.hashCode());
		result = PRIME * result + ((codPerExe == null) ? 0 : codPerExe.hashCode());
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
		final OrgaoPeriodoExercicioOrgPerExePK other = (OrgaoPeriodoExercicioOrgPerExePK) obj;
		if (codOrg == null) {
			if (other.codOrg != null)
				return false;
		} else if (!codOrg.equals(other.codOrg))
			return false;
		if (codPerExe == null) {
			if (other.codPerExe != null)
				return false;
		} else if (!codPerExe.equals(other.codPerExe))
			return false;
		return true;
	}

}
