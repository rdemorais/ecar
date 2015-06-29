<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.dao.EntidadeDao" %>
<%@ include file="../../frm_global.jsp"%>
<%@ include file="funcoes.jsp"%>

<%
session.removeAttribute("objPesquisa");
%>
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>

<script language="javascript">
/*
 * Seleciona os dados da janela de pesquisa
 * E como uma interface que deve ser implementada para receber dados de uma janela de pesquisa
 * Sempre deve receber um código e uma descricao
 */
function getDadosPesquisa(codigo, descricao){
	document.form.codEnt.value = codigo;
	document.form.nomeEnt.value = descricao;
	
	reload(document.form);
}

function aoClicarCancelar(){
	document.form.codEnt.value = "";
	document.form.nomeEnt.value = "";
	
	reload(document.form);
}

function validarGravar(form){ 
	if(form.codEnt.value == ""){
		alert("<%=_msg.getMensagem("entidade.hierarquia.validacao.codEnt.obrigatorio")%>");
		return(false);		
	}
	return(true);
}

function reload(form){
	form.action = "frm_inc.jsp";
	form.submit();
}


function selectAllPai(form){
	// Função para selecionar todos os Checkbox
	var numChecks = verificaChecks(form, "entidadeEntPai");

	if(numChecks > 1){
		if(form.todosPai.checked){
			for(i = 0; i < form.entidadeEntPai.length; i++)
				form.entidadeEntPai[i].checked = true;
		}else{
			for(i = 0; i < form.entidadeEntPai.length; i++)
				form.entidadeEntPai[i].checked = false;
		}
	}
	
	if(numChecks == 1){
		if(form.todosPai.checked){
			form.entidadeEntPai.checked = true;
		}else{
			form.entidadeEntPai.checked = false;
		}
	}
}

function verificaTodosPai(form){
	// Verifica se todos os checkbox estão selecionados para marcar a caixa todos
	var checkTodos = true;
	var numChecks = verificaChecks(form, "entidadeEntPai");
	
	if(numChecks > 1){
		for(i = 0; i < form.entidadeEntPai.length; i++){
			if(form.entidadeEntPai[i].checked == false){
				checkTodos = false;
			}		
		}
	}
	
	if(numChecks == 1){
		if(form.entidadeEntPai.checked == false)
			checkTodos = false;
	}
	
	if(checkTodos){
		form.todosPai.checked = true;
	}
}


function selectAllFilhos(form){
	// Função para selecionar todos os Checkbox
	var numChecks = verificaChecks(form, "entidadeEntFilho");

	if(numChecks > 1){
		if(form.todosFilhos.checked){
			for(i = 0; i < form.entidadeEntFilho.length; i++)
				form.entidadeEntFilho[i].checked = true;
		}else{
			for(i = 0; i < form.entidadeEntFilho.length; i++)
				form.entidadeEntFilho[i].checked = false;
		}
	}
	
	if(numChecks == 1){
		if(form.todosFilhos.checked){
			form.entidadeEntFilho.checked = true;
		}else{
			form.entidadeEntFilho.checked = false;
		}
	}
}

function verificaTodosFilhos(form){
	// Verifica se todos os checkbox estão selecionados para marcar a caixa todos
	var checkTodos = true;
	var numChecks = verificaChecks(form, "entidadeEntFilho");
	
	if(numChecks > 1){
		for(i = 0; i < form.entidadeEntFilho.length; i++){
			if(form.entidadeEntFilho[i].checked == false){
				checkTodos = false;
			}		
		}
	}
	
	if(numChecks == 1){
		if(form.entidadeEntFilho.checked == false)
			checkTodos = false;
	}
	
	if(checkTodos){
		form.todosFilhos.checked = true;
	}
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<body onload="javascript:verificaTodosPai(document.forms[0]); javascript:verificaTodosFilhos(document.forms[0])">

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	 
	<input type="hidden" id="contLEntidade" name="contEntidade" value="0">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

<%
	try {
		EntidadeDao entidadeDao = new EntidadeDao(request);
		
		List pais = new ArrayList();
		List paisExcluidos = new ArrayList();
		List filhos = new ArrayList();
		List filhosExcluidos = new ArrayList();
		
		String codEnt = "";
		String nomeEnt = "";
		
		if(!"".equals(Pagina.getParamStr(request, "codEnt"))){
			EntidadeEnt entidade = (EntidadeEnt) entidadeDao.buscar(EntidadeEnt.class, Long.valueOf( Pagina.getParamStr(request, "codEnt") ) );
	    	nomeEnt = entidade.getNomeEnt();
	    	codEnt = Pagina.getParamStr(request, "codEnt");
	    	
	    	paisExcluidos.add(entidade);
	    	filhosExcluidos.add(entidade);
	    	
	    	pais = entidadeDao.getIdPais(entidade);
	    	filhos = entidadeDao.getIdFilhos(entidade);
		}
%>

		<table class="form"> 
			<tr><td class="label">&nbsp;</td></tr>
			<tr>
				<td colspan=2>
					<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
				</td>
			</tr>
			<tr>
				<td class="label">* Entidade</td>
				<td>
					<input type="hidden" name="codEnt" id="codEnt" value="<%=codEnt%>">
					<input type="text" name="nomeEnt" size="55" value="<%=nomeEnt%>" disabled>
					<input type="button" class="botao" name="pesquisarEnt" value="Pesquisar" onclick="popup_pesquisa('ecar.popup.PopUpEntidade')" <%=_disabled%>>
				</td>
			</tr>
			<tr>
				<td width="20%" class="label" valign="top">Entidades Vinculadas (Pai)</td>
				<td>
					<!-- <input type="checkbox" class="form_check_radio" name="todosPai" onclick="selectAllPai(document.forms[0])"> Todos	<br><br> 
					-->
					<br>
				<%
					if(!"".equals(Pagina.getParamStr(request, "codEnt"))){
				%>
										
					<a href="#" onclick="popup_pesquisa_pai('ecar.popup.PopUpEntidade')"> Adicionar Novo Pai </a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<!-- 
					<a href="#" onClick="removeEntidadePai()"> Excluir </a>
					-->						
					<br><br>	 
					<!-- 	
						<combo:CheckListTag 
								nome="entidadeEntPai"
								objeto="ecar.pojo.EntidadeEnt" 
								label="nomeEnt" 
								value="codEnt" 
								objetosExcluidos="<%=paisExcluidos%>"
								selected="<%=pais%>"
								filters="indAtivoEnt=S"
								order="nomeEnt"
						/>
					-->	
					
				<%	
						Iterator it = pais.iterator();
						while (it.hasNext()) {
							//EntidadeEnt paiEnt = (EntidadeEnt) it.next();
							EntidadeEnt paiEnt = (EntidadeEnt) entidadeDao.buscar(EntidadeEnt.class, Long.valueOf(it.next().toString()));
				%>
						<input type="checkbox" class="form_check_radio" name="entidadeEntPai" value="<%=paiEnt.getCodEnt()%>" checked> <%=paiEnt.getNomeEnt()%>	<br>		
				<%		
						}
				
					} else {
				%>
						Selecione a Entidade.
				<%
					}
				%>
				</td>
			</tr>
			<tr>
				<td id="pai" colspan="2"> </td>
			</tr>
			<tr><td class="label">&nbsp;</td></tr>
			<tr>
				<td width="20%" class="label" valign="top">Entidades Vinculadas (Filhos)</td>
				<td>
					<!-- 
					<input type="checkbox" class="form_check_radio" name="todosFilhos" onclick="selectAllFilhos(document.forms[0])"> Todos	<br><br>
					-->
					<br>
				<%
					if(!"".equals(Pagina.getParamStr(request, "codEnt"))){
				%>
					<a href="#" onclick="popup_pesquisa_filho('ecar.popup.PopUpEntidade')"> Adicionar Novo Filho </a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<!-- 
					<a href="#" onClick="removeEntidadeFilho()"> Excluir </a>
					-->
				<br><br>
				<%	
						Iterator it_filhos = filhos.iterator();
						while (it_filhos.hasNext()) {
							//EntidadeEnt paiEnt = (EntidadeEnt) it.next();
							EntidadeEnt filhoEnt = (EntidadeEnt) entidadeDao.buscar(EntidadeEnt.class, Long.valueOf(it_filhos.next().toString()));
				%>
						<input type="checkbox" class="form_check_radio" name="entidadeEntFilho" value="<%=filhoEnt.getCodEnt()%>" checked> <%=filhoEnt.getNomeEnt()%>	<br>		
				
				
				<%
						}
						
					} else {
				%>
						Selecione a Entidade.
				<%
					}
				%>
				</td>
				<tr>
				<td id="filho" colspan="2"> </td>
			</tr>
			</tr>	
			<tr><td class="label">&nbsp;</td></tr>
		</table> 

<%
	} catch(ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
%>
		<script language="javascript">
			document.form.action = "frm_inc.jsp";
			document.form.submit();
		</script>
<%
	} catch (Exception e){
%>
		<%@ include file="/excecao.jsp"%>
<%
	}
%>

	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	
	<%@ include file="../../include/estadoMenu.jsp"%>
</form>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>