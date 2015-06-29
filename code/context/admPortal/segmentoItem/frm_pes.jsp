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
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/frmPadraoAdmPortal.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javaScript" src="../../js/html2xhtml.js"></script>
<script language="javaScript" src="../../js/richtext.js"></script>

<script language="javascript">
function validarPesquisar(form){
   	//setHiddenVal('rte');
    //form.integraSgti.value = form.richText.value;
	return(true);
}

function aoSelecionarSubmit(form){
	form.action = "frm_pes.jsp";
	form.submit();
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

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

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codSegmento" value="<%=codSegmento%>">
	<input type="hidden" name="hidRichText" value="<%=hidRichText%>">
	
<%
try	{
	SegmentoItemSgti segItem = new SegmentoItemSgti();
	SegmentoItemDao segItemDao = new SegmentoItemDao(request);
	SegmentoDao segmentoDao = new SegmentoDao(request);
	
	if(session.getAttribute("objPesquisa") != null  && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		segItem = (SegmentoItemSgti) session.getAttribute("objPesquisa");
		session.removeAttribute("objPesquisa");
	}else{
		segItem = new SegmentoItemSgti();
		
		segItemDao.setSegmentoItem(segItem, request, false, application, true);
  	}
	
	/* se o segmento estiver nulo, deve-se instanciar */
	if(segItem.getSegmentoSgt() == null){
		/* Instanciar o segmento, quando n�o for item fixo ir� depender da tela de itens livres */
		if (codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.materias"))).intValue()
					|| codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.taxacoes"))).intValue()
					|| codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.pergFreq"))).intValue()
					|| codSegmento == (Long.valueOf(_msg.getMensagem("admPortal.glossario"))).intValue()){
			segItem.setSegmentoSgt( (SegmentoSgt) segmentoDao.buscar(SegmentoSgt.class, Long.valueOf(codSegmento)));
		}else{
			if(isFormUpload){
				if(!"".equals(FileUpload.verificaValorCampo(campos, "segmentoSgt"))){
					segItem.setSegmentoSgt( (SegmentoSgt) segmentoDao.buscar(SegmentoSgt.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "segmentoSgt"))));
				}
			} else {
				if(!"".equals(Pagina.getParamStr(request, "segmentoSgt"))){
					segItem.setSegmentoSgt( (SegmentoSgt) segmentoDao.buscar(SegmentoSgt.class, Long.valueOf(Pagina.getParamStr(request, "segmentoSgt"))));
				}
			}
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
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

<%
	_disabled = "";
	_obrigatorio = "";
	_comboSimNao = "Todos";	
%>

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

	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>	
	
<%
} catch (ECARException e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
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
