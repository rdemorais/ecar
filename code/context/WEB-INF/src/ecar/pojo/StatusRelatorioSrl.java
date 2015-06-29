package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** eu
/** @author Hibernate CodeGenerator */
public class StatusRelatorioSrl implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7064051589269854322L;

	/** identifier field */
    private Long codSrl;

    /** nullable persistent field */
    private String indAtivoSrl;

    /** nullable persistent field */
    private String descricaoSrl;

    /** persistent field */
    private Set acompReferenciaItemAris;

    /** full constructor
     * @param indAtivoSrl
     * @param acompReferenciaItemAris
     * @param descricaoSrl
     */
    public StatusRelatorioSrl(String indAtivoSrl, String descricaoSrl, Set acompReferenciaItemAris) {
        this.indAtivoSrl = indAtivoSrl;
        this.descricaoSrl = descricaoSrl;
        this.acompReferenciaItemAris = acompReferenciaItemAris;
    }

    /** default constructor */
    public StatusRelatorioSrl() {
    }

    /** minimal constructor
     * @param acompReferenciaItemAris
     */
    public StatusRelatorioSrl(Set acompReferenciaItemAris) {
        this.acompReferenciaItemAris = acompReferenciaItemAris;
    }

    /**
     *
     * @return
     */
    public Long getCodSrl() {
        return this.codSrl;
    }

    /**
     *
     * @param codSrl
     */
    public void setCodSrl(Long codSrl) {
        this.codSrl = codSrl;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoSrl() {
        return this.indAtivoSrl;
    }

    /**
     *
     * @param indAtivoSrl
     */
    public void setIndAtivoSrl(String indAtivoSrl) {
        this.indAtivoSrl = indAtivoSrl;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSrl() {
        return this.descricaoSrl;
    }

    /**
     *
     * @param descricaoSrl
     */
    public void setDescricaoSrl(String descricaoSrl) {
        this.descricaoSrl = descricaoSrl;
    }

    /**
     *
     * @return
     */
    public Set getAcompReferenciaItemAris() {
        return this.acompReferenciaItemAris;
    }

    /**
     *
     * @param acompReferenciaItemAris
     */
    public void setAcompReferenciaItemAris(Set acompReferenciaItemAris) {
        this.acompReferenciaItemAris = acompReferenciaItemAris;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSrl", getCodSrl())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof StatusRelatorioSrl) ) return false;
        StatusRelatorioSrl castOther = (StatusRelatorioSrl) other;
        return new EqualsBuilder()
            .append(this.getCodSrl(), castOther.getCodSrl())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSrl())
            .toHashCode();
    }

}
