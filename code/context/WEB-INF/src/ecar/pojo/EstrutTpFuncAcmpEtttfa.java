package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import ecar.dao.ItemEstruturaDao;
import ecar.dao.ItemEstruturarevisaoIettrevDAO;
import ecar.exception.ECARException;
import ecar.pojo.historico.HistoricoItemEstruturaIett;
import ecar.pojo.historico.HistoricoPontoCriticoPtc;


/** @author Hibernate CodeGenerator */
public class EstrutTpFuncAcmpEtttfa implements Serializable, PaiFilho, ObjetoEstrutura {

    /**
	 * 
	 */
	private static final long serialVersionUID = 240358655656388605L;

	/** identifier field */
    private ecar.pojo.EstrutTpFuncAcmpEtttfaPK comp_id;

    /** nullable persistent field */
    private String indListagemImprResEtttfa;

    /** nullable persistent field */
    private String indListagemImprCompEtttfa;

    /** nullable persistent field */
    private String indListagemTelaEtttfa;

    /** nullable persistent field */
    private String indRevisao;

    /** nullable persistent field */
    private ecar.pojo.EstruturaEtt estruturaEtt;

    /** nullable persistent field */
    private ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa;
    
    private Integer seqApresentTelaCampoEtttfa;
    
    private Integer larguraListagemTelaEtttfa;
    
    private Integer seqApresListagemTelaEtttfa;

    private String idFiltroEtttfa;
    
    private String idPodeBloquearEtttfa;
    
    private String indManterProximoNivelEtttfa;
    
    /**
    * Lista das Funções de acompanhamento que tem permissão de acesso a Função de estrutura 
    * mesmo quando o item estiver bloquado para planejamento 
    */ 
    private Set<TipoFuncAcompTpfa> libTipoFuncAcompTpfas;
	
    /** full constructor
     * @param comp_id
     * @param seqApresListagemTelaEtttfa
     * @param indListagemImprResEtttfa
     * @param indManterProximoNivel
     * @param indListagemImprCompEtttfa
     * @param indListagemTelaEtttfa
     * @param tipoFuncAcompTpfa
     * @param estruturaEtt
     * @param indRevisao
     * @param larguraListagemTelaEtttfa
     * @param seqApresentTelaCampoEtttfa
     */
    public EstrutTpFuncAcmpEtttfa(ecar.pojo.EstrutTpFuncAcmpEtttfaPK comp_id, String indListagemImprResEtttfa, String indListagemImprCompEtttfa, String indListagemTelaEtttfa, ecar.pojo.EstruturaEtt estruturaEtt, ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa,
            Integer seqApresentTelaCampoEtttfa, Integer larguraListagemTelaEtttfa, Integer seqApresListagemTelaEtttfa, String indRevisao, String indManterProximoNivel) {
        this.comp_id = comp_id;
        this.indListagemImprResEtttfa = indListagemImprResEtttfa;
        this.indListagemImprCompEtttfa = indListagemImprCompEtttfa;
        this.indListagemTelaEtttfa = indListagemTelaEtttfa;
        this.estruturaEtt = estruturaEtt;
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
        this.indRevisao = indRevisao;
        this.seqApresentTelaCampoEtttfa = seqApresentTelaCampoEtttfa;
        this.larguraListagemTelaEtttfa = larguraListagemTelaEtttfa;
        this.seqApresListagemTelaEtttfa = seqApresListagemTelaEtttfa;
        this.indManterProximoNivelEtttfa = indManterProximoNivel;
    }

    /** default constructor */
    public EstrutTpFuncAcmpEtttfa() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public EstrutTpFuncAcmpEtttfa(ecar.pojo.EstrutTpFuncAcmpEtttfaPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EstrutTpFuncAcmpEtttfaPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.EstrutTpFuncAcmpEtttfaPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public String getIndListagemImprResEtttfa() {
        return this.indListagemImprResEtttfa;
    }

    /**
     *
     * @param indListagemImprResEtttfa
     */
    public void setIndListagemImprResEtttfa(String indListagemImprResEtttfa) {
        this.indListagemImprResEtttfa = indListagemImprResEtttfa;
    }

    /**
     *
     * @return
     */
    public String getIndListagemImprCompEtttfa() {
        return this.indListagemImprCompEtttfa;
    }

    /**
     *
     * @param indListagemImprCompEtttfa
     */
    public void setIndListagemImprCompEtttfa(String indListagemImprCompEtttfa) {
        this.indListagemImprCompEtttfa = indListagemImprCompEtttfa;
    }

    /**
     *
     * @return
     */
    public String getIndListagemTelaEtttfa() {
        return this.indListagemTelaEtttfa;
    }

    /**
     *
     * @param indListagemTelaEtttfa
     */
    public void setIndListagemTelaEtttfa(String indListagemTelaEtttfa) {
        this.indListagemTelaEtttfa = indListagemTelaEtttfa;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EstrutTpFuncAcmpEtttfa) ) return false;
        EstrutTpFuncAcmpEtttfa castOther = (EstrutTpFuncAcmpEtttfa) other;
        return new EqualsBuilder()
            .append(this.getEstruturaEtt(), castOther.getEstruturaEtt())
            .append(this.getTipoFuncAcompTpfa(), castOther.getTipoFuncAcompTpfa())
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
        comp_id = new EstrutTpFuncAcmpEtttfaPK();        
        comp_id.setCodEtt(this.getEstruturaEtt().getCodEtt());
        comp_id.setCodTpfa(this.getTipoFuncAcompTpfa().getCodTpfa());
    }


    /**
     * @return Returns the larguraListagemTelaEtttfa.
     */
    public Integer getLarguraListagemTelaEtttfa() {
        return larguraListagemTelaEtttfa;
    }
    /**
     * @param larguraListagemTelaEtttfa The larguraListagemTelaEtttfa to set.
     */
    public void setLarguraListagemTelaEtttfa(Integer larguraListagemTelaEtttfa) {
        this.larguraListagemTelaEtttfa = larguraListagemTelaEtttfa;
    }
    /**
     * @return Returns the seqApresentTelaCampoEtttfa.
     */
    public Integer getSeqApresentTelaCampoEtttfa() {
        return seqApresentTelaCampoEtttfa;
    }
    /**
     * @param seqApresentTelaCampoEtttfa The seqApresentTelaCampoEtttfa to set.
     */
    public void setSeqApresentTelaCampoEtttfa(Integer seqApresentTelaCampoEtttfa) {
        this.seqApresentTelaCampoEtttfa = seqApresentTelaCampoEtttfa;
    }
    /**
     * @return Returns the seqApresListagemTelaEtttfa.
     */
    public Integer getSeqApresListagemTelaEtttfa() {
        return seqApresListagemTelaEtttfa;
    }
    /**
     * @param seqApresListagemTelaEtttfa The seqApresListagemTelaEtttfa to set.
     */
    public void setSeqApresListagemTelaEtttfa(Integer seqApresListagemTelaEtttfa) {
        this.seqApresListagemTelaEtttfa = seqApresListagemTelaEtttfa;
    }
    
    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#getNome()
     */
    public String iGetNome() {
        return "Fun" + this.getTipoFuncAcompTpfa().getCodTpfa();
    }
    
    public String iGetNomeOrdenarLista() {
        return "";
    }
    

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#getLabel()
     */
    public String iGetLabel() {
        return this.getTipoFuncAcompTpfa().getLabelTpfa();
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#getLargura()
     */
    public Integer iGetLargura() {
        return (this.getLarguraListagemTelaEtttfa() == null) ?
        		Integer.valueOf(ObjetoEstrutura.DEFAULT_LARGURA_TELA_CAMPO) : this.getLarguraListagemTelaEtttfa();
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#getSequenciaListagem()
     */
    public Integer iGetSequenciaColunaEmListagem() {
        return (this.getSeqApresListagemTelaEtttfa() == null) ? 
        		Integer.valueOf(ObjetoEstrutura.MAX_SEQUENCIA_COLUNA_LISTAGEM) : this.getSeqApresListagemTelaEtttfa();
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetValor(ecar.pojo.ItemEstruturaIett)
     */
    public String iGetValor(ItemEstruturaIett item) throws ECARException {
        UsuarioUsu usuarioFuncao = (UsuarioUsu) new ItemEstruturaDao(null).
        	getValorFunAcompItemEstrutura(item, this.getTipoFuncAcompTpfa());

        SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) new ItemEstruturaDao(null).
    	getValorSatbFunAcompItemEstrutura(item, this.getTipoFuncAcompTpfa());
        
        String resultado = "";
        if (usuarioFuncao != null){
        	resultado = usuarioFuncao.getNomeUsuSent();
        } else if (sisAtributoSatb != null) {
        	resultado = sisAtributoSatb.getDescricaoSatb();
        }
        	       
        return resultado;
    }
    
    /**
     *
     * @param pontoCriticoPtc
     * @return
     * @throws ECARException
     */
    public String iGetValor(PontoCriticoPtc pontoCriticoPtc) throws ECARException{
    	return "";
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetSequenciaCampoEmTela()
     */
    public Integer iGetSequenciaCampoEmTela() {
        return (this.getSeqApresentTelaCampoEtttfa() == null) ? 
        		Integer.valueOf(ObjetoEstrutura.MAX_SEQUENCIA_TELA_CAMPO) : this.getSeqApresentTelaCampoEtttfa();
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
        return new Boolean(false);
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetCodigoFk(ecar.pojo.ItemEstruturaIett)
     */
    public String iGetValorCodFk(ItemEstruturaIett item) throws ECARException {
        UsuarioUsu usuarioFuncao = (UsuarioUsu) new ItemEstruturaDao(null).
        	getValorFunAcompItemEstrutura(item, this.getTipoFuncAcompTpfa());
        
        SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) new ItemEstruturaDao(null).
    		getValorSatbFunAcompItemEstrutura(item, this.getTipoFuncAcompTpfa());
        
        String resultado = "";
        if (usuarioFuncao != null){
        	resultado = "U" + usuarioFuncao.getCodUsu().toString();
        } else if (sisAtributoSatb != null) {
        	resultado = "G" + sisAtributoSatb.getCodSatb().toString();
        }
        	       
        return resultado;
    }
    
    /**
     *
     * @param pontoCriticoPtc
     * @return
     * @throws ECARException
     */
    public String iGetValorCodFk(PontoCriticoPtc pontoCriticoPtc) throws ECARException {
    	return "";
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetCodFk()
     */
    public String iGetCodFk() {
        return "";
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#iGetNomeFk()
     */
    public String iGetNomeFk() {
        return "";
    }

	public String iGetValor(ItemEstruturarevisaoIettrev itemRev) throws ECARException {
        UsuarioUsu usuarioFuncao = (UsuarioUsu) new ItemEstruturarevisaoIettrevDAO(null).
    	getValorFunAcompItemEstrutura(itemRev, this.getTipoFuncAcompTpfa());

    return (usuarioFuncao != null) ? usuarioFuncao.getNomeUsuSent() : "";

	}

	public String iGetValorCodFk(ItemEstruturarevisaoIettrev itemRev) throws ECARException {
        UsuarioUsu usuarioFuncao = (UsuarioUsu) new ItemEstruturarevisaoIettrevDAO(null).
    	getValorFunAcompItemEstrutura(itemRev, this.getTipoFuncAcompTpfa());
        return (usuarioFuncao != null) ? usuarioFuncao.getCodUsu().toString() : "";
	}
	
	/* (non-Javadoc)
     * @see ecar.pojo.ObjetoEstrutura#getDica()
     */
    public String iGetDica() {
    	return "";
    }

    /**
     *
     * @return
     */
    public String getIndRevisao() {
		return indRevisao;
	}

    /**
     *
     * @param indRevisao
     */
    public void setIndRevisao(String indRevisao) {
		this.indRevisao = indRevisao;
	}

        /**
         *
         * @return
         */
        public Boolean iGetIndOpcional() {
		// TODO Auto-generated method stub
		return null;
	}

	public SisGrupoAtributoSga iGetGrupoAtributosLivres() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer iGetTamanhoConteudoAtrib() {
		// TODO Auto-generated method stub
		return null;
	}

        /**
         *
         * @return
         */
        public String getIdFiltroEtttfa() {
		return idFiltroEtttfa;
	}

        /**
         *
         * @param idFiltroEtttfa
         */
        public void setIdFiltroEtttfa(String idFiltroEtttfa) {
		this.idFiltroEtttfa = idFiltroEtttfa;
	}

        /**
         *
         * @return
         */
        public String getIdPodeBloquearEtttfa() {
		return idPodeBloquearEtttfa;
	}

        /**
         *
         * @param idPodeBloquearEtttfa
         */
        public void setIdPodeBloquearEtttfa(String idPodeBloquearEtttfa) {
		this.idPodeBloquearEtttfa = idPodeBloquearEtttfa;
	}

	public Boolean iGetBloqueado() {
        return ("S".equals(this.getIdPodeBloquearEtttfa()) ? new Boolean(true) : new Boolean(false));
	}

        /**
         *
         * @return
         */
        public Set iGetLibTipoFuncAcompTpfas() {
		return libTipoFuncAcompTpfas;
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
        public void setLibTipoFuncAcompTpfas(Set<TipoFuncAcompTpfa> libTipoFuncAcompTpfas) {
		this.libTipoFuncAcompTpfas = libTipoFuncAcompTpfas;
	}

        /**
         *
         * @return
         */
        public String getIndManterProximoNivelEtttfa() {
		return indManterProximoNivelEtttfa;
	}

        /**
         *
         * @param indManterProximoNivelEtttfa
         */
        public void setIndManterProximoNivelEtttfa(String indManterProximoNivelEtttfa) {
		this.indManterProximoNivelEtttfa = indManterProximoNivelEtttfa;
	}

        /**
         *
         * @param historicoPontoCriticoPtc
         * @return
         * @throws ECARException
         */
        public String iGetValor(HistoricoPontoCriticoPtc historicoPontoCriticoPtc) throws ECARException {
	    // TODO Auto-generated method stub
	    return null;
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
        public String iGetValor(
			HistoricoItemEstruturaIett historicoItemEstruturaIett)
			throws ECARException {
		UsuarioUsu usuarioFuncao = (UsuarioUsu) new ItemEstruturaDao(null).getValorFunAcompItemEstrutura(historicoItemEstruturaIett, this.getTipoFuncAcompTpfa());
	
	    SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) new ItemEstruturaDao(null).getValorSatbFunAcompItemEstrutura(historicoItemEstruturaIett, this.getTipoFuncAcompTpfa());
	    
	    String resultado = "";
	    if (usuarioFuncao != null){
	    	resultado = usuarioFuncao.getNomeUsuSent();
	    } else if (sisAtributoSatb != null) {
	    	resultado = sisAtributoSatb.getDescricaoSatb();
	    }
	    return resultado;
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
			} else if (object instanceof HistoricoItemEstruturaIett){
				return this.iGetValor((HistoricoItemEstruturaIett) object);
			}
		}
		return "";
	}

        /**
         *
         * @param historicoItemEstruturaIett
         * @return
         * @throws ECARException
         */
        public String iGetValorCodFk(
			HistoricoItemEstruturaIett historicoItemEstruturaIett)
			throws ECARException {
		
		UsuarioUsu usuarioFuncao = (UsuarioUsu) new ItemEstruturaDao(null).
    	getValorFunAcompItemEstrutura(historicoItemEstruturaIett, this.getTipoFuncAcompTpfa());
    
	    SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) new ItemEstruturaDao(null).
			getValorSatbFunAcompItemEstrutura(historicoItemEstruturaIett, this.getTipoFuncAcompTpfa());
	    
	    String resultado = "";
	    if (usuarioFuncao != null){
	    	resultado = "U" + usuarioFuncao.getCodUsu().toString();
	    } else if (sisAtributoSatb != null) {
	    	resultado = "G" + sisAtributoSatb.getCodSatb().toString();
	    }
	    	       
	    return resultado;
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
	