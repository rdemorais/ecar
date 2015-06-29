package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsAcaoPesquisaRetorno")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)		
public class WsAcaoPesquisaRetorno {
	
	@XmlElement(required = true, nillable = false)
	private Integer chv;
	
	@XmlElement(required = true, nillable = false)
	private Integer chvExterno;
	
	@XmlElement(required = true, nillable = false)
	private String nomeAcao;
	
	@XmlElement(required = true, nillable = false)
	private String dataInicioAcao;
	
	@XmlElement(required = true, nillable = false)
	private String dataTerminoAcao;
	
	@XmlElement(required = true, nillable = false)
	private String situacaoAcao;
	
	@XmlElement(required = true, nillable = false)
	private String percExecucaoAcao;
	
	@XmlElement(required = true, nillable = false)
	private String nomeProjeto;
	
	@XmlElement(required = true, nillable = false)
	private String nomeGerente;

	public WsAcaoPesquisaRetorno() {
	}

	public WsAcaoPesquisaRetorno(Integer chv, Integer chvExterno,
			String nomeAcao, String dataInicioAcao, String dataTerminoAcao,
			String situacaoAcao, String percExecucaoAcao, String nomeProjeto,
			String nomeGerente) {
		this.chv = chv;
		this.chvExterno = chvExterno;
		this.nomeAcao = nomeAcao;
		this.dataInicioAcao = dataInicioAcao;
		this.dataTerminoAcao = dataTerminoAcao;
		this.situacaoAcao = situacaoAcao;
		this.percExecucaoAcao = percExecucaoAcao;
		this.nomeProjeto = nomeProjeto;
		this.nomeGerente = nomeGerente;
	}

	public Integer getChv() {
		return chv;
	}

	public void setChv(Integer chv) {
		this.chv = chv;
	}

	public Integer getChvExterno() {
		return chvExterno;
	}

	public void setChvExterno(Integer chvExterno) {
		this.chvExterno = chvExterno;
	}

	public String getNomeAcao() {
		return nomeAcao;
	}

	public void setNomeAcao(String nomeAcao) {
		this.nomeAcao = nomeAcao;
	}

	public String getDataInicioAcao() {
		return dataInicioAcao;
	}

	public void setDataInicioAcao(String dataInicioAcao) {
		this.dataInicioAcao = dataInicioAcao;
	}

	public String getDataTerminoAcao() {
		return dataTerminoAcao;
	}

	public void setDataTerminoAcao(String dataTerminoAcao) {
		this.dataTerminoAcao = dataTerminoAcao;
	}

	public String getSituacaoAcao() {
		return situacaoAcao;
	}

	public void setSituacaoAcao(String situacaoAcao) {
		this.situacaoAcao = situacaoAcao;
	}

	public String getPercExecucaoAcao() {
		return percExecucaoAcao;
	}

	public void setPercExecucaoAcao(String percExecucaoAcao) {
		this.percExecucaoAcao = percExecucaoAcao;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	public String getNomeGerente() {
		return nomeGerente;
	}

	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
	}

}
