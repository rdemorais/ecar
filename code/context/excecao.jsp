<%
e.printStackTrace();
org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
%>

<script language="Javascript">
if(window.opener){
	alert('<%=_msg.getMensagem("erro.excecao.jsp")%> \nExce��o: <%=e.getMessage()%> \n Esta janela ser� fechada...');
	window.close();
}
else {
	alert('<%=_msg.getMensagem("erro.excecao.jsp")%> \nExce��o: <%=e.getMessage()%>');
}
</script>
