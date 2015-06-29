package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ConfigSisExecFinanCsefv implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6266326118695930521L;

	/** identifier field */
    private Long codCsefv;

    /** nullable persistent field */
    private Long versaoCsefv;

    /** nullable persistent field */
    private Long mesVersaoCsefv;

    /** nullable persistent field */
    private Long anoVersaoCsefv;

    /** nullable persistent field */
    private Date dataInclusaoCsefv;

    /** nullable persistent field */
    private String indAtivoCsefv;

    /** persistent field */
    private ecar.pojo.ConfigSisExecFinanCsef configSisExecFinanCsef;
    
    private Set configExecFinanCefs;
    
    private Set importacaoImps;
    
    
    /**
     *
     * @return
     */
    public Set getImportacaoImps() {
		return importacaoImps;
	}

        /**
         *
         * @param importacaoImps
         */
        public void setImportacaoImps(Set importacaoImps) {
		importacaoImps = importacaoImps;
	}

	/** 
	 * full constructor.<br>
	 * 
	 * @author N/C
	 * @since N/C
         * @param configExecFinanCefs
         * @param versaoCsefv
         * @param anoVersaoCsefv
         * @param dataInclusaoCsefv
         * @param mesVersaoCsefv
         * @param indAtivoCsefv
         * @param configSisExecFinanCsef
	 */
    public ConfigSisExecFinanCsefv(Set configExecFinanCefs, 
    		Long versaoCsefv, Long anoVersaoCsefv, Long mesVersaoCsefv, Date dataInclusaoCsefv, 
    		String indAtivoCsefv, ecar.pojo.ConfigSisExecFinanCsef configSisExecFinanCsef) {
        this.versaoCsefv = versaoCsefv;
        this.mesVersaoCsefv = mesVersaoCsefv;
        this.anoVersaoCsefv = anoVersaoCsefv;
        this.dataInclusaoCsefv = dataInclusaoCsefv;
        this.indAtivoCsefv = indAtivoCsefv;
        this.configSisExecFinanCsef = configSisExecFinanCsef;
        this.configExecFinanCefs = configExecFinanCefs;
    }

    /** default constructor */
    public ConfigSisExecFinanCsefv() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @author N/C
	 * @since N/C 
     * @param configExecFinanCefs
     * @param configSisExecFinanCsef
     */
    public ConfigSisExecFinanCsefv(Set configExecFinanCefs, ecar.pojo.ConfigSisExecFinanCsef configSisExecFinanCsef) {
        this.configSisExecFinanCsef = configSisExecFinanCsef;
        this.configExecFinanCefs = configExecFinanCefs;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getCodCsefv() {
        return this.codCsefv;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param codCsefv
     */
    public void setCodCsefv(Long codCsefv) {
        this.codCsefv = codCsefv;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getVersaoCsefv() {
        return this.versaoCsefv;
    }

    /**
     * @param versaoCsefv
     * @author N/C
	 * @since N/C 
     */
    public void setVersaoCsefv(Long versaoCsefv) {
        this.versaoCsefv = versaoCsefv;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Date
     */
    public Date getDataInclusaoCsefv() {
        return this.dataInclusaoCsefv;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param dataInclusaoCsefv
     */
    public void setDataInclusaoCsefv(Date dataInclusaoCsefv) {
        this.dataInclusaoCsefv = dataInclusaoCsefv;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndAtivoCsefv() {
        return this.indAtivoCsefv;
    }

    /**
     * @param indAtivoCsefv
     * @author N/C
	 * @since N/C 
     */
    public void setIndAtivoCsefv(String indAtivoCsefv) {
        this.indAtivoCsefv = indAtivoCsefv;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.ConfigSisExecFinanCsef
     */
    public ecar.pojo.ConfigSisExecFinanCsef getConfigSisExecFinanCsef() {
        return this.configSisExecFinanCsef;
    }

    /**
     * @param configSisExecFinanCsef
     * @author N/C
	 * @since N/C 
     */
    public void setConfigSisExecFinanCsef(ecar.pojo.ConfigSisExecFinanCsef configSisExecFinanCsef) {
        this.configSisExecFinanCsef = configSisExecFinanCsef;
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codCsefv", getCodCsefv())
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
        if ( !(other instanceof ConfigSisExecFinanCsefv) ) return false;
        ConfigSisExecFinanCsefv castOther = (ConfigSisExecFinanCsefv) other;
        return new EqualsBuilder()
            .append(this.getCodCsefv(), castOther.getCodCsefv())
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
            .append(getCodCsefv())
            .toHashCode();
    }


	/**
	 * @author N/C
	 * @since N/C 
	 * @return Set
	 */
    public Set getConfigExecFinanCefs() {
		return configExecFinanCefs;
	}

    /**
     * @param configExecFinanCefs
     * @author N/C
	 * @since N/C 
     */
	public void setConfigExecFinanCefs(Set configExecFinanCefs) {
		this.configExecFinanCefs = configExecFinanCefs;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Long
	 */
	public Long getAnoVersaoCsefv() {
		return anoVersaoCsefv;
	}

	/**
         * @param anoVersaoCsefv
         * @author N/C
	 * @since N/C 
	 */
	public void setAnoVersaoCsefv(Long anoVersaoCsefv) {
		this.anoVersaoCsefv = anoVersaoCsefv;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Long
	 */
	public Long getMesVersaoCsefv() {
		return mesVersaoCsefv;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param mesVersaoCsefv
	 */
	public void setMesVersaoCsefv(Long mesVersaoCsefv) {
		this.mesVersaoCsefv = mesVersaoCsefv;
	}

	
	/**
	 * Este método é usado na combo de Versao/Sistema, só para retornar uma String de label para  a
	 * tag combo:ComboTag.<br>
	 * 
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getSistemaVersaoCsefv(){
		String retorno = "";
		String versao = String.valueOf(this.getVersaoCsefv());
		String mes = (this.getMesVersaoCsefv().longValue() < 10) ? "0" + String.valueOf(this.getMesVersaoCsefv()) : String.valueOf(this.getMesVersaoCsefv());
		String ano = String.valueOf(this.getAnoVersaoCsefv());

		if(this.getConfigSisExecFinanCsef() != null)
			retorno = this.getConfigSisExecFinanCsef().getNomeCsef() + " ver. " + versao + " - " + mes + "/" + ano;
		else
			retorno = "Ver. " + versao + " - " + mes + "/" + ano;
		return retorno;
	}
}
