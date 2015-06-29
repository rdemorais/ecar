package ecar.servlet.relatorio.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class ConsolidadoMonitoramentoDTO {
	
	private List<CMSecretariaDTO> estrategias = new ArrayList<CMSecretariaDTO>();
	private List<CMSecretariaDTO> produtos = new ArrayList<CMSecretariaDTO>();

	public ConsolidadoMonitoramentoDTO() {
	}
	
	public List<CMSecretariaDTO> getEstrategias() {
		return estrategias;
	}
	public void setEstrategias(List<CMSecretariaDTO> estrategias) {
		this.estrategias = estrategias;
	}
	public List<CMSecretariaDTO> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<CMSecretariaDTO> produtos) {
		this.produtos = produtos;
	}
}