package ecar.servlet.relatorio.PPA_LinhaAcao;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 * @author 70744416353
 */
public class PPA_LinhaAcaoBean implements Serializable {


	private static final long serialVersionUID = 1618823163312063271L;

	/**
	 * Descricao do item
	 */
	private String nome;

	/**
	 * Sigla do Orgao
	 */
	private String siglaOrgao;
	
	/**
	 * valor do Exercicio 1
	 */
	private BigDecimal valor1;
	
	/**
	 * valor do Exercicio 2 
	 */
	private BigDecimal valor2;
	
	/**
	 * valor do Exercicio 3 
	 */	
	private BigDecimal valor3;
	
	/**
	 * valor do Exercicio 4 
	 */	
	private BigDecimal valor4;
	
	/**
	 * valor total dos exercicios 
	 */	
	private BigDecimal total;

	
	private Integer nivelItem;
	
	private Integer indice;
	
	final private BigDecimal ZERO = new BigDecimal(0);


        /**
         *
         * @return
         */
        public Integer getNivelItem() {
		return nivelItem;
	}


        /**
         *
         * @param nivelItem
         */
        public void setNivelItem(Integer nivelItem) {
		this.nivelItem = nivelItem;
	}


        /**
         *
         * @return
         */
        public String getNome() {
		return nome;
	}


        /**
         *
         * @return
         */
        public String getSiglaOrgao() {
		return siglaOrgao;
	}


        /**
         *
         * @return
         */
        public BigDecimal getTotal() {
		return total==null?ZERO:total;
	}

        /**
         *
         * @return
         */
        public BigDecimal getValor1() {
		return valor1==null?ZERO:valor1;
	}

        /**
         *
         * @return
         */
        public BigDecimal getValor2() {
		return valor2==null?ZERO:valor2;
	}

        /**
         *
         * @return
         */
        public BigDecimal getValor3() {
		return valor3==null?ZERO:valor3;
	}

        /**
         *
         * @return
         */
        public BigDecimal getValor4() {
		return valor4==null?ZERO:valor4;
	}


        /**
         *
         * @param nome
         */
        public void setNome(String nome) {
		this.nome = nome;
	}


        /**
         *
         * @param siglaOrgao
         */
        public void setSiglaOrgao(String siglaOrgao) {
		this.siglaOrgao = siglaOrgao;
	}


        /**
         *
         * @param total
         */
        public void setTotal(BigDecimal total) {
		this.total = total;
	}


        /**
         *
         * @param valor1
         */
        public void setValor1(BigDecimal valor1) {
		this.valor1 = valor1;
	}


        /**
         *
         * @param valor2
         */
        public void setValor2(BigDecimal valor2) {
		this.valor2 = valor2;
	}


        /**
         *
         * @param valor3
         */
        public void setValor3(BigDecimal valor3) {
		this.valor3 = valor3;
	}


        /**
         *
         * @param valor4
         */
        public void setValor4(BigDecimal valor4) {
		this.valor4 = valor4;
	}
	
    @Override
	public String toString() {
		return "\n" +
				"nome: " + nome +
				"\t nivel: " + nivelItem + 
				"\t siglaOrgao: " + siglaOrgao +
				"\t valor1: " + valor1 +
				"\t valor2: " + valor2 +
				"\t valor3: " + valor3 +
				"\t valor4: " + valor4 +
				"\t total: " + total;		
	}


	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((nivelItem == null) ? 0 : nivelItem.hashCode());
		result = PRIME * result + ((nome == null) ? 0 : nome.hashCode());
		result = PRIME * result + ((siglaOrgao == null) ? 0 : siglaOrgao.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PPA_LinhaAcaoBean other = (PPA_LinhaAcaoBean) obj;
		if (nivelItem == null) {
			if (other.nivelItem != null)
				return false;
		} else if (!nivelItem.equals(other.nivelItem))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (siglaOrgao == null) {
			if (other.siglaOrgao != null)
				return false;
		} else if (!siglaOrgao.equals(other.siglaOrgao))
			return false;
		return true;
	}


        /**
         *
         * @return
         */
        public Integer getIndice() {
		return indice;
	}


        /**
         *
         * @param indice
         */
        public void setIndice(Integer indice) {
		this.indice = indice;
	}


	
	
}
