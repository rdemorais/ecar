package ecar.intercambioDados.montador;

import ecar.intercambioDados.TipoDadosImportacao;

public class MontadorTXTDTOFactory extends MontadorDTOFactory {

	@Override
	public IMontadorDTO criar(TipoDadosImportacao tipo) {
		
		IMontadorDTO montador = null;
		
		if (tipo!=null && tipo.equals(TipoDadosImportacao.ITEM))
			montador = new MontadorItemEstruturaTXTDTO();
			
		return montador;
	}

}
