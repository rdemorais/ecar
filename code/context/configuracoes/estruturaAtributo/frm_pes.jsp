<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.EstruturaAtributoEttat" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.EstruturaAtributoDao" %>
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
<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
function validarPesquisar(form){
	if(Trim(form.labelTamanhoConteudo.value) != ""){
		if(!validaNum(form.labelTamanhoConteudo, "Tamanho do Conteúdo", true)){
			form.labelTamanhoConteudo.focus();
			return(false);
		}
	}
	if(Trim(form.seqApresentTelaCampoEttat.value) != ""){
		if(!validaNum(form.seqApresentTelaCampoEttat, "Sequência de Apresentação em Telas de Informação", true)){
			form.seqApresentTelaCampoEttat.focus();
			return(false);
		}
	}
	if(Trim(form.seqApresListagemTelaEttat.value) != ""){
			if(!validaNum(form.seqApresListagemTelaEttat, "Sequência de Apresentação em Telas de Listagem", true)){
				form.seqApresListagemTelaEttat.focus();
				return(false);
			}	
	}
	if(Trim(form.larguraListagemTelaEttat.value) != ""){
			if(!validaNum(form.larguraListagemTelaEttat, "Largura da Coluna em Telas de Listagem", true)){
				form.larguraListagemTelaEttat.focus();
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
<%

boolean ehPesquisa = true;

try{
	
	List listaEstrutura = new ArrayList(0);
	List listaAtributo = new ArrayList(0);
	List listaFuncao = new ArrayList(0);

	String selectedEstrutura = Pagina.getParamStr(request, "estruturaEtt");
	String selectedAtributo = Pagina.getParamStr(request, "atributosAtb");
	String selectedFuncao = Pagina.getParamStr(request, "funcaoFun");

	EstruturaAtributoEttat estruturaAtributo = new EstruturaAtributoEttat();
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
	    /* este TRY existe para o caso de haver um objPesquisa na sessão criado em outra tela. Não permite que um outro 
	       objeto que não o EstruturaFuncaoEttf seja utilizado nessa pesquisa */
		try{
			estruturaAtributo = (EstruturaAtributoEttat) session.getAttribute("objPesquisa");			
		}catch(Exception e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			estruturaAtributo = new EstruturaAtributoEttat();			
		}
	}else{
		estruturaAtributo = new EstruturaAtributoEttat();
  	}

	EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);
				
	estruturaAtributoDao.controlaListas(request,  listaEstrutura,  listaAtributo, listaFuncao);

	_disabled = "";
	_obrigatorio = "";
	
	String strReadOnly = "false";
	boolean blVisualizaDesc = false;
	String _pesqdisabled = "disabled";
	String obrigatorioDisabled = "";

%>
<div id="conteudo">

<form name="form" method="post" action="">
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