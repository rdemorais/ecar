package ecar.servlet.relatorio.dto;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class SituacaoProdutoCoRespDTO {
	private String siglaSecretaria;

	public SituacaoProdutoCoRespDTO(String siglaSecretaria) {
		this.siglaSecretaria = siglaSecretaria;
	}
	
	public String getSiglaSecretaria() {
		return siglaSecretaria;
	}

	public void setSiglaSecretaria(String siglaSecretaria) {
		this.siglaSecretaria = siglaSecretaria;
	}
	
	@Override
	public String toString() {
		return siglaSecretaria;
	}
}
