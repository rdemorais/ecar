package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Classe POJO para ItemEstruturaIett
 * @author gabrielsolana
 *
 */
public class ItemEstruturaIettMin implements Serializable {


	private static final long serialVersionUID = -8110926690492488601L;

    private Long codIett;

    private Integer nivelIett;  
    
    private String siglaIett;
    
    private String indAtivoIett;
    
    private ecar.pojo.EstruturaEtt estruturaEtt;
    
    private Set<ItemEstruturaIettMin> itemEstruturaIetts;

    private ItemEstruturaIettMin itemEstruturaIett;

    
    /**
     *
     */
    public ItemEstruturaIettMin() {
	}

    public ecar.pojo.EstruturaEtt getEstruturaEtt() {
		return estruturaEtt;
	}



	public void setEstruturaEtt(ecar.pojo.EstruturaEtt estruturaEtt) {
		this.estruturaEtt = estruturaEtt;
	}



	public String getIndAtivoIett() {
		return indAtivoIett;
	}

	public void setIndAtivoIett(String indAtivoIett) {
		this.indAtivoIett = indAtivoIett;
	}



	public String getSiglaIett() {
		return siglaIett;
	}



	public void setSiglaIett(String siglaIett) {
		this.siglaIett = siglaIett;
	}



	/**
     *
     * @return
     */
    public Long getCodIett() {
		return codIett;
	}


        /**
         *
         * @param codIett
         */
        public void setCodIett(Long codIett) {
		this.codIett = codIett;
	}


        /**
         *
         * @return
         */
        public ItemEstruturaIettMin getItemEstruturaIett() {
		return itemEstruturaIett;
	}


        /**
         *
         * @param itemEstruturaIett
         */
        public void setItemEstruturaIett(ItemEstruturaIettMin itemEstruturaIett) {
		this.itemEstruturaIett = itemEstruturaIett;
	}


        /**
         *
         * @return
         */
        public Set<ItemEstruturaIettMin> getItemEstruturaIetts() {
		return itemEstruturaIetts;
	}


        /**
         *
         * @param itemEstruturaIetts
         */
        public void setItemEstruturaIetts(Set<ItemEstruturaIettMin> itemEstruturaIetts) {
		this.itemEstruturaIetts = itemEstruturaIetts;
	}


        /**
         *
         * @return
         */
        public Integer getNivelIett() {
		return nivelIett;
	}


        /**
         *
         * @param nivelIett
         */
        public void setNivelIett(Integer nivelIett) {
		this.nivelIett = nivelIett;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIett", getCodIett())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstruturaIettMin) ) return false;
        ItemEstruturaIettMin castOther = (ItemEstruturaIettMin) other;
        return new EqualsBuilder()
            .append(this.getCodIett(), castOther.getCodIett())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIett())
            .toHashCode();
    }

}
