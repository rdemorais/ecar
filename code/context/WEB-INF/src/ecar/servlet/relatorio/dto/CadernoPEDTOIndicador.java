package ecar.servlet.relatorio.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class CadernoPEDTOIndicador extends CadernoPEDTOProduto {
	
	private String nomeInd;
	private String conceituacao;
	private String interpretacao;
	private String metodoCalculo;
	private List<CadernoPEDTOIndGraf> serieGraf = new ArrayList<CadernoPEDTOIndGraf>();
	
	public CadernoPEDTOIndicador() {
		
	}
	
	public CadernoPEDTOIndicador(CadernoPEDTOProduto produto) {
		this.setObjNome(produto.getObjNome());
		this.setObjSigla(produto.getObjSigla());
		this.setEstSigla(produto.getEstSigla());
		this.setEstNome(produto.getEstNome());
		this.setProdSigla(produto.getProdSigla());
		this.setProdNome(produto.getProdNome());
		produto.addIndicador(this);
	}
	
	public String getNomeInd() {
		return nomeInd;
	}
	public void setNomeInd(String nomeInd) {
		this.nomeInd = nomeInd;
	}
	public String getConceituacao() {
		return conceituacao;
	}
	public void setConceituacao(String conceituacao) {
		this.conceituacao = conceituacao;
	}
	public String getInterpretacao() {
		return interpretacao;
	}
	public void setInterpretacao(String interpretacao) {
		this.interpretacao = interpretacao;
	}
	public String getMetodoCalculo() {
		return metodoCalculo;
	}
	public void setMetodoCalculo(String metodoCalculo) {
		this.metodoCalculo = metodoCalculo;
	}

	public List<CadernoPEDTOIndGraf> getSerieGraf() {
		return serieGraf;
	}

	public void setSerieGraf(List<CadernoPEDTOIndGraf> serieGraf) {
		this.serieGraf = serieGraf;
	}
	
	public void addSerieGraf(CadernoPEDTOIndGraf serie) {
		this.serieGraf.add(serie);
	}
}
