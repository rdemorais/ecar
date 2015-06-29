package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ItemEstUsutpfuacIettutfaPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -11042168329899180L;

	/** identifier field */
    private Long codTpfa;

    /** identifier field */
    private Long codIett;


    /** full constructor
     * @param codTpfa
     * @param codIett
     */
    public ItemEstUsutpfuacIettutfaPK(Long codTpfa, Long codIett) {
        this.codTpfa = codTpfa;
        this.codIett = codIett;
    }

    /** default constructor */
    public ItemEstUsutpfuacIettutfaPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodTpfa() {
        return this.codTpfa;
    }

    /**
     *
     * @param codTpfa
     */
    public void setCodTpfa(Long codTpfa) {
        this.codTpfa = codTpfa;
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
            .append("codTpfa", getCodTpfa())
            .append("codIett", getCodIett())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstUsutpfuacIettutfaPK) ) return false;
        ItemEstUsutpfuacIettutfaPK castOther = (ItemEstUsutpfuacIettutfaPK) other;
        return new EqualsBuilder()
            .append(this.getCodTpfa(), castOther.getCodTpfa())
            .append(this.getCodIett(), castOther.getCodIett())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodTpfa())
            .append(getCodIett())
            .toHashCode();
    }

}
