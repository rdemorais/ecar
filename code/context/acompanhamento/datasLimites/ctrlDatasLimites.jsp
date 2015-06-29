
<jsp:directive.page import="ecar.pojo.AcompRelatorioArel"/>
<jsp:directive.page import="ecar.util.Dominios"/>
<%@ include file="/../../login/validaAcesso.jsp"%>
<%@ include file="/../../frm_global.jsp"%>

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
<%@ page import="ecar.evento.Evento" %>
<%@ page import="ecar.evento.EventoConclusaoAcompanhamento" %>
<%@ page import="ecar.evento.URLEvento" %>


<%@page import="ecar.evento.EventoAlterarDatasLimitesParecer"%>
<%@page import="ecar.evento.EventoAlterarDatasLimitesRegistroFisico"%>
<%@page import="ecar.evento.EventoRecuperacaoAcompanhamento"%><html lang="pt-br">
<head>
<%@ include file="/../../include/meta.jsp"%>
</head>   
<body>
<form name="form" method="post">  



<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>"> 
<input type="hidden" name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">

<!--  campos de controle para envio de e-mails -->
<input type="hidden" name="contadorTela"  value="<%=Pagina.getParamStr(request, "contadorTela")%>">
<input type="hidden" name="autorizarMail" value="<%=Pagina.getParamStr(request, "autorizarMail")%>">


<!-- acompanhamento físico -->
<input type="hidden" name="dataAcompFisicoAnt"  value="<%=Pagina.getParamStr(request, "dataAcompFisicoAnt")%>">
<input type="hidden" name="dataAcompFisicoAlt"  value="<%=Pagina.getParamStr(request, "dataAcompFisicoAlt")%>">


<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">

<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">

<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">

<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
<input type="hidden" name="codEvento" value='<%=Pagina.getParamStr(request, "codEvento")%>'>

<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">


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
	
	//guarda o codigo do evento se for alteração de data de parecer ou de registro fisico 
	String codEvento = Pagina.getParamStr(request, "codEvento"); 	
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
		
			// se o for pra liberar ou recuperar o acompanhamento
			if( (!"N".equals(liberaOuRecupera)) && (!"".equals(liberaOuRecupera)) ) {
				
				if( "L".equals(liberaOuRecupera) ) {
					configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_CONCLUSAO_ACOMPANHAMENTO);
				}
				
				if( "R".equals(liberaOuRecupera) ) {
					configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_RECUPERACAO_ACOMPANHAMENTO);
				}
				
				
			} else  {  
			
				// se o acompanhamento fisico for alterado 
				if( !dataAcompFisicoAnt.equals(dataAcompFisicoAlt) && codEvento.equals(Dominios.CFG_MAIL_ALTERAR_DATA_LIMITE_REGISTRO_FISICO.toString())) {
					configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_ALTERAR_DATA_LIMITE_REGISTRO_FISICO);
				}
				
				//se alterou a data limite de algum parecer
				if(indAlterouDataLimite == 1) {
					configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_ALTERAR_DATA_LIMITE_PARECER);
				}
				
			}	
		
		/* valida se o envio foi autorizado */
		if( "S".equals(Pagina.getParamStr(request, "autorizarMail"))) {
		
			List listAri =  null;
			Iterator itList = null;
			ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = null;
			TfuncacompConfigmailTfacfgmPK tfcfgmPK = null;
			TfuncacompConfigmailTfacfgm tfcfm = null;
			List usuarios = null;
			Iterator itUsu = null;
			UsuarioUsu usu = null;
			Long   codIett	 = Long.valueOf(0);
			String textoMail = "";
			String assunto   = "";
			String remetente = "";
			List listEmpresa  = null;
			EmpresaEmp emp = null;
			Iterator it  = null;
			AgendadorEmail ae = null;
			String descricaoEvento = null;
			String html = null;
			Evento evento = null;
			String link = null;
						
		
			//Só envia email para as funções de acompanhamento cadastradas no item
			listAri = new ArrayList(ari.getItemEstruturaIett().getItemEstUsutpfuacIettutfas());	
			itList = listAri.iterator();
			
			
			while( itList.hasNext() ) {
		
				itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itList.next();
				//começa a definição das funcoes cadastradas pra receber -> alterar se tiver duas alterações diferentes
				tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
				if(configMailCfgm == null && codEvento != null) {
					configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Integer.valueOf(codEvento));
				}
				
				//se for alteração data limite parecer ou alteração data limite fisico
				tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
				tfcfgmPK.setCodTpfa(itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa().getCodTpfa());
				tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
				
				if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {
					usuarios = new ArrayList();
					if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
						usuarios.add((UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
					} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null){
						usuarios.addAll(usuDAO.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
					}
					
					itUsu = null;
					
					if(usuarios != null) {
						itUsu = usuarios.iterator();
					}	
					
					while (itUsu != null && itUsu.hasNext()){
					
						usu = (UsuarioUsu) itUsu.next();
						
						
						// controle para não enviar e-mail para o usuário logado
						if(itemEstUsutpfacIetutfa.getUsuarioUsu()!=null && itemEstUsutpfacIetutfa.getUsuarioUsu().equals(usuarioLogado)) {
							continue;
						}
						
						
						textoMail = "";
						assunto   = "";
						remetente = "";
					
						if( configMailCfgm.getTextosSiteMail() != null ) {
							textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
							assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
							remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
						}
						
						// se nao tem remetente configurado no textoSite, pega da empresa.
						if( "".equals(remetente) || remetente == null ) {
							listEmpresa = new ArrayList();
							emp = null;
	
							listEmpresa = empDAO.listar(EmpresaEmp.class, null);
	
							it = listEmpresa.iterator();
							while( it.hasNext() ) {
								emp = (EmpresaEmp) it.next();
							}
							
							remetente = emp.getEmailContatoEmp();
						}
					
						if( ari.getItemEstruturaIett() != null )
							codIett = ari.getItemEstruturaIett().getCodIett();
	
						ae = new AgendadorEmail();
						
						descricaoEvento = null;
						html = null;
						
						//se for liberar ou recuperar acompanhamento
						if( (!"N".equals(Pagina.getParamStr(request, "liberarOuRecuperar"))) && (!"".equals(Pagina.getParamStr(request, "liberarOuRecuperar")))) {
							String labelQuemAlterou = null;
							
							descricaoEvento = "Liberação/Recuperação de Acompanhamento";
							
							if( "L".equals(liberaOuRecupera) ) {
								// -------- Montando link --------
								link = null;
								
								evento = new EventoConclusaoAcompanhamento();
							    link = URLEvento.montaURLEvento(evento, request);
					
								html = ae.montaEmailComLink(textoMail, usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(), codIett,
									   descricaoEvento, null, null, AgendadorEmail.LABEL_WHO_CHANGE_LIBERACAO,
									   ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa(), link).toString();
							}
							else if( "R".equals(liberaOuRecupera) ) {
								// -------- Montando link --------
								link = null;
								
								evento = new EventoRecuperacaoAcompanhamento();
							     
							    link = URLEvento.montaURLEvento(evento, request);
								html = ae.montaEmailComLink(textoMail, usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(), codIett, 
									   descricaoEvento, null, null, AgendadorEmail.LABEL_WHO_CHANGE_RECUPERACAO,
									   ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa(), link).toString();
							}
							else {
								// Nada faz (?)
							}
						
							ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
							
							
						} 
						
						
					}
					
				}
				
			
				
				/* -- se limite acompanhamento fisico foi alterado, manda e-mail -- */
			 	// -------------------------------
				if( !dataAcompFisicoAnt.equals(dataAcompFisicoAlt)  && !"S".equals(Pagina.getParamStr(request, "liberarOuRecuperar"))) {
					link = null;
					evento = new EventoAlterarDatasLimitesRegistroFisico();
							     
					link = URLEvento.montaURLEvento(evento, request);
					//se enviou registro fisico deve se fazer uma nova pesquisa porque existem 2 eventos
					// e sinalizar que nao se deve mais enviar nenhum email de parecer
					configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_ALTERAR_DATA_LIMITE_REGISTRO_FISICO);
											
					//começa a definição das funcoes cadastradas pra receber -> alterar se tiver duas alterações diferentes
					TfuncacompConfigmailTfacfgmPK tfcfgmPK2 = new TfuncacompConfigmailTfacfgmPK();
					if(configMailCfgm == null && codEvento != null) {
						ConfigMailCfgm configMailCfgm2 = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Integer.valueOf(codEvento));
					}
												
									
					//se for alteração data limite parecer ou alteração data limite fisico
					tfcfgmPK2.setCodCfgm(configMailCfgm.getCodCfgm());
					tfcfgmPK2.setCodTpfa(itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa().getCodTpfa());
					TfuncacompConfigmailTfacfgm tfcfm2 = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
												
					if ("S".equals(tfcfm2.getEnviaMailTfacfgm())) {
												
						usuarios = new ArrayList();
						if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
							usuarios.add((UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
						} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null){
							usuarios.addAll(usuDAO.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
						}
													
						itUsu = null;
													
						if(usuarios != null) {
							itUsu = usuarios.iterator();
						}	
													
						while (itUsu != null && itUsu.hasNext()){
												
							usu = (UsuarioUsu) itUsu.next();
														
							// controle para não enviar e-mail para o usuário logado
							if(itemEstUsutpfacIetutfa.getUsuarioUsu()!=null && 
							  itemEstUsutpfacIetutfa.getUsuarioUsu().equals(usuarioLogado)) {
								continue;
							}
															
						
							textoMail = "";
							assunto   = "";
							remetente = "";
													
							if( configMailCfgm.getTextosSiteMail() != null ) {
									textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
									assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
									remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
							}
													
							// se nao tem remetente configurado no textoSite, pega da empresa.
							if( "".equals(remetente) || remetente == null ) {
								listEmpresa = new ArrayList();
								emp = null;
								listEmpresa = empDAO.listar(EmpresaEmp.class, null);
								
								it = listEmpresa.iterator();
								while( it.hasNext() ) {
									emp = (EmpresaEmp) it.next();
								}
															
								remetente = emp.getEmailContatoEmp();
							}
							
							descricaoEvento = "Alteração da <b>Data Limite para Acompanhamento Físico</b>";
							html = ae.montaEmailComLink(textoMail, usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(), codIett, 
									descricaoEvento, dataAcompFisicoAnt, dataAcompFisicoAlt, 
									AgendadorEmail.LABEL_WHO_CHANGE_ALTERACAO,
									ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa(), link).toString();
							ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
						}
					}			    
				}
											
				/* -- se data limite para ... foi alterada, manda e-mail -- */
				String teste = Pagina.getParamStr(request, "liberarOuRecuperar");
				System.out.println(teste);
				if( listArli != null && listArli.size() > 0 && 
						("N".equals(Pagina.getParamStr(request, "liberarOuRecuperar"))) || "".equals(Pagina.getParamStr(request, "liberarOuRecuperar"))) {
					link = null;
								
					evento = new EventoAlterarDatasLimitesParecer();
							     
					link = URLEvento.montaURLEvento(evento, request);
					Iterator itArli = listArli.iterator();
								
					// data limite para ...
					String dataLimitePara = "";
					String dataLimiteParaAnt = "";
								
								
								
					while( itArli.hasNext() ) {
						arli = (AcompRefItemLimitesArli)  itArli.next();
									
						dataLimitePara    = Pagina.getParamStr(request, "dataLimite"+arli.getTipoFuncAcompTpfa().getCodTpfa());
						dataLimiteParaAnt = Pagina.getParamStr(request, "dataLimiteAnt"+arli.getTipoFuncAcompTpfa().getCodTpfa());
									
						if( !dataLimiteParaAnt.equals(dataLimitePara) ) {
							
							configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_ALTERAR_DATA_LIMITE_PARECER);
										
							//começa a definição das funcoes cadastradas pra receber -> alterar se tiver duas alterações diferentes
							TfuncacompConfigmailTfacfgmPK tfcfgmPK2 = new TfuncacompConfigmailTfacfgmPK();
							if(configMailCfgm == null && codEvento != null) {
								ConfigMailCfgm configMailCfgm2 = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Integer.valueOf(codEvento));
							}
											
							//se for alteração data limite parecer ou alteração data limite fisico
							tfcfgmPK2.setCodCfgm(configMailCfgm.getCodCfgm());
							tfcfgmPK2.setCodTpfa(itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa().getCodTpfa());
							TfuncacompConfigmailTfacfgm tfcfm2 = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
											
							if ("S".equals(tfcfm2.getEnviaMailTfacfgm())) {
											
								usuarios = new ArrayList();
								if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
									usuarios.add((UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
								} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null){
									usuarios.addAll(usuDAO.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
								}
												
								itUsu = null;
												
								if(usuarios != null) {
									itUsu = usuarios.iterator();
								}	
												
								while (itUsu != null && itUsu.hasNext()){
												
									usu = (UsuarioUsu) itUsu.next();
													
													
									// controle para não enviar e-mail para o usuário logado
									if(itemEstUsutpfacIetutfa.getUsuarioUsu()!=null && itemEstUsutpfacIetutfa.getUsuarioUsu().equals(usuarioLogado)) {
										continue;
									}
														
									
									textoMail = "";
									assunto   = "";
									remetente = "";
											
									if( configMailCfgm.getTextosSiteMail() != null ) {
										textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
										assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
										remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
									}
												
									// se nao tem remetente configurado no textoSite, pega da empresa.
									if( "".equals(remetente) || remetente == null ) {
										listEmpresa = new ArrayList();
										emp = null;
							
										listEmpresa = empDAO.listar(EmpresaEmp.class, null);
							
										it = listEmpresa.iterator();
										while( it.hasNext() ) {
											emp = (EmpresaEmp) it.next();
										}
													
										remetente = emp.getEmailContatoEmp();
									}
												
													
									descricaoEvento = "Alteração da <b>Data limite para " + arli.getTipoFuncAcompTpfa().getLabelPosicaoTpfa() + "</b>.";
									html = ae.montaEmailComLink(textoMail,		
					 					usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(), codIett, 
										descricaoEvento, dataLimiteParaAnt, dataLimitePara, 
										AgendadorEmail.LABEL_WHO_CHANGE_ALTERACAO,
										ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa(), link).toString();
										ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
													
													
								}
							}
						
						}
					}
				}
			
			
				if ("S".equals(tfcfm.getEnviaSms())) {
					// envia sms
				}			
			} 
			%>
				
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
		
	if(configMailCfgm != null) {%>
		<input type="hidden" name="obrigatorio" value="<%=configMailCfgm.getIndEnvioObrigatorio()%>">
	<%}%>

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
	var config  = "";
	
	<%if(configMailCfgm != null) {%>
		config  = "<%=configMailCfgm.getAtivoCfgm()%>";
	<%}%>
	
	
		
	/* -- 
	 *	se alguma data sofreu alteração, se for a primeira vez que está se passando pela página e
	 *	e a configuração da estrutura permitir o envio de e-mails.
	 * -- */		
	if( counter == '' && indDatasAlt && config == 'S' ) {
		if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.periodoReferencia.alteracao.autorizaEnviaEmail")%>') == true )) {
			document.form.action = 'ctrlDatasLimites.jsp';
			document.form.autorizarMail.value = 'S';
			<%if(configMailCfgm != null) {%>
				document.form.codEvento.value = "<%=configMailCfgm.getCodCfgm()%>";
			<%}%>
		} 
	} 
		
	document.form.contadorTela.value = 1;
	document.form.submit();
</script>

</form>
</body>
</html>

