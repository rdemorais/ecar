package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class LocAtributoLoca extends AtributoLivre implements Serializable, PaiFilho {

    /**
	 * 
	 */
	private static final long serialVersionUID = -181164579500471436L;

	/** identifier field */
    private LocAtributoLocaPK comp_id;

    /** nullable persistent field */
    private LocalItemLit localItemLit;

    /** full constructor
     * @param comp_id
     * @param localItemLit
     */
    public LocAtributoLoca(LocAtributoLocaPK comp_id, LocalItemLit localItemLit) {
        this.comp_id = comp_id;
        this.localItemLit = localItemLit;
    }

    /** default constructor */
    public LocAtributoLoca() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public LocAtributoLoca(LocAtributoLocaPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public LocAtributoLocaPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(LocAtributoLocaPK comp_id) {
        this.comp_id = comp_id;
    }
    
    /**
     *
     * @return
     */
    public LocalItemLit getLocalItemLit() {
        return this.localItemLit;
    }

    /**
     *
     * @param localItemLit
     */
    public void setLocalItemLit(LocalItemLit localItemLit) {
        this.localItemLit = localItemLit;
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
        if ( !(other instanceof LocAtributoLoca) ) return false;
        LocAtributoLoca castOther = (LocAtributoLoca) other;
        return new EqualsBuilder()
            .append(this.getLocalItemLit(), castOther.getLocalItemLit())
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
        comp_id = new LocAtributoLocaPK();        
        comp_id.setCodSatb(this.getSisAtributoSatb().getCodSatb());
        comp_id.setCodLit(this.getLocalItemLit().getCodLit());
    }    
}
