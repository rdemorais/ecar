/**
 * 
 */
package ecar.webservices.simpr;

import javax.jws.WebService;

import ecar.pojo.simpr.WsAcao;
import ecar.pojo.simpr.WsAcaoAtividade;
import ecar.pojo.simpr.WsAcaoCadastro;
import ecar.pojo.simpr.WsAutenticacaoUsuario;
import ecar.pojo.simpr.WsIndicador;
import ecar.webservices.exc.EcarWSException;

/**
 * @author gekson.silva
 *
 */
@WebService
public interface EcarWS  {

	Integer[] listaAcao(Integer chvExternoMinimo, Integer chvExternoMaximo, WsAutenticacaoUsuario autenticacao) throws EcarWSException;		
	
	WsAcao obtemAcao(Integer chvExterno, WsAutenticacaoUsuario autenticacao) throws EcarWSException ;
	
	WsAcaoCadastro obtemAcaoCadastro(Integer chvExterno, WsAutenticacaoUsuario autenticacao) throws EcarWSException;
	
	WsAcaoAtividade[] obtemAcaoAtividade(Integer chvExterno, WsAutenticacaoUsuario autenticacao) throws EcarWSException;
	
	Integer[] listaIndicador(Integer chvExternoMinimo, Integer chvExternoMaximo, WsAutenticacaoUsuario autenticacao) throws EcarWSException;
	
	WsIndicador obtemIndicador(Integer chvExterno, WsAutenticacaoUsuario autenticacao) throws EcarWSException;
	
	void enviarLog(String log, WsAutenticacaoUsuario autenticacao) throws EcarWSException;
	
//	Integer[] listaObra(Integer chvExternoMinimo, Integer chvExternoMaximo);
	
//	WsObra obtemObra(Integer chvExterno);	
}
