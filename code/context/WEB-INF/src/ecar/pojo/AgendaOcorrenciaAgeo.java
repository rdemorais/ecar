package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class AgendaOcorrenciaAgeo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7183712145764938228L;

	/** identifier field */
    private Long codAgeo;

    /** nullable persistent field */
    private String localAgeo;
    
    /** nullable persistent field */
    private String descricaoAgeo;

    /** nullable persistent field */
    private Integer minutoEventoAgeo;

    /** nullable persistent field */
    private Integer horaEventoAgeo;

    /** nullable persistent field */
    private Date dataEventoAgeo;

    /** persistent field */
    private ecar.pojo.AgendaAge agendaAge;

    /** persistent field */
    private Set itemEstrutVinculoIettvs;

    /** persistent field */
    private Set destaqueItemRelDtqirs;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param localAgeo 
     * @param descricaoAgeo
     * @param minutoEventoAgeo 
     * @param agendaAge
     * @param horaEventoAgeo
     * @param dataEventoAgeo
     * @param itemEstrutVinculoIettvs
     * @param destaqueItemRelDtqirs
     */
    public AgendaOcorrenciaAgeo(String localAgeo, String descricaoAgeo, Integer minutoEventoAgeo, 
    		Integer horaEventoAgeo, Date dataEventoAgeo, ecar.pojo.AgendaAge agendaAge, Set itemEstrutVinculoIettvs, 
    		Set destaqueItemRelDtqirs) {
        this.localAgeo = localAgeo;
    	this.descricaoAgeo = descricaoAgeo;
        this.minutoEventoAgeo = minutoEventoAgeo;
        this.horaEventoAgeo = horaEventoAgeo;
        this.dataEventoAgeo = dataEventoAgeo;
        this.agendaAge = agendaAge;
        this.itemEstrutVinculoIettvs = itemEstrutVinculoIettvs;
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
    }

    /** default constructor */
    public AgendaOcorrenciaAgeo() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param agendaAge
     * @param itemEstrutVinculoIettvs
     * @param destaqueItemRelDtqirs
     */
    public AgendaOcorrenciaAgeo(ecar.pojo.AgendaAge agendaAge, Set itemEstrutVinculoIettvs, Set destaqueItemRelDtqirs) {
        this.agendaAge = agendaAge;
        this.itemEstrutVinculoIettvs = itemEstrutVinculoIettvs;
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
    public Long getCodAgeo() {
        return this.codAgeo;
    } 

    /**
     * @author N/C
     * @since N/C
     * @param codAgeo
     */
    public void setCodAgeo(Long codAgeo) {
        this.codAgeo = codAgeo;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getDescricaoAgeo() {
        return this.descricaoAgeo;
    }

    /**
     * @author N/C
     * @since N/C
     * @param descricaoAgeo
     */
    public void setDescricaoAgeo(String descricaoAgeo) {
        this.descricaoAgeo = descricaoAgeo;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Integer
     */
    public Integer getMinutoEventoAgeo() {
        return this.minutoEventoAgeo;
    }

    /**
     * @author N/C
     * @since N/C
     * @param minutoEventoAgeo
     */
    public void setMinutoEventoAgeo(Integer minutoEventoAgeo) {
        this.minutoEventoAgeo = minutoEventoAgeo;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Integer
     */
    public Integer getHoraEventoAgeo() {
        return this.horaEventoAgeo;
    }

    /**
     * @author N/C
     * @since N/C
     * @param horaEventoAgeo
     */
    public void setHoraEventoAgeo(Integer horaEventoAgeo) {
        this.horaEventoAgeo = horaEventoAgeo;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataEventoAgeo() {
        return this.dataEventoAgeo;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataEventoAgeo
     */
    public void setDataEventoAgeo(Date dataEventoAgeo) {
        this.dataEventoAgeo = dataEventoAgeo;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.AgendaAge
     */
    public ecar.pojo.AgendaAge getAgendaAge() {
        return this.agendaAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @param agendaAge
     */
    public void setAgendaAge(ecar.pojo.AgendaAge agendaAge) {
        this.agendaAge = agendaAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Set
     */
    public Set getItemEstrutVinculoIettvs() {
        return this.itemEstrutVinculoIettvs;
    }

    /**
     * @author N/C
     * @since N/C
     * @param itemEstrutVinculoIettvs
     */
    public void setItemEstrutVinculoIettvs(Set itemEstrutVinculoIettvs) {
        this.itemEstrutVinculoIettvs = itemEstrutVinculoIettvs;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Set
     */
    public Set getDestaqueItemRelDtqirs() {
        return this.destaqueItemRelDtqirs;
    }

    /**
     * @param destaqueItemRelDtqirs
     * @author N/C
     * @since N/C
     */
    public void setDestaqueItemRelDtqirs(Set destaqueItemRelDtqirs) {
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codAgeo", getCodAgeo())
            .toString();
    }

    /**
     * @author N/C
     * @since N/C
     * @param other
     * @return boolean
     * 
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof AgendaOcorrenciaAgeo) ) return false;
        AgendaOcorrenciaAgeo castOther = (AgendaOcorrenciaAgeo) other;
        return new EqualsBuilder()
            .append(this.getCodAgeo(), castOther.getCodAgeo())
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
            .append(getCodAgeo())
            .toHashCode();
    }

	/**
	 * @author N/C
     * @since N/C
	 * @return String - (Returns the localAgeo.)
	 */
	public String getLocalAgeo() {
		return localAgeo;
	}
	/**
	 * @author N/C
     * @since N/C
         * @param localAgeo
	 */
	public void setLocalAgeo(String localAgeo) {
		this.localAgeo = localAgeo;
	}
}
