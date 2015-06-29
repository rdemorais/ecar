package ecar.intercambioDados.importacao.comunicacao;

import java.util.List;

import ecar.exception.ECARException;

public abstract class ComunicacaoImportacao {
	
	protected Configuracao configuracaoImportacao;
	
	public abstract List<IRegistro> obtemRegistros() throws ECARException; 
	
}
