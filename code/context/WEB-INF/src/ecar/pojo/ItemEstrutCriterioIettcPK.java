package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ItemEstrutCriterioIettcPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8028796050732875981L;

	/** identifier field */
    private Long codCri;

    /** identifier field */
    private Long codIett;

    /** full constructor
     * @param codCri
     * @param codIett
     */
    public ItemEstrutCriterioIettcPK(Long codCri, Long codIett) {
        this.codCri = codCri;
        this.codIett = codIett;
    }

    /** default constructor */
    public ItemEstrutCriterioIettcPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodCri() {
        return this.codCri;
    }

    /**
     *
     * @param codCri
     */
    public void setCodCri(Long codCri) {
        this.codCri = codCri;
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
            .append("codCri", getCodCri())
            .append("codIett", getCodIett())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrutCriterioIettcPK) ) return false;
        ItemEstrutCriterioIettcPK castOther = (ItemEstrutCriterioIettcPK) other;
        return new EqualsBuilder()
            .append(this.getCodCri(), castOther.getCodCri())
            .append(this.getCodIett(), castOther.getCodIett())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodCri())
            .append(getCodIett())
            .toHashCode();
    }

}
