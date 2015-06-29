package ecar.servlet.relatorio.PPA_Programa;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Classe pojo que representa a Fonte de dados de Item de Programa
 * @author Gabriel I Solana
 * @since 07/2007
 * 
 */
public class PPA_ProgramaBean implements Serializable {


	private static final long serialVersionUID = -7546583257214378562L;

	/**
	 * Nome do Programa
	 */
	private String nome;

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

	private Integer indice;
	
	final private BigDecimal ZERO = new BigDecimal(0);
	
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
