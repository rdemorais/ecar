package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcaoAtividadeDependencia {
	
	@XmlElement(required = true, nillable = false)
	private String atividadePredecessora;
	
	@XmlElement(required = true, nillable = false)
	private WsDominio tipoDependencia;
	
	@XmlElement(required = true, nillable = false)
	private Integer latenciaDependencia;

	public WsAcaoAtividadeDependencia() {
	}
	
	public String getAtividadePredecessora() {
		return atividadePredecessora;
	}

	public void setAtividadePredecessora(String atividadePredecessora) {
		this.atividadePredecessora = atividadePredecessora;
	}

	public WsDominio getTipoDependencia() {
		return tipoDependencia;
	}

	public void setTipoDependencia(WsDominio tipoDependencia) {
		this.tipoDependencia = tipoDependencia;
	}

	public Integer getLatenciaDependencia() {
		return latenciaDependencia;
	}

	public void setLatenciaDependencia(Integer latenciaDependencia) {
		this.latenciaDependencia = latenciaDependencia;
	}

}
