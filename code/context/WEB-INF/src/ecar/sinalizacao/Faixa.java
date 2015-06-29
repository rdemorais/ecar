package ecar.sinalizacao;

import java.io.Serializable;

import ecar.pojo.Cor;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class Faixa implements Serializable {
	
	private static final long serialVersionUID = 5434794276703734258L;
	private Long codFaixa;
	private Cor cor;
	private double min;
	private double max;
	private Sinalizacao sinalizacao;
	private Boolean exigeJustificativa;

	public Faixa() {
	}
	
	public Faixa(Cor cor, double min, double max) {
		this.cor = cor;
		this.min = min;
		this.max = max;
	}

	public Sinalizacao getSinalizacao() {
		return sinalizacao;
	}
	public void setSinalizacao(Sinalizacao sinalizacao) {
		this.sinalizacao = sinalizacao;
	}
	public Long getCodFaixa() {
		return codFaixa;
	}
	public void setCodFaixa(Long codFaixa) {
		this.codFaixa = codFaixa;
	}
	public Cor getCor() {
		return cor;
	}
	public void setCor(Cor cor) {
		this.cor = cor;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	
	public Boolean isExigeJustificativa() {
		return exigeJustificativa;
	}

	public void setExigeJustificativa(Boolean exigeJustificativa) {
		this.exigeJustificativa = exigeJustificativa;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codFaixa == null) ? 0 : codFaixa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Faixa)) {
			return false;
		}
		Faixa other = (Faixa) obj;
		if (codFaixa == null) {
			if (other.codFaixa != null) {
				return false;
			}
		} else if (!codFaixa.equals(other.codFaixa)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String s = "Faixa: Min:" + min + ", Max: " + max + ", Cor: " + (cor != null ? cor.getNomeCor() : ""); 
		return s;
	}
}