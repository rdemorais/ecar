<%@ page import="ecar.pojo.SegmentoCategoriaSgtc" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SegmentoDao" %>

<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.SegmentoCategoriaDao" %>

<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<html lang="pt-br"> 
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoAdmPortal.js"></script>

<script language="javascript">
function validarPesquisar(form){
	return(true);
}
</script>

</head>

<%
	SegmentoCategoriaSgtc segmentoCategoria;
	SegmentoCategoriaDao segmentoCategoriaDao = new SegmentoCategoriaDao(request);	
	if(session.getAttribute("objPesquisa") != null  && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		segmentoCategoria = (SegmentoCategoriaSgtc) session.getAttribute("objPesquisa");
	}else{
		segmentoCategoria =  new SegmentoCategoriaSgtc(); 
	}	 
	_disabled = "";
	_readOnly = "";
	_comboSimNao = "Todos";
	_obrigatorio = "";
	
	String tituloCat = "Categorias de Itens Livres";
	String codSegmento = "";
	if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.pergFreq"))){
		tituloCat = "Categorias de Perguntas Frequentes";
		codSegmento = Pagina.getParamStr(request, "codSegmento");
	}
%>


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
	<input type="hidden" name="codSegmento" value="<%=codSegmento%>">						
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
	<%
	String linkPesquisar = "frm_pes.jsp?codSegmento=" + codSegmento;
	String linkIncluir = "frm_inc.jsp?codSegmento=" + codSegmento;
	%>
	<util:linkstop incluir="<%=linkIncluir%>" pesquisar="<%=linkPesquisar%>"/>

	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

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