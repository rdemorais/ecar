package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ItemEstrutFisicoIettfPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8890040386669077885L;

	/** identifier field */
    private Long codIettir;

    /** identifier field */
    private Long codExe;

    /** full constructor
     * @param codIettir
     * @param codExe
     */
    public ItemEstrutFisicoIettfPK(Long codIettir, Long codExe) {
        this.codIettir = codIettir;
        this.codExe = codExe;
    }

    /** default constructor */
    public ItemEstrutFisicoIettfPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodIettir() {
        return this.codIettir;
    }

    /**
     *
     * @param codIettir
     */
    public void setCodIettir(Long codIettir) {
        this.codIettir = codIettir;
    }

    /**
     *
     * @return
     */
    public Long getCodExe() {
        return this.codExe;
    }

    /**
     *
     * @param codExe
     */
    public void setCodExe(Long codExe) {
        this.codExe = codExe;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIettir", getCodIettir())
            .append("codExe", getCodExe())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrutFisicoIettfPK) ) return false;
        ItemEstrutFisicoIettfPK castOther = (ItemEstrutFisicoIettfPK) other;
        return new EqualsBuilder()
            .append(this.getCodIettir(), castOther.getCodIettir())
            .append(this.getCodExe(), castOther.getCodExe())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettir())
            .append(getCodExe())
            .toHashCode();
    }

}
