package ecar.webservices;

import java.util.Date;

import org.apache.log4j.Logger;

import ecar.dao.UsuarioDao;
import ecar.exception.ECARException;
import ecar.pojo.SessionWS;
import ecar.pojo.UsuarioUsu;
import ecar.webservices.exc.SegurancaWSException;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.sentinela.client.autenticacao.AutenticacaoSentinelaBD;
import gov.pr.celepar.sentinela.client.pojo.Usuario;
import gov.pr.celepar.sentinela.excecao.SentinelaException;


/**
 * 
 * @author Rafael Freitas de Morais
 * 30/06/2012
 */
public class SegurancaWS {

	protected Logger logger = Logger.getLogger(SegurancaWS.class);
	
	private static SegurancaWS instance;
	private static final String SECUTIRY_CODE = "KBV5I61HF3RWV9C01LG246TVD"; 

	private SegurancaWS() {
		logger.debug("Iniciando SegurancaWS...");
	}
	
	public static SegurancaWS getInstance() {
		if(instance == null) {
			return instance = new SegurancaWS();
		}else {
			return instance;
		}
	}
	
	public UsuarioUsu autentica(String usuario, String senha) throws SegurancaWSException{
		AutenticacaoSentinelaBD sentinelaBD = new AutenticacaoSentinelaBD();
		UsuarioDao usuarioDao = new UsuarioDao();
		try {
			logger.debug("Autenticando no Sentinela...");
			Usuario sentinelaUser = sentinelaBD.autentica(usuario, senha, SECUTIRY_CODE);
			
			if(sentinelaUser == null) {
				throw new SegurancaWSException("FALHA NA AUTENTICACAO");
			}
			logger.debug("Sentinela OK Usuario: " + sentinelaUser.getCodUsuario());
			
			UsuarioUsu usu = usuarioDao.getUsuarioUsuByEmail(sentinelaUser.getEmail());
			logger.debug("Autenticacao realizada, usuarioUsu carregado...");
			
			logger.debug("Verificando existencia de SessionWS para usuario: [" + usu.getEmail1Usu()+"]");
//			SessionWS sessionWS = usuarioDao.verificaSessionWS(usu).getSessionWS();
			SessionWS sessionWS = usu.getSessionWS();
			if(sessionWS != null) {
				logger.debug("SessionWS localizada, verificando validade...");
				if(verificaValidade(usu)) {
					logger.debug("SessionWS verificada. Valida");					
					return usu;
				}else {
					usuarioDao.atualizaSessionWS(usu, true);
					logger.debug("SessionWS verificada. Invalida. Gerando nova...");
					return usu;
				}
			}
			
			logger.debug("Gerando SessionWS...");
			
			usuarioDao.criarSessionWS(usu);
			
			return usu;
		} catch (SentinelaException e) {
			throw new SegurancaWSException(e.getMessage());
		} catch (ECARException e) {
			throw new SegurancaWSException("Problemas em UsuarioDao.", e);
		}
	}
	
	private boolean verificaValidade(UsuarioUsu usuarioUsu) {
		
		if(true) {
			return true;
		}
		
		if(usuarioUsu == null) {
			return false;
		}
		UsuarioDao usuarioDao = new UsuarioDao();
		
		long difMilli = (new Date()).getTime() - usuarioUsu.getSessionWS().getDataHoraInicio().getTime();
		int segundos = (int) difMilli / 1000;
		int hora;
		hora = segundos / 3600;
		System.out.println("################## verificaValidade " + hora);
		if(hora < 1){
			try {
				usuarioDao.atualizaSessionWS(usuarioUsu, false);
			} catch (ECARException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
				
//		try {		
//			SessionWS sessionWS = usuarioUsu.getSessionWS();
//			usuarioUsu.getSessionWS().setUsuarioUsu(null);
//			usuarioUsu.setDataUltAcessoUsu(new Date());
//			usuarioUsu.setSessionWS(null);
//			usuarioUsu.getSessao().clear();
//			usuarioDao.alterar(usuarioUsu);
			
//			sessionWS.setUsuarioUsu(null);
//			usuarioDao.removeSessionWS(sessionWS);
//		} catch (ECARException e) {			
//			e.printStackTrace();
//		}
		return false;
	}
	
	public boolean verificaSessao(String uuid) throws SegurancaWSException{
		UsuarioDao usuarioDao = new UsuarioDao();
		try {
			UsuarioUsu usuarioUsu = usuarioDao.verificaSessionWS(uuid);
			return verificaValidade(usuarioUsu);
		} catch (ECARException e) {
			throw new SegurancaWSException("Problemas em UsuarioDao.", e);
		}		
	}

	public Seguranca autenticacaoOk(String uuid) {
		Seguranca seguranca = new Seguranca();
		
		seguranca.setAutenticado(true);
		seguranca.setUuid(uuid);
		
		return seguranca;
	}
	
	public Seguranca autenticacaoFALHA() {
		return new Seguranca();
	}
	
	public void logOff(String uuid)  throws SegurancaWSException{
		UsuarioDao usuarioDao = new UsuarioDao();
		try {
			UsuarioUsu usuarioUsu = usuarioDao.verificaSessionWS(uuid);
			if(usuarioUsu != null) {
				usuarioDao.removeSessao(usuarioUsu);	
			}			
		} catch (ECARException e) {
			throw new SegurancaWSException("Problemas em UsuarioDao.", e);
		}	
	}
}