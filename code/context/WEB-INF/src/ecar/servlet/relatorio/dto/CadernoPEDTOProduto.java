package ecar.servlet.relatorio.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class CadernoPEDTOProduto extends CadernoPEDTOEstrategia{
	
	private String prodSigla;
	private String prodNome;
	private List<CadernoPEDTOIndicador> listIndicadores = new ArrayList<CadernoPEDTOIndicador>();
	
	public CadernoPEDTOProduto() {
		
	}
	
	public CadernoPEDTOProduto(CadernoPEDTOEstrategia estrategia) {
		this.setObjNome(estrategia.getObjNome());
		this.setObjSigla(estrategia.getObjSigla());
		this.setEstSigla(estrategia.getEstSigla());
		this.setEstNome(estrategia.getEstNome());
		estrategia.addProduto(this);
	}
	
	public String getProdSigla() {
		return prodSigla;
	}
	public void setProdSigla(String prodSigla) {
		this.prodSigla = prodSigla;
	}
	public String getProdNome() {
		return prodNome;
	}
	public void setProdNome(String prodNome) {
		this.prodNome = prodNome;
	}
	
	public List<CadernoPEDTOIndicador> getListIndicadores() {
		return listIndicadores;
	}

	public void setListIndicadores(List<CadernoPEDTOIndicador> listIndicadores) {
		this.listIndicadores = listIndicadores;
	}
	
	public void addIndicador(CadernoPEDTOIndicador indicador) {
		this.listIndicadores.add(indicador);
	}

	@Override
	public String toString() {
		return "CadernoPEDTOProduto [prodNome=" + prodNome + ", prodSigla="
				+ prodSigla + ", getCor()=" + getCor() + ", getEstNome()="
				+ getEstNome() + ", getEstSigla()=" + getEstSigla()
				+ ", getListProdutos()=" + getListProdutos()
				+ ", getParecer()=" + getParecer() + ", getResponsavel()="
				+ getResponsavel() + ", getSituacao()=" + getSituacao()
				+ ", getListEstrategias()=" + getListEstrategias()
				+ ", getObjNome()=" + getObjNome() + ", getObjSigla()="
				+ getObjSigla() + "]";
	}
	
	
}