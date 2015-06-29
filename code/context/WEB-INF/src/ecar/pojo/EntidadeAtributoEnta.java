package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class EntidadeAtributoEnta extends AtributoLivre implements Serializable, PaiFilho {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5336452824597432516L;

	/** identifier field */
    private EntidadeAtributoEntaPK comp_id;

    /** nullable persistent field */
    private EntidadeEnt entidadeEnt;

    /** full constructor
     * @param comp_id
     * @param entidadeEnt
     */
    public EntidadeAtributoEnta(EntidadeAtributoEntaPK comp_id, EntidadeEnt entidadeEnt) {
        this.comp_id = comp_id;
        this.entidadeEnt = entidadeEnt;
    }

    /** default constructor */
    public EntidadeAtributoEnta() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public EntidadeAtributoEnta(ecar.pojo.EntidadeAtributoEntaPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EntidadeAtributoEntaPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.EntidadeAtributoEntaPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EntidadeEnt getEntidadeEnt() {
        return this.entidadeEnt;
    }

    /**
     *
     * @param entidadeEnt
     */
    public void setEntidadeEnt(ecar.pojo.EntidadeEnt entidadeEnt) {
        this.entidadeEnt = entidadeEnt;
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
        if ( !(other instanceof EntidadeAtributoEnta) ) return false;
        EntidadeAtributoEnta castOther = (EntidadeAtributoEnta) other;
        return new EqualsBuilder()
            .append(this.getEntidadeEnt(), castOther.getEntidadeEnt())
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
        comp_id = new EntidadeAtributoEntaPK();        
        comp_id.setCodSatb(this.getSisAtributoSatb().getCodSatb());
        comp_id.setCodEnt(this.getEntidadeEnt().getCodEnt());
    }    
    
}
