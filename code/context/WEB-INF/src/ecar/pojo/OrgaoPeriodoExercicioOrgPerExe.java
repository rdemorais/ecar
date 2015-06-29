package ecar.pojo;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class OrgaoPeriodoExercicioOrgPerExe implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7601177340415425448L;

	private ecar.pojo.OrgaoPeriodoExercicioOrgPerExePK compId;
	
	private String indAtivoOrgPerExe;
	
	private ecar.pojo.OrgaoOrg orgaoOrg;
	
	private ecar.pojo.PeriodoExercicioPerExe periodoExercicioPerExe;

	/**
	 * @return the compId
	 */
	public ecar.pojo.OrgaoPeriodoExercicioOrgPerExePK getCompId() {
		return compId;
	}

	/**
	 * @param compId the compId to set
	 */
	public void setCompId(ecar.pojo.OrgaoPeriodoExercicioOrgPerExePK compId) {
		this.compId = compId;
	}

	/**
	 * @return the indAtivoPodPerExe
	 */
	public String getIndAtivoOrgPerExe() {
		return indAtivoOrgPerExe;
	}

	/**
         *
         * @param indAtivoOrgPerExe
         */
	public void setIndAtivoOrgPerExe(String indAtivoOrgPerExe) {
		this.indAtivoOrgPerExe = indAtivoOrgPerExe;
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
	public ecar.pojo.OrgaoOrg getOrgaoOrg() {
		return orgaoOrg;
	}

	/**
         * @param orgaoOrg
	 */
	public void setOrgaoOrg(ecar.pojo.OrgaoOrg orgaoOrg) {
		this.orgaoOrg = orgaoOrg;
	}
	
	

}
