<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.OpcaoOpc" %>
<%@ page import="ecar.pojo.PerfilPfl" %>
<%@ page import="ecar.dao.PerfilDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
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

<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/select.js"></script>
<script language="javascript">
/*
*************************************************************************
* FUNÇÕES aoClicarGravar(form), aoClicarCancelar(form) e onLoad(form)   *
*  não foram adicionadas pelo frmPadrao.js, pois o botão Cancelar irá   *
*  executar uma ação específica para esta tela.                         *
*************************************************************************
*/

function aoClicarGravar(form){
	if(validarGravar(form)){
		form.hidAcao.value = "incluir";
		form.action = "ctrl.jsp"
		form.submit();
	}
}	

function aoClicarCancelar(form){
	form.perfilPfl.value = "<%=Pagina.getParamStr(request, "perfilPfl").toString()%>";
	form.action = "frm_inc.jsp";
	form.submit();
}

function onLoad(form) {
	form.reset();
	focoInicial(form);
}

function validarGravar(form){
	if(form.perfilPfl.value == ""){
		alert("<%=_msg.getMensagem("opcaoPerfil.validacao.perfilPfl.procurar")%>");
		form.perfilPfl.focus();
		return(false);
	}
	
	if(form.perfilPfl.value != "<%=Pagina.getParamStr(request, "perfilPfl").toString()%>"){
		alert("<%=_msg.getMensagem("opcaoPerfil.validacao.perfilPfl.trocarProcurar")%>");
		form.perfilPfl.focus();
		return(false);
	}
	
	marca(form);
	return(true);
}

function validarProcurar(form){
	if(form.perfilPfl.value == ""){
		alert("<%=_msg.getMensagem("opcaoPerfil.validacao.perfilPfl.procurar")%>");
		form.perfilPfl.focus();
		return(false);
	}
	
	form.action = "frm_inc.jsp";
	form.submit();
}

function adicionarOpcao(form){
	// PDI Utilizava a comparação abaixo, não funcionou
	//if (form.opcaoOpcDisponiveis.options[form.opcaoOpcDisponiveis.selectedIndex].text != "")

	if (form.opcaoOpcDisponiveis.value != "")
	{
   		err = 0;
   		tamanho  = form.opcaoOpcLiberadas.length;
   		uscod    = form.opcaoOpcDisponiveis.value;
   		usnome   = form.opcaoOpcDisponiveis.options[form.opcaoOpcDisponiveis.selectedIndex].text;

   		var x = form.opcaoOpcLiberadas.options.length;

     	for (i=0; i<form.opcaoOpcLiberadas.length; i++)
     	{
       		var cod = form.opcaoOpcLiberadas.options[i].value;
       		if (cod == uscod){
         		err = 1;
	       	}
     	}

   		if (err != 1)
       		form.opcaoOpcLiberadas.options[x] = new Option(usnome,uscod);
       	
       	if ( form.opcaoOpcDisponiveis.selectedIndex != -1)
 	       form.opcaoOpcDisponiveis.options[form.opcaoOpcDisponiveis.selectedIndex] = null;
 	       
 	    sortSelect(form.opcaoOpcLiberadas, true);
  	}
}

function retirarOpcao(form){
	// PDI Utilizava a comparação abaixo, não funcionou
	//if (form.opcaoOpcLiberadas.options[form.opcaoOpcLiberadas.selectedIndex].text != "")
	
	if (form.opcaoOpcLiberadas.value != "")
	{
   		err = 0;
   		tamanho  = form.opcaoOpcDisponiveis.length;
   		uscod    = form.opcaoOpcLiberadas.value;
   		usnome   = form.opcaoOpcLiberadas.options[form.opcaoOpcLiberadas.selectedIndex].text;

   		var x = form.opcaoOpcDisponiveis.options.length;

     	for (i=0; i<form.opcaoOpcDisponiveis.length; i++)
     	{
       		var cod = form.opcaoOpcDisponiveis.options[i].value;
       		if (cod == uscod){
         		err = 1;
	       	}
     	}

   		if (err != 1)
       		form.opcaoOpcDisponiveis.options[x] = new Option(usnome,uscod);
       	
       	if (form.opcaoOpcLiberadas.selectedIndex != -1)
 	       form.opcaoOpcLiberadas.options[form.opcaoOpcLiberadas.selectedIndex] = null;
 	       
 	    sortSelect(form.opcaoOpcDisponiveis, true);
  	}
}

function marca(form){
  	for (i=0; i<form.opcaoOpcLiberadas.length; i++)
  	{
    	if (form.opcaoOpcLiberadas.options[i].value != " " && form.opcaoOpcLiberadas.options[i].value != "")
      		form.opcaoOpcLiberadas.options[i].selected = true;
	    else
      		form.opcaoOpcLiberadas.options[i].selected = false;
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
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<h1>Opção x Perfil</h1>

	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

	<table class="form">
		<tr><td class="label">&nbsp;</td></tr>
		<tr>
			<td class="label"></td>
			<td><input type="button" class="botao" value="Procurar" onclick="validarProcurar(form);"></td>
		</tr>
		<tr>
			<td class="label">* Descrição</td>
			<td>
	<%
			if ( Pagina.getParam(request, "perfilPfl") != null){
	%>		
				<combo:ComboTag 
						nome="perfilPfl"
						objeto="ecar.pojo.PerfilPfl"
						label="descricaoPfl"
						value="codPfl"
						order="descricaoPfl"
						selected="<%=Pagina.getParamStr(request, "perfilPfl").toString()%>"
					/>
	<%
			}else{
	%>
				<combo:ComboTag 
						nome="perfilPfl"
						objeto="ecar.pojo.PerfilPfl"
						label="descricaoPfl"
						value="codPfl"
						order="descricaoPfl"
					/>
	<%
			}
	%>
			</td>
		</tr>

<%
		if (Pagina.getParam(request, "perfilPfl") != null){
			try{
				PerfilDao perfilDao = new PerfilDao(request);
				PerfilPfl perfil = (PerfilPfl) perfilDao.buscar(PerfilPfl.class, Long.valueOf(Pagina.getParam(request, "perfilPfl")));
				List opcoesDisponiveis = perfilDao.getOpcoesDisponiveis(perfil);
				
				List opcoesLiberadas = new ArrayList();
				opcoesLiberadas.addAll(perfil.getOpcaoPerfilOpcps());
%>
			<tr>
				<td class="label">Opções Disponíveis</td>
				<td>
					<combo:ComboTag 
							nome="opcaoOpcDisponiveis"
							objeto="ecar.pojo.OpcaoOpc"
							label="descricaoOpc"
							value="codOpcao"
							order="descricaoOpc"
							colecao="<%=opcoesDisponiveis%>"
							scripts=" multiple size=5 style='width:300' "
							option="nao"
						/>
				</td>
			</tr>
			<tr>
				<td class="label"></td>
				<td>
					<input type="button" class="botao" value="\/ - Adicionar" onclick="adicionarOpcao(form);">
					<input type="button" class="botao" value="/\ - Retirar" onclick="retirarOpcao(form);">
				</td>
			</tr>
			<tr>
				<td class="label">Opções Liberadas</td>
				<td>
					<combo:ComboTag 
							nome="opcaoOpcLiberadas"
							objeto="ecar.pojo.OpcaoOpc"
							label="descricaoOpc"
							value="codOpcao"
							order="descricaoOpc"
							colecao="<%=opcoesLiberadas%>"
							scripts=" multiple size=5 style='width:300' "
							option="nao"
						/>
				</td>
			</tr>
			
			<script language="JavaScript">
				sortSelect(form.opcaoOpcLiberadas, true);
			</script>
			
<%
			}catch(ECARException e){
				org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
				Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
			} catch (Exception e){
			%>
				<%@ include file="/excecao.jsp"%>
			<%
			}
		}
%>		
		
		<tr><td class="label">&nbsp;</td></tr>
	</table>

	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	<%@ include file="../../include/estadoMenu.jsp"%>
</form>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>