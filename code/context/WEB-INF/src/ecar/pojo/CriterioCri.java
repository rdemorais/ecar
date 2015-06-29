package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CriterioCri implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 76564882978345910L;

	/** identifier field */
    private Long codCri;

    /** nullable persistent field */
    private String indAtivoCri;

    /** nullable persistent field */
    private Date dataInclusaoCri;

    /** nullable persistent field */
    private String descricaoCri;

    /** persistent field */
    private Set itemEstrutCriterioIettcs;
    
    /* Mantis #2156 */
    private Set historicoIettcHs;

    /**
     *
     * @return
     */
    public Set getHistoricoIettcHs() {
		return historicoIettcHs;
	}

    /**
     *
     * @param historicoIettcHs
     */
    public void setHistoricoIettcHs(Set historicoIettcHs) {
		this.historicoIettcHs = historicoIettcHs;
	}

        /** full constructor
         * @param indAtivoCri
         * @param dataInclusaoCri
         * @param descricaoCri
         * @param itemEstrutCriterioIettcs
         */
    public CriterioCri(String indAtivoCri, Date dataInclusaoCri, String descricaoCri, Set itemEstrutCriterioIettcs) {
        this.indAtivoCri = indAtivoCri;
        this.dataInclusaoCri = dataInclusaoCri;
        this.descricaoCri = descricaoCri;
        this.itemEstrutCriterioIettcs = itemEstrutCriterioIettcs;
    }

    /** default constructor */
    public CriterioCri() {
    }

    /** minimal constructor
     * @param itemEstrutCriterioIettcs
     */
    public CriterioCri(Set itemEstrutCriterioIettcs) {
        this.itemEstrutCriterioIettcs = itemEstrutCriterioIettcs;
    }

    /**
     *
     * @return
     */
    public Long getCodCri() {
        return this.codCri;
    }

    /**
     *
     * @param codCri
     */
    public void setCodCri(Long codCri) {
        this.codCri = codCri;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoCri() {
        return this.indAtivoCri;
    }

    /**
     *
     * @param indAtivoCri
     */
    public void setIndAtivoCri(String indAtivoCri) {
        this.indAtivoCri = indAtivoCri;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoCri() {
        return this.dataInclusaoCri;
    }

    /**
     *
     * @param dataInclusaoCri
     */
    public void setDataInclusaoCri(Date dataInclusaoCri) {
        this.dataInclusaoCri = dataInclusaoCri;
    }

    /**
     *
     * @return
     */
    public String getDescricaoCri() {
        return this.descricaoCri;
    }

    /**
     *
     * @param descricaoCri
     */
    public void setDescricaoCri(String descricaoCri) {
        this.descricaoCri = descricaoCri;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutCriterioIettcs() {
        return this.itemEstrutCriterioIettcs;
    }

    /**
     *
     * @param itemEstrutCriterioIettcs
     */
    public void setItemEstrutCriterioIettcs(Set itemEstrutCriterioIettcs) {
        this.itemEstrutCriterioIettcs = itemEstrutCriterioIettcs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codCri", getCodCri())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof CriterioCri) ) return false;
        CriterioCri castOther = (CriterioCri) other;
        return new EqualsBuilder()
            .append(this.getCodCri(), castOther.getCodCri())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodCri())
            .toHashCode();
    }

}
