package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 04/06/2007 
 */
public class ItemEstrutCriterioIettc implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3393596693074946026L;

	/** identifier field */
    private ecar.pojo.ItemEstrutCriterioIettcPK comp_id;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** nullable persistent field */
    private ecar.pojo.CriterioCri criterioCri;
    
    /* -- Mantis #2156 -- */
    private Date dataUltManutencao;
    private UsuarioUsu usuManutencao;
    private Boolean indExclusaoPosHistorico;

    /**
     *
     * @return
     */
    public Boolean getIndExclusaoPosHistorico() {
		return indExclusaoPosHistorico;
	}

    /**
     *
     * @param indExclusaoPosHistorico
     */
    public void setIndExclusaoPosHistorico(Boolean indExclusaoPosHistorico) {
		this.indExclusaoPosHistorico = indExclusaoPosHistorico;
	}

        /** full constructor
         * @param comp_id
         * @param itemEstruturaIett
         * @param criterioCri
         */
    public ItemEstrutCriterioIettc(ItemEstrutCriterioIettcPK comp_id, ItemEstruturaIett itemEstruturaIett, 
    		ecar.pojo.CriterioCri criterioCri) {
        this.comp_id = comp_id;
        this.itemEstruturaIett = itemEstruturaIett;
        this.criterioCri = criterioCri;
    }

    /** default constructor */
    public ItemEstrutCriterioIettc() {
    }

    /**
     *
     * @return
     */
    public UsuarioUsu getUsuManutencao() {
		return usuManutencao;
	}

    /**
     *
     * @param usuManutencao
     */
    public void setUsuManutencao(ecar.pojo.UsuarioUsu usuManutencao) {
		this.usuManutencao = usuManutencao;
	}

    /** minimal constructor
     * @param comp_id
     */
    public ItemEstrutCriterioIettc(ecar.pojo.ItemEstrutCriterioIettcPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstrutCriterioIettcPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.ItemEstrutCriterioIettcPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturaIett getItemEstruturaIett() {
        return this.itemEstruturaIett;
    }

    /**
     *
     * @param itemEstruturaIett
     */
    public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
        this.itemEstruturaIett = itemEstruturaIett;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.CriterioCri getCriterioCri() {
        return this.criterioCri;
    }

    /**
     *
     * @param criterioCri
     */
    public void setCriterioCri(ecar.pojo.CriterioCri criterioCri) {
        this.criterioCri = criterioCri;
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
        if ( !(other instanceof ItemEstrutCriterioIettc) ) return false;
        ItemEstrutCriterioIettc castOther = (ItemEstrutCriterioIettc) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
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
     * @return
     */
    public Date getDataUltManutencao() {
		return dataUltManutencao;
	}

        /**
         *
         * @param dataUltManutencao
         */
        public void setDataUltManutencao(Date dataUltManutencao) {
		this.dataUltManutencao = dataUltManutencao;
	}
	
        /**
         *
         */
        public void atribuirPK(){
		comp_id = new ItemEstrutCriterioIettcPK();
		comp_id.setCodIett(this.getItemEstruturaIett().getCodIett());
		comp_id.setCodCri(this.getCriterioCri().getCodCri());
	}

}
