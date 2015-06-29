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
<%@ page import="java.util.HashSet" %> 
<%@ include file="../../frm_global.jsp"%>



<%@page import="ecar.dao.OrgaoDao"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>

<script language="javascript" src="../../js/cnpj.js"></script>
<script language="javascript" src="../../js/cpf.js"></script>
<script language="javascript" src="../../js/cep.js"></script>
<script language="javascript" src="../../js/email.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script type="text/javascript" src="../../js/selectMultiplos.js"></script>

<script language="javascript" src="../../js/validacoesAtributoLivre.js"></script>
<script language="javascript" src="../../js/datas.js"></script>

<script language="javascript">
function abrirPopUpSentinela(){
	abreJanela("popUpPesquisarUsuarioSentinela.jsp",500, 300, "usuarioSetinela");
}
function abrirPopUpUpload(nome, labelCampo, excluir){
	abreJanela("popUpUpload.jsp?nomeCampo="+nome+"&labelCampo="+labelCampo+"&excluir="+excluir,
				500, 100, nome);
}
function setarValoresSentinela(codUsu, nomeUsu, login, cpfCnpjUsu, email){
	document.forms[0].nomeUsu.value = nomeUsu;
	document.forms[0].idUsuarioUsu.value = login;	
	document.forms[0].idDominioUsu.value = codUsu;	
	document.forms[0].cnpjCpfUsu.value = cpfCnpjUsu;		
	document.forms[0].email1Usu.value = email;	
}
function validarAlterar(form){

	selAllCombo(form.vinculandos);
 	selAllCombo(form.naoVinculandos);

	if(form.indAtivoUsu[0].checked == false && form.indAtivoUsu[1].checked == false){
		alert("<%=_msg.getMensagem("usuario.validacao.indAtivoUsu.obrigatorio")%>");
		form.indAtivoUsu[0].focus();
		return false;
	}
	if(!validaString(form.nomeUsu, "Nome", true)){
		return false;
	}  
	var qtdeOrgao = 0;
	for (i = 1; i <= parseInt(document.getElementById('numOrgaos').value); i++){
		if (document.getElementById("adicionaOrgaoOrg"+i).value == "S") {
			qtdeOrgao++;
			break;
		}
	}
			
	if (qtdeOrgao == 0) {
		alert ("É obrigatório informar pelo menos um Órgão. Selecione um órgão e clique em Adicionar.");
		return false;
	}  

//	var qtdeEntidade = 0;
//	for (i = 1; i <= parseInt(document.getElementById('numEntidades').value); i++){
//		if (document.getElementById("adicionaEntidadeEnt"+i).value == "S") {
//			qtdeEntidade++;
//			break;
//		}
//	}
			
//	if (qtdeEntidade == 0) {
//		alert ("É obrigatório informar pelo menos uma Entidade. Selecione uma Entidade e clique em Adicionar.");
//		return false;
//	}  

	if(Trim(form.dataNascimentoUsu.value) != ""){
		if(!validaData(form.dataNascimentoUsu,false,true,true)){
			if(form.dataNascimentoUsu.value == ""){
		   		alert("<%=_msg.getMensagem("usuario.validacao.dataNascimento.obrigatorio")%>");
		  	}else{
		   		alert("<%=_msg.getMensagem("usuario.validacao.dataNascimento.invalido")%>");
		  	}
		  	return(false);
		}
	}
  

	if(!validaString(form.idUsuarioUsu, "Login", true)){
		return false;
	}  
	var numChecks = verificaChecks(form, "classeAcesso");
	encontrouChecked = false;
	if(numChecks > 1){
		for(i = 0; i < form.classeAcesso.length; i++){
			if(form.classeAcesso[i].checked == true){
		    	encontrouChecked = true;
		    } 
		}
	}else{
		if(form.classeAcesso.checked == true)
			encontrouChecked = true;
	}		 
	if(!encontrouChecked){
		alert("<%=_msg.getMensagem("usuario.validacao.classeAcesso.obrigatorio")%>");
		return false; 
	} 
	if ( ! validaCamposVariaveis(form) ) 
		return false;	

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
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
<%	try {
		UsuarioUsu usuario;
		UsuarioDao usuarioDao = new UsuarioDao(request);
		OrgaoDao orgaoDao = new OrgaoDao(request);
		
		if(session.getAttribute("objUsuario") != null){
			usuario = (UsuarioUsu) session.getAttribute("objUsuario");
			session.removeAttribute("objUsuario");
		}else{
			usuario = (UsuarioUsu) usuarioDao.buscar(UsuarioUsu.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
	  	}
		boolean pesquisa = false;	
		boolean insere = false;				
		boolean popUpPesquisa = false;					
		_disabled = "";
		
		
		List listaOrgaos = orgaoDao.consultarOrgaosAtivosOuAssociadoUsuario (usuario); 
		
%>
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

		<input type="hidden" name="codigo" value="<%=usuario.getCodUsu()%>">

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