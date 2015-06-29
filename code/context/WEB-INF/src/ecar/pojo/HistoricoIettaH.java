package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_efiech
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 15/05/2007
 * @version 0.2, 28/05/2007
 */
public class HistoricoIettaH implements Serializable {


	
	private static final long serialVersionUID = 3409362551170960346L;

	/** cod_iettah - Chave Primaria (PK) */
	private Long codIettaH;

	/** ind_ativo_ietta */
	private String indAtivoIetta;

	/** data_inclusao_ietta */
	private Date dataInclusaoIetta;
	
	/** data_ietta */
	private Date dataIetta;
	
	/** descricao_ietta */
	private String descricaoIetta;
	
    /** cod_ietta - Chave estrangeira (FK) - Tabela <tb_item_estrut_acao_ietta> */
	private ItemEstrutAcaoIetta itemEstrutAcaoIetta;

    /** cod_iett - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
	private ItemEstruturaIett itemEstruturaIett;

    /** cod_usu - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
	private UsuarioUsu usuarioUsu;
	
    /** cod_usu_manutencao - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */	
	private UsuarioUsu usuManutencao;
	
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;
	
    /**
     *
     */
    public HistoricoIettaH() { }

    /**
     *
     * @return
     */
    public Long getCodIettaH() {
		return codIettaH;
	}

        /**
         *
         * @param codIettaH
         */
        public void setCodIettaH(Long codIettaH) {
		this.codIettaH = codIettaH;
	}

        /**
         *
         * @return
         */
        public Date getDataIetta() {
		return dataIetta;
	}

        /**
         *
         * @param dataIetta
         */
        public void setDataIetta(Date dataIetta) {
		this.dataIetta = dataIetta;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoIetta() {
		return dataInclusaoIetta;
	}

        /**
         *
         * @param dataInclusaoIetta
         */
        public void setDataInclusaoIetta(Date dataInclusaoIetta) {
		this.dataInclusaoIetta = dataInclusaoIetta;
	}

        /**
         *
         * @return
         */
        public String getDescricaoIetta() {
		return descricaoIetta;
	}

        /**
         *
         * @param descricaoIetta
         */
        public void setDescricaoIetta(String descricaoIetta) {
		this.descricaoIetta = descricaoIetta;
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
        public String getIndAtivoIetta() {
		return indAtivoIetta;
	}

        /**
         *
         * @param indAtivoIetta
         */
        public void setIndAtivoIetta(String indAtivoIetta) {
		this.indAtivoIetta = indAtivoIetta;
	}

        /**
         *
         * @return
         */
        public ItemEstrutAcaoIetta getItemEstrutAcaoIetta() {
		return itemEstrutAcaoIetta;
	}

        /**
         *
         * @param itemEstrutAcaoIetta
         */
        public void setItemEstrutAcaoIetta(ItemEstrutAcaoIetta itemEstrutAcaoIetta) {
		this.itemEstrutAcaoIetta = itemEstrutAcaoIetta;
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
