package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Cor implements Serializable {

	private static final long serialVersionUID = 6635146529198913952L;
				/* Valores usados para cores definidas no sistema */
        /**
         *
         */
        public static final String NAO_LIBERADO = "N/L";
        /**
         *
         */
        public static final String NAO_ACOMPANHADO = "N/A";
        /**
         *
         */
        public static final String BRANCO = "BRANCO";
	
	/* Nome usados na interface para cores definidas no sistema */
        /**
         *
         */
        public static final String LABEL_NAO_LIBERADO = "N/L";
        /**
         *
         */
        public static final String LABEL_NAO_ACOMPANHADO = "N/A";
        /**
         *
         */
        public static final String LABEL_BRANCO = "N�o informado";

	/** identifier field */
    private Long codCor;

    /** nullable persistent field */
    private String significadoCor;

    /** nullable persistent field */
    private String nomeCor;
    
    /** nullable persistent field */
    private Long ordemCor;
    
    private String codCorGrafico;
    
    /** nullable persistent field */
    private String indPosicoesGeraisCor;
    
    /** nullable persistent field */
    private String indPontoCriticoCor;
    
    /** nullable persistent field */
    private String indIndicadoresFisicosCor;
    
    /** nullable persistent field */
    private String caminhoImagemPontoCriticoCor;
    
    /** nullable persistent field */
    private String caminhoImagemIndResulCor;

    /** persistent field */
    private Set acompRelatorioArels;

    /** persistent field */
    private Set pontoCriticoCorPtccores;

    /** persistent field */
    private Set itemEstrutMarcadorIettms;
    
    /** persistent field */
    private Set corTipoFuncAcompCtfas;
    
    /** persistent field */
    private Set itemEstrtIndResulCorIettrcores;

    /** persistent field */
    private Set faixas;

    /** 
     * full constructor.<br>
     * 
     * @author N/C
	 * @since N/C
     * @param significadoCor
     * @param nomeCor
     * @param acompRelatorioArels
     * @param itemEstrutMarcadorIettms
     * @param pontoCriticoCorPtccores
     * @param ordemCor
     * @param indPosicoesGeraisCor
     * @param indPontoCriticoCor
     * @param indIndicadoresFisicosCor
     * @param caminhoImagemPontoCriticoCor
     * @param codCorGrafico
     * @param caminhoImagemIndResulCor
     * @param itemEstrtIndResulCorIettrcores
     * @param corTipoFuncAcompCtfas
	 */
    public Cor(String significadoCor, String nomeCor, Set acompRelatorioArels, Set itemEstrutMarcadorIettms, Set pontoCriticoCorPtccores, Long ordemCor, String indPosicoesGeraisCor, String indPontoCriticoCor, String indIndicadoresFisicosCor, String caminhoImagemPontoCriticoCor, String caminhoImagemIndResulCor, Set corTipoFuncAcompCtfas, String codCorGrafico, Set itemEstrtIndResulCorIettrcores, Set faixas ) {
        this.significadoCor = significadoCor;
        this.nomeCor = nomeCor;
        this.acompRelatorioArels = acompRelatorioArels;
        this.itemEstrutMarcadorIettms = itemEstrutMarcadorIettms;
        this.pontoCriticoCorPtccores = pontoCriticoCorPtccores;
        this.ordemCor = ordemCor;
        this.indPosicoesGeraisCor = indPosicoesGeraisCor;
        this.indPontoCriticoCor = indPontoCriticoCor;
        this.indIndicadoresFisicosCor = indIndicadoresFisicosCor;
        this.caminhoImagemPontoCriticoCor = caminhoImagemPontoCriticoCor;
        this.caminhoImagemIndResulCor = caminhoImagemIndResulCor;
        this.corTipoFuncAcompCtfas = corTipoFuncAcompCtfas;
        this.codCorGrafico = codCorGrafico;
        this.itemEstrtIndResulCorIettrcores = itemEstrtIndResulCorIettrcores;
        this.faixas = faixas;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
	public Set getCorTipoFuncAcompCtfas() {
		return corTipoFuncAcompCtfas;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param corTipoFuncAcompCtfas
	 */
	public void setCorTipoFuncAcompCtfas(Set corTipoFuncAcompCtfas) {
		this.corTipoFuncAcompCtfas = corTipoFuncAcompCtfas;
	}

	/** default constructor */
    public Cor() {
    }

    /** 
     * minimal constructor.<br>
     * 
     * @author N/C
	 * @since N/C 
     * @param acompRelatorioArels
     * @param pontoCriticoCorPtccores
     * @param itemEstrutMarcadorIettms
     * @param itemEstrtIndResulCorIettrcores
     */
    public Cor(Set acompRelatorioArels, Set itemEstrutMarcadorIettms, Set pontoCriticoCorPtccores, Set itemEstrtIndResulCorIettrcores) {
        this.acompRelatorioArels = acompRelatorioArels;
        this.itemEstrutMarcadorIettms = itemEstrutMarcadorIettms;
        this.pontoCriticoCorPtccores = pontoCriticoCorPtccores;
        this.itemEstrtIndResulCorIettrcores = itemEstrtIndResulCorIettrcores;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Long
     */
    public Long getCodCor() {
        return this.codCor;
    }

    /**
     * @param codCor
     * @author N/C
	 * @since N/C 
     */
    public void setCodCor(Long codCor) {
        this.codCor = codCor;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getSignificadoCor() {
        return this.significadoCor;
    }

    /**
     * @param significadoCor
     * @author N/C
	 * @since N/C 
     */
    public void setSignificadoCor(String significadoCor) {
        this.significadoCor = significadoCor;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return String
     */
    public String getNomeCor() {
        return this.nomeCor;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param nomeCor
     */
    public void setNomeCor(String nomeCor) {
        this.nomeCor = nomeCor;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
    public Set getAcompRelatorioArels() {
        return this.acompRelatorioArels;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @param acompRelatorioArels
     */
    public void setAcompRelatorioArels(Set acompRelatorioArels) {
        this.acompRelatorioArels = acompRelatorioArels;
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
    public Set getItemEstrutMarcadorIettms() {
        return this.itemEstrutMarcadorIettms;
    }

    /**
     * @param itemEstrutMarcadorIettms
     * @author N/C
	 * @since N/C 
     */
    public void setItemEstrutMarcadorIettms(Set itemEstrutMarcadorIettms) {
        this.itemEstrutMarcadorIettms = itemEstrutMarcadorIettms;
    }

    /**
     * @author N/C
	 * @since N/C 
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codCor", getCodCor())
            .append("nomeCor", getNomeCor())
            .append("caminhoImagemIndResulCor", getCaminhoImagemIndResulCor())
            .toString();
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
        if ( !(other instanceof Cor) ) return false;
        Cor castOther = (Cor) other;
        return new EqualsBuilder()
            .append(this.getCodCor(), castOther.getCodCor())
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
            .append(getCodCor())
            .toHashCode();
    }

    /**
     * @author N/C
	 * @since N/C 
     * @return Set
     */
	public Set getPontoCriticoCorPtccores() {
		return pontoCriticoCorPtccores;
	}

	/**
         * @param pontoCriticoCorPtccores
         * @author N/C
	 * @since N/C 
	 */
	public void setPontoCriticoCorPtccores(Set pontoCriticoCorPtccores) {
		this.pontoCriticoCorPtccores = pontoCriticoCorPtccores;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return Long
	 */
	public Long getOrdemCor() {
		return ordemCor;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param ordemCor
	 */
	public void setOrdemCor(Long ordemCor) {
		this.ordemCor = ordemCor;
	}
	
	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getIndPosicoesGeraisCor() {
		return indPosicoesGeraisCor;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param indPosicoesGeraisCor
	 */
	public void setIndPosicoesGeraisCor(String indPosicoesGeraisCor) {
		this.indPosicoesGeraisCor = indPosicoesGeraisCor;
	}
	
	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getIndPontoCriticoCor() {
		return indPontoCriticoCor;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param indPontoCriticoCor
	 */
	public void setIndPontoCriticoCor(String indPontoCriticoCor) {
		this.indPontoCriticoCor = indPontoCriticoCor;
	}
	
	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getIndIndicadoresFisicosCor() {
		return indIndicadoresFisicosCor;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param indIndicadoresFisicosCor
	 */
	public void setIndIndicadoresFisicosCor(String indIndicadoresFisicosCor) {
		this.indIndicadoresFisicosCor = indIndicadoresFisicosCor;
	}

	/**
	 * @author N/C
	 * @since N/C 
	 * @return String
	 */
	public String getCaminhoImagemPontoCriticoCor() {
		return caminhoImagemPontoCriticoCor;
	}

	/**
	 * @author N/C
	 * @since N/C 
         * @param caminhoImagemPontoCriticoCor
	 */
	public void setCaminhoImagemPontoCriticoCor(String caminhoImagemPontoCriticoCor) {
		this.caminhoImagemPontoCriticoCor = caminhoImagemPontoCriticoCor;
	}

        /**
         *
         * @return
         */
        public String getCodCorGrafico() {
		return codCorGrafico;
	}

        /**
         *
         * @param codCorGrafico
         */
        public void setCodCorGrafico(String codCorGrafico) {
		this.codCorGrafico = codCorGrafico;
	}

        /**
         *
         * @return
         */
        public Set getItemEstrtIndResulCorIettrcores() {
		return itemEstrtIndResulCorIettrcores;
	}

        /**
         *
         * @param itemEstrtIndResulCorIettrcores
         */
        public void setItemEstrtIndResulCorIettrcores(Set itemEstrtIndResulCorIettrcores) {
		this.itemEstrtIndResulCorIettrcores = itemEstrtIndResulCorIettrcores;
	}

        /**
         *
         * @return
         */
        public String getCaminhoImagemIndResulCor() {
		return caminhoImagemIndResulCor;
	}

        /**
         *
         * @param caminhoImagemIndResulCor
         */
        public void setCaminhoImagemIndResulCor(String caminhoImagemIndResulCor) {
		this.caminhoImagemIndResulCor = caminhoImagemIndResulCor;
	}

				public Set getFaixas() {
					return faixas;
				}

				public void setFaixas(Set faixas) {
					this.faixas = faixas;
				}

        
        
}
