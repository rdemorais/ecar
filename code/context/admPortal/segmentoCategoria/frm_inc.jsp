<%@ page import="ecar.pojo.SegmentoCategoriaSgtc" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SegmentoCategoriaDao" %>
<%@ page import="ecar.dao.SegmentoDao" %>
<%@ page import="java.util.List" %>

<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ include file="../../frm_global.jsp"%>
<%@ include file="../../login/validaAcesso.jsp"%>
 
<%session.removeAttribute("objPesquisa");%> 
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoAdmPortal.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript">
<%@ include file="validacao.jsp"%>
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
 
<form name="form" method="post" enctype="multipart/form-data">
	 
	<%
	SegmentoCategoriaSgtc segmentoCategoria = new SegmentoCategoriaSgtc();
	SegmentoCategoriaDao segmentoCategoriaDao = new SegmentoCategoriaDao(request);
	
	if(session.getAttribute("objSegmentoCategoria") != null){
		segmentoCategoria = (SegmentoCategoriaSgtc) session.getAttribute("objSegmentoCategoria");
		session.removeAttribute("objSegmentoCategoria");
	}else{
		segmentoCategoria = new SegmentoCategoriaSgtc();
  	}
  	  	
  	String titulo = "Categorias de Itens Livres";
  	String codSegmento = "";
  	if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.pergFreq"))){
  		titulo = "Categorias de Perguntas Frequentes";
  		codSegmento = Pagina.getParamStr(request, "codSegmento");
  	}
  	
	_disabled = ""; 
	%>

	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codSegmento" value="<%=codSegmento%>">	

	<!-- TITULO --> 
	<%@ include file="/titulo_tela.jsp"%>
 
	<%
	String linkPesquisar = "frm_pes.jsp?codSegmento=" + codSegmento;
	String linkIncluir = "frm_inc.jsp?codSegmento=" + codSegmento;
	%>
	<util:linkstop incluir="<%=linkIncluir%>" pesquisar="<%=linkPesquisar%>"/>

	
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>


	<table class="form"> 
	<%@ include file="form.jsp"%>

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
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>