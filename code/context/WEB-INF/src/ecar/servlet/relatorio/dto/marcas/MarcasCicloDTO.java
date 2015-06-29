package ecar.servlet.relatorio.dto.marcas;

public class MarcasCicloDTO {
	private String marca;
	private String eixo;
	private String acao;
	private String codAcao;
	private String parecer;
	private String encaminhamentos;
	private String responsavel;
	private String prazo;

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getEixo() {
		return eixo;
	}
	public void setEixo(String eixo) {
		this.eixo = eixo;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getCodAcao() {
		return codAcao;
	}
	public void setCodAcao(String codAcao) {
		this.codAcao = codAcao;
	}
	public String getParecer() {
		return parecer;
	}
	public void setParecer(String parecer) {
		this.parecer = parecer;
	}
	public String getEncaminhamentos() {
		return encaminhamentos;
	}
	public void setEncaminhamentos(String encaminhamentos) {
		this.encaminhamentos = encaminhamentos;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getPrazo() {
		return prazo;
	}
	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}
}