package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PopupComportamentoPppc implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9007200405716784053L;

	/** identifier field */
    private Long codPppc;

    /** nullable persistent field */
    private String descricaoPppc;

    /** persistent field */
    private Set popupPpps;

    /** full constructor
     * @param descricaoPppc
     * @param popupPpps
     */
    public PopupComportamentoPppc(String descricaoPppc, Set popupPpps) {
        this.descricaoPppc = descricaoPppc;
        this.popupPpps = popupPpps;
    }

    /** default constructor */
    public PopupComportamentoPppc() {
    }

    /** minimal constructor
     * @param popupPpps
     */
    public PopupComportamentoPppc(Set popupPpps) {
        this.popupPpps = popupPpps;
    }

    /**
     *
     * @return
     */
    public Long getCodPppc() {
        return this.codPppc;
    }

    /**
     *
     * @param codPppc
     */
    public void setCodPppc(Long codPppc) {
        this.codPppc = codPppc;
    }

    /**
     *
     * @return
     */
    public String getDescricaoPppc() {
        return this.descricaoPppc;
    }

    /**
     *
     * @param descricaoPppc
     */
    public void setDescricaoPppc(String descricaoPppc) {
        this.descricaoPppc = descricaoPppc;
    }

    /**
     *
     * @return
     */
    public Set getPopupPpps() {
        return this.popupPpps;
    }

    /**
     *
     * @param popupPpps
     */
    public void setPopupPpps(Set popupPpps) {
        this.popupPpps = popupPpps;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codPppc", getCodPppc())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PopupComportamentoPppc) ) return false;
        PopupComportamentoPppc castOther = (PopupComportamentoPppc) other;
        return new EqualsBuilder()
            .append(this.getCodPppc(), castOther.getCodPppc())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodPppc())
            .toHashCode();
    }

}
