package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class TipoAcompFuncAcompPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3815803282240057255L;

	/** identifier field */
    private Long codTa;

    /** identifier field */
    private Long codTpfa;

    /** full constructor
     * @param codTa
     * @param codTpfa
     */
    public TipoAcompFuncAcompPK(Long codTa, Long codTpfa) {
        this.codTa = codTa;
        this.codTpfa = codTpfa;
    }

    /** default constructor */
    public TipoAcompFuncAcompPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodTa() {
        return this.codTa;
    }

    /**
     *
     * @param codTa
     */
    public void setCodTa(Long codTa) {
        this.codTa = codTa;
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
            .append("codTa", getCodTa())
            .append("codTpfa", getCodTpfa())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof TipoAcompFuncAcompPK) ) return false;
        TipoAcompFuncAcompPK castOther = (TipoAcompFuncAcompPK) other;
        return new EqualsBuilder()
            .append(this.getCodTa(), castOther.getCodTa())
            .append(this.getCodTpfa(), castOther.getCodTpfa())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodTa())
            .append(getCodTpfa())
            .toHashCode();
    }

}
