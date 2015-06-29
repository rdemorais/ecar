package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoCategoriaSgtc implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -196227467829607366L;

	/** identifier field */
    private Long codSgtc;

    /** nullable persistent field */
    private Date dataInclusaoSgtc;

    /** nullable persistent field */
    private String indAtivoSgtc;

    /** nullable persistent field */
    private String legImagemSgtc;

    /** nullable persistent field */
    private String imagemSgtc;

    /** nullable persistent field */
    private String descricaoSgtc;

    /** nullable persistent field */
    private String tituloSgtc;

    /** nullable persistent field */
    private String indUtilizTpAcessoSgtc;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;

    /** persistent field */
    private ecar.pojo.SegmentoSgt segmentoSgt;

    /** persistent field */
    private Set segmentoCategTpAcessSgts;

    /** persistent field */
    private Set segmentoItemSgtis;

    /** full constructor
     * @param dataInclusaoSgtc
     * @param indAtivoSgtc
     * @param indUtilizTpAcessoSgtc
     * @param tituloSgtc
     * @param legImagemSgtc
     * @param descricaoSgtc
     * @param segmentoCategTpAcessSgts
     * @param imagemSgtc
     * @param usuarioUsu
     * @param segmentoSgt
     * @param segmentoItemSgtis
     */
    public SegmentoCategoriaSgtc(Date dataInclusaoSgtc, String indAtivoSgtc, String legImagemSgtc, String imagemSgtc, String descricaoSgtc, String tituloSgtc, String indUtilizTpAcessoSgtc, ecar.pojo.UsuarioUsu usuarioUsu, ecar.pojo.SegmentoSgt segmentoSgt, Set segmentoCategTpAcessSgts, Set segmentoItemSgtis) {
        this.dataInclusaoSgtc = dataInclusaoSgtc;
        this.indAtivoSgtc = indAtivoSgtc;
        this.legImagemSgtc = legImagemSgtc;
        this.imagemSgtc = imagemSgtc;
        this.descricaoSgtc = descricaoSgtc;
        this.tituloSgtc = tituloSgtc;
        this.indUtilizTpAcessoSgtc = indUtilizTpAcessoSgtc;
        this.usuarioUsu = usuarioUsu;
        this.segmentoSgt = segmentoSgt;
        this.segmentoCategTpAcessSgts = segmentoCategTpAcessSgts;
        this.segmentoItemSgtis = segmentoItemSgtis;
    }

    /** default constructor */
    public SegmentoCategoriaSgtc() {
    }

    /** minimal constructor
     * @param usuarioUsu
     * @param segmentoSgt
     * @param segmentoCategTpAcessSgts
     * @param segmentoItemSgtis
     */
    public SegmentoCategoriaSgtc(ecar.pojo.UsuarioUsu usuarioUsu, ecar.pojo.SegmentoSgt segmentoSgt, Set segmentoCategTpAcessSgts, Set segmentoItemSgtis) {
        this.usuarioUsu = usuarioUsu;
        this.segmentoSgt = segmentoSgt;
        this.segmentoCategTpAcessSgts = segmentoCategTpAcessSgts;
        this.segmentoItemSgtis = segmentoItemSgtis;
    }

    /**
     *
     * @return
     */
    public Long getCodSgtc() {
        return this.codSgtc;
    }

    /**
     *
     * @param codSgtc
     */
    public void setCodSgtc(Long codSgtc) {
        this.codSgtc = codSgtc;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoSgtc() {
        return this.dataInclusaoSgtc;
    }

    /**
     *
     * @param dataInclusaoSgtc
     */
    public void setDataInclusaoSgtc(Date dataInclusaoSgtc) {
        this.dataInclusaoSgtc = dataInclusaoSgtc;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoSgtc() {
        return this.indAtivoSgtc;
    }

    /**
     *
     * @param indAtivoSgtc
     */
    public void setIndAtivoSgtc(String indAtivoSgtc) {
        this.indAtivoSgtc = indAtivoSgtc;
    }

    /**
     *
     * @return
     */
    public String getLegImagemSgtc() {
        return this.legImagemSgtc;
    }

    /**
     *
     * @param legImagemSgtc
     */
    public void setLegImagemSgtc(String legImagemSgtc) {
        this.legImagemSgtc = legImagemSgtc;
    }

    /**
     *
     * @return
     */
    public String getImagemSgtc() {
        return this.imagemSgtc;
    }

    /**
     *
     * @param imagemSgtc
     */
    public void setImagemSgtc(String imagemSgtc) {
        this.imagemSgtc = imagemSgtc;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSgtc() {
        return this.descricaoSgtc;
    }

    /**
     *
     * @param descricaoSgtc
     */
    public void setDescricaoSgtc(String descricaoSgtc) {
        this.descricaoSgtc = descricaoSgtc;
    }

    /**
     *
     * @return
     */
    public String getTituloSgtc() {
        return this.tituloSgtc;
    }

    /**
     *
     * @param tituloSgtc
     */
    public void setTituloSgtc(String tituloSgtc) {
        this.tituloSgtc = tituloSgtc;
    }

    /**
     *
     * @return
     */
    public String getIndUtilizTpAcessoSgtc() {
        return this.indUtilizTpAcessoSgtc;
    }

    /**
     *
     * @param indUtilizTpAcessoSgtc
     */
    public void setIndUtilizTpAcessoSgtc(String indUtilizTpAcessoSgtc) {
        this.indUtilizTpAcessoSgtc = indUtilizTpAcessoSgtc;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }

    /**
     *
     * @param usuarioUsu
     */
    public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SegmentoSgt getSegmentoSgt() {
        return this.segmentoSgt;
    }

    /**
     *
     * @param segmentoSgt
     */
    public void setSegmentoSgt(ecar.pojo.SegmentoSgt segmentoSgt) {
        this.segmentoSgt = segmentoSgt;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoCategTpAcessSgts() {
        return this.segmentoCategTpAcessSgts;
    }

    /**
     *
     * @param segmentoCategTpAcessSgts
     */
    public void setSegmentoCategTpAcessSgts(Set segmentoCategTpAcessSgts) {
        this.segmentoCategTpAcessSgts = segmentoCategTpAcessSgts;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoItemSgtis() {
        return this.segmentoItemSgtis;
    }

    /**
     *
     * @param segmentoItemSgtis
     */
    public void setSegmentoItemSgtis(Set segmentoItemSgtis) {
        this.segmentoItemSgtis = segmentoItemSgtis;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSgtc", getCodSgtc())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SegmentoCategoriaSgtc) ) return false;
        SegmentoCategoriaSgtc castOther = (SegmentoCategoriaSgtc) other;
        return new EqualsBuilder()
            .append(this.getCodSgtc(), castOther.getCodSgtc())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSgtc())
            .toHashCode();
    }

}
