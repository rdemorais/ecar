package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class EntidadeEnt implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2402992138928889968L;

	/** identifier field */
    private Long codEnt;

    /** nullable persistent field */
    private String indAtivoEnt;

    /** nullable persistent field */
    private Date dataInclusaoEnt;

    /** nullable persistent field */
    private String siglaEnt;

    /** nullable persistent field */
    private String nomeEnt;

    /** nullable persistent field */
    private String cpfCnpjEnt;

    /** nullable persistent field */
    private String emailEnt;

    /** persistent field */
    private Set enderecoEnds;

    /** persistent field */
    private Set entidadeAtributoEntas;

    /** persistent field */
    private Set itemEstrutEntidadeIettes;

    /** persistent field */
    private Set localEntidadeLents;

    /** persistent field */
    private Set hierarquiaEntidadeHentsByCodEntPai;

    /** persistent field */
    private Set hierarquiaEntidadeHentsByCodEnt;

    /** persistent field */
    private Set regDemandaRegds;

    /** persistent field */
    private Set telefoneTels;

    private Set entidadeDemandaEntds;    
    
    private Set usuarioUsus;
    
    /* Mantis #2156 */
    private Set historicoIetteHs;

    private Set agendaEntidadesAgeent;
    
    private String indOrgaoEnt;    

    /**
     *
     * @return
     */
    public Set getHistoricoIetteHs() {
		return historicoIetteHs;
	}

        /**
         *
         * @param historicoIetteHs
         */
        public void setHistoricoIetteHs(Set historicoIetteHs) {
		this.historicoIetteHs = historicoIetteHs;
	}

        /** full constructor
         * @param indAtivoEnt
         * @param itemEstrutEntidadeIettes
         * @param dataInclusaoEnt
         * @param nomeEnt
         * @param siglaEnt
         * @param emailEnt
         * @param cpfCnpjEnt
         * @param indOrgaoEnt
         * @param telefoneTels
         * @param enderecoEnds
         * @param hierarquiaEntidadeHentsByCodEnt
         * @param entidadeAtributoEntas
         * @param hierarquiaEntidadeHentsByCodEntPai
         * @param localEntidadeLents
         * @param regDemandaRegds
         * @param entidadeDemandaEntds
         */
    public EntidadeEnt(String indAtivoEnt, Date dataInclusaoEnt, String siglaEnt, String nomeEnt, String cpfCnpjEnt, String emailEnt, Set enderecoEnds, Set entidadeAtributoEntas, Set itemEstrutEntidadeIettes, Set localEntidadeLents, Set hierarquiaEntidadeHentsByCodEntPai, Set hierarquiaEntidadeHentsByCodEnt, Set regDemandaRegds, Set telefoneTels, Set entidadeDemandaEntds, String indOrgaoEnt) {
        this.indAtivoEnt = indAtivoEnt;
        this.dataInclusaoEnt = dataInclusaoEnt;
        this.siglaEnt = siglaEnt;
        this.nomeEnt = nomeEnt;
        this.cpfCnpjEnt = cpfCnpjEnt;
        this.emailEnt = emailEnt;
        this.enderecoEnds = enderecoEnds;
        this.entidadeAtributoEntas = entidadeAtributoEntas;
        this.itemEstrutEntidadeIettes = itemEstrutEntidadeIettes;
        this.localEntidadeLents = localEntidadeLents;
        this.hierarquiaEntidadeHentsByCodEntPai = hierarquiaEntidadeHentsByCodEntPai;
        this.hierarquiaEntidadeHentsByCodEnt = hierarquiaEntidadeHentsByCodEnt;
        this.regDemandaRegds = regDemandaRegds;
        this.telefoneTels = telefoneTels;
        this.entidadeDemandaEntds = entidadeDemandaEntds;
        this.indOrgaoEnt = indOrgaoEnt;
    }

    /** default constructor */
    public EntidadeEnt() {
    }

    /** minimal constructor
     * @param enderecoEnds
     * @param hierarquiaEntidadeHentsByCodEntPai
     * @param entidadeAtributoEntas
     * @param itemEstrutEntidadeIettes
     * @param localEntidadeLents
     * @param telefoneTels
     * @param hierarquiaEntidadeHentsByCodEnt
     * @param regDemandaRegds
     * @param entidadeDemandaEntds
     */
    public EntidadeEnt(Set enderecoEnds, Set entidadeAtributoEntas, Set itemEstrutEntidadeIettes, Set localEntidadeLents, Set hierarquiaEntidadeHentsByCodEntPai, Set hierarquiaEntidadeHentsByCodEnt, Set regDemandaRegds, Set telefoneTels, Set entidadeDemandaEntds) {
        this.enderecoEnds = enderecoEnds;
        this.entidadeAtributoEntas = entidadeAtributoEntas;
        this.itemEstrutEntidadeIettes = itemEstrutEntidadeIettes;
        this.localEntidadeLents = localEntidadeLents;
        this.hierarquiaEntidadeHentsByCodEntPai = hierarquiaEntidadeHentsByCodEntPai;
        this.hierarquiaEntidadeHentsByCodEnt = hierarquiaEntidadeHentsByCodEnt;
        this.regDemandaRegds = regDemandaRegds;
        this.telefoneTels = telefoneTels;
        this.entidadeDemandaEntds = entidadeDemandaEntds;
    }

    /**
     *
     * @return
     */
    public Long getCodEnt() {
        return this.codEnt;
    }

    /**
     *
     * @param codEnt
     */
    public void setCodEnt(Long codEnt) {
        this.codEnt = codEnt;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoEnt() {
        return this.indAtivoEnt;
    }

    /**
     *
     * @param indAtivoEnt
     */
    public void setIndAtivoEnt(String indAtivoEnt) {
        this.indAtivoEnt = indAtivoEnt;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoEnt() {
        return this.dataInclusaoEnt;
    }

    /**
     *
     * @param dataInclusaoEnt
     */
    public void setDataInclusaoEnt(Date dataInclusaoEnt) {
        this.dataInclusaoEnt = dataInclusaoEnt;
    }

    /**
     *
     * @return
     */
    public String getSiglaEnt() {
        return this.siglaEnt;
    }

    /**
     *
     * @param siglaEnt
     */
    public void setSiglaEnt(String siglaEnt) {
        this.siglaEnt = siglaEnt;
    }

    /**
     *
     * @return
     */
    public String getNomeEnt() {
        return this.nomeEnt;
    }

    /**
     *
     * @param nomeEnt
     */
    public void setNomeEnt(String nomeEnt) {
        this.nomeEnt = nomeEnt;
    }

    /**
     *
     * @return
     */
    public String getCpfCnpjEnt() {
        return this.cpfCnpjEnt;
    }

    /**
     *
     * @param cpfCnpjEnt
     */
    public void setCpfCnpjEnt(String cpfCnpjEnt) {
        this.cpfCnpjEnt = cpfCnpjEnt;
    }

    /**
     *
     * @return
     */
    public String getEmailEnt() {
        return this.emailEnt;
    }

    /**
     *
     * @param emailEnt
     */
    public void setEmailEnt(String emailEnt) {
        this.emailEnt = emailEnt;
    }

    /**
     *
     * @return
     */
    public Set getEnderecoEnds() {
        return this.enderecoEnds;
    }

    /**
     *
     * @param enderecoEnds
     */
    public void setEnderecoEnds(Set enderecoEnds) {
        this.enderecoEnds = enderecoEnds;
    }

    /**
     *
     * @return
     */
    public Set getEntidadeAtributoEntas() {
        return this.entidadeAtributoEntas;
    }

    /**
     *
     * @param entidadeAtributoEntas
     */
    public void setEntidadeAtributoEntas(Set entidadeAtributoEntas) {
        this.entidadeAtributoEntas = entidadeAtributoEntas;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrutEntidadeIettes() {
        return this.itemEstrutEntidadeIettes;
    }

    /**
     *
     * @param itemEstrutEntidadeIettes
     */
    public void setItemEstrutEntidadeIettes(Set itemEstrutEntidadeIettes) {
        this.itemEstrutEntidadeIettes = itemEstrutEntidadeIettes;
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
    public Set getHierarquiaEntidadeHentsByCodEntPai() {
        return this.hierarquiaEntidadeHentsByCodEntPai;
    }

    /**
     *
     * @param hierarquiaEntidadeHentsByCodEntPai
     */
    public void setHierarquiaEntidadeHentsByCodEntPai(Set hierarquiaEntidadeHentsByCodEntPai) {
        this.hierarquiaEntidadeHentsByCodEntPai = hierarquiaEntidadeHentsByCodEntPai;
    }

    /**
     *
     * @return
     */
    public Set getHierarquiaEntidadeHentsByCodEnt() {
        return this.hierarquiaEntidadeHentsByCodEnt;
    }

    /**
     *
     * @param hierarquiaEntidadeHentsByCodEnt
     */
    public void setHierarquiaEntidadeHentsByCodEnt(Set hierarquiaEntidadeHentsByCodEnt) {
        this.hierarquiaEntidadeHentsByCodEnt = hierarquiaEntidadeHentsByCodEnt;
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
    public Set getTelefoneTels() {
        return this.telefoneTels;
    }

    /**
     *
     * @param telefoneTels
     */
    public void setTelefoneTels(Set telefoneTels) {
        this.telefoneTels = telefoneTels;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codEnt", getCodEnt())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EntidadeEnt) ) return false;
        EntidadeEnt castOther = (EntidadeEnt) other;
        return new EqualsBuilder()
            .append(this.getCodEnt(), castOther.getCodEnt())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodEnt())
            .toHashCode();
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
        public Set getAgendaEntidadesAgeent() {
		return agendaEntidadesAgeent;
	}

        /**
         *
         * @param agendaEntidadesAgeent
         */
        public void setAgendaEntidadesAgeent(Set agendaEntidadesAgeent) {
		this.agendaEntidadesAgeent = agendaEntidadesAgeent;
	}

        /**
         *
         * @return
         */
        public Set getUsuarioUsus() {
		return usuarioUsus;
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
        public String getIndOrgaoEnt() {
		return indOrgaoEnt;
	}

        /**
         *
         * @param indOrgaoEnt
         */
        public void setIndOrgaoEnt(String indOrgaoEnt) {
		this.indOrgaoEnt = indOrgaoEnt;
	}

}
