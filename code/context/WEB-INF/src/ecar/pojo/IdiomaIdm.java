package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class IdiomaIdm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -181445393114283736L;

	/** identifier field */
    private Long codIdm;

    /** nullable persistent field */
    private String nomeIdm;

    /** persistent field */
    private Set textosSiteTxts;

    /** persistent field */
    private Set paginaPgns;

    /** full constructor
     * @param nomeIdm
     * @param textosSiteTxts
     * @param paginaPgns
     */
    public IdiomaIdm(String nomeIdm, Set textosSiteTxts, Set paginaPgns) {
        this.nomeIdm = nomeIdm;
        this.textosSiteTxts = textosSiteTxts;
        this.paginaPgns = paginaPgns;
    }

    /** default constructor */
    public IdiomaIdm() {
    }

    /** minimal constructor
     * @param textosSiteTxts
     * @param paginaPgns
     */
    public IdiomaIdm(Set textosSiteTxts, Set paginaPgns) {
        this.textosSiteTxts = textosSiteTxts;
        this.paginaPgns = paginaPgns;
    }

    /**
     *
     * @return
     */
    public Long getCodIdm() {
        return this.codIdm;
    }

    /**
     *
     * @param codIdm
     */
    public void setCodIdm(Long codIdm) {
        this.codIdm = codIdm;
    }

    /**
     *
     * @return
     */
    public String getNomeIdm() {
        return this.nomeIdm;
    }

    /**
     *
     * @param nomeIdm
     */
    public void setNomeIdm(String nomeIdm) {
        this.nomeIdm = nomeIdm;
    }

    /**
     *
     * @return
     */
    public Set getTextosSiteTxts() {
        return this.textosSiteTxts;
    }

    /**
     *
     * @param textosSiteTxts
     */
    public void setTextosSiteTxts(Set textosSiteTxts) {
        this.textosSiteTxts = textosSiteTxts;
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
            .append("codIdm", getCodIdm())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof IdiomaIdm) ) return false;
        IdiomaIdm castOther = (IdiomaIdm) other;
        return new EqualsBuilder()
            .append(this.getCodIdm(), castOther.getCodIdm())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIdm())
            .toHashCode();
    }

}
