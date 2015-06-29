package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ItemEstrtBenefIettbPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9030954185371248697L;

	/** identifier field */
    private Long codIett;

    /** identifier field */
    private Long codBnf;

    /** full constructor
     * @param codIett
     * @param codBnf
     */
    public ItemEstrtBenefIettbPK(Long codIett, Long codBnf) {
        this.codIett = codIett;
        this.codBnf = codBnf;
    }

    /** default constructor */
    public ItemEstrtBenefIettbPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodIett() {
        return this.codIett;
    }

    /**
     *
     * @param codIett
     */
    public void setCodIett(Long codIett) {
        this.codIett = codIett;
    }

    /**
     *
     * @return
     */
    public Long getCodBnf() {
        return this.codBnf;
    }

    /**
     *
     * @param codBnf
     */
    public void setCodBnf(Long codBnf) {
        this.codBnf = codBnf;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIett", getCodIett())
            .append("codBnf", getCodBnf())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrtBenefIettbPK) ) return false;
        ItemEstrtBenefIettbPK castOther = (ItemEstrtBenefIettbPK) other;
        return new EqualsBuilder()
            .append(this.getCodIett(), castOther.getCodIett())
            .append(this.getCodBnf(), castOther.getCodBnf())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIett())
            .append(getCodBnf())
            .toHashCode();
    }

}
