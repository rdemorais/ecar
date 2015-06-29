package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IettUsutpfuacrevIettutfarPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1567621139531202406L;

	/** identifier field */
    private Integer codTpfa;

    /** identifier field */
    private Integer codIettrev;

    /** identifier field */
    private Integer codUsu;

    /** full constructor
     * @param codTpfa
     * @param codIettrev
     * @param codUsu
     */
    public IettUsutpfuacrevIettutfarPK(Integer codTpfa, Integer codIettrev, Integer codUsu) {
        this.codTpfa = codTpfa;
        this.codIettrev = codIettrev;
        this.codUsu = codUsu;
    }

    /** default constructor */
    public IettUsutpfuacrevIettutfarPK() {
    }

    /**
     *
     * @return
     */
    public Integer getCodTpfa() {
        return this.codTpfa;
    }

    /**
     *
     * @param codTpfa
     */
    public void setCodTpfa(Integer codTpfa) {
        this.codTpfa = codTpfa;
    }

    /**
     *
     * @return
     */
    public Integer getCodIettrev() {
        return this.codIettrev;
    }

    /**
     *
     * @param codIettrev
     */
    public void setCodIettrev(Integer codIettrev) {
        this.codIettrev = codIettrev;
    }

    /**
     *
     * @return
     */
    public Integer getCodUsu() {
        return this.codUsu;
    }

    /**
     *
     * @param codUsu
     */
    public void setCodUsu(Integer codUsu) {
        this.codUsu = codUsu;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codTpfa", getCodTpfa())
            .append("codIettrev", getCodIettrev())
            .append("codUsu", getCodUsu())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof IettUsutpfuacrevIettutfarPK) ) return false;
        IettUsutpfuacrevIettutfarPK castOther = (IettUsutpfuacrevIettutfarPK) other;
        return new EqualsBuilder()
            .append(this.getCodTpfa(), castOther.getCodTpfa())
            .append(this.getCodIettrev(), castOther.getCodIettrev())
            .append(this.getCodUsu(), castOther.getCodUsu())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodTpfa())
            .append(getCodIettrev())
            .append(getCodUsu())
            .toHashCode();
    }

}
