package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoItemLeiauteSgtil implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7887255926766204399L;

	/** identifier field */
    private Long codSgtil;

    /** nullable persistent field */
    private String linkSgtil;

    /** nullable persistent field */
    private String descricaoSgtil;

    /** persistent field */
    private Set segmentoItemSgtis;

    /** full constructor
     * @param linkSgtil
     * @param descricaoSgtil
     * @param segmentoItemSgtis
     */
    public SegmentoItemLeiauteSgtil(String linkSgtil, String descricaoSgtil, Set segmentoItemSgtis) {
        this.linkSgtil = linkSgtil;
        this.descricaoSgtil = descricaoSgtil;
        this.segmentoItemSgtis = segmentoItemSgtis;
    }

    /** default constructor */
    public SegmentoItemLeiauteSgtil() {
    }

    /** minimal constructor
     * @param segmentoItemSgtis
     */
    public SegmentoItemLeiauteSgtil(Set segmentoItemSgtis) {
        this.segmentoItemSgtis = segmentoItemSgtis;
    }

    /**
     *
     * @return
     */
    public Long getCodSgtil() {
        return this.codSgtil;
    }

    /**
     *
     * @param codSgtil
     */
    public void setCodSgtil(Long codSgtil) {
        this.codSgtil = codSgtil;
    }

    /**
     *
     * @return
     */
    public String getLinkSgtil() {
        return this.linkSgtil;
    }

    /**
     *
     * @param linkSgtil
     */
    public void setLinkSgtil(String linkSgtil) {
        this.linkSgtil = linkSgtil;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSgtil() {
        return this.descricaoSgtil;
    }

    /**
     *
     * @param descricaoSgtil
     */
    public void setDescricaoSgtil(String descricaoSgtil) {
        this.descricaoSgtil = descricaoSgtil;
    }

    /**
     *
     * @return
     */
    public Set getSegmentoItemSgtis() {
        return this.segmentoItemSgtis;
    }

    /**
     *
     * @param segmentoItemSgtis
     */
    public void setSegmentoItemSgtis(Set segmentoItemSgtis) {
        this.segmentoItemSgtis = segmentoItemSgtis;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codSgtil", getCodSgtil())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SegmentoItemLeiauteSgtil) ) return false;
        SegmentoItemLeiauteSgtil castOther = (SegmentoItemLeiauteSgtil) other;
        return new EqualsBuilder()
            .append(this.getCodSgtil(), castOther.getCodSgtil())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSgtil())
            .toHashCode();
    }

}
