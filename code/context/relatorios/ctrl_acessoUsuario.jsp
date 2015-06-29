<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>
<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>
<%
try{
	//ecar.pojo.ConfiguracaoCfg configura2 = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();

	String acao = Pagina.getParamStr(request, "hidAcao");

%>
<form name="form2" method="post">
	<input type="hidden" name="titulo_sistema" value="<%=configuracaoCfg.getTituloSistema()%>">
	<input type="hidden" name="dataInicio" value="<%=Pagina.getParamStr(request, "dataInicio")%>">
	<input type="hidden" name="dataFim" value="<%=Pagina.getParamStr(request, "dataFim")%>">
	<input type="hidden" name="usu" value="<%=Pagina.getParamStr(request, "usu")%>">
	<input type="hidden" name="codUsu" value="<%=Pagina.getParamStr(request, "codUsu")%>">
	<input type="hidden" name="nomeUsu" value="<%=Pagina.getParamStr(request, "nomeUsu")%>">
	<input type="hidden" name="ordenacao" value="<%=Pagina.getParamStr(request, "ordenacao")%>">
</form>
	
<%
	if("imprimir".equals(acao)){
%>
		<script language="javascript">
			document.form2.action = "<%=_pathEcar%>/RelatorioAcessoUsuario";
			document.form2.submit();
		</script>
<%
	}


} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
</html>

