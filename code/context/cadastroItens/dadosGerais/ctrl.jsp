<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="ecar.pojo.EstruturaEtt"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.LinkedHashSet"/>
<jsp:directive.page import="org.apache.commons.collections.map.LinkedMap"/>
<jsp:directive.page import="ecar.evento.EventoAtivarMonitoramento"/>
<jsp:directive.page import="ecar.evento.EventoDesativarMonitoramento"/>
<jsp:directive.page import="ecar.evento.EventoBloquearPlanejamento"/>
<jsp:directive.page import="ecar.evento.EventoDesbloquearPlanejamento"/>
<%@page import="ecar.dao.SisAtributoDao"%>
<%@page import="ecar.pojo.SisAtributoSatb"%>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
<%@ page import="ecar.util.Dominios"%>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.UsuarioUsu"%>
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
<%@ page import="ecar.pojo.FuncaoFun" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="java.util.Date" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.dao.FuncaoDao"%>
<%@ page import="ecar.evento.Evento" %>
<%@ page import="ecar.evento.URLEvento" %>
<%@ page import="ecar.evento.EventoManutencaoFuncaoAcompanhamento"%>
<%@ page import="ecar.api.facade.*" %>

<%@ page import="ecar.dao.EtiquetaDao" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
</head>
<body>
<form name="form" method="post">   
<%
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
%>
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAba" value=<%=Pagina.getParamStr(request, "codAba")%>>
<!-- usado para guardar a estrutura detalhada na tela de cadastro -->
<input type=hidden name="codIettPrincipal" value="<%=Pagina.getParam(request, "codIettPrincipal")%>">
<input type=hidden name="ultEttSelecionado" value="<%=Pagina.getParam(request, "ultEttSelecionado")%>">
<input type=hidden name="codEttSelecionado" value="<%=Pagina.getParam(request, "codEttSelecionado")%>">
<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>
<input type="hidden" name="ultimoIdLinhaExpandido" id="ultimoIdLinhaExpandido" value="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>">

<input type="hidden" name="hidIndCopiar" value=<%=Pagina.getParam(request, "indCopiar")%>>

<%
	
	String msg = "";
	String submit = "";
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	String descricaoEvento = null;
	boolean enviarMail = false;
	Long codIett = null;
	int codConfigMailCfgm = 0; 
	
	EmpresaDao empDAO = new EmpresaDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);			
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
	UsuarioDao usuDAO = new UsuarioDao();
	Dao dao = new Dao();
	FuncaoDao funcaoDao = new FuncaoDao(request);
	String hidAcao= Pagina.getParamStr(request, "hidAcao");
	Evento evento = null;
	ItemEstruturaIett itemEstrutura = null;
	ItemEstruturaIett itemEstruturaAnterior = null;
	List listaFuncaoAnterior = null;
	String hidTelaAlteracao = Pagina.getParamStr(request, "hidTelaAlteracao");
	
	try{
			itemEstrutura = new ItemEstruturaIett();
			EstruturaEtt estrutura = null; //Usado para hidAcao=pesquisarModelo
			if("incluir".equals(hidAcao)){ 
			
				try {
					itemEstruturaDao.setItemEstrutura(request, itemEstrutura);
					
					itemEstrutura.setUsuarioUsuByCodUsuIncIett(usuario);
					
					//Obtem a Função de Dados Gerais 
					FuncaoFun funcao = funcaoDao.getFuncaoDadosGerais();
					
					itemEstruturaDao.salvar(request, itemEstrutura,funcao);
					
					EtiquetaDao etiquetaDao = new EtiquetaDao();
					etiquetaDao.atualizaEtiquetaInclusaoItem(itemEstrutura.getCodIett(),itemEstrutura.getItemEstruturaIett().getCodIett());
					
					submit = "frm_con.jsp";
					msg = _msg.getMensagem("itemEstrutura.dadosBasicos.inclusao.sucesso");
					
					enviarMail = true;
					codIett = itemEstrutura.getCodIett();
				} catch ( ECARException e){
					e.printStackTrace();
					org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
					submit = "frm_inc.jsp";
					msg = _msg.getMensagem(e.getMessageKey());%>
					
					<input type="hidden" name="codEtt" value=<%=itemEstrutura.getEstruturaEtt().getCodEtt()%>>
					
				<%} catch (Exception e){
					org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
					submit = "frm_inc.jsp";
				%>
					<%@ include file="/excecao.jsp"%>
				<%
				} 
			}	
			else if("alterar".equals(hidAcao)){
				
				//usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
				
				
				//@TODO: 
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
					itemEstrutura = itemEstruturaDao.alterar(request, usuario, seguranca.getGruposAcesso(), historico);
					submit = "frm_con.jsp";
					msg = _msg.getMensagem("itemEstrutura.dadosBasicos.alteracao.sucesso");
					
					codConfigMailCfgm = Dominios.CFG_MAIL_MANUTENCAO_FUNCOES_ACOMPANHAMENTO.intValue(); 
					evento = new EventoManutencaoFuncaoAcompanhamento();	
					enviarMail = true;
					codIett = itemEstrutura.getCodIett();
				}
				//descricaoEvento = "Alteração da <b>" + //Data limite para " + arli.getTipoFuncAcompTpfa().getLabelPosicaoTpfa() + "</b>.";
			}
			else if("alterarMonitoramento".equals(hidAcao)){

				//usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
				
				
	            /***Historico***/
	            HistoricoIett historico = new HistoricoIett(itemEstrutura,
	            												HistoricoIett.alteracao,
	            												itemEstruturaDao.getSession(),
	            												new ConfiguracaoDao(request),
	            												request);
	            /***Historico***/
				
				itemEstrutura = itemEstruturaDao.alterarMonitoramento(request, usuario, historico);
				
				if("S".equals(itemEstrutura.getIndMonitoramentoIett())){
					descricaoEvento = "Ativar Monitoramento";
					codConfigMailCfgm = Dominios.CFG_MAIL_ATIVAR_MONITORAMENTO.intValue();
					evento = new EventoAtivarMonitoramento();
				} else {
					descricaoEvento = "Desativar Monitoramento";
					codConfigMailCfgm = Dominios.CFG_MAIL_DESATIVAR_MONITORAMENTO.intValue();
					evento = new EventoDesativarMonitoramento();
				}
				
				enviarMail = true;
				
				codIett = itemEstrutura.getCodIett();
				
				submit = "frm_con.jsp";
				if (hidTelaAlteracao!=null && hidTelaAlteracao.equals("S")) {
					submit = "frm_alt.jsp";
				}
				
				msg = _msg.getMensagem("itemEstrutura.dadosBasicos.alteracao.sucesso");
			}			
			else if("alterarPlanejamento".equals(hidAcao)){
				
				//usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
				
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
					codConfigMailCfgm = Dominios.CFG_MAIL_BLOQUEAR_PLANEJAMENTO.intValue();
					evento = new EventoBloquearPlanejamento();
				} else {
					descricaoEvento = "Desbloquear Planejamento";
					codConfigMailCfgm = Dominios.CFG_MAIL_DESBLOQUEAR_PLANEJAMENTO.intValue();
					evento = new EventoDesbloquearPlanejamento();
				}
				
				enviarMail = true;
				
				codIett = itemEstrutura.getCodIett();
				
				submit = "frm_con.jsp";
				if (hidTelaAlteracao!=null && hidTelaAlteracao.equals("S")) {
					submit = "frm_alt.jsp";
				}
				
				msg = _msg.getMensagem("itemEstrutura.dadosBasicos.alteracao.sucesso");
			} else if ("pesquisarModelo".equals(hidAcao)){
				String nomePesquisa = Pagina.getParamStr(request, "nomePesquisa"); //String Pesquisada
				String indCopiar = Pagina.getParamStr(request, "indCopiar"); //Indicador se será copiado apenas o pai ou os filhos também
				//String campo = Pagina.getParamStr(request, "campo"); //Atualmente a pesquisa sempre será realizada por nome
			
				estrutura = (EstruturaEtt)  dao.buscar(EstruturaEtt.class, Long.valueOf(request.getParameter("codEtt")));
			
				List listItens = itemEstruturaDao.getListaItensModelo(null, estrutura, nomePesquisa, indCopiar, (ecar.login.SegurancaECAR)session.getAttribute("seguranca"));
			
				submit = "popUpPesquisaItemModelo.jsp";
			
				session.setAttribute("listItens",listItens);
			} else if ("funcoesModelo".equals(hidAcao)) {
				String iettPai = Pagina.getParamStr(request, "iett");
				String[] campos = Pagina.getParamArray(request, "iett_"+iettPai);
				estrutura = (EstruturaEtt)  dao.buscar(EstruturaEtt.class, Long.valueOf(request.getParameter("codEtt")));
				
				List itens = new ArrayList();
				
				itens.add(itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(iettPai)));
				
				if (campos!= null && campos.length!=0){
					for (int i =0; i<campos.length;  i++){
						if (campos[i]!=null && campos[i].length()!=0)
						itens.add(itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(campos[i] ) ) );
					}
				} 
				
				submit = "popUpFuncoesItemModelo.jsp";
			
				session.setAttribute("listItens",itens);
			
			} else if ("criarModelo".equals(hidAcao)) {
				List itens = new ArrayList();
				Map mapItensFuncoes = new LinkedMap();
				List  listFuncoes = new ArrayList();
//				FuncaoDao funcaoDao = new FuncaoDao (request); 

				submit = "frm_alt.jsp";
				ItemEstruturaIett itemPai = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "iett")));
				String [] strItensFilhos = Pagina.getParamLista(request, "iett_"+itemPai.getCodIett());
				
				String [] strFuncoes = Pagina.getParamLista(request, "funcao_"+itemPai.getEstruturaEtt().getCodEtt());
				if (strFuncoes != null && strFuncoes.length > 0){
					for (int j=0; j < strFuncoes.length; j++){
						FuncaoFun funcao = (FuncaoFun) funcaoDao.buscar(FuncaoFun.class, Long.valueOf(strFuncoes[j]));
						listFuncoes.add(funcao); 
					}
				}
				
				itens.add(itemPai);
				mapItensFuncoes.put(itemPai, listFuncoes );
				if (strItensFilhos!=null) {
					for (int i=0; i < strItensFilhos.length; i++){
						ItemEstruturaIett itemFilho = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(strItensFilhos[i])); 
						itens.add(itemFilho);
						
						strFuncoes = Pagina.getParamLista(request, "funcao_"+itemFilho.getEstruturaEtt().getCodEtt());
						listFuncoes = new ArrayList();
						if (strFuncoes != null && strFuncoes.length > 0){
							for (int j=0; j < strFuncoes.length; j++){
								FuncaoFun funcao = (FuncaoFun) funcaoDao.buscar(FuncaoFun.class, Long.valueOf(strFuncoes[j])); 
								listFuncoes.add(funcao); 
							}
						}
						mapItensFuncoes.put(itemFilho, listFuncoes );
					}
				}
			
				ItemEstruturaIett itemEstruturaSelecionado = (ItemEstruturaIett) request.getSession().getAttribute("itemEstruturaSelecionado");
				itemEstrutura =  itemEstruturaDao.criarCopiaItensFuncoes(mapItensFuncoes, usuario, itemEstruturaSelecionado);
				
				if (itemEstrutura!= null){
					submit += "?codIett="+itemEstrutura.getCodIett()+"&codEtt="+itemEstrutura.getEstruturaEtt().getCodEtt();
				} else {
					submit = "popUpFuncoesItemModelo.jsp";
				}
			}	
%>

<%			if (itemEstrutura!= null && itemEstrutura.getEstruturaEtt()!=null){ %>
				<input type="hidden" name="codEtt" value=<%=itemEstrutura.getEstruturaEtt().getCodEtt()%>>
				<input type="hidden" name="codIett" value=<%=itemEstrutura.getCodIett()%>>
				
<%			} else if (estrutura!= null) {%>
				<input type="hidden" name="codEtt" value=<%=estrutura.getCodEtt()%>>
				<input type="hidden" name="hidAcao" value=<%=hidAcao%>>
				<input type="hidden" name="codIett" value=<%=itemEstrutura.getCodIett()%>>
<%			} %>


<%			
			if (enviarMail == true){
			
				
				
				if( "S".equals(Pagina.getParamStr(request, "autorizarMail")) ) {
					
					String link = null;										
					
					if(evento != null)								     
				    	link = URLEvento.montaURLEvento(evento, request);
				    	
				 	ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Integer.valueOf(codConfigMailCfgm));
				
					ItemEstruturaIett item = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, codIett);	
					List listIett = new ArrayList(item.getItemEstUsutpfuacIettutfas());					
					
				 
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
					
					Iterator itListIett = listIett.iterator();
					while( itListIett.hasNext() ) {
					
						List usuarios = new ArrayList();
						usuarios.addAll(usuariosExcluidos);
						usuariosExcluidos = new ArrayList();
						ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itListIett.next();
						TipoFuncAcompTpfa tpfa = itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa();
				 
						TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
						tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
						tfcfgmPK.setCodTpfa(tpfa.getCodTpfa());
											
						TfuncacompConfigmailTfacfgm tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
				 
						if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {
	    					
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
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "frm_alt.jsp";
		if (e.getMessageArgs() != null) {
			msg = _msg.getMensagem(e.getMessageKey(),(String[])e.getMessageArgs());
		} else {
			msg = _msg.getMensagem(e.getMessageKey());
		}
		
		
		if (itemEstrutura.getCodIett() == null) {
			itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class,Long.valueOf(Pagina.getParamStr(request, "codIett")));
		}
		%>
		<input type="hidden" name="codEtt" value=<%=itemEstrutura.getEstruturaEtt().getCodEtt()%>>
		<input type="hidden" name="codIett" value=<%=itemEstrutura.getCodIett()%>>
		
	<%} catch (Exception e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "frm_con.jsp";
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	%>

<script>
		<% if (hidAcao.equals("criarModelo")){ %>
			window.opener.document.form.action = "<%=submit%>";
			window.opener.document.form.submit();
			window.close();
		<%} else {%>
			document.form.action = "<%=submit%>";
			document.form.submit();
		<%}%>
</script>

</form>
</body>
</html>