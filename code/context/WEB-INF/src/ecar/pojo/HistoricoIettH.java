package ecar.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
  * Classe responsavel para armazenar dados da tabela tb_historico_ietteh
  * - Modificado para comportar historico
  * 
  * @author rogerio, gabriel
  * @since 0.1, 14/05/2007
  * @version 0.2, 28/05/2007
  */
public class HistoricoIettH implements Serializable {


	private static final long serialVersionUID = -6518158770349660239L;

	/** cod_ietth - Chave Primaria (PK) */
	private Long codIettH;
	
    /** COD_IETT - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;
    
    /** cod_sit - Chave estrangeira (FK) - Tabela <tb_situacao_sit> */
	private SituacaoSit situacaoSit;
	
    /** cod_are - Chave estrangeira (FK) - Tabela <tb_area_are> */
	private AreaAre areaAre;

    /** cod_sare - Chave estrangeira (FK) - Tabela <tb_sub_area_sare> */
	private SubAreaSare subAreaSare;
	
    /** cod_uo - Chave estrangeira (FK) - Tabela <tb_unidade_orcamentaria_uo> */	
	private UnidadeOrcamentariaUO unidadeOrcamentariaUO;
	
	/** val_previsto_futuro_iett */
	private BigDecimal valPrevistoFuturoIett;
	
	/** ind_bloq_planejamento_iett */
	private String indBloqPlanejamentoIett;
	
	/** beneficios_iett */
	private String beneficiosIett;
	
	/** origem_iett */
	private String origemIett;
	
	/** objetivo_especifico_iett */
	private String objetivoEspecificoIett;
	
	/** objetivo_geral_iett */
	private String objetivoGeralIett;
	
	/** ind_monitoramento_iett */
	private String indMonitoramentoIett;
	
	/** ind_critica_iett */
	private String indCriticaIett;
	
	/** data_inicio_monitoramento_iett */
	private Date dataInicioMonitoramentoIett;
	
	/** data_termino_iett */
	private Date dataTerminoIett;
	
	/** data_inicio_iett */
	private Date dataInicioIett;
	
	/** ind_ativo_iett */
	private String indAtivoIett;
	
	/** data_ult_manutencao_iett */
	private Date dataUltManutencaoIett;
	
	/** data_inclusao_iett */
	private Date dataInclusaoIett;
	
	/** descricao_iett */
	private String descricaoIett;
	
	/** sigla_iett */
	private String siglaIett;
	
    /** cod_usu_inc_iett - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
	private UsuarioUsu usuarioUsuByCodUsuIncIett;

    /** cod_usu_ult_manut_iett - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuarioUsuByCodUsuUltManutIett;

    /** cod_orgao_responsavel1_iett - Chave estrangeira (FK) - Tabela <tb_orgao_org> */
	private OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iett;

    /** cod_orgao_responsavel2_iett - Chave estrangeira (FK) - Tabela <tb_orgao_org> */
	private OrgaoOrg orgaoOrgByCodOrgaoResponsavel2Iett;
	
    /** cod_iett_pai - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
	private ItemEstruturaIett itemEstruturaIettPai;

    /** cod_prcd_iett - Chave estrangeira (FK) - Tabela <tb_periodicidade_prdc> */
	private PeriodicidadePrdc periodicidadePrdc;
	
	/** descricao_r5 */
	private String descricaoR5;
	
	/** descricao_r4 */	
	private String descricaoR4;
	
	/** descricao_r3 */	
	private String descricaoR3;
	
	/** descricao_r2 */	
	private String descricaoR2;
	
	/** descricao_r1 */	
	private String descricaoR1;
	
	/** data_r5 */
	private Date dataR5;
	
	/** data_r4 */	
	private Date dataR4;
	
	/** data_r3 */		
	private Date dataR3;
	
	/** data_r2 */	
	private Date dataR2;
	
	/** data_r1 */	
	private Date dataR1;
	
	/** nome_iett */
    private String nomeIett;
    
    /** nivel_iett */
    private Integer nivelIett;

    /** cod_ett - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
	private EstruturaEtt estruturaEtt;
	
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;
	    
    /**
     *
     */
    public HistoricoIettH() { }

    /**
     *
     * @return
     */
    public AreaAre getAreaAre() {
		return areaAre;
	}

        /**
         *
         * @param areaAre
         */
        public void setAreaAre(AreaAre areaAre) {
		this.areaAre = areaAre;
	}

        /**
         *
         * @return
         */
        public String getBeneficiosIett() {
		return beneficiosIett;
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
        public Long getCodIettH() {
		return codIettH;
	}

        /**
         *
         * @param codIettH
         */
        public void setCodIettH(Long codIettH) {
		this.codIettH = codIettH;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoIett() {
		return dataInclusaoIett;
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
        public Date getDataInicioIett() {
		return dataInicioIett;
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
        public Date getDataInicioMonitoramentoIett() {
		return dataInicioMonitoramentoIett;
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
        public Date getDataR1() {
		return dataR1;
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
        public Date getDataR2() {
		return dataR2;
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
        public Date getDataR3() {
		return dataR3;
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
        public Date getDataR4() {
		return dataR4;
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
        public Date getDataR5() {
		return dataR5;
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
        public Date getDataTerminoIett() {
		return dataTerminoIett;
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
        public Date getDataUltManutencaoIett() {
		return dataUltManutencaoIett;
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
        public String getDescricaoIett() {
		return descricaoIett;
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
        public String getDescricaoR1() {
		return descricaoR1;
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
        public String getDescricaoR2() {
		return descricaoR2;
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
        public String getDescricaoR3() {
		return descricaoR3;
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
        public String getDescricaoR4() {
		return descricaoR4;
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
        public String getDescricaoR5() {
		return descricaoR5;
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
        public EstruturaEtt getEstruturaEtt() {
		return estruturaEtt;
	}

        /**
         *
         * @param estruturaEtt
         */
        public void setEstruturaEtt(EstruturaEtt estruturaEtt) {
		this.estruturaEtt = estruturaEtt;
	}

        /**
         *
         * @return
         */
        public HistoricoMaster getHistoricoMaster() {
		return historicoMaster;
	}

        /**
         *
         * @param historicoMaster
         */
        public void setHistoricoMaster(HistoricoMaster historicoMaster) {
		this.historicoMaster = historicoMaster;
	}

        /**
         *
         * @return
         */
        public String getIndAtivoIett() {
		return indAtivoIett;
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
        public String getIndBloqPlanejamentoIett() {
		return indBloqPlanejamentoIett;
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
        public String getIndCriticaIett() {
		return indCriticaIett;
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
        public String getIndMonitoramentoIett() {
		return indMonitoramentoIett;
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
        public ItemEstruturaIett getItemEstruturaIett() {
		return itemEstruturaIett;
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
        public ItemEstruturaIett getItemEstruturaIettPai() {
		return itemEstruturaIettPai;
	}

        /**
         *
         * @param itemEstruturaIettPai
         */
        public void setItemEstruturaIettPai(ItemEstruturaIett itemEstruturaIettPai) {
		this.itemEstruturaIettPai = itemEstruturaIettPai;
	}

        /**
         *
         * @return
         */
        public Integer getNivelIett() {
		return nivelIett;
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
        public String getNomeIett() {
		return nomeIett;
	}

        /**
         *
         * @param nomeIett
         */
        public void setNomeIett(String nomeIett) {
		this.nomeIett = nomeIett;
	}

        /**
         *
         * @return
         */
        public String getObjetivoEspecificoIett() {
		return objetivoEspecificoIett;
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
		return objetivoGeralIett;
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
        public OrgaoOrg getOrgaoOrgByCodOrgaoResponsavel1Iett() {
		return orgaoOrgByCodOrgaoResponsavel1Iett;
	}

        /**
         *
         * @param orgaoOrgByCodOrgaoResponsavel1Iett
         */
        public void setOrgaoOrgByCodOrgaoResponsavel1Iett(
			OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iett) {
		this.orgaoOrgByCodOrgaoResponsavel1Iett = orgaoOrgByCodOrgaoResponsavel1Iett;
	}

        /**
         *
         * @return
         */
        public OrgaoOrg getOrgaoOrgByCodOrgaoResponsavel2Iett() {
		return orgaoOrgByCodOrgaoResponsavel2Iett;
	}

        /**
         *
         * @param orgaoOrgByCodOrgaoResponsavel2Iett
         */
        public void setOrgaoOrgByCodOrgaoResponsavel2Iett(
			OrgaoOrg orgaoOrgByCodOrgaoResponsavel2Iett) {
		this.orgaoOrgByCodOrgaoResponsavel2Iett = orgaoOrgByCodOrgaoResponsavel2Iett;
	}

        /**
         *
         * @return
         */
        public String getOrigemIett() {
		return origemIett;
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
        public PeriodicidadePrdc getPeriodicidadePrdc() {
		return periodicidadePrdc;
	}

        /**
         *
         * @param periodicidadePrdc
         */
        public void setPeriodicidadePrdc(PeriodicidadePrdc periodicidadePrdc) {
		this.periodicidadePrdc = periodicidadePrdc;
	}

        /**
         *
         * @return
         */
        public String getSiglaIett() {
		return siglaIett;
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
        public SubAreaSare getSubAreaSare() {
		return subAreaSare;
	}

        /**
         *
         * @param subAreaSare
         */
        public void setSubAreaSare(SubAreaSare subAreaSare) {
		this.subAreaSare = subAreaSare;
	}

        /**
         *
         * @return
         */
        public UnidadeOrcamentariaUO getUnidadeOrcamentariaUO() {
		return unidadeOrcamentariaUO;
	}

        /**
         *
         * @param unidadeOrcamentariaUO
         */
        public void setUnidadeOrcamentariaUO(UnidadeOrcamentariaUO unidadeOrcamentariaUO) {
		this.unidadeOrcamentariaUO = unidadeOrcamentariaUO;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsuByCodUsuIncIett() {
		return usuarioUsuByCodUsuIncIett;
	}

        /**
         *
         * @param usuarioUsuByCodUsuIncIett
         */
        public void setUsuarioUsuByCodUsuIncIett(UsuarioUsu usuarioUsuByCodUsuIncIett) {
		this.usuarioUsuByCodUsuIncIett = usuarioUsuByCodUsuIncIett;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsuByCodUsuUltManutIett() {
		return usuarioUsuByCodUsuUltManutIett;
	}

        /**
         *
         * @param usuarioUsuByCodUsuUltManutIett
         */
        public void setUsuarioUsuByCodUsuUltManutIett(
			UsuarioUsu usuarioUsuByCodUsuUltManutIett) {
		this.usuarioUsuByCodUsuUltManutIett = usuarioUsuByCodUsuUltManutIett;
	}

        /**
         *
         * @return
         */
        public BigDecimal getValPrevistoFuturoIett() {
		return valPrevistoFuturoIett;
	}

        /**
         *
         * @param valPrevistoFuturoIett
         */
        public void setValPrevistoFuturoIett(BigDecimal valPrevistoFuturoIett) {
		this.valPrevistoFuturoIett = valPrevistoFuturoIett;
	}
    
    
    
}
