package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SegmentoItemFonteSgtif implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1910137873536009552L;

	/** identifier field */
    private Long codSgtif;

    /** nullable persistent field */
    private String descricaoSgtif;

    /** persistent field */
    private Set segmentoItemSgtis;

    /** full constructor
     * @param descricaoSgtif
     * @param segmentoItemSgtis
     */
    public SegmentoItemFonteSgtif(String descricaoSgtif, Set segmentoItemSgtis) {
        this.descricaoSgtif = descricaoSgtif;
        this.segmentoItemSgtis = segmentoItemSgtis;
    }

    /** default constructor */
    public SegmentoItemFonteSgtif() {
    }

    /** minimal constructor
     * @param segmentoItemSgtis
     */
    public SegmentoItemFonteSgtif(Set segmentoItemSgtis) {
        this.segmentoItemSgtis = segmentoItemSgtis;
    }

    /**
     *
     * @return
     */
    public Long getCodSgtif() {
        return this.codSgtif;
    }

    /**
     *
     * @param codSgtif
     */
    public void setCodSgtif(Long codSgtif) {
        this.codSgtif = codSgtif;
    }

    /**
     *
     * @return
     */
    public String getDescricaoSgtif() {
        return this.descricaoSgtif;
    }

    /**
     *
     * @param descricaoSgtif
     */
    public void setDescricaoSgtif(String descricaoSgtif) {
        this.descricaoSgtif = descricaoSgtif;
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
            .append("codSgtif", getCodSgtif())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SegmentoItemFonteSgtif) ) return false;
        SegmentoItemFonteSgtif castOther = (SegmentoItemFonteSgtif) other;
        return new EqualsBuilder()
            .append(this.getCodSgtif(), castOther.getCodSgtif())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodSgtif())
            .toHashCode();
    }

}
