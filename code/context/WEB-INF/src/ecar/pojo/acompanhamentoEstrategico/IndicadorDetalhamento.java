package ecar.pojo.acompanhamentoEstrategico;


public class IndicadorDetalhamento {
	private String mes;
	private String ano;
	private Double realizadoNoMes;
	private Double previstoNoMes;
	private String tipo;
//	private String totalRealizado;
//	private String totalPrevisto;
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public Double getRealizadoNoMes() {
		return realizadoNoMes;
	}
	public void setRealizadoNoMes(Double realizadoNoMes) {
		this.realizadoNoMes = realizadoNoMes;
	}
	public Double getPrevistoNoMes() {
		return previstoNoMes;
	}
	public void setPrevistoNoMes(Double previstoNoMes) {
		this.previstoNoMes = previstoNoMes;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
			
}
