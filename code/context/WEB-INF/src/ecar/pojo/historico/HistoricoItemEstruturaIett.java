package ecar.pojo.historico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.Hibernate;

import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SituacaoSit;
import ecar.pojo.UsuarioUsu;

/**
 * @author Paulo Pinheiro
 * @since n/c
 * @version 0.2, 02/06/2007
 */
public class HistoricoItemEstruturaIett implements Serializable, IHistorico {

    /**
     *
     * @return
     */
    public Long getIdObjetoSerializado() {
		return idObjetoSerializado;
	}

    /**
     *
     * @param idObjetoSerializado
     */
    public void setIdObjetoSerializado(Long idObjetoSerializado) {
		this.idObjetoSerializado = idObjetoSerializado;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8164920548366171799L;

	private UsuarioUsu usuarioUsuOperacao;
	private Date dataHistorico;
	private Long tipoHistorico;
	private String historico;
	private Long idObjetoSerializado;
	private Long idHistorico;

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
	private ItemEstruturaIett itemEstruturaIett;

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
	private Set itemEstUsutpfuacIettutfas;

	/** persistent field */
	private Set itemEstruturaNivelIettns;

	/** persistent field */
	private SituacaoSit situacaoSit;

	private Set itemEstruturaSisAtributoIettSatbs;

	private Set agendaAge;

	private Boolean indExclusaoPosHistorico;
	
	private List atributoEstrutura;
	
	private SisGrupoAtributoSga sisGrupoAtributoNivelPlanejamento;

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

	/** default constructor */
	public HistoricoItemEstruturaIett() {
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
        public ItemEstruturaIett getItemEstruturaIett() {
		return this.itemEstruturaIett;
	}

        /**
         *
         * @param itemEstruturaIett
         */
        public void setItemEstruturaIett(ItemEstruturaIett itemEstruturaIett) {
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

    @Override
	public String toString() {
		return new ToStringBuilder(this).append("codIett", getCodIett()).toString();
	}

    @Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof HistoricoItemEstruturaIett))
			return false;
		HistoricoItemEstruturaIett castOther = (HistoricoItemEstruturaIett) other;
		return new EqualsBuilder().append(this.getCodIett(), castOther.getCodIett()).isEquals();
	}

    @Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCodIett()).toHashCode();
	}

	/**
	 * @return Returns the nomeIett.
	 */
	public String getNomeIett() {
		return nomeIett;
	}

	/**
	 * @param nomeIett
	 *            The nomeIett to set.
	 */
	public void setNomeIett(String nomeIett) {
		this.nomeIett = nomeIett;
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
        public void setUnidadeOrcamentariaUO(ecar.pojo.UnidadeOrcamentariaUO unidadeOrcamentariaUO) {
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
        public void setItemEstruturaSisAtributoIettSatbs(Set itemEstruturaSisAtributoIettSatbs) {
		this.itemEstruturaSisAtributoIettSatbs = itemEstruturaSisAtributoIettSatbs;
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
         * @return
         */
        public UsuarioUsu getUsuarioUsuOperacao() {
		return usuarioUsuOperacao;
	}

        /**
         *
         * @param usuarioUsuOperacao
         */
        public void setUsuarioUsuOperacao(UsuarioUsu usuarioUsuOperacao) {
		this.usuarioUsuOperacao = usuarioUsuOperacao;
	}

        /**
         *
         * @return
         */
        public Date getDataHistorico() {
		return dataHistorico;
	}

        /**
         *
         * @param dataHistorico
         */
        public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

        /**
         *
         * @return
         */
        public Long getTipoHistorico() {
		return tipoHistorico;
	}

        /**
         *
         * @param tipoHistorico
         */
        public void setTipoHistorico(Long tipoHistorico) {
		this.tipoHistorico = tipoHistorico;
	}

        /**
         *
         * @return
         */
        public String getHistorico() {
		return this.historico;
	}

        /**
         *
         * @param historico
         */
        public void setHistorico(String historico) {
		this.historico = historico;
	}

        /**
         *
         * @param objPai
         */
        public void carregar(Object objPai) {

		ItemEstruturaIett clone = (ItemEstruturaIett) objPai;

		this.setCodIett(clone.getCodIett());
		this.setIdObjetoSerializado(clone.getCodIett());
		this.setSiglaIett(clone.getSiglaIett());
		this.setDescricaoIett(clone.getDescricaoIett());
		this.setValPrevistoFuturoIett(clone.getValPrevistoFuturoIett());
		this.setDataInicioMonitoramentoIett(clone.getDataInicioMonitoramentoIett());
		this.setDataTerminoIett(clone.getDataTerminoIett());
		this.setDataInicioIett(clone.getDataInicioIett());
		this.setDataUltManutencaoIett(clone.getDataUltManutencaoIett());
		this.setDataInclusaoIett(clone.getDataInclusaoIett());
		this.setDataR5(clone.getDataR5());
		this.setDataR4(clone.getDataR4());
		this.setDataR3(clone.getDataR3());
		this.setDataR2(clone.getDataR2());
		this.setDataR1(clone.getDataR1());
		this.setDescricaoR1(clone.getDescricaoR1());
		this.setDescricaoR2(clone.getDescricaoR2());
		this.setDescricaoR3(clone.getDescricaoR3());
		this.setDescricaoR4(clone.getDescricaoR4());
		this.setDescricaoR5(clone.getDescricaoR5());
		this.setNivelIett(clone.getNivelIett());
		this.setNomeIett(clone.getNomeIett());
		this.setIndModeloIett(clone.getIndModeloIett());
		this.setIndBloqPlanejamentoIett(clone.getIndBloqPlanejamentoIett());
		this.setEstruturaEtt(clone.getEstruturaEtt());
		Hibernate.initialize(this.getEstruturaEtt().getEstruturaSituacaoEtts());
		this.setOrgaoOrgByCodOrgaoResponsavel1Iett(clone.getOrgaoOrgByCodOrgaoResponsavel1Iett());
		this.setOrgaoOrgByCodOrgaoResponsavel2Iett(clone.getOrgaoOrgByCodOrgaoResponsavel2Iett());
		this.setItemEstruturaIett(clone.getItemEstruturaIett());
		this.setSubAreaSare(clone.getSubAreaSare());
		this.setAreaAre(clone.getAreaAre());
		this.setUsuarioUsuByCodUsuUltManutIett(clone.getUsuarioUsuByCodUsuUltManutIett());
		this.setUsuarioUsuByCodUsuIncIett(clone.getUsuarioUsuByCodUsuIncIett());
		this.setPeriodicidadePrdc(clone.getPeriodicidadePrdc());
		this.setUnidadeOrcamentariaUO(clone.getUnidadeOrcamentariaUO());
		this.setSituacaoSit(clone.getSituacaoSit());
		this.setBeneficiosIett(clone.getBeneficiosIett());
		this.setOrigemIett(clone.getOrigemIett());
		this.setObjetivoEspecificoIett(clone.getObjetivoEspecificoIett());
		this.setObjetivoGeralIett(clone.getObjetivoGeralIett());
		this.setIndMonitoramentoIett(clone.getIndMonitoramentoIett());
		this.setIndCriticaIett(clone.getIndCriticaIett());
		this.setIndAtivoIett(clone.getIndAtivoIett());
		this.setIndExclusaoPosHistorico(clone.getIndExclusaoPosHistorico());
		
		if (clone.getItemEstUsutpfuacIettutfas() == null){
			this.setItemEstUsutpfuacIettutfas(new HashSet<ItemEstUsutpfuacIettutfa>());
		} else {
			this.setItemEstUsutpfuacIettutfas(clone.getItemEstUsutpfuacIettutfas());
		}
		
		if (clone.getItemEstruturaSisAtributoIettSatbs() == null){
			this.setItemEstruturaSisAtributoIettSatbs(new HashSet<ItemEstruturaSisAtributoIettSatb>());
		} else {
			this.setItemEstruturaSisAtributoIettSatbs(clone.getItemEstruturaSisAtributoIettSatbs());
		}
		
		if (clone.getItemEstruturaNivelIettns() == null){
			this.setItemEstruturaNivelIettns(new HashSet<SisAtributoSatb>());
		} else {
			this.setItemEstruturaNivelIettns(clone.getItemEstruturaNivelIettns());
		}
		
	}

        /**
         *
         * @return
         */
        public Object descarregar() {

		ItemEstruturaIett iett = null;
		
		try {
			iett = (ItemEstruturaIett) super.clone();

			iett.setCodIett(this.getCodIett());
			iett.setSiglaIett(this.getSiglaIett());
			iett.setDescricaoIett(this.getDescricaoIett());
			iett.setValPrevistoFuturoIett(this.getValPrevistoFuturoIett());
			iett.setDataInicioMonitoramentoIett(this.getDataInicioMonitoramentoIett());
			iett.setDataTerminoIett(this.getDataTerminoIett());
			iett.setDataInicioIett(this.getDataInicioIett());
			iett.setDataUltManutencaoIett(this.getDataUltManutencaoIett());
			iett.setDataInclusaoIett(this.getDataInclusaoIett());
			iett.setDataR5(this.getDataR5());
			iett.setDataR4(this.getDataR4());
			iett.setDataR3(this.getDataR3());
			iett.setDataR2(this.getDataR2());
			iett.setDataR1(this.getDataR1());
			iett.setDescricaoR1(this.getDescricaoR1());
			iett.setDescricaoR2(this.getDescricaoR2());
			iett.setDescricaoR3(this.getDescricaoR3());
			iett.setDescricaoR4(this.getDescricaoR4());
			iett.setDescricaoR5(this.getDescricaoR5());
			iett.setNivelIett(this.getNivelIett());
			iett.setNomeIett(this.getNomeIett());
			iett.setIndModeloIett(this.getIndModeloIett());
			iett.setIndBloqPlanejamentoIett(this.getIndBloqPlanejamentoIett());
			iett.setEstruturaEtt(this.getEstruturaEtt());
			iett.setOrgaoOrgByCodOrgaoResponsavel1Iett(this.getOrgaoOrgByCodOrgaoResponsavel1Iett());
			iett.setOrgaoOrgByCodOrgaoResponsavel2Iett(this.getOrgaoOrgByCodOrgaoResponsavel2Iett());
			iett.setItemEstruturaIett(this.getItemEstruturaIett());
			iett.setSubAreaSare(this.getSubAreaSare());
			iett.setAreaAre(this.getAreaAre());
			iett.setUsuarioUsuByCodUsuUltManutIett(this.getUsuarioUsuByCodUsuUltManutIett());
			iett.setUsuarioUsuByCodUsuIncIett(this.getUsuarioUsuByCodUsuIncIett());
			iett.setPeriodicidadePrdc(this.getPeriodicidadePrdc());
			iett.setUnidadeOrcamentariaUO(this.getUnidadeOrcamentariaUO());
			iett.setSituacaoSit(this.getSituacaoSit());
			iett.setItemEstUsutpfuacIettutfas(this.getItemEstUsutpfuacIettutfas());
			iett.setItemEstruturaSisAtributoIettSatbs(this.getItemEstruturaSisAtributoIettSatbs());
			iett.setItemEstruturaNivelIettns(this.getItemEstruturaNivelIettns());
			iett.setBeneficiosIett(this.getBeneficiosIett());
			iett.setOrigemIett(this.getOrigemIett());
			iett.setObjetivoEspecificoIett(this.getObjetivoEspecificoIett());
			iett.setObjetivoGeralIett(this.getObjetivoGeralIett());
			iett.setIndMonitoramentoIett(this.getIndMonitoramentoIett());
			iett.setIndCriticaIett(this.getIndCriticaIett());
			iett.setIndAtivoIett(this.getIndAtivoIett());
			iett.setIndExclusaoPosHistorico(this.getIndExclusaoPosHistorico());

			return iett;

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}

	}

        /**
         *
         * @param lista
         * @return
         */
        public List<ItemEstruturaIett> geraListaHistorico(List<ItemEstruturaIett> lista) {

		return null;
	}

        /**
         *
         * @param data
         * @return
         */
        public List listaObjetoSerializaNoDia(Date data) {
		// TODO Auto-generated method stub
		return null;
	}

        /**
         *
         * @return
         */
        public Long getIdHistorico() {
		return idHistorico;
	}

        /**
         *
         * @param idHistorico
         */
        public void setIdHistorico(Long idHistorico) {
		this.idHistorico = idHistorico;
	}

        /**
         *
         * @return
         */
        public List getAtributoEstrutura() {
		return atributoEstrutura;
	}

        /**
         *
         * @param atributoEstrutura
         */
        public void setAtributoEstrutura(List atributoEstrutura) {
		this.atributoEstrutura = atributoEstrutura;
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
        public SisGrupoAtributoSga getSisGrupoAtributoNivelPlanejamento() {
		return sisGrupoAtributoNivelPlanejamento;
	}

        /**
         *
         * @param sisGrupoAtributoNivelPlanejamento
         */
        public void setSisGrupoAtributoNivelPlanejamento(SisGrupoAtributoSga sisGrupoAtributoNivelPlanejamento) {
		this.sisGrupoAtributoNivelPlanejamento = sisGrupoAtributoNivelPlanejamento;
	}

}
