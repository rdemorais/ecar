<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");
String indTabelaUso = session.getAttribute("indTabelaUso").toString();
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
function validarGravar(form){
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
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>	
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

<%
	SisAtributoSatb atributo = new SisAtributoSatb();
	
	if(session.getAttribute("objAtributo") != null){
		atributo = (SisAtributoSatb) session.getAttribute("objAtributo");
		session.removeAttribute("objAtributo");
	}else{
		atributo = new SisAtributoSatb();
  	}
	
	_disabled = "";
%>
	<input type="hidden" name="codSatb" value="<%=Pagina.trocaNull(atributo.getCodSatb())%>">
	
	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	
</form>

</div>
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