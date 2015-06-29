package ecar.intercambioDados.importacao.analisesemantica;

import ecar.intercambioDados.IBusinessObject;

public abstract class AnaliseSemanticaFactory {

	public abstract AnaliseSemantica criar(IBusinessObject objetoNegocio);
	
}
