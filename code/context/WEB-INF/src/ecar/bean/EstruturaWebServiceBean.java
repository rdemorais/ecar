package ecar.bean;

import java.io.Serializable;


/**
 * Bean das estruturas do item do web service.<br>
 * 
 * @author aleixo
 * @version 0.1 - 10/04/2007
 */

public class EstruturaWebServiceBean implements Serializable {
	
	/**
	 * Variável da superclasse Serializable. 
	 */
	private static final long serialVersionUID = 4562696001506498529L;

	private Long codigo;
	private String nome;

	/**
	 * Construtor padrão
	 * @author aleixo
	 * @version 0.1 - 10/04/2007
	 */
	public EstruturaWebServiceBean(){
	}

	/**
	 * Construtor completo.
	 * 
	 * @author aleixo
	 * @version 0.1 - 10/04/2007
         * @param codigo
         * @param nome
	 */
	public EstruturaWebServiceBean(Long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	/**
	 * Retorna o valor de codigo
	 * @author aleixo
	 * @version 0.1 - 10/04/2007
	 * @return Long
	 */
	public Long getCodigo() {
		return codigo;
	}
	
	/**
	 * Define um valor para codigo.
         * @param codigo
         * @author aleixo
	 * @version 0.1 - 10/04/2007
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Retorna o valor de nome
	 * @author aleixo
	 * @version 0.1 - 10/04/2007
	 * @return String
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Define um valor para nome.
         * @param nome
         * @author aleixo
	 * @version 0.1 - 10/04/2007
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
}
