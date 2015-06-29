package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class TipoParticipacaoTpp implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 817316799617562636L;

	/** identifier field */
    private Long codTpp;

    /** nullable persistent field */
    private String indAtivoTpp;

    /** nullable persistent field */
    private Date dataInclusaoTpp;

    /** nullable persistent field */
    private String descricaoTpp;

    /** persistent field */
    private Set itemEstrutEntidadeIettes;
    
    /* Mantis #2156 */
    private Set historicoIetteHs;

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
         * @param indAtivoTpp
         * @param dataInclusaoTpp
         * @param descricaoTpp
         * @param itemEstrutEntidadeIettes
         */
    public TipoParticipacaoTpp(String indAtivoTpp, Date dataInclusaoTpp, String descricaoTpp, Set itemEstrutEntidadeIettes) {
        this.indAtivoTpp = indAtivoTpp;
        this.dataInclusaoTpp = dataInclusaoTpp;
        this.descricaoTpp = descricaoTpp;
        this.itemEstrutEntidadeIettes = itemEstrutEntidadeIettes;
    }

    /** default constructor */
    public TipoParticipacaoTpp() {
    }

    /** minimal constructor
     * @param itemEstrutEntidadeIettes
     */
    public TipoParticipacaoTpp(Set itemEstrutEntidadeIettes) {
        this.itemEstrutEntidadeIettes = itemEstrutEntidadeIettes;
    }

    /**
     *
     * @return
     */
    public Long getCodTpp() {
        return this.codTpp;
    }

    /**
     *
     * @param codTpp
     */
    public void setCodTpp(Long codTpp) {
        this.codTpp = codTpp;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoTpp() {
        return this.indAtivoTpp;
    }

    /**
     *
     * @param indAtivoTpp
     */
    public void setIndAtivoTpp(String indAtivoTpp) {
        this.indAtivoTpp = indAtivoTpp;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoTpp() {
        return this.dataInclusaoTpp;
    }

    /**
     *
     * @param dataInclusaoTpp
     */
    public void setDataInclusaoTpp(Date dataInclusaoTpp) {
        this.dataInclusaoTpp = dataInclusaoTpp;
    }

    /**
     *
     * @return
     */
    public String getDescricaoTpp() {
        return this.descricaoTpp;
    }

    /**
     *
     * @param descricaoTpp
     */
    public void setDescricaoTpp(String descricaoTpp) {
        this.descricaoTpp = descricaoTpp;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codTpp", getCodTpp())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof TipoParticipacaoTpp) ) return false;
        TipoParticipacaoTpp castOther = (TipoParticipacaoTpp) other;
        return new EqualsBuilder()
            .append(this.getCodTpp(), castOther.getCodTpp())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodTpp())
            .toHashCode();
    }

}
