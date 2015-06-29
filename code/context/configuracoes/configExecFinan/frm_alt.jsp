<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.ConfigExecFinanCef" %>
<%@ page import="ecar.dao.ConfigExecFinanDao" %>
<%@ include file="../../frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/numero.js"></script>

<script language="javascript">
 
function validarAlterar(form){
	if(!validaString(form.nomeCef,"Nome",true)){
		return(false);
	}
	if(!validaString(form.labelCef,"Label para utilizar nas telas",true)){
		return(false);
	}
	if(!validaString(form.numCaracteresCef,"Número de Posições",true)||!validaNum(form.numCaracteresCef,"Número de Posições",true)){
		return(false);
	}
	if(form.configTipoDadoCtd.value == ""){
		alert("<%=_msg.getMensagem("configExecFinan.validacao.configTipoDadoCtd.obrigatorio")%>");
		form.configTipoDadoCtd.focus();
		return(false);	
	}
	if(form.configSisExecFinanCsefv.value == ""){
		alert("<%=_msg.getMensagem("configExecFinan.validacao.configSisExecFinanCsefv.obrigatorio")%>");
		form.configSisExecFinanCsefv.focus();
		return(false);	
	}
	if(!validaString(form.seqApresentacaoCef, "Seqüência de Apresentação", true)||!validaNum(form.seqApresentacaoCef, "Seqüência de Apresentação", true)){
		return(false);
	}
	return(true);
}
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
			ConfigExecFinanDao configExecFinanDao = new ConfigExecFinanDao(request);
			ConfigExecFinanCef configExecFinan = (ConfigExecFinanCef) configExecFinanDao.buscar(ConfigExecFinanCef.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
			_disabled = "";
			%>
			<input type="hidden" name="codigo" value="<%=configExecFinan.getCodCef()%>">
		
			<table class="form">
			<%@ include file="form.jsp"%>
			</table>
		
			<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
			<%
	}catch( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		/* redireciona para o controle */
			%>
			<script language="javascript">
			document.form.action ="ctrl.jsp";
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
</html>