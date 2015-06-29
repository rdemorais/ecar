package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class UploadTipoArquivoUta implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7100816468707310007L;

	/** identifier field */
    private Long codUta;

    /** nullable persistent field */
    private String tagHtmlUta;

    /** nullable persistent field */
    private String descricaoUta;

    /** persistent field */
    private ecar.pojo.UploadTipoCategoriaUtc uploadTipoCategoriaUtc;

    /** persistent field */
    private Set itemEstrutUploadIettups;
    
    /* Mantis #2156 */
    private Set historicoIettupHs;

    /** full constructor
     * @param tagHtmlUta
     * @param descricaoUta
     * @param uploadTipoCategoriaUtc
     * @param itemEstrutUploadIettups
     */
    public UploadTipoArquivoUta(String tagHtmlUta, String descricaoUta, ecar.pojo.UploadTipoCategoriaUtc uploadTipoCategoriaUtc, Set itemEstrutUploadIettups) {
        this.tagHtmlUta = tagHtmlUta;
        this.descricaoUta = descricaoUta;
        this.uploadTipoCategoriaUtc = uploadTipoCategoriaUtc;
        this.itemEstrutUploadIettups = itemEstrutUploadIettups;
    }

    /** default constructor */
    public UploadTipoArquivoUta() {
    }

    /** minimal constructor
     * @param uploadTipoCategoriaUtc
     * @param itemEstrutUploadIettups
     */
    public UploadTipoArquivoUta(ecar.pojo.UploadTipoCategoriaUtc uploadTipoCategoriaUtc, Set itemEstrutUploadIettups) {
        this.uploadTipoCategoriaUtc = uploadTipoCategoriaUtc;
        this.itemEstrutUploadIettups = itemEstrutUploadIettups;
    }

    /**
     *
     * @return
     */
    public Long getCodUta() {
        return this.codUta;
    }

    /**
     *
     * @param codUta
     */
    public void setCodUta(Long codUta) {
        this.codUta = codUta;
    }

    /**
     *
     * @return
     */
    public String getTagHtmlUta() {
        return this.tagHtmlUta;
    }

    /**
     *
     * @param tagHtmlUta
     */
    public void setTagHtmlUta(String tagHtmlUta) {
        this.tagHtmlUta = tagHtmlUta;
    }

    /**
     *
     * @return
     */
    public String getDescricaoUta() {
        return this.descricaoUta;
    }

    /**
     *
     * @param descricaoUta
     */
    public void setDescricaoUta(String descricaoUta) {
        this.descricaoUta = descricaoUta;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codUta", getCodUta())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof UploadTipoArquivoUta) ) return false;
        UploadTipoArquivoUta castOther = (UploadTipoArquivoUta) other;
        return new EqualsBuilder()
            .append(this.getCodUta(), castOther.getCodUta())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodUta())
            .toHashCode();
    }

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

}
