package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ItemEstFisicoRevIettfr implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 637416663063697288L;

	/** identifier field */
    private ecar.pojo.ItemEstFisicoRevIettfrPK comp_id;

    /** nullable persistent field */
    private String indAtivoIettfr;

    /** nullable persistent field */
    private Date dataInclusaoIettfr;

    /** nullable persistent field */
    private Double qtdPrevistaIettfr;

    /** nullable persistent field */
    private ecar.pojo.IettIndResulRevIettrr iettIndResulRevIettrr;

    /** nullable persistent field */
    private ecar.pojo.ExercicioExe exercicioExe;

    /** full constructor
     * @param comp_id
     * @param iettIndResulRevIettrr
     * @param indAtivoIettfr
     * @param dataInclusaoIettfr
     * @param qtdPrevistaIettfr
     * @param exercicioExe
     */
    public ItemEstFisicoRevIettfr(ecar.pojo.ItemEstFisicoRevIettfrPK comp_id, String indAtivoIettfr, Date dataInclusaoIettfr, Double qtdPrevistaIettfr, ecar.pojo.IettIndResulRevIettrr iettIndResulRevIettrr, ecar.pojo.ExercicioExe exercicioExe) {
        this.comp_id = comp_id;
        this.indAtivoIettfr = indAtivoIettfr;
        this.dataInclusaoIettfr = dataInclusaoIettfr;
        this.qtdPrevistaIettfr = qtdPrevistaIettfr;
        this.iettIndResulRevIettrr = iettIndResulRevIettrr;
        this.exercicioExe = exercicioExe;
    }

    /** default constructor */
    public ItemEstFisicoRevIettfr() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public ItemEstFisicoRevIettfr(ecar.pojo.ItemEstFisicoRevIettfrPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstFisicoRevIettfrPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.ItemEstFisicoRevIettfrPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoIettfr() {
        return this.indAtivoIettfr;
    }

    /**
     *
     * @param indAtivoIettfr
     */
    public void setIndAtivoIettfr(String indAtivoIettfr) {
        this.indAtivoIettfr = indAtivoIettfr;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoIettfr() {
        return this.dataInclusaoIettfr;
    }

    /**
     *
     * @param dataInclusaoIettfr
     */
    public void setDataInclusaoIettfr(Date dataInclusaoIettfr) {
        this.dataInclusaoIettfr = dataInclusaoIettfr;
    }

    /**
     *
     * @return
     */
    public Double getQtdPrevistaIettfr() {
        return this.qtdPrevistaIettfr;
    }

    /**
     *
     * @param qtdPrevistaIettfr
     */
    public void setQtdPrevistaIettfr(Double qtdPrevistaIettfr) {
        this.qtdPrevistaIettfr = qtdPrevistaIettfr;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.IettIndResulRevIettrr getIettIndResulRevIettrr() {
        return this.iettIndResulRevIettrr;
    }

    /**
     *
     * @param iettIndResulRevIettrr
     */
    public void setIettIndResulRevIettrr(ecar.pojo.IettIndResulRevIettrr iettIndResulRevIettrr) {
        this.iettIndResulRevIettrr = iettIndResulRevIettrr;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof ItemEstFisicoRevIettfr) ) return false;
        ItemEstFisicoRevIettfr castOther = (ItemEstFisicoRevIettfr) other;
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
