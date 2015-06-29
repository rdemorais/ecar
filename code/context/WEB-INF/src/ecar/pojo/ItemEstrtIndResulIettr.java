package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import ecar.exception.ECARException;
import ecar.sinalizacao.Sinalizacao;
import ecar.sinalizacao.dao.SinalizacaoDao;


/** 
 * Esta classe representa o indicador cadastrado no sistema. Est� relacionado com a classe {@link ItemEstrutFisicoIettf}
 * onde as quantidades previstas s�o informadas.
 * 
 * @author Hibernate CodeGenerator 
 * */
public class ItemEstrtIndResulIettr implements Serializable, Cloneable {

	private static final long serialVersionUID = 1339358391652674029L;
		/**
     *
     */
    public static final String AUTOMATICO = "A";
        /**
         *
         */
        public static final String MANUAL = "M";
	
	/** identifier field */
    private Long codIettir;

    /** nullable persistent field */
    // Unidade de medida usada caso seja uma descri��o. Neste caso, guarda o que o usu�rio digita.
    private String unidMedidaIettr;
    
    /** nullable persistent field */
    // Unidade de medida usado caso seja um combo. Neste caso guarda o c�digo
    private ecar.pojo.SisAtributoSatb codUnidMedidaIettr;

    /** nullable persistent field */
    private String descricaoIettir;

    /** nullable persistent field */
    private String nomeIettir;
    
    // INI - Ministerio da Saude - RIPSA
    private String conceituacao;
    
    private String interpretacao;
    
    private String metodoCalculo;
    
    private String fonteIettr;
    
    private Integer ordem;
    
    // FIM - Ministerio da Saude - RIPSA

    /** nullable persistent field */
    private String indProjecaoIettr;

    /** nullable persistent field */
    private String indAcumulavelIettr;

    /** persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** persistent field */
    private Set acompRealFisicoArfs;

    /** persistent field */
    private Set<ItemEstrutFisicoIettf> itemEstrutFisicoIettfs;

    /** nullable persistent field */
    private String indTipoQtde;

    /** nullable persistent field */
    private String indValorFinalIettr;
    
    private String indRealPorLocal;
    
    private Set iettIndResulRevIettrrs;
    
    /** persistent field */
    private ecar.pojo.SisAtributoSatb sisAtributoSatb;
    
    private ecar.pojo.PeriodicidadePrdc periodicidadePrdc;
    
    /* Mantis #2156 */
    private Set historicoIettfHs;

    private Boolean indExclusaoPosHistorico;

    private String formulaIettr;

    private Double indiceMaisRecenteIettr;
    
    private Date dataApuracaoIettr;
    
    private Date dataUltManutencao;
    
    private UsuarioUsu usuarioUsuManutencao;
    
    private String indAtivoIettr;
    
    /** nullable persistent field */
    private String indPublicoIettr;
    
    /** nullable persistent field */
    private String labelGraficoGrupoIettir;
    
    /** nullable persistent field */
    private String indSinalizacaoIettr;
    
    /**
     * Quantidade realizada informada por local MANTIS #0011576
     */
    private String indPrevPorLocal;
    
    /** persistent field */
    private ecar.pojo.ItemEstruturaIettPPA itemEstruturaIettPPA;
    
    /** persistent field */
    @Deprecated
    private Set itemEstrtIndResulCorIettrcores;
    
    private Sinalizacao sinalizacao;
    
    private Double linhaBase;
    
    private Boolean considerarLinhaDeBase;
    
    /**
     * Tipo de Atualiza��o
     * A - Autom�tica
     * M - Manual 
     */
    private String indTipoAtualizacaoPrevisto;
    
    /**
     * Tipo de Atualiza��o
     * A - Autom�tica
     * M - Manual 
     */
    private String indTipoAtualizacaoRealizado;
    
    /**
     * Utiliza��o de Estrutura de Servi�o
     * Ir� indicar qual o servi�o fornece os c�lculos para o campo 
     *  Realizado 
     */
    private ServicoSer realizadoServicoSer;

    /**
     * Utiliza��o de Estrutura de Servi�o
     * Ir� indicar qual o servi�o fornece os c�lculos para o campo 
     *  Previsto 
     */
    private ServicoSer previstoServicoSer;

    
    
    /*
     * Retorna o valor do nivel de abrang�ncia
     * para o indicador
     */
    private Long nivelAbrangencia; 
    
    public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Boolean getConsiderarLinhaDeBase() {
		return considerarLinhaDeBase;
	}

	public void setConsiderarLinhaDeBase(Boolean considerarLinhaDeBase) {
		this.considerarLinhaDeBase = considerarLinhaDeBase;
	}

	public Double getLinhaBase() {
		return linhaBase;
	}

	public void setLinhaBase(Double linhaBase) {
		this.linhaBase = linhaBase;
	}

	public Sinalizacao getSinalizacao() {
		if(sinalizacao == null){
			sinalizacao = new Sinalizacao(1L);
		}
		return sinalizacao;
	}

	public void setSinalizacao(Sinalizacao sinalizacao) {
		this.sinalizacao = sinalizacao;
	}

	public String getConceituacao() {
		return conceituacao;
	}

	public void setConceituacao(String conceituacao) {
		this.conceituacao = conceituacao;
	}

	public String getInterpretacao() {
		return interpretacao;
	}

	public void setInterpretacao(String interpretacao) {
		this.interpretacao = interpretacao;
	}

	public String getMetodoCalculo() {
		return metodoCalculo;
	}

	public void setMetodoCalculo(String metodoCalculo) {
		this.metodoCalculo = metodoCalculo;
	}

	public Long getNivelAbrangencia() {
		
		return nivelAbrangencia;    	

	}

	public void setNivelAbrangencia(Long nivelAbrangencia) {
		this.nivelAbrangencia = nivelAbrangencia;
	}

	/**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturaIettPPA getItemEstruturaIettPPA() {
		return itemEstruturaIettPPA;
	}

        /**
         *
         * @param itemEstruturaIettPPA
         */
        public void setItemEstruturaIettPPA(
			ecar.pojo.ItemEstruturaIettPPA itemEstruturaIettPPA) {
		this.itemEstruturaIettPPA = itemEstruturaIettPPA;
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
        public Set getHistoricoIettfHs() {
		return historicoIettfHs;
	}

        /**
         *
         * @param historicoIettfHs
         */
        public void setHistoricoIettfHs(Set historicoIettfHs) {
		this.historicoIettfHs = historicoIettfHs;
	}
    
        /** Full Constructor
         * @param unidMedidaIettr
         * @param indTipoQtde
         * @param nomeIettir
         * @param descricaoIettir
         * @param indAcumulavelIettr
         * @param itemEstrtIndResulCorIettrcores
         * @param indProjecaoIettr
         * @param acompRealFisicoArfs
         * @param indiceMaisRecenteIettr
         * @param indPublicoIettr
         * @param indValorFinalIettr
         * @param itemEstrutFisicoIettfs
         * @param itemEstruturaIett
         * @param labelGraficoGrupoIettir
         * @param fonteIettr
         * @param sisAtributoSatb
         * @param indPrevPorLocal
         * @param formulaIettr
         * @param indRealPorLocal
         * @param itemEstrtIndResulLocalIettirls
         * @param indAtivoIettr
         * @param dataApuracaoIettr
         * @param periodicidadePrdc
         * @param indSinalizacaoIettr
         */
	public ItemEstrtIndResulIettr(String unidMedidaIettr, String descricaoIettir, String nomeIettir, String indProjecaoIettr, String indAcumulavelIettr, String indPublicoIettr, ecar.pojo.ItemEstruturaIett itemEstruturaIett, Set acompRealFisicoArfs, Set itemEstrutFisicoIettfs, String indTipoQtde, String indValorFinalIettr, String indRealPorLocal, ecar.pojo.SisAtributoSatb sisAtributoSatb, ecar.pojo.PeriodicidadePrdc periodicidadePrdc, String fonteIettr, String formulaIettr, Double indiceMaisRecenteIettr, Date dataApuracaoIettr, Set itemEstrtIndResulLocalIettirls, String indAtivoIettr, String indPrevPorLocal, String labelGraficoGrupoIettir, Set itemEstrtIndResulCorIettrcores, String indSinalizacaoIettr, Sinalizacao sinalizacao) {
		this.unidMedidaIettr = unidMedidaIettr;
		this.descricaoIettir = descricaoIettir;
		this.nomeIettir = nomeIettir;
		this.indProjecaoIettr = indProjecaoIettr;
		this.indAcumulavelIettr = indAcumulavelIettr;
		this.indPublicoIettr = indPublicoIettr;
		this.itemEstruturaIett = itemEstruturaIett;
		this.acompRealFisicoArfs = acompRealFisicoArfs;
		this.itemEstrutFisicoIettfs = itemEstrutFisicoIettfs;
		this.indTipoQtde = indTipoQtde;
		this.indValorFinalIettr = indValorFinalIettr;
		this.indRealPorLocal = indRealPorLocal;
		this.sisAtributoSatb = sisAtributoSatb;
		this.periodicidadePrdc = periodicidadePrdc;
		this.fonteIettr = fonteIettr;
		this.formulaIettr = formulaIettr;
		this.indiceMaisRecenteIettr = indiceMaisRecenteIettr;
		this.dataApuracaoIettr = dataApuracaoIettr;
		this.indAtivoIettr = indAtivoIettr;
		this.indPrevPorLocal = indPrevPorLocal;
		this.labelGraficoGrupoIettir = labelGraficoGrupoIettir;
		this.itemEstrtIndResulCorIettrcores = itemEstrtIndResulCorIettrcores;
		this.indSinalizacaoIettr = indSinalizacaoIettr;
		this.sinalizacao = sinalizacao;
	}

    /** default constructor */
    public ItemEstrtIndResulIettr() {
    }

    /** minimal constructor
     * @param itemEstruturaIett
     * @param sisAtributoSatb
     * @param acompRealFisicoArfs
     * @param itemEstrutFisicoIettfs
     * @param indAtivoIettr
     */
    public ItemEstrtIndResulIettr(ecar.pojo.ItemEstruturaIett itemEstruturaIett, Set acompRealFisicoArfs, Set itemEstrutFisicoIettfs, ecar.pojo.SisAtributoSatb sisAtributoSatb, String indAtivoIettr) {
        this.itemEstruturaIett = itemEstruturaIett;
        this.acompRealFisicoArfs = acompRealFisicoArfs;
        this.itemEstrutFisicoIettfs = itemEstrutFisicoIettfs;
        this.sisAtributoSatb = sisAtributoSatb;
        this.indAtivoIettr = indAtivoIettr;
        try {
			this.sinalizacao = (Sinalizacao) new SinalizacaoDao().buscar(Sinalizacao.class, new Long(1));
		} catch (ECARException e) {
			e.printStackTrace();
		}
    }

    /**
     *
     * @return
     */
    public Long getCodIettir() {
        return this.codIettir;
    }

    /**
     *
     * @param codIettir
     */
    public void setCodIettir(Long codIettir) {
        this.codIettir = codIettir;
    }

    /**
     *
     * @return
     */
    public String getUnidMedidaIettr() {
        return this.unidMedidaIettr;
    }

    /**
     *
     * @param unidMedidaIettr
     */
    public void setUnidMedidaIettr(String unidMedidaIettr) {
        this.unidMedidaIettr = unidMedidaIettr;
    }

    /**
     *
     * @return
     */
    public String getDescricaoIettir() {
        return this.descricaoIettir;
    }

    /**
     *
     * @param descricaoIettir
     */
    public void setDescricaoIettir(String descricaoIettir) {
        this.descricaoIettir = descricaoIettir;
    }

    /**
     *
     * @return
     */
    public String getNomeIettir() {
        return this.nomeIettir;
    }

    /**
     *
     * @param nomeIettir
     */
    public void setNomeIettir(String nomeIettir) {
        this.nomeIettir = nomeIettir;
    }

    /**
     *
     * @return
     */
    public String getIndProjecaoIettr() {
        return this.indProjecaoIettr;
    }

    /**
     *
     * @param indProjecaoIettr
     */
    public void setIndProjecaoIettr(String indProjecaoIettr) {
        this.indProjecaoIettr = indProjecaoIettr;
    }

    /**
     *
     * @return
     */
    public String getIndAcumulavelIettr() {
        return this.indAcumulavelIettr;
    }
    
    /**
     *
     * @return
     */
    public String getIndPublicoIettr() {
        return this.indPublicoIettr;
    }

    /**
     *
     * @param indPublicoIettr
     */
    public void setIndPublicoIettr(String indPublicoIettr) {
		this.indPublicoIettr = indPublicoIettr;
	}

    /**
     *
     * @param indAcumulavelIettr
     */
    public void setIndAcumulavelIettr(String indAcumulavelIettr) {
        this.indAcumulavelIettr = indAcumulavelIettr;
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

    /**
     *
     * @return
     */
    
    public Set<ItemEstrutFisicoIettf> getItemEstrutFisicoIettfs() {
        return this.itemEstrutFisicoIettfs;
    }

    /**
     *
     * @param itemEstrutFisicoIettfs
     */
    public void setItemEstrutFisicoIettfs(Set<ItemEstrutFisicoIettf> itemEstrutFisicoIettfs) {
        this.itemEstrutFisicoIettfs = itemEstrutFisicoIettfs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIettir", getCodIettir())
            .toString();
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

    @Override
	public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrtIndResulIettr) ) return false;
        ItemEstrtIndResulIettr castOther = (ItemEstrtIndResulIettr) other;
        return new EqualsBuilder()
            .append(this.getCodIettir(), castOther.getCodIettir())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettir())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public String getIndTipoQtde() {
		return indTipoQtde;
	}

        /**
         *
         * @param indTipoQtde
         */
        public void setIndTipoQtde(String indTipoQtde) {
		this.indTipoQtde = indTipoQtde;
	}

        /**
         *
         * @return
         */
        public String getIndValorFinalIettr() {
		return indValorFinalIettr;
	}

        /**
         *
         * @param indValorFinalIettr
         */
        public void setIndValorFinalIettr(String indValorFinalIettr) {
		this.indValorFinalIettr = indValorFinalIettr;
	}

        /**
         *
         * @return
         */
        public Set getIettIndResulRevIettrrs() {
		return iettIndResulRevIettrrs;
	}

        /**
         *
         * @param iettIndResulRevIettrrs
         */
        public void setIettIndResulRevIettrrs(Set iettIndResulRevIettrrs) {
		this.iettIndResulRevIettrrs = iettIndResulRevIettrrs;
	}

        /**
         *
         * @return
         */
        public String getIndRealPorLocal() {
		return indRealPorLocal;
	}

        /**
         *
         * @param indRealPorLocal
         */
        public void setIndRealPorLocal(String indRealPorLocal) {
		this.indRealPorLocal = indRealPorLocal;
	}

        /**
         *
         * @return
         */
        public Date getDataApuracaoIettr() {
		return dataApuracaoIettr;
	}

        /**
         *
         * @param dataApuracaoIettr
         */
        public void setDataApuracaoIettr(Date dataApuracaoIettr) {
		this.dataApuracaoIettr = dataApuracaoIettr;
	}

        /**
         *
         * @return
         */
        public String getFonteIettr() {
		return fonteIettr;
	}

        /**
         *
         * @param fonteIettr
         */
        public void setFonteIettr(String fonteIettr) {
		this.fonteIettr = fonteIettr;
	}

        /**
         *
         * @return
         */
        public String getFormulaIettr() {
		return formulaIettr;
	}

        /**
         *
         * @param formulaIettr
         */
        public void setFormulaIettr(String formulaIettr) {
		this.formulaIettr = formulaIettr;
	}

        /**
         *
         * @return
         */
        public Double getIndiceMaisRecenteIettr() {
		return indiceMaisRecenteIettr;
	}

        /**
         *
         * @param indiceMaisRecenteIettr
         */
        public void setIndiceMaisRecenteIettr(Double indiceMaisRecenteIettr) {
		this.indiceMaisRecenteIettr = indiceMaisRecenteIettr;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.PeriodicidadePrdc getPeriodicidadePrdc() {
		return periodicidadePrdc;
	}

        /**
         *
         * @param periodicidadePrdc
         */
        public void setPeriodicidadePrdc(ecar.pojo.PeriodicidadePrdc periodicidadePrdc) {
		this.periodicidadePrdc = periodicidadePrdc;
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
         * @return
         */
        public String getIndAtivoIettr() {
		return indAtivoIettr;
	}

        /**
         *
         * @param indAtivoIettr
         */
        public void setIndAtivoIettr(String indAtivoIettr) {
		this.indAtivoIettr = indAtivoIettr;
	}

        /**
         *
         * @return
         */
        public String getIndPrevPorLocal() {
		return indPrevPorLocal;
	}

        /**
         *
         * @param indPrevPorLocal
         */
        public void setIndPrevPorLocal(String indPrevPorLocal) {
		this.indPrevPorLocal = indPrevPorLocal;
	}
	
        /**
         *
         * @return
         */
        public ecar.pojo.SisAtributoSatb getCodUnidMedidaIettr() {
		return codUnidMedidaIettr;
	}

        /**
         *
         * @param codUnidMedidaIettr
         */
        public void setCodUnidMedidaIettr(ecar.pojo.SisAtributoSatb codUnidMedidaIettr) {
		this.codUnidMedidaIettr = codUnidMedidaIettr;
	}

        /**
         *
         * @return
         */
        public String getLabelGraficoGrupoIettir() {
		return labelGraficoGrupoIettir;
	}

        /**
         *
         * @param labelGraficoGrupoIettir
         */
        public void setLabelGraficoGrupoIettir(String labelGraficoGrupoIettir) {
		this.labelGraficoGrupoIettir = labelGraficoGrupoIettir;
	}

        /**
         *
         * @return
         */
        public Set<ItemEstrtIndResulCorIettrcor> getItemEstrtIndResulCorIettrcores() {
		return itemEstrtIndResulCorIettrcores;
	}

        /**
         *
         * @param itemEstrtIndResulCorIettrcores
         */
        public void setItemEstrtIndResulCorIettrcores(Set<ItemEstrtIndResulCorIettrcor> itemEstrtIndResulCorIettrcores) {
		this.itemEstrtIndResulCorIettrcores = itemEstrtIndResulCorIettrcores;
	}

        /**
         *
         * @return
         */
        public String getIndSinalizacaoIettr() {
		return indSinalizacaoIettr;
	}

        /**
         *
         * @param indSinalizacaoIettr
         */
        public void setIndSinalizacaoIettr(String indSinalizacaoIettr) {
		this.indSinalizacaoIettr = indSinalizacaoIettr;
	}

        /**
         *
         * @return
         */
        public String getIndTipoAtualizacaoPrevisto() {
		return indTipoAtualizacaoPrevisto;
	}

        /**
         *
         * @param indTipoAtualizacaoPrevisto
         */
        public void setIndTipoAtualizacaoPrevisto(String indTipoAtualizacaoPrevisto) {
		this.indTipoAtualizacaoPrevisto = indTipoAtualizacaoPrevisto;
	}

        /**
         *
         * @return
         */
        public String getIndTipoAtualizacaoRealizado() {
		return indTipoAtualizacaoRealizado;
	}

        /**
         *
         * @param indTipoAtualizacaoRealizado
         */
        public void setIndTipoAtualizacaoRealizado(String indTipoAtualizacaoRealizado) {
		this.indTipoAtualizacaoRealizado = indTipoAtualizacaoRealizado;
	}

        /**
         *
         * @return
         */
        public ServicoSer getRealizadoServicoSer() {
		return realizadoServicoSer;
	}

        /**
         *
         * @param realizadoServicoSer
         */
        public void setRealizadoServicoSer(ServicoSer realizadoServicoSer) {
		this.realizadoServicoSer = realizadoServicoSer;
	}

        /**
         *
         * @return
         */
        public ServicoSer getPrevistoServicoSer() {
		return previstoServicoSer;
	}

        /**
         *
         * @param previstoServicoSer
         */
        public void setPrevistoServicoSer(ServicoSer previstoServicoSer) {
		this.previstoServicoSer = previstoServicoSer;
	}
        
        
}
