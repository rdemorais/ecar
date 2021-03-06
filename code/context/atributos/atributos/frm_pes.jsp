<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="comum.util.Pagina" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>

<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">
function validarPesquisar(form){
	return(true);
}

function aoCarregar(form){
	onLoad(form);
	aoMudarTipoValidacao();
	habilitaPanelsID();
}
</script>

<%
String indTabelaUso ="";
if(session.getAttribute("indTabelaUso")!=null)
 indTabelaUso = session.getAttribute("indTabelaUso").toString();%>
<title>
	<%=_msg.getMensagem("atributo.titulo."+indTabelaUso)%>
</title>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:aoCarregar(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>	
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

<%
	SisAtributoSatb atributo;
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		atributo = (SisAtributoSatb) session.getAttribute("objPesquisa");
	}else{
		atributo = new SisAtributoSatb();
  	}
	_disabled = "";
    _comboSimNao = "Todos";	
	_obrigatorio = "";    
%>

	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>	
	
</form>

</div>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>