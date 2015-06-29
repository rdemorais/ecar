package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class OpcaoOpc implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8731799363395960133L;

	/** identifier field */
    private Long codOpcao;

    /** nullable persistent field */
    private String descricaoOpc;

    /** nullable persistent field */
    private Integer nivelOpc;

    /** nullable persistent field */
    private String programaOpc;

    /** nullable persistent field */
    private String indSiteOpc;

    /** nullable persistent field */
    private String indAdminOpc;

    /** persistent field */
    private Set opcaoMenuOpcmsByCodOpcPai;

    /** persistent field */
    private Set opcaoMenuOpcmsByCodOpcFilho;

    /** persistent field */
    private Set opcaoPerfilOpcps;

    /** full constructor
     * @param descricaoOpc
     * @param nivelOpc
     * @param programaOpc
     * @param indAdminOpc
     * @param opcaoMenuOpcmsByCodOpcPai
     * @param indSiteOpc
     * @param opcaoMenuOpcmsByCodOpcFilho
     * @param opcaoPerfilOpcps
     */
    public OpcaoOpc(String descricaoOpc, Integer nivelOpc, String programaOpc, String indSiteOpc, String indAdminOpc, Set opcaoMenuOpcmsByCodOpcPai, Set opcaoMenuOpcmsByCodOpcFilho, Set opcaoPerfilOpcps) {
        this.descricaoOpc = descricaoOpc;
        this.nivelOpc = nivelOpc;
        this.programaOpc = programaOpc;
        this.indSiteOpc = indSiteOpc;
        this.indAdminOpc = indAdminOpc;
        this.opcaoMenuOpcmsByCodOpcPai = opcaoMenuOpcmsByCodOpcPai;
        this.opcaoMenuOpcmsByCodOpcFilho = opcaoMenuOpcmsByCodOpcFilho;
        this.opcaoPerfilOpcps = opcaoPerfilOpcps;
    }

    /** default constructor */
    public OpcaoOpc() {
    }

    /** minimal constructor
     * @param opcaoMenuOpcmsByCodOpcPai
     * @param opcaoMenuOpcmsByCodOpcFilho
     * @param opcaoPerfilOpcps
     */
    public OpcaoOpc(Set opcaoMenuOpcmsByCodOpcPai, Set opcaoMenuOpcmsByCodOpcFilho, Set opcaoPerfilOpcps) {
        this.opcaoMenuOpcmsByCodOpcPai = opcaoMenuOpcmsByCodOpcPai;
        this.opcaoMenuOpcmsByCodOpcFilho = opcaoMenuOpcmsByCodOpcFilho;
        this.opcaoPerfilOpcps = opcaoPerfilOpcps;
    }

    /**
     *
     * @return
     */
    public Long getCodOpcao() {
        return this.codOpcao;
    }

    /**
     *
     * @param codOpcao
     */
    public void setCodOpcao(Long codOpcao) {
        this.codOpcao = codOpcao;
    }

    /**
     *
     * @return
     */
    public String getDescricaoOpc() {
        return this.descricaoOpc;
    }

    /**
     *
     * @param descricaoOpc
     */
    public void setDescricaoOpc(String descricaoOpc) {
        this.descricaoOpc = descricaoOpc;
    }

    /**
     *
     * @return
     */
    public Integer getNivelOpc() {
        return this.nivelOpc;
    }

    /**
     *
     * @param nivelOpc
     */
    public void setNivelOpc(Integer nivelOpc) {
        this.nivelOpc = nivelOpc;
    }

    /**
     *
     * @return
     */
    public String getProgramaOpc() {
        return this.programaOpc;
    }

    /**
     *
     * @param programaOpc
     */
    public void setProgramaOpc(String programaOpc) {
        this.programaOpc = programaOpc;
    }

    /**
     *
     * @return
     */
    public String getIndSiteOpc() {
        return this.indSiteOpc;
    }

    /**
     *
     * @param indSiteOpc
     */
    public void setIndSiteOpc(String indSiteOpc) {
        this.indSiteOpc = indSiteOpc;
    }

    /**
     *
     * @return
     */
    public String getIndAdminOpc() {
        return this.indAdminOpc;
    }

    /**
     *
     * @param indAdminOpc
     */
    public void setIndAdminOpc(String indAdminOpc) {
        this.indAdminOpc = indAdminOpc;
    }

    /**
     *
     * @return
     */
    public Set getOpcaoMenuOpcmsByCodOpcPai() {
        return this.opcaoMenuOpcmsByCodOpcPai;
    }

    /**
     *
     * @param opcaoMenuOpcmsByCodOpcPai
     */
    public void setOpcaoMenuOpcmsByCodOpcPai(Set opcaoMenuOpcmsByCodOpcPai) {
        this.opcaoMenuOpcmsByCodOpcPai = opcaoMenuOpcmsByCodOpcPai;
    }

    /**
     *
     * @return
     */
    public Set getOpcaoMenuOpcmsByCodOpcFilho() {
        return this.opcaoMenuOpcmsByCodOpcFilho;
    }

    /**
     *
     * @param opcaoMenuOpcmsByCodOpcFilho
     */
    public void setOpcaoMenuOpcmsByCodOpcFilho(Set opcaoMenuOpcmsByCodOpcFilho) {
        this.opcaoMenuOpcmsByCodOpcFilho = opcaoMenuOpcmsByCodOpcFilho;
    }

    /**
     *
     * @return
     */
    public Set getOpcaoPerfilOpcps() {
        return this.opcaoPerfilOpcps;
    }

    /**
     *
     * @param opcaoPerfilOpcps
     */
    public void setOpcaoPerfilOpcps(Set opcaoPerfilOpcps) {
        this.opcaoPerfilOpcps = opcaoPerfilOpcps;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codOpcao", getCodOpcao())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof OpcaoOpc) ) return false;
        OpcaoOpc castOther = (OpcaoOpc) other;
        return new EqualsBuilder()
            .append(this.getCodOpcao(), castOther.getCodOpcao())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodOpcao())
            .toHashCode();
    }

}
