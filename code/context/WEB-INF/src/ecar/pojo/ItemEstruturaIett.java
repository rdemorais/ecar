package ecar.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import ecar.intercambioDados.BusinessIntercambioDadosObject;
import ecar.util.Dominios;


/** 
 * 
 * Esta classe representa os itens cadastrados na estrutura. Cada item est� relacionado a uma {@link EstruturaEtt}.
 * 
 * No inicio do desenvolvimento, os atributos de neg�cio entravam nesta classe da forma tradicional, ou seja,
 * como um c�digo (private String talcoisa;), mas com o aumento da demanda, optou-se pela ado��o de uma estrutura mais din�mica
 * neste momento entra o conceito de Grupo de Atributo e atributo. Assim, os atributos que n�o est�o fisicamente nesta classe, est�o configurados
 * na classe {@link SisAtributoSatb} e aqui est� representada pelo itemEstruturaSisAtributoIettSatbs.
 * 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 02/06/2007
 */
public class ItemEstruturaIett extends BusinessIntercambioDadosObject implements 
	Cloneable, Serializable, Hierarchyable, IConfiguracaoAtributoLivre, Comparable<ItemEstruturaIett> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5404133613905404346L;

	/*
	 * Atributo para atender a demanda Atencao do Ministro do novo site - Bijari.
	 */
	private Boolean atencaoIett;
	
	/** identifier field */
    private Long codIett;

    /** nullable persistent field */
    private BigDecimal valPrevistoFuturoIett;

    /** nullable persistent field */
    private String indBloqPlanejamentoIett;

    /** nullable persistent field */
    private String beneficiosIett;

    /** nullable persistent field */
    private String origemIett;

    /** nullable persistent field */
    private String objetivoEspecificoIett;

    /** nullable persistent field */
    private String objetivoGeralIett;

    /** nullable persistent field */
    private String indMonitoramentoIett;

    /** nullable persistent field */
    private String indCriticaIett;

    /** nullable persistent field */
    private Date dataInicioMonitoramentoIett;

    /** nullable persistent field */
    private Date dataTerminoIett;

    /** nullable persistent field */
    private Date dataInicioIett;

    /** nullable persistent field */
    private String indAtivoIett;

    /** nullable persistent field */
    private Date dataUltManutencaoIett;

    /** nullable persistent field */
    private Date dataInclusaoIett;

    /** nullable persistent field */
    private String descricaoIett;

    /** nullable persistent field */
    private String siglaIett;

    /** nullable persistent field */
    private String descricaoR5;

    /** nullable persistent field */
    private String descricaoR4;

    /** nullable persistent field */
    private String descricaoR3;

    /** nullable persistent field */
    private String descricaoR2;

    /** nullable persistent field */
    private String descricaoR1;

    /** nullable persistent field */
    private Date dataR5;

    /** nullable persistent field */
    private Date dataR4;

    /** nullable persistent field */
    private Date dataR3;

    /** nullable persistent field */
    private Date dataR2;

    /** nullable persistent field */
    private Date dataR1;
    
    /** nullable persistent field */
    private Integer nivelIett;

    private String nomeIett;
    
    private String indModeloIett;

    /** persistent field */
    private ecar.pojo.EstruturaEtt estruturaEtt;

    /** persistent field */
    private ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iett;

    /** persistent field */
    private ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel2Iett;

    /** persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** persistent field */
    private ecar.pojo.SubAreaSare subAreaSare;

    /** persistent field */
    private ecar.pojo.AreaAre areaAre;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsuByCodUsuUltManutIett;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsuByCodUsuIncIett;

    /** persistent field */
    private ecar.pojo.PeriodicidadePrdc periodicidadePrdc;

    /** persistent field */
    private ecar.pojo.UnidadeOrcamentariaUO unidadeOrcamentariaUO;

    /** persistent field */
    private Set efItemEstContaEfiecs;

    /** persistent field */
    private Set itemEstrutEntidadeIettes;

    /** persistent field */
    private Set acompReferenciaItemAris;

    /** persistent field */
    private Set itemEstUsutpfuacIettutfas;

    /** persistent field */
    private Set itemEstruturaNivelIettns;

    /** persistent field */
    private Set itemEstrUplCategIettucs;

    /** persistent field */
    private Set itemEstrtBenefIettbs;

    /** persistent field */
    private Set itemEstrutCriterioIettcs;

    /** persistent field */
    private Set itemEstrutUsuarioIettusesByCodIett;

	/** persistent field */
    private Set itemEstrutUsuarioIettusesByCodIettOrigem;

    /** persistent field */
    private Set acompRealFisicoArfs;

    /** persistent field */
    private Set apontamentoApts;

    /** persistent field */
    private Set itemEstrutLocalIettls;

    /** persistent field */
    private Set itemEstruturaIetts;

    /** persistent field */
    private Set efItemEstPrevisaoEfieps;

    /** persistent field */
    private Set itemEstrutVinculoIettvs;

    /** persistent field */
    private Set pontoCriticoPtcs;

    /** persistent field */
    private Set itemEstrtIndResulIettrs;

    /** persistent field */
    private Set itemEstrutMarcadorIettms;

    /** persistent field */
    private Set itemEstrutAcaoIettas;

    /** persistent field */
    private Set itemEstrutUploadIettups;
    
    private Set efIettFonteTotEfiefts;

    /** persistent field */
    private SituacaoSit situacaoSit;
    
    /** persistent field */
    private Set itemRegdemandaIregds;  
    
    private Set itemEstruturarevisaoIettrevs;
    
    private Set itemEstruturaSisAtributoIettSatbs;
    
    private Set agendaAge;
    
    private Set estruturasVirtual;
    private Set pesquisas; 
    
    private Set relacionamentosIettEtiqueta;
    
    private Set relIettEtiqueta;
    
    /**
     *
     * @return
     */
    public Set<EstruturaEtt> getEstruturasVirtual() {
		return estruturasVirtual;
	}

    /**
     *
     * @param estruturasVirtual
     */
    public void setEstruturasVirtual(Set estruturasVirtual) {
		this.estruturasVirtual = estruturasVirtual;
	}

	/* -- Mantis #2156 -- */
    private Set historicoIettHs;
    private Set historicoIettHpais;
    private Set historicoIettaHs;
    private Set historicoIettbHs;
    private Set historicoIettcHs;
    private Set historicoIetteHs;
    private Set historicoIettlHs;
    private Set historicoIettrHs;
    private Set historicoIettupHs;
    private Set historicoIettusHorigs;
    private Set historicoIettusHs;
    private Set historicoIettutfaHs;
    private Set historicoEfiecHs;
    private Set historicoEfieftHs;
    private Set historicoEfiepHs;
    private Set historicoIettSatbHs;
    
    private Boolean indExclusaoPosHistorico;
    

    public Boolean getAtencaoIett() {
		return atencaoIett;
	}
    
    public void setAtencaoIett(Boolean atencaoIett) {
		this.atencaoIett = atencaoIett;
	}
    
    /**
     *
     * @return
     */
    public Set getHistoricoEfiepHs() {
		return historicoEfiepHs;
	}

    /**
     *
     * @param historicoEfiepHs
     */
    public void setHistoricoEfiepHs(Set historicoEfiepHs) {
		this.historicoEfiepHs = historicoEfiepHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoEfieftHs() {
		return historicoEfieftHs;
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
         * @param historicoEfieftHs
         */
        public void setHistoricoEfieftHs(Set historicoEfieftHs) {
		this.historicoEfieftHs = historicoEfieftHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoEfiecHs() {
		return historicoEfiecHs;
	}

        /**
         *
         * @param historicoEfiecHs
         */
        public void setHistoricoEfiecHs(Set historicoEfiecHs) {
		this.historicoEfiecHs = historicoEfiecHs;
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
        public Set getHistoricoIettrHs() {
		return historicoIettrHs;
	}

        /**
         *
         * @param historicoIettrHs
         */
        public void setHistoricoIettrHs(Set historicoIettrHs) {
		this.historicoIettrHs = historicoIettrHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIetteHs() {
		return historicoIetteHs;
	}

        /**
         *
         * @param historicoIetteHs
         */
        public void setHistoricoIetteHs(Set historicoIetteHs) {
		this.historicoIetteHs = historicoIetteHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettcHs() {
		return historicoIettcHs;
	}

        /**
         *
         * @param historicoIettcHs
         */
        public void setHistoricoIettcHs(Set historicoIettcHs) {
		this.historicoIettcHs = historicoIettcHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettaHs() {
		return historicoIettaHs;
	}

        /**
         *
         * @param historicoIettaHs
         */
        public void setHistoricoIettaHs(Set historicoIettaHs) {
		this.historicoIettaHs = historicoIettaHs;
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
         * @param nomeIett
         * @param valPrevistoFuturoIett
         * @param indBloqPlanejamentoIett
         * @param descricaoR1
         * @param origemIett
         * @param estruturaEtt
         * @param beneficiosIett
         * @param indMonitoramentoIett
         * @param objetivoGeralIett
         * @param efItemEstContaEfiecs
         * @param dataTerminoIett
         * @param dataInicioIett
         * @param dataInicioMonitoramentoIett
         * @param indCriticaIett
         * @param objetivoEspecificoIett
         * @param descricaoR5
         * @param siglaIett
         * @param dataInclusaoIett
         * @param dataR2
         * @param nivelIett
         * @param descricaoR3
         * @param descricaoR4
         * @param descricaoIett
         * @param dataUltManutencaoIett
         * @param indAtivoIett
         * @param acompRealFisicoArfs
         * @param dataR1
         * @param itemEstrutEntidadeIettes
         * @param itemEstrutMarcadorIettms
         * @param descricaoR2
         * @param itemEstrutAcaoIettas
         * @param unidadeOrcamentariaUO
         * @param itemEstrutLocalIettls
         * @param itemEstruturaIett
         * @param itemEstrUplCategIettucs
         * @param orgaoOrgByCodOrgaoResponsavel2Iett
         * @param apontamentoApts
         * @param dataR4
         * @param orgaoOrgByCodOrgaoResponsavel1Iett
         * @param itemEstrtIndResulIettrs
         * @param dataR5
         * @param itemEstruturaIetts
         * @param dataR3
         * @param itemEstrutUsuarioIettusesByCodIett
         * @param usuarioUsuByCodUsuUltManutIett
         * @param usuarioUsuByCodUsuIncIett
         * @param subAreaSare
         * @param itemEstruturaNivelIettns
         * @param itemEstUsutpfuacIettutfas
         * @param itemEstruturarevisaoIettrevs
         * @param agendaAge
         * @param pontoCriticoPtcs
         * @param efIettFonteTotEfiefts
         * @param itemEstrutCriterioIettcs
         * @param areaAre
         * @param periodicidadePrdc
         * @param itemEstrutUploadIettups
         * @param efItemEstPrevisaoEfieps
         * @param acompReferenciaItemAris
         * @param itemEstrutVinculoIettvs
         * @param itemEstrtBenefIettbs
         * @param situacaoSit
         * @param itemRegdemandaIregds
         * @param itemEstrutUsuarioIettusesByCodIettOrigem
         */
    public ItemEstruturaIett(String nomeIett, BigDecimal valPrevistoFuturoIett, String indBloqPlanejamentoIett, String beneficiosIett, 
    		String origemIett, String objetivoEspecificoIett, String objetivoGeralIett, String indMonitoramentoIett, String indCriticaIett, 
    		Date dataInicioMonitoramentoIett, Date dataTerminoIett, Date dataInicioIett, String indAtivoIett, Date dataUltManutencaoIett, 
    		Date dataInclusaoIett, String descricaoIett, String siglaIett, String descricaoR5, String descricaoR4, String descricaoR3, 
    		String descricaoR2, String descricaoR1, Date dataR5, Date dataR4, Date dataR3, Date dataR2, Date dataR1, 
    		ecar.pojo.EstruturaEtt estruturaEtt, ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iett, 
    		ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel2Iett, ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.SubAreaSare subAreaSare, 
    		ecar.pojo.AreaAre areaAre, ecar.pojo.UsuarioUsu usuarioUsuByCodUsuUltManutIett, ecar.pojo.UsuarioUsu usuarioUsuByCodUsuIncIett, 
    		ecar.pojo.PeriodicidadePrdc periodicidadePrdc, UnidadeOrcamentariaUO unidadeOrcamentariaUO, 
    		Set efItemEstContaEfiecs, Set itemEstrutEntidadeIettes, Set acompReferenciaItemAris, Set itemEstUsutpfuacIettutfas, 
    		Set itemEstruturaNivelIettns, Set itemEstrUplCategIettucs, Set itemEstrtBenefIettbs, Set itemEstrutCriterioIettcs, 
    		Set itemEstrutUsuarioIettusesByCodIett, 
    		Set itemEstrutUsuarioIettusesByCodIettOrigem, Set acompRealFisicoArfs, Set apontamentoApts, Set itemEstrutLocalIettls, 
    		Set itemEstruturaIetts, Set efItemEstPrevisaoEfieps, Set itemEstrutVinculoIettvs, 
    		Set pontoCriticoPtcs, Set itemEstrtIndResulIettrs, Set itemEstrutMarcadorIettms, Set itemEstrutAcaoIettas, 
    		Set itemEstrutUploadIettups, Set efIettFonteTotEfiefts, SituacaoSit situacaoSit, Set itemRegdemandaIregds,
    		Integer nivelIett, Set itemEstruturarevisaoIettrevs, Set agendaAge
    		
    ) {
        this.nomeIett = nomeIett;
        this.valPrevistoFuturoIett = valPrevistoFuturoIett;
        this.indBloqPlanejamentoIett = indBloqPlanejamentoIett;
        this.beneficiosIett = beneficiosIett;
        this.origemIett = origemIett;
        this.objetivoEspecificoIett = objetivoEspecificoIett;
        this.objetivoGeralIett = objetivoGeralIett;
        this.indMonitoramentoIett = indMonitoramentoIett;
        this.indCriticaIett = indCriticaIett;
        this.dataInicioMonitoramentoIett = dataInicioMonitoramentoIett;
        this.dataTerminoIett = dataTerminoIett;
        this.dataInicioIett = dataInicioIett;
        this.indAtivoIett = indAtivoIett;
        this.dataUltManutencaoIett = dataUltManutencaoIett;
        this.dataInclusaoIett = dataInclusaoIett;
        this.descricaoIett = descricaoIett;
        this.siglaIett = siglaIett;
        this.descricaoR5 = descricaoR5;
        this.descricaoR4 = descricaoR4;
        this.descricaoR3 = descricaoR3;
        this.descricaoR2 = descricaoR2;
        this.descricaoR1 = descricaoR1;
        this.dataR5 = dataR5;
        this.dataR4 = dataR4;
        this.dataR3 = dataR3;
        this.dataR2 = dataR2;
        this.dataR1 = dataR1;
        this.estruturaEtt = estruturaEtt;
        this.orgaoOrgByCodOrgaoResponsavel1Iett = orgaoOrgByCodOrgaoResponsavel1Iett;
        this.orgaoOrgByCodOrgaoResponsavel2Iett = orgaoOrgByCodOrgaoResponsavel2Iett;
        this.itemEstruturaIett = itemEstruturaIett;
        this.subAreaSare = subAreaSare;
        this.areaAre = areaAre;
        this.usuarioUsuByCodUsuUltManutIett = usuarioUsuByCodUsuUltManutIett;
        this.usuarioUsuByCodUsuIncIett = usuarioUsuByCodUsuIncIett;
        this.periodicidadePrdc = periodicidadePrdc;
        this.efItemEstContaEfiecs = efItemEstContaEfiecs;
        this.itemEstrutEntidadeIettes = itemEstrutEntidadeIettes;
        this.acompReferenciaItemAris = acompReferenciaItemAris;
        this.itemEstUsutpfuacIettutfas = itemEstUsutpfuacIettutfas;
        this.itemEstruturaNivelIettns = itemEstruturaNivelIettns;
        this.itemEstrUplCategIettucs = itemEstrUplCategIettucs;
        this.itemEstrtBenefIettbs = itemEstrtBenefIettbs;
        this.itemEstrutCriterioIettcs = itemEstrutCriterioIettcs;
        this.itemEstrutUsuarioIettusesByCodIett = itemEstrutUsuarioIettusesByCodIett;
		this.itemEstrutUsuarioIettusesByCodIettOrigem = itemEstrutUsuarioIettusesByCodIettOrigem;		
        this.acompRealFisicoArfs = acompRealFisicoArfs;
        this.apontamentoApts = apontamentoApts;
        this.itemEstrutLocalIettls = itemEstrutLocalIettls;
        this.itemEstruturaIetts = itemEstruturaIetts;
        this.efItemEstPrevisaoEfieps = efItemEstPrevisaoEfieps;
        this.itemEstrutVinculoIettvs = itemEstrutVinculoIettvs;
        this.pontoCriticoPtcs = pontoCriticoPtcs;
        this.itemEstrtIndResulIettrs = itemEstrtIndResulIettrs;
        this.itemEstrutMarcadorIettms = itemEstrutMarcadorIettms;
        this.itemEstrutAcaoIettas = itemEstrutAcaoIettas;
        this.itemEstrutUploadIettups = itemEstrutUploadIettups;
        this.efIettFonteTotEfiefts = efIettFonteTotEfiefts;
        this.unidadeOrcamentariaUO = unidadeOrcamentariaUO;
        this.situacaoSit = situacaoSit;
        this.itemRegdemandaIregds = itemRegdemandaIregds;
        this.nivelIett = nivelIett;
        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
        this.agendaAge = agendaAge;
    }
    
    /**
     * Construtor usado para mostrar as siglas dos objetivos estrat�gicos
     * no relat�rio de acompanhamento.
     * @param codIett
     * @param siglaIett
     */
    public ItemEstruturaIett(Long codIett, String siglaIett) {
    	this.codIett = codIett;
    	this.siglaIett = siglaIett;
    }

	/** default constructor */
    public ItemEstruturaIett() {
    }

    /** minimal constructor
     * @param estruturaEtt
     * @param orgaoOrgByCodOrgaoResponsavel1Iett
     * @param usuarioUsuByCodUsuIncIett
     * @param orgaoOrgByCodOrgaoResponsavel2Iett
     * @param itemEstruturaIett
     * @param subAreaSare
     * @param areaAre
     * @param itemEstrutAcaoIettas
     * @param pontoCriticoPtcs
     * @param itemEstruturarevisaoIettrevs
     * @param itemEstUsutpfuacIettutfas
     * @param periodicidadePrdc
     * @param efItemEstPrevisaoEfieps
     * @param acompReferenciaItemAris
     * @param efItemEstContaEfiecs
     * @param usuarioUsuByCodUsuUltManutIett
     * @param itemEstrutEntidadeIettes
     * @param itemEstruturaNivelIettns
     * @param itemRegdemandaIregds
     * @param itemEstrutUsuarioIettusesByCodIett
     * @param itemEstrutCriterioIettcs
     * @param itemEstruturaIetts
     * @param apontamentoApts
     * @param itemEstrutMarcadorIettms
     * @param itemEstrUplCategIettucs
     * @param itemEstrutLocalIettls
     * @param itemEstrutUsuarioIettusesByCodIettOrigem
     * @param acompRealFisicoArfs
     * @param itemEstrutVinculoIettvs
     * @param itemEstrutUploadIettups
     * @param itemEstrtBenefIettbs
     * @param itemEstrtIndResulIettrs
     */
    public ItemEstruturaIett(ecar.pojo.EstruturaEtt estruturaEtt, ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iett, ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel2Iett, ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.SubAreaSare subAreaSare, ecar.pojo.AreaAre areaAre, ecar.pojo.UsuarioUsu usuarioUsuByCodUsuUltManutIett, ecar.pojo.UsuarioUsu usuarioUsuByCodUsuIncIett, ecar.pojo.PeriodicidadePrdc periodicidadePrdc, Set efItemEstContaEfiecs, Set itemEstrutEntidadeIettes, Set acompReferenciaItemAris, Set itemEstUsutpfuacIettutfas, Set itemEstruturaNivelIettns, Set itemEstrUplCategIettucs, Set itemEstrtBenefIettbs, Set itemEstrutCriterioIettcs, Set itemEstrutUsuarioIettusesByCodIett, Set itemEstrutUsuarioIettusesByCodIettOrigem, Set acompRealFisicoArfs, Set apontamentoApts, Set itemEstrutLocalIettls, Set itemEstruturaIetts, Set efItemEstPrevisaoEfieps, Set itemEstrutVinculoIettvs, Set pontoCriticoPtcs, Set itemEstrtIndResulIettrs, Set itemEstrutMarcadorIettms, Set itemEstrutAcaoIettas, Set itemEstrutUploadIettups,
    		Set itemRegdemandaIregds, Set itemEstruturarevisaoIettrevs) {
        this.estruturaEtt = estruturaEtt;
        this.orgaoOrgByCodOrgaoResponsavel1Iett = orgaoOrgByCodOrgaoResponsavel1Iett;
        this.orgaoOrgByCodOrgaoResponsavel2Iett = orgaoOrgByCodOrgaoResponsavel2Iett;
        this.itemEstruturaIett = itemEstruturaIett;
        this.subAreaSare = subAreaSare;
        this.areaAre = areaAre;
        this.usuarioUsuByCodUsuUltManutIett = usuarioUsuByCodUsuUltManutIett;
        this.usuarioUsuByCodUsuIncIett = usuarioUsuByCodUsuIncIett;
        this.periodicidadePrdc = periodicidadePrdc;
        this.efItemEstContaEfiecs = efItemEstContaEfiecs;
        this.itemEstrutEntidadeIettes = itemEstrutEntidadeIettes;
        this.acompReferenciaItemAris = acompReferenciaItemAris;
        this.itemEstUsutpfuacIettutfas = itemEstUsutpfuacIettutfas;
        this.itemEstruturaNivelIettns = itemEstruturaNivelIettns;
        this.itemEstrUplCategIettucs = itemEstrUplCategIettucs;
        this.itemEstrtBenefIettbs = itemEstrtBenefIettbs;
        this.itemEstrutCriterioIettcs = itemEstrutCriterioIettcs;
        this.itemEstrutUsuarioIettusesByCodIett = itemEstrutUsuarioIettusesByCodIett;
		this.itemEstrutUsuarioIettusesByCodIettOrigem = itemEstrutUsuarioIettusesByCodIettOrigem;		
        this.acompRealFisicoArfs = acompRealFisicoArfs;
        this.apontamentoApts = apontamentoApts;
        this.itemEstrutLocalIettls = itemEstrutLocalIettls;
        this.itemEstruturaIetts = itemEstruturaIetts;
        this.efItemEstPrevisaoEfieps = efItemEstPrevisaoEfieps;
        this.itemEstrutVinculoIettvs = itemEstrutVinculoIettvs;
        this.pontoCriticoPtcs = pontoCriticoPtcs;
        this.itemEstrtIndResulIettrs = itemEstrtIndResulIettrs;
        this.itemEstrutMarcadorIettms = itemEstrutMarcadorIettms;
        this.itemEstrutAcaoIettas = itemEstrutAcaoIettas;
        this.itemEstrutUploadIettups = itemEstrutUploadIettups;
        this.itemRegdemandaIregds = itemRegdemandaIregds;
        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
    }
    
    public ItemEstruturaIett(String siglaIett, String nomeIett, Date dataInicioIett, Date dataTerminoIett){
    	this.siglaIett       =  siglaIett;
    	this.nomeIett        =  nomeIett;
    	this.dataInicioIett  = dataInicioIett;
    	this.dataTerminoIett = dataTerminoIett;
    }

    /**
     *
     * @return
     */
    public Long getCodIett() {
        return this.codIett;
    }

    /**
     *
     * @param codIett
     */
    public void setCodIett(Long codIett) {
        this.codIett = codIett;
    }

    /**
     *
     * @return
     */
    public BigDecimal getValPrevistoFuturoIett() {
        return this.valPrevistoFuturoIett;
    }

    /**
     *
     * @param valPrevistoFuturoIett
     */
    public void setValPrevistoFuturoIett(BigDecimal valPrevistoFuturoIett) {
        this.valPrevistoFuturoIett = valPrevistoFuturoIett;
    }

    /**
     *
     * @return
     */
    public String getIndBloqPlanejamentoIett() {
        return this.indBloqPlanejamentoIett;
    }

    /**
     *
     * @param indBloqPlanejamentoIett
     */
    public void setIndBloqPlanejamentoIett(String indBloqPlanejamentoIett) {
        this.indBloqPlanejamentoIett = indBloqPlanejamentoIett;
    }

    /**
     *
     * @return
     */
    public String getBeneficiosIett() {
        return this.beneficiosIett;
    }

    /**
     *
     * @param beneficiosIett
     */
    public void setBeneficiosIett(String beneficiosIett) {
        this.beneficiosIett = beneficiosIett;
    }

    /**
     *
     * @return
     */
    public String getOrigemIett() {
        return this.origemIett;
    }

    /**
     *
     * @param origemIett
     */
    public void setOrigemIett(String origemIett) {
        this.origemIett = origemIett;
    }

    /**
     *
     * @return
     */
    public String getObjetivoEspecificoIett() {
        return this.objetivoEspecificoIett;
    }

    /**
     *
     * @param objetivoEspecificoIett
     */
    public void setObjetivoEspecificoIett(String objetivoEspecificoIett) {
        this.objetivoEspecificoIett = objetivoEspecificoIett;
    }

    /**
     *
     * @return
     */
    public String getObjetivoGeralIett() {
        return this.objetivoGeralIett;
    }

    /**
     *
     * @param objetivoGeralIett
     */
    public void setObjetivoGeralIett(String objetivoGeralIett) {
        this.objetivoGeralIett = objetivoGeralIett;
    }

    /**
     *
     * @return
     */
    public String getIndMonitoramentoIett() {
        return this.indMonitoramentoIett;
    }

    /**
     *
     * @param indMonitoramentoIett
     */
    public void setIndMonitoramentoIett(String indMonitoramentoIett) {
        this.indMonitoramentoIett = indMonitoramentoIett;
    }

    /**
     *
     * @return
     */
    public String getIndCriticaIett() {
        return this.indCriticaIett;
    }

    /**
     *
     * @param indCriticaIett
     */
    public void setIndCriticaIett(String indCriticaIett) {
        this.indCriticaIett = indCriticaIett;
    }

    /**
     *
     * @return
     */
    public Date getDataInicioMonitoramentoIett() {
        return this.dataInicioMonitoramentoIett;
    }

    /**
     *
     * @param dataInicioMonitoramentoIett
     */
    public void setDataInicioMonitoramentoIett(Date dataInicioMonitoramentoIett) {
        this.dataInicioMonitoramentoIett = dataInicioMonitoramentoIett;
    }

    /**
     *
     * @return
     */
    public Date getDataTerminoIett() {
        return this.dataTerminoIett;
    }

    /**
     *
     * @param dataTerminoIett
     */
    public void setDataTerminoIett(Date dataTerminoIett) {
        this.dataTerminoIett = dataTerminoIett;
    }

    /**
     *
     * @return
     */
    public Date getDataInicioIett() {
        return this.dataInicioIett;
    }

    /**
     *
     * @param dataInicioIett
     */
    public void setDataInicioIett(Date dataInicioIett) {
        this.dataInicioIett = dataInicioIett;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoIett() {
        return this.indAtivoIett;
    }

    /**
     *
     * @param indAtivoIett
     */
    public void setIndAtivoIett(String indAtivoIett) {
        this.indAtivoIett = indAtivoIett;
    }

    /**
     *
     * @return
     */
    public Date getDataUltManutencaoIett() {
        return this.dataUltManutencaoIett;
    }

    /**
     *
     * @param dataUltManutencaoIett
     */
    public void setDataUltManutencaoIett(Date dataUltManutencaoIett) {
        this.dataUltManutencaoIett = dataUltManutencaoIett;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoIett() {
        return this.dataInclusaoIett;
    }

    /**
     *
     * @param dataInclusaoIett
     */
    public void setDataInclusaoIett(Date dataInclusaoIett) {
        this.dataInclusaoIett = dataInclusaoIett;
    }

    /**
     *
     * @return
     */
    public String getDescricaoIett() {
        return this.descricaoIett;
    }

    /**
     *
     * @param descricaoIett
     */
    public void setDescricaoIett(String descricaoIett) {
        this.descricaoIett = descricaoIett;
    }

    /**
     *
     * @return
     */
    public String getSiglaIett() {
        return this.siglaIett;
    }

    /**
     *
     * @param siglaIett
     */
    public void setSiglaIett(String siglaIett) {
        this.siglaIett = siglaIett;
    }

    /**
     *
     * @return
     */
    public String getDescricaoR5() {
        return this.descricaoR5;
    }

    /**
     *
     * @param descricaoR5
     */
    public void setDescricaoR5(String descricaoR5) {
        this.descricaoR5 = descricaoR5;
    }

    /**
     *
     * @return
     */
    public String getDescricaoR4() {
        return this.descricaoR4;
    }

    /**
     *
     * @param descricaoR4
     */
    public void setDescricaoR4(String descricaoR4) {
        this.descricaoR4 = descricaoR4;
    }

    /**
     *
     * @return
     */
    public String getDescricaoR3() {
        return this.descricaoR3;
    }

    /**
     *
     * @param descricaoR3
     */
    public void setDescricaoR3(String descricaoR3) {
        this.descricaoR3 = descricaoR3;
    }

    /**
     *
     * @return
     */
    public String getDescricaoR2() {
        return this.descricaoR2;
    }

    /**
     *
     * @param descricaoR2
     */
    public void setDescricaoR2(String descricaoR2) {
        this.descricaoR2 = descricaoR2;
    }

    /**
     *
     * @return
     */
    public String getDescricaoR1() {
        return this.descricaoR1;
    }

    /**
     *
     * @param descricaoR1
     */
    public void setDescricaoR1(String descricaoR1) {
        this.descricaoR1 = descricaoR1;
    }

    /**
     *
     * @return
     */
    public Date getDataR5() {
        return this.dataR5;
    }

    /**
     *
     * @param dataR5
     */
    public void setDataR5(Date dataR5) {
        this.dataR5 = dataR5;
    }

    /**
     *
     * @return
     */
    public Date getDataR4() {
        return this.dataR4;
    }

    /**
     *
     * @param dataR4
     */
    public void setDataR4(Date dataR4) {
        this.dataR4 = dataR4;
    }

    /**
     *
     * @return
     */
    public Date getDataR3() {
        return this.dataR3;
    }

    /**
     *
     * @param dataR3
     */
    public void setDataR3(Date dataR3) {
        this.dataR3 = dataR3;
    }

    /**
     *
     * @return
     */
    public Date getDataR2() {
        return this.dataR2;
    }

    /**
     *
     * @param dataR2
     */
    public void setDataR2(Date dataR2) {
        this.dataR2 = dataR2;
    }

    /**
     *
     * @return
     */
    public Date getDataR1() {
        return this.dataR1;
    }

    /**
     *
     * @param dataR1
     */
    public void setDataR1(Date dataR1) {
        this.dataR1 = dataR1;
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
    public ecar.pojo.OrgaoOrg getOrgaoOrgByCodOrgaoResponsavel1Iett() {
        return this.orgaoOrgByCodOrgaoResponsavel1Iett;
    }

    /**
     *
     * @param orgaoOrgByCodOrgaoResponsavel1Iett
     */
    public void setOrgaoOrgByCodOrgaoResponsavel1Iett(ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iett) {
        this.orgaoOrgByCodOrgaoResponsavel1Iett = orgaoOrgByCodOrgaoResponsavel1Iett;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.OrgaoOrg getOrgaoOrgByCodOrgaoResponsavel2Iett() {
        return this.orgaoOrgByCodOrgaoResponsavel2Iett;
    }

    /**
     *
     * @param orgaoOrgByCodOrgaoResponsavel2Iett
     */
    public void setOrgaoOrgByCodOrgaoResponsavel2Iett(ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel2Iett) {
        this.orgaoOrgByCodOrgaoResponsavel2Iett = orgaoOrgByCodOrgaoResponsavel2Iett;
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
    public ecar.pojo.SubAreaSare getSubAreaSare() {
        return this.subAreaSare;
    }

    /**
     *
     * @param subAreaSare
     */
    public void setSubAreaSare(ecar.pojo.SubAreaSare subAreaSare) {
        this.subAreaSare = subAreaSare;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.AreaAre getAreaAre() {
        return this.areaAre;
    }

    /**
     *
     * @param areaAre
     */
    public void setAreaAre(ecar.pojo.AreaAre areaAre) {
        this.areaAre = areaAre;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsuByCodUsuUltManutIett() {
        return this.usuarioUsuByCodUsuUltManutIett;
    }

    /**
     *
     * @param usuarioUsuByCodUsuUltManutIett
     */
    public void setUsuarioUsuByCodUsuUltManutIett(ecar.pojo.UsuarioUsu usuarioUsuByCodUsuUltManutIett) {
        this.usuarioUsuByCodUsuUltManutIett = usuarioUsuByCodUsuUltManutIett;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsuByCodUsuIncIett() {
        return this.usuarioUsuByCodUsuIncIett;
    }

    /**
     *
     * @param usuarioUsuByCodUsuIncIett
     */
    public void setUsuarioUsuByCodUsuIncIett(ecar.pojo.UsuarioUsu usuarioUsuByCodUsuIncIett) {
        this.usuarioUsuByCodUsuIncIett = usuarioUsuByCodUsuIncIett;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.PeriodicidadePrdc getPeriodicidadePrdc() {
        return this.periodicidadePrdc;
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
    public Set getEfItemEstContaEfiecs() {
        return this.efItemEstContaEfiecs;
    }

    /**
     *
     * @param efItemEstContaEfiecs
     */
    public void setEfItemEstContaEfiecs(Set efItemEstContaEfiecs) {
        this.efItemEstContaEfiecs = efItemEstContaEfiecs;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutEntidadeIettes() {
        return this.itemEstrutEntidadeIettes;
    }

    /**
     *
     * @param itemEstrutEntidadeIettes
     */
    public void setItemEstrutEntidadeIettes(Set itemEstrutEntidadeIettes) {
        this.itemEstrutEntidadeIettes = itemEstrutEntidadeIettes;
    }

    /**
     *
     * @return
     */
    public Set getAcompReferenciaItemAris() {
        return this.acompReferenciaItemAris;
    }

    /**
     *
     * @param acompReferenciaItemAris
     */
    public void setAcompReferenciaItemAris(Set acompReferenciaItemAris) {
        this.acompReferenciaItemAris = acompReferenciaItemAris;
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
    public Set getItemEstruturaNivelIettns() {
        return this.itemEstruturaNivelIettns;
    }

    /**
     *
     * @param itemEstruturaNivelIettns
     */
    public void setItemEstruturaNivelIettns(Set itemEstruturaNivelIettns) {
        this.itemEstruturaNivelIettns = itemEstruturaNivelIettns;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrUplCategIettucs() {
        return this.itemEstrUplCategIettucs;
    }

    /**
     *
     * @param itemEstrUplCategIettucs
     */
    public void setItemEstrUplCategIettucs(Set itemEstrUplCategIettucs) {
        this.itemEstrUplCategIettucs = itemEstrUplCategIettucs;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrtBenefIettbs() {
        return this.itemEstrtBenefIettbs;
    }

    /**
     *
     * @param itemEstrtBenefIettbs
     */
    public void setItemEstrtBenefIettbs(Set itemEstrtBenefIettbs) {
        this.itemEstrtBenefIettbs = itemEstrtBenefIettbs;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutCriterioIettcs() {
        return this.itemEstrutCriterioIettcs;
    }

    /**
     *
     * @param itemEstrutCriterioIettcs
     */
    public void setItemEstrutCriterioIettcs(Set itemEstrutCriterioIettcs) {
        this.itemEstrutCriterioIettcs = itemEstrutCriterioIettcs;
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
    public Set getApontamentoApts() {
        return this.apontamentoApts;
    }

    /**
     *
     * @param apontamentoApts
     */
    public void setApontamentoApts(Set apontamentoApts) {
        this.apontamentoApts = apontamentoApts;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutLocalIettls() {
        return this.itemEstrutLocalIettls;
    }

    /**
     *
     * @param itemEstrutLocalIettls
     */
    public void setItemEstrutLocalIettls(Set itemEstrutLocalIettls) {
        this.itemEstrutLocalIettls = itemEstrutLocalIettls;
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
    public Set getEfItemEstPrevisaoEfieps() {
        return this.efItemEstPrevisaoEfieps;
    }

    /**
     *
     * @param efItemEstPrevisaoEfieps
     */
    public void setEfItemEstPrevisaoEfieps(Set efItemEstPrevisaoEfieps) {
        this.efItemEstPrevisaoEfieps = efItemEstPrevisaoEfieps;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutVinculoIettvs() {
        return this.itemEstrutVinculoIettvs;
    }

    /**
     *
     * @param itemEstrutVinculoIettvs
     */
    public void setItemEstrutVinculoIettvs(Set itemEstrutVinculoIettvs) {
        this.itemEstrutVinculoIettvs = itemEstrutVinculoIettvs;
    }

    /**
     *
     * @return
     */
    public Set getPontoCriticoPtcs() {
        return this.pontoCriticoPtcs;
    }

    /**
     *
     * @param pontoCriticoPtcs
     */
    public void setPontoCriticoPtcs(Set pontoCriticoPtcs) {
        this.pontoCriticoPtcs = pontoCriticoPtcs;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrtIndResulIettrs() {
        return this.itemEstrtIndResulIettrs;
    }

    /**
     *
     * @param itemEstrtIndResulIettrs
     */
    public void setItemEstrtIndResulIettrs(Set itemEstrtIndResulIettrs) {
        this.itemEstrtIndResulIettrs = itemEstrtIndResulIettrs;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutMarcadorIettms() {
        return this.itemEstrutMarcadorIettms;
    }

    /**
     *
     * @param itemEstrutMarcadorIettms
     */
    public void setItemEstrutMarcadorIettms(Set itemEstrutMarcadorIettms) {
        this.itemEstrutMarcadorIettms = itemEstrutMarcadorIettms;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutAcaoIettas() {
        return this.itemEstrutAcaoIettas;
    }

    /**
     *
     * @param itemEstrutAcaoIettas
     */
    public void setItemEstrutAcaoIettas(Set itemEstrutAcaoIettas) {
        this.itemEstrutAcaoIettas = itemEstrutAcaoIettas;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutUploadIettups() {
        return this.itemEstrutUploadIettups;
    }
    
    /**
    *
    * @return
    */
   public Set getItemEstrutUploadIettupsAtivos() {
	   Set itensEstrutUploadIettupsAtivos = new HashSet();
	   if(this.itemEstrutUploadIettups != null && this.itemEstrutUploadIettups.size() > 0){
		   for (Object object : this.itemEstrutUploadIettups) {
			   ItemEstrutUploadIettup item = (ItemEstrutUploadIettup) object;
			   if(Dominios.SIM.equals(item.getIndAtivoIettup())){
				   itensEstrutUploadIettupsAtivos.add(item);
			   }
		   }
	   }
       return itensEstrutUploadIettupsAtivos;
   }

    /**
     *
     * @param itemEstrutUploadIettups
     */
    public void setItemEstrutUploadIettups(Set itemEstrutUploadIettups) {
        this.itemEstrutUploadIettups = itemEstrutUploadIettups;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIett", getCodIett())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstruturaIett) ) return false;
        ItemEstruturaIett castOther = (ItemEstruturaIett) other;
        return new EqualsBuilder()
            .append(this.getCodIett(), castOther.getCodIett())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIett())
            .toHashCode();
    }

    /**
     * @return Returns the efIettFonteTotEfiefts.
     */
    public Set getEfIettFonteTotEfiefts() {
        return efIettFonteTotEfiefts;
    }
    /**
     * @param efIettFonteTotEfiefts The efIettFonteTotEfiefts to set.
     */
    public void setEfIettFonteTotEfiefts(Set efIettFonteTotEfiefts) {
        this.efIettFonteTotEfiefts = efIettFonteTotEfiefts;
    }

    /**
     * @return Returns the nomeIett.
     */
    public String getNomeIett() {
        return nomeIett;
    }
    /**
     * @param nomeIett The nomeIett to set.
     */
    public void setNomeIett(String nomeIett) {
        this.nomeIett = nomeIett;
    }

	/**
	 * @return Returns the itemEstrutUsuarioIettusesByCodIett.
	 */
	public Set getItemEstrutUsuarioIettusesByCodIett() {
		return itemEstrutUsuarioIettusesByCodIett;
	}

	/**
	 * @param itemEstrutUsuarioIettusesByCodIett The itemEstrutUsuarioIettusesByCodIett to set.
	 */
	public void setItemEstrutUsuarioIettusesByCodIett(
			Set itemEstrutUsuarioIettusesByCodIett) {
		this.itemEstrutUsuarioIettusesByCodIett = itemEstrutUsuarioIettusesByCodIett;
	}

	/**
	 * @return Returns the itemEstrutUsuarioIettusesByCodIettOrigem.
	 */
	public Set getItemEstrutUsuarioIettusesByCodIettOrigem() {
		return itemEstrutUsuarioIettusesByCodIettOrigem;
	}

	/**
	 * @param itemEstrutUsuarioIettusesByCodIettOrigem The itemEstrutUsuarioIettusesByCodIettOrigem to set.
	 */
	public void setItemEstrutUsuarioIettusesByCodIettOrigem(
			Set itemEstrutUsuarioIettusesByCodIettOrigem) {
		this.itemEstrutUsuarioIettusesByCodIettOrigem = itemEstrutUsuarioIettusesByCodIettOrigem;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.UnidadeOrcamentariaUO getUnidadeOrcamentariaUO() {
		return unidadeOrcamentariaUO;
	}

        /**
         *
         * @param unidadeOrcamentariaUO
         */
        public void setUnidadeOrcamentariaUO(
			ecar.pojo.UnidadeOrcamentariaUO unidadeOrcamentariaUO) {
		this.unidadeOrcamentariaUO = unidadeOrcamentariaUO;
	}

        /**
         *
         * @return
         */
        public SituacaoSit getSituacaoSit() {
		return situacaoSit;
	}

    /**
     *
     * @param situacaoSit
     */
    public void setSituacaoSit(SituacaoSit situacaoSit) {
		this.situacaoSit = situacaoSit;
	}
	
    /**
     *
     * @return
     */
    public Set getItemRegdemandaIregds() {
		return this.itemRegdemandaIregds;
	}

    /**
     *
     * @param itemRegdemandaIregds
     */
    public void setItemRegdemandaIregds(Set itemRegdemandaIregds) {
		this.itemRegdemandaIregds = itemRegdemandaIregds;
	}

    /**
     *
     * @return
     */
    public Integer getNivelIett() {
        return this.nivelIett;
    }

    /**
     *
     * @param nivelIett
     */
    public void setNivelIett(Integer nivelIett) {
        this.nivelIett = nivelIett;
    }

    /**
     *
     * @return
     */
    public Set getItemEstruturaSisAtributoIettSatbs() {
		return itemEstruturaSisAtributoIettSatbs;
	}
	
    /**
     *
     * @param itemEstruturaSisAtributoIettSatbs
     */
    public void setItemEstruturaSisAtributoIettSatbs(
			Set itemEstruturaSisAtributoIettSatbs) {
		this.itemEstruturaSisAtributoIettSatbs = itemEstruturaSisAtributoIettSatbs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettHpais() {
		return historicoIettHpais;
	}

        /**
         *
         * @param historicoIettHpais
         */
        public void setHistoricoIettHpais(Set historicoIettHpais) {
		this.historicoIettHpais = historicoIettHpais;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettbHs() {
		return historicoIettbHs;
	}

        /**
         *
         * @param historicoIettbHs
         */
        public void setHistoricoIettbHs(Set historicoIettbHs) {
		this.historicoIettbHs = historicoIettbHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettlHs() {
		return historicoIettlHs;
	}

        /**
         *
         * @param historicoIettlHs
         */
        public void setHistoricoIettlHs(Set historicoIettlHs) {
		this.historicoIettlHs = historicoIettlHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettupHs() {
		return historicoIettupHs;
	}

        /**
         *
         * @param historicoIettupHs
         */
        public void setHistoricoIettupHs(Set historicoIettupHs) {
		this.historicoIettupHs = historicoIettupHs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettusHorigs() {
		return historicoIettusHorigs;
	}

        /**
         *
         * @param historicoIettusHorigs
         */
        public void setHistoricoIettusHorigs(Set historicoIettusHorigs) {
		this.historicoIettusHorigs = historicoIettusHorigs;
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
        public Set getHistoricoIettSatbHs() {
		return historicoIettSatbHs;
	}

        /**
         *
         * @param historicoIettSatbHs
         */
        public void setHistoricoIettSatbHs(Set historicoIettSatbHs) {
		this.historicoIettSatbHs = historicoIettSatbHs;
	}
	
	/**
	 * Todas as propriedades primitivas ou imut�veis como String s�o automaticamente copiadas.
	 * O m�todo clone dever� inicilizar apenas as propriedades mut�veis.
	 * @author carlos
	 * @since 19/08/2007
	 * @return Object
	 */
    @Override
	public Object clone() {
		
		try
		{
			ItemEstruturaIett clone = (ItemEstruturaIett) super.clone();

			clone.setCodIett(codIett);
			clone.setValPrevistoFuturoIett(valPrevistoFuturoIett);
			clone.setDataInicioMonitoramentoIett(dataInicioMonitoramentoIett);
			clone.setDataTerminoIett(dataTerminoIett);
			clone.setDataInicioIett(dataInicioIett);
			clone.setDataUltManutencaoIett(dataUltManutencaoIett);
			clone.setDataInclusaoIett(dataInclusaoIett);
			clone.setDataR5(dataR5);
			clone.setDataR4(dataR4);
			clone.setDataR3(dataR3);
			clone.setDataR2(dataR2);
			clone.setDataR1(dataR1);
			clone.setNivelIett(nivelIett);
			clone.setEstruturaEtt(estruturaEtt);
			clone.setOrgaoOrgByCodOrgaoResponsavel1Iett(orgaoOrgByCodOrgaoResponsavel1Iett);
			clone.setOrgaoOrgByCodOrgaoResponsavel2Iett(orgaoOrgByCodOrgaoResponsavel2Iett);
			clone.setItemEstruturaIett(itemEstruturaIett);
			clone.setSubAreaSare(subAreaSare);
			clone.setAreaAre(areaAre);
			clone.setUsuarioUsuByCodUsuUltManutIett(usuarioUsuByCodUsuUltManutIett);
			clone.setUsuarioUsuByCodUsuIncIett(usuarioUsuByCodUsuIncIett);
			clone.setPeriodicidadePrdc(periodicidadePrdc);
			clone.setUnidadeOrcamentariaUO(unidadeOrcamentariaUO);
			clone.setSituacaoSit(situacaoSit);
						
		    clone.setEfItemEstContaEfiecs(efItemEstContaEfiecs);
		    clone.setItemEstrutEntidadeIettes(itemEstrutEntidadeIettes);
		    clone.setAcompReferenciaItemAris(acompReferenciaItemAris);
		    clone.setItemEstUsutpfuacIettutfas(itemEstUsutpfuacIettutfas);
		    clone.setItemEstruturaNivelIettns(itemEstruturaNivelIettns);
		    clone.setItemEstrUplCategIettucs(itemEstrUplCategIettucs);
		    clone.setItemEstrtBenefIettbs(itemEstrtBenefIettbs);
		    clone.setItemEstrutCriterioIettcs(itemEstrutCriterioIettcs);
		    clone.setItemEstrutUsuarioIettusesByCodIett(itemEstrutUsuarioIettusesByCodIett);
		    clone.setItemEstrutUsuarioIettusesByCodIettOrigem(itemEstrutUsuarioIettusesByCodIettOrigem);
		    clone.setAcompRealFisicoArfs(acompRealFisicoArfs);
		    clone.setApontamentoApts(apontamentoApts);
		    clone.setItemEstrutLocalIettls(itemEstrutLocalIettls);
		    clone.setItemEstruturaIetts(itemEstruturaIetts);
		    clone.setEfItemEstPrevisaoEfieps(efItemEstPrevisaoEfieps);
		    clone.setItemEstrutVinculoIettvs(itemEstrutVinculoIettvs);
		    clone.setPontoCriticoPtcs(pontoCriticoPtcs);
		    clone.setItemEstrtIndResulIettrs(itemEstrtIndResulIettrs);
		    clone.setItemEstrutMarcadorIettms(itemEstrutMarcadorIettms);
		    clone.setItemEstrutAcaoIettas(itemEstrutAcaoIettas);
		    clone.setItemEstrutUploadIettups(itemEstrutUploadIettups);
		    clone.setEfIettFonteTotEfiefts(efIettFonteTotEfiefts);
		    clone.setItemRegdemandaIregds(itemRegdemandaIregds);  
		    clone.setItemEstruturarevisaoIettrevs(itemEstruturarevisaoIettrevs);
		    clone.setItemEstruturaSisAtributoIettSatbs(itemEstruturaSisAtributoIettSatbs);
		    clone.setHistoricoIettHs(historicoIettHs);
		    clone.setHistoricoIettHpais(historicoIettHpais);
		    clone.setHistoricoIettaHs(historicoIettaHs);
		    clone.setHistoricoIettbHs(historicoIettbHs);
		    clone.setHistoricoIettcHs(historicoIettcHs);
		    clone.setHistoricoIetteHs(historicoIetteHs);
		    clone.setHistoricoIettlHs(historicoIettlHs);
		    clone.setHistoricoIettrHs(historicoIettrHs);
		    clone.setHistoricoIettupHs(historicoIettupHs);
		    clone.setHistoricoIettusHorigs(historicoIettusHorigs);
		    clone.setHistoricoIettusHs(historicoIettusHs);
		    clone.setHistoricoIettutfaHs(historicoIettutfaHs);
		    clone.setHistoricoEfiecHs(historicoEfiecHs);
		    clone.setHistoricoEfieftHs(historicoEfieftHs);
		    clone.setHistoricoEfiepHs(historicoEfiepHs);
		    clone.setHistoricoIettSatbHs(historicoIettSatbHs);

		    return clone;
		
		} catch (CloneNotSupportedException e) {
	      return null;
		}
	}

    /**
     *
     * @return
     */
    public Set getAgendaAge() {
		return agendaAge;
	}

        /**
         *
         * @param agendaAge
         */
        public void setAgendaAge(Set agendaAge) {
		this.agendaAge = agendaAge;
	}
	
        /**
         *
         * @param estruturaVirtual
         * @return
         */
        public boolean pertenceEstruturaVirtual(EstruturaEtt estruturaVirtual){
		
		return getEstruturasVirtual().contains(estruturaVirtual);
		
	}

        /**
         *
         * @return
         */
        public String getIndModeloIett() {
		return indModeloIett;
	}

        /**
         *
         * @param indModeloIett
         */
        public void setIndModeloIett(String indModeloIett) {
		this.indModeloIett = indModeloIett;
	}


        /**
         *
         * @return
         */
        public Set getPesquisas() {
		return pesquisas;
	}

        /**
         *
         * @param pesquisas
         */
        public void setPesquisas(Set pesquisas) {
		this.pesquisas = pesquisas;
	}

	/**
	 * Dado um SisAtributo � retornado o ItemEstruturaSisAtributoIettSatb(valor do atributo Livre no item) correspondente ao SisAtributo informado e o Item Estrutura Atual(this). 
	 * @param sisAtributo
	 * @return
	 */
	public ItemEstruturaSisAtributoIettSatb buscarItemEstruturaSisAtributoLista (SisAtributoSatb sisAtributo){
		
		Set atributoLivres = this.getItemEstruturaSisAtributoIettSatbs();	
		ItemEstruturaSisAtributoIettSatb atributoLivre=null;
		
		for (Object object : atributoLivres) {
			 atributoLivre = (ItemEstruturaSisAtributoIettSatb)object;
			
			ItemEstruturaSisAtributoIettSatb atributoLivreInner = new ItemEstruturaSisAtributoIettSatb();
			
			atributoLivreInner.setItemEstruturaIett(this);
			atributoLivreInner.setSisAtributoSatb(sisAtributo);
			
			//Se o atributoLivre for igual ao Inner interrompe o Loop, pois o atributoLivre correspondente ao atributoLivre que est� sendo alterado na p�gina, foi encontrado.
			if (atributoLivre.equals(atributoLivreInner)) {
				break;
			}
		}

		return atributoLivre;
		
	}

        /**
         *
         * @param item
         * @return
         */
        public int compareTo(ItemEstruturaIett item) {
		
		int ret=0;
		
		if (getCodIett() < item.getCodIett()) {
			ret = -1;
		} else if (getCodIett() > item.getCodIett()) {
			ret = 1;
		}
		
		return ret;
	}

	public Long iGetCodigo() {
		return getCodIett();
	}

	public String iGetNome() {
		return getNomeIett();
	}
	
	public Hierarchyable iGetPai() {
		return getItemEstruturaIett();
	}

	public Hierarchyable iGetEstrutura() {
		return getEstruturaEtt();
	}

	public Collection<FuncaoSisAtributo> getListaAtributosLivres() {
		
		return getItemEstruturaSisAtributoIettSatbs();
	}
	
	 
	/**
	 * Retorna o pr�prio Item. Esse metodo ter� um comportamento diferente nas fun��es que possu�rem atributos
	 * livres(pontos criticos, e as pr�ximas fun��es), pois dever� retornar o item propriet�rio da fun��o consultada.    
	 */
	public ItemEstruturaIett itemProprietario() {
		return this;
	}

	public Long getCodigo() {
		return getCodIett();
	}
	
	/**
	 * Lista todos os atributos livres pertencentes ao grupo enviado como parametro, que possui informa��o dada pelo usu�rio 
	 * no preenchimento do item. 
	 * 
	 * @param grupoAtributoLivre
	 * @return
	 */
	public List<ItemEstruturaSisAtributoIettSatb> obterAtirbutosLivresInformados(SisGrupoAtributoSga grupoAtributoLivre){
		
		Iterator<ItemEstruturaSisAtributoIettSatb> itItemEstruturaSisAtributo = itemEstruturaSisAtributoIettSatbs.iterator(); 
		
		List<ItemEstruturaSisAtributoIettSatb> listaItemEstrutuSisAtributoInformados = new ArrayList<ItemEstruturaSisAtributoIettSatb>();
		
		
		while (itItemEstruturaSisAtributo.hasNext()) {
			ItemEstruturaSisAtributoIettSatb itemEstruturaAtributoLivre = itItemEstruturaSisAtributo.next();
			
			if (itemEstruturaAtributoLivre.getSisAtributoSatb().getSisGrupoAtributoSga().equals(grupoAtributoLivre)) {
				listaItemEstrutuSisAtributoInformados.add(itemEstruturaAtributoLivre);
			}
		}
		
		return listaItemEstrutuSisAtributoInformados;
	}

	public Set getRelacionamentosIettEtiqueta() {
		return relacionamentosIettEtiqueta;
	}

	public void setRelacionamentosIettEtiqueta(Set relacionamentosIettEtiqueta) {
		this.relacionamentosIettEtiqueta = relacionamentosIettEtiqueta;
	}
	
	public Set getRelIettEtiqueta() {
		return relIettEtiqueta;
	}
	
	public void setRelIettEtiqueta(Set relIettEtiqueta) {
		this.relIettEtiqueta = relIettEtiqueta;
	}
	
	public String getCodigoHexadecimalCor(){
		String codigoHexadecimal = null;
		if(StringUtils.isNotBlank(siglaIett)){
			if(StringUtils.equals("OE 01", siglaIett)){
				codigoHexadecimal =  "#f16477";
			}
			else if(StringUtils.equals("OE 02", siglaIett)){
				codigoHexadecimal = "#b93092";
			}
			else if(StringUtils.equals("OE 03", siglaIett)){
				codigoHexadecimal = "#9b5ba4";
			}
			else if(StringUtils.equals("OE 04", siglaIett)){
				codigoHexadecimal = "#b1005d";
			}
			else if(StringUtils.equals("OE 05", siglaIett)){
				codigoHexadecimal = "#0079c1";
			}
			else if(StringUtils.equals("OE 06", siglaIett)){
				codigoHexadecimal = "#00b5cc";
			}
			else if(StringUtils.equals("OE 07", siglaIett)){
				codigoHexadecimal = "#7e81be";
			}
			else if(StringUtils.equals("OE 08", siglaIett)){
				codigoHexadecimal = "#492f92";
			}
			else if(StringUtils.equals("OE 09", siglaIett)){
				codigoHexadecimal = "#7bc143";
			}
			else if(StringUtils.equals("OE 10", siglaIett)){
				codigoHexadecimal = "#b2bb1e";
			}
			else if(StringUtils.equals("OE 11", siglaIett)){
				codigoHexadecimal = "#61c5ba";
			}
			else if(StringUtils.equals("OE 12", siglaIett)){
				codigoHexadecimal = "#00958f";
			}
			else if(StringUtils.equals("OE 13", siglaIett)){
				codigoHexadecimal = "#f58025";
			}
			else if(StringUtils.equals("OE 14", siglaIett)){
				codigoHexadecimal = "#fdb813";
			}
			else if(StringUtils.equals("OE 15", siglaIett)){
				codigoHexadecimal = "#bb8d0a";
			}
			else if(StringUtils.equals("OE 16", siglaIett)){
				codigoHexadecimal = "#b15c11";
			}
		}
		
		return codigoHexadecimal.toUpperCase();
	}
	
	public String getCodigoHexadecimalSubCor(){
		String codigoHexadecimal = null;
		if(StringUtils.isNotBlank(siglaIett)){
			if(StringUtils.equals("OE 01", siglaIett)){
				 codigoHexadecimal = "#fcdddc";
			}
			else if(StringUtils.equals("OE 02", siglaIett)){
				codigoHexadecimal = "#ecd3e6";
			}
			else if(StringUtils.equals("OE 03", siglaIett)){
				codigoHexadecimal = "#e4d7e9";
			}
			else if(StringUtils.equals("OE 04", siglaIett)){
				codigoHexadecimal = "#edcad5";
			}
			else if(StringUtils.equals("OE 05", siglaIett)){
				codigoHexadecimal = "#c7d9f0";
			}
			else if(StringUtils.equals("OE 06", siglaIett)){
				codigoHexadecimal = "#d2edf3";
			}
			else if(StringUtils.equals("OE 07", siglaIett)){
				codigoHexadecimal = "#dfdff0";
			}
			else if(StringUtils.equals("OE 08", siglaIett)){
				codigoHexadecimal = "#cdc6e3";
			}
			else if(StringUtils.equals("OE 09", siglaIett)){
				codigoHexadecimal = "#e2f0d4";
			}
			else if(StringUtils.equals("OE 10", siglaIett)){
				codigoHexadecimal = "#edefcf";
			}
			else if(StringUtils.equals("OE 11", siglaIett)){
				codigoHexadecimal = "#def1ef";
			}
			else if(StringUtils.equals("OE 12", siglaIett)){
				codigoHexadecimal = "#c4e3e3";
			}
			else if(StringUtils.equals("OE 13", siglaIett)){
				codigoHexadecimal = "#fee2ca";
			}
			else if(StringUtils.equals("OE 14", siglaIett)){
				codigoHexadecimal = "#ffefcf";
			}
			else if(StringUtils.equals("OE 15", siglaIett)){
				codigoHexadecimal = "#f1e5c6";
			}
			else if(StringUtils.equals("OE 16", siglaIett)){
				codigoHexadecimal = "#efd5be";
			}
		}
		
		return codigoHexadecimal.toUpperCase();
	}
	
	public String getSiglaFormatadaObjetivoEstrategico(){
		String siglaFormatada = null;
		if(StringUtils.isNotBlank(siglaIett)){
			String sigla = siglaIett.replaceAll("[^0-9]", "");
			if(sigla.startsWith("0")){
				siglaFormatada = sigla.replace("0", "");
			}else{
				siglaFormatada = sigla;
			}
		}
		return siglaFormatada;
	}
	
}