package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 02/06/2007 */
public class AreaAre implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8323491411304194077L;

	/** identifier field */
    private Long codAre;

    /** nullable persistent field */
    private String indAtivoAre;

    /** nullable persistent field */
    private Date dataInclusaoAre;

    /** nullable persistent field */
    private String nomeAre;

    /** nullable persistent field */
    private Long codigoIdentAre;

    /** persistent field */
    private Set itemEstruturaIetts;
    
    /** persistent field */
    private Set itemEstruturaRevisaoIettRevs;
    
    /* Mantis #2156 */
    private Set historicoIettHs;
    
    /**
     *
     * @return
     */
    public Set getHistoricoIettHs() {
		return historicoIettHs;
	}

    /**
     *
     * @param historicoIettHs
     */
    public void setHistoricoIettHs(Set historicoIettHs) {
		this.historicoIettHs = historicoIettHs;
	}

        /**
         *
         * @return
         */
        public Set getItemEstruturaRevisaoIettRevs() {
		return itemEstruturaRevisaoIettRevs;
	}

        /**
         *
         * @param itemEstruturaRevisaoIettRevs
         */
        public void setItemEstruturaRevisaoIettRevs(Set itemEstruturaRevisaoIettRevs) {
		this.itemEstruturaRevisaoIettRevs = itemEstruturaRevisaoIettRevs;
	}

	/** 
	 * full constructor.<br> 
	 * 
	 * @author N/C
	 * @since N/C
         * @param indAtivoAre
         * @param dataInclusaoAre
         * @param nomeAre 
         * @param codigoIdentAre
         * @param itemEstruturaIetts
         * @param itemEstruturaRevisaoIettRevs
	 */
    public AreaAre(String indAtivoAre, Date dataInclusaoAre, String nomeAre, Long codigoIdentAre, 
    		Set itemEstruturaIetts, Set itemEstruturaRevisaoIettRevs) {
        this.indAtivoAre = indAtivoAre;
        this.dataInclusaoAre = dataInclusaoAre;
        this.nomeAre = nomeAre;
        this.codigoIdentAre = codigoIdentAre;
        this.itemEstruturaIetts = itemEstruturaIetts;
        this.itemEstruturaRevisaoIettRevs = itemEstruturaRevisaoIettRevs;
    }

    /** default constructor */
    public AreaAre() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @author N/C
	 * @since N/C
     * @param itemEstruturaIetts
     * @param itemEstruturaRevisaoIettRevs
     */
    public AreaAre(Set itemEstruturaIetts, Set itemEstruturaRevisaoIettRevs) {
        this.itemEstruturaIetts = itemEstruturaIetts;
        this.itemEstruturaRevisaoIettRevs = itemEstruturaRevisaoIettRevs;

    }

    /**
     * @author N/C
	 * @since N/C
     * @return Long
     */
    public Long getCodAre() {
        return this.codAre;
    }

    /**
     * @param codAre
     * @author N/C
	 * @since N/C
     */
    public void setCodAre(Long codAre) {
        this.codAre = codAre;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return String
     */
    public String getIndAtivoAre() {
        return this.indAtivoAre;
    }

    /**
     * @param indAtivoAre
     * @author N/C
	 * @since N/C
     */
    public void setIndAtivoAre(String indAtivoAre) {
        this.indAtivoAre = indAtivoAre;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return Date
     */
    public Date getDataInclusaoAre() {
        return this.dataInclusaoAre;
    }

    /**
     * @author N/C
	 * @since N/C
     * @param dataInclusaoAre
     */
    public void setDataInclusaoAre(Date dataInclusaoAre) {
        this.dataInclusaoAre = dataInclusaoAre;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return String
     */
    public String getNomeAre() {
        return this.nomeAre;
    }

    /**
     * @author N/C
	 * @since N/C
     * @param nomeAre
     */
    public void setNomeAre(String nomeAre) {
        this.nomeAre = nomeAre;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return Set
     */
    public Set getItemEstruturaIetts() {
        return this.itemEstruturaIetts;
    }

    /**
     * @param itemEstruturaIetts
     * @author N/C
	 * @since N/C
     */
    public void setItemEstruturaIetts(Set itemEstruturaIetts) {
        this.itemEstruturaIetts = itemEstruturaIetts;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return Long
     */
    public Long getCodigoIdentAre() {
		return codigoIdentAre;
	}

    /**
     * @param codigoIdentAre
     * @author N/C
	 * @since N/C
     */
	public void setCodigoIdentAre(Long codigoIdentAre) {
		this.codigoIdentAre = codigoIdentAre;
	}

	/**
	 * @author N/C
	 * @since N/C
	 * @return String
	 */
    @Override
	public String toString() {
        return new ToStringBuilder(this)
            .append("codAre", getCodAre())
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
        if ( !(other instanceof AreaAre) ) return false;
        AreaAre castOther = (AreaAre) other;
        return new EqualsBuilder()
            .append(this.getCodAre(), castOther.getCodAre())
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
            .append(getCodAre())
            .toHashCode();
    }

}
