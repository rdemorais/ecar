package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class EstrutTpFuncAcmpEtttfaPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1166629369357178482L;

	/** identifier field */
    private Long codEtt;

    /** identifier field */
    private Long codTpfa;

    /** full constructor
     * @param codEtt
     * @param codTpfa
     */
    public EstrutTpFuncAcmpEtttfaPK(Long codEtt, Long codTpfa) {
        this.codEtt = codEtt;
        this.codTpfa = codTpfa;
    }

    /** default constructor */
    public EstrutTpFuncAcmpEtttfaPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodEtt() {
        return this.codEtt;
    }

    /**
     *
     * @param codEtt
     */
    public void setCodEtt(Long codEtt) {
        this.codEtt = codEtt;
    }

    /**
     *
     * @return
     */
    public Long getCodTpfa() {
        return this.codTpfa;
    }

    /**
     *
     * @param codTpfa
     */
    public void setCodTpfa(Long codTpfa) {
        this.codTpfa = codTpfa;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codEtt", getCodEtt())
            .append("codTpfa", getCodTpfa())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EstrutTpFuncAcmpEtttfaPK) ) return false;
        EstrutTpFuncAcmpEtttfaPK castOther = (EstrutTpFuncAcmpEtttfaPK) other;
        return new EqualsBuilder()
            .append(this.getCodEtt(), castOther.getCodEtt())
            .append(this.getCodTpfa(), castOther.getCodTpfa())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodEtt())
            .append(getCodTpfa())
            .toHashCode();
    }
    
}
