<%@ include file="../frm_global.jsp"%>
<%@ page import="ecar.pojo.PopupPpp" %>
<%@ page import="ecar.dao.PopUpDao" %>
<%@ page import="comum.util.Pagina" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%
ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
boolean visualizacao = Pagina.getParamBool(request, "visualizacao");
PopupPpp popUp;
if(!visualizacao){
	popUp = (PopupPpp) new PopUpDao(request).buscar(PopupPpp.class, Long.valueOf(Pagina.getParamStr(request, "codPpp")));
}
else {
	popUp = new PopupPpp();
	popUp.setConteudoPpp(Pagina.getParamStr(request, "texto"));
	if(!"".equals(Pagina.getParamStr(request, "altura")))
		popUp.setJanelaAlturaPpp(Integer.valueOf(Pagina.getParamStr(request, "altura")));
	if(!"".equals(Pagina.getParamStr(request, "largura")))
		popUp.setJanelaLarguraPpp(Integer.valueOf(Pagina.getParamStr(request, "largura")));
}

UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
String codUsuario = usuario.getCodUsu().toString();
String nomePopUp = "popUp_" + codUsuario + "_" + popUp.getCodPpp();


%>


<%@page import="ecar.pojo.ConfiguracaoCfg"%><html lang="pt-br">
<script language="javascript" src="../js/cookie.js"></script>
<script>
function naoMostrarNovamente(){
	if(document.forms[0].mostrarNovamente.checked){
		setCookie("<%=nomePopUp%>","1", null, _pathEcar, null, null);	
	} else {
		if(getCookie("<%=nomePopUp%>") != null)
			deleteCookie("<%=nomePopUp%>", _pathEcar, null);
	}
}
</script>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracao.getEstilo().getNome()%>.css" type="text/css">
<body>
<%
try{
%>
<form name="form">
<table height="<%=popUp.getJanelaAlturaPpp().toString()%>px" width="<%=popUp.getJanelaLarguraPpp().toString()%>px">
<tr heigth="90%"><td valign="top">
	<%=popUp.getConteudoPpp()%>
</td></tr>
<tr height="5%"><td valign="botton" class="label">
	<%
	if("S".equals(popUp.getIndDesativarPpp())){
		%>
		<input type="checkbox" class="form_check_radio" name="mostrarNovamente" onclick="naoMostrarNovamente()"> Não mostrar novamente
		<br>
		<%
	}
	%>
	<input type="button" value="Fechar" onclick="javascript:window.close()">				
</td></tr>

</form>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
</html>