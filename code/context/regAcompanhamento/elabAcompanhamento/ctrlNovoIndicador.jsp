<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.AcompRealFisicoDao" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
</head>   
<body>
<form name="form" method="post">  

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>"> 
<input type="hidden" name="subNiveis" value="<%=Pagina.getParamStr(request, "subNiveis")%>">
<input type="hidden" name="primeiroItemClicado" value="<%=Pagina.getParamStr(request, "primeiroItemClicado")%>">
<input type="hidden" name="primeiroItemAriClicado" value="<%=Pagina.getParamStr(request, "primeiroItemAriClicado")%>">
<input type="hidden" name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<input type=hidden name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">


<%
	String msg = "";
	String submit = "";
	try{
		new AcompRealFisicoDao(request).incAcompRealFisicoArf(request);
		msg = _msg.getMensagem("acompanhamento.realizadoFisico.novoIndicador.inclusaoSucesso");
		submit = "realizadoFisico.jsp";
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "realizadoFisico.jsp";
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

