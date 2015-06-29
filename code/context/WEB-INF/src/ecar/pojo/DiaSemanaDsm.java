package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class DiaSemanaDsm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6588829607844495611L;

	/** identifier field */
    private Long codDsm;

    /** nullable persistent field */
    private String indDiaUtilDsm;

    /** nullable persistent field */
    private String descricaoDsm;

    /** full constructor
     * @param indDiaUtilDsm
     * @param descricaoDsm
     */
    public DiaSemanaDsm(String indDiaUtilDsm, String descricaoDsm) {
        this.indDiaUtilDsm = indDiaUtilDsm;
        this.descricaoDsm = descricaoDsm;
    }

    /** default constructor */
    public DiaSemanaDsm() {
    }

    /**
     *
     * @return
     */
    public Long getCodDsm() {
        return this.codDsm;
    }

    /**
     *
     * @param codDsm
     */
    public void setCodDsm(Long codDsm) {
        this.codDsm = codDsm;
    }

    /**
     *
     * @return
     */
    public String getIndDiaUtilDsm() {
        return this.indDiaUtilDsm;
    }

    /**
     *
     * @param indDiaUtilDsm
     */
    public void setIndDiaUtilDsm(String indDiaUtilDsm) {
        this.indDiaUtilDsm = indDiaUtilDsm;
    }

    /**
     *
     * @return
     */
    public String getDescricaoDsm() {
        return this.descricaoDsm;
    }

    /**
     *
     * @param descricaoDsm
     */
    public void setDescricaoDsm(String descricaoDsm) {
        this.descricaoDsm = descricaoDsm;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codDsm", getCodDsm())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof DiaSemanaDsm) ) return false;
        DiaSemanaDsm castOther = (DiaSemanaDsm) other;
        return new EqualsBuilder()
            .append(this.getCodDsm(), castOther.getCodDsm())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodDsm())
            .toHashCode();
    }

}
