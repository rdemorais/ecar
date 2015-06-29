package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_iettsatbh
 * - Modificado para comportar historico
 * 
 * @author gabriel
 * @since 0.1, 28/05/2007
 */
public class HistoricoIettSatbH implements Serializable {

	private static final long serialVersionUID = -2072478213406272529L;

	/** cod_iettsatbh - Chave Primaria (PK) */
    private Long codIettsatbh;	
	
    /** COD_IETT - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;
    
    /** cod_satb - Chave estrangeira (FK) - Tabela <tb_sis_atributo_satb> */
    private SisAtributoSatb sisAtributoSatb;
    
    /** informacao */
    private String informacao;
    
    /** COD_USU_MANUTENCAO - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;
    
    /** data_ult_manutencao - Data da ultima alteracao*/
    private Date dataUltManutencao;
    
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;

    /**
     *
     */
    public HistoricoIettSatbH() {
	}
    
    /**
     *
     * @return
     */
    public Long getCodIettsatbh() {
		return codIettsatbh;
	}

        /**
         *
         * @param codIettsatbh
         */
        public void setCodIettsatbh(Long codIettsatbh) {
		this.codIettsatbh = codIettsatbh;
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
        public String getInformacao() {
		return informacao;
	}

        /**
         *
         * @param informacao
         */
        public void setInformacao(String informacao) {
		this.informacao = informacao;
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
        public SisAtributoSatb getSisAtributoSatb() {
		return sisAtributoSatb;
	}

        /**
         *
         * @param sisAtributoSatb
         */
        public void setSisAtributoSatb(SisAtributoSatb sisAtributoSatb) {
		this.sisAtributoSatb = sisAtributoSatb;
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
