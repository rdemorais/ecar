<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.FuncaoFun" %>
<%@ page import="ecar.dao.FuncaoDao" %>
<%@ page import="java.util.List" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">	
</form>

<%
	FuncaoFun funcao = new FuncaoFun();
	FuncaoDao funcaoDao = new FuncaoDao(request);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	
	if ("incluir".equalsIgnoreCase(hidAcao)) {
		/* melhor usar getParamStr, pois nunca devolve null */
		
		funcaoDao.setFuncaoFun(request,funcao,true);
				
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			funcaoDao.salvar(funcao);
			msg = _msg.getMensagem("funcao.inclusao.sucesso" );
			session.removeAttribute("objFuncao");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objFuncao", funcao);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("alterar".equalsIgnoreCase(hidAcao)) {
		try {			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */
			funcao = (FuncaoFun) funcaoDao.buscar(FuncaoFun.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
			funcaoDao.setFuncaoFun(request,funcao,true);
			
			funcaoDao.alterar(funcao);
			msg = _msg.getMensagem("funcao.alteracao.sucesso" );
			session.removeAttribute("objFuncao");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objFuncao", funcao);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("excluir".equalsIgnoreCase(hidAcao)) {
		try {
			funcaoDao.excluir( (FuncaoFun) funcaoDao.buscar(FuncaoFun.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("funcao.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("pesquisar".equalsIgnoreCase(hidAcao)) {
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		funcaoDao.setFuncaoFun(request,funcao,false);
				
		session.setAttribute("objPesquisa", funcao);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	
	if (refazPesquisa) {
		try {
			funcao = (FuncaoFun) session.getAttribute("objPesquisa");
			List lista = funcaoDao.pesquisar(funcao, new String[] {"codFun","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("funcao.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null){
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
		}
	}
	%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>
			
