package ecar.intercambioDados.montador;

import ecar.exception.ECARException;
import ecar.intercambioDados.dto.IBusinessObjectDTO;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public interface IMontadorDTO {
	
	IBusinessObjectDTO montar(IRegistro registro, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado, IBusinessObjectDTO objetoNegocioAnterior, int numeroLinha) throws ECARException;
	

}
