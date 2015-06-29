/**
 * 
 */
package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author gekson.silva
 *
 */
@XmlRootElement
public class Categoria implements Serializable {
	
	private static final long serialVersionUID = 3478957157636919130L;

	private Long codigo;
	private String nome;	
	private String indAtivo;
	private Set etiquetas;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIndAtivo() {
		return indAtivo;
	}
	public void setIndAtivo(String indAtivo) {
		this.indAtivo = indAtivo;
	}
	public Set getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(Set etiquetas) {
		this.etiquetas = etiquetas;
	}
		
}
