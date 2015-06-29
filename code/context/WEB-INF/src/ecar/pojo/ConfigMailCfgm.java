package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since N/C
 * @version 0.2, 01/06/2007
 */
public class ConfigMailCfgm implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2023637184065294955L;

	/** identifier field */
    private Integer codCfgm;

    /** nullable persistent field */
    private String descricaoCfgm;

    /** nullable persistent field */
    private String ativoCfgm;

    /** nullable persistent field */
    private ecar.pojo.ConfiguracaoCfg configuracaoCfg;

    /** persistent field */
    private ecar.pojo.TextosSiteTxt textosSiteSms;

    /** persistent field */
    private ecar.pojo.TextosSiteTxt textosSiteMail;

    /** persistent field */
    private Set tfuncacompConfigmailTfacfgms;
    
    /* -- Mantis #11084 (Rogério) -- */
    private String indEnvioObrigatorio; 

	/** 
     * full constructor.<br>
     * 
     * @author N/C
	 * @since N/C 
         * @param descricaoCfgm
         * @param ativoCfgm
         * @param configuracaoCfg
         * @param textosSiteSms
         * @param textosSiteMail
         * @param tfuncacompConfigmailTfacfgms
     */
    public ConfigMailCfgm(String descricaoCfgm, String ativoCfgm, ecar.pojo.ConfiguracaoCfg configuracaoCfg, 
    		ecar.pojo.TextosSiteTxt textosSiteSms, ecar.pojo.TextosSiteTxt textosSiteMail, 
    		Set tfuncacompConfigmailTfacfgms) {
        this.descricaoCfgm = descricaoCfgm;
        this.ativoCfgm = ativoCfgm;
        this.configuracaoCfg = configuracaoCfg;
        this.textosSiteSms = textosSiteSms;
        this.textosSiteMail = textosSiteMail;
        this.tfuncacompConfigmailTfacfgms = tfuncacompConfigmailTfacfgms;
    }

    /** default constructor */
    public ConfigMailCfgm() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @param textosSiteSms
     * @param textosSiteMail
     * @param tfuncacompConfigmailTfacfgms
     */
    public ConfigMailCfgm(ecar.pojo.TextosSiteTxt textosSiteSms, ecar.pojo.TextosSiteTxt textosSiteMail, 
    		Set tfuncacompConfigmailTfacfgms) {
    	this.textosSiteSms = textosSiteSms;
        this.textosSiteMail = textosSiteMail;
        this.tfuncacompConfigmailTfacfgms = tfuncacompConfigmailTfacfgms;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getDescricaoCfgm() {
        return this.descricaoCfgm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param descricaoCfgm
     */
    public void setDescricaoCfgm(String descricaoCfgm) {
        this.descricaoCfgm = descricaoCfgm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getAtivoCfgm() {
        return this.ativoCfgm;
    }

    /**
     * @param ativoCfgm
     * @author N/C
	 * @since N/C 
     */
    public void setAtivoCfgm(String ativoCfgm) {
        this.ativoCfgm = ativoCfgm;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.ConfiguracaoCfg
     */
    public ecar.pojo.ConfiguracaoCfg getConfiguracaoCfg() {
        return this.configuracaoCfg;
    }

    /**
     * @param configuracaoCfg
     * @author N/C
	 * @since N/C 
     */
    public void setConfiguracaoCfg(ecar.pojo.ConfiguracaoCfg configuracaoCfg) {
        this.configuracaoCfg = configuracaoCfg;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.TextosSiteTxt
     */
    public ecar.pojo.TextosSiteTxt getTextosSiteSms() {
        return this.textosSiteSms;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param textosSiteSms
     */
    public void setTextosSiteSms(ecar.pojo.TextosSiteTxt textosSiteSms) {
        this.textosSiteSms = textosSiteSms;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.TextosSiteTxt
     */
    public ecar.pojo.TextosSiteTxt getTextosSiteMail() {
        return this.textosSiteMail;
    }

    /**
     * @param textosSiteMail
     * @author N/C
	 * @since N/C 
     */
    public void setTextosSiteMail(ecar.pojo.TextosSiteTxt textosSiteMail) {
        this.textosSiteMail = textosSiteMail;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
    public Set getTfuncacompConfigmailTfacfgms() {
        return this.tfuncacompConfigmailTfacfgms;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param tfuncacompConfigmailTfacfgms
     */
    public void setTfuncacompConfigmailTfacfgms(Set tfuncacompConfigmailTfacfgms) {
        this.tfuncacompConfigmailTfacfgms = tfuncacompConfigmailTfacfgms;
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codCfgm", getCodCfgm())
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
        if ( !(other instanceof ConfigMailCfgm) ) return false;
        ConfigMailCfgm castOther = (ConfigMailCfgm) other;
        return new EqualsBuilder()
            .append(this.getCodCfgm(), castOther.getCodCfgm())
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
            .append(getCodCfgm())
            .toHashCode();
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Integer
     */
	public Integer getCodCfgm() {
		return codCfgm;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param codCfgm
	 */
	public void setCodCfgm(Integer codCfgm) {
		this.codCfgm = codCfgm;
	}

        /**
         *
         * @return
         */
        public String getIndEnvioObrigatorio() {
		return indEnvioObrigatorio;
	}

        /**
         *
         * @param indEnvioObrigatorio
         */
        public void setIndEnvioObrigatorio(String indEnvioObrigatorio) {
		this.indEnvioObrigatorio = indEnvioObrigatorio;
	}
}
