package ecar.servlet.relatorio.dto;

import java.util.ArrayList;
import java.util.List;

public class MonitoramentoCicloEstrategiaDTO {
	private String estSigla;
	private String sitEstrategia;
	private String imgEst;
	private List<MonitoramentoCicloProdutoDTO> produtos = new ArrayList<MonitoramentoCicloProdutoDTO>();
	public String getEstSigla() {
		return estSigla;
	}
	public void setEstSigla(String estSigla) {
		this.estSigla = estSigla;
	}
	public String getSitEstrategia() {
		return sitEstrategia;
	}
	public void setSitEstrategia(String sitEstrategia) {
		this.sitEstrategia = sitEstrategia;
	}
	public String getImgEst() {
		return imgEst;
	}
	public void setImgEst(String imgEst) {
		this.imgEst = imgEst;
	}
	public List<MonitoramentoCicloProdutoDTO> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<MonitoramentoCicloProdutoDTO> produtos) {
		this.produtos = produtos;
	}
	public void addProduto(MonitoramentoCicloProdutoDTO produto) {
		this.produtos.add(produto);
	}
}
