<%@ taglib uri="http://celepar.pr.gov.br/taglibs/informacao.tld" prefix="info" %>

<%@page import="ecar.pojo.ConfiguracaoCfg"%>
<%@page import="ecar.dao.ConfiguracaoDao"%><html lang="pt-br">
<head>
<%@ include file="/frm_global.jsp"%>
<%
	ConfiguracaoCfg configuracaoCfg = new ConfiguracaoDao().buscarConfiguracaoTituloENomeArquivoCss();
%>
<title><%=configuracaoCfg.getTituloSistema()%></title>
<%@ include file="include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
</head>

<body>

<div align="center">
	<p>
	<h1>Sistema de Seguran&ccedil;a</h1>
	<p>
	<info:sentinelaErro/>
		<p>
	<br>
	<br>
	<br>
	<table>
		<tr>
			<td align='center'>
				<a href="javascript:history.back();">Clique aqui para voltar a tela anterior do sistema</a>
			</td>
		</tr>
		<tr>
			<td align='center'>
				ou
			</td>
		</tr>
		<tr>
			<td align='center'>
				<a href="<%=request.getContextPath()%>/sair.jsp">Clique aqui para sair do sistema</a>
			</td>
		</tr>
	</table>
</div>

</body>
</html>
