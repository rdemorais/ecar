package ecar.bean;

/**
 * Bean contendo as informações dos valores das metas físicas e seus exercícios.<br>
 * 
 * @author aleixo
 */

public class IndResulExercicioBean {

	private Long codExe;
	private Double valor;
	
	/**
	 * Retorna Long codExe.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Long
	 */
	public Long getCodExe() {
		return codExe;
	}
	
	/**
	 * Atribui valor especificado para Long codExe.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param codExe
	 */
	public void setCodExe(Long codExe) {
		this.codExe = codExe;
	}
	
	/**
	 * Retorna Double valor.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Double
	 */
	public Double getValor() {
		return valor;
	}
	
	/**
	 * Atribui valor especificado para Double valor.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param valor
	 */
	public void setValor(Double valor) {
		this.valor = valor;
	}
}
