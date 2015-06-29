package ecar.api.facade;

import comum.util.Util;

import ecar.pojo.AcompRealFisicoLocalArfl;

/**
 * 
 * Wrapper class para a classe do ecar AcompRealFisicoLocalArfl - 
 * realizado por local.
 * 
 * Exemplo de uso
 * <pre>
 * {@code
 * 		IndicadorResultado indicador = new IndicadorResultado(2);
 *		List<Realizado> realizados = indicador.getAcompanhamentosRealizado();
 *		
 *		System.out.println(indicador.getRealObject().getIndRealPorLocal());
 *		
 *		for(Realizado realizado: realizados){
 *			List<RealizadoLocal> l = indicador.getAcompanhamentoFisicoLocal(realizado);
 *			for(RealizadoLocal o: l){
 *				System.out.println(o.getLocal().getNome() + "\t" + o.getLocal().getSigla() + "\t" + o.getLocal().getCodigoIBGE() + "\t" + o.getRealizado());
 *			}
 *		}
 *}
 * 
 * </pre>
 * 
 * 
 * 
 * @author N/A
 *
 */
public class RealizadoLocal implements EcarWrapperInterface<AcompRealFisicoLocalArfl>{

	private AcompRealFisicoLocalArfl realizadoLocal;
	
	public RealizadoLocal(AcompRealFisicoLocalArfl realizadoLocal){
		this.realizadoLocal = realizadoLocal;
	}
	
	
	/**
	 * 
	 * Retorna o objeto real do ecar.
	 * 
	 * @return
	 */
	public AcompRealFisicoLocalArfl getRealObject(){
		return realizadoLocal;
	}
	
	
	public double getRealizado(){
		return realizadoLocal.getQuantidadeArfl();
	}
	
	
	public Local getLocal(){
		return new Local(realizadoLocal.getLocalItemLit());
	}

	/**
	 * Retorna o valor formatado como moeda ou número decimal.
	 * @return
	 */
	public String getRealizadoLocalFormatado(){

		String res = "";
		//se for  moeda
		if(realizadoLocal.getAcompRealFisicoArf().getItemEstrtIndResulIettr().getIndTipoQtde().equals("V")){
			//por no formato de moeda
			res  = Util.formataMoeda(getRealizado());
		}else{
			res = Util.formataNumeroDecimal(getRealizado());
		}
		
		return res;
	}
	
}
