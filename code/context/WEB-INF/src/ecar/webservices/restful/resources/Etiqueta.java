package ecar.webservices.restful.resources;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Etiqueta {

	private String id;
	private String nome;
	private String prioridade;
	private String ativo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
		
}
