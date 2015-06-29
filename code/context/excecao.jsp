<%
e.printStackTrace();
org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
%>

<script language="Javascript">
if(window.opener){
	alert('<%=_msg.getMensagem("erro.excecao.jsp")%> \nExceção: <%=e.getMessage()%> \n Esta janela será fechada...');
	window.close();
}
else {
	alert('<%=_msg.getMensagem("erro.excecao.jsp")%> \nExceção: <%=e.getMessage()%>');
}
</script>
