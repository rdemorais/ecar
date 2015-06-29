package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class AcompRefLimitesArl implements Serializable, PaiFilho {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2817826749619625362L;

	/** identifier field */
    private ecar.pojo.AcompRefLimitesArlPK comp_id;

    /** nullable persistent field */
    private Date dataLimiteArl;

    /** nullable persistent field */
    private ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa;

    /** nullable persistent field */
    private ecar.pojo.AcompReferenciaAref acompReferenciaAref;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param comp_id
     * @param dataLimiteArl 
     * @param tipoFuncAcompTpfa
     * @param acompReferenciaAref
     */
    public AcompRefLimitesArl(ecar.pojo.AcompRefLimitesArlPK comp_id, Date dataLimiteArl, 
    		ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa, ecar.pojo.AcompReferenciaAref acompReferenciaAref) {
        this.comp_id = comp_id;
        this.dataLimiteArl = dataLimiteArl;
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
        this.acompReferenciaAref = acompReferenciaAref;
    }

    /** default constructor */
    public AcompRefLimitesArl() {
    }

    /** minimal constructor.<br>
     * 
     * @param comp_id
     * @author N/C
     * @since N/C
     */
    public AcompRefLimitesArl(ecar.pojo.AcompRefLimitesArlPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.AcompRefLimitesArlPK
     */
    public ecar.pojo.AcompRefLimitesArlPK getComp_id() {
        return this.comp_id;
    }

    /**
     * @param comp_id
     * @author N/C
     * @since N/C
     */
    public void setComp_id(ecar.pojo.AcompRefLimitesArlPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataLimiteArl() {
        return this.dataLimiteArl;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataLimiteArl
     */
    public void setDataLimiteArl(Date dataLimiteArl) {
        this.dataLimiteArl = dataLimiteArl;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.TipoFuncAcompTpfa
     */
    public ecar.pojo.TipoFuncAcompTpfa getTipoFuncAcompTpfa() {
        return this.tipoFuncAcompTpfa;
    }

    /**
     * @param tipoFuncAcompTpfa
     * @author N/C
     * @since N/C
     */
    public void setTipoFuncAcompTpfa(ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa) {
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.AcompReferenciaAref
     */
    public ecar.pojo.AcompReferenciaAref getAcompReferenciaAref() {
        return this.acompReferenciaAref;
    }

    /**
     * @param acompReferenciaAref
     * @author N/C
     * @since N/C
     */
    public void setAcompReferenciaAref(ecar.pojo.AcompReferenciaAref acompReferenciaAref) {
        this.acompReferenciaAref = acompReferenciaAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }


    /** 
     * O trecho de código abaixo foi alterado, para que na comparação EqualsBuilder()
     * não seja comparado o comp_id = null, pois antes de salvar não existe o comp_id.<br>
     * 
     * @param other
     * @author N/C
     * @since N/C
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof AcompRefLimitesArl) ) return false;
        AcompRefLimitesArl castOther = (AcompRefLimitesArl) other;
        return new EqualsBuilder()
            .append(this.getAcompReferenciaAref(), castOther.getAcompReferenciaAref())
			.append(this.getTipoFuncAcompTpfa(), castOther.getTipoFuncAcompTpfa())
			.isEquals();
    }

    /**
     * @author N/C
     * @since N/C
     * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }
    
    /**
     * @author N/C
     * @since N/C
     */
    public void atribuirPKPai() {
        comp_id = new AcompRefLimitesArlPK();        
        comp_id.setCodAref(this.getAcompReferenciaAref().getCodAref());
        comp_id.setCodTpfa(this.getTipoFuncAcompTpfa().getCodTpfa());
    }
}
