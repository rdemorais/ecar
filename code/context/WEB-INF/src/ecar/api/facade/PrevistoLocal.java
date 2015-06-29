package ecar.api.facade;

import comum.util.Util;

import ecar.pojo.ItemEstrtIndResulLocalIettirl;

/**
 * Classe wrapper para o previsto local do indicador
 * 
 * @see ItemEstrtIndResulLocalIettirl
 * 
 * @author 82035644020
 *
 */
public class PrevistoLocal implements EcarWrapperInterface<ItemEstrtIndResulLocalIettirl>{

	private ItemEstrtIndResulLocalIettirl previstoLocal = null;
	private Local local = null;
	
	public PrevistoLocal(ItemEstrtIndResulLocalIettirl previstoLocal){
		this.previstoLocal = previstoLocal;
		this.local = new Local(previstoLocal.getLocalItemLit());
	}

	public long getId(){
		return previstoLocal.getCodIettirl();
	}
	
	public double getValorPrevisto(){
		return this.previstoLocal.getQtdPrevistaIettirl();
	}
	
	public Local getLocal(){
		return local;
	}
	
	public EcarData getData(){
		int mes = getRealObject().getItemEstrutFisicoIettf().getMesIettf();
		int ano = getRealObject().getItemEstrutFisicoIettf().getAnoIettf();
		return new EcarData(mes, ano);
	}
	
	public ItemEstrtIndResulLocalIettirl getRealObject(){
		return previstoLocal;
	}

	public String toString(){
		return "Local: " + getLocal().getNome() + " Valor: " + getValorPrevisto() + " " + getData().getDataFormatada() + "\n"; 
	}
	
	/**
	 * Retorna o valor formatado como moeda ou número decimal.
	 * @return
	 */
	public String getPrevistoLocalFormatado(){

		String res = "";
		//se for  moeda
		if(previstoLocal.getItemEstrutFisicoIettf().getItemEstrtIndResulIettr().getIndTipoQtde().equals("V")){
			//por no formato de moeda
			res  = Util.formataMoeda(getValorPrevisto());
		}else{
			res = Util.formataNumeroDecimal(getValorPrevisto());
		}
		
		return res;
	}

	
	
}
