package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class BeneficiarioBnf implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2786335167028915811L;

	/** identifier field */
    private Long codBnf;

    /** nullable persistent field */
    private String indAtivoBnf;

    /** nullable persistent field */
    private Date dataInclusaoBnf;

    /** nullable persistent field */
    private String nomeBnf;

    /** persistent field */
    private Set itemEstrtBenefIettbs;
    
    /* Mantis #2156 */
    private Set historicoIettbHs;

    /**
     *
     * @return
     */
    public Set getHistoricoIettbHs() {
		return historicoIettbHs;
	}

    /**
     *
     * @param historicoIettbHs
     */
    public void setHistoricoIettbHs(Set historicoIettbHs) {
		this.historicoIettbHs = historicoIettbHs;
	}

	/** 
     * full constructor.<br>
     * 
     * @author N/C
	 * @since N/C 
         * @param indAtivoBnf
         * @param dataInclusaoBnf
         * @param nomeBnf
         * @param itemEstrtBenefIettbs
     */
    public BeneficiarioBnf(String indAtivoBnf, Date dataInclusaoBnf, String nomeBnf, Set itemEstrtBenefIettbs) {
        this.indAtivoBnf = indAtivoBnf;
        this.dataInclusaoBnf = dataInclusaoBnf;
        this.nomeBnf = nomeBnf;
        this.itemEstrtBenefIettbs = itemEstrtBenefIettbs;
    }

    /** default constructor */
    public BeneficiarioBnf() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @param itemEstrtBenefIettbs
     * @author N/C
	 * @since N/C 
     * 
     */
    public BeneficiarioBnf(Set itemEstrtBenefIettbs) {
        this.itemEstrtBenefIettbs = itemEstrtBenefIettbs;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getCodBnf() {
        return this.codBnf;
    }

    /**
     * @param codBnf
     * @author N/C
	 * @since N/C 
     */
    public void setCodBnf(Long codBnf) {
        this.codBnf = codBnf;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndAtivoBnf() {
        return this.indAtivoBnf;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param indAtivoBnf
     */
    public void setIndAtivoBnf(String indAtivoBnf) {
        this.indAtivoBnf = indAtivoBnf;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Date
     */
    public Date getDataInclusaoBnf() {
        return this.dataInclusaoBnf;
    }

    /**
     * @param dataInclusaoBnf
     * @author N/C
	 * @since N/C 
     */
    public void setDataInclusaoBnf(Date dataInclusaoBnf) {
        this.dataInclusaoBnf = dataInclusaoBnf;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getNomeBnf() {
        return this.nomeBnf;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param nomeBnf
     */
    public void setNomeBnf(String nomeBnf) {
        this.nomeBnf = nomeBnf;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
    public Set getItemEstrtBenefIettbs() {
        return this.itemEstrtBenefIettbs;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param itemEstrtBenefIettbs
     */
    public void setItemEstrtBenefIettbs(Set itemEstrtBenefIettbs) {
        this.itemEstrtBenefIettbs = itemEstrtBenefIettbs;
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codBnf", getCodBnf())
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
        if ( !(other instanceof BeneficiarioBnf) ) return false;
        BeneficiarioBnf castOther = (BeneficiarioBnf) other;
        return new EqualsBuilder()
            .append(this.getCodBnf(), castOther.getCodBnf())
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
            .append(getCodBnf())
            .toHashCode();
    }

}
