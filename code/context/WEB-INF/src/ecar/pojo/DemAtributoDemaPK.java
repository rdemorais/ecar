package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class DemAtributoDemaPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8612737575192159463L;

	/** identifier field */
    private Long codSatb;

    /** identifier field */
    private Long codRegd;

    /** full constructor
     * @param codSatb
     * @param codRegd
     */
    public DemAtributoDemaPK(Long codSatb, Long codRegd) {
        this.codSatb = codSatb;
        this.codRegd = codRegd;
    }

    /** default constructor */
    public DemAtributoDemaPK() {
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
    public Long getCodRegd() {
        return this.codRegd;
    }

    /**
     *
     * @param codRegd
     */
    public void setCodRegd(Long codRegd) {
        this.codRegd = codRegd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSatb", getCodSatb())
            .append("codRegd", getCodRegd())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof DemAtributoDemaPK) ) return false;
        DemAtributoDemaPK castOther = (DemAtributoDemaPK) other;
        return new EqualsBuilder()
            .append(this.getCodSatb(), castOther.getCodSatb())
            .append(this.getCodRegd(), castOther.getCodRegd())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSatb())
            .append(getCodRegd())
            .toHashCode();
    }

}
