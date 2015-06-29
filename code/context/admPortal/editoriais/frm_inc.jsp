<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SegmentoDao" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.SegmentoSgt" %>

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

<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{
%>
<div id="conteudo"> 
 
<form name="form" method="post">
	 
	<%
	SisAtributoSatb editoria = new SisAtributoSatb();
	SisAtributoDao editoriaDao = new SisAtributoDao(request);
	
	if(session.getAttribute("objEditoria") != null){
		editoria = (SisAtributoSatb) session.getAttribute("objEditoria");
		session.removeAttribute("objEditoria");
	}else{
		editoria = new SisAtributoSatb();
  	}
  	  	
  	String titulo = "Editorias de Itens Livres";
  	String codSegmento = "";

  	if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.materias"))){
  		titulo = "Editorias de Matérias";
  		codSegmento = Pagina.getParamStr(request, "codSegmento");
  	}
  	if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.taxacoes"))){
  		titulo = "Editorias de imagens da mídia";
  		codSegmento = Pagina.getParamStr(request, "codSegmento");
  	}

  	if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.pergFreq"))){
  		titulo = "Editorias de Perguntas Frequentes";
  		codSegmento = Pagina.getParamStr(request, "codSegmento");
  	}
  	if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.glossario"))){
  		titulo = "Editorias de Glossário";
  		codSegmento = Pagina.getParamStr(request, "codSegmento");
  	}
  	
  	SegmentoSgt segmento = new SegmentoSgt();

  	boolean podeIncluir = false;  	
  	if(!"".equals(codSegmento))
	  	segmento = (SegmentoSgt) new SegmentoDao(request).buscar(SegmentoSgt.class, Long.valueOf(codSegmento));
	else
		podeIncluir = true;

  	if(segmento != null && segmento.getSisGrupoAtributoSga() != null){
  		podeIncluir = true;
  	}
  	
	_disabled = ""; 
	%>

	<input type="hidden" name="hidAcao" value="">

	<!-- TITULO --> 
	<%@ include file="/titulo_tela.jsp"%>
 
 	<%if(podeIncluir){%>
	 
		<%
		String linkPesquisar = "frm_pes.jsp?codSegmento=" + codSegmento;
		String linkIncluir = "frm_inc.jsp?codSegmento=" + codSegmento;
		%>
		<util:linkstop incluir="<%=linkIncluir%>" pesquisar="<%=linkPesquisar%>"/>	
		<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>	 
		<table class="form"> 	
		<%@ include file="form.jsp"%>
		</table>	
		<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	<%} else {
		%>
		<table class="form">
		<%@ include file="formSemGrupoAtributo.jsp"%>		
		</table>				
		<%
	}%>
	
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
