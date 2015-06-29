package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class DestaqueItemRelDtqir implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 883062951774274156L;

	/** identifier field */
    private Long codDtqir;

    /** persistent field */
    private ecar.pojo.AgendaAge agendaAge;

    /** persistent field */
    private ecar.pojo.AgendaOcorrenciaAgeo agendaOcorrenciaAgeo;

    /** persistent field */
    private ecar.pojo.DestaqueSubAreaDtqsa destaqueSubAreaDtqsa;

    /** persistent field */
    private ecar.pojo.SegmentoItemSgti segmentoItemSgti;

    /** full constructor
     * @param agendaAge 
     * @param segmentoItemSgti
     * @param agendaOcorrenciaAgeo
     * @param destaqueSubAreaDtqsa
     */
    public DestaqueItemRelDtqir(ecar.pojo.AgendaAge agendaAge, ecar.pojo.AgendaOcorrenciaAgeo agendaOcorrenciaAgeo, ecar.pojo.DestaqueSubAreaDtqsa destaqueSubAreaDtqsa, ecar.pojo.SegmentoItemSgti segmentoItemSgti) {
        this.agendaAge = agendaAge;
        this.agendaOcorrenciaAgeo = agendaOcorrenciaAgeo;
        this.destaqueSubAreaDtqsa = destaqueSubAreaDtqsa;
        this.segmentoItemSgti = segmentoItemSgti;
    }

    /** default constructor */
    public DestaqueItemRelDtqir() {
    }

    /**
     *
     * @return
     */
    public Long getCodDtqir() {
        return this.codDtqir;
    }

    /**
     *
     * @param codDtqir
     */
    public void setCodDtqir(Long codDtqir) {
        this.codDtqir = codDtqir;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.AgendaAge getAgendaAge() {
        return this.agendaAge;
    }

    /**
     *
     * @param agendaAge
     */
    public void setAgendaAge(ecar.pojo.AgendaAge agendaAge) {
        this.agendaAge = agendaAge;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.AgendaOcorrenciaAgeo getAgendaOcorrenciaAgeo() {
        return this.agendaOcorrenciaAgeo;
    }

    /**
     *
     * @param agendaOcorrenciaAgeo
     */
    public void setAgendaOcorrenciaAgeo(ecar.pojo.AgendaOcorrenciaAgeo agendaOcorrenciaAgeo) {
        this.agendaOcorrenciaAgeo = agendaOcorrenciaAgeo;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.DestaqueSubAreaDtqsa getDestaqueSubAreaDtqsa() {
        return this.destaqueSubAreaDtqsa;
    }

    /**
     *
     * @param destaqueSubAreaDtqsa
     */
    public void setDestaqueSubAreaDtqsa(ecar.pojo.DestaqueSubAreaDtqsa destaqueSubAreaDtqsa) {
        this.destaqueSubAreaDtqsa = destaqueSubAreaDtqsa;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SegmentoItemSgti getSegmentoItemSgti() {
        return this.segmentoItemSgti;
    }

    /**
     *
     * @param segmentoItemSgti
     */
    public void setSegmentoItemSgti(ecar.pojo.SegmentoItemSgti segmentoItemSgti) {
        this.segmentoItemSgti = segmentoItemSgti;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codDtqir", getCodDtqir())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof DestaqueItemRelDtqir) ) return false;
        DestaqueItemRelDtqir castOther = (DestaqueItemRelDtqir) other;
        return new EqualsBuilder()
            .append(this.getCodDtqir(), castOther.getCodDtqir())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodDtqir())
            .toHashCode();
    }

}
