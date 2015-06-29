package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class LocalItemLit implements Serializable,Comparable<LocalItemLit> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -917656525916641866L;

	/** identifier field */
    private Long codLit;

    /** nullable persistent field */
    private String indAtivoLit;

    /** nullable persistent field */
    private Date dataInclusaoLit;

    private Long codLgp;
    
    /** nullable persistent field */
    private String identificacaoLit;
    
    /** nullable persistent field */
    private String siglaLit;
    
    private String codIbgeLit;
    
    private String codPlanejamentoLit;

    /** persistent field */
    private ecar.pojo.LocalGrupoLgp localGrupoLgp;

    /** persistent field */
    private Set acompRealFisicoLocalArfls;
    
    private Set localItemHierarquiaLithsByCodLit;

    /** persistent field */
    private Set localItemHierarquiaLithsByCodLitPai;

    /** persistent field */
    private Set itemEstrutLocalIettls;

    
    /** persistent field */
    private Set localDemandaLdems;

    /** persistent field */
    private Set localEntidadeLents;

    /** persistent field */
    private Set locAtributoLocas;    
    
    private Set itemEstLocalRevIettlrs;
    
    private Set itemEstrtIndResulLocalIettirls;

    /* Mantis #2156 */
    private Set historicoIettlHs;
    
    /**
     *
     * @return
     */
    public Set getHistoricoIettlHs() {
		return historicoIettlHs;
	}

    /**
     *
     * @param historicoIettlHs
     */
    public void setHistoricoIettlHs(Set historicoIettlHs) {
		this.historicoIettlHs = historicoIettlHs;
	}

        /** full constructor
         * @param indAtivoLit
         * @param localEntidadeLents
         * @param dataInclusaoLit
         * @param identificacaoLit
         * @param codPlanejamentoLit
         * @param localGrupoLgp
         * @param codIbgeLit
         * @param locAtributoLocas
         * @param itemEstrutLocalIettls 
         * @param itemEstLocalRevIettlrs
         * @param localItemHierarquiaLithsByCodLitPai
         * @param localItemHierarquiaLithsByCodLit
         * @param siglaLit
         * @param localDemandaLdems
         * @param acompRealFisicoLocalArfls
         */
    public LocalItemLit(String indAtivoLit, Date dataInclusaoLit, String identificacaoLit, String codIbgeLit, String codPlanejamentoLit, ecar.pojo.LocalGrupoLgp localGrupoLgp, Set localItemHierarquiaLithsByCodLit, Set localItemHierarquiaLithsByCodLitPai, Set itemEstrutLocalIettls, Set localDemandaLdems, Set localEntidadeLents, Set locAtributoLocas, Set itemEstLocalRevIettlrs, Set acompRealFisicoLocalArfls, String siglaLit) {
        this.indAtivoLit = indAtivoLit;
        this.dataInclusaoLit = dataInclusaoLit;
        this.identificacaoLit = identificacaoLit;
        this.codIbgeLit = codIbgeLit;
        this.codPlanejamentoLit = codPlanejamentoLit;
        this.localGrupoLgp = localGrupoLgp;
        this.localItemHierarquiaLithsByCodLit = localItemHierarquiaLithsByCodLit;
        this.localItemHierarquiaLithsByCodLitPai = localItemHierarquiaLithsByCodLitPai;
        this.itemEstrutLocalIettls = itemEstrutLocalIettls;
        this.localDemandaLdems = localDemandaLdems;
        this.localEntidadeLents = localEntidadeLents;
        this.locAtributoLocas = locAtributoLocas;        
        this.itemEstLocalRevIettlrs = itemEstLocalRevIettlrs;
        this.acompRealFisicoLocalArfls = acompRealFisicoLocalArfls;
        this.itemEstrtIndResulLocalIettirls = itemEstrtIndResulLocalIettirls;
        this.siglaLit = siglaLit;
    }

    /** default constructor */
    public LocalItemLit() {
    }

    /** minimal constructor
     * @param localGrupoLgp
     * @param localItemHierarquiaLithsByCodLit
     * @param localItemHierarquiaLithsByCodLitPai
     * @param acompRealFisicoLocalArfls
     * @param localDemandaLdems
     * @param itemEstrutLocalIettls
     * @param locAtributoLocas
     * @param localEntidadeLents
     * @param itemEstLocalRevIettlrs
     */
    public LocalItemLit(ecar.pojo.LocalGrupoLgp localGrupoLgp, Set localItemHierarquiaLithsByCodLit, Set localItemHierarquiaLithsByCodLitPai, Set itemEstrutLocalIettls, Set localDemandaLdems, Set localEntidadeLents, Set locAtributoLocas, Set itemEstLocalRevIettlrs, Set acompRealFisicoLocalArfls) {
        this.localGrupoLgp = localGrupoLgp;
        this.localItemHierarquiaLithsByCodLit = localItemHierarquiaLithsByCodLit;
        this.localItemHierarquiaLithsByCodLitPai = localItemHierarquiaLithsByCodLitPai;
        this.itemEstrutLocalIettls = itemEstrutLocalIettls;
        this.localDemandaLdems = localDemandaLdems;
        this.localEntidadeLents = localEntidadeLents;
        this.locAtributoLocas = locAtributoLocas;        
        this.itemEstLocalRevIettlrs = itemEstLocalRevIettlrs;
        this.acompRealFisicoLocalArfls = acompRealFisicoLocalArfls;
    }

    /**
     *
     * @return
     */
    public Long getCodLit() {
        return this.codLit;
    }

    /**
     *
     * @param codLit
     */
    public void setCodLit(Long codLit) {
        this.codLit = codLit;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoLit() {
        return this.indAtivoLit;
    }

    /**
     *
     * @param indAtivoLit
     */
    public void setIndAtivoLit(String indAtivoLit) {
        this.indAtivoLit = indAtivoLit;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoLit() {
        return this.dataInclusaoLit;
    }

    /**
     *
     * @param dataInclusaoLit
     */
    public void setDataInclusaoLit(Date dataInclusaoLit) {
        this.dataInclusaoLit = dataInclusaoLit;
    }

    /**
     *
     * @return
     */
    public String getIdentificacaoLit() {
        return this.identificacaoLit;
    }

    /**
     *
     * @param identificacaoLit
     */
    public void setIdentificacaoLit(String identificacaoLit) {
        this.identificacaoLit = identificacaoLit;
    }
    
    /**
     *
     * @return
     */
    public String getSiglaLit() {
        return this.siglaLit;
    }

    /**
     *
     * @param siglaLit
     */
    public void setSiglaLit(String siglaLit) {
        this.siglaLit = siglaLit;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.LocalGrupoLgp getLocalGrupoLgp() {
        return this.localGrupoLgp;
    }

    /**
     *
     * @param localGrupoLgp
     */
    public void setLocalGrupoLgp(ecar.pojo.LocalGrupoLgp localGrupoLgp) {
        this.localGrupoLgp = localGrupoLgp;
    }

    /**
     *
     * @return
     */
    public Set getLocalItemHierarquiaLithsByCodLit() {
        return this.localItemHierarquiaLithsByCodLit;
    }

    /**
     *
     * @param localItemHierarquiaLithsByCodLit
     */
    public void setLocalItemHierarquiaLithsByCodLit(Set localItemHierarquiaLithsByCodLit) {
        this.localItemHierarquiaLithsByCodLit = localItemHierarquiaLithsByCodLit;
    }

    /**
     *
     * @return
     */
    public Set getLocalItemHierarquiaLithsByCodLitPai() {
        return this.localItemHierarquiaLithsByCodLitPai;
    }

    /**
     *
     * @param localItemHierarquiaLithsByCodLitPai
     */
    public void setLocalItemHierarquiaLithsByCodLitPai(Set localItemHierarquiaLithsByCodLitPai) {
        this.localItemHierarquiaLithsByCodLitPai = localItemHierarquiaLithsByCodLitPai;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutLocalIettls() {
        return this.itemEstrutLocalIettls;
    }

    /**
     *
     * @param itemEstrutLocalIettls
     */
    public void setItemEstrutLocalIettls(Set itemEstrutLocalIettls) {
        this.itemEstrutLocalIettls = itemEstrutLocalIettls;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codLit", getCodLit())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof LocalItemLit) ) return false;
        LocalItemLit castOther = (LocalItemLit) other;
        return new EqualsBuilder()
            .append(this.getCodLit(), castOther.getCodLit())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodLit())
            .toHashCode();
    }

    /**
     * @return Returns the codIbgeLit.
     */
    public String getCodIbgeLit() {
        return codIbgeLit;
    }
    /**
     * @param codIbgeLit The codIbgeLit to set.
     */
    public void setCodIbgeLit(String codIbgeLit) {
        this.codIbgeLit = codIbgeLit;
    }
    /**
     * @return Returns the codPlanejamentoLit.
     */
    public String getCodPlanejamentoLit() {
        return codPlanejamentoLit;
    }
    /**
     * @param codPlanejamentoLit The codPlanejamentoLit to set.
     */
    public void setCodPlanejamentoLit(String codPlanejamentoLit) {
        this.codPlanejamentoLit = codPlanejamentoLit;
    }
    
    /**
     *
     * @return
     */
    public Set getLocalDemandaLdems() {
        return this.localDemandaLdems;
    }

    /**
     *
     * @param localDemandaLdems
     */
    public void setLocalDemandaLdems(Set localDemandaLdems) {
        this.localDemandaLdems = localDemandaLdems;
    }

    /**
     *
     * @return
     */
    public Set getLocalEntidadeLents() {
        return this.localEntidadeLents;
    }

    /**
     *
     * @param localEntidadeLents
     */
    public void setLocalEntidadeLents(Set localEntidadeLents) {
        this.localEntidadeLents = localEntidadeLents;
    }

    /**
     *
     * @return
     */
    public Set getLocAtributoLocas() {
        return this.locAtributoLocas;
    }

    /**
     *
     * @param locAtributoLocas
     */
    public void setLocAtributoLocas(Set locAtributoLocas) {
        this.locAtributoLocas = locAtributoLocas;
    }

    /**
     *
     * @return
     */
    public Set getItemEstLocalRevIettlrs() {
		return itemEstLocalRevIettlrs;
	}

        /**
         *
         * @param itemEstLocalRevIettlrs
         */
        public void setItemEstLocalRevIettlrs(Set itemEstLocalRevIettlrs) {
		this.itemEstLocalRevIettlrs = itemEstLocalRevIettlrs;
	}
	
        /**
         *
         * @return
         */
        public Set getAcompRealFisicoLocalArfls() {
		return acompRealFisicoLocalArfls;
	}

        /**
         *
         * @param acompRealFisicoLocalArfls
         */
        public void setAcompRealFisicoLocalArfls(Set acompRealFisicoLocalArfls) {
		this.acompRealFisicoLocalArfls = acompRealFisicoLocalArfls;
	}

	/**
	 * @return the itemEstrtIndResulLocalIettirls
	 */
	public Set getItemEstrtIndResulLocalIettirls() {
		return itemEstrtIndResulLocalIettirls;
	}

	/**
	 * @param itemEstrtIndResulLocalIettirls the itemEstrtIndResulLocalIettirls to set
	 */
	public void setItemEstrtIndResulLocalIettirls(Set itemEstrtIndResulLocalIettirls) {
		this.itemEstrtIndResulLocalIettirls = itemEstrtIndResulLocalIettirls;
	}

	public int compareTo(LocalItemLit o) {

		if (getIdentificacaoLit().compareTo(o.getIdentificacaoLit()) < 0){
			return -1;
		} else if (getIdentificacaoLit().compareTo(o.getIdentificacaoLit()) > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	public void setCodLgp(Long codLgp) {
		this.codLgp = codLgp;
	}

	public Long getCodLgp() {
		return codLgp;
	}
}
