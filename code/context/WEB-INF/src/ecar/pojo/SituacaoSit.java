package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.3, 02/06/2007
 */
public class SituacaoSit implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8179687342215119212L;

	/** identifier field */
    private Long codSit;

    /** nullable persistent field */
    private String indComentarioSit;

    /** nullable persistent field */
    private String indConcluidoSit;

    /** nullable persistent field */
    private String comentarioSit;

    /** nullable persistent field */
    private String descricaoSit;
        
    /** persistent field */
    private Set situacaoTpFuncAcmpSitfas;

    /** persistent field */
    private Set acompRelatorioArels;

    /** persistent field */
    private Set estruturaSituacaoEtts;

    /** persistent field */
    private Set acompRealFisicoArfs;

    /** persistent field */
    private Set itemEstruturaIetts;
    
    /** nullable persistent field */
    private String indMetaFisicaSit;
    
    /* Mantis #10717 */
    private String indSemAcompanhamentoSit;
    
    /* Mantis #2156 */
    private Set historicoIettHs;
    
    private Set perfilIntercambioDadosPflidsNaoInformado;
    
    private Set perfilIntercambioDadosPflidsSemCorrespondente;
    

        
    /**
     *
     * @return
     */
    public Set getHistoricoIettHs() {
		return historicoIettHs;
	}

    /**
     *
     * @param historicoIettHs
     */
    public void setHistoricoIettHs(Set historicoIettHs) {
		this.historicoIettHs = historicoIettHs;
	}

        /** full constructor
         * @param indComentarioSit
         * @param acompRelatorioArels
         * @param comentarioSit
         * @param indConcluidoSit
         * @param situacaoTpFuncAcmpSitfas
         * @param descricaoSit
         * @param estruturaSituacaoEtts
         * @param itemEstruturarevisaoIettrevs
         * @param itemEstruturaIetts
         * @param acompRealFisicoArfs
         */
    public SituacaoSit(String indComentarioSit, String indConcluidoSit, String comentarioSit, String descricaoSit, Set situacaoTpFuncAcmpSitfas, Set acompRelatorioArels, Set estruturaSituacaoEtts, Set acompRealFisicoArfs, Set itemEstruturaIetts, Set itemEstruturarevisaoIettrevs, Set perfilIntercambioDadosPflidsNaoInformado, Set perfilIntercambioDadosPflidsSemCorrespondente) {
        this.indComentarioSit = indComentarioSit;
        this.indConcluidoSit = indConcluidoSit;
        this.comentarioSit = comentarioSit;
        this.descricaoSit = descricaoSit;
        this.situacaoTpFuncAcmpSitfas = situacaoTpFuncAcmpSitfas;
        this.acompRelatorioArels = acompRelatorioArels;
        this.estruturaSituacaoEtts = estruturaSituacaoEtts;
        this.acompRealFisicoArfs = acompRealFisicoArfs;
        this.itemEstruturaIetts = itemEstruturaIetts;
        this.perfilIntercambioDadosPflidsNaoInformado = perfilIntercambioDadosPflidsNaoInformado;
        this.perfilIntercambioDadosPflidsSemCorrespondente = perfilIntercambioDadosPflidsSemCorrespondente;
        
        //this.indMetaFisicaSit = indMetaFisicaSit;
//        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
    }

    /** default constructor */
    public SituacaoSit() {
    }

    /** minimal constructor
     * @param situacaoTpFuncAcmpSitfas
     * @param acompRelatorioArels
     * @param itemEstruturarevisaoIettrevs
     * @param estruturaSituacaoEtts
     * @param itemEstruturaIetts
     * @param acompRealFisicoArfs
     */
    public SituacaoSit(Set situacaoTpFuncAcmpSitfas, Set acompRelatorioArels, Set estruturaSituacaoEtts, Set acompRealFisicoArfs, Set itemEstruturaIetts, Set itemEstruturarevisaoIettrevs) {
        this.situacaoTpFuncAcmpSitfas = situacaoTpFuncAcmpSitfas;
        this.acompRelatorioArels = acompRelatorioArels;
        this.estruturaSituacaoEtts = estruturaSituacaoEtts;
        this.acompRealFisicoArfs = acompRealFisicoArfs;
        this.itemEstruturaIetts = itemEstruturaIetts;
//        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
    }

    /**
     *
     * @return
     */
    public Long getCodSit() {
        return this.codSit;
    }

    /**
     *
     * @param codSit
     */
    public void setCodSit(Long codSit) {
        this.codSit = codSit;
    }

    /**
     *
     * @return
     */
    public String getIndComentarioSit() {
        return this.indComentarioSit;
    }

    /**
     *
     * @param indComentarioSit
     */
    public void setIndComentarioSit(String indComentarioSit) {
        this.indComentarioSit = indComentarioSit;
    }

    /**
     *
     * @return
     */
    public String getIndConcluidoSit() {
        return this.indConcluidoSit;
    }

    /**
     *
     * @param indConcluidoSit
     */
    public void setIndConcluidoSit(String indConcluidoSit) {
        this.indConcluidoSit = indConcluidoSit;
    }

    /**
     *
     * @return
     */
    public String getComentarioSit() {
        return this.comentarioSit;
    }

    /**
     *
     * @param comentarioSit
     */
    public void setComentarioSit(String comentarioSit) {
        this.comentarioSit = comentarioSit;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSit() {
        return this.descricaoSit;
    }

    /**
     *
     * @param descricaoSit
     */
    public void setDescricaoSit(String descricaoSit) {
        this.descricaoSit = descricaoSit;
    }

    /**
     *
     * @return
     */
    public Set getSituacaoTpFuncAcmpSitfas() {
        return this.situacaoTpFuncAcmpSitfas;
    }

    /**
     *
     * @param situacaoTpFuncAcmpSitfas
     */
    public void setSituacaoTpFuncAcmpSitfas(Set situacaoTpFuncAcmpSitfas) {
        this.situacaoTpFuncAcmpSitfas = situacaoTpFuncAcmpSitfas;
    }

    /**
     *
     * @return
     */
    public Set getAcompRelatorioArels() {
        return this.acompRelatorioArels;
    }

    /**
     *
     * @param acompRelatorioArels
     */
    public void setAcompRelatorioArels(Set acompRelatorioArels) {
        this.acompRelatorioArels = acompRelatorioArels;
    }

    /**
     *
     * @return
     */
    public Set getEstruturaSituacaoEtts() {
        return this.estruturaSituacaoEtts;
    }

    /**
     *
     * @param estruturaSituacaoEtts
     */
    public void setEstruturaSituacaoEtts(Set estruturaSituacaoEtts) {
        this.estruturaSituacaoEtts = estruturaSituacaoEtts;
    }

    /**
     *
     * @return
     */
    public Set getAcompRealFisicoArfs() {
        return this.acompRealFisicoArfs;
    }

    /**
     *
     * @param acompRealFisicoArfs
     */
    public void setAcompRealFisicoArfs(Set acompRealFisicoArfs) {
        this.acompRealFisicoArfs = acompRealFisicoArfs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSit", getCodSit())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SituacaoSit) ) return false;
        SituacaoSit castOther = (SituacaoSit) other;
        return new EqualsBuilder()
            .append(this.getCodSit(), castOther.getCodSit())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSit())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Set getItemEstruturaIetts() {
		return itemEstruturaIetts;
	}

        /**
         *
         * @param itemEstruturaIetts
         */
        public void setItemEstruturaIetts(Set itemEstruturaIetts) {
		this.itemEstruturaIetts = itemEstruturaIetts;
	}
	
        /**
         *
         * @return
         */
        public String getIndMetaFisicaSit() {
        return this.indMetaFisicaSit;
    }

        /**
         *
         * @param indMetaFisicaSit
         */
        public void setIndMetaFisicaSit(String indMetaFisicaSit) {
        this.indMetaFisicaSit = indMetaFisicaSit;
    }
    
        /**
         *
         * @return
         */
        public String getIndSemAcompanhamentoSit() {
		return indSemAcompanhamentoSit;
	}

        /**
         *
         * @param indSemAcompanhamentoSit
         */
        public void setIndSemAcompanhamentoSit(String indSemAcompanhamentoSit) {
		this.indSemAcompanhamentoSit = indSemAcompanhamentoSit;
	}

    /**
     * 
     * @return
     */
	public Set getPerfilIntercambioDadosPflidsNaoInformado() {
		return perfilIntercambioDadosPflidsNaoInformado;
	}
	/**
	 * 
	 * @param perfilIntercambioDadosPflidsNaoInformado
	 */
	public void setPerfilIntercambioDadosPflidsNaoInformado(
			Set perfilIntercambioDadosPflidsNaoInformado) {
		this.perfilIntercambioDadosPflidsNaoInformado = perfilIntercambioDadosPflidsNaoInformado;
	}
	/**
	 * 
	 * @return
	 */
	public Set getPerfilIntercambioDadosPflidsSemCorrespondente() {
		return perfilIntercambioDadosPflidsSemCorrespondente;
	}
	/**
	 * 
	 * @param perfilIntercambioDadosPflidsSemCorrespondente
	 */
	public void setPerfilIntercambioDadosPflidsSemCorrespondente(
			Set perfilIntercambioDadosPflidsSemCorrespondente) {
		this.perfilIntercambioDadosPflidsSemCorrespondente = perfilIntercambioDadosPflidsSemCorrespondente;
	}

}
