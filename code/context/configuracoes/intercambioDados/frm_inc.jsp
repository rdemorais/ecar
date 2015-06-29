<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ include file="../../frm_global.jsp"%>
 
<%
	_disabled = "disabled";
	boolean ehPesquisa = false;
	boolean exibirDadosManutencao = false;
 	/* remove um objeto de pesquisa que pode ter sido utilizado em outra tela */
   session.removeAttribute("objPesquisa");
 %>

<html lang="pt-br">
<head>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript">

function caminhoRetorno(){
	return "frm_inc.jsp";
}

function iniciarProcesso(hidAcao){
	document.form.hidAcao.value=hidAcao;
	document.form.action = "ctrl.jsp";
	
	document.form.submit();	
}


</script>
</head>


<!-- sempre colocar o foco inicial no primeiro campo -->
<body >

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="jspOrigem" value="">

	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	<% try { 
		_disabled = "";
	%>
	<util:linkstop incluir="javascript:iniciarProcesso('iniciarIncluir');" pesquisar="javascript:iniciarProcesso('iniciarPesquisa');"/>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar" />

	<%@ include file="form.jsp"%>

	<util:barrabotoes incluir="Gravar" cancelar="Cancelar" />

	<%} catch (ECARException e) {
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

	<%@ include file="../../include/estadoMenu.jsp"%>
	
</form>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>