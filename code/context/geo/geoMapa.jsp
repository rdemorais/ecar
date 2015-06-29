
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<html lang="pt-br">
<head> 
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/popUp.js"></script>
<script language="javascript">
function continuar(form){
	form.action = "../salaControle/grafico.jsp";
	form.submit();
}
</script>
</head>
<%
try {

	String cidade = request.getParameter("cidade");
%>
<%@ include file="../cabecalho.jsp"%> 
<%@ include file="../divmenu.jsp"%> 
<body onload="javascript:doLoad()"> 
<div id="conteudo">
<%@ include file="/titulo_tela.jsp"%>

<form name="form" method="post">
	<input type="hidden" name="codAri" value="">	
	<input type="hidden" name="itemDoNivelClicado" value="">	
	<input type="hidden" name="pause" value="N">
		
	<div id="subconteudo">
	<input type="button" name="btPause" value="Continuar [ > ]" onclick="javascript:continuar(form)">
	<br><br>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="20%" align="center"><h1>Informações por Município</h1></td>		
			<td width="80%"></td>	
		</tr>
	</table>
	<br>

		<table border="0" cellpadding="0" cellspacing="0" align="center">
		  <tr>
		    <td align="center"><img src="<%=_pathEcar%>/images/mapa_pr.gif" usemap="#mapaParana">
		    <map name="mapaParana">
		    
		    <area shape ="poly" coords ="402,84,404,91,398,94,402,99,396,110,389,109,393,129,401,129,403,134,399,150,406,154,412,150,410,145,422,151,435,151,433,146,437,145,429,135,432,117,423,104,141,102,409,90"
		    href ="geoMapa.jsp?cidade=londrina#ancora"
		    title="Londrina" />
		    <!-- 
		    <area shape ="rect" coords ="272,277,325,348"
		    href ="geoMapa.jsp?cidade=cascavel"
		    title="Cascavel" />
		    -->
		    <area shape ="poly" coords ="319,95,330,101,330,111,326,114,324,119,320,119,314,128,306,125,306,123,309,120,314,118,305,110,306,106"
		    href ="geoMapa.jsp?cidade=maringa#ancora"
		    title="Maringá" />
		    <!-- 
		    <area shape ="rect" coords ="732,346,750,379"
		    href ="geoMapa.jsp?cidade=curitiba"
		    title="Curitiba" /> 
		    <area shape ="rect" coords ="168,338,188,385"
		    href ="geoMapa.jsp?cidade=fozdoiguacu"
		    title="Foz do Iguaçú" />    
		    -->
		    </map>
		    </td>        
		  </tr>
		</table>	
		
		<% if (cidade != null){ %>
			<% if (cidade.equals("londrina")){ %>
				<%@ include file="geoPosicoesLondrina.jsp"%>
			<% } %>
			<% if (cidade.equals("maringa")){ %>
				<%@ include file="geoPosicoesMaringa.jsp"%>
			<% } %>
		<% } %>
	</div>	
	
	
</form>
	<%

} catch (ECARException e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	
</div>

</body>
<%@ include file="../include/ocultarAguarde.jsp"%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>
</html>