package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class OpcaoMenuOpcm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 966790933230426107L;

	/** identifier field */
    private ecar.pojo.OpcaoMenuOpcmPK comp_id;

    /** nullable persistent field */
    private Integer seqApresentacaoOpcm;

    /** nullable persistent field */
    private String indSeparadorOpcm;

    /** nullable persistent field */
    private ecar.pojo.OpcaoOpc opcaoOpcByCodOpcPai;

    /** nullable persistent field */
    private ecar.pojo.OpcaoOpc opcaoOpcByCodOpcFilho;

    /** persistent field */
    private ecar.pojo.PaginaAreaSitePa paginaAreaSitePa;

    /** full constructor
     * @param comp_id
     * @param seqApresentacaoOpcm
     * @param indSeparadorOpcm
     * @param opcaoOpcByCodOpcPai
     * @param paginaAreaSitePa
     * @param opcaoOpcByCodOpcFilho
     */
    public OpcaoMenuOpcm(ecar.pojo.OpcaoMenuOpcmPK comp_id, Integer seqApresentacaoOpcm, String indSeparadorOpcm, ecar.pojo.OpcaoOpc opcaoOpcByCodOpcPai, ecar.pojo.OpcaoOpc opcaoOpcByCodOpcFilho, ecar.pojo.PaginaAreaSitePa paginaAreaSitePa) {
        this.comp_id = comp_id;
        this.seqApresentacaoOpcm = seqApresentacaoOpcm;
        this.indSeparadorOpcm = indSeparadorOpcm;
        this.opcaoOpcByCodOpcPai = opcaoOpcByCodOpcPai;
        this.opcaoOpcByCodOpcFilho = opcaoOpcByCodOpcFilho;
        this.paginaAreaSitePa = paginaAreaSitePa;
    }

    /** default constructor */
    public OpcaoMenuOpcm() {
    }

    /** minimal constructor
     * @param comp_id
     * @param paginaAreaSitePa
     */
    public OpcaoMenuOpcm(ecar.pojo.OpcaoMenuOpcmPK comp_id, ecar.pojo.PaginaAreaSitePa paginaAreaSitePa) {
        this.comp_id = comp_id;
        this.paginaAreaSitePa = paginaAreaSitePa;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.OpcaoMenuOpcmPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.OpcaoMenuOpcmPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public Integer getSeqApresentacaoOpcm() {
        return this.seqApresentacaoOpcm;
    }

    /**
     *
     * @param seqApresentacaoOpcm
     */
    public void setSeqApresentacaoOpcm(Integer seqApresentacaoOpcm) {
        this.seqApresentacaoOpcm = seqApresentacaoOpcm;
    }

    /**
     *
     * @return
     */
    public String getIndSeparadorOpcm() {
        return this.indSeparadorOpcm;
    }

    /**
     *
     * @param indSeparadorOpcm
     */
    public void setIndSeparadorOpcm(String indSeparadorOpcm) {
        this.indSeparadorOpcm = indSeparadorOpcm;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.OpcaoOpc getOpcaoOpcByCodOpcPai() {
        return this.opcaoOpcByCodOpcPai;
    }

    /**
     *
     * @param opcaoOpcByCodOpcPai
     */
    public void setOpcaoOpcByCodOpcPai(ecar.pojo.OpcaoOpc opcaoOpcByCodOpcPai) {
        this.opcaoOpcByCodOpcPai = opcaoOpcByCodOpcPai;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.OpcaoOpc getOpcaoOpcByCodOpcFilho() {
        return this.opcaoOpcByCodOpcFilho;
    }

    /**
     *
     * @param opcaoOpcByCodOpcFilho
     */
    public void setOpcaoOpcByCodOpcFilho(ecar.pojo.OpcaoOpc opcaoOpcByCodOpcFilho) {
        this.opcaoOpcByCodOpcFilho = opcaoOpcByCodOpcFilho;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.PaginaAreaSitePa getPaginaAreaSitePa() {
        return this.paginaAreaSitePa;
    }

    /**
     *
     * @param paginaAreaSitePa
     */
    public void setPaginaAreaSitePa(ecar.pojo.PaginaAreaSitePa paginaAreaSitePa) {
        this.paginaAreaSitePa = paginaAreaSitePa;
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
        if ( !(other instanceof OpcaoMenuOpcm) ) return false;
        OpcaoMenuOpcm castOther = (OpcaoMenuOpcm) other;
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

}
