<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SegmentoSgt" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SegmentoDao" %>
<%@ page import="java.util.List" %>

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
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/frmPadraoAdmPortal.js"></script>

<script language="javascript">
function validarPesquisar(form){
	if (!validaString(form.codSegmento,'Segmento',true)){
		return(false);
	}
	return(true);
}
</script>

</head>

<%
	SisAtributoSatb editoria;
	SisAtributoDao editoriaDao = new SisAtributoDao(request);	
	if(session.getAttribute("objPesquisa") != null  && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		editoria = (SisAtributoSatb) session.getAttribute("objPesquisa");
	}else{
		editoria =  new SisAtributoSatb(); 
	}	 
	_disabled = "";
	_readOnly = "";
	_comboSimNao = "Todos";
	_obrigatorio = "";
	
	String tituloEd = "Editorias de Itens Livres";
	String codSegmento = "";

	if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.materias"))){
		tituloEd = "Editorias de Matérias";
	  	codSegmento = Pagina.getParamStr(request, "codSegmento");
	}
	if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.taxacoes"))){
	  	tituloEd = "Editorias de imagens da mídia";
	  	codSegmento = Pagina.getParamStr(request, "codSegmento");
	}
	
	if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.pergFreq"))){
	  	tituloEd = "Editorias de Perguntas Frequentes";
	  	codSegmento = Pagina.getParamStr(request, "codSegmento");
	}
	if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.glossario"))){
	  	tituloEd = "Editorias de Glossário";
	  	codSegmento = Pagina.getParamStr(request, "codSegmento");
	}
	SegmentoSgt segmento = new SegmentoSgt();
	if(!"".equals(codSegmento))
		segmento = (SegmentoSgt) new SegmentoDao(request).buscar(SegmentoSgt.class, Long.valueOf(codSegmento));  	
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
