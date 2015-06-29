<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ include file="../../frm_global.jsp"%>

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

function validarAlterar(form){
	if(form.sisGrupoAtributoSga.value == ""){
		alert("<%=_msg.getMensagem("sisAtributo.validacao.sisGrupoAtributoSga.obrigatorio")%>");
		form.sisGrupoAtributoSga.focus();
		return(false);
	}
	
	if(!validaString(form.descricaoSatb,'Descrição',true)){
		return(false);
	}

	
	if (form.atribInfCompSatb2.value == 'autoIncrementalScript' || form.atribInfCompSatb2.value == 'mascaraScript' || form.atribInfCompSatb2.value == 'mascaraEditavelScript') {
		if(!validaRadioSelecionado(form.geral,'geral')){
			alert("Obrigatório o preenchimento do campo Geral!");
			return(false);
		}

		if(!validaRadioSelecionado(form.periodico,'periodico')){
			alert("Obrigatório o preenchimento do campo Periódico!");
			return(false);
		}
	}
	

	if (!validarMascara(form.mascara.value,form.atribInfCompSatb2.value,'<%=EcarCfg.getConfiguracao("caracter.mascara.mes")%>','<%=EcarCfg.getConfiguracao("caracter.mascara.ano")%>','<%=EcarCfg.getConfiguracao("caracter.mascara.sequencial")%>')){
		return(false);
	}
	
	return(true);
}

function aoCarregar(form){
	onLoad(form);
	aoMudarTipoValidacao();
	habilitaPanelsID();
}

</script>
<%String indTabelaUso = session.getAttribute("indTabelaUso").toString();%>
<title>
	<%=_msg.getMensagem("atributo.titulo."+indTabelaUso)%>
</title>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:aoCarregar(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
<%	
	try {
		SisAtributoDao atributoDao = new SisAtributoDao(request);
		SisAtributoSatb atributo;
		
		if(session.getAttribute("objStributo") != null){
			atributo = (SisAtributoSatb) session.getAttribute("objAtributo");
			session.removeAttribute("objAtributo");
		}else{
			atributo = (SisAtributoSatb) atributoDao.buscar(SisAtributoSatb.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
	  	}
		
		_disabled = "";
	%>
	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>	
	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
	
		<input type="hidden" name="codigo" value="<%=atributo.getCodSatb()%>">

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