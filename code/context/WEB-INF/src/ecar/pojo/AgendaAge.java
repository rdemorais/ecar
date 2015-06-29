package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class AgendaAge implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 204551417827244883L;

	
    private Long codAge;
    private String descricaoAge;
    private Integer minutoEventoAge;
    private String indAtivoAge;
    private Integer horaEventoAge;
    private Date dataAge;
    private Date dataLimiteAge;
    private String localAge;
    private String eventoAge;

    private ecar.pojo.UsuarioUsu usuarioUsu;
    private Set destaqueItemRelDtqirs;
    private Set agendaOcorrenciaAgeos;
    
    private String nomeContato;
    private String telefoneContato ;
    private String orgaoContato;
    private String comentario ;
    private String realizado ;
    private String exibirPortal;
    private Date   dataRealizado;
    private Set agendaEntidadesAgeent;
    private ItemEstruturaIett itemEstruturaIett;
    private SisAtributoSatb tipoEventoSatb;
    

    /** 
     * full constructor
     * 
     * @param descricaoAge
     * @param minutoEventoAge
     * @param indAtivoAge
     * @param dataAge 
     * @param dataLimiteAge 
     * @param horaEventoAge
     * @param eventoAge
     * @param localAge
     * @param tipoEventoSatb
     * @param usuarioUsu
     * @param agendaOcorrenciaAgeos
     * @param destaqueItemRelDtqirs
     */
    public AgendaAge(String descricaoAge, Integer minutoEventoAge, String indAtivoAge, 
    		Integer horaEventoAge, Date dataAge, Date dataLimiteAge, String localAge, String eventoAge, 
    		ecar.pojo.UsuarioUsu usuarioUsu, Set destaqueItemRelDtqirs, Set agendaOcorrenciaAgeos, SisAtributoSatb tipoEventoSatb) {
        this.descricaoAge = descricaoAge;
        this.minutoEventoAge = minutoEventoAge;
        this.indAtivoAge = indAtivoAge;
        this.horaEventoAge = horaEventoAge;
        this.dataAge = dataAge;
        this.dataLimiteAge = dataLimiteAge;
        this.localAge = localAge;
        this.eventoAge = eventoAge;
        this.usuarioUsu = usuarioUsu;
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
        this.agendaOcorrenciaAgeos = agendaOcorrenciaAgeos;
        
    }

    /**
     *
     * @return
     */
    public String getNomeContato() {
		return nomeContato;
	}

    /**
     *
     * @param nomeContato
     */
    public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}

        /**
         *
         * @return
         */
        public String getTelefoneContato() {
		return telefoneContato;
	}

        /**
         *
         * @param telefoneContato
         */
        public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato = telefoneContato;
	}

        /**
         *
         * @return
         */
        public String getOrgaoContato() {
		return orgaoContato;
	}

        /**
         *
         * @param orgaoContato
         */
        public void setOrgaoContato(String orgaoContato) {
		this.orgaoContato = orgaoContato;
	}

        /**
         *
         * @return
         */
        public String getComentario() {
		return comentario;
	}

        /**
         *
         * @param comentario
         */
        public void setComentario(String comentario) {
		this.comentario = comentario;
	}

        /**
         *
         * @return
         */
        public String getRealizado() {
		return realizado;
	}

        /**
         *
         * @param realizado
         */
        public void setRealizado(String realizado) {
		this.realizado = realizado;
	}

        /**
         *
         * @return
         */
        public String getExibirPortal() {
		return exibirPortal;
	}

        /**
         *
         * @param exibirPortal
         */
        public void setExibirPortal(String exibirPortal) {
		this.exibirPortal = exibirPortal;
	}

        /**
         *
         * @return
         */
        public Date getDataRealizado() {
		return dataRealizado;
	}

        /**
         *
         * @param dataRealizado
         */
        public void setDataRealizado(Date dataRealizado) {
		this.dataRealizado = dataRealizado;
	}

	/** default constructor */
    public AgendaAge() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @param usuarioUsu
     * @param destaqueItemRelDtqirs
     * @param agendaOcorrenciaAgeos
     */
    public AgendaAge(ecar.pojo.UsuarioUsu usuarioUsu, Set destaqueItemRelDtqirs, Set agendaOcorrenciaAgeos) {
        this.usuarioUsu = usuarioUsu;
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
        this.agendaOcorrenciaAgeos = agendaOcorrenciaAgeos;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
    public Long getCodAge() {
        return this.codAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @param codAge
     */
    public void setCodAge(Long codAge) {
        this.codAge = codAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getDescricaoAge() {
        return this.descricaoAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @param descricaoAge
     */
    public void setDescricaoAge(String descricaoAge) {
        this.descricaoAge = descricaoAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Integer
     */
    public Integer getMinutoEventoAge() {
        return this.minutoEventoAge;
    }

    /**
     * @param minutoEventoAge
     * @author N/C
     * @since N/C
     */
    public void setMinutoEventoAge(Integer minutoEventoAge) {
        this.minutoEventoAge = minutoEventoAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getIndAtivoAge() {
        return this.indAtivoAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @param indAtivoAge
     */
    public void setIndAtivoAge(String indAtivoAge) {
        this.indAtivoAge = indAtivoAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Integer
     */
    public Integer getHoraEventoAge() {
        return this.horaEventoAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @param horaEventoAge
     */
    public void setHoraEventoAge(Integer horaEventoAge) {
        this.horaEventoAge = horaEventoAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataAge() {
        return this.dataAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @param dataAge
     */
    public void setDataAge(Date dataAge) {
        this.dataAge = dataAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataLimiteAge() {
        return this.dataLimiteAge;
    }

    /**
     * @param dataLimiteAge
     * @author N/C
     * @since N/C
     */
    public void setDataLimiteAge(Date dataLimiteAge) {
        this.dataLimiteAge = dataLimiteAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getLocalAge() {
        return this.localAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @param localAge
     */
    public void setLocalAge(String localAge) {
        this.localAge = localAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getEventoAge() {
        return this.eventoAge;
    }

    /**
     * @param eventoAge
     * @author N/C
     * @since N/C
     */
    public void setEventoAge(String eventoAge) {
        this.eventoAge = eventoAge;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.UsuarioUsu
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }

    /**
     * @author N/C
     * @since N/C
     * @param usuarioUsu
     */
    public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
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
     * @author N/C
     * @since N/C
     * @param destaqueItemRelDtqirs
     */
    public void setDestaqueItemRelDtqirs(Set destaqueItemRelDtqirs) {
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Set
     */
    public Set getAgendaOcorrenciaAgeos() {
        return this.agendaOcorrenciaAgeos;
    }

    /**
     * @param agendaOcorrenciaAgeos
     * @author N/C
     * @since N/C
     */
    public void setAgendaOcorrenciaAgeos(Set agendaOcorrenciaAgeos) {
        this.agendaOcorrenciaAgeos = agendaOcorrenciaAgeos;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codAge", getCodAge())
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
        if ( !(other instanceof AgendaAge) ) return false;
        AgendaAge castOther = (AgendaAge) other;
        return new EqualsBuilder()
            .append(this.getCodAge(), castOther.getCodAge())
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
            .append(getCodAge())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Set getAgendaEntidadesAgeent() {
		return agendaEntidadesAgeent;
	}

        /**
         *
         * @param agendaEntidadesAgeent
         */
        public void setAgendaEntidadesAgeent(Set agendaEntidadesAgeent) {
		this.agendaEntidadesAgeent = agendaEntidadesAgeent;
	}

        /**
         *
         * @return
         */
        public ItemEstruturaIett getItemEstruturaIett() {
		return itemEstruturaIett;
	}

        /**
         *
         * @param itemEstruturaIett
         */
        public void setItemEstruturaIett(ItemEstruturaIett itemEstruturaIett) {
		this.itemEstruturaIett = itemEstruturaIett;
	}

        /**
         *
         * @return
         */
        public SisAtributoSatb getTipoEventoSatb() {
		return tipoEventoSatb;
	}

        /**
         *
         * @param tipoEventoSatb
         */
        public void setTipoEventoSatb(SisAtributoSatb tipoEventoSatb) {
		this.tipoEventoSatb = tipoEventoSatb;
	}


}
