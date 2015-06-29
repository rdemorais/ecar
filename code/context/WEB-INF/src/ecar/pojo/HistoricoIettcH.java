package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_iettch
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 07/05/2007
 * @version 0.2, 28/05/2007
 */
public class HistoricoIettcH implements Serializable {


	private static final long serialVersionUID = -6828084790684462380L;
	
	/** cod_iettch - Chave Primaria (PK) */
	private Long codIettCH;
	
    /** data_ult_manutencao */
	private Date dataUltManutencao;	

	/** cod_cri - Chave estrangeira (FK) - Tabela <tb_criterio_cri> */
	private CriterioCri criterioCri;
	
    /** COD_IETT - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;	  
	  
    /** COD_USU_MANUTENCAO - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;
    
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;
    
    /**
     *
     */
    public HistoricoIettcH() {}

    /**
     *
     * @return
     */
    public Long getCodIettCH() {
		return codIettCH;
	}

        /**
         *
         * @param codIettCH
         */
        public void setCodIettCH(Long codIettCH) {
		this.codIettCH = codIettCH;
	}

        /**
         *
         * @return
         */
        public CriterioCri getCriterioCri() {
		return criterioCri;
	}

        /**
         *
         * @param criterioCri
         */
        public void setCriterioCri(CriterioCri criterioCri) {
		this.criterioCri = criterioCri;
	}

        /**
         *
         * @return
         */
        public Date getDataUltManutencao() {
		return dataUltManutencao;
	}

        /**
         *
         * @param dataUltManutencao
         */
        public void setDataUltManutencao(Date dataUltManutencao) {
		this.dataUltManutencao = dataUltManutencao;
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
