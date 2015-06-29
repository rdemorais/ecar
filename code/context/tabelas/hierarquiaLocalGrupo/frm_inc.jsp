<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ecar.pojo.LocalGrupoLgp" %>
<%@ page import="ecar.dao.LocalGrupoDao" %>
<%@ include file="../../frm_global.jsp"%>

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

<script language="javascript">
function selectAll(form){
	// Função para selecionar todos os Checkbox
	var numChecks = verificaChecks(form, "localGrupoLgpFilho");

	if(numChecks > 1){
		if(form.todos.checked){
			for(i = 0; i < form.localGrupoLgpFilho.length; i++)
				form.localGrupoLgpFilho[i].checked = true;	
		}else{
			for(i = 0; i < form.localGrupoLgpFilho.length; i++)
				form.localGrupoLgpFilho[i].checked = false;		
		}
	}
	
	if(numChecks == 1){
		if(form.todos.checked)
			form.localGrupoLgpFilho.checked = true;	
		else
			form.localGrupoLgpFilho.checked = false;		
	}
}
function verificaTodos(form){
	// Verifica se todos os checkbox estão selecionados para marcar a caixa todos
		var checkTodos = true;
		var numChecks = verificaChecks(form, "localGrupoLgpFilho");
		
		if(numChecks > 1){
			for(i = 0; i < form.localGrupoLgpFilho.length; i++){
				if(form.localGrupoLgpFilho[i].checked == false){
					checkTodos = false;
				}		
			}
		}
		
		if(numChecks == 1){
			if(form.localGrupoLgpFilho.checked == false)
				checkTodos = false;
		}
		
		if(checkTodos)
			form.todos.checked = true;
}
function validarGravar(form){ 
	if(form.localGrupoLgp.value == ""){
		alert("<%=_msg.getMensagem("localGrupo.validacao.localGrupoLgp.obrigatorio")%>");
		form.localGrupoLgp.focus();
		return(false);		
	}
	return(true);
}

function reload(form){
	form.action = "frm_inc.jsp";
	form.submit();
}

</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form); javascript:verificaTodos(document.forms[0])">

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codLocalGrupoLgp" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

	<%
	try{

	LocalGrupoDao localGrupoDao = new LocalGrupoDao(request);
	List gruposFilho =new ArrayList();
	List gruposExcluidos = new ArrayList();
	String selected = "";
	if(!"".equals(Pagina.getParamStr(request, "localGrupoLgp"))){
    	LocalGrupoLgp localGrupoPai =  (LocalGrupoLgp) localGrupoDao.buscar( LocalGrupoLgp.class, Long.valueOf( Pagina.getParamStr(request, "localGrupoLgp") ) );
    	selected = localGrupoPai.getCodLgp().toString();
    	gruposFilho = localGrupoDao.getFilhosById(localGrupoPai);
    	gruposExcluidos = localGrupoDao.getAscendentes(localGrupoPai);
    	gruposExcluidos.add(localGrupoPai);
	} 
	%>

			<table class="form"> 
			<tr><td class="label">&nbsp;</td></tr>
		
			<tr>
				<td width="20%" class="label" valign="top">Grupo</td>
				<td>
						<combo:ComboTag 
								nome="localGrupoLgp"
								objeto="ecar.pojo.LocalGrupoLgp" 
								label="identificacaoLgp" 
								value="codLgp" 
								order="identificacaoLgp"
								selected="<%=selected%>"
								filters="indAtivoLgp=S"
								scripts="onchange=reload(document.forms[0]);"		
						/>
				</td>
			</tr>
			<tr>
				<td width="20%" class="label" valign="top">Grupos Filhos</td>
				<td>
				<input type="checkbox" class="form_check_radio" name="todos" onclick="selectAll(document.forms[0])"> Todos	<br><br>	
				<%if(!"".equals(Pagina.getParamStr(request, "localGrupoLgp"))){%>
						<combo:CheckListTag 
								nome="localGrupoLgpFilho"
								objeto="ecar.pojo.LocalGrupoLgp" 
								label="identificacaoLgp" 
								value="codLgp" 
								objetosExcluidos="<%=gruposExcluidos%>"
								selected="<%=gruposFilho%>"
								filters="indAtivoLgp=S"
								order="identificacaoLgp"
						/>		
				<%}else{%>		
				Selecione o grupo pai.
				<%}%>
				</td>
			</tr>	



	<%
	
	}catch(ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
		%>
		<script language="javascript">
		document.form.action = "frm_pes.jsp";
		document.form.submit();
		</script>
		<%
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
%>

	
	<tr><td class="label">&nbsp;</td></tr>
	
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	
	<%@ include file="../../include/estadoMenu.jsp"%>
</form>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>