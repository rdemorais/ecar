package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PrioridadePrior implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1097069576667851486L;

	/** identifier field */
    private Long codPrior;

    /** nullable persistent field */
    private String descricaoPrior;

    /** persistent field */
    private Set regDemandaRegds;

    /** full constructor
     * @param descricaoPrior
     * @param regDemandaRegds
     */
    public PrioridadePrior(String descricaoPrior, Set regDemandaRegds) {
        this.descricaoPrior = descricaoPrior;
        this.regDemandaRegds = regDemandaRegds;
    }

    /** default constructor */
    public PrioridadePrior() {
    }

    /** minimal constructor
     * @param regDemandaRegds
     */
    public PrioridadePrior(Set regDemandaRegds) {
        this.regDemandaRegds = regDemandaRegds;
    }

    /**
     * 
     * @return
     */
    public Long getCodPrior() {
        return this.codPrior;
    }

    /**
     *
     * @param codPrior
     */
    public void setCodPrior(Long codPrior) {
        this.codPrior = codPrior;
    }

    /**
     *
     * @return
     */
    public String getDescricaoPrior() {
        return this.descricaoPrior;
    }

    /**
     *
     * @param descricaoPrior
     */
    public void setDescricaoPrior(String descricaoPrior) {
        this.descricaoPrior = descricaoPrior;
    }

    /**
     *
     * @return
     */
    public Set getRegDemandaRegds() {
        return this.regDemandaRegds;
    }

    /**
     *
     * @param regDemandaRegds
     */
    public void setRegDemandaRegds(Set regDemandaRegds) {
        this.regDemandaRegds = regDemandaRegds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codPrior", getCodPrior())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof PrioridadePrior) ) return false;
        PrioridadePrior castOther = (PrioridadePrior) other;
        return new EqualsBuilder()
            .append(this.getCodPrior(), castOther.getCodPrior())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodPrior())
            .toHashCode();
    }

}
