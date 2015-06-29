package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IettUsutpfuacrevIettutfar implements Serializable, PaiFilho  {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7421055937799306437L;

	/** identifier field */
    private ecar.pojo.IettUsutpfuacrevIettutfarPK comp_id;

    /** nullable persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev;

    /** nullable persistent field */
    private ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa;

    /** full constructor
     * @param comp_id
     * @param itemEstruturarevisaoIettrev
     * @param usuarioUsu
     * @param tipoFuncAcompTpfa
     */
    public IettUsutpfuacrevIettutfar(ecar.pojo.IettUsutpfuacrevIettutfarPK comp_id, ecar.pojo.UsuarioUsu usuarioUsu, ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev, ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa) {
        this.comp_id = comp_id;
        this.usuarioUsu = usuarioUsu;
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
    }

    /** default constructor */
    public IettUsutpfuacrevIettutfar() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public IettUsutpfuacrevIettutfar(ecar.pojo.IettUsutpfuacrevIettutfarPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.IettUsutpfuacrevIettutfarPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.IettUsutpfuacrevIettutfarPK comp_id) {
        this.comp_id = comp_id;
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
    public ecar.pojo.ItemEstruturarevisaoIettrev getItemEstruturarevisaoIettrev() {
        return this.itemEstruturarevisaoIettrev;
    }

    /**
     *
     * @param itemEstruturarevisaoIettrev
     */
    public void setItemEstruturarevisaoIettrev(ecar.pojo.ItemEstruturarevisaoIettrev itemEstruturarevisaoIettrev) {
        this.itemEstruturarevisaoIettrev = itemEstruturarevisaoIettrev;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.TipoFuncAcompTpfa getTipoFuncAcompTpfa() {
        return this.tipoFuncAcompTpfa;
    }

    /**
     *
     * @param tipoFuncAcompTpfa
     */
    public void setTipoFuncAcompTpfa(ecar.pojo.TipoFuncAcompTpfa tipoFuncAcompTpfa) {
        this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
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
        if ( !(other instanceof ItemEstUsutpfuacIettutfa) ) return false;
        IettUsutpfuacrevIettutfar castOther = (IettUsutpfuacrevIettutfar) other;
        return new EqualsBuilder()
        	.append(this.getTipoFuncAcompTpfa(), castOther.getTipoFuncAcompTpfa())
            .append(this.getItemEstruturarevisaoIettrev(), castOther.getItemEstruturarevisaoIettrev())
            .append(this.getUsuarioUsu(), castOther.getUsuarioUsu())
            .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }
    
    /**
     *
     */
    public void atribuirPKPai() {
        comp_id = new IettUsutpfuacrevIettutfarPK();        
        comp_id.setCodIettrev(this.getItemEstruturarevisaoIettrev().getCodIettrev());
        comp_id.setCodTpfa(Integer.valueOf(this.getTipoFuncAcompTpfa().getCodTpfa().intValue()));
        comp_id.setCodUsu(Integer.valueOf(this.getUsuarioUsu().getCodUsu().intValue()));
    }
}
