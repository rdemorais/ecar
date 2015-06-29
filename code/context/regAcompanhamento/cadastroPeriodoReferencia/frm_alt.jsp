<%@ page import="org.apache.log4j.Logger"%>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoRegAcompanhamento.js" type="text/javascript"></script>

<script language="javascript" src="<%=_pathEcar%>/js/datas.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<%@ include file="validacao.jsp"%>
</head>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<body onload="focoInicial(document.form);">
<div id="conteudo">

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<form  name="form" method="post" action="frm_alt.jsp">

<%
try {
	boolean inserirPeriodo = false;
	String alt_disabled = "disabled";
%>
	<util:barrabotoes alterar="Continua" voltar="Cancelar"/>
	<%@ include file="form.jsp"%> 
	<util:barrabotoes alterar="Continua" voltar="Cancelar"/>
<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
%>
	<script type="text/javascript">
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
</form>

<br>
</div>
</body>

<%@ include file="/include/mensagemAlert.jsp" %>
</html>

<script type="text/javascript">
document.form.orgaoOrg.disabled = true;
document.form.anoAref.disabled = true;
document.form.mesAref.disabled = true;
document.form.diaAref.disabled = true;
if(document.form.checkItensOrgaoInformado != null)
	document.form.checkItensOrgaoInformado.disabled = true;
</script>
