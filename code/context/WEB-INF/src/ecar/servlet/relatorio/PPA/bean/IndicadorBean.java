package ecar.servlet.relatorio.PPA.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 70744416353
 */
public class IndicadorBean implements Serializable {

	private static final long serialVersionUID = -3968704271360226386L;

	private String nome;
	private String unidade;
	private String periodicidade;
	private String fonte;
	private Double indiceRecente;
	private Date dataApuracao;
	private Double indiceDt1;
	private Double indiceDt2;
	private Double indiceDt3;
	private Double indiceDt4;
	private String baseGeografica;
	private Integer indice;
	
	
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
        /**
         *
         * @return
         */
        public String getBaseGeografica() {
		return baseGeografica;
	}
        /**
         *
         * @param baseGeografica
         */
        public void setBaseGeografica(String baseGeografica) {
		this.baseGeografica = baseGeografica;
	}
        /**
         *
         * @return
         */
        public Date getDataApuracao() {
		return dataApuracao;
	}
        /**
         *
         * @param dataApuracao
         */
        public void setDataApuracao(Date dataApuracao) {
		this.dataApuracao = dataApuracao;
	}
        /**
         *
         * @return
         */
        public String getFonte() {
		return fonte;
	}
        /**
         *
         * @param fonte
         */
        public void setFonte(String fonte) {
		this.fonte = fonte;
	}
        /**
         *
         * @return
         */
        public Double getIndiceDt1() {
		return indiceDt1;
	}
        /**
         *
         * @param indiceDt1
         */
        public void setIndiceDt1(Double indiceDt1) {
		this.indiceDt1 = indiceDt1;
	}
        /**
         *
         * @return
         */
        public Double getIndiceDt2() {
		return indiceDt2;
	}
        /**
         *
         * @param indiceDt2
         */
        public void setIndiceDt2(Double indiceDt2) {
		this.indiceDt2 = indiceDt2;
	}
        /**
         *
         * @return
         */
        public Double getIndiceDt3() {
		return indiceDt3;
	}
        /**
         *
         * @param indiceDt3
         */
        public void setIndiceDt3(Double indiceDt3) {
		this.indiceDt3 = indiceDt3;
	}
        /**
         *
         * @return
         */
        public Double getIndiceDt4() {
		return indiceDt4;
	}
        /**
         *
         * @param indiceDt4
         */
        public void setIndiceDt4(Double indiceDt4) {
		this.indiceDt4 = indiceDt4;
	}
        /**
         *
         * @return
         */
        public Double getIndiceRecente() {
		return indiceRecente;
	}
        /**
         *
         * @param indiceRecente
         */
        public void setIndiceRecente(Double indiceRecente) {
		this.indiceRecente = indiceRecente;
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
         * @param nome
         */
        public void setNome(String nome) {
		this.nome = nome;
	}
        /**
         *
         * @return
         */
        public String getPeriodicidade() {
		return periodicidade;
	}
        /**
         *
         * @param periodicidade
         */
        public void setPeriodicidade(String periodicidade) {
		this.periodicidade = periodicidade;
	}
        /**
         *
         * @return
         */
        public String getUnidade() {
		return unidade;
	}
        /**
         *
         * @param unidade
         */
        public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
		
}
