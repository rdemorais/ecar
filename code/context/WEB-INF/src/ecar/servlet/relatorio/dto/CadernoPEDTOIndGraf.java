package ecar.servlet.relatorio.dto;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class CadernoPEDTOIndGraf {
	
	private String mes;
	private Double qtdPrev;
	private Double qtdRea;

	public CadernoPEDTOIndGraf() {
	}
	
	public CadernoPEDTOIndGraf(String mes) {
		this.mes = mes;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public Double getQtdPrev() {
		return qtdPrev;
	}

	public void setQtdPrev(Double qtdPrev) {
		this.qtdPrev = qtdPrev;
	}

	public Double getQtdRea() {
		return qtdRea;
	}

	public void setQtdRea(Double qtdRea) {
		this.qtdRea = qtdRea;
	}

	@Override
	public String toString() {
		return "CadernoPEDTOIndGraf [mes=" + mes + ", qtdPrev=" + qtdPrev
				+ ", qtdRea=" + qtdRea + "]";
	}
	
}