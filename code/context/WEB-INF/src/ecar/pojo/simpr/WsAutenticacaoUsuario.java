package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsAutenticacaoUsuario")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAutenticacaoUsuario {
	
	@XmlElement(required = true, nillable = false)
	private String cpf;
	
	@XmlElement(required = true, nillable = false)
	private String senha;

	public WsAutenticacaoUsuario() {
	}

	public WsAutenticacaoUsuario(java.lang.String cpf, java.lang.String senha) {
		this.cpf = cpf;
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
