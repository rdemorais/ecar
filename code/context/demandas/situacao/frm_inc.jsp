<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SitDemandaSitd" %>
<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");
String indTabelaUso = session.getAttribute("indTabelaUso").toString();
%>

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
function validarGravar(form){
	if(!validaString(form.descricaoSitd,'Descrição',true)){
		return(false);
	}
	
	if(form.indConclusaoSitd.value == "S" && form.indPrimeiraSituacaoSitd.value == "S"){
		alert("Pelo menos um dos índices precisa ser Não!");
		form.indConclusaoSitd.focus();
		return(false);
	}
	
	return(true);
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

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
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

<%
	SitDemandaSitd situacao = new SitDemandaSitd();
		  	
  	if(session.getAttribute("objSituacao") != null){
		situacao = (SitDemandaSitd) session.getAttribute("objSituacao");
		session.removeAttribute("objSituacao");
	}else{
		situacao = new SitDemandaSitd();
  	}
	
	_disabled = "";
%>
	<input type="hidden" name="codSitd" value="<%=Pagina.trocaNull(situacao.getCodSitd())%>">
	
	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	
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