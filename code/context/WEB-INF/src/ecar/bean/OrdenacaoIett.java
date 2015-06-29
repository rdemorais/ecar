/*
 * Created on 19/04/2006
 */
package ecar.bean;

import ecar.pojo.ItemEstruturaIett;


/**
 * Bean contendo as informações para controle de ordenacao de Iett.<br>
 * 
 * @author cristiano
 */

public class OrdenacaoIett {
	private ItemEstruturaIett iett;
	private String campoOrdenar;
	
	/**
	 * Retorna String campoOrdenar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getCampoOrdenar() {
		return campoOrdenar;
	}
	
	/**
	 * Atribui valor especificado para String campoOrdenar.<br>
	 * 
         * @param campoOrdenar
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setCampoOrdenar(String campoOrdenar) {
		this.campoOrdenar = campoOrdenar;
	}
	
	/**
	 * Retorna ItemEstruturaIett iett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return ItemEstruturaIett
	 */
	public ItemEstruturaIett getIett() {
		return iett;
	}
	
	/**
	 * Atribui valor especificado para ItemEstruturaIett iett.<br>
	 * 
         * @param iett
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setIett(ItemEstruturaIett iett) {
		this.iett = iett;
	}
}
