package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_efiech
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 15/05/2007
 * @version 0.2, 24/05/2007
 */
public class HistoricoEfiecH implements Serializable {

	private static final long serialVersionUID = -3748269881961153860L;
	
	/** cod_efiech - Chave Primaria (PK) */
    private Long codEfiecH;
    
    /** COD_EFIEC - Chave estrangeira (FK) - Tabela <tb_ef_item_est_conta_efiec> */
    private EfItemEstContaEfiec efItemEstContaEfiec;
    
    /** ind_acumulado_efiec */
    private String indAcumuladoEfiec;
    
    /** conta_sistema_orc_efiec */
    private String contaSistemaOrcEfiec;
    
    /** ind_ativo_efiec */
    private String indAtivoEfiec;
    
    /** COD_IETT - Chave estrangeira (FK) - Tabela <tb_item_estrutura_iett> */
    private ItemEstruturaIett itemEstruturaIett;
    
    /** COD_FONR - Chave estrangeira (FK) - Tabela <tb_fonte_recurso_fonr> */
    private FonteRecursoFonr fonteRecursoFonr;
    
    /** COD_EXE -  Chave estrangeira (FK) - Tabela <tb_exercicio_exe> */
    private ExercicioExe exercicioExe;
    
    /** COD_REC -  Chave estrangeira (FK) - Tabela <tb_recurso_rec> */
    private RecursoRec recursoRec;
    
    /** COD_USU_MANUTENCAO - Chave estrangeira (FK) - Tabela <tb_usuario_usu> */
    private UsuarioUsu usuManutencao;
    
    /** data_ult_manutencao - Data da ultima alteracao*/
    private Date dataUltManutencao;
    
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;
    
    
    /**
     *
     */
    public HistoricoEfiecH() {
	}


    /**
     *
     * @return
     */
    public Long getCodEfiecH() {
		return codEfiecH;
	}


    /**
     *
     * @param codEfiecH
     */
    public void setCodEfiecH(Long codEfiecH) {
		this.codEfiecH = codEfiecH;
	}


    /**
     *
     * @return
     */
    public String getContaSistemaOrcEfiec() {
		return contaSistemaOrcEfiec;
	}


        /**
         *
         * @param contaSistemaOrcEfiec
         */
        public void setContaSistemaOrcEfiec(String contaSistemaOrcEfiec) {
		this.contaSistemaOrcEfiec = contaSistemaOrcEfiec;
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
        public EfItemEstContaEfiec getEfItemEstContaEfiec() {
		return efItemEstContaEfiec;
	}


        /**
         *
         * @param efItemEstContaEfiec
         */
        public void setEfItemEstContaEfiec(EfItemEstContaEfiec efItemEstContaEfiec) {
		this.efItemEstContaEfiec = efItemEstContaEfiec;
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
        public String getIndAcumuladoEfiec() {
		return indAcumuladoEfiec;
	}


        /**
         *
         * @param indAcumuladoEfiec
         */
        public void setIndAcumuladoEfiec(String indAcumuladoEfiec) {
		this.indAcumuladoEfiec = indAcumuladoEfiec;
	}


        /**
         *
         * @return
         */
        public String getIndAtivoEfiec() {
		return indAtivoEfiec;
	}


        /**
         *
         * @param indAtivoEfiec
         */
        public void setIndAtivoEfiec(String indAtivoEfiec) {
		this.indAtivoEfiec = indAtivoEfiec;
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
        public RecursoRec getRecursoRec() {
		return recursoRec;
	}


        /**
         *
         * @param recursoRec
         */
        public void setRecursoRec(RecursoRec recursoRec) {
		this.recursoRec = recursoRec;
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