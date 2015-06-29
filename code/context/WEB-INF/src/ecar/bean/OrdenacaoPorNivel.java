package ecar.bean;

import java.util.ArrayList;
import java.util.List;


/**
 * Bean contendo as informações para controle de ordenacao de Iett.<br>
 * 
 * @author cristiano
 */
public class OrdenacaoPorNivel {
	private Integer nivel;
	private List ordenacaoIett = new ArrayList();
	
	/**
	 * Retorna Integer nivel.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Integer
	 */
	public Integer getNivel() {
		return nivel;
	}
	
	/**
	 * Atribui valor especificado para Integer nivel.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param nivel
	 */
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	
	/**
	 * Retorna List ordenacaoIett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return List
	 */
	public List getOrdenacaoIett() {
		return ordenacaoIett;
	}
	
	/**
	 * Atribui valor especificado para List ordenacaoIett.<br>
	 * 
         * @param ordenacaoIett
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setOrdenacaoIett(List ordenacaoIett) {
		this.ordenacaoIett = ordenacaoIett;
	}
}
