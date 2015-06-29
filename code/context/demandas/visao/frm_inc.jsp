<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.pojo.VisaoDemandasVisDem"%>
<%

boolean ehPesquisa = false;
boolean ehIncluir = true;

session.removeAttribute("objPesquisa");
%>


<%@page import="ecar.pojo.VisaoDemandasVisDem"%><html lang="pt-br">
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

	function aoClicarCancelar(form){
		for(i = 0; i < document.forms[0].length; i++){
			if(document.forms[0].elements[i].name.indexOf('exibicao') != -1 && document.forms[0].elements[i].disabled){
				document.forms[0].elements[i].disabled = false;
			}
		}
		form.reset();
		focoInicial(form);
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
	VisaoDemandasVisDem visaoDemandas;
	
	if(session.getAttribute("objVisao") != null){
		visaoDemandas = (VisaoDemandasVisDem) session.getAttribute("obVisao");
		session.removeAttribute("objVisao");
	}else{
		visaoDemandas = new VisaoDemandasVisDem();
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