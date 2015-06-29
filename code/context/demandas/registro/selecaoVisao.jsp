<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
<%@page import="ecar.pojo.VisaoDemandasVisDem"%>
<%@page import="ecar.dao.VisaoDao"%>
<%@page import="ecar.pojo.UsuarioUsu"%>
<jsp:directive.page import="ecar.taglib.util.Input"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.Iterator"/>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 

<%@page import="ecar.pojo.UsuarioUsu"%><html lang="pt-br">
	<head>
		<%@ include file="/include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link href="<%=_pathEcar%>/css/style.css" rel="stylesheet" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>	
		<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>
	</head>
<script type="text/javascript">
function consultarDemandas(cod){
	form.hidAcao.value = "consultar";
	form.visao.value = cod;
	form.action = "lista.jsp";
	form.submit();
}	
</script>
	<!-- sempre colocar o foco inicial no primeiro campo -->
	<body onload="javascript:onLoad(document.form);">
	
<%@ include file="/cabecalho.jsp"%>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">
			<form name="form" method="post" action="">
				<input type="hidden" name="hidAcao" value="">
				<input type="hidden" name="visao" value="">
				<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>		
	
<%
if(session.getAttribute("titulo") == null || session.getAttribute("titulo").toString().equals("null")) {
	
	%>
	<h1>
	Demandas > Cadastramento Demandas 
	</h1>
	<%
}
%>
	
<table class="barrabotoes" cellpadding="0" cellspacing="0">
<tr>


<td  align="left"> Visões </td></td>
</tr>
</table>				
<table class="form">
<tr>
	
	<td align="left"> 
	<%
	VisaoDao visaoDao = new VisaoDao(request);
	if (session.getAttribute("seguranca")!=null) {
		UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
		List visoes = visaoDao.getVisoesGrupoAcesso(usuario, false, request);
		request.setAttribute("todasVisoes", visoes);		
	}
	%>
		<c:forEach var="visaoTodas" items="${todasVisoes}">
   	 		<a href="javascript:consultarDemandas(<c:out value ="${visaoTodas.codVisao}"/>);"><c:out value ="${visaoTodas.descricaoVisao}"/></a>
   	 		<br>
		</c:forEach>					
	</td>
</tr>
</table>
</form>
		</div>
	</body>
	<%@ include file="/include/estadoMenu.jsp"%>
	<%@ include file="/include/mensagemAlert.jsp"%>
</html>