package ecar.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


/**
 * Bean para disponibilização de dados do item via web service.<br>
 * 
 * @author aleixo
 * @version 0.1 - 03/04/2007
 */

public class ItemWebServiceBean implements Serializable {
	
	/**
	 * Variável da superclasse Serializable. 
	 */
	private static final long serialVersionUID = 4562696001506498529L;

	private Long codItem;
	private String nomeItem;
	private String nomeCliente;
	private String siglaCliente;
	private Collection itensFilhos;
	private String indAtivo;
	private Date dataInicial;
	private Date dataFinal;
	private Collection indicativoItem;
	
	/**
	 * Construtor padrão
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
	 */
	public ItemWebServiceBean(){
	}

	/**
	 * Construtor completo.
	 * 
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
         * @param codItem
         * @param nomeItem
         * @param nomeCliente
         * @param itensFilhos
         * @param indAtivo
         * @param dataInicial 
         * @param dataFinal
         * @param indicativoItem
	 */
	public ItemWebServiceBean(Long codItem, String nomeItem, String nomeCliente, Collection itensFilhos, String indAtivo, Date dataInicial, Date dataFinal, Collection indicativoItem) {
		this.codItem = codItem;
		this.nomeItem = nomeItem;
		this.nomeCliente = nomeCliente;
		this.itensFilhos = itensFilhos;
		this.indAtivo = indAtivo;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.indicativoItem = indicativoItem;
	}

	/**
	 * Retorna o valor de codItem
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
	 * @return Long
	 */
	public Long getCodItem() {
		return codItem;
	}
	
	/**
	 * Define um valor para codItem.
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
         * @param codItem
	 */
	public void setCodItem(Long codItem) {
		this.codItem = codItem;
	}
	
	/**
	 * Retorna o valor de dataFinal
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
	 * @return Date
	 */
	public Date getDataFinal() {
		return dataFinal;
	}

	/**
	 * Define um valor para dataFinal.
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
         * @param dataFinal
	 */
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	/**
	 * Retorna o valor de dataInicial
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
	 * @return Date
	 */
	public Date getDataInicial() {
		return dataInicial;
	}
	
	/**
	 * Define um valor para dataInicial.
         * @param dataInicial
         * @author aleixo
	 * @version 0.1 - 03/04/2007
	 */
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	/**
	 * Retorna o valor de indAtivo
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
	 * @return String
	 */
	public String getIndAtivo() {
		return indAtivo;
	}
	
	/**
	 * Define um valor para indAtivo.
         * @param indAtivo
         * @author aleixo
	 * @version 0.1 - 03/04/2007
	 */
	public void setIndAtivo(String indAtivo) {
		this.indAtivo = indAtivo;
	}

	/**
	 * Retorna o valor de indicativoItem
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
	 * @return Collection
	 */
	public Collection getIndicativoItem() {
		return indicativoItem;
	}
	
	/**
	 * Define um valor para indicativoItem.
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
         * @param indicativoItem
	 */
	public void setIndicativoItem(Collection indicativoItem) {
		this.indicativoItem = indicativoItem;
	}
	
	/**
	 * Retorna o valor de itensFilhos
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
	 * @return Collection
	 */
	public Collection getItensFilhos() {
		return itensFilhos;
	}
	
	/**
	 * Define um valor para itensFilhos.
         * @param itensFilhos
         * @author aleixo
	 * @version 0.1 - 03/04/2007
	 */
	public void setItensFilhos(Collection itensFilhos) {
		this.itensFilhos = itensFilhos;
	}
	
	/**
	 * Retorna o valor de nomeCliente
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
	 * @return String
	 */
	public String getNomeCliente() {
		return nomeCliente;
	}
	
	/**
	 * Define um valor para nomeCliente.
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
         * @param nomeCliente
	 */
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	/**
	 * Retorna o valor de nomeItem
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
	 * @return String
	 */
	public String getNomeItem() {
		return nomeItem;
	}
	
	/**
	 * Define um valor para nomeItem.
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
         * @param nomeItem
	 */
	public void setNomeItem(String nomeItem) {
		this.nomeItem = nomeItem;
	}

	/**
	 * Retorna o valor de siglaCliente
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
	 * @return String
	 */
	public String getSiglaCliente() {
		return siglaCliente;
	}

	/**
	 * Define um valor para siglaCliente.
	 * @author aleixo
	 * @version 0.1 - 03/04/2007
         * @param siglaCliente
	 */
	public void setSiglaCliente(String siglaCliente) {
		this.siglaCliente = siglaCliente;
	}
}
