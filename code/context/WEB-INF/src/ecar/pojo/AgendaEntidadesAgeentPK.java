package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author 70744416353
 */
public class AgendaEntidadesAgeentPK implements Serializable {

	
	private static final long serialVersionUID = -7626533657988163033L;

    private Long codAge ;
    private Long codEnt;

    /**
     *
     * @param codAge
     * @param codEnt
     */
    public AgendaEntidadesAgeentPK(Long codAge, Long codEnt) {
        this.codAge = codAge;
        this.codEnt = codEnt;
    }

    
    /** default constructor */
    public AgendaEntidadesAgeentPK() {    }

    /**
     *
     * @return
     */
    public Long getCodAge() {
        return this.codAge;
    }

    /**
     *
     * @param codAge
     */
    public void setCodAge(Long codAge) {
        this.codAge = codAge;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codAge", getCodAge())
            .append("codEnt", getCodEnt())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof AgendaEntidadesAgeentPK) ) return false;
        AgendaEntidadesAgeentPK castOther = (AgendaEntidadesAgeentPK) other;
        return new EqualsBuilder()
            .append(this.getCodAge(), castOther.getCodAge())
            .append(this.getCodEnt(), castOther.getCodEnt())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodAge())
            .append(getCodEnt())
            .toHashCode();
    }

}
