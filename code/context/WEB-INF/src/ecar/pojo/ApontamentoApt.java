package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ApontamentoApt implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 333766403978802258L;

	/** identifier field */
    private Long codApt;

    /** nullable persistent field */
    private Date dataInclusaoApt;

    /** nullable persistent field */
    private String textoApt;

    /** persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;

    /** persistent field */
    private ecar.pojo.PontoCriticoPtc pontoCriticoPtc;

    /** full constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param dataInclusaoApt
     * @param textoApt
     * @param itemEstruturaIett
     * @param usuarioUsu
     * @param pontoCriticoPtc
     */
    public ApontamentoApt(Date dataInclusaoApt, String textoApt, ecar.pojo.ItemEstruturaIett itemEstruturaIett, 
    		ecar.pojo.UsuarioUsu usuarioUsu, ecar.pojo.PontoCriticoPtc pontoCriticoPtc) {
        this.dataInclusaoApt = dataInclusaoApt;
        this.textoApt = textoApt;
        this.itemEstruturaIett = itemEstruturaIett;
        this.usuarioUsu = usuarioUsu;
        this.pontoCriticoPtc = pontoCriticoPtc;
    }

    /** default constructor */
    public ApontamentoApt() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @author N/C
     * @since N/C
     * @param itemEstruturaIett
     * @param usuarioUsu
     * @param pontoCriticoPtc
     */
    public ApontamentoApt(ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.UsuarioUsu usuarioUsu, 
    		ecar.pojo.PontoCriticoPtc pontoCriticoPtc) {
        this.itemEstruturaIett = itemEstruturaIett;
        this.usuarioUsu = usuarioUsu;
        this.pontoCriticoPtc = pontoCriticoPtc;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Long
     */
    public Long getCodApt() {
        return this.codApt;
    }

    /**
     * @param codApt
     * @author N/C
     * @since N/C
     */
    public void setCodApt(Long codApt) {
        this.codApt = codApt;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Date
     */
    public Date getDataInclusaoApt() {
        return this.dataInclusaoApt;
    }

    /**
     * @param dataInclusaoApt 
     * @author N/C
     * @since N/C
     */
    public void setDataInclusaoApt(Date dataInclusaoApt) {
        this.dataInclusaoApt = dataInclusaoApt;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getTextoApt() {
        return this.textoApt;
    }

    /**
     * @author N/C
     * @since N/C
     * @param textoApt
     */
    public void setTextoApt(String textoApt) {
        this.textoApt = textoApt;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.ItemEstruturaIett
     */
    public ecar.pojo.ItemEstruturaIett getItemEstruturaIett() {
        return this.itemEstruturaIett;
    }

    /**
     * @author N/C
     * @since N/C
     * @param itemEstruturaIett
     */
    public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
        this.itemEstruturaIett = itemEstruturaIett;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.UsuarioUsu
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }

    /**
     * @author N/C
     * @since N/C
     * @param usuarioUsu
     */
    public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }

    /**
     * @author N/C
     * @since N/C
     * @return ecar.pojo.PontoCriticoPtc
     */
    public ecar.pojo.PontoCriticoPtc getPontoCriticoPtc() {
        return this.pontoCriticoPtc;
    }

    /**
     * @param pontoCriticoPtc
     * @author N/C
     * @since N/C
     */
    public void setPontoCriticoPtc(ecar.pojo.PontoCriticoPtc pontoCriticoPtc) {
        this.pontoCriticoPtc = pontoCriticoPtc;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codApt", getCodApt())
            .toString();
    }

    /**
     * @param other
     * @author N/C
     * @since N/C
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ApontamentoApt) ) return false;
        ApontamentoApt castOther = (ApontamentoApt) other;
        return new EqualsBuilder()
            .append(this.getCodApt(), castOther.getCodApt())
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
            .append(getCodApt())
            .toHashCode();
    }

}
