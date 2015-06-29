<%@ include file="../../../login/validaAcesso.jsp"%>
<%@ include file="../../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ItemEstrutLocalIettl" %>
<%@ page import="ecar.dao.ItemEstrutLocalDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ItemEstruturarevisaoIettrevDAO" %>
<%@ page import="ecar.pojo.ItemEstruturarevisaoIettrev" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../../js/menu_retratil.js"></script>
<script language="javascript" src="../../../js/focoInicial.js"></script>
<script language="javascript" src="../../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../../js/popUp.js"></script>

<script language="javascript">
<%@ include file="validacao.jsp"%>

/* declara uma variavel sem inicializar, para que seu typeof resulte em "undefined" */
/* isto serve para passar um parametro que nao interessa, porque nao foi possivel passar com ,, */
var variavel_undefined;

/*
 * Seleciona os dados da janela de pesquisa
 * E como uma interface que deve ser implementada para receber dados de uma janela de pesquisa
 * Sempre deve receber um código e uma descricao
 */
function getDadosPesquisa(codigo, descricao){
	document.form.codLit.value = codigo;
	document.form.identificacaoLit.value = descricao;
}

function aoClicarVoltar(form){
	window.close();	
}
</script>

</head>

<body onload="focoInicial(document.form);">

<%
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	ItemEstruturarevisaoIettrevDAO itemEstruturaRevDao = new ItemEstruturarevisaoIettrevDAO(request);
	ItemEstruturarevisaoIettrev itemEstruturaRev = (ItemEstruturarevisaoIettrev) itemEstruturaRevDao.buscar(ItemEstruturarevisaoIettrev.class, Integer.valueOf(Pagina.getParamStr(request, "codIettrev")));
	
	String codAbaLocal = Pagina.getParamStr(request, "codAbaLocal");
	String codAba = Pagina.getParamStr(request, "codAba");
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAbaLocal));
%>
<!-- TITULO -->


<form name="form" method="post">
	
	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>

	<table class="form">


		<input type=hidden name="hidAcao" value="incluir">
		<input type="hidden" name="inseriuMeta" value="S">
		<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
		<input type=hidden name="codIettrev" value="<%=Pagina.getParamStr(request,"codIettrev")%>">
		<input type="hidden" name="codAba" value=<%=codAba%>>
	
		<tr>
			<td colspan=2>
				<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
			</td>
		</tr>
		<tr>
			<td class="label">* Local</td>
			<td>
				<input type="hidden" name="codLit" value="">
				<input type="text" name="identificacaoLit" size="25" value="" disabled>
				<input type="button" class="botao" name="pesquisarLit" value="Pesquisar" onclick="popup_pesquisa('ecar.popup.PopUpLocalItem', variavel_undefined, '&codLgp=<%=Pagina.getParamStr(request, "codLgp")%>')">
			</td>
		</tr>
		<tr><td class="label">&nbsp;</td></tr>
	
	</table>

	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
	
</form>

<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

</div>

</body>
<%@ include file="../../../include/mensagemAlert.jsp"%>
</html>