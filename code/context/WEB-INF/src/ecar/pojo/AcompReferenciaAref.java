package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * Esta classe representa os per�odos de refer�ncia configurados no sistema.
 * 
 * O monitoramento � realizado a partir de um per�odo, ou seja, um conjunto de configura��es que definem
 * como os atores v�o interagir em termos de datas e que itens devem entrar naquele "Ciclo".
 * 
 * @author Hibernate CodeGenerator 
 * 
 * */
public class AcompReferenciaAref implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 263097901608280652L;

	/** identifier field */
    private Long codAref;

    /** nullable persistent field */
    private Date dataUltManutAref;

    /** nullable persistent field */
    private Date dataInclusaoAref;

    /** nullable persistent field */
    private Date dataLimiteAcompFisicoAref;

    /** nullable persistent field */
    private Date dataInicioAref;
    
    /** nullable persistent field */
    private String diaAref;

    /** nullable persistent field */
    private String mesAref;

    /** nullable persistent field */
    private String anoAref;

    /** nullable persistent field */
    private String nomeAref;
    
    /** nullable persistent field */
    private String indInformacaoOrgaoAref;

    /** persistent field */
    private OrgaoOrg orgaoOrg;

    /** persistent field */
    private TipoAcompanhamentoTa tipoAcompanhamentoTa;
    
    /** persistent field */
    private UsuarioUsu usuarioUsuByCodUsuIncAref;

    /** persistent field */
    private UsuarioUsu usuarioUsuByCodUsuUltManutAref;
    
    /** garten em 24/02/2005 */
    private ecar.pojo.ExercicioExe exercicioExe;

    /** persistent field */
    private Set acompReferenciaItemAris;

    /** persistent field */
    private Set acompRefLimitesArls;
    
    
	/** 
     * full constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param dataUltManutAref
     * @param dataInclusaoAref
     * @param dataLimiteAcompFisicoAref
     * @param tipoAcompanhamentoTa
     * @param diaAref 
     * @param mesAref
     * @param dataInicioAref
     * @param nomeAref
     * @param orgaoOrg
     * @param anoAref
     * @param usuarioUsuByCodUsuIncAref 
     * @param exercicioExe 
     * @param usuarioUsuByCodUsuUltManutAref
     * @param acompReferenciaItemAris
     * @param acompRefLimitesArls
     * * @param diaAref
     */
    public AcompReferenciaAref(Date dataUltManutAref, Date dataInclusaoAref, Date dataLimiteAcompFisicoAref, 
    		Date dataInicioAref, TipoAcompanhamentoTa tipoAcompanhamentoTa,  String mesAref, String anoAref, 
    		String nomeAref, ecar.pojo.OrgaoOrg orgaoOrg, ecar.pojo.UsuarioUsu usuarioUsuByCodUsuIncAref, 
    		ecar.pojo.UsuarioUsu usuarioUsuByCodUsuUltManutAref, ecar.pojo.ExercicioExe exercicioExe, 
    		Set acompReferenciaItemAris, Set acompRefLimitesArls, String diaAref, String indInformacaoOrgaoAref) {
        this.dataUltManutAref = dataUltManutAref;
        this.dataInclusaoAref = dataInclusaoAref;
        this.dataLimiteAcompFisicoAref = dataLimiteAcompFisicoAref;
        this.dataInicioAref = dataInicioAref;
        this.tipoAcompanhamentoTa = tipoAcompanhamentoTa;
        this.diaAref = diaAref;
        this.mesAref = mesAref;
        this.anoAref = anoAref;
        this.nomeAref = nomeAref;
        this.orgaoOrg = orgaoOrg;
        this.indInformacaoOrgaoAref = indInformacaoOrgaoAref;
        this.usuarioUsuByCodUsuIncAref = usuarioUsuByCodUsuIncAref;
        this.usuarioUsuByCodUsuUltManutAref = usuarioUsuByCodUsuUltManutAref;
        this.exercicioExe = exercicioExe;
        this.acompReferenciaItemAris = acompReferenciaItemAris;
        this.acompRefLimitesArls = acompRefLimitesArls;
    }

    /** default constructor */
    public AcompReferenciaAref() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @author N/C
     * @since N/C
     * @param orgaoOrg
     * @param exercicioExe 
     * @param usuarioUsuByCodUsuIncAref
     * @param usuarioUsuByCodUsuUltManutAref
     * @param acompReferenciaItemAris
     * @param acompRefLimitesArls
     * @param tipoAcompanhamentoTa
     */
    public AcompReferenciaAref(ecar.pojo.OrgaoOrg orgaoOrg, ecar.pojo.ExercicioExe exercicioExe, 
    		ecar.pojo.UsuarioUsu usuarioUsuByCodUsuIncAref, ecar.pojo.UsuarioUsu usuarioUsuByCodUsuUltManutAref, 
    		Set acompReferenciaItemAris, Set acompRefLimitesArls, TipoAcompanhamentoTa tipoAcompanhamentoTa) {
        this.orgaoOrg = orgaoOrg;
        this.exercicioExe = exercicioExe;
        this.usuarioUsuByCodUsuIncAref = usuarioUsuByCodUsuIncAref;
        this.usuarioUsuByCodUsuUltManutAref = usuarioUsuByCodUsuUltManutAref;
        this.acompReferenciaItemAris = acompReferenciaItemAris;
        this.acompRefLimitesArls = acompRefLimitesArls;
        this.tipoAcompanhamentoTa = tipoAcompanhamentoTa;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
    public Long getCodAref() {
        return this.codAref;
    }

    /**
     * @param codAref
     * @author N/C
     * @since N/C
     */
    public void setCodAref(Long codAref) {
        this.codAref = codAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataUltManutAref() {
        return this.dataUltManutAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataUltManutAref
     */
    public void setDataUltManutAref(Date dataUltManutAref) {
        this.dataUltManutAref = dataUltManutAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataInclusaoAref() {
        return this.dataInclusaoAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataInclusaoAref
     */
    public void setDataInclusaoAref(Date dataInclusaoAref) {
        this.dataInclusaoAref = dataInclusaoAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataLimiteAcompFisicoAref() {
        return this.dataLimiteAcompFisicoAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataLimiteAcompFisicoAref
     */
    public void setDataLimiteAcompFisicoAref(Date dataLimiteAcompFisicoAref) {
        this.dataLimiteAcompFisicoAref = dataLimiteAcompFisicoAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataInicioAref() {
        return this.dataInicioAref;
    }

    /**
     * @param dataInicioAref
     * @author N/C
     * @since N/C
     */
    public void setDataInicioAref(Date dataInicioAref) {
        this.dataInicioAref = dataInicioAref;
    }
    
    /**
     * @author N/C
     * @since N/C
     * @return TipoAcompanhamentoTa
     */
    public TipoAcompanhamentoTa getTipoAcompanhamentoTa() {
        return this.tipoAcompanhamentoTa;
    }

    /**
     * @author N/C
     * @since N/C
     * @param tipoAcompanhamentoTa
     */
    public void setTipoAcompanhamentoTa (TipoAcompanhamentoTa tipoAcompanhamentoTa) {
        this.tipoAcompanhamentoTa = tipoAcompanhamentoTa;
    }
    
    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getDiaAref() {
        return this.diaAref;
    }

    /**
     * @param mesAref
     * @author N/C
     * @since N/C
     */
    public void setDiaAref(String diaAref) {
        this.diaAref = diaAref;
    }


    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getMesAref() {
        return this.mesAref;
    }

    /**
     * @param mesAref
     * @author N/C
     * @since N/C
     */
    public void setMesAref(String mesAref) {
        this.mesAref = mesAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getAnoAref() {
        return this.anoAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @param anoAref
     */
    public void setAnoAref(String anoAref) {
        this.anoAref = anoAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getNomeAref() {
        return this.nomeAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @param nomeAref
     */
    public void setNomeAref(String nomeAref) {
        this.nomeAref = nomeAref;
    }
    
    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public String getIndInformacaoOrgaoAref() {
        return this.indInformacaoOrgaoAref;
    }

    /**
     * @param 
     * @author N/C
     * @since N/C
     */
    public void setIndInformacaoOrgaoAref(String indInformacaoOrgaoAref) {
        this.indInformacaoOrgaoAref = indInformacaoOrgaoAref;
    }



    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.OrgaoOrg
     */
    public ecar.pojo.OrgaoOrg getOrgaoOrg() {
        return this.orgaoOrg;
    }

    /**
     * @param orgaoOrg
     * @author N/C
     * @since N/C
     */
    public void setOrgaoOrg(ecar.pojo.OrgaoOrg orgaoOrg) {
        this.orgaoOrg = orgaoOrg;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.UsuarioUsu
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsuByCodUsuIncAref() {
        return this.usuarioUsuByCodUsuIncAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @param usuarioUsuByCodUsuIncAref
     */
    public void setUsuarioUsuByCodUsuIncAref(ecar.pojo.UsuarioUsu usuarioUsuByCodUsuIncAref) {
        this.usuarioUsuByCodUsuIncAref = usuarioUsuByCodUsuIncAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.UsuarioUsu
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsuByCodUsuUltManutAref() {
        return this.usuarioUsuByCodUsuUltManutAref;
    }

    /**
     * @param usuarioUsuByCodUsuUltManutAref
     * @author N/C
     * @since N/C
     */
    public void setUsuarioUsuByCodUsuUltManutAref(ecar.pojo.UsuarioUsu usuarioUsuByCodUsuUltManutAref) {
        this.usuarioUsuByCodUsuUltManutAref = usuarioUsuByCodUsuUltManutAref;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Set
     */
    public Set getAcompReferenciaItemAris() {
        return this.acompReferenciaItemAris;
    }

    /**
     * @param acompReferenciaItemAris
     * @author N/C
     * @since N/C
     */
    public void setAcompReferenciaItemAris(Set acompReferenciaItemAris) {
        this.acompReferenciaItemAris = acompReferenciaItemAris;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Set
     */
    public Set getAcompRefLimitesArls() {
        return this.acompRefLimitesArls;
    }

    /**
     * @author N/C
     * @since N/C
     * @param acompRefLimitesArls
     */
    public void setAcompRefLimitesArls(Set acompRefLimitesArls) {
        this.acompRefLimitesArls = acompRefLimitesArls;
    }
    
    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codAref", getCodAref())
            .toString();
    }

    /**
     * @author N/C
     * @since N/C
     * @param other
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof AcompReferenciaAref) ) return false;
        AcompReferenciaAref castOther = (AcompReferenciaAref) other;
        return new EqualsBuilder()
            .append(this.getCodAref(), castOther.getCodAref())
            .isEquals();
    }

    /**
     * @author N/C
     * @since N/C
     * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodAref())
            .toHashCode();
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.ExercicioExe - (Returns the exercicioExe.)
     */
    public ecar.pojo.ExercicioExe getExercicioExe() {
        return exercicioExe;
    }
    /**
     * @author N/C
     * @since N/C
     * @param exercicioExe
     */
    public void setExercicioExe(ecar.pojo.ExercicioExe exercicioExe) {
        this.exercicioExe = exercicioExe;
    }
    
    public String getMesAnoReferencia(){
    	return this.mesAref+"-"+this.anoAref;
    }
    
}
