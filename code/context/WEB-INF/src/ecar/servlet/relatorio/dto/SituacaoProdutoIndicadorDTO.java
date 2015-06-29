package ecar.servlet.relatorio.dto;

/**
 * 
 * @author Rafael de Morais
 * 
 */
public class SituacaoProdutoIndicadorDTO {
	private String nomeIndicador;
	private String sinalizacao;
	private String percent;
	
	public SituacaoProdutoIndicadorDTO() {
	}
	
	public SituacaoProdutoIndicadorDTO(String nomeIndicador) {
		this.nomeIndicador = nomeIndicador;
	}
	
	public SituacaoProdutoIndicadorDTO(String nomeIndicador,
			String sinalizacao, String percent) {
		super();
		this.nomeIndicador = nomeIndicador;
		this.sinalizacao = sinalizacao;
		this.percent = percent;
	}

	public String getSinalizacao() {
		return sinalizacao;
	}

	public void setSinalizacao(String sinalizacao) {
		this.sinalizacao = sinalizacao;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getNomeIndicador() {
		return nomeIndicador;
	}

	public void setNomeIndicador(String nomeIndicador) {
		this.nomeIndicador = nomeIndicador;
	}

	@Override
	public String toString() {
		return nomeIndicador;
	}
}