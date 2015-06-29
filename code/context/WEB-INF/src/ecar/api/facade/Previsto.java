package ecar.api.facade;

import java.io.Serializable;

import comum.util.Util;
import ecar.pojo.ItemEstrutFisicoIettf;

/**
 * Wrapper class para os valores previstos mensais de um indicador.
 * 
 * @author 82035644020
 *
 */
public class Previsto implements EcarWrapperInterface<ItemEstrutFisicoIettf>, Serializable {

	private static final long serialVersionUID = 6170975838536269096L;
	private ItemEstrutFisicoIettf previsto = null;
		
	public Previsto(ItemEstrutFisicoIettf previsto){
		this.previsto = previsto;
	}
	
	
	public long getId(){
		return previsto.getCodIettf();
	}
	
	
	public Double getValorPrevisto(){
		return previsto.getQtdPrevistaIettf();
	}
	
	public EcarData getData(){
		return new EcarData(previsto.getMesIettf(), previsto.getAnoIettf());
	}
	
	/*
	 * (non-Javadoc)
	 * @see ecar.api.facade.EcarWrapperInterface#getRealObject()
	 */
	public ItemEstrutFisicoIettf getRealObject() {
		return previsto;
	}
	
	
	public String toString(){
		return "Previsto para a data " + getData().getDataFormatadaComBarra() + ": " + getValorPrevisto() + "\n"; 	
	}

	/**
	 * Retorna o valor formatado como moeda ou n�mero decimal.
	 * @return
	 */
	public String getPrevistoFormatado(){
		//se for  moeda
		if(previsto.getItemEstrtIndResulIettr().getIndTipoQtde().equals("V")){
			//por no formato de moeda
			return Util.formataMoeda(getValorPrevisto());
		}else{
			return Util.formataNumeroDecimal(getValorPrevisto());
		}
		
	}

	public String getPrevistoFormatadoPT_BR(){
		//se for  moeda
		if(previsto.getItemEstrtIndResulIettr().getIndTipoQtde().equals("V")){
			//por no formato de moeda
			return Util.formataMoeda(getValorPrevisto());
		}else{
			return Util.formataDecimalPT_BR(getValorPrevisto());
		}
		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((previsto == null) ? 0 : previsto.hashCode());
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
		if (!(obj instanceof Previsto)) {
			return false;
		}
		Previsto other = (Previsto) obj;
		if (previsto == null) {
			if (other.previsto != null) {
				return false;
			}
		} else if (!previsto.equals(other.previsto)) {
			return false;
		}
		return true;
	}

	
	
}
