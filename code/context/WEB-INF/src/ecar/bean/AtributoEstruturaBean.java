/*
 * Created on 12/09/2006
 */
package ecar.bean;

import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaEtt;


/**
 * Bean contendo primeiro atributo válido da estrutura e sua respectiva estrutura.<br> 
 * 
 * @author aleixo
 */

public class AtributoEstruturaBean {
	private EstruturaEtt estrutura;
	private EstruturaAtributoEttat atributo;
	
	/**
	 * Retorna EstruturaAtributoEttat atributo.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return EstruturaAtributoEttat
	 */
	public EstruturaAtributoEttat getAtributo() {
		return atributo;
	}
	
	/**
	 * Atribui valor especficado para EstruturaAtributoEttat atributo.<br>
	 * 
         * @param atributo
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setAtributo(EstruturaAtributoEttat atributo) {
		this.atributo = atributo;
	}
	
	/**
	 * Retorna EstruturaEtt estrutura.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return EstruturaEtt
	 */
	public EstruturaEtt getEstrutura() {
		return estrutura;
	}
	
	/**
	 * Atribui valor especificado para EstruturaEtt estrutura.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param estrutura
	 */
	public void setEstrutura(EstruturaEtt estrutura) {
		this.estrutura = estrutura;
	}
	
}