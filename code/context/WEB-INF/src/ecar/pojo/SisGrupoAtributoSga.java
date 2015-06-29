package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SisGrupoAtributoSga implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6341634051295614106L;

	/** identifier field */
    private Long codSga;

    /** nullable persistent field */
    private String descricaoSga;

    /** nullable persistent field */
    private Date dataInclusaoSga;

    /** nullable persistent field */
    private String indAtivoSga;

    /** nullable persistent field */
    private String indCadSiteSga;

    /** nullable persistent field */
    private String indSistemaSga;

    /** nullable persistent field */
    private Integer seqApresentacaoSga;

    /** nullable persistent field */
    private String indObrigatorioSga;

    /** persistent field */
    private String indTabelaUsoSga;    
    
    /** persistent field */
    private String indCadUsuSga;    
    
    /** persistent field */
    private String descInfoComplementar;
    
    /** persistent field */
    private ecar.pojo.SisTipoOrdenacaoSto sisTipoOrdenacaoSto;

    /** persistent field */
    private ecar.pojo.SisTipoExibicGrupoSteg sisTipoExibicGrupoSteg;
    
    /** persistent field */
    private ecar.pojo.SisTipoExibicGrupoSteg sisTipoExibicGrupoCadUsuSteg;

    /** persistent field */
    private Set segmentoSgts;

    /** persistent field */
    private Set sisAtributoSatbs;
    
    /** persistent field */
    private Set configuracaoCfgsByCodSgaGrAtrPgIni;

    /** persistent field */
    private Set configuracaoCfgsByCodSgaGrAtrNvPlan;

    /** persistent field */
    private Set configuracaoCfgsByCodSgaGrAtrClAcesso;

    /** persistent field */
    private Set configuracaoCfgsByCodSgaGrAtrLeiCapa;

    /** persistent field */
    private Set configuracaoCfgsByCodSgaGrAtrTpAcesso;
    
    /** persistent field */
    private Set configuracaoCfgsByCodSgaGrAtrMetasFisicas;

    /** persistent field */
    private Set grupoAtributosLivresSgas;

    /** full constructor
     * @param descricaoSga
     * @param seqApresentacaoSga
     * @param dataInclusaoSga
     * @param indCadSiteSga
     * @param indAtivoSga
     * @param indSistemaSga
     * @param indCadUsuSga
     * @param configuracaoCfgsByCodSgaGrAtrTpAcesso
     * @param sisTipoExibicGrupoSteg
     * @param sisAtributoSatbs
     * @param descInfoComplementar
     * @param sisTipoOrdenacaoSto
     * @param indObrigatorioSga
     * @param segmentoSgts
     * @param grupoAtributosLivresSgas
     * @param configuracaoCfgsByCodSgaGrAtrPgIni
     * @param configuracaoCfgsByCodSgaGrAtrLeiCapa
     * @param configuracaoCfgsByCodSgaGrAtrClAcesso
     * @param sisTipoExibicGrupoCadUsuSteg
     * @param configuracaoCfgsByCodSgaGrAtrNvPlan
     * @param configuracaoCfgsByCodSgaGrAtrMetasFisicas
     */
    public SisGrupoAtributoSga(String descricaoSga, Date dataInclusaoSga, String indAtivoSga, String indCadSiteSga, String indSistemaSga, Integer seqApresentacaoSga, String indObrigatorioSga, ecar.pojo.SisTipoOrdenacaoSto sisTipoOrdenacaoSto, ecar.pojo.SisTipoExibicGrupoSteg sisTipoExibicGrupoSteg, Set segmentoSgts, Set sisAtributoSatbs, Set configuracaoCfgsByCodSgaGrAtrPgIni, Set configuracaoCfgsByCodSgaGrAtrNvPlan, Set configuracaoCfgsByCodSgaGrAtrClAcesso, Set configuracaoCfgsByCodSgaGrAtrLeiCapa, Set configuracaoCfgsByCodSgaGrAtrTpAcesso, Set configuracaoCfgsByCodSgaGrAtrMetasFisicas, String indCadUsuSga, String descInfoComplementar, Set grupoAtributosLivresSgas, ecar.pojo.SisTipoExibicGrupoSteg sisTipoExibicGrupoCadUsuSteg) {
        this.descricaoSga = descricaoSga;
        this.dataInclusaoSga = dataInclusaoSga;
        this.indAtivoSga = indAtivoSga;
        this.indCadSiteSga = indCadSiteSga;
        this.indSistemaSga = indSistemaSga;
        this.seqApresentacaoSga = seqApresentacaoSga;
        this.indObrigatorioSga = indObrigatorioSga;
        this.sisTipoOrdenacaoSto = sisTipoOrdenacaoSto;
        this.sisTipoExibicGrupoSteg = sisTipoExibicGrupoSteg;
        this.segmentoSgts = segmentoSgts;
        this.sisAtributoSatbs = sisAtributoSatbs;
        this.configuracaoCfgsByCodSgaGrAtrPgIni = configuracaoCfgsByCodSgaGrAtrPgIni;
        this.configuracaoCfgsByCodSgaGrAtrNvPlan = configuracaoCfgsByCodSgaGrAtrNvPlan;
        this.configuracaoCfgsByCodSgaGrAtrClAcesso = configuracaoCfgsByCodSgaGrAtrClAcesso;
        this.configuracaoCfgsByCodSgaGrAtrLeiCapa = configuracaoCfgsByCodSgaGrAtrLeiCapa;
        this.configuracaoCfgsByCodSgaGrAtrTpAcesso = configuracaoCfgsByCodSgaGrAtrTpAcesso;
        this.configuracaoCfgsByCodSgaGrAtrTpAcesso = configuracaoCfgsByCodSgaGrAtrMetasFisicas;
        this.indCadUsuSga = indCadUsuSga;
        this.descInfoComplementar = descInfoComplementar;
        this.grupoAtributosLivresSgas = grupoAtributosLivresSgas;
        this.sisTipoExibicGrupoCadUsuSteg = sisTipoExibicGrupoCadUsuSteg;
    }

    /** default constructor */
    public SisGrupoAtributoSga() {
    }

    /** minimal constructor
     * @param sisTipoOrdenacaoSto
     * @param configuracaoCfgsByCodSgaGrAtrClAcesso
     * @param sisTipoExibicGrupoSteg
     * @param sisAtributoSatbs
     * @param segmentoSgts
     * @param configuracaoCfgsByCodSgaGrAtrNvPlan
     * @param configuracaoCfgsByCodSgaGrAtrLeiCapa
     * @param configuracaoCfgsByCodSgaGrAtrPgIni
     * @param configuracaoCfgsByCodSgaGrAtrMetasFisicas
     * @param configuracaoCfgsByCodSgaGrAtrTpAcesso
     */
    public SisGrupoAtributoSga(ecar.pojo.SisTipoOrdenacaoSto sisTipoOrdenacaoSto, ecar.pojo.SisTipoExibicGrupoSteg sisTipoExibicGrupoSteg, Set segmentoSgts, Set sisAtributoSatbs, Set configuracaoCfgsByCodSgaGrAtrPgIni, Set configuracaoCfgsByCodSgaGrAtrNvPlan, Set configuracaoCfgsByCodSgaGrAtrClAcesso, Set configuracaoCfgsByCodSgaGrAtrLeiCapa, Set configuracaoCfgsByCodSgaGrAtrTpAcesso, Set configuracaoCfgsByCodSgaGrAtrMetasFisicas) {
        this.sisTipoOrdenacaoSto = sisTipoOrdenacaoSto;
        this.sisTipoExibicGrupoSteg = sisTipoExibicGrupoSteg;
        this.segmentoSgts = segmentoSgts;
        this.sisAtributoSatbs = sisAtributoSatbs;
        this.configuracaoCfgsByCodSgaGrAtrPgIni = configuracaoCfgsByCodSgaGrAtrPgIni;
        this.configuracaoCfgsByCodSgaGrAtrNvPlan = configuracaoCfgsByCodSgaGrAtrNvPlan;
        this.configuracaoCfgsByCodSgaGrAtrClAcesso = configuracaoCfgsByCodSgaGrAtrClAcesso;
        this.configuracaoCfgsByCodSgaGrAtrLeiCapa = configuracaoCfgsByCodSgaGrAtrLeiCapa;
        this.configuracaoCfgsByCodSgaGrAtrTpAcesso = configuracaoCfgsByCodSgaGrAtrTpAcesso;
        this.configuracaoCfgsByCodSgaGrAtrMetasFisicas = configuracaoCfgsByCodSgaGrAtrMetasFisicas;
    }

    /**
     *
     * @return
     */
    public Long getCodSga() {
        return this.codSga;
    }

    /**
     *
     * @param codSga
     */
    public void setCodSga(Long codSga) {
        this.codSga = codSga;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSga() {
        return this.descricaoSga;
    }

    /**
     *
     * @param descricaoSga
     */
    public void setDescricaoSga(String descricaoSga) {
        this.descricaoSga = descricaoSga;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoSga() {
        return this.dataInclusaoSga;
    }

    /**
     *
     * @param dataInclusaoSga
     */
    public void setDataInclusaoSga(Date dataInclusaoSga) {
        this.dataInclusaoSga = dataInclusaoSga;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoSga() {
        return this.indAtivoSga;
    }

    /**
     *
     * @param indAtivoSga
     */
    public void setIndAtivoSga(String indAtivoSga) {
        this.indAtivoSga = indAtivoSga;
    }

    /**
     *
     * @return
     */
    public String getIndCadSiteSga() {
        return this.indCadSiteSga;
    }

    /**
     *
     * @param indCadSiteSga
     */
    public void setIndCadSiteSga(String indCadSiteSga) {
        this.indCadSiteSga = indCadSiteSga;
    }

    /**
     *
     * @return
     */
    public String getIndSistemaSga() {
        return this.indSistemaSga;
    }

    /**
     *
     * @param indSistemaSga
     */
    public void setIndSistemaSga(String indSistemaSga) {
        this.indSistemaSga = indSistemaSga;
    }

    /**
     *
     * @return
     */
    public Integer getSeqApresentacaoSga() {
        return this.seqApresentacaoSga;
    }

    /**
     *
     * @param seqApresentacaoSga
     */
    public void setSeqApresentacaoSga(Integer seqApresentacaoSga) {
        this.seqApresentacaoSga = seqApresentacaoSga;
    }

    /**
     *
     * @return
     */
    public String getIndObrigatorioSga() {
        return this.indObrigatorioSga;
    }

    /**
     *
     * @param indObrigatorioSga
     */
    public void setIndObrigatorioSga(String indObrigatorioSga) {
        this.indObrigatorioSga = indObrigatorioSga;
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
    public Set getSegmentoSgts() {
        return this.segmentoSgts;
    }

    /**
     *
     * @param segmentoSgts
     */
    public void setSegmentoSgts(Set segmentoSgts) {
        this.segmentoSgts = segmentoSgts;
    }

    /**
     *
     * @return
     */
    public Set getSisAtributoSatbs() {
        return this.sisAtributoSatbs;
    }
         
    /**
     *
     * @param sisAtributoSatbs
     */
    public void setSisAtributoSatbs(Set sisAtributoSatbs) {
        this.sisAtributoSatbs = sisAtributoSatbs;
    }

    /**
     *
     * @return
     */
    public Set getConfiguracaoCfgsByCodSgaGrAtrPgIni() {
        return this.configuracaoCfgsByCodSgaGrAtrPgIni;
    }

    /**
     *
     * @param configuracaoCfgsByCodSgaGrAtrPgIni
     */
    public void setConfiguracaoCfgsByCodSgaGrAtrPgIni(Set configuracaoCfgsByCodSgaGrAtrPgIni) {
        this.configuracaoCfgsByCodSgaGrAtrPgIni = configuracaoCfgsByCodSgaGrAtrPgIni;
    }

    /**
     *
     * @return
     */
    public Set getConfiguracaoCfgsByCodSgaGrAtrNvPlan() {
        return this.configuracaoCfgsByCodSgaGrAtrNvPlan;
    }

    /**
     *
     * @param configuracaoCfgsByCodSgaGrAtrNvPlan
     */
    public void setConfiguracaoCfgsByCodSgaGrAtrNvPlan(Set configuracaoCfgsByCodSgaGrAtrNvPlan) {
        this.configuracaoCfgsByCodSgaGrAtrNvPlan = configuracaoCfgsByCodSgaGrAtrNvPlan;
    }

    /**
     *
     * @return
     */
    public Set getConfiguracaoCfgsByCodSgaGrAtrClAcesso() {
        return this.configuracaoCfgsByCodSgaGrAtrClAcesso;
    }

    /**
     *
     * @param configuracaoCfgsByCodSgaGrAtrClAcesso
     */
    public void setConfiguracaoCfgsByCodSgaGrAtrClAcesso(Set configuracaoCfgsByCodSgaGrAtrClAcesso) {
        this.configuracaoCfgsByCodSgaGrAtrClAcesso = configuracaoCfgsByCodSgaGrAtrClAcesso;
    }

    /**
     *
     * @return
     */
    public Set getConfiguracaoCfgsByCodSgaGrAtrLeiCapa() {
        return this.configuracaoCfgsByCodSgaGrAtrLeiCapa;
    }

    /**
     *
     * @param configuracaoCfgsByCodSgaGrAtrLeiCapa
     */
    public void setConfiguracaoCfgsByCodSgaGrAtrLeiCapa(Set configuracaoCfgsByCodSgaGrAtrLeiCapa) {
        this.configuracaoCfgsByCodSgaGrAtrLeiCapa = configuracaoCfgsByCodSgaGrAtrLeiCapa;
    }

    /**
     *
     * @return
     */
    public Set getConfiguracaoCfgsByCodSgaGrAtrTpAcesso() {
        return this.configuracaoCfgsByCodSgaGrAtrTpAcesso;
    }

    /**
     *
     * @param configuracaoCfgsByCodSgaGrAtrTpAcesso
     */
    public void setConfiguracaoCfgsByCodSgaGrAtrTpAcesso(Set configuracaoCfgsByCodSgaGrAtrTpAcesso) {
        this.configuracaoCfgsByCodSgaGrAtrTpAcesso = configuracaoCfgsByCodSgaGrAtrTpAcesso;
    }

    /**
     *
     * @return
     */
    public Set getConfiguracaoCfgsByCodSgaGrAtrMetasFisicas() {
		return configuracaoCfgsByCodSgaGrAtrMetasFisicas;
	}

    /**
     *
     * @param configuracaoCfgsByCodSgaGrAtrMetasFisicas
     */
    public void setConfiguracaoCfgsByCodSgaGrAtrMetasFisicas(
			Set configuracaoCfgsByCodSgaGrAtrMetasFisicas) {
		this.configuracaoCfgsByCodSgaGrAtrMetasFisicas = configuracaoCfgsByCodSgaGrAtrMetasFisicas;
	}

    /**
     *
     * @return
     */
    public Set getGrupoAtributosLivresSgas() {
		return grupoAtributosLivresSgas;
	}

    /**
     *
     * @param grupoAtributosLivresSgas
     */
    public void setGrupoAtributosLivresSgas(Set grupoAtributosLivresSgas) {
		this.grupoAtributosLivresSgas = grupoAtributosLivresSgas;
	}

    /**
     *
     * @return
     */
    public String getIndTabelaUsoSga() {
        return this.indTabelaUsoSga;
    }

        /**
         *
         * @param indTabelaUsoSga
         */
        public void setIndTabelaUsoSga(String indTabelaUsoSga) {
        this.indTabelaUsoSga = indTabelaUsoSga;
    }    
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSga", getCodSga())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SisGrupoAtributoSga) ) return false;
        SisGrupoAtributoSga castOther = (SisGrupoAtributoSga) other;
        return new EqualsBuilder()
            .append(this.getCodSga(), castOther.getCodSga())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSga())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public String getIndCadUsuSga() {
		return indCadUsuSga;
	}

        /**
         *
         * @param indCadUsuSga
         */
        public void setIndCadUsuSga(String indCadUsuSga) {
		this.indCadUsuSga = indCadUsuSga;
	}

        /**
         *
         * @return
         */
        public String getDescInfoComplementar() {
		return descInfoComplementar;
	}

        /**
         *
         * @param descInfoComplementar
         */
        public void setDescInfoComplementar(String descInfoComplementar) {
		this.descInfoComplementar = descInfoComplementar;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.SisTipoExibicGrupoSteg getSisTipoExibicGrupoCadUsuSteg() {
		return sisTipoExibicGrupoCadUsuSteg;
	}

        /**
         *
         * @param sisTipoExibicGrupoCadUsuSteg
         */
        public void setSisTipoExibicGrupoCadUsuSteg(
			ecar.pojo.SisTipoExibicGrupoSteg sisTipoExibicGrupoCadUsuSteg) {
		this.sisTipoExibicGrupoCadUsuSteg = sisTipoExibicGrupoCadUsuSteg;
	}

}
