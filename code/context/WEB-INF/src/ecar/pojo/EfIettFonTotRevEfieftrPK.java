package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class EfIettFonTotRevEfieftrPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2155595063623428379L;

	/** identifier field */
    private Long codIettrev;

    /** identifier field */
    private Long codFonr;

    /** full constructor
     * @param codIettrev
     * @param codFonr
     */
    public EfIettFonTotRevEfieftrPK(Long codIettrev, Long codFonr) {
        this.codIettrev = codIettrev;
        this.codFonr = codFonr;
    }

    /** default constructor */
    public EfIettFonTotRevEfieftrPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodIettrev() {
        return this.codIettrev;
    }

    /**
     *
     * @param codIettrev
     */
    public void setCodIettrev(Long codIettrev) {
        this.codIettrev= codIettrev;
    }

    /**
     *
     * @return
     */
    public Long getCodFonr() {
        return this.codFonr;
    }

    /**
     *
     * @param codFonr
     */
    public void setCodFonr(Long codFonr) {
        this.codFonr = codFonr;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIettrev", getCodIettrev())
            .append("codFonr", getCodFonr())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EfIettFonTotRevEfieftrPK) ) return false;
        EfIettFonTotRevEfieftrPK castOther = (EfIettFonTotRevEfieftrPK) other;
        return new EqualsBuilder()
            .append(this.getCodIettrev(), castOther.getCodIettrev())
            .append(this.getCodFonr(), castOther.getCodFonr())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettrev())
            .append(getCodFonr())
            .toHashCode();
    }

}
