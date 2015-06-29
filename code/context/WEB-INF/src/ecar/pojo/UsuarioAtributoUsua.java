package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class UsuarioAtributoUsua extends AtributoLivre implements Serializable, PaiFilho {

	private static final long serialVersionUID = -2084647941512206682L;

	/** identifier field */
    private UsuarioAtributoUsuaPK comp_id;

    /** nullable persistent field */
    private UsuarioUsu usuarioUsu;

    /** full constructor
     * @param comp_id
     * @param usuarioUsu
     */
    public UsuarioAtributoUsua(UsuarioAtributoUsuaPK comp_id, UsuarioUsu usuarioUsu) {
        this.comp_id = comp_id;
        this.usuarioUsu = usuarioUsu;
    }

    /** default constructor */
    public UsuarioAtributoUsua() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public UsuarioAtributoUsua(UsuarioAtributoUsuaPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public UsuarioAtributoUsuaPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(UsuarioAtributoUsuaPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }

    /**
     *
     * @param usuarioUsu
     */
    public void setUsuarioUsu(UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof UsuarioAtributoUsua) ) return false;
        UsuarioAtributoUsua castOther = (UsuarioAtributoUsua) other;
        return new EqualsBuilder()
            .append(this.getUsuarioUsu(), castOther.getUsuarioUsu())
            .append(this.getSisAtributoSatb(), castOther.getSisAtributoSatb())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }
    
    public void atribuirPKPai() {
        comp_id = new UsuarioAtributoUsuaPK();        
        comp_id.setCodSatb(this.getSisAtributoSatb().getCodSatb());
        comp_id.setCodUsu(this.getUsuarioUsu().getCodUsu());
    }

}
