package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class UsuarioAtributoUsuaPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3815808282240057255L;

	/** identifier field */
    private Long codUsu;

    /** identifier field */
    private Long codSatb;

    /** full constructor
     * @param codUsu
     * @param codSatb
     */
    public UsuarioAtributoUsuaPK(Long codUsu, Long codSatb) {
        this.codUsu = codUsu;
        this.codSatb = codSatb;
    }

    /** default constructor */
    public UsuarioAtributoUsuaPK() {
    }

    /**
     *
     * @return
     */
    public Long getCodUsu() {
        return this.codUsu;
    }

    /**
     *
     * @param codUsu
     */
    public void setCodUsu(Long codUsu) {
        this.codUsu = codUsu;
    }

    /**
     *
     * @return
     */
    public Long getCodSatb() {
        return this.codSatb;
    }

    /**
     *
     * @param codSatb
     */
    public void setCodSatb(Long codSatb) {
        this.codSatb = codSatb;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codUsu", getCodUsu())
            .append("codSatb", getCodSatb())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof UsuarioAtributoUsuaPK) ) return false;
        UsuarioAtributoUsuaPK castOther = (UsuarioAtributoUsuaPK) other;
        return new EqualsBuilder()
            .append(this.getCodUsu(), castOther.getCodUsu())
            .append(this.getCodSatb(), castOther.getCodSatb())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodUsu())
            .append(getCodSatb())
            .toHashCode();
    }

}
