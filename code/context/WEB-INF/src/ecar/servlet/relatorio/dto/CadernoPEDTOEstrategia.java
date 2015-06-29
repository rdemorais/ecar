package ecar.servlet.relatorio.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class CadernoPEDTOEstrategia extends CadernoPEDTO{
	
	private String estSigla;
	private String estNome;
	private String responsavel;
	private String cor;
	private String situacao;
	private String parecer;
	private List<CadernoPEDTOProduto> listProdutos = new ArrayList<CadernoPEDTOProduto>();
	
	public CadernoPEDTOEstrategia() {
	
	}
	
	public CadernoPEDTOEstrategia(String estSigla) {
		this.estSigla = estSigla;
	}
	
	public CadernoPEDTOEstrategia(CadernoPEDTO cadernoPE) {
		this.setObjSigla(cadernoPE.getObjSigla());
		this.setObjNome(cadernoPE.getObjNome());
		cadernoPE.addEstrategia(this);
	}
	
	
	
	public String getEstSigla() {
		return estSigla;
	}
	public void setEstSigla(String estSigla) {
		this.estSigla = estSigla;
	}
	public String getEstNome() {
		return estNome;
	}
	public void setEstNome(String estNome) {
		this.estNome = estNome;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public String getParecer() {
		return parecer;
	}
	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public List<CadernoPEDTOProduto> getListProdutos() {
		return listProdutos;
	}

	public void setListProdutos(List<CadernoPEDTOProduto> listProdutos) {
		this.listProdutos = listProdutos;
	}
	
	public void addProduto(CadernoPEDTOProduto produto) {
		this.listProdutos.add(produto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((estSigla == null) ? 0 : estSigla.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CadernoPEDTOEstrategia other = (CadernoPEDTOEstrategia) obj;
		if (estSigla == null) {
			if (other.estSigla != null)
				return false;
		} else if (!estSigla.equals(other.estSigla))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CadernoPEDTOEstrategia [cor=" + cor + ", estNome=" + estNome
				+ ", estSigla=" + estSigla + ", listProdutos=" + listProdutos
				+ ", parecer=" + parecer + ", responsavel=" + responsavel
				+ ", situacao=" + situacao + ", getObjNome()=" + getObjNome()
				+ ", getObjSigla()=" + getObjSigla() + "]";
	}
}