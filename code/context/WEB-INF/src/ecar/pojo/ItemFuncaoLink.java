/*
 * Criado em 21/06/2005
 *
 * Classe utilizada para implementar a pesquisa na estrutura, 
 * quando encontrado um resultado, é incluído um objeto com nome
 * da função e o link para a mesma.
 *
 */
package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author evandro
 */
public class ItemFuncaoLink implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8522763616552973899L;

	private String nome;
	
	private String link;
	
	
	
	/**
	 * @return Returns the link.
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link The link to set.
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return Returns the nome.
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome The nome to set.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
    @Override
	public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemFuncaoLink) ) return false;
        ItemFuncaoLink castOther = (ItemFuncaoLink) other;
        return new EqualsBuilder()
            .append(this.getNome(), castOther.getNome())
            .isEquals();
    }
	
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("nome", getNome())
            .toString();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNome())
            .toHashCode();
    }
}
