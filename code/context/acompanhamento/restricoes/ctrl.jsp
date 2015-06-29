
<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="java.util.StringTokenizer"/>
<jsp:directive.page import="ecar.historico.HistoricoPtc"/>
<jsp:directive.page import="ecar.dao.ConfiguracaoDao"/>
<jsp:directive.page import="java.util.Date"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.dao.PontoCriticoDao" %>
<%@page import="ecar.pojo.PontoCriticoPtc"%>

<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.email.AgendadorEmail" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
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
<%@page import="comum.util.FileUpload"%>
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
</head> 
<body>
<form name="form" method="post">
<%
	String msg = "";
	String submit = "";
	Long codIett = null;
	Long codTpfa = null;
	Long codAri = null;
	String strAcao = Pagina.getParamStr(request, "hidAcao");
	String codIettuc = null;
	StringTokenizer codigosParaExcluirSt = null;
	String codigosParaExcluir[] = null;
	
	strAcao = Pagina.getParamStr(request, "hidAcao");
	if (!"".equals(Pagina.getParamStr(request,"codIett"))) {
		codIett	= Long.valueOf(Pagina.getParamStr(request,"codIett"));
	}  
	String tpfa_ = Pagina.getParamStr(request,"codTpfa");
	if (!Pagina.getParamStr(request,"codTpfa").equals("")) {
		codTpfa = Long.valueOf(Pagina.getParamStr(request,"codTpfa"));
	}
	codAri = Long.valueOf(Pagina.getParamStr(request,"codAri"));
	codIettuc = Pagina.getParamStr(request, "codIettuc");
	codigosParaExcluir = request.getParameterValues("excluirPontoCritico");
	String formaVisualizacaoControle =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	
	
	
	try{
		
		
		%>
		
		<!-- variaveis que devem passar de uma tela para a outra -->
		<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
		<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
		<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
		<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
		<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
		<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
		<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
		<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	
		
		<!-- Campo necessário para botões de Avança/Retrocede -->
		<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
		<input type="hidden" name="hidAcao"       value="">
		<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
		
		
		
		<input type="hidden" name="codAba" value=<%=Pagina.getParamStr(request, "codAba")%>>
		
		<input type="hidden" name="codAri" value=<%=Pagina.getParamStr(request, "codAri")%>>
		<input type="hidden" name="codTpfa" value=<%=Pagina.getParamStr(request, "codTpfa")%>>
		<input type="hidden" name="funcao" value=<%=Pagina.getParamStr(request, "funcao")%>>
		<input type="hidden" name="funcaoParecer" value="<%=Pagina.getParamStr(request, "funcaoParecer")%>">
		<input type="hidden" name="primeiroAcomp" value=<%=Pagina.getParamStr(request, "primeiroAcomp")%>>
		<input type="hidden" name="codAcomp" value=<%=Pagina.getParamStr(request, "codAcomp")%>>
		<input type="hidden" name="codArel" value=<%=Pagina.getParamStr(request, "codArel")%>>

		<!-- campo hidden para cada chave primaria da tabela --> 
		<input type="hidden" name="codPontoCritico" value=<%=Pagina.getParamStr(request, "codPontoCritico")%>>
		<input type="hidden" name="codIettuc" value=<%=Pagina.getParamStr(request, "codIettuc")%>>
		<input type="hidden" name="codIett" value=<%=Pagina.getParamStr(request,"codIett")%>>
				
		<!-- Campos para manter a seleção em Posição Geral -->
		<input type="hidden" name="hidItemAberto" value="<%=Pagina.getParamStr(request, "hidItemAberto")%>">
		<input type="hidden" name="periodo" 	  value="<%=Pagina.getParamStr(request, "periodo")%>">
		<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
		
		<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
		<%
		
		
%>
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 


<%
		String descricaoEvento = null;
		
		PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
		ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
		TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
		UsuarioDao usuDAO = new UsuarioDao();
		EmpresaDao empDAO = new EmpresaDao(request);
		Dao dao = new Dao();

		PontoCriticoPtc pontoCritico = new PontoCriticoPtc();
		UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();			
		if("incluir".equals(strAcao)){
			descricaoEvento = "Inserção de "+Pagina.getParamStr(request, "labelEttf");
			
			pontoCriticoDao.setPontoCritico(request, pontoCritico);
			//pontoCriticoDao.setPontoCritico(request, campos, pontoCritico);	
			pontoCriticoDao.salvar(pontoCritico, usuario);
			submit = "pontosCriticos.jsp" + "?hidFormaVisualizacaoEscolhida=" + formaVisualizacaoControle + "&codTipoAcompanhamento=" + codTipoAcompanhamento;;
			msg = _msg.getMensagem("itemEstrutura.pontoCritico.inclusao.sucesso");
		}	
		else if("alterar".equals(strAcao)){
			descricaoEvento = "Alteração de "+ Pagina.getParamStr(request, "labelEttf");
			//pontoCriticoDao.setPontoCritico(request, campos, pontoCritico);
						
			/***Historico***/
            HistoricoPtc historico = new HistoricoPtc(pontoCritico, 
  	            											HistoricoPtc.alteracao,
            												pontoCriticoDao.getSession(),
            												new ConfiguracaoDao(request),
            												request);
            												
            												
	        /***Historico***/
		
			pontoCritico = (PontoCriticoPtc) pontoCriticoDao.buscar(PontoCriticoPtc.class, Long.valueOf(Pagina.getParamStr(request, "cod")));
			pontoCriticoDao.alterar( pontoCritico, request, usuario, historico);
			
			submit = "pontosCriticos.jsp" + "?hidFormaVisualizacaoEscolhida=" + formaVisualizacaoControle + "&codTipoAcompanhamento=" + codTipoAcompanhamento;
			msg = _msg.getMensagem("itemEstrutura.pontoCritico.alteracao.sucesso");
			%>
			<script language=javascript>
				document.form.codPontoCritico.value = '';
			</script>
			<%
		}
		else if("excluir".equals(strAcao)){
			descricaoEvento = "Exclusão de " + Pagina.getParamStr(request, "labelEttf");
			pontoCriticoDao.excluir(codigosParaExcluir, usuario);
			submit = "pontosCriticos.jsp" + "?hidFormaVisualizacaoEscolhida=" + formaVisualizacaoControle + "&codTipoAcompanhamento=" + codTipoAcompanhamento;;
			msg = _msg.getMensagem("itemEstrutura.pontoCritico.exclusao.sucesso");				
		}
		
		if( "S".equals(Pagina.getParamStr(request, "autorizarMail")) ) {
						
			ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_PONTO_CRITICO);
		
			ItemEstruturaIett item = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, codIett);	
			List listIett = new ArrayList(item.getItemEstUsutpfuacIettutfas());
			Iterator itListIett = listIett.iterator();
		 
			// data limite para ...
			String dataLimitePara = "";
			String dataLimiteParaAnt = "";				
			
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
						
						html = ae.montaEmail(textoMail, usu.getNomeUsu(), null, codIett, descricaoEvento, null, null, null, null).toString();
																					
						ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
					}
				}
			}
			
		}
			
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "pontosCriticos.jsp?hidFormaVisualizacaoEscolhida=" + formaVisualizacaoControle + "&codTipoAcompanhamento=" + codTipoAcompanhamento;;
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