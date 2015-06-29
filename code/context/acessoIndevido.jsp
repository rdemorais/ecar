<html lang="pt-br">
<%
	ConfiguracaoCfg configuracaoCfg = (ConfiguracaoCfg)session.getAttribute("configuracao");
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="js/menu_retratil.js"></script>
<script language="javascript" src="js/menu_retratil.js"></script>
<script language="javascript" src="js/focoInicial.js"></script>
<script language="javascript" src="js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="js/datas.js"></script>
<script language="javascript" src="js/numero.js"></script>
<script language="javascript" src="js/popUp.js"></script> 
<script language="javascript" src="js/validacoes.js"></script>
<script language="javascript" src="validacao.js"></script>
<%@ include file="include/meta.jsp" %>
<%@ include file="login/validaAcesso.jsp" %>
<%@ include file="frm_global.jsp" %>
<%@ include file="cabecalho.jsp" %>
<%@ include file="divmenu.jsp" %> 
<body>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<table align="center">
		<tr>
			<td align="center" valign="middle">
				<p class="tituloCabecAcesso" >Acesso Indevido!!!</p>
			</td>
		</tr>
		<tr>
			<td align="center" valign="middle">
				<p class="tituloCabecAcesso" style="font-size:12px;" onclick="history.back();" >Clique aqui para retornar</p>
			</td>
		</tr>		
	</table>
</body>
</html>