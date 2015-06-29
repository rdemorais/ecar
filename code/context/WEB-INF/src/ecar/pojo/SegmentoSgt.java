package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoSgt implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3927410700638515107L;

	/** identifier field */
    private Long codSgt;

    /** nullable persistent field */
    private String linkPesquisaSgt;

    /** nullable persistent field */
    private String indMenuSgt;

    /** nullable persistent field */
    private String indAtivoSgt;

    /** nullable persistent field */
    private String legendaImagemSgt;

    /** nullable persistent field */
    private String imagemSgt;

    /** nullable persistent field */
    private String descricaoSgt;

    /** nullable persistent field */
    private String tituloSgt;

    /** nullable persistent field */
    private String indUtilizTpAcessoSgt;

    /** nullable persistent field */
    private Date dataInclusaoSgt;

    /** persistent field */
    private ecar.pojo.SegmentoAreaSgta segmentoAreaSgta;

    /** persistent field */
    private ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga;

    /** persistent field */
    private ecar.pojo.SegmentoLeiauteSgtl segmentoLeiauteSgtl;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;

    /** persistent field */
    private Set segmentoCategoriaSgtcs;

    /** persistent field */
    private Set segmentoTpAcessoSgttas;

    /** persistent field */
    private Set segmentoItemSgtis;

    /** full constructor
     * @param linkPesquisaSgt
     * @param indMenuSgt
     * @param indAtivoSgt
     * @param legendaImagemSgt
     * @param descricaoSgt
     * @param imagemSgt
     * @param tituloSgt
     * @param sisGrupoAtributoSga
     * @param segmentoLeiauteSgtl
     * @param dataInclusaoSgt
     * @param segmentoAreaSgta
     * @param usuarioUsu
     * @param indUtilizTpAcessoSgt
     * @param segmentoCategoriaSgtcs
     * @param segmentoTpAcessoSgttas
     * @param segmentoItemSgtis
     */
    public SegmentoSgt(String linkPesquisaSgt, String indMenuSgt, String indAtivoSgt, String legendaImagemSgt, String imagemSgt, String descricaoSgt, String tituloSgt, String indUtilizTpAcessoSgt, Date dataInclusaoSgt, ecar.pojo.SegmentoAreaSgta segmentoAreaSgta, ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga, ecar.pojo.SegmentoLeiauteSgtl segmentoLeiauteSgtl, ecar.pojo.UsuarioUsu usuarioUsu, Set segmentoCategoriaSgtcs, Set segmentoTpAcessoSgttas, Set segmentoItemSgtis) {
        this.linkPesquisaSgt = linkPesquisaSgt;
        this.indMenuSgt = indMenuSgt;
        this.indAtivoSgt = indAtivoSgt;
        this.legendaImagemSgt = legendaImagemSgt;
        this.imagemSgt = imagemSgt;
        this.descricaoSgt = descricaoSgt;
        this.tituloSgt = tituloSgt;
        this.indUtilizTpAcessoSgt = indUtilizTpAcessoSgt;
        this.dataInclusaoSgt = dataInclusaoSgt;
        this.segmentoAreaSgta = segmentoAreaSgta;
        this.sisGrupoAtributoSga = sisGrupoAtributoSga;
        this.segmentoLeiauteSgtl = segmentoLeiauteSgtl;
        this.usuarioUsu = usuarioUsu;
        this.segmentoCategoriaSgtcs = segmentoCategoriaSgtcs;
        this.segmentoTpAcessoSgttas = segmentoTpAcessoSgttas;
        this.segmentoItemSgtis = segmentoItemSgtis;
    }

    /** default constructor */
    public SegmentoSgt() {
    }

    /** minimal constructor
     * @param segmentoAreaSgta
     * @param usuarioUsu
     * @param segmentoLeiauteSgtl
     * @param sisGrupoAtributoSga
     * @param segmentoItemSgtis
     * @param segmentoTpAcessoSgttas
     * @param segmentoCategoriaSgtcs
     */
    public SegmentoSgt(ecar.pojo.SegmentoAreaSgta segmentoAreaSgta, ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga, ecar.pojo.SegmentoLeiauteSgtl segmentoLeiauteSgtl, ecar.pojo.UsuarioUsu usuarioUsu, Set segmentoCategoriaSgtcs, Set segmentoTpAcessoSgttas, Set segmentoItemSgtis) {
        this.segmentoAreaSgta = segmentoAreaSgta;
        this.sisGrupoAtributoSga = sisGrupoAtributoSga;
        this.segmentoLeiauteSgtl = segmentoLeiauteSgtl;
        this.usuarioUsu = usuarioUsu;
        this.segmentoCategoriaSgtcs = segmentoCategoriaSgtcs;
        this.segmentoTpAcessoSgttas = segmentoTpAcessoSgttas;
        this.segmentoItemSgtis = segmentoItemSgtis;
    }

    /**
     *
     * @return
     */
    public Long getCodSgt() {
        return this.codSgt;
    }

    /**
     *
     * @param codSgt
     */
    public void setCodSgt(Long codSgt) {
        this.codSgt = codSgt;
    }

    /**
     *
     * @return
     */
    public String getLinkPesquisaSgt() {
        return this.linkPesquisaSgt;
    }

    /**
     *
     * @param linkPesquisaSgt
     */
    public void setLinkPesquisaSgt(String linkPesquisaSgt) {
        this.linkPesquisaSgt = linkPesquisaSgt;
    }

    /**
     *
     * @return
     */
    public String getIndMenuSgt() {
        return this.indMenuSgt;
    }

    /**
     *
     * @param indMenuSgt
     */
    public void setIndMenuSgt(String indMenuSgt) {
        this.indMenuSgt = indMenuSgt;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoSgt() {
        return this.indAtivoSgt;
    }

    /**
     *
     * @param indAtivoSgt
     */
    public void setIndAtivoSgt(String indAtivoSgt) {
        this.indAtivoSgt = indAtivoSgt;
    }

    /**
     *
     * @return
     */
    public String getLegendaImagemSgt() {
        return this.legendaImagemSgt;
    }

    /**
     *
     * @param legendaImagemSgt
     */
    public void setLegendaImagemSgt(String legendaImagemSgt) {
        this.legendaImagemSgt = legendaImagemSgt;
    }

    /**
     *
     * @return
     */
    public String getImagemSgt() {
        return this.imagemSgt;
    }

    /**
     *
     * @param imagemSgt
     */
    public void setImagemSgt(String imagemSgt) {
        this.imagemSgt = imagemSgt;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSgt() {
        return this.descricaoSgt;
    }

    /**
     *
     * @param descricaoSgt
     */
    public void setDescricaoSgt(String descricaoSgt) {
        this.descricaoSgt = descricaoSgt;
    }

    /**
     *
     * @return
     */
    public String getTituloSgt() {
        return this.tituloSgt;
    }

    /**
     *
     * @param tituloSgt
     */
    public void setTituloSgt(String tituloSgt) {
        this.tituloSgt = tituloSgt;
    }

    /**
     *
     * @return
     */
    public String getIndUtilizTpAcessoSgt() {
        return this.indUtilizTpAcessoSgt;
    }

    /**
     *
     * @param indUtilizTpAcessoSgt
     */
    public void setIndUtilizTpAcessoSgt(String indUtilizTpAcessoSgt) {
        this.indUtilizTpAcessoSgt = indUtilizTpAcessoSgt;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoSgt() {
        return this.dataInclusaoSgt;
    }

    /**
     *
     * @param dataInclusaoSgt
     */
    public void setDataInclusaoSgt(Date dataInclusaoSgt) {
        this.dataInclusaoSgt = dataInclusaoSgt;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SegmentoAreaSgta getSegmentoAreaSgta() {
        return this.segmentoAreaSgta;
    }

    /**
     *
     * @param segmentoAreaSgta
     */
    public void setSegmentoAreaSgta(ecar.pojo.SegmentoAreaSgta segmentoAreaSgta) {
        this.segmentoAreaSgta = segmentoAreaSgta;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SisGrupoAtributoSga getSisGrupoAtributoSga() {
        return this.sisGrupoAtributoSga;
    }

    /**
     *
     * @param sisGrupoAtributoSga
     */
    public void setSisGrupoAtributoSga(ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga) {
        this.sisGrupoAtributoSga = sisGrupoAtributoSga;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SegmentoLeiauteSgtl getSegmentoLeiauteSgtl() {
        return this.segmentoLeiauteSgtl;
    }

    /**
     *
     * @param segmentoLeiauteSgtl
     */
    public void setSegmentoLeiauteSgtl(ecar.pojo.SegmentoLeiauteSgtl segmentoLeiauteSgtl) {
        this.segmentoLeiauteSgtl = segmentoLeiauteSgtl;
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
    public Set getSegmentoCategoriaSgtcs() {
        return this.segmentoCategoriaSgtcs;
    }

    /**
     *
     * @param segmentoCategoriaSgtcs
     */
    public void setSegmentoCategoriaSgtcs(Set segmentoCategoriaSgtcs) {
        this.segmentoCategoriaSgtcs = segmentoCategoriaSgtcs;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoTpAcessoSgttas() {
        return this.segmentoTpAcessoSgttas;
    }

    /**
     *
     * @param segmentoTpAcessoSgttas
     */
    public void setSegmentoTpAcessoSgttas(Set segmentoTpAcessoSgttas) {
        this.segmentoTpAcessoSgttas = segmentoTpAcessoSgttas;
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
            .append("codSgt", getCodSgt())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SegmentoSgt) ) return false;
        SegmentoSgt castOther = (SegmentoSgt) other;
        return new EqualsBuilder()
            .append(this.getCodSgt(), castOther.getCodSgt())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSgt())
            .toHashCode();
    }

}
