<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.PeriodicidadePrdc" %>
<%@ page import="ecar.dao.PeriodicidadeDao" %>
<%@ include file="../../frm_global.jsp"%>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>

<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">

function validarAlterar(form){
	if(!validaString(form.descricaoPrdc,'Descrição',true)){
		return(false);
	}
	
	if(!validaString(form.numMesesPrdc,'Número de Meses',true)){
		return(false);
	}
	
	if(!validaNum(form.numMesesPrdc, "Número de Meses", false)){
		alert("<%=_msg.getMensagem("periodicidade.validacao.numMesesPrdc.invalido")%>");
		form.numMesesPrdc.focus();
		return(false);
	}
	
	if(form.numMesesPrdc.value < 1){
		alert("<%=_msg.getMensagem("periodicidade.validacao.numMesesPrdc.invalido")%>");
		form.numMesesPrdc.focus();
		return(false);
	} 
	
	return(true);
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
<%	
	try {
		PeriodicidadeDao periodicidadeDao = new PeriodicidadeDao(request);
		PeriodicidadePrdc periodicidade;
		
		if(session.getAttribute("objPeriodicidade") != null){
			periodicidade = (PeriodicidadePrdc) session.getAttribute("objPeriodicidade");
			session.removeAttribute("objPeriodicidade");
		}else{
			periodicidade = (PeriodicidadePrdc) periodicidadeDao.buscar(PeriodicidadePrdc.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
	  	}
		
		_disabled = "";
%>

		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
	
		<input type="hidden" name="codigo" value="<%=periodicidade.getCodPrdc()%>">

		<table class="form">
			<%@ include file="form.jsp"%>
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