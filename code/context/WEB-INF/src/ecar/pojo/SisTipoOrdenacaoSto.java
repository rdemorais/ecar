package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SisTipoOrdenacaoSto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2620087079927318613L;

	/** identifier field */
    private Long codSto;

    /** nullable persistent field */
    private String stringSqlSto;

    /** nullable persistent field */
    private String descricaoSto;

    /** nullable persistent field */
    private String indAtivoSto;

    /** nullable persistent field */
    private Date dataInclusaoSto;

    /** persistent field */
    private Set sisGrupoAtributoSgas;


    /** full constructor
     * @param stringSqlSto
     * @param descricaoSto
     * @param dataInclusaoSto
     * @param indAtivoSto
     * @param sisGrupoAtributoSgas
     */
    public SisTipoOrdenacaoSto(String stringSqlSto, String descricaoSto, String indAtivoSto, Date dataInclusaoSto, Set sisGrupoAtributoSgas) {
        this.stringSqlSto = stringSqlSto;
        this.descricaoSto = descricaoSto;
        this.indAtivoSto = indAtivoSto;
        this.dataInclusaoSto = dataInclusaoSto;
        this.sisGrupoAtributoSgas = sisGrupoAtributoSgas;
    }

    /** default constructor */
    public SisTipoOrdenacaoSto() {
    }

    /** minimal constructor
     * @param sisGrupoAtributoSgas
     */
    public SisTipoOrdenacaoSto(Set sisGrupoAtributoSgas) {
        this.sisGrupoAtributoSgas = sisGrupoAtributoSgas;
    }

    /**
     *
     * @return
     */
    public Long getCodSto() {
        return this.codSto;
    }

    /**
     *
     * @param codSto
     */
    public void setCodSto(Long codSto) {
        this.codSto = codSto;
    }

    /**
     *
     * @return
     */
    public String getStringSqlSto() {
        return this.stringSqlSto;
    }

    /**
     *
     * @param stringSqlSto
     */
    public void setStringSqlSto(String stringSqlSto) {
        this.stringSqlSto = stringSqlSto;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSto() {
        return this.descricaoSto;
    }

    /**
     *
     * @param descricaoSto
     */
    public void setDescricaoSto(String descricaoSto) {
        this.descricaoSto = descricaoSto;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoSto() {
        return this.indAtivoSto;
    }

    /**
     *
     * @param indAtivoSto
     */
    public void setIndAtivoSto(String indAtivoSto) {
        this.indAtivoSto = indAtivoSto;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoSto() {
        return this.dataInclusaoSto;
    }

    /**
     *
     * @param dataInclusaoSto
     */
    public void setDataInclusaoSto(Date dataInclusaoSto) {
        this.dataInclusaoSto = dataInclusaoSto;
    }

    /**
     *
     * @return
     */
    public Set getSisGrupoAtributoSgas() {
        return this.sisGrupoAtributoSgas;
    }

    /**
     *
     * @param sisGrupoAtributoSgas
     */
    public void setSisGrupoAtributoSgas(Set sisGrupoAtributoSgas) {
        this.sisGrupoAtributoSgas = sisGrupoAtributoSgas;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSto", getCodSto())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SisTipoOrdenacaoSto) ) return false;
        SisTipoOrdenacaoSto castOther = (SisTipoOrdenacaoSto) other;
        return new EqualsBuilder()
            .append(this.getCodSto(), castOther.getCodSto())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSto())
            .toHashCode();
    }

}
