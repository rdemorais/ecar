package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/** @author Hibernate CodeGenerator */
public class CorTipoFuncAcompCtfa implements Serializable, PaiFilho {

    
	private static final long serialVersionUID = -5273583228892375000L;

	/** identifier field */
    private ecar.pojo.CorTipoFuncAcompCtfaPK comp_id;

    private Cor cor;
    
    private TipoFuncAcompTpfa tipoFuncAcompTpfa;
    
    private String posicaoCtfa;
    
    private String caminhoImagemCtfa;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
	 * @since N/C 
     * @param cor
     * @param tipoFuncAcompTpfa 
     * @param posicaoCtfa
     * @param caminhoImagemCtfa
     */
    public CorTipoFuncAcompCtfa(Cor cor, TipoFuncAcompTpfa tipoFuncAcompTpfa, String posicaoCtfa, String caminhoImagemCtfa) {
        this.posicaoCtfa = posicaoCtfa;
        this.caminhoImagemCtfa = caminhoImagemCtfa;
    }

    /** default constructor */
    public CorTipoFuncAcompCtfa() {
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
	public String getCaminhoImagemCtfa() {
		return caminhoImagemCtfa;
	}

	/**
         * @param caminhoImagemCtfa
         * @author N/C
	 * @since N/C 
	 */
	public void setCaminhoImagemCtfa(String caminhoImagemCtfa) {
		this.caminhoImagemCtfa = caminhoImagemCtfa;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getPosicaoCtfa() {
		return posicaoCtfa;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param posicaoCtfa
	 */
	public void setPosicaoCtfa(String posicaoCtfa) {
		this.posicaoCtfa = posicaoCtfa;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param other
	 * @return boolean
	 */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof CorTipoFuncAcompCtfa) ) return false;
        CorTipoFuncAcompCtfa castOther = (CorTipoFuncAcompCtfa) other;
        return new EqualsBuilder()            
            .append(this.getCor(), castOther.getCor())
            .append(this.getTipoFuncAcompTpfa(), castOther.getTipoFuncAcompTpfa())
            .isEquals();
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

    /**
     * @author N/C
	 * @since N/C 
     */
    /* (non-Javadoc)
     * @see ecar.pojo.PaiFilho#atribuirPKPai()
     */
    public void atribuirPKPai() {
        comp_id = new CorTipoFuncAcompCtfaPK();        
        comp_id.setCodCor(this.getCor().getCodCor());
        comp_id.setCodTpfa(this.getTipoFuncAcompTpfa().getCodTpfa());
        comp_id.setPosicaoCtfa(this.getPosicaoCtfa());
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return ecar.pojo.CorTipoFuncAcompCtfaPK
     */
	public ecar.pojo.CorTipoFuncAcompCtfaPK getComp_id() {
		return comp_id;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param comp_id
	 */
	public void setComp_id(ecar.pojo.CorTipoFuncAcompCtfaPK comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Cor
	 */
	public Cor getCor() {
		return cor;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param cor
	 */
	public void setCor(Cor cor) {
		this.cor = cor;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return TipoFuncAcompTpfa
	 */
	public TipoFuncAcompTpfa getTipoFuncAcompTpfa() {
		return tipoFuncAcompTpfa;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @param tipoFuncAcompTpfa
	 */
	public void setTipoFuncAcompTpfa(TipoFuncAcompTpfa tipoFuncAcompTpfa) {
		this.tipoFuncAcompTpfa = tipoFuncAcompTpfa;
	}

}
