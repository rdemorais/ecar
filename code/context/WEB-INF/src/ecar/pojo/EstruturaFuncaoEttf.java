package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class EstruturaFuncaoEttf implements Serializable, PaiFilho {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4624948831235192351L;

	/** identifier field */
    private ecar.pojo.EstruturaFuncaoEttfPK comp_id;

    /** nullable persistent field */
    private String labelEttf;

    /** nullable persistent field */
    private String indListagemImpressCompEttf;

    /** nullable persistent field */
    private String indListagemImpressaResEttf;

    /** nullable persistent field */
    private String indRevisaoEttf;

    /** nullable persistent field */
    private ecar.pojo.EstruturaEtt estruturaEtt;

    /** nullable persistent field */
    private ecar.pojo.FuncaoFun funcaoFun;
    
    /** nullable persistent field */
    private String dicaEttf;
    
    private String indPodeBloquearEttf;
    
    private Integer seqApresentacaoTelaEttf;
  
    private Integer seqApresentacaoRelatorioEttf;
    
    private String documentacaoEttf;
    
    private String indExibirHistoricoEttf;
    
    /** 
     * Lista das Funções de acompanhamento que tem permissão de acesso a Função de estrutura 
     * mesmo quando esta está bloqueada. 
     */ 
    private Set<TipoFuncAcompTpfa> libTipoFuncAcompTpfas;
    

    /** full constructor
     * @param comp_id
     * @param labelEttf
     * @param documentacaoEttf
     * @param indListagemImpressaResEttf
     * @param funcaoFun
     * @param indListagemImpressCompEttf
     * @param indExibirHistoricoEttf
     * @param indRevisaoEttf
     * @param estruturaEtt
     * @param seqApresentacaoTelaEttf
     * @param seqApresentacaoRelatorioEttf
     * @param libTipoFuncAcompTpfa
     * @param dicaEttf
     */
    public EstruturaFuncaoEttf(ecar.pojo.EstruturaFuncaoEttfPK comp_id, String labelEttf, String indListagemImpressCompEttf, String indListagemImpressaResEttf, ecar.pojo.EstruturaEtt estruturaEtt, ecar.pojo.FuncaoFun funcaoFun, String indRevisaoEttf, String dicaEttf, Integer seqApresentacaoTelaEttf, Integer seqApresentacaoRelatorioEttf, String documentacaoEttf, Set<TipoFuncAcompTpfa> libTipoFuncAcompTpfa, String indExibirHistoricoEttf) {
        this.comp_id = comp_id;
        this.labelEttf = labelEttf;
        this.indListagemImpressCompEttf = indListagemImpressCompEttf;
        this.indListagemImpressaResEttf = indListagemImpressaResEttf;
        this.estruturaEtt = estruturaEtt;
        this.funcaoFun = funcaoFun;
        this.indRevisaoEttf = indRevisaoEttf;
        this.dicaEttf = dicaEttf;
        this.seqApresentacaoTelaEttf = seqApresentacaoTelaEttf;
        this.seqApresentacaoRelatorioEttf = seqApresentacaoRelatorioEttf;
        this.documentacaoEttf = documentacaoEttf;
        this.libTipoFuncAcompTpfas = libTipoFuncAcompTpfa;
        this.indExibirHistoricoEttf = indExibirHistoricoEttf;
    }

    /** default constructor */
    public EstruturaFuncaoEttf() {
    }

    /** minimal constructor
     * @param comp_id
     */
    public EstruturaFuncaoEttf(ecar.pojo.EstruturaFuncaoEttfPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EstruturaFuncaoEttfPK getComp_id() {
        return this.comp_id;
    }

    /**
     *
     * @param comp_id
     */
    public void setComp_id(ecar.pojo.EstruturaFuncaoEttfPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *
     * @return
     */
    public String getLabelEttf() {
        return this.labelEttf;
    }

    /**
     *
     * @param labelEttf
     */
    public void setLabelEttf(String labelEttf) {
        this.labelEttf = labelEttf;
    }

    /**
     *
     * @return
     */
    public String getIndListagemImpressCompEttf() {
        return this.indListagemImpressCompEttf;
    }

    /**
     *
     * @param indListagemImpressCompEttf
     */
    public void setIndListagemImpressCompEttf(String indListagemImpressCompEttf) {
        this.indListagemImpressCompEttf = indListagemImpressCompEttf;
    }

    /**
     *
     * @return
     */
    public String getIndListagemImpressaResEttf() {
        return this.indListagemImpressaResEttf;
    }

    /**
     *
     * @param indListagemImpressaResEttf
     */
    public void setIndListagemImpressaResEttf(String indListagemImpressaResEttf) {
        this.indListagemImpressaResEttf = indListagemImpressaResEttf;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.EstruturaEtt getEstruturaEtt() {
        return this.estruturaEtt;
    }

    /**
     *
     * @param estruturaEtt
     */
    public void setEstruturaEtt(ecar.pojo.EstruturaEtt estruturaEtt) {
        this.estruturaEtt = estruturaEtt;
    }

    /**
     *
     * @return
     */
    public ecar.pojo.FuncaoFun getFuncaoFun() {
        return this.funcaoFun;
    }

    /**
     *
     * @param funcaoFun
     */
    public void setFuncaoFun(ecar.pojo.FuncaoFun funcaoFun) {
        this.funcaoFun = funcaoFun;
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
        if ( !(other instanceof EstruturaFuncaoEttf) ) return false;
        EstruturaFuncaoEttf castOther = (EstruturaFuncaoEttf) other;
        return new EqualsBuilder()
            .append(this.getEstruturaEtt(), castOther.getEstruturaEtt())
            .append(this.getFuncaoFun(), castOther.getFuncaoFun())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }
    
    /**
     *
     */
    public void atribuirPKPai() {
        comp_id = new EstruturaFuncaoEttfPK();        
        comp_id.setCodEtt(this.getEstruturaEtt().getCodEtt());
        comp_id.setCodFun(this.getFuncaoFun().getCodFun());
    }

    /**
     *
     * @return
     */
    public String getIndRevisaoEttf() {
		return indRevisaoEttf;
	}

        /**
         *
         * @param indRevisaoEttf
         */
        public void setIndRevisaoEttf(String indRevisaoEttf) {
		this.indRevisaoEttf = indRevisaoEttf;
	}
	
        /**
         *
         * @return
         */
        public String getDicaEttf() {
		return dicaEttf;
	}

        /**
         *
         * @param dicaEttf
         */
        public void setDicaEttf(String dicaEttf) {
		this.dicaEttf = dicaEttf;
	}

        /**
         *
         * @return
         */
        public Integer getSeqApresentacaoRelatorioEttf() {
		return seqApresentacaoRelatorioEttf;
	}

        /**
         *
         * @param seqApresentacaoRelatorioEttf
         */
        public void setSeqApresentacaoRelatorioEttf(Integer seqApresentacaoRelatorioEttf) {
		this.seqApresentacaoRelatorioEttf = seqApresentacaoRelatorioEttf;
	}

        /**
         *
         * @return
         */
        public Integer getSeqApresentacaoTelaEttf() {
		return seqApresentacaoTelaEttf;
	}

        /**
         *
         * @param seqApresentacaoTelaEttf
         */
        public void setSeqApresentacaoTelaEttf(Integer seqApresentacaoTelaEttf) {
		this.seqApresentacaoTelaEttf = seqApresentacaoTelaEttf;
	}

        /**
         *
         * @return
         */
        public String getIndPodeBloquearEttf() {
		return indPodeBloquearEttf;
	}

        /**
         *
         * @param indPodeBloquearEttf
         */
        public void setIndPodeBloquearEttf(String indPodeBloquearEttf) {
		this.indPodeBloquearEttf = indPodeBloquearEttf;
	}

        /**
         *
         * @return
         */
        public String getDocumentacaoEttf() {
		return documentacaoEttf;
	}

        /**
         *
         * @param documentacaoEttf
         */
        public void setDocumentacaoEttf(String documentacaoEttf) {
		this.documentacaoEttf = documentacaoEttf;
	}

        /**
         *
         * @return
         */
        public Set<TipoFuncAcompTpfa> getLibTipoFuncAcompTpfas() {
		return libTipoFuncAcompTpfas;
	}

        /**
         *
         * @param libTipoFuncAcompTpfas
         */
        public void setLibTipoFuncAcompTpfas(
			Set<TipoFuncAcompTpfa> libTipoFuncAcompTpfas) {
		this.libTipoFuncAcompTpfas = libTipoFuncAcompTpfas;
	}
	
        /**
         *
         * @return
         */
        public String getIndExibirHistoricoEttf(){
		return indExibirHistoricoEttf;
	}
	
        /**
         *
         * @param indExibirHistoricoEttf
         */
        public void setIndExibirHistoricoEttf(String indExibirHistoricoEttf){
		this.indExibirHistoricoEttf = indExibirHistoricoEttf;
	}



}
