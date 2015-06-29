package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class EstruturaAcessoEttaPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 445170844841566493L;

	/** identifier field */
    private Long codEtt;

    /** identifier field */
    private Long codAtb;

    /** full constructor
     * @param codEtt
     * @param codAtb
     */
    public EstruturaAcessoEttaPK(Long codEtt, Long codAtb) {
        this.codEtt = codEtt;
        this.codAtb = codAtb;
    }

    /** default constructor */
    public EstruturaAcessoEttaPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodEtt() {
        return this.codEtt;
    }

    /**
     *
     * @param codEtt
     */
    public void setCodEtt(Long codEtt) {
        this.codEtt = codEtt;
    }

    /**
     *
     * @return
     */
    public Long getCodAtb() {
        return this.codAtb;
    }

    /**
     *
     * @param codAtb
     */
    public void setCodAtb(Long codAtb) {
        this.codAtb = codAtb;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codEtt", getCodEtt())
            .append("codAtb", getCodAtb())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EstruturaAcessoEttaPK) ) return false;
        EstruturaAcessoEttaPK castOther = (EstruturaAcessoEttaPK) other;
        return new EqualsBuilder()
            .append(this.getCodEtt(), castOther.getCodEtt())
            .append(this.getCodAtb(), castOther.getCodAtb())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodEtt())
            .append(getCodAtb())
            .toHashCode();
    }

}
