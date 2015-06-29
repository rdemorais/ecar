package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class GrupoAtributoGatt implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 609196540464217556L;

	/** identifier field */
    private Long codGatt;

    /** nullable persistent field */
    private String descricaoGatt;

    /** persistent field */
    private String indTabelaUsoGatt;

    /** nullable persistent field */
    private Integer seqApresentacaoGatt;

    /** nullable persistent field */
    private String indObrigatorioGatt;

    /** nullable persistent field */
    private String indSistemaGatt;

    /** nullable persistent field */
    private Date dataInclusaoGatt;

    /** nullable persistent field */
    private String indAtivoGatt;

    /** persistent field */
    private ecar.pojo.SisTipoOrdenacaoSto sisTipoOrdenacaoSto;

    /** persistent field */
    private ecar.pojo.SisTipoExibicGrupoSteg sisTipoExibicGrupoSteg;

    /** persistent field */
    private Set atributoAtts;

    /** full constructor
     * @param descricaoGatt
     * @param atributoAtts
     * @param indTabelaUsoGatt
     * @param seqApresentacaoGatt
     * @param indSistemaGatt 
     * @param sisTipoOrdenacaoSto
     * @param dataInclusaoGatt
     * @param indAtivoGatt
     * @param indObrigatorioGatt
     * @param sisTipoExibicGrupoSteg
     */
    public GrupoAtributoGatt(String descricaoGatt, String indTabelaUsoGatt, Integer seqApresentacaoGatt, String indObrigatorioGatt, String indSistemaGatt, Date dataInclusaoGatt, String indAtivoGatt, ecar.pojo.SisTipoOrdenacaoSto sisTipoOrdenacaoSto, ecar.pojo.SisTipoExibicGrupoSteg sisTipoExibicGrupoSteg, Set atributoAtts) {
        this.descricaoGatt = descricaoGatt;
        this.indTabelaUsoGatt = indTabelaUsoGatt;
        this.seqApresentacaoGatt = seqApresentacaoGatt;
        this.indObrigatorioGatt = indObrigatorioGatt;
        this.indSistemaGatt = indSistemaGatt;
        this.dataInclusaoGatt = dataInclusaoGatt;
        this.indAtivoGatt = indAtivoGatt;
        this.sisTipoOrdenacaoSto = sisTipoOrdenacaoSto;
        this.sisTipoExibicGrupoSteg = sisTipoExibicGrupoSteg;
        this.atributoAtts = atributoAtts;
    }

    /** default constructor */
    public GrupoAtributoGatt() {
    }

    /** minimal constructor
     * @param indTabelaUsoGatt
     * @param sisTipoOrdenacaoSto
     * @param sisTipoExibicGrupoSteg
     * @param atributoAtts
     */
    public GrupoAtributoGatt(String indTabelaUsoGatt, ecar.pojo.SisTipoOrdenacaoSto sisTipoOrdenacaoSto, ecar.pojo.SisTipoExibicGrupoSteg sisTipoExibicGrupoSteg, Set atributoAtts) {
        this.indTabelaUsoGatt = indTabelaUsoGatt;
        this.sisTipoOrdenacaoSto = sisTipoOrdenacaoSto;
        this.sisTipoExibicGrupoSteg = sisTipoExibicGrupoSteg;
        this.atributoAtts = atributoAtts;
    }

    /**
     *
     * @return
     */
    public Long getCodGatt() {
        return this.codGatt;
    }

    /**
     *
     * @param codGatt
     */
    public void setCodGatt(Long codGatt) {
        this.codGatt = codGatt;
    }

    /**
     *
     * @return
     */
    public String getDescricaoGatt() {
        return this.descricaoGatt;
    }

    /**
     *
     * @param descricaoGatt
     */
    public void setDescricaoGatt(String descricaoGatt) {
        this.descricaoGatt = descricaoGatt;
    }

    /**
     *
     * @return
     */
    public String getIndTabelaUsoGatt() {
        return this.indTabelaUsoGatt;
    }

    /**
     *
     * @param indTabelaUsoGatt
     */
    public void setIndTabelaUsoGatt(String indTabelaUsoGatt) {
        this.indTabelaUsoGatt = indTabelaUsoGatt;
    }

    /**
     *
     * @return
     */
    public Integer getSeqApresentacaoGatt() {
        return this.seqApresentacaoGatt;
    }

    /**
     *
     * @param seqApresentacaoGatt
     */
    public void setSeqApresentacaoGatt(Integer seqApresentacaoGatt) {
        this.seqApresentacaoGatt = seqApresentacaoGatt;
    }

    /**
     *
     * @return
     */
    public String getIndObrigatorioGatt() {
        return this.indObrigatorioGatt;
    }

    /**
     *
     * @param indObrigatorioGatt
     */
    public void setIndObrigatorioGatt(String indObrigatorioGatt) {
        this.indObrigatorioGatt = indObrigatorioGatt;
    }

    /**
     *
     * @return
     */
    public String getIndSistemaGatt() {
        return this.indSistemaGatt;
    }

    /**
     *
     * @param indSistemaGatt
     */
    public void setIndSistemaGatt(String indSistemaGatt) {
        this.indSistemaGatt = indSistemaGatt;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoGatt() {
        return this.dataInclusaoGatt;
    }

    /**
     *
     * @param dataInclusaoGatt
     */
    public void setDataInclusaoGatt(Date dataInclusaoGatt) {
        this.dataInclusaoGatt = dataInclusaoGatt;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoGatt() {
        return this.indAtivoGatt;
    }

    /**
     *
     * @param indAtivoGatt
     */
    public void setIndAtivoGatt(String indAtivoGatt) {
        this.indAtivoGatt = indAtivoGatt;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SisTipoOrdenacaoSto getSisTipoOrdenacaoSto() {
        return this.sisTipoOrdenacaoSto;
    }

    /**
     *
     * @param sisTipoOrdenacaoSto
     */
    public void setSisTipoOrdenacaoSto(ecar.pojo.SisTipoOrdenacaoSto sisTipoOrdenacaoSto) {
        this.sisTipoOrdenacaoSto = sisTipoOrdenacaoSto;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SisTipoExibicGrupoSteg getSisTipoExibicGrupoSteg() {
        return this.sisTipoExibicGrupoSteg;
    }

    /**
     *
     * @param sisTipoExibicGrupoSteg
     */
    public void setSisTipoExibicGrupoSteg(ecar.pojo.SisTipoExibicGrupoSteg sisTipoExibicGrupoSteg) {
        this.sisTipoExibicGrupoSteg = sisTipoExibicGrupoSteg;
    }

    /**
     *
     * @return
     */
    public Set getAtributoAtts() {
        return this.atributoAtts;
    }

    /**
     *
     * @param atributoAtts
     */
    public void setAtributoAtts(Set atributoAtts) {
        this.atributoAtts = atributoAtts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codGatt", getCodGatt())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof GrupoAtributoGatt) ) return false;
        GrupoAtributoGatt castOther = (GrupoAtributoGatt) other;
        return new EqualsBuilder()
            .append(this.getCodGatt(), castOther.getCodGatt())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodGatt())
            .toHashCode();
    }

}
