package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class EfIettFonTotRevEfieftr implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4618206056177065245L;

	/** identifier field */
    private ecar.pojo.EfIettFonTotRevEfieftrPK comp_id;

    /** nullable persistent field */
    private Long valorEfieftr;

    /** nullable persistent field */
    private Date dataValorEfieftr;

    /** nullable persistent field */
    private String indAtivoEfieftr;

    /** nullable persistent field */
    private Date dataInclusaoEfieftr;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev;

    /** nullable persistent field */
    private ecar.pojo.FonteRecursoFonr fonteRecursoFonr;


    /** full constructor
     * @param comp_id
     * @param valorEfieftr
     * @param dataValorEfieftr
     * @param indAtivoEfieftr
     * @param dataInclusaoEfieftr
     * @param itemEstruturarevisaoIettrev
     * @param fonteRecursoFonr
     */
    public EfIettFonTotRevEfieftr(ecar.pojo.EfIettFonTotRevEfieftrPK comp_id, Long valorEfieftr, Date dataValorEfieftr, String indAtivoEfieftr, Date dataInclusaoEfieftr, ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev, ecar.pojo.FonteRecursoFonr fonteRecursoFonr) {
        this.comp_id = comp_id;
        this.valorEfieftr = valorEfieftr;
        this.dataValorEfieftr = dataValorEfieftr;
        this.indAtivoEfieftr = indAtivoEfieftr;
        this.dataInclusaoEfieftr = dataInclusaoEfieftr;
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
        this.fonteRecursoFonr = fonteRecursoFonr;
    }

    /** default constructor */
    public EfIettFonTotRevEfieftr() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public EfIettFonTotRevEfieftr(ecar.pojo.EfIettFonTotRevEfieftrPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EfIettFonTotRevEfieftrPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.EfIettFonTotRevEfieftrPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public Long getValorEfieftr() {
        return this.valorEfieftr;
    }

    /**
     *
     * @param valorEfieftr
     */
    public void setValorEfieftr(Long valorEfieftr) {
        this.valorEfieftr = valorEfieftr;
    }

    /**
     *
     * @return
     */
    public Date getDataValorEfieftr() {
        return this.dataValorEfieftr;
    }

    /**
     *
     * @param dataValorEfieftr
     */
    public void setDataValorEfieftr(Date dataValorEfieftr) {
        this.dataValorEfieftr = dataValorEfieftr;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoEfieftr() {
        return this.indAtivoEfieftr;
    }

    /**
     *
     * @param indAtivoEfieftr
     */
    public void setIndAtivoEfieftr(String indAtivoEfieftr) {
        this.indAtivoEfieftr = indAtivoEfieftr;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoEfieftr() {
        return this.dataInclusaoEfieftr;
    }

    /**
     *
     * @param dataInclusaoEfieftr
     */
    public void setDataInclusaoEfieftr(Date dataInclusaoEfieftr) {
        this.dataInclusaoEfieftr = dataInclusaoEfieftr;
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

    /**
     *
     * @return
     */
    public ecar.pojo.FonteRecursoFonr getFonteRecursoFonr() {
        return this.fonteRecursoFonr;
    }

    /**
     *
     * @param fonteRecursoFonr
     */
    public void setFonteRecursoFonr(ecar.pojo.FonteRecursoFonr fonteRecursoFonr) {
        this.fonteRecursoFonr = fonteRecursoFonr;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EfIettFonTotRevEfieftr) ) return false;
        EfIettFonTotRevEfieftr castOther = (EfIettFonTotRevEfieftr) other;
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
