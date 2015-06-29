package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class AcompRealFisicoLocalArfl implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1032766922264988389L;

    /** nullable persistent field */
    private Long codArfl;

	/** nullable persistent field */
    private Double quantidadeArfl;

    /** nullable persistent field */
    private AcompRealFisicoArf acompRealFisicoArf;

    /** nullable persistent field */
    private LocalItemLit localItemLit;      

    /** 
     * full constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param quantidadeArfl
     * @param acompRealFisicoArf
     * @param localItemLit
     */
    public AcompRealFisicoLocalArfl(Double quantidadeArfl, AcompRealFisicoArf acompRealFisicoArf, 
    		LocalItemLit localItemLit) {
        this.quantidadeArfl = quantidadeArfl;
        this.acompRealFisicoArf = acompRealFisicoArf;
        this.localItemLit = localItemLit;
    }

    /** default constructor */
    public AcompRealFisicoLocalArfl() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @param acompRealFisicoArf
     * @param localItemLit
     * @author N/C
     * @since N/C
     */
    public AcompRealFisicoLocalArfl(AcompRealFisicoArf acompRealFisicoArf, LocalItemLit localItemLit) {
        this.acompRealFisicoArf = acompRealFisicoArf;
        this.localItemLit = localItemLit;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codArf", getCodArfl())
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
        if ( !(other instanceof AcompRealFisicoLocalArfl) ) return false;
        AcompRealFisicoLocalArfl castOther = (AcompRealFisicoLocalArfl) other;
        return new EqualsBuilder()
            .append(this.getCodArfl(), castOther.getCodArfl())
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
            .append(getCodArfl())
            .toHashCode();
    }

    /**
     * @author N/C
     * @since N/C
     * @return AcompRealFisicoArf
     */
	public AcompRealFisicoArf getAcompRealFisicoArf() {
		return acompRealFisicoArf;
	}

	/**
         * @param acompRealFisicoArf
         * @author N/C
     * @since N/C
	 */
	public void setAcompRealFisicoArf(AcompRealFisicoArf acompRealFisicoArf) {
		this.acompRealFisicoArf = acompRealFisicoArf;
	}

	/**
	 * @author N/C
     * @since N/C
	 * @return Long
	 */
	public Long getCodArfl() {
		return codArfl;
	}

	/**
         * @param codArfl
         * @author N/C
     * @since N/C
	 */
	public void setCodArfl(Long codArfl) {
		this.codArfl = codArfl;
	}

	/**
	 * @author N/C
     * @since N/C
	 * @return LocalItemLit
	 */
	public LocalItemLit getLocalItemLit() {
		return localItemLit;
	}

	/**
	 * @author N/C
     * @since N/C
         * @param localItemLit
	 */
	public void setLocalItemLit(LocalItemLit localItemLit) {
		this.localItemLit = localItemLit;
	}

	/**
	 * @author N/C
     * @since N/C
	 * @return Double
	 */
	public Double getQuantidadeArfl() {
		return quantidadeArfl;
	}

	/**
	 * @author N/C
     * @since N/C
         * @param quantidadeArfl
	 */
	public void setQuantidadeArfl(Double quantidadeArfl) {
		this.quantidadeArfl = quantidadeArfl;
	}

	
}
