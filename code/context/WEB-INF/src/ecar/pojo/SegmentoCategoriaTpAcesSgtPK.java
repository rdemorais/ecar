package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoCategoriaTpAcesSgtPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7005317371229087677L;

	/** identifier field */
    private Long codSgtc;

    /** identifier field */
    private Long codSatb;

    /** full constructor
     * @param codSgtc
     * @param codSatb
     */
    public SegmentoCategoriaTpAcesSgtPK(Long codSgtc, Long codSatb) {
        this.codSgtc = codSgtc;
        this.codSatb = codSatb;
    }

    /** default constructor */
    public SegmentoCategoriaTpAcesSgtPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodSgtc() {
        return this.codSgtc;
    }

    /**
     *
     * @param codSgtc
     */
    public void setCodSgtc(Long codSgtc) {
        this.codSgtc = codSgtc;
    }

    /**
     *
     * @return
     */
    public Long getCodSatb() {
        return this.codSatb;
    }

    /**
     *
     * @param codSatb
     */
    public void setCodSatb(Long codSatb) {
        this.codSatb = codSatb;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSgtc", getCodSgtc())
            .append("codSatb", getCodSatb())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SegmentoCategoriaTpAcesSgtPK) ) return false;
        SegmentoCategoriaTpAcesSgtPK castOther = (SegmentoCategoriaTpAcesSgtPK) other;
        return new EqualsBuilder()
            .append(this.getCodSgtc(), castOther.getCodSgtc())
            .append(this.getCodSatb(), castOther.getCodSatb())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSgtc())
            .append(getCodSatb())
            .toHashCode();
    }

}
