package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class EstAtribTipoAcompEata implements Serializable {

	private static final long serialVersionUID = -2308045661834756987L;

	/** identifier field */
    private Long codEata;

    /** persistent field */
    private Long sequenciaEata;

    /** persistent field */
    private String exibirEata;

    /** 
     * @author egger - SERPRO 
     * persistent field */
    private String filtroEata;

    /** persistent field */
    private ecar.pojo.TipoAcompanhamentoTa tipoAcompanhamentoTa;

    /** persistent field */
    private ecar.pojo.EstruturaAtributoEttat estruturaAtributoEttat;

    /**
     * @param codEata 
     * @param sequenciaEata 
     * @param filtroEata
     * @param exibirEata
     * @param tipoAcompanhamentoTa
     * @param estruturaAtributoEttat
     * @author egger - SERPRO
     * full constructor */
    public EstAtribTipoAcompEata(Long codEata, Long sequenciaEata, String exibirEata, String filtroEata, ecar.pojo.TipoAcompanhamentoTa tipoAcompanhamentoTa, ecar.pojo.EstruturaAtributoEttat estruturaAtributoEttat) {
        this.codEata = codEata;
        this.sequenciaEata = sequenciaEata;
        this.exibirEata = exibirEata;
        this.filtroEata = filtroEata;
        this.tipoAcompanhamentoTa = tipoAcompanhamentoTa;
        this.estruturaAtributoEttat = estruturaAtributoEttat;
    }

    /** default constructor */
    public EstAtribTipoAcompEata() {
    }

    /**
     *
     * @return
     */
    public Long getCodEata() {
        return this.codEata;
    }

    /**
     *
     * @param codEata
     */
    public void setCodEata(Long codEata) {
        this.codEata = codEata;
    }

    /**
     *
     * @return
     */
    public Long getSequenciaEata() {
        return this.sequenciaEata;
    }

    /**
     *
     * @param sequenciaEata
     */
    public void setSequenciaEata(Long sequenciaEata) {
        this.sequenciaEata = sequenciaEata;
    }

    /**
     *
     * @return
     */
    public String getExibirEata() {
        return this.exibirEata;
    }

    /**
     *
     * @param exibirEata
     */
    public void setExibirEata(String exibirEata) {
        this.exibirEata = exibirEata;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.TipoAcompanhamentoTa getTipoAcompanhamentoTa() {
        return this.tipoAcompanhamentoTa;
    }

    /**
     *
     * @param tipoAcompanhamentoTa
     */
    public void setTipoAcompanhamentoTa(ecar.pojo.TipoAcompanhamentoTa tipoAcompanhamentoTa) {
        this.tipoAcompanhamentoTa = tipoAcompanhamentoTa;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EstruturaAtributoEttat getEstruturaAtributoEttat() {
        return this.estruturaAtributoEttat;
    }

    /**
     *
     * @param estruturaAtributoEttat
     */
    public void setEstruturaAtributoEttat(ecar.pojo.EstruturaAtributoEttat estruturaAtributoEttat) {
        this.estruturaAtributoEttat = estruturaAtributoEttat;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codEata", getCodEata())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof EstAtribTipoAcompEata) ) return false;
        EstAtribTipoAcompEata castOther = (EstAtribTipoAcompEata) other;
        return new EqualsBuilder()
            .append(this.getCodEata(), castOther.getCodEata())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodEata())
            .toHashCode();
    }

	/**
	 * @return the filtroEata
	 */
	public String getFiltroEata() {
		return filtroEata;
	}

	/**
	 * @param filtroEata the filtroEata to set
	 */
	public void setFiltroEata(String filtroEata) {
		this.filtroEata = filtroEata;
	}

}
