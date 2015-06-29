package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 * @author Hibernate CodeGenerator
 * @since n/c
 * @version 0.2, 06/06/2007 
 */
public class ItemEstrutAcaoIetta implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -947743942972353486L;

	/** identifier field */
    private Long codIetta;

    /** nullable persistent field */
    private String indAtivoIetta;

    /** nullable persistent field */
    private Date dataInclusaoIetta;

    /** nullable persistent field */
    private Date dataIetta;

    /** nullable persistent field */
    private String descricaoIetta;

    /** persistent field */
    private ecar.pojo.ItemEstruturaIett itemEstruturaIett;

    /** persistent field */
    private UsuarioUsu usuarioUsu;
    
    private UsuarioUsu usuarioUsuManutencao;
    
    /* Mantis #2156 */
    private Set historicoIettaHs;
    private Boolean indExclusaoPosHistorico;
    
    
    
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

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsu() {
		return usuarioUsu;
	}

        /**
         *
         * @param usuarioUsu
         */
        public void setUsuarioUsu(UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
	}

        /** full constructor
         * @param indAtivoIetta
         * @param dataInclusaoIetta
         * @param dataIetta
         * @param itemEstruturaIett
         * @param descricaoIetta
         * @param usuarioUsu
         */
    public ItemEstrutAcaoIetta(String indAtivoIetta, Date dataInclusaoIetta, Date dataIetta, String descricaoIetta, ecar.pojo.ItemEstruturaIett itemEstruturaIett, UsuarioUsu usuarioUsu) {
        this.indAtivoIetta = indAtivoIetta;
        this.dataInclusaoIetta = dataInclusaoIetta;
        this.dataIetta = dataIetta;
        this.descricaoIetta = descricaoIetta;
        this.itemEstruturaIett = itemEstruturaIett;
        this.usuarioUsu = usuarioUsu;
    }

    /** default constructor */
    public ItemEstrutAcaoIetta() {
    }

    /** minimal constructor
     * @param itemEstruturaIett
     * @param usuarioUsu
     */
    public ItemEstrutAcaoIetta(ecar.pojo.ItemEstruturaIett itemEstruturaIett, UsuarioUsu usuarioUsu) {
        this.itemEstruturaIett = itemEstruturaIett;
        this.usuarioUsu = usuarioUsu;
    }

    /**
     *
     * @return
     */
    public Long getCodIetta() {
        return this.codIetta;
    }

    /**
     *
     * @param codIetta
     */
    public void setCodIetta(Long codIetta) {
        this.codIetta = codIetta;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoIetta() {
        return this.indAtivoIetta;
    }

    /**
     *
     * @param indAtivoIetta
     */
    public void setIndAtivoIetta(String indAtivoIetta) {
        this.indAtivoIetta = indAtivoIetta;
    }

    /**
     *
     * @return
     */
    public Date getDataInclusaoIetta() {
        return this.dataInclusaoIetta;
    }

    /**
     *
     * @param dataInclusaoIetta
     */
    public void setDataInclusaoIetta(Date dataInclusaoIetta) {
        this.dataInclusaoIetta = dataInclusaoIetta;
    }

    /**
     *
     * @return
     */
    public Date getDataIetta() {
        return this.dataIetta;
    }

    /**
     *
     * @param dataIetta
     */
    public void setDataIetta(Date dataIetta) {
        this.dataIetta = dataIetta;
    }

    /**
     *
     * @return
     */
    public String getDescricaoIetta() {
        return this.descricaoIetta;
    }

    /**
     *
     * @param descricaoIetta
     */
    public void setDescricaoIetta(String descricaoIetta) {
        this.descricaoIetta = descricaoIetta;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codIetta", getCodIetta())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemEstrutAcaoIetta) ) return false;
        ItemEstrutAcaoIetta castOther = (ItemEstrutAcaoIetta) other;
        return new EqualsBuilder()
            .append(this.getCodIetta(), castOther.getCodIetta())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodIetta())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Set getHistoricoIettaHs() {
		return historicoIettaHs;
	}

    /**
     *
     * @param historicoIettaHs
     */
    public void setHistoricoIettaHs(Set historicoIettaHs) {
		this.historicoIettaHs = historicoIettaHs;
	}

}
