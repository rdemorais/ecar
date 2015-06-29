/*
 * Created on 17/04/2006
 */
package ecar.bean;


/**
 * Bean contendo as informações de totalização de cada estrutura para imprimir no relatório.<br>
 * 
 * @author aleixo
 */

public class TotalizadorRelatorios {
	
	private Long id;
	private String estrutura;
	private int total;
	
	/**
	 * Retorna String estrutura.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getEstrutura() {
		return estrutura;
	}
	
	/**
	 * Atribui valor especificado para String estrutura.<br>
	 * 
         * @param estrutura
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setEstrutura(String estrutura) {
		this.estrutura = estrutura;
	}
	
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
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Retorna int total.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 */
	public int getTotal() {
		return total;
	}
	
	/**
	 * Atribui valor especificado para int total.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	/**
	 * Construtor.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public TotalizadorRelatorios(){
		
	}
	
	/**
	 * Incrementa total.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void incrementeTotal(){
		this.total++;
	}
}
