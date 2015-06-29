package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoCategoriaTpAcesSgt implements Serializable, PaiFilho {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5014240783980561102L;

	/** identifier field */
    private ecar.pojo.SegmentoCategoriaTpAcesSgtPK comp_id;

    /** nullable persistent field */
    private ecar.pojo.SisAtributoSatb sisAtributoSatb;

    /** nullable persistent field */
    private ecar.pojo.SegmentoCategoriaSgtc segmentoCategoriaSgtc;

    /** full constructor
     * @param comp_id
     * @param sisAtributoSatb
     * @param segmentoCategoriaSgtc
     */
    public SegmentoCategoriaTpAcesSgt(ecar.pojo.SegmentoCategoriaTpAcesSgtPK comp_id, ecar.pojo.SisAtributoSatb sisAtributoSatb, ecar.pojo.SegmentoCategoriaSgtc segmentoCategoriaSgtc) {
        this.comp_id = comp_id;        
        this.sisAtributoSatb = sisAtributoSatb;
        this.segmentoCategoriaSgtc = segmentoCategoriaSgtc;
    }

    /** default constructor */
    public SegmentoCategoriaTpAcesSgt() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public SegmentoCategoriaTpAcesSgt(ecar.pojo.SegmentoCategoriaTpAcesSgtPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SegmentoCategoriaTpAcesSgtPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.SegmentoCategoriaTpAcesSgtPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SisAtributoSatb getSisAtributoSatb() {
        return this.sisAtributoSatb;
    }

    /**
     *
     * @param sisAtributoSatb
     */
    public void setSisAtributoSatb(ecar.pojo.SisAtributoSatb sisAtributoSatb) {
        this.sisAtributoSatb = sisAtributoSatb;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SegmentoCategoriaSgtc getSegmentoCategoriaSgtc() {
        return this.segmentoCategoriaSgtc;
    }

    /**
     *
     * @param segmentoCategoriaSgtc
     */
    public void setSegmentoCategoriaSgtc(ecar.pojo.SegmentoCategoriaSgtc segmentoCategoriaSgtc) {
        this.segmentoCategoriaSgtc = segmentoCategoriaSgtc;
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
        if ( !(other instanceof SegmentoCategoriaTpAcesSgt) ) return false;
        SegmentoCategoriaTpAcesSgt castOther = (SegmentoCategoriaTpAcesSgt) other;
        return new EqualsBuilder()
            .append(this.getSegmentoCategoriaSgtc(), castOther.getSegmentoCategoriaSgtc())
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
        comp_id = new SegmentoCategoriaTpAcesSgtPK();        
        comp_id.setCodSatb(this.getSisAtributoSatb().getCodSatb());
        comp_id.setCodSgtc(this.getSegmentoCategoriaSgtc().getCodSgtc());
    }

}
