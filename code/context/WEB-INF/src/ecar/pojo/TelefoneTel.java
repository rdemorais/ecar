package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TelefoneTel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4658116182918882622L;

	/** identifier field */
    private Long codTel;

    /** nullable persistent field */
    private String telefoneTel;

    /** nullable persistent field */
    private String dddTel;

    /** nullable persistent field */
    private String idTel;

    /** persistent field */
    private ecar.pojo.EntidadeEnt entidadeEnt;

    /** full constructor
     * @param telefoneTel
     * @param idTel
     * @param dddTel
     * @param entidadeEnt
     */
    public TelefoneTel(String telefoneTel, String idTel,  String dddTel, EntidadeEnt entidadeEnt) {
        this.telefoneTel = telefoneTel;
        this.idTel = idTel;
        this.dddTel = dddTel;
        this.entidadeEnt = entidadeEnt;
    }

    /** default constructor */
    public TelefoneTel() {
    }

    /** minimal constructor
     * @param entidadeEnt
     */
    public TelefoneTel(ecar.pojo.EntidadeEnt entidadeEnt) {
        this.entidadeEnt = entidadeEnt;
    }

    /**
     *
     * @return
     */
    public Long getCodTel() {
        return this.codTel;
    }

    /**
     *
     * @param codTel
     */
    public void setCodTel(Long codTel) {
        this.codTel = codTel;
    }

    /**
     *
     * @return
     */
    public String getTelefoneTel() {
        return this.telefoneTel;
    }

    /**
     *
     * @param telefoneTel
     */
    public void setTelefoneTel(String telefoneTel) {
        this.telefoneTel = telefoneTel;
    }

    /**
     *
     * @return
     */
    public String getDddTel() {
        return this.dddTel;
    }

    /**
     *
     * @param dddTel
     */
    public void setDddTel(String dddTel) {
        this.dddTel = dddTel;
    }

    /**
     *
     * @return
     */
    public String getIdTel() {
        return this.idTel;
    }

    /**
     *
     * @param idTel
     */
    public void setIdTel(String idTel) {
        this.idTel = idTel;
    }
    
    /**
     *
     * @return
     */
    public ecar.pojo.EntidadeEnt getEntidadeEnt() {
        return this.entidadeEnt;
    }

    /**
     *
     * @param entidadeEnt
     */
    public void setEntidadeEnt(EntidadeEnt entidadeEnt) {
        this.entidadeEnt = entidadeEnt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codTel", getCodTel())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof TelefoneTel) ) return false;
        TelefoneTel castOther = (TelefoneTel) other;
        return new EqualsBuilder()
            .append(this.getDddTel(), castOther.getDddTel())
            .append(this.getTelefoneTel(), castOther.getTelefoneTel())
            .append(this.getEntidadeEnt(), castOther.getEntidadeEnt())           
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getDddTel())
            .append(getTelefoneTel())
            .append(getEntidadeEnt())
            .toHashCode();
    }

}
