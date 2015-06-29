<%@ page import="ecar.pojo.SegmentoCategoriaSgtc" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SegmentoDao" %>
<%@ page import="java.util.List" %>

<%@ page import="ecar.dao.SegmentoCategoriaDao" %>
<%@ page import="comum.util.FileUpload" %>

<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%
SegmentoCategoriaSgtc segmentoCategoria = new SegmentoCategoriaSgtc();
SegmentoCategoriaDao segmentoCategoriaDao = new SegmentoCategoriaDao(request);
_disabled = "disabled";
_readOnly = "readonly";

String tituloCat = "Categorias de Itens Livres";
String codSegmento = "";
if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.pergFreq"))){
	tituloCat = "Categorias de Perguntas Frequentes";
	codSegmento = Pagina.getParamStr(request, "codSegmento");
}
  	
%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="JavaScript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoAdmPortal.js"></script>

<script language="JavaScript">
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="frm_nav.jsp">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
	<%
	String linkPesquisar = "frm_pes.jsp?codSegmento=" + codSegmento;
	String linkIncluir = "frm_inc.jsp?codSegmento=" + codSegmento;
	%>
	<util:linkstop incluir="<%=linkIncluir%>" pesquisar="<%=linkPesquisar%>"/>

<%
	try {		
		
		List lista = (List) session.getAttribute("lista");
		
		int i = 0;
		int hidTotRegistro = lista.size(); //Total de Registros
		int hidRegistro = 0;
		if(!"".equals(Pagina.getParamStr(request, "hidRegistro")))
			hidRegistro = Integer.valueOf(Pagina.getParamStr(request, "hidRegistro")).intValue(); //Registro atual
		
		//Se o último registro for excluído deve apontar para a última posição.
		if (hidRegistro >= hidTotRegistro)
			hidRegistro = hidTotRegistro - 1;
		
		segmentoCategoria = (SegmentoCategoriaSgtc) lista.get(hidRegistro);
		
%>

	<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>

	<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
	<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
	<input type="hidden" name="codigo" value="<%=segmentoCategoria.getCodSgtc()%>">
	<input type="hidden" name="codSegmento" value="<%=codSegmento%>">						
	
	<table class="form">
	<%@ include file="form.jsp"%>
	</table>
	
	<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir"/>

<%
	} catch (Exception ex) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(ex);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
		%>
		<script language="javascript">
		document.form.action = "frm_pes.jsp";
		document.form.submit();
		</script>
		<%
	}
%>
	
	
</form>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>