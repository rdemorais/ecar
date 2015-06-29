package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class EfIettPrevisaoRevEfiepr implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6491339987612281490L;

	/** identifier field */
    private ecar.pojo.EfIettPrevisaoRevEfieprPK comp_id;

    /** nullable persistent field */
    private Date dataInclusaoEfiepr;

    /** nullable persistent field */
    private String indAtivoEfiepr;

    /** nullable persistent field */
    private Double valorRevisadoEfiepr;

    /** nullable persistent field */
    private Double valorAprovadoEfiepr;

    /** nullable persistent field */
    private ecar.pojo.RecursoRec recursoRec;

    /** nullable persistent field */
    private ecar.pojo.FonteRecursoFonr fonteRecursoFonr;

    /** nullable persistent field */
    private ecar.pojo.ExercicioExe exercicioExe;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev;

    /** full constructor
     * @param comp_id
     * @param dataInclusaoEfiepr
     * @param recursoRec
     * @param indAtivoEfiepr
     * @param exercicioExe
     * @param valorRevisadoEfiepr
     * @param valorAprovadoEfiepr
     * @param fonteRecursoFonr
     * @param itemEstruturarevisaoIettrev
     */
    public EfIettPrevisaoRevEfiepr(ecar.pojo.EfIettPrevisaoRevEfieprPK comp_id, Date dataInclusaoEfiepr, String indAtivoEfiepr, Double valorRevisadoEfiepr, Double valorAprovadoEfiepr, ecar.pojo.RecursoRec recursoRec, ecar.pojo.FonteRecursoFonr fonteRecursoFonr, ecar.pojo.ExercicioExe exercicioExe, ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev) {
        this.comp_id = comp_id;
        this.dataInclusaoEfiepr = dataInclusaoEfiepr;
        this.indAtivoEfiepr = indAtivoEfiepr;
        this.valorRevisadoEfiepr = valorRevisadoEfiepr;
        this.valorAprovadoEfiepr = valorAprovadoEfiepr;
        this.recursoRec = recursoRec;
        this.fonteRecursoFonr = fonteRecursoFonr;
        this.exercicioExe = exercicioExe;
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
    }

    /** default constructor */
    public EfIettPrevisaoRevEfiepr() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public EfIettPrevisaoRevEfiepr(ecar.pojo.EfIettPrevisaoRevEfieprPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EfIettPrevisaoRevEfieprPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.EfIettPrevisaoRevEfieprPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoEfiepr() {
        return this.dataInclusaoEfiepr;
    }

    /**
     *
     * @param dataInclusaoEfiepr
     */
    public void setDataInclusaoEfiepr(Date dataInclusaoEfiepr) {
        this.dataInclusaoEfiepr = dataInclusaoEfiepr;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoEfiepr() {
        return this.indAtivoEfiepr;
    }

    /**
     *
     * @param indAtivoEfiepr
     */
    public void setIndAtivoEfiepr(String indAtivoEfiepr) {
        this.indAtivoEfiepr = indAtivoEfiepr;
    }

    /**
     *
     * @return
     */
    public Double getValorRevisadoEfiepr() {
        return this.valorRevisadoEfiepr;
    }

    /**
     *
     * @param valorRevisadoEfiepr
     */
    public void setValorRevisadoEfiepr(Double valorRevisadoEfiepr) {
        this.valorRevisadoEfiepr = valorRevisadoEfiepr;
    }

    /**
     *
     * @return
     */
    public Double getValorAprovadoEfiepr() {
        return this.valorAprovadoEfiepr;
    }

    /**
     *
     * @param valorAprovadoEfiepr
     */
    public void setValorAprovadoEfiepr(Double valorAprovadoEfiepr) {
        this.valorAprovadoEfiepr = valorAprovadoEfiepr;
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
    public ecar.pojo.ItemEstruturarevisaoIettrev getItemEstruturarevisaoIettrev() {
        return this.itemEstruturarevisaoIettrev;
    }

    /**
     *
     * @param itemEstruturarevisaoIettrev
     */
    public void setItemEstruturarevisaoIettrev(ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev) {
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof EfIettPrevisaoRevEfiepr) ) return false;
        EfIettPrevisaoRevEfiepr castOther = (EfIettPrevisaoRevEfiepr) other;
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

}
