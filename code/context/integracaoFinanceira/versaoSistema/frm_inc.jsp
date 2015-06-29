<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.ConfigSisExecFinanCsefv" %>
<%@ page import="ecar.dao.ConfigSisExecFinanCsefvDao" %>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@ include file="../../frm_global.jsp"%>

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

function validarGravar(form){
	
	if(!validaString(form.configSisExecFinanCsef,'Sistema', true)){
		return(false);
	}
	
	if(!validaString(form.versaoCsefv,'Versão', true)){
		return(false);
	}

	if(!validaString(form.mesVersaoCsefv,'Mês/Ano', true)){
		return(false);
	}
	
	if (validarMesAno(form.mesVersaoCsefv.value) == false){
		alert("<%=_msg.getMensagem("integracaoFinanceira.exportarArquivo.mesAnoIni.invalido")%>");
		form.mesVersaoCsefv.focus();
		return false;
	}
	
	if(!validaString(form.ativo,'Ativo', true)){
		return(false);
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

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="incluir">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
<%	try {

		ConfigSisExecFinanCsefvDao consefvDao = new ConfigSisExecFinanCsefvDao(request);
				
		List confg = consefvDao.listar(ConfigSisExecFinanCsefv.class, null);
		ConfigSisExecFinanCsefv consefv = new ConfigSisExecFinanCsefv();
		
		if(confg != null && confg.size() > 0){
			consefv = (ConfigSisExecFinanCsefv) confg.iterator().next();
		}
		
		_disabled = "";
		
%>
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

		<input type="hidden" name="codigo" value="<%=consefv.getCodCsefv()%>">

		<table class="form">
			<%@ include file="form.jsp"%>
		</table>
	
		<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

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
<%@ include file="../../include/mensagemAlert.jsp"%>
</body>
</html>