package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class AcompRefItemLimitesArli implements Serializable, PaiFilho {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9049427947423135111L;

	/** identifier field */
    private ecar.pojo.AcompRefItemLimitesArliPK comp_id;

    /** nullable persistent field */
    private Date dataLimiteArli;

    /** nullable persistent field */
    private ecar.pojo.AcompReferenciaItemAri acompReferenciaItemAri;

    /** nullable persistent field */
    private ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param comp_id 
     * @param dataLimiteArli
     * @param acompReferenciaItemAri 
     * @param tipoFuncAcompTpfa
     */
    public AcompRefItemLimitesArli(ecar.pojo.AcompRefItemLimitesArliPK comp_id, Date dataLimiteArli, 
    		ecar.pojo.AcompReferenciaItemAri acompReferenciaItemAri, ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa) {
        this.comp_id = comp_id;
        this.dataLimiteArli = dataLimiteArli;
        this.acompReferenciaItemAri = acompReferenciaItemAri;
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
    }

    /** default constructor */
    public AcompRefItemLimitesArli() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param comp_id
     */
    public AcompRefItemLimitesArli(ecar.pojo.AcompRefItemLimitesArliPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.AcompRefItemLimitesArliPK
     */
    public ecar.pojo.AcompRefItemLimitesArliPK getComp_id() {
        return this.comp_id;
    }

    /**
     * @param comp_id
     * @author N/C
     * @since N/C
     */
    public void setComp_id(ecar.pojo.AcompRefItemLimitesArliPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataLimiteArli() {
        return this.dataLimiteArli;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataLimiteArli
     */
    public void setDataLimiteArli(Date dataLimiteArli) {
        this.dataLimiteArli = dataLimiteArli;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.AcompReferenciaItemAri
     */
    public ecar.pojo.AcompReferenciaItemAri getAcompReferenciaItemAri() {
        return this.acompReferenciaItemAri;
    }

    /**
     * @param acompReferenciaItemAri
     * @author N/C
     * @since N/C
     */
    public void setAcompReferenciaItemAri(ecar.pojo.AcompReferenciaItemAri acompReferenciaItemAri) {
        this.acompReferenciaItemAri = acompReferenciaItemAri;
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
     * @author N/C
     * @since N/C
     * @param other
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof AcompRefItemLimitesArli) ) return false;
        AcompRefItemLimitesArli castOther = (AcompRefItemLimitesArli) other;
        return new EqualsBuilder()
            .append(this.getAcompReferenciaItemAri(), castOther.getAcompReferenciaItemAri())
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
        comp_id = new AcompRefItemLimitesArliPK();        
        comp_id.setCodAri(this.getAcompReferenciaItemAri().getCodAri());
        comp_id.setCodTpfa(this.getTipoFuncAcompTpfa().getCodTpfa());
    }

}
