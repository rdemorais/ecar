package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class TipoEnderecoCorrTpec implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9008415892030582235L;

	/** identifier field */
    private Long codTpec;

    /** nullable persistent field */
    private String descricaoTpec;

    /** persistent field */
    private Set usuarioUsus;

    /** full constructor
     * @param descricaoTpec
     * @param usuarioUsus
     */
    public TipoEnderecoCorrTpec(String descricaoTpec, Set usuarioUsus) {
        this.descricaoTpec = descricaoTpec;
        this.usuarioUsus = usuarioUsus;
    }

    /** default constructor */
    public TipoEnderecoCorrTpec() {
    }

    /** minimal constructor
     * @param usuarioUsus
     */
    public TipoEnderecoCorrTpec(Set usuarioUsus) {
        this.usuarioUsus = usuarioUsus;
    }

    /**
     *
     * @return
     */
    public Long getCodTpec() {
        return this.codTpec;
    }

    /**
     *
     * @param codTpec
     */
    public void setCodTpec(Long codTpec) {
        this.codTpec = codTpec;
    }

    /**
     *
     * @return
     */
    public String getDescricaoTpec() {
        return this.descricaoTpec;
    }

    /**
     *
     * @param descricaoTpec
     */
    public void setDescricaoTpec(String descricaoTpec) {
        this.descricaoTpec = descricaoTpec;
    }

    /**
     *
     * @return
     */
    public Set getUsuarioUsus() {
        return this.usuarioUsus;
    }

    /**
     *
     * @param usuarioUsus
     */
    public void setUsuarioUsus(Set usuarioUsus) {
        this.usuarioUsus = usuarioUsus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codTpec", getCodTpec())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof TipoEnderecoCorrTpec) ) return false;
        TipoEnderecoCorrTpec castOther = (TipoEnderecoCorrTpec) other;
        return new EqualsBuilder()
            .append(this.getCodTpec(), castOther.getCodTpec())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodTpec())
            .toHashCode();
    }

}
