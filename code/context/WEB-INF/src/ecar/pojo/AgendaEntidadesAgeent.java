package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Classe baseada em ItemEstrutLocalIettl
 * @author 05110500460
 *
 */
public class AgendaEntidadesAgeent implements Serializable, PaiFilho {

	private static final long serialVersionUID = 266601912150873947L;
	
    private AgendaEntidadesAgeentPK comp_id;
    private Date dataInclusaoAgeent;
    private AgendaAge agendaAge;
    private EntidadeEnt entidadeEnt;
    private UsuarioUsu usuarioUsuManutencao;

    /**
     *
     * @param comp_id
     * @param dataInclusaoAgeent
     * @param agenda
     * @param entidade
     */
    public AgendaEntidadesAgeent(ecar.pojo.AgendaEntidadesAgeentPK comp_id, Date dataInclusaoAgeent, AgendaAge agenda,  EntidadeEnt entidade) {
        this.comp_id = comp_id;
        this.dataInclusaoAgeent = dataInclusaoAgeent;
        this.agendaAge = agenda;
        this.entidadeEnt = entidade;
    }

    /** default constructor */
    public AgendaEntidadesAgeent() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public AgendaEntidadesAgeent (AgendaEntidadesAgeentPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public AgendaEntidadesAgeentPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(AgendaEntidadesAgeentPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public UsuarioUsu getUsuarioUsuManutencao() {
		return usuarioUsuManutencao;
	}

    /**
     *
     * @param usuarioUsuManutencao
     */
    public void setUsuarioUsuManutencao(UsuarioUsu usuarioUsuManutencao) {
		this.usuarioUsuManutencao = usuarioUsuManutencao;
	}

    /**
     *
     * @return
     */
    public Date getDataInclusaoAgeent() {
		return dataInclusaoAgeent;
	}

    /**
     *
     * @param dataInclusaoAgeent
     */
    public void setDataInclusaoAgeent(Date dataInclusaoAgeent) {
		this.dataInclusaoAgeent = dataInclusaoAgeent;
	}

    /**
     *
     * @return
     */
    public AgendaAge getAgendaAge() {
		return agendaAge;
	}

        /**
         *
         * @param agenda
         */
        public void setAgendaAge(AgendaAge agenda) {
		this.agendaAge = agenda;
	}

        /**
         *
         * @return
         */
        public EntidadeEnt getEntidadeEnt() {
		return entidadeEnt;
	}

        /**
         *
         * @param entidade
         */
        public void setEntidadeEnt(EntidadeEnt entidade) {
		this.entidadeEnt = entidade;
	}

	
    @Override
	public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof AgendaEntidadesAgeent ) ) return false;
        AgendaEntidadesAgeent castOther = (AgendaEntidadesAgeent) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }
    
    /**
     *
     */
    public void atribuirPKPai() {
        comp_id = new AgendaEntidadesAgeentPK();
        comp_id.setCodAge(this.getAgendaAge().getCodAge());
        comp_id.setCodEnt(this.getEntidadeEnt().getCodEnt());
    }

	
}
