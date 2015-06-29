<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.RecursoRec" %>
<%@ page import="ecar.dao.RecursoDao" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.FonteRecursoFonr" %>
<%@ page import="ecar.pojo.EfIettFonteTotEfieft" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiep" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ExercicioDao" %> 
<%@ page import="ecar.dao.FonteRecursoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.ItemEstruturaFonteRecursoDao" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoDao" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoRevDao" %>
<%@ page import="ecar.pojo.EfIettPrevisaoRevEfiepr" %>

<%@ page import="comum.util.Util" %> 
<%@ page import="comum.util.Data" %>

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Date" %>  
<%@ page import="java.util.ArrayList" %> 
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="ecar.pojo.ItemEstruturarevisaoIettrev" %>
<%@ page import="ecar.dao.ItemEstruturarevisaoIettrevDAO" %>
<%@ page import="ecar.pojo.EfIettFonTotRevEfieftr" %>
<%@ page import="ecar.dao.ItemEstruturaFonteRecursoRevDao" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%> 
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>

<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script>
function aoClicarBtn1(form){
	form.btn1[0].disabled = true;
	form.btn1[1].disabled = true;
	form.hidAcao.value = "alterar";
	form.action = "frm_alt.jsp";
	form.submit();
}	

function aoClicarVoltar(form){
	window.close();
} 
function mudaTela(codFr){
	document.form.codFonr.value = codFr;
	document.form.action = "../recurso/lista.jsp";
	document.form.submit();	
}

function getDadosPesquisa(codigo, descricao){
	document.form.codFonr.value = codigo;
	document.form.nomeFonr.value = descricao;
}
</script>
</head>

<%
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	
	ItemEstruturarevisaoIettrevDAO itemEstruturaRevisaoDao = new ItemEstruturarevisaoIettrevDAO(request);
	ItemEstruturarevisaoIettrev itemEstruturaRevisao = (ItemEstruturarevisaoIettrev) itemEstruturaRevisaoDao.buscar(ItemEstruturarevisaoIettrev.class,Integer.valueOf(Pagina.getParamStr(request, "codIettrev")));
	EfIettFonTotRevEfieftr ieFonte = new ItemEstruturaFonteRecursoRevDao(request).buscar(
												 Long.valueOf(Pagina.getParamStr(request, "codIettrev")), 
												 Long.valueOf(Pagina.getParamStr(request, "codFonr")));
	
	String codAba = Pagina.getParamStr(request, "codAba");
	String codAbaRecurso = Pagina.getParamStr(request, "codAbaRec");
%>
				
<%
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAbaRecurso));
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
%>

<H1><%=estruturaFuncao.getLabelEttf()%></H1>
	
<form  name="form" method="post" >

	<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Cancelar"/>
	<%
	_disabled = "disabled";
	_readOnly = "readonly";
	%>

	<table name="form" class="form">
	<%@ include file="form.jsp"%>
	</table>
	
	<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Cancelar"/>

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

<br>
</div>
</body>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>