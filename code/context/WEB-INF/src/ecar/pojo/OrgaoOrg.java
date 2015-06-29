package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class OrgaoOrg implements Serializable {


	private static final long serialVersionUID = 5642847112240244380L;

	/** identifier field */
    private Long codOrg;

    /** nullable persistent field */
    private String siglaOrg;

    /** nullable persistent field */
    private String descricaoOrg;

    /** nullable persistent field */
    private String indAtivoOrg;

    /** nullable persistent field */
    private Long codigoIdentOrg;

    /** persistent field */
    private Set acompReferenciaArefs;

    /** persistent field */
    private Set usuarioUsus;

    /** persistent field */
    private Set itemEstruturaIettsByCodOrgaoResponsavel1Iett;

    /** persistent field */
    private Set itemEstruturaIettsByCodOrgaoResponsavel2Iett;

    /** persistent field */
    private Set unidadeOrcamentariaUO;
    
    /** persistent field */
    private Set regDemandaRegds;
    
    private Set itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev;
    private Set itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev;
    
    private ecar.pojo.PoderPod poderPod;
    
    /* Mantis #2156 */
    private Set historicoIettH1s;
    private Set historicoIettH2s;
    
    private Set orgaoPeriodoExercicioOrgPerexes;

    /** persistent field */
    private Set itemEstruturaIettsByCodOrgaoResponsavel1IettPPA;
    
    /**
     *
     * @return
     */
    public Set getItemEstruturaIettsByCodOrgaoResponsavel1IettPPA() {
		return itemEstruturaIettsByCodOrgaoResponsavel1IettPPA;
	}

    /**
     *
     * @param itemEstruturaIettsByCodOrgaoResponsavel1IettPPA
     */
    public void setItemEstruturaIettsByCodOrgaoResponsavel1IettPPA(
			Set itemEstruturaIettsByCodOrgaoResponsavel1IettPPA) {
		this.itemEstruturaIettsByCodOrgaoResponsavel1IettPPA = itemEstruturaIettsByCodOrgaoResponsavel1IettPPA;
	}

    /**
     *
     * @return
     */
    public Set getHistoricoIettH1s() {
		return historicoIettH1s;
	}

        /**
         *
         * @param historicoIettH1s
         */
        public void setHistoricoIettH1s(Set historicoIettH1s) {
		this.historicoIettH1s = historicoIettH1s;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettH2s() {
		return historicoIettH2s;
	}

        /**
         *
         * @param historicoIettH2s
         */
        public void setHistoricoIettH2s(Set historicoIettH2s) {
		this.historicoIettH2s = historicoIettH2s;
	}

        /** full constructor
         * @param siglaOrg
         * @param codigoIdentOrg
         * @param descricaoOrg
         * @param indAtivoOrg
         * @param itemEstruturaIettsByCodOrgaoResponsavel2Iett
         * @param usuarioUsus
         * @param unidadeOrcamentariaUO
         * @param acompReferenciaArefs
         * @param itemEstruturaIettsByCodOrgaoResponsavel1Iett
         * @param itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev
         * @param regDemandaRegds
         * @param poderPod
         * @param itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev
         */
    public OrgaoOrg(String siglaOrg, String descricaoOrg, String indAtivoOrg, Long codigoIdentOrg, Set acompReferenciaArefs, Set usuarioUsus, Set itemEstruturaIettsByCodOrgaoResponsavel1Iett, Set itemEstruturaIettsByCodOrgaoResponsavel2Iett, Set unidadeOrcamentariaUO, Set regDemandaRegds, Set itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev, Set itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev, PoderPod poderPod) {
        this.siglaOrg = siglaOrg;
        this.descricaoOrg = descricaoOrg;
        this.indAtivoOrg = indAtivoOrg;
        this.acompReferenciaArefs = acompReferenciaArefs;
        this.usuarioUsus = usuarioUsus;
        this.codigoIdentOrg = codigoIdentOrg;
        this.itemEstruturaIettsByCodOrgaoResponsavel1Iett = itemEstruturaIettsByCodOrgaoResponsavel1Iett;
        this.itemEstruturaIettsByCodOrgaoResponsavel2Iett = itemEstruturaIettsByCodOrgaoResponsavel2Iett;
        this.unidadeOrcamentariaUO = unidadeOrcamentariaUO;
        this.regDemandaRegds = regDemandaRegds;
        this.itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev = itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev;
        this.itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev = itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev;
        this.poderPod = poderPod;
    }

    /** default constructor */
    public OrgaoOrg() {
    }
    
    public OrgaoOrg(Long codOrg, String siglaOrg){
    	this.codOrg = codOrg;
    	this.siglaOrg = siglaOrg;
    }

    /** minimal constructor
     * @param acompReferenciaArefs
     * @param itemEstruturaIettsByCodOrgaoResponsavel1Iett
     * @param usuarioUsus
     * @param itemEstruturaIettsByCodOrgaoResponsavel2Iett
     * @param unidadeOrcamentariaUO
     * @param regDemandaRegds
     * @param itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev
     * @param itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev
     */
    public OrgaoOrg(Set acompReferenciaArefs, Set usuarioUsus, Set itemEstruturaIettsByCodOrgaoResponsavel1Iett, Set itemEstruturaIettsByCodOrgaoResponsavel2Iett, Set unidadeOrcamentariaUO, Set regDemandaRegds, Set itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev, Set itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev) {
        this.acompReferenciaArefs = acompReferenciaArefs;
        this.usuarioUsus = usuarioUsus;
        this.itemEstruturaIettsByCodOrgaoResponsavel1Iett = itemEstruturaIettsByCodOrgaoResponsavel1Iett;
        this.itemEstruturaIettsByCodOrgaoResponsavel2Iett = itemEstruturaIettsByCodOrgaoResponsavel2Iett;
        this.unidadeOrcamentariaUO = unidadeOrcamentariaUO;
        this.regDemandaRegds = regDemandaRegds;        
        this.itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev = itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev;
        this.itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev = itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev;

    }

    /**
     *
     * @return
     */
    public Long getCodOrg() {
        return this.codOrg;
    }

    /**
     *
     * @param codOrg
     */
    public void setCodOrg(Long codOrg) {
        this.codOrg = codOrg;
    }

    /**
     *
     * @return
     */
    public String getSiglaOrg() {
        return this.siglaOrg;
    }

    /**
     *
     * @param siglaOrg
     */
    public void setSiglaOrg(String siglaOrg) {
        this.siglaOrg = siglaOrg;
    }

    /**
     *
     * @return
     */
    public String getDescricaoOrg() {
        return this.descricaoOrg;
    }

    /**
     *
     * @param descricaoOrg
     */
    public void setDescricaoOrg(String descricaoOrg) {
        this.descricaoOrg = descricaoOrg;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoOrg() {
        return this.indAtivoOrg;
    }

    /**
     *
     * @param indAtivoOrg
     */
    public void setIndAtivoOrg(String indAtivoOrg) {
        this.indAtivoOrg = indAtivoOrg;
    }

    /**
     *
     * @return
     */
    public Set getAcompReferenciaArefs() {
        return this.acompReferenciaArefs;
    }

    /**
     *
     * @param acompReferenciaArefs
     */
    public void setAcompReferenciaArefs(Set acompReferenciaArefs) {
        this.acompReferenciaArefs = acompReferenciaArefs;
    }

    /**
     *
     * @return
     */
    public Set getUsuarioUsus() {
        return this.usuarioUsus;
    }

    /**
     *
     * @param usuarioUsus
     */
    public void setUsuarioUsus(Set usuarioUsus) {
        this.usuarioUsus = usuarioUsus;
    }

    /**
     *
     * @return
     */
    public Set getItemEstruturaIettsByCodOrgaoResponsavel1Iett() {
        return this.itemEstruturaIettsByCodOrgaoResponsavel1Iett;
    }

    /**
     *
     * @param itemEstruturaIettsByCodOrgaoResponsavel1Iett
     */
    public void setItemEstruturaIettsByCodOrgaoResponsavel1Iett(Set itemEstruturaIettsByCodOrgaoResponsavel1Iett) {
        this.itemEstruturaIettsByCodOrgaoResponsavel1Iett = itemEstruturaIettsByCodOrgaoResponsavel1Iett;
    }

    /**
     *
     * @return
     */
    public Set getItemEstruturaIettsByCodOrgaoResponsavel2Iett() {
        return this.itemEstruturaIettsByCodOrgaoResponsavel2Iett;
    }

    /**
     *
     * @param itemEstruturaIettsByCodOrgaoResponsavel2Iett
     */
    public void setItemEstruturaIettsByCodOrgaoResponsavel2Iett(Set itemEstruturaIettsByCodOrgaoResponsavel2Iett) {
        this.itemEstruturaIettsByCodOrgaoResponsavel2Iett = itemEstruturaIettsByCodOrgaoResponsavel2Iett;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codOrg", getCodOrg())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof OrgaoOrg) ) return false;
        OrgaoOrg castOther = (OrgaoOrg) other;
        return new EqualsBuilder()
            .append(this.getCodOrg(), castOther.getCodOrg())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodOrg())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Long getCodigoIdentOrg() {
		return codigoIdentOrg;
	}

    /**
     *
     * @param codigoIdentOrg
     */
    public void setCodigoIdentOrg(Long codigoIdentOrg) {
		this.codigoIdentOrg = codigoIdentOrg;
	}

    /**
     *
     * @return
     */
    public Set getUnidadeOrcamentariaUO() {
		return unidadeOrcamentariaUO;
	}

        /**
         *
         * @param unidadeOrcamentariaUO
         */
        public void setUnidadeOrcamentariaUO(Set unidadeOrcamentariaUO) {
		this.unidadeOrcamentariaUO = unidadeOrcamentariaUO;
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

    /**
     *
     * @return
     */
    public Set getItemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev() {
		return itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev;
	}

        /**
         *
         * @param itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev
         */
        public void setItemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev(
			Set itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev) {
		this.itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev = itemEstruturarevisaoIettrevsByCodorgaoresponsavel1Iettrev;
	}

        /**
         *
         * @return
         */
        public Set getItemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev() {
		return itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev;
	}

        /**
         *
         * @param itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev
         */
        public void setItemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev(
			Set itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev) {
		this.itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev = itemEstruturarevisaoIettrevsByCodorgaoresponsavel2Iettrev;
	}

	/**
	 * @return the poderPod
	 */
	public ecar.pojo.PoderPod getPoderPod() {
		return poderPod;
	}

	/**
	 * @param poderPod the poderPod to set
	 */
	public void setPoderPod(ecar.pojo.PoderPod poderPod) {
		this.poderPod = poderPod;
	}

	/**
	 * @return the orgaoPeriodoExercicioOrgPerexes
	 */
	public Set getOrgaoPeriodoExercicioOrgPerexes() {
		return orgaoPeriodoExercicioOrgPerexes;
	}

	/**
	 * @param orgaoPeriodoExercicioOrgPerexes the orgaoPeriodoExercicioOrgPerexes to set
	 */
	public void setOrgaoPeriodoExercicioOrgPerexes(
			Set orgaoPeriodoExercicioOrgPerexes) {
		this.orgaoPeriodoExercicioOrgPerexes = orgaoPeriodoExercicioOrgPerexes;
	}
	
}
