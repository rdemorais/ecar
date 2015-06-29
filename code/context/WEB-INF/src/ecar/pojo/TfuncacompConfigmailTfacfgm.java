package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TfuncacompConfigmailTfacfgm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 415636910800654768L;

	/** identifier field */
    private ecar.pojo.TfuncacompConfigmailTfacfgmPK comp_id;

    /** nullable persistent field */
    private String enviaSms;

    /** nullable persistent field */
    private String enviaMailTfacfgm;

    /** nullable persistent field */
    private ecar.pojo.ConfigMailCfgm configMailCfgm;

    /** nullable persistent field */
    private ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa;

    /** full constructor
     * @param comp_id
     * @param enviaSms
     * @param configMailCfgm
     * @param tipoFuncAcompTpfa
     * @param enviaMailTfacfgm
     */
    public TfuncacompConfigmailTfacfgm(ecar.pojo.TfuncacompConfigmailTfacfgmPK comp_id, String enviaSms, String enviaMailTfacfgm, ecar.pojo.ConfigMailCfgm configMailCfgm, ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa) {
        this.comp_id = comp_id;
        this.enviaSms = enviaSms;
        this.enviaMailTfacfgm = enviaMailTfacfgm;
        this.configMailCfgm = configMailCfgm;
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
    }

    /** default constructor */
    public TfuncacompConfigmailTfacfgm() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public TfuncacompConfigmailTfacfgm(ecar.pojo.TfuncacompConfigmailTfacfgmPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.TfuncacompConfigmailTfacfgmPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.TfuncacompConfigmailTfacfgmPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public String getEnviaSms() {
        return this.enviaSms;
    }

    /**
     *
     * @param enviaSms
     */
    public void setEnviaSms(String enviaSms) {
        this.enviaSms = enviaSms;
    }

    /**
     *
     * @return
     */
    public String getEnviaMailTfacfgm() {
        return this.enviaMailTfacfgm;
    }

    /**
     *
     * @param enviaMailTfacfgm
     */
    public void setEnviaMailTfacfgm(String enviaMailTfacfgm) {
        this.enviaMailTfacfgm = enviaMailTfacfgm;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ConfigMailCfgm getConfigMailCfgm() {
        return this.configMailCfgm;
    }

    /**
     *
     * @param configMailCfgm
     */
    public void setConfigMailCfgm(ecar.pojo.ConfigMailCfgm configMailCfgm) {
        this.configMailCfgm = configMailCfgm;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.TipoFuncAcompTpfa getTipoFuncAcompTpfa() {
        return this.tipoFuncAcompTpfa;
    }

    /**
     *
     * @param tipoFuncAcompTpfa
     */
    public void setTipoFuncAcompTpfa(ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa) {
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof TfuncacompConfigmailTfacfgm) ) return false;
        TfuncacompConfigmailTfacfgm castOther = (TfuncacompConfigmailTfacfgm) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}
