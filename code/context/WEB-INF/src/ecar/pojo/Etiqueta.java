package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
@XmlRootElement
public class Etiqueta implements Serializable {

	private static final long serialVersionUID = 7620849192194131400L;

	private Long codigo;
	private String nome;
	private String nomeFonetico;
	private String indAtivo;
	private String indPrioritario;

	/** persistent field */
	private Set relacionamentosIettEtiqueta;
	
	private Categoria categoria;
	
	public Etiqueta(){
		
	}
	
	public Etiqueta(Long codigo, String nome){
		this.codigo = codigo;
		this.nome   = nome;
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

	public String getNomeFonetico() {
		return nomeFonetico;
	}

	public void setNomeFonetico(String nomeFonetico) {
		this.nomeFonetico = nomeFonetico;
	}

	public String getIndAtivo() {
		return indAtivo;
	}
	
	public void setIndAtivo(String indAtivo) {
		this.indAtivo = indAtivo;
	}
	
	public String getIndPrioritario() {
		return indPrioritario;
	}
	
	public void setIndPrioritario(String indPrioritario) {
		this.indPrioritario = indPrioritario;
	}
	
	public Set getRelacionamentosIettEtiqueta() {
		return relacionamentosIettEtiqueta;
	}

	public void setRelacionamentosIettEtiqueta(Set relacionamentosIettEtiqueta) {
		this.relacionamentosIettEtiqueta = relacionamentosIettEtiqueta;
	}
	
	public String getPrioritario(){
		return StringUtils.equals(indPrioritario, "S") ? "SIM" : "N&atilde;O";
	}
	
	public String getAtivo(){
		return StringUtils.equals(indAtivo, "S") ? "SIM" : "N&atilde;O";
	}

	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("codigo", getCodigo())
				.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Etiqueta other = (Etiqueta) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	

}
