<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
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
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script type="text/javascript" src="../../js/selectMultiplos.js"></script>
<script language="javascript" src="../../js/datas.js"></script>

<script language="javascript">
function validarPesquisar(form){
	if(form.orgaoOrg.value != ""){
		var qtdeOrgao = 0;
		for (i = 1; i <= parseInt(document.getElementById('numOrgaos').value); i++){
			
				qtdeOrgao++;
				break;
		}
				
		if (qtdeOrgao == 0) {
		   alert ("Para pesquisar por Orgão, selecione um órgão e clique em Adicionar.");
		   return false;
		}  
	}
	return(true);
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
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

	<%
	try{
	
		UsuarioUsu usuario = null;
		if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
			usuario = (UsuarioUsu) session.getAttribute("objPesquisa");
		}else{
			usuario = new UsuarioUsu();
	  	}

		UsuarioDao usuarioDao = new UsuarioDao(request);
		OrgaoDao orgaoDao = new OrgaoDao(request);
		
		_disabled = "";
	    _comboSimNao = "Todos";		
	    _obrigatorio = "";
	    boolean pesquisa = true;
		boolean insere = false;					    
		boolean popUpPesquisa = false;	
		
		
		List listaOrgaos = orgaoDao.consultarOrgaosAtivosOuAssociadoUsuario (null); 
		
			%>

			<table class="form">
			<%@ include file="form.jsp"%>
			</table>
		
			<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>	

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