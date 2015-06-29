package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsAcaoPesquisaFiltro")
@XmlRootElement		
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcaoPesquisaFiltro {
	
	@XmlElement(required = true, nillable = false)
	private WsDominio areaTematica;
	
	@XmlElement(required = true, nillable = false)
	private WsDominio projetoAcao;
	
	@XmlElement(required = true, nillable = false)
	private WsDominio eixoAcao;
	
	@XmlElement(required = true, nillable = false)
	private String nomeAcao;
	
	@XmlElement(required = true, nillable = false)
	private String nomeGerente;
	
	@XmlElement(required = true, nillable = false)
	private String dataInicio;
	
	@XmlElement(required = true, nillable = false)
	private String dataTermino;
	
	@XmlElement(required = true, nillable = false)
	private WsDominio situacaoAcao;
	
	@XmlElement(required = true, nillable = false)
	private Integer percExecucaoAcao;
	
	@XmlElement(required = true, nillable = false)
	private WsDominio tipoAcao;
	
	public WsAcaoPesquisaFiltro() {
	}

	public WsAcaoPesquisaFiltro(WsDominio areaTematica, WsDominio projetoAcao,
			WsDominio eixoAcao, String nomeAcao, String nomeGerente,
			String dataInicio, String dataTermino, WsDominio situacaoAcao,
			Integer percExecucaoAcao, WsDominio tipoAcao) {
		this.areaTematica = areaTematica;
		this.projetoAcao = projetoAcao;
		this.eixoAcao = eixoAcao;
		this.nomeAcao = nomeAcao;
		this.nomeGerente = nomeGerente;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.situacaoAcao = situacaoAcao;
		this.percExecucaoAcao = percExecucaoAcao;
		this.tipoAcao = tipoAcao;
	}

	public WsDominio getAreaTematica() {
		return areaTematica;
	}

	public void setAreaTematica(WsDominio areaTematica) {
		this.areaTematica = areaTematica;
	}

	public WsDominio getProjetoAcao() {
		return projetoAcao;
	}

	public void setProjetoAcao(WsDominio projetoAcao) {
		this.projetoAcao = projetoAcao;
	}

	public WsDominio getEixoAcao() {
		return eixoAcao;
	}

	public void setEixoAcao(WsDominio eixoAcao) {
		this.eixoAcao = eixoAcao;
	}

	public String getNomeAcao() {
		return nomeAcao;
	}

	public void setNomeAcao(String nomeAcao) {
		this.nomeAcao = nomeAcao;
	}

	public String getNomeGerente() {
		return nomeGerente;
	}

	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(String dataTermino) {
		this.dataTermino = dataTermino;
	}

	public WsDominio getSituacaoAcao() {
		return situacaoAcao;
	}

	public void setSituacaoAcao(WsDominio situacaoAcao) {
		this.situacaoAcao = situacaoAcao;
	}

	public Integer getPercExecucaoAcao() {
		return percExecucaoAcao;
	}

	public void setPercExecucaoAcao(Integer percExecucaoAcao) {
		this.percExecucaoAcao = percExecucaoAcao;
	}

	public WsDominio getTipoAcao() {
		return tipoAcao;
	}

	public void setTipoAcao(WsDominio tipoAcao) {
		this.tipoAcao = tipoAcao;
	}

}
