<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
 
<%
	_disabled = "";
	boolean ehPesquisa = false;
	boolean exibirDadosManutencao = true;
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
	return "frm_alt.jsp";
}


function validarAlterar (form){
	//metodo localizado no form.jsp
	return validarGravar (form);
}

</script>

</head>


<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form)">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="jspOrigem" value="">

	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	<% try { %>
	<util:barrabotoes alterar="Gravar" cancelar="Cancelar" controlaFluxo="true"/>

		<%@ include file="form.jsp"%>	

	<util:barrabotoes alterar="Gravar" cancelar="Cancelar" controlaFluxo="true"/>

	<%
	} 
	catch (ECARException e) {
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