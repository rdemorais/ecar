package ecar.bean;

import ecar.pojo.TipoFuncAcompTpfa;

/**
 * Bean contendo as informações das funções de tipo de acompanhamento e sua ordem.
 * 
 * @author aleixo
 */

public class SequenciaTipoFuncAcomp {
	private Integer sequencia;
	private TipoFuncAcompTpfa funcao;
	
	/**
	 * Retorna TipoFuncAcompTpfa funcao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return TipoFuncAcompTpfa
	 */
	public TipoFuncAcompTpfa getFuncao() {
		return funcao;
	}
	
	/**
	 * Atribui valor especificado para TipoFuncAcompTpfa funcao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param funcao
	 */
	public void setFuncao(TipoFuncAcompTpfa funcao) {
		this.funcao = funcao;
	}
	
	/**
	 * Retorna Integer sequencia.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Integer
	 */
	public Integer getSequencia() {
		return sequencia;
	}
	
	/**
	 * Atribui valor especificado para Integer sequencia.<br>
	 * 
         * @param sequencia
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
}
