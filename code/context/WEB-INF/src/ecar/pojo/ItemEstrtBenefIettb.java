package ecar.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator, rogerio
 * @since n/c
 * @version 0.2, 06/06/2007
 */
public class ItemEstrtBenefIettb implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7535685191203324456L;

	/** identifier field */
    private ecar.pojo.ItemEstrtBenefIettbPK comp_id;

    /** nullable persistent field */
    private Date dataUltManutencaoIettb;

    /** nullable persistent field */
    private String comentarioIettb;

    /** nullable persistent field */
    private BigDecimal qtdPrevistaIettb;

    /** nullable persistent field */
    private ecar.pojo.BeneficiarioBnf beneficiarioBnf;

    /** nullable persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;
    
    /* -- Mantis #2156 -- */
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
         * @param dataUltManutencaoIettb
         * @param qtdPrevistaIettb
         * @param comentarioIettb
         * @param beneficiarioBnf
         * @param itemEstruturaIett
         */
    public ItemEstrtBenefIettb(ecar.pojo.ItemEstrtBenefIettbPK comp_id, Date dataUltManutencaoIettb, String comentarioIettb, BigDecimal qtdPrevistaIettb, ecar.pojo.BeneficiarioBnf beneficiarioBnf, ecar.pojo.ItemEstruturaIett itemEstruturaIett) {
        this.comp_id = comp_id;
        this.dataUltManutencaoIettb = dataUltManutencaoIettb;
        this.comentarioIettb = comentarioIettb;
        this.qtdPrevistaIettb = qtdPrevistaIettb;
        this.beneficiarioBnf = beneficiarioBnf;
        this.itemEstruturaIett = itemEstruturaIett;
    }

    /** default constructor */
    public ItemEstrtBenefIettb() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public ItemEstrtBenefIettb(ecar.pojo.ItemEstrtBenefIettbPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstrtBenefIettbPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.ItemEstrtBenefIettbPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public Date getDataUltManutencaoIettb() {
        return this.dataUltManutencaoIettb;
    }

    /**
     *
     * @param dataUltManutencaoIettb
     */
    public void setDataUltManutencaoIettb(Date dataUltManutencaoIettb) {
        this.dataUltManutencaoIettb = dataUltManutencaoIettb;
    }

    /**
     *
     * @return
     */
    public String getComentarioIettb() {
        return this.comentarioIettb;
    }

    /**
     *
     * @param comentarioIettb
     */
    public void setComentarioIettb(String comentarioIettb) {
        this.comentarioIettb = comentarioIettb;
    }

    /**
     *
     * @return
     */
    public BigDecimal getQtdPrevistaIettb() {
        return this.qtdPrevistaIettb;
    }

    /**
     *
     * @param qtdPrevistaIettb
     */
    public void setQtdPrevistaIettb(BigDecimal qtdPrevistaIettb) {
        this.qtdPrevistaIettb = qtdPrevistaIettb;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.BeneficiarioBnf getBeneficiarioBnf() {
        return this.beneficiarioBnf;
    }

    /**
     *
     * @param beneficiarioBnf
     */
    public void setBeneficiarioBnf(ecar.pojo.BeneficiarioBnf beneficiarioBnf) {
        this.beneficiarioBnf = beneficiarioBnf;
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
	
        /**
         *
         */
        public void atribuirPKPai(){
		comp_id = new ItemEstrtBenefIettbPK();
		comp_id.setCodIett(this.getItemEstruturaIett().getCodIett());
		comp_id.setCodBnf(this.getBeneficiarioBnf().getCodBnf());
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
        if ( !(other instanceof ItemEstrtBenefIettb) ) return false;
        ItemEstrtBenefIettb castOther = (ItemEstrtBenefIettb) other;
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

}
