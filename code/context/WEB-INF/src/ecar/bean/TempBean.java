package ecar.bean;


/**
 * @author cristiano
 *
 */

public class TempBean {
	private Long codLit = null;
	private Integer mes = null;
	private Integer ano = null;
	private Double qtde = null;
	
	
	/**
	 * Retorna Integer ano.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Integer
	 */
	public Integer getAno() {
		return ano;
	}
	
	/**
	 * Atribui valor especificado para Integer ano.<br>
	 * 
         * @param ano
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	/**
	 * Retorna Long codLit.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Long
	 */
	public Long getCodLit() {
		return codLit;
	}
	
	/**
	 * Atribui valor especificado para Long codLit.<br>
	 * 
         * @param codLit
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setCodLit(Long codLit) {
		this.codLit = codLit;
	}
	
	/**
	 * Retorna Integer mes.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Integer
	 */
	public Integer getMes() {
		return mes;
	}
	
	/**
	 * Atribui valor especificado para Integer mes.<br>
	 * 
         * @param mes
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	
	/**
	 * Retorna Double qtde.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Double
	 */
	public Double getQtde() {
		return qtde;
	}
	
	/**
	 * Atribui valor especificado para Double qtde.<br>
	 * 
         * @param qtde
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}
}
