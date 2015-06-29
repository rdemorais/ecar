package ecar.pojo.ms.rel;

import java.util.ArrayList;
import java.util.List;

public class ResultadoExcel {
	private String siglaOe;
	private String siglaEst;
	private String sigla;
	private String nome;
	private String rem;
	private String situacao;
	private String responsavel;
	private String email;
	private String areaResponsavel;
	private List<ProdutoExcel> produtos = new ArrayList<ProdutoExcel>();
	private StringBuffer indicadorLista = new StringBuffer();
	
	public ResultadoExcel() {

	}
	
	
	public ResultadoExcel(String siglaOe, String siglaEst, String sigla, String nome, String rem, String situacao, String responsavel, String email, String areaResponsavel) {
		super();
		this.siglaOe = siglaOe;
		this.siglaEst = siglaEst;
		this.sigla = sigla;
		this.nome = nome;
		this.rem = rem;
		this.situacao = situacao;
		this.responsavel = responsavel;
		this.email = email;
		this.areaResponsavel = areaResponsavel;
	}

	public StringBuffer getIndicadorLista() {
		return indicadorLista;
	}

	public void setIndicadorLista(StringBuffer indicadorLista) {
		this.indicadorLista = indicadorLista;
	}


	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
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
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getSiglaOe() {
		return siglaOe;
	}
	public void setSiglaOe(String siglaOe) {
		this.siglaOe = siglaOe;
	}
	public String getSiglaEst() {
		return siglaEst;
	}
	public void setSiglaEst(String siglaEst) {
		this.siglaEst = siglaEst;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void addProduto(ProdutoExcel produto) {
		this.produtos.add(produto);
	}
	public List<ProdutoExcel> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<ProdutoExcel> produtos) {
		this.produtos = produtos;
	}
	@Override
	public String toString() {
		return "ResultadoExcel [siglaOe=" + siglaOe + ", siglaEst=" + siglaEst + ", sigla=" + sigla + ", nome=" + nome + ", rem=" + rem + ", situacao=" + situacao + ", responsavel=" + responsavel + ", email=" + email + ", areaResponsavel=" + areaResponsavel + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
		result = prime * result + ((siglaEst == null) ? 0 : siglaEst.hashCode());
		result = prime * result + ((siglaOe == null) ? 0 : siglaOe.hashCode());
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
		ResultadoExcel other = (ResultadoExcel) obj;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		if (siglaEst == null) {
			if (other.siglaEst != null)
				return false;
		} else if (!siglaEst.equals(other.siglaEst))
			return false;
		if (siglaOe == null) {
			if (other.siglaOe != null)
				return false;
		} else if (!siglaOe.equals(other.siglaOe))
			return false;
		return true;
	}
	
}