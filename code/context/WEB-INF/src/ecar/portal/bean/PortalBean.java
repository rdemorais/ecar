/*
 * Created on 11/07/2005
 *
 */
package ecar.portal.bean;

import java.util.ArrayList;
import java.util.List;


/**
 * Bean contendo as informações que serão apresentadas na tela do portal.<br>
 * 
 * @author cristiano
 */

public class PortalBean {

	private List secaoBean;
	private List menuTopoBean;
    
    /**
     * Construtor
     */
	public PortalBean() {
		
	}

	/**
	 * Retorna List secaoBean.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return List
	 */
	public List getSecaoBean() {
		return secaoBean;
	}

	/**
	 * Atribui valor especificado para List sessaoBean.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param List sessaoBean
	 */
	public void setSecaoBean(List sessaoBean) {
		this.secaoBean = sessaoBean;
	}

	/**
	 * Retorna List menuTopoBean.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return List
	 */
	public List getMenuTopoBean() {
		return menuTopoBean;
	}

	/**
	 * Atribui valor especificado para List menuTopoBean.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param List menuTopoBean
	 */
	public void setMenuTopoBean(List menuTopoBean) {
		this.menuTopoBean = menuTopoBean;
	}
	
	/**
	 * Adiciona elemento no SecaoBean bean.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param SecaoBean bean
	 */
	public void adicionarElementoNoSecaoBean(SecaoBean bean) {
		if(this.secaoBean == null) {
			this.secaoBean = new ArrayList();
		}
		this.secaoBean.add(bean);		
	}
}
