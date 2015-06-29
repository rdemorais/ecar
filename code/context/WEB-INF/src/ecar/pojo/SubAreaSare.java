package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator */
public class SubAreaSare implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7433574826908550446L;

	/** identifier field */
    private Long codSare;

    /** nullable persistent field */
    private String indAtivoSare;

    /** nullable persistent field */
    private Date dataInclusaoSare;

    /** persistent field */
    private String nomeSare;

    /** nullable persistent field */
    private Long codigoIdentSare;

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
    
    
        /** full constructor
         * @param indAtivoSare
         * @param nomeSare
         * @param dataInclusaoSare
         * @param codigoIdentSare
         * @param itemEstruturaIetts
         * @param itemEstruturaRevisaoIettRevs
         */
    public SubAreaSare(String indAtivoSare, Date dataInclusaoSare, String nomeSare, Long codigoIdentSare, Set itemEstruturaIetts, Set itemEstruturaRevisaoIettRevs) {
        this.indAtivoSare = indAtivoSare;
        this.dataInclusaoSare = dataInclusaoSare;
        this.nomeSare = nomeSare;
        this.codigoIdentSare = codigoIdentSare;
        this.itemEstruturaIetts = itemEstruturaIetts;
        this.itemEstruturaRevisaoIettRevs = itemEstruturaRevisaoIettRevs;
    }

    /** default constructor */
    public SubAreaSare() {
    }

    /** minimal constructor
     * @param nomeSare
     * @param itemEstruturaIetts
     * @param itemEstruturaRevisaoIettRevs
     */
    public SubAreaSare(String nomeSare, Set itemEstruturaIetts, Set itemEstruturaRevisaoIettRevs) {
        this.nomeSare = nomeSare;
        this.itemEstruturaIetts = itemEstruturaIetts;
        this.itemEstruturaRevisaoIettRevs = itemEstruturaRevisaoIettRevs;
    }

    /**
     *
     * @return
     */
    public Long getCodSare() {
        return this.codSare;
    }

    /**
     *
     * @param codSare
     */
    public void setCodSare(Long codSare) {
        this.codSare = codSare;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoSare() {
        return this.indAtivoSare;
    }

    /**
     *
     * @param indAtivoSare
     */
    public void setIndAtivoSare(String indAtivoSare) {
        this.indAtivoSare = indAtivoSare;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoSare() {
        return this.dataInclusaoSare;
    }

    /**
     *
     * @param dataInclusaoSare
     */
    public void setDataInclusaoSare(Date dataInclusaoSare) {
        this.dataInclusaoSare = dataInclusaoSare;
    }

    /**
     *
     * @return
     */
    public String getNomeSare() {
        return this.nomeSare;
    }

    /**
     *
     * @param nomeSare
     */
    public void setNomeSare(String nomeSare) {
        this.nomeSare = nomeSare;
    }

    /**
     *
     * @return
     */
    public Set getItemEstruturaIetts() {
        return this.itemEstruturaIetts;
    }

    /**
     *
     * @param itemEstruturaIetts
     */
    public void setItemEstruturaIetts(Set itemEstruturaIetts) {
        this.itemEstruturaIetts = itemEstruturaIetts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSare", getCodSare())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SubAreaSare) ) return false;
        SubAreaSare castOther = (SubAreaSare) other;
        return new EqualsBuilder()
            .append(this.getCodSare(), castOther.getCodSare())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSare())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Long getCodigoIdentSare() {
		return codigoIdentSare;
	}

        /**
         *
         * @param codigoIdentSare
         */
        public void setCodigoIdentSare(Long codigoIdentSare) {
		this.codigoIdentSare = codigoIdentSare;
	}

}
