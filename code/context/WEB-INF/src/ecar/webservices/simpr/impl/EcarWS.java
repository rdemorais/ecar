/**
 * 
 */
package ecar.webservices.simpr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.soap.MTOM;

import org.apache.log4j.Logger;
import org.jboss.wsf.spi.annotation.WebContext;

import ecar.dao.EstruturaDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.TipoFuncAcompDao;
import ecar.exception.ECARException;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.simpr.WsAcao;
import ecar.pojo.simpr.WsAcaoAtividade;
import ecar.pojo.simpr.WsAcaoAtividadeCadastro;
import ecar.pojo.simpr.WsAcaoCadastro;
import ecar.pojo.simpr.WsAutenticacaoUsuario;
import ecar.pojo.simpr.WsIndicador;
import ecar.webservices.exc.EcarWSException;
import ecar.webservices.exc.EcarWSExceptionHandler;
import ecar.webservices.exc.ExceptionGlossario;

/**
 * @author gekson.silva
 *
 */

@MTOM(threshold = 0)
@Remote(ecar.webservices.simpr.EcarWS.class)//wsdlLocation="https://aplicacao-homologacao.saude.gov.br",
@WebService(name = "EcarWS", targetNamespace = "https://aplicacao-homologacao.saude.gov.br/pe-homologa/EcarWS", serviceName = "EcarWS")
@SOAPBinding(style = Style.DOCUMENT)
@Stateless
@WebContext(contextRoot = "/pe-homologa")
//@WebContext(contextRoot = "/pe")
public class EcarWS implements ecar.webservices.simpr.EcarWS {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@WebMethod
	public Integer[] listaAcao(Integer chvExternoMinimo,
			Integer chvExternoMaximo, WsAutenticacaoUsuario autenticacao) throws EcarWSException {	
		try {
			ItemEstruturaDao dao = new ItemEstruturaDao(null);
			EstruturaEtt resul;
			EstruturaEtt prod;
			Integer chvMin = 0;
			Integer chvMax = 0;
			if(chvExternoMinimo != null) {
				chvMin = chvExternoMinimo;
			}
			
			if(chvExternoMaximo != null) {
				chvMax = chvExternoMaximo;
			}
			
			if(chvMax < chvMin) {
				throw new EcarWSException(ExceptionGlossario.ERRO_CHAVE_MAX_MENOR_CHAVE_MIN);
			}
			
			resul = (EstruturaEtt) new EstruturaDao(null).buscar(EstruturaEtt.class, 4L);
			prod = (EstruturaEtt) new EstruturaDao(null).buscar(EstruturaEtt.class, 5L);
			Integer[] ids = dao.listaAcaoSimPR(chvMin, chvMax, resul, prod);
			logger.debug("listaAcao(): Parametros: RESUL=4, PROD=5, chvExternoMinimo="+chvExternoMinimo+",chvExternoMaximo="+chvExternoMaximo);
			logger.debug("Quantidade de itens carregados: " + ids.length);
			return ids;
		} catch (Exception e) {
			e.printStackTrace();
			new EcarWSExceptionHandler().fireException(e);
			return null;
		}
	}
	
	@WebMethod
	public Integer[] listaIndicador(Integer chvExternoMinimo,
			Integer chvExternoMaximo, WsAutenticacaoUsuario autenticacao) throws EcarWSException {
		return null;
	}

	@WebMethod
	public WsAcao obtemAcao(Integer chvExterno, WsAutenticacaoUsuario autenticacao) throws EcarWSException  {
		WsAcao a = new WsAcao();

		buscaAcaoCadastro(chvExterno, a);
		
		buscaAcaoTarefa(chvExterno, a);
		
		return a;
	}

	private void buscaAcaoTarefa(Integer chvExterno, WsAcao a) {
		try {
			ItemEstruturaDao dao = new ItemEstruturaDao(null);
			Map<String, Object> params = new HashMap<String, Object>();
			boolean isIntermediario = false;
			params.put("prioridadeTarefa", "0");
			
			//params.put("tpfa", (TipoFuncAcompTpfa) new TipoFuncAcompDao().buscar(TipoFuncAcompTpfa.class, 7L));
			
			ItemEstruturaIett iett = dao.getItemEtruturaById(chvExterno.longValue());
			
			isIntermediario = (iett.getEstruturaEtt().getCodEtt() == 5 ? false : true);
			
			//descricaoR5 está sendo usada para exceção do SOS Emergencias. Plano de Acao dos Hospitais
			if(iett.getDescricaoR5().equals("EXC")) {
				isIntermediario = false;
			}
			
			List<WsAcaoAtividadeCadastro> tarefas = dao.obtemAcaoTarefa(chvExterno.longValue(), params, true, isIntermediario);
							
			List<WsAcaoAtividade> acoesAtividade = new ArrayList<WsAcaoAtividade>();
			
			for (WsAcaoAtividadeCadastro wsAcaoAtividadeCadastro : tarefas) {
				WsAcaoAtividade acaoTarefa = new WsAcaoAtividade();
				acaoTarefa.setDadosCadastro(wsAcaoAtividadeCadastro);
				acoesAtividade.add(acaoTarefa);				
			}
			
			WsAcaoAtividade[] acaoT = new WsAcaoAtividade[acoesAtividade.size()];
			acoesAtividade.toArray(acaoT);
			
			a.setDadosAtividades(acaoT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buscaAcaoCadastro(Integer chvExterno, WsAcao a) {
		try {
			ItemEstruturaDao dao = new ItemEstruturaDao(null);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cnpj", "00394544012787");
			params.put("nomeEmpresa", "Ministerio da Saude");
			params.put("tpfa", (TipoFuncAcompTpfa) new TipoFuncAcompDao().buscar(TipoFuncAcompTpfa.class, 3L));
			WsAcaoCadastro acao = dao.obtemAcao(chvExterno.longValue(), params);
			
			a.setDadosCadastro(acao);
			
		} catch (ECARException e) {
			e.printStackTrace();			
		}
	}

	@WebMethod
	public WsAcaoCadastro obtemAcaoCadastro(Integer chvExterno, WsAutenticacaoUsuario autenticacao) throws EcarWSException {
		WsAcao a = new WsAcao();

		buscaAcaoCadastro(chvExterno, a);
		return a.getDadosCadastro();
	}

	@WebMethod
	public WsAcaoAtividade[] obtemAcaoAtividade(Integer chvExterno, WsAutenticacaoUsuario autenticacao) throws EcarWSException {
		WsAcao a = new WsAcao();

		buscaAcaoTarefa(chvExterno, a);
		return a.getDadosAtividades();
	}

	@WebMethod
	public WsIndicador obtemIndicador(Integer chvExterno, WsAutenticacaoUsuario autenticacao) throws EcarWSException {
		return null;
	}

	@WebMethod
	public void enviarLog(String log, WsAutenticacaoUsuario autenticacao) throws EcarWSException {
		// TODO Auto-generated method stub
		
	}
	
}
