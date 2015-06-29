<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.portal.CapaContato" %>

<%

CapaContato capaContato = new CapaContato(request);

%>
<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
</form>

<%
	String msg = "";
	String submit = "contato.jsp";
	try{				
		capaContato.enviarEmail(request, Long.valueOf(Pagina.getParamStr(request, "areaContato")), Pagina.getParamStr(request, "email"), 
			Pagina.getParamStr(request, "mensagem"));
		msg = _msg.getMensagem("portal.contato.envio.sucesso");
		
	} catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg);
%>

	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

