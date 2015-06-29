package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PeriodicidadePrdc implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5830633285541411158L;

	/** identifier field */
    private Long codPrdc;

    /** nullable persistent field */
    private Integer numMesesPrdc;

    /** nullable persistent field */
    private String descricaoPrdc;

    /** persistent field */
    private Set configuracaoCfgs;

    /** persistent field */
    private Set itemEstruturaIetts;
    
    private Set itemEstruturarevisaoIettrevs;
    
    private Set itemEstrtIndResulIettr;
    
    /* Mantis #2156 */
    private Set historicoIettHs;

    /**
     *
     * @return
     */
    public Set getHistoricoIettHs() {
		return historicoIettHs;
	}

    /**
     *
     * @param historicoIettHs
     */
    public void setHistoricoIettHs(Set historicoIettHs) {
		this.historicoIettHs = historicoIettHs;
	}

        /** full constructor
         * @param numMesesPrdc
         * @param configuracaoCfgs
         * @param descricaoPrdc
         * @param itemEstruturaIetts
         * @param itemEstruturarevisaoIettrevs
         */
    public PeriodicidadePrdc(Integer numMesesPrdc, String descricaoPrdc, Set configuracaoCfgs, Set itemEstruturaIetts, Set itemEstruturarevisaoIettrevs) {
        this.numMesesPrdc = numMesesPrdc;
        this.descricaoPrdc = descricaoPrdc;
        this.configuracaoCfgs = configuracaoCfgs;
        this.itemEstruturaIetts = itemEstruturaIetts;
        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
        this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
       
    }

    /** default constructor */
    public PeriodicidadePrdc() {
    }

    /** minimal constructor
     * @param configuracaoCfgs
     * @param itemEstruturaIetts
     * @param itemEstruturarevisaoIettrevs
     */
    public PeriodicidadePrdc(Set configuracaoCfgs, Set itemEstruturaIetts, Set itemEstruturarevisaoIettrevs) {
        this.configuracaoCfgs = configuracaoCfgs;
        this.itemEstruturaIetts = itemEstruturaIetts;
        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
    }

    /**
     *
     * @return
     */
    public Long getCodPrdc() {
        return this.codPrdc;
    }

    /**
     *
     * @param codPrdc
     */
    public void setCodPrdc(Long codPrdc) {
        this.codPrdc = codPrdc;
    }

    /**
     *
     * @return
     */
    public Integer getNumMesesPrdc() {
        return this.numMesesPrdc;
    }

    /**
     *
     * @param numMesesPrdc
     */
    public void setNumMesesPrdc(Integer numMesesPrdc) {
        this.numMesesPrdc = numMesesPrdc;
    }

    /**
     *
     * @return
     */
    public String getDescricaoPrdc() {
        return this.descricaoPrdc;
    }

    /**
     *
     * @param descricaoPrdc
     */
    public void setDescricaoPrdc(String descricaoPrdc) {
        this.descricaoPrdc = descricaoPrdc;
    }

    /**
     *
     * @return
     */
    public Set getConfiguracaoCfgs() {
        return this.configuracaoCfgs;
    }

    /**
     *
     * @param configuracaoCfgs
     */
    public void setConfiguracaoCfgs(Set configuracaoCfgs) {
        this.configuracaoCfgs = configuracaoCfgs;
    }

    /**
     *
     * @return
     */
    public Set getItemEstruturaIetts() {
        return this.itemEstruturaIetts;
    }

    /**
     *
     * @param itemEstruturaIetts
     */
    public void setItemEstruturaIetts(Set itemEstruturaIetts) {
        this.itemEstruturaIetts = itemEstruturaIetts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codPrdc", getCodPrdc())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PeriodicidadePrdc) ) return false;
        PeriodicidadePrdc castOther = (PeriodicidadePrdc) other;
        return new EqualsBuilder()
            .append(this.getCodPrdc(), castOther.getCodPrdc())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodPrdc())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Set getItemEstruturarevisaoIettrevs() {
		return itemEstruturarevisaoIettrevs;
	}

        /**
         *
         * @param itemEstruturarevisaoIettrevs
         */
        public void setItemEstruturarevisaoIettrevs(Set itemEstruturarevisaoIettrevs) {
		this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
	}

        /**
         *
         * @return
         */
        public Set getItemEstrtIndResulIettr() {
		return itemEstrtIndResulIettr;
	}

        /**
         *
         * @param itemEstrtIndResulIettr
         */
        public void setItemEstrtIndResulIettr(Set itemEstrtIndResulIettr) {
		this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
	}

}
