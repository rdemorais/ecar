<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.FuncaoFun" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttfPK" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.FuncaoDao" %>
<%@ page import="java.util.List" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/exibeEscondeListAcomp.js" type="text/javascript"></script>

<%-- @ include file="../../js/exibeEscondeListAcomp.js" --%>
<script language="javascript" type="text/javascript">

function validarAlterar(form){
	setHiddenVal('rte');
	form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
	form.richText.value = form.richText.value.replace(/&gt;/g, ">");
	form.documentacaoEttf.value = form.richText.value;

	if(!validaString(form.labelEttf,'Label a utilizar no Nível', true)){
		return(false);
	}

	if((trim(form.seqApresentacaoTelaEttf.value) != "") || (trim(form.seqApresentacaoRelatorioEttf.value) != "")){
		if(!validaNum(form.seqApresentacaoTelaEttf, "Sequência de Apresentação de Tela", false)){
			alert("<%=_msg.getMensagem("estruturaFuncao.validacao.seqApresentacaoTelaEttf.invalido")%>");
			form.getseqApresentacaoTelaEttf.value.focus();
			return(false);
		}
		
		if(!validaNum(form.seqApresentacaoRelatorioEttf, "Sequência de Apresentação de Relatório", false)){
			alert("<%=_msg.getMensagem("estruturaFuncao.validacao.seqApresentacaoRelatorioEttf.invalido")%>");
			form.getseqApresentacaoRelatorioEttf.value.focus();
			return(false);
		}
	}
	return true;
}
</script>

<!-- sempre colocar o foco inicial no primeiro campo -->
</head><body onload="javascript:onLoad(document.form); document.form.estruturaEtt.disabled = true; document.form.funcaoFun.disabled = true">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="">
	<input type="hidden" name="hidAcao" value="alterar">
	<input type="hidden" name="hidRegistro" value='<%=Pagina.getParamInt(request, "hidRegistro")%>'>
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
	<%	
	
		boolean ehPesquisa = false;
	
		try {

		List listaEstrutura =new EstruturaDao(request).listar(EstruturaEtt.class, new String[]{"nomeEtt", "asc"});
		List listaFuncao = new FuncaoDao(request).listar(FuncaoFun.class, new String[]{"nomeFun", "asc"});

		String selected = "";

		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);

		EstruturaFuncaoEttfPK chave = new EstruturaFuncaoEttfPK();
		chave.setCodEtt(Long.valueOf(Pagina.getParamLong(request, "estruturaEtt")));
		chave.setCodFun(Long.valueOf(Pagina.getParamLong(request, "funcaoFun")));
		
		EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.buscar(EstruturaFuncaoEttf.class, chave);
				
		String selectedEstrutura = estruturaFuncao.getEstruturaEtt().getCodEtt().toString();
		String selectedFuncao = estruturaFuncao.getFuncaoFun().getCodFun().toString();
		
		_disabled = "";
		
		String strReadOnly = "false";
		boolean blVisualizaDesc = true;
		String _pesqdisabled = "";
%>
		<%/* campos hidden com código estrutura e código função pois nesta página as combos com estas informações
				estão desabilitadas e, por isso, não são submetidas */
		%>
		<input type="hidden" name="codEtt" value="<%=selectedEstrutura%>">
		<input type="hidden" name="codFun" value="<%=selectedFuncao%>">		

		<util:linkstop  pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

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