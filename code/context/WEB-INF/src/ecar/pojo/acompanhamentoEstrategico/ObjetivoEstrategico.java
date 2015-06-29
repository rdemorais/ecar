/***********************************************************************
 * Module:  ObjetivoEstrategico.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class ObjetivoEstrategico
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** @pdOid 2b8186dd-5af2-404c-8e32-6000032764ef */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ObjetivoEstrategico implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;

	private String nome;		

	public List<Estrategia> estrategias;
	
	// Propriedades oriundas da consulta listarObjetivoEstrategicoFiltro,
	// responsável por montar os objetos.
	private Long codAri;
	private String nomeCor;
	private Long codObj;
	private Long codEstrat;
	private Long codProd;
	private Long codAcao;
	private String objetivo;
	private String estrategia;
	private String produto;
	private String acao;
	private String mes;
	private String ano;
	private String parecer;
	private Long codCor;
	private String siglaIett;
	private Long codResult;
	private String resultado;
	private String prioritario;
	private String siglaEstrategia;
	private String siglaResultado;
	private String siglaProduto;
	private String siglaAcao;
	private String responsavelResultado;
	private Long codResponsavelResultado;
	private Date dataInicioResultado;
	private Date dataFimResultado;
	private Date dataInicioProduto;
	private Date dataFimProduto;
	private boolean atencao;
	private String ativoProd;
	private String ativoAcao;
	private String situacao;

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public ObjetivoEstrategico() {
	}

	public ObjetivoEstrategico(Long codigo) {
		this.codigo = codigo;
	}
	
	public ObjetivoEstrategico(Long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
		
	public ObjetivoEstrategico(Long codigo, String nome, String siglaIett) {
		this.codigo = codigo;
		this.nome = nome;
		this.setSiglaIett(siglaIett);
	}

	// Construtor responsável por montar o objeto através da conulta
	// listarObjetivoEstrategicoFiltro.
	public ObjetivoEstrategico(String mes, String ano, String parecer, Long codAri, Long codCor, String nomeCor, Long codObj,
			Long codEstrat, Long codProd, Long codAcao, String objetivo,
			String estrategia, String produto, String acao) {

		this.codAri = codAri;
		
		if(nomeCor != null) {
			this.nomeCor = nomeCor;	
		}		
		
		this.codObj = codObj;
		this.codEstrat = codEstrat;
		this.codProd = codProd;
		
		if(codAcao != null) {
			this.codAcao = codAcao;	
		}		
		
		this.objetivo = objetivo;
		this.estrategia = estrategia;
		this.produto = produto;
		
		if(acao != null) {
			this.acao = acao;	
		}	
		
		this.mes = mes;
		this.ano = ano;
		this.parecer = parecer;
		this.codCor = codCor;		
	}	

	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param newCodigo
	 * @pdOid ed68652b-db8a-4bef-b769-b2f0e9433fe9
	 */
	public void setCodigo(Long newCodigo) {
		codigo = newCodigo;
	}

	/** @pdOid 73d47936-0077-435c-b12b-6b2c6f6cb5fe */
	public String getNome() {
		return nome;
	}

	/**
	 * @param newNome
	 * @pdOid 7ec28c23-2be2-494c-b501-2474ee5e11cb
	 */
	public void setNome(String newNome) {
		nome = newNome;
	}

	/** @pdGenerated default getter */
	public java.util.List<Estrategia> getEstrategias() {
		if (estrategias == null)
			estrategias = new java.util.ArrayList<Estrategia>();
		return estrategias;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorEstrategias() {
		if (estrategias == null)
			estrategias = new java.util.ArrayList<Estrategia>();
		return estrategias.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newEstrategias
	 */
	public void setEstrategias(java.util.List<Estrategia> newEstrategias) {
		removeAllEstrategias();
		for (java.util.Iterator iter = newEstrategias.iterator(); iter
				.hasNext();)
			addEstrategias((Estrategia) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newEstrategia
	 */
	public void addEstrategias(Estrategia newEstrategia) {
		if (newEstrategia == null)
			return;
		if (this.estrategias == null)
			this.estrategias = new java.util.ArrayList<Estrategia>();
		if (!this.estrategias.contains(newEstrategia))
			this.estrategias.add(newEstrategia);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldEstrategia
	 */
	public void removeEstrategias(Estrategia oldEstrategia) {
		if (oldEstrategia == null)
			return;
		if (this.estrategias != null)
			if (this.estrategias.contains(oldEstrategia))
				this.estrategias.remove(oldEstrategia);
	}

	/** @pdGenerated default removeAll */
	public void removeAllEstrategias() {
		if (estrategias != null)
			estrategias.clear();
	}

	public Long getCodAri() {
		return codAri;
	}

	public void setCodAri(Long codAri) {
		this.codAri = codAri;
	}

	public String getNomeCor() {
		if(nomeCor == null){
			return "";
		}
		return nomeCor;
	}

	public void setNomeCor(String nomeCor) {
		this.nomeCor = nomeCor;
	}

	public Long getCodObj() {
		return codObj;
	}

	public void setCodObj(Long codObj) {
		this.codObj = codObj;
	}

	public Long getCodEstrat() {
		return codEstrat;
	}

	public void setCodEstrat(Long codEstrat) {
		this.codEstrat = codEstrat;
	}

	public Long getCodProd() {
		return codProd;
	}

	public void setCodProd(Long codProd) {
		this.codProd = codProd;
	}

	public Long getCodAcao() {
		return codAcao;
	}

	public void setCodAcao(Long codAcao) {
		this.codAcao = codAcao;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(String estrategia) {
		this.estrategia = estrategia;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}	

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public Long getCodCor() {
		if(codCor == null){
			return 0L;
		}
		return codCor;
	}

	public void setCodCor(Long codCor) {
		this.codCor = codCor;
	}

	public void setSiglaIett(String siglaIett) {
		this.siglaIett = siglaIett;
	}

	public String getSiglaIett() {
		return siglaIett;
	}

	public Long getCodResult() {
		return codResult;
	}

	public void setCodResult(Long codResult) {
		this.codResult = codResult;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getPrioritario() {
		return prioritario;
	}

	public void setPrioritario(String prioritario) {
		this.prioritario = prioritario;
	}

	public String getSiglaEstrategia() {
		return siglaEstrategia;
	}

	public void setSiglaEstrategia(String siglaEstrategia) {
		this.siglaEstrategia = siglaEstrategia;
	}

	public String getSiglaResultado() {
		return siglaResultado;
	}

	public void setSiglaResultado(String siglaResultado) {
		this.siglaResultado = siglaResultado;
	}

	public String getSiglaProduto() {
		return siglaProduto;
	}

	public void setSiglaProduto(String siglaProduto) {
		this.siglaProduto = siglaProduto;
	}

	public String getSiglaAcao() {
		return siglaAcao;
	}

	public void setSiglaAcao(String siglaAcao) {
		this.siglaAcao = siglaAcao;
	}

	public String getResponsavelResultado() {
		return responsavelResultado;
	}

	public void setResponsavelResultado(String responsavelResultado) {
		this.responsavelResultado = responsavelResultado;
	}

	public Long getCodResponsavelResultado() {
		return codResponsavelResultado;
	}

	public void setCodResponsavelResultado(Long codResponsavelResultado) {
		this.codResponsavelResultado = codResponsavelResultado;
	}

	public Date getDataInicioResultado() {
		return dataInicioResultado;
	}

	public void setDataInicioResultado(Date dataInicioResultado) {
		this.dataInicioResultado = dataInicioResultado;
	}

	public Date getDataFimResultado() {
		return dataFimResultado;
	}

	public void setDataFimResultado(Date dataFimResultado) {
		this.dataFimResultado = dataFimResultado;
	}

	public Date getDataInicioProduto() {
		return dataInicioProduto;
	}

	public void setDataInicioProduto(Date dataInicioProduto) {
		this.dataInicioProduto = dataInicioProduto;
	}

	public Date getDataFimProduto() {
		return dataFimProduto;
	}

	public void setDataFimProduto(Date dataFimProduto) {
		this.dataFimProduto = dataFimProduto;
	}

	public boolean isAtencao() {
		return atencao;
	}

	public void setAtencao(Boolean atencao) {
		if(atencao == null) {
			this.atencao = false;
		}else{
			this.atencao = atencao;	
		}		
	}

	public String getAtivoProd() {
		return ativoProd;
	}

	public void setAtivoProd(String ativoProd) {
		this.ativoProd = ativoProd;
	}

	public String getAtivoAcao() {
		return ativoAcao;
	}

	public void setAtivoAcao(String ativoAcao) {
		this.ativoAcao = ativoAcao;
	}

	@Override
	public String toString() {
		return "ObjetivoEstrategico [codigo=" + codigo + ", nome=" + nome
				+ ", estrategias=" + estrategias + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjetivoEstrategico other = (ObjetivoEstrategico) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}