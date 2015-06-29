package ecar.bean;

import java.util.Date;

import ecar.pojo.TipoFuncAcompTpfa;

/**
 * Bean contendo as informações para ordenação por datas para acompanhamentos.<br>
 * 
 * @author aleixo
 */

public class OrdenacaoDataTpfa {
	
	private Date data = null;
	private String label = "";
	private TipoFuncAcompTpfa tpfa = null;
	private String tpfaFixo = null;
        /**
         *
         */
        public static String FUNCAO_INICIO = "FUNCAO_INICIO";
        /**
         *
         */
        public static String FUNCAO_LIMITE = "FUNCAO_LIMITE";
	
	/**
	 * Retorna TipoFuncAcompTpfa getTpfa.<br>
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
	
	/**
	 * Retorna Date data.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Date
	 */
	public Date getData() {
		return data;
	}
	
	/**
	 * Atribui valor especificado para Date data.<br>
	 * 
         * @param data
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setData(Date data) {
		this.data = data;
	}
	
	/**
	 * Retorna String label.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Atribui valor especificado para String label.<br>
	 * 
         * @param label
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Retorna String tpfaFixo.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getTpfaFixo() {
		return tpfaFixo;
	}
	
	/**
	 * Atribui valor especificado para String tpfaFixo.<br>
	 * 
         * @param tpfaFixo
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setTpfaFixo(String tpfaFixo) {
		this.tpfaFixo = tpfaFixo;
	}
}
