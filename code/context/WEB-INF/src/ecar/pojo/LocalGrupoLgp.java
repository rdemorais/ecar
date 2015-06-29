package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class LocalGrupoLgp implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8236909808790616303L;

	/** identifier field */
    private Long codLgp;

    /** nullable persistent field */
    private String indAtivoLgp;

    /** nullable persistent field */
    private Date dataInclusaoLgp;

    /** nullable persistent field */
    private String identificacaoLgp;
    
    /** nullable persistent field */
    private String siglaLgp;

    /** persistent field */
    private Set localItemLits;

    /** persistent field */
    private Set localGrupoHierarquiaLgphsByCodLgp;

    /** persistent field */
    private Set localGrupoHierarquiaLgphsByCodLgpPai;

    /** full constructor
     * @param indAtivoLgp
     * @param localItemLits
     * @param identificacaoLgp
     * @param dataInclusaoLgp
     * @param siglaLgp
     * @param localGrupoHierarquiaLgphsByCodLgpPai
     * @param localGrupoHierarquiaLgphsByCodLgp
     */
    public LocalGrupoLgp(String indAtivoLgp, Date dataInclusaoLgp, String identificacaoLgp, Set localItemLits, Set localGrupoHierarquiaLgphsByCodLgp, Set localGrupoHierarquiaLgphsByCodLgpPai,String siglaLgp) {
        this.indAtivoLgp = indAtivoLgp;
        this.dataInclusaoLgp = dataInclusaoLgp;
        this.identificacaoLgp = identificacaoLgp;
        this.localItemLits = localItemLits;
        this.localGrupoHierarquiaLgphsByCodLgp = localGrupoHierarquiaLgphsByCodLgp;
        this.localGrupoHierarquiaLgphsByCodLgpPai = localGrupoHierarquiaLgphsByCodLgpPai;
        this.siglaLgp = siglaLgp;
    }

    /** default constructor */
    public LocalGrupoLgp() {
    }

    /** minimal constructor
     * @param localItemLits
     * @param localGrupoHierarquiaLgphsByCodLgp
     * @param localGrupoHierarquiaLgphsByCodLgpPai
     */
    public LocalGrupoLgp(Set localItemLits, Set localGrupoHierarquiaLgphsByCodLgp, Set localGrupoHierarquiaLgphsByCodLgpPai) {
        this.localItemLits = localItemLits;
        this.localGrupoHierarquiaLgphsByCodLgp = localGrupoHierarquiaLgphsByCodLgp;
        this.localGrupoHierarquiaLgphsByCodLgpPai = localGrupoHierarquiaLgphsByCodLgpPai;
    }

    /**
     *
     * @return
     */
    public Long getCodLgp() {
        return this.codLgp;
    }

    /**
     *
     * @param codLgp
     */
    public void setCodLgp(Long codLgp) {
        this.codLgp = codLgp;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoLgp() {
        return this.indAtivoLgp;
    }

    /**
     *
     * @param indAtivoLgp
     */
    public void setIndAtivoLgp(String indAtivoLgp) {
        this.indAtivoLgp = indAtivoLgp;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoLgp() {
        return this.dataInclusaoLgp;
    }

    /**
     *
     * @param dataInclusaoLgp
     */
    public void setDataInclusaoLgp(Date dataInclusaoLgp) {
        this.dataInclusaoLgp = dataInclusaoLgp;
    }

    /**
     *
     * @return
     */
    public String getIdentificacaoLgp() {
        return this.identificacaoLgp;
    }

    /**
     *
     * @param identificacaoLgp
     */
    public void setIdentificacaoLgp(String identificacaoLgp) {
        this.identificacaoLgp = identificacaoLgp;
    }
    
    /**
     *
     * @return
     */
    public String getSiglaLgp(){
    	return this.siglaLgp;
    }
    
    /**
     *
     * @param siglaLgp
     */
    public void setSiglaLgp(String siglaLgp){
    	this.siglaLgp = siglaLgp;
    }

    /**
     *
     * @return
     */
    public Set getLocalItemLits() {
        return this.localItemLits;
    }

    /**
     *
     * @param localItemLits
     */
    public void setLocalItemLits(Set localItemLits) {
        this.localItemLits = localItemLits;
    }

    /**
     *
     * @return
     */
    public Set getLocalGrupoHierarquiaLgphsByCodLgp() {
        return this.localGrupoHierarquiaLgphsByCodLgp;
    }

    /**
     *
     * @param localGrupoHierarquiaLgphsByCodLgp
     */
    public void setLocalGrupoHierarquiaLgphsByCodLgp(Set localGrupoHierarquiaLgphsByCodLgp) {
        this.localGrupoHierarquiaLgphsByCodLgp = localGrupoHierarquiaLgphsByCodLgp;
    }

    /**
     *
     * @return
     */
    public Set getLocalGrupoHierarquiaLgphsByCodLgpPai() {
        return this.localGrupoHierarquiaLgphsByCodLgpPai;
    }

    /**
     *
     * @param localGrupoHierarquiaLgphsByCodLgpPai
     */
    public void setLocalGrupoHierarquiaLgphsByCodLgpPai(Set localGrupoHierarquiaLgphsByCodLgpPai) {
        this.localGrupoHierarquiaLgphsByCodLgpPai = localGrupoHierarquiaLgphsByCodLgpPai;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codLgp", getCodLgp())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof LocalGrupoLgp) ) return false;
        LocalGrupoLgp castOther = (LocalGrupoLgp) other;
        return new EqualsBuilder()
            .append(this.getCodLgp(), castOther.getCodLgp())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodLgp())
            .toHashCode();
    }

}
