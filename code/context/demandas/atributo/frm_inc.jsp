<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.pojo.AtributoDemandaAtbdem"%>
<%

boolean ehPesquisa = false;
boolean ehIncluir = true;

session.removeAttribute("objPesquisa");
%>

<html lang="pt-br">
	<head>
		<%@ include file="/include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link href="<%=_pathEcar%>/css/style.css" rel="stylesheet" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
		<%@ include file="validacao.jsp" %>
<script language="javascript" type="text/javascript">
	function validarGravar(form){
	return validar(form);
}
</script>		
	</head>

	<!-- sempre colocar o foco inicial no primeiro campo -->
	<body onload="javascript:onLoad(document.form);">

<%@ include file="/cabecalho.jsp"%>
<%@ include file="/divmenu.jsp"%>
<%
try{
%>
		<div id="conteudo">
			<form name="form" method="post" action="">
				<input type="hidden" name="hidAcao" value="">
				
				<!-- TITULO -->
				<%@ include file="/titulo_tela.jsp"%>
				<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp" />
<%
	AtributoDemandaAtbdem atributoDemanda;
	
	if(session.getAttribute("objAtributoDemanda") != null){
		atributoDemanda = (AtributoDemandaAtbdem) session.getAttribute("objAtributoDemanda");
		session.removeAttribute("objAtributoDemanda");
	}else{
		atributoDemanda = new AtributoDemandaAtbdem();
  	}
	
	_disabled = "";
	
	String strReadOnly = "false";
	String _pesqdisabled = "";
	boolean blVisualizaDesc = true;
%>
				<util:barrabotoes incluir="Gravar" cancelar="Cancelar" />

				<%@ include file="form.jsp"%>

				<util:barrabotoes incluir="Gravar" cancelar="Cancelar" />

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
	<%@ include file="/include/mensagemAlert.jsp"%>
</html>