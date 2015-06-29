<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>
<%@ page import="ecar.pojo.LocalItemLit" %>
<%@ page import="ecar.dao.LocalItemDao" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ include file="../../frm_global.jsp"%>

<html lang="pt-br"> 
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>

<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoesAtributoLivre.js"></script>

<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">

function validarAlterar(form){
	if(form.localGrupoLgp.value == ""){
		alert("<%=_msg.getMensagem("localItem.validacao.localGrupoLgp.obrigatorio")%>");
		form.localGrupoLgp.focus();
		return(false);
	}
	
	if(!validaString(form.identificacaoLit,'Descrição',true)){
		return(false);
	}
	
	if(!validarCamposVariaveis(form)){
		return(false);
	}
	
	return(true);
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

<%
	try {
		LocalItemDao localItemDao = new LocalItemDao(request);
		LocalItemLit localItem;
		
		if(session.getAttribute("objLocalItem") != null){
			localItem = (LocalItemLit) session.getAttribute("objLocalItem");
			session.removeAttribute("objLocalItem");
		}else{
			localItem = (LocalItemLit) localItemDao.buscar(LocalItemLit.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));;
	  	}
	  	
	  	boolean pesquisa = false;
		
		_disabled = "";
%>

		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
	
		<input type="hidden" name="codigo" value="<%=localItem.getCodLit()%>">

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