/*
 * Created on 11/07/2005
 *
 */
package ecar.portal.bean;

import java.util.List;


/**
 * Bean contendo as informações de uma seção que serão apresentadas na tela do portal.<br>
 * 
 * @author cristiano
 */

public class SecaoBean {

	private String id;
	private String titulo;
	private List informacaoBean; 
    
    /**
     * Construtor
     */
	public SecaoBean() {
		
	}

	/**
	 * Retorna List informacaoBean.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return List
	 */
	public List getInformacaoBean() {
		return informacaoBean;
	}

	/**
	 * Atribui valor especificado para List informacaoBean.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param List informacaoBean
	 */
	public void setInformacaoBean(List informacaoBean) {
		this.informacaoBean = informacaoBean;
	}

	/**
	 * Retorna String titulo.<br>
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
	 * Atribui valor especificado para String titulo.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param String titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Retorna String id.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * Atribui valor especificado para String id.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param String id
	 */
	public void setId(String id) {
		this.id = id;
	}
}
