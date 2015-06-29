<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ page import="ecar.pojo.PeriodoRevisaoPrev" %>
<%@ page import="ecar.dao.PeriodoRevisaoPrevDao" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/datas.js"></script>

<script>
<%@ include file="validacao.jsp"%>

function aoClicarVoltar(form){
	form.action = "lista.jsp";
	form.submit();	
}
</script>
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body onload="focoInicial(document.form);">
<div id="conteudo">

<%
try{
	PeriodoRevisaoPrevDao prevDao = new PeriodoRevisaoPrevDao(request);
	PeriodoRevisaoPrev prev = new PeriodoRevisaoPrev();
%>
				
<br><br>
<%@ include file="/titulo_tela.jsp"%>

<form  name="form" method="post" >

	  <util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
	<table name="form" class="form">
	<%@ include file="form.jsp"%>
	</table>

	  <util:barrabotoes incluir="Gravar" voltar="Cancelar"/>


</form>
<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
	%>
	<script>
	document.form.action = "lista.jsp";
	document.submit();
	</script>
	<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>
</div>
</body>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>