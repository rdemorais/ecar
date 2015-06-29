package ecar.webservices;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Seguranca implements Serializable{
	private static final long serialVersionUID = 1619782537337066301L;

	private String uuid;
	private boolean autenticado;
	
	@XmlTransient
	private Long codigoUsuario;
	
	public Seguranca() {
		uuid = "";
		autenticado = false;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public boolean isAutenticado() {
		return autenticado;
	}
	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

	public Long getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Long codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	
}