package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class RegDemandaRegd implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4001671150180361250L;

	/** identifier field */
    private Long codRegd;

    /** nullable persistent field */
    private Date dataLimiteRegd;

    /** nullable persistent field */
    private String descricaoRegd;

    /** nullable persistent field */
    private String observacaoRegd;

    /** nullable persistent field */
    private Integer numeroDocOrigemRegd;

    /** nullable persistent field */
    private Date dataSolicitacaoRegd;

    /** nullable persistent field */
    private Date dataInclusaoRegd;
    
    /** nullable persistent field */
    private Date dataAlteracaoRegd;

    /** nullable persistent field */
    private String indAtivoRegd;

    /** nullable persistent field */
    private String nomeSolicitanteRegd;
    
    private Date dataSituacaoRegd;

    /** persistent field */
    private ecar.pojo.PrioridadePrior prioridadePrior;

    /** persistent field */
    private ecar.pojo.SitDemandaSitd sitDemandaSitd;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsuByCodUsuInclusaoRegd;
    
    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsuByCodUsuAlteracaoRegd;

    /** persistent field */
    private Set entidadeOrgaoDemandaEntorgds;

    /** persistent field */
    private Set localDemandaLdems;

    /** persistent field */
    private Set regApontamentoRegdas;

    /** persistent field */
    private Set itemRegdemandaIregds;

    /** persistent field */
    private Set demAtributoDemas;
    
    // con
    private Set regDemandaRegds;
    
    // demanda relacionada
    private RegDemandaRegd regDemandaRegd;
    
    //
    private Set entidadeDemandaEntds;
     
    //anexos de demanda
    private Set anexos;
    
    
    /** full constructor
     * @param dataSituacaoRegd
     * @param dataLimiteRegd
     * @param regDemandaRegds
     * @param descricaoRegd
     * @param regDemandaRegd
     * @param dataSolicitacaoRegd
     * @param numeroDocOrigemRegd
     * @param observacaoRegd
     * @param dataInclusaoRegd
     * @param usuarioUsuByCodUsuInclusaoRegd
     * @param indAtivoRegd
     * @param prioridadePrior
     * @param demAtributoDemas
     * @param sitDemandaSitd
     * @param nomeSolicitanteRegd
     * @param localDemandaLdems
     * @param regApontamentoRegdas
     * @param entidadeOrgaoDemandaEntorgds
     * @param entidadeDemandaEntds
     * @param itemRegdemandaIregds
     */
    public RegDemandaRegd(Date dataSituacaoRegd, Date dataLimiteRegd, String descricaoRegd, String observacaoRegd, Integer numeroDocOrigemRegd, Date dataSolicitacaoRegd, Date dataInclusaoRegd, String indAtivoRegd, ecar.pojo.PrioridadePrior prioridadePrior, ecar.pojo.SitDemandaSitd sitDemandaSitd, ecar.pojo.UsuarioUsu usuarioUsuByCodUsuInclusaoRegd, String nomeSolicitanteRegd, Set entidadeOrgaoDemandaEntorgds, Set localDemandaLdems, Set regApontamentoRegdas, Set itemRegdemandaIregds, Set demAtributoDemas, Set regDemandaRegds, RegDemandaRegd regDemandaRegd, Set entidadeDemandaEntds) {
        this.dataLimiteRegd = dataLimiteRegd;
        this.descricaoRegd = descricaoRegd;
        this.observacaoRegd = observacaoRegd;
        this.numeroDocOrigemRegd = numeroDocOrigemRegd;
        this.dataSolicitacaoRegd = dataSolicitacaoRegd;
        this.dataInclusaoRegd = dataInclusaoRegd;
        this.indAtivoRegd = indAtivoRegd;
        this.prioridadePrior = prioridadePrior;
        this.sitDemandaSitd = sitDemandaSitd;
        this.usuarioUsuByCodUsuInclusaoRegd = usuarioUsuByCodUsuInclusaoRegd;
        this.nomeSolicitanteRegd = nomeSolicitanteRegd;
        this.entidadeOrgaoDemandaEntorgds = entidadeOrgaoDemandaEntorgds;
        this.dataSituacaoRegd = dataSituacaoRegd;
        this.localDemandaLdems = localDemandaLdems;
        this.regApontamentoRegdas = regApontamentoRegdas;
        this.itemRegdemandaIregds = itemRegdemandaIregds;
        this.demAtributoDemas = demAtributoDemas;
        this.regDemandaRegd = regDemandaRegd;
        this.regDemandaRegds = regDemandaRegds;
        this.entidadeDemandaEntds = entidadeDemandaEntds;
    }

    /** default constructor */
    public RegDemandaRegd() {
    }

    /** minimal constructor
     * @param prioridadePrior
     * @param regApontamentoRegdas
     * @param usuarioUsuByCodUsuInclusaoRegd
     * @param nomeSolicitanteRegd
     * @param sitDemandaSitd
     * @param entidadeDemandaEntds
     * @param entidadeOrgaoDemandaEntorgds
     * @param localDemandaLdems
     * @param demAtributoDemas
     * @param itemRegdemandaIregds
     * @param regDemandaRegd
     * @param regDemandaRegds
     */
    public RegDemandaRegd(ecar.pojo.PrioridadePrior prioridadePrior, ecar.pojo.SitDemandaSitd sitDemandaSitd, ecar.pojo.UsuarioUsu usuarioUsuByCodUsuInclusaoRegd, String nomeSolicitanteRegd, Set entidadeOrgaoDemandaEntorgds, Set localDemandaLdems, Set regApontamentoRegdas, Set itemRegdemandaIregds, Set demAtributoDemas, Set regDemandaRegds, RegDemandaRegd regDemandaRegd, Set entidadeDemandaEntds) {
        this.prioridadePrior = prioridadePrior;
        this.sitDemandaSitd = sitDemandaSitd;
        this.usuarioUsuByCodUsuInclusaoRegd = usuarioUsuByCodUsuInclusaoRegd;
        this.nomeSolicitanteRegd = nomeSolicitanteRegd;
        this.entidadeOrgaoDemandaEntorgds = entidadeOrgaoDemandaEntorgds;
        this.localDemandaLdems = localDemandaLdems;
        this.regApontamentoRegdas = regApontamentoRegdas;
        this.itemRegdemandaIregds = itemRegdemandaIregds;
        this.demAtributoDemas = demAtributoDemas;
        this.regDemandaRegd = regDemandaRegd;
        this.regDemandaRegds = regDemandaRegds;
        this.entidadeDemandaEntds = entidadeDemandaEntds;
    }

    /**
     *
     * @return
     */
    public Long getCodRegd() {
        return this.codRegd;
    }

    /**
     *
     * @param codRegd
     */
    public void setCodRegd(Long codRegd) {
        this.codRegd = codRegd;
    }

    /**
     *
     * @return
     */
    public Date getDataLimiteRegd() {
        return this.dataLimiteRegd;
    }

    /**
     *
     * @param dataLimiteRegd
     */
    public void setDataLimiteRegd(Date dataLimiteRegd) {
        this.dataLimiteRegd = dataLimiteRegd;
    }

    /**
     *
     * @return
     */
    public String getDescricaoRegd() {
        return this.descricaoRegd;
    }

    /**
     *
     * @param descricaoRegd
     */
    public void setDescricaoRegd(String descricaoRegd) {
        this.descricaoRegd = descricaoRegd;
    }

    /**
     *
     * @return
     */
    public String getObservacaoRegd() {
        return this.observacaoRegd;
    }

    /**
     *
     * @param observacaoRegd
     */
    public void setObservacaoRegd(String observacaoRegd) {
        this.observacaoRegd = observacaoRegd;
    }

    /**
     *
     * @return
     */
    public Integer getNumeroDocOrigemRegd() {
        return this.numeroDocOrigemRegd;
    }

    /**
     *
     * @param numeroDocOrigemRegd
     */
    public void setNumeroDocOrigemRegd(Integer numeroDocOrigemRegd) {
        this.numeroDocOrigemRegd = numeroDocOrigemRegd;
    }

    /**
     *
     * @return
     */
    public Date getDataSolicitacaoRegd() {
        return this.dataSolicitacaoRegd;
    }

    /**
     *
     * @param dataSolicitacaoRegd
     */
    public void setDataSolicitacaoRegd(Date dataSolicitacaoRegd) {
        this.dataSolicitacaoRegd = dataSolicitacaoRegd;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoRegd() {
        return this.dataInclusaoRegd;
    }

    /**
     *
     * @param dataInclusaoRegd
     */
    public void setDataInclusaoRegd(Date dataInclusaoRegd) {
        this.dataInclusaoRegd = dataInclusaoRegd;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoRegd() {
        return this.indAtivoRegd;
    }

    /**
     *
     * @param indAtivoRegd
     */
    public void setIndAtivoRegd(String indAtivoRegd) {
        this.indAtivoRegd = indAtivoRegd;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.PrioridadePrior getPrioridadePrior() {
        return this.prioridadePrior;
    }

    /**
     *
     * @param prioridadePrior
     */
    public void setPrioridadePrior(ecar.pojo.PrioridadePrior prioridadePrior) {
        this.prioridadePrior = prioridadePrior;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SitDemandaSitd getSitDemandaSitd() {
        return this.sitDemandaSitd;
    }

    /**
     *
     * @param sitDemandaSitd
     */
    public void setSitDemandaSitd(ecar.pojo.SitDemandaSitd sitDemandaSitd) {
        this.sitDemandaSitd = sitDemandaSitd;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsuByCodUsuInclusaoRegd() {
        return this.usuarioUsuByCodUsuInclusaoRegd;
    }

    /**
     *
     * @param usuarioUsuByCodUsuInclusaoRegd
     */
    public void setUsuarioUsuByCodUsuInclusaoRegd(ecar.pojo.UsuarioUsu usuarioUsuByCodUsuInclusaoRegd) {
        this.usuarioUsuByCodUsuInclusaoRegd = usuarioUsuByCodUsuInclusaoRegd;
    }
    
    /**
     *
     * @param usuarioUsuByCodUsuAlteracaoRegd
     */
    public void setUsuarioUsuByCodUsuAlteracaoRegd(ecar.pojo.UsuarioUsu usuarioUsuByCodUsuAlteracaoRegd) {
		this.usuarioUsuByCodUsuAlteracaoRegd = usuarioUsuByCodUsuAlteracaoRegd;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.UsuarioUsu getUsuarioUsuByCodUsuAlteracaoRegd() {
		return usuarioUsuByCodUsuAlteracaoRegd;
	}

        /**
         *
         * @return
         */
        public String getNomeSolicitanteRegd() {
        return this.nomeSolicitanteRegd;
    }

    /**
     *
     * @param nomeSolicitanteRegd
     */
    public void setNomeSolicitanteRegd(String nomeSolicitanteRegd) {
        this.nomeSolicitanteRegd = nomeSolicitanteRegd;
    }

    /**
     *
     * @return
     */
    public Set getEntidadeOrgaoDemandaEntorgds() {
        return this.entidadeOrgaoDemandaEntorgds;
    }

    /**
     *
     * @param entidadeOrgaoDemandaEntorgds
     */
    public void setEntidadeOrgaoDemandaEntorgds(Set entidadeOrgaoDemandaEntorgds) {
        this.entidadeOrgaoDemandaEntorgds = entidadeOrgaoDemandaEntorgds;
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
    public Set getRegApontamentoRegdas() {
        return this.regApontamentoRegdas;
    }

    /**
     *
     * @param regApontamentoRegdas
     */
    public void setRegApontamentoRegdas(Set regApontamentoRegdas) {
        this.regApontamentoRegdas = regApontamentoRegdas;
    }

    /**
     *
     * @return
     */
    public Set getItemRegdemandaIregds() {
        return this.itemRegdemandaIregds;
    }

    /**
     *
     * @param itemRegdemandaIregds
     */
    public void setItemRegdemandaIregds(Set itemRegdemandaIregds) {
        this.itemRegdemandaIregds = itemRegdemandaIregds;
    }

    /**
     *
     * @return
     */
    public Set getDemAtributoDemas() {
        return this.demAtributoDemas;
    }

    /**
     *
     * @param demAtributoDemas
     */
    public void setDemAtributoDemas(Set demAtributoDemas) {
        this.demAtributoDemas = demAtributoDemas;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codRegd", getCodRegd())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof RegDemandaRegd) ) return false;
        RegDemandaRegd castOther = (RegDemandaRegd) other;
        return new EqualsBuilder()
            .append(this.getCodRegd(), castOther.getCodRegd())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodRegd())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public RegDemandaRegd getRegDemandaRegd() {
		return regDemandaRegd;
	}

        /**
         *
         * @param regDemandaRegd
         */
        public void setRegDemandaRegd(RegDemandaRegd regDemandaRegd) {
		this.regDemandaRegd = regDemandaRegd;
	}

        /**
         *
         * @return
         */
        public Set getRegDemandaRegds() {
		return regDemandaRegds;
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
        public Set getEntidadeDemandaEntds() {
		return entidadeDemandaEntds;
	}

        /**
         *
         * @param entidadeDemandaEntds
         */
        public void setEntidadeDemandaEntds(Set entidadeDemandaEntds) {
		this.entidadeDemandaEntds = entidadeDemandaEntds;
	}

        /**
         *
         * @return
         */
        public Date getDataSituacaoRegd() {
		return dataSituacaoRegd;
	}

        /**
         *
         * @param dataSituacaoRegd
         */
        public void setDataSituacaoRegd(Date dataSituacaoRegd) {
		this.dataSituacaoRegd = dataSituacaoRegd;
	}

        /**
         *
         * @param dataAlteracaoRegd
         */
        public void setDataAlteracaoRegd(Date dataAlteracaoRegd) {
		this.dataAlteracaoRegd = dataAlteracaoRegd;
	}

        /**
         *
         * @return
         */
        public Date getDataAlteracaoRegd() {
		return dataAlteracaoRegd;
	}

        /**
         *
         * @return
         */
        public Set getAnexos() {
		return anexos;
	}

        /**
         *
         * @param anexos
         */
        public void setAnexos(Set anexos) {
		this.anexos = anexos;
	}



}
