package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TipoEnderecoTend implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5483521156739721240L;

	/** identifier field */
    private Long codTpend;

    /** nullable persistent field */
    private String descricaoTpend;

    /** persistent field */
    private Set enderecoEnds;

    /** full constructor
     * @param descricaoTpend
     * @param enderecoEnds
     */
    public TipoEnderecoTend(String descricaoTpend, Set enderecoEnds) {
        this.descricaoTpend = descricaoTpend;
        this.enderecoEnds = enderecoEnds;
    }

    /** default constructor */
    public TipoEnderecoTend() {
    }

    /** minimal constructor
     * @param enderecoEnds
     */
    public TipoEnderecoTend(Set enderecoEnds) {
        this.enderecoEnds = enderecoEnds;
    }

    /**
     *
     * @return
     */
    public Long getCodTpend() {
        return this.codTpend;
    }

    /**
     *
     * @param codTpend
     */
    public void setCodTpend(Long codTpend) {
        this.codTpend = codTpend;
    }

    /**
     *
     * @return
     */
    public String getDescricaoTpend() {
        return this.descricaoTpend;
    }

    /**
     *
     * @param descricaoTpend
     */
    public void setDescricaoTpend(String descricaoTpend) {
        this.descricaoTpend = descricaoTpend;
    }

    /**
     *
     * @return
     */
    public Set getEnderecoEnds() {
        return this.enderecoEnds;
    }

    /**
     *
     * @param enderecoEnds
     */
    public void setEnderecoEnds(Set enderecoEnds) {
        this.enderecoEnds = enderecoEnds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codTpend", getCodTpend())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof TipoEnderecoTend) ) return false;
        TipoEnderecoTend castOther = (TipoEnderecoTend) other;
        return new EqualsBuilder()
            .append(this.getCodTpend(), castOther.getCodTpend())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodTpend())
            .toHashCode();
    }

}
