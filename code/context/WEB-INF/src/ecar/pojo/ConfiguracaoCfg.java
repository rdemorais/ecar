package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ConfiguracaoCfg implements Serializable {

    
	/**
	 * 
	 */
	private static final long serialVersionUID = 6382685284312412737L;

	/** identifier field */
    private Long codCfg;

    /** nullable persistent field */
    private String nomeEstruturaCfg;

    /** nullable persistent field */
    private String tpArqIntegFinanceiraCfg;

    /** nullable persistent field */
    private String indGerarHistoricoCfg;
        
    /** persistent field */
    private Set configMailCfgms;
 
   /** persistent field */
    private Set cfgAbas;  
    
    /** nullable persistent field */
    private String horaEnvioMailCfg;

    /** nullable persistent field */
    private Long diasAntecedenciaMailCfg;
    
    private Long intervaloAtualizacaoEmail;
    
    private Date ultimoEnvioEmailCfg;
    
	private String tituloSistema;
	
	private String emailServer;
	
	private Estilo estilo;
	
	private String imagemEsquerda;
	
	private String raizUpload;
	
	private String uploadCategoria;
	
	private String contextPath;
	
	private String uploadAnexos;
	
	private String uploadEmpresa;
	
	private String uploadAdmPortal;
	
	private String uploadUsuarios; 
	
	private String uploadIconeLinks;
	
	private String uploadExportacaoDemandas;
	
	private String separadorArqTXT;
	
	private String separadorCampoMultivalor;
	
	private String exibDefaultEstCfg;
	
	private String labelMonitorado;
	
	private String labelOrgao;
	
	private String labelAgrupamentoItensSemOrgao;
		
	private String labelSituacaoParecer;
	
	private String labelCorParecer;

	private String labelSituacaoListaPareceres;
	
	private String financeiroDescValor1Cfg;
	
	private String financeiroDescValor2Cfg;
	
	private String financeiroDescValor3Cfg;
	
	private String financeiroDescValor4Cfg;
	
	private String financeiroDescValor5Cfg;
	
	private String financeiroDescValor6Cfg;
	
	
	private Long numRegistros;
	
	private Long qtdeItensGalAnexo;
	
	private int nuItensExibidosPaginacao;
	
	private Date ultimaAtualizacaoEmail;
	
    private String recursoDescValor1Cfg;
	
	private String recursoDescValor2Cfg;
	
	private String recursoDescValor3Cfg;
	
	private Long periodoPadrao;
	
	private Long tempoSessao;
	 
    /** persistent field */
    private SisGrupoAtributoSga sisGrupoAtributoSgaByUnidMedida;

    /** persistent field */
    private SisGrupoAtributoSga sisGrupoAtributoSgaByTipoEvento;

    /** persistent field */
    private SisAtributoSatb sisAtributoSatbByCodSacapa;

    /** persistent field */
    private SisAtributoSatb sisAtributoSatbByCodSapadrao;

    /** persistent field */
    private SisAtributoSatb sisAtributoSatbByCodSaAcesso;
    
    /** persistent field */
    private SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrPgIni;

    /** persistent field */
    private SisGrupoAtributoSga sisGrupoAtributoSgaTipoPontoCritico;

    /** persistent field */
    private SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrNvPlan;

    /** persistent field */
    private SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrClAcesso;

    /** persistent field */
    private SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa;

    /** persistent field */
    private SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso;
    
    /** persistent field */
    private SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas;

    

    
    
    /** persistent field */
    private PeriodicidadePrdc periodicidadePrdc;
    
    private Set periodoRevisaoPrevs;
    
    private String uploadIntegracao;
    
    /** nullable persistent field */
    private String indOcultarObservacoesParecer;
    
    /** nullable persistent field */
    private String indExibirSituacoesFormatoAbas;

    /** nullable persistent field */
    private String indExibirArvoreNavegacaoCfg;
    
    private VisaoDemandasVisDem visaoDemandasVisDem;
    
    /** 
     * full constructor.<br>
     * 
     * @author N/C
	 * @since N/C 
     * @param nomeEstruturaCfg
     * @param tpArqIntegFinanceiraCfg
     * @param indLiberarAcompCfg
     * @param indGerarHistoricoCfg
     * @param sisAtributoSatbByCodSacapa
     * @param sisGrupoAtributoSgaByCodSgaGrAtrPgIni
     * @param sisGrupoAtributoSgaByCodSgaGrAtrNvPlan
     * @param sisAtributoSatbByCodSapadrao
     * @param sisGrupoAtributoSgaByCodSgaGrAtrClAcesso
     * @param sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa
     * @param sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso
     * @param sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas
     * @param sisAtributoSatbByCodSaAcesso 
     * @param periodicidadePrdc
     * @param configMailCfgms
     * @param horaEnvioMailCfg
     * @param periodoRevisaoPrevs 
     * @param diasAntecedenciaMailCfg
     * @param ultimaAtualizacaoEmail
     * @param intervaloAtualizacaoEmail
     * @param cfgAbas
     * @param estilo 
     * @param ultimoEnvioEmailCfg
     * @param tituloSistema 
     * @param emailServer
     * @param raizUpload
     * @param imagemEsquerda
     * @param uploadCategoria
     * @param uploadAnexos 
     * @param uploadAdmPortal
     * @param uploadEmpresa
     * @param uploadIconeLinks 
     * @param uploadUsuarios
     * @param nuItensExibidosPaginacao
     * @param labelOrgao
     * @param labelAgrupamentoItensSemOrgao
     * @param numRegistros
     * @param exibDefaultEstCfg
     * @param labelCorParecer
     * @param labelMonitorado
     * @param qtdeItensGalAnexo
     * @param labelSituacaoListaPareceres
     * @param labelSituacaoParecer
     * @param financeiroDescValor1Cfg 
     * @param financeiroDescValor2Cfg 
     * @param financeiroDescValor3Cfg
     * @param financeiroDescValor5Cfg
     * @param recursoDescValor2Cfg
     * @param financeiroDescValor4Cfg
     * @param sisGrupoAtributoSgaByTipoEvento
     * @param financeiroDescValor6Cfg
     * @param uploadIntegracao
     * @param periodoPadrao
     * @param recursoDescValor1Cfg
     * @param sisGrupoAtributoSgaByUnidMedida
     * @param sisGrupoAtributoSgaTipoPontoCritico
     * @param indExibirArvoreNavegacaoCfg
     * @param recursoDescValor3Cfg
     */
    public ConfiguracaoCfg(String nomeEstruturaCfg, String tpArqIntegFinanceiraCfg, String indLiberarAcompCfg, 
    		String indGerarHistoricoCfg, ecar.pojo.SisAtributoSatb sisAtributoSatbByCodSacapa, 
    		ecar.pojo.SisAtributoSatb sisAtributoSatbByCodSapadrao, 
    		ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrPgIni, 
    		ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrNvPlan, 
    		ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrClAcesso, 
    		ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa, 
    		ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso, 
    		ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas, 
    		ecar.pojo.PeriodicidadePrdc periodicidadePrdc, SisAtributoSatb sisAtributoSatbByCodSaAcesso,
    		Long diasAntecedenciaMailCfg, String horaEnvioMailCfg, Set cfgAbas, Set configMailCfgms, 
    		Set periodoRevisaoPrevs, Date ultimaAtualizacaoEmail, Long intervaloAtualizacaoEmail, 
    		Date ultimoEnvioEmailCfg, String tituloSistema, String emailServer, Estilo estilo, String imagemEsquerda, 
    		String raizUpload, String uploadCategoria, String uploadAnexos, String uploadEmpresa, 
    		String uploadAdmPortal, String uploadUsuarios, String uploadIconeLinks, Long numRegistros, Long qtdeItensGalAnexo, 
    		int nuItensExibidosPaginacao, String exibDefaultEstCfg, String labelMonitorado, String labelOrgao,
    		String labelAgrupamentoItensSemOrgao, String labelSituacaoParecer, String labelCorParecer, 
    		String labelSituacaoListaPareceres, String financeiroDescValor1Cfg, String financeiroDescValor2Cfg, 
    		String financeiroDescValor3Cfg, String financeiroDescValor4Cfg, String financeiroDescValor5Cfg, 
    		String financeiroDescValor6Cfg, SisGrupoAtributoSga sisGrupoAtributoSgaTipoPontoCritico, 
    	   	SisGrupoAtributoSga sisGrupoAtributoSgaByUnidMedida, 
    	   	SisGrupoAtributoSga sisGrupoAtributoSgaByTipoEvento, String uploadIntegracao, 
    	   	String recursoDescValor1Cfg, String recursoDescValor2Cfg, String recursoDescValor3Cfg, 
    	   	Long periodoPadrao, String indExibirArvoreNavegacaoCfg, VisaoDemandasVisDem visaoDemandasVisDem) {
        this.nomeEstruturaCfg = nomeEstruturaCfg;
        this.tpArqIntegFinanceiraCfg = tpArqIntegFinanceiraCfg;
        this.indGerarHistoricoCfg = indGerarHistoricoCfg;
        this.sisAtributoSatbByCodSacapa = sisAtributoSatbByCodSacapa;
        this.sisAtributoSatbByCodSapadrao = sisAtributoSatbByCodSapadrao;
        this.sisGrupoAtributoSgaByCodSgaGrAtrPgIni = sisGrupoAtributoSgaByCodSgaGrAtrPgIni;
        this.sisGrupoAtributoSgaByCodSgaGrAtrNvPlan = sisGrupoAtributoSgaByCodSgaGrAtrNvPlan;
        this.sisGrupoAtributoSgaByCodSgaGrAtrClAcesso = sisGrupoAtributoSgaByCodSgaGrAtrClAcesso;
        this.sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa = sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa;
        this.sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso = sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso;
        this.sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas = sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas;
        this.sisGrupoAtributoSgaByUnidMedida = sisGrupoAtributoSgaByUnidMedida;
        this.sisGrupoAtributoSgaByTipoEvento= sisGrupoAtributoSgaByTipoEvento;
        this.periodicidadePrdc = periodicidadePrdc;
        this.sisAtributoSatbByCodSaAcesso = sisAtributoSatbByCodSaAcesso;
        this.diasAntecedenciaMailCfg = diasAntecedenciaMailCfg;
        this.horaEnvioMailCfg = horaEnvioMailCfg;
        this.cfgAbas = cfgAbas;
        this.configMailCfgms = configMailCfgms;
        this.tituloSistema = tituloSistema;
    	this.emailServer = emailServer;
    	this.estilo = estilo;
    	this.imagemEsquerda = imagemEsquerda;
    	this.raizUpload = raizUpload;
    	this.uploadCategoria = uploadCategoria;
    	this.uploadAnexos = uploadAnexos;
    	this.uploadEmpresa = uploadEmpresa;
    	this.uploadAdmPortal = uploadAdmPortal;
    	this.uploadUsuarios = uploadUsuarios;
    	this.uploadIconeLinks = uploadIconeLinks;
    	this.numRegistros = numRegistros;
    	this.qtdeItensGalAnexo = qtdeItensGalAnexo;
    	this.nuItensExibidosPaginacao = nuItensExibidosPaginacao;
    	this.periodoRevisaoPrevs =  periodoRevisaoPrevs;
    	this.exibDefaultEstCfg = exibDefaultEstCfg;
    	this.ultimaAtualizacaoEmail = ultimaAtualizacaoEmail;
    	this.intervaloAtualizacaoEmail = intervaloAtualizacaoEmail;
    	this.ultimoEnvioEmailCfg = ultimoEnvioEmailCfg;
    	this.labelMonitorado = labelMonitorado;
    	this.labelOrgao = labelOrgao;
    	this.labelAgrupamentoItensSemOrgao = labelAgrupamentoItensSemOrgao;
    	this.labelSituacaoParecer = labelSituacaoParecer;
    	this.labelCorParecer = labelCorParecer;
    	this.labelSituacaoListaPareceres = labelSituacaoListaPareceres; 
    	this.financeiroDescValor1Cfg = financeiroDescValor1Cfg;
    	this.financeiroDescValor2Cfg = financeiroDescValor2Cfg;
    	this.financeiroDescValor3Cfg = financeiroDescValor3Cfg;
    	this.financeiroDescValor4Cfg = financeiroDescValor4Cfg;
    	this.financeiroDescValor5Cfg = financeiroDescValor5Cfg;
    	this.financeiroDescValor6Cfg = financeiroDescValor6Cfg;
    	this.sisGrupoAtributoSgaTipoPontoCritico = sisGrupoAtributoSgaTipoPontoCritico;
    	this.uploadIntegracao = uploadIntegracao;
    	this.recursoDescValor1Cfg = recursoDescValor1Cfg;
    	this.recursoDescValor2Cfg = recursoDescValor2Cfg;
    	this.recursoDescValor3Cfg = recursoDescValor3Cfg;
    	this.periodoPadrao = periodoPadrao;
    	this.indExibirArvoreNavegacaoCfg = indExibirArvoreNavegacaoCfg;
    	this.visaoDemandasVisDem = visaoDemandasVisDem;
    }

	/** default constructor */
    public ConfiguracaoCfg() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @author N/C
	 * @since N/C 
     * @param sisAtributoSatbByCodSacapa
     * @param sisAtributoSatbByCodSapadrao 
     * @param sisGrupoAtributoSgaByCodSgaGrAtrNvPlan
     * @param sisGrupoAtributoSgaByCodSgaGrAtrPgIni
     * @param periodicidadePrdc
     * @param sisGrupoAtributoSgaByCodSgaGrAtrClAcesso
     * @param sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa
     * @param sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso
     * @param sisAtributoSatbByCodSaAcesso
     * @param cfgAbas
     * @param sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas
     * @param configMailCfgms
     * @param sisGrupoAtributoSgaTipoPontoCritico
     * @param periodoRevisaoPrevs
     */
    public ConfiguracaoCfg(ecar.pojo.SisAtributoSatb sisAtributoSatbByCodSacapa, ecar.pojo.SisAtributoSatb sisAtributoSatbByCodSapadrao, ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrPgIni, ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrNvPlan, ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrClAcesso, ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa, ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso, ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas, ecar.pojo.PeriodicidadePrdc periodicidadePrdc,
    		SisAtributoSatb sisAtributoSatbByCodSaAcesso, Set cfgAbas, Set configMailCfgms, Set periodoRevisaoPrevs,SisGrupoAtributoSga sisGrupoAtributoSgaTipoPontoCritico) {
        this.sisAtributoSatbByCodSacapa = sisAtributoSatbByCodSacapa;
        this.sisAtributoSatbByCodSapadrao = sisAtributoSatbByCodSapadrao;
        this.sisGrupoAtributoSgaByCodSgaGrAtrPgIni = sisGrupoAtributoSgaByCodSgaGrAtrPgIni;
        this.sisGrupoAtributoSgaByCodSgaGrAtrNvPlan = sisGrupoAtributoSgaByCodSgaGrAtrNvPlan;
        this.sisGrupoAtributoSgaByCodSgaGrAtrClAcesso = sisGrupoAtributoSgaByCodSgaGrAtrClAcesso;
        this.sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa = sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa;
        this.sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso = sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso;
        this.sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas = sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas;
        this.periodicidadePrdc = periodicidadePrdc;
        this.sisAtributoSatbByCodSaAcesso = sisAtributoSatbByCodSaAcesso;
        this.cfgAbas = cfgAbas;
        this.configMailCfgms = configMailCfgms;
        this.periodoRevisaoPrevs = periodoRevisaoPrevs;
        this.sisGrupoAtributoSgaTipoPontoCritico = sisGrupoAtributoSgaTipoPontoCritico;
     }
    
    public ConfiguracaoCfg(String nomeEstiloCss, String tituloSistema)
    {
    	setEstilo(new Estilo());
    	getEstilo().setNome(nomeEstiloCss);
    	this.tituloSistema = tituloSistema;
    }

    /**
     *
     * @return
     */
    public String getLabelMonitorado() {
		return labelMonitorado;
	}

    /**
     *
     * @param labelMonitorado
     */
    public void setLabelMonitorado(String labelMonitorado) {
		this.labelMonitorado = labelMonitorado;
	}

    /**
     *
     * @return
     */
    public String getLabelOrgao() {
	return labelOrgao;
	}

    /**
     *
     * @param labelOrgao
     */
    public void setLabelOrgao(String labelOrgao) {
	this.labelOrgao = labelOrgao;
	}
        
    /**
     * 
     * @return
     */
    public String getLabelAgrupamentoItensSemOrgao() {
		return labelAgrupamentoItensSemOrgao;
	}

    /**
     * 
     * @param labelAgrupamentoItensSemOrgao
     */
	public void setLabelAgrupamentoItensSemOrgao(
			String labelAgrupamentoItensSemOrgao) {
		this.labelAgrupamentoItensSemOrgao = labelAgrupamentoItensSemOrgao;
	}
	
	/**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getLabelSituacaoParecer() {
		return labelSituacaoParecer;
	}

	/**
         * @param labelSituacaoParecer
         * @author N/C
	 * @since N/C 
     */
	public void setLabelSituacaoParecer(String labelSituacaoParecer) {
		this.labelSituacaoParecer = labelSituacaoParecer;
	}

	/**
     * @author N/C
	 * @since N/C 
     * @return String
     */
	public String getLabelCorParecer() {
		return labelCorParecer;
	}
	
	/**
         * @param labelCorParecer 
         * @author N/C
	 * @since N/C 
     */
	public void setLabelCorParecer(String labelCorParecer) {
		this.labelCorParecer = labelCorParecer;
	}
	
	/**
     * @author N/C
	 * @since N/C 
     * @return String
     */
	public String getLabelSituacaoListaPareceres() {
		return labelSituacaoListaPareceres;
	}
	
	/**
         * @param labelSituacaoListaPareceres
         * @author N/C
	 * @since N/C 
     */
	public void setLabelSituacaoListaPareceres(String labelSituacaoListaPareceres) {
		this.labelSituacaoListaPareceres = labelSituacaoListaPareceres;
	}
	

	/**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getCodCfg() {
        return this.codCfg;
    }

    /**
     * @param codCfg
     * @author N/C
	 * @since N/C 
     */
    public void setCodCfg(Long codCfg) {
        this.codCfg = codCfg;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getNomeEstruturaCfg() {
        return this.nomeEstruturaCfg;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param nomeEstruturaCfg
     */
    public void setNomeEstruturaCfg(String nomeEstruturaCfg) {
        this.nomeEstruturaCfg = nomeEstruturaCfg;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getTpArqIntegFinanceiraCfg() {
        return this.tpArqIntegFinanceiraCfg;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param tpArqIntegFinanceiraCfg
     */
    public void setTpArqIntegFinanceiraCfg(String tpArqIntegFinanceiraCfg) {
        this.tpArqIntegFinanceiraCfg = tpArqIntegFinanceiraCfg;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndGerarHistoricoCfg() {
        return this.indGerarHistoricoCfg;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param indGerarHistoricoCfg
     */
    public void setIndGerarHistoricoCfg(String indGerarHistoricoCfg) {
        this.indGerarHistoricoCfg = indGerarHistoricoCfg;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return car.pojo.SisAtributoSatb
     */
    public ecar.pojo.SisAtributoSatb getSisAtributoSatbByCodSacapa() {
        return this.sisAtributoSatbByCodSacapa;
    }

    /**
     * @param sisAtributoSatbByCodSacapa
     * @author N/C
	 * @since N/C 
     */
    public void setSisAtributoSatbByCodSacapa(ecar.pojo.SisAtributoSatb sisAtributoSatbByCodSacapa) {
        this.sisAtributoSatbByCodSacapa = sisAtributoSatbByCodSacapa;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getRecursoDescValor1Cfg() {
		return recursoDescValor1Cfg;
	}

    /**
     * @author N/C
	 * @since N/C 
     * @param recursoDescValor1Cfg
     */
	public void setRecursoDescValor1Cfg(String recursoDescValor1Cfg) {
		this.recursoDescValor1Cfg = recursoDescValor1Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getRecursoDescValor2Cfg() {
		return recursoDescValor2Cfg;
	}

	/**
         * @param recursoDescValor2Cfg
         * @author N/C
	 * @since N/C 
	 */
	public void setRecursoDescValor2Cfg(String recursoDescValor2Cfg) {
		this.recursoDescValor2Cfg = recursoDescValor2Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getRecursoDescValor3Cfg() {
		return recursoDescValor3Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param recursoDescValor3Cfg
	 */
	public void setRecursoDescValor3Cfg(String recursoDescValor3Cfg) {
		this.recursoDescValor3Cfg = recursoDescValor3Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return ecar.pojo.SisAtributoSatb
	 */
	public ecar.pojo.SisAtributoSatb getSisAtributoSatbByCodSapadrao() {
        return this.sisAtributoSatbByCodSapadrao;
    }

	/**
	 * @author N/C
	 * @since N/C 
         * @param sisAtributoSatbByCodSapadrao
	 */
    public void setSisAtributoSatbByCodSapadrao(ecar.pojo.SisAtributoSatb sisAtributoSatbByCodSapadrao) {
        this.sisAtributoSatbByCodSapadrao = sisAtributoSatbByCodSapadrao;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.SisGrupoAtributoSga
     */
    public ecar.pojo.SisGrupoAtributoSga getSisGrupoAtributoSgaByCodSgaGrAtrPgIni() {
        return this.sisGrupoAtributoSgaByCodSgaGrAtrPgIni;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param sisGrupoAtributoSgaByCodSgaGrAtrPgIni
     */
    public void setSisGrupoAtributoSgaByCodSgaGrAtrPgIni(ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrPgIni) {
        this.sisGrupoAtributoSgaByCodSgaGrAtrPgIni = sisGrupoAtributoSgaByCodSgaGrAtrPgIni;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.SisGrupoAtributoSga
     */
    public ecar.pojo.SisGrupoAtributoSga getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan() {
        return this.sisGrupoAtributoSgaByCodSgaGrAtrNvPlan;
    }

    /**
     * @param sisGrupoAtributoSgaByCodSgaGrAtrNvPlan
     * @author N/C
	 * @since N/C 
     */
    public void setSisGrupoAtributoSgaByCodSgaGrAtrNvPlan(ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrNvPlan) {
        this.sisGrupoAtributoSgaByCodSgaGrAtrNvPlan = sisGrupoAtributoSgaByCodSgaGrAtrNvPlan;
    }

    /**
     * 
     * @return ecar.pojo.SisGrupoAtributoSga
     */
    public ecar.pojo.SisGrupoAtributoSga getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso() {
        return this.sisGrupoAtributoSgaByCodSgaGrAtrClAcesso;
    }

    /**
     * @param sisGrupoAtributoSgaByCodSgaGrAtrClAcesso
     * @author N/C
	 * @since N/C 
     */
    public void setSisGrupoAtributoSgaByCodSgaGrAtrClAcesso(ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrClAcesso) {
        this.sisGrupoAtributoSgaByCodSgaGrAtrClAcesso = sisGrupoAtributoSgaByCodSgaGrAtrClAcesso;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.SisGrupoAtributoSga
     */
    public ecar.pojo.SisGrupoAtributoSga getSisGrupoAtributoSgaByCodSgaGrAtrLeiCapa() {
        return this.sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa
     */
    public void setSisGrupoAtributoSgaByCodSgaGrAtrLeiCapa(ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa) {
        this.sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa = sisGrupoAtributoSgaByCodSgaGrAtrLeiCapa;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.SisGrupoAtributoSga
     */
    public ecar.pojo.SisGrupoAtributoSga getSisGrupoAtributoSgaByCodSgaGrAtrTpAcesso() {
        return this.sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso
     */
    public void setSisGrupoAtributoSgaByCodSgaGrAtrTpAcesso(ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso) {
        this.sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso = sisGrupoAtributoSgaByCodSgaGrAtrTpAcesso;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.PeriodicidadePrdc
     */
    public ecar.pojo.PeriodicidadePrdc getPeriodicidadePrdc() {
        return this.periodicidadePrdc;
    }

    /**
     * @param periodicidadePrdc
     * @author N/C
	 * @since N/C 
     */
    public void setPeriodicidadePrdc(ecar.pojo.PeriodicidadePrdc periodicidadePrdc) {
        this.periodicidadePrdc = periodicidadePrdc;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.SisGrupoAtributoSga
     */
    public ecar.pojo.SisGrupoAtributoSga getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() {
		return sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas;
	}
    

    /**
     * @param sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas
     * @author N/C
	 * @since N/C 
     */
	public void setSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas(
			ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas) {
		this.sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas = sisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
    @Override
	public String toString() {
        return new ToStringBuilder(this)
            .append("codCfg", getCodCfg())
            .toString();
    }

	/**
	 * @author N/C
	 * @since N/C 
         * @param other
	 * @return boolean
	 */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ConfiguracaoCfg) ) return false;
        ConfiguracaoCfg castOther = (ConfiguracaoCfg) other;
        return new EqualsBuilder()
            .append(this.getCodCfg(), castOther.getCodCfg())
            .isEquals();
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodCfg())
            .toHashCode();
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
	public Set getConfigMailCfgms() {
		return configMailCfgms;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param configMailCfgms
	 */
	public void setConfigMailCfgms(Set configMailCfgms) {
		this.configMailCfgms = configMailCfgms;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Set
	 */
	public Set getCfgAbas() {
		return cfgAbas;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param cfgAbas
	 */
	public void setCfgAbas(Set cfgAbas) {
		this.cfgAbas = cfgAbas;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Long
	 */
	public Long getDiasAntecedenciaMailCfg() {
		return diasAntecedenciaMailCfg;
	}

	/**
         * @param diasAntecedenciaMailCfg
         * @author N/C
	 * @since N/C 
	 */
	public void setDiasAntecedenciaMailCfg(Long diasAntecedenciaMailCfg) {
		this.diasAntecedenciaMailCfg = diasAntecedenciaMailCfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getHoraEnvioMailCfg() {
		return horaEnvioMailCfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param horaEnvioMailCfg
	 */
	public void setHoraEnvioMailCfg(String horaEnvioMailCfg) {
		this.horaEnvioMailCfg = horaEnvioMailCfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return ecar.pojo.SisAtributoSatb
	 */
	public ecar.pojo.SisAtributoSatb getSisAtributoSatbByCodSaAcesso() {
		return sisAtributoSatbByCodSaAcesso;
	}

	/**
         * @param sisAtributoSatbByCodSaAcesso
         * @author N/C
	 * @since N/C 
	 */
	public void setSisAtributoSatbByCodSaAcesso(
			ecar.pojo.SisAtributoSatb sisAtributoSatbByCodSaAcesso) {
		this.sisAtributoSatbByCodSaAcesso = sisAtributoSatbByCodSaAcesso;
	}

	/**
         * @param tituloSistema
         * @author N/C
	 * @since N/C 
	 */
	public void setTituloSistema(String tituloSistema) {
		this.tituloSistema = tituloSistema;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getTituloSistema() {
		return tituloSistema;
	}

	/**
         * @param emailServer
         * @author N/C
	 * @since N/C 
	 */
	public void setEmailServer(String emailServer) {
		this.emailServer = emailServer;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getEmailServer() {
		return emailServer;
	}

	/**
         * @param imagemEsquerda
         * @author N/C
	 * @since N/C 
	 */
	public void setImagemEsquerda(String imagemEsquerda) {
		this.imagemEsquerda = imagemEsquerda;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getImagemEsquerda() {
		return imagemEsquerda;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param raizUpload
	 */
	public void setRaizUpload(String raizUpload) {
		this.raizUpload = raizUpload;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getRaizUpload() {
		return raizUpload;
	}

	/**
         * @param uploadCategoria
         * @author N/C
	 * @since N/C 
	 */
	public void setUploadCategoria(String uploadCategoria) {
		this.uploadCategoria = uploadCategoria;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getUploadCategoria() {
		return uploadCategoria;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param uploadAnexos
	 */
	public void setUploadAnexos(String uploadAnexos) {
		this.uploadAnexos = uploadAnexos;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getUploadAnexos() {
		return uploadAnexos;
	}

	/**
         * @param uploadEmpresa
         * @author N/C
	 * @since N/C 
	 */
	public void setUploadEmpresa(String uploadEmpresa) {
		this.uploadEmpresa = uploadEmpresa;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getUploadEmpresa() {
		return uploadEmpresa;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param uploadAdmPortal
	 */
	public void setUploadAdmPortal(String uploadAdmPortal) {
		this.uploadAdmPortal = uploadAdmPortal;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getUploadAdmPortal() {
		return uploadAdmPortal;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param uploadUsuarios
	 */
	public void setUploadUsuarios(String uploadUsuarios) {
		this.uploadUsuarios = uploadUsuarios;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getUploadUsuarios() {
		return uploadUsuarios;
	}
	
	/**
	 * @author N/C
	 * @since N/C
	 * @return String
	 */
	public String getUploadIconeLinks() {
		return uploadIconeLinks;
	}

	/**
	 * @author N/C
	 * @since N/C
         * @param uploadIconeLinks
	 */
	public void setUploadIconeLinks(String uploadIconeLinks) {
		this.uploadIconeLinks = uploadIconeLinks;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param numRegistros
	 */
	public void setNumRegistros(Long numRegistros) {
		this.numRegistros = numRegistros;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Long
	 */
	public Long getNumRegistros() {
		return numRegistros;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param qtdeItensGalAnexo
	 */
	public void setQtdeItensGalAnexo(Long qtdeItensGalAnexo) {
		this.qtdeItensGalAnexo = qtdeItensGalAnexo;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Long
	 */
	public Long getQtdeItensGalAnexo() {
		return qtdeItensGalAnexo;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Set
	 */
	public Set getPeriodoRevisaoPrevs() {
		return periodoRevisaoPrevs;
	}

	/**
         * @param periodoRevisaoPrevs
         * @author N/C
	 * @since N/C 
	 */
	public void setPeriodoRevisaoPrevs(Set periodoRevisaoPrevs) {
		this.periodoRevisaoPrevs = periodoRevisaoPrevs;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getExibDefaultEstCfg() {
		return exibDefaultEstCfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param exibDefaultEstCfg
	 */
	public void setExibDefaultEstCfg(String exibDefaultEstCfg) {
		this.exibDefaultEstCfg = exibDefaultEstCfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Date
	 */
	public Date getUltimaAtualizacaoEmail() {
		return ultimaAtualizacaoEmail;
	}

	/**
         * @param ultimaAtualizacaoEmail
         * @author N/C
	 * @since N/C 
	 */
	public void setUltimaAtualizacaoEmail(Date ultimaAtualizacaoEmail) {
		this.ultimaAtualizacaoEmail = ultimaAtualizacaoEmail;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Long
	 */
	public Long getIntervaloAtualizacaoEmail() {
		return intervaloAtualizacaoEmail;
	}

	/**
         * @param intervaloAtualizacaoEmail
         * @author N/C
	 * @since N/C 
	 */
	public void setIntervaloAtualizacaoEmail(Long intervaloAtualizacaoEmail) {
		this.intervaloAtualizacaoEmail = intervaloAtualizacaoEmail;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Date
	 */
	public Date getUltimoEnvioEmailCfg() {
		return ultimoEnvioEmailCfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param ultimoEnvioEmailCfg
	 */
	public void setUltimoEnvioEmailCfg(Date ultimoEnvioEmailCfg) {
		this.ultimoEnvioEmailCfg = ultimoEnvioEmailCfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getFinanceiroDescValor1Cfg() {
		return financeiroDescValor1Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param financeiroDescValor1Cfg
	 */
	public void setFinanceiroDescValor1Cfg(String financeiroDescValor1Cfg) {
		this.financeiroDescValor1Cfg = financeiroDescValor1Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getFinanceiroDescValor2Cfg() {
		return financeiroDescValor2Cfg;
	}

	/**
         * @param financeiroDescValor2Cfg
         * @author N/C
	 * @since N/C 
	 */
	public void setFinanceiroDescValor2Cfg(String financeiroDescValor2Cfg) {
		this.financeiroDescValor2Cfg = financeiroDescValor2Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getFinanceiroDescValor3Cfg() {
		return financeiroDescValor3Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param financeiroDescValor3Cfg
	 */
	public void setFinanceiroDescValor3Cfg(String financeiroDescValor3Cfg) {
		this.financeiroDescValor3Cfg = financeiroDescValor3Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getFinanceiroDescValor4Cfg() {
		return financeiroDescValor4Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param financeiroDescValor4Cfg
	 */
	public void setFinanceiroDescValor4Cfg(String financeiroDescValor4Cfg) {
		this.financeiroDescValor4Cfg = financeiroDescValor4Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getFinanceiroDescValor5Cfg() {
		return financeiroDescValor5Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param financeiroDescValor5Cfg
	 */
	public void setFinanceiroDescValor5Cfg(String financeiroDescValor5Cfg) {
		this.financeiroDescValor5Cfg = financeiroDescValor5Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Long
	 */
	public Long getPeriodoPadrao() {
		return periodoPadrao;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param periodoPadrao
	 */
	public void setPeriodoPadrao(Long periodoPadrao) {
		this.periodoPadrao = periodoPadrao;
	}
	
	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getFinanceiroDescValor6Cfg() {
		return financeiroDescValor6Cfg;
	}

	/**
         * @param financeiroDescValor6Cfg
         * @author N/C
	 * @since N/C 
	 */
	public void setFinanceiroDescValor6Cfg(String financeiroDescValor6Cfg) {
		this.financeiroDescValor6Cfg = financeiroDescValor6Cfg;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return ecar.pojo.SisGrupoAtributoSga
	 */
	public ecar.pojo.SisGrupoAtributoSga getSisGrupoAtributoSgaTipoPontoCritico() {
		return sisGrupoAtributoSgaTipoPontoCritico;
	}

	/**
         * @param sisGrupoAtributoSgaTipoPontoCritico
         * @author N/C
	 * @since N/C 
	 */
	public void setSisGrupoAtributoSgaTipoPontoCritico(
			ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSgaTipoPontoCritico) {
		this.sisGrupoAtributoSgaTipoPontoCritico = sisGrupoAtributoSgaTipoPontoCritico;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getUploadIntegracao() {
		return uploadIntegracao;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param uploadIntegracao
	 */
	public void setUploadIntegracao(String uploadIntegracao) {
		this.uploadIntegracao = uploadIntegracao;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param contextPath
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

        /**
         *
         * @return
         */
        public SisGrupoAtributoSga getSisGrupoAtributoSgaByUnidMedida() {
		return sisGrupoAtributoSgaByUnidMedida;
	}

        /**
         *
         * @param sisGrupoAtributoSgaByUnidMedida
         */
        public void setSisGrupoAtributoSgaByUnidMedida(
			SisGrupoAtributoSga sisGrupoAtributoSgaByUnidMedida) {
		this.sisGrupoAtributoSgaByUnidMedida = sisGrupoAtributoSgaByUnidMedida;
	}

        /**
         *
         * @return
         */
        public SisGrupoAtributoSga getSisGrupoAtributoSgaByTipoEvento() {
		return sisGrupoAtributoSgaByTipoEvento;
	}

        /**
         *
         * @param sisGrupoAtributoSgaByTipoEvento
         */
        public void setSisGrupoAtributoSgaByTipoEvento(
			SisGrupoAtributoSga sisGrupoAtributoSgaByTipoEvento) {
		this.sisGrupoAtributoSgaByTipoEvento = sisGrupoAtributoSgaByTipoEvento;
	}

        /**
         *
         * @return
         */
        public int getNuItensExibidosPaginacao() {
		return nuItensExibidosPaginacao;
	}

        /**
         *
         * @param nuItensExibidosPaginacao
         */
        public void setNuItensExibidosPaginacao(int nuItensExibidosPaginacao) {
		this.nuItensExibidosPaginacao = nuItensExibidosPaginacao;
	}

        /**
         *
         * @return
         */
        public String getIndOcultarObservacoesParecer() {
		return indOcultarObservacoesParecer;
	}

        /**
         *
         * @param indOcultarObservacoesParecer
         */
        public void setIndOcultarObservacoesParecer(String indOcultarObservacoesParecer) {
		this.indOcultarObservacoesParecer = indOcultarObservacoesParecer;
	}
			
        /**
         *
         * @return
         */
        public String getIndExibirSituacoesFormatoAbas() {
		return indExibirSituacoesFormatoAbas;
	}

        /**
         *
         * @param indExibirSituacoesFormatoAbas
         */
        public void setIndExibirSituacoesFormatoAbas(String indExibirSituacoesFormatoAbas) {
		this.indExibirSituacoesFormatoAbas = indExibirSituacoesFormatoAbas;
	}

        /**
         *
         * @return
         */
        public String getUploadExportacaoDemandas() {
		return uploadExportacaoDemandas;
	}

        /**
         *
         * @param uploadExportacaoDemandas
         */
        public void setUploadExportacaoDemandas(String uploadExportacaoDemandas) {
		this.uploadExportacaoDemandas = uploadExportacaoDemandas;
	}

        /**
         *
         * @return
         */
        public String getSeparadorArqTXT() {
		return separadorArqTXT;
	}

        /**
         *
         * @param separadorArqTXT
         */
        public void setSeparadorArqTXT(String separadorArqTXT) {
		this.separadorArqTXT = separadorArqTXT;
	}

        /**
         *
         * @return
         */
        public Estilo getEstilo() {
		return estilo;
	}

        /**
         *
         * @param estilo
         */
        public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}
        /**
         *
         * @return
         */
        public String getSeparadorCampoMultivalor() {
		return separadorCampoMultivalor;
	}

        /**
         *
         * @param separadorCampoMultivalor
         */
        public void setSeparadorCampoMultivalor(String separadorCampoMultivalor) {
		this.separadorCampoMultivalor = separadorCampoMultivalor;
	}

        /**
         *
         * @param tempoSessao
         */
        public void setTempoSessao(Long tempoSessao) {
		this.tempoSessao = tempoSessao;
	}

        /**
         *
         * @return
         */
        public Long getTempoSessao() {
		return tempoSessao;
	}
	
        /**
         *
         * @return
         */
        public String getIndExibirArvoreNavegacaoCfg() {
		return indExibirArvoreNavegacaoCfg;
	}

        /**
         *
         * @param indExibirArvoreNavegacaoCfg
         */
        public void setIndExibirArvoreNavegacaoCfg(String indExibirArvoreNavegacaoCfg) {
		this.indExibirArvoreNavegacaoCfg = indExibirArvoreNavegacaoCfg;
	}
	
    public VisaoDemandasVisDem getVisaoDemandasVisDem() {
		return visaoDemandasVisDem;
	}

	public void setVisaoDemandasVisDem(VisaoDemandasVisDem visaoDemandasVisDem) {
		this.visaoDemandasVisDem = visaoDemandasVisDem;
	}
}
