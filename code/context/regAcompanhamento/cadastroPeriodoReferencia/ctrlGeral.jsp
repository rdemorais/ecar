
<jsp:directive.page import="ecar.pojo.AcompReferenciaItemAri" />
<jsp:directive.page import="ecar.pojo.AcompRelatorioArel" />
<jsp:directive.page import="ecar.dao.AcompReferenciaDao" />
<jsp:directive.page import="ecar.util.Dominios" />
<jsp:directive.page import="ecar.evento.EventoCriacaoRegistroAcompanhamento"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.pojo.AcompReferenciaAref"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="comum.database.Dao"%>
<%@ page import="ecar.exception.ECARException"%>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa"%>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgmPK"%>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgm"%>
<%@ page import="ecar.dao.TfuncacompConfigmailTfacfgmDAO"%>
<%@ page import="ecar.dao.UsuarioDao"%>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.pojo.EmpresaEmp"%>
<%@ page import="ecar.dao.EmpresaDao"%>
<%@ page import="ecar.email.AgendadorEmail"%>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa"%>
<%@ page import="ecar.pojo.AcompRefLimitesArl"%>
<%@ page import="ecar.dao.ConfigMailCfgmDAO"%>
<%@ page import="ecar.pojo.ConfigMailCfgm"%>
<%@ page import="comum.util.Util"%>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ page import="ecar.dao.SisAtributoDao"%>
<%@ page import="ecar.login.SegurancaECAR"%>
<%@ page import="ecar.evento.Evento"%>
<%@ page import="ecar.evento.URLEvento"%>
<%@page import="ecar.evento.EventoAlterarDatasLimitesParecer"%>
<%@page import="ecar.evento.EventoAlterarDatasLimitesRegistroFisico"%>


<%@page import="ecar.pojo.ConfiguracaoCfg"%>
<%@page import="ecar.dao.ConfiguracaoDao"%><html lang="pt-br">
<head>

<META HTTP-EQUIV="Expires" CONTENT="never">
</head>
<body>
<form name="form" method="post">
<input type="hidden" name="hidAcao" value="<%=Pagina.getParamStr(request, "hidAcao")%>">
<input type="hidden" name="codigo" value="<%=Pagina.getParamStr(request, "codigo")%>">
<input	type="hidden" name="existeAriFaltandoParecerConfirma" value="<%=Pagina.getParamStr(request, "existeAriFaltandoParecerConfirma")%>">

<!-- campo de controle para mensagens --> <input type="hidden"
	name="msgOperacao" value=""> <%
 	UsuarioUsu usuarioLogado = ((SegurancaECAR) session.getAttribute("seguranca")).getUsuario();

 	Dao dao = new Dao();
 	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
 	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
 	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
 	SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
 	UsuarioDao usuDAO = new UsuarioDao();
 	EmpresaDao empDAO = new EmpresaDao(request);
 	TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
 	Evento evento = null;
 	String link = null;

 	String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
 	List listNiveis = new ArrayList();
 	String[] niveis = niveisPlanejamento.split(":");
 	for (int n = 0; n < niveis.length; n++) {
 		String codNivel = niveis[n];
 		if (!"".equals(codNivel)) {
 			listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
 		}
 	}

 	List listaAcompReferencia = (List) session.getAttribute("listaAcompReferencia");
 	String msg = "";
 	String submit = "";
 	boolean selecionouItensSemOrgao = false;

 	List listArefParaGravar = new ArrayList();

 	try {
 		List orgaosItensMarcadosTela = new ArrayList();

 		String[] itemSelecao = request.getParameterValues("iett");
 		if (itemSelecao != null) {
 			for (int i = 0; i < itemSelecao.length; i++) {
 				ItemEstruturaIett item = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, Long.valueOf(itemSelecao[i]));

 				if (item.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null
 						&& !orgaosItensMarcadosTela.contains(item.getOrgaoOrgByCodOrgaoResponsavel1Iett())) {
 					orgaosItensMarcadosTela.add(item.getOrgaoOrgByCodOrgaoResponsavel1Iett());
 				} else if (item.getOrgaoOrgByCodOrgaoResponsavel1Iett() == null){
 					selecionouItensSemOrgao = true;
 				}
 			}
 		}

 		String separarPorOrgao = "";

 		if (listaAcompReferencia != null && !listaAcompReferencia.isEmpty()) {
 			separarPorOrgao = ((AcompReferenciaAref) listaAcompReferencia.get(0)).getTipoAcompanhamentoTa().getIndSepararOrgaoTa();

 			Iterator itAcompRef = listaAcompReferencia.iterator();

 			while (itAcompRef.hasNext()) {
 				AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itAcompRef.next();

 				listArefParaGravar = new ArrayList();
 				//Se for pra separar por orgao
 				if ("S".equals(separarPorOrgao)) {
 					//Se for alteração, sempre vai gravar
 					if(acompReferencia.getCodAref() != null ||
 						// Se for para itens com orgaos sem informacao
 						(Dominios.NAO.equals(acompReferencia.getIndInformacaoOrgaoAref()) && acompReferencia.getOrgaoOrg() == null) && selecionouItensSemOrgao||
 						// Se  for para referencias com orgao
 						(acompReferencia.getOrgaoOrg() != null && orgaosItensMarcadosTela.contains(acompReferencia.getOrgaoOrg()))) {
 						listArefParaGravar.add(acompReferencia);
 					} 
 				} else {
 					listArefParaGravar.add(acompReferencia);
 				}
 				
 				if(!listArefParaGravar.isEmpty())
 					acompReferenciaItemDao.salvarOuAlterarAcompReferenciaItens(listArefParaGravar, request, listNiveis);

 				if ("S".equals(Pagina.getParamStr(request, "autorizarMail"))) {

 					String referencia_hidden = acompReferencia.getCodAref().toString();
 					String codTipoAcompanhamento = acompReferencia.getTipoAcompanhamentoTa().getCodTa().toString();

 					if (itemSelecao != null) {
 						for (int i = 0; i < itemSelecao.length; i++) {

 							ItemEstruturaIett item = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, Long.valueOf(itemSelecao[i]));

 			 				AcompReferenciaItemAri ari = acompReferenciaItemDao.getAcompReferenciaItemByItemEstruturaIett(acompReferencia, item);
 							String codAri = ari.getCodAri().toString();

 							List listIett = new ArrayList(item.getItemEstUsutpfuacIettutfas());
 							Iterator itListIett = listIett.iterator();

 							// data limite para ...
 							String dataLimitePara = "";
 							String dataLimiteParaAnt = "";

 							while (itListIett.hasNext()) {
 								ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itListIett.next();

 								TipoFuncAcompTpfa tpfa = itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa();

 								TfuncacompConfigmailTfacfgm tfcfm = null;
 								TfuncacompConfigmailTfacfgmPK tfcfgmPK = null;
 								ConfigMailCfgm configMailCfgm = null;

 								UsuarioUsu usu = null;

 								Long codIett = item.getCodIett();
 								String textoMail = "";
 								String assunto = "";
 								String remetente = "";
 								String descricaoEvento = null;
 								String html = null;

 								AgendadorEmail ae = new AgendadorEmail();

 								List listEmpresa = null;
 								EmpresaEmp emp = null;
 								Iterator itEmp = null;

 								List usuarios = new ArrayList();
 								if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
 									usuarios.add((UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
 								} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null) {
 									usuarios.addAll(usuDAO.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
 								}

 								Iterator itUsu = usuarios.iterator();

 								while (itUsu.hasNext()) {

 									usu = (UsuarioUsu) itUsu.next();

 									// controle para não enviar e-mail para o usuário logado
 									if (usu.equals(usuarioLogado)) {
 										continue;
 									}

 									// FIXME: enviar e-mail somente para usuário que possui AREL no acompanhamento
 									/*
 									if(acompReferencia.getCodAref() != null && acompReferencia.getCodAref().longValue() > 0) {
 										if(!listUsuariosComArel.contains(usu)) {
 											continue;
 										}
 									}
 									 */

 									if ("S".equals(Pagina.getParamStr(request, "criacaoPeriodo"))) {
 									
 										//seta os atributos pra montar o link no envio de email
 										request.setAttribute("codTipoAcompanhamento", acompReferencia.getTipoAcompanhamentoTa().getCodTa().toString());
										request.setAttribute("referencia_hidden", acompReferencia.getCodAref().toString());
										request.setAttribute("codAri", codAri.toString());
										
										
										evento = new EventoCriacaoRegistroAcompanhamento();
										link = URLEvento.montaURLEvento(evento, request);
 										
 										configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class,
 												Dominios.CFG_MAIL_CRIACAO_REGISTRO_ACOMPANHAMENTO);
 										tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
 										tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
 										tfcfgmPK.setCodTpfa(tpfa.getCodTpfa());
 										tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class,
 												tfcfgmPK);

 										if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {
 											//usu = (UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getComp_id().getCodUsu());																	
 											if (configMailCfgm.getTextosSiteMail() != null) {
 												textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
 												assunto = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
 												remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
 											}

 											// se nao tem remetente configurado no textoSite, pega da empresa.
 											if ("".equals(remetente) || remetente == null) {
 												listEmpresa = new ArrayList();
 												listEmpresa = empDAO.listar(EmpresaEmp.class, null);

 												itEmp = listEmpresa.iterator();
 												while (itEmp.hasNext()) {
 													emp = (EmpresaEmp) itEmp.next();
 												}
 												remetente = emp.getEmailContatoEmp();
 											}

 											/* -- somente pro caso de inclusão -- */
 											if ("incluir".equals(Pagina.getParamStr(request, "hidAcao"))) {
 												descricaoEvento = "Inclusão de Ciclo de Acompanhamento.";
 												html = ae.montaEmailComLink(textoMail, usu.getNomeUsu(), null, codIett, descricaoEvento, null, null, null,
 														acompReferencia.getTipoAcompanhamentoTa().getDescricaoTa(), link).toString();

 												ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "", usu);
 											}
 											/* -- somente pro caso de inclusão -- */
 										}
 									}
 									if ("S".equals(Pagina.getParamStr(request, "alteraDataRegistro"))) {
		
 										//seta os atributos pra montar o link no envio de email
 										request.setAttribute("codTipoAcompanhamento", acompReferencia.getTipoAcompanhamentoTa().getCodTa().toString());
										request.setAttribute("referencia_hidden", acompReferencia.getCodAref().toString());
										request.setAttribute("codAri", codAri.toString());
										
 										evento = new EventoAlterarDatasLimitesRegistroFisico();
 										link = URLEvento.montaURLEvento(evento, request);

 										configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class,
 												Dominios.CFG_MAIL_ALTERAR_DATA_LIMITE_REGISTRO_FISICO);
 										tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
 										tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
 										tfcfgmPK.setCodTpfa(tpfa.getCodTpfa());

 										tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class,
 												tfcfgmPK);

 										if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {
 											//usu = (UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getComp_id().getCodUsu());

 											if (configMailCfgm.getTextosSiteMail() != null) {
 												textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
 												assunto = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
 												remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
 											}

 											// se nao tem remetente configurado no textoSite, pega da empresa.
 											if ("".equals(remetente) || remetente == null) {
 												listEmpresa = new ArrayList();
 												listEmpresa = empDAO.listar(EmpresaEmp.class, null);

 												itEmp = listEmpresa.iterator();
 												while (itEmp.hasNext()) {
 													emp = (EmpresaEmp) itEmp.next();
 												}

 												remetente = emp.getEmailContatoEmp();
 											}

 											String dataAcompFisicoAnt = Pagina.getParamStr(request, "dataLimiteAcompFisicoArefAnt");
 											String dataAcompFisicoAlt = Pagina.getParamStr(request, "dataLimiteAcompFisicoArefAlt");

 											/* -- se limite acompanhamento fisico foi alterado, manda e-mail -- */
 											if (!dataAcompFisicoAnt.equals(dataAcompFisicoAlt)) {
 												descricaoEvento = "Alteração da <b>Data Limite para Acompanhamento Físico</b>";
 												html = ae.montaEmailComLink(textoMail, usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(), codIett,
 														descricaoEvento, dataAcompFisicoAnt, dataAcompFisicoAlt,
 														AgendadorEmail.LABEL_WHO_CHANGE_ALTERACAO,
 														acompReferencia.getTipoAcompanhamentoTa().getDescricaoTa(), link).toString();
 												ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "", usu);
 											}
 										}
 									}

 									if ("S".equals(Pagina.getParamStr(request, "alteraDataParecer"))) {

 								
										//seta os atributos pra montar o link no envio de email
 										request.setAttribute("codTipoAcompanhamento", acompReferencia.getTipoAcompanhamentoTa().getCodTa().toString());
										request.setAttribute("referencia_hidden", acompReferencia.getCodAref().toString());
										request.setAttribute("codAri", codAri.toString());
										
 										evento = new EventoAlterarDatasLimitesParecer();
 										link = URLEvento.montaURLEvento(evento, request);
 										
 					 					configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class,
 												Dominios.CFG_MAIL_ALTERAR_DATA_LIMITE_PARECER);
 										tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
 										tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
 										tfcfgmPK.setCodTpfa(tpfa.getCodTpfa());

 										tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class,
 												tfcfgmPK);

 										if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {
 											//usu = (UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getComp_id().getCodUsu());

 											if (configMailCfgm.getTextosSiteMail() != null) {
 												textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
 												assunto = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
 												remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
 											}

 											// se nao tem remetente configurado no textoSite, pega da empresa.
 											if ("".equals(remetente) || remetente == null) {
 												listEmpresa = new ArrayList();
 												listEmpresa = empDAO.listar(EmpresaEmp.class, null);

 												itEmp = listEmpresa.iterator();
 												while (itEmp.hasNext()) {
 													emp = (EmpresaEmp) itEmp.next();
 												}

 												remetente = emp.getEmailContatoEmp();
 											}

 											List listArl = new ArrayList(acompReferencia.getAcompRefLimitesArls());
 											AcompRefLimitesArl arl = null;

 											/* -- se data limite para ... foi alterada, manda e-mail -- */
 											if (listArl != null && listArl.size() > 0) {
 												Iterator itArl = listArl.iterator();

 												while (itArl.hasNext()) {
 													arl = (AcompRefLimitesArl) itArl.next();

 													dataLimitePara = Pagina.getParamStr(request, "prazoFinalPara"
 															+ arl.getTipoFuncAcompTpfa().getCodTpfa());
 													dataLimiteParaAnt = Pagina.getParamStr(request, "prazoFinalParaAnt"
 															+ arl.getTipoFuncAcompTpfa().getCodTpfa());

 													if (!dataLimiteParaAnt.equals(dataLimitePara)) {
 														descricaoEvento = "Alteração da <b>Data limite para "
 																+ arl.getTipoFuncAcompTpfa().getLabelPosicaoTpfa() + "</b>.";
 														html = ae.montaEmailComLink(textoMail, usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(),
 																codIett, descricaoEvento, dataLimiteParaAnt, dataLimitePara,
 																AgendadorEmail.LABEL_WHO_CHANGE_ALTERACAO,
 																acompReferencia.getTipoAcompanhamentoTa().getDescricaoTa(), link).toString();
 														ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "", usu);
 													}
 												}
 											}
 										}
 									}

 									if ("S".equals(tfcfm.getEnviaSms())) {
 										// envia sms
 									}
 								}
 							} // fim while(itListIett)
 						}
 					}
 				} // fim if(autorizaMail)
 				/* -- novo - rogerio/igor - 31/05 -- */
 			}

 			session.removeAttribute("acompReferencia");
 			session.removeAttribute("listaAcompReferencia");
 			session.removeAttribute("periodoRef");

 		}
 		submit = "lista.jsp";

 		msg = _msg.getMensagem("periodoReferencia.geracao.sucesso");
 	} catch (ECARException e) {
 		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
 		submit = "lista.jsp";
 		msg = _msg.getMensagem(e.getMessageKey());
 	} catch (Exception e) {
 %> <%@ include file="/excecao.jsp"%> <%
 	}
 	if (msg != null)
 		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg);
 %> <script>
	document.form.action = "<%=submit%>";
	document.form.submit();
</script></form>
</body>
</html>