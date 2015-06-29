package ecar.intercambioDados.importacao.comunicacao;

import ecar.intercambioDados.TipoDadosImportacao;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;


public interface IRegistro {

	TipoDadosImportacao getTipo(PerfilIntercambioDadosPflid perfil);
}
