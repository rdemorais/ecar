package ecar.intercambioDados.montador;

import ecar.intercambioDados.TipoDadosImportacao;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public abstract class MontadorFactory {

	public abstract IMontadorTXT criar(TipoDadosImportacao tipo, PerfilIntercambioDadosPflid perfil);
	
}
