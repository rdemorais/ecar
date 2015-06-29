package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/** @author Hibernate CodeGenerator */
public class TipoFuncAcompTpfa implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4682970374690152578L;
    private Long codTpfa;
    private String labelTpfa;
    private String descricaoTpfa;
    private String indAtivarMonitoramento;
    private String indDesativarMonitoramento;
    private String indBloquearPlanejamento;
    private String indDesbloquearPlanejamento;
    private String indLerItemEstrutura;
    private String indAlterarItemEstrutura;
    private String indExcluirItemEstrutura;
    private String indVisualizarParecer;
    private String indEmitePosicaoTpfa;
    private String indAtualizaSituacaoCadastro;
    private Integer tamanhoSinalTpfa;
    private String indInformaAndamentoTpfa;
    private String labelPosicaoTpfa;
    private TipoFuncAcompTpfa tipoFuncAcompTpfa;
    private Set estrutTpFuncAcmpEtttfas;
    private Set itemEstUsutpfuacIettutfas;
    private Set situacaoTpFuncAcmpSitfas;
    private Set tipoFuncAcompTpfas;
    private Set acompRefItemLimitesArlis;
    private Set acompRefLimitesArls;
    private Set acompRelatorioArels;
	private Set itemEstrutUsuarioIettuses;
    private Set iettutfaHistorIettutfahs;
    private String indInitMonitTpfa;
    private String indNaoMonitTpfa;
    private Set tfuncacompConfigmailTfacfgm;
    private Set tipoAcompFuncAcompTafcs;
//    private Set estruturaFuncoes;
    private String documentacaoTpfa;
    
    /* Mantis #2156 */
    private Set historicoIettusHs;
    private Set historicoIettutfaHs;
    private Set tipoacompTipofuncacompSisatributoTatpfasatbs;
    
    private Set tipoFuncAcompTpfasPermiteAlterarSuperior;
    private Set tipoFuncAcompTpfasPermiteAlterarInferior;
    
    
    
    public String getIndAtualizaSituacaoCadastro() {
		return indAtualizaSituacaoCadastro;
	}

	public void setIndAtualizaSituacaoCadastro(String indAtualizaSituacaoCadastro) {
		this.indAtualizaSituacaoCadastro = indAtualizaSituacaoCadastro;
	}

	/**
     *
     * @return
     */
    public Set getTipoFuncAcompTpfasPermiteAlterarSuperior() {
		return tipoFuncAcompTpfasPermiteAlterarSuperior;
	}

    /**
     *
     * @param tipoFuncAcompTpfasPermiteAlterarSuperior
     */
    public void setTipoFuncAcompTpfasPermiteAlterarSuperior(Set tipoFuncAcompTpfasPermiteAlterarSuperior) {
		this.tipoFuncAcompTpfasPermiteAlterarSuperior = tipoFuncAcompTpfasPermiteAlterarSuperior;
	}

        /**
         *
         * @return
         */
        public Set getTipoFuncAcompTpfasPermiteAlterarInferior() {
		return tipoFuncAcompTpfasPermiteAlterarInferior;
	}

        /**
         *
         * @param tipoFuncAcompTpfasPermiteAlterarInferior
         */
        public void setTipoFuncAcompTpfasPermiteAlterarInferior(Set tipoFuncAcompTpfasPermiteAlterarInferior) {
		this.tipoFuncAcompTpfasPermiteAlterarInferior = tipoFuncAcompTpfasPermiteAlterarInferior;
	}

        /**
         *
         * @return
         */
        public String getIndVisualizarParecer() {
		return indVisualizarParecer;
	}

        /**
         *
         * @param indVisualizarParecer
         */
        public void setIndVisualizarParecer(String indVisualizarParecer) {
		this.indVisualizarParecer = indVisualizarParecer;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettutfaHs() {
		return historicoIettutfaHs;
	}

        /**
         *
         * @param historicoIettutfaHs
         */
        public void setHistoricoIettutfaHs(Set historicoIettutfaHs) {
		this.historicoIettutfaHs = historicoIettutfaHs;
	}

        /**
         *
         * @return
         */
        public Set getTfuncacompConfigmailTfacfgm() {
		return tfuncacompConfigmailTfacfgm;
	}

        /**
         *
         * @param tfuncacompConfigmailTfacfgm
         */
        public void setTfuncacompConfigmailTfacfgm(Set tfuncacompConfigmailTfacfgm) {
		this.tfuncacompConfigmailTfacfgm = tfuncacompConfigmailTfacfgm;
	}

        /** full constructor
         * @param labelTpfa
         * @param descricaoTpfa
         * @param iettutfaHistorIettutfahs
         * @param indAtivarMonitoramento
         * @param indDesativarMonitoramento
         * @param indBloquearPlanejamento
         * @param indInitMonitTpfa
         * @param tipoacompTipofuncacompSisatributoTatpfasatbs
         * @param acompRefItemLimitesArlis
         * @param indVisualizarParecer
         * @param indDesbloquearPlanejamento
         * @param indLerItemEstrutura
         * @param indEmitePosicaoTpfa
         * @param indAlterarItemEstrutura
         * @param tipoFuncAcompTpfa
         * @param documentacaoTpfa
         * @param indExcluirItemEstrutura
         * @param indInformaAndamentoTpfa
         * @param itemEstUsutpfuacIettutfas
         * @param tamanhoSinalTpfa
         * @param tipoFuncAcompTpfas
         * @param estrutTpFuncAcmpEtttfas
         * @param situacaoTpFuncAcmpSitfas
         * @param labelPosicaoTpfa
         * @param indNaoMonitTpfa
         * @param acompRefLimitesArls
         * @param acompRelatorioArels
         * @param tipoAcompFuncAcompTafcs
         */
    public TipoFuncAcompTpfa(String labelTpfa, String descricaoTpfa, 
    		String indAtivarMonitoramento, String indDesativarMonitoramento,
    		String indBloquearPlanejamento, String indDesbloquearPlanejamento,
    		String indLerItemEstrutura, String indAlterarItemEstrutura, 
    		String indExcluirItemEstrutura, String indVisualizarParecer,
    		String indEmitePosicaoTpfa, 
    		Integer tamanhoSinalTpfa, String indInformaAndamentoTpfa, 
    		String labelPosicaoTpfa, ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa, 
    		Set estrutTpFuncAcmpEtttfas, Set itemEstUsutpfuacIettutfas, 
    		Set situacaoTpFuncAcmpSitfas, Set tipoFuncAcompTpfas, Set acompRefItemLimitesArlis, 
    		Set acompRefLimitesArls, Set acompRelatorioArels, Set iettutfaHistorIettutfahs, 
    		String indInitMonitTpfa, String indNaoMonitTpfa, Set tipoAcompFuncAcompTafcs, 
    		String documentacaoTpfa,
    		Set tipoacompTipofuncacompSisatributoTatpfasatbs) {
        this.labelTpfa = labelTpfa;
        this.descricaoTpfa = descricaoTpfa;
        this.indAtivarMonitoramento = indAtivarMonitoramento;
        this.indDesativarMonitoramento = indDesativarMonitoramento;
        this.indBloquearPlanejamento = indBloquearPlanejamento;
        this.indDesbloquearPlanejamento = indDesbloquearPlanejamento;
        this.indLerItemEstrutura = indLerItemEstrutura;
        this.indAlterarItemEstrutura = indAlterarItemEstrutura;
        this.indExcluirItemEstrutura = indExcluirItemEstrutura;
        this.indVisualizarParecer = indVisualizarParecer;
        this.indEmitePosicaoTpfa = indEmitePosicaoTpfa;
        this.tamanhoSinalTpfa = tamanhoSinalTpfa;
        this.indInformaAndamentoTpfa = indInformaAndamentoTpfa;
        this.labelPosicaoTpfa = labelPosicaoTpfa;
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
        this.estrutTpFuncAcmpEtttfas = estrutTpFuncAcmpEtttfas;
        this.itemEstUsutpfuacIettutfas = itemEstUsutpfuacIettutfas;
        this.situacaoTpFuncAcmpSitfas = situacaoTpFuncAcmpSitfas;
        this.tipoFuncAcompTpfas = tipoFuncAcompTpfas;
        this.acompRefItemLimitesArlis = acompRefItemLimitesArlis;
        this.acompRefLimitesArls = acompRefLimitesArls;
        this.acompRelatorioArels = acompRelatorioArels;
        this.iettutfaHistorIettutfahs = iettutfaHistorIettutfahs;
        this.indInitMonitTpfa = indInitMonitTpfa;
        this.indNaoMonitTpfa = indNaoMonitTpfa;
        this.tipoAcompFuncAcompTafcs = tipoAcompFuncAcompTafcs;
        this.documentacaoTpfa = documentacaoTpfa;
        this.tipoacompTipofuncacompSisatributoTatpfasatbs = tipoacompTipofuncacompSisatributoTatpfasatbs;
    }

    /** default constructor */
    public TipoFuncAcompTpfa() {
    }

    /** minimal constructor with documentacaoTpfa
     * @param tipoFuncAcompTpfa
     * @param tipoAcompFuncAcompTafcs
     * @param estrutTpFuncAcmpEtttfas
     * @param itemEstUsutpfuacIettutfas
     * @param situacaoTpFuncAcmpSitfas
     * @param acompRefLimitesArls
     * @param tipoFuncAcompTpfas
     * @param iettutfaHistorIettutfahs
     * @param acompRefItemLimitesArlis
     * @param indNaoMonitTpfa
     * @param indInitMonitTpfa
     * @param documentacaoTpfa
     */
    public TipoFuncAcompTpfa(TipoFuncAcompTpfa tipoFuncAcompTpfa, Set estrutTpFuncAcmpEtttfas, Set itemEstUsutpfuacIettutfas, Set situacaoTpFuncAcmpSitfas, Set tipoFuncAcompTpfas, Set acompRefItemLimitesArlis, Set acompRefLimitesArls, Set iettutfaHistorIettutfahs, String indInitMonitTpfa, String indNaoMonitTpfa, Set tipoAcompFuncAcompTafcs, String documentacaoTpfa) {
    	this.tipoAcompFuncAcompTafcs = tipoAcompFuncAcompTafcs;
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
        this.estrutTpFuncAcmpEtttfas = estrutTpFuncAcmpEtttfas;
        this.itemEstUsutpfuacIettutfas = itemEstUsutpfuacIettutfas;
        this.situacaoTpFuncAcmpSitfas = situacaoTpFuncAcmpSitfas;
        this.tipoFuncAcompTpfas = tipoFuncAcompTpfas;
        this.acompRefItemLimitesArlis = acompRefItemLimitesArlis;
        this.acompRefLimitesArls = acompRefLimitesArls;
        this.iettutfaHistorIettutfahs = iettutfaHistorIettutfahs;      
        this.indInitMonitTpfa = indInitMonitTpfa;
        this.indNaoMonitTpfa = indNaoMonitTpfa;
        this.documentacaoTpfa = documentacaoTpfa;
    }

    /** minimal constructor
     * @param tipoFuncAcompTpfa
     * @param estrutTpFuncAcmpEtttfas
     * @param iettutfaHistorIettutfahs
     * @param situacaoTpFuncAcmpSitfas
     * @param acompRefItemLimitesArlis
     * @param itemEstUsutpfuacIettutfas
     * @param indInitMonitTpfa
     * @param tipoFuncAcompTpfas
     * @param acompRefLimitesArls
     * @param indNaoMonitTpfa
     * @param tipoAcompFuncAcompTafcs
     */
    public TipoFuncAcompTpfa(TipoFuncAcompTpfa tipoFuncAcompTpfa, Set estrutTpFuncAcmpEtttfas, Set itemEstUsutpfuacIettutfas, Set situacaoTpFuncAcmpSitfas, Set tipoFuncAcompTpfas, Set acompRefItemLimitesArlis, Set acompRefLimitesArls, Set iettutfaHistorIettutfahs, String indInitMonitTpfa, String indNaoMonitTpfa, Set tipoAcompFuncAcompTafcs) {
    	this.tipoAcompFuncAcompTafcs = tipoAcompFuncAcompTafcs;
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
        this.estrutTpFuncAcmpEtttfas = estrutTpFuncAcmpEtttfas;
        this.itemEstUsutpfuacIettutfas = itemEstUsutpfuacIettutfas;
        this.situacaoTpFuncAcmpSitfas = situacaoTpFuncAcmpSitfas;
        this.tipoFuncAcompTpfas = tipoFuncAcompTpfas;
        this.acompRefItemLimitesArlis = acompRefItemLimitesArlis;
        this.acompRefLimitesArls = acompRefLimitesArls;
        this.iettutfaHistorIettutfahs = iettutfaHistorIettutfahs;      
        this.indInitMonitTpfa = indInitMonitTpfa;
        this.indNaoMonitTpfa = indNaoMonitTpfa;
    }

    /**
     *
     * @return
     */
    public String getIndAtivarMonitoramento() {
		return indAtivarMonitoramento;
	}

    /**
     *
     * @param indAtivarMonitoramento
     */
    public void setIndAtivarMonitoramento(String indAtivarMonitoramento) {
		this.indAtivarMonitoramento = indAtivarMonitoramento;
	}

    /**
     *
     * @return
     */
    public String getIndDesativarMonitoramento() {
		return indDesativarMonitoramento;
	}

        /**
         *
         * @param indDesativarMonitoramento
         */
        public void setIndDesativarMonitoramento(String indDesativarMonitoramento) {
		this.indDesativarMonitoramento = indDesativarMonitoramento;
	}

        /**
         *
         * @return
         */
        public String getIndLerItemEstrutura() {
		return indLerItemEstrutura;
	}

        /**
         *
         * @param indLerItemEstrutura
         */
        public void setIndLerItemEstrutura(String indLerItemEstrutura) {
		this.indLerItemEstrutura = indLerItemEstrutura;
	}

        /**
         *
         * @return
         */
        public String getIndAlterarItemEstrutura() {
		return indAlterarItemEstrutura;
	}

        /**
         *
         * @param indAlterarItemEstrutura
         */
        public void setIndAlterarItemEstrutura(String indAlterarItemEstrutura) {
		this.indAlterarItemEstrutura = indAlterarItemEstrutura;
	}

        /**
         *
         * @return
         */
        public String getIndExcluirItemEstrutura() {
		return indExcluirItemEstrutura;
	}

        /**
         *
         * @param indExcluirItemEstrutura
         */
        public void setIndExcluirItemEstrutura(String indExcluirItemEstrutura) {
		this.indExcluirItemEstrutura = indExcluirItemEstrutura;
	}

        /**
         *
         * @return
         */
        public Long getCodTpfa() {
        return this.codTpfa;
    }

        /**
         *
         * @param codTpfa
         */
        public void setCodTpfa(Long codTpfa) {
        this.codTpfa = codTpfa;
    }

        /**
         *
         * @return
         */
        public String getLabelTpfa() {
        return this.labelTpfa;
    }

    /**
     *
     * @param labelTpfa
     */
    public void setLabelTpfa(String labelTpfa) {
        this.labelTpfa = labelTpfa;
    }

    /**
     *
     * @return
     */
    public String getDescricaoTpfa() {
        return this.descricaoTpfa;
    }

    /**
     *
     * @param descricaoTpfa
     */
    public void setDescricaoTpfa(String descricaoTpfa) {
        this.descricaoTpfa = descricaoTpfa;
    }

    /**
     *
     * @return
     */
    public String getIndEmitePosicaoTpfa() {
        return this.indEmitePosicaoTpfa;
    }

    /**
     *
     * @param indEmitePosicaoTpfa
     */
    public void setIndEmitePosicaoTpfa(String indEmitePosicaoTpfa) {
        this.indEmitePosicaoTpfa = indEmitePosicaoTpfa;
    }

    /**
     *
     * @return
     */
    public Integer getTamanhoSinalTpfa() {
        return this.tamanhoSinalTpfa;
    }

    /**
     *
     * @param tamanhoSinalTpfa
     */
    public void setTamanhoSinalTpfa(Integer tamanhoSinalTpfa) {
        this.tamanhoSinalTpfa = tamanhoSinalTpfa;
    }

    /**
     *
     * @return
     */
    public String getIndInformaAndamentoTpfa() {
        return this.indInformaAndamentoTpfa;
    }

    /**
     *
     * @param indInformaAndamentoTpfa
     */
    public void setIndInformaAndamentoTpfa(String indInformaAndamentoTpfa) {
        this.indInformaAndamentoTpfa = indInformaAndamentoTpfa;
    }

    /**
     *
     * @return
     */
    public String getLabelPosicaoTpfa() {
        return this.labelPosicaoTpfa;
    }

    /**
     *
     * @param labelPosicaoTpfa
     */
    public void setLabelPosicaoTpfa(String labelPosicaoTpfa) {
        this.labelPosicaoTpfa = labelPosicaoTpfa;
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
    public Set getEstrutTpFuncAcmpEtttfas() {
        return this.estrutTpFuncAcmpEtttfas;
    }

    /**
     *
     * @param estrutTpFuncAcmpEtttfas
     */
    public void setEstrutTpFuncAcmpEtttfas(Set estrutTpFuncAcmpEtttfas) {
        this.estrutTpFuncAcmpEtttfas = estrutTpFuncAcmpEtttfas;
    }

    /**
     *
     * @return
     */
    public Set getItemEstUsutpfuacIettutfas() {
        return this.itemEstUsutpfuacIettutfas;
    }

    /**
     *
     * @param itemEstUsutpfuacIettutfas
     */
    public void setItemEstUsutpfuacIettutfas(Set itemEstUsutpfuacIettutfas) {
        this.itemEstUsutpfuacIettutfas = itemEstUsutpfuacIettutfas;
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
    public Set getTipoFuncAcompTpfas() {
        return this.tipoFuncAcompTpfas;
    }

    /**
     *
     * @param tipoFuncAcompTpfas
     */
    public void setTipoFuncAcompTpfas(Set tipoFuncAcompTpfas) {
        this.tipoFuncAcompTpfas = tipoFuncAcompTpfas;
    }
 
    /**
     *
     * @return
     */
    public Set getAcompRefItemLimitesArlis() {
        return this.acompRefItemLimitesArlis;
    }

    /**
     *
     * @param acompRefItemLimitesArlis
     */
    public void setAcompRefItemLimitesArlis(Set acompRefItemLimitesArlis) {
        this.acompRefItemLimitesArlis = acompRefItemLimitesArlis;
    }

    /**
     *
     * @return
     */
    public Set getAcompRefLimitesArls() {
        return this.acompRefLimitesArls;
    }

    /**
     *
     * @param acompRefLimitesArls
     */
    public void setAcompRefLimitesArls(Set acompRefLimitesArls) {
        this.acompRefLimitesArls = acompRefLimitesArls;
    }
    
    /**
     *
     * @return
     */
    public Set getAcompRelatorioArels() {
        return acompRelatorioArels;
    }
    
    /**
     *
     * @param acompRelatorioArels
     */
    public void setAcompRelatorioArels(Set acompRelatorioArels) {
        this.acompRelatorioArels = acompRelatorioArels;
    }

    
    @Override
    public String toString() {
    	/*TODO ver com o fernando se deixa dessa forma ou da forma anterior ou ver outra solução para
    	 * compatibilidade com  a tagLib CheckListTag 
    	 **/
    	return getCodTpfa().toString();
        /* return new ToStringBuilder(this)
            .append("codTpfa", getCodTpfa())
            .toString();*/
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof TipoFuncAcompTpfa) ) return false;
        TipoFuncAcompTpfa castOther = (TipoFuncAcompTpfa) other;
        return new EqualsBuilder()
            .append(this.getCodTpfa(), castOther.getCodTpfa())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodTpfa())
            .toHashCode();
    }

	/**
	 * @return Returns the itemEstrutUsuarioIettuses.
	 */
	public Set getItemEstrutUsuarioIettuses() {
		return itemEstrutUsuarioIettuses;
	}

	/**
	 * @param itemEstrutUsuarioIettuses The itemEstrutUsuarioIettuses to set.
	 */
	public void setItemEstrutUsuarioIettuses(Set itemEstrutUsuarioIettuses) {
		this.itemEstrutUsuarioIettuses = itemEstrutUsuarioIettuses;
	}

        /**
         *
         * @return
         */
        public Set getIettutfaHistorIettutfahs() {
        return this.iettutfaHistorIettutfahs;
    }

    /**
     *
     * @param iettutfaHistorIettutfahs
     */
    public void setIettutfaHistorIettutfahs(Set iettutfaHistorIettutfahs) {
        this.iettutfaHistorIettutfahs = iettutfaHistorIettutfahs;
    }

    /**
     *
     * @return
     */
    public String getIndInitMonitTpfa() {
		return indInitMonitTpfa;
	}

    /**
     *
     * @param indInitMonitTpfa
     */
    public void setIndInitMonitTpfa(String indInitMonitTpfa) {
		this.indInitMonitTpfa = indInitMonitTpfa;
	}

        /**
         *
         * @return
         */
        public String getIndNaoMonitTpfa() {
		return indNaoMonitTpfa;
	}

        /**
         *
         * @param indnaoMonitTpfa
         */
        public void setIndNaoMonitTpfa(String indnaoMonitTpfa) {
		this.indNaoMonitTpfa = indnaoMonitTpfa;
	}

        /**
         *
         * @return
         */
        public Set getTipoAcompFuncAcompTafcs() {
		return tipoAcompFuncAcompTafcs;
	}

        /**
         *
         * @param tipoAcompFuncAcompTafcs
         */
        public void setTipoAcompFuncAcompTafcs(Set tipoAcompFuncAcompTafcs) {
		this.tipoAcompFuncAcompTafcs = tipoAcompFuncAcompTafcs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettusHs() {
		return historicoIettusHs;
	}

        /**
         *
         * @param historicoIettusHs
         */
        public void setHistoricoIettusHs(Set historicoIettusHs) {
		this.historicoIettusHs = historicoIettusHs;
	}

        /**
         *
         * @return
         */
        public String getDocumentacaoTpfa() {
		return documentacaoTpfa;
	}

        /**
         *
         * @param documentacaoTpfa
         */
        public void setDocumentacaoTpfa(String documentacaoTpfa) {
		this.documentacaoTpfa = documentacaoTpfa;
	}

        /**
         *
         * @return
         */
        public String getIndBloquearPlanejamento() {
		return indBloquearPlanejamento;
	}

        /**
         *
         * @param indBloquearPlanejamento
         */
        public void setIndBloquearPlanejamento(String indBloquearPlanejamento) {
		this.indBloquearPlanejamento = indBloquearPlanejamento;
	}

        /**
         *
         * @return
         */
        public String getIndDesbloquearPlanejamento() {
		return indDesbloquearPlanejamento;
	}

        /**
         *
         * @param indDesbloquearPlanejamento
         */
        public void setIndDesbloquearPlanejamento(String indDesbloquearPlanejamento) {
		this.indDesbloquearPlanejamento = indDesbloquearPlanejamento;
	}

//	public Set getEstruturaFuncoes() {
//		return estruturaFuncoes;
//	}
//
//	public void setEstruturaFuncoes(Set estruturaFuncoes) {
//		this.estruturaFuncoes = estruturaFuncoes;
//	}

        /**
         *
         * @return
         */
        public Set getTipoacompTipofuncacompSisatributoTatpfasatbs() {
		return tipoacompTipofuncacompSisatributoTatpfasatbs;
	}

        /**
         *
         * @param tipoacompTipofuncacompSisatributoTatpfasatbs
         */
        public void setTipoacompTipofuncacompSisatributoTatpfasatbs(
			Set tipoacompTipofuncacompSisatributoTatpfasatbs) {
		this.tipoacompTipofuncacompSisatributoTatpfasatbs = tipoacompTipofuncacompSisatributoTatpfasatbs;
	}

}
