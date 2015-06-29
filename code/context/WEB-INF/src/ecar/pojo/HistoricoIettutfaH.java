package ecar.pojo;

import java.io.Serializable;
import java.util.Date;


/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_efiech
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 09/05/2007
 * @version 0.2, 28/05/2007
 */
public class HistoricoIettutfaH implements Serializable {


	private static final long serialVersionUID = 956145047252172293L;

	/** cod_iettutfah - Chave Primaria (PK) */
	private Long codIettutfaH;
	
    /** COD_IETT - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;	  
	 
    /** cod_usu - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
	private UsuarioUsu usuarioUsu;
	
    /** cod_tpfa - Chave estrangeira (FK) - Tabela <tb_tipo_func_acomp_tpfa> */
	private TipoFuncAcompTpfa tipoFuncAcompTpfa;	
    
    /** data_ult_manutencao - Data da ultima alteracao*/
    private Date dataUltManutencao;	
	
    /** COD_USU_MANUTENCAO - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;
    	
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;
    
    /**
     *
     */
    public HistoricoIettutfaH() {}

    /**
     *
     * @return
     */
    public Long getCodIettutfaH() {
		return codIettutfaH;
	}

    /**
     *
     * @param codIettutfaH
     */
    public void setCodIettutfaH(Long codIettutfaH) {
		this.codIettutfaH = codIettutfaH;
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
