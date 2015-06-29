<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">

function validarPesquisar(form) {
	if(Trim(form.tamanhoSinalTpfa.value) != ""){
		if(!validaNum(form.tamanhoSinalTpfa, 'Tamanho da Imagem de Sinal', false)){
			alert("<%=_msg.getMensagem("tipoFuncAcomp.validacao.tamanhoSinalTpfa.invalido")%>");
			form.tamanhoSinalTpfa.focus();
			return(false);
		}
	}
	return(true);
}
/*
Comentado Ref. Bug 5255
function selectEmitePosicao(opt) {
	if( opt == 'S' ) { 
    	document.getElementById('emitePosicao').style.display = 'block';
    } else if( opt == 'N' ) { 
        document.getElementById('emitePosicao').style.display = 'none';
        document.form.indInitMonitTpfa.checked = false;
    	document.form.indNaoMonitTpfa.checked = false;
    }
}*/
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>
<%


try{
	TipoFuncAcompTpfa tipoFuncAcomp;
	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		tipoFuncAcomp = (TipoFuncAcompTpfa) session.getAttribute("objPesquisa");
	}else{
		tipoFuncAcomp = new TipoFuncAcompTpfa();
  	}
	
	_disabled = "";
	_obrigatorio = "";
	
	//Excluir as funções filhos, neto **********************
	List funcoesFilho = new ArrayList();
	
	//List funcoesPermitidos = new ArrayList();
   	//funcoesPermitidos = tipoFuncAcompDao.getTipoFuncAcompPermitidos(funcoesFilho);
   	
   	String strReadOnly = "true";
	boolean blVisualizaDesc = false;
	String _pesqdisabled = "disabled";
	
%>
<div id="conteudo">

<form name="form" method="post" action="">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

	<%@ include file="form.jsp"%>

	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>	
	
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
<%@ include file="/include/mensagemAlert.jsp"%>
</html>