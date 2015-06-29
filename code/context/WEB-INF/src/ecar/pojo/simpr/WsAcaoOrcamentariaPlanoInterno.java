package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcaoOrcamentariaPlanoInterno {
	
	@XmlElement(required = true, nillable = false)
	private Integer chv;
	
	@XmlElement(required = true, nillable = false)
	private String planoInterno;
	
	@XmlElement(required = true, nillable = false)
	private Integer chvAcao;
	
	@XmlElement(required = true, nillable = false)
	private WsPessoaJuridica orgao;
	
	@XmlElement(required = true, nillable = false)
	private String unidadeOrcamentaria;

	public WsAcaoOrcamentariaPlanoInterno() {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getChv() {
		return chv;
	}

	public void setChv(Integer chv) {
		this.chv = chv;
	}

	public String getPlanoInterno() {
		return planoInterno;
	}

	public void setPlanoInterno(String planoInterno) {
		this.planoInterno = planoInterno;
	}

	public Integer getChvAcao() {
		return chvAcao;
	}

	public void setChvAcao(Integer chvAcao) {
		this.chvAcao = chvAcao;
	}

	public WsPessoaJuridica getOrgao() {
		return orgao;
	}

	public void setOrgao(WsPessoaJuridica orgao) {
		this.orgao = orgao;
	}

	public String getUnidadeOrcamentaria() {
		return unidadeOrcamentaria;
	}

	public void setUnidadeOrcamentaria(String unidadeOrcamentaria) {
		this.unidadeOrcamentaria = unidadeOrcamentaria;
	}

}
