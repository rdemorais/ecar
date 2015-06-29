package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import ecar.util.Dominios;

/**
 * Classe que representa a estrutura do sistema, por exemplo:
 * 
 * Objetivo Estratégico
 * --> 	Estratégia da Secretaria
 * -----> Produto
 *  
 * Aqui, os atributos a serem usados são configurados.
 *  
 * @author Hibernate CodeGenerator 
 * */
public class EstruturaEtt implements Serializable,Hierarchyable,Comparable<EstruturaEtt> {

	private static final long serialVersionUID = 8960587232704518158L;

	/** identifier field */
    private Long codEtt;

    /** nullable persistent field */
    private String siglaEtt;

    /** nullable persistent field */
    private String nomeEtt;
    
    /** nullable persistent field */
    private String labelEtt;

    /** nullable persistent field */
    private String indAtivoEtt;

    /** nullable persistent field */
    private Date dataInclusaoEtt;

    private Long tamanhoListagemVerticalEtt;
    
    private String indExibirImprimirListagem;
    
    private String indExibirOpcaoModelo;
    


	/** nullable persistent field */
    private String indPrevFinanceiraEtt;

    /** nullable persistent field */
    private Date dataUltManutEtt;

    /** persistent field */
    private ecar.pojo.EstruturaEtt estruturaEtt;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;

    /** persistent field */
    private Set estruturaAtributoEttats;

    /** persistent field */
    private Set estrutTpFuncAcmpEtttfas;

    /** persistent field */
    private Set estruturaAcessoEttas;

    /** persistent field */
    private Set estruturaSituacaoEtts;

    /** persistent field */
    private Set estruturaFuncaoEttfs;
    
    /**
     * Utilizado para armazenar lista de estrutura de funcão escolhidas
     */
    private Set estruturaFuncaoEttfsEscolhidas;

    /** persistent field */
    private Set itemEstruturaIetts;

    /** persistent field */
    private Set estruturaEtts;

    /** persistent field */
    private Integer seqApresentacaoEtt;

    /** persistent field */
    private String codCor1Ett;

    /** persistent field */
    private String codCor2Ett;

    /** persistent field */
    private String codCor3Ett;

    /** persistent field */
    private String codCor4Ett;
    
    private String indExibirEstruturaEtt;
    
    private Long codAtbExibirEstruturaEtt;

    /** persistent field */
    private AtributosAtb atributoAtbExibirEstruturaEtt;
    
    private Set<SisAtributoSatb> sisAtributoSatbEttSuperior;
    
    /** nullable persistent field */
    private String indEtapaNivelSuperiorEtt;

    private Set tipoAcompanhamentoTas;
    
    private Set itemEstruturarevisaoIettrevs;
    
    private Set estruturaNivelGeracaoTas;
    
    /* Mantis #2156 */
    private Set historicoIettHs;
    
    /** persistent field */
    private Set itemEstruturaIettPPAs;
    
    private Boolean virtual;
    
    private Set itensEstruturaVirtual;
    
    /** nullable persistent field */
    private String indExibirGerarArquivos;
    
    private Set perfilIntercambioDadosCadastroPidcsBaseImp;

    private Set perfilIntercambioDadosCadastroPidcsCriacaoItemImp;
    
    private Set perfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp;

    private transient long ordemInsercao;
    
    @AvaliaMetodo(valida=false)
    public long getOrdemInsercao() {
		return ordemInsercao;
	}

	public void setOrdemInsercao(long ordemInsercao) {
		this.ordemInsercao = ordemInsercao;
	}

	/**
     *
     * @return
     */
    public Set getItensEstruturaVirtual() {
		return itensEstruturaVirtual;
	}

        /**
         *
         * @param itensEstruturaVirtual
         */
        public void setItensEstruturaVirtual(Set itensEstruturaVirtual) {
		this.itensEstruturaVirtual = itensEstruturaVirtual;
	}

        /**
         *
         * @return
         */
        public Boolean getVirtual() {
		return virtual;
	}

        /**
         *
         * @param virtual
         */
        public void setVirtual(Boolean virtual) {
		this.virtual = virtual;
	}

        /**
         *
         * @return
         */
        public boolean isVirtual() {
		
		boolean ret;
		
		if (virtual == null){
			ret = false;
		} else {
			ret = virtual.booleanValue();
		}
		
		return ret;
	}
	
        /**
         *
         * @return
         */
        public Set getItemEstruturaIettPPAs() {
		return itemEstruturaIettPPAs;
	}

        /**
         *
         * @param itemEstruturaIettPPAs
         */
        public void setItemEstruturaIettPPAs(Set itemEstruturaIettPPAs) {
		this.itemEstruturaIettPPAs = itemEstruturaIettPPAs;
	}

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

        /**
         *
         * @return
         */
        public Set getItemEstruturarevisaoIettrevs() {
		return itemEstruturarevisaoIettrevs;
	}

        /**
         *
         * @param itemEstruturarevisaoIettrevs
         */
        public void setItemEstruturarevisaoIettrevs(Set itemEstruturarevisaoIettrevs) {
		this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
	}
    
    
        /** full constructor
         * @param tipoAcompanhamentoTas
         * @param indExibirImprimirListagem
         * @param usuarioUsu
         * @param tamanhoListagemVerticalEtt
         * @param siglaEtt
         * @param labelEtt
         * @param nomeEtt
         * @param codCor3Ett
         * @param codCor4Ett
         * @param dataInclusaoEtt
         * @param indAtivoEtt
         * @param indPrevFinanceiraEtt
         * @param itemEstruturarevisaoIettrevs
         * @param indEtapaNivelSuperiorEtt
         * @param dataUltManutEtt
         * @param estrutTpFuncAcmpEtttfas
         * @param estruturaAcessoEttas
         * @param estruturaEtt
         * @param codCor2Ett
         * @param estruturaSituacaoEtts
         * @param estruturaAtributoEttats
         * @param estruturaFuncaoEttfs
         * @param codCor1Ett
         * @param seqApresentacaoEtt
         * @param estruturaEtts
         * @param itemEstruturaIetts
         * @param indExibirGerarArquivos
         */
    public EstruturaEtt(Set tipoAcompanhamentoTas,String indExibirImprimirListagem, String indExibirOpcaoModelo, Long tamanhoListagemVerticalEtt, String siglaEtt, String nomeEtt, String labelEtt, String indAtivoEtt, Date dataInclusaoEtt, String indPrevFinanceiraEtt, Date dataUltManutEtt, ecar.pojo.EstruturaEtt estruturaEtt, ecar.pojo.UsuarioUsu usuarioUsu, Set estruturaAtributoEttats, Set estrutTpFuncAcmpEtttfas, Set estruturaAcessoEttas, Set estruturaSituacaoEtts, Set estruturaFuncaoEttfs, Set itemEstruturaIetts, Set estruturaEtts, Integer seqApresentacaoEtt, String codCor1Ett, String codCor2Ett, String codCor3Ett, String codCor4Ett, Set itemEstruturarevisaoIettrevs, String indEtapaNivelSuperiorEtt, String indExibirGerarArquivos, Set perfilIntercambioDadosCadastroPidcsBaseImp, Set perfilIntercambioDadosCadastroPidcsCriacaoItemImp, Set perfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp) {
        this.siglaEtt = siglaEtt;
        this.tipoAcompanhamentoTas = tipoAcompanhamentoTas;
        this.nomeEtt = nomeEtt;
        this.labelEtt = labelEtt;
        this.indAtivoEtt = indAtivoEtt;
        this.dataInclusaoEtt = dataInclusaoEtt;
        this.indPrevFinanceiraEtt = indPrevFinanceiraEtt;
        this.dataUltManutEtt = dataUltManutEtt;
        this.estruturaEtt = estruturaEtt;
        this.usuarioUsu = usuarioUsu;
        this.estruturaAtributoEttats = estruturaAtributoEttats;
        this.estrutTpFuncAcmpEtttfas = estrutTpFuncAcmpEtttfas;
        this.estruturaAcessoEttas = estruturaAcessoEttas;
        this.estruturaSituacaoEtts = estruturaSituacaoEtts;
        this.estruturaFuncaoEttfs = estruturaFuncaoEttfs;
        this.itemEstruturaIetts = itemEstruturaIetts;
        this.estruturaEtts = estruturaEtts;
        this.seqApresentacaoEtt = seqApresentacaoEtt;
        this.codCor1Ett = codCor1Ett;
        this.codCor2Ett = codCor2Ett;
        this.codCor3Ett = codCor3Ett;
        this.codCor4Ett = codCor4Ett;
        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
        this.indExibirImprimirListagem = indExibirImprimirListagem;
        this.indExibirOpcaoModelo = indExibirOpcaoModelo;
        this.tamanhoListagemVerticalEtt = tamanhoListagemVerticalEtt;
        this.indEtapaNivelSuperiorEtt = indEtapaNivelSuperiorEtt;
        this.indExibirGerarArquivos = indExibirGerarArquivos;
        this.perfilIntercambioDadosCadastroPidcsBaseImp = perfilIntercambioDadosCadastroPidcsBaseImp; 
        this.perfilIntercambioDadosCadastroPidcsCriacaoItemImp = perfilIntercambioDadosCadastroPidcsCriacaoItemImp; 
        this.perfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp = perfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp;
    }

    
	/** default constructor */
    public EstruturaEtt() {
    }

    /** minimal constructor
     * @param tipoAcompanhamentoTas
     * @param estruturaEtt
     * @param usuarioUsu
     * @param estrutTpFuncAcmpEtttfas
     * @param estruturaAcessoEttas
     * @param estruturaAtributoEttats
     * @param estruturaSituacaoEtts
     * @param itemEstruturarevisaoIettrevs
     * @param estruturaFuncaoEttfs
     * @param estruturaEtts
     * @param itemEstruturaIetts
     */
    public EstruturaEtt(Set tipoAcompanhamentoTas, ecar.pojo.EstruturaEtt estruturaEtt, ecar.pojo.UsuarioUsu usuarioUsu, Set estruturaAtributoEttats, Set estrutTpFuncAcmpEtttfas, Set estruturaAcessoEttas, Set estruturaSituacaoEtts, Set estruturaFuncaoEttfs, Set itemEstruturaIetts, Set estruturaEtts, Set itemEstruturarevisaoIettrevs) {
        this.estruturaEtt = estruturaEtt;
        this.tipoAcompanhamentoTas = tipoAcompanhamentoTas;
        this.usuarioUsu = usuarioUsu;
        this.estruturaAtributoEttats = estruturaAtributoEttats;
        this.estrutTpFuncAcmpEtttfas = estrutTpFuncAcmpEtttfas;
        this.estruturaAcessoEttas = estruturaAcessoEttas;
        this.estruturaSituacaoEtts = estruturaSituacaoEtts;
        this.estruturaFuncaoEttfs = estruturaFuncaoEttfs;
        this.itemEstruturaIetts = itemEstruturaIetts;
        this.estruturaEtts = estruturaEtts;
        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
        
    }

    /**
     *
     * @return
     */
    public Long getCodEtt() {
        return this.codEtt;
    }

    /**
     *
     * @param codEtt
     */
    public void setCodEtt(Long codEtt) {
        this.codEtt = codEtt;
    }

    /**
     *
     * @return
     */
    public String getSiglaEtt() {
        return this.siglaEtt;
    }

    /**
     *
     * @param siglaEtt
     */
    public void setSiglaEtt(String siglaEtt) {
        this.siglaEtt = siglaEtt;
    }

    /**
    *
    * @return
    */
    @AvaliaMetodo(valida=false) 
   public String getNomeExibicao() {
    	StringBuffer nome = new StringBuffer();
    	nome.append(getNomeEtt());
    	//Se o label estiver preenchido
    	//o nome de exibição vai fica igual a
    	// nomeEstrutura (labelEstrutura)
    	if (!getLabelEtt().equals(Dominios.STRING_VAZIA)){
    		nome.append(" (" + getLabelEtt() + ")");
    	} 
    	return nome.toString();
   }

    
    /**
     *
     * @return
     */
    public String getNomeEtt() {
        return this.nomeEtt;
    }

    /**
     *
     * @param nomeEtt
     */
    public void setNomeEtt(String nomeEtt) {
        this.nomeEtt = nomeEtt;
    }

    /**
     *
     * @return
     */
    public String getLabelEtt() {
		return labelEtt;
	}

    /**
     *
     * @param labelEtt
     */
    public void setLabelEtt(String labelEtt) {
		this.labelEtt = labelEtt;
	}

    /**
     *
     * @return
     */
    public String getIndAtivoEtt() {
        return this.indAtivoEtt;
    }

        /**
         *
         * @param indAtivoEtt
         */
        public void setIndAtivoEtt(String indAtivoEtt) {
        this.indAtivoEtt = indAtivoEtt;
    }

        /**
         *
         * @return
         */
        public Date getDataInclusaoEtt() {
        return this.dataInclusaoEtt;
    }

        /**
         *
         * @param dataInclusaoEtt
         */
        public void setDataInclusaoEtt(Date dataInclusaoEtt) {
        this.dataInclusaoEtt = dataInclusaoEtt;
    }

    /**
     *
     * @return
     */
    public String getIndPrevFinanceiraEtt() {
        return this.indPrevFinanceiraEtt;
    }

    /**
     *
     * @param indPrevFinanceiraEtt
     */
    public void setIndPrevFinanceiraEtt(String indPrevFinanceiraEtt) {
        this.indPrevFinanceiraEtt = indPrevFinanceiraEtt;
    }

    /**
     *
     * @return
     */
    public Date getDataUltManutEtt() {
        return this.dataUltManutEtt;
    }

    /**
     *
     * @return
     */
    public String getIndEtapaNivelSuperiorEtt() {
		return indEtapaNivelSuperiorEtt;
	}

    /**
     *
     * @param indEtapaNivelSuperiorEtt
     */
    public void setIndEtapaNivelSuperiorEtt(String indEtapaNivelSuperiorEtt) {
		this.indEtapaNivelSuperiorEtt = indEtapaNivelSuperiorEtt;
	}

        /**
         *
         * @param dataUltManutEtt
         */
        public void setDataUltManutEtt(Date dataUltManutEtt) {
        this.dataUltManutEtt = dataUltManutEtt;
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

    /**
     *
     * @return
     */
    public Set getEstruturaAtributoEttats() {
        return this.estruturaAtributoEttats;
    }

    /**
     *
     * @param estruturaAtributoEttats
     */
    public void setEstruturaAtributoEttats(Set estruturaAtributoEttats) {
        this.estruturaAtributoEttats = estruturaAtributoEttats;
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
    public Set getEstruturaAcessoEttas() {
        return this.estruturaAcessoEttas;
    }

    /**
     *
     * @param estruturaAcessoEttas
     */
    public void setEstruturaAcessoEttas(Set estruturaAcessoEttas) {
        this.estruturaAcessoEttas = estruturaAcessoEttas;
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
    public Set getEstruturaFuncaoEttfs() {
        return this.estruturaFuncaoEttfs;
    }

    /**
     *
     * @param estruturaFuncaoEttfs
     */
    public void setEstruturaFuncaoEttfs(Set estruturaFuncaoEttfs) {
        this.estruturaFuncaoEttfs = estruturaFuncaoEttfs;
    }

    /**
     *
     * @return
     */
    public Set getItemEstruturaIetts() {
        return this.itemEstruturaIetts;
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
    public Set getEstruturaEtts() {
        return this.estruturaEtts;
    }

    /**
     *
     * @param estruturaEtts
     */
    public void setEstruturaEtts(Set estruturaEtts) {
        this.estruturaEtts = estruturaEtts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codEtt", getCodEtt())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EstruturaEtt) ) return false;
        EstruturaEtt castOther = (EstruturaEtt) other;
        return new EqualsBuilder()
            .append(this.getCodEtt(), castOther.getCodEtt())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodEtt())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Integer getSeqApresentacaoEtt() {
		return seqApresentacaoEtt;
	}

        /**
         *
         * @param seqApresentacaoEtt
         */
        public void setSeqApresentacaoEtt(Integer seqApresentacaoEtt) {
		this.seqApresentacaoEtt = seqApresentacaoEtt;
	}

        /**
         *
         * @return
         */
        public String getCodCor1Ett() {
		return codCor1Ett;
	}

        /**
         *
         * @param codCor1Ett
         */
        public void setCodCor1Ett(String codCor1Ett) {
		this.codCor1Ett = codCor1Ett;
	}

        /**
         *
         * @return
         */
        public String getCodCor2Ett() {
		return codCor2Ett;
	}

        /**
         *
         * @param codCor2Ett
         */
        public void setCodCor2Ett(String codCor2Ett) {
		this.codCor2Ett = codCor2Ett;
	}

        /**
         *
         * @return
         */
        public String getCodCor3Ett() {
		return codCor3Ett;
	}

        /**
         *
         * @param codCor3Ett
         */
        public void setCodCor3Ett(String codCor3Ett) {
		this.codCor3Ett = codCor3Ett;
	}

        /**
         *
         * @return
         */
        public String getCodCor4Ett() {
		return codCor4Ett;
	}

        /**
         *
         * @param codCor4Ett
         */
        public void setCodCor4Ett(String codCor4Ett) {
		this.codCor4Ett = codCor4Ett;
	}

        /**
         *
         * @return
         */
        public String getIndExibirImprimirListagem() {
		return indExibirImprimirListagem;
	}

        /**
         *
         * @param indExibirImprimirListagem
         */
        public void setIndExibirImprimirListagem(String indExibirImprimirListagem) {
		this.indExibirImprimirListagem = indExibirImprimirListagem;
	}
        
        public String getIndExibirOpcaoModelo() {
    		return indExibirOpcaoModelo;
    	}

    	public void setIndExibirOpcaoModelo(String indExibirOpcaoModelo) {
    		this.indExibirOpcaoModelo = indExibirOpcaoModelo;
    	}

        /**
         *
         * @return
         */
        public Long getTamanhoListagemVerticalEtt() {
		return tamanhoListagemVerticalEtt;
	}

        /**
         *
         * @param tamanhoListagemVerticalEtt
         */
        public void setTamanhoListagemVerticalEtt(Long tamanhoListagemVerticalEtt) {
		this.tamanhoListagemVerticalEtt = tamanhoListagemVerticalEtt;
	}

        /**
         *
         * @return
         */
        public Set getTipoAcompanhamentoTas() {
		return tipoAcompanhamentoTas;
	}

        /**
         *
         * @param tipoAcompanhamentoTas
         */
        public void setTipoAcompanhamentoTas(Set tipoAcompanhamentoTas) {
		this.tipoAcompanhamentoTas = tipoAcompanhamentoTas;
	}

	/**
	 * @return the estruturaNivelGeracaoTas
	 */
	public Set getEstruturaNivelGeracaoTas() {
		return estruturaNivelGeracaoTas;
	}

	/**
	 * @param estruturaNivelGeracaoTas the estruturaNivelGeracaoTas to set
	 */
	public void setEstruturaNivelGeracaoTas(Set estruturaNivelGeracaoTas) {
		this.estruturaNivelGeracaoTas = estruturaNivelGeracaoTas;
	}

        /**
         *
         * @return
         */
        public String getIndExibirGerarArquivos() {
		return indExibirGerarArquivos;
	}

        /**
         *
         * @param indExibirGerarArquivos
         */
        public void setIndExibirGerarArquivos(String indExibirGerarArquivos) {
		this.indExibirGerarArquivos = indExibirGerarArquivos;
	}


        /**
         *
         * @return
         */
        public Long iGetCodigo() {
		return getCodEtt();
	}

        /**
         *
         * @return
         */
        public String iGetNome() {
		return getNomeEtt();
	}

        /**
         *
         * @return
         */
        public Hierarchyable iGetPai() {
		return getEstruturaEtt();
	}

        /**
         *
         * @return
         */
        public Hierarchyable iGetEstrutura() {
		return this;
	}

        /**
         *
         * @return
         */
        public Set getEstruturaFuncaoEttfsEscolhidas() {
		return estruturaFuncaoEttfsEscolhidas;
	}

        /**
         *
         * @param estruturaFuncaoEttfsEscolhidas
         */
    public void setEstruturaFuncaoEttfsEscolhidas(Set estruturaFuncaoEttfsEscolhidas) {
		this.estruturaFuncaoEttfsEscolhidas = estruturaFuncaoEttfsEscolhidas;
	}
	 
    public Set getPerfilIntercambioDadosCadastroPidcsBaseImp() {
		return perfilIntercambioDadosCadastroPidcsBaseImp;
	}

	public void setPerfilIntercambioDadosCadastroPidcsBaseImp(
			Set perfilIntercambioDadosCadastroPidcsBaseImp) {
		this.perfilIntercambioDadosCadastroPidcsBaseImp = perfilIntercambioDadosCadastroPidcsBaseImp;
	}

	public Set getPerfilIntercambioDadosCadastroPidcsCriacaoItemImp() {
		return perfilIntercambioDadosCadastroPidcsCriacaoItemImp;
	}

	public void setPerfilIntercambioDadosCadastroPidcsCriacaoItemImp(
			Set perfilIntercambioDadosCadastroPidcsCriacaoItemImp) {
		this.perfilIntercambioDadosCadastroPidcsCriacaoItemImp = perfilIntercambioDadosCadastroPidcsCriacaoItemImp;
	}

	public Set getPerfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp() {
		return perfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp;
	}

	public void setPerfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp(
			Set perfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp) {
		this.perfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp = perfilIntercambioDadosCadastroPidcsItemNivelSuperiorImp;
	}
	
	public String getIndExibirEstruturaEtt() {
		return indExibirEstruturaEtt;
	}

	public void setIndExibirEstruturaEtt(String indExibirEstruturaEtt) {
		this.indExibirEstruturaEtt = indExibirEstruturaEtt;
	}

	public AtributosAtb getAtributoAtbExibirEstruturaEtt() {
		return atributoAtbExibirEstruturaEtt;
	}

	public void setAtributoAtbExibirEstruturaEtt(AtributosAtb atributoAtbExibirEstruturaEtt) {
		this.atributoAtbExibirEstruturaEtt = atributoAtbExibirEstruturaEtt;
	}

	public Set<SisAtributoSatb> getSisAtributoSatbEttSuperior() {
		return sisAtributoSatbEttSuperior;
	}

	public void setSisAtributoSatbEttSuperior(Set<SisAtributoSatb> sisAtributoSatbEttSuperior) {
		this.sisAtributoSatbEttSuperior = sisAtributoSatbEttSuperior;
	}

	/**
	 * Manipula um atributo transiente, necessário para preservar a ordem de inserção do objeto EstruturaEtt em uma coleção que preserve a ordem de inserção.
	 * Ex.: TreeMap, SortedSet
	 */
	public int compareTo(EstruturaEtt o) {
		if (this.getOrdemInsercao() != 0){
			if(this.getOrdemInsercao() < o.getOrdemInsercao())
				return -1;
			else if(this.getOrdemInsercao() > o.getOrdemInsercao())
				return 1;
			return 0;
		} else {
			if(this.getCodEtt() < o.getCodEtt())
				return -1;
			else if(this.getCodEtt() > o.getCodEtt())
				return 1;
			return 0;
		}
		
	}

}
