<jsp:directive.page import="ecar.util.Dominios"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.AcompRelatorioArel" %> 
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgmPK" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgm" %>
<%@ page import="ecar.pojo.EmpresaEmp" %>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.AcompRelatorioDao" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.dao.EmpresaDao" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.TfuncacompConfigmailTfacfgmDAO" %>

<%@ page import="ecar.login.SegurancaECAR" %>
<%@ page import="ecar.email.AgendadorEmail" %>

<%@ page import="comum.database.Dao" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.FileUpload"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>


<%@page import="ecar.evento.Evento"%>
<%@page import="ecar.evento.EventoLiberacaoAvaliacaoPararecer"%>
<%@page import="ecar.evento.URLEvento"%>
<%@page import="ecar.evento.EventoRecuperacaoAvaliacaoPararecer"%>
<%@page import="ecar.dao.TipoFuncAcompDao"%>
<%@page import="ecar.dao.ItemEstUsutpfuacDao"%>
<%@page import="ecar.pojo.SisAtributoSatb"%>
<%@page import="java.util.Set"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
</head>   
<body>
<form name="form" method="post">  
<%
  		String codAri = null;
    	String codArel = null;
    	String codTpfa = null;
    	String funcao = null;
    	String funcaoParecer = null;
    	String codAcomp = null;
    	String primeiroAcomp = null;
    	String autorizarMail = null;
    	String permissaoReferencia = null;
    	String codIett = null;
    	
    	boolean isFormUpload = FileUpload.isMultipartContent(request);
    	
    	List campos = new ArrayList();
    	if(isFormUpload){
    		campos = FileUpload.criaListaCampos(request); 
    	}
    	if(isFormUpload){
    		codAri = FileUpload.verificaValorCampo(campos,"codAri");
    		codArel = FileUpload.verificaValorCampo(campos, "codArel");
    		codTpfa = FileUpload.verificaValorCampo(campos, "codTpfa");
    		funcao = FileUpload.verificaValorCampo(campos, "funcao");
    		funcaoParecer = FileUpload.verificaValorCampo(campos, "funcaoParecer");
    		codAcomp = FileUpload.verificaValorCampo(campos, "codAcomp");
    		primeiroAcomp = FileUpload.verificaValorCampo(campos, "primeiroAcomp");
    		autorizarMail = FileUpload.verificaValorCampo(campos, "autorizarMail");
    		permissaoReferencia = FileUpload.verificaValorCampo(campos, "permissaoReferencia");
    	} else {
    		codAri = Pagina.getParamStr(request,"codAri");
    		codArel = Pagina.getParamStr(request, "codArel");
    		codTpfa = Pagina.getParamStr(request, "codTpfa");
    		funcao = Pagina.getParamStr(request, "funcao");
    		funcaoParecer = Pagina.getParamStr(request, "funcaoParecer");
    		codAcomp = Pagina.getParamStr(request, "codAcomp");
    		primeiroAcomp = Pagina.getParamStr(request, "primeiroAcomp");
    		autorizarMail = Pagina.getParamStr(request, "autorizarMail");
    		permissaoReferencia = Pagina.getParamStr(request, "permissaoReferencia");
    	}
    	 
    	Long codTpfaUsuario = (Long) request.getSession().getAttribute("codTpfaUsuario");

    	AcompRelatorioDao acompRelatorioDao = new AcompRelatorioDao(request);
    	AcompReferenciaItemDao ariDAO = new AcompReferenciaItemDao(request);
    	AcompReferenciaItemAri ari = (AcompReferenciaItemAri)  ariDAO.buscar(AcompReferenciaItemAri.class, Long.valueOf(codAri));
    	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
    	TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
    	UsuarioDao usuDAO = new UsuarioDao();
    	EmpresaDao empDAO = new EmpresaDao(request);
    	TipoFuncAcompDao tpfaDAO = new TipoFuncAcompDao(request);
    	ItemEstUsutpfuacDao iettEstUsuTpfaDao = new ItemEstUsutpfuacDao(request);
    	
    	
    	if( ari.getItemEstruturaIett() != null ){
    		codIett = ari.getItemEstruturaIett().getCodIett().toString();
    	}
    	Dao dao = new Dao();
  %>
<!-- variaveis que devem passar de uma tela para a outra -->
<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
<input type="hidden" name="mesReferencia" value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">

<!-- Campo necessário para botões de Avança/Retrocede -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">

<!-- Campos para manter a seleção em Posição Geral -->
<input type="hidden" name="hidItemAberto" value="<%=Pagina.getParamStr(request, "hidItemAberto")%>">

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="msgConfirm" value=""> 
<input type="hidden" name="codArel" value="<%=codArel%>"> 
<input type="hidden" name="codAri" id="codAri" value="<%=codAri%>">
<input type="hidden" name="codTpfa" value="<%=codTpfa%>">
<input type="hidden" name="funcao" value="<%=funcao%>">
<input type="hidden" name="funcaoParecer" value="<%=funcaoParecer%>">
<input type="hidden" name="codAcomp" value="<%=codAcomp%>">
<input type="hidden" name="primeiroAcomp" value="<%=primeiroAcomp%>">
<input type="hidden" name="codIett" value="<%=codIett%>">

<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>

<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">

<%
	UsuarioUsu usuarioLogado = ((SegurancaECAR)session.getAttribute("seguranca")).getUsuario();

	String msg = "";
	String submit = "";
	boolean salvar = false; 
	boolean alterar = false; 
	String descricaoEvento = null;
	boolean liberar = false;	
	Long codAri2 = null;
	boolean podeLiberarAcomp = false;
	
	try{
	   AcompRelatorioArel acompRelatorio = new AcompRelatorioArel();
	   if(!codArel.equals("")){
            acompRelatorio = (AcompRelatorioArel) acompRelatorioDao.buscar(AcompRelatorioArel.class, 
                    										  Long.valueOf(codArel));            
	

            
			codAri2 = acompRelatorio.getAcompReferenciaItemAri().getCodAri();
	        alterar = true;
	        if ("S".equals(acompRelatorio.getIndLiberadoArel())){
		    	liberar = false;
	        } else {
				liberar = true;            
	        }
	        
       } else {
       		salvar = true; 
       }

	   ItemEstUsutpfuacIettutfa itemEstUsuTpfa = (ItemEstUsutpfuacIettutfa) iettEstUsuTpfaDao.getUsuarioAcompanhamento(ari.getItemEstruturaIett(), acompRelatorio.getTipoFuncAcompTpfa());
	   UsuarioUsu usuarioFuncao = itemEstUsuTpfa.getUsuarioUsu();
	   SisAtributoSatb grupoFuncao = itemEstUsuTpfa.getSisAtributoSatb();
	   Set gruposAcessoUsuarioLogado = usuDAO.getClassesAcessoUsuario(usuarioLogado);
	   
	   if(salvar){
		   if(usuarioFuncao != null && usuarioLogado.getCodUsu().equals(usuarioFuncao.getCodUsu())){
			   acompRelatorio.setTipoFuncAcompTpfaUsuario(acompRelatorio.getTipoFuncAcompTpfa());
		   } else if (grupoFuncao != null && gruposAcessoUsuarioLogado != null && gruposAcessoUsuarioLogado.contains(grupoFuncao)){
			   acompRelatorio.setTipoFuncAcompTpfaUsuario(acompRelatorio.getTipoFuncAcompTpfa());
		   } else{
	           TipoFuncAcompTpfa tpfaSuperior = iettEstUsuTpfaDao.buscarMaiorHierarquia(acompRelatorio.getAcompReferenciaItemAri().getItemEstruturaIett(),	acompRelatorio.getTipoFuncAcompTpfa(), usuarioLogado);
		           acompRelatorio.setTipoFuncAcompTpfaUsuario(tpfaSuperior);
		   }
            //TipoFuncAcompTpfa tpfaUsuario = (TipoFuncAcompTpfa) tpfaDAO.buscar(TipoFuncAcompTpfa.class, 
				//	  Long.valueOf(co));   
			descricaoEvento = "Adicionado um novo Parecer de Avaliação.";
			msg = _msg.getMensagem(acompRelatorioDao.salvar(acompRelatorio, request, campos));
			submit = "relatorios.jsp";
		}
		
		if(alterar){
			if(usuarioFuncao != null && usuarioLogado.getCodUsu().equals(usuarioFuncao.getCodUsu())){
				acompRelatorio.setTipoFuncAcompTpfaUsuario(acompRelatorio.getTipoFuncAcompTpfa());
		 	} else if (grupoFuncao != null && gruposAcessoUsuarioLogado != null && gruposAcessoUsuarioLogado.contains(grupoFuncao)){
				acompRelatorio.setTipoFuncAcompTpfaUsuario(acompRelatorio.getTipoFuncAcompTpfa());
		 	} else{
				TipoFuncAcompTpfa tpfaSuperior = iettEstUsuTpfaDao.buscarMaiorHierarquia(acompRelatorio.getAcompReferenciaItemAri().getItemEstruturaIett(),	acompRelatorio.getTipoFuncAcompTpfa(), usuarioLogado);
		      	acompRelatorio.setTipoFuncAcompTpfaUsuario(tpfaSuperior);
		 	}
			
			descricaoEvento = "Parecer de Avaliação alterado.";
			msg = _msg.getMensagem(acompRelatorioDao.alterar(acompRelatorio, request, campos));
			submit = "relatorios.jsp";
	
			if(liberar) {
				
				podeLiberarAcomp = true;
			
				AcompReferenciaItemAri acompRefItemAri = new AcompReferenciaItemAri();
				
				acompRefItemAri = (AcompReferenciaItemAri)ariDAO.buscar(AcompReferenciaItemAri.class,codAri2);
				
		   		List lista = new ArrayList(acompRefItemAri.getAcompRelatorioArels());
				Iterator itLista = lista.iterator();
		 	
				while(itLista.hasNext() && podeLiberarAcomp) {
					AcompRelatorioArel acompRelatorioArel = (AcompRelatorioArel) itLista.next();
					if( acompRelatorioArel.getIndLiberadoArel() != null && 
						!(acompRelatorioArel.getIndLiberadoArel()).equals("S")){
						podeLiberarAcomp = false;
					}
				}
			}
	
			if (podeLiberarAcomp && (permissaoReferencia!= null && permissaoReferencia.equals("true"))){
				msg = msg + ". Deseja liberar acompanhamento?";
			}
	
			if( "S".equals(autorizarMail) ) {
				
				ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, ((liberar == true) ? Dominios.CFG_MAIL_LIBERACAO_PARECER : Dominios.CFG_MAIL_RECUPERACAO_PARECER));
					
				ItemEstruturaIett item = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, new Long(codIett));	
				List listIett = new ArrayList(item.getItemEstUsutpfuacIettutfas());
				Iterator itListIett = listIett.iterator();
					 
				// data limite para ...
				String dataLimitePara = "";
				String dataLimiteParaAnt = "";				
				
				
				// monta link para enviar via email para usuarios relacionados ao item 
				String link = null;
						
				Evento evento = null;		
				
				//nao esta sendo carregado na pagina e é necessário para montar o link do email
				request.setAttribute("referencia_hidden", ari.getAcompReferenciaAref().getCodAref().toString());
		
				if (liberar) {
					 evento = new EventoLiberacaoAvaliacaoPararecer();
				} else {
					evento = new EventoRecuperacaoAvaliacaoPararecer();
				}
				
				link = URLEvento.montaURLEvento(evento, request);
				
				
				while( itListIett.hasNext() ) {
				
					ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itListIett.next();
					
			
					TipoFuncAcompTpfa tpfa = itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa();
					 
					TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
					tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
					tfcfgmPK.setCodTpfa(tpfa.getCodTpfa());
										
					TfuncacompConfigmailTfacfgm tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
					 
					if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {
					
						//UsuarioUsu usu = (UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getComp_id().getCodUsu());
						
						
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
							if(usu.equals(usuarioLogado)) {
								continue;
							}
							
							//FIXME: enviar e-mail somente para usuário que possui AREL no acompanhamento
							/*
							if(!listUsuariosComArel.contains(usu)) {
								continue;
							}
							*/
				
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
							
							html = ae.montaEmailComLink(textoMail, usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(), new Long(codIett), 
									descricaoEvento, null, null, 
									AgendadorEmail.LABEL_WHO_CHANGE_ALTERACAO,
									acompRelatorio.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa(), link).toString();
											
							if (usu.getEmail1UsuSent()!=null && usu.getEmail1UsuSent().length()>0)
								ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
						}
					}
				}
			}
		}
		
	} catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "relatorios.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	
	if (podeLiberarAcomp && (permissaoReferencia!= null && permissaoReferencia.equals("true"))){
		if (msg != null) 
			Mensagem.setInput(_jspx_page_context, "document.form.msgConfirm.value", msg); 
	} else {
		if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	}
	%>

<script>
			document.form.action = "<%=submit%>";
			document.form.submit();
</script>

</form>
</body>
</html>


    
    