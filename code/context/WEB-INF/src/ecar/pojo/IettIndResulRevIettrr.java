package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IettIndResulRevIettrr implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 151818856121043596L;

	/** identifier field */
    private Integer codIettirr;

    /** nullable persistent field */
    //private String unidMedidaIettrr;

    /** nullable persistent field */
    //private String descricaoIettirr;

    /** nullable persistent field */
    //private String nomeIettirr;

    /** nullable persistent field */
    //private String indProjecaoIettrr;

    /** nullable persistent field */
    //private String indAcumulavelIettrr;

    /** nullable persistent field */
    //private String indTipoqtde;

    /** persistent field */
    private ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev;

    /** persistent field */
    private Set itemEstFisicoRevIettfrs;
    
    private ItemEstrtIndResulIettr itemEstrtIndResulIettr;

    /** full constructor
     * @param codIettirr
     * @param unidMedidaIettrr
     * @param descricaoIettirr
     * @param itemEstruturarevisaoIettrev
     * @param nomeIettirr
     * @param indAcumulavelIettrr
     * @param indProjecaoIettrr
     * @param indTipoqtde
     * @param indPublicoIettrr
     * @param itemEstFisicoRevIettfrs
     */
    public IettIndResulRevIettrr(Integer codIettirr, String unidMedidaIettrr, String descricaoIettirr, String nomeIettirr, String indProjecaoIettrr, String indAcumulavelIettrr, String indPublicoIettrr, String indTipoqtde, ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev, Set itemEstFisicoRevIettfrs) {
        this.codIettirr = codIettirr;
        /*
        this.unidMedidaIettrr = unidMedidaIettrr;
        this.descricaoIettirr = descricaoIettirr;
        this.nomeIettirr = nomeIettirr;
        this.indProjecaoIettrr = indProjecaoIettrr;
        this.indAcumulavelIettrr = indAcumulavelIettrr;
        this.indTipoqtde = indTipoqtde;
        */
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
        this.itemEstFisicoRevIettfrs = itemEstFisicoRevIettfrs;
    }

    /** default constructor */
    public IettIndResulRevIettrr() {
    }

    /** minimal constructor
     * @param codIettirr
     * @param itemEstruturarevisaoIettrev
     * @param itemEstFisicoRevIettfrs
     */
    public IettIndResulRevIettrr(Integer codIettirr, ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev, Set itemEstFisicoRevIettfrs) {
        this.codIettirr = codIettirr;
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
        this.itemEstFisicoRevIettfrs = itemEstFisicoRevIettfrs;
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
    /*
    public String getUnidMedidaIettrr() {
        return this.unidMedidaIettrr;
    }

    public void setUnidMedidaIettrr(String unidMedidaIettrr) {
        this.unidMedidaIettrr = unidMedidaIettrr;
    }

    public String getDescricaoIettirr() {
        return this.descricaoIettirr;
    }

    public void setDescricaoIettirr(String descricaoIettirr) {
        this.descricaoIettirr = descricaoIettirr;
    }

    public String getNomeIettirr() {
        return this.nomeIettirr;
    }

    public void setNomeIettirr(String nomeIettirr) {
        this.nomeIettirr = nomeIettirr;
    }

    public String getIndProjecaoIettrr() {
        return this.indProjecaoIettrr;
    }

    public void setIndProjecaoIettrr(String indProjecaoIettrr) {
        this.indProjecaoIettrr = indProjecaoIettrr;
    }

    public String getIndAcumulavelIettrr() {
        return this.indAcumulavelIettrr;
    }

    public void setIndAcumulavelIettrr(String indAcumulavelIettrr) {
        this.indAcumulavelIettrr = indAcumulavelIettrr;
    }

    public String getIndTipoqtde() {
        return this.indTipoqtde;
    }

    public void setIndTipoqtde(String indTipoqtde) {
        this.indTipoqtde = indTipoqtde;
    }
    */
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
    public Set getItemEstFisicoRevIettfrs() {
        return this.itemEstFisicoRevIettfrs;
    }

    /**
     *
     * @param itemEstFisicoRevIettfrs
     */
    public void setItemEstFisicoRevIettfrs(Set itemEstFisicoRevIettfrs) {
        this.itemEstFisicoRevIettfrs = itemEstFisicoRevIettfrs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIettirr", getCodIettirr())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof IettIndResulRevIettrr) ) return false;
        IettIndResulRevIettrr castOther = (IettIndResulRevIettrr) other;
        return new EqualsBuilder()
            .append(this.getCodIettirr(), castOther.getCodIettirr())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettirr())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public ItemEstrtIndResulIettr getItemEstrtIndResulIettr() {
		return itemEstrtIndResulIettr;
	}

        /**
         *
         * @param itemEstrtIndResulIettr
         */
        public void setItemEstrtIndResulIettr(
			ItemEstrtIndResulIettr itemEstrtIndResulIettr) {
		this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
	}

}
