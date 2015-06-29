package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcaoAtividadeRestrProvid {
	
	@XmlElement(required = true, nillable = false)
	private WsDominio tipoRestricao;
	
	@XmlElement(required = true, nillable = false)
	private String descricaoRestricao;
	
	@XmlElement(required = true, nillable = false)
	private String dataRestricao;
	
	@XmlElement(required = true, nillable = false)
	private String descricaoProvidencia;
	
	@XmlElement(required = true, nillable = false)
	private String dataProvidencia;

	public WsAcaoAtividadeRestrProvid() {
	}
	
	public WsDominio getTipoRestricao() {
		return tipoRestricao;
	}

	public void setTipoRestricao(WsDominio tipoRestricao) {
		this.tipoRestricao = tipoRestricao;
	}

	public String getDescricaoRestricao() {
		return descricaoRestricao;
	}

	public void setDescricaoRestricao(String descricaoRestricao) {
		this.descricaoRestricao = descricaoRestricao;
	}

	public String getDataRestricao() {
		return dataRestricao;
	}

	public void setDataRestricao(String dataRestricao) {
		this.dataRestricao = dataRestricao;
	}

	public String getDescricaoProvidencia() {
		return descricaoProvidencia;
	}

	public void setDescricaoProvidencia(String descricaoProvidencia) {
		this.descricaoProvidencia = descricaoProvidencia;
	}

	public String getDataProvidencia() {
		return dataProvidencia;
	}

	public void setDataProvidencia(String dataProvidencia) {
		this.dataProvidencia = dataProvidencia;
	}

}
