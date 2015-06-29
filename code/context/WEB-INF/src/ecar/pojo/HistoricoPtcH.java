package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_ptch
 * - Modificado para comportar historico
 * 
 * @author Davi Gadêlha
 * @since 0.1, 28/07/2008
 */
public class HistoricoPtcH implements Serializable{

	/** cod_ptch - Chave Primaria (PK) */
	private Long codPtcH;
	
	/** cod_ptc - Chave estrangeira (FK) - Tabela <tb_ponto_critico_ptc> */
    private PontoCriticoPtc pontoCriticoPtc;
    
    /** ind_ativo_ptc */
    private String indAtivoPtc;
    
    /** data_inclusao_ptc */
    private Date dataInclusaoPtc;

    /** data_solucao_ptc */
    private Date dataSolucaoPtc;

    /** descricao_solucao_ptc */
    private String descricaoSolucaoPtc;

    /** ind_ambito_interno_gov_ptc */
    private String indAmbitoInternoGovPtc;

    /** data_limite_ptc */
    private Date dataLimitePtc;

    /** data_identificacao_ptc */
    private Date dataIdentificacaoPtc;
    
    /** data_ult_manut_ptc */
    private Date dataUltManutencaoPtc;
    
    /** descricao_ptc */
    private String descricaoPtc;

    /** cod_iett - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;
    
    /** cod_usu_responsavel - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuarioUsu;
    
    /** cod_usu_inclusao - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuarioUsuInclusao;
        
    /** cod_usu_ult_manut_ptc - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuarioUsuByCodUsuUltManutPtc;
    
    /** cod_satb_tipo - Chave estrangeira (FK) - Tabela <tb_sis_atributo_satb> */
    private SisAtributoSatb sisAtributoTipo;
    
    /** cod_arel - Chave estrangeira (FK) - Tabela <tb_sis_atributo_satb> */
    private AcompRelatorioArel acompRelatorioArel;
	
	/** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;
    
    /** ind_excluido_ptc */
    private String indExcluidoPtc;

    /** cod_cor_ptch - Chave estrangeira (FK) - Tabela <tb_historico_cor_ptch> **/ 
    private Set historicoCorPtcH;
    
    /**
     *
     */
    public HistoricoPtcH() { }
    
    /**
     *
     * @return
     */
    public Long getCodPtcH() {
		return codPtcH;
	}

        /**
         *
         * @param codPtcH
         */
        public void setCodPtcH(Long codPtcH) {
		this.codPtcH = codPtcH;
	}

        /**
         *
         * @return
         */
        public PontoCriticoPtc getPontoCriticoPtc() {
		return pontoCriticoPtc;
	}

        /**
         *
         * @param pontoCriticoPtc
         */
        public void setPontoCriticoPtc(PontoCriticoPtc pontoCriticoPtc) {
		this.pontoCriticoPtc = pontoCriticoPtc;
	}

        /**
         *
         * @return
         */
        public String getIndAtivoPtc() {
		return indAtivoPtc;
	}

        /**
         *
         * @param indAtivoPtc
         */
        public void setIndAtivoPtc(String indAtivoPtc) {
		this.indAtivoPtc = indAtivoPtc;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoPtc() {
		return dataInclusaoPtc;
	}

        /**
         *
         * @param dataInclusaoPtc
         */
        public void setDataInclusaoPtc(Date dataInclusaoPtc) {
		this.dataInclusaoPtc = dataInclusaoPtc;
	}

        /**
         *
         * @return
         */
        public Date getDataSolucaoPtc() {
		return dataSolucaoPtc;
	}

        /**
         *
         * @param dataSolucaoPtc
         */
        public void setDataSolucaoPtc(Date dataSolucaoPtc) {
		this.dataSolucaoPtc = dataSolucaoPtc;
	}

        /**
         *
         * @return
         */
        public String getDescricaoSolucaoPtc() {
		return descricaoSolucaoPtc;
	}

        /**
         *
         * @param descricaoSolucaoPtc
         */
        public void setDescricaoSolucaoPtc(String descricaoSolucaoPtc) {
		this.descricaoSolucaoPtc = descricaoSolucaoPtc;
	}

        /**
         *
         * @return
         */
        public String getIndAmbitoInternoGovPtc() {
		return indAmbitoInternoGovPtc;
	}

        /**
         *
         * @param indAmbitoInternoGovPtc
         */
        public void setIndAmbitoInternoGovPtc(String indAmbitoInternoGovPtc) {
		this.indAmbitoInternoGovPtc = indAmbitoInternoGovPtc;
	}

        /**
         *
         * @return
         */
        public Date getDataLimitePtc() {
		return dataLimitePtc;
	}

        /**
         *
         * @param dataLimitePtc
         */
        public void setDataLimitePtc(Date dataLimitePtc) {
		this.dataLimitePtc = dataLimitePtc;
	}

        /**
         *
         * @return
         */
        public Date getDataIdentificacaoPtc() {
		return dataIdentificacaoPtc;
	}

        /**
         *
         * @param dataIdentificacaoPtc
         */
        public void setDataIdentificacaoPtc(Date dataIdentificacaoPtc) {
		this.dataIdentificacaoPtc = dataIdentificacaoPtc;
	}

        /**
         *
         * @return
         */
        public String getDescricaoPtc() {
		return descricaoPtc;
	}

        /**
         *
         * @param descricaoPtc
         */
        public void setDescricaoPtc(String descricaoPtc) {
		this.descricaoPtc = descricaoPtc;
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
        public UsuarioUsu getUsuarioUsu() {
		return usuarioUsu;
	}

        /**
         *
         * @param usuarioUsu
         */
        public void setUsuarioUsu(UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsuInclusao() {
		return usuarioUsuInclusao;
	}

        /**
         *
         * @param usuarioUsuInclusao
         */
        public void setUsuarioUsuInclusao(UsuarioUsu usuarioUsuInclusao) {
		this.usuarioUsuInclusao = usuarioUsuInclusao;
	}

        /**
         *
         * @return
         */
        public SisAtributoSatb getSisAtributoTipo() {
		return sisAtributoTipo;
	}

        /**
         *
         * @param sisAtributoTipo
         */
        public void setSisAtributoTipo(SisAtributoSatb sisAtributoTipo) {
		this.sisAtributoTipo = sisAtributoTipo;
	}

        /**
         *
         * @return
         */
        public AcompRelatorioArel getAcompRelatorioArel() {
		return acompRelatorioArel;
	}

        /**
         *
         * @param acompRelatorioArel
         */
        public void setAcompRelatorioArel(AcompRelatorioArel acompRelatorioArel) {
		this.acompRelatorioArel = acompRelatorioArel;
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
        public UsuarioUsu getUsuarioUsuByCodUsuUltManutPtc() {
		return usuarioUsuByCodUsuUltManutPtc;
	}

        /**
         *
         * @param usuarioUsuByCodUsuUltManutPtc
         */
        public void setUsuarioUsuByCodUsuUltManutPtc(
			UsuarioUsu usuarioUsuByCodUsuUltManutPtc) {
		this.usuarioUsuByCodUsuUltManutPtc = usuarioUsuByCodUsuUltManutPtc;
	}

        /**
         *
         * @return
         */
        public Date getDataUltManutencaoPtc() {
		return dataUltManutencaoPtc;
	}

        /**
         *
         * @param dataUltManutencaoPtc
         */
        public void setDataUltManutencaoPtc(Date dataUltManutencaoPtc) {
		this.dataUltManutencaoPtc = dataUltManutencaoPtc;
	}

        /**
         *
         * @return
         */
        public String getIndExcluidoPtc() {
		return indExcluidoPtc;
	}

        /**
         *
         * @param indExcluidoPtc
         */
        public void setIndExcluidoPtc(String indExcluidoPtc) {
		this.indExcluidoPtc = indExcluidoPtc;
	}
	 
        /**
         *
         * @return
         */
        public Set getHistoricoCorPtcH() {
		return historicoCorPtcH;
	}

        /**
         *
         * @param historicoCorPtcH
         */
        public void setHistoricoCorPtcH(Set historicoCorPtcH) {
		this.historicoCorPtcH = historicoCorPtcH;
	}
}
