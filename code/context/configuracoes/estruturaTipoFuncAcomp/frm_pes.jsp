<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.EstrutTpFuncAcmpEtttfa" %>
<%@ page import="ecar.dao.EstruturaTipoFuncAcompDao" %> 
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

<script language="javascript" type="text/javascript">
function validarPesquisar(form){
	if(Trim(form.seqApresentTelaCampoEtttfa.value) != ""){
		if(!validaNum(form.seqApresentTelaCampoEtttfa, "Seqüência em Tela de Cadastramento", true)){
			form.seqApresentTelaCampoEtttfa.focus();
			return(false);
		}
	}
	if(Trim(form.seqApresListagemTelaEtttfa.value) != ""){
		if(!validaNum(form.seqApresListagemTelaEtttfa, "Seqüência em Lista do Cadastro de Itens", true)){
			form.seqApresListagemTelaEtttfa.focus();
			return(false);
		}
	}
	if(Trim(form.larguraListagemTelaEtttfa.value) != ""){
		if(!validaNum(form.larguraListagemTelaEtttfa, "Largura em Lista do Cadastro de Itens", true)){
			form.larguraListagemTelaEtttfa.focus();
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
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
<%

boolean ehPesquisa=true;

try{
	
	List listaEstrutura = new ArrayList(0);
	List listaTipoFuncAcomp = new ArrayList(0);

	String selectedEstrutura = Pagina.getParamStr(request, "estruturaEtt");
	String selectedTipoFuncAcomp = Pagina.getParamStr(request, "tipoFuncAcompTpfa");

	EstrutTpFuncAcmpEtttfa estruturaTipoFuncAcomp = new EstrutTpFuncAcmpEtttfa();
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
	    /* este TRY existe para o caso de haver um objPesquisa na sessão criado em outra tela. Não permite que um outro 
	       objeto que não o EstrutTpFuncAcmpEtttfa seja utilizado nessa pesquisa */
		try{
			estruturaTipoFuncAcomp = (EstrutTpFuncAcmpEtttfa) session.getAttribute("objPesquisa");			
		}catch(Exception e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			estruturaTipoFuncAcomp = new EstrutTpFuncAcmpEtttfa();			
		}
	}else{
		estruturaTipoFuncAcomp = new EstrutTpFuncAcmpEtttfa();
  	}
	EstruturaTipoFuncAcompDao estruturaTipoFuncAcompDao = new EstruturaTipoFuncAcompDao(request);
	estruturaTipoFuncAcompDao.controlaListas(request,  listaEstrutura,  listaTipoFuncAcomp);
	
	//lFuncoesAcompanhamento =estruturaDao.getFuncoesAcompanhamentoById(estrutura);

	_disabled = "";
	_obrigatorio = "";
	String _pesqdisabled = "disabled";
%>
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
		//document.form.action = "ctrl.jsp";
		//document.form.submit();
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