package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

/** @author Hibernate CodeGenerator */
public class ParametroPar implements Serializable {

    /**
     *
     */
    public static final Long IDENTIFICADOR_ITEM_ESTRUTURA = 1L;
        /**
         *
         */
        public static final Long DATA_ATUAL = 2L;
        /**
         *
         */
        public static final Long DATA_LIMITE_REALIZADO_FISICO = 3L;
        /**
         *
         */
        public static final Long DATA_INICIO_ACOMPANHAMENTO = 4L;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5528105977946214930L;

	/** identifier field */
    private Long codParametroPar;
    private String descricaoPar;
    private String tabelaPar;
    private String atributoPar;
    private Set servicoParametros;
    
    /** full constructor
     * @param codParametroPar
     * @param descricaoPar
     * @param tabelaPar
     * @param atributoPar
     * @param servicosSers
     */
	public ParametroPar(Long codParametroPar, String descricaoPar,
			String tabelaPar, String atributoPar, Set servicosSers) {
		super();
		this.codParametroPar = codParametroPar;
		this.descricaoPar = descricaoPar;
		this.tabelaPar = tabelaPar;
		this.atributoPar = atributoPar;
		this.servicoParametros = servicoParametros;
	}
	
	/** 
     * default constructor.<br>
     * 
     * @author N/C
     * @since N/C 
     */
	public ParametroPar() {
		super();
	}

	/**
	 * 
	 * @return
	 */
	public Long getCodParametroPar() {
		return codParametroPar;
	}

	
	/**
	 * 
         * @param codParametroPar
	 */
	public void setCodParametroPar(Long codParametroPar) {
		this.codParametroPar = codParametroPar;
	}

	/**
	 * 
	 * @return
	 */
	public String getDescricaoPar() {
		return descricaoPar;
	}

	/**
	 * 
         *
         * @param descricaoPar
         */
	public void setDescricaoPar(String descricaoPar) {
		this.descricaoPar = descricaoPar;
	}

	/**
	 * 
	 * @return
	 */
	public String getTabelaPar() {
		return tabelaPar;
	}

	/**
	 * 
         * @param tabelaPar
	 */
	public void setTabelaPar(String tabelaPar) {
		this.tabelaPar = tabelaPar;
	}

	/**
	 * 
	 * @return
	 */
	public String getAtributoPar() {
		return atributoPar;
	}

	/**
	 * 
         *
         * @param atributoPar
         */
	public void setAtributoPar(String atributoPar) {
		this.atributoPar = atributoPar;
	}

        /**
         *
         * @return
         */
        public Set getServicoParametros() {
		return servicoParametros;
	}

        /**
         *
         * @param servicoParametros
         */
        public void setServicoParametros(Set servicoParametros) {
		this.servicoParametros = servicoParametros;
	}

	
}
