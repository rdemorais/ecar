package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ItemEstLocalRevIettlr implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3325486531648760821L;

	/** identifier field */
    private ecar.pojo.ItemEstLocalRevIettlrPK comp_id;

    /** nullable persistent field */
    private Date dataInclusaoIettlr;

    /** nullable persistent field */
    private ecar.pojo.LocalItemLit localItemLit;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev;

    /** full constructor
     * @param comp_id
     * @param dataInclusaoIettlr
     * @param localItemLit
     * @param itemEstruturarevisaoIettrev
     */
    public ItemEstLocalRevIettlr(ecar.pojo.ItemEstLocalRevIettlrPK comp_id, Date dataInclusaoIettlr, ecar.pojo.LocalItemLit localItemLit, ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev) {
        this.comp_id = comp_id;
        this.dataInclusaoIettlr = dataInclusaoIettlr;
        this.localItemLit = localItemLit;
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
    }

    /** default constructor */
    public ItemEstLocalRevIettlr() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public ItemEstLocalRevIettlr(ecar.pojo.ItemEstLocalRevIettlrPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstLocalRevIettlrPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.ItemEstLocalRevIettlrPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoIettlr() {
        return this.dataInclusaoIettlr;
    }

    /**
     *
     * @param dataInclusaoIettlr
     */
    public void setDataInclusaoIettlr(Date dataInclusaoIettlr) {
        this.dataInclusaoIettlr = dataInclusaoIettlr;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.LocalItemLit getLocalItemLit() {
        return this.localItemLit;
    }

    /**
     *
     * @param localItemLit
     */
    public void setLocalItemLit(ecar.pojo.LocalItemLit localItemLit) {
        this.localItemLit = localItemLit;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturarevisaoIettrev getItemEstruturarevisaoIettrev() {
        return this.itemEstruturarevisaoIettrev;
    }

    /**
     *
     * @param itemEstruturarevisaoIettrev
     */
    public void setItemEstruturarevisaoIettrev(ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev) {
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof ItemEstLocalRevIettlr) ) return false;
        ItemEstLocalRevIettlr castOther = (ItemEstLocalRevIettlr) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}
