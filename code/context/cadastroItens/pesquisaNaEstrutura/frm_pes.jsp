<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript">
function aoClicarPesquisar(form){
	if(validar(form)){
		form.action = "frm_nav_est.jsp";
		form.submit();
	}
	
	return false;
}

function validar(form){
	if (Trim(form.palavraChave.value) != ""){
		if (Trim(form.palavraChave.value).length < 3){
			alert("<%=_msg.getMensagem("itemEstrutura.pesquisaEstrutura.validacao.palavraChave.tamanho")%>");
			form.palavraChave.focus();
			return false;
		}	
	} else {
		alert("<%=_msg.getMensagem("itemEstrutura.pesquisaEstrutura.validacao.palavraChave.obrigatorio")%>");
		form.palavraChave.focus();
		return false;
	}
	
	if(!validarCheckSelecionado(form, "estruturaEtt")){
		alert("<%=_msg.getMensagem("itemEstrutura.pesquisaEstrutura.validacao.estruturaEtt.obrigatorio")%>");
		return false;
	}

	return true;
}

/** Select All está normalmente nos frmPadrao.js **/
function selectAll(form, nomeCheckBoxCtrl, nomeCheckBoxDep){
    if (typeof nomeCheckBoxCtrl == "undefined") nomeCheckBoxCtrl = "todos";
    if (typeof nomeCheckBoxDep == "undefined") nomeCheckBoxDep = "excluir";

	// Função para selecionar todos os Checkbox
	var numChecks = verificaChecks(form, nomeCheckBoxDep);

	if(numChecks > 1){
		for(i = 0; i < eval('form.' + nomeCheckBoxDep + '.length'); i++)
			eval('form.' + nomeCheckBoxDep + '[i]').checked = eval('form.' + nomeCheckBoxCtrl).checked;	
	}
	
	if(numChecks == 1)
		eval('form.' + nomeCheckBoxDep).checked = eval('form.' + nomeCheckBoxCtrl).checked;	
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body>
<div id="conteudo">

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<%
try{
	EstruturaDao estruturaDao = new EstruturaDao(request);
%>

<form name="form" method="post" onsubmit="javascript:aoClicarPesquisar(form)">

<table class="form">
	<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>
	<!--tr><td colspan=2>&nbsp;</td></tr-->
	<tr> 
		<td class="label">* Palavra-chave</td>
		<td><input type="text" name="palavraChave" size="55" maxlength="50" value="">
		<!--&nbsp;<input type="button" name="btPesquisa" value="Pesquisar" onclick="aoClicarPesquisar(form);">-->
		</td>
	</tr>
	<tr> 
		<td class="label">* Tipo de pesquisa</td>
		<td>
			<input type="radio" class="form_check_radio" name="tipoPesquisa" value="B" checked> Básica
			<input type="radio" class="form_check_radio" name="tipoPesquisa" value="A"> Avançada
		</td>
	</tr>
	<!--tr><td colspan=2>&nbsp;</td></tr-->
	<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>
	<!--tr><td colspan=2>&nbsp;</td></tr-->
	<tr> 
		<td>&nbsp;</td>
		<td>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="checkbox" class="form_check_radio" name="estTodos" value="todos" onclick="javascript:selectAll(document.form, 'estTodos', 'estruturaEtt')"> <b>Todos</b>
		</td>
	</tr>
<%
	//List lista = estruturaDao.getEstruturas();
	List lista = estruturaDao.getListaEstruturas();
	Iterator it = lista.iterator();
	
	while(it.hasNext()){
		EstruturaEtt estrutura = (EstruturaEtt) it.next();
%>
		<tr> 
			<td>&nbsp;</td>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="checkbox" class="form_check_radio" name="estruturaEtt" value="<%=estrutura.getCodEtt()%>"> <%=estrutura.getNomeEtt()%>
			</td>
		</tr>
<%
	}
%>
	<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>
	<tr> 
		<td colspan=2 align=center>
			<input type="button" name="btPesquisa" value="Pesquisar" onclick="aoClicarPesquisar(form);">
		</td>
	</tr>
	<tr><td class="espacador" colspan=2><img src="../../images/pixel.gif"></td></tr>
</table>

<%@ include file="../../include/estadoMenu.jsp"%>
</form>

<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

</div> <!-- conteudo -->
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>

