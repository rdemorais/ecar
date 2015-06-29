<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
function validarPesquisar(form){
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
	return(true);
}
function reload(form){
	form.action = "frm_pes.jsp";
	form.submit();
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="">
	<%
	
	boolean ehPesquisa = true;
	
	try{
		
		List listaEstrutura = new ArrayList(0);
		List listaFuncao = new ArrayList(0);

		String selectedEstrutura = Pagina.getParamStr(request, "estruturaEtt");
		String selectedFuncao = Pagina.getParamStr(request, "funcaoFun");
	
		EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
		if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		    /* este TRY existe para o caso de haver um objPesquisa na sessão criado em outra tela. Não permite que um outro 
		       objeto que não o EstruturaFuncaoEttf seja utilizado nessa pesquisa */
			try{
				estruturaFuncao = (EstruturaFuncaoEttf) session.getAttribute("objPesquisa");			
			}catch(Exception e){
				org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
				estruturaFuncao = new EstruturaFuncaoEttf();			
			}
		}else{
			estruturaFuncao = new EstruturaFuncaoEttf();
	  	}
		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);

		estruturaFuncaoDao.controlaListas(request,  listaEstrutura,  listaFuncao);

		_disabled = "";
		_obrigatorio = "";

		String strReadOnly = "false";
		boolean blVisualizaDesc = false;
		String _pesqdisabled = "disabled";
%>
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop pesquisar="frm_pes.jsp"/>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

	<%@ include file="form.jsp"%>
		
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>	
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
<%@ include file="/include/mensagemAlert.jsp"%>
</html>