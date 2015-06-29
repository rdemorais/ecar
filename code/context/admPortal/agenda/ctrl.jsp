<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AgendaDao" %>
<%@ page import="ecar.dao.AgendaOcorrenciaDao" %>
<%@ page import="ecar.pojo.AgendaAge" %>
<%@ page import="ecar.pojo.AgendaOcorrenciaAgeo" %>
   
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamStr(request, "hidRegistro")%>">	
	<!-- campo de controle para as mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">
	<input type="hidden" name="hidPesquisou" value="">
	<!-- código para recuperar a alteração -->
	<input type="hidden" name="codAgeo" value="<%=Pagina.getParamStr(request, "codAgeo")%>">
</form>

<%
	AgendaAge agenda = new AgendaAge();
	AgendaDao agendaDao = new AgendaDao(request);
	AgendaOcorrenciaAgeo agendaOC = new AgendaOcorrenciaAgeo();
	AgendaOcorrenciaDao agendaOCDao = new AgendaOcorrenciaDao(request);
	
	String hidAcao = Pagina.getParamStr(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean alterarOc = "ALTERAR_OCORRENCIAS".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		submit = "frm_inc.jsp";
		try {
			agendaDao.setAgenda(request, agenda);
			agenda.setIndAtivoAge(Pagina.getParamStr(request, "indAtivo"));
			/* TODO agenda.setUsuarioUsu(); */
			agendaDao.salvar(agenda, request);
			msg = _msg.getMensagem("admPortal.agenda.inclusao.sucesso");
			session.removeAttribute("objAgenda");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objAgenda", agenda);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		submit = "frm_pes.jsp";
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			
			/* busca o objeto para manter o que já esta cadastrado */
			agenda = (AgendaAge) agendaDao.buscar(AgendaAge.class, Long.valueOf(Pagina.getParamStr(request, "codAge")));
			agendaDao.setAgenda(request, agenda);
			agenda.setUsuarioUsu(((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
			agendaDao.alterar(agenda, request);
			//submit = "frm_con.jsp";
			msg = _msg.getMensagem("admPortal.agenda.alteracao.sucesso");
	/*		agendaOC = (AgendaOcorrenciaAgeo) agendaOCDao.buscar(AgendaOcorrenciaAgeo.class, Long.valueOf(Pagina.getParamStr(request, "codAgeo")));
			agenda =  agendaOC.getAgendaAge();
			agenda.setIndAtivoAge(Pagina.getParamStr(request, "indAtivo"));
			agendaDao.alterar(agenda);
			
			agendaOCDao.setAgendaOcorrencia(request, agendaOC);
			agendaOCDao.alterar(agendaOC);
			msg = _msg.getMensagem("admPortal.agenda.alteracao.sucesso");
			session.removeAttribute("objSegmentoItem");*/
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			submit = "frm_alt.jsp";
			session.setAttribute("objAgendaOC", agendaOC);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterarOc) {
		submit = "frm_pes.jsp";
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			agendaOC = (AgendaOcorrenciaAgeo) agendaOCDao.buscar(AgendaOcorrenciaAgeo.class, Long.valueOf(Pagina.getParamStr(request, "codAgeo")));
				
			agenda =  agendaOC.getAgendaAge();
			agenda.setEventoAge(Pagina.getParamStr(request, "eventoAge"));
			agenda.setIndAtivoAge(Pagina.getParamStr(request, "indAtivo"));
			agendaDao.alterar(agenda);
						
			agendaOCDao.alterarOcorrencias(request, agenda);
						
			msg = _msg.getMensagem("admPortal.agenda.alteracao.sucesso");
			session.removeAttribute("objSegmentoItem");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			submit = "frm_alt.jsp";
			session.setAttribute("objAgendaOC", agendaOC);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			agendaOC = (AgendaOcorrenciaAgeo) agendaOCDao.buscar(AgendaOcorrenciaAgeo.class, Long.valueOf(Pagina.getParamStr(request, "codAgeo")));
			agendaDao.excluir(agendaOC.getAgendaAge(), agendaOC, Pagina.getParamStr(request, "hidExcluir"));
			msg = _msg.getMensagem("admPortal.agenda.exclusao.sucesso");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	}
	/* da o alert para incluir, alterar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	
	%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>