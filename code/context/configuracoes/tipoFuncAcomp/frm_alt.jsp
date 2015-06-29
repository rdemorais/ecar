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
<%@ include file="validacao.jsp" %>
<script language="javascript" type="text/javascript">
function validarAlterar(form){	
	return valida(form);
}

<%--
Comentado Ref. Bug 5255
function selectEmitePosicao(opt) {
	if( opt == 'S' ) { 
    	document.getElementById('emitePosicao').style.display = 'block';
    } else if( opt == 'N' ) { 
        document.getElementById('emitePosicao').style.display = 'none';
        document.form.indInitMonitTpfa.checked = false;
    	document.form.indNaoMonitTpfa.checked = false;
    }
}

function onloadNav(form) {
	if( form.indEmitePosicaoTpfa[0].checked == true) {
        document.getElementById('emitePosicao').style.display = 'block';
    }
}
--%>
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>
<%	


try {
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
		TipoFuncAcompTpfa tipoFuncAcomp = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));
		
		_disabled = "";
		
		//Excluir as funções filhos, neto **********************
		List funcoesFilho = new ArrayList();
		List listTipoFuncAcomp = new ArrayList();
		listTipoFuncAcomp.add(tipoFuncAcomp);
   		funcoesFilho = tipoFuncAcompDao.getDescendentes(listTipoFuncAcomp);
	   	funcoesFilho.add(tipoFuncAcomp);
		
	   	//List funcoesPermitidos = new ArrayList();
   		//funcoesPermitidos = tipoFuncAcompDao.getTipoFuncAcompPermitidos(funcoesFilho);
   		
	   	String strReadOnly = "false";
		boolean blVisualizaDesc = true;
		String _pesqdisabled = "";
		
%>

<div id="conteudo">

<form name="form" method="post" action="">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value='<%=Pagina.getParamInt(request, "hidRegistro")%>'>
	
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

		<input type="hidden" name="codigo" value="<%=tipoFuncAcomp.getCodTpfa()%>">
		
<% if(tipoFuncAcomp.getTipoFuncAcompTpfa() != null) {%>		
	<input type="hidden" name="tipoFuncAcompTpfaPaiAnterior" id="tipoFuncAcompTpfaPaiAnterior" value="<%=tipoFuncAcomp.getTipoFuncAcompTpfa().getCodTpfa().toString()%>">
<%
	if(tipoFuncAcompDao.existePermissaoAlterarParecer(tipoFuncAcomp.getTipoFuncAcompTpfa(), tipoFuncAcomp.getCodTpfa())) { 
%>
		<input type="hidden" name="existePermissao" id="existePermissao" value="true">
<%	} else { 
%>
		<input type="hidden" name="existePermissao" id="existePermissao" value="false">
<% 
	}
} else {
%>
	<input type="hidden" name="tipoFuncAcompTpfaPaiAnterior" id="tipoFuncAcompTpfaPaiAnterior" value="">
	<input type="hidden" name="permiteAlterar" id="existePermissao" value="false">
<%}

		
	


%>
		<%@ include file="form.jsp"%>
	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

<%
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		/* redireciona para o controle */
		%>
		<script language="javascript" type="text/javascript">
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
</html>