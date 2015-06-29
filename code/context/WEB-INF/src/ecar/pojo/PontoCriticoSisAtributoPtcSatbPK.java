package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author 70744416353
 */
public class PontoCriticoSisAtributoPtcSatbPK implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4660848379775372502L;
	
	private Long codPtc;
	private Long codSatb;

	/**
	 * Minimal constructor
	 */
	
	public PontoCriticoSisAtributoPtcSatbPK(){		
	}
	
	/**
	 * FullConstructor
         *
         * @param codPtc
         * @param codSatb
         */
	
	public PontoCriticoSisAtributoPtcSatbPK(Long codPtc, Long codSatb){
		this.codPtc = codPtc;
		this.codSatb = codSatb;
	}
	
	
        /**
         *
         * @return
         */
        public Long getCodPtc() {
		return codPtc;
	}

        /**
         *
         * @param codPtc
         */
        public void setCodPtc(Long codPtc) {
		this.codPtc = codPtc;
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
            .append("codPtc", getCodPtc())
            .append("codSatb", getCodSatb())
            .toString();
    }

    /**
     * @author N/C
     * @since N/C
     * @param other
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PontoCriticoSisAtributoPtcSatbPK) ) return false;
        PontoCriticoSisAtributoPtcSatbPK castOther = (PontoCriticoSisAtributoPtcSatbPK) other;
        return new EqualsBuilder()
            .append(this.getCodPtc(), castOther.getCodPtc())
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
            .append(getCodPtc())
            .append(getCodSatb())
            .toHashCode();
    }

}
