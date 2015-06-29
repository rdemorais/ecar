package ecar.intercambioDados.importacao.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import ecar.exception.ECARException;
import ecar.intercambioDados.IBusinessObject;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public interface ImportacaoDadosGeraisImportacaoPacInterCommand {
		
//	IBusinessObjectDTO montarDTO(IRegistro registro, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado, IBusinessObjectDTO objetoNegocioAnterior, int numeroLinha) throws ECARException;
	
	public IBusinessObject montar(IBusinessObject objetoNegocio, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado) throws ECARException;
	
	public IBusinessObject montar(IRegistro registro, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado) throws ECARException;
	
	String importar(IBusinessObject objetoNegocio, Transaction tx, HttpServletRequest request, UsuarioUsu usuarioLogado, Logger logger) throws ECARException;

}
