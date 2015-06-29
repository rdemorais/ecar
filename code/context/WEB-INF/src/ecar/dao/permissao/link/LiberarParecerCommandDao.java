package ecar.dao.permissao.link;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import comum.database.Dao;

import ecar.dao.AbaDao;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.EstruturaAcessoDao;
import ecar.dao.ItemEstUsutpfuacDao;
import ecar.dao.TipoAcompanhamentoDao;
import ecar.dao.TipoFuncAcompDao;
import ecar.evento.URLEvento;
import ecar.exception.ECARException;
import ecar.exception.PermissaoAcessoLinkException;
import ecar.login.SegurancaECAR;
import ecar.pojo.Aba;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.UsuarioAtributoUsua;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;


/*
 * Created on 15/12/2008
 * 
 */

/**
 *
 * @author 70744416353
 */
public class LiberarParecerCommandDao extends Dao implements PermissaoAcessoLinkCommand {
	
	/**
	 * Verifica a permissao de acesso do usuário e monta a URL de redirecionamento
	 * 
         * @param evento
         * @param parametros
	 * @param seguranca
	 * @param servletContext
         * @return
         * @throws ECARException
	 */
	 public String execute(String evento, String parametros, SegurancaECAR seguranca, ServletContext servletContext) throws ECARException {
		 
			
		String url = null;
			
		try {
			permissaoAcesso(parametros, seguranca);
			url = URLEvento.montaURLRedirecionamento(Dominios.CFG_MAIL_LIBERACAO_PARECER.toString(), parametros, servletContext);
			
		}  catch(ECARException e) {
			
			//guarda a mensagem que o usuário deve exibir na tela
			if(e.getCausaRaiz() != null) {
				String mensagemErro = e.getCausaRaiz().getMessage();
				throw new ECARException(mensagemErro);
		
			}
		}	
		
		return url; 
	 }


		/**
	     * Verifica a permissao de acesso do usuário
	     * 
                 * @param parametros
                 * @param seguranca
                 * @throws PermissaoAcessoLinkException
	     */	
		public void permissaoAcesso(String parametros, SegurancaECAR seguranca) throws PermissaoAcessoLinkException {
			
			int posicao = 0;

			//recupera os parametros passados no link
			String codTipoAcompanhamento= "";
			String referencia_hidden = "";
			String periodo = "";
			String codAri = "";
			String[] valores = parametros.split(",");
				
			for(int i=0; i< valores.length; i++) {
				
				if(valores[i].contains("codTipoAcompanhamento")) {
					
					posicao =	valores[i].indexOf("=");
					String nomeParametro = valores[i];
					codTipoAcompanhamento= valores[i].substring(posicao+1, (valores[i].length()));
				
				
				} else if(valores[i].contains("referencia_hidden")) {
					
					posicao = valores[i].indexOf("=");
					String nomeParametro = valores[i];
					referencia_hidden = valores[i].substring(posicao+1, (valores[i].length()));
					
				} else if(valores[i].contains("codAri")) {
					
					posicao = valores[i].indexOf("=");
					String nomeParametro = valores[i];
					codAri = valores[i].substring(posicao+1, (valores[i].length()));
					
				}
			
			}
		
			
			
			//PERMISSAO DE ACESSO DA PARTE DE REGISTRO DE MONITORAMENTO 
			int qtdePeriodosAnteriores = 1;
			Collection periodosConsiderados = new ArrayList();
			AcompReferenciaItemAri ari = null;
			ItemEstruturaIett item = null;
			AcompReferenciaAref acompReferencia = null;
			TipoAcompanhamentoTa tipoAcompanhamento = null;
			List tpfaOrdenadosPorEstrutura = null;
			TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(null);
			EstruturaAcessoDao estruturaAcessoDao = new EstruturaAcessoDao(null);
			AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null);
			AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(null);
			TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(null);
			ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(null);
			AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
			AbaDao abaDao = new AbaDao(null);
			boolean usuarioLogadoEmiteParecer = false;
			boolean permissaoLapis = false;
			boolean permissaoAdministradorAcompanhamento = false;
			String exibeAba = null;
			Iterator itPeriodosAcao = null;
			Map  mapAcao = null;
			
			UsuarioUsu usuario = seguranca.getUsuario();
			
				
			try {
				
				try {
					
					if(codTipoAcompanhamento != null && !codTipoAcompanhamento.equals("")) {
						tipoAcompanhamento = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
						permissaoAdministradorAcompanhamento = estruturaAcessoDao.temPermissoesAcessoAcomp(tipoAcompanhamento, seguranca.getGruposAcesso());
					}	
				} catch(Exception e) {
					//NAO EXISTE O TIPO DE ACOMPANHAMENTO;
					throw new PermissaoAcessoLinkException(ErroPermissaoAcessoLinkEnum.TIPO_DE_ACOMPANHAMENTO_INEXISTENTE);
				}
				
				
				try {

					if(referencia_hidden != null && !referencia_hidden.equals("")) {
						periodosConsiderados = acompReferenciaDao.getPeriodosAnterioresOrdenado(Long.valueOf(referencia_hidden), qtdePeriodosAnteriores,  
										Long.valueOf(codTipoAcompanhamento), false);
					}		
					
				} catch(Exception e) {
					//NAO EXISTE A REFERENCIA;
					throw new PermissaoAcessoLinkException(ErroPermissaoAcessoLinkEnum.REFERENCIA_INEXISTENTE);
				} 
				
				
				try{
					// Busca coleção com o período a ser considereado
					if(codAri != null && !codAri.equals("")) {
						ari = (AcompReferenciaItemAri) ariDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(codAri));
						item = ari.getItemEstruturaIett();
					}	
				} catch(Exception e) {
					//NAO EXISTE O ACOMPANHAMENTO;
					throw new PermissaoAcessoLinkException(ErroPermissaoAcessoLinkEnum.ACOMPANHAMENTO_INEXISTENTE);
				}
				
				
			
			    //verifica se aba situação está configurada para aparecer
				Collection lista =  abaDao.listarAbasMonitoramento();
				if(lista != null) {
					Iterator it =  lista.iterator();
					while (it.hasNext()) {
						Aba aba = (Aba) it.next();
						if(aba.getNomeAba().equals("SITUACAO")) {
							exibeAba = aba.getExibePosicaoAba();
							break;
						}
					}
						
					if(exibeAba == null || exibeAba.equals("N")) {
						//ABA_INEXISTENTE;
						throw new PermissaoAcessoLinkException(ErroPermissaoAcessoLinkEnum.ABA_INEXISTENTE);
					}
				}	
					
				
				
				if (item.getIndAtivoIett() == null || "".equals(item.getIndAtivoIett().trim()) || "N".equals(item.getIndAtivoIett().toUpperCase())) {
					
					//O ITEM ESTÁ INATIVO;
					throw new PermissaoAcessoLinkException(ErroPermissaoAcessoLinkEnum.ITEM_INATIVO);
				
				//verifica se o usuário pode "Gerar Período de Acompanhamento"
				} else if(permissaoAdministradorAcompanhamento) {
					permissaoLapis = true;
					
				} else {	
					
					
					itPeriodosAcao = periodosConsiderados.iterator();
					mapAcao = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsiderados, item);
					tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
					
			
					if(itPeriodosAcao != null && itPeriodosAcao.hasNext()) {
						//Pega só o período selecionado (Aref), que é o primeiro
						acompReferencia = (AcompReferenciaAref) itPeriodosAcao.next();
						if(!mapAcao.isEmpty() && mapAcao.containsKey(acompReferencia)) {
							AcompReferenciaItemAri ariAcao = (AcompReferenciaItemAri) mapAcao.get(acompReferencia);
											
							//Pega os Arels do Ari selecionado 
							List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ariAcao, tpfaOrdenadosPorEstrutura);
							Iterator itRelatorios = relatorios.iterator();
							
							if(itRelatorios != null) {
								while(itRelatorios.hasNext()){												
									AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();										
													
									ItemEstUsutpfuacIettutfa itemEstUsu 
														= itemEstUsuDao.buscar(item.getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
													 
									//Verifica se a permissão é de grupo ou usuário
									if(itemEstUsu!=null && usuario != null) {							 							
										if (itemEstUsu.getUsuarioUsu() != null) {
											usuarioLogadoEmiteParecer = itemEstUsu.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
										} else if (itemEstUsu.getSisAtributoSatb() != null) {
											if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
												Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
												if(itUsuarios != null) {
													while (itUsuarios.hasNext()) {
														UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
															if (usuarioAtributoUsua!=null && usuarioAtributoUsua.getUsuarioUsu()!=null && 
																	usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
																usuarioLogadoEmiteParecer = true;
																break;
															}
													}
												}	
											}
										}
									}	
									
									if(usuarioLogadoEmiteParecer==true)
										break;											
								}	
								
							} 
							
						} 
						
						if(!(usuarioLogadoEmiteParecer || permissaoLapis)) {  
							//NÃO TEM PERMISSAO PARA ACESSAR O ACOMPANHAMENTO
							throw new PermissaoAcessoLinkException(ErroPermissaoAcessoLinkEnum.SEM_PERMISSAO_ACOMPANHAMENTO);
						}  
						
					}  
					
				}
					
			}	catch(ECARException e) {
				this.logger.error(e);
				throw new PermissaoAcessoLinkException(e);
			}
			
		}	
		
}	