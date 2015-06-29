package ecar.bean;

import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.TipoFuncAcompTpfa;

/**
 * Bean contendo as informações para permissão de acesso a um AREL.<br>
 * 
 * @author cristiano
 */

public class AcessoRelatorio {
	private TipoFuncAcompTpfa tpfa;
	private AcompRelatorioArel arel;
	private int permissao;
	
	/**
	 * Retorna AcompRelatorioArel arel.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return AcompRelatorioArel
	 */
	public AcompRelatorioArel getArel() {
		return arel;
	}
	
	/**
	 * Atribui valor especificado para AcompRelatorioArel arel.<br>
	 * 
         * @param arel
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setArel(AcompRelatorioArel arel) {
		this.arel = arel;
	}
	
	/**
	 * Retorna int permissao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 */
	public int getPermissao() {
		return permissao;
	}
	
	/**
	 * Atribui valor especificado para int permissao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param permissao
	 */
	public void setPermissao(int permissao) {
		this.permissao = permissao;
	}
	
	/**
	 * Retorna TipoFuncAcompTpfa tpfa.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return TipoFuncAcompTpfa
	 */
	public TipoFuncAcompTpfa getTpfa() {
		return tpfa;
	}
	
	/**
	 * Atribui valor especificado para TipoFuncAcompTpfa tpfa.<br>
	 * 
         * @param tpfa
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setTpfa(TipoFuncAcompTpfa tpfa) {
		this.tpfa = tpfa;
	}
}
