<!--  usuario/usuario -->
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.ArrayList" %> 
<%@ page import="comum.util.FileUpload" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="ecar.pojo.UsuarioAtributoUsua" %> 
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
function getSelections(select) {
    var options = select.options,
    sOptions = [],
    opt, k=0;
    while(opt = options[k++])
        if(opt.selected) {
            sOptions[sOptions.length] = opt;
    }
    return sOptions;
}
function copyOptionsFromComboArray(aComboArray) {
  var options = new Array();
  for (var i = 0; i < aComboArray.options.length; ++i) {
     options[i] = new Option(aComboArray.options[i].text, aComboArray.options[i].value);
  }
  return options;
}
function cleanComboArray(aComboArray) {
  var arraySize = aComboArray.options.length;
  for (var i = 0; i < arraySize; ++i) {
     aComboArray.options[i] = null;
  }
  aComboArray.length = 0;
}
function fillComboArray(comboArray, options) {
  for (var i = 0; i < options.length; ++i) {
     comboArray[comboArray.length] = options[i];
  }
  return options;
}
function adicionarMarcados(comboOrigem, comboDestino){
  if (comboOrigem.selectedIndex < 0) {
       return;
  }
  var selecionados = getSelections(comboOrigem);

  //Limpa referências. Problemas da raposa...
  var comboOrigemOptionsCopy = copyOptionsFromComboArray(comboOrigem)
  cleanComboArray(comboOrigem)
  fillComboArray(comboOrigem, comboOrigemOptionsCopy);

  var comboDestinoOptionsCopy = copyOptionsFromComboArray(comboDestino);
  cleanComboArray(comboDestino);
  fillComboArray(comboDestino, comboDestinoOptionsCopy);
  fillComboArray(comboDestino, selecionados);

  if (selecionados != '') {
     if (comboDestino.options.length > 0 && comboDestino.options[0].value == '-1') {
        comboDestino.length = 0;
     }
       for (var k = 0; k < selecionados.length; ++k) {
          for (var i = 0; i < comboOrigem.length; ++i) {
           if (comboOrigem.options[i].value == selecionados[k].value) {
              comboOrigem.options[i] = null;
           }
        }
     }
  }
}


function AoSelecionarUsuarioPopup(codigo,nome){
   document.forms[0].codUsuario.value=codigo;
   document.forms[0].nomeUsuario.value=nome;
   document.forms[0].nomeUsuario.disabled=false;
   document.forms[0].acao.value="";
   document.forms[0].submit();
}

function validar(form) {
   selAllCombo(form.vinculandos);
   selAllCombo(form.naoVinculandos);
   return true;
}

function AoClicarPrincipal(){
   frm = document.forms[0];
   if(frm){
       frm.action="principal.do";
       frm.submit();
   }
}
function AoClicarSalvar(){
        frm = document.forms[0];
        if(frm){
            validar(frm);
            frm.nomeUsuario.disabled=false;
            frm.acao.value="conf_vincular";
            frm.submit();
        }
}


</script>


</head>

<body onload="javascript:onLoad(document.form)">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codUsuarioUsu" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
	<%
	try{
	
	UsuarioUsu usuario;
	UsuarioDao usuarioDao = new UsuarioDao(request);
	
	if(session.getAttribute("objUsuario") != null){
		usuario = (UsuarioUsu) session.getAttribute("objUsuario");
		session.removeAttribute("objUsuario");
	}else{
		usuario = new UsuarioUsu();
  	}
	%>  
	<table class="form">
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
<tr>
	<td> &nbsp; </td>
	<td> &nbsp; </td>
</tr>


<!-- form name="usuarioGrupoForm" method="post" action="/sentinela/usuarioGrupo.do"-->

    <input type="hidden" name="acao" value="">
    <div id="conteudo_corpo">
      <table width="288" align="center" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="146" valign="top">
            <table class="tabela">
    		  <tr>
        		<td align="center" class="form_label">Grupos vinculados</td>
        		<td>&nbsp;</td>
		        <td align="center" class="form_label">Grupos não vinculados</td>
    		  </tr>
    		  <tr>
        		<td>
            	  <select name="vinculandos" id="vinculandos_esquerda" class="lista" multiple="true" >
	                <option value="12">Administrador ecar</option>
	                <option value="18">Consultas Acompanhamento</option>
	                <option value="14">Coordenador</option>
	                <option value="15">Coordenador SAM</option>
	                <option value="19">GEPAC</option>
	                <option value="13">Gerente</option>
	                <option value="17">Monitor Sala</option>
	                <option value="16">Sala Situação</option>
	                <option value="11">SEN - Administrador Geral Ecar</option>
            	  </select>
        		</td>
        		<td valign="middle">
            	  <div align="center">
					<input name="btnExcluir" type="button" id="btnExcluir" onClick="adicionarMarcados(document.getElementById('vinculandos_esquerda'), document.getElementById('naoVinculandos_direita'))"  value="&gt; &gt;">
					<br><br>
					<input name="btnIncluir" id="btnIncluir" type="button" onClick="adicionarMarcados(document.getElementById('naoVinculandos_direita'), document.getElementById('vinculandos_esquerda'))"value="&lt; &lt;">
				  </div>
        		</td>
		        <td>
        	      <select name="naoVinculandos" id="naoVinculandos_direita" class="lista" multiple="true">
				  	null              
				  </select>
        		</td>
    		  </tr>
			</table>
          </td>
        </tr>
      </table>
	</div>
  <!--/form-->
</table>

	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	
</form>

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