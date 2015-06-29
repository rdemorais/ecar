package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ConfigExecFinanCef implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4632396800304471451L;

	/** identifier field */
    private Long codCef;

    /** nullable persistent field */
    private Integer seqApresentacaoCef;

    /** nullable persistent field */
    private String idXmlCef;

    /** nullable persistent field */
    private String labelCef;

    /** nullable persistent field */
    private String nomeCef;

    /** nullable persistent field */
    private Integer numCaracteresCef;

    /** persistent field */
    private ecar.pojo.ConfigTipoDadoCtd configTipoDadoCtd;
       
    private ConfigSisExecFinanCsefv configSisExecFinanCsefv;
   

    /** 
     * full constructor.<br>
     * 
     * @author N/C
	 * @since N/C 
     * @param configSisExecFinanCsefv
     * @param seqApresentacaoCef
     * @param idXmlCef
     * @param labelCef
     * @param nomeCef
     * @param numCaracteresCef
     * @param configTipoDadoCtd
     */
    public ConfigExecFinanCef(ConfigSisExecFinanCsefv configSisExecFinanCsefv, Integer seqApresentacaoCef, 
    		String idXmlCef, String labelCef, String nomeCef, Integer numCaracteresCef, 
    		ecar.pojo.ConfigTipoDadoCtd configTipoDadoCtd) {
        this.seqApresentacaoCef = seqApresentacaoCef;
        this.idXmlCef = idXmlCef;
        this.labelCef = labelCef;
        this.nomeCef = nomeCef;
        this.numCaracteresCef = numCaracteresCef;
        this.configTipoDadoCtd = configTipoDadoCtd;
        this.configSisExecFinanCsefv = configSisExecFinanCsefv;
    }

    /** default constructor */
    public ConfigExecFinanCef() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @param configSisExecFinanCsefv 
     * @param configTipoDadoCtd
     * @author N/C
	 * @since N/C 
     */
    public ConfigExecFinanCef(ConfigSisExecFinanCsefv configSisExecFinanCsefv, 
    		ecar.pojo.ConfigTipoDadoCtd configTipoDadoCtd) {
        this.configTipoDadoCtd = configTipoDadoCtd;
        this.configSisExecFinanCsefv = configSisExecFinanCsefv;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
	public Long getCodCef() {
        return this.codCef;
    }

	/**
	 * @author N/C
	 * @since N/C 
         * @param codCef
	 */
    public void setCodCef(Long codCef) {
        this.codCef = codCef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Integer
     */
    public Integer getSeqApresentacaoCef() {
        return this.seqApresentacaoCef;
    }

    /**
     * @param seqApresentacaoCef
     * @author N/C
	 * @since N/C 
     */
    public void setSeqApresentacaoCef(Integer seqApresentacaoCef) {
        this.seqApresentacaoCef = seqApresentacaoCef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIdXmlCef() {
        return this.idXmlCef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param idXmlCef
     */
    public void setIdXmlCef(String idXmlCef) {
        this.idXmlCef = idXmlCef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getLabelCef() {
        return this.labelCef;
    }

    /**
     * @param labelCef
     * @author N/C
	 * @since N/C 
     */
    public void setLabelCef(String labelCef) {
        this.labelCef = labelCef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getNomeCef() {
        return this.nomeCef;
    }

    /**
     * @param nomeCef
     * @author N/C
	 * @since N/C 
     */
    public void setNomeCef(String nomeCef) {
        this.nomeCef = nomeCef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Integer
     */
    public Integer getNumCaracteresCef() {
        return this.numCaracteresCef;
    }

    /**
     * @param numCaracteresCef
     * @author N/C
	 * @since N/C 
     */
    public void setNumCaracteresCef(Integer numCaracteresCef) {
        this.numCaracteresCef = numCaracteresCef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.ConfigTipoDadoCtd
     */
    public ecar.pojo.ConfigTipoDadoCtd getConfigTipoDadoCtd() {
        return this.configTipoDadoCtd;
    }

    /**
     *
     * @param configTipoDadoCtd
     */
    public void setConfigTipoDadoCtd(ecar.pojo.ConfigTipoDadoCtd configTipoDadoCtd) {
        this.configTipoDadoCtd = configTipoDadoCtd;
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codCef", getCodCef())
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
        if ( !(other instanceof ConfigExecFinanCef) ) return false;
        ConfigExecFinanCef castOther = (ConfigExecFinanCef) other;
        return new EqualsBuilder()
            .append(this.getCodCef(), castOther.getCodCef())
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
            .append(getCodCef())
            .toHashCode();
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ConfigSisExecFinanCsefv
     */
	public ConfigSisExecFinanCsefv getConfigSisExecFinanCsefv() {
		return configSisExecFinanCsefv;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param configSisExecFinanCsefv
	 */
	public void setConfigSisExecFinanCsefv(
			ConfigSisExecFinanCsefv configSisExecFinanCsefv) {
		this.configSisExecFinanCsefv = configSisExecFinanCsefv;
	}

}
