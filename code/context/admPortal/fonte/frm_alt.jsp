<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ page import="ecar.pojo.SegmentoItemFonteSgtif" %>
<%@ page import="ecar.dao.SegmentoItemFonteDao" %>

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

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

	<%
	try{
		SegmentoItemFonteDao fonteDao = new SegmentoItemFonteDao(request);
		SegmentoItemFonteSgtif fonte;
		
		if(session.getAttribute("objFonte") != null){
			fonte = (SegmentoItemFonteSgtif) session.getAttribute("objFonte");
			session.removeAttribute("objFonte");
		}else{
			fonte = (SegmentoItemFonteSgtif) fonteDao.buscar(SegmentoItemFonteSgtif.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
	  	}
		
		_disabled = "";
%>
		<input type="hidden" name="codigo" value="<%=fonte.getCodSgtif()%>">
	
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