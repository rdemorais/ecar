package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class DestaqueAreaDtqa implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8093025803860626806L;

	/** identifier field */
    private Long codDtqa;

    /** nullable persistent field */
    private Integer qtdColunasDtqa;

    /** nullable persistent field */
    private Date dataInclusaoDtqa;

    /** nullable persistent field */
    private String descricaoDtqa;

    /** nullable persistent field */
    private String nomeDtqa;

    /** nullable persistent field */
    private String identificacaoDtqa;

    /** persistent field */
    private Set destaqueSubAreaDtqsas;

    /** full constructor
     * @param qtdColunasDtqa
     * @param destaqueSubAreaDtqsas
     * @param descricaoDtqa
     * @param dataInclusaoDtqa
     * @param nomeDtqa
     * @param identificacaoDtqa
     */
    public DestaqueAreaDtqa(Integer qtdColunasDtqa, Date dataInclusaoDtqa, String descricaoDtqa, String nomeDtqa, String identificacaoDtqa, Set destaqueSubAreaDtqsas) {
        this.qtdColunasDtqa = qtdColunasDtqa;
        this.dataInclusaoDtqa = dataInclusaoDtqa;
        this.descricaoDtqa = descricaoDtqa;
        this.nomeDtqa = nomeDtqa;
        this.identificacaoDtqa = identificacaoDtqa;
        this.destaqueSubAreaDtqsas = destaqueSubAreaDtqsas;
    }

    /** default constructor */
    public DestaqueAreaDtqa() {
    }

    /** minimal constructor
     * @param destaqueSubAreaDtqsas
     */
    public DestaqueAreaDtqa(Set destaqueSubAreaDtqsas) {
        this.destaqueSubAreaDtqsas = destaqueSubAreaDtqsas;
    }

    /**
     *
     * @return
     */
    public Long getCodDtqa() {
        return this.codDtqa;
    }

    /**
     *
     * @param codDtqa
     */
    public void setCodDtqa(Long codDtqa) {
        this.codDtqa = codDtqa;
    }

    /**
     *
     * @return
     */
    public Integer getQtdColunasDtqa() {
        return this.qtdColunasDtqa;
    }

    /**
     *
     * @param qtdColunasDtqa
     */
    public void setQtdColunasDtqa(Integer qtdColunasDtqa) {
        this.qtdColunasDtqa = qtdColunasDtqa;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoDtqa() {
        return this.dataInclusaoDtqa;
    }

    /**
     *
     * @param dataInclusaoDtqa
     */
    public void setDataInclusaoDtqa(Date dataInclusaoDtqa) {
        this.dataInclusaoDtqa = dataInclusaoDtqa;
    }

    /**
     *
     * @return
     */
    public String getDescricaoDtqa() {
        return this.descricaoDtqa;
    }

    /**
     *
     * @param descricaoDtqa
     */
    public void setDescricaoDtqa(String descricaoDtqa) {
        this.descricaoDtqa = descricaoDtqa;
    }

    /**
     *
     * @return
     */
    public String getNomeDtqa() {
        return this.nomeDtqa;
    }

    /**
     *
     * @param nomeDtqa
     */
    public void setNomeDtqa(String nomeDtqa) {
        this.nomeDtqa = nomeDtqa;
    }

    /**
     *
     * @return
     */
    public String getIdentificacaoDtqa() {
        return this.identificacaoDtqa;
    }

    /**
     *
     * @param identificacaoDtqa
     */
    public void setIdentificacaoDtqa(String identificacaoDtqa) {
        this.identificacaoDtqa = identificacaoDtqa;
    }

    /**
     *
     * @return
     */
    public Set getDestaqueSubAreaDtqsas() {
        return this.destaqueSubAreaDtqsas;
    }

    /**
     *
     * @param destaqueSubAreaDtqsas
     */
    public void setDestaqueSubAreaDtqsas(Set destaqueSubAreaDtqsas) {
        this.destaqueSubAreaDtqsas = destaqueSubAreaDtqsas;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codDtqa", getCodDtqa())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof DestaqueAreaDtqa) ) return false;
        DestaqueAreaDtqa castOther = (DestaqueAreaDtqa) other;
        return new EqualsBuilder()
            .append(this.getCodDtqa(), castOther.getCodDtqa())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodDtqa())
            .toHashCode();
    }

}
