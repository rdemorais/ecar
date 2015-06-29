package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author 70744416353
 */
public class ItemEstruturaSisAtributoIettSatbPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3298092790581090133L;
	
	private Long codIett;
	private Long codSatb;

	/**
	 * Minimal constructor
	 */
	
	public ItemEstruturaSisAtributoIettSatbPK(){		
	}
	
	/**
	 * FullConstructor
         *
         * @param codIett
         * @param codSatb
         */
	
	public ItemEstruturaSisAtributoIettSatbPK(Long codIett, Long codSatb){
		this.codIett = codIett;
		this.codSatb = codSatb;
	}
	
	
        /**
         *
         * @return
         */
        public Long getCodIett() {
		return codIett;
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
        public Long getCodSatb() {
		return codSatb;
	}

        /**
         *
         * @param codSatb
         */
        public void setCodSatb(Long codSatb) {
		this.codSatb = codSatb;
	}
	
    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIett", getCodIett())
            .append("codSatb", getCodSatb())
            .toString();
    }

    /**
     * @param other
     * @author N/C
     * @since N/C
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstruturaSisAtributoIettSatbPK) ) return false;
        ItemEstruturaSisAtributoIettSatbPK castOther = (ItemEstruturaSisAtributoIettSatbPK) other;
        return new EqualsBuilder()
            .append(this.getCodIett(), castOther.getCodIett())
            .append(this.getCodSatb(), castOther.getCodSatb())
            .isEquals();
    }

    /**
     * @author N/C
     * @since N/C
     * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIett())
            .append(getCodSatb())
            .toHashCode();
    }

}
