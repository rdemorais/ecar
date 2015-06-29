package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class AcompRefItemLimitesArliPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4631517589933260432L;

	/** identifier field */
    private Long codAri;

    /** identifier field */
    private Long codTpfa;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param codAri
     * @param codTpfa
     */
    public AcompRefItemLimitesArliPK(Long codAri, Long codTpfa) {
        this.codAri = codAri;
        this.codTpfa = codTpfa;
    }

    /** default constructor */
    public AcompRefItemLimitesArliPK() {
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
    public Long getCodAri() {
        return this.codAri;
    }

    /**
     * @param codAri
     * @author N/C
     * @since N/C
     */
    public void setCodAri(Long codAri) {
        this.codAri = codAri;
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
            .append("codAri", getCodAri())
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
        if ( !(other instanceof AcompRefItemLimitesArliPK) ) return false;
        AcompRefItemLimitesArliPK castOther = (AcompRefItemLimitesArliPK) other;
        return new EqualsBuilder()
            .append(this.getCodAri(), castOther.getCodAri())
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
            .append(getCodAri())
            .append(getCodTpfa())
            .toHashCode();
    }

}
