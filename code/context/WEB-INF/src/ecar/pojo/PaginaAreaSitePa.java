package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PaginaAreaSitePa implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1261171791396300382L;

	/** identifier field */
    private Long codPas;

    /** nullable persistent field */
    private Integer seqApresentacaoPas;

    /** nullable persistent field */
    private String corPas;

    /** nullable persistent field */
    private String urlPas;

    /** nullable persistent field */
    private String indCapaPas;

    /** nullable persistent field */
    private String textoPas;

    /** nullable persistent field */
    private String nomePas;

    /** persistent field */
    private Set opcaoMenuOpcms;

    /** persistent field */
    private Set paginaPgns;

    /** nullable persistent field */
    private String tituloPas;

    /** full constructor
     * @param seqApresentacaoPas
     * @param corPas
     * @param indCapaPas
     * @param urlPas
     * @param textoPas
     * @param paginaPgns
     * @param nomePas
     * @param opcaoMenuOpcms
     * @param tituloPas
     */
    public PaginaAreaSitePa(Integer seqApresentacaoPas, String corPas, String urlPas, String indCapaPas, String textoPas, String nomePas, Set opcaoMenuOpcms, Set paginaPgns, String tituloPas) {
        this.seqApresentacaoPas = seqApresentacaoPas;
        this.corPas = corPas;
        this.urlPas = urlPas;
        this.indCapaPas = indCapaPas;
        this.textoPas = textoPas;
        this.nomePas = nomePas;
        this.opcaoMenuOpcms = opcaoMenuOpcms;
        this.paginaPgns = paginaPgns;
        this.tituloPas = tituloPas;
    }

    /** default constructor */
    public PaginaAreaSitePa() {
    }

    /** minimal constructor
     * @param opcaoMenuOpcms
     * @param paginaPgns
     */
    public PaginaAreaSitePa(Set opcaoMenuOpcms, Set paginaPgns) {
        this.opcaoMenuOpcms = opcaoMenuOpcms;
        this.paginaPgns = paginaPgns;
    }

    /**
     *
     * @return
     */
    public Long getCodPas() {
        return this.codPas;
    }

    /**
     *
     * @param codPas
     */
    public void setCodPas(Long codPas) {
        this.codPas = codPas;
    }

    /**
     *
     * @return
     */
    public Integer getSeqApresentacaoPas() {
        return this.seqApresentacaoPas;
    }

    /**
     *
     * @param seqApresentacaoPas
     */
    public void setSeqApresentacaoPas(Integer seqApresentacaoPas) {
        this.seqApresentacaoPas = seqApresentacaoPas;
    }

    /**
     *
     * @return
     */
    public String getCorPas() {
        return this.corPas;
    }

    /**
     *
     * @param corPas
     */
    public void setCorPas(String corPas) {
        this.corPas = corPas;
    }

    /**
     *
     * @return
     */
    public String getUrlPas() {
        return this.urlPas;
    }

    /**
     *
     * @param urlPas
     */
    public void setUrlPas(String urlPas) {
        this.urlPas = urlPas;
    }

    /**
     *
     * @return
     */
    public String getIndCapaPas() {
        return this.indCapaPas;
    }

    /**
     *
     * @param indCapaPas
     */
    public void setIndCapaPas(String indCapaPas) {
        this.indCapaPas = indCapaPas;
    }

    /**
     *
     * @return
     */
    public String getTextoPas() {
        return this.textoPas;
    }

    /**
     *
     * @param textoPas
     */
    public void setTextoPas(String textoPas) {
        this.textoPas = textoPas;
    }

    /**
     *
     * @return
     */
    public String getNomePas() {
        return this.nomePas;
    }

    /**
     *
     * @param nomePas
     */
    public void setNomePas(String nomePas) {
        this.nomePas = nomePas;
    }

    /**
     *
     * @return
     */
    public Set getOpcaoMenuOpcms() {
        return this.opcaoMenuOpcms;
    }

    /**
     *
     * @param opcaoMenuOpcms
     */
    public void setOpcaoMenuOpcms(Set opcaoMenuOpcms) {
        this.opcaoMenuOpcms = opcaoMenuOpcms;
    }

    /**
     *
     * @return
     */
    public Set getPaginaPgns() {
        return this.paginaPgns;
    }

    /**
     *
     * @param paginaPgns
     */
    public void setPaginaPgns(Set paginaPgns) {
        this.paginaPgns = paginaPgns;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codPas", getCodPas())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PaginaAreaSitePa) ) return false;
        PaginaAreaSitePa castOther = (PaginaAreaSitePa) other;
        return new EqualsBuilder()
            .append(this.getCodPas(), castOther.getCodPas())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodPas())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public String getTituloPas() {
		return tituloPas;
	}

        /**
         *
         * @param tituloPas
         */
        public void setTituloPas(String tituloPas) {
		this.tituloPas = tituloPas;
	}

}
