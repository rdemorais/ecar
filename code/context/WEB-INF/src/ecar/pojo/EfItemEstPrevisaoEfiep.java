package ecar.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 06/06/2007
 */
public class EfItemEstPrevisaoEfiep implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5348763093955159324L;

	/** identifier field */
    private ecar.pojo.EfItemEstPrevisaoEfiepPK comp_id;

    /** nullable persistent field */
    private Date dataInclusaoEfiep;

    /** nullable persistent field */
    private String indAtivoEfiep;

    /** nullable persistent field */
    private BigDecimal valorRevisadoEfiep;

    /** nullable persistent field */
    private BigDecimal valorAprovadoEfiep;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** nullable persistent field */
    private ecar.pojo.FonteRecursoFonr fonteRecursoFonr;

    /** nullable persistent field */
    private ecar.pojo.ExercicioExe exercicioExe;

    /** nullable persistent field */
    private ecar.pojo.RecursoRec recursoRec;
    
    private ecar.pojo.EspecieEsp especieEsp;
    
    private ecar.pojo.FonteFon fonteFon;
    
    /* -- Mantis #2156 -- */
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

        /** full constructor
         * @param comp_id
         * @param dataInclusaoEfiep
         * @param valorAprovadoEfiep
         * @param valorRevisadoEfiep
         * @param indAtivoEfiep
         * @param itemEstruturaIett
         * @param fonteRecursoFonr
         * @param exercicioExe
         * @param recursoRec
         */
    public EfItemEstPrevisaoEfiep(ecar.pojo.EfItemEstPrevisaoEfiepPK comp_id, Date dataInclusaoEfiep, String indAtivoEfiep, BigDecimal valorRevisadoEfiep, BigDecimal valorAprovadoEfiep, ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.FonteRecursoFonr fonteRecursoFonr, ecar.pojo.ExercicioExe exercicioExe, ecar.pojo.RecursoRec recursoRec) {
        this.comp_id = comp_id;
        this.dataInclusaoEfiep = dataInclusaoEfiep;
        this.indAtivoEfiep = indAtivoEfiep;
        this.valorRevisadoEfiep = valorRevisadoEfiep;
        this.valorAprovadoEfiep = valorAprovadoEfiep;
        this.itemEstruturaIett = itemEstruturaIett;
        this.fonteRecursoFonr = fonteRecursoFonr;
        this.exercicioExe = exercicioExe;
        this.recursoRec = recursoRec;
    }

    /** default constructor */
    public EfItemEstPrevisaoEfiep() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public EfItemEstPrevisaoEfiep(ecar.pojo.EfItemEstPrevisaoEfiepPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EfItemEstPrevisaoEfiepPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.EfItemEstPrevisaoEfiepPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoEfiep() {
        return this.dataInclusaoEfiep;
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
    public String getIndAtivoEfiep() {
        return this.indAtivoEfiep;
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
    public BigDecimal getValorRevisadoEfiep() {
        return this.valorRevisadoEfiep;
    }

    /**
     *
     * @param valorRevisadoEfiep
     */
    public void setValorRevisadoEfiep(BigDecimal valorRevisadoEfiep) {
        this.valorRevisadoEfiep = valorRevisadoEfiep;
    }

    /**
     *
     * @return
     */
    public BigDecimal getValorAprovadoEfiep() {
        return this.valorAprovadoEfiep;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EfItemEstPrevisaoEfiep) ) return false;
        EfItemEstPrevisaoEfiep castOther = (EfItemEstPrevisaoEfiep) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

	/**
	 * @return the especieEsp
	 */
	public ecar.pojo.EspecieEsp getEspecieEsp() {
		return especieEsp;
	}

	/**
	 * @param especieEsp the especieEsp to set
	 */
	public void setEspecieEsp(ecar.pojo.EspecieEsp especieEsp) {
		this.especieEsp = especieEsp;
	}

	/**
	 * @return the fonteFon
	 */
	public ecar.pojo.FonteFon getFonteFon() {
		return fonteFon;
	}

	/**
	 * @param fonteFon the fonteFon to set
	 */
	public void setFonteFon(ecar.pojo.FonteFon fonteFon) {
		this.fonteFon = fonteFon;
	}

}
