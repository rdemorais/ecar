package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ConfigTipoDadoCtd implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6796274857955141400L;

	/** identifier field */
    private Long codCtd;

    /** nullable persistent field */
    private String idRotinaCtd;

    /** nullable persistent field */
    private String nomeCtd;

    /** persistent field */
    private Set configExecFinanCefs;

    /** 
     * full constructor 
     * 
     * @author N/C
	 * @since N/C  
     * @param idRotinaCtd
     * @param nomeCtd
     * @param configExecFinanCefs
     */
    public ConfigTipoDadoCtd(String idRotinaCtd, String nomeCtd, Set configExecFinanCefs) {
        this.idRotinaCtd = idRotinaCtd;
        this.nomeCtd = nomeCtd;
        this.configExecFinanCefs = configExecFinanCefs;
    }

    /** default constructor */
    public ConfigTipoDadoCtd() {
    }

    /** 
     * minimal constructor
     * 
     * @author N/C
	 * @since N/C 
     * @param configExecFinanCefs
     */
    public ConfigTipoDadoCtd(Set configExecFinanCefs) {
        this.configExecFinanCefs = configExecFinanCefs;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getCodCtd() {
        return this.codCtd;
    }

    /**
     * @param codCtd
     * @author N/C
	 * @since N/C 
     */
    public void setCodCtd(Long codCtd) {
        this.codCtd = codCtd;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIdRotinaCtd() {
        return this.idRotinaCtd;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param idRotinaCtd
     */
    public void setIdRotinaCtd(String idRotinaCtd) {
        this.idRotinaCtd = idRotinaCtd;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getNomeCtd() {
        return this.nomeCtd;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param nomeCtd
     */
    public void setNomeCtd(String nomeCtd) {
        this.nomeCtd = nomeCtd;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
    public Set getConfigExecFinanCefs() {
        return this.configExecFinanCefs;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param configExecFinanCefs
     */
    public void setConfigExecFinanCefs(Set configExecFinanCefs) {
        this.configExecFinanCefs = configExecFinanCefs;
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codCtd", getCodCtd())
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
        if ( !(other instanceof ConfigTipoDadoCtd) ) return false;
        ConfigTipoDadoCtd castOther = (ConfigTipoDadoCtd) other;
        return new EqualsBuilder()
            .append(this.getCodCtd(), castOther.getCodCtd())
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
            .append(getCodCtd())
            .toHashCode();
    }

}
