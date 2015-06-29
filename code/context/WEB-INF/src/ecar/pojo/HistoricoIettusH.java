package ecar.pojo;

import java.io.Serializable;
import java.util.Date;



/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_iettush
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 11/05/2007
 * @version 0.2, 28/05/2007
 */
public class HistoricoIettusH implements Serializable {


	private static final long serialVersionUID = 8901465621134361315L;

	/** cod_iettush - Chave Primaria (PK) */
	private Long codIettusH;
	
    /** cod_tpfa - Chave estrangeira (FK) - Tabela <tb_tipo_func_acomp_tpfa> */
	private TipoFuncAcompTpfa tipoFuncAcompTpfa;
	
    /** cod_usu - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
	private UsuarioUsu usuarioUsu;
	
	/** cod_iett - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
	private ItemEstruturaIett itemEstruturaIett;
	
	/** data_inclusao_iettus */
	private Date dataInclusaoIettus;
	
	/** cod_iett_orig - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
	private ItemEstruturaIett itemEstruturaIettOrigem;
	
	/** cod_tp_perm_iettus */
	private String codTpPermIettus;
	
	/** cod_atb */
	private Long cod_atb;
	
	/** ind_leitura_iettus */
    private String indLeituraIettus;
    
    /** ind_edicao_iettus */
	private String indEdicaoIettus;
	
	/** ind_excluir_iettus */
    private String indExcluirIettus;	
	
    /** ind_ativ_monit_iettus */
	private String indAtivMonitIettus;
	
	/** ind_desat_monit_iettus */
	private String indDesatMonitIettus;

	/** ind_bloq_plan_iettus */
	private String indBloqPlanIettus;
	
	/** ind_desbl_plan_iettus */
	private String indDesblPlanIettus;
	
	/** ind_inf_andamento_iettus */
	private String indInfAndamentoIettus;
	
	/** ind_emite_pos_iettus */
	private String indEmitePosIettus;
	
	/** ind_prox_nivel_iettus */
	private String indProxNivelIettus;
	
    /** cod_usu_manutencao - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;

    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;
    
    private String indLeituraParecerIettus;
    
    /**
     *
     * @return
     */
    public String getIndLeituraParecerIettus() {
		return indLeituraParecerIettus;
	}

    /**
     *
     * @param indLeituraParecerIettus
     */
    public void setIndLeituraParecerIettus(String indLeituraParecerIettus) {
		this.indLeituraParecerIettus = indLeituraParecerIettus;
	}

    /**
     *
     */
    public HistoricoIettusH() {
	
	}

        /**
         *
         * @return
         */
        public Long getCod_atb() {
		return cod_atb;
	}

        /**
         *
         * @param cod_atb
         */
        public void setCod_atb(Long cod_atb) {
		this.cod_atb = cod_atb;
	}

        /**
         *
         * @return
         */
        public Long getCodIettusH() {
		return codIettusH;
	}

        /**
         *
         * @param codIettusH
         */
        public void setCodIettusH(Long codIettusH) {
		this.codIettusH = codIettusH;
	}

        /**
         *
         * @return
         */
        public String getCodTpPermIettus() {
		return codTpPermIettus;
	}

        /**
         *
         * @param codTpPermIettus
         */
        public void setCodTpPermIettus(String codTpPermIettus) {
		this.codTpPermIettus = codTpPermIettus;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoIettus() {
		return dataInclusaoIettus;
	}

        /**
         *
         * @param dataInclusaoIettus
         */
        public void setDataInclusaoIettus(Date dataInclusaoIettus) {
		this.dataInclusaoIettus = dataInclusaoIettus;
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
        public String getIndAtivMonitIettus() {
		return indAtivMonitIettus;
	}

        /**
         *
         * @param indAtivMonitIettus
         */
        public void setIndAtivMonitIettus(String indAtivMonitIettus) {
		this.indAtivMonitIettus = indAtivMonitIettus;
	}

        /**
         *
         * @return
         */
        public String getIndBloqPlanIettus() {
		return indBloqPlanIettus;
	}

        /**
         *
         * @param indBloqPlanIettus
         */
        public void setIndBloqPlanIettus(String indBloqPlanIettus) {
		this.indBloqPlanIettus = indBloqPlanIettus;
	}

        /**
         *
         * @return
         */
        public String getIndDesatMonitIettus() {
		return indDesatMonitIettus;
	}

        /**
         *
         * @param indDesatMonitIettus
         */
        public void setIndDesatMonitIettus(String indDesatMonitIettus) {
		this.indDesatMonitIettus = indDesatMonitIettus;
	}

        /**
         *
         * @return
         */
        public String getIndDesblPlanIettus() {
		return indDesblPlanIettus;
	}

        /**
         *
         * @param indDesblPlanIettus
         */
        public void setIndDesblPlanIettus(String indDesblPlanIettus) {
		this.indDesblPlanIettus = indDesblPlanIettus;
	}

        /**
         *
         * @return
         */
        public String getIndEdicaoIettus() {
		return indEdicaoIettus;
	}

        /**
         *
         * @param indEdicaoIettus
         */
        public void setIndEdicaoIettus(String indEdicaoIettus) {
		this.indEdicaoIettus = indEdicaoIettus;
	}

        /**
         *
         * @return
         */
        public String getIndEmitePosIettus() {
		return indEmitePosIettus;
	}

        /**
         *
         * @param indEmitePosIettus
         */
        public void setIndEmitePosIettus(String indEmitePosIettus) {
		this.indEmitePosIettus = indEmitePosIettus;
	}

        /**
         *
         * @return
         */
        public String getIndExcluirIettus() {
		return indExcluirIettus;
	}

        /**
         *
         * @param indExcluirIettus
         */
        public void setIndExcluirIettus(String indExcluirIettus) {
		this.indExcluirIettus = indExcluirIettus;
	}

        /**
         *
         * @return
         */
        public String getIndInfAndamentoIettus() {
		return indInfAndamentoIettus;
	}

        /**
         *
         * @param indInfAndamentoIettus
         */
        public void setIndInfAndamentoIettus(String indInfAndamentoIettus) {
		this.indInfAndamentoIettus = indInfAndamentoIettus;
	}

        /**
         *
         * @return
         */
        public String getIndLeituraIettus() {
		return indLeituraIettus;
	}

        /**
         *
         * @param indLeituraIettus
         */
        public void setIndLeituraIettus(String indLeituraIettus) {
		this.indLeituraIettus = indLeituraIettus;
	}

        /**
         *
         * @return
         */
        public String getIndProxNivelIettus() {
		return indProxNivelIettus;
	}

        /**
         *
         * @param indProxNivelIettus
         */
        public void setIndProxNivelIettus(String indProxNivelIettus) {
		this.indProxNivelIettus = indProxNivelIettus;
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
        public ItemEstruturaIett getItemEstruturaIettOrigem() {
		return itemEstruturaIettOrigem;
	}

        /**
         *
         * @param itemEstruturaIettOrigem
         */
        public void setItemEstruturaIettOrigem(ItemEstruturaIett itemEstruturaIettOrigem) {
		this.itemEstruturaIettOrigem = itemEstruturaIettOrigem;
	}

        /**
         *
         * @return
         */
        public TipoFuncAcompTpfa getTipoFuncAcompTpfa() {
		return tipoFuncAcompTpfa;
	}

        /**
         *
         * @param tipoFuncAcompTpfa
         */
        public void setTipoFuncAcompTpfa(TipoFuncAcompTpfa tipoFuncAcompTpfa) {
		this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
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
        public UsuarioUsu getUsuManutencao() {
		return usuManutencao;
	}

        /**
         *
         * @param usuManutencao
         */
        public void setUsuManutencao(UsuarioUsu usuManutencao) {
		this.usuManutencao = usuManutencao;
	}
	
	
    
}
