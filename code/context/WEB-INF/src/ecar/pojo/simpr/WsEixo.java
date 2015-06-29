package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsEixo")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsEixo {
	
	@XmlElement(required = true, nillable = false)
	private Integer chv;

	@XmlElement(required = true, nillable = false)
	private Integer chvExterno;

	@XmlElement(required = true, nillable = false)
	private WsDominio areaTematica;

	@XmlElement(required = true, nillable = false)
	private WsDominio projeto;

	@XmlElement(required = true, nillable = false)
	private String nomeEixo;

	@XmlElement(required = true, nillable = false)
	private WsPessoaJuridica orgaoResponsavel;

	@XmlElement(required = true, nillable = false)
	private String objetivo;

	public WsEixo() {
	}

	public WsEixo(Integer chv, Integer chvExterno, WsDominio areaTematica,
			WsDominio projeto, String nomeEixo,
			WsPessoaJuridica orgaoResponsavel, String objetivo) {
		this.chv = chv;
		this.chvExterno = chvExterno;
		this.areaTematica = areaTematica;
		this.projeto = projeto;
		this.nomeEixo = nomeEixo;
		this.orgaoResponsavel = orgaoResponsavel;
		this.objetivo = objetivo;
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

	public String getNomeEixo() {
		return nomeEixo;
	}

	public void setNomeEixo(String nomeEixo) {
		this.nomeEixo = nomeEixo;
	}

	public WsPessoaJuridica getOrgaoResponsavel() {
		return orgaoResponsavel;
	}

	public void setOrgaoResponsavel(WsPessoaJuridica orgaoResponsavel) {
		this.orgaoResponsavel = orgaoResponsavel;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

}
