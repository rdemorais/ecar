package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoAreaSgta implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 801095712371198016L;

	/** identifier field */
    private Long codSgta;

    /** nullable persistent field */
    private Date dataInclusaoSgta;

    /** nullable persistent field */
    private String descricaoSgta;

    /** persistent field */
    private Set segmentoSgts;

    /** full constructor
     * @param dataInclusaoSgta
     * @param descricaoSgta
     * @param segmentoSgts
     */
    public SegmentoAreaSgta(Date dataInclusaoSgta, String descricaoSgta, Set segmentoSgts) {
        this.dataInclusaoSgta = dataInclusaoSgta;
        this.descricaoSgta = descricaoSgta;
        this.segmentoSgts = segmentoSgts;
    }

    /** default constructor */
    public SegmentoAreaSgta() {
    }

    /** minimal constructor
     * @param segmentoSgts
     */
    public SegmentoAreaSgta(Set segmentoSgts) {
        this.segmentoSgts = segmentoSgts;
    }

    /**
     *
     * @return
     */
    public Long getCodSgta() {
        return this.codSgta;
    }

    /**
     *
     * @param codSgta
     */
    public void setCodSgta(Long codSgta) {
        this.codSgta = codSgta;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoSgta() {
        return this.dataInclusaoSgta;
    }

    /**
     *
     * @param dataInclusaoSgta
     */
    public void setDataInclusaoSgta(Date dataInclusaoSgta) {
        this.dataInclusaoSgta = dataInclusaoSgta;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSgta() {
        return this.descricaoSgta;
    }

    /**
     *
     * @param descricaoSgta
     */
    public void setDescricaoSgta(String descricaoSgta) {
        this.descricaoSgta = descricaoSgta;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoSgts() {
        return this.segmentoSgts;
    }

    /**
     *
     * @param segmentoSgts
     */
    public void setSegmentoSgts(Set segmentoSgts) {
        this.segmentoSgts = segmentoSgts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSgta", getCodSgta())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SegmentoAreaSgta) ) return false;
        SegmentoAreaSgta castOther = (SegmentoAreaSgta) other;
        return new EqualsBuilder()
            .append(this.getCodSgta(), castOther.getCodSgta())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSgta())
            .toHashCode();
    }

}
