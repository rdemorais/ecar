package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TfuncacompConfigmailTfacfgmPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7946146971588962280L;

	/** identifier field */
    private Integer codCfgm;

    /** identifier field */
    private Long codTpfa;

    /** full constructor
     * @param codCfgm
     * @param codTpfa
     */
    public TfuncacompConfigmailTfacfgmPK(Integer codCfgm, Long codTpfa) {
        this.codCfgm = codCfgm;
        this.codTpfa = codTpfa;
    }

    /** default constructor */
    public TfuncacompConfigmailTfacfgmPK() {
    }

    /**
     *
     * @return
     */
    public Integer getCodCfgm() {
        return this.codCfgm;
    }

    /**
     *
     * @param codCfgm
     */
    public void setCodCfgm(Integer codCfgm) {
        this.codCfgm = codCfgm;
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
            .append("codCfgm", getCodCfgm())
            .append("codTpfa", getCodTpfa())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof TfuncacompConfigmailTfacfgmPK) ) return false;
        TfuncacompConfigmailTfacfgmPK castOther = (TfuncacompConfigmailTfacfgmPK) other;
        return new EqualsBuilder()
            .append(this.getCodCfgm(), castOther.getCodCfgm())
            .append(this.getCodTpfa(), castOther.getCodTpfa())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodCfgm())
            .append(getCodTpfa())
            .toHashCode();
    }

}
