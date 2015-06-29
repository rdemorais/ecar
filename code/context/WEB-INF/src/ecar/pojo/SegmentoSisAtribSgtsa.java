package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoSisAtribSgtsa implements Serializable, PaiFilho {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8796818044530081247L;

	/** identifier field */
    private ecar.pojo.SegmentoSisAtribSgtsaPK comp_id;
    
    /** nullable persistent field */
    private ecar.pojo.SegmentoItemSgti segmentoItemSgti;

    /** nullable persistent field */
    private ecar.pojo.SisAtributoSatb sisAtributoSatb;

    /** full constructor
     * @param comp_id
     * @param segmentoItemSgti
     * @param sisAtributoSatb
     */
    public SegmentoSisAtribSgtsa(ecar.pojo.SegmentoSisAtribSgtsaPK comp_id, ecar.pojo.SegmentoItemSgti segmentoItemSgti, ecar.pojo.SisAtributoSatb sisAtributoSatb) {
        this.comp_id = comp_id;
        this.segmentoItemSgti = segmentoItemSgti;
        this.sisAtributoSatb = sisAtributoSatb;
    }

    /** default constructor */
    public SegmentoSisAtribSgtsa() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public SegmentoSisAtribSgtsa(ecar.pojo.SegmentoSisAtribSgtsaPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SegmentoSisAtribSgtsaPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.SegmentoSisAtribSgtsaPK comp_id) {
        this.comp_id = comp_id;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SegmentoSisAtribSgtsa) ) return false;
        SegmentoSisAtribSgtsa castOther = (SegmentoSisAtribSgtsa) other;
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
        comp_id = new SegmentoSisAtribSgtsaPK();        
        comp_id.setCodSgti(this.getSegmentoItemSgti().getCodSgti());
        comp_id.setCodSatb(this.getSisAtributoSatb().getCodSatb());
    }

}
