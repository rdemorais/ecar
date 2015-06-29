package ecar.intercambioDados.montador;

import ecar.intercambioDados.TipoDadosImportacao;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public class MontadorTXTFactory extends MontadorFactory {

	@Override
	public IMontadorTXT criar(TipoDadosImportacao tipo, PerfilIntercambioDadosPflid perfil) {
		
		IMontadorTXT montador = null;
		
		if (tipo!=null && tipo.equals(TipoDadosImportacao.ITEM))
			montador = new MontadorItemEstruturaTXT();
			
		return montador;
	}

}
