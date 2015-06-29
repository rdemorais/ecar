<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.evento.EventoAtivarMonitoramento"/>
<jsp:directive.page import="ecar.evento.EventoDesativarMonitoramento"/>
<jsp:directive.page import="ecar.evento.EventoBloquearPlanejamento"/>
<jsp:directive.page import="ecar.evento.EventoDesbloquearPlanejamento"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<jsp:directive.page import="ecar.pojo.FuncaoFun"/>
<%@page import="ecar.dao.SisAtributoDao"%>
<%@page import="ecar.pojo.SisAtributoSatb"%>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@page import="ecar.pojo.ItemEstruturaIett"%>
<%@page import="ecar.pojo.UsuarioUsu"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.email.AgendadorEmail" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> 
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgmPK" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgm" %>
<%@ page import="ecar.dao.TfuncacompConfigmailTfacfgmDAO" %>
<%@ page import="ecar.pojo.EmpresaEmp" %>
<%@ page import="ecar.dao.EmpresaDao" %>
<%@ page import="comum.database.Dao" %>
<%@ page import="comum.util.Util" %>
<%@ page import="ecar.historico.HistoricoIett" %>
<%@ page import="ecar.pojo.HistoricoIettH" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.evento.Evento" %>
<%@ page import="ecar.evento.URLEvento" %>
<%@ page import="ecar.api.facade.*" %>

<%@page import="ecar.evento.EventoManutencaoFuncaoAcompanhamento"%><html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
</head>
<body>
<form name="form" method="post">   

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAba" value=<%=Pagina.getParamStr(request, "codAba")%>>
<input type="hidden" name="codAri"        value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type="hidden" name="codAcomp"      value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<!-- campo hidden para nao perder variaveis necessarias ao botao avançar e retroceder e o link voltar -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">



<%
	String msg = "";
	String submit = "";
	UsuarioUsu usuario = new UsuarioUsu();
	String descricaoEvento = null;
	boolean enviarMail = false;
	Long codIett = null;
	int codConfigMailCfgm = 0; 

	EmpresaDao empDAO = new EmpresaDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);			
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	FuncaoDao funcaoDao = new FuncaoDao(request);
	TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
	UsuarioDao usuDAO = new UsuarioDao();
	Dao dao = new Dao();
	Evento evento = null;
	ItemEstruturaIett itemEstruturaAnterior = null;
	List listaFuncaoAnterior = null;
	

	try{
			ItemEstruturaIett itemEstrutura = new ItemEstruturaIett();
			if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){ 
			
				itemEstruturaDao.setItemEstrutura(request, itemEstrutura);
				usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
				itemEstrutura.setUsuarioUsuByCodUsuIncIett(usuario);
				//Obtem a Função de Dados Gerais
				funcaoDao = new FuncaoDao(request); 
				FuncaoFun funcao = funcaoDao.getFuncaoDadosGerais();
				
				itemEstruturaDao.salvar(request, itemEstrutura, funcao);
				submit = "frm_con.jsp";
				msg = _msg.getMensagem("itemEstrutura.dadosBasicos.inclusao.sucesso");
				
				codIett = itemEstrutura.getCodIett();				
			}	
			else if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
				
				usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
				
				ItemEstrutura itemEst = new ItemEstrutura(Long.valueOf(Pagina.getParamStr(request, "codIett")));
				
				String dataInicio = request.getParameter("dataInicioIett");
				String dataTermino = request.getParameter("dataTerminoIett");
				
				if(itemEst.podeAlterarData(dataInicio, dataTermino) == false){
					submit = "frm_con.jsp";
				
					String keyAndProperties = itemEst.getErrorKey();
					int length = keyAndProperties.split("#").length;
					String key = keyAndProperties.split("#")[0];
					String parameters[] = new String[length-1];
					
					for(int i=1; i < length; i++){
						parameters[i-1] = keyAndProperties.split("#")[i]; 	
					}
					
					msg = _msg.getMensagem(key, parameters);
					
					itemEstrutura = itemEst.getRealObject();
				}else{
			
		            /***Historico***/
		  		    HistoricoIett historico = new HistoricoIett(itemEstrutura, 
	   	            												HistoricoIett.alteracao,
		            												itemEstruturaDao.getSession(),
		            												new ConfiguracaoDao(request),
		            												request);
		            /***Historico***/
					
					
					itemEstruturaAnterior = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
					listaFuncaoAnterior =  new ArrayList(itemEstruturaAnterior.getItemEstUsutpfuacIettutfas());
					itemEstrutura = itemEstruturaDao.alterar(request, usuario, historico);;
					submit = "frm_con.jsp";
					msg = _msg.getMensagem("itemEstrutura.dadosBasicos.alteracao.sucesso");
					
					codConfigMailCfgm = Dominios.CFG_MAIL_MANUTENCAO_FUNCOES_ACOMPANHAMENTO.intValue(); 
					evento = new EventoManutencaoFuncaoAcompanhamento();	
					enviarMail = true;
					codIett = itemEstrutura.getCodIett();
				}	
			}else if("alterarMonitoramento".equals(Pagina.getParamStr(request, "hidAcao"))){

				usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
				
				 
	            /***Historico***/
	            HistoricoIett historico = new HistoricoIett(itemEstrutura,
	            												HistoricoIett.alteracao,
	             												itemEstruturaDao.getSession(),
	            												new ConfiguracaoDao(request),
	            												request);
	            /***Historico***/
				
				itemEstrutura = itemEstruturaDao.alterarMonitoramento(request, usuario, historico);;
				
				if("S".equals(itemEstrutura.getIndMonitoramentoIett())){
					descricaoEvento = "Ativar Monitoramento";
					evento = new EventoAtivarMonitoramento();
					codConfigMailCfgm = Dominios.CFG_MAIL_ATIVAR_MONITORAMENTO.intValue();
				} else {
					descricaoEvento = "Desativar Monitoramento";
					evento = new EventoDesativarMonitoramento();
					codConfigMailCfgm = Dominios.CFG_MAIL_DESATIVAR_MONITORAMENTO.intValue();
				}
				
				enviarMail = true;
				
				codIett = itemEstrutura.getCodIett();
				
				submit = "frm_con.jsp";
				msg = _msg.getMensagem("itemEstrutura.dadosBasicos.alteracao.sucesso");
			}			
			else if("alterarPlanejamento".equals(Pagina.getParamStr(request, "hidAcao"))){
				
				usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
				
	            /***Historico***/
	            HistoricoIett historico = new HistoricoIett(itemEstrutura,
   	            												HistoricoIett.alteracao,
	            												itemEstruturaDao.getSession(),
	            												new ConfiguracaoDao(request),
	            												request);
	            /***Historico***/
				
				itemEstrutura = itemEstruturaDao.alterarPlanejamento(request, usuario, historico);
				
				if("S".equals(itemEstrutura.getIndBloqPlanejamentoIett())){
					descricaoEvento = "Bloquear Planejamento";
					evento = new EventoBloquearPlanejamento();
					codConfigMailCfgm = Dominios.CFG_MAIL_BLOQUEAR_PLANEJAMENTO.intValue();
				} else {
					descricaoEvento = "Desbloquear Planejamento";
					evento = new EventoDesbloquearPlanejamento();
					codConfigMailCfgm = Dominios.CFG_MAIL_DESBLOQUEAR_PLANEJAMENTO.intValue();
				}
				
				enviarMail = true;
				
				codIett = itemEstrutura.getCodIett();
				
				submit = "frm_con.jsp";
				msg = _msg.getMensagem("itemEstrutura.dadosBasicos.alteracao.sucesso");
			}	
%>
		<!-- campo hidden para cada chave primaria da tabela --> 
		<input type="hidden" name="codIett" value=<%=itemEstrutura.getCodIett()%>>
		<input type="hidden" name="codEtt" value=<%=itemEstrutura.getEstruturaEtt().getCodEtt()%>>
	
<%			
			if (enviarMail == true){
				if( "S".equals(Pagina.getParamStr(request, "autorizarMail")) ) {
								
					String link = null;
					
					//seta os atributos pra montar o link no envio de email
 					request.setAttribute("codIett",itemEstrutura.getCodIett().toString());
 					long codAbaDadosGerais = funcaoDao. getCodFuncaoDadosGerais();
 					String codAba = String.valueOf(codAbaDadosGerais);
					request.setAttribute("codAba", codAba);
					request.setAttribute("codEttSelecionado", itemEstrutura.getEstruturaEtt().getCodEtt().toString()); 
																				     
				    link = URLEvento.montaURLEvento(evento, request);
				    
					ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Integer.valueOf(codConfigMailCfgm));
				
					ItemEstruturaIett item = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, codIett);	
					List listIett = new ArrayList(item.getItemEstUsutpfuacIettutfas());
					Iterator itListIett = listIett.iterator();
				 
					// data limite para ...
					String dataLimitePara = "";
					String dataLimiteParaAnt = "";	
					
					descricaoEvento = "";
					
					
					List usuariosExcluidos = new ArrayList();
					Iterator itListIettMail = listIett.iterator(); 
					
					/**********Pesquisa quais as funções de acompanhamento que foram alteradadas :***************/ 
					if(codConfigMailCfgm == Dominios.CFG_MAIL_MANUTENCAO_FUNCOES_ACOMPANHAMENTO.intValue()) {
					
						// Todos os usuarios escolhidos e excluidos recebem email 
						
						// Usuario  ->  Usuario	 ou Vazio -> Usuario 
						while(itListIettMail.hasNext()) {
							
							ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itListIettMail.next();
							TipoFuncAcompTpfa tpfa = itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa();
					 
							String nomeOriginal = Pagina.getParamStr(request, "nomeFun" + tpfa.getCodTpfa() +"Original");
							String codOriginal = Pagina.getParamStr(request, "codigoFun" + tpfa.getCodTpfa());
							
							if(nomeOriginal != null) { 
									
									
								// se for usuario
								if(itemEstUsutpfacIetutfa.getUsuarioUsu() != null && !nomeOriginal.equals(itemEstUsutpfacIetutfa.getUsuarioUsu().getNomeUsu())){
								
									if(!nomeOriginal.equals("")) {
										descricaoEvento += "Alteração da Função de Acompanhamento " + tpfa.getLabelTpfa() +
										". Responsável Anterior: " + nomeOriginal + ". Responsável Atual: " + itemEstUsutpfacIetutfa.getUsuarioUsu().getNomeUsu() + ".\n";
									} else {
										descricaoEvento += "Alteração da Função de Acompanhamento " + tpfa.getLabelTpfa() + 
														". Responsável Anterior:   . Responsável Atual: " + 
										 		        itemEstUsutpfacIetutfa.getUsuarioUsu().getNomeUsu() + ".\n";
									}
										
										
									//adiciona o usuario excluido a lista de usuarios para os quais email vao ser enviados. 
									UsuarioDao usuarioDao = new UsuarioDao(request);
									if (codOriginal != null && !"".equals(codOriginal) && codOriginal.startsWith("U")) {
										UsuarioUsu usuarioExcluido = (UsuarioUsu) usuarioDao.buscar(UsuarioUsu.class, Long.valueOf(codOriginal.substring(1)));
										usuariosExcluidos.add(usuarioExcluido);
									}
									if (codOriginal != null && !"".equals(codOriginal) && codOriginal.startsWith("G")) {
										SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
										SisAtributoSatb sisAtributoSatbExcluido = (SisAtributoSatb) sisAtributoDao.buscar(SisAtributoSatb.class,Long.valueOf(codOriginal.substring(1)));
										if(sisAtributoSatbExcluido != null)
											usuariosExcluidos.addAll(usuDAO.getUsuariosBySisAtributoSatb(sisAtributoSatbExcluido));
									}
									
								} 
							}
						}
						
						
						
						Iterator itListIettMailAnt = listaFuncaoAnterior.iterator();
						boolean existeAnterior = false;
						ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfaAnt = null;
						TipoFuncAcompTpfa tpfaAnt = null;
							
						
						// Usuario  ->  Vazio	
						while(itListIettMailAnt.hasNext()) {			
					
							List usuarioRemovidos = new ArrayList();			
									
							itemEstUsutpfacIetutfaAnt = (ItemEstUsutpfuacIettutfa) itListIettMailAnt.next();
							tpfaAnt = itemEstUsutpfacIetutfaAnt.getTipoFuncAcompTpfa();
							itListIettMail = listIett.iterator();
							
							String nomeOriginal = Pagina.getParamStr(request, "nomeFun" + tpfaAnt.getCodTpfa() +"Original");
							String codOriginal = Pagina.getParamStr(request, "codigoFun" + tpfaAnt.getCodTpfa());
						
							
							while (itListIettMail.hasNext()) {
							
								existeAnterior = false;
								ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itListIettMail.next();
								TipoFuncAcompTpfa tpfa = itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa();
					 
								if(tpfaAnt.equals(tpfa)) {
									existeAnterior = true;	
									break;
								}
							}	
										
									
							if(!existeAnterior) {	
					 
								// se tiver limpado o usuario da funcao de acompanhamento
								descricaoEvento += "Alteração da Função de Acompanhamento " + tpfaAnt.getLabelTpfa() + 
												       ". Responsável Anterior: "+ nomeOriginal +". Responsável Atual:  " + 
								 		               ".\n";			
								
										
								//adiciona o usuario excluido a lista de usuarios para os quais email vao ser enviados. 
								UsuarioDao usuarioDao = new UsuarioDao(request);
								if (codOriginal != null && !"".equals(codOriginal) && codOriginal.startsWith("U")) {
									UsuarioUsu usuarioExcluido = (UsuarioUsu) usuarioDao.buscar(UsuarioUsu.class, Long.valueOf(codOriginal.substring(1)));
									usuarioRemovidos.add(usuarioExcluido);
								}
								if (codOriginal != null && !"".equals(codOriginal) && codOriginal.startsWith("G")) {
									SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
									SisAtributoSatb sisAtributoSatbExcluido = (SisAtributoSatb) sisAtributoDao.buscar(SisAtributoSatb.class,Long.valueOf(codOriginal.substring(1)));
									if(sisAtributoSatbExcluido != null)
									usuarioRemovidos.addAll(usuDAO.getUsuariosBySisAtributoSatb(sisAtributoSatbExcluido));
								}
										
							}
							
							
							TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
							tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
							tfcfgmPK.setCodTpfa(tpfaAnt.getCodTpfa());
												
							TfuncacompConfigmailTfacfgm tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
					 
							if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {
		    					
								
								Iterator itUsu = usuarioRemovidos.iterator();
								
								while (itUsu.hasNext()){
								
									UsuarioUsu usu = (UsuarioUsu) itUsu.next();
								
									String textoMail = "";
									String assunto   = "";
									String remetente = "";
											
									if( configMailCfgm.getTextosSiteMail() != null ) {
										textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
										assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
										remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
									}
											
									// se nao tem remetente configurado no textoSite, pega da empresa.
									if( "".equals(remetente) || remetente == null ) {
										List listEmpresa = new ArrayList();
										EmpresaEmp emp = null;
								
										listEmpresa = empDAO.listar(EmpresaEmp.class, null);
									
										Iterator it = listEmpresa.iterator();
										while( it.hasNext() ) {
											emp = (EmpresaEmp) it.next();
										}
									 
										remetente = emp.getEmailContatoEmp();
									}
													
									AgendadorEmail ae = new AgendadorEmail();
													
									String html = null;		
									
									html = ae.montaEmailComLink(textoMail, usu.getNomeUsu(), null, codIett, descricaoEvento, null, null, null, null, link).toString();
									ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
								}
							}
				 		}
					}
					
					/**********Pesquisa quais as funções de acompanhamento que foram alteradadas :***************/		
					
					
								
					
					while( itListIett.hasNext() ) {
					
						ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itListIett.next();
						TipoFuncAcompTpfa tpfa = itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa();
				 
						TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
						tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
						tfcfgmPK.setCodTpfa(tpfa.getCodTpfa());
											
						TfuncacompConfigmailTfacfgm tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
				 
						if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {
						
							
							List usuarios = new ArrayList();
							if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
								usuarios.add((UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
							} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null){
								usuarios.addAll(usuDAO.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
							}
							
							Iterator itUsu = usuarios.iterator();
							
							while (itUsu.hasNext()){
							
								UsuarioUsu usu = (UsuarioUsu) itUsu.next();
							
								String textoMail = "";
								String assunto   = "";
								String remetente = "";
										
								if( configMailCfgm.getTextosSiteMail() != null ) {
									textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
									assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
									remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
								}
										
								// se nao tem remetente configurado no textoSite, pega da empresa.
								if( "".equals(remetente) || remetente == null ) {
									List listEmpresa = new ArrayList();
									EmpresaEmp emp = null;
							
									listEmpresa = empDAO.listar(EmpresaEmp.class, null);
								
									Iterator it = listEmpresa.iterator();
									while( it.hasNext() ) {
										emp = (EmpresaEmp) it.next();
									}
								 
									remetente = emp.getEmailContatoEmp();
								}
												
								AgendadorEmail ae = new AgendadorEmail();
												
								String html = null;				
								
								html = ae.montaEmailComLink(textoMail, usu.getNomeUsu(), null, codIett, descricaoEvento, null, null, null, null, link).toString();
								ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
							}
						}
					}
				}
			}
			
			
	}
	catch ( ECARException e){
		e.printStackTrace();
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "frm_con.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	%>

<script>
			document.form.action = "<%=submit%>";
			document.form.submit();
</script>

</form>
</body>
</html>