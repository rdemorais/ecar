<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SegmentoSgt" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SegmentoDao" %>
<%@ page import="java.util.List" %>

<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%
SisAtributoSatb editoria = new SisAtributoSatb();
SisAtributoDao editoriaDao = new SisAtributoDao(request);
_disabled = "disabled";
_readOnly = "readonly";

String tituloEd = "Editorias de Itens Livres";
String codSegmento = "";

if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.materias"))){
		tituloEd = "Editorias de Mat�rias";
		codSegmento = Pagina.getParamStr(request, "codSegmento");
}
if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.taxacoes"))){
  		tituloEd = "Editorias de imagens da m�dia";
  		codSegmento = Pagina.getParamStr(request, "codSegmento");
}

if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.pergFreq"))){
  	tituloEd = "Editorias de Perguntas Frequentes";
  	codSegmento = Pagina.getParamStr(request, "codSegmento");
}
if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.glossario"))){
  	tituloEd = "Editorias de Gloss�rio";
  	codSegmento = Pagina.getParamStr(request, "codSegmento");
}

codSegmento = Pagina.getParamStr(request, "codSegmento");
SegmentoSgt segmento = new SegmentoSgt();
if(!"".equals(codSegmento))
	segmento = (SegmentoSgt) new SegmentoDao(request).buscar(SegmentoSgt.class, Long.valueOf(codSegmento));
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
		
		//Se o �ltimo registro for exclu�do deve apontar para a �ltima posi��o.
		if (hidRegistro >= hidTotRegistro)
			hidRegistro = hidTotRegistro - 1;
		
		editoria = (SisAtributoSatb) lista.get(hidRegistro);
		
%>

	<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>

	<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
	<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
	<input type="hidden" name="codigo" value="<%=editoria.getCodSatb()%>">
	
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
