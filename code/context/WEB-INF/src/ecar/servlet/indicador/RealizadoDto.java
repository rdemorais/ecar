package ecar.servlet.indicador;

public class RealizadoDto {
	private Long indId;
	private Double valorRealizado;
	private Long codAref;
	public Long getIndId() {
		return indId;
	}
	public void setIndId(Long indId) {
		this.indId = indId;
	}
	public Double getValorRealizado() {
		return valorRealizado;
	}
	public void setValorRealizado(Double valorRealizado) {
		this.valorRealizado = valorRealizado;
	}
	public Long getCodAref() {
		return codAref;
	}
	public void setCodAref(Long codAref) {
		this.codAref = codAref;
	}
}