package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import ecar.dao.UsuarioDao;

/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 02/06/2007
 */
public class UsuarioUsu implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4361197726603230902L;

	/** identifier field */
    private Long codUsu;

    /** nullable persistent field */
    private String nomeUsu = "";

    /** nullable persistent field */
    private String senhaUsu;

    /** nullable persistent field */
    private String cnpjCpfUsu;

    /** nullable persistent field */
    private String residEnderecoUsu;

    /** nullable persistent field */
    private Date dataInclusaoUsu;

    /** nullable persistent field */
    private Date dataUltAlteracaoUsu;

    /** nullable persistent field */
    private String residComplementoUsu;

    /** nullable persistent field */
    private String residDddUsu;

    /** nullable persistent field */
    private String faxUsu;

    /** nullable persistent field */
    private String residBairroUsu;

    /** nullable persistent field */
    private String residCepUsu;

    /** nullable persistent field */
    private Date dataNascimentoUsu;

    /** nullable persistent field */
    private String email1Usu;

    /** nullable persistent field */
    private String residTelefoneUsu;

    /** nullable persistent field */
    private String idUsuarioUsu;

    /** nullable persistent field */
    private String residCidadeUsu;

    /** nullable persistent field */
    private String indAtivoUsu;

    /** nullable persistent field */
    private String respostaLembreteUsu;

    /** nullable persistent field */
    private String perguntaLembreteUsu;

    /** nullable persistent field */
    private String indCompletoUsu;

    /** nullable persistent field */
    private String dddFaxUsu;

    /** nullable persistent field */
    private Date dataUltAcessoUsu;

    /** nullable persistent field */
    private String comercEnderecoUsu;

    /** nullable persistent field */
    private String comercCidadeUsu;

    /** nullable persistent field */
    private String comercCepUsu;

    /** nullable persistent field */
    private String comercBairroUsu;

    /** nullable persistent field */
    private String comercComplementoUsu;

    /** nullable persistent field */
    private String comercTelefoneUsu;

    /** nullable persistent field */
    private String comercDddUsu;

    /** nullable persistent field */
    private String email2Usu;

    /** nullable persistent field */
    private String indAutentSisOpUsu;

    /** nullable persistent field */
    private String idDominioUsu;

    /** nullable persistent field */
    private String residRamalUsu;

    /** nullable persistent field */
    private String comercRamalUsu;

    /** persistent field */
    private Set orgaoOrgs;

    /** persistent field */
    private Set entidadeEnts;
        
    /** persistent field */
    private ecar.pojo.TipoEnderecoCorrTpec tipoEnderecoCorrTpec;

    /** persistent field */
    private ecar.pojo.Uf ufByComercUfUsu;

    /** persistent field */
    private ecar.pojo.Uf ufByResidUfUsu;
    
    private SessionWS sessionWS;

    /** persistent field */
    private Set usuarioAtributoUsuas;

    /** persistent field */
    private Set itemEstUsutpfuacIettutfas;
    private Set itemEstUsutpfuacIettutfaManutencaos;

    /** persistent field */
    private Set acompReferenciaArefsByCodUsuIncAref;

    /** persistent field */
    private Set acompReferenciaArefsByCodUsuUltManutAref;

    /** persistent field */
    private Set acompRelatorioArelsByUsuarioUsu;

    /** persistent field */
    private Set acompRelatorioArelsByUsuarioUsuUltimaManutencao;
    
    /** persistent field */
    private Set logs;

    /** persistent field */
    private Set itemEstrutUsuarioIettuses;

    /** persistent field */
    private Set segmentoItemSgtis;

    /** persistent field */
    private Set efItemEstRealizadoEfiers;

    /** persistent field */
    private Set apontamentoApts;

    /** persistent field */
    private Set estruturaEtts;

    /** persistent field */
    private Set itemEstruturaIettsByCodUsuUltManutIett;

    /** persistent field */
    private Set itemEstruturaIettsByCodUsuIncIett;

    /** persistent field */
    private Set regControleAcessoRcas;

    /** persistent field */
    private Set segmentoCategoriaSgtcs;

    /** persistent field */
    private Set segmentoSgts;

    /** persistent field */
    private Set itemEstrutMarcadorIettms;

    /** persistent field */
    private Set itemEstrutUploadIettups;

    /** persistent field */
    private Set agendaAges;

    /** persistent field */
    private Set unidadeOrcamentariaUO;
    
    /** persistent field */
    private Set regDemandaRegdsByCodUsuInclusaoRegd;

    /** persistent field */
    private Set itemEstruturaRevisaoIettRevs;
    
    /** persistent field */
    private Set itemEstrutAcaoIettas;
    
    private Set iettUsutpfuacrevIettutfars;
    
    private Set pontoCriticoPtcs;
  
    private Set itemEstruturaSisAtributoIettSatbs;
    
    private Set ImportacaoImps;
    
    private Set contasRejeitadasCrejs;
    
    /* -- Mantis #2156 -- */
    private Set historicoMasters;
    private Set historicoIettHincs;
    private Set historicoIettHmanuts;
    private Set historicoIettaHs;
    private Set historicoIettaHusus;
    private Set historicoIettbHs;
    private Set historicoIettcHs;
    private Set historicoIetteHs;
    private Set historicoIettfHs;
    private Set historicoIettlHs;
    private Set historicoIettrHs;
    private Set historicoIettupHs;
    private Set historicoIettupHusus;
    private Set historicoIettusHs;
    private Set historicoIettutfaHs;
    private Set historicoIettutfaHmanuts;
    private Set historicoEfiecHs;
    private Set historicoEfieftHs;
    private Set historicoEfiepHs;
    private Set historicoEfierHs;
    private Set historicoEfierHusus;
    private Set historicoIettSatbHs;
    
    private Set itemEstrutUsuarioIettusManuts;
    private Set itemEstrutCriterioIettcs;
    private Set itemEstrutUploadIettupsUsuManutencao;
    private Set itemEstrutAcaoIettaUsuManuts;
    private Set itemEstrutEntidadeIette;
    private Set itemEstrtBenefIettb;
    private Set itemEstrutLocalIettl;
    private Set itemEstrtIndResulIettr;
    
    private Set emailsRecebidos;
    
    private Set perfilIntercambioDadosPflids;
    
    private Map<String,String> mapArquivosAtuaisUsuarios;      
    
    public SessionWS getSessionWS() {
		return sessionWS;
	}
    
    public void setSessionWS(SessionWS sessionWS) {
		this.sessionWS = sessionWS;
	}
    
    /**
     *
     * @return
     */
    public Set getItemEstrutAcaoIettaUsuManuts() {
		return itemEstrutAcaoIettaUsuManuts;
	}

        /**
         *
         * @param itemEstrutAcaoIettaUsuManuts
         */
        public void setItemEstrutAcaoIettaUsuManuts(Set itemEstrutAcaoIettaUsuManuts) {
		this.itemEstrutAcaoIettaUsuManuts = itemEstrutAcaoIettaUsuManuts;
	}

        /**
         *
         * @return
         */
        public Set getItemEstrutUploadIettupsUsuManutencao() {
		return itemEstrutUploadIettupsUsuManutencao;
	}

        /**
         *
         * @param itemEstrutUploadIettupsUsuManutencao
         */
        public void setItemEstrutUploadIettupsUsuManutencao(
			Set itemEstrutUploadIettupsUsuManutencao) {
		this.itemEstrutUploadIettupsUsuManutencao = itemEstrutUploadIettupsUsuManutencao;
	}

        /**
         *
         * @return
         */
        public Set getItemEstrutCriterioIettcs() {
		return itemEstrutCriterioIettcs;
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
        public Set getItemEstrutUsuarioIettusManuts() {
		return itemEstrutUsuarioIettusManuts;
	}

        /**
         *
         * @param itemEstrutUsuarioIettusManuts
         */
        public void setItemEstrutUsuarioIettusManuts(Set itemEstrutUsuarioIettusManuts) {
		this.itemEstrutUsuarioIettusManuts = itemEstrutUsuarioIettusManuts;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoEfierHusus() {
		return historicoEfierHusus;
	}

        /**
         *
         * @param historicoEfierHusus
         */
        public void setHistoricoEfierHusus(Set historicoEfierHusus) {
		this.historicoEfierHusus = historicoEfierHusus;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoEfierHs() {
		return historicoEfierHs;
	}

        /**
         *
         * @param historicoEfierHs
         */
        public void setHistoricoEfierHs(Set historicoEfierHs) {
		this.historicoEfierHs = historicoEfierHs;
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
        public Set getHistoricoIettHincs() {
		return historicoIettHincs;
	}

        /**
         *
         * @param historicoIettHincs
         */
        public void setHistoricoIettHincs(Set historicoIettHincs) {
		this.historicoIettHincs = historicoIettHincs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoMasters() {
		return historicoMasters;
	}

        /**
         *
         * @param historicoMasters
         */
        public void setHistoricoMasters(Set historicoMasters) {
		this.historicoMasters = historicoMasters;
	}

        /**
         *
         * @return
         */
        public Set getContasRejeitadasCrejs() {
		return contasRejeitadasCrejs;
	}

        /**
         *
         * @param contasRejeitadasCrejs
         */
        public void setContasRejeitadasCrejs(Set contasRejeitadasCrejs) {
		this.contasRejeitadasCrejs = contasRejeitadasCrejs;
	}

        /**
         *
         * @return
         */
        public Set getImportacaoImps() {
		return ImportacaoImps;
	}

        /**
         *
         * @param importacaoImps
         */
        public void setImportacaoImps(Set importacaoImps) {
		ImportacaoImps = importacaoImps;
	}

        /**
         *
         * @return
         */
        public Set getIettUsutpfuacrevIettutfars() {
		return iettUsutpfuacrevIettutfars;
	}

        /**
         *
         * @param iettUsutpfuacrevIettutfars
         */
        public void setIettUsutpfuacrevIettutfars(Set iettUsutpfuacrevIettutfars) {
		this.iettUsutpfuacrevIettutfars = iettUsutpfuacrevIettutfars;
	}

        /**
         *
         * @return
         */
        public Set getItemEstruturaRevisaoIettRevs() {
		return itemEstruturaRevisaoIettRevs;
	}

        /**
         *
         * @param itemEstruturaRevisaoIettRevs
         */
        public void setItemEstruturaRevisaoIettRevs(Set itemEstruturaRevisaoIettRevs) {
		this.itemEstruturaRevisaoIettRevs = itemEstruturaRevisaoIettRevs;
	}


        /** full constructor
         * @param nomeUsu
         * @param idDominioUsu
         * @param senhaUsu
         * @param residEnderecoUsu
         * @param cnpjCpfUsu
         * @param estruturaEtts
         * @param dataUltAlteracaoUsu
         * @param dataInclusaoUsu
         * @param faxUsu
         * @param residBairroUsu
         * @param residComplementoUsu
         * @param efItemEstRealizadoEfiers
         * @param logs
         * @param unidadeOrcamentariaUO
         * @param email2Usu
         * @param residDddUsu
         * @param acompRelatorioArelsByUsuarioUsu
         * @param email1Usu
         * @param dataNascimentoUsu
         * @param indCompletoUsu
         * @param perguntaLembreteUsu
         * @param pontoCriticoPtcs
         * @param respostaLembreteUsu
         * @param itemEstUsutpfuacIettutfas
         * @param comercCidadeUsu
         * @param residCepUsu
         * @param comercDddUsu
         * @param segmentoItemSgtis
         * @param dddFaxUsu
         * @param dataUltAcessoUsu
         * @param indAtivoUsu
         * @param residCidadeUsu
         * @param comercCepUsu
         * @param comercComplementoUsu
         * @param comercBairroUsu
         * @param residTelefoneUsu
         * @param idUsuarioUsu
         * @param comercTelefoneUsu
         * @param itemEstrutAcaoIettas
         * @param agendaAges
         * @param itemEstrutUploadIettups
         * @param comercEnderecoUsu
         * @param comercRamalUsu
         * @param regDemandaRegdsByCodUsuInclusaoRegd
         * @param residRamalUsu
         * @param emailsRecebidos
         * @param tipoEnderecoCorrTpec
         * @param usuarioAtributoUsuas
         * @param orgaoOrgs
         * @param indAutentSisOpUsu
         * @param itemEstrutUsuarioIettuses
         * @param acompRelatorioArelsByUsuarioUsuUltimaManutencao
         * @param segmentoCategoriaSgtcs
         * @param itemEstruturaRevisaoIettRevs
         * @param ufByResidUfUsu
         * @param itemEstruturaIettsByCodUsuIncIett
         * @param ufByComercUfUsu
         * @param acompReferenciaArefsByCodUsuUltManutAref
         * @param acompReferenciaArefsByCodUsuIncAref
         * @param regControleAcessoRcas
         * @param itemEstruturaIettsByCodUsuUltManutIett
         * @param apontamentoApts
         * @param segmentoSgts
         * @param itemEstrutMarcadorIettms
         * @param iettUsutpfuacrevIettutfars
         */
    public UsuarioUsu(String nomeUsu, String senhaUsu, String cnpjCpfUsu, String residEnderecoUsu, Date dataInclusaoUsu, Date dataUltAlteracaoUsu, String residComplementoUsu, String residDddUsu, String faxUsu, String residBairroUsu, String residCepUsu, Date dataNascimentoUsu, String email1Usu, String residTelefoneUsu, String idUsuarioUsu, String residCidadeUsu, String indAtivoUsu, String respostaLembreteUsu, String perguntaLembreteUsu, String indCompletoUsu, String dddFaxUsu, Date dataUltAcessoUsu, String comercEnderecoUsu, String comercCidadeUsu, String comercCepUsu, String comercBairroUsu, String comercComplementoUsu, String comercTelefoneUsu, String comercDddUsu, String email2Usu, String indAutentSisOpUsu, String idDominioUsu, String residRamalUsu, String comercRamalUsu, Set orgaoOrgs, ecar.pojo.TipoEnderecoCorrTpec tipoEnderecoCorrTpec, ecar.pojo.Uf ufByComercUfUsu, ecar.pojo.Uf ufByResidUfUsu, Set usuarioAtributoUsuas, Set itemEstUsutpfuacIettutfas, Set acompReferenciaArefsByCodUsuIncAref, Set acompReferenciaArefsByCodUsuUltManutAref, Set acompRelatorioArelsByUsuarioUsu, Set acompRelatorioArelsByUsuarioUsuUltimaManutencao, Set logs, Set itemEstrutUsuarioIettuses, Set segmentoItemSgtis, Set efItemEstRealizadoEfiers, Set apontamentoApts, Set estruturaEtts, Set itemEstruturaIettsByCodUsuUltManutIett, Set itemEstruturaIettsByCodUsuIncIett, Set regControleAcessoRcas, Set segmentoCategoriaSgtcs, Set segmentoSgts, Set itemEstrutMarcadorIettms, Set itemEstrutUploadIettups, Set agendaAges, Set unidadeOrcamentariaUO,  Set regDemandaRegdsByCodUsuInclusaoRegd, Set itemEstruturaRevisaoIettRevs, Set iettUsutpfuacrevIettutfars, Set pontoCriticoPtcs, Set itemEstrutAcaoIettas, Set emailsRecebidos, Set perfilIntercambioDadosPflids) {
        this.nomeUsu = nomeUsu;
        this.senhaUsu = senhaUsu;
        this.cnpjCpfUsu = cnpjCpfUsu;
        this.residEnderecoUsu = residEnderecoUsu;
        this.dataInclusaoUsu = dataInclusaoUsu;
        this.dataUltAlteracaoUsu = dataUltAlteracaoUsu;
        this.residComplementoUsu = residComplementoUsu;
        this.residDddUsu = residDddUsu;
        this.faxUsu = faxUsu;
        this.residBairroUsu = residBairroUsu;
        this.residCepUsu = residCepUsu;
        this.dataNascimentoUsu = dataNascimentoUsu;
        this.email1Usu = email1Usu;
        this.residTelefoneUsu = residTelefoneUsu;
        this.idUsuarioUsu = idUsuarioUsu;
        this.residCidadeUsu = residCidadeUsu;
        this.indAtivoUsu = indAtivoUsu;
        this.respostaLembreteUsu = respostaLembreteUsu;
        this.perguntaLembreteUsu = perguntaLembreteUsu;
        this.indCompletoUsu = indCompletoUsu;
        this.dddFaxUsu = dddFaxUsu;
        this.dataUltAcessoUsu = dataUltAcessoUsu;
        this.comercEnderecoUsu = comercEnderecoUsu;
        this.comercCidadeUsu = comercCidadeUsu;
        this.comercCepUsu = comercCepUsu;
        this.comercBairroUsu = comercBairroUsu;
        this.comercComplementoUsu = comercComplementoUsu;
        this.comercTelefoneUsu = comercTelefoneUsu;
        this.comercDddUsu = comercDddUsu;
        this.email2Usu = email2Usu;
        this.indAutentSisOpUsu = indAutentSisOpUsu;
        this.idDominioUsu = idDominioUsu;
        this.residRamalUsu = residRamalUsu;
        this.comercRamalUsu = comercRamalUsu;
        this.orgaoOrgs = orgaoOrgs;
        this.tipoEnderecoCorrTpec = tipoEnderecoCorrTpec;
        this.ufByComercUfUsu = ufByComercUfUsu;
        this.ufByResidUfUsu = ufByResidUfUsu;
        this.usuarioAtributoUsuas = usuarioAtributoUsuas;
        this.itemEstUsutpfuacIettutfas = itemEstUsutpfuacIettutfas;
        this.acompReferenciaArefsByCodUsuIncAref = acompReferenciaArefsByCodUsuIncAref;
        this.acompReferenciaArefsByCodUsuUltManutAref = acompReferenciaArefsByCodUsuUltManutAref;
        this.acompRelatorioArelsByUsuarioUsu = acompRelatorioArelsByUsuarioUsu;
        this.acompRelatorioArelsByUsuarioUsuUltimaManutencao = acompRelatorioArelsByUsuarioUsuUltimaManutencao;
        this.logs = logs;
        this.itemEstrutUsuarioIettuses = itemEstrutUsuarioIettuses;
        this.segmentoItemSgtis = segmentoItemSgtis;
        this.efItemEstRealizadoEfiers = efItemEstRealizadoEfiers;
        this.apontamentoApts = apontamentoApts;
        this.estruturaEtts = estruturaEtts;
        this.itemEstruturaIettsByCodUsuUltManutIett = itemEstruturaIettsByCodUsuUltManutIett;
        this.itemEstruturaIettsByCodUsuIncIett = itemEstruturaIettsByCodUsuIncIett;
        this.regControleAcessoRcas = regControleAcessoRcas;
        this.segmentoCategoriaSgtcs = segmentoCategoriaSgtcs;
        this.segmentoSgts = segmentoSgts;
        this.itemEstrutMarcadorIettms = itemEstrutMarcadorIettms;
        this.itemEstrutUploadIettups = itemEstrutUploadIettups;
        this.agendaAges = agendaAges;
        this.unidadeOrcamentariaUO = unidadeOrcamentariaUO;
        this.regDemandaRegdsByCodUsuInclusaoRegd = regDemandaRegdsByCodUsuInclusaoRegd;
        this.itemEstruturaRevisaoIettRevs = itemEstruturaRevisaoIettRevs;
        this.iettUsutpfuacrevIettutfars = iettUsutpfuacrevIettutfars;
        this.pontoCriticoPtcs = pontoCriticoPtcs;
        this.itemEstrutAcaoIettas = itemEstrutAcaoIettas;
        this.emailsRecebidos = emailsRecebidos;
        this.perfilIntercambioDadosPflids = perfilIntercambioDadosPflids;
    }

    /** default constructor */
    public UsuarioUsu() {
    }

    /** minimal constructor
     * @param orgaoOrgs
     * @param tipoEnderecoCorrTpec
     * @param ufByComercUfUsu
     * @param unidadeOrcamentariaUO
     * @param segmentoSgts
     * @param itemEstrutMarcadorIettms
     * @param usuarioAtributoUsuas
     * @param acompReferenciaArefsByCodUsuUltManutAref
     * @param ufByResidUfUsu
     * @param itemEstUsutpfuacIettutfas
     * @param pontoCriticoPtcs
     * @param logs
     * @param acompReferenciaArefsByCodUsuIncAref
     * @param iettUsutpfuacrevIettutfars
     * @param acompRelatorioArelsByUsuarioUsu
     * @param estruturaEtts
     * @param acompRelatorioArelsByUsuarioUsuUltimaManutencao
     * @param segmentoCategoriaSgtcs
     * @param itemEstruturaRevisaoIettRevs
     * @param efItemEstRealizadoEfiers
     * @param segmentoItemSgtis
     * @param apontamentoApts
     * @param itemEstrutUsuarioIettuses
     * @param itemEstruturaIettsByCodUsuIncIett
     * @param agendaAges
     * @param itemEstrutUploadIettups
     * @param regControleAcessoRcas
     * @param itemEstruturaIettsByCodUsuUltManutIett
     * @param regDemandaRegdsByCodUsuInclusaoRegd
     * @param itemEstrutAcaoIettas
     */
    public UsuarioUsu(Set orgaoOrgs, ecar.pojo.TipoEnderecoCorrTpec tipoEnderecoCorrTpec, ecar.pojo.Uf ufByComercUfUsu, ecar.pojo.Uf ufByResidUfUsu, Set usuarioAtributoUsuas, Set itemEstUsutpfuacIettutfas, Set acompReferenciaArefsByCodUsuIncAref, Set acompReferenciaArefsByCodUsuUltManutAref, Set acompRelatorioArelsByUsuarioUsu, Set acompRelatorioArelsByUsuarioUsuUltimaManutencao, Set logs, Set itemEstrutUsuarioIettuses, Set segmentoItemSgtis, Set efItemEstRealizadoEfiers, Set apontamentoApts, Set estruturaEtts, Set itemEstruturaIettsByCodUsuUltManutIett, Set itemEstruturaIettsByCodUsuIncIett, Set regControleAcessoRcas, Set segmentoCategoriaSgtcs, Set segmentoSgts, Set itemEstrutMarcadorIettms, Set itemEstrutUploadIettups, Set agendaAges, Set unidadeOrcamentariaUO, Set regDemandaRegdsByCodUsuInclusaoRegd, Set itemEstruturaRevisaoIettRevs, Set iettUsutpfuacrevIettutfars, Set pontoCriticoPtcs, Set itemEstrutAcaoIettas) {
        this.orgaoOrgs = orgaoOrgs;
        this.tipoEnderecoCorrTpec = tipoEnderecoCorrTpec;
        this.ufByComercUfUsu = ufByComercUfUsu;
        this.ufByResidUfUsu = ufByResidUfUsu;
        this.usuarioAtributoUsuas = usuarioAtributoUsuas;
        this.itemEstUsutpfuacIettutfas = itemEstUsutpfuacIettutfas;
        this.acompReferenciaArefsByCodUsuIncAref = acompReferenciaArefsByCodUsuIncAref;
        this.acompReferenciaArefsByCodUsuUltManutAref = acompReferenciaArefsByCodUsuUltManutAref;
        this.acompRelatorioArelsByUsuarioUsu = acompRelatorioArelsByUsuarioUsu;
        this.acompRelatorioArelsByUsuarioUsuUltimaManutencao = acompRelatorioArelsByUsuarioUsuUltimaManutencao;
        this.logs = logs;
        this.itemEstrutUsuarioIettuses = itemEstrutUsuarioIettuses;
        this.itemEstruturaRevisaoIettRevs = itemEstruturaRevisaoIettRevs;
        this.segmentoItemSgtis = segmentoItemSgtis;
        this.efItemEstRealizadoEfiers = efItemEstRealizadoEfiers;
        this.apontamentoApts = apontamentoApts;
        this.estruturaEtts = estruturaEtts;
        this.itemEstruturaIettsByCodUsuUltManutIett = itemEstruturaIettsByCodUsuUltManutIett;
        this.itemEstruturaIettsByCodUsuIncIett = itemEstruturaIettsByCodUsuIncIett;
        this.regControleAcessoRcas = regControleAcessoRcas;
        this.segmentoCategoriaSgtcs = segmentoCategoriaSgtcs;
        this.segmentoSgts = segmentoSgts;
        this.itemEstrutMarcadorIettms = itemEstrutMarcadorIettms;
        this.itemEstrutUploadIettups = itemEstrutUploadIettups;
        this.agendaAges = agendaAges;
        this.unidadeOrcamentariaUO = unidadeOrcamentariaUO;
        this.regDemandaRegdsByCodUsuInclusaoRegd = regDemandaRegdsByCodUsuInclusaoRegd;
        this.iettUsutpfuacrevIettutfars = iettUsutpfuacrevIettutfars;
        this.pontoCriticoPtcs = pontoCriticoPtcs;
        this.itemEstrutAcaoIettas = itemEstrutAcaoIettas;
    }

    /**
     *
     * @return
     */
    public Long getCodUsu() {
        return this.codUsu;
    }

    /**
     *
     * @param codUsu
     */
    public void setCodUsu(Long codUsu) {
        this.codUsu = codUsu;
    }

    /**
     *
     * @return
     */
    public String getNomeUsuSent() {
    	if(this.getIdDominioUsu() != null && !"".equals(this.getIdDominioUsu().trim())) {
    		UsuarioDao usuarioDao = new UsuarioDao(null);

    		// recuperar nome do usu�rio via interface do sentinela
    		String nome = usuarioDao.getNomeUsuarioSentinela(Long.parseLong(this.getIdDominioUsu()));
    		
    		this.setNomeUsu(nome);
    	}

        return this.nomeUsu;
    }

    /**
     *
     * @return
     */
    public String getNomeUsu() {
        return this.nomeUsu;
    }
    
    
    /**
     *
     * @param nomeUsu
     */
    public void setNomeUsu(String nomeUsu) {
        this.nomeUsu = nomeUsu;
    }

    /**
     *
     * @return
     */
    public String getSenhaUsu() {
        return this.senhaUsu;
    }

    /**
     *
     * @return
     */
    public Set getUnidadeOrcamentariaUO() {
		return unidadeOrcamentariaUO;
	}

    /**
     *
     * @param unidadeOrcamentariaUO
     */
    public void setUnidadeOrcamentariaUO(Set unidadeOrcamentariaUO) {
		this.unidadeOrcamentariaUO = unidadeOrcamentariaUO;
	}

        /**
         *
         * @param senhaUsu
         */
        public void setSenhaUsu(String senhaUsu) {
        this.senhaUsu = senhaUsu;
    }

        /**
         *
         * @return
         */
        public String getCnpjCpfUsu() {
    	if(this.getIdDominioUsu() != null && !"".equals(this.getIdDominioUsu().trim())) {
    		UsuarioDao usuarioDao = new UsuarioDao(null);

    		// recuperar CPF do usu�rio via interface do sentinela
    		String cpf = usuarioDao.getCpfUsuarioSentinela(Long.parseLong(this.getIdDominioUsu()));
    		if(!cpf.equals(usuarioDao.INFORMACAO_NAO_LOCALIZADA_SENTINELA)) {
    			this.setCnpjCpfUsu(cpf);    			
    		}
    	}

    	return this.cnpjCpfUsu;
    }

        /**
         *
         * @param cnpjCpfUsu
         */
        public void setCnpjCpfUsu(String cnpjCpfUsu) {
        this.cnpjCpfUsu = cnpjCpfUsu;
    }

    /**
     *
     * @return
     */
    public String getResidEnderecoUsu() {
        return this.residEnderecoUsu;
    }

    /**
     *
     * @param residEnderecoUsu
     */
    public void setResidEnderecoUsu(String residEnderecoUsu) {
        this.residEnderecoUsu = residEnderecoUsu;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoUsu() {
        return this.dataInclusaoUsu;
    }

    /**
     *
     * @param dataInclusaoUsu
     */
    public void setDataInclusaoUsu(Date dataInclusaoUsu) {
        this.dataInclusaoUsu = dataInclusaoUsu;
    }

    /**
     *
     * @return
     */
    public Date getDataUltAlteracaoUsu() {
        return this.dataUltAlteracaoUsu;
    }

    /**
     *
     * @param dataUltAlteracaoUsu
     */
    public void setDataUltAlteracaoUsu(Date dataUltAlteracaoUsu) {
        this.dataUltAlteracaoUsu = dataUltAlteracaoUsu;
    }

    /**
     *
     * @return
     */
    public String getResidComplementoUsu() {
        return this.residComplementoUsu;
    }

    /**
     *
     * @param residComplementoUsu
     */
    public void setResidComplementoUsu(String residComplementoUsu) {
        this.residComplementoUsu = residComplementoUsu;
    }

    /**
     *
     * @return
     */
    public String getResidDddUsu() {
        return this.residDddUsu;
    }

    /**
     *
     * @param residDddUsu
     */
    public void setResidDddUsu(String residDddUsu) {
        this.residDddUsu = residDddUsu;
    }

    /**
     *
     * @return
     */
    public String getFaxUsu() {
        return this.faxUsu;
    }

    /**
     *
     * @param faxUsu
     */
    public void setFaxUsu(String faxUsu) {
        this.faxUsu = faxUsu;
    }

    /**
     *
     * @return
     */
    public String getResidBairroUsu() {
        return this.residBairroUsu;
    }

    /**
     *
     * @param residBairroUsu
     */
    public void setResidBairroUsu(String residBairroUsu) {
        this.residBairroUsu = residBairroUsu;
    }

    /**
     *
     * @return
     */
    public String getResidCepUsu() {
        return this.residCepUsu;
    }

    /**
     *
     * @param residCepUsu
     */
    public void setResidCepUsu(String residCepUsu) {
        this.residCepUsu = residCepUsu;
    }

    /**
     *
     * @return
     */
    public Date getDataNascimentoUsu() {
        return this.dataNascimentoUsu;
    }

    /**
     *
     * @param dataNascimentoUsu
     */
    public void setDataNascimentoUsu(Date dataNascimentoUsu) {
        this.dataNascimentoUsu = dataNascimentoUsu;
    }

    /**
     *
     * @return
     */
    public String getEmail1UsuSent() {
    	if(this.getIdDominioUsu() != null && !"".equals(this.getIdDominioUsu().trim())) {
    		UsuarioDao usuarioDao = new UsuarioDao(null);

    		// recuperar E-mail do usu�rio via interface do sentinela
    		String email = usuarioDao.getEmailUsuarioSentinela(Long.parseLong(this.getIdDominioUsu()));    		
    		this.setEmail1Usu(email);
    	}

    	return this.email1Usu;
    }

    /**
     *
     * @return
     */
    public String getEmail1Usu() {
    	return this.email1Usu;
    }
    
    
    /**
     *
     * @param email1Usu
     */
    public void setEmail1Usu(String email1Usu) {
        this.email1Usu = email1Usu;
    }

    /**
     *
     * @return
     */
    public String getResidTelefoneUsu() {
        return this.residTelefoneUsu;
    }

    /**
     *
     * @param residTelefoneUsu
     */
    public void setResidTelefoneUsu(String residTelefoneUsu) {
        this.residTelefoneUsu = residTelefoneUsu;
    }

    /**
     *
     * @return
     */
    public String getIdUsuarioUsu() {
    	if(this.getIdDominioUsu() != null && !"".equals(this.getIdDominioUsu().trim())) {
    		UsuarioDao usuarioDao = new UsuarioDao(null);

    		// recuperar login do usu�rio via interface do sentinela
    		String login = usuarioDao.getLoginUsuarioSentinela(Long.parseLong(this.getIdDominioUsu()));
    		
    		this.setIdUsuarioUsu(login);
    	}

        return this.idUsuarioUsu;
    }

    /**
     *
     * @param idUsuarioUsu
     */
    public void setIdUsuarioUsu(String idUsuarioUsu) {
        this.idUsuarioUsu = idUsuarioUsu;
    }

    /**
     *
     * @return
     */
    public String getResidCidadeUsu() {
        return this.residCidadeUsu;
    }

    /**
     *
     * @param residCidadeUsu
     */
    public void setResidCidadeUsu(String residCidadeUsu) {
        this.residCidadeUsu = residCidadeUsu;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoUsu() {
        return this.indAtivoUsu;
    }

    /**
     *
     * @param indAtivoUsu
     */
    public void setIndAtivoUsu(String indAtivoUsu) {
        this.indAtivoUsu = indAtivoUsu;
    }

    /**
     *
     * @return
     */
    public String getRespostaLembreteUsu() {
        return this.respostaLembreteUsu;
    }

    /**
     *
     * @param respostaLembreteUsu
     */
    public void setRespostaLembreteUsu(String respostaLembreteUsu) {
        this.respostaLembreteUsu = respostaLembreteUsu;
    }

    /**
     *
     * @return
     */
    public String getPerguntaLembreteUsu() {
        return this.perguntaLembreteUsu;
    }

    /**
     *
     * @param perguntaLembreteUsu
     */
    public void setPerguntaLembreteUsu(String perguntaLembreteUsu) {
        this.perguntaLembreteUsu = perguntaLembreteUsu;
    }

    /**
     *
     * @return
     */
    public String getIndCompletoUsu() {
        return this.indCompletoUsu;
    }

    /**
     *
     * @param indCompletoUsu
     */
    public void setIndCompletoUsu(String indCompletoUsu) {
        this.indCompletoUsu = indCompletoUsu;
    }

    /**
     *
     * @return
     */
    public String getDddFaxUsu() {
        return this.dddFaxUsu;
    }

    /**
     *
     * @param dddFaxUsu
     */
    public void setDddFaxUsu(String dddFaxUsu) {
        this.dddFaxUsu = dddFaxUsu;
    }

    /**
     *
     * @return
     */
    public Date getDataUltAcessoUsu() {
        return this.dataUltAcessoUsu;
    }

    /**
     *
     * @param dataUltAcessoUsu
     */
    public void setDataUltAcessoUsu(Date dataUltAcessoUsu) {
        this.dataUltAcessoUsu = dataUltAcessoUsu;
    }

    /**
     *
     * @return
     */
    public String getComercEnderecoUsu() {
        return this.comercEnderecoUsu;
    }

    /**
     *
     * @param comercEnderecoUsu
     */
    public void setComercEnderecoUsu(String comercEnderecoUsu) {
        this.comercEnderecoUsu = comercEnderecoUsu;
    }

    /**
     *
     * @return
     */
    public String getComercCidadeUsu() {
        return this.comercCidadeUsu;
    }

    /**
     *
     * @param comercCidadeUsu
     */
    public void setComercCidadeUsu(String comercCidadeUsu) {
        this.comercCidadeUsu = comercCidadeUsu;
    }

    /**
     *
     * @return
     */
    public String getComercCepUsu() {
        return this.comercCepUsu;
    }

    /**
     *
     * @param comercCepUsu
     */
    public void setComercCepUsu(String comercCepUsu) {
        this.comercCepUsu = comercCepUsu;
    }

    /**
     *
     * @return
     */
    public String getComercBairroUsu() {
        return this.comercBairroUsu;
    }

    /**
     *
     * @param comercBairroUsu
     */
    public void setComercBairroUsu(String comercBairroUsu) {
        this.comercBairroUsu = comercBairroUsu;
    }

    /**
     *
     * @return
     */
    public String getComercComplementoUsu() {
        return this.comercComplementoUsu;
    }

    /**
     *
     * @param comercComplementoUsu
     */
    public void setComercComplementoUsu(String comercComplementoUsu) {
        this.comercComplementoUsu = comercComplementoUsu;
    }

    /**
     *
     * @return
     */
    public String getComercTelefoneUsu() {
        return this.comercTelefoneUsu;
    }

    /**
     *
     * @param comercTelefoneUsu
     */
    public void setComercTelefoneUsu(String comercTelefoneUsu) {
        this.comercTelefoneUsu = comercTelefoneUsu;
    }

    /**
     *
     * @return
     */
    public String getComercDddUsu() {
        return this.comercDddUsu;
    }

    /**
     *
     * @param comercDddUsu
     */
    public void setComercDddUsu(String comercDddUsu) {
        this.comercDddUsu = comercDddUsu;
    }

    /**
     *
     * @return
     */
    public String getEmail2Usu() {
        return this.email2Usu;
    }

    /**
     *
     * @param email2Usu
     */
    public void setEmail2Usu(String email2Usu) {
        this.email2Usu = email2Usu;
    }

    /**
     *
     * @return
     */
    public String getIndAutentSisOpUsu() {
        return this.indAutentSisOpUsu;
    }

    /**
     *
     * @param indAutentSisOpUsu
     */
    public void setIndAutentSisOpUsu(String indAutentSisOpUsu) {
        this.indAutentSisOpUsu = indAutentSisOpUsu;
    }

    /**
     *
     * @return
     */
    public String getIdDominioUsu() {
        return this.idDominioUsu;
    }

    /**
     *
     * @param idDominioUsu
     */
    public void setIdDominioUsu(String idDominioUsu) {
        this.idDominioUsu = idDominioUsu;
    }

    /**
     *
     * @return
     */
    public String getResidRamalUsu() {
        return this.residRamalUsu;
    }

    /**
     *
     * @param residRamalUsu
     */
    public void setResidRamalUsu(String residRamalUsu) {
        this.residRamalUsu = residRamalUsu;
    }

    /**
     *
     * @return
     */
    public String getComercRamalUsu() {
        return this.comercRamalUsu;
    }

    /**
     *
     * @param comercRamalUsu
     */
    public void setComercRamalUsu(String comercRamalUsu) {
        this.comercRamalUsu = comercRamalUsu;
    }

    /**
     *
     * @return
     */
    public Set getOrgaoOrgs() {
        return this.orgaoOrgs;
    }

    /**
     *
     * @param orgaoOrgs
     */
    public void setOrgaoOrgs(Set orgaoOrgs) {
        this.orgaoOrgs = orgaoOrgs;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.TipoEnderecoCorrTpec getTipoEnderecoCorrTpec() {
        return this.tipoEnderecoCorrTpec;
    }

    /**
     *
     * @param tipoEnderecoCorrTpec
     */
    public void setTipoEnderecoCorrTpec(ecar.pojo.TipoEnderecoCorrTpec tipoEnderecoCorrTpec) {
        this.tipoEnderecoCorrTpec = tipoEnderecoCorrTpec;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.Uf getUfByComercUfUsu() {
        return this.ufByComercUfUsu;
    }

    /**
     *
     * @param ufByComercUfUsu
     */
    public void setUfByComercUfUsu(ecar.pojo.Uf ufByComercUfUsu) {
        this.ufByComercUfUsu = ufByComercUfUsu;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.Uf getUfByResidUfUsu() {
        return this.ufByResidUfUsu;
    }

    /**
     *
     * @param ufByResidUfUsu
     */
    public void setUfByResidUfUsu(ecar.pojo.Uf ufByResidUfUsu) {
        this.ufByResidUfUsu = ufByResidUfUsu;
    }

    /**
     *
     * @return
     */
    public Set getUsuarioAtributoUsuas() {
        return this.usuarioAtributoUsuas;
    }

    /**
     *
     * @param usuarioAtributoUsuas
     */
    public void setUsuarioAtributoUsuas(Set usuarioAtributoUsuas) {
        this.usuarioAtributoUsuas = usuarioAtributoUsuas;
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
    public Set getAcompReferenciaArefsByCodUsuIncAref() {
        return this.acompReferenciaArefsByCodUsuIncAref;
    }

    /**
     *
     * @param acompReferenciaArefsByCodUsuIncAref
     */
    public void setAcompReferenciaArefsByCodUsuIncAref(Set acompReferenciaArefsByCodUsuIncAref) {
        this.acompReferenciaArefsByCodUsuIncAref = acompReferenciaArefsByCodUsuIncAref;
    }

    /**
     *
     * @return
     */
    public Set getAcompReferenciaArefsByCodUsuUltManutAref() {
        return this.acompReferenciaArefsByCodUsuUltManutAref;
    }

    /**
     *
     * @param acompReferenciaArefsByCodUsuUltManutAref
     */
    public void setAcompReferenciaArefsByCodUsuUltManutAref(Set acompReferenciaArefsByCodUsuUltManutAref) {
        this.acompReferenciaArefsByCodUsuUltManutAref = acompReferenciaArefsByCodUsuUltManutAref;
    }

    /**
     *
     * @return
     */
    public Set getLogs() {
        return this.logs;
    }

    /**
     *
     * @param logs
     */
    public void setLogs(Set logs) {
        this.logs = logs;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutUsuarioIettuses() {
        return this.itemEstrutUsuarioIettuses;
    }

    /**
     *
     * @param itemEstrutUsuarioIettuses
     */
    public void setItemEstrutUsuarioIettuses(Set itemEstrutUsuarioIettuses) {
        this.itemEstrutUsuarioIettuses = itemEstrutUsuarioIettuses;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoItemSgtis() {
        return this.segmentoItemSgtis;
    }

    /**
     *
     * @param segmentoItemSgtis
     */
    public void setSegmentoItemSgtis(Set segmentoItemSgtis) {
        this.segmentoItemSgtis = segmentoItemSgtis;
    }

    /**
     *
     * @return
     */
    public Set getEfItemEstRealizadoEfiers() {
        return this.efItemEstRealizadoEfiers;
    }

    /**
     *
     * @param efItemEstRealizadoEfiers
     */
    public void setEfItemEstRealizadoEfiers(Set efItemEstRealizadoEfiers) {
        this.efItemEstRealizadoEfiers = efItemEstRealizadoEfiers;
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

    /**
     *
     * @return
     */
    public Set getItemEstruturaIettsByCodUsuUltManutIett() {
        return this.itemEstruturaIettsByCodUsuUltManutIett;
    }

    /**
     *
     * @param itemEstruturaIettsByCodUsuUltManutIett
     */
    public void setItemEstruturaIettsByCodUsuUltManutIett(Set itemEstruturaIettsByCodUsuUltManutIett) {
        this.itemEstruturaIettsByCodUsuUltManutIett = itemEstruturaIettsByCodUsuUltManutIett;
    }

    /**
     *
     * @return
     */
    public Set getItemEstruturaIettsByCodUsuIncIett() {
        return this.itemEstruturaIettsByCodUsuIncIett;
    }

    /**
     *
     * @param itemEstruturaIettsByCodUsuIncIett
     */
    public void setItemEstruturaIettsByCodUsuIncIett(Set itemEstruturaIettsByCodUsuIncIett) {
        this.itemEstruturaIettsByCodUsuIncIett = itemEstruturaIettsByCodUsuIncIett;
    }

    /**
     *
     * @return
     */
    public Set getRegControleAcessoRcas() {
        return this.regControleAcessoRcas;
    }

    /**
     *
     * @param regControleAcessoRcas
     */
    public void setRegControleAcessoRcas(Set regControleAcessoRcas) {
        this.regControleAcessoRcas = regControleAcessoRcas;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoCategoriaSgtcs() {
        return this.segmentoCategoriaSgtcs;
    }

    /**
     *
     * @param segmentoCategoriaSgtcs
     */
    public void setSegmentoCategoriaSgtcs(Set segmentoCategoriaSgtcs) {
        this.segmentoCategoriaSgtcs = segmentoCategoriaSgtcs;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoSgts() {
        return this.segmentoSgts;
    }

    /**
     *
     * @param segmentoSgts
     */
    public void setSegmentoSgts(Set segmentoSgts) {
        this.segmentoSgts = segmentoSgts;
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
    public Set getItemEstrutUploadIettups() {
        return this.itemEstrutUploadIettups;
    }

    /**
     *
     * @param itemEstrutUploadIettups
     */
    public void setItemEstrutUploadIettups(Set itemEstrutUploadIettups) {
        this.itemEstrutUploadIettups = itemEstrutUploadIettups;
    }

    /**
     *
     * @return
     */
    public Set getAgendaAges() {
        return this.agendaAges;
    }

    /**
     *
     * @param agendaAges
     */
    public void setAgendaAges(Set agendaAges) {
        this.agendaAges = agendaAges;
    }
    
    /**
     *
     * @return
     */
    public Set getAcompRelatorioArelsByUsuarioUsu() {
        return acompRelatorioArelsByUsuarioUsu;
    }
     
    /**
     *
     * @param acompRelatorioArelsByUsuarioUsu
     */
    public void setAcompRelatorioArelsByUsuarioUsu(
            Set acompRelatorioArelsByUsuarioUsu) {
        this.acompRelatorioArelsByUsuarioUsu = acompRelatorioArelsByUsuarioUsu;
    }

    /**
     *
     * @return
     */
    public Set getAcompRelatorioArelsByUsuarioUsuUltimaManutencao() {
        return acompRelatorioArelsByUsuarioUsuUltimaManutencao;
    }

    /**
     *
     * @param acompRelatorioArelsByUsuarioUsuUltimaManutencao
     */
    public void setAcompRelatorioArelsByUsuarioUsuUltimaManutencao(
            Set acompRelatorioArelsByUsuarioUsuUltimaManutencao) {
        this.acompRelatorioArelsByUsuarioUsuUltimaManutencao = acompRelatorioArelsByUsuarioUsuUltimaManutencao;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codUsu", getCodUsu())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof UsuarioUsu) ) return false;
        UsuarioUsu castOther = (UsuarioUsu) other;
        return new EqualsBuilder()
            .append(this.getCodUsu(), castOther.getCodUsu())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodUsu())
            .toHashCode();
    }
    
    /**
     *
     * @return
     */
    public Set getRegDemandaRegdsByCodUsuInclusaoRegd() {
        return this.regDemandaRegdsByCodUsuInclusaoRegd;
    }

    /**
     *
     * @param regDemandaRegdsByCodUsuInclusaoRegd
     */
    public void setRegDemandaRegdsByCodUsuInclusaoRegd(Set regDemandaRegdsByCodUsuInclusaoRegd) {
        this.regDemandaRegdsByCodUsuInclusaoRegd = regDemandaRegdsByCodUsuInclusaoRegd;
    }

    /**
     *
     * @return
     */
    public Set getPontoCriticoPtcs() {
		return pontoCriticoPtcs;
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
        public Set getItemEstrutAcaoIettas() {
		return itemEstrutAcaoIettas;
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
        public Set getHistoricoEfieftHs() {
		return historicoEfieftHs;
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
        public Set getHistoricoIettaHusus() {
		return historicoIettaHusus;
	}

        /**
         *
         * @param historicoIettaHusus
         */
        public void setHistoricoIettaHusus(Set historicoIettaHusus) {
		this.historicoIettaHusus = historicoIettaHusus;
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
        public Set getHistoricoIettHmanuts() {
		return historicoIettHmanuts;
	}

        /**
         *
         * @param historicoIettHmanuts
         */
        public void setHistoricoIettHmanuts(Set historicoIettHmanuts) {
		this.historicoIettHmanuts = historicoIettHmanuts;
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
        public Set getHistoricoIettupHusus() {
		return historicoIettupHusus;
	}

        /**
         *
         * @param historicoIettupHusus
         */
        public void setHistoricoIettupHusus(Set historicoIettupHusus) {
		this.historicoIettupHusus = historicoIettupHusus;
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
        public Set getHistoricoIettutfaHmanuts() {
		return historicoIettutfaHmanuts;
	}

        /**
         *
         * @param historicoIettutfaHmanuts
         */
        public void setHistoricoIettutfaHmanuts(Set historicoIettutfaHmanuts) {
		this.historicoIettutfaHmanuts = historicoIettutfaHmanuts;
	}

        /**
         *
         * @return
         */
        public Set getItemEstrtBenefIettb() {
		return itemEstrtBenefIettb;
	}

        /**
         *
         * @param itemEstrtBenefIettb
         */
        public void setItemEstrtBenefIettb(Set itemEstrtBenefIettb) {
		this.itemEstrtBenefIettb = itemEstrtBenefIettb;
	}

        /**
         *
         * @return
         */
        public Set getItemEstrutEntidadeIette() {
		return itemEstrutEntidadeIette;
	}

        /**
         *
         * @param itemEstrutEntidadeIette
         */
        public void setItemEstrutEntidadeIette(Set itemEstrutEntidadeIette) {
		this.itemEstrutEntidadeIette = itemEstrutEntidadeIette;
	}

        /**
         *
         * @return
         */
        public Set getItemEstrutLocalIettl() {
		return itemEstrutLocalIettl;
	}

        /**
         *
         * @param itemEstrutLocalIettl
         */
        public void setItemEstrutLocalIettl(Set itemEstrutLocalIettl) {
		this.itemEstrutLocalIettl = itemEstrutLocalIettl;
	}

        /**
         *
         * @return
         */
        public Set getItemEstUsutpfuacIettutfaManutencaos() {
		return itemEstUsutpfuacIettutfaManutencaos;
	}

        /**
         *
         * @param itemEstUsutpfuacIettutfaManutencaos
         */
        public void setItemEstUsutpfuacIettutfaManutencaos(
			Set itemEstUsutpfuacIettutfaManutencaos) {
		this.itemEstUsutpfuacIettutfaManutencaos = itemEstUsutpfuacIettutfaManutencaos;
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
         *
         * @return
         */
        public Set getItemEstrtIndResulIettr() {
		return itemEstrtIndResulIettr;
	}

        /**
         *
         * @param itemEstrtIndResulIettr
         */
        public void setItemEstrtIndResulIettr(Set itemEstrtIndResulIettr) {
		this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
	}

        /**
         *
         * @return
         */
        public Set getEmailsRecebidos() {
		return emailsRecebidos;
	}

        /**
         *
         * @param emailsRecebidos
         */
        public void setEmailsRecebidos(Set emailsRecebidos) {
		this.emailsRecebidos = emailsRecebidos;
	}

        /**
         *
         * @return
         */
        public Set getEntidadeEnts() {
		return entidadeEnts;
	}

        /**
         *
         * @param entidadeEnts
         */
        public void setEntidadeEnts(Set entidadeEnts) {
		this.entidadeEnts = entidadeEnts;
	}

	 /**
        *
        * @return
        */
       public String recuperarEntidadesUsuarioIdsString() {
		String ids = "";
		Iterator<EntidadeEnt> it = null;
		EntidadeEnt entidadeEnt = null;
		int i = 0;
		if (this.getEntidadeEnts()!=null) {
			it = this.getEntidadeEnts().iterator();
			while(it.hasNext()) {
				entidadeEnt = it.next();
				if (i==0)
					ids = entidadeEnt.getCodEnt().toString();
				else
					ids += "," + entidadeEnt.getCodEnt().toString();
				i++;
			}
		}
		
		return ids;
	}

    /**
     * 
     * @return
     */
	public Set getPerfilIntercambioDadosPflids() {
		return perfilIntercambioDadosPflids;
	}

	/**
	 * 
	 * @param perfilIntercambioDadosPflids
	 */
	public void setPerfilIntercambioDadosPflids(Set perfilIntercambioDadosPflids) {
		this.perfilIntercambioDadosPflids = perfilIntercambioDadosPflids;
	}

	public Map<String, String> getMapArquivosAtuaisUsuarios() {
		return mapArquivosAtuaisUsuarios;
	}

	public void setMapArquivosAtuaisUsuarios(Map<String, String> mapArquivosAtuaisUsuarios) {
		this.mapArquivosAtuaisUsuarios = mapArquivosAtuaisUsuarios;
	}

}
