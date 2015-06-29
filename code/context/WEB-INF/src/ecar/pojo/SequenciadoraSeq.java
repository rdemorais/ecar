package ecar.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SequenciadoraSeq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6712671501931623071L;

	/** identifier field */
	private Long codSeq;

	/** nullable persistent field */
	//private String tabelaSeq;

	/** nullable persistent field */
	private Long sequenciaSeq;

	private SisAtributoSatb atributoLivreSistema;

	private Integer ano;

	// private ItemEstruturaIett itemEstrutura;
	private EstruturaEtt estrutura;

	private FuncaoFun funcao;

        /**
         *
         * @return
         */
        public SisAtributoSatb getAtributoLivreSistema() {
		return atributoLivreSistema;
	}

        /**
         *
         * @param atributoLivreSistema
         */
        public void setAtributoLivreSistema(SisAtributoSatb atributoLivreSistema) {
		this.atributoLivreSistema = atributoLivreSistema;
	}

        /**
         *
         * @return
         */
        public Integer getAno() {
		return ano;
	}

        /**
         *
         * @param ano
         */
        public void setAno(Integer ano) {
		this.ano = ano;
	}

        /**
         *
         * @return
         */
        public EstruturaEtt getEstrutura() {
		return estrutura;
	}

        /**
         *
         * @param estrutura
         */
        public void setEstrutura(EstruturaEtt estrutura) {
		this.estrutura = estrutura;
	}

        /**
         *
         * @return
         */
        public FuncaoFun getFuncao() {
		return funcao;
	}

        /**
         *
         * @param funcao
         */
        public void setFuncao(FuncaoFun funcao) {
		this.funcao = funcao;
	}

	/** full constructor */
	/*public SequenciadoraSeq(String tabelaSeq, Long sequenciaSeq) {
		this.tabelaSeq = tabelaSeq;
		this.sequenciaSeq = sequenciaSeq;
	}*/

	/** default constructor */
	public SequenciadoraSeq() {
	}

        /**
         *
         * @return
         */
        public Long getCodSeq() {
		return this.codSeq;
	}

        /**
         *
         * @param codSeq
         */
        public void setCodSeq(Long codSeq) {
		this.codSeq = codSeq;
	}

	/*public String getTabelaSeq() {
		return this.tabelaSeq;
	}

	public void setTabelaSeq(String tabelaSeq) {
		this.tabelaSeq = tabelaSeq;
	}*/

        /**
         *
         * @return
         */
        public Long getSequenciaSeq() {
		return this.sequenciaSeq;
	}

	private void setSequenciaSeq(Long sequenciaSeq) {
		this.sequenciaSeq = sequenciaSeq;
	}

    @Override
	public String toString() {
		return new ToStringBuilder(this).append("codSeq", getCodSeq()).toString();
	}

    @Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof SequenciadoraSeq))
			return false;
		SequenciadoraSeq castOther = (SequenciadoraSeq) other;
		return new EqualsBuilder().append(this.getCodSeq(), castOther.getCodSeq()).isEquals();
	}

    @Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCodSeq()).toHashCode();
	}

    /**
     *
     * @return
     */
    public Long incrementar() {

		Long valorAtual = this.getSequenciaSeq();
		
		Long novoValor = ++valorAtual;
		
		this.setSequenciaSeq(novoValor);

		return this.getSequenciaSeq();
	}


    /**
     *
     * @return
     */
    public Long inicializar() {

		Long valorAtual = new Long(1); 
		
		this.setSequenciaSeq(valorAtual);

		return this.getSequenciaSeq();
	}

}
