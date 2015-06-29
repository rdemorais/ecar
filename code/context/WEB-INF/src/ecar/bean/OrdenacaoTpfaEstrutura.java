/*
 * Created on 19/04/2006
 */
package ecar.bean;

import java.util.List;

import ecar.pojo.EstruturaEtt;


/**
 * Bean contendo as informações para controle de ordenacao de TipoFuncAcomp por Estruturas.<br>
 * 
 * @author aleixo
 */

public class OrdenacaoTpfaEstrutura {
	private EstruturaEtt estrutura;
	private List tipoFuncAcomp;
	
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
	
	/**
	 * Retorna List tipoFuncAcomp.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return List
	 */
	public List getTipoFuncAcomp() {
		return tipoFuncAcomp;
	}
	
	/**
	 * Atribui valor especificado para List tipoFuncAcomp.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param tipoFuncAcomp
	 */
	public void setTipoFuncAcomp(List tipoFuncAcomp) {
		this.tipoFuncAcomp = tipoFuncAcomp;
	}
	
}
