/**
 * 
 */
package ecar.pojo.acompanhamentoEstrategico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author gekson.silva
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Categoria {

	private Long codigo;
	private String nome;		
	
	public Categoria() {
		this.codigo = 0L;
		this.nome = "";
	}
	
	public Categoria(Long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	
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
	
}
