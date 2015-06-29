package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoSisAtribSgtsaPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6899218598557490585L;

	/** identifier field */
    private Long codSgti;

    /** identifier field */
    private Long codSatb;

    /** full constructor
     * @param codSgti
     * @param codSatb
     */
    public SegmentoSisAtribSgtsaPK(Long codSgti, Long codSatb) {
        this.codSgti = codSgti;
        this.codSatb = codSatb;
    }

    /** default constructor */
    public SegmentoSisAtribSgtsaPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodSgti() {
        return this.codSgti;
    }

    /**
     *
     * @param codSgti
     */
    public void setCodSgti(Long codSgti) {
        this.codSgti = codSgti;
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
            .append("codSgti", getCodSgti())
            .append("codSatb", getCodSatb())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SegmentoSisAtribSgtsaPK) ) return false;
        SegmentoSisAtribSgtsaPK castOther = (SegmentoSisAtribSgtsaPK) other;
        return new EqualsBuilder()
            .append(this.getCodSgti(), castOther.getCodSgti())
            .append(this.getCodSatb(), castOther.getCodSatb())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSgti())
            .append(getCodSatb())
            .toHashCode();
    }

}
