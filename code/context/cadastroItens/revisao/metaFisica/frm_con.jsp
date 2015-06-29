<%@ include file="../../../login/validaAcesso.jsp"%>
<%@ include file="../../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttfPK" %>
<%@ page import="ecar.pojo.IettIndResulRevIettrr" %>
<%@ page import="ecar.pojo.ItemEstFisicoRevIettfr" %>
<%@ page import="ecar.dao.IettIndResulRevIettrrDAO" %>
<%@ page import="ecar.dao.ItemEstruturarevisaoIettrevDAO" %>
<%@ page import="ecar.pojo.ItemEstruturarevisaoIettrev" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="ecar.dao.ItemEstFisicoRevIettfrDAO" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../../js/menu_retratil.js"></script>
<script language="javascript" src="../../../js/focoInicial.js"></script>

<script language="javascript" src="../../../js/numero.js"></script>

<script language="javascript" src="../../../js/validacoes.js"></script>
<script language="javascript">
<%@ include file="validacao.jsp"%>

function aoClicarCancelar(form){
	form.reset();
}

function aoClicarBtn1(form){
	form.action = "frm_alt.jsp";
	form.submit();	
}

function aoClicarVoltar(form){
	window.close();
}
</script>

</head>

<body>

<%
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	ValidaPermissao validaPermissao = new ValidaPermissao();
	
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")) , seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	
	ItemEstruturarevisaoIettrevDAO itemEstruturaRevDao = new ItemEstruturarevisaoIettrevDAO(request);
	ItemEstruturarevisaoIettrev itemEstruturaRev = (ItemEstruturarevisaoIettrev) itemEstruturaRevDao.buscar(ItemEstruturarevisaoIettrev.class, Integer.valueOf(Pagina.getParamStr(request, "codIettrev")));
	
	String codAbaMeta = Pagina.getParamStr(request, "codAbaMeta");
	String codAba = Pagina.getParamStr(request, "codAba");
%>

<%
	
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAbaMeta));

	List indicadoresIett = new ArrayList(itemEstrutura.getItemEstrtIndResulIettrs());
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
%>
<!-- TITULO -->


<form name="form" method="post">
	<input type=hidden name="hidAcao" value="consultar">

	<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Voltar"/>

<%
	IettIndResulRevIettrrDAO itemEstrtIndResulDao = new IettIndResulRevIettrrDAO(request);
	IettIndResulRevIettrr itemEstrtIndResul = new IettIndResulRevIettrr();
	_disabled = "disabled";
	_readOnly = "readonly";
	Integer codIettrr = Integer.valueOf(Pagina.getParamStr(request, "codIettirr"));
	if (itemEstrtIndResulDao.buscar(IettIndResulRevIettrr.class, Integer.valueOf(Pagina.getParamStr(request, "codIettirr"))) != null){
		itemEstrtIndResul = (IettIndResulRevIettrr) itemEstrtIndResulDao.buscar(IettIndResulRevIettrr.class, codIettrr);
	}
%>

	<input type="hidden" name="codIettirr" value="<%=Pagina.trocaNull(itemEstrtIndResul.getCodIettirr())%>">
	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Voltar"/>
	
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