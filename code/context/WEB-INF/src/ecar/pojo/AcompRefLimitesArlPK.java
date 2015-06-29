package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class AcompRefLimitesArlPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7325055960229600755L;

	/** identifier field */
    private Long codAref;

    /** identifier field */
    private Long codTpfa;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param codAref
     * @param codTpfa
     */
    public AcompRefLimitesArlPK(Long codAref, Long codTpfa) {
        this.codAref = codAref;
        this.codTpfa = codTpfa;
    }

    /** default constructor */
    public AcompRefLimitesArlPK() {
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
    public Long getCodAref() {
        return this.codAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @param codAref
     */
    public void setCodAref(Long codAref) {
        this.codAref = codAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
    public Long getCodTpfa() {
        return this.codTpfa;
    }

    /**
     * @author N/C
     * @since N/C
     * @param codTpfa
     */
    public void setCodTpfa(Long codTpfa) {
        this.codTpfa = codTpfa;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codAref", getCodAref())
            .append("codTpfa", getCodTpfa())
            .toString();
    }

    /**
     * @param other
     * @author N/C
     * @since N/C
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof AcompRefLimitesArlPK) ) return false;
        AcompRefLimitesArlPK castOther = (AcompRefLimitesArlPK) other;
        return new EqualsBuilder()
            .append(this.getCodAref(), castOther.getCodAref())
            .append(this.getCodTpfa(), castOther.getCodTpfa())
            .isEquals();
    }

    /**
     * @author N/C
     * @since N/C
     * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodAref())
            .append(getCodTpfa())
            .toHashCode();
    }

}
