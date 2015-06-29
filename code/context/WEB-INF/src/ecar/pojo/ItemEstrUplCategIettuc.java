package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ItemEstrUplCategIettuc implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -326442274973370903L;

	/** identifier field */
    private Long codIettuc;

    /** nullable persistent field */
    private String nomeIettuc;

    /** nullable persistent field */
    private String indAtivoIettuc;

    /** nullable persistent field */
    private Date dataInclusaoIettuc;

    /** nullable persistent field */
    private String imagemIettuc;

    /** nullable persistent field */
    private String descricaoIettuc;

    /** persistent field */
    private ecar.pojo.UploadTipoCategoriaUtc uploadTipoCategoriaUtc;

    /** persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** persistent field */
    private Set itemEstrutUploadIettups;
    
    /* Mantis #2156 */
    private Set historicoIettupHs;
    
    /** persistent field */
    private ecar.pojo.AcompReferenciaItemAri acompReferenciaItemAri;

    /**
     *
     * @return
     */
    public Set getHistoricoIettupHs() {
		return historicoIettupHs;
	}

    /**
     *
     * @param historicoIettupHs
     */
    public void setHistoricoIettupHs(Set historicoIettupHs) {
		this.historicoIettupHs = historicoIettupHs;
	}

        /** full constructor
         * @param nomeIettuc
         * @param imagemIettuc
         * @param indAtivoIettuc
         * @param dataInclusaoIettuc
         * @param descricaoIettuc
         * @param uploadTipoCategoriaUtc
         * @param itemEstrutUploadIettups
         * @param itemEstruturaIett
         * @param acompReferenciaItemAri
         */
    public ItemEstrUplCategIettuc(String nomeIettuc, String indAtivoIettuc, Date dataInclusaoIettuc, 
    							  String imagemIettuc, String descricaoIettuc, 
    							  ecar.pojo.UploadTipoCategoriaUtc uploadTipoCategoriaUtc, 
    							  ecar.pojo.ItemEstruturaIett itemEstruturaIett, 
    							  Set itemEstrutUploadIettups,
    							  ecar.pojo.AcompReferenciaItemAri acompReferenciaItemAri) {
        this.nomeIettuc = nomeIettuc;
        this.indAtivoIettuc = indAtivoIettuc;
        this.dataInclusaoIettuc = dataInclusaoIettuc;
        this.imagemIettuc = imagemIettuc;
        this.descricaoIettuc = descricaoIettuc;
        this.uploadTipoCategoriaUtc = uploadTipoCategoriaUtc;
        this.itemEstruturaIett = itemEstruturaIett;
        this.itemEstrutUploadIettups = itemEstrutUploadIettups;
        this.acompReferenciaItemAri = acompReferenciaItemAri;
    }

    /** default constructor */
    public ItemEstrUplCategIettuc() {
    }

    /** minimal constructor
     * @param uploadTipoCategoriaUtc
     * @param itemEstruturaIett
     * @param itemEstrutUploadIettups
     */
    public ItemEstrUplCategIettuc(ecar.pojo.UploadTipoCategoriaUtc uploadTipoCategoriaUtc, ecar.pojo.ItemEstruturaIett itemEstruturaIett, Set itemEstrutUploadIettups) {
        this.uploadTipoCategoriaUtc = uploadTipoCategoriaUtc;
        this.itemEstruturaIett = itemEstruturaIett;
        this.itemEstrutUploadIettups = itemEstrutUploadIettups;
    }

    /**
     *
     * @return
     */
    public Long getCodIettuc() {
        return this.codIettuc;
    }

    /**
     *
     * @param codIettuc
     */
    public void setCodIettuc(Long codIettuc) {
        this.codIettuc = codIettuc;
    }

    /**
     *
     * @return
     */
    public String getNomeIettuc() {
        return this.nomeIettuc;
    }

    /**
     *
     * @param nomeIettuc
     */
    public void setNomeIettuc(String nomeIettuc) {
        this.nomeIettuc = nomeIettuc;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoIettuc() {
        return this.indAtivoIettuc;
    }

    /**
     *
     * @param indAtivoIettuc
     */
    public void setIndAtivoIettuc(String indAtivoIettuc) {
        this.indAtivoIettuc = indAtivoIettuc;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoIettuc() {
        return this.dataInclusaoIettuc;
    }

    /**
     *
     * @param dataInclusaoIettuc
     */
    public void setDataInclusaoIettuc(Date dataInclusaoIettuc) {
        this.dataInclusaoIettuc = dataInclusaoIettuc;
    }

    /**
     *
     * @return
     */
    public String getImagemIettuc() {
        return this.imagemIettuc;
    }

    /**
     *
     * @param imagemIettuc
     */
    public void setImagemIettuc(String imagemIettuc) {
        this.imagemIettuc = imagemIettuc;
    }

    /**
     *
     * @return
     */
    public String getDescricaoIettuc() {
        return this.descricaoIettuc;
    }

    /**
     *
     * @param descricaoIettuc
     */
    public void setDescricaoIettuc(String descricaoIettuc) {
        this.descricaoIettuc = descricaoIettuc;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UploadTipoCategoriaUtc getUploadTipoCategoriaUtc() {
        return this.uploadTipoCategoriaUtc;
    }

    /**
     *
     * @param uploadTipoCategoriaUtc
     */
    public void setUploadTipoCategoriaUtc(ecar.pojo.UploadTipoCategoriaUtc uploadTipoCategoriaUtc) {
        this.uploadTipoCategoriaUtc = uploadTipoCategoriaUtc;
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
    public Set getItemEstrutUploadIettups() {
        return this.itemEstrutUploadIettups;
    }

    /**
     *
     * @param itemEstrutUploadIettups
     */
    public void setItemEstrutUploadIettups(Set itemEstrutUploadIettups) {
        this.itemEstrutUploadIettups = itemEstrutUploadIettups;
    }
    
    /**
     *
     * @return
     */
    public ecar.pojo.AcompReferenciaItemAri getAcompReferenciaItemAri() {
		return acompReferenciaItemAri;
	}

    /**
     *
     * @param acompReferenciaItemAri
     */
    public void setAcompReferenciaItemAri(
			ecar.pojo.AcompReferenciaItemAri acompReferenciaItemAri) {
		this.acompReferenciaItemAri = acompReferenciaItemAri;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIettuc", getCodIettuc())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrUplCategIettuc) ) return false;
        ItemEstrUplCategIettuc castOther = (ItemEstrUplCategIettuc) other;
        return new EqualsBuilder()
            .append(this.getCodIettuc(), castOther.getCodIettuc())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettuc())
            .toHashCode();
    }

}
