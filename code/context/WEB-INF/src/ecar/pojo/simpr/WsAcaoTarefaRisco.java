package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsAcaoTarefaRisco")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcaoTarefaRisco {
	
	@XmlElement(required = true, nillable = false)
	private String descricaoRisco;

	public WsAcaoTarefaRisco() {
	}

	public WsAcaoTarefaRisco(String descricaoRisco) {
		this.descricaoRisco = descricaoRisco;
	}

	public String getDescricaoRisco() {
		return descricaoRisco;
	}

	public void setDescricaoRisco(String descricaoRisco) {
		this.descricaoRisco = descricaoRisco;
	}

}
