package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_iettvh
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 08/05/2007
 * @version 0.2, 29/05/2007
 */
public class HistoricoIettvH implements Serializable {


	private static final long serialVersionUID = 2266997012890611498L;
	
	
	/** cod_efiech - Chave Primaria (PK) */
	private Long codIettvH;

    /** cod_ageo - Chave estrangeira (FK) - Tabela <tb_agenda_ocorrencia_ageo> */	
    private AgendaOcorrenciaAgeo agendaOcorrenciaAgeo;

    /** cod_sgti - Chave estrangeira (FK) - Tabela <tb_segmento_item_sgti> */
    private SegmentoItemSgti segmentoItemSgti;

    /** cod_iett - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;
    
    /** cod_iettv  */
    private Long codIettv;    
    
    /** data_inclusao_iettv */
    private Date dataInclusaoIettv;
    
    /** COD_USU_MANUTENCAO - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;

    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;

    /**
     *
     */
    public HistoricoIettvH() {}

        /**
         *
         * @return
         */
        public AgendaOcorrenciaAgeo getAgendaOcorrenciaAgeo() {
		return agendaOcorrenciaAgeo;
	}

        /**
         *
         * @param agendaOcorrenciaAgeo
         */
        public void setAgendaOcorrenciaAgeo(AgendaOcorrenciaAgeo agendaOcorrenciaAgeo) {
		this.agendaOcorrenciaAgeo = agendaOcorrenciaAgeo;
	}

        /**
         *
         * @return
         */
        public Long getCodIettvH() {
		return codIettvH;
	}

        /**
         *
         * @param codIettvH
         */
        public void setCodIettvH(Long codIettvH) {
		this.codIettvH = codIettvH;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoIettv() {
		return dataInclusaoIettv;
	}

        /**
         *
         * @param dataInclusaoIettv
         */
        public void setDataInclusaoIettv(Date dataInclusaoIettv) {
		this.dataInclusaoIettv = dataInclusaoIettv;
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
        public Long getCodIettv() {
		return codIettv;
	}

        /**
         *
         * @param codIettv
         */
        public void setCodIettv(Long codIettv) {
		this.codIettv = codIettv;
	}

        /**
         *
         * @return
         */
        public SegmentoItemSgti getSegmentoItemSgti() {
		return segmentoItemSgti;
	}

        /**
         *
         * @param segmentoItemSgti
         */
        public void setSegmentoItemSgti(SegmentoItemSgti segmentoItemSgti) {
		this.segmentoItemSgti = segmentoItemSgti;
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
