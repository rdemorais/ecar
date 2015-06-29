package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 06/06/2007
 */
public class ItemEstrutVinculoIettv implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4415413986060743501L;

	/** identifier field */
    private Long codIettv;

    /** nullable persistent field */
    private Date dataInclusaoIettv;

    /** persistent field */
    private ecar.pojo.AgendaOcorrenciaAgeo agendaOcorrenciaAgeo;

    /** persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** persistent field */
    private ecar.pojo.SegmentoItemSgti segmentoItemSgti;
    
    /* -- Mantis #2156 -- */
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
     * @param dataInclusaoIettv
     * @param agendaOcorrenciaAgeo
     * @param segmentoItemSgti
     * @param itemEstruturaIett
     */
    public ItemEstrutVinculoIettv(Date dataInclusaoIettv, ecar.pojo.AgendaOcorrenciaAgeo agendaOcorrenciaAgeo, ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.SegmentoItemSgti segmentoItemSgti) {
        this.dataInclusaoIettv = dataInclusaoIettv;
        this.agendaOcorrenciaAgeo = agendaOcorrenciaAgeo;
        this.itemEstruturaIett = itemEstruturaIett;
        this.segmentoItemSgti = segmentoItemSgti;
    }

    /** default constructor */
    public ItemEstrutVinculoIettv() {
    }

    /** minimal constructor
     * @param agendaOcorrenciaAgeo
     * @param itemEstruturaIett
     * @param segmentoItemSgti
     */
    public ItemEstrutVinculoIettv(ecar.pojo.AgendaOcorrenciaAgeo agendaOcorrenciaAgeo, ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.SegmentoItemSgti segmentoItemSgti) {
        this.agendaOcorrenciaAgeo = agendaOcorrenciaAgeo;
        this.itemEstruturaIett = itemEstruturaIett;
        this.segmentoItemSgti = segmentoItemSgti;
    }

    /**
     *
     * @return
     */
    public Long getCodIettv() {
        return this.codIettv;
    }

    /**
     *
     * @param codIettv
     */
    public void setCodIettv(Long codIettv) {
        this.codIettv = codIettv;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoIettv() {
        return this.dataInclusaoIettv;
    }

    /**
     *
     * @param dataInclusaoIettv
     */
    public void setDataInclusaoIettv(Date dataInclusaoIettv) {
        this.dataInclusaoIettv = dataInclusaoIettv;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.AgendaOcorrenciaAgeo getAgendaOcorrenciaAgeo() {
        return this.agendaOcorrenciaAgeo;
    }

    /**
     *
     * @param agendaOcorrenciaAgeo
     */
    public void setAgendaOcorrenciaAgeo(ecar.pojo.AgendaOcorrenciaAgeo agendaOcorrenciaAgeo) {
        this.agendaOcorrenciaAgeo = agendaOcorrenciaAgeo;
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
            .append("codIettv", getCodIettv())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrutVinculoIettv) ) return false;
        ItemEstrutVinculoIettv castOther = (ItemEstrutVinculoIettv) other;
        return new EqualsBuilder()
            .append(this.getCodIettv(), castOther.getCodIettv())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettv())
            .toHashCode();
    }

}
