package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ItemEstrutEntidadeIettePK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8318555746459446718L;

	/** identifier field */
    private Long codIett;

    /** identifier field */
    private Long codEnt;

    /** identifier field */
    private Long codTpp;

    /** full constructor
     * @param codIett
     * @param codEnt
     * @param codTpp
     */
    public ItemEstrutEntidadeIettePK(Long codIett, Long codEnt, Long codTpp) {
        this.codIett = codIett;
        this.codEnt = codEnt;
        this.codTpp = codTpp;
    }

    /** default constructor */
    public ItemEstrutEntidadeIettePK() {
    }

    /**
     *
     * @return
     */
    public Long getCodIett() {
        return this.codIett;
    }

    /**
     *
     * @param codIett
     */
    public void setCodIett(Long codIett) {
        this.codIett = codIett;
    }

    /**
     *
     * @return
     */
    public Long getCodEnt() {
        return this.codEnt;
    }

    /**
     *
     * @param codEnt
     */
    public void setCodEnt(Long codEnt) {
        this.codEnt = codEnt;
    }

    /**
     *
     * @return
     */
    public Long getCodTpp() {
        return this.codTpp;
    }

    /**
     *
     * @param codTpp
     */
    public void setCodTpp(Long codTpp) {
        this.codTpp = codTpp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIett", getCodIett())
            .append("codEnt", getCodEnt())
            .append("codTpp", getCodTpp())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrutEntidadeIettePK) ) return false;
        ItemEstrutEntidadeIettePK castOther = (ItemEstrutEntidadeIettePK) other;
        return new EqualsBuilder()
            .append(this.getCodIett(), castOther.getCodIett())
            .append(this.getCodEnt(), castOther.getCodEnt())
            .append(this.getCodTpp(), castOther.getCodTpp())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIett())
            .append(getCodEnt())
            .append(getCodTpp())
            .toHashCode();
    }

}
