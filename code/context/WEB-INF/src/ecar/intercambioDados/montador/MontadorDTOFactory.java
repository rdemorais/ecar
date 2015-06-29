package ecar.intercambioDados.montador;

import ecar.intercambioDados.TipoDadosImportacao;

public abstract class MontadorDTOFactory {

	public abstract IMontadorDTO criar(TipoDadosImportacao tipo);
	
}
