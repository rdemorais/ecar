package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class LocAtributoLocaPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6850285260153454444L;

	/** identifier field */
    private Long codSatb;

    /** identifier field */
    private Long codLit;

    /** full constructor
     * @param codSatb
     * @param codLit
     */
    public LocAtributoLocaPK(Long codSatb, Long codLit) {
        this.codSatb = codSatb;
        this.codLit = codLit;
    }

    /** default constructor */
    public LocAtributoLocaPK() {
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

    /**
     *
     * @return
     */
    public Long getCodLit() {
        return this.codLit;
    }

    /**
     *
     * @param codLit
     */
    public void setCodLit(Long codLit) {
        this.codLit = codLit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSatb", getCodSatb())
            .append("codLit", getCodLit())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof LocAtributoLocaPK) ) return false;
        LocAtributoLocaPK castOther = (LocAtributoLocaPK) other;
        return new EqualsBuilder()
            .append(this.getCodSatb(), castOther.getCodSatb())
            .append(this.getCodLit(), castOther.getCodLit())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSatb())
            .append(getCodLit())
            .toHashCode();
    }

}
