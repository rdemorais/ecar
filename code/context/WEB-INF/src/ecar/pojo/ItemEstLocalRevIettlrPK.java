package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ItemEstLocalRevIettlrPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1664542377238620415L;

	/** identifier field */
    private Integer codLit;

    /** identifier field */
    private Integer codIettrev;

    /** full constructor
     * @param codLit
     * @param codIettrev
     */
    public ItemEstLocalRevIettlrPK(Integer codLit, Integer codIettrev) {
        this.codLit = codLit;
        this.codIettrev = codIettrev;
    }

    /** default constructor */
    public ItemEstLocalRevIettlrPK() {
    }

    /**
     *
     * @return
     */
    public Integer getCodLit() {
        return this.codLit;
    }

    /**
     *
     * @param codLit
     */
    public void setCodLit(Integer codLit) {
        this.codLit = codLit;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codLit", getCodLit())
            .append("codIettrev", getCodIettrev())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof ItemEstLocalRevIettlrPK) ) return false;
        ItemEstLocalRevIettlrPK castOther = (ItemEstLocalRevIettlrPK) other;
        return new EqualsBuilder()
            .append(this.getCodLit(), castOther.getCodLit())
            .append(this.getCodIettrev(), castOther.getCodIettrev())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodLit())
            .append(getCodIettrev())
            .toHashCode();
    }

}
