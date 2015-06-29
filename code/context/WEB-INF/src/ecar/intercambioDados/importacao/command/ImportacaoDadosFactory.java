package ecar.intercambioDados.importacao.command;

import ecar.intercambioDados.IBusinessObject;
import ecar.pojo.ItemEstruturaIett;

public class ImportacaoDadosFactory {
	
	public static ImportacaoDados criar(IBusinessObject objetoNegocio) {
		
		ImportacaoDados importacao = null;
		
		if (objetoNegocio instanceof ItemEstruturaIett) {
			importacao = new ImportacaoDadosItemEstrutura();
		}
		
		return importacao;
		
	}

}
