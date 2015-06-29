package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 06/06/2007
 */
public class ItemEstUsutpfuacIettutfa implements Serializable, PaiFilho {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1033156885539177414L;

	/** identifier field */
    private ecar.pojo.ItemEstUsutpfuacIettutfaPK comp_id;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** nullable persistent field */
    private ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa;

    /** nullable persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;
    
    /** nullable persistent field */
    private ecar.pojo.SisAtributoSatb sisAtributoSatb;
    
    /* -- Mantis #2156 -- */
    private Boolean indExclusaoPosHistorico;
    private Date dataUltManutencao;
    private UsuarioUsu usuManutencao;
    

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
     * @return
     */
    public UsuarioUsu getUsuManutencao() {
		return usuManutencao;
	}

        /**
         *
         * @param usuManutencao
         */
        public void setUsuManutencao(UsuarioUsu usuManutencao) {
		this.usuManutencao = usuManutencao;
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

        /** full constructor
         * @param comp_id
         * @param itemEstruturaIett
         * @param tipoFuncAcompTpfa
         * @param usuarioUsu
         * @param sisAtributoSatb
         */
    public ItemEstUsutpfuacIettutfa(ecar.pojo.ItemEstUsutpfuacIettutfaPK comp_id, ecar.pojo.ItemEstruturaIett itemEstruturaIett, 
    								ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa, 
    								ecar.pojo.UsuarioUsu usuarioUsu,
    								SisAtributoSatb sisAtributoSatb) {
        this.comp_id = comp_id;
        this.itemEstruturaIett = itemEstruturaIett;
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
        this.usuarioUsu = usuarioUsu;
        this.sisAtributoSatb = sisAtributoSatb;
    }

    /** default constructor */
    public ItemEstUsutpfuacIettutfa() {
    }

    /** minimal constructor
     * @param comp_id
     * @param acompRelatorioArels
     */
    public ItemEstUsutpfuacIettutfa(ecar.pojo.ItemEstUsutpfuacIettutfaPK comp_id, Set acompRelatorioArels) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstUsutpfuacIettutfaPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.ItemEstUsutpfuacIettutfaPK comp_id) {
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
    public ecar.pojo.TipoFuncAcompTpfa getTipoFuncAcompTpfa() {
        return this.tipoFuncAcompTpfa;
    }

    /**
     *
     * @param tipoFuncAcompTpfa
     */
    public void setTipoFuncAcompTpfa(ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa) {
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }

    /**
     *
     * @param usuarioUsu
     */
    public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
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
        if ( !(other instanceof ItemEstUsutpfuacIettutfa) ) return false;
        ItemEstUsutpfuacIettutfa castOther = (ItemEstUsutpfuacIettutfa) other;
        return new EqualsBuilder()
        	.append(this.getTipoFuncAcompTpfa(), castOther.getTipoFuncAcompTpfa())
            .append(this.getItemEstruturaIett(), castOther.getItemEstruturaIett())
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
        comp_id = new ItemEstUsutpfuacIettutfaPK();        
        comp_id.setCodIett(this.getItemEstruturaIett().getCodIett());
        comp_id.setCodTpfa(this.getTipoFuncAcompTpfa().getCodTpfa());
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SisAtributoSatb getSisAtributoSatb() {
		return sisAtributoSatb;
	}

        /**
         *
         * @param sisAtributoSatb
         */
        public void setSisAtributoSatb(ecar.pojo.SisAtributoSatb sisAtributoSatb) {
		this.sisAtributoSatb = sisAtributoSatb;
	}

}
