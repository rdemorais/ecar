package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 06/06/2007 
 */
public class ItemEstrutLocalIettl implements Serializable, PaiFilho, Comparable<ItemEstrutLocalIettl> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3033298695753782063L;

	/** identifier field */
    private ecar.pojo.ItemEstrutLocalIettlPK comp_id;

    /** nullable persistent field */
    private Date dataInclusaoIettl;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** nullable persistent field */
    private ecar.pojo.LocalItemLit localItemLit;
    
    /* -- Mantis #2156 --*/
    private Boolean indExclusaoPosHistorico;
    
    private UsuarioUsu usuarioUsuManutencao;

    /**
     *
     * @return
     */
    public Boolean getIndExclusaoPosHistorico() {
		return indExclusaoPosHistorico;
	}

    /**
     *
     * @param indExclusaoPosHistorico
     */
    public void setIndExclusaoPosHistorico(Boolean indExclusaoPosHistorico) {
		this.indExclusaoPosHistorico = indExclusaoPosHistorico;
	}

        /** full constructor
         * @param comp_id
         * @param dataInclusaoIettl
         * @param itemEstruturaIett
         * @param localItemLit
         */
    public ItemEstrutLocalIettl(ecar.pojo.ItemEstrutLocalIettlPK comp_id, Date dataInclusaoIettl, ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.LocalItemLit localItemLit) {
        this.comp_id = comp_id;
        this.dataInclusaoIettl = dataInclusaoIettl;
        this.itemEstruturaIett = itemEstruturaIett;
        this.localItemLit = localItemLit;
    }

    /** default constructor */
    public ItemEstrutLocalIettl() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public ItemEstrutLocalIettl(ecar.pojo.ItemEstrutLocalIettlPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstrutLocalIettlPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.ItemEstrutLocalIettlPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoIettl() {
        return this.dataInclusaoIettl;
    }

    /**
     *
     * @param dataInclusaoIettl
     */
    public void setDataInclusaoIettl(Date dataInclusaoIettl) {
        this.dataInclusaoIettl = dataInclusaoIettl;
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
    public ecar.pojo.LocalItemLit getLocalItemLit() {
        return this.localItemLit;
    }

    /**
     *
     * @param localItemLit
     */
    public void setLocalItemLit(ecar.pojo.LocalItemLit localItemLit) {
        this.localItemLit = localItemLit;
    }
    
    /**
     *
     * @return
     */
    public UsuarioUsu getUsuarioUsuManutencao() {
		return usuarioUsuManutencao;
	}

    /**
     *
     * @param usuarioUsuManutencao
     */
    public void setUsuarioUsuManutencao(UsuarioUsu usuarioUsuManutencao) {
		this.usuarioUsuManutencao = usuarioUsuManutencao;
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
        if ( !(other instanceof ItemEstrutLocalIettl) ) return false;
        ItemEstrutLocalIettl castOther = (ItemEstrutLocalIettl) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
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
        comp_id = new ItemEstrutLocalIettlPK();        
        comp_id.setCodIett(this.getItemEstruturaIett().getCodIett());
        comp_id.setCodLit(this.getLocalItemLit().getCodLit());
    }

    //Método que implementa a interface Comparable
    //É utilizado para ordenar pela função Collections.sort
	public int compareTo(ItemEstrutLocalIettl item) {
		return this.getLocalItemLit().getIdentificacaoLit().compareToIgnoreCase(item.getLocalItemLit().getIdentificacaoLit());
	}

}
