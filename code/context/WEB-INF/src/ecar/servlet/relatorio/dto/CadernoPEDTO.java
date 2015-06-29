package ecar.servlet.relatorio.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Rafael de Morais
 *
 */
/**
 * @author rafael
 *
 */
public class CadernoPEDTO {
	private String objSigla;
	private String objNome;
	private String labelFA1;
	private String responsavelFA1;
	private String labelFA2;
	private String responsavelFA2;
	private Integer qtdEstrategia;
	private Integer qtdProduto;
	
	private List<CadernoPEDTOEstrategia> listEstrategias = new ArrayList<CadernoPEDTOEstrategia>();
	
	public CadernoPEDTO() {
	}
	
	public CadernoPEDTO(String objSigla) {
		this.objSigla = objSigla; 
	}
	
	public String getObjSigla() {
		return objSigla;
	}
	public void setObjSigla(String objSigla) {
		this.objSigla = objSigla;
	}
	public String getObjNome() {
		return objNome;
	}
	public void setObjNome(String objNome) {
		this.objNome = objNome;
	}
	
	public List<CadernoPEDTOEstrategia> getListEstrategias() {
		return listEstrategias;
	}

	public void setListEstrategias(List<CadernoPEDTOEstrategia> listEstrategias) {
		this.listEstrategias = listEstrategias;
	}
	
	public void addEstrategia(CadernoPEDTOEstrategia est) {
		this.listEstrategias.add(est);
	}
	
	public String getLabelFA1() {
		return labelFA1;
	}

	public void setLabelFA1(String labelFA1) {
		this.labelFA1 = labelFA1;
	}

	public String getResponsavelFA1() {
		return responsavelFA1;
	}

	public void setResponsavelFA1(String responsavelFA1) {
		this.responsavelFA1 = responsavelFA1;
	}

	public String getLabelFA2() {
		return labelFA2;
	}

	public void setLabelFA2(String labelFA2) {
		this.labelFA2 = labelFA2;
	}

	public String getResponsavelFA2() {
		return responsavelFA2;
	}

	public void setResponsavelFA2(String responsavelFA2) {
		this.responsavelFA2 = responsavelFA2;
	}
	
	public Integer getQtdEstrategia() {
		return qtdEstrategia;
	}

	public void setQtdEstrategia(Integer qtdEstrategia) {
		this.qtdEstrategia = qtdEstrategia;
	}

	public Integer getQtdProduto() {
		return qtdProduto;
	}

	public void setQtdProduto(Integer qtdProduto) {
		this.qtdProduto = qtdProduto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((objSigla == null) ? 0 : objSigla.hashCode());
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
		CadernoPEDTO other = (CadernoPEDTO) obj;
		if (objSigla == null) {
			if (other.objSigla != null)
				return false;
		} else if (!objSigla.equals(other.objSigla))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CadernoPEDTO [labelFA1=" + labelFA1 + ", labelFA2=" + labelFA2
				+ ", listEstrategias=" + listEstrategias + ", objNome="
				+ objNome + ", objSigla=" + objSigla + ", responsavelFA1="
				+ responsavelFA1 + ", responsavelFA2=" + responsavelFA2 + "]";
	}

}
