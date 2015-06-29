<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.SegmentoDao" %>
<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.pojo.SegmentoSgt" %>
<%@ page import="ecar.pojo.SegmentoItemSgti" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="comum.util.FileUpload" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="JavaScript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoAdmPortal.js"></script>
<script language="javaScript" src="../../js/html2xhtml.js"></script>
<script language="javaScript" src="../../js/richtext.js"></script>

</head>

<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<%
boolean isFormUpload = FileUpload.isMultipartContent(request);
List campos = new ArrayList();
if(isFormUpload){
	campos = FileUpload.criaListaCampos(request); 
}

/* Código passado pelo menu, referenciando o form que será montado e outras validações */
int codSegmento;
if(isFormUpload)
	codSegmento = (Long.valueOf(FileUpload.verificaValorCampo(campos, "codSegmento"))).intValue();
else
	codSegmento = Pagina.getParamInt(request, "codSegmento");
	
int hidRichText;
if(isFormUpload)
	hidRichText = (Long.valueOf(FileUpload.verificaValorCampo(campos, "hidRichText"))).intValue();
else
	hidRichText = Pagina.getParamInt(request, "hidRichText");
%>

<div id="conteudo">

<form name="form" method="post" action="frm_nav.jsp">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codSegmento" value="<%=codSegmento%>">
	<input type="hidden" name="hidRichText" value="<%=hidRichText%>">
		
<%
try	{
	SegmentoItemSgti segItem = new SegmentoItemSgti();
	SegmentoItemDao segItemDao = new SegmentoItemDao(request);
	SegmentoDao segmentoDao = new SegmentoDao(request);
	
	String titulo = "";
	if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.materias"))).intValue())
		titulo = "Matérias";
	else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.taxacoes"))).intValue())
		titulo = "Na Mídia";
	else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.pergFreq"))).intValue())
		titulo = "Perguntas Freqüentes";
	else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.glossario"))).intValue())
		titulo = "Glossário";
	else
		titulo = "Itens Livres";
	
	String linkIncluir = "frm_inc.jsp?codSegmento=" + codSegmento;
	String linkPesquisar = "frm_pes.jsp?codSegmento=" + codSegmento + "&hidRichText=" + codSegmento;
	
	_disabled = "disabled";
	_readOnly = "readonly";
%>

	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
	<util:linkstop incluir="<%=linkIncluir%>" pesquisar="<%=linkPesquisar%>"/>

<%
	List lista = (List) session.getAttribute("lista");
	
	int i = 0;
	int hidTotRegistro = lista.size(); //Total de Registros
	int hidRegistro = 0;
	if(isFormUpload){
		if(!"".equals(FileUpload.verificaValorCampo(campos, "hidRegistro")))
			hidRegistro = Integer.valueOf(FileUpload.verificaValorCampo(campos, "hidRegistro")).intValue(); //Registro atual
	}else{
		if(!"".equals(Pagina.getParamStr(request, "hidRegistro")))
			hidRegistro = Integer.valueOf(Pagina.getParamStr(request, "hidRegistro")).intValue(); //Registro atual
	}
	
	//Se o último registro for excluído deve apontar para a última posição.
	if (hidRegistro >= hidTotRegistro)
		hidRegistro = hidTotRegistro - 1;
	
	segItem = (SegmentoItemSgti) lista.get(hidRegistro);
%>

	<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>

	<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
	<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
	<input type="hidden" name="codigo" value="<%=segItem.getCodSgti()%>">

	<table class="form" >
		
	<%if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.materias"))).intValue()){%>
		<%@ include file="form_materias.jsp"%>
	<%}else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.taxacoes"))).intValue()){%>
		<%@ include file="form_taxacoes.jsp"%>
	<%}else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.pergFreq"))).intValue()){%>
		<%@ include file="form_pergFreq.jsp"%>
	<%}else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.glossario"))).intValue()){%>
		<%@ include file="form_glossario.jsp"%>
	<%}else{%>
		<%@ include file="form_itensLivres.jsp"%>
	<%}%>
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
