package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ItemEstrutMarcadorIettm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7771360487046217726L;

	/** identifier field */
    private Long codIettm;

    /** nullable persistent field */
    private Date dataInclusaoIettm;

    /** nullable persistent field */
    private String descricaoIettm;

    /** persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;

    /** persistent field */
    private ecar.pojo.Cor cor;

    /** full constructor
     * @param dataInclusaoIettm
     * @param descricaoIettm
     * @param itemEstruturaIett
     * @param usuarioUsu
     * @param cor
     */
    public ItemEstrutMarcadorIettm(Date dataInclusaoIettm, String descricaoIettm, ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.UsuarioUsu usuarioUsu, ecar.pojo.Cor cor) {
        this.dataInclusaoIettm = dataInclusaoIettm;
        this.descricaoIettm = descricaoIettm;
        this.itemEstruturaIett = itemEstruturaIett;
        this.usuarioUsu = usuarioUsu;
        this.cor = cor;
    }

    /** default constructor */
    public ItemEstrutMarcadorIettm() {
    }

    /** minimal constructor
     * @param itemEstruturaIett
     * @param usuarioUsu
     * @param cor
     */
    public ItemEstrutMarcadorIettm(ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.UsuarioUsu usuarioUsu, ecar.pojo.Cor cor) {
        this.itemEstruturaIett = itemEstruturaIett;
        this.usuarioUsu = usuarioUsu;
        this.cor = cor;
    }

    /**
     *
     * @return
     */
    public Long getCodIettm() {
        return this.codIettm;
    }

    /**
     *
     * @param codIettm
     */
    public void setCodIettm(Long codIettm) {
        this.codIettm = codIettm;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoIettm() {
        return this.dataInclusaoIettm;
    }

    /**
     *
     * @param dataInclusaoIettm
     */
    public void setDataInclusaoIettm(Date dataInclusaoIettm) {
        this.dataInclusaoIettm = dataInclusaoIettm;
    }

    /**
     *
     * @return
     */
    public String getDescricaoIettm() {
        return this.descricaoIettm;
    }

    /**
     *
     * @param descricaoIettm
     */
    public void setDescricaoIettm(String descricaoIettm) {
        this.descricaoIettm = descricaoIettm;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstruturaIett getItemEstruturaIett() {
        return this.itemEstruturaIett;
    }

    /**
     *
     * @param itemEstruturaIett
     */
    public void setItemEstruturaIett(ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
        this.itemEstruturaIett = itemEstruturaIett;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UsuarioUsu getUsuarioUsu() {
        return this.usuarioUsu;
    }

    /**
     *
     * @param usuarioUsu
     */
    public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
        this.usuarioUsu = usuarioUsu;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.Cor getCor() {
        return this.cor;
    }

    /**
     *
     * @param cor
     */
    public void setCor(ecar.pojo.Cor cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIettm", getCodIettm())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrutMarcadorIettm) ) return false;
        ItemEstrutMarcadorIettm castOther = (ItemEstrutMarcadorIettm) other;
        return new EqualsBuilder()
            .append(this.getCodIettm(), castOther.getCodIettm())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettm())
            .toHashCode();
    }

}
