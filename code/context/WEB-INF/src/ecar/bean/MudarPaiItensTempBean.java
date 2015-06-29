package ecar.bean;


/**
 * @author joaoarias
 *
 */

public class MudarPaiItensTempBean {
	
	private Long codItem;
	private Long codNovoPai;
	
	
	/**
	 * Atribui valor especificado para Long codItem e Long codNovoPai.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param codItem
	 * @param codNovoPai
	 */
	public MudarPaiItensTempBean(Long codItem, Long codNovoPai) {
		this.codItem = codItem;
		this.codNovoPai = codNovoPai;
		
	}
	
	/**
	 * Retorna Long codItem.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Long
	 */
	public Long getCodItem() {
		return codItem;
	}
	
	/**
	 * Atribui valor especificado para Long codItem.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param codItem
	 */
	public void setCodItem(Long codItem) {
		this.codItem = codItem;
	}
	
	/**
	 * Retorna Long codNovoPai.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Long
	 */
	public Long getCodPai() {
		return codNovoPai;
	}
	
	/**
	 * Atribui valor especificado para Long codNovoPai.<br>
	 * 
         * @param codNovoPai
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setCodNovoPai(Long codNovoPai) {
		this.codNovoPai = codNovoPai;
	}	
	
}
