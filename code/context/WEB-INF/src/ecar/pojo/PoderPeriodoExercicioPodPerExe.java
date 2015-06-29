package ecar.pojo;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class PoderPeriodoExercicioPodPerExe implements Serializable {

	private static final long serialVersionUID = 5500235352230132363L;
	
	private ecar.pojo.PoderPeriodoExercicioPodPerExePK compId;
	
	private String indAtivoPodPerExe;
	
	private ecar.pojo.PoderPod poderPod;
	
	private ecar.pojo.PeriodoExercicioPerExe periodoExercicioPerExe;

	/**
	 * @return the compId
	 */
	public ecar.pojo.PoderPeriodoExercicioPodPerExePK getCompId() {
		return compId;
	}

	/**
	 * @param compId the compId to set
	 */
	public void setCompId(ecar.pojo.PoderPeriodoExercicioPodPerExePK compId) {
		this.compId = compId;
	}

	/**
	 * @return the indAtivoPodPerExe
	 */
	public String getIndAtivoPodPerExe() {
		return indAtivoPodPerExe;
	}

	/**
	 * @param indAtivoPodPerExe the indAtivoPodPerExe to set
	 */
	public void setIndAtivoPodPerExe(String indAtivoPodPerExe) {
		this.indAtivoPodPerExe = indAtivoPodPerExe;
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
	public ecar.pojo.PoderPod getPoderPod() {
		return poderPod;
	}

	/**
	 * @param poderPod the poderPod to set
	 */
	public void setPoderPod(ecar.pojo.PoderPod poderPod) {
		this.poderPod = poderPod;
	}
	
	

}
