package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Aba implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5528105977946214930L;

	/** identifier field */
    private Integer codAba;
    private String labelAba;
    private String exibePosicaoAba;
    private Integer ordemAba;
    private Set configuracaoCfgs;
    private String nomeAba;
    private String indGeral;
    private String abaSuperior;
    private FuncaoFun funcaoFun;
	private Set tipoacompAbasSisatributoTaabasatbs;
	private String ehPadraoRegistro;
	private String ehPadraoVisualizacao;

        /** full constructor
         * @param codAba
         * @param abaSuperior
         * @param exibePosicaoAba
         * @param labelAba
         * @param ordemAba
         * @param configuracaoCfgs
         * @param nomeAba
         * @param funcaoFun
         * @param tipoacompAbasSisatributoTaabasatbs
         */
    public Aba(Integer codAba, 
    			String labelAba, 
    			String exibePosicaoAba, 
    			Integer ordemAba, 
    			Set configuracaoCfgs, 
    			String nomeAba,
    			String abaSuperior,
    			FuncaoFun funcaoFun,
    			Set tipoacompAbasSisatributoTaabasatbs) {
        this.codAba = codAba;
        this.labelAba = labelAba;
        this.exibePosicaoAba = exibePosicaoAba;
        this.ordemAba = ordemAba;
        this.configuracaoCfgs = configuracaoCfgs;
        this.nomeAba = nomeAba;
        this.abaSuperior = abaSuperior;
        this.funcaoFun = funcaoFun;
        this.tipoacompAbasSisatributoTaabasatbs = tipoacompAbasSisatributoTaabasatbs;
    }

    /** 
     * default constructor.<br>
     * 
     */
    public Aba() {
    }

    /** 
     * minimal constructor
     * 
     * @author N/C
     * @since N/C
     * @param codAba
     * @param configuracaoCfgs
     */
    public Aba(Integer codAba, Set configuracaoCfgs) {
        this.codAba = codAba;
        this.configuracaoCfgs = configuracaoCfgs;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Integer
     */
    public Integer getCodAba() {
        return this.codAba;
    }

    /**
     * @param codAba
     * @author N/C
     * @since N/C
     */
    public void setCodAba(Integer codAba) {
        this.codAba = codAba;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getLabelAba() {
        return this.labelAba;
    }

    /**
     * @author N/C
     * @since N/C
     * @param labelAba
     */
    public void setLabelAba(String labelAba) {
        this.labelAba = labelAba;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    public String getExibePosicaoAba() {
        return this.exibePosicaoAba;
    }

    /**
     * @author N/C
     * @since N/C
     * @param exibePosicaoAba
     */
    public void setExibePosicaoAba(String exibePosicaoAba) {
        this.exibePosicaoAba = exibePosicaoAba;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Integer
     */
    public Integer getOrdemAba() {
        return this.ordemAba;
    }

    /**
     * @author N/C
     * @since N/C
     * @param ordemAba
     */
    public void setOrdemAba(Integer ordemAba) {
        this.ordemAba = ordemAba;
    }

    /**
     * @author N/C
     * @since N/C
     * @return Set
     */
    public Set getconfiguracaoCfgs() {
        return this.configuracaoCfgs;
    }

    /**
     * @param configuracaoCfgs
     * @author N/C
     * @since N/C
     */
    public void setconfiguracaoCfgs(Set configuracaoCfgs) {
        this.configuracaoCfgs = configuracaoCfgs;
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codAba", getCodAba())
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
        if ( !(other instanceof Aba) ) return false;
        Aba castOther = (Aba) other;
        return new EqualsBuilder()
            .append(this.getCodAba(), castOther.getCodAba())
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
            .append(getCodAba())
            .toHashCode();
    }

    /**
     * @author N/C
     * @since N/C
     * @return String
     */
	public String getNomeAba() {
		return nomeAba;
	}

	/**
	 * @author N/C
     * @since N/C
         * @param nomeAba
	 */
	public void setNomeAba(String nomeAba) {
		this.nomeAba = nomeAba;
	}

	/**
	 * @author N/C
     * @since N/C
	 * @return Set
	 */
	public Set getConfiguracaoCfgs() {
		return configuracaoCfgs;
	}

	/**
         * @param configuracaoCfgs
         * @author N/C
     * @since N/C
	 */
	public void setConfiguracaoCfgs(Set configuracaoCfgs) {
		this.configuracaoCfgs = configuracaoCfgs;
	}

        /**
         *
         * @return
         */
        public FuncaoFun getFuncaoFun() {
		return funcaoFun;
	}

        /**
         *
         * @param funcaoFun
         */
        public void setFuncaoFun(FuncaoFun funcaoFun) {
		this.funcaoFun = funcaoFun;
	}

        /**
         *
         * @return
         */
        public Set getTipoacompAbasSisatributoTaabasatbs() {
		return tipoacompAbasSisatributoTaabasatbs;
	}

        /**
         *
         * @param tipoacompAbasSisatributoTaabasatbs
         */
        public void setTipoacompAbasSisatributoTaabasatbs(
			Set tipoacompAbasSisatributoTaabasatbs) {
		this.tipoacompAbasSisatributoTaabasatbs = tipoacompAbasSisatributoTaabasatbs;
	}

        /**
         *
         * @return
         */
        public String getIndGeral() {
		return indGeral;
	}

        /**
         *
         * @param indGeral
         */
        public void setIndGeral(String indGeral) {
		this.indGeral = indGeral;
	}

        /**
         *
         * @return
         */
        public String getAbaSuperior() {
		return abaSuperior;
	}

        /**
         *
         * @param abaSuperior
         */
        public void setAbaSuperior(String abaSuperior) {
		this.abaSuperior = abaSuperior;
	}

		public String getEhPadraoRegistro() {
			return ehPadraoRegistro;
		}

		public void setEhPadraoRegistro(String ehPadraoRegistro) {
			this.ehPadraoRegistro = ehPadraoRegistro;
		}

		public String getEhPadraoVisualizacao() {
			return ehPadraoVisualizacao;
		}

		public void setEhPadraoVisualizacao(String ehPadraoVisualizacao) {
			this.ehPadraoVisualizacao = ehPadraoVisualizacao;
		}
}
