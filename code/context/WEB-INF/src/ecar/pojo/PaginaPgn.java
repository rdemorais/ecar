package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PaginaPgn implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4996590935525305034L;

	/** identifier field */
    private Long codPgn;

    /** nullable persistent field */
    private Integer qtdSubAreaPgn;

    /** nullable persistent field */
    private String descricaoMapaPgn;

    /** nullable persistent field */
    private String tituloMapaPgn;

    /** nullable persistent field */
    private String indMapaPgn;

    /** nullable persistent field */
    private String indLoginPgn;

    /** nullable persistent field */
    private String parametrosPgn;

    /** nullable persistent field */
    private String indHomePgn;

    /** nullable persistent field */
    private String urlPgn;

    /** nullable persistent field */
    private String tituloPgn;

    /** nullable persistent field */
    private String nomePgn;

    /** persistent field */
    private ecar.pojo.IdiomaIdm idiomaIdm;

    /** persistent field */
    private ecar.pojo.PaginaPgn paginaPgn;

    /** persistent field */
    private ecar.pojo.PaginaAreaSitePa paginaAreaSitePa;

    /** persistent field */
    private Set paginaPgns;

    /** full constructor
     * @param qtdSubAreaPgn
     * @param idiomaIdm
     * @param descricaoMapaPgn
     * @param parametrosPgn
     * @param indLoginPgn
     * @param tituloMapaPgn
     * @param paginaPgns
     * @param urlPgn
     * @param indHomePgn
     * @param indMapaPgn
     * @param nomePgn
     * @param tituloPgn
     * @param paginaPgn
     * @param paginaAreaSitePa
     */
    public PaginaPgn(Integer qtdSubAreaPgn, String descricaoMapaPgn, String tituloMapaPgn, String indMapaPgn, String indLoginPgn, String parametrosPgn, String indHomePgn, String urlPgn, String tituloPgn, String nomePgn, ecar.pojo.IdiomaIdm idiomaIdm, ecar.pojo.PaginaPgn paginaPgn, ecar.pojo.PaginaAreaSitePa paginaAreaSitePa, Set paginaPgns) {
        this.qtdSubAreaPgn = qtdSubAreaPgn;
        this.descricaoMapaPgn = descricaoMapaPgn;
        this.tituloMapaPgn = tituloMapaPgn;
        this.indMapaPgn = indMapaPgn;
        this.indLoginPgn = indLoginPgn;
        this.parametrosPgn = parametrosPgn;
        this.indHomePgn = indHomePgn;
        this.urlPgn = urlPgn;
        this.tituloPgn = tituloPgn;
        this.nomePgn = nomePgn;
        this.idiomaIdm = idiomaIdm;
        this.paginaPgn = paginaPgn;
        this.paginaAreaSitePa = paginaAreaSitePa;
        this.paginaPgns = paginaPgns;
    }

    /** default constructor */
    public PaginaPgn() {
    }

    /** minimal constructor
     * @param idiomaIdm
     * @param paginaPgn
     * @param paginaAreaSitePa
     * @param paginaPgns
     */
    public PaginaPgn(ecar.pojo.IdiomaIdm idiomaIdm, ecar.pojo.PaginaPgn paginaPgn, ecar.pojo.PaginaAreaSitePa paginaAreaSitePa, Set paginaPgns) {
        this.idiomaIdm = idiomaIdm;
        this.paginaPgn = paginaPgn;
        this.paginaAreaSitePa = paginaAreaSitePa;
        this.paginaPgns = paginaPgns;
    }

    /**
     *
     * @return
     */
    public Long getCodPgn() {
        return this.codPgn;
    }

    /**
     *
     * @param codPgn
     */
    public void setCodPgn(Long codPgn) {
        this.codPgn = codPgn;
    }

    /**
     *
     * @return
     */
    public Integer getQtdSubAreaPgn() {
        return this.qtdSubAreaPgn;
    }

    /**
     *
     * @param qtdSubAreaPgn
     */
    public void setQtdSubAreaPgn(Integer qtdSubAreaPgn) {
        this.qtdSubAreaPgn = qtdSubAreaPgn;
    }

    /**
     *
     * @return
     */
    public String getDescricaoMapaPgn() {
        return this.descricaoMapaPgn;
    }

    /**
     *
     * @param descricaoMapaPgn
     */
    public void setDescricaoMapaPgn(String descricaoMapaPgn) {
        this.descricaoMapaPgn = descricaoMapaPgn;
    }

    /**
     *
     * @return
     */
    public String getTituloMapaPgn() {
        return this.tituloMapaPgn;
    }

    /**
     *
     * @param tituloMapaPgn
     */
    public void setTituloMapaPgn(String tituloMapaPgn) {
        this.tituloMapaPgn = tituloMapaPgn;
    }

    /**
     *
     * @return
     */
    public String getIndMapaPgn() {
        return this.indMapaPgn;
    }

    /**
     *
     * @param indMapaPgn
     */
    public void setIndMapaPgn(String indMapaPgn) {
        this.indMapaPgn = indMapaPgn;
    }

    /**
     *
     * @return
     */
    public String getIndLoginPgn() {
        return this.indLoginPgn;
    }

    /**
     *
     * @param indLoginPgn
     */
    public void setIndLoginPgn(String indLoginPgn) {
        this.indLoginPgn = indLoginPgn;
    }

    /**
     *
     * @return
     */
    public String getParametrosPgn() {
        return this.parametrosPgn;
    }

    /**
     *
     * @param parametrosPgn
     */
    public void setParametrosPgn(String parametrosPgn) {
        this.parametrosPgn = parametrosPgn;
    }

    /**
     *
     * @return
     */
    public String getIndHomePgn() {
        return this.indHomePgn;
    }

    /**
     *
     * @param indHomePgn
     */
    public void setIndHomePgn(String indHomePgn) {
        this.indHomePgn = indHomePgn;
    }

    /**
     *
     * @return
     */
    public String getUrlPgn() {
        return this.urlPgn;
    }

    /**
     *
     * @param urlPgn
     */
    public void setUrlPgn(String urlPgn) {
        this.urlPgn = urlPgn;
    }

    /**
     *
     * @return
     */
    public String getTituloPgn() {
        return this.tituloPgn;
    }

    /**
     *
     * @param tituloPgn
     */
    public void setTituloPgn(String tituloPgn) {
        this.tituloPgn = tituloPgn;
    }

    /**
     *
     * @return
     */
    public String getNomePgn() {
        return this.nomePgn;
    }

    /**
     *
     * @param nomePgn
     */
    public void setNomePgn(String nomePgn) {
        this.nomePgn = nomePgn;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.IdiomaIdm getIdiomaIdm() {
        return this.idiomaIdm;
    }

    /**
     *
     * @param idiomaIdm
     */
    public void setIdiomaIdm(ecar.pojo.IdiomaIdm idiomaIdm) {
        this.idiomaIdm = idiomaIdm;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.PaginaPgn getPaginaPgn() {
        return this.paginaPgn;
    }

    /**
     *
     * @param paginaPgn
     */
    public void setPaginaPgn(ecar.pojo.PaginaPgn paginaPgn) {
        this.paginaPgn = paginaPgn;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.PaginaAreaSitePa getPaginaAreaSitePa() {
        return this.paginaAreaSitePa;
    }

    /**
     *
     * @param paginaAreaSitePa
     */
    public void setPaginaAreaSitePa(ecar.pojo.PaginaAreaSitePa paginaAreaSitePa) {
        this.paginaAreaSitePa = paginaAreaSitePa;
    }

    /**
     *
     * @return
     */
    public Set getPaginaPgns() {
        return this.paginaPgns;
    }

    /**
     *
     * @param paginaPgns
     */
    public void setPaginaPgns(Set paginaPgns) {
        this.paginaPgns = paginaPgns;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codPgn", getCodPgn())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PaginaPgn) ) return false;
        PaginaPgn castOther = (PaginaPgn) other;
        return new EqualsBuilder()
            .append(this.getCodPgn(), castOther.getCodPgn())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodPgn())
            .toHashCode();
    }

}
