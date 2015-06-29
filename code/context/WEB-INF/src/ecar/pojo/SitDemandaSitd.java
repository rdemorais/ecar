package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SitDemandaSitd implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4955905627522727818L;

	/** identifier field */
    private Long codSitd;

    /** nullable persistent field */
    private String descricaoSitd;

    /** nullable persistent field */
    private String indConclusaoSitd;

    /** nullable persistent field */
    private String indPrimeiraSituacaoSitd;

    /** persistent field */
    private Set regDemandaRegds;
        
    private Set visaoSituacaoDemandas;


    /** full constructor
     * @param descricaoSitd
     * @param indConclusaoSitd
     * @param regDemandaRegds
     * @param indPrimeiraSituacaoSitd
     */
    public SitDemandaSitd(String descricaoSitd, String indConclusaoSitd, String indPrimeiraSituacaoSitd, Set regDemandaRegds) {
        this.descricaoSitd = descricaoSitd;
        this.indConclusaoSitd = indConclusaoSitd;
        this.indPrimeiraSituacaoSitd = indPrimeiraSituacaoSitd;
        this.regDemandaRegds = regDemandaRegds;
    }

    /** default constructor */
    public SitDemandaSitd() {
    }

    /** minimal constructor
     * @param regDemandaRegds
     */
    public SitDemandaSitd(Set regDemandaRegds) {
        this.regDemandaRegds = regDemandaRegds;
    }

    /**
     *
     * @return
     */
    public Long getCodSitd() {
        return this.codSitd;
    }

    /**
     *
     * @param codSitd
     */
    public void setCodSitd(Long codSitd) {
        this.codSitd = codSitd;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSitd() {
        return this.descricaoSitd;
    }

    /**
     *
     * @param descricaoSitd
     */
    public void setDescricaoSitd(String descricaoSitd) {
        this.descricaoSitd = descricaoSitd;
    }

    /**
     *
     * @return
     */
    public String getIndConclusaoSitd() {
        return this.indConclusaoSitd;
    }

    /**
     *
     * @param indConclusaoSitd
     */
    public void setIndConclusaoSitd(String indConclusaoSitd) {
        this.indConclusaoSitd = indConclusaoSitd;
    }

    /**
     *
     * @return
     */
    public String getIndPrimeiraSituacaoSitd() {
        return this.indPrimeiraSituacaoSitd;
    }

    /**
     *
     * @param indPrimeiraSituacaoSitd
     */
    public void setIndPrimeiraSituacaoSitd(String indPrimeiraSituacaoSitd) {
        this.indPrimeiraSituacaoSitd = indPrimeiraSituacaoSitd;
    }

    /**
     *
     * @return
     */
    public Set getRegDemandaRegds() {
        return this.regDemandaRegds;
    }

    /**
     *
     * @param regDemandaRegds
     */
    public void setRegDemandaRegds(Set regDemandaRegds) {
        this.regDemandaRegds = regDemandaRegds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSitd", getCodSitd())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof SitDemandaSitd) ) return false;
        SitDemandaSitd castOther = (SitDemandaSitd) other;
        return new EqualsBuilder()
            .append(this.getCodSitd(), castOther.getCodSitd())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSitd())
            .toHashCode();
    }
    
    



	public void setVisaoSituacaoDemandas(Set visaoSituacaoDemandas) {
		this.visaoSituacaoDemandas = visaoSituacaoDemandas;
	}

	public Set getVisaoSituacaoDemandas() {
		return visaoSituacaoDemandas;
	}




	
}
