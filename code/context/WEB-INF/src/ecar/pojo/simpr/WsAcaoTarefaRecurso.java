package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsAcaoTarefaRecurso")
@XmlRootElement		
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcaoTarefaRecurso {
	
	@XmlElement(required = true, nillable = false)
	private WsDominio tipoRecurso;
	
	@XmlElement(required = true, nillable = false)
	private String grupoRecurso;
	
	@XmlElement(required = true, nillable = false)
	private String nomeRecurso;
	
	@XmlElement(required = true, nillable = false)
	private String valorRecurso;
	
	@XmlElement(required = true, nillable = false)
	private Integer qtdeMaximaRecurso;

	public WsAcaoTarefaRecurso() {
	}

	public WsAcaoTarefaRecurso(WsDominio tipoRecurso, String grupoRecurso,
			String nomeRecurso, String valorRecurso, Integer qtdeMaximaRecurso) {
		this.tipoRecurso = tipoRecurso;
		this.grupoRecurso = grupoRecurso;
		this.nomeRecurso = nomeRecurso;
		this.valorRecurso = valorRecurso;
		this.qtdeMaximaRecurso = qtdeMaximaRecurso;
	}

	public WsDominio getTipoRecurso() {
		return tipoRecurso;
	}

	public void setTipoRecurso(WsDominio tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}

	public String getGrupoRecurso() {
		return grupoRecurso;
	}

	public void setGrupoRecurso(String grupoRecurso) {
		this.grupoRecurso = grupoRecurso;
	}

	public String getNomeRecurso() {
		return nomeRecurso;
	}

	public void setNomeRecurso(String nomeRecurso) {
		this.nomeRecurso = nomeRecurso;
	}

	public String getValorRecurso() {
		return valorRecurso;
	}

	public void setValorRecurso(String valorRecurso) {
		this.valorRecurso = valorRecurso;
	}

	public Integer getQtdeMaximaRecurso() {
		return qtdeMaximaRecurso;
	}

	public void setQtdeMaximaRecurso(Integer qtdeMaximaRecurso) {
		this.qtdeMaximaRecurso = qtdeMaximaRecurso;
	}

}
