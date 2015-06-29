<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.ItemEstruturaDao" %>

<%@ page import="java.util.HashMap" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

	<form name="form" method="post">
		<input type="hidden" name="msgPesquisa" value="">
	</form>

<%
	//ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
	
	String submit = "";
	String msg = "";
	
	try{
		String opcaoPesquisa = Pagina.getParamStr(request, "opcaoPesquisa");
		
		/* Pesquisa em Estruturas */
		if ("E".equals(opcaoPesquisa))
			submit = "frm_nav_est.jsp";
		
		/* Pesquisa nas Informações */
		if ("I".equals(opcaoPesquisa))
			submit = "frm_nav_inf.jsp";
		
		//HashMap itensMap = itemDao.pesquisarNaEstrutura(request, seguranca.getCodUsu(), application);
		//session.setAttribute("itensMap", itensMap);
	}catch (Exception e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "frm_pes.jsp";
		msg = _msg.getMensagem(e.getMessage());
	}
	
	/* da o alert */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg); 
%>

<jsp:include page="<%=submit%>"/>
	<!--script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script--> 
	
</body>
</html>

