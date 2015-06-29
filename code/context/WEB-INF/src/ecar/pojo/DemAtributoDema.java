package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class DemAtributoDema extends AtributoLivre implements Serializable, PaiFilho {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3480446183539535314L;

	/** identifier field */
    private DemAtributoDemaPK comp_id;

    /** nullable persistent field */
    private RegDemandaRegd regDemandaRegd;

    /** full constructor
     * @param comp_id
     * @param regDemandaRegd
     */
    public DemAtributoDema(DemAtributoDemaPK comp_id, RegDemandaRegd regDemandaRegd) {
        this.comp_id = comp_id;
        this.regDemandaRegd = regDemandaRegd;
    }

    /**
     *
     */
    public DemAtributoDema() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public DemAtributoDema(ecar.pojo.DemAtributoDemaPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.DemAtributoDemaPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.DemAtributoDemaPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.RegDemandaRegd getRegDemandaRegd() {
        return this.regDemandaRegd;
    }

    /**
     *
     * @param regDemandaRegd
     */
    public void setRegDemandaRegd(ecar.pojo.RegDemandaRegd regDemandaRegd) {
        this.regDemandaRegd = regDemandaRegd;
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
        if ( !(other instanceof DemAtributoDema) ) return false;
        DemAtributoDema castOther = (DemAtributoDema) other;
        return new EqualsBuilder()
	        .append(this.getRegDemandaRegd(), castOther.getRegDemandaRegd())
	        .append(this.getSisAtributoSatb(), castOther.getSisAtributoSatb())
	        .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }
    
    /**
     *
     */
    public void atribuirPKPai() {
        comp_id = new DemAtributoDemaPK();        
        comp_id.setCodSatb(this.getSisAtributoSatb().getCodSatb());
        comp_id.setCodRegd(this.getRegDemandaRegd().getCodRegd());
    } 
}
