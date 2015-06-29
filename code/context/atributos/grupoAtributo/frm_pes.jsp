<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="comum.util.Pagina" %>

<%
boolean ehPesquisa = true;
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

<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">
function validarPesquisar(form){
	if(Trim(form.seqApresentacaoSga.value) != ""){
		if(!validaNum(form.seqApresentacaoSga, 'Seq. de Apresenta��o', false)){
			alert("<%=_msg.getMensagem("demGrupoAtributo.validacao.seqApresentacaoSga.invalido")%>");
			form.seqApresentacaoSga.focus();
			return(false);
		}
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
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

<%
	SisGrupoAtributoSga grupoAtributo;
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		grupoAtributo = (SisGrupoAtributoSga) session.getAttribute("objPesquisa");
	}else{
		grupoAtributo = new SisGrupoAtributoSga();
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