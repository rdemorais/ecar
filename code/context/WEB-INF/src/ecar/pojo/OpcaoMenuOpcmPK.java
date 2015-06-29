package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class OpcaoMenuOpcmPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4794991716785046680L;

	/** identifier field */
    private Long codOpcFilho;

    /** identifier field */
    private Long codOpcPai;

    /** full constructor
     * @param codOpcFilho
     * @param codOpcPai
     */
    public OpcaoMenuOpcmPK(Long codOpcFilho, Long codOpcPai) {
        this.codOpcFilho = codOpcFilho;
        this.codOpcPai = codOpcPai;
    }

    /** default constructor */
    public OpcaoMenuOpcmPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodOpcFilho() {
        return this.codOpcFilho;
    }

    /**
     *
     * @param codOpcFilho
     */
    public void setCodOpcFilho(Long codOpcFilho) {
        this.codOpcFilho = codOpcFilho;
    }

    /**
     *
     * @return
     */
    public Long getCodOpcPai() {
        return this.codOpcPai;
    }

    /**
     *
     * @param codOpcPai
     */
    public void setCodOpcPai(Long codOpcPai) {
        this.codOpcPai = codOpcPai;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codOpcFilho", getCodOpcFilho())
            .append("codOpcPai", getCodOpcPai())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof OpcaoMenuOpcmPK) ) return false;
        OpcaoMenuOpcmPK castOther = (OpcaoMenuOpcmPK) other;
        return new EqualsBuilder()
            .append(this.getCodOpcFilho(), castOther.getCodOpcFilho())
            .append(this.getCodOpcPai(), castOther.getCodOpcPai())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodOpcFilho())
            .append(getCodOpcPai())
            .toHashCode();
    }

}
