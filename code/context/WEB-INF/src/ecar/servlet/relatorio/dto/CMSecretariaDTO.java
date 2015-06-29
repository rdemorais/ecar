package ecar.servlet.relatorio.dto;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class CMSecretariaDTO {
	private String nomeSecretaria;
	private Long total;
	private Long totalMon;
	
	public CMSecretariaDTO() {
	}
	
	public CMSecretariaDTO(String nomeSecretaria, Long total,
			Long totalMon) {
		super();
		this.nomeSecretaria = nomeSecretaria;
		this.total = total;
		this.totalMon = totalMon;
	}

	public String getNomeSecretaria() {
		return nomeSecretaria;
	}

	public void setNomeSecretaria(String nomeSecretaria) {
		this.nomeSecretaria = nomeSecretaria;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getTotalMon() {
		return totalMon;
	}

	public void setTotalMon(Long totalMon) {
		this.totalMon = totalMon;
	}
}