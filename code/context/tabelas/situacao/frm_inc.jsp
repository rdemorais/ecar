<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.SituacaoSit" %>
<%@ page import="ecar.dao.SituacaoDao" %>
<%@ include file="../../frm_global.jsp"%>
 
<% session.removeAttribute("objPesquisa");%> 
 
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
	if(!validaString(form.descricaoSit, "Descrição", true)){
		return(false);		
	}
	if(form.indConcluidoSit[0].checked == false && form.indConcluidoSit[1].checked == false){
		alert("<%=_msg.getMensagem("situacao.validacao.indConcluidoSit.obrigatorio")%>");
		return(false);
	}
	if(form.indComentarioSit[0].checked == false && form.indComentarioSit[1].checked == false){
		alert("<%=_msg.getMensagem("situacao.validacao.indComentarioSit.obrigatorio")%>");
		return(false);
	}	
	if(form.indMetaFisicaSit[0].checked == false && form.indMetaFisicaSit[1].checked == false){
		alert("<%=_msg.getMensagem("situacao.validacao.indMetaFisicaSit.obrigatorio")%>");
		return(false);
	}	
	if(form.indSemAcompanhamentoSit[0].checked == false && form.indSemAcompanhamentoSit[1].checked == false){
		alert("<%=_msg.getMensagem("situacao.validacao.indSemAcompanhamentoSit.obrigatorio")%>");
		return(false);
	}	
	return(true);
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">
<%
boolean ehPesquisa = false;

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
	SituacaoDao situacaoDao = new SituacaoDao(request);
	SituacaoSit situacao;
	
	if(session.getAttribute("objSituacao") != null){
		situacao = (SituacaoSit) session.getAttribute("objSituacao");
		session.removeAttribute("objSituacao");
	}else{
		situacao = new SituacaoSit();
  	}
	
	_disabled = "";
	%>
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