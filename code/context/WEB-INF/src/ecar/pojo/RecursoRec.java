package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class RecursoRec implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6024228760919019794L;

	/** identifier field */
    private Long codRec;

    /** nullable persistent field */
    private String indAtivoRec;

    /** nullable persistent field */
    private String indOrcamentoRec;

    /** nullable persistent field */
    private Date dataInclusaoRec;

    /** nullable persistent field */
    private String siglaRec;

    /** nullable persistent field */
    private Date dataFimValidadeRec;

    /** nullable persistent field */
    private String nomeRec;

    /** nullable persistent field */
    private Date dataIniValidadeRec;
    
    /** nullable persistent field */
    private Integer sequenciaRec;

    /** persistent field */
    private Set efItemEstContaEfiecs;

    /** persistent field */
    private Set efItemEstPrevisaoEfieps;
    
    private Set efIettPrevisaoRevEfieprs;
    
    private Set fonteFons;
    
    /* Mantis #2156 */
    private Set historicoEfiecHs;
    private Set historicoEfiepHs;

    /**
     *
     * @return
     */
    public Set getHistoricoEfiecHs() {
		return historicoEfiecHs;
	}

    /**
     *
     * @param historicoEfiecHs
     */
    public void setHistoricoEfiecHs(Set historicoEfiecHs) {
		this.historicoEfiecHs = historicoEfiecHs;
	}

        /** full constructor
         * @param indAtivoRec
         * @param indOrcamentoRec
         * @param dataInclusaoRec
         * @param efItemEstPrevisaoEfieps
         * @param nomeRec
         * @param sequenciaRec
         * @param dataFimValidadeRec
         * @param dataIniValidadeRec
         * @param siglaRec
         * @param efIettPrevisaoRevEfieprs
         * @param efItemEstContaEfiecs
         */
    public RecursoRec(String indAtivoRec, String indOrcamentoRec, Date dataInclusaoRec, String siglaRec, Date dataFimValidadeRec, String nomeRec, Date dataIniValidadeRec, Integer sequenciaRec, Set efItemEstContaEfiecs, Set efItemEstPrevisaoEfieps, Set efIettPrevisaoRevEfieprs) {
        this.indAtivoRec = indAtivoRec;
        this.indOrcamentoRec = indOrcamentoRec;
        this.dataInclusaoRec = dataInclusaoRec;
        this.siglaRec = siglaRec;
        this.dataFimValidadeRec = dataFimValidadeRec;
        this.nomeRec = nomeRec;
        this.dataIniValidadeRec = dataIniValidadeRec;
        this.sequenciaRec = sequenciaRec;
        this.efItemEstContaEfiecs = efItemEstContaEfiecs;
        this.efItemEstPrevisaoEfieps = efItemEstPrevisaoEfieps;
        this.efIettPrevisaoRevEfieprs = efIettPrevisaoRevEfieprs;
    }

    /** default constructor */
    public RecursoRec() {
    }

    /** minimal constructor
     * @param efItemEstContaEfiecs
     * @param efItemEstPrevisaoEfieps
     * @param efIettPrevisaoRevEfieprs
     */
    public RecursoRec( Set efItemEstContaEfiecs, Set efItemEstPrevisaoEfieps, Set efIettPrevisaoRevEfieprs) {
        this.efItemEstContaEfiecs = efItemEstContaEfiecs;
        this.efItemEstPrevisaoEfieps = efItemEstPrevisaoEfieps;
        this.efIettPrevisaoRevEfieprs = efIettPrevisaoRevEfieprs;
    }

    /**
     *
     * @return
     */
    public Long getCodRec() {
        return this.codRec;
    }

    /**
     *
     * @param codRec
     */
    public void setCodRec(Long codRec) {
        this.codRec = codRec;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoRec() {
        return this.indAtivoRec;
    }

    /**
     *
     * @param indAtivoRec
     */
    public void setIndAtivoRec(String indAtivoRec) {
        this.indAtivoRec = indAtivoRec;
    }

    /**
     *
     * @return
     */
    public String getIndOrcamentoRec() {
        return this.indOrcamentoRec;
    }

    /**
     *
     * @param indOrcamentoRec
     */
    public void setIndOrcamentoRec(String indOrcamentoRec) {
        this.indOrcamentoRec = indOrcamentoRec;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoRec() {
        return this.dataInclusaoRec;
    }

    /**
     *
     * @param dataInclusaoRec
     */
    public void setDataInclusaoRec(Date dataInclusaoRec) {
        this.dataInclusaoRec = dataInclusaoRec;
    }

    /**
     *
     * @return
     */
    public String getSiglaRec() {
        return this.siglaRec;
    }

    /**
     *
     * @param siglaRec
     */
    public void setSiglaRec(String siglaRec) {
        this.siglaRec = siglaRec;
    }

    /**
     *
     * @return
     */
    public Date getDataFimValidadeRec() {
        return this.dataFimValidadeRec;
    }

    /**
     *
     * @param dataFimValidadeRec
     */
    public void setDataFimValidadeRec(Date dataFimValidadeRec) {
        this.dataFimValidadeRec = dataFimValidadeRec;
    }

    /**
     *
     * @return
     */
    public String getNomeRec() {
        return this.nomeRec;
    }

    /**
     *
     * @param nomeRec
     */
    public void setNomeRec(String nomeRec) {
        this.nomeRec = nomeRec;
    }

    /**
     *
     * @return
     */
    public Date getDataIniValidadeRec() {
        return this.dataIniValidadeRec;
    }

    /**
     *
     * @param dataIniValidadeRec
     */
    public void setDataIniValidadeRec(Date dataIniValidadeRec) {
        this.dataIniValidadeRec = dataIniValidadeRec;
    }
    
    /**
     *
     * @return
     */
    public Integer getSequenciaRec() {
        return this.sequenciaRec;
    }

    /**
     *
     * @param sequenciaRec
     */
    public void setSequenciaRec(Integer sequenciaRec) {
        this.sequenciaRec = sequenciaRec;
    }

    /**
     *
     * @return
     */
    public Set getEfItemEstContaEfiecs() {
        return this.efItemEstContaEfiecs;
    }

    /**
     *
     * @param efItemEstContaEfiecs
     */
    public void setEfItemEstContaEfiecs(Set efItemEstContaEfiecs) {
        this.efItemEstContaEfiecs = efItemEstContaEfiecs;
    }

    /**
     *
     * @return
     */
    public Set getEfItemEstPrevisaoEfieps() {
        return this.efItemEstPrevisaoEfieps;
    }

    /**
     *
     * @param efItemEstPrevisaoEfieps
     */
    public void setEfItemEstPrevisaoEfieps(Set efItemEstPrevisaoEfieps) {
        this.efItemEstPrevisaoEfieps = efItemEstPrevisaoEfieps;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codRec", getCodRec())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof RecursoRec) ) return false;
        RecursoRec castOther = (RecursoRec) other;
        return new EqualsBuilder()
            .append(this.getCodRec(), castOther.getCodRec())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodRec())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Set getEfIettPrevisaoRevEfieprs() {
		return efIettPrevisaoRevEfieprs;
	}

        /**
         *
         * @param efIettPrevisaoRevEfieprs
         */
        public void setEfIettPrevisaoRevEfieprs(Set efIettPrevisaoRevEfieprs) {
		this.efIettPrevisaoRevEfieprs = efIettPrevisaoRevEfieprs;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoEfiepHs() {
		return historicoEfiepHs;
	}

        /**
         *
         * @param historicoEfiepHs
         */
        public void setHistoricoEfiepHs(Set historicoEfiepHs) {
		this.historicoEfiepHs = historicoEfiepHs;
	}

	/**
	 * @return the fonteFons
	 */
	public Set getFonteFons() {
		return fonteFons;
	}

	/**
	 * @param fonteFons the fonteFons to set
	 */
	public void setFonteFons(Set fonteFons) {
		this.fonteFons = fonteFons;
	}

}
