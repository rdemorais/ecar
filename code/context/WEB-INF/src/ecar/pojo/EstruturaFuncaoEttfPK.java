package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class EstruturaFuncaoEttfPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5273583228892375000L;

	/** identifier field */
    private Long codEtt;

    /** identifier field */
    private Long codFun;

    /** full constructor
     * @param codEtt
     * @param codFun
     */
    public EstruturaFuncaoEttfPK(Long codEtt, Long codFun) {
        this.codEtt = codEtt;
        this.codFun = codFun;
    }

    /** default constructor */
    public EstruturaFuncaoEttfPK() {
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
    public Long getCodFun() {
        return this.codFun;
    }

    /**
     *
     * @param codFun
     */
    public void setCodFun(Long codFun) {
        this.codFun = codFun;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codEtt", getCodEtt())
            .append("codFun", getCodFun())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EstruturaFuncaoEttfPK) ) return false;
        EstruturaFuncaoEttfPK castOther = (EstruturaFuncaoEttfPK) other;
        return new EqualsBuilder()
            .append(this.getCodEtt(), castOther.getCodEtt())
            .append(this.getCodFun(), castOther.getCodFun())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodEtt())
            .append(getCodFun())
            .toHashCode();
    }

}
