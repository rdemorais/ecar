package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ItemEstrutLocalIettlPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 954077277252260522L;

	/** identifier field */
    private Long codLit;

    /** identifier field */
    private Long codIett;

    /** full constructor
     * @param codLit
     * @param codIett
     */
    public ItemEstrutLocalIettlPK(Long codLit, Long codIett) {
        this.codLit = codLit;
        this.codIett = codIett;
    }

    /** default constructor */
    public ItemEstrutLocalIettlPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodLit() {
        return this.codLit;
    }

    /**
     *
     * @param codLit
     */
    public void setCodLit(Long codLit) {
        this.codLit = codLit;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codLit", getCodLit())
            .append("codIett", getCodIett())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrutLocalIettlPK) ) return false;
        ItemEstrutLocalIettlPK castOther = (ItemEstrutLocalIettlPK) other;
        return new EqualsBuilder()
            .append(this.getCodLit(), castOther.getCodLit())
            .append(this.getCodIett(), castOther.getCodIett())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodLit())
            .append(getCodIett())
            .toHashCode();
    }

}
