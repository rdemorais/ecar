/**
 * 
 */
package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 */
public class PesquisaIett implements Serializable, PaiFilho {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2530399740961201244L;

	/**
	 * PK Composta
	 */
	private PesquisaIettPK comp_id;
	
	/**
	 * Pesquisa
	 */
	private Pesquisa pesquisa;
	
	/**
	 * Item
	 */
	private ItemEstruturaIett itemEstruturaIett;
	
	/**
	 * Referência
	 */
	private AcompReferenciaAref acompReferenciaAref;

	/**
	 * @param comp_id
	 * @param pesquisa
	 * @param itemEstruturaIett
	 * @param acompReferenciaAref
	 */
	public PesquisaIett(PesquisaIettPK comp_id, Pesquisa pesquisa,
			ItemEstruturaIett itemEstruturaIett,
			AcompReferenciaAref acompReferenciaAref) {
		super();
		this.comp_id = comp_id;
		this.pesquisa = pesquisa;
		this.itemEstruturaIett = itemEstruturaIett;
		this.acompReferenciaAref = acompReferenciaAref;
	}

	/**
	 * 
	 */
	public PesquisaIett() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the comp_id
	 */
	public PesquisaIettPK getComp_id() {
		return comp_id;
	}

	/**
	 * @param comp_id the comp_id to set
	 */
	public void setComp_id(PesquisaIettPK comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * @return the pesquisa
	 */
	public Pesquisa getPesquisa() {
		return pesquisa;
	}

	/**
	 * @param pesquisa the pesquisa to set
	 */
	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}

	/**
	 * @return the itemEstruturaIett
	 */
	public ItemEstruturaIett getItemEstruturaIett() {
		return itemEstruturaIett;
	}

	/**
	 * @param itemEstruturaIett the itemEstruturaIett to set
	 */
	public void setItemEstruturaIett(ItemEstruturaIett itemEstruturaIett) {
		this.itemEstruturaIett = itemEstruturaIett;
	}

	/**
	 * @return the acompReferenciaAref
	 */
	public AcompReferenciaAref getAcompReferenciaAref() {
		return acompReferenciaAref;
	}

	/**
	 * @param acompReferenciaAref the acompReferenciaAref to set
	 */
	public void setAcompReferenciaAref(AcompReferenciaAref acompReferenciaAref) {
		this.acompReferenciaAref = acompReferenciaAref;
	}
	
	@Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof PesquisaIett) ) return false;
        PesquisaIett castOther = (PesquisaIett) other;
        return new EqualsBuilder()
            .append(this.getPesquisa().getCodPesquisa(), castOther.getPesquisa().getCodPesquisa())
            .append(this.getItemEstruturaIett().getCodIett(), castOther.getItemEstruturaIett().getCodIett())
            .append(this.getAcompReferenciaAref().getCodAref(), castOther.getAcompReferenciaAref().getCodAref())
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
       comp_id = new PesquisaIettPK();
       comp_id.setCodPesquisa(this.getPesquisa().getCodPesquisa());
       comp_id.setCodIett(this.getItemEstruturaIett().getCodIett());
       comp_id.setCodAref(this.getAcompReferenciaAref().getCodAref());
   }
	
}
