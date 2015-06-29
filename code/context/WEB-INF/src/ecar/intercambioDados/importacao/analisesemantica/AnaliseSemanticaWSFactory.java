package ecar.intercambioDados.importacao.analisesemantica;

import ecar.intercambioDados.IBusinessObject;
import ecar.pojo.ItemEstruturaIett;

public class AnaliseSemanticaWSFactory extends AnaliseSemanticaFactory {

	public AnaliseSemantica criar(IBusinessObject objetoNegocio) {
		
		AnaliseSemantica analiseSemantica = null;
		
		if (objetoNegocio instanceof ItemEstruturaIett) {
			
		}
		
		return analiseSemantica;
	}
	
}
