<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.PeriodoExercicioPerExe" %>
<%@ page import="ecar.dao.PeriodoExercicioDao" %>
<%@ page import="comum.util.Data" %> 
<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.PeriodoExercicioDao" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
	<input type="hidden" name="codigo" value="<%=Pagina.getParamInt(request, "codigo")%>">
</form>

<%
	PeriodoExercicioPerExe perExe = new PeriodoExercicioPerExe();
	PeriodoExercicioDao perExeDao = new PeriodoExercicioDao(request);
	Mensagem mensagem = new Mensagem(application);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	
	/* default sempre refazer a pesquisa */
	boolean refazPesquisa = true;  
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		/* melhor usar getParamStr, pois nunca devolve null */
		perExe.setNomePerExe(Pagina.getParamStr(request, "nomePerExe").trim()); 
		
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			perExeDao.salvar(perExe);
			msg = _msg.getMensagem("perExe.inclusao.sucesso");
			session.removeAttribute("objPerExe");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objPerExe", perExe);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			perExe = (PeriodoExercicioPerExe) perExeDao.buscar(PeriodoExercicioPerExe.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			/* altera o que foi modificado na tela */
			perExe.setNomePerExe(Pagina.getParamStr(request, "nomePerExe").trim());

			/* altera no BD */
			perExeDao.alterar(perExe);
			msg = _msg.getMensagem("perExe.alteracao.sucesso");
			session.removeAttribute("objPerExe");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objPerExe", perExe);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			perExeDao.excluir((PeriodoExercicioPerExe) perExeDao.buscar(PeriodoExercicioPerExe.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("perExe.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		perExe.setNomePerExe(Pagina.getParam(request, "nomePerExe"));
		
		session.setAttribute("objPesquisa", perExe);
	}
	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			perExe = (PeriodoExercicioPerExe) session.getAttribute("objPesquisa");
			List lista = perExeDao.pesquisar(perExe, new String[] {"nomePerExe","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("perExe.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
	}
	%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

