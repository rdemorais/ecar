package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class DestaqueSubAreaDtqsa implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4604059910994696249L;

	/** identifier field */
    private Long codDtqsa;

    /** nullable persistent field */
    private Integer qtdMaxItensDtqsa;

    /** nullable persistent field */
    private String identificacaoDtqsa;

    /** nullable persistent field */
    private String descricaoDtqsa;

    /** persistent field */
    private ecar.pojo.DestaqueAreaDtqa destaqueAreaDtqa;

    /** persistent field */
    private ecar.pojo.DestaqueTipoOrdemDtqto destaqueTipoOrdemDtqto;

    /** persistent field */
    private Set destaqueItemRelDtqirs;

    /** full constructor
     * @param qtdMaxItensDtqsa
     * @param identificacaoDtqsa
     * @param descricaoDtqsa
     * @param destaqueAreaDtqa
     * @param destaqueTipoOrdemDtqto
     * @param destaqueItemRelDtqirs
     */
    public DestaqueSubAreaDtqsa(Integer qtdMaxItensDtqsa, String identificacaoDtqsa, String descricaoDtqsa, ecar.pojo.DestaqueAreaDtqa destaqueAreaDtqa, ecar.pojo.DestaqueTipoOrdemDtqto destaqueTipoOrdemDtqto, Set destaqueItemRelDtqirs) {
        this.qtdMaxItensDtqsa = qtdMaxItensDtqsa;
        this.identificacaoDtqsa = identificacaoDtqsa;
        this.descricaoDtqsa = descricaoDtqsa;
        this.destaqueAreaDtqa = destaqueAreaDtqa;
        this.destaqueTipoOrdemDtqto = destaqueTipoOrdemDtqto;
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
    }

    /** default constructor */
    public DestaqueSubAreaDtqsa() {
    }

    /** minimal constructor
     * @param destaqueAreaDtqa
     * @param destaqueTipoOrdemDtqto
     * @param destaqueItemRelDtqirs
     */
    public DestaqueSubAreaDtqsa(ecar.pojo.DestaqueAreaDtqa destaqueAreaDtqa, ecar.pojo.DestaqueTipoOrdemDtqto destaqueTipoOrdemDtqto, Set destaqueItemRelDtqirs) {
        this.destaqueAreaDtqa = destaqueAreaDtqa;
        this.destaqueTipoOrdemDtqto = destaqueTipoOrdemDtqto;
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
    }

    /**
     *
     * @return
     */
    public Long getCodDtqsa() {
        return this.codDtqsa;
    }

    /**
     *
     * @param codDtqsa
     */
    public void setCodDtqsa(Long codDtqsa) {
        this.codDtqsa = codDtqsa;
    }

    /**
     *
     * @return
     */
    public Integer getQtdMaxItensDtqsa() {
        return this.qtdMaxItensDtqsa;
    }

    /**
     *
     * @param qtdMaxItensDtqsa
     */
    public void setQtdMaxItensDtqsa(Integer qtdMaxItensDtqsa) {
        this.qtdMaxItensDtqsa = qtdMaxItensDtqsa;
    }

    /**
     *
     * @return
     */
    public String getIdentificacaoDtqsa() {
        return this.identificacaoDtqsa;
    }

    /**
     *
     * @param identificacaoDtqsa
     */
    public void setIdentificacaoDtqsa(String identificacaoDtqsa) {
        this.identificacaoDtqsa = identificacaoDtqsa;
    }

    /**
     *
     * @return
     */
    public String getDescricaoDtqsa() {
        return this.descricaoDtqsa;
    }

    /**
     *
     * @param descricaoDtqsa
     */
    public void setDescricaoDtqsa(String descricaoDtqsa) {
        this.descricaoDtqsa = descricaoDtqsa;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.DestaqueAreaDtqa getDestaqueAreaDtqa() {
        return this.destaqueAreaDtqa;
    }

    /**
     *
     * @param destaqueAreaDtqa
     */
    public void setDestaqueAreaDtqa(ecar.pojo.DestaqueAreaDtqa destaqueAreaDtqa) {
        this.destaqueAreaDtqa = destaqueAreaDtqa;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.DestaqueTipoOrdemDtqto getDestaqueTipoOrdemDtqto() {
        return this.destaqueTipoOrdemDtqto;
    }

    /**
     *
     * @param destaqueTipoOrdemDtqto
     */
    public void setDestaqueTipoOrdemDtqto(ecar.pojo.DestaqueTipoOrdemDtqto destaqueTipoOrdemDtqto) {
        this.destaqueTipoOrdemDtqto = destaqueTipoOrdemDtqto;
    }

    /**
     *
     * @return
     */
    public Set getDestaqueItemRelDtqirs() {
        return this.destaqueItemRelDtqirs;
    }

    /**
     *
     * @param destaqueItemRelDtqirs
     */
    public void setDestaqueItemRelDtqirs(Set destaqueItemRelDtqirs) {
        this.destaqueItemRelDtqirs = destaqueItemRelDtqirs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codDtqsa", getCodDtqsa())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof DestaqueSubAreaDtqsa) ) return false;
        DestaqueSubAreaDtqsa castOther = (DestaqueSubAreaDtqsa) other;
        return new EqualsBuilder()
            .append(this.getCodDtqsa(), castOther.getCodDtqsa())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodDtqsa())
            .toHashCode();
    }

}
