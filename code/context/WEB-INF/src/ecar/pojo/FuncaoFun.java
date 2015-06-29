package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class FuncaoFun implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 690552054393430272L;

	/** identifier field */
    private Long codFun;

    /** nullable persistent field */
    private String indAtivoFun;

    /** nullable persistent field */
    private String labelPadraoFun;

    /** nullable persistent field */
    private String nomeFun;

    /** nullable persistent field */
    private String linkFuncaoFun;

    /** nullable persistent field */
    private String indExclusivoEstruturaFun;
    
    private String indOpcionalFun;
    
    /**
     * Indica se a função deve ser listada na função de copia dos itens 
     */
    private String indCopiaFun;

    /** persistent field */
    private Set estruturaFuncaoEttfs;
    
    private String documentacaoFun;
    
    private Set atributosAtbs;
    
    private String indPossuiAtributos;

    /**
     *
     * @return
     */
    public String getIndPossuiAtributos() {
		return indPossuiAtributos;
	}

    /**
     *
     * @param indPossuiAtributos
     */
    public void setIndPossuiAtributos(String indPossuiAtributos) {
		this.indPossuiAtributos = indPossuiAtributos;
	}

        /**
         *
         * @return
         */
        public String getDocumentacaoFun() {
		return documentacaoFun;
	}

        /**
         *
         * @param documentacaoFun
         */
        public void setDocumentacaoFun(String documentacaoFun) {
		this.documentacaoFun = documentacaoFun;
	}

        /** full constructor
         * @param indAtivoFun
         * @param nomeFun
         * @param labelPadraoFun
         * @param atributosAtbs
         * @param documentacaoFun
         * @param linkFuncaoFun
         * @param indOpcionalFun
         * @param indExclusivoEstruturaFun
         * @param estruturaFuncaoEttfs
         */
    public FuncaoFun(String indAtivoFun, String labelPadraoFun, String nomeFun, String linkFuncaoFun, String indExclusivoEstruturaFun, String indOpcionalFun, Set estruturaFuncaoEttfs, String documentacaoFun, Set atributosAtbs) {
        this.indAtivoFun = indAtivoFun;
        this.labelPadraoFun = labelPadraoFun;
        this.nomeFun = nomeFun;
        this.linkFuncaoFun = linkFuncaoFun;
        this.indExclusivoEstruturaFun = indExclusivoEstruturaFun;
        this.indOpcionalFun = indOpcionalFun;
        this.estruturaFuncaoEttfs = estruturaFuncaoEttfs;
        this.documentacaoFun = documentacaoFun;
        this.atributosAtbs = atributosAtbs;
    }

    /** default constructor */
    public FuncaoFun() {
    }

    /** minimal constructor
     * @param estruturaFuncaoEttfs
     */
    public FuncaoFun(Set estruturaFuncaoEttfs) {
        this.estruturaFuncaoEttfs = estruturaFuncaoEttfs;
    }

    /**
     *
     * @return
     */
    public Long getCodFun() {
        return this.codFun;
    }

    /**
     *
     * @param codFun
     */
    public void setCodFun(Long codFun) {
        this.codFun = codFun;
    }

    /**
     *
     * @return
     */
    public String getIndAtivoFun() {
        return this.indAtivoFun;
    }

    /**
     *
     * @param indAtivoFun
     */
    public void setIndAtivoFun(String indAtivoFun) {
        this.indAtivoFun = indAtivoFun;
    }

    /**
     *
     * @return
     */
    public String getLabelPadraoFun() {
        return this.labelPadraoFun;
    }

    /**
     *
     * @param labelPadraoFun
     */
    public void setLabelPadraoFun(String labelPadraoFun) {
        this.labelPadraoFun = labelPadraoFun;
    }

    /**
     *
     * @return
     */
    public String getNomeFun() {
        return this.nomeFun;
    }

    /**
     *
     * @param nomeFun
     */
    public void setNomeFun(String nomeFun) {
        this.nomeFun = nomeFun;
    }

    /**
     *
     * @return
     */
    public String getLinkFuncaoFun() {
        return this.linkFuncaoFun;
    }

    /**
     *
     * @param linkFuncaoFun
     */
    public void setLinkFuncaoFun(String linkFuncaoFun) {
        this.linkFuncaoFun = linkFuncaoFun;
    }

    /**
     *
     * @return
     */
    public String getIndExclusivoEstruturaFun() {
        return this.indExclusivoEstruturaFun;
    }

    /**
     *
     * @param indExclusivoEstruturaFun
     */
    public void setIndExclusivoEstruturaFun(String indExclusivoEstruturaFun) {
        this.indExclusivoEstruturaFun = indExclusivoEstruturaFun;
    }

    /**
     *
     * @return
     */
    public Set getEstruturaFuncaoEttfs() {
        return this.estruturaFuncaoEttfs;
    }

    /**
     *
     * @param estruturaFuncaoEttfs
     */
    public void setEstruturaFuncaoEttfs(Set estruturaFuncaoEttfs) {
        this.estruturaFuncaoEttfs = estruturaFuncaoEttfs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codFun", getCodFun())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof FuncaoFun) ) return false;
        FuncaoFun castOther = (FuncaoFun) other;
        return new EqualsBuilder()
            .append(this.getCodFun(), castOther.getCodFun())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodFun())
            .toHashCode();
    }

    /**
     * @return Returns the indOpcionalFun.
     */
    public String getIndOpcionalFun() {
        return indOpcionalFun;
    }
    /**
     * @param indOpcionalFun The indOpcionalFun to set.
     */
    public void setIndOpcionalFun(String indOpcionalFun) {
        this.indOpcionalFun = indOpcionalFun;
    }

    /**
     *
     * @return
     */
    public Set getAtributosAtbs() {
		return atributosAtbs;
	}

    /**
     *
     * @param atributosAtbs
     */
    public void setAtributosAtbs(Set atributosAtbs) {
		this.atributosAtbs = atributosAtbs;
	}

    /**
     *
     * @return
     */
    public String getIndCopiaFun() {
		return indCopiaFun;
	}

        /**
         *
         * @param indCopiaFun
         */
        public void setIndCopiaFun(String indCopiaFun) {
		this.indCopiaFun = indCopiaFun;
	}
}
