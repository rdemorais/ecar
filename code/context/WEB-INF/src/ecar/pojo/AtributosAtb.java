package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class AtributosAtb implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1374443426255204385L;

	/** identifier field */
    private Long codAtb;

    /** nullable persistent field */
    private String indAtivoAtb;

    /** nullable persistent field */
    private String labelPadraoAtb;

    /** nullable persistent field */
    private String nomeAtb;
    
    private String nomeFkAtb;

    private String codFkAtb;

    /** nullable persistent field */
    private String indExclusivoEstruturaAtb;

    /** nullable persistent field */
    private String indOpcionalAtb;

    /** persistent field */
    private Set estruturaAtributoEttats;
    
    /** persistent field */
    private ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga;
     
    private String DocumentacaoAtb;
    
    private ecar.pojo.FuncaoFun funcaoFun;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
	 * @since N/C 
     * @param indAtivoAtb
     * @param labelPadraoAtb
     * @param nomeAtb
     * @param codFkAtb
     * @param nomeFkAtb
     * @param indExclusivoEstruturaAtb
     * @param sisGrupoAtributoSga
     * @param estruturaAtributoEttats
     * @param DocumentacaoAtb 
     * @param indOpcionalAtb
     * @param funcaoFun
     */
    public AtributosAtb(String indAtivoAtb, String labelPadraoAtb, String nomeAtb, String nomeFkAtb, 
    		String codFkAtb, String indExclusivoEstruturaAtb, String indOpcionalAtb, Set estruturaAtributoEttats, 
    		ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga, String DocumentacaoAtb, FuncaoFun funcaoFun) {
        this.indAtivoAtb = indAtivoAtb;
        this.labelPadraoAtb = labelPadraoAtb;
        this.nomeAtb = nomeAtb;
        this.nomeFkAtb = nomeFkAtb;
        this.codFkAtb = codFkAtb;
        this.indExclusivoEstruturaAtb = indExclusivoEstruturaAtb;
        this.indOpcionalAtb = indOpcionalAtb;
        this.estruturaAtributoEttats = estruturaAtributoEttats;
        this.sisGrupoAtributoSga = sisGrupoAtributoSga;
        this.DocumentacaoAtb = DocumentacaoAtb;
        this.funcaoFun = funcaoFun;
    }

    /** default constructor */
    public AtributosAtb() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @author N/C
	 * @since N/C 
     * @param estruturaAtributoEttats
     */
    public AtributosAtb(Set estruturaAtributoEttats) {
        this.estruturaAtributoEttats = estruturaAtributoEttats;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getCodAtb() {
        return this.codAtb;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param codAtb
     */
    public void setCodAtb(Long codAtb) {
        this.codAtb = codAtb;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndAtivoAtb() {
        return this.indAtivoAtb;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param indAtivoAtb
     */
    public void setIndAtivoAtb(String indAtivoAtb) {
        this.indAtivoAtb = indAtivoAtb;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getLabelPadraoAtb() {
        return this.labelPadraoAtb;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param labelPadraoAtb
     */
    public void setLabelPadraoAtb(String labelPadraoAtb) {
        this.labelPadraoAtb = labelPadraoAtb;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getNomeAtb() {
        return this.nomeAtb;
    }

    /**
     * @param nomeAtb
     * @author N/C
	 * @since N/C 
     */
    public void setNomeAtb(String nomeAtb) {
        this.nomeAtb = nomeAtb;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getNomeFkAtb() {
        return this.nomeFkAtb;
    }

    /**
     * @param nomeFkAtb
     * @author N/C
	 * @since N/C 
     */
    public void setNomeFkAtb(String nomeFkAtb) {
        this.nomeFkAtb = nomeFkAtb;
    }
    
    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndExclusivoEstruturaAtb() {
        return this.indExclusivoEstruturaAtb;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param indExclusivoEstruturaAtb
     */
    public void setIndExclusivoEstruturaAtb(String indExclusivoEstruturaAtb) {
        this.indExclusivoEstruturaAtb = indExclusivoEstruturaAtb;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getIndOpcionalAtb() {
        return this.indOpcionalAtb;
    }

    /**
     * @param indOpcionalAtb
     * @author N/C
	 * @since N/C 
     */
    public void setIndOpcionalAtb(String indOpcionalAtb) {
        this.indOpcionalAtb = indOpcionalAtb;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
    public Set getEstruturaAtributoEttats() {
        return this.estruturaAtributoEttats;
    }

    /**
     * @param estruturaAtributoEttats
     * @author N/C
	 * @since N/C 
     */
    public void setEstruturaAtributoEttats(Set estruturaAtributoEttats) {
        this.estruturaAtributoEttats = estruturaAtributoEttats;
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codAtb", getCodAtb())
            .toString();
    }

    /**
     * @param other
     * @author N/C
	 * @since N/C 
	 * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof AtributosAtb) ) return false;
        AtributosAtb castOther = (AtributosAtb) other;
        return new EqualsBuilder()
            .append(this.getCodAtb(), castOther.getCodAtb())
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
            .append(getCodAtb())
            .toHashCode();
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String - (Returns the codFkAtb).
     */
    public String getCodFkAtb() {
        return codFkAtb;
    }
    /**
     * @param codFkAtb
     * @author N/C
	 * @since N/C 
     */
    public void setCodFkAtb(String codFkAtb) {
        this.codFkAtb = codFkAtb;
    }
    
    /**
     * Get para ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga
     * @author aleixo
     * @return ecar.pojo.SisGrupoAtributoSga
     */
    public ecar.pojo.SisGrupoAtributoSga getSisGrupoAtributoSga(){
    	return this.sisGrupoAtributoSga;
    }
    
    /**
     * Set para ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga
     * @author aleixo
     * @param sisGrupoAtributoSga
     */
    public void setSisGrupoAtributoSga(ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga){
    	this.sisGrupoAtributoSga = sisGrupoAtributoSga;
    }

    /**
     * @author Robson
     * @since 24/08/2007
     * @return String
     */
	public String getDocumentacaoAtb() {
		return DocumentacaoAtb;
	}

    /**
     * @param DocumentacaoAtb
     * @author Robson
     * @since 24/08/2007
     */
	public void setDocumentacaoAtb(String DocumentacaoAtb) {
		this.DocumentacaoAtb = DocumentacaoAtb;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.FuncaoFun getFuncaoFun() {
		return funcaoFun;
	}

        /**
         *
         * @param funcaoFun
         */
        public void setFuncaoFun(ecar.pojo.FuncaoFun funcaoFun) {
		this.funcaoFun = funcaoFun;
	}
}
