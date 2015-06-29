package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class EntidadeAtributoEntaPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3083189837052020355L;

	/** identifier field */
    private Long codEnt;

    /** identifier field */
    private Long codSatb;

    /** full constructor
     * @param codEnt
     * @param codSatb
     */
    public EntidadeAtributoEntaPK(Long codEnt, Long codSatb) {
        this.codEnt = codEnt;
        this.codSatb = codSatb;
    }

    /** default constructor */
    public EntidadeAtributoEntaPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodEnt() {
        return this.codEnt;
    }

    /**
     *
     * @param codEnt
     */
    public void setCodEnt(Long codEnt) {
        this.codEnt = codEnt;
    }

    /**
     *
     * @return
     */
    public Long getCodSatb() {
        return this.codSatb;
    }

    /**
     *
     * @param codSatb
     */
    public void setCodSatb(Long codSatb) {
        this.codSatb = codSatb;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codEnt", getCodEnt())
            .append("codSatb", getCodSatb())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof EntidadeAtributoEntaPK) ) return false;
        EntidadeAtributoEntaPK castOther = (EntidadeAtributoEntaPK) other;
        return new EqualsBuilder()
            .append(this.getCodEnt(), castOther.getCodEnt())
            .append(this.getCodSatb(), castOther.getCodSatb())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodEnt())
            .append(getCodSatb())
            .toHashCode();
    }

}
