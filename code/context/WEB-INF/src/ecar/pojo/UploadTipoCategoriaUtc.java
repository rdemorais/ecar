package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class UploadTipoCategoriaUtc implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -121459432706941595L;

	/** identifier field */
    private Long codUtc;

    /** nullable persistent field */
    private String nomeUtc;

    /** persistent field */
    private Set itemEstrUplCategIettucs;

    /** persistent field */
    private Set uploadTipoArquivoUtas;
    
    /** nullable persistent field */
    private String urlJanelaUtc;

    /** full constructor
     * @param nomeUtc
     * @param itemEstrUplCategIettucs
     * @param uploadTipoArquivoUtas
     */
    public UploadTipoCategoriaUtc(String nomeUtc, Set itemEstrUplCategIettucs, Set uploadTipoArquivoUtas) {
        this.nomeUtc = nomeUtc;
        this.itemEstrUplCategIettucs = itemEstrUplCategIettucs;
        this.uploadTipoArquivoUtas = uploadTipoArquivoUtas;
    }

    /** default constructor */
    public UploadTipoCategoriaUtc() {
    }

    /** minimal constructor
     * @param itemEstrUplCategIettucs
     * @param uploadTipoArquivoUtas
     */
    public UploadTipoCategoriaUtc(Set itemEstrUplCategIettucs, Set uploadTipoArquivoUtas) {
        this.itemEstrUplCategIettucs = itemEstrUplCategIettucs;
        this.uploadTipoArquivoUtas = uploadTipoArquivoUtas;
    }

    /**
     *
     * @return
     */
    public Long getCodUtc() {
        return this.codUtc;
    }

    /**
     *
     * @param codUtc
     */
    public void setCodUtc(Long codUtc) {
        this.codUtc = codUtc;
    }

    /**
     *
     * @return
     */
    public String getNomeUtc() {
        return this.nomeUtc;
    }

    /**
     *
     * @param nomeUtc
     */
    public void setNomeUtc(String nomeUtc) {
        this.nomeUtc = nomeUtc;
    }

    /**
     *
     * @return
     */
    public Set getItemEstrUplCategIettucs() {
        return this.itemEstrUplCategIettucs;
    }

    /**
     *
     * @param itemEstrUplCategIettucs
     */
    public void setItemEstrUplCategIettucs(Set itemEstrUplCategIettucs) {
        this.itemEstrUplCategIettucs = itemEstrUplCategIettucs;
    }

    /**
     *
     * @return
     */
    public Set getUploadTipoArquivoUtas() {
        return this.uploadTipoArquivoUtas;
    }

    /**
     *
     * @param uploadTipoArquivoUtas
     */
    public void setUploadTipoArquivoUtas(Set uploadTipoArquivoUtas) {
        this.uploadTipoArquivoUtas = uploadTipoArquivoUtas;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codUtc", getCodUtc())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof UploadTipoCategoriaUtc) ) return false;
        UploadTipoCategoriaUtc castOther = (UploadTipoCategoriaUtc) other;
        return new EqualsBuilder()
            .append(this.getCodUtc(), castOther.getCodUtc())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodUtc())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public String getUrlJanelaUtc() {
		return urlJanelaUtc;
	}

        /**
         *
         * @param urlJanelaUtc
         */
        public void setUrlJanelaUtc(String urlJanelaUtc) {
		this.urlJanelaUtc = urlJanelaUtc;
	}

}
