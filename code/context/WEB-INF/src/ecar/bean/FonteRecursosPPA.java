package ecar.bean;

/**
 * Bean contendo as informações das fontes de recursos para RelatorioPPA.<br>
 * 
 * @author aleixo
 */

public class FonteRecursosPPA {
	
	private Long id;
	private String label;
	private double valor;
	
	/**
	 * Retorna Long id.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Long
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Atribui valor especificado para Long id.<br>
	 * 
         * @param id
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Retorna String label.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String getLabel
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Atribui valor espcecificado para String label.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Retorna double valor.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return double
	 */
	public double getValor() {
		return valor;
	}
	
	/**
	 * Atribui valor especificado para double valor.<br>
	 * 
         * @param valor
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	/**
	 * Soma valor especificado para o double valor.<br>
	 * 
         * @param valor
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void somaValor(double valor){
		this.valor += valor;
	}
}
