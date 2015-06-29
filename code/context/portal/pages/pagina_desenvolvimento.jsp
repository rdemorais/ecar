<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page language="java" %>
<%@ page import="ecar.pojo.SegmentoItemSgti" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-br">
<head>


<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
</head>
<body>
<%@ include file="/cabecalho.jsp" %>
	
	<form name="form" method="post" action="ctrl_artigos.jsp">
		<input type="hidden" name="hidAcao" value="">	
	</form>
	
	
	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td></td>
			<td align="right">&lt;&lt;<a href="javascript:history.back()"> Voltar</a></td>
		</tr>
	</table>
	<center><h1 class="interno">PÁGINA EM DESENVOLVIMENTO</h1></center>
	<br><br><br>

</body>
</html>
