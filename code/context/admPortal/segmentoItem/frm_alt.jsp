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
<%@ page import="org.apache.commons.fileupload.DiskFileUpload" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
boolean isFormUpload = FileUpload.isMultipartContent(request);
List campos = new ArrayList();
if(isFormUpload){
	campos = FileUpload.criaListaCampos(request); 
}

/* C�digo passado pelo menu, referenciando o form que ser� montado e outras valida��es */
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

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/frmPadraoAdmPortal.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javaScript" src="../../js/html2xhtml.js"></script>
<script language="javaScript" src="../../js/richtext.js"></script>

<script language="javascript">
<%@ include file="validacao.jsp"%>

function aoSelecionarSubmit(form){
	form.action = "frm_alt.jsp";
	form.submit();
}
function ExcluirImagem(imagem) {		
	document.form.hidAcao.value = imagem;
	document.form.action = "ctrl.jsp";
	document.form.submit();
	
}

</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" enctype="multipart/form-data">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="codSegmento" value="<%=codSegmento%>">
	<input type="hidden" name="hidRichText" value="<%=hidRichText%>">	
	
	<%if(isFormUpload){%>
		<input type="hidden" name="hidRegistro" value="<%=FileUpload.verificaValorCampo(campos, "hidRegistro")%>">
	<%}else{%>
		<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamStr(request, "hidRegistro")%>">
	<%}%>

<%
try	{
	SegmentoItemSgti segItem = new SegmentoItemSgti();
	SegmentoItemDao segItemDao = new SegmentoItemDao(request);
	SegmentoDao segmentoDao = new SegmentoDao(request);
	
	if(session.getAttribute("objSegmentoItem") != null){
		segItem = (SegmentoItemSgti) session.getAttribute("objSegmentoItem");
		session.removeAttribute("objSegmentoItem");
	}else{
		segItem = (SegmentoItemSgti) segItemDao.buscar(SegmentoItemSgti.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
		
		if(isFormUpload){
			segItemDao.setSegmentoItemUpload(segItem, campos, application, true);
		}
  	}
	
	String titulo = "";
	if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.materias"))).intValue())
		titulo = "Mat�rias";
	else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.taxacoes"))).intValue())
		titulo = "Na M�dia";
	else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.pergFreq"))).intValue())
		titulo = "Perguntas Freq�entes";
	else if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.glossario"))).intValue())
		titulo = "Gloss�rio";
	else
		titulo = "Itens Livres";
	
	String linkIncluir = "frm_inc.jsp?codSegmento=" + codSegmento;
	String linkPesquisar = "frm_pes.jsp?codSegmento=" + codSegmento + "&hidRichText=" + codSegmento;
%>

	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
	<util:linkstop incluir="<%=linkIncluir%>" pesquisar="<%=linkPesquisar%>"/>
	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

<%
	_disabled = "";
%>
	
	<input type="hidden" name="codigo" value="<%=segItem.getCodSgti()%>">

	<table class="form"> 
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
