package ecar.bean;

import java.io.Serializable;


/**
 * Bean para disponibilização de dados do item via web service apenas com Sigla e Nome do item.<br>
 * 
 * @author aleixo
 * @version 0.1 - 29/05/2007
 */

public class ItemWSMinBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6236341188225744038L;

	private String codItem; //siglaIett
	private String nomeItem; //nomeIett
	
	/**
	 * Construtor padrão
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
	 */
	public ItemWSMinBean(){
	}

	/**
	 * Construtor completo.
	 * 
	 * @author aleixo
	 * @version 0.1 - 29/05/2007
         * @param codItem
         * @param nomeItem
	 */
	public ItemWSMinBean(String codItem, String nomeItem) {
		this.codItem = codItem;
		this.nomeItem = nomeItem;
	}

	/**
	 * Retorna o valor de codItem
	 * @author aleixo
	 * @version 0.1 - 29/05/2007
	 * @return String
	 */
	public String getCodItem() {
		return codItem;
	}
	
	/**
	 * Define um valor para codItem.
	 * @author aleixo
	 * @version 0.1 - 29/05/2007
         * @param codItem
	 */
	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}
	
	/**
	 * Retorna o valor de nomeItem
	 * @author aleixo
	 * @version 0.1 - 29/05/2007
	 * @return String
	 */
	public String getNomeItem() {
		return nomeItem;
	}
	
	/**
	 * Define um valor para nomeItem.
	 * @author aleixo
	 * @version 0.1 - 29/05/2007
         * @param nomeItem
	 */
	public void setNomeItem(String nomeItem) {
		this.nomeItem = nomeItem;
	}
}
