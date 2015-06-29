
<jsp:directive.page import="ecar.pojo.AcompRelatorioArel"/>
<jsp:directive.page import="ecar.util.Dominios"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="java.util.Date" %>
<%@ page import="comum.util.Data" %>
<%@ page import="comum.util.Util" %>
<%@ page import="ecar.email.AgendadorEmail" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgmPK" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgm" %>
<%@ page import="ecar.dao.TfuncacompConfigmailTfacfgmDAO" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="javax.mail.SendFailedException" %>
<%@ page import="ecar.dao.EmpresaDao" %>
<%@ page import="ecar.pojo.EmpresaEmp" %>
<%@ page import="ecar.pojo.AcompRefItemLimitesArli" %>
<%@ page import="java.util.Set" %>
<%@ page import="comum.util.Util" %>
<%@ page import="ecar.login.SegurancaECAR" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
</head>   
<body>
<form name="form" method="post">  

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>"> 
<input type="hidden" name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">

<!--  campos de controle para envio de e-mails -->
<input type="hidden" name="contadorTela"  value="<%=Pagina.getParamStr(request, "contadorTela")%>">
<input type="hidden" name="autorizarMail" value="<%=Pagina.getParamStr(request, "autorizarMail")%>">

<!-- acompanhamento físico -->
<input type="hidden" name="dataAcompFisicoAnt"  value="<%=Pagina.getParamStr(request, "dataAcompFisicoAnt")%>">
<input type="hidden" name="dataAcompFisicoAlt"  value="<%=Pagina.getParamStr(request, "dataAcompFisicoAlt")%>">


<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">

<%
	UsuarioUsu usuarioLogado = ((SegurancaECAR)session.getAttribute("seguranca")).getUsuario();

	UsuarioDao usuDAO = new UsuarioDao();
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	AcompReferenciaItemDao  ariDAO = new AcompReferenciaItemDao(request);
	TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
	EmpresaDao empDAO = new EmpresaDao(request);

	String msg = "";
	String submit = "";
	
	// acompanhamento físico
	String dataAcompFisicoAnt = "";
	String dataAcompFisicoAlt = "";
	
	ConfigMailCfgm configMailCfgm = null;
	String liberaOuRecupera = Pagina.getParamStr(request, "liberarOuRecuperar");
	if( (!"N".equals(liberaOuRecupera)) && (!"".equals(liberaOuRecupera)) ) {
		if( "L".equals(liberaOuRecupera) ) {
			configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_CONCLUSAO_ACOMPANHAMENTO);
		}
		if( "R".equals(liberaOuRecupera) ) {
			configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_RECUPERACAO_ACOMPANHAMENTO);
		}
	} else {
		configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_ALTERAR_DATA_LIMITE_REGISTRO_FISICO);
	}
		
	int indAlterouDataLimite = 0;
	
	try{
		AcompReferenciaItemAri ari = (AcompReferenciaItemAri)  ariDAO.buscar(AcompReferenciaItemAri.class, Long.valueOf(Pagina.getParamStr(request, "codAri")));

		List listArli = new ArrayList(ari.getAcompRefItemLimitesArlis());
		AcompRefItemLimitesArli arli = null ;
	
		if( listArli != null && listArli.size() > 0 ) {
			Iterator itArli = listArli.iterator();
			while( itArli.hasNext() ) {
				arli = (AcompRefItemLimitesArli)  itArli.next();
				if( "".equals(Pagina.getParamStr(request, "contadorTela")) ) { %>
					<!-- data limite para (pega da tela anterior e do banco) ... -->
					<input type="hidden" name="dataLimite<%=arli.getTipoFuncAcompTpfa().getCodTpfa()%>"  value="<%=Pagina.getParamStr(request, "dataLimite"+arli.getTipoFuncAcompTpfa().getCodTpfa())%>">
					<input type="hidden" name="dataLimiteAnt<%=arli.getTipoFuncAcompTpfa().getCodTpfa()%>"  value="<%=Pagina.trocaNullData(arli.getDataLimiteArli())%>"> <%
					if( !Pagina.getParamStr(request, "dataLimite"+arli.getTipoFuncAcompTpfa().getCodTpfa()).equals(Pagina.trocaNullData(arli.getDataLimiteArli())) ) {
						indAlterouDataLimite = 1;
					}
				} else {  %>
					<!-- data limite para (pega o que foi atribuido na 1a passada) ... -->
					<input type="hidden" name="dataLimite<%=arli.getTipoFuncAcompTpfa().getCodTpfa()%>"  value="<%=Pagina.getParamStr(request, "dataLimite"+arli.getTipoFuncAcompTpfa().getCodTpfa())%>">
					<input type="hidden" name="dataLimiteAnt<%=arli.getTipoFuncAcompTpfa().getCodTpfa()%>"  value="<%=Pagina.getParamStr(request, "dataLimiteAnt"+arli.getTipoFuncAcompTpfa().getCodTpfa())%>"> <%
				}
			}
		}

		// apenas para os dados do Limite de Acompanhamento Fisico
		if( "".equals(Pagina.getParamStr(request, "contadorTela")) ) {
			dataAcompFisicoAnt = Data.parseDate(ari.getDataLimiteAcompFisicoAri());
			dataAcompFisicoAlt = Data.parseDate(Pagina.getParamDataBanco(request, "dataLimiteAcompFisico"));

			ariDAO.alterar(request);
		} else {
			dataAcompFisicoAnt = Pagina.getParamStr(request, "dataAcompFisicoAnt");
			dataAcompFisicoAlt = Pagina.getParamStr(request, "dataAcompFisicoAlt");	
		}
		
		/* -- INICIO IMPLEMENTAÇÃO PARA ENVIO DE E-MAIL -- */
		
		/* valida se o envio foi autorizado */
		
		if( "S".equals(Pagina.getParamStr(request, "autorizarMail")) ) {
			//FIXME: enviar e-mail somente para usuário que possui AREL no acompanhamento
			/*
			List listUsuariosComArel = new ArrayList();
			Iterator itArels = ari.getAcompRelatorioArels().iterator();
			while(itArels.hasNext()) {
				AcompRelatorioArel arel = (AcompRelatorioArel)itArels.next();
				if(!listUsuariosComArel.contains(arel.getUsuarioUsu())) {
					listUsuariosComArel.add(arel.getUsuarioUsu());
				}
				if(!listUsuariosComArel.contains(arel.getUsuarioUsuUltimaManutencao())) {
					listUsuariosComArel.add(arel.getUsuarioUsuUltimaManutencao());
				}
			}
			*/

			List listAri = new ArrayList(ari.getItemEstruturaIett().getItemEstUsutpfuacIettutfas());			
			Iterator itList = listAri.iterator();
			
			while( itList.hasNext() ) {
				ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itList.next();
				
				TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
				tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
				tfcfgmPK.setCodTpfa(itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa().getCodTpfa());
				
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
						
						// controle para não enviar e-mail para o usuário logado
						if(itemEstUsutpfacIetutfa.getUsuarioUsu() == null ||
						   itemEstUsutpfacIetutfa.getUsuarioUsu().equals(usuarioLogado)) {
							continue;
						}
		
						//FIXME: enviar e-mail somente para usuário que possui AREL no acompanhamento
						/*
						if(!listUsuariosComArel.contains(itemEstUsutpfacIetutfa.getUsuarioUsu())) {
							continue;
						}
						*/
								
				
						Long   codIett	 = Long.valueOf(0);
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
					
						if( ari.getItemEstruturaIett() != null )
							codIett = ari.getItemEstruturaIett().getCodIett();
	
						AgendadorEmail ae = new AgendadorEmail();
						
						String descricaoEvento = null;
						String html = null;
						
						//se for liberar ou recuperar acompanhamento
						if( (!"N".equals(Pagina.getParamStr(request, "liberarOuRecuperar"))) && (!"".equals(Pagina.getParamStr(request, "liberarOuRecuperar")))) {
							
							descricaoEvento = "Liberação/Recuperação de Acompanhamento";
							String labelQuemAlterou = AgendadorEmail.LABEL_WHO_CHANGE_LIBERACAO;
							if("R".equals(liberaOuRecupera)) {
								labelQuemAlterou = AgendadorEmail.LABEL_WHO_CHANGE_RECUPERACAO;
							}
							html = ae.montaEmail(textoMail, usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(), codIett, 
									descricaoEvento, null, null, labelQuemAlterou,
									ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()).toString();
							ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
						} else {
							/* -- se limite acompanhamento fisico foi alterado, manda e-mail -- */
							if( !dataAcompFisicoAnt.equals(dataAcompFisicoAlt) ) {
								descricaoEvento = "Alteração da <b>Data Limite para Acompanhamento Físico</b>";
								html = ae.montaEmail(textoMail, usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(), codIett, 
										descricaoEvento, dataAcompFisicoAnt, dataAcompFisicoAlt, 
										AgendadorEmail.LABEL_WHO_CHANGE_ALTERACAO,
										ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()).toString();
								ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
							}
											
							/* -- se data limite para ... foi alterada, manda e-mail -- */
							if( listArli != null && listArli.size() > 0 ) {
								Iterator itArli = listArli.iterator();
								
								// data limite para ...
								String dataLimitePara = "";
								String dataLimiteParaAnt = "";
								
								while( itArli.hasNext() ) {
									arli = (AcompRefItemLimitesArli)  itArli.next();
									
									dataLimitePara    = Pagina.getParamStr(request, "dataLimite"+arli.getTipoFuncAcompTpfa().getCodTpfa());
									dataLimiteParaAnt = Pagina.getParamStr(request, "dataLimiteAnt"+arli.getTipoFuncAcompTpfa().getCodTpfa());
									
									if( !dataLimiteParaAnt.equals(dataLimitePara) ) {
										descricaoEvento = "Alteração da <b>Data limite para " + arli.getTipoFuncAcompTpfa().getLabelPosicaoTpfa() + "</b>.";
										html = ae.montaEmail(textoMail, usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(), codIett, 
												descricaoEvento, dataLimiteParaAnt, dataLimitePara, 
												AgendadorEmail.LABEL_WHO_CHANGE_ALTERACAO,
												ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()).toString();
										ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
									}
								}
							}
						}
					}
				}
				
				if ("S".equals(tfcfm.getEnviaSms())) {
					// envia sms
				}			
			} %>
				
			<script language="JavaScript">
				document.form.autorizarMail.value = '';
			</script> <%
			
			dataAcompFisicoAnt = "";
			dataAcompFisicoAlt = "";
		} // fim if autorizarMail	
		
		/* -- FIM IMPLEMENTAÇÃO PARA ENVIO DE E-MAIL -- */	
		
		submit = "datasLimites.jsp";
		msg = _msg.getMensagem("acompanhamento.datasLimites.alteracao.sucesso");
	} catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "datasLimites.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){ 
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	%>

<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">

<script>
	document.form.action = "<%=submit%>";

	var indDatasAlt  = false; 			

	/* -- acompanhamento físico -- */
	document.form.dataAcompFisicoAnt.value = "<%=dataAcompFisicoAnt%>";
	document.form.dataAcompFisicoAlt.value = "<%=dataAcompFisicoAlt%>";

	var dataAcFisAnt = document.form.dataAcompFisicoAnt.value;
	var dataAcFisAlt = document.form.dataAcompFisicoAlt.value;	
	
	/* -- valida se alguma das datas sofreu alteração -- */
	if( dataAcFisAnt != dataAcFisAlt ) {
		indDatasAlt = true;
	}
	
	if( '<%=indAlterouDataLimite%>' == '1' ) {
		indDatasAlt = true;
	}

	var counter = document.form.contadorTela.value;	
	var config  = "<%=configMailCfgm.getAtivoCfgm()%>";
		
	/* -- 
	 *	se alguma data sofreu alteração, se for a primeira vez que está se passando pela página e
	 *	e a configuração da estrutura permitir o envio de e-mails.
	 * -- */		
	if( counter == '' && indDatasAlt && config == 'S' ) {
		if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.periodoReferencia.alteracao.autorizaEnviaEmail")%>') == true )) {
			document.form.action = 'ctrlDatasLimites.jsp';
			document.form.autorizarMail.value = 'S';
		} 
	} 
		
	document.form.contadorTela.value = 1;
	document.form.submit();
</script>

</form>
</body>
</html>

