package ecar.pojo;

import java.io.Serializable;
import java.util.Date;

/** @author Hibernate CodeGenerator */
public class ItemEstrtIndResulLocalIettirl implements Serializable,Comparable<ItemEstrtIndResulLocalIettirl> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1482382700469469861L;
	
	private Long codIettirl;

	//Mantis 0010128 - Qtd prevista não é mais informado por exercício
	//private ItemEstrtIndResulIettr itemEsrtIndResulIettr;
	//private ExercicioExe exercicioExe;
	
	private LocalItemLit localItemLit;
	
	private Double qtdPrevistaIettirl;
	
	private String indAtivoIettirl;
	
	private Date dataInclusaoIettirl;
	
	private ecar.pojo.ItemEstrutFisicoIettf itemEstrutFisicoIettf;

	/**
	 * @return the codIettirl
	 */
	public Long getCodIettirl() {
		return codIettirl;
	}

	/**
	 * @param codIettirl the codIettirl to set
	 */
	public void setCodIettirl(Long codIettirl) {
		this.codIettirl = codIettirl;
	}

	/**
	 * @param itemEstrutFisicoIettf the itemEstrutFisicoIettf to set
	 */
	public void setItemEstrutFisicoIettf(ecar.pojo.ItemEstrutFisicoIettf itemEstrutFisicoIettf) {
		this.itemEstrutFisicoIettf = itemEstrutFisicoIettf;
	}

	/**
	 * @return the itemEstrutFisicoIettf
	 */
	public ecar.pojo.ItemEstrutFisicoIettf getItemEstrutFisicoIettf() {
		return itemEstrutFisicoIettf;
	}

	/**
	 * @return the dataInclusaoIettirl
	 */
	public Date getDataInclusaoIettirl() {
		return dataInclusaoIettirl;
	}

	/**
	 * @param dataInclusaoIettirl the dataInclusaoIettirl to set
	 */
	public void setDataInclusaoIettirl(Date dataInclusaoIettirl) {
		this.dataInclusaoIettirl = dataInclusaoIettirl;
	}

	/**
	 * @return the indAtivoIettirl
	 */
	public String getIndAtivoIettirl() {
		return indAtivoIettirl;
	}

	/**
	 * @param indAtivoIettirl the indAtivoIettirl to set
	 */
	public void setIndAtivoIettirl(String indAtivoIettirl) {
		this.indAtivoIettirl = indAtivoIettirl;
	}

	/**
	 * @return the localItemLit
	 */
	public LocalItemLit getLocalItemLit() {
		return localItemLit;
	}

	/**
	 * @param localItemLit the localItemLit to set
	 */
	public void setLocalItemLit(LocalItemLit localItemLit) {
		this.localItemLit = localItemLit;
	}

	/**
	 * @return the qtdPrevistaIettirl
	 */
	public Double getQtdPrevistaIettirl() {
		return qtdPrevistaIettirl;
	}

	/**
	 * @param qtdPrevistaIettirl the qtdPrevistaIettirl to set
	 */
	public void setQtdPrevistaIettirl(Double qtdPrevistaIettirl) {
		this.qtdPrevistaIettirl = qtdPrevistaIettirl;
	}

	
	public int compareTo(ItemEstrtIndResulLocalIettirl o) {
		
		if(this.getLocalItemLit().getSiglaLit().compareTo(o.getLocalItemLit().getSiglaLit()) < 0) {
			return -1;
		} else if(this.getLocalItemLit().getSiglaLit().compareTo(o.getLocalItemLit().getSiglaLit()) > 0) {
			return 1;
			//Mantis 0010128 - Qtd prevista não é mais informado por exercício			
		} else {
			return -1;
			/*
			if (this.getExercicioExe().getDescricaoExe().compareTo(o.getExercicioExe().getDescricaoExe()) < 0){
				return -1;
			} else if (this.getExercicioExe().getDescricaoExe().compareTo(o.getExercicioExe().getDescricaoExe()) > 0){
				return 1;
			} else {
				return 0;
				
			}
			*/		
		}		
	}
	
}
