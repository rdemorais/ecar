
<jsp:directive.page import="ecar.util.Dominios"/>
<%@ include file="../../../login/validaAcesso.jsp"%>
<%@ include file="../../../frm_global.jsp"%>
<%@ page import="ecar.dao.ApontamentoDao" %>
<%@page import="ecar.pojo.ApontamentoApt"%> 
 
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
<%@ page import="comum.util.Util" %>
<%@ page import="comum.database.Dao" %>
<%@ page import="ecar.evento.Evento" %>
<%@ page import="ecar.evento.URLEvento" %>
<%@page import="ecar.evento.EventoManutencaoApontamentoPontoCritico"%>
<%@ page import="ecar.dao.FuncaoDao" %>

<html lang="pt-br"> 
<body>
<%@ include file="../../../include/meta.jsp"%>
<form name="form" method="post">   
<%
	Long   codIett	 = Long.valueOf(Pagina.getParamStr(request, "codIett"));
	String formaVisualizacaoControle =  Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
	String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	FuncaoDao funcaoDao = new FuncaoDao(request);
	long codAba = funcaoDao.getCodFuncaoDadosGerais();
	String msg = "";
	String submit = "";
	ApontamentoDao apontamentoDao = new ApontamentoDao(request);			
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
	UsuarioDao usuDAO = new UsuarioDao();
	EmpresaDao empDAO = new EmpresaDao(request);
	Dao dao = new Dao();
%>
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAba" value=<%=codAba%>>
<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="cod" value=<%=Pagina.getParamStr(request, "cod")%>>
<input type="hidden" name="codPtc" value=<%=Pagina.getParamStr(request, "codPtc")%>>
<input type="hidden" name="codIett" value=<%=codIett%>>


<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
<input type="hidden" name="codIettRelacao" value="<%=Pagina.getParamStr(request, "codIettRelacao")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
	<!-- campo hidden para nao perder variaveis necessarias ao botao avançar e retroceder e o link voltar -->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="codAri"        value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="codArisControleVisualizacao" value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">

<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type="hidden" name="codAcomp"      value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<input type="hidden" name="hidAcao"       value="<%=Pagina.getParamStr(request, "hidAcao")%>">

<input type="hidden" name="codIetta"      value="<%=Pagina.getParamStr(request, "codIetta")%>">
<input type="hidden" name="codAba"        value="<%=codAba%>">
<input type="hidden" name="labelEttf"     value="<%=Pagina.getParamStr(request, "labelEttf")%>">
<input type="hidden" name="Usuario"       value="<%=Pagina.getParamStr(request, "Usuario")%>">
<input type="hidden" name="codApt" 		  value="<%=Pagina.getParamStr(request, "codApt")%>">
<input type="hidden" name="periodo" 	  value="<%=Pagina.getParamStr(request, "periodo")%>">
<input type="hidden" name="referencia_hidden" value="<%=Pagina.getParamStr(request, "referencia_hidden")%>">
<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">


<%

	String descricaoEvento = null;

	try{
			ApontamentoApt apontamento = new ApontamentoApt();
			if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
				apontamentoDao.setApontamento(request, apontamento);
				apontamentoDao.salvar(apontamento);
				submit = "lista.jsp?hidFormaVisualizacaoEscolhida=" + formaVisualizacaoControle + "&codTipoAcompanhamento=" + codTipoAcompanhamento;
				descricaoEvento = "Inclusão de " +Pagina.getParamStr(request, "labelEttf");
				msg = _msg.getMensagem("itemEstrutura.apontamento.inclusao.sucesso");
			}	
			if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
				apontamento = (ApontamentoApt) apontamentoDao.buscar(ApontamentoApt.class, Long.valueOf(Pagina.getParamStr(request, "codApt")));	
				apontamentoDao.setApontamento(request, apontamento);					
				apontamentoDao.alterar(apontamento);
				submit = "frm_con.jsp?hidFormaVisualizacaoEscolhida=" + formaVisualizacaoControle + "&codTipoAcompanhamento=" + codTipoAcompanhamento;
				descricaoEvento = "Alteração de " +Pagina.getParamStr(request, "labelEttf");				
				msg = _msg.getMensagem("itemEstrutura.apontamento.alteracao.sucesso");
			}
			if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
				String codigosParaExcluir[] = request.getParameterValues("excluir");
				apontamentoDao.excluir(codigosParaExcluir);
				submit = "lista.jsp?hidFormaVisualizacaoEscolhida=" + formaVisualizacaoControle + "&codTipoAcompanhamento=" + codTipoAcompanhamento;
				descricaoEvento = "Exclusão de " +Pagina.getParamStr(request, "labelEttf");
				msg = _msg.getMensagem("itemEstrutura.apontamento.exclusao.sucesso");				
			}
			
			if( "S".equals(Pagina.getParamStr(request, "autorizarMail")) ) {
				
				String link = null;										
				Evento evento = new EventoManutencaoApontamentoPontoCritico();									     
			    link = URLEvento.montaURLEvento(evento, request);
			    
				ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_APONTAMENTO);
			
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
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "lista.jsp?hidFormaVisualizacaoEscolhida=" + formaVisualizacaoControle + "&codTipoAcompanhamento=" + codTipoAcompanhamento;
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