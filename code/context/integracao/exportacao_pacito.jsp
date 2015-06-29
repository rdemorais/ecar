<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<%@ page import="ecar.pojo.SisGrupoAtributoSga"%>
<%@ page import="ecar.pojo.SisTipoExibicGrupoSteg"%>
<%@ page import="ecar.taglib.util.Input"%>
<%@ page import="ecar.dao.SisGrupoAtributoDao"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="comum.util.Pagina" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/datas.js" type="text/javascript"></script>
<script language="javascript">
function validarPesquisar(form){
	return(true);
}
</script>
 
<title>	Exportação para o Pacito </title>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<%

String indTabelaUso ="";
if(session.getAttribute("indTabelaUso")!=null)
 indTabelaUso = session.getAttribute("indTabelaUso").toString();

try{
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
		
	<util:barrabotoes pesquisar="Gerar" cancelar="Cancelar"/>

<%
	SisAtributoSatb atributo;
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		atributo = (SisAtributoSatb) session.getAttribute("objPesquisa");
	}else{
		atributo = new SisAtributoSatb();
  	}
	_disabled = "";
    _comboSimNao = "Todos";	
	_obrigatorio = "";    
%>
	
	<table class="form">
		<tr>
			<td class="label"><%=_obrigatorio%> Início</td>
			<td>
				<input type="text" name="dataInicio" size="15" maxlength="10" value="" onkeyup="mascaraData(event, this);">
				<img class="posicao" title="Selecione a data" src="../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataInicio,'','../calendario/calendario.jsp')">
			</td>
		</tr>
		<tr>
			<td class="label"><%=_obrigatorio%> Fim</td>
			<td>
				<input type="text" name="dataFim" size="15" maxlength="10" value="" onkeyup="mascaraData(event, this);">
				<img class="posicao" title="Selecione a data" src="../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataFim, '','../calendario/calendario.jsp')">
			</td>
		</tr>
	</table>
	<util:barrabotoes pesquisar="Gerar" cancelar="Cancelar"/>	
	
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
<%@ include file="../../include/estadoMenu.jsp"%>
</html>