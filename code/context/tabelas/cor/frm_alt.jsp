<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.Cor" %>
<%@ page import="ecar.dao.CorDao" %>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<!-- Para uso do componente de cor -->
<script language="javascript" src="<%=_pathEcar%>/js/ColorPicker2.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/PopupWindow.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/AnchorPosition.js" type="text/javascript"></script>

<script language="javascript">

function validarAlterar(form){
	if(!validaString(form.nomeCor, "Nome", true)){
		return(false);
	}
	if(!validaString(form.significadoCor, "Significado", true)){
		return(false);
	}
	if(!validaString(form.codCorGrafico, "Cor no Gráfico", true)){
		return(false);
	}
	
	return(true);
}

/*Para uso da paleta de cores*/
var field = "";
// Create a new ColorPicker object using Window Popup
var cp = new ColorPicker('window');

function pickColor(color) {
	field.value = color;
}


function selecionarCor(campo, pick){
	field = document.getElementById(campo);
	cp.select(field, pick);		
}
	

</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<div id="conteudo">

<form name="form" method="post" enctype="multipart/form-data">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
<%	

	boolean ehPesquisa = false;

	try {

		CorDao corDao = new CorDao(request);
		Cor cor;
		
		if(session.getAttribute("objCor") != null){
			cor = (Cor) session.getAttribute("objCor");
			session.removeAttribute("objCor");
		}else{
			cor = (Cor) corDao.buscar(Cor.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
	  	}
		
		_disabled = "";
%>
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

		<input type="hidden" name="codigo" value="<%=cor.getCodCor()%>">

		<table class="form">
		<%@ include file="form.jsp"%>
		</table>
	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

<%
	} catch (ECARException e) {
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