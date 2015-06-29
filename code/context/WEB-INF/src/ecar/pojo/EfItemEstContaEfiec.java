package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @author n/c, rogerio
 * @since n/c
 * @version 0.2, 06/06/2007
 */
public class EfItemEstContaEfiec implements Serializable {

	/**
	 * Constante para FormaInclusao
	 * 
	 * Exemplo:
	 * EfItemEstContaEfiec efItemEstContaEfiec = new EfItemEstContaEfiec();
	 * efItemEstContaEfiec.setFormaInclusao( EfItemEstContaEfiec.FormaInclusao.CARGA );
	 */
    public interface FormaInclusao {

        /**
         *
         */
        public static Integer CARGA = new Integer(1);
        /**
         *
         */
        public static Integer USUARIO = new Integer(2);
        /**
         *
         */
        public static Integer IMPORTACAO = new Integer(3);
								   };
	
	private static final long serialVersionUID = 1619513963105794948L;

    private Long codEfiec;

    private String indAcumuladoEfiec;

    private String contaSistemaOrcEfiec;

    private String indAtivoEfiec;
    
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    private ecar.pojo.FonteRecursoFonr fonteRecursoFonr;

    private ecar.pojo.ExercicioExe exercicioExe;

    private ecar.pojo.RecursoRec recursoRec;

    private Integer formaInclusao;
    
    /* Mantis #2156 */
    private Set historicoEfiecHs;
    private Boolean indExclusaoPosHistorico;
    
    /**
     *
     * @return
     */
    public Boolean getIndExclusaoPosHistorico() {
		return indExclusaoPosHistorico;
	}

    /**
     *
     * @param indExclusaoPosHistorico
     */
    public void setIndExclusaoPosHistorico(Boolean indExclusaoPosHistorico) {
		this.indExclusaoPosHistorico = indExclusaoPosHistorico;
	}

        /**
         *
         * @param indAcumuladoEfiec
         * @param contaSistemaOrcEfiec
         * @param itemEstruturaIett
         * @param fonteRecursoFonr
         * @param exercicioExe
         * @param recursoRec
         */
        public EfItemEstContaEfiec(String indAcumuladoEfiec, String contaSistemaOrcEfiec, ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.FonteRecursoFonr fonteRecursoFonr, ecar.pojo.ExercicioExe exercicioExe, ecar.pojo.RecursoRec recursoRec) {
        this.indAcumuladoEfiec = indAcumuladoEfiec;
        this.contaSistemaOrcEfiec = contaSistemaOrcEfiec;
        this.itemEstruturaIett = itemEstruturaIett;
        this.fonteRecursoFonr = fonteRecursoFonr;
        this.exercicioExe = exercicioExe;
        this.recursoRec = recursoRec;
    }

        /**
         *
         */
        public EfItemEstContaEfiec() {
    }

    /**
     *
     * @param itemEstruturaIett
     * @param fonteRecursoFonr
     * @param exercicioExe
     * @param recursoRec
     */
    public EfItemEstContaEfiec(ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.FonteRecursoFonr fonteRecursoFonr, ecar.pojo.ExercicioExe exercicioExe, ecar.pojo.RecursoRec recursoRec) {
        this.itemEstruturaIett = itemEstruturaIett;
        this.fonteRecursoFonr = fonteRecursoFonr;
        this.exercicioExe = exercicioExe;
        this.recursoRec = recursoRec;
    }

    
    /**
     *
     * @return
     */
    public Integer getFormaInclusao() {
		return formaInclusao;
	}

    /**
     *
     * @param formaInclusao
     */
    public void setFormaInclusao(Integer formaInclusao) {
		this.formaInclusao = formaInclusao;
	}

        /**
         *
         * @return
         */
        public Long getCodEfiec() {
        return this.codEfiec;
    }

        /**
         *
         * @param codEfiec
         */
        public void setCodEfiec(Long codEfiec) {
        this.codEfiec = codEfiec;
    }

        /**
         *
         * @return
         */
        public String getIndAcumuladoEfiec() {
        return this.indAcumuladoEfiec;
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
    public String getContaSistemaOrcEfiec() {
        return this.contaSistemaOrcEfiec;
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
    public ecar.pojo.ItemEstruturaIett getItemEstruturaIett() {
        return this.itemEstruturaIett;
    }

    /**
     *
     * @param itemEstruturaIett
     */
    public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
        this.itemEstruturaIett = itemEstruturaIett;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.FonteRecursoFonr getFonteRecursoFonr() {
        return this.fonteRecursoFonr;
    }

    /**
     *
     * @param fonteRecursoFonr
     */
    public void setFonteRecursoFonr(ecar.pojo.FonteRecursoFonr fonteRecursoFonr) {
        this.fonteRecursoFonr = fonteRecursoFonr;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ExercicioExe getExercicioExe() {
        return this.exercicioExe;
    }

    /**
     *
     * @param exercicioExe
     */
    public void setExercicioExe(ecar.pojo.ExercicioExe exercicioExe) {
        this.exercicioExe = exercicioExe;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.RecursoRec getRecursoRec() {
        return this.recursoRec;
    }

    /**
     *
     * @param recursoRec
     */
    public void setRecursoRec(ecar.pojo.RecursoRec recursoRec) {
        this.recursoRec = recursoRec;
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


    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codEfiec", getCodEfiec())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EfItemEstContaEfiec) ) return false;
        EfItemEstContaEfiec castOther = (EfItemEstContaEfiec) other;
        return new EqualsBuilder()
            .append(this.getCodEfiec(), castOther.getCodEfiec())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodEfiec())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Set getHistoricoEfiecHs() {
		return historicoEfiecHs;
	}

    /**
     *
     * @param historicoEfiecHs
     */
    public void setHistoricoEfiecHs(Set historicoEfiecHs) {
		this.historicoEfiecHs = historicoEfiecHs;
	}

}
