package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class EfIettFonteTotEfieftPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2155595063623428379L;

	/** identifier field */
    private Long codIett;

    /** identifier field */
    private Long codFonr;

    /** full constructor
     * @param codIett
     * @param codFonr
     */
    public EfIettFonteTotEfieftPK(Long codIett, Long codFonr) {
        this.codIett = codIett;
        this.codFonr = codFonr;
    }

    /** default constructor */
    public EfIettFonteTotEfieftPK() {
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
            .append("codIett", getCodIett())
            .append("codFonr", getCodFonr())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EfIettFonteTotEfieftPK) ) return false;
        EfIettFonteTotEfieftPK castOther = (EfIettFonteTotEfieftPK) other;
        return new EqualsBuilder()
            .append(this.getCodIett(), castOther.getCodIett())
            .append(this.getCodFonr(), castOther.getCodFonr())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIett())
            .append(getCodFonr())
            .toHashCode();
    }

}
