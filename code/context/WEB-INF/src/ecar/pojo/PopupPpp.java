package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PopupPpp implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3355411366972544074L;

	/** identifier field */
    private Long codPpp;

    /** nullable persistent field */
    private String indAtivaPpp;

    /** nullable persistent field */
    private Date dataInclusaoPpp;

    /** nullable persistent field */
    private Date dataFimExibicaoPpp;

    /** nullable persistent field */
    private Date dataIniExibicaoPpp;

    /** nullable persistent field */
    private String indDesativarPpp;

    /** nullable persistent field */
    private Integer janelaLarguraPpp;

    /** nullable persistent field */
    private Integer janelaAlturaPpp;

    /** nullable persistent field */
    private String conteudoPpp;

    /** nullable persistent field */
    private String nomePpp;

    /** persistent field */
    private ecar.pojo.PopupComportamentoPppc popupComportamentoPppc;

    /** full constructor
     * @param indAtivaPpp
     * @param dataInclusaoPpp
     * @param dataFimExibicaoPpp
     * @param conteudoPpp
     * @param dataIniExibicaoPpp
     * @param janelaLarguraPpp
     * @param indDesativarPpp
     * @param janelaAlturaPpp
     * @param nomePpp
     * @param popupComportamentoPppc
     */
    public PopupPpp(String indAtivaPpp, Date dataInclusaoPpp, Date dataFimExibicaoPpp, Date dataIniExibicaoPpp, String indDesativarPpp, Integer janelaLarguraPpp, Integer janelaAlturaPpp, String conteudoPpp, String nomePpp, ecar.pojo.PopupComportamentoPppc popupComportamentoPppc) {
        this.indAtivaPpp = indAtivaPpp;
        this.dataInclusaoPpp = dataInclusaoPpp;
        this.dataFimExibicaoPpp = dataFimExibicaoPpp;
        this.dataIniExibicaoPpp = dataIniExibicaoPpp;
        this.indDesativarPpp = indDesativarPpp;
        this.janelaLarguraPpp = janelaLarguraPpp;
        this.janelaAlturaPpp = janelaAlturaPpp;
        this.conteudoPpp = conteudoPpp;
        this.nomePpp = nomePpp;
        this.popupComportamentoPppc = popupComportamentoPppc;
    }

    /** default constructor */
    public PopupPpp() {
    }

    /** minimal constructor
     * @param popupComportamentoPppc
     */
    public PopupPpp(ecar.pojo.PopupComportamentoPppc popupComportamentoPppc) {
        this.popupComportamentoPppc = popupComportamentoPppc;
    }

    /**
     *
     * @return
     */
    public Long getCodPpp() {
        return this.codPpp;
    }

    /**
     *
     * @param codPpp
     */
    public void setCodPpp(Long codPpp) {
        this.codPpp = codPpp;
    }

    /**
     *
     * @return
     */
    public String getIndAtivaPpp() {
        return this.indAtivaPpp;
    }

    /**
     *
     * @param indAtivaPpp
     */
    public void setIndAtivaPpp(String indAtivaPpp) {
        this.indAtivaPpp = indAtivaPpp;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoPpp() {
        return this.dataInclusaoPpp;
    }

    /**
     *
     * @param dataInclusaoPpp
     */
    public void setDataInclusaoPpp(Date dataInclusaoPpp) {
        this.dataInclusaoPpp = dataInclusaoPpp;
    }

    /**
     *
     * @return
     */
    public Date getDataFimExibicaoPpp() {
        return this.dataFimExibicaoPpp;
    }

    /**
     *
     * @param dataFimExibicaoPpp
     */
    public void setDataFimExibicaoPpp(Date dataFimExibicaoPpp) {
        this.dataFimExibicaoPpp = dataFimExibicaoPpp;
    }

    /**
     *
     * @return
     */
    public Date getDataIniExibicaoPpp() {
        return this.dataIniExibicaoPpp;
    }

    /**
     *
     * @param dataIniExibicaoPpp
     */
    public void setDataIniExibicaoPpp(Date dataIniExibicaoPpp) {
        this.dataIniExibicaoPpp = dataIniExibicaoPpp;
    }

    /**
     *
     * @return
     */
    public String getIndDesativarPpp() {
        return this.indDesativarPpp;
    }

    /**
     *
     * @param indDesativarPpp
     */
    public void setIndDesativarPpp(String indDesativarPpp) {
        this.indDesativarPpp = indDesativarPpp;
    }

    /**
     *
     * @return
     */
    public Integer getJanelaLarguraPpp() {
        return this.janelaLarguraPpp;
    }

    /**
     *
     * @param janelaLarguraPpp
     */
    public void setJanelaLarguraPpp(Integer janelaLarguraPpp) {
        this.janelaLarguraPpp = janelaLarguraPpp;
    }

    /**
     *
     * @return
     */
    public Integer getJanelaAlturaPpp() {
        return this.janelaAlturaPpp;
    }

    /**
     *
     * @param janelaAlturaPpp
     */
    public void setJanelaAlturaPpp(Integer janelaAlturaPpp) {
        this.janelaAlturaPpp = janelaAlturaPpp;
    }

    /**
     *
     * @return
     */
    public String getConteudoPpp() {
        return this.conteudoPpp;
    }

    /**
     *
     * @param conteudoPpp
     */
    public void setConteudoPpp(String conteudoPpp) {
        this.conteudoPpp = conteudoPpp;
    }

    /**
     *
     * @return
     */
    public String getNomePpp() {
        return this.nomePpp;
    }

    /**
     *
     * @param nomePpp
     */
    public void setNomePpp(String nomePpp) {
        this.nomePpp = nomePpp;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.PopupComportamentoPppc getPopupComportamentoPppc() {
        return this.popupComportamentoPppc;
    }

    /**
     *
     * @param popupComportamentoPppc
     */
    public void setPopupComportamentoPppc(ecar.pojo.PopupComportamentoPppc popupComportamentoPppc) {
        this.popupComportamentoPppc = popupComportamentoPppc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codPpp", getCodPpp())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PopupPpp) ) return false;
        PopupPpp castOther = (PopupPpp) other;
        return new EqualsBuilder()
            .append(this.getCodPpp(), castOther.getCodPpp())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodPpp())
            .toHashCode();
    }

}
