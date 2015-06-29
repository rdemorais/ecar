package ecar.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * Classe responsavel para armazenar dados da tabela TB_HISTORICO_EFIEFTH
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 15/05/2007
 * @version 0.2, 24/05/2007
 */
public class HistoricoEfieftH implements Serializable {


	private static final long serialVersionUID = -500791394438807770L;
		
	/** cod_efiefth - Chave Primaria (PK) */
	private Long codEfieftH;
	
	/** data_valor_efieft */
	private Date dataValorEfieft;
	
	/** valor_efieft */
	private BigDecimal valorEfieft;
	
	/** ind_ativo_efieft */
	private String indAtivoEfieft;
	
	/** data_inclusao_efieft */
	private Date dataInclusaoEfieft;
	
    /** COD_IETT - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;
    
    /** COD_FONR - Chave estrangeira (FK) - Tabela <tb_fonte_recurso_fonr> */
    private FonteRecursoFonr fonteRecursoFonr;
    
    /** COD_USU_MANUTENCAO - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;
    
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;
    
    /**
     *
     */
    public HistoricoEfieftH() { }

    /**
     *
     * @return
     */
    public Long getCodEfieftH() {
		return codEfieftH;
	}

    /**
     *
     * @param codEfieftH
     */
    public void setCodEfieftH(Long codEfieftH) {
		this.codEfieftH = codEfieftH;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoEfieft() {
		return dataInclusaoEfieft;
	}

        /**
         *
         * @param dataInclusaoEfieft
         */
        public void setDataInclusaoEfieft(Date dataInclusaoEfieft) {
		this.dataInclusaoEfieft = dataInclusaoEfieft;
	}

        /**
         *
         * @return
         */
        public Date getDataValorEfieft() {
		return dataValorEfieft;
	}

        /**
         *
         * @param dataValorEfieft
         */
        public void setDataValorEfieft(Date dataValorEfieft) {
		this.dataValorEfieft = dataValorEfieft;
	}

        /**
         *
         * @return
         */
        public FonteRecursoFonr getFonteRecursoFonr() {
		return fonteRecursoFonr;
	}

        /**
         *
         * @param fonteRecursoFonr
         */
        public void setFonteRecursoFonr(FonteRecursoFonr fonteRecursoFonr) {
		this.fonteRecursoFonr = fonteRecursoFonr;
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
        public String getIndAtivoEfieft() {
		return indAtivoEfieft;
	}

        /**
         *
         * @param indAtivoEfieft
         */
        public void setIndAtivoEfieft(String indAtivoEfieft) {
		this.indAtivoEfieft = indAtivoEfieft;
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

        /**
         *
         * @return
         */
        public BigDecimal getValorEfieft() {
		return valorEfieft;
	}

        /**
         *
         * @param valorEfieft
         */
        public void setValorEfieft(BigDecimal valorEfieft) {
		this.valorEfieft = valorEfieft;
	}

        
    
}
