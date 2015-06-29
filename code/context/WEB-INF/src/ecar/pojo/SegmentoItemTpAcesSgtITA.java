package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoItemTpAcesSgtITA implements Serializable, PaiFilho {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8482711170016216429L;

	/** identifier field */
    private ecar.pojo.SegmentoItemTpAcesSgtITAPK comp_id;

    /** nullable persistent field */
    private ecar.pojo.SisAtributoSatb sisAtributoSatb;

    /** nullable persistent field */
    private ecar.pojo.SegmentoItemSgti segmentoItemSgti;

    /** full constructor
     * @param comp_id
     * @param sisAtributoSatb
     * @param segmentoItemSgti
     */
    public SegmentoItemTpAcesSgtITA(ecar.pojo.SegmentoItemTpAcesSgtITAPK comp_id, ecar.pojo.SisAtributoSatb sisAtributoSatb, ecar.pojo.SegmentoItemSgti segmentoItemSgti) {
        this.comp_id = comp_id;        
        this.sisAtributoSatb = sisAtributoSatb;
        this.segmentoItemSgti = segmentoItemSgti;
    }

    /** default constructor */
    public SegmentoItemTpAcesSgtITA() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public SegmentoItemTpAcesSgtITA(ecar.pojo.SegmentoItemTpAcesSgtITAPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SegmentoItemTpAcesSgtITAPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.SegmentoItemTpAcesSgtITAPK comp_id) {
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
    public ecar.pojo.SegmentoItemSgti getSegmentoItemSgti() {
        return this.segmentoItemSgti;
    }

    /**
     *
     * @param segmentoItemSgti
     */
    public void setSegmentoItemSgti(ecar.pojo.SegmentoItemSgti segmentoItemSgti) {
        this.segmentoItemSgti = segmentoItemSgti;
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
        if ( !(other instanceof SegmentoItemTpAcesSgtITA) ) return false;
        SegmentoItemTpAcesSgtITA castOther = (SegmentoItemTpAcesSgtITA) other;
        return new EqualsBuilder()
            .append(this.getSegmentoItemSgti(), castOther.getSegmentoItemSgti())
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
        comp_id = new SegmentoItemTpAcesSgtITAPK();        
        comp_id.setCodSatb(this.getSisAtributoSatb().getCodSatb());
        comp_id.setCodSgti(this.getSegmentoItemSgti().getCodSgti());
    }

}
