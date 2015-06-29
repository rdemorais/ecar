<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.EmpresaEmp" %>
<%@ page import="ecar.dao.EmpresaDao" %>

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
<script language="javascript">

function aoClicarCancelar(form){
	form.action = "frm_inc.jsp";
	form.submit();
}

function validarAlterar(form){
	if(!validaString(form.razaoSocialEmp,'Razão Social', true)){
		return(false);
	}
	
	if(!validaString(form.siglaEmp,'Sigla',true)){
		return(false);
	}
	
	if(Trim(form.cnpjCpfEmp.value) != ""){
		var valor = Trim(form.cnpjCpfEmp.value);
		
		if(valor.length == 14){ //CNPJ
			if(!validaCNPJ(form.cnpjCpfEmp, false, true, true)){
				alert("<%=_msg.getMensagem("empresa.validacao.cnpjCpfEmp.invalido")%>");
				return(false);
			}
		}
		if(valor.length == 11){ //CPF
			if(!validaCPF(form.cnpjCpfEmp, false, true, true)){
				alert("<%=_msg.getMensagem("empresa.validacao.cnpjCpfEmp.invalido")%>");
				return(false);
			}
		}
		if(valor.length != 14 && valor.length != 11){
			alert("<%=_msg.getMensagem("empresa.validacao.cnpjCpfEmp.invalido")%>");
			form.cnpjCpfEmp.focus();
			return(false);
		}
	}
	
	if(Trim(form.cepEmp.value) != ""){
		if(!validaCEP(form.cepEmp, false, true, false)){
			alert("<%=_msg.getMensagem("empresa.validacao.cepEmp.invalido")%>");
			return(false);
		}
	}
	
	if(Trim(form.ddd1Emp.value) != ""){
		if(!validaNum(form.ddd1Emp, 'DDD do Telefone 1', false)){
			alert("<%=_msg.getMensagem("empresa.validacao.ddd1Emp.invalido")%>");
			form.ddd1Emp.focus();
			return(false);
		}
	}
	
	if(Trim(form.telefone1Emp.value) != ""){
		if(!validaNum(form.telefone1Emp, 'Telefone 1', false)){
			alert("<%=_msg.getMensagem("empresa.validacao.telefone1Emp.invalido")%>");
			form.telefone1Emp.focus();
			return(false);
		}
	}
	
	if(Trim(form.ddd2Emp.value) != ""){
		if(!validaNum(form.ddd2Emp, 'DDD do Telefone 2', false)){
			alert("<%=_msg.getMensagem("empresa.validacao.ddd2Emp.invalido")%>");
			form.ddd2Emp.focus();
			return(false);
		}
	}
	
	if(Trim(form.telefone2Emp.value) != ""){
		if(!validaNum(form.telefone2Emp, 'Telefone 2', false)){
			alert("<%=_msg.getMensagem("empresa.validacao.telefone2Emp.invalido")%>");
			form.telefone2Emp.focus();
			return(false);
		}
	}
	
	if(Trim(form.dddFaxEmp.value) != ""){
		if(!validaNum(form.dddFaxEmp, 'DDD do Fax', false)){
			alert("<%=_msg.getMensagem("empresa.validacao.dddFaxEmp.invalido")%>");
			form.dddFaxEmp.focus();
			return(false);
		}
	}
	
	if(Trim(form.faxEmp.value) != ""){
		if(!validaNum(form.faxEmp, 'Fax', false)){
			alert("<%=_msg.getMensagem("empresa.validacao.faxEmp.invalido")%>");
			form.faxEmp.focus();
			return(false);
		}
	}
	
	if(!validaString(form.emailContatoEmp,'E-mail Contato',true)){
		return(false);
	} else {
		if(!validaEmail(Trim(form.emailContatoEmp.value))){
			alert("<%=_msg.getMensagem("empresa.validacao.emailContatoEmp.invalido")%>");
			form.emailContatoEmp.focus();
			return(false);
		}
	}
	
	if(!validaString(form.emailErrosEmp,'E-mail Erros',true)){
		return(false);
	} else {
		if(!validaEmail(Trim(form.emailErrosEmp.value))){
			alert("<%=_msg.getMensagem("empresa.validacao.emailErrosEmp.invalido")%>");
			form.emailErrosEmp.focus();
			return(false);
		}
	}
	
//	if(Trim(form.hidlogotipoEmp.value) == ""){
//		if(Trim(form.logotipoEmp.value) == ""){
//			alert("%=_msg.getMensagem("empresa.validacao.logotipoEmp.obrigatorio")%>");
//			form.logotipoEmp.focus();
//			return(false);
//		}
//	}
//	
//	if(Trim(form.hidlogotipoEmailEmp.value) == ""){
//		if(Trim(form.logotipoEmailEmp.value) == ""){
//			alert("%=_msg.getMensagem("empresa.validacao.logotipoEmailEmp.obrigatorio")%>");
//			form.logotipoEmailEmp.focus();
//			return(false);
//		}
//	}
//	
//	if(Trim(form.hidlogotipoRelatorioEmp.value) == ""){
//		if(Trim(form.logotipoRelatorioEmp.value) == ""){
//			alert("%=_msg.getMensagem("empresa.validacao.logotipoRelatorioEmp.obrigatorio")%>");
//			form.logotipoRelatorioEmp.focus();
//			return(false);
//		}
//	}
	
	return(true);
}

function ExcluirImagem(imagem) {		
	document.form.hidAcao.value = imagem;
	document.form.action = "ctrl.jsp";
	document.form.submit();
	
}




/*
function aoClicarAlterar(form){

	if (form.logotipoEmp.value != null && form.logotipoEmp.value != ""){
		form.hidlogotipoEmp.value = form.logotipoEmp.value;
	}
	if (form.logotipoEmailEmp.value != null && form.logotipoEmailEmp.value != ""){
		form.hidlogotipoEmailEmp.value = form.logotipoEmailEmp.value;
	}
	if (form.logotipoRelatorioEmp.value != null && form.logotipoRelatorioEmp.value != ""){
		form.hidlogotipoRelatorioEmp.value = form.logotipoRelatorioEmp.value;
	}
	
	if(validarAlterar(form)){
		form.hidAcao.value = "alterar";
		form.action = "ctrl.jsp";
		form.submit();
	}
}
*/


</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body >

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" enctype="multipart/form-data">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
<%	try {

		EmpresaDao empresaDao = new EmpresaDao(request);
		//EmpresaEmp empresa = (EmpresaEmp) empresaDao.buscar(EmpresaEmp.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
		
		List confg = empresaDao.listar(EmpresaEmp.class, null);
		EmpresaEmp empresa = new EmpresaEmp();
		if(confg != null && confg.size() > 0){
			empresa = (EmpresaEmp) confg.iterator().next();
		}
		
		_disabled = "";
		
%>
		<util:barrabotoes alterar="Gravar" cancelar="Cancelar"/>

		<input type="hidden" name="codigo" value="<%=empresa.getCodEmp()%>">

		<table class="form">
			<%@ include file="form.jsp"%>
		</table>
	
		<util:barrabotoes alterar="Gravar" cancelar="Cancelar"/>

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