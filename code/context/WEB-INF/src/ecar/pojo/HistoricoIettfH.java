package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_ietteh
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 08/05/2007
 * @version 0.2, 28/05/2007
 */
public class HistoricoIettfH implements Serializable {

	
	private static final long serialVersionUID = 6787091105639562521L;

	/** cod_iettfh - Chave Primaria (PK) */
	private Long codIettfH;
	
	/** ind_ativo_iettf */
	private String indAtivoIettf;
	
	/** data_inclusao_iettf */
	private Date dataInclusaoIettf;

	/** qtd_prevista_iettf */
	private Double qtdPrevistaIettf;
	
    /** cod_exe - Chave estrangeira (FK) - Tabela <tb_exercicio_exe> */	
	private ExercicioExe exercicioExe;
	
    /** cod_iettir - Chave estrangeira (FK) - Tabela <tb_item_estrt_ind_resul_iettr> */	
	private ItemEstrtIndResulIettr itemEstrtIndResulIettr;
	
    /** COD_USU_MANUTENCAO - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;
    
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;	
	
    private Set historicoIettfHs;
    
    private Date dataUltManutencao;
        
    
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
    public Set getHistoricoIettfHs() {
		return historicoIettfHs;
	}

        /**
         *
         * @param historicoIettfHs
         */
        public void setHistoricoIettfHs(Set historicoIettfHs) {
		this.historicoIettfHs = historicoIettfHs;
	}

        /**
         *
         */
        public HistoricoIettfH() {
	}

        /**
         *
         * @return
         */
        public Long getCodIettfH() {
		return codIettfH;
	}

        /**
         *
         * @param codIettfH
         */
        public void setCodIettfH(Long codIettfH) {
		this.codIettfH = codIettfH;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoIettf() {
		return dataInclusaoIettf;
	}

        /**
         *
         * @param dataInclusaoIettf
         */
        public void setDataInclusaoIettf(Date dataInclusaoIettf) {
		this.dataInclusaoIettf = dataInclusaoIettf;
	}

        /**
         *
         * @return
         */
        public ExercicioExe getExercicioExe() {
		return exercicioExe;
	}

        /**
         *
         * @param exercicioExe
         */
        public void setExercicioExe(ExercicioExe exercicioExe) {
		this.exercicioExe = exercicioExe;
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
        public String getIndAtivoIettf() {
		return indAtivoIettf;
	}

        /**
         *
         * @param indAtivoIettf
         */
        public void setIndAtivoIettf(String indAtivoIettf) {
		this.indAtivoIettf = indAtivoIettf;
	}

        /**
         *
         * @return
         */
        public ItemEstrtIndResulIettr getItemEstrtIndResulIettr() {
		return itemEstrtIndResulIettr;
	}

        /**
         *
         * @param itemEstrtIndResulIettr
         */
        public void setItemEstrtIndResulIettr(
			ItemEstrtIndResulIettr itemEstrtIndResulIettr) {
		this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
	}

        /**
         *
         * @return
         */
        public Double getQtdPrevistaIettf() {
		return qtdPrevistaIettf;
	}

        /**
         *
         * @param qtdPrevistaIettf
         */
        public void setQtdPrevistaIettf(Double qtdPrevistaIettf) {
		this.qtdPrevistaIettf = qtdPrevistaIettf;
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
