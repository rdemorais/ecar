package ecar.pojo.dto;

import java.io.Serializable;

public class SecretariaEstatisticaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2110660376841639897L;
	
	private Long codOrgao;
	private String siglaOrgao;
	private Long quantidade;
	
	public SecretariaEstatisticaDTO(Long codOrgao, String siglaOrgao, Long quantidade) {
		super();
		this.codOrgao = codOrgao;
		this.siglaOrgao = siglaOrgao;
		this.quantidade = quantidade;
	}	
	
	public Long getCodOrgao() {
		return codOrgao;
	}
	public void setCodOrgao(Long codOrgao) {
		this.codOrgao = codOrgao;
	}
	public String getSiglaOrgao() {
		return siglaOrgao;
	}
	public void setSiglaOrgao(String siglaOrgao) {
		this.siglaOrgao = siglaOrgao;
	}
	public Long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}	

}
