package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SisTipoExibicGrupoSteg implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -274883075116937554L;

	/** identifier field */
    private Long codSteg;

    /** nullable persistent field */
    private String descricaoSteg;

    /** nullable persistent field */
    private Date dataInclusaoSteg;

    /** nullable persistent field */
    private String indAtivoSteg;

    /** persistent field */
    private Set sisGrupoAtributoSgas;


    /** full constructor
     * @param descricaoSteg
     * @param dataInclusaoSteg
     * @param indAtivoSteg
     * @param sisGrupoAtributoSgas
     */
    public SisTipoExibicGrupoSteg(String descricaoSteg, Date dataInclusaoSteg, String indAtivoSteg, Set sisGrupoAtributoSgas) {
        this.descricaoSteg = descricaoSteg;
        this.dataInclusaoSteg = dataInclusaoSteg;
        this.indAtivoSteg = indAtivoSteg;
        this.sisGrupoAtributoSgas = sisGrupoAtributoSgas;
    }

    /** default constructor */
    public SisTipoExibicGrupoSteg() {
    }

    /** minimal constructor
     * @param sisGrupoAtributoSgas
     */
    public SisTipoExibicGrupoSteg(Set sisGrupoAtributoSgas) {
        this.sisGrupoAtributoSgas = sisGrupoAtributoSgas;
    }

    /**
     *
     * @return
     */
    public Long getCodSteg() {
        return this.codSteg;
    }

    /**
     *
     * @param codSteg
     */
    public void setCodSteg(Long codSteg) {
        this.codSteg = codSteg;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSteg() {
        return this.descricaoSteg;
    }

    /**
     *
     * @param descricaoSteg
     */
    public void setDescricaoSteg(String descricaoSteg) {
        this.descricaoSteg = descricaoSteg;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoSteg() {
        return this.dataInclusaoSteg;
    }

    /**
     *
     * @param dataInclusaoSteg
     */
    public void setDataInclusaoSteg(Date dataInclusaoSteg) {
        this.dataInclusaoSteg = dataInclusaoSteg;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoSteg() {
        return this.indAtivoSteg;
    }

    /**
     *
     * @param indAtivoSteg
     */
    public void setIndAtivoSteg(String indAtivoSteg) {
        this.indAtivoSteg = indAtivoSteg;
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
            .append("codSteg", getCodSteg())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SisTipoExibicGrupoSteg) ) return false;
        SisTipoExibicGrupoSteg castOther = (SisTipoExibicGrupoSteg) other;
        return new EqualsBuilder()
            .append(this.getCodSteg(), castOther.getCodSteg())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSteg())
            .toHashCode();
    }

}
