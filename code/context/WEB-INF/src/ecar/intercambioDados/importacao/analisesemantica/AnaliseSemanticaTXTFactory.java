package ecar.intercambioDados.importacao.analisesemantica;

import ecar.intercambioDados.IBusinessObject;
import ecar.pojo.ItemEstruturaIett;

public class AnaliseSemanticaTXTFactory extends AnaliseSemanticaFactory {

	public AnaliseSemantica criar(IBusinessObject objetoNegocio) {
		
		AnaliseSemantica analiseSemantica = null;
		
		if (objetoNegocio instanceof ItemEstruturaIett) {
			analiseSemantica = new AnaliseSemanticaItemEstruturaTXT();
		}
		
		return analiseSemantica;
	}
	
}
