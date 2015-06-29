<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.ConfigSisExecFinanCsefv" %>
<%@ page import="ecar.dao.ConfigSisExecFinanCsefvDao" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>

<script language="javascript" src="../../js/cnpj.js"></script>
<script language="javascript" src="../../js/cpf.js"></script>
<script language="javascript" src="../../js/cep.js"></script>
<script language="javascript" src="../../js/email.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/datas.js"></script>


<script language="javascript">

function validarPesquisar(form){
		
	if (Trim(form.mesVersaoCsefv.value) != "" && validarMesAno(form.mesVersaoCsefv.value) == false){
		alert("<%=_msg.getMensagem("integracaoFinanceira.exportarArquivo.mesAnoIni.invalido")%>");
		form.mesVersaoCsefv.focus();
		return false;
	}
		
	return(true);
	
}

function validarMesAno(mesAno){
	var x = mesAno.split("/");
	
	/* É necessário usar o segundo parâmetro (10) no parseInt() para converter para decimal, 
	pois quando o mes vem com o zero na frente do numero (01,02,03...) o sistema pensa que é um número 
	octal e para o números 08 ele converte zero. */
	
	var mes = parseInt(x[0],10);
	var ano = parseInt(x[1],10);

	if(mesAno.length < 7){
		return false;
	}
	if(mes < 1 || mes > 12){
		return false;
	}
	if(isNumeric(ano) == false){
		return false;
	}
	else{
		if(ano < 1950 || ano > 2150){
			return false;
		}
	}
	return true;
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

<% 

		ConfigSisExecFinanCsefv consefv = new ConfigSisExecFinanCsefv();
		ConfigSisExecFinanCsefvDao consefvDao = new ConfigSisExecFinanCsefvDao(request);
		
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		/* deve recuperar o que foi digitado se o objeto existe na sessao e o resultado da pesquisa foi falso */
		consefv = (ConfigSisExecFinanCsefv) session.getAttribute("objPesquisa");
		session.removeAttribute("objPesquisa");
	}else{
		consefv = (ConfigSisExecFinanCsefv) session.getAttribute("objPesquisa");
		consefv = new ConfigSisExecFinanCsefv();
  	}
	_disabled = "";
	_obrigatorio = "";	
	request.setAttribute("pesquisar","true");	
	
%>

	<table class="form">
	<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>	
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
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>