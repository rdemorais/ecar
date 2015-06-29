package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ContatoMailCategoriaCttm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7850033928125703551L;

	/** identifier field */
    private Long codCttmc;

    /** nullable persistent field */
    private String descricaoCttmc;

    /** persistent field */
    private Set contatoMailCttms;

    /** 
     * full constructor.<br>
     *
     * @author N/C
	 * @since N/C 
     * @param descricaoCttmc
     * @param contatoMailCttms
     */
    public ContatoMailCategoriaCttm(String descricaoCttmc, Set contatoMailCttms) {
        this.descricaoCttmc = descricaoCttmc;
        this.contatoMailCttms = contatoMailCttms;
    }

    /** default constructor */
    public ContatoMailCategoriaCttm() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @author N/C
	 * @since N/C 
     * @param contatoMailCttms
     */
    public ContatoMailCategoriaCttm(Set contatoMailCttms) {
        this.contatoMailCttms = contatoMailCttms;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getCodCttmc() {
        return this.codCttmc;
    }

    /**
     * @param codCttmc
     * @author N/C
	 * @since N/C 
     */
    public void setCodCttmc(Long codCttmc) {
        this.codCttmc = codCttmc;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getDescricaoCttmc() {
        return this.descricaoCttmc;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param descricaoCttmc
     */
    public void setDescricaoCttmc(String descricaoCttmc) {
        this.descricaoCttmc = descricaoCttmc;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
    public Set getContatoMailCttms() {
        return this.contatoMailCttms;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param contatoMailCttms
     */
    public void setContatoMailCttms(Set contatoMailCttms) {
        this.contatoMailCttms = contatoMailCttms;
    }

    /**
     * @author N/C
	 * @since N/C 
	 * #return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codCttmc", getCodCttmc())
            .toString();
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param other
	 * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ContatoMailCategoriaCttm) ) return false;
        ContatoMailCategoriaCttm castOther = (ContatoMailCategoriaCttm) other;
        return new EqualsBuilder()
            .append(this.getCodCttmc(), castOther.getCodCttmc())
            .isEquals();
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodCttmc())
            .toHashCode();
    }

}
