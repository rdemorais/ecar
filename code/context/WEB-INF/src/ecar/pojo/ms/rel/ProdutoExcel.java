package ecar.pojo.ms.rel;

import java.util.ArrayList;
import java.util.List;

public class ProdutoExcel {
	private ResultadoExcel resultado;
	private String siglaProd;
	private String nomeProd;
	private String situacao;
	private String responsavel;
	private String email;
	private String areaResponsavel;
	
	private List<AcaoExcel> acoes = new ArrayList<AcaoExcel>();
	
	public ProdutoExcel() {

	}
	
	public ProdutoExcel(String siglaProd, String nomeProd, String situacao, String responsavel, String email, String areaResponsavel) {
		super();
		this.siglaProd = siglaProd;
		this.nomeProd = nomeProd;
		this.situacao = situacao;
		this.responsavel = responsavel;
		this.email = email;
		this.areaResponsavel = areaResponsavel;
	}

	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAreaResponsavel() {
		return areaResponsavel;
	}
	public void setAreaResponsavel(String areaResponsavel) {
		this.areaResponsavel = areaResponsavel;
	}
	public ResultadoExcel getResultado() {
		return resultado;
	}
	public void setResultado(ResultadoExcel resultado) {
		this.resultado = resultado;
	}
	public String getSiglaProd() {
		return siglaProd;
	}
	public void setSiglaProd(String siglaProd) {
		this.siglaProd = siglaProd;
	}
	public String getNomeProd() {
		return nomeProd;
	}
	public void setNomeProd(String nomeProd) {
		this.nomeProd = nomeProd;
	}
	
	public List<AcaoExcel> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<AcaoExcel> acoes) {
		this.acoes = acoes;
	}
	
	public void addAcao(AcaoExcel acao) {
		this.acoes.add(acao);
	}
	@Override
	public String toString() {
		return "ProdutoExcel [siglaProd=" + siglaProd + ", nomeProd=" + nomeProd + ", situacao=" + situacao + ", responsavel=" + responsavel + ", email=" + email + ", areaResponsavel=" + areaResponsavel + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeProd == null) ? 0 : nomeProd.hashCode());
		result = prime * result + ((resultado == null) ? 0 : resultado.hashCode());
		result = prime * result + ((siglaProd == null) ? 0 : siglaProd.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoExcel other = (ProdutoExcel) obj;
		if (nomeProd == null) {
			if (other.nomeProd != null)
				return false;
		} else if (!nomeProd.equals(other.nomeProd))
			return false;
		if (resultado == null) {
			if (other.resultado != null)
				return false;
		} else if (!resultado.equals(other.resultado))
			return false;
		if (siglaProd == null) {
			if (other.siglaProd != null)
				return false;
		} else if (!siglaProd.equals(other.siglaProd))
			return false;
		return true;
	}
	
}