<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.ItemEstrutFisicoIettf" %>
<%@ page import="ecar.dao.ItemEstrutFisicoDao" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>

<%@ include file="../../include/exibirAguarde.jsp"%>

<%@page import="ecar.pojo.historico.HistoricoItemEstruturaIett"%>
<%@page import="ecar.dao.ItemEstrtIndResulIettrDao"%>
<%@page import="ecar.pojo.historico.HistoricoItemEstrtIndResulIettr"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/datas.js"></script>

<script language="javascript">
function aoClicarVoltar(form){
	form.action = "lista.jsp";
	form.submit();	
}
</script>

</head>

<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<%
	String tipoAcao = "consultar";
	try {
		ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
		String[] cods = Pagina.getParamArray(request, "codHist");
		HistoricoItemEstrtIndResulIettr itemEstrtIndResulAntes = itemEstrtIndResulDao.getHistorico(cods[0]);
		HistoricoItemEstrtIndResulIettr itemEstrtIndResulDepois = itemEstrtIndResulDao.getHistorico(cods[1]);
		ItemEstruturaIett itemEstrutura = itemEstrtIndResulAntes.getItemEstruturaIett();
		//itemEstrtIndResulAntes.

		ValidaPermissao validaPermissao = new ValidaPermissao();
		if (!validaPermissao.permissaoConsultaIETT(Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca)) {
			response.sendRedirect(request.getContextPath() + "/acessoIndevido.jsp");
		}

		String codAba = Pagina.getParamStr(request, "codAba");
%>

<%@ include file="/titulo_tela.jsp"%>



<%
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);

		estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));

		Boolean permissaoAlterar = new Boolean(false);//validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
%>

<br><br>
<!-- TITULO -->


<form name="form" method="post">
	<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Cancelar"/>

<%

		_disabled = "disabled";
		_readOnly = "readonly";
		String _disabledQtdePorLocal = "disabled";
		String _msgQtdePorLocal = "";

		String _disabledPrevQtdePorLocal = "disabled";
		String _msgPrevQtdePorLocal = "";

		ExercicioDao exercicioDao = new ExercicioDao(request);
		String codigosCor = "";
		String pathCores = "";
		String titleCores = "";
%>

	<input type="hidden" name="codIettir" id="codIettir" value="<%=Pagina.trocaNull(itemEstrtIndResulAntes.getCodIettir())%>">
	<table class="form">
	<tr>	
		<td>
			<table>
				<%@ include file="frm_historico_antes.jsp"%>
			</table>
		</td>
		<td>
			<table>
				<%@ include file="frm_historico_depois.jsp"%>
			</table>
		</td>
	</tr>
	</table>

	<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Cancelar"/>
	
</form>

<script language="JavaScript">
if( form.indAcumulavelIettr[1].checked == true) {
	document.getElementById('linha').style.display = "";
}
</script>

<%
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, e.getMessageKey());
	} catch (Exception e) {
%>
	<%@ include file="/excecao.jsp"%>
<%
	}
%>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
<%@ include file="../../include/ocultarAguarde.jsp"%>
</html>