<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaRealizadoDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.EfItemEstContaEfiec" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiep" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.FonteRecursoFonr" %>

<%@ page import="comum.util.Data" %> 
<%@ page import="comum.util.Util" %> 

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Iterator" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%
try{ 
	/* carrega o usuário da session */
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	
	if(!"".equals(Pagina.getParamStr(request, "referencia"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT( acompReferenciaItem.getItemEstruturaIett().getCodIett() , seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}		
	
%>

<html lang="pt-br"> 
<head>

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script>
function recarregar(form){
	form.action = "financeiro.jsp";
	form.submit();
}
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].submit();
}
</script>
</head>

<body>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<div id="conteudo"> 

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<%@ include file="botoesAvancaRetrocede.jsp" %>
	<br>
	
	<util:barraLinksRegAcomp 
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		selectedFuncao="FINANCEIRO"
		usuario="<%=usuario%>"
		primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
		request="<%=request%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>"
	/>
	
	<br>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
		<tr class="linha_titulo" align="left">
			<td>
				Refer&ecirc;ncia: <%=acompReferenciaItem.getAcompReferenciaAref().getNomeAref()%>&nbsp;&nbsp;&nbsp;(<%=acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()%>)
			</td> 
		</tr>
		<tr class="linha_titulo" align="left">
			<td>
				M&ecirc;s/Ano: <%=acompReferenciaItem.getAcompReferenciaAref().getMesAref()%>/<%=acompReferenciaItem.getAcompReferenciaAref().getAnoAref()%>
			</td> 
		</tr>
		<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
	</table>

	<util:arvoreEstruturaElabAcomp
		itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
		codigoAcomp="<%=Pagina.getParamStr(request, "codAcomp")%>"
		contextPath="<%=_pathEcar%>"
		listaParaArvore="<%=(java.util.List)session.getAttribute("listaParaArvore")%>"  
		nivelPrimeiroIettClicado="<%=(String)session.getAttribute("nivelPrimeiroIettClicado")%>"
		abaAtual="<%=ecar.taglib.util.ArvoreEstruturaElabAcompTag.ABA_DADOS_BASICOS%>"
		primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
	/>

<form  name="form" method="post" >
	
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type=hidden name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type=hidden name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">

<%@ include file="../../relAcompanhamento/financeiroTabela.jsp" %>

</form>

<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem(e.getMessageKey()));
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>
</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>