package ecar.sinalizacao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import ecar.pojo.ItemEstrtIndResulIettr;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class Sinalizacao implements Serializable{

	private static final long serialVersionUID = 5689746381141806979L;

	private Long codSin;
	
	private String identificacao;
	
	/**
	 * true para maior, melhor
	 * false para menor, melhor
	 * null para manter
	 */
	private Boolean polaridade; 
	
	private Set<Faixa> faixas = new HashSet<Faixa>();
	
	private Set<ItemEstrtIndResulIettr> itemEstrtIndResulIettrList = new HashSet<ItemEstrtIndResulIettr>(); 

	public Sinalizacao() {
	}

	public Sinalizacao(Long codSin){
		this.codSin = codSin;
	}
	
	public void addItemEstrtIndResulIettr(ItemEstrtIndResulIettr indResulIettr) {
		this.itemEstrtIndResulIettrList.add(indResulIettr);
	}
	
	public Set<ItemEstrtIndResulIettr> getItemEstrtIndResulIettrList() {
		return itemEstrtIndResulIettrList;
	}

	public void setItemEstrtIndResulIettrList(
			Set<ItemEstrtIndResulIettr> itemEstrtIndResulIettrList) {
		this.itemEstrtIndResulIettrList = itemEstrtIndResulIettrList;
	}

	public Long getCodSin() {
		return codSin;
	}

	public void setCodSin(Long codSin) {
		this.codSin = codSin;
	}

	public Set<Faixa> getFaixas() {
		return faixas;
	}

	public void setFaixas(Set<Faixa> faixas) {
		this.faixas = faixas;
	}
	
	public void addFaixa(Faixa faixa) {
		this.faixas.add(faixa);
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public Boolean getPolaridade() {
		return polaridade;
	}

	public void setPolaridade(Boolean polaridade) {
		this.polaridade = polaridade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codSin == null) ? 0 : codSin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Sinalizacao)) {
			return false;
		}
		Sinalizacao other = (Sinalizacao) obj;
		if (codSin == null) {
			if (other.codSin != null) {
				return false;
			}
		} else if (!codSin.equals(other.codSin)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Sinalizacao [codSin=" + codSin + ", identificacao="
				+ identificacao + ", polaridade=" + polaridade + ", faixas="
				+ faixas + ", itemEstrtIndResulIettrList="
				+ itemEstrtIndResulIettrList + "]";
	}
	
	
	
}