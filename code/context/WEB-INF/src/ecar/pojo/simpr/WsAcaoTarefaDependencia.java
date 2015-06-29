package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsAcaoTarefaDependencia")
@XmlRootElement	
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcaoTarefaDependencia {

	@XmlElement(required = true, nillable = false)
	private String tarefaPredecessora;

	@XmlElement(required = true, nillable = false)
	private WsDominio tipoDependencia;

	@XmlElement(required = true, nillable = false)
	private Integer latenciaDependencia;

	public WsAcaoTarefaDependencia() {
	}

	public WsAcaoTarefaDependencia(java.lang.String tarefaPredecessora,
			WsDominio tipoDependencia, java.lang.Integer latenciaDependencia) {
		this.tarefaPredecessora = tarefaPredecessora;
		this.tipoDependencia = tipoDependencia;
		this.latenciaDependencia = latenciaDependencia;
	}

	public String getTarefaPredecessora() {
		return tarefaPredecessora;
	}

	public void setTarefaPredecessora(String tarefaPredecessora) {
		this.tarefaPredecessora = tarefaPredecessora;
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
