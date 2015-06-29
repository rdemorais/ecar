package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ItemEstFisicoRevIettfrPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4623905650561792123L;

	/** identifier field */
    private Integer codExe;

    /** identifier field */
    private Integer codIettirr;

    /** full constructor
     * @param codExe
     * @param codIettirr
     */
    public ItemEstFisicoRevIettfrPK(Integer codExe, Integer codIettirr) {
        this.codExe = codExe;
        this.codIettirr = codIettirr;
    }

    /** default constructor */
    public ItemEstFisicoRevIettfrPK() {
    }

    /**
     *
     * @return
     */
    public Integer getCodExe() {
        return this.codExe;
    }

    /**
     *
     * @param codExe
     */
    public void setCodExe(Integer codExe) {
        this.codExe = codExe;
    }

    /**
     *
     * @return
     */
    public Integer getCodIettirr() {
        return this.codIettirr;
    }

    /**
     *
     * @param codIettirr
     */
    public void setCodIettirr(Integer codIettirr) {
        this.codIettirr = codIettirr;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codExe", getCodExe())
            .append("codIettirr", getCodIettirr())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof ItemEstFisicoRevIettfrPK) ) return false;
        ItemEstFisicoRevIettfrPK castOther = (ItemEstFisicoRevIettfrPK) other;
        return new EqualsBuilder()
            .append(this.getCodExe(), castOther.getCodExe())
            .append(this.getCodIettirr(), castOther.getCodIettirr())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodExe())
            .append(getCodIettirr())
            .toHashCode();
    }

}
