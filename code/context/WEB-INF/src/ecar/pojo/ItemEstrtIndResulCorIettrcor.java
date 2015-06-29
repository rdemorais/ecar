package ecar.pojo;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 *
 * @author 70744416353
 */
public class ItemEstrtIndResulCorIettrcor  implements java.io.Serializable, PaiFilho {

    // Fields    

	private static final long serialVersionUID = 8700325318702968320L;

	 /**
	 * 
	 */
	
	private ItemEstrtIndResulCorIettrcorPK id;
    private ItemEstrtIndResulIettr itemEstrtIndResulIettr;
    private Cor cor;
    private Integer frequenciaEnvioEmailIettrcor;
    private BigDecimal valorPrimEmailIettrcor;
    private String indAtivoEnvioEmailIettrcor;
    private String indMenorValorIettrcor;
    private String indMaiorValorIettrcor;
    private Integer sequenciaIettrcor;
    

     // Constructors

    /** default constructor */
    public ItemEstrtIndResulCorIettrcor() {
    }

    /** minimal constructor
     * @param id
     * @param cor
     * @param itemEstrtIndResulIettr
     */
    public ItemEstrtIndResulCorIettrcor(ItemEstrtIndResulCorIettrcorPK id, ItemEstrtIndResulIettr itemEstrtIndResulIettr, Cor cor) {
        this.id = id;
        this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
        this.cor = cor;
    }
    /** full constructor
     * @param id
     * @param itemEstrtIndResulIettr
     * @param cor
     * @param frequenciaEnvioEmailIettrcor
     * @param valorPrimEmailIettrcor
     * @param indAtivoEnvioEmailIettrcor
     * @param indMaiorValorIettrcor
     * @param indMenorValorIettrcor
     * @param sequenciaIettrcor
     */
    public ItemEstrtIndResulCorIettrcor(ItemEstrtIndResulCorIettrcorPK id, ItemEstrtIndResulIettr itemEstrtIndResulIettr, 
    									Cor cor, Integer frequenciaEnvioEmailIettrcor, BigDecimal valorPrimEmailIettrcor, 
    									String indAtivoEnvioEmailIettrcor, String indMenorValorIettrcor, String indMaiorValorIettrcor, Integer sequenciaIettrcor) {
       this.id = id;
       this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
       this.cor = cor;
       this.frequenciaEnvioEmailIettrcor = frequenciaEnvioEmailIettrcor;
       this.valorPrimEmailIettrcor = valorPrimEmailIettrcor;
       this.indAtivoEnvioEmailIettrcor = indAtivoEnvioEmailIettrcor;
       this.indMenorValorIettrcor = indMenorValorIettrcor;
       this.indMaiorValorIettrcor = indMaiorValorIettrcor;
       this.sequenciaIettrcor = sequenciaIettrcor; 
    }
    
   
    // Property accessors
    /**
     *
     * @return
     */
    public ItemEstrtIndResulCorIettrcorPK getId() {
        return this.id;
    }
    
    /**
     *
     * @param id
     */
    public void setId(ItemEstrtIndResulCorIettrcorPK id) {
        this.id = id;
    }
    /**
     *
     * @return
     */
    public ItemEstrtIndResulIettr getItemEstrtIndResulIettr() {
        return this.itemEstrtIndResulIettr;
    }
    
    /**
     *
     * @param itemEstrtIndResulIettr
     */
    public void setItemEstrtIndResulIettr(ItemEstrtIndResulIettr itemEstrtIndResulIettr) {
        this.itemEstrtIndResulIettr = itemEstrtIndResulIettr;
    }
    /**
     *
     * @return
     */
    public Cor getCor() {
        return this.cor;
    }
    
    /**
     *
     * @param cor
     */
    public void setCor(Cor cor) {
        this.cor = cor;
    }
    /**
     *
     * @return
     */
    public Integer getFrequenciaEnvioEmailIettrcor() {
        return this.frequenciaEnvioEmailIettrcor;
    }
    
    /**
     *
     * @param frequenciaEnvioEmailIettrcor
     */
    public void setFrequenciaEnvioEmailIettrcor(Integer frequenciaEnvioEmailIettrcor) {
        this.frequenciaEnvioEmailIettrcor = frequenciaEnvioEmailIettrcor;
    }
    /**
     *
     * @return
     */
    public BigDecimal getValorPrimEmailIettrcor() {
        return this.valorPrimEmailIettrcor;
    }
    
    /**
     *
     * @param valorPrimEmailIettrcor
     */
    public void setValorPrimEmailIettrcor(BigDecimal valorPrimEmailIettrcor) {
        this.valorPrimEmailIettrcor = valorPrimEmailIettrcor;
    }
    /**
     *
     * @return
     */
    public String getIndAtivoEnvioEmailIettrcor() {
        return this.indAtivoEnvioEmailIettrcor;
    }
    
    /**
     *
     * @param indAtivoEnvioEmailIettrcor
     */
    public void setIndAtivoEnvioEmailIettrcor(String indAtivoEnvioEmailIettrcor) {
        this.indAtivoEnvioEmailIettrcor = indAtivoEnvioEmailIettrcor;
    }

    /**
     *
     */
    public void atribuirPKPai() {
        id = new ItemEstrtIndResulCorIettrcorPK();        
        id.setCodCor(this.getCor().getCodCor());
        id.setCodIettir(this.getItemEstrtIndResulIettr().getCodIettir());
		
	}
	
    @Override
	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrtIndResulCorIettrcor) ) return false;
        ItemEstrtIndResulCorIettrcor castOther = (ItemEstrtIndResulCorIettrcor) other;
        return new EqualsBuilder()
            .append(this.getItemEstrtIndResulIettr(), castOther.getItemEstrtIndResulIettr())
            .append(this.getCor(), castOther.getCor())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public String getIndMenorValorIettrcor() {
		return indMenorValorIettrcor;
	}

        /**
         *
         * @param indMenorValorIettrcor
         */
        public void setIndMenorValorIettrcor(String indMenorValorIettrcor) {
		this.indMenorValorIettrcor = indMenorValorIettrcor;
	}

        /**
         *
         * @return
         */
        public String getIndMaiorValorIettrcor() {
		return indMaiorValorIettrcor;
	}

        /**
         *
         * @param indMaiorValorIettrcor
         */
        public void setIndMaiorValorIettrcor(String indMaiorValorIettrcor) {
		this.indMaiorValorIettrcor = indMaiorValorIettrcor;
	}

        /**
         *
         * @return
         */
        public Integer getSequenciaIettrcor() {
		return sequenciaIettrcor;
	}

        /**
         *
         * @param sequenciaIettrcor
         */
        public void setSequenciaIettrcor(Integer sequenciaIettrcor) {
		this.sequenciaIettrcor = sequenciaIettrcor;
	}


}


