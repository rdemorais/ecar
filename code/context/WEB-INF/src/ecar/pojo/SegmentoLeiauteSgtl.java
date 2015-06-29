package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoLeiauteSgtl implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7047020395052572937L;

	/** identifier field */
    private Long codSgtl;

    /** nullable persistent field */
    private String linkSgtl;

    /** nullable persistent field */
    private String descricaoSgtl;

    /** persistent field */
    private Set segmentoSgts;

    /** full constructor
     * @param linkSgtl
     * @param segmentoSgts
     * @param descricaoSgtl
     */
    public SegmentoLeiauteSgtl(String linkSgtl, String descricaoSgtl, Set segmentoSgts) {
        this.linkSgtl = linkSgtl;
        this.descricaoSgtl = descricaoSgtl;
        this.segmentoSgts = segmentoSgts;
    }

    /** default constructor */
    public SegmentoLeiauteSgtl() {
    }

    /** minimal constructor
     * @param segmentoSgts
     */
    public SegmentoLeiauteSgtl(Set segmentoSgts) {
        this.segmentoSgts = segmentoSgts;
    }

    /**
     *
     * @return
     */
    public Long getCodSgtl() {
        return this.codSgtl;
    }

    /**
     *
     * @param codSgtl
     */
    public void setCodSgtl(Long codSgtl) {
        this.codSgtl = codSgtl;
    }

    /**
     *
     * @return
     */
    public String getLinkSgtl() {
        return this.linkSgtl;
    }

    /**
     *
     * @param linkSgtl
     */
    public void setLinkSgtl(String linkSgtl) {
        this.linkSgtl = linkSgtl;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSgtl() {
        return this.descricaoSgtl;
    }

    /**
     *
     * @param descricaoSgtl
     */
    public void setDescricaoSgtl(String descricaoSgtl) {
        this.descricaoSgtl = descricaoSgtl;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoSgts() {
        return this.segmentoSgts;
    }

    /**
     *
     * @param segmentoSgts
     */
    public void setSegmentoSgts(Set segmentoSgts) {
        this.segmentoSgts = segmentoSgts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSgtl", getCodSgtl())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SegmentoLeiauteSgtl) ) return false;
        SegmentoLeiauteSgtl castOther = (SegmentoLeiauteSgtl) other;
        return new EqualsBuilder()
            .append(this.getCodSgtl(), castOther.getCodSgtl())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSgtl())
            .toHashCode();
    }

}
