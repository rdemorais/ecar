
<jsp:directive.page import="ecar.dao.PoderDao"/>
<jsp:directive.page import="ecar.pojo.PoderPod"/><%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript">

function validarAlterar(form){

	if(form.nomePod.value == ""){
		alert("<%=_msg.getMensagem("poder.validacao.nomePod.obrigatorio")%>");
		form.nomePod.focus();
		return(false);
	}
	
	if(form.siglaPod.value == ""){
		alert("<%=_msg.getMensagem("poder.validacao.siglaPod.obrigatorio")%>");
		form.siglaPod.focus();
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

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

<%
	try{
		PoderDao poderDao = new PoderDao(request);
		PoderPod poder;
		
		if(session.getAttribute("objPoderPod") != null){
			poder = (PoderPod) session.getAttribute("objPoderPod");
			session.removeAttribute("objPoderPod");
		}else{
			poder = (PoderPod) poderDao.buscar(PoderPod.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
	  	}
		
		_disabled = "";

%>
		<input type="hidden" name="codigo" value="<%=poder.getCodPod()%>">
	
		<table class="form">
		<%@ include file="form.jsp"%>
		</table>
	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
<%
	} catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		/* redireciona para o controle */
%>
		<script language="javascript">
			document.form.action = "ctrl.jsp";
			document.form.submit();
		</script>
<%
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
%>

	
</form>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>