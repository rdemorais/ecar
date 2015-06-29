<%@ page import="ecar.pojo.PopupPpp" %>
<%@ page import="ecar.dao.PopUpDao" %>

<%@ page import="java.util.List" %>

<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ include file="../../frm_global.jsp"%>
<%@ include file="../../login/validaAcesso.jsp"%>

<%
int hidRichText;
	hidRichText = Pagina.getParamInt(request, "hidRichText");
%> 
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoAdmPortal.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javaScript" src="../../js/html2xhtml.js"></script>
<script language="javaScript" src="../../js/richtext.js"></script>

<script language="javascript">
<%@ include file="validacao.jsp"%>

function aoClicarBtn1(form){
	if(validar(form)){
		var url = "../../popUp/popUpAvisoPadrao.jsp?visualizacao=true&texto="+escape(form.conteudoPpp.value)+"&altura="+escape(form.janelaAlturaPpp.value)+"&largura="+escape(form.janelaLarguraPpp.value);
		abreJanela(url, form.janelaLarguraPpp.value, form.janelaAlturaPpp.value, "Visualização");
	}
}
</script>


</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
String linkIncluir = "frm_inc.jsp";
String linkPesquisar = "frm_pes.jsp?hidRichText=1";

try{
%>
<div id="conteudo"> 
 
<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="texto" value="">
	<input type="hidden" name="hidRichText" value="<%=hidRichText%>">	
	 
	<!-- TITULO --> 
	<%@ include file="/titulo_tela.jsp"%>
 
	<util:linkstop incluir="<%=linkIncluir%>" pesquisar="<%=linkPesquisar%>"/>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar" btn1="Visualizar"/>

	<%
	PopupPpp popUp = new PopupPpp();  	
	PopUpDao popUpDao = new PopUpDao(request);
	
	if(session.getAttribute("objPopup") != null){
		popUp = (PopupPpp) session.getAttribute("objPopup");
		session.removeAttribute("objPopup");
	}else{
		popUp = new PopupPpp();
  	}
  	  	 
	_disabled = ""; 
	%>

	<table class="form"> 
	<%@ include file="form.jsp"%>

	<util:barrabotoes incluir="Gravar" cancelar="Cancelar" btn1="Visualizar"/>
	
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
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>