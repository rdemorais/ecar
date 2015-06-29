package ecar.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_iettbh
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 08/05/2007
 * @version 0.2, 28/05/2007
 */
public class HistoricoIettbH implements Serializable {


	private static final long serialVersionUID = -4034041596982194690L;
	
	/** cod_iettbh - Chave Primaria (PK) */
	private Long codIettbH;
	
    /** data_ult_manutencao_iettb */
	private Date dataUltManutencaoIettb;
	
	/** comentario_iettb */
	private String comentarioIettb;
	
	/** qtd_prevista_iettb */
	private BigDecimal qtdPrevistaIettb;
	
    /** cod_bnf - Chave estrangeira (FK) - Tabela <tb_beneficiario_bnf> */
	private BeneficiarioBnf beneficiarioBnf;

    /** COD_IETT - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;
    
    /** COD_USU_MANUTENCAO - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;
    
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;

    /**
     *
     */
    public HistoricoIettbH() {
	}
    
    /**
     *
     * @return
     */
    public BeneficiarioBnf getBeneficiarioBnf() {
		return beneficiarioBnf;
	}

        /**
         *
         * @param beneficiarioBnf
         */
        public void setBeneficiarioBnf(BeneficiarioBnf beneficiarioBnf) {
		this.beneficiarioBnf = beneficiarioBnf;
	}

        /**
         *
         * @return
         */
        public Long getCodIettbH() {
		return codIettbH;
	}

        /**
         *
         * @param codIettbH
         */
        public void setCodIettbH(Long codIettbH) {
		this.codIettbH = codIettbH;
	}

        /**
         *
         * @return
         */
        public String getComentarioIettb() {
		return comentarioIettb;
	}

        /**
         *
         * @param comentarioIettb
         */
        public void setComentarioIettb(String comentarioIettb) {
		this.comentarioIettb = comentarioIettb;
	}

        /**
         *
         * @return
         */
        public Date getDataUltManutencaoIettb() {
		return dataUltManutencaoIettb;
	}

        /**
         *
         * @param dataUltManutencaoIettb
         */
        public void setDataUltManutencaoIettb(Date dataUltManutencaoIettb) {
		this.dataUltManutencaoIettb = dataUltManutencaoIettb;
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
        public BigDecimal getQtdPrevistaIettb() {
		return qtdPrevistaIettb;
	}

        /**
         *
         * @param qtdPrevistaIettb
         */
        public void setQtdPrevistaIettb(BigDecimal qtdPrevistaIettb) {
		this.qtdPrevistaIettb = qtdPrevistaIettb;
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
