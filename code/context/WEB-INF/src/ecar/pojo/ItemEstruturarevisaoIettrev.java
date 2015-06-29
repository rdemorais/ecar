package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ItemEstruturarevisaoIettrev implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2805562688390537116L;

	/** identifier field */
    private Integer codIettrev;

    /** nullable persistent field */
    private Double valPrevistoFuturoIettrev;

    /** nullable persistent field */
    private String indBloqPlanejamentoIettrev;

    /** nullable persistent field */
    private String beneficiosIettrev;

    /** nullable persistent field */
    private String origemIettrev;

    /** nullable persistent field */
    private String objetivoEspecificoIettrev;

    /** nullable persistent field */
    private String objetivoGeralIettrev;

    /** nullable persistent field */
    private String indMonitoramentoIettrev;

    /** nullable persistent field */
    private String indCriticaIettrev;

    /** nullable persistent field */
    private Date dataInicioMonitoramentoIettrev;

    /** nullable persistent field */
    private Date dataTerminoIettrev;

    /** nullable persistent field */
    private Date dataInicioIettrev;

    /** nullable persistent field */
    private String indAtivoIettrev;

    /** nullable persistent field */
    private Date dataInclusaoIettrev;

    /** nullable persistent field */
    private String descricaoIettrev;

    /** nullable persistent field */
    private String siglaIettrev;

    /** nullable persistent field */
    private String descricaoR5rev;

    /** nullable persistent field */
    private String descricaoR4rev;

    /** nullable persistent field */
    private String descricaoR3rev;

    /** nullable persistent field */
    private String descricaoR2rev;

    /** nullable persistent field */
    private String descricaoR1rev;

    /** nullable persistent field */
    private Date dataR5rev;

    /** nullable persistent field */
    private Date dataR4rev;

    /** nullable persistent field */
    private Date dataR3rev;

    /** nullable persistent field */
    private Date dataR2rev;

    /** nullable persistent field */
    private Date dataR1rev;

    /** nullable persistent field */
    private String nomeIettrev;

    /** nullable persistent field */
    private String justificativaIettrev;

    /** nullable persistent field */
    private Integer nivelIettrev;
    
    private String situacaoIettrev;

    /** persistent field */
    private ecar.pojo.PeriodicidadePrdc periodicidadePrdcrev;

    /** persistent field */
    private ecar.pojo.UnidadeOrcamentariaUO unidadeOrcamentariaUorev;

    /** persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIettrev;

    /** persistent field */
    private ecar.pojo.AreaAre areaArerev;

    /** persistent field */
    private ecar.pojo.SubAreaSare subAreaSarerev;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsuByCodUsuIncIettrev;

    /** persistent field */
    private ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev;

    /** persistent field */
    private ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel2Iettrev;

    /** persistent field */
    private ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iettrev;

    /** persistent field */
    private ecar.pojo.EstruturaEtt estruturaEttrev;
    
    /** persistent field */
    private Set efIettFonTotRevEfieftrs;

    /** persistent field */
    private Set itemEstruturarevisaoIettrevs;

    /** persistent field */
    private Set itemEstLocalRevIettlrs;

    /** persistent field */
    private Set iettUsutpfuacrevIettutfars;

    /** persistent field */
    private Set efIettPrevisaoRevEfieprs;

    /** persistent field */
    private Set iettIndResulRevIettrrs;

    /** full constructor
     * @param codIettrev
     * @param orgaoOrgByCodOrgaoResponsavel1Iettrev
     * @param valPrevistoFuturoIettrev
     * @param descricaoIettrev
     * @param indBloqPlanejamentoIettrev
     * @param origemIettrev
     * @param iettIndResulRevIettrrs
     * @param beneficiosIettrev
     * @param indMonitoramentoIettrev
     * @param dataInicioMonitoramentoIettrev
     * @param itemEstruturaIettrev
     * @param dataInicioIettrev
     * @param objetivoEspecificoIettrev
     * @param objetivoGeralIettrev
     * @param descricaoR5rev
     * @param dataTerminoIettrev
     * @param descricaoR2rev
     * @param unidadeOrcamentariaUorev
     * @param efIettFonTotRevEfieftrs
     * @param indCriticaIettrev
     * @param indAtivoIettrev
     * @param descricaoR3rev
     * @param dataInclusaoIettrev
     * @param descricaoR4rev
     * @param descricaoR1rev
     * @param usuarioUsuByCodUsuIncIettrev
     * @param dataR2rev
     * @param siglaIettrev
     * @param dataR5rev
     * @param nomeIettrev
     * @param itemEstruturarevisaoIettrevs
     * @param situacaoIettrev
     * @param dataR3rev
     * @param periodicidadePrdcrev
     * @param nivelIettrev
     * @param dataR4rev
     * @param justificativaIettrev
     * @param dataR1rev
     * @param itemEstLocalRevIettlrs
     * @param itemEstruturarevisaoIettrev
     * @param subAreaSarerev
     * @param areaArerev
     * @param orgaoOrgByCodOrgaoResponsavel2Iettrev
     * @param efIettPrevisaoRevEfieprs
     * @param estruturaEttrev
     * @param iettUsutpfuacrevIettutfars
     */
    public ItemEstruturarevisaoIettrev(Integer codIettrev, Double valPrevistoFuturoIettrev, String indBloqPlanejamentoIettrev, String beneficiosIettrev, String origemIettrev, String objetivoEspecificoIettrev, String objetivoGeralIettrev, 
    		String indMonitoramentoIettrev, String indCriticaIettrev, Date dataInicioMonitoramentoIettrev, Date dataTerminoIettrev, Date dataInicioIettrev, String indAtivoIettrev, Date dataInclusaoIettrev, 
    		String descricaoIettrev, String siglaIettrev, String descricaoR5rev, String descricaoR4rev, String descricaoR3rev, String descricaoR2rev, String descricaoR1rev, Date dataR5rev, Date dataR4rev, Date dataR3rev, 
    		Date dataR2rev, Date dataR1rev, String nomeIettrev, String justificativaIettrev, Integer nivelIettrev, ecar.pojo.PeriodicidadePrdc periodicidadePrdcrev, 
    		ecar.pojo.UnidadeOrcamentariaUO unidadeOrcamentariaUorev, ecar.pojo.ItemEstruturaIett itemEstruturaIettrev, ecar.pojo.AreaAre areaArerev, 
    		ecar.pojo.SubAreaSare subAreaSarerev, String situacaoIettrev, ecar.pojo.UsuarioUsu usuarioUsuByCodUsuIncIettrev, 
    		ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev, ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel2Iettrev, ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iettrev, 
    		ecar.pojo.EstruturaEtt estruturaEttrev, Set itemEstruturarevisaoIettrevs, Set itemEstLocalRevIettlrs, 
    		Set iettUsutpfuacrevIettutfars, Set efIettPrevisaoRevEfieprs, Set iettIndResulRevIettrrs,
    		Set efIettFonTotRevEfieftrs) {
        this.codIettrev = codIettrev;
        this.valPrevistoFuturoIettrev = valPrevistoFuturoIettrev;
        this.indBloqPlanejamentoIettrev = indBloqPlanejamentoIettrev;
        this.beneficiosIettrev = beneficiosIettrev;
        this.origemIettrev = origemIettrev;
        this.objetivoEspecificoIettrev = objetivoEspecificoIettrev;
        this.objetivoGeralIettrev = objetivoGeralIettrev;
        this.indMonitoramentoIettrev = indMonitoramentoIettrev;
        this.indCriticaIettrev = indCriticaIettrev;
        this.dataInicioMonitoramentoIettrev = dataInicioMonitoramentoIettrev;
        this.dataTerminoIettrev = dataTerminoIettrev;
        this.dataInicioIettrev = dataInicioIettrev;
        this.indAtivoIettrev = indAtivoIettrev;
        this.dataInclusaoIettrev = dataInclusaoIettrev;
        this.descricaoIettrev = descricaoIettrev;
        this.siglaIettrev = siglaIettrev;
        this.descricaoR5rev = descricaoR5rev;
        this.descricaoR4rev = descricaoR4rev;
        this.descricaoR3rev = descricaoR3rev;
        this.descricaoR2rev = descricaoR2rev;
        this.descricaoR1rev = descricaoR1rev;
        this.dataR5rev = dataR5rev;
        this.dataR4rev = dataR4rev;
        this.dataR3rev = dataR3rev;
        this.dataR2rev = dataR2rev;
        this.dataR1rev = dataR1rev;
        this.nomeIettrev = nomeIettrev;
        this.justificativaIettrev = justificativaIettrev;
        this.nivelIettrev = nivelIettrev;
        this.periodicidadePrdcrev = periodicidadePrdcrev;
        this.unidadeOrcamentariaUorev = unidadeOrcamentariaUorev;
        this.itemEstruturaIettrev = itemEstruturaIettrev;
        this.areaArerev = areaArerev;
        this.subAreaSarerev = subAreaSarerev;
        this.situacaoIettrev = situacaoIettrev;
        this.usuarioUsuByCodUsuIncIettrev = usuarioUsuByCodUsuIncIettrev;
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
        this.orgaoOrgByCodOrgaoResponsavel2Iettrev = orgaoOrgByCodOrgaoResponsavel2Iettrev;
        this.orgaoOrgByCodOrgaoResponsavel1Iettrev = orgaoOrgByCodOrgaoResponsavel1Iettrev;
        this.estruturaEttrev = estruturaEttrev;
        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
        this.itemEstLocalRevIettlrs = itemEstLocalRevIettlrs;
        this.iettUsutpfuacrevIettutfars = iettUsutpfuacrevIettutfars;
        this.efIettPrevisaoRevEfieprs = efIettPrevisaoRevEfieprs;
        this.iettIndResulRevIettrrs = iettIndResulRevIettrrs;
        this.efIettFonTotRevEfieftrs = efIettFonTotRevEfieftrs;
    }

    /** default constructor */
    public ItemEstruturarevisaoIettrev() {
    }

    /** minimal constructor
     * @param codIettrev
     * @param estruturaEttrev
     * @param unidadeOrcamentariaUorev
     * @param periodicidadePrdcrev
     * @param itemEstruturarevisaoIettrev
     * @param itemEstruturaIettrev
     * @param efIettPrevisaoRevEfieprs
     * @param areaArerev
     * @param subAreaSarerev
     * @param usuarioUsuByCodUsuIncIettrev
     * @param orgaoOrgByCodOrgaoResponsavel2Iettrev
     * @param iettUsutpfuacrevIettutfars
     * @param itemEstLocalRevIettlrs
     * @param orgaoOrgByCodOrgaoResponsavel1Iettrev
     * @param itemEstruturarevisaoIettrevs
     * @param iettIndResulRevIettrrs
     * @param efIettFonTotRevEfieftrs
     */
    public ItemEstruturarevisaoIettrev(Integer codIettrev, ecar.pojo.PeriodicidadePrdc periodicidadePrdcrev, ecar.pojo.UnidadeOrcamentariaUO unidadeOrcamentariaUorev, ecar.pojo.ItemEstruturaIett itemEstruturaIettrev, 
    		ecar.pojo.AreaAre areaArerev, ecar.pojo.SubAreaSare subAreaSarerev, ecar.pojo.UsuarioUsu usuarioUsuByCodUsuIncIettrev, 
    		ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev, ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel2Iettrev, ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iettrev, ecar.pojo.EstruturaEtt estruturaEttrev, 
    		Set itemEstruturarevisaoIettrevs, Set itemEstLocalRevIettlrs, Set iettUsutpfuacrevIettutfars, Set efIettPrevisaoRevEfieprs, Set iettIndResulRevIettrrs, Set efIettFonTotRevEfieftrs) {
        this.codIettrev = codIettrev;
        this.periodicidadePrdcrev = periodicidadePrdcrev;
        this.unidadeOrcamentariaUorev = unidadeOrcamentariaUorev;
        this.itemEstruturaIettrev = itemEstruturaIettrev;
        this.areaArerev = areaArerev;
        this.subAreaSarerev = subAreaSarerev;
        this.usuarioUsuByCodUsuIncIettrev = usuarioUsuByCodUsuIncIettrev;
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
        this.orgaoOrgByCodOrgaoResponsavel2Iettrev = orgaoOrgByCodOrgaoResponsavel2Iettrev;
        this.orgaoOrgByCodOrgaoResponsavel1Iettrev = orgaoOrgByCodOrgaoResponsavel1Iettrev;
        this.estruturaEttrev = estruturaEttrev;
        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
        this.itemEstLocalRevIettlrs = itemEstLocalRevIettlrs;
        this.iettUsutpfuacrevIettutfars = iettUsutpfuacrevIettutfars;
        this.efIettPrevisaoRevEfieprs = efIettPrevisaoRevEfieprs;
        this.iettIndResulRevIettrrs = iettIndResulRevIettrrs;
        this.efIettFonTotRevEfieftrs = efIettFonTotRevEfieftrs;
    }

    /**
     *
     * @return
     */
    public Integer getCodIettrev() {
        return this.codIettrev;
    }

    /**
     *
     * @param codIettrev
     */
    public void setCodIettrev(Integer codIettrev) {
        this.codIettrev = codIettrev;
    }

    /**
     *
     * @return
     */
    public Double getValPrevistoFuturoIettrev() {
        return this.valPrevistoFuturoIettrev;
    }

    /**
     *
     * @param valPrevistoFuturoIettrev
     */
    public void setValPrevistoFuturoIettrev(Double valPrevistoFuturoIettrev) {
        this.valPrevistoFuturoIettrev = valPrevistoFuturoIettrev;
    }

    /**
     *
     * @return
     */
    public String getIndBloqPlanejamentoIettrev() {
        return this.indBloqPlanejamentoIettrev;
    }

    /**
     *
     * @param indBloqPlanejamentoIettrev
     */
    public void setIndBloqPlanejamentoIettrev(String indBloqPlanejamentoIettrev) {
        this.indBloqPlanejamentoIettrev = indBloqPlanejamentoIettrev;
    }

    /**
     *
     * @return
     */
    public String getBeneficiosIettrev() {
        return this.beneficiosIettrev;
    }

    /**
     *
     * @param beneficiosIettrev
     */
    public void setBeneficiosIettrev(String beneficiosIettrev) {
        this.beneficiosIettrev = beneficiosIettrev;
    }

    /**
     *
     * @return
     */
    public String getOrigemIettrev() {
        return this.origemIettrev;
    }

    /**
     *
     * @param origemIettrev
     */
    public void setOrigemIettrev(String origemIettrev) {
        this.origemIettrev = origemIettrev;
    }

    /**
     *
     * @return
     */
    public String getObjetivoEspecificoIettrev() {
        return this.objetivoEspecificoIettrev;
    }

    /**
     *
     * @param objetivoEspecificoIettrev
     */
    public void setObjetivoEspecificoIettrev(String objetivoEspecificoIettrev) {
        this.objetivoEspecificoIettrev = objetivoEspecificoIettrev;
    }

    /**
     *
     * @return
     */
    public String getObjetivoGeralIettrev() {
        return this.objetivoGeralIettrev;
    }

    /**
     *
     * @param objetivoGeralIettrev
     */
    public void setObjetivoGeralIettrev(String objetivoGeralIettrev) {
        this.objetivoGeralIettrev = objetivoGeralIettrev;
    }

    /**
     *
     * @return
     */
    public String getIndMonitoramentoIettrev() {
        return this.indMonitoramentoIettrev;
    }

    /**
     *
     * @param indMonitoramentoIettrev
     */
    public void setIndMonitoramentoIettrev(String indMonitoramentoIettrev) {
        this.indMonitoramentoIettrev = indMonitoramentoIettrev;
    }

    /**
     *
     * @return
     */
    public String getIndCriticaIettrev() {
        return this.indCriticaIettrev;
    }

    /**
     *
     * @param indCriticaIettrev
     */
    public void setIndCriticaIettrev(String indCriticaIettrev) {
        this.indCriticaIettrev = indCriticaIettrev;
    }

    /**
     *
     * @return
     */
    public Date getDataInicioMonitoramentoIettrev() {
        return this.dataInicioMonitoramentoIettrev;
    }

    /**
     *
     * @param dataInicioMonitoramentoIettrev
     */
    public void setDataInicioMonitoramentoIettrev(Date dataInicioMonitoramentoIettrev) {
        this.dataInicioMonitoramentoIettrev = dataInicioMonitoramentoIettrev;
    }

    /**
     *
     * @return
     */
    public Date getDataTerminoIettrev() {
        return this.dataTerminoIettrev;
    }

    /**
     *
     * @param dataTerminoIettrev
     */
    public void setDataTerminoIettrev(Date dataTerminoIettrev) {
        this.dataTerminoIettrev = dataTerminoIettrev;
    }

    /**
     *
     * @return
     */
    public Date getDataInicioIettrev() {
        return this.dataInicioIettrev;
    }

    /**
     *
     * @param dataInicioIettrev
     */
    public void setDataInicioIettrev(Date dataInicioIettrev) {
        this.dataInicioIettrev = dataInicioIettrev;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoIettrev() {
        return this.indAtivoIettrev;
    }

    /**
     *
     * @param indAtivoIettrev
     */
    public void setIndAtivoIettrev(String indAtivoIettrev) {
        this.indAtivoIettrev = indAtivoIettrev;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoIettrev() {
        return this.dataInclusaoIettrev;
    }

    /**
     *
     * @param dataInclusaoIettrev
     */
    public void setDataInclusaoIettrev(Date dataInclusaoIettrev) {
        this.dataInclusaoIettrev = dataInclusaoIettrev;
    }

    /**
     *
     * @return
     */
    public String getDescricaoIettrev() {
        return this.descricaoIettrev;
    }

    /**
     *
     * @param descricaoIettrev
     */
    public void setDescricaoIettrev(String descricaoIettrev) {
        this.descricaoIettrev = descricaoIettrev;
    }

    /**
     *
     * @return
     */
    public String getSiglaIettrev() {
        return this.siglaIettrev;
    }

    /**
     *
     * @param siglaIettrev
     */
    public void setSiglaIettrev(String siglaIettrev) {
        this.siglaIettrev = siglaIettrev;
    }

    /**
     *
     * @return
     */
    public String getDescricaoR5rev() {
        return this.descricaoR5rev;
    }

    /**
     *
     * @param descricaoR5rev
     */
    public void setDescricaoR5rev(String descricaoR5rev) {
        this.descricaoR5rev = descricaoR5rev;
    }

    /**
     *
     * @return
     */
    public String getDescricaoR4rev() {
        return this.descricaoR4rev;
    }

    /**
     *
     * @param descricaoR4rev
     */
    public void setDescricaoR4rev(String descricaoR4rev) {
        this.descricaoR4rev = descricaoR4rev;
    }

    /**
     *
     * @return
     */
    public String getDescricaoR3rev() {
        return this.descricaoR3rev;
    }

    /**
     *
     * @param descricaoR3rev
     */
    public void setDescricaoR3rev(String descricaoR3rev) {
        this.descricaoR3rev = descricaoR3rev;
    }

    /**
     *
     * @return
     */
    public String getDescricaoR2rev() {
        return this.descricaoR2rev;
    }

    /**
     *
     * @param descricaoR2rev
     */
    public void setDescricaoR2rev(String descricaoR2rev) {
        this.descricaoR2rev = descricaoR2rev;
    }

    /**
     *
     * @return
     */
    public String getDescricaoR1rev() {
        return this.descricaoR1rev;
    }

    /**
     *
     * @param descricaoR1rev
     */
    public void setDescricaoR1rev(String descricaoR1rev) {
        this.descricaoR1rev = descricaoR1rev;
    }

    /**
     *
     * @return
     */
    public Date getDataR5rev() {
        return this.dataR5rev;
    }

    /**
     *
     * @param dataR5rev
     */
    public void setDataR5rev(Date dataR5rev) {
        this.dataR5rev = dataR5rev;
    }

    /**
     *
     * @return
     */
    public Date getDataR4rev() {
        return this.dataR4rev;
    }

    /**
     *
     * @param dataR4rev
     */
    public void setDataR4rev(Date dataR4rev) {
        this.dataR4rev = dataR4rev;
    }

    /**
     *
     * @return
     */
    public Date getDataR3rev() {
        return this.dataR3rev;
    }

    /**
     *
     * @param dataR3rev
     */
    public void setDataR3rev(Date dataR3rev) {
        this.dataR3rev = dataR3rev;
    }

    /**
     *
     * @return
     */
    public Date getDataR2rev() {
        return this.dataR2rev;
    }

    /**
     *
     * @param dataR2rev
     */
    public void setDataR2rev(Date dataR2rev) {
        this.dataR2rev = dataR2rev;
    }

    /**
     *
     * @return
     */
    public Date getDataR1rev() {
        return this.dataR1rev;
    }

    /**
     *
     * @param dataR1rev
     */
    public void setDataR1rev(Date dataR1rev) {
        this.dataR1rev = dataR1rev;
    }

    /**
     *
     * @return
     */
    public String getNomeIettrev() {
        return this.nomeIettrev;
    }

    /**
     *
     * @param nomeIettrev
     */
    public void setNomeIettrev(String nomeIettrev) {
        this.nomeIettrev = nomeIettrev;
    }

    /**
     *
     * @return
     */
    public String getJustificativaIettrev() {
        return this.justificativaIettrev;
    }

    /**
     *
     * @param justificativaIettrev
     */
    public void setJustificativaIettrev(String justificativaIettrev) {
        this.justificativaIettrev = justificativaIettrev;
    }

    /**
     *
     * @return
     */
    public Integer getNivelIettrev() {
        return this.nivelIettrev;
    }

    /**
     *
     * @param nivelIettrev
     */
    public void setNivelIettrev(Integer nivelIettrev) {
        this.nivelIettrev = nivelIettrev;
    }

    /**
     * 
     * @return
     */
    public ecar.pojo.PeriodicidadePrdc getPeriodicidadePrdcrev() {
        return this.periodicidadePrdcrev;
    }

    /**
     *
     * @param periodicidadePrdcrev
     */
    public void setPeriodicidadePrdcrev(ecar.pojo.PeriodicidadePrdc periodicidadePrdcrev) {
        this.periodicidadePrdcrev = periodicidadePrdcrev;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UnidadeOrcamentariaUO getUnidadeOrcamentariaUorev() {
        return this.unidadeOrcamentariaUorev;
    }

    /**
     *
     * @param unidadeOrcamentariaUorev
     */
    public void setUnidadeOrcamentariaUorev(ecar.pojo.UnidadeOrcamentariaUO unidadeOrcamentariaUorev) {
        this.unidadeOrcamentariaUorev = unidadeOrcamentariaUorev;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturaIett getItemEstruturaIettrev() {
        return this.itemEstruturaIettrev;
    }

    /**
     *
     * @param itemEstruturaIettrev
     */
    public void setItemEstruturaIettrev(ecar.pojo.ItemEstruturaIett itemEstruturaIettrev) {
        this.itemEstruturaIettrev = itemEstruturaIettrev;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.AreaAre getAreaArerev() {
        return this.areaArerev;
    }

    /**
     *
     * @param areaArerev
     */
    public void setAreaArerev(ecar.pojo.AreaAre areaArerev) {
        this.areaArerev = areaArerev;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.SubAreaSare getSubAreaSarerev() {
        return this.subAreaSarerev;
    }

    /**
     *
     * @param subAreaSarerev
     */
    public void setSubAreaSarerev(ecar.pojo.SubAreaSare subAreaSarerev) {
        this.subAreaSarerev = subAreaSarerev;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsuByCodUsuIncIettrev() {
        return this.usuarioUsuByCodUsuIncIettrev;
    }

    /**
     *
     * @param usuarioUsuByCodUsuIncIettrev
     */
    public void setUsuarioUsuByCodUsuIncIettrev(ecar.pojo.UsuarioUsu usuarioUsuByCodUsuIncIettrev) {
        this.usuarioUsuByCodUsuIncIettrev = usuarioUsuByCodUsuIncIettrev;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturarevisaoIettrev getItemEstruturarevisaoIettrev() {
        return this.itemEstruturarevisaoIettrev;
    }

    /**
     *
     * @param itemEstruturarevisaoIettrev
     */
    public void setItemEstruturarevisaoIettrev(ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev) {
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.OrgaoOrg getOrgaoOrgByCodOrgaoResponsavel2Iettrev() {
        return this.orgaoOrgByCodOrgaoResponsavel2Iettrev;
    }

    /**
     *
     * @param orgaoOrgByCodOrgaoResponsavel2Iettrev
     */
    public void setOrgaoOrgByCodOrgaoResponsavel2Iettrev(ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel2Iettrev) {
        this.orgaoOrgByCodOrgaoResponsavel2Iettrev = orgaoOrgByCodOrgaoResponsavel2Iettrev;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.OrgaoOrg getOrgaoOrgByCodOrgaoResponsavel1Iettrev() {
        return this.orgaoOrgByCodOrgaoResponsavel1Iettrev;
    }

    /**
     *
     * @param orgaoOrgByCodOrgaoResponsavel1Iettrev
     */
    public void setOrgaoOrgByCodOrgaoResponsavel1Iettrev(ecar.pojo.OrgaoOrg orgaoOrgByCodOrgaoResponsavel1Iettrev) {
        this.orgaoOrgByCodOrgaoResponsavel1Iettrev = orgaoOrgByCodOrgaoResponsavel1Iettrev;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EstruturaEtt getEstruturaEttrev() {
        return this.estruturaEttrev;
    }

    /**
     *
     * @param estruturaEttrev
     */
    public void setEstruturaEttrev(ecar.pojo.EstruturaEtt estruturaEttrev) {
        this.estruturaEttrev = estruturaEttrev;
    }

    /**
     *
     * @return
     */
    public Set getItemEstruturarevisaoIettrevs() {
        return this.itemEstruturarevisaoIettrevs;
    }

    /**
     *
     * @param itemEstruturarevisaoIettrevs
     */
    public void setItemEstruturarevisaoIettrevs(Set itemEstruturarevisaoIettrevs) {
        this.itemEstruturarevisaoIettrevs = itemEstruturarevisaoIettrevs;
    }

    /**
     *
     * @return
     */
    public Set getItemEstLocalRevIettlrs() {
        return this.itemEstLocalRevIettlrs;
    }

    /**
     *
     * @param itemEstLocalRevIettlrs
     */
    public void setItemEstLocalRevIettlrs(Set itemEstLocalRevIettlrs) {
        this.itemEstLocalRevIettlrs = itemEstLocalRevIettlrs;
    }

    /**
     *
     * @return
     */
    public Set getIettUsutpfuacrevIettutfars() {
        return this.iettUsutpfuacrevIettutfars;
    }

    /**
     *
     * @param iettUsutpfuacrevIettutfars
     */
    public void setIettUsutpfuacrevIettutfars(Set iettUsutpfuacrevIettutfars) {
        this.iettUsutpfuacrevIettutfars = iettUsutpfuacrevIettutfars;
    }

    /**
     *
     * @return
     */
    public Set getEfIettPrevisaoRevEfieprs() {
        return this.efIettPrevisaoRevEfieprs;
    }

    /**
     *
     * @param efIettPrevisaoRevEfieprs
     */
    public void setEfIettPrevisaoRevEfieprs(Set efIettPrevisaoRevEfieprs) {
        this.efIettPrevisaoRevEfieprs = efIettPrevisaoRevEfieprs;
    }

    /**
     *
     * @return
     */
    public Set getIettIndResulRevIettrrs() {
        return this.iettIndResulRevIettrrs;
    }

    /**
     *
     * @param iettIndResulRevIettrrs
     */
    public void setIettIndResulRevIettrrs(Set iettIndResulRevIettrrs) {
        this.iettIndResulRevIettrrs = iettIndResulRevIettrrs;
    }

    /**
     *
     * @return
     */
    public String getSituacaoSitrev(){
    	/*Este método retorna sempre null, pois as Situações no cadastro de revisão
    	 * Estão fixas... mas o campo está cadastrado no sistema.
    	 * Quando o relatório é gerado, ele sempre dá um getXXXX para chamar o método GET
    	 * de cada campo. Como o campo está fixo, ele tenta dar um getSituacaoSitrev() neste
    	 * pojo e não encontra o método, gerando uma NoSuchMethodException.
    	 * Então este método retorna null e no relatório essa chamada é tratada, pois está fixo a chamada para
    	 * cada situação.
    	*/
    	return null;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIettrev", getCodIettrev())
            .toString();
    }


    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstruturaIett) ) return false;
        ItemEstruturarevisaoIettrev castOther = (ItemEstruturarevisaoIettrev) other;
        return new EqualsBuilder()
            .append(this.getCodIettrev(), castOther.getCodIettrev())
            .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettrev())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public String getSituacaoIettrev() {
		return situacaoIettrev;
	}

        /**
         *
         * @param situacaoIettrev
         */
        public void setSituacaoIettrev(String situacaoIettrev) {
		this.situacaoIettrev = situacaoIettrev;
	}

        /**
         *
         * @return
         */
        public Set getEfIettFonTotRevEfieftrs() {
		return efIettFonTotRevEfieftrs;
	}

        /**
         *
         * @param efIettFonTotRevEfieftrs
         */
        public void setEfIettFonTotRevEfieftrs(Set efIettFonTotRevEfieftrs) {
		this.efIettFonTotRevEfieftrs = efIettFonTotRevEfieftrs;
	}

}
