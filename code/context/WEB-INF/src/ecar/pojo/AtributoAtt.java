package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AtributoAtt implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8248557770553453874L;

	/** identifier field */
    private Long codAtt;

    /** nullable persistent field */
    private String descricaoAtt;

    /** nullable persistent field */
    private Date dataInclusaoAtt;

    /** nullable persistent field */
    private String atribInfCompAtt;

    /** nullable persistent field */
    private String indAtivoAtt;

    /** persistent field */
    private ecar.pojo.GrupoAtributoGatt grupoAtributoGatt;

    /** persistent field */
    private Set entidadeAtributoEntas;

    /** persistent field */
    private Set locAtributoLocas;

    /** persistent field */
    private Set demAtributoDemas;

    /** full constructor.<br>
     * 
     * @author N/C
	 * @since N/C
     * @param descricaoAtt
     * @param dataInclusaoAtt 
     * @param indAtivoAtt
     * @param grupoAtributoGatt
     * @param atribInfCompAtt
     * @param demAtributoDemas
     * @param entidadeAtributoEntas
     * @param locAtributoLocas
     */
    public AtributoAtt(String descricaoAtt, Date dataInclusaoAtt, String atribInfCompAtt, 
    		String indAtivoAtt, ecar.pojo.GrupoAtributoGatt grupoAtributoGatt, Set entidadeAtributoEntas, 
    		Set locAtributoLocas, Set demAtributoDemas) {
        this.descricaoAtt = descricaoAtt;
        this.dataInclusaoAtt = dataInclusaoAtt;
        this.atribInfCompAtt = atribInfCompAtt;
        this.indAtivoAtt = indAtivoAtt;
        this.grupoAtributoGatt = grupoAtributoGatt;
        this.entidadeAtributoEntas = entidadeAtributoEntas;
        this.locAtributoLocas = locAtributoLocas;
        this.demAtributoDemas = demAtributoDemas;
    }

    /** default constructor */
    public AtributoAtt() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @author N/C
	 * @since N/C
     * @param grupoAtributoGatt
     * @param entidadeAtributoEntas
     * @param locAtributoLocas
     * @param demAtributoDemas
     */
    public AtributoAtt(ecar.pojo.GrupoAtributoGatt grupoAtributoGatt, Set entidadeAtributoEntas, 
    		Set locAtributoLocas, Set demAtributoDemas) {
        this.grupoAtributoGatt = grupoAtributoGatt;
        this.entidadeAtributoEntas = entidadeAtributoEntas;
        this.locAtributoLocas = locAtributoLocas;
        this.demAtributoDemas = demAtributoDemas;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return Long
     */
    public Long getCodAtt() {
        return this.codAtt;
    }

    /**
     * @author N/C
	 * @since N/C
     * @param codAtt
     */
    public void setCodAtt(Long codAtt) {
        this.codAtt = codAtt;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return String
     */
    public String getDescricaoAtt() {
        return this.descricaoAtt;
    }

    /**
     * @author N/C
	 * @since N/C
     * @param descricaoAtt
     */
    public void setDescricaoAtt(String descricaoAtt) {
        this.descricaoAtt = descricaoAtt;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return Date
     */
    public Date getDataInclusaoAtt() {
        return this.dataInclusaoAtt;
    }

    /**
     * @param dataInclusaoAtt
     * @author N/C
	 * @since N/C
     */
    public void setDataInclusaoAtt(Date dataInclusaoAtt) {
        this.dataInclusaoAtt = dataInclusaoAtt;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return String
     */
    public String getAtribInfCompAtt() {
        return this.atribInfCompAtt;
    }

    /**
     * @author N/C
	 * @since N/C
     * @param atribInfCompAtt
     */
    public void setAtribInfCompAtt(String atribInfCompAtt) {
        this.atribInfCompAtt = atribInfCompAtt;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return String
     */
    public String getIndAtivoAtt() {
        return this.indAtivoAtt;
    }

    /**
     * @author N/C
	 * @since N/C
     * @param indAtivoAtt
     */
    public void setIndAtivoAtt(String indAtivoAtt) {
        this.indAtivoAtt = indAtivoAtt;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return ecar.pojo.GrupoAtributoGatt
     */
    public ecar.pojo.GrupoAtributoGatt getGrupoAtributoGatt() {
        return this.grupoAtributoGatt;
    }

    /**
     * @param grupoAtributoGatt
     * @author N/C
	 * @since N/C
     */
    public void setGrupoAtributoGatt(ecar.pojo.GrupoAtributoGatt grupoAtributoGatt) {
        this.grupoAtributoGatt = grupoAtributoGatt;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return Set
     */
    public Set getEntidadeAtributoEntas() {
        return this.entidadeAtributoEntas;
    }

    /**
     * @author N/C
	 * @since N/C
     * @param entidadeAtributoEntas
     */
    public void setEntidadeAtributoEntas(Set entidadeAtributoEntas) {
        this.entidadeAtributoEntas = entidadeAtributoEntas;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return Set
     */
    public Set getLocAtributoLocas() {
        return this.locAtributoLocas;
    }

    /**
     * @param locAtributoLocas
     * @author N/C
	 * @since N/C
     */
    public void setLocAtributoLocas(Set locAtributoLocas) {
        this.locAtributoLocas = locAtributoLocas;
    }

    /**
     * @author N/C
	 * @since N/C
     * @return Set
     */
    public Set getDemAtributoDemas() {
        return this.demAtributoDemas;
    }

    /**
     * @param demAtributoDemas
     * @author N/C
	 * @since N/C
     */
    public void setDemAtributoDemas(Set demAtributoDemas) {
        this.demAtributoDemas = demAtributoDemas;
    }

    /**
     * @author N/C
	 * @since N/C
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codAtt", getCodAtt())
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
        if ( !(other instanceof AtributoAtt) ) return false;
        AtributoAtt castOther = (AtributoAtt) other;
        return new EqualsBuilder()
            .append(this.getCodAtt(), castOther.getCodAtt())
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
            .append(getCodAtt())
            .toHashCode();
    }

}
