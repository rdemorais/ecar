package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_ietteh
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 08/05/2007
 * @version 0.2, 28/05/2007
 */
public class HistoricoIetteH implements Serializable {


	private static final long serialVersionUID = 5303315170214139465L;

	/** cod_ietteh - Chave Primaria (PK) */
	private Long codIetteH;
	
	/** data_ult_manutencao_iette */
	private Date dataUltManutencaoIette;

	/** data_fim_iette */	
	private Date dataFimIette;
	
	/** data_inicio_iette */	
	private Date dataInicioIette;
	
	/** descricao_iette */	
	private String descricaoIette;
	
    /** cod_ent - Chave estrangeira (FK) - Tabela <tb_entidade_ent> */
    private EntidadeEnt entidadeEnt;
    
    /** cod_tpp - Chave estrangeira (FK) - Tabela <tb_tipo_participacao_tpp> */
    private TipoParticipacaoTpp tipoParticipacaoTpp;
    
    /** COD_IETT - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;    
    
    /** COD_USU_MANUTENCAO - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;
    
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;
    
    /**
     *
     */
    public HistoricoIetteH() {}

    /**
     *
     * @return
     */
    public Long getCodIetteH() {
		return codIetteH;
	}

    /**
     *
     * @param codIetteH
     */
    public void setCodIetteH(Long codIetteH) {
		this.codIetteH = codIetteH;
	}

        /**
         *
         * @return
         */
        public Date getDataFimIette() {
		return dataFimIette;
	}

        /**
         *
         * @param dataFimIette
         */
        public void setDataFimIette(Date dataFimIette) {
		this.dataFimIette = dataFimIette;
	}

        /**
         *
         * @return
         */
        public Date getDataInicioIette() {
		return dataInicioIette;
	}

        /**
         *
         * @param dataInicioIette
         */
        public void setDataInicioIette(Date dataInicioIette) {
		this.dataInicioIette = dataInicioIette;
	}

        /**
         *
         * @return
         */
        public Date getDataUltManutencaoIette() {
		return dataUltManutencaoIette;
	}

        /**
         *
         * @param dataUltManutencaoIette
         */
        public void setDataUltManutencaoIette(Date dataUltManutencaoIette) {
		this.dataUltManutencaoIette = dataUltManutencaoIette;
	}

        /**
         *
         * @return
         */
        public String getDescricaoIette() {
		return descricaoIette;
	}

        /**
         *
         * @param descricaoIette
         */
        public void setDescricaoIette(String descricaoIette) {
		this.descricaoIette = descricaoIette;
	}

        /**
         *
         * @return
         */
        public EntidadeEnt getEntidadeEnt() {
		return entidadeEnt;
	}

        /**
         *
         * @param entidadeEnt
         */
        public void setEntidadeEnt(EntidadeEnt entidadeEnt) {
		this.entidadeEnt = entidadeEnt;
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
        public TipoParticipacaoTpp getTipoParticipacaoTpp() {
		return tipoParticipacaoTpp;
	}

        /**
         *
         * @param tipoParticipacaoTpp
         */
        public void setTipoParticipacaoTpp(TipoParticipacaoTpp tipoParticipacaoTpp) {
		this.tipoParticipacaoTpp = tipoParticipacaoTpp;
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
