package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 02/06/2007
 */
public class UnidadeOrcamentariaUO implements Serializable {


	private static final long serialVersionUID = 1445262261304390515L;

	/** identifier field */
    private Long codUo;

    /** nullable persistent field */
    private String descricaoUo;

    /** nullable persistent field */
    private String siglaUo;

    /** nullable persistent field */
    private String indAtivoUo;

    /** nullable persistent field */
    private Long codigoIdentUo;

    /** persistent field */
    private OrgaoOrg orgaoOrg;

    /** persistent field */
    private UsuarioUsu usuarioUsu;

    /** persistent field */
    private Set itemEstruturaIettsByCodUo;
    
    private Set itemEstruturarevisaoIettrevs;
    
    /** nullable persistent field */
    private String indTipoAdministracaoUo;

    /** nullable persistent field */
    private String indTipoOrcamentoUo;

    /* Mantis #2156 */
    private Set historicoIettHs;
    
    private Set unidadeOrcamentariaPeriodoExercicioUoPerexes;
    
    /**
     *
     * @return
     */
    public Set getItemEstruturarevisaoIettrevs() {
		return itemEstruturarevisaoIettrevs;
	}

        /**
         *
         * @param itemEstruturarevisaoIettrevs
         */
        public void setItemEstruturarevisaoIettrevs(Set itemEstruturarevisaoIettrevs) {
		this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
	}

        /** full constructor
         * @param descricaoUo
         * @param siglaUo
         * @param indAtivoUo
         * @param codigoIdentUo
         * @param orgaoOrg
         * @param indTipoOrcamentoUo
         * @param indTipoAdministracaoUo
         * @param itemEstruturaIettsByCodUo
         * @param usuarioUsu
         * @param itemEstruturarevisaoIettrevs
         */
    public UnidadeOrcamentariaUO(String descricaoUo, String siglaUo, String indAtivoUo, Long codigoIdentUo, OrgaoOrg orgaoOrg, UsuarioUsu usuarioUsu, Set itemEstruturaIettsByCodUo, Set itemEstruturarevisaoIettrevs, String indTipoAdministracaoUo, String indTipoOrcamentoUo) {
        this.descricaoUo = descricaoUo;
        this.siglaUo = siglaUo;
        this.indAtivoUo = indAtivoUo;
        this.codigoIdentUo = codigoIdentUo;
        this.orgaoOrg = orgaoOrg;
        this.usuarioUsu = usuarioUsu;
        this.itemEstruturaIettsByCodUo = itemEstruturaIettsByCodUo;
        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
        this.indTipoAdministracaoUo = indTipoAdministracaoUo;
        this.indTipoOrcamentoUo = indTipoOrcamentoUo;
    }

    /** default constructor */
    public UnidadeOrcamentariaUO() {
    }

    /** minimal constructor
     * @param itemEstruturaIettsByCodUo
     * @param itemEstruturarevisaoIettrevs
     */
    public UnidadeOrcamentariaUO(Set itemEstruturaIettsByCodUo, Set itemEstruturarevisaoIettrevs) {
        this.itemEstruturaIettsByCodUo = itemEstruturaIettsByCodUo;
        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codUo", getCodUo())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof UnidadeOrcamentariaUO) ) return false;
        UnidadeOrcamentariaUO castOther = (UnidadeOrcamentariaUO) other;
        return new EqualsBuilder()
            .append(this.getCodUo(), castOther.getCodUo())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodUo())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Long getCodigoIdentUo() {
		return codigoIdentUo;
	}

        /**
         *
         * @param codigoIdentUo
         */
        public void setCodigoIdentUo(Long codigoIdentUo) {
		this.codigoIdentUo = codigoIdentUo;
	}

        /**
         *
         * @return
         */
        public Long getCodUo() {
		return codUo;
	}

        /**
         *
         * @param codUo
         */
        public void setCodUo(Long codUo) {
		this.codUo = codUo;
	}

        /**
         *
         * @return
         */
        public String getDescricaoUo() {
		return descricaoUo;
	}

        /**
         *
         * @param descricaoUo
         */
        public void setDescricaoUo(String descricaoUo) {
		this.descricaoUo = descricaoUo;
	}

        /**
         *
         * @return
         */
        public String getIndAtivoUo() {
		return indAtivoUo;
	}

        /**
         *
         * @param indAtivoUo
         */
        public void setIndAtivoUo(String indAtivoUo) {
		this.indAtivoUo = indAtivoUo;
	}

        /**
         *
         * @return
         */
        public Set getItemEstruturaIettsByCodUo() {
		return itemEstruturaIettsByCodUo;
	}

        /**
         *
         * @param itemEstruturaIettsByCodUo
         */
        public void setItemEstruturaIettsByCodUo(Set itemEstruturaIettsByCodUo) {
		this.itemEstruturaIettsByCodUo = itemEstruturaIettsByCodUo;
	}

        /**
         *
         * @return
         */
        public OrgaoOrg getOrgaoOrg() {
		return orgaoOrg;
	}

        /**
         *
         * @param orgaoOrg
         */
        public void setOrgaoOrg(OrgaoOrg orgaoOrg) {
		this.orgaoOrg = orgaoOrg;
	}

        /**
         *
         * @return
         */
        public String getSiglaUo() {
		return siglaUo;
	}

        /**
         *
         * @param siglaUo
         */
        public void setSiglaUo(String siglaUo) {
		this.siglaUo = siglaUo;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsu() {
		return usuarioUsu;
	}

        /**
         *
         * @param usuarioUsu
         */
        public void setUsuarioUsu(UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
	}

	/**
	 * @return the indTipoAdministracaoUo
	 */
	public String getIndTipoAdministracaoUo() {
		return indTipoAdministracaoUo;
	}

	/**
	 * @param indTipoAdministracaoUo the indTipoAdministracaoUo to set
	 */
	public void setIndTipoAdministracaoUo(String indTipoAdministracaoUo) {
		this.indTipoAdministracaoUo = indTipoAdministracaoUo;
	}

	/**
	 * @return the indTipoOrcamentoUo
	 */
	public String getIndTipoOrcamentoUo() {
		return indTipoOrcamentoUo;
	}

	/**
	 * @param indTipoOrcamentoUo the indTipoOrcamentoUo to set
	 */
	public void setIndTipoOrcamentoUo(String indTipoOrcamentoUo) {
		this.indTipoOrcamentoUo = indTipoOrcamentoUo;
	}

        /**
         *
         * @return
         */
        public Set getHistoricoIettHs() {
		return historicoIettHs;
	}

        /**
         *
         * @param historicoIettHs
         */
        public void setHistoricoIettHs(Set historicoIettHs) {
		this.historicoIettHs = historicoIettHs;
	}

	/**
	 * @return the unidadeOrcamentariaPeriodoExercicioUoPerexes
	 */
	public Set getUnidadeOrcamentariaPeriodoExercicioUoPerexes() {
		return unidadeOrcamentariaPeriodoExercicioUoPerexes;
	}

	/**
	 * @param unidadeOrcamentariaPeriodoExercicioUoPerexes the unidadeOrcamentariaPeriodoExercicioUoPerexes to set
	 */
	public void setUnidadeOrcamentariaPeriodoExercicioUoPerexes(
			Set unidadeOrcamentariaPeriodoExercicioUoPerexes) {
		this.unidadeOrcamentariaPeriodoExercicioUoPerexes = unidadeOrcamentariaPeriodoExercicioUoPerexes;
	}

}