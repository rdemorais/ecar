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
public class ItemEstrutUploadIettup implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7942580892340816246L;

	/** identifier field */
    private Long codIettup;

    /** nullable persistent field */
    private Date dataInclusaoIettup;

    /** nullable persistent field */
    private String indAtivoIettup;

    /** nullable persistent field */
    private String nomeOriginalIettup;

    /** nullable persistent field */
    private Long tamanhoIettup;

    /** nullable persistent field */
    private String descricaoIettup;

    /** nullable persistent field */
    private String arquivoIettup;

    /** persistent field */
    private ecar.pojo.ItemEstrUplCategIettuc itemEstrUplCategIettuc;

    /** persistent field */
    private ecar.pojo.UploadTipoArquivoUta uploadTipoArquivoUta;

    /** persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** persistent field */
    private ecar.pojo.UsuarioUsu usuarioUsu;
    
    /** persistent field */
    private ecar.pojo.AcompRelatorioArel acompRelatorioArel;
    
    /* -- Mantis #2156 -- */
    private Boolean indExclusaoPosHistorico;
    private UsuarioUsu usuarioUsuManutencao;

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
         * @param dataInclusaoIettup
         * @param indAtivoIettup
         * @param nomeOriginalIettup
         * @param arquivoIettup
         * @param tamanhoIettup
         * @param descricaoIettup
         * @param uploadTipoArquivoUta
         * @param itemEstrUplCategIettuc
         * @param acompRelatorioArel
         * @param usuarioUsu
         * @param itemEstruturaIett
         */
    public ItemEstrutUploadIettup(Date dataInclusaoIettup, String indAtivoIettup, String nomeOriginalIettup, 
    							  Long tamanhoIettup, String descricaoIettup, String arquivoIettup, 
    							  ecar.pojo.ItemEstrUplCategIettuc itemEstrUplCategIettuc, 
    							  ecar.pojo.UploadTipoArquivoUta uploadTipoArquivoUta, 
    							  ecar.pojo.ItemEstruturaIett itemEstruturaIett, 
    							  ecar.pojo.UsuarioUsu usuarioUsu,
    							  ecar.pojo.AcompRelatorioArel acompRelatorioArel) {
        this.dataInclusaoIettup = dataInclusaoIettup;
        this.indAtivoIettup = indAtivoIettup;
        this.nomeOriginalIettup = nomeOriginalIettup;
        this.tamanhoIettup = tamanhoIettup;
        this.descricaoIettup = descricaoIettup;
        this.arquivoIettup = arquivoIettup;
        this.itemEstrUplCategIettuc = itemEstrUplCategIettuc;
        this.uploadTipoArquivoUta = uploadTipoArquivoUta;
        this.itemEstruturaIett = itemEstruturaIett;
        this.usuarioUsu = usuarioUsu;
        this.acompRelatorioArel = acompRelatorioArel;
    }

    /** default constructor */
    public ItemEstrutUploadIettup() {
    }

    /** minimal constructor
     * @param itemEstrUplCategIettuc
     * @param itemEstruturaIett
     * @param uploadTipoArquivoUta
     * @param usuarioUsu
     */
    public ItemEstrutUploadIettup(ecar.pojo.ItemEstrUplCategIettuc itemEstrUplCategIettuc, ecar.pojo.UploadTipoArquivoUta uploadTipoArquivoUta, ecar.pojo.ItemEstruturaIett itemEstruturaIett, ecar.pojo.UsuarioUsu usuarioUsu) {
        this.itemEstrUplCategIettuc = itemEstrUplCategIettuc;
        this.uploadTipoArquivoUta = uploadTipoArquivoUta;
        this.itemEstruturaIett = itemEstruturaIett;
        this.usuarioUsu = usuarioUsu;
    }

    /**
     *
     * @return
     */
    public Long getCodIettup() {
        return this.codIettup;
    }

    /**
     *
     * @param codIettup
     */
    public void setCodIettup(Long codIettup) {
        this.codIettup = codIettup;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoIettup() {
        return this.dataInclusaoIettup;
    }

    /**
     *
     * @param dataInclusaoIettup
     */
    public void setDataInclusaoIettup(Date dataInclusaoIettup) {
        this.dataInclusaoIettup = dataInclusaoIettup;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoIettup() {
        return this.indAtivoIettup;
    }

    /**
     *
     * @param indAtivoIettup
     */
    public void setIndAtivoIettup(String indAtivoIettup) {
        this.indAtivoIettup = indAtivoIettup;
    }

    /**
     *
     * @return
     */
    public String getNomeOriginalIettup() {
        return this.nomeOriginalIettup;
    }

    /**
     *
     * @param nomeOriginalIettup
     */
    public void setNomeOriginalIettup(String nomeOriginalIettup) {
        this.nomeOriginalIettup = nomeOriginalIettup;
    }

    /**
     *
     * @return
     */
    public Long getTamanhoIettup() {
        return this.tamanhoIettup;
    }

    /**
     *
     * @param tamanhoIettup
     */
    public void setTamanhoIettup(Long tamanhoIettup) {
        this.tamanhoIettup = tamanhoIettup;
    }

    /**
     *
     * @return
     */
    public String getDescricaoIettup() {
        return this.descricaoIettup;
    }

    /**
     *
     * @param descricaoIettup
     */
    public void setDescricaoIettup(String descricaoIettup) {
        this.descricaoIettup = descricaoIettup;
    }

    /**
     *
     * @return
     */
    public String getArquivoIettup() {
        return this.arquivoIettup;
    }

    /**
     *
     * @param arquivoIettup
     */
    public void setArquivoIettup(String arquivoIettup) {
        this.arquivoIettup = arquivoIettup;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.ItemEstrUplCategIettuc getItemEstrUplCategIettuc() {
        return this.itemEstrUplCategIettuc;
    }

    /**
     *
     * @param itemEstrUplCategIettuc
     */
    public void setItemEstrUplCategIettuc(ecar.pojo.ItemEstrUplCategIettuc itemEstrUplCategIettuc) {
        this.itemEstrUplCategIettuc = itemEstrUplCategIettuc;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.UploadTipoArquivoUta getUploadTipoArquivoUta() {
        return this.uploadTipoArquivoUta;
    }

    /**
     *
     * @param uploadTipoArquivoUta
     */
    public void setUploadTipoArquivoUta(ecar.pojo.UploadTipoArquivoUta uploadTipoArquivoUta) {
        this.uploadTipoArquivoUta = uploadTipoArquivoUta;
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
    public ecar.pojo.AcompRelatorioArel getAcompRelatorioArel() {
		return acompRelatorioArel;
	}

    /**
     *
     * @param acompRelatorioArel
     */
    public void setAcompRelatorioArel(
			ecar.pojo.AcompRelatorioArel acompRelatorioArel) {
		this.acompRelatorioArel = acompRelatorioArel;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIettup", getCodIettup())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrutUploadIettup) ) return false;
        ItemEstrutUploadIettup castOther = (ItemEstrutUploadIettup) other;
        return new EqualsBuilder()
            .append(this.getCodIettup(), castOther.getCodIettup())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIettup())
            .toHashCode();
    }

}
