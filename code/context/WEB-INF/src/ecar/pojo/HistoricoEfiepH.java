package ecar.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * Classe responsavel para armazenar dados da tabela TB_HISTORICO_EFIEPH
 * - Modificado para comportar historico
 * 
 * @author rogerio, gabriel
 * @since 0.1, 15/05/2007
 * @version 0.2, 24/05/2007
 */
public class HistoricoEfiepH implements Serializable {


	private static final long serialVersionUID = 6385290793094336191L;
	
	/** COD_EFIEPH - Chave Primaria (PK) */
	private Long codEfiepH;

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
    
    /** cod_mah - Chave estrangeira (FK) - Tabela <tb_historico_master_mah> */
    private HistoricoMaster historicoMaster;    
	
    /** valor_revisado_efiep */
	private BigDecimal valorRevisadoEfiep;
	
	/** valor_aprovado_efiep */
	private BigDecimal valorAprovadoEfiep;
	
	/** ind_ativo_efiep */
	private String indAtivoEfiep;
	
	/** data_inclusao_efiep */
	private Date dataInclusaoEfiep;    
    
        /**
         *
         */
        public HistoricoEfiepH() { }

    /**
     *
     * @return
     */
    public Long getCodEfiepH() {
		return codEfiepH;
	}

        /**
         *
         * @param codEfiepH
         */
        public void setCodEfiepH(Long codEfiepH) {
		this.codEfiepH = codEfiepH;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoEfiep() {
		return dataInclusaoEfiep;
	}

        /**
         *
         * @param dataInclusaoEfiep
         */
        public void setDataInclusaoEfiep(Date dataInclusaoEfiep) {
		this.dataInclusaoEfiep = dataInclusaoEfiep;
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
        public String getIndAtivoEfiep() {
		return indAtivoEfiep;
	}

        /**
         *
         * @param indAtivoEfiep
         */
        public void setIndAtivoEfiep(String indAtivoEfiep) {
		this.indAtivoEfiep = indAtivoEfiep;
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

        /**
         *
         * @return
         */
        public BigDecimal getValorAprovadoEfiep() {
		return valorAprovadoEfiep;
	}

        /**
         *
         * @param valorAprovadoEfiep
         */
        public void setValorAprovadoEfiep(BigDecimal valorAprovadoEfiep) {
		this.valorAprovadoEfiep = valorAprovadoEfiep;
	}

        /**
         *
         * @return
         */
        public BigDecimal getValorRevisadoEfiep() {
		return valorRevisadoEfiep;
	}

        /**
         *
         * @param valorRevisadoEfiep
         */
        public void setValorRevisadoEfiep(BigDecimal valorRevisadoEfiep) {
		this.valorRevisadoEfiep = valorRevisadoEfiep;
	}
    
    
    
    
}
