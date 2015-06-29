package ecar.pojo;

import java.io.Serializable;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class PoderPod implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1630257595332672741L;

	private Long codPod;
	
	private String siglaPod;
	
	private String nomePod;
	
	private String indAtivoPod;
	
	private Set orgaoOrgs;
	
	private Set poderPeriodoExercicioPodPerexes;

	/**
	 * Default constructor
	 */
	public PoderPod(){
		
	}
	
	/**
	 * Full Constructor
	 * @param siglaPod
	 * @param nomePod
         * @param indAtivoPod
         * @param orgaoOrgs
	 */
	public PoderPod(String siglaPod, String nomePod, String indAtivoPod, Set orgaoOrgs){
		this.siglaPod = siglaPod;
		this.nomePod = nomePod;
		this.indAtivoPod = indAtivoPod;
		this.orgaoOrgs = orgaoOrgs;
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

	/**
	 * @return the indAtivoPod
	 */
	public String getIndAtivoPod() {
		return indAtivoPod;
	}

	/**
	 * @param indAtivoPod the indAtivoPod to set
	 */
	public void setIndAtivoPod(String indAtivoPod) {
		this.indAtivoPod = indAtivoPod;
	}

	/**
	 * @return the nomePod
	 */
	public String getNomePod() {
		return nomePod;
	}

	/**
	 * @param nomePod the nomePod to set
	 */
	public void setNomePod(String nomePod) {
		this.nomePod = nomePod;
	}

	/**
	 * @return the siglaPod
	 */
	public String getSiglaPod() {
		return siglaPod;
	}

	/**
	 * @param siglaPod the siglaPod to set
	 */
	public void setSiglaPod(String siglaPod) {
		this.siglaPod = siglaPod;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
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
		final PoderPod other = (PoderPod) obj;
		if (codPod == null) {
			if (other.codPod != null)
				return false;
		} else if (!codPod.equals(other.codPod))
			return false;
		return true;
	}

	/**
	 * @return the orgaoOrgs
	 */
	public Set getOrgaoOrgs() {
		return orgaoOrgs;
	}

	/**
	 * @param orgaoOrgs the orgaoOrgs to set
	 */
	public void setOrgaoOrgs(Set orgaoOrgs) {
		this.orgaoOrgs = orgaoOrgs;
	}

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
	
	
}
