package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_iettlh
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 08/05/2007
 * @version 0.2, 28/05/2007
 */
public class HistoricoIettlH implements Serializable {


	private static final long serialVersionUID = 6054938153453536522L;
	
	/** cod_iettlh - Chave Primaria (PK) */
	private Long codIettLH;
	
    /** COD_IETT - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;

    /** cod_lit - Chave estrangeira (FK) - Tabela <tb_local_item_lit> */    
	private LocalItemLit localItemLit;
	
	/** data_inclusao_iettl */
	private Date dataInclusaoIettl;
	
	/** cod_usu_manutencao */
	private UsuarioUsu usuManutencao;
	
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;	
	
    /**
     *
     */
    public HistoricoIettlH() { }

    /**
     *
     * @return
     */
    public Long getCodIettLH() {
		return codIettLH;
	}

    /**
     *
     * @param codIettLH
     */
    public void setCodIettLH(Long codIettLH) {
		this.codIettLH = codIettLH;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoIettl() {
		return dataInclusaoIettl;
	}

        /**
         *
         * @param dataInclusaoIettl
         */
        public void setDataInclusaoIettl(Date dataInclusaoIettl) {
		this.dataInclusaoIettl = dataInclusaoIettl;
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
        public LocalItemLit getLocalItemLit() {
		return localItemLit;
	}

        /**
         *
         * @param localItemLit
         */
        public void setLocalItemLit(LocalItemLit localItemLit) {
		this.localItemLit = localItemLit;
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
