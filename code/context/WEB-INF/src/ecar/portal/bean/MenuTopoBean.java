/*
 * Created on 11/07/2005
 *
 */
package ecar.portal.bean;

import java.io.Serializable;


/**
 * Bean contendo as informações de cada opção de menu do topo da tela do portal.<br>
 * 
 * @author cristiano
 */

public class MenuTopoBean implements Serializable {

	

	private static final long serialVersionUID = -2191142848370541789L;
	private String titulo = null;
	private String descricao = null;
	private String link = null;
	private String tituloPagina = null;

	/**
	 * Retorna String tituloPagina.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getTituloPagina() {
		return tituloPagina;
	}

	/**
	 * Atribui valor especificado para String tituloPagina.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param String tituloPagina
	 */
	public void setTituloPagina(String tituloPagina) {
		this.tituloPagina = tituloPagina;
	}

	/**
     * Construtor
     */
	public MenuTopoBean() {
		
	}

	/**
	 * Retorna String descricao
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
	 * @param String descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Retorna String link.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Atribui valor especificado para String link.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param String link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Retorna valor especificado para String titulo.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Atribui valor especfificado para String titulo.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param String titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
