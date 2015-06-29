/*
 * Created on 14/09/2006
 */
package ecar.bean;

import ecar.pojo.ItemEstruturaIett;



/**
 * Bean contendo a descrição que será apresentada na tela de listagem de itens e seu respectivo item.<br>
 * 
 * @author aleixo
 */

public class AtributoEstruturaListagemItens implements Comparable{
	
	private String descricao;
	private ItemEstruturaIett item;
	
	/**
	 * Retorna String descricao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getDescricao() {
		return descricao;
	}
	
	/**
	 * Atribui valor especificado para String descricao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	/**
	 * Retorna ItemEstruturaIett item.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return ItemEstruturaIett
	 */
	public ItemEstruturaIett getItem() {
		return item;
	}
	
	/**
	 * Atribui valor especificado para ItemEstruturaIett item.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param item
	 */
	public void setItem(ItemEstruturaIett item) {
		this.item = item;
	}

	/**
	 * Método para ordenção através do atributo descricao
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param outroAtributoEstruturaListagemItens
	 */
	public int compareTo(Object outroAtributoEstruturaListagemItens) {
				
		return this.getDescricao().toUpperCase().compareTo(
				((AtributoEstruturaListagemItens)outroAtributoEstruturaListagemItens).getDescricao().toUpperCase());
		
	}
}

