package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ConfigSisExecFinanCsef implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1288629963141422848L;

	/** identifier field */
    private Long codCsef;

    /** nullable persistent field */
    private String indAtivoCsef;

    /** nullable persistent field */
    private Date dataInclusaoCsef;

    /** nullable persistent field */
    private String siglaCsef;

    /** nullable persistent field */
    private String nomeCsef;

    /** nullable persistent field */
    private String indPermiteValormanualorcCsef;

    /** persistent field */
    private Set fonteRecursoFonrs;

    /** persistent field */
    private Set configSisExecFinanCsefvs;



    /**
     *  full constructor 
     *
     * @author N/C
	 * @since N/C 
     * @param indAtivoCsef
     * @param dataInclusaoCsef
     * @param siglaCsef
     * @param nomeCsef
     * @param configSisExecFinanCsefvs
     * @param fonteRecursoFonrs
     * @param indPermiteValormanualorcCsef
     */
    public ConfigSisExecFinanCsef(String indAtivoCsef, Date dataInclusaoCsef, String siglaCsef, 
    		String nomeCsef, String indPermiteValormanualorcCsef, Set fonteRecursoFonrs, Set configSisExecFinanCsefvs) {
        this.indAtivoCsef = indAtivoCsef;
        this.dataInclusaoCsef = dataInclusaoCsef;
        this.siglaCsef = siglaCsef;
        this.nomeCsef = nomeCsef;
        this.indPermiteValormanualorcCsef = indPermiteValormanualorcCsef;
        this.fonteRecursoFonrs = fonteRecursoFonrs;
        this.configSisExecFinanCsefvs = configSisExecFinanCsefvs;
    }

    /** default constructor */
    public ConfigSisExecFinanCsef() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @param fonteRecursoFonrs
     * @param configSisExecFinanCsefvs
     * @author N/C
	 * @since N/C 
     */
    public ConfigSisExecFinanCsef(Set fonteRecursoFonrs, Set configSisExecFinanCsefvs) {
        this.fonteRecursoFonrs = fonteRecursoFonrs;
        this.configSisExecFinanCsefvs = configSisExecFinanCsefvs;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getCodCsef() {
        return this.codCsef;
    }

    /**
     * @param codCsef
     * @author N/C
	 * @since N/C 
     */
    public void setCodCsef(Long codCsef) {
        this.codCsef = codCsef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndAtivoCsef() {
        return this.indAtivoCsef;
    }

    /**
     * @param indAtivoCsef
     * @author N/C
	 * @since N/C 
     */
    public void setIndAtivoCsef(String indAtivoCsef) {
        this.indAtivoCsef = indAtivoCsef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Date
     */
    public Date getDataInclusaoCsef() {
        return this.dataInclusaoCsef;
    }

    /**
     * @param dataInclusaoCsef
     * @author N/C
	 * @since N/C 
     */
    public void setDataInclusaoCsef(Date dataInclusaoCsef) {
        this.dataInclusaoCsef = dataInclusaoCsef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getSiglaCsef() {
        return this.siglaCsef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param siglaCsef
     */
    public void setSiglaCsef(String siglaCsef) {
        this.siglaCsef = siglaCsef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getNomeCsef() {
        return this.nomeCsef;
    }

    /**
     * @param nomeCsef
     * @author N/C
	 * @since N/C 
     */
    public void setNomeCsef(String nomeCsef) {
        this.nomeCsef = nomeCsef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndPermiteValormanualorcCsef() {
        return this.indPermiteValormanualorcCsef;
    }

    /**
     * @param indPermiteValormanualorcCsef
     * @author N/C
	 * @since N/C 
     */
    public void setIndPermiteValormanualorcCsef(String indPermiteValormanualorcCsef) {
        this.indPermiteValormanualorcCsef = indPermiteValormanualorcCsef;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
    public Set getFonteRecursoFonrs() {
        return this.fonteRecursoFonrs;
    }

    /**
     * @param fonteRecursoFonrs
     * @author N/C
	 * @since N/C 
     */
    public void setFonteRecursoFonrs(Set fonteRecursoFonrs) {
        this.fonteRecursoFonrs = fonteRecursoFonrs;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
    public Set getConfigSisExecFinanCsefvs() {
        return this.configSisExecFinanCsefvs;
    }

    /**
     * @param configSisExecFinanCsefvs
     * @author N/C
	 * @since N/C 
     */
    public void setConfigSisExecFinanCsefvs(Set configSisExecFinanCsefvs) {
        this.configSisExecFinanCsefvs = configSisExecFinanCsefvs;
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codCsef", getCodCsef())
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
        if ( !(other instanceof ConfigSisExecFinanCsef) ) return false;
        ConfigSisExecFinanCsef castOther = (ConfigSisExecFinanCsef) other;
        return new EqualsBuilder()
            .append(this.getCodCsef(), castOther.getCodCsef())
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
            .append(getCodCsef())
            .toHashCode();
    }
}
