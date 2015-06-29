package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsIndicadorCadastro")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsIndicadorCadastro {

	@XmlElement(required = true, nillable = false)
	private Integer chvExterno;

	@XmlElement(required = true, nillable = false)
	private String nomeIndicador;

	@XmlElement(required = true, nillable = false)
	private WsDominio categoriaIndicador;

	@XmlElement(required = true, nillable = false)
	private WsDominio areaTematica;

	@XmlElement(required = true, nillable = false)
	private WsDominio projeto;

	@XmlElement(required = true, nillable = false)
	private WsDominioExterno eixo;

	@XmlElement(required = true, nillable = false)
	private WsDominioExterno acao;

	@XmlElement(required = true, nillable = false)
	private String dataCriacaoIndicador;

	@XmlElement(required = true, nillable = false)
	private String objetivoIndicador;

	@XmlElement(required = true, nillable = false)
	private Boolean sigiloso;

	@XmlElement(required = true, nillable = false)
	private WsPessoaJuridica orgaoExecucao;

	@XmlElement(required = true, nillable = false)
	private WsPessoaFisica responsavelExecucao;

	@XmlElement(required = true, nillable = false)
	private WsPessoaJuridica orgaoGestao;

	@XmlElement(required = true, nillable = false)
	private WsPessoaFisica responsavelGestao;

	@XmlElement(required = true, nillable = false)
	private WsDominio stuacaoIndicador;

	public WsIndicadorCadastro() {
	}

	public WsIndicadorCadastro(Integer chvExterno, String nomeIndicador,
			WsDominio categoriaIndicador, WsDominio areaTematica,
			WsDominio projeto, WsDominioExterno eixo, WsDominioExterno acao,
			String dataCriacaoIndicador, String objetivoIndicador,
			Boolean sigiloso, WsPessoaJuridica orgaoExecucao,
			WsPessoaFisica responsavelExecucao, WsPessoaJuridica orgaoGestao,
			WsPessoaFisica responsavelGestao, WsDominio stuacaoIndicador) {
		this.chvExterno = chvExterno;
		this.nomeIndicador = nomeIndicador;
		this.categoriaIndicador = categoriaIndicador;
		this.areaTematica = areaTematica;
		this.projeto = projeto;
		this.eixo = eixo;
		this.acao = acao;
		this.dataCriacaoIndicador = dataCriacaoIndicador;
		this.objetivoIndicador = objetivoIndicador;
		this.sigiloso = sigiloso;
		this.orgaoExecucao = orgaoExecucao;
		this.responsavelExecucao = responsavelExecucao;
		this.orgaoGestao = orgaoGestao;
		this.responsavelGestao = responsavelGestao;
		this.stuacaoIndicador = stuacaoIndicador;
	}

	public Integer getChvExterno() {
		return chvExterno;
	}

	public void setChvExterno(Integer chvExterno) {
		this.chvExterno = chvExterno;
	}

	public String getNomeIndicador() {
		return nomeIndicador;
	}

	public void setNomeIndicador(String nomeIndicador) {
		this.nomeIndicador = nomeIndicador;
	}

	public WsDominio getCategoriaIndicador() {
		return categoriaIndicador;
	}

	public void setCategoriaIndicador(WsDominio categoriaIndicador) {
		this.categoriaIndicador = categoriaIndicador;
	}

	public WsDominio getAreaTematica() {
		return areaTematica;
	}

	public void setAreaTematica(WsDominio areaTematica) {
		this.areaTematica = areaTematica;
	}

	public WsDominio getProjeto() {
		return projeto;
	}

	public void setProjeto(WsDominio projeto) {
		this.projeto = projeto;
	}

	public WsDominioExterno getEixo() {
		return eixo;
	}

	public void setEixo(WsDominioExterno eixo) {
		this.eixo = eixo;
	}

	public WsDominioExterno getAcao() {
		return acao;
	}

	public void setAcao(WsDominioExterno acao) {
		this.acao = acao;
	}

	public String getDataCriacaoIndicador() {
		return dataCriacaoIndicador;
	}

	public void setDataCriacaoIndicador(String dataCriacaoIndicador) {
		this.dataCriacaoIndicador = dataCriacaoIndicador;
	}

	public String getObjetivoIndicador() {
		return objetivoIndicador;
	}

	public void setObjetivoIndicador(String objetivoIndicador) {
		this.objetivoIndicador = objetivoIndicador;
	}

	public Boolean getSigiloso() {
		return sigiloso;
	}

	public void setSigiloso(Boolean sigiloso) {
		this.sigiloso = sigiloso;
	}

	public WsPessoaJuridica getOrgaoExecucao() {
		return orgaoExecucao;
	}

	public void setOrgaoExecucao(WsPessoaJuridica orgaoExecucao) {
		this.orgaoExecucao = orgaoExecucao;
	}

	public WsPessoaFisica getResponsavelExecucao() {
		return responsavelExecucao;
	}

	public void setResponsavelExecucao(WsPessoaFisica responsavelExecucao) {
		this.responsavelExecucao = responsavelExecucao;
	}

	public WsPessoaJuridica getOrgaoGestao() {
		return orgaoGestao;
	}

	public void setOrgaoGestao(WsPessoaJuridica orgaoGestao) {
		this.orgaoGestao = orgaoGestao;
	}

	public WsPessoaFisica getResponsavelGestao() {
		return responsavelGestao;
	}

	public void setResponsavelGestao(WsPessoaFisica responsavelGestao) {
		this.responsavelGestao = responsavelGestao;
	}

	public WsDominio getStuacaoIndicador() {
		return stuacaoIndicador;
	}

	public void setStuacaoIndicador(WsDominio stuacaoIndicador) {
		this.stuacaoIndicador = stuacaoIndicador;
	}

}
