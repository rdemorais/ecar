package ecar.bean;

import java.io.Serializable;


/**
 * Bean dos indicativos do item do web service.<br>
 * Estes indicativos são os Níveis de Planejamento/Fases.
 * 
 * @author aleixo
 * @version 0.1 - 09/04/2007
 */

public class IndicativoItemWebServiceBean implements Serializable {
	
	/**
	 * Variável da superclasse Serializable. 
	 */
	private static final long serialVersionUID = 4562696001506498529L;

	private Long codIndicativo;
	private String descricaoIndicativo;

	/**
	 * Construtor padrão
	 * @author aleixo
	 * @version 0.1 - 09/04/2007
	 */
	public IndicativoItemWebServiceBean(){
	}

	/**
	 * Construtor completo.
	 * 
	 * @author aleixo
	 * @version 0.1 - 09/04/2007
	 * @param codIndicativo
	 * @param descricaoIndicativo
	 */
	public IndicativoItemWebServiceBean(Long codIndicativo, String descricaoIndicativo) {
		this.codIndicativo = codIndicativo;
		this.descricaoIndicativo = descricaoIndicativo;
	}

	/**
	 * Retorna o valor de codIndicativo
	 * @author aleixo
	 * @version 0.1 - 09/04/2007
	 * @return Long
	 */
	public Long getCodIndicativo() {
		return codIndicativo;
	}
	
	/**
	 * Define um valor para codIndicativo.
	 * @author aleixo
	 * @version 0.1 - 09/04/2007
         * @param codIndicativo
	 */
	public void setCodIndicativo(Long codIndicativo) {
		this.codIndicativo = codIndicativo;
	}
	
	/**
	 * Retorna o valor de descricaoIndicativo
	 * @author aleixo
	 * @version 0.1 - 09/04/2007
	 * @return String
	 */
	public String getDescricaoIndicativo() {
		return descricaoIndicativo;
	}
	
	/**
	 * Define um valor para descricaoIndicativo.
	 * @author aleixo
	 * @version 0.1 - 09/04/2007
         * @param descricaoIndicativo
	 */
	public void setDescricaoIndicativo(String descricaoIndicativo) {
		this.descricaoIndicativo = descricaoIndicativo;
	}
}
