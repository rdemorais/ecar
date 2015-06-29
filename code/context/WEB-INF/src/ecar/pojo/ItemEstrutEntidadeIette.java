package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 06/06/2007
 */
public class ItemEstrutEntidadeIette implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1793332812752103080L;

	/** identifier field */
    private ecar.pojo.ItemEstrutEntidadeIettePK comp_id;

    /** nullable persistent field */
    private Date dataUltManutencaoIette;

    /** nullable persistent field */
    private Date dataFimIette;

    /** nullable persistent field */
    private Date dataInicioIette;

    /** nullable persistent field */
    private String descricaoIette;

    /** nullable persistent field */
    private ecar.pojo.EntidadeEnt entidadeEnt;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** nullable persistent field */
    private ecar.pojo.TipoParticipacaoTpp tipoParticipacaoTpp;

    /* -- Mantis #2156 -- */
    private Boolean indExclusaoPosHistorico;
    
    private UsuarioUsu usuarioUsuManutencao;
    
    /** full constructor
     * @param comp_id
     * @param dataUltManutencaoIette
     * @param dataFimIette
     * @param entidadeEnt
     * @param dataInicioIette
     * @param descricaoIette
     * @param itemEstruturaIett
     * @param tipoParticipacaoTpp
     */
    public ItemEstrutEntidadeIette(ecar.pojo.ItemEstrutEntidadeIettePK comp_id, Date dataUltManutencaoIette, Date dataFimIette, Date dataInicioIette, String descricaoIette, ecar.pojo.EntidadeEnt entidadeEnt, ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.TipoParticipacaoTpp tipoParticipacaoTpp) {
        this.comp_id = comp_id;
        this.dataUltManutencaoIette = dataUltManutencaoIette;
        this.dataFimIette = dataFimIette;
        this.dataInicioIette = dataInicioIette;
        this.descricaoIette = descricaoIette;
        this.entidadeEnt = entidadeEnt;
        this.itemEstruturaIett = itemEstruturaIett;
        this.tipoParticipacaoTpp = tipoParticipacaoTpp;
    }

    /** default constructor */
    public ItemEstrutEntidadeIette() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public ItemEstrutEntidadeIette(ecar.pojo.ItemEstrutEntidadeIettePK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstrutEntidadeIettePK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.ItemEstrutEntidadeIettePK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public Date getDataUltManutencaoIette() {
        return this.dataUltManutencaoIette;
    }

    /**
     *
     * @param dataUltManutencaoIette
     */
    public void setDataUltManutencaoIette(Date dataUltManutencaoIette) {
        this.dataUltManutencaoIette = dataUltManutencaoIette;
    }

    /**
     *
     * @return
     */
    public Date getDataFimIette() {
        return this.dataFimIette;
    }

    /**
     *
     * @param dataFimIette
     */
    public void setDataFimIette(Date dataFimIette) {
        this.dataFimIette = dataFimIette;
    }

    /**
     *
     * @return
     */
    public Date getDataInicioIette() {
        return this.dataInicioIette;
    }

    /**
     *
     * @param dataInicioIette
     */
    public void setDataInicioIette(Date dataInicioIette) {
        this.dataInicioIette = dataInicioIette;
    }

    /**
     *
     * @return
     */
    public String getDescricaoIette() {
        return this.descricaoIette;
    }

    /**
     *
     * @param descricaoIette
     */
    public void setDescricaoIette(String descricaoIette) {
        this.descricaoIette = descricaoIette;
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
    public ecar.pojo.TipoParticipacaoTpp getTipoParticipacaoTpp() {
        return this.tipoParticipacaoTpp;
    }

    /**
     *
     * @param tipoParticipacaoTpp
     */
    public void setTipoParticipacaoTpp(ecar.pojo.TipoParticipacaoTpp tipoParticipacaoTpp) {
        this.tipoParticipacaoTpp = tipoParticipacaoTpp;
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
        if ( !(other instanceof ItemEstrutEntidadeIette) ) return false;
        ItemEstrutEntidadeIette castOther = (ItemEstrutEntidadeIette) other;
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

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsuManutencao() {
		return usuarioUsuManutencao;
	}

        /**
         *
         * @param usuarioUsuManutencao
         */
        public void setUsuarioUsuManutencao(UsuarioUsu usuarioUsuManutencao) {
		this.usuarioUsuManutencao = usuarioUsuManutencao;
	}

        /**
         *
         */
        public void atribuirPK() {
		comp_id = new ItemEstrutEntidadeIettePK();
		comp_id.setCodIett(this.getItemEstruturaIett().getCodIett());
		comp_id.setCodEnt(this.getEntidadeEnt().getCodEnt());
		comp_id.setCodTpp(this.getTipoParticipacaoTpp().getCodTpp());
	}

}
