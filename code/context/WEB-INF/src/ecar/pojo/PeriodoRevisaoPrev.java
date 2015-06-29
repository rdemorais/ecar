package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PeriodoRevisaoPrev implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5830633285541411158L;

	/** identifier field */
    private Long codPrev;

    /** nullable persistent field */
    private Date dtInicioPrev;

    /** nullable persistent field */
    private Date dtFimPrev;

    /** nullable persistent field */
    private String descricaoPrev;
    
    /** persistent field */
    private ConfiguracaoCfg configuracaoCfg;
    

    /** full constructor
     * @param dtInicioPrev
     * @param dtFimPrev
     * @param descricaoPrev
     * @param configuracaoCfg
     */
    public PeriodoRevisaoPrev(Date dtInicioPrev, Date dtFimPrev, ConfiguracaoCfg configuracaoCfg, String descricaoPrev) {
        this.descricaoPrev = descricaoPrev;
    	this.dtInicioPrev = dtInicioPrev;
        this.dtFimPrev = dtFimPrev;
        this.configuracaoCfg = configuracaoCfg;      
    }

    /** default constructor */
    public PeriodoRevisaoPrev() {
    }

    /** minimal constructor
     * @param configuracaoCfg
     */
    public PeriodoRevisaoPrev(ConfiguracaoCfg configuracaoCfg) {
        this.configuracaoCfg = configuracaoCfg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codPrev", getCodPrev())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PeriodoRevisaoPrev) ) return false;
        PeriodoRevisaoPrev castOther = (PeriodoRevisaoPrev) other;
        return new EqualsBuilder()
            .append(this.getCodPrev(), castOther.getCodPrev())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodPrev())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Long getCodPrev() {
		return codPrev;
	}

    /**
     *
     * @param codPrev
     */
    public void setCodPrev(Long codPrev) {
		this.codPrev = codPrev;
	}

        /**
         *
         * @return
         */
        public ConfiguracaoCfg getConfiguracaoCfg() {
		return configuracaoCfg;
	}

        /**
         *
         * @param configuracaoCfg
         */
        public void setConfiguracaoCfg(ConfiguracaoCfg configuracaoCfg) {
		this.configuracaoCfg = configuracaoCfg;
	}

        /**
         *
         * @return
         */
        public Date getDtFimPrev() {
		return dtFimPrev;
	}

        /**
         *
         * @param dtFimPrev
         */
        public void setDtFimPrev(Date dtFimPrev) {
		this.dtFimPrev = dtFimPrev;
	}

        /**
         *
         * @return
         */
        public Date getDtInicioPrev() {
		return dtInicioPrev;
	}

        /**
         *
         * @param dtInicioPrev
         */
        public void setDtInicioPrev(Date dtInicioPrev) {
		this.dtInicioPrev = dtInicioPrev;
	}

        /**
         *
         * @return
         */
        public String getDescricaoPrev() {
		return descricaoPrev;
	}

        /**
         *
         * @param descricaoPrev
         */
        public void setDescricaoPrev(String descricaoPrev) {
		this.descricaoPrev = descricaoPrev;
	}
}
