<%@ page import="ecar.pojo.SegmentoCategoriaSgtc" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SegmentoCategoriaDao" %>
<%@ page import="ecar.dao.SegmentoDao" %>

<%@ page import="java.util.List" %>
<%@ page import="comum.util.FileUpload" %>

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
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript">
<%@ include file="validacao.jsp"%>
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

	<%
	try{
		
		SegmentoCategoriaDao segmentoCategoriaDao = new SegmentoCategoriaDao(request);
		SegmentoCategoriaSgtc segmentoCategoria;
		
		if(session.getAttribute("objSegmentoCategoria") != null){
			segmentoCategoria = (SegmentoCategoriaSgtc) session.getAttribute("objSegmentoCategoria");
			session.removeAttribute("objSegmentoCategoria");
		}else{
			segmentoCategoria = (SegmentoCategoriaSgtc) segmentoCategoriaDao.buscar(SegmentoCategoriaSgtc.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
	  	}
	  	
	  	String titulo = "Categorias de Itens Livres";
	  	String codSegmento = "";
	  	if(Pagina.getParamStr(request, "codSegmento").equals(_msg.getMensagem("admPortal.pergFreq"))){
	  		titulo = "Categorias de Perguntas Frequentes";
	  		codSegmento = Pagina.getParamStr(request, "codSegmento");
	  	}
  	
		
		_disabled = "";
%>

	<form name="form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="hidAcao" value="alterar">
		<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamStr(request, "hidRegistro")%>">
		<input type="hidden" name="codSegmento" value="<%=codSegmento%>">					
	
		<!-- TITULO -->
		<%@ include file="/titulo_tela.jsp"%>
	
		<%
		String linkPesquisar = "frm_pes.jsp?codSegmento=" + codSegmento;
		String linkIncluir = "frm_inc.jsp?codSegmento=" + codSegmento;
		%>
		<util:linkstop incluir="<%=linkIncluir%>" pesquisar="<%=linkPesquisar%>"/>

		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

		<input type="hidden" name="codigo" value="<%=segmentoCategoria.getCodSgtc()%>">
	
		<table class="form"> 
		<%@ include file="form.jsp"%>
		</table>
	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
<%	
	}catch(ECARException e){
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