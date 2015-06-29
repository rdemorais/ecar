package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class FonteRecursoFonr implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3870967123780813612L;

	/** identifier field */
    private Long codFonr;

    /** nullable persistent field */
    private String indAtivoFonr;

    /** nullable persistent field */
    private Date dataInclusaoFonr;

    /** nullable persistent field */
    private Date dataFimValidadeFonr;

    /** nullable persistent field */
    private Date dataIniValidadeFonr;

    /** nullable persistent field */
    private String indOrcamentoFonr;

    /** nullable persistent field */
    private String nomeFonr;
    
    /** nullable persistent field */
    private Integer sequenciaFonr;
    
    /** nullable persistent field */
    private String siglaFonr;

    /** persistent field */
    private ecar.pojo.ConfigSisExecFinanCsef configSisExecFinanCsef;

    /** persistent field */
    private Set efItemEstContaEfiecs;

    /** persistent field */
    private Set efItemEstPrevisaoEfieps;

    /** persistent field */
    private Set efIettFonteTotEfiefts;
    private Set efIettPrevisaoRevEfieprs;
    
    /* Mantis #2156 */
    private Set historicoEfiecHs;
    private Set historicoEfieftHs;
    private Set historicoEfiepHs;
    

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
         *
         * @return
         */
        public Set getHistoricoEfieftHs() {
		return historicoEfieftHs;
	}

        /**
         *
         * @param historicoEfieftHs
         */
        public void setHistoricoEfieftHs(Set historicoEfieftHs) {
		this.historicoEfieftHs = historicoEfieftHs;
	}

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
         * @param indAtivoFonr
         * @param efIettFonteTotEfiefts
         * @param dataInclusaoFonr
         * @param dataFimValidadeFonr
         * @param efIettPrevisaoRevEfieprs
         * @param dataIniValidadeFonr
         * @param nomeFonr
         * @param sequenciaFonr
         * @param indOrcamentoFonr
         * @param configSisExecFinanCsef
         * @param siglaFonr
         * @param efItemEstContaEfiecs
         * @param efItemEstPrevisaoEfieps
         */
    public FonteRecursoFonr(String indAtivoFonr, Date dataInclusaoFonr, Date dataFimValidadeFonr, Date dataIniValidadeFonr, String indOrcamentoFonr, String nomeFonr, Integer sequenciaFonr, String siglaFonr, ecar.pojo.ConfigSisExecFinanCsef configSisExecFinanCsef, Set efItemEstContaEfiecs, Set efItemEstPrevisaoEfieps,
            Set efIettFonteTotEfiefts, Set efIettPrevisaoRevEfieprs) {
        this.indAtivoFonr = indAtivoFonr;
        this.dataInclusaoFonr = dataInclusaoFonr;
        this.dataFimValidadeFonr = dataFimValidadeFonr;
        this.dataIniValidadeFonr = dataIniValidadeFonr;
        this.indOrcamentoFonr = indOrcamentoFonr;
        this.nomeFonr = nomeFonr;
        this.sequenciaFonr = sequenciaFonr;
        this.siglaFonr = siglaFonr;
        this.configSisExecFinanCsef = configSisExecFinanCsef;
        this.efItemEstContaEfiecs = efItemEstContaEfiecs;
        this.efItemEstPrevisaoEfieps = efItemEstPrevisaoEfieps;
        this.efIettFonteTotEfiefts = efIettFonteTotEfiefts;
        this.efIettPrevisaoRevEfieprs = efIettPrevisaoRevEfieprs;
    }

    /** default constructor */
    public FonteRecursoFonr() {
    }

    /** minimal constructor
     * @param configSisExecFinanCsef
     * @param efItemEstContaEfiecs
     * @param efItemEstPrevisaoEfieps
     * @param efIettPrevisaoRevEfieprs
     */
    public FonteRecursoFonr(ecar.pojo.ConfigSisExecFinanCsef configSisExecFinanCsef, Set efItemEstContaEfiecs, Set efItemEstPrevisaoEfieps, Set efIettPrevisaoRevEfieprs) {
        this.configSisExecFinanCsef = configSisExecFinanCsef;
        this.efItemEstContaEfiecs = efItemEstContaEfiecs;
        this.efItemEstPrevisaoEfieps = efItemEstPrevisaoEfieps;
        this.efIettPrevisaoRevEfieprs = efIettPrevisaoRevEfieprs;
    }

    /**
     *
     * @return
     */
    public Long getCodFonr() {
        return this.codFonr;
    }

    /**
     *
     * @param codFonr
     */
    public void setCodFonr(Long codFonr) {
        this.codFonr = codFonr;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoFonr() {
        return this.indAtivoFonr;
    }

    /**
     *
     * @param indAtivoFonr
     */
    public void setIndAtivoFonr(String indAtivoFonr) {
        this.indAtivoFonr = indAtivoFonr;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoFonr() {
        return this.dataInclusaoFonr;
    }

    /**
     *
     * @param dataInclusaoFonr
     */
    public void setDataInclusaoFonr(Date dataInclusaoFonr) {
        this.dataInclusaoFonr = dataInclusaoFonr;
    }

    /**
     *
     * @return
     */
    public Date getDataFimValidadeFonr() {
        return this.dataFimValidadeFonr;
    }

    /**
     *
     * @param dataFimValidadeFonr
     */
    public void setDataFimValidadeFonr(Date dataFimValidadeFonr) {
        this.dataFimValidadeFonr = dataFimValidadeFonr;
    }

    /**
     *
     * @return
     */
    public Date getDataIniValidadeFonr() {
        return this.dataIniValidadeFonr;
    }

    /**
     *
     * @param dataIniValidadeFonr
     */
    public void setDataIniValidadeFonr(Date dataIniValidadeFonr) {
        this.dataIniValidadeFonr = dataIniValidadeFonr;
    }

    /**
     *
     * @return
     */
    public String getIndOrcamentoFonr() {
        return this.indOrcamentoFonr;
    }

    /**
     *
     * @param indOrcamentoFonr
     */
    public void setIndOrcamentoFonr(String indOrcamentoFonr) {
        this.indOrcamentoFonr = indOrcamentoFonr;
    }

    /**
     *
     * @return
     */
    public String getNomeFonr() {
        return this.nomeFonr;
    }

    /**
     *
     * @param nomeFonr
     */
    public void setNomeFonr(String nomeFonr) {
        this.nomeFonr = nomeFonr;
    }
    
    /**
     *
     * @return
     */
    public Integer getSequenciaFonr() {
        return this.sequenciaFonr;
    }

    /**
     *
     * @param sequenciaFonr
     */
    public void setSequenciaFonr(Integer sequenciaFonr) {
        this.sequenciaFonr = sequenciaFonr;
    }
    
    /**
     *
     * @return
     */
    public String getSiglaFonr() {
        return this.siglaFonr;
    }

    /**
     *
     * @param siglaFonr
     */
    public void setSiglaFonr(String siglaFonr) {
        this.siglaFonr = siglaFonr;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ConfigSisExecFinanCsef getConfigSisExecFinanCsef() {
        return this.configSisExecFinanCsef;
    }

    /**
     *
     * @param configSisExecFinanCsef
     */
    public void setConfigSisExecFinanCsef(ecar.pojo.ConfigSisExecFinanCsef configSisExecFinanCsef) {
        this.configSisExecFinanCsef = configSisExecFinanCsef;
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
            .append("codFonr", getCodFonr())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof FonteRecursoFonr) ) return false;
        FonteRecursoFonr castOther = (FonteRecursoFonr) other;
        return new EqualsBuilder()
            .append(this.getCodFonr(), castOther.getCodFonr())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodFonr())
            .toHashCode();
    }
    
    /**
     * @return Returns the efIettFonteTotEfiefts.
     */
    public Set getEfIettFonteTotEfiefts() {
        return efIettFonteTotEfiefts;
    }
    /**
     * @param efIettFonteTotEfiefts The efIettFonteTotEfiefts to set.
     */
    public void setEfIettFonteTotEfiefts(Set efIettFonteTotEfiefts) {
        this.efIettFonteTotEfiefts = efIettFonteTotEfiefts;
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
    

}
