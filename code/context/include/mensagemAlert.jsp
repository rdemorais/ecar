<%
if(Pagina.getParam(request, "msgOperacao") != null){
	Mensagem.alert(_jspx_page_context, Pagina.getParam(request, "msgOperacao"));
}
if(Pagina.getParam(request, "msgPesquisa") != null){
	Mensagem.alert(_jspx_page_context, Pagina.getParam(request, "msgPesquisa"));
}
%>