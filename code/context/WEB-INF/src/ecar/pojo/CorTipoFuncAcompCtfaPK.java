package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CorTipoFuncAcompCtfaPK implements Serializable {

    
	private static final long serialVersionUID = -1592010239395376993L;

	/** identifier field */
    private Long codCor;

    /** identifier field */
    private Long codTpfa;
    
    /** identifier field */
    private String posicaoCtfa;

    /** 
     * full constructor
     * 
     * @author N/C
	 * @since N/C 
     * @param codCor
     * @param codTpfa
     * @param posicaoCtfa
     */
    public CorTipoFuncAcompCtfaPK(Long codCor, Long codTpfa, String posicaoCtfa) {
        this.codCor = codCor;
        this.codTpfa = codTpfa;
        this.posicaoCtfa = posicaoCtfa;
    }

    /** default constructor */
    public CorTipoFuncAcompCtfaPK() {
    }    

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getCodCor() {
		return codCor;
	}

    /**
     * @author N/C
	 * @since N/C 
     * @param codCor
     */
	public void setCodCor(Long codCor) {
		this.codCor = codCor;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Long
	 */
	public Long getCodTpfa() {
		return codTpfa;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param codTpfa
	 */
	public void setCodTpfa(Long codTpfa) {
		this.codTpfa = codTpfa;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getPosicaoCtfa() {
		return posicaoCtfa;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param posicaoCtfa
	 */
	public void setPosicaoCtfa(String posicaoCtfa) {
		this.posicaoCtfa = posicaoCtfa;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
    @Override
	public String toString() {
        return new ToStringBuilder(this)
            .append("codCor", getCodCor())
            .append("codTpfa", getCodTpfa())
            .append("posicaoCtfa", getPosicaoCtfa())
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
        if ( !(other instanceof CorTipoFuncAcompCtfaPK) ) return false;
        CorTipoFuncAcompCtfaPK castOther = (CorTipoFuncAcompCtfaPK) other;
        return new EqualsBuilder()
            .append(this.getCodCor(), castOther.getCodCor())
            .append(this.getCodTpfa(), castOther.getCodTpfa())
            .append(this.getPosicaoCtfa(), castOther.getPosicaoCtfa())
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
            .append(getCodCor())
            .append(getCodTpfa())
            .append(getPosicaoCtfa())
            .toHashCode();
    }

}
