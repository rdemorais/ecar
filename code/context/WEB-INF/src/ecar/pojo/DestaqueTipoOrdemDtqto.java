package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class DestaqueTipoOrdemDtqto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8742762018955929149L;

	/** identifier field */
    private Long codDtqto;

    /** nullable persistent field */
    private String rotinaDtqto;

    /** nullable persistent field */
    private String identificacaoDtqto;

    /** persistent field */
    private Set destaqueSubAreaDtqsas;

    /** full constructor
     * @param rotinaDtqto
     * @param identificacaoDtqto
     * @param destaqueSubAreaDtqsas
     */
    public DestaqueTipoOrdemDtqto(String rotinaDtqto, String identificacaoDtqto, Set destaqueSubAreaDtqsas) {
        this.rotinaDtqto = rotinaDtqto;
        this.identificacaoDtqto = identificacaoDtqto;
        this.destaqueSubAreaDtqsas = destaqueSubAreaDtqsas;
    }

    /** default constructor */
    public DestaqueTipoOrdemDtqto() {
    }

    /** minimal constructor
     * @param destaqueSubAreaDtqsas
     */
    public DestaqueTipoOrdemDtqto(Set destaqueSubAreaDtqsas) {
        this.destaqueSubAreaDtqsas = destaqueSubAreaDtqsas;
    }

    /**
     *
     * @return
     */
    public Long getCodDtqto() {
        return this.codDtqto;
    }

    /**
     *
     * @param codDtqto
     */
    public void setCodDtqto(Long codDtqto) {
        this.codDtqto = codDtqto;
    }

    /**
     *
     * @return
     */
    public String getRotinaDtqto() {
        return this.rotinaDtqto;
    }

    /**
     *
     * @param rotinaDtqto
     */
    public void setRotinaDtqto(String rotinaDtqto) {
        this.rotinaDtqto = rotinaDtqto;
    }

    /**
     *
     * @return
     */
    public String getIdentificacaoDtqto() {
        return this.identificacaoDtqto;
    }

    /**
     *
     * @param identificacaoDtqto
     */
    public void setIdentificacaoDtqto(String identificacaoDtqto) {
        this.identificacaoDtqto = identificacaoDtqto;
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
            .append("codDtqto", getCodDtqto())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof DestaqueTipoOrdemDtqto) ) return false;
        DestaqueTipoOrdemDtqto castOther = (DestaqueTipoOrdemDtqto) other;
        return new EqualsBuilder()
            .append(this.getCodDtqto(), castOther.getCodDtqto())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodDtqto())
            .toHashCode();
    }

}
