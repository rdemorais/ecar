package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class RegApontamentoRegda implements Serializable {
		
    /**
	 * 
	 */
	private static final long serialVersionUID = -2481032929186535170L;
	
	/** Tamanho máximo do apontamento em caracteres */
	public static final int NUMERO_MAXIMO_CARACTERES_APONTAMENTO = 3000; 
	
	/** Tamanho de apontamentos para listagem */
	public static final int NUM_MAX_CARAC_APONT_LISTAGEM = 500;

	/** identifier field */
    private Long codRegda;

    /** nullable persistent field  */
    private String infoRegda;

    /** nullable persistent field  */
    private Date dataRegda;    

    /** persistent field */
    private ecar.pojo.RegDemandaRegd regDemandaRegd;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;
    
    private Set anexos;

    /** full constructor
     * @param regDemandaRegd
     * @param usuarioUsu
     * @param infoRegda
     * @param dataRegda
     */
    public RegApontamentoRegda(ecar.pojo.RegDemandaRegd regDemandaRegd, UsuarioUsu usuarioUsu,
    		String infoRegda, Date dataRegda) {
        this.regDemandaRegd = regDemandaRegd;
        this.usuarioUsu = usuarioUsu;
        this.dataRegda = dataRegda;
        this.infoRegda = infoRegda;
    }

    /**
     *
     * @param regDemandaRegd
     * @param usuarioUsu
     */
    public RegApontamentoRegda(ecar.pojo.RegDemandaRegd regDemandaRegd, UsuarioUsu usuarioUsu) {
        this.regDemandaRegd = regDemandaRegd;
        this.usuarioUsu = usuarioUsu;
    }
    
    /** default constructor */
    public RegApontamentoRegda() {
    }
    
    /**
     *
     * @return
     */
    public Set getAnexos() {
		return anexos;
	}

    /**
     *
     * @param anexos
     */
    public void setAnexos(Set anexos) {
		this.anexos = anexos;
	}

    /**
     *
     * @return
     */
    public Long getCodRegda() {
        return this.codRegda;
    }

    /**
     *
     * @param codRegda
     */
    public void setCodRegda(Long codRegda) {
        this.codRegda = codRegda;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.RegDemandaRegd getRegDemandaRegd() {
        return this.regDemandaRegd;
    }

    /**
     *
     * @param regDemandaRegd
     */
    public void setRegDemandaRegd(ecar.pojo.RegDemandaRegd regDemandaRegd) {
        this.regDemandaRegd = regDemandaRegd;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }

    /**
     *
     * @param usuarioUsu
     */
    public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }
    
    /**
     *
     * @return
     */
    public Date getDataRegda() {
        return this.dataRegda;
    }

    /**
     *
     * @param dataRegda
     */
    public void setDataRegda(Date dataRegda) {
        this.dataRegda = dataRegda;
    }

    /**
     *
     * @return
     */
    public String getInfoRegda() {
        return this.infoRegda;
    }

    /**
     *
     * @param infoRegda
     */
    public void setInfoRegda(String infoRegda) {
        this.infoRegda = infoRegda;
    }    
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codRegda", getCodRegda())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof RegApontamentoRegda) ) return false;
        RegApontamentoRegda castOther = (RegApontamentoRegda) other;
        return new EqualsBuilder()
            .append(this.getCodRegda(), castOther.getCodRegda())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodRegda())
            .toHashCode();
    }

}
