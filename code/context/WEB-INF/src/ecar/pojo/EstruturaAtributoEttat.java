package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import ecar.dao.ItemEstruturaDao;
import ecar.dao.ItemEstruturarevisaoIettrevDAO;
import ecar.dao.PontoCriticoDao;
import ecar.exception.ECARException;
import ecar.pojo.historico.HistoricoItemEstruturaIett;
import ecar.pojo.historico.HistoricoPontoCriticoPtc;
import ecar.sinalizacao.Sinalizacao;


/** @author Hibernate CodeGenerator */
public class EstruturaAtributoEttat implements  Cloneable, Serializable, PaiFilho, ObjetoEstrutura {




	/**
	 * 
	 */
	private static final long serialVersionUID = 4183170413821064029L;

	/** identifier field */
    private ecar.pojo.EstruturaAtributoEttatPK comp_id;

    /** nullable persistent field */
    private String indObrigatorioEttat;

    /** 
     * @author egger - SERPRO
     * nullable persistent field */
    private String indPodeBloquearEttat;
    
    /**
    * Lista das Fun��es de acompanhamento que tem permiss�o de acesso a Fun��o de estrutura 
    * mesmo quando esta est� bloqueada. 
    */ 
    private Set<TipoFuncAcompTpfa> libTipoFuncAcompTpfas;

    /** 
     * @author Fernando Peron - SERPRO
     * nullable persistent field */
    private String indFiltroEttat;
    
    /** nullable persistent field */
    private String indListagemImpressCompEtta;

    /** nullable persistent field */
    private Integer seqApresListagemTelaEttat;

    /** nullable persistent field */
    private Integer larguraListagemTelaEttat;

    /** nullable persistent field */
    private String indListagemTelaEttat;

    /** nullable persistent field */
    private Integer seqApresentTelaCampoEttat;

    /** nullable persistent field */
    private String labelEstruturaEttat;

    /** nullable persistent field */
    private String indListagemImpressaResEtta;
    
    /** nullable persistent field */
    private String indRevisaoEttat;

    /** nullable persistent field */
    private ecar.pojo.EstruturaEtt estruturaEtt;

    /** nullable persistent field */
    private ecar.pojo.AtributosAtb atributosAtb;
    
    /** nullable persistent field */
    private String indRelacaoImpressaEttat;
    
    /** nullable persistent field */
    
    /** nullable persistent field */
    private String dicaEttat;
    
    /** nullable persistent field */
    private Integer tamanhoConteudoAtribEttat;
    
    private Set estAtribTipoAcompEatas; 
    
    private String documentacaoEttat;
    
    /** nullable persistent field */
    private String indListagemArvoreEttat;
    
    /** full constructor
     * @param estAtribTipoAcompEatas
     * @param documentacaoEttat
     * @param indObrigatorioEttat
     * @param comp_id
     * @param indListagemImpressCompEtta
     * @param seqApresListagemTelaEttat
     * @param indRelacaoImpressaEttat
     * @param larguraListagemTelaEttat
     * @param dicaEttat
     * @param indListagemTelaEttat
     * @param seqApresentTelaCampoEttat
     * @param atributosAtb
     * @param estruturaEtt
     * @param indListagemImpressaResEtta
     * @param indRevisaoEttat
     * @param labelEstruturaEttat
     * @param tamanhoConteudoAtribEttat
     */
    public EstruturaAtributoEttat(Set estAtribTipoAcompEatas, ecar.pojo.EstruturaAtributoEttatPK comp_id, String indObrigatorioEttat, String indListagemImpressCompEtta, Integer seqApresListagemTelaEttat, Integer larguraListagemTelaEttat, String indListagemTelaEttat, Integer seqApresentTelaCampoEttat, String labelEstruturaEttat, String indListagemImpressaResEtta, ecar.pojo.EstruturaEtt estruturaEtt, ecar.pojo.AtributosAtb atributosAtb, String indRelacaoImpressaEttat, String indRevisaoEttat, String dicaEttat, Integer tamanhoConteudoAtribEttat, String documentacaoEttat) {
        this.indObrigatorioEttat = indObrigatorioEttat;
        this.indListagemImpressCompEtta = indListagemImpressCompEtta;
        this.seqApresListagemTelaEttat = seqApresListagemTelaEttat;
        this.larguraListagemTelaEttat = larguraListagemTelaEttat;
        this.indListagemTelaEttat = indListagemTelaEttat;
        this.seqApresentTelaCampoEttat = seqApresentTelaCampoEttat;
        this.labelEstruturaEttat = labelEstruturaEttat;
        this.indListagemImpressaResEtta = indListagemImpressaResEtta;
        this.estruturaEtt = estruturaEtt;
        this.atributosAtb = atributosAtb;
        this.indRelacaoImpressaEttat = indRelacaoImpressaEttat;
        this.indRevisaoEttat = indRevisaoEttat;
        this.dicaEttat = dicaEttat;
        this.tamanhoConteudoAtribEttat = tamanhoConteudoAtribEttat;
        this.estAtribTipoAcompEatas = estAtribTipoAcompEatas;
        this.documentacaoEttat = documentacaoEttat;
    }

    /** default constructor */
    public EstruturaAtributoEttat() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public EstruturaAtributoEttat(ecar.pojo.EstruturaAtributoEttatPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EstruturaAtributoEttatPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.EstruturaAtributoEttatPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public String getIndObrigatorioEttat() {
        return this.indObrigatorioEttat;
    }

    /**
     *
     * @param indObrigatorioEttat
     */
    public void setIndObrigatorioEttat(String indObrigatorioEttat) {
        this.indObrigatorioEttat = indObrigatorioEttat;
    }

    /**
     *
     * @return
     */
    public String getIndListagemImpressCompEtta() {
        return this.indListagemImpressCompEtta;
    }

    /**
     *
     * @param indListagemImpressCompEtta
     */
    public void setIndListagemImpressCompEtta(String indListagemImpressCompEtta) {
        this.indListagemImpressCompEtta = indListagemImpressCompEtta;
    }

    /**
     *
     * @return
     */
    public Integer getSeqApresListagemTelaEttat() {
        return this.seqApresListagemTelaEttat;
    }

    /**
     *
     * @param seqApresListagemTelaEttat
     */
    public void setSeqApresListagemTelaEttat(Integer seqApresListagemTelaEttat) {
        this.seqApresListagemTelaEttat = seqApresListagemTelaEttat;
    }

    /**
     *
     * @return
     */
    public Integer getLarguraListagemTelaEttat() {
        return this.larguraListagemTelaEttat;
    }

    /**
     *
     * @param larguraListagemTelaEttat
     */
    public void setLarguraListagemTelaEttat(Integer larguraListagemTelaEttat) {
        this.larguraListagemTelaEttat = larguraListagemTelaEttat;
    }

    /**
     *
     * @return
     */
    public String getIndListagemTelaEttat() {
        return this.indListagemTelaEttat;
    }

    /**
     *
     * @param indListagemTelaEttat
     */
    public void setIndListagemTelaEttat(String indListagemTelaEttat) {
        this.indListagemTelaEttat = indListagemTelaEttat;
    }

    /**
     *
     * @return
     */
    public Integer getSeqApresentTelaCampoEttat() {
        return this.seqApresentTelaCampoEttat;
    }

    /**
     *
     * @param seqApresentTelaCampoEttat
     */
    public void setSeqApresentTelaCampoEttat(Integer seqApresentTelaCampoEttat) {
        this.seqApresentTelaCampoEttat = seqApresentTelaCampoEttat;
    }

    /**
     *
     * @return
     */
    public String getLabelEstruturaEttat() {
        return this.labelEstruturaEttat;
    }

    /**
     *
     * @param labelEstruturaEttat
     */
    public void setLabelEstruturaEttat(String labelEstruturaEttat) {
        this.labelEstruturaEttat = labelEstruturaEttat;
    }

    /**
     *
     * @return
     */
    public String getIndListagemImpressaResEtta() {
        return this.indListagemImpressaResEtta;
    }

    /**
     *
     * @param indListagemImpressaResEtta
     */
    public void setIndListagemImpressaResEtta(String indListagemImpressaResEtta) {
        this.indListagemImpressaResEtta = indListagemImpressaResEtta;
    }

    /**
     *
     * @return
     */
    public Integer getTamanhoConteudoAtribEttat() {
		return tamanhoConteudoAtribEttat;
	}

    /**
     *
     * @param tamanhoConteudoAtribEttat
     */
    public void setTamanhoConteudoAtribEttat(Integer tamanhoConteudoAtribEttat) {
		this.tamanhoConteudoAtribEttat = tamanhoConteudoAtribEttat;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.EstruturaEtt getEstruturaEtt() {
        return this.estruturaEtt;
    }

        /**
         *
         * @param estruturaEtt
         */
        public void setEstruturaEtt(ecar.pojo.EstruturaEtt estruturaEtt) {
        this.estruturaEtt = estruturaEtt;
    }

        /**
         *
         * @return
         */
        public ecar.pojo.AtributosAtb getAtributosAtb() {
        return this.atributosAtb;
    }

    /**
     *
     * @param atributosAtb
     */
    public void setAtributosAtb(ecar.pojo.AtributosAtb atributosAtb) {
        this.atributosAtb = atributosAtb;
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
        if ( !(other instanceof EstruturaAtributoEttat) ) return false;
        EstruturaAtributoEttat castOther = (EstruturaAtributoEttat) other;
        return new EqualsBuilder()            
            .append(this.getEstruturaEtt(), castOther.getEstruturaEtt())
            .append(this.getAtributosAtb(), castOther.getAtributosAtb())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

    /* (non-Javadoc)
     * @see ecar.pojo.PaiFilho#atribuirPKPai()
     */
    /**
     *
     */
    public void atribuirPKPai() {
        comp_id = new EstruturaAtributoEttatPK();        
        comp_id.setCodEtt(this.getEstruturaEtt().getCodEtt());
        comp_id.setCodAtb(this.getAtributosAtb().getCodAtb());
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#getNome()
     */
    public String iGetNome() {
        return this.getAtributosAtb().getNomeAtb();
    }
    
    public String iGetNomeOrdenarLista() {
        /* se for um objeto fk retorna o nome do objeto + o nome da campo a ordenar
         * Ex. codOrg.nomeOrg
         */
        if (this.getAtributosAtb().getSisGrupoAtributoSga() == null && this.getAtributosAtb().getNomeFkAtb() != null && !"".equals(this.getAtributosAtb().getNomeFkAtb().trim()))
            return (this.getAtributosAtb().getNomeAtb() + "." + this.getAtributosAtb().getNomeFkAtb());
        else if (this.getAtributosAtb().getSisGrupoAtributoSga() == null){
        	return this.getAtributosAtb().getNomeAtb();
        } else {
        	 //A ordena��o n�o � levada em considera��o quando o atributo definido para ser o primeiro da lista � um atributo livre
            return "";
        }
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#getLabel()
     */
    public String iGetLabel() {
        return (this.getLabelEstruturaEttat() != null) ? 
                this.getLabelEstruturaEttat() : this.getAtributosAtb().getLabelPadraoAtb();
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#getLargura()
     */
    public Integer iGetLargura() {
        return (this.getLarguraListagemTelaEttat() == null) ?
        		Integer.valueOf(ObjetoEstrutura.DEFAULT_LARGURA_TELA_CAMPO) : this.getLarguraListagemTelaEttat();
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#getSequenciaListagem()
     */
    public Integer iGetSequenciaColunaEmListagem() {
        return (this.getSeqApresListagemTelaEttat() == null) ? 
        		Integer.valueOf(ObjetoEstrutura.MAX_SEQUENCIA_COLUNA_LISTAGEM) : this.getSeqApresListagemTelaEttat();
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#getValue(ecar.pojo.ItemEstruturaIett)
     */
    public String iGetValor(ItemEstruturaIett item) throws ECARException {
        return new ItemEstruturaDao(null).getValorAtributoItemEstrutura(item, 
                this.getAtributosAtb().getNomeAtb(), this.getAtributosAtb().getNomeFkAtb());
    }
    
    /*
     * (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetValor(ecar.pojo.PontoCriticoPtc)
     */
    /**
     *
     * @param pontoCriticoPtc
     * @return
     * @throws ECARException
     */
    public String iGetValor(PontoCriticoPtc pontoCriticoPtc) throws ECARException{
    	return new PontoCriticoDao(null).getValorAtributoPontoCritico(pontoCriticoPtc,this.getAtributosAtb().getNomeAtb(), this.getAtributosAtb().getNomeFkAtb());
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetSequenciaCampoEmTela()
     */
    public Integer iGetSequenciaCampoEmTela() {
        return (this.getSeqApresentTelaCampoEttat() == null) ? 
        		Integer.valueOf(ObjetoEstrutura.MAX_SEQUENCIA_TELA_CAMPO) : this.getSeqApresentTelaCampoEttat();
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetTipo()
     */
    public Class iGetTipo() {
        return this.getClass();
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetObrigatorio()
     */
    public Boolean iGetObrigatorio() {
        return ("S".equals(this.getIndObrigatorioEttat()) ? new Boolean(true) : new Boolean(false));
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetCodigoFk(ecar.pojo.ItemEstruturaIett)
     */
    public String iGetValorCodFk(ItemEstruturaIett item) throws ECARException {
        return new ItemEstruturaDao(null).getValorAtributoItemEstrutura(item, 
                this.getAtributosAtb().getNomeAtb(), this.getAtributosAtb().getCodFkAtb());
    }
    
    /*(non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetCodigoFk(ecar.pojo.PontoCriticoPtc)
     */
    /**
     *
     * @param pontoCriticoPtc
     * @return
     * @throws ECARException
     */
    public String iGetValorCodFk(PontoCriticoPtc pontoCriticoPtc) throws ECARException {
    	return new PontoCriticoDao(null).getValorAtributoPontoCritico(pontoCriticoPtc, 
                this.getAtributosAtb().getNomeAtb(), this.getAtributosAtb().getCodFkAtb());
    }

    
    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetCodFk()
     */
    public String iGetCodFk() {
        return this.getAtributosAtb().getCodFkAtb();
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetNomeFk()
     */
    public String iGetNomeFk() {
        return this.getAtributosAtb().getNomeFkAtb();
    }
    
    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#getDica()
     */
    public String iGetDica() {
    	return this.getDicaEttat();
    }

    /**
     *
     * @return
     */
    public String getIndRelacaoImpressaEttat() {
        return this.indRelacaoImpressaEttat;
    }

    /**
     *
     * @param indRelacaoImpressaEttat
     */
    public void setIndRelacaoImpressaEttat(String indRelacaoImpressaEttat) {
        this.indRelacaoImpressaEttat = indRelacaoImpressaEttat;
    }

    /**
     *
     * @return
     */
    public String getIndRevisaoEttat() {
		return indRevisaoEttat;
	}

    /**
     *
     * @param indRevisaoEttat
     */
    public void setIndRevisaoEttat(String indRevisaoEttat) {
		this.indRevisaoEttat = indRevisaoEttat;
	}

	public String iGetValor(ItemEstruturarevisaoIettrev itemRev) throws ECARException {
        return new ItemEstruturarevisaoIettrevDAO(null).getValorAtributoItemEstrutura(itemRev, 
                this.getAtributosAtb().getNomeAtb(), this.getAtributosAtb().getNomeFkAtb());
	}

	public String iGetValorCodFk(ItemEstruturarevisaoIettrev itemRev) throws ECARException {
        return new ItemEstruturarevisaoIettrevDAO(null).getValorAtributoItemEstrutura(itemRev, 
                this.getAtributosAtb().getNomeAtb(), this.getAtributosAtb().getCodFkAtb());
	}

        /**
         *
         * @return
         */
        public String getDicaEttat() {
		return dicaEttat;
	}

        /**
         *
         * @param dicaEttat
         */
        public void setDicaEttat(String dicaEttat) {
		this.dicaEttat = dicaEttat;
	}

        /**
         *
         * @return
         */
        public Boolean iGetIndOpcional() {
		if(this.getAtributosAtb() != null){
			if("S".equals(this.getAtributosAtb().getIndOpcionalAtb()))
				return new Boolean(true);
		}
		return new Boolean(false);
	}

        /**
         *
         * @return
         */
        public Set getEstAtribTipoAcompEatas() {
		return estAtribTipoAcompEatas;
	}

        /**
         *
         * @param estAtribTipoAcompEatas
         */
        public void setEstAtribTipoAcompEatas(Set estAtribTipoAcompEatas) {
		this.estAtribTipoAcompEatas = estAtribTipoAcompEatas;
	}

	public SisGrupoAtributoSga iGetGrupoAtributosLivres() {
		if(this.getAtributosAtb() != null)
			return this.getAtributosAtb().getSisGrupoAtributoSga();
		else
			return null;
	}

	public Integer iGetTamanhoConteudoAtrib() {
		if (tamanhoConteudoAtribEttat != null && tamanhoConteudoAtribEttat != 0) {
			return tamanhoConteudoAtribEttat;
		} else {
			return DEFAULT_TAMANHO_CAMPO_TEXT;
		}
	}

        /**
         *
         * @return
         */
        public String getIndPodeBloquearEttat() {
		return indPodeBloquearEttat;
	}

        /**
         *
         * @param indPodeBloquearEttat
         */
        public void setIndPodeBloquearEttat(String indPodeBloquearEttat) {
		this.indPodeBloquearEttat = indPodeBloquearEttat;
	}

        /**
         *
         * @return
         */
        public String getIndFiltroEttat() {
		return indFiltroEttat;
	}

        /**
         *
         * @param indFiltroEttat
         */
        public void setIndFiltroEttat(String indFiltroEttat) {
		this.indFiltroEttat = indFiltroEttat;
	}

	public Boolean iGetBloqueado() {
		return ("S".equals(this.getIndPodeBloquearEttat()) ? new Boolean(true) : new Boolean(false));
	}

        /**
         *
         * @return
         */
        public String getDocumentacaoEttat() {
		return documentacaoEttat;
	}

        /**
         *
         * @param documentacaoEttat
         */
        public void setDocumentacaoEttat(String documentacaoEttat) {
		this.documentacaoEttat = documentacaoEttat;
	}

        /**
         *
         * @return
         */
        public Set<TipoFuncAcompTpfa> getLibTipoFuncAcompTpfas() {
		return libTipoFuncAcompTpfas;
	}

        /**
         *
         * @param libTipoFuncAcompTpfas
         */
        public void setLibTipoFuncAcompTpfas(
			Set<TipoFuncAcompTpfa> libTipoFuncAcompTpfas) {
		this.libTipoFuncAcompTpfas = libTipoFuncAcompTpfas;
	}

	
        /**
         *
         * @return
         */
        public Set iGetLibTipoFuncAcompTpfas() {
		return libTipoFuncAcompTpfas ;
	} 

        /**
         *
         * @return
         */
        public String getIndListagemArvoreEttat() {
		return indListagemArvoreEttat;
	}

        /**
         *
         * @param indListagemArvoreEttat
         */
        public void setIndListagemArvoreEttat(String indListagemArvoreEttat) {
		this.indListagemArvoreEttat = indListagemArvoreEttat;
	}

        /**
         *
         * @param historicoPontoCriticoPtc
         * @return
         * @throws ECARException
         */
        public String iGetValor(HistoricoPontoCriticoPtc historicoPontoCriticoPtc) throws ECARException {
		return new PontoCriticoDao(null).getValorAtributoPontoCritico(historicoPontoCriticoPtc, 
                this.getAtributosAtb().getNomeAtb(), this.getAtributosAtb().getNomeFkAtb());
    }

        /**
         *
         * @param historicoPontoCriticoPtc
         * @return
         * @throws ECARException
         */
        public String iGetValorCodFk(HistoricoPontoCriticoPtc historicoPontoCriticoPtc) throws ECARException {
	    // TODO Auto-generated method stub
	    return null;
    }
	
        /**
         *
         * @param historicoItemEstruturaIett
         * @return
         * @throws ECARException
         */
        public String iGetValor(HistoricoItemEstruturaIett historicoItemEstruturaIett) throws ECARException {
		return new ItemEstruturaDao(null).getValorAtributoItemEstrutura(historicoItemEstruturaIett, 
                this.getAtributosAtb().getNomeAtb(), this.getAtributosAtb().getNomeFkAtb());
    }

        /**
         *
         * @param historicoItemEstruturaIett
         * @return
         * @throws ECARException
         */
        public String iGetValorCodFk(HistoricoItemEstruturaIett historicoItemEstruturaIett) throws ECARException {
		return new ItemEstruturaDao(null).getValorAtributoItemEstrutura(historicoItemEstruturaIett, 
                this.getAtributosAtb().getNomeAtb(), this.getAtributosAtb().getCodFkAtb());
    } 

        /**
         *
         * @param object
         * @return
         * @throws ECARException
         */
        public String iGetValor(Object object) throws ECARException {
			if (object != null){
				if (object instanceof ItemEstruturaIett) {
					return this.iGetValor((ItemEstruturaIett) object);	
				} else if (object instanceof PontoCriticoPtc){
					return this.iGetValor((PontoCriticoPtc) object);
				} else if (object instanceof HistoricoItemEstruturaIett){
					return this.iGetValor((HistoricoItemEstruturaIett) object);
				}
			}
			return "";
		}
        

        /**
        *
        * @param object
        * @return
        * @throws ECARException
        */
       public String iGetValor(IConfiguracaoAtributoLivre object) throws ECARException {
			if (object != null){
				if (object instanceof ItemEstruturaIett) {
					return this.iGetValor((ItemEstruturaIett) object);	
				} else if (object instanceof PontoCriticoPtc){
					return this.iGetValor((PontoCriticoPtc) object);
				} 
			}
			return "";
		}

	
        /**
         *
         * @param object
         * @return
         * @throws ECARException
         */
        public String iGetValorCodFk(Object object) throws ECARException {
		if (object != null){
			if (object instanceof ItemEstruturaIett){
				return this.iGetValorCodFk((ItemEstruturaIett) object);
			} else if (object instanceof HistoricoItemEstruturaIett){
				return this.iGetValorCodFk((HistoricoItemEstruturaIett) object);
			}
		}
		
		return "";
	}

}
