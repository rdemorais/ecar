package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoItemSgti implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1609994316985778067L;

	/** identifier field */
    private Long codSgti;

    /** nullable persistent field */
    private String legendaImag1Sgti;

    /** nullable persistent field */
    private String imagem1Stgi;

    /** nullable persistent field */
    private String integraSgti;

    /** nullable persistent field */
    private String linhaApoioSgti;

    /** nullable persistent field */
    private String tituloSgti;

    /** nullable persistent field */
    private Date dataInclusaoSgti;

    /** nullable persistent field */
    private String indSuperDestaqueSgti;

    /** nullable persistent field */
    private String indDestaqueSgti;

    /** nullable persistent field */
    private String indUtilizTpAcessoSgti;

    /** nullable persistent field */
    private Date dataFimValidadeSgti;

    /** nullable persistent field */
    private Date dataIniValidadeSgti;

    /** nullable persistent field */
    private String palavrasChavesSgti;

    /** nullable persistent field */
    private String urlLinkSgti;

    /** nullable persistent field */
    private String anexoLegendaSgti;

    /** nullable persistent field */
    private String anexoEnderecoSgti;

    /** nullable persistent field */
    private String indAtivoSgti;

    /** nullable persistent field */
    private String legendaImagCapaSgti;

    /** nullable persistent field */
    private String imagemCapaSgti;

    /** nullable persistent field */
    private String legendaImag3Sgti;

    /** nullable persistent field */
    private String imagem3Sgti;

    /** nullable persistent field */
    private String legendaImag2Sgti;

    /** nullable persistent field */
    private String imagem2Sgti;

    /** nullable persistent field */
    private Date dataItemSgti;
    
    /** persistent field */
    private ecar.pojo.SegmentoItemFonteSgtif segmentoItemFonteSgtif;

    /** persistent field */
    private ecar.pojo.SegmentoCategoriaSgtc segmentoCategoriaSgtc;

    /** persistent field */
    private ecar.pojo.SegmentoItemLeiauteSgtil segmentoItemLeiauteSgtil;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;

    /** persistent field */
    private ecar.pojo.SegmentoSgt segmentoSgt;

    /** persistent field */
    private Set itemEstrutVinculoIettvs;

    /** persistent field */
    private Set segmentoItemTpacesSgtitas;

    /** persistent field */
    private Set destaqueItemRelDtqirs;

    /** persistent field */
    private Set segmentoSisAtribSgtsas;

    /** persistent field */
    private Set segmentoItemRelacSgtirsByCodSgtiRelac;

    /** persistent field */
    private Set segmentoItemRelacSgtirsByCodSgtiOrigem;

    /** full constructor
     * @param legendaImag1Sgti
     * @param imagem1Stgi
     * @param segmentoSgt
     * @param integraSgti
     * @param linhaApoioSgti
     * @param tituloSgti
     * @param segmentoItemLeiauteSgtil
     * @param indDestaqueSgti
     * @param indSuperDestaqueSgti
     * @param indUtilizTpAcessoSgti
     * @param dataIniValidadeSgti
     * @param dataInclusaoSgti
     * @param palavrasChavesSgti
     * @param indAtivoSgti
     * @param dataFimValidadeSgti
     * @param imagem3Sgti
     * @param segmentoCategoriaSgtc
     * @param destaqueItemRelDtqirs
     * @param urlLinkSgti
     * @param anexoLegendaSgti
     * @param anexoEnderecoSgti
     * @param segmentoItemTpacesSgtitas
     * @param imagemCapaSgti
     * @param imagem2Sgti
     * @param legendaImag3Sgti
     * @param segmentoItemFonteSgtif
     * @param usuarioUsu
     * @param legendaImagCapaSgti
     * @param dataItemSgti
     * @param itemEstrutVinculoIettvs
     * @param legendaImag2Sgti
     * @param segmentoItemRelacSgtirsByCodSgtiOrigem
     * @param segmentoSisAtribSgtsas
     * @param segmentoItemRelacSgtirsByCodSgtiRelac
     */
    public SegmentoItemSgti(String legendaImag1Sgti, String imagem1Stgi, String integraSgti, String linhaApoioSgti, String tituloSgti, Date dataInclusaoSgti, String indSuperDestaqueSgti, String indDestaqueSgti, String indUtilizTpAcessoSgti, Date dataFimValidadeSgti, Date dataIniValidadeSgti, String palavrasChavesSgti, String urlLinkSgti, String anexoLegendaSgti, String anexoEnderecoSgti, String indAtivoSgti, String legendaImagCapaSgti, String imagemCapaSgti, String legendaImag3Sgti, String imagem3Sgti, String legendaImag2Sgti, String imagem2Sgti, Date dataItemSgti, ecar.pojo.SegmentoItemFonteSgtif segmentoItemFonteSgtif, ecar.pojo.SegmentoCategoriaSgtc segmentoCategoriaSgtc, ecar.pojo.SegmentoItemLeiauteSgtil segmentoItemLeiauteSgtil, ecar.pojo.UsuarioUsu usuarioUsu, ecar.pojo.SegmentoSgt segmentoSgt, Set itemEstrutVinculoIettvs, Set segmentoItemTpacesSgtitas, Set destaqueItemRelDtqirs, Set segmentoSisAtribSgtsas, Set segmentoItemRelacSgtirsByCodSgtiRelac, Set segmentoItemRelacSgtirsByCodSgtiOrigem) {
        this.legendaImag1Sgti = legendaImag1Sgti;
        this.imagem1Stgi = imagem1Stgi;
        this.integraSgti = integraSgti;
        this.linhaApoioSgti = linhaApoioSgti;
        this.tituloSgti = tituloSgti;
        this.dataInclusaoSgti = dataInclusaoSgti;
        this.indSuperDestaqueSgti = indSuperDestaqueSgti;
        this.indDestaqueSgti = indDestaqueSgti;
        this.indUtilizTpAcessoSgti = indUtilizTpAcessoSgti;
        this.dataFimValidadeSgti = dataFimValidadeSgti;
        this.dataIniValidadeSgti = dataIniValidadeSgti;
        this.palavrasChavesSgti = palavrasChavesSgti;
        this.urlLinkSgti = urlLinkSgti;
        this.anexoLegendaSgti = anexoLegendaSgti;
        this.anexoEnderecoSgti = anexoEnderecoSgti;
        this.indAtivoSgti = indAtivoSgti;
        this.legendaImagCapaSgti = legendaImagCapaSgti;
        this.imagemCapaSgti = imagemCapaSgti;
        this.legendaImag3Sgti = legendaImag3Sgti;
        this.imagem3Sgti = imagem3Sgti;
        this.legendaImag2Sgti = legendaImag2Sgti;
        this.imagem2Sgti = imagem2Sgti;
        this.dataItemSgti = dataItemSgti;
        this.segmentoItemFonteSgtif = segmentoItemFonteSgtif;
        this.segmentoCategoriaSgtc = segmentoCategoriaSgtc;
        this.segmentoItemLeiauteSgtil = segmentoItemLeiauteSgtil;
        this.usuarioUsu = usuarioUsu;
        this.segmentoSgt = segmentoSgt;
        this.itemEstrutVinculoIettvs = itemEstrutVinculoIettvs;
        this.segmentoItemTpacesSgtitas = segmentoItemTpacesSgtitas;
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
        this.segmentoSisAtribSgtsas = segmentoSisAtribSgtsas;
        this.segmentoItemRelacSgtirsByCodSgtiRelac = segmentoItemRelacSgtirsByCodSgtiRelac;
        this.segmentoItemRelacSgtirsByCodSgtiOrigem = segmentoItemRelacSgtirsByCodSgtiOrigem;
    }

    /** default constructor */
    public SegmentoItemSgti() {
    }

    /** minimal constructor
     * @param segmentoItemFonteSgtif
     * @param segmentoItemRelacSgtirsByCodSgtiRelac
     * @param segmentoCategoriaSgtc
     * @param segmentoItemLeiauteSgtil
     * @param usuarioUsu
     * @param segmentoSgt
     * @param segmentoItemTpacesSgtitas
     * @param destaqueItemRelDtqirs
     * @param itemEstrutVinculoIettvs
     * @param segmentoItemRelacSgtirsByCodSgtiOrigem
     * @param segmentoSisAtribSgtsas
     */
    public SegmentoItemSgti(ecar.pojo.SegmentoItemFonteSgtif segmentoItemFonteSgtif, ecar.pojo.SegmentoCategoriaSgtc segmentoCategoriaSgtc, ecar.pojo.SegmentoItemLeiauteSgtil segmentoItemLeiauteSgtil, ecar.pojo.UsuarioUsu usuarioUsu, ecar.pojo.SegmentoSgt segmentoSgt, Set itemEstrutVinculoIettvs, Set segmentoItemTpacesSgtitas, Set destaqueItemRelDtqirs, Set segmentoSisAtribSgtsas, Set segmentoItemRelacSgtirsByCodSgtiRelac, Set segmentoItemRelacSgtirsByCodSgtiOrigem) {
        this.segmentoItemFonteSgtif = segmentoItemFonteSgtif;
        this.segmentoCategoriaSgtc = segmentoCategoriaSgtc;
        this.segmentoItemLeiauteSgtil = segmentoItemLeiauteSgtil;
        this.usuarioUsu = usuarioUsu;
        this.segmentoSgt = segmentoSgt;
        this.itemEstrutVinculoIettvs = itemEstrutVinculoIettvs;
        this.segmentoItemTpacesSgtitas = segmentoItemTpacesSgtitas;
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
        this.segmentoSisAtribSgtsas = segmentoSisAtribSgtsas;
        this.segmentoItemRelacSgtirsByCodSgtiRelac = segmentoItemRelacSgtirsByCodSgtiRelac;
        this.segmentoItemRelacSgtirsByCodSgtiOrigem = segmentoItemRelacSgtirsByCodSgtiOrigem;
    }

    /**
     *
     * @return
     */
    public Long getCodSgti() {
        return this.codSgti;
    }

    /**
     *
     * @param codSgti
     */
    public void setCodSgti(Long codSgti) {
        this.codSgti = codSgti;
    }

    /**
     *
     * @return
     */
    public String getLegendaImag1Sgti() {
        return this.legendaImag1Sgti;
    }

    /**
     *
     * @param legendaImag1Sgti
     */
    public void setLegendaImag1Sgti(String legendaImag1Sgti) {
        this.legendaImag1Sgti = legendaImag1Sgti;
    }

    /**
     *
     * @return
     */
    public String getImagem1Stgi() {
        return this.imagem1Stgi;
    }

    /**
     *
     * @param imagem1Stgi
     */
    public void setImagem1Stgi(String imagem1Stgi) {
        this.imagem1Stgi = imagem1Stgi;
    }

    /**
     *
     * @return
     */
    public String getIntegraSgti() {
        return this.integraSgti;
    }

    /**
     *
     * @param integraSgti
     */
    public void setIntegraSgti(String integraSgti) {
        this.integraSgti = integraSgti;
    }

    /**
     *
     * @return
     */
    public String getLinhaApoioSgti() {
        return this.linhaApoioSgti;
    }

    /**
     *
     * @param linhaApoioSgti
     */
    public void setLinhaApoioSgti(String linhaApoioSgti) {
        this.linhaApoioSgti = linhaApoioSgti;
    }

    /**
     *
     * @return
     */
    public String getTituloSgti() {
        return this.tituloSgti;
    }

    /**
     *
     * @param tituloSgti
     */
    public void setTituloSgti(String tituloSgti) {
        this.tituloSgti = tituloSgti;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoSgti() {
        return this.dataInclusaoSgti;
    }

    /**
     *
     * @param dataInclusaoSgti
     */
    public void setDataInclusaoSgti(Date dataInclusaoSgti) {
        this.dataInclusaoSgti = dataInclusaoSgti;
    }

    /**
     *
     * @return
     */
    public String getIndSuperDestaqueSgti() {
        return this.indSuperDestaqueSgti;
    }

    /**
     *
     * @param indSuperDestaqueSgti
     */
    public void setIndSuperDestaqueSgti(String indSuperDestaqueSgti) {
        this.indSuperDestaqueSgti = indSuperDestaqueSgti;
    }

    /**
     *
     * @return
     */
    public String getIndDestaqueSgti() {
        return this.indDestaqueSgti;
    }

    /**
     *
     * @param indDestaqueSgti
     */
    public void setIndDestaqueSgti(String indDestaqueSgti) {
        this.indDestaqueSgti = indDestaqueSgti;
    }

    /**
     *
     * @return
     */
    public String getIndUtilizTpAcessoSgti() {
        return this.indUtilizTpAcessoSgti;
    }

    /**
     *
     * @param indUtilizTpAcessoSgti
     */
    public void setIndUtilizTpAcessoSgti(String indUtilizTpAcessoSgti) {
        this.indUtilizTpAcessoSgti = indUtilizTpAcessoSgti;
    }

    /**
     *
     * @return
     */
    public Date getDataFimValidadeSgti() {
        return this.dataFimValidadeSgti;
    }

    /**
     *
     * @param dataFimValidadeSgti
     */
    public void setDataFimValidadeSgti(Date dataFimValidadeSgti) {
        this.dataFimValidadeSgti = dataFimValidadeSgti;
    }

    /**
     *
     * @return
     */
    public Date getDataIniValidadeSgti() {
        return this.dataIniValidadeSgti;
    }

    /**
     *
     * @param dataIniValidadeSgti
     */
    public void setDataIniValidadeSgti(Date dataIniValidadeSgti) {
        this.dataIniValidadeSgti = dataIniValidadeSgti;
    }

    /**
     *
     * @return
     */
    public String getPalavrasChavesSgti() {
        return this.palavrasChavesSgti;
    }

    /**
     *
     * @param palavrasChavesSgti
     */
    public void setPalavrasChavesSgti(String palavrasChavesSgti) {
        this.palavrasChavesSgti = palavrasChavesSgti;
    }

    /**
     *
     * @return
     */
    public String getUrlLinkSgti() {
        return this.urlLinkSgti;
    }

    /**
     *
     * @param urlLinkSgti
     */
    public void setUrlLinkSgti(String urlLinkSgti) {
        this.urlLinkSgti = urlLinkSgti;
    }

    /**
     *
     * @return
     */
    public String getAnexoLegendaSgti() {
        return this.anexoLegendaSgti;
    }

    /**
     *
     * @param anexoLegendaSgti
     */
    public void setAnexoLegendaSgti(String anexoLegendaSgti) {
        this.anexoLegendaSgti = anexoLegendaSgti;
    }

    /**
     *
     * @return
     */
    public String getAnexoEnderecoSgti() {
        return this.anexoEnderecoSgti;
    }

    /**
     *
     * @param anexoEnderecoSgti
     */
    public void setAnexoEnderecoSgti(String anexoEnderecoSgti) {
        this.anexoEnderecoSgti = anexoEnderecoSgti;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoSgti() {
        return this.indAtivoSgti;
    }

    /**
     *
     * @param indAtivoSgti
     */
    public void setIndAtivoSgti(String indAtivoSgti) {
        this.indAtivoSgti = indAtivoSgti;
    }

    /**
     *
     * @return
     */
    public String getLegendaImagCapaSgti() {
        return this.legendaImagCapaSgti;
    }

    /**
     *
     * @param legendaImagCapaSgti
     */
    public void setLegendaImagCapaSgti(String legendaImagCapaSgti) {
        this.legendaImagCapaSgti = legendaImagCapaSgti;
    }

    /**
     *
     * @return
     */
    public String getImagemCapaSgti() {
        return this.imagemCapaSgti;
    }

    /**
     *
     * @param imagemCapaSgti
     */
    public void setImagemCapaSgti(String imagemCapaSgti) {
        this.imagemCapaSgti = imagemCapaSgti;
    }

    /**
     *
     * @return
     */
    public String getLegendaImag3Sgti() {
        return this.legendaImag3Sgti;
    }

    /**
     *
     * @param legendaImag3Sgti
     */
    public void setLegendaImag3Sgti(String legendaImag3Sgti) {
        this.legendaImag3Sgti = legendaImag3Sgti;
    }

    /**
     *
     * @return
     */
    public String getImagem3Sgti() {
        return this.imagem3Sgti;
    }

    /**
     *
     * @param imagem3Sgti
     */
    public void setImagem3Sgti(String imagem3Sgti) {
        this.imagem3Sgti = imagem3Sgti;
    }

    /**
     *
     * @return
     */
    public String getLegendaImag2Sgti() {
        return this.legendaImag2Sgti;
    }

    /**
     *
     * @param legendaImag2Sgti
     */
    public void setLegendaImag2Sgti(String legendaImag2Sgti) {
        this.legendaImag2Sgti = legendaImag2Sgti;
    }

    /**
     *
     * @return
     */
    public String getImagem2Sgti() {
        return this.imagem2Sgti;
    }

    /**
     *
     * @param imagem2Sgti
     */
    public void setImagem2Sgti(String imagem2Sgti) {
        this.imagem2Sgti = imagem2Sgti;
    }
        
    /**
     *
     * @return
     */
    public Date getDataItemSgti() {
        return this.dataItemSgti;
    }

    /**
     *
     * @param dataItemSgti
     */
    public void setDataItemSgti(Date dataItemSgti) {
        this.dataItemSgti = dataItemSgti;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SegmentoItemFonteSgtif getSegmentoItemFonteSgtif() {
        return this.segmentoItemFonteSgtif;
    }

    /**
     *
     * @param segmentoItemFonteSgtif
     */
    public void setSegmentoItemFonteSgtif(ecar.pojo.SegmentoItemFonteSgtif segmentoItemFonteSgtif) {
        this.segmentoItemFonteSgtif = segmentoItemFonteSgtif;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SegmentoCategoriaSgtc getSegmentoCategoriaSgtc() {
        return this.segmentoCategoriaSgtc;
    }

    /**
     *
     * @param segmentoCategoriaSgtc
     */
    public void setSegmentoCategoriaSgtc(ecar.pojo.SegmentoCategoriaSgtc segmentoCategoriaSgtc) {
        this.segmentoCategoriaSgtc = segmentoCategoriaSgtc;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SegmentoItemLeiauteSgtil getSegmentoItemLeiauteSgtil() {
        return this.segmentoItemLeiauteSgtil;
    }
    
    /**
     *
     * @param segmentoItemLeiauteSgtil
     */
    public void setSegmentoItemLeiauteSgtil(ecar.pojo.SegmentoItemLeiauteSgtil segmentoItemLeiauteSgtil) {
        this.segmentoItemLeiauteSgtil = segmentoItemLeiauteSgtil;
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
    public Set getItemEstrutVinculoIettvs() {
        return this.itemEstrutVinculoIettvs;
    }

    /**
     *
     * @param itemEstrutVinculoIettvs
     */
    public void setItemEstrutVinculoIettvs(Set itemEstrutVinculoIettvs) {
        this.itemEstrutVinculoIettvs = itemEstrutVinculoIettvs;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoItemTpacesSgtitas() {
        return this.segmentoItemTpacesSgtitas;
    }

    /**
     *
     * @param segmentoItemTpacesSgtitas
     */
    public void setSegmentoItemTpacesSgtitas(Set segmentoItemTpacesSgtitas) {
        this.segmentoItemTpacesSgtitas = segmentoItemTpacesSgtitas;
    }

    /**
     *
     * @return
     */
    public Set getDestaqueItemRelDtqirs() {
        return this.destaqueItemRelDtqirs;
    }

    /**
     *
     * @param destaqueItemRelDtqirs
     */
    public void setDestaqueItemRelDtqirs(Set destaqueItemRelDtqirs) {
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoSisAtribSgtsas() {
        return this.segmentoSisAtribSgtsas;
    }

    /**
     *
     * @param segmentoSisAtribSgtsas
     */
    public void setSegmentoSisAtribSgtsas(Set segmentoSisAtribSgtsas) {
        this.segmentoSisAtribSgtsas = segmentoSisAtribSgtsas;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoItemRelacSgtirsByCodSgtiRelac() {
        return this.segmentoItemRelacSgtirsByCodSgtiRelac;
    }

    /**
     *
     * @param segmentoItemRelacSgtirsByCodSgtiRelac
     */
    public void setSegmentoItemRelacSgtirsByCodSgtiRelac(Set segmentoItemRelacSgtirsByCodSgtiRelac) {
        this.segmentoItemRelacSgtirsByCodSgtiRelac = segmentoItemRelacSgtirsByCodSgtiRelac;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoItemRelacSgtirsByCodSgtiOrigem() {
        return this.segmentoItemRelacSgtirsByCodSgtiOrigem;
    }

    /**
     *
     * @param segmentoItemRelacSgtirsByCodSgtiOrigem
     */
    public void setSegmentoItemRelacSgtirsByCodSgtiOrigem(Set segmentoItemRelacSgtirsByCodSgtiOrigem) {
        this.segmentoItemRelacSgtirsByCodSgtiOrigem = segmentoItemRelacSgtirsByCodSgtiOrigem;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSgti", getCodSgti())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SegmentoItemSgti) ) return false;
        SegmentoItemSgti castOther = (SegmentoItemSgti) other;
        return new EqualsBuilder()
            .append(this.getCodSgti(), castOther.getCodSgti())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSgti())
            .toHashCode();
    }

}
