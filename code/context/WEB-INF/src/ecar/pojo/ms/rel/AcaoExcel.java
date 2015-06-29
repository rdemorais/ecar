package ecar.pojo.ms.rel;

public class AcaoExcel {
	private ProdutoExcel produto;
	private String sigla;
	private String nome;
	private String situacao;
	private String responsavel;
	private String email;
	private String areaResponsavel;
	
	public AcaoExcel() {

	}
	
	public AcaoExcel(String sigla, String nome, String situacao, String responsavel, String email, String areaResponsavel) {
		this.sigla = sigla;
		this.nome = nome;
		this.situacao = situacao;
		this.responsavel = responsavel;
		this.email = email;
		this.areaResponsavel = areaResponsavel;
	}

	public ProdutoExcel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoExcel produto) {
		this.produto = produto;
	}

	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	@Override
	public String toString() {
		return "AcaoExcel [sigla=" + sigla + ", nome=" + nome + ", situacao=" + situacao + ", responsavel=" + responsavel + ", email=" + email + ", areaResponsavel=" + areaResponsavel + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		AcaoExcel other = (AcaoExcel) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}
	
}